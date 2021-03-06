/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.drawingtool.quillpen;

import android.graphics.Canvas;

import android.util.Log;
import com.orange.dgil.trail.android.animation.AnimManager;
import com.orange.dgil.trail.android.animation.AnimParameters;
import com.orange.dgil.trail.android.drawingtool.DrawingToolsContext;
import com.orange.dgil.trail.android.drawingtool.IDrawingTool;
import com.orange.dgil.trail.android.drawingtool.TrailOptions;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.quad.QuadCurveArrayException;
import com.orange.dgil.trail.core.vecto.SlidingWindowIndexException;

public class QuillPen implements IDrawingTool, QuillTrailBitmapListener {

  private final BitmapDrawer bitmapDrawer;
  private final QuillTrailBitmap quillTrailBitmap;
  private final QuillTrailBitmapEnd quillTrailBitmapEnd;
  private final QuadCurveTrail quadCurveTrail;
  private final QuadCurveTrail quadCurveTrailEnd;
  private final TrailOptions trailOptions;
  private final AnimManager animManager;
  private final AnimParameters animParameters;
  private final TrailPoint lastVectoPoint;
  private final TrailPoint lastRawPoint = new TrailPoint();

  private boolean inGesture;
  private boolean trailEndEnabled;
  private boolean quadCurveArrayIsFull;


  public QuillPen(DrawingToolsContext drawingToolsContext) {
    trailOptions = drawingToolsContext.getTrailOptions();
    animManager = drawingToolsContext.getAnimManager();
    animParameters = drawingToolsContext.getAnimManager().getAnimationParameters();
    bitmapDrawer = new BitmapDrawer(trailOptions);

    quadCurveTrail = new QuadCurveTrail(true, drawingToolsContext.getAndroidMetrics());
    lastVectoPoint = quadCurveTrail.getLastVectoPoint();

    quadCurveTrailEnd = new QuadCurveTrail(false, drawingToolsContext.getAndroidMetrics());

    quillTrailBitmap = new QuillTrailBitmap(drawingToolsContext, bitmapDrawer, quadCurveTrail.getQuadCurveArray(), this);
    quillTrailBitmapEnd = new QuillTrailBitmapEnd(drawingToolsContext, bitmapDrawer, quadCurveTrailEnd.getQuadCurveArray());
  }

  @Override
  public void reset() {
    quadCurveTrail.reset();
    quillTrailBitmap.reset();
  }

  @Override
  public void trimMemory() {
    Log.i(QuillPen.class.getSimpleName(), "trimMemory, release quill bitmaps");
    quillTrailBitmap.trimMemory();
    quillTrailBitmapEnd.trimMemory();
  }

  @Override
  public void forceRedrawForAnimation(boolean eraseBitmap) {
    quillTrailBitmap.forceRedrawForAnimation(eraseBitmap);
  }

  @Override
  public void touchDown(int x, int y) {
    inGesture = true;
    trailEndEnabled = false;
    quadCurveArrayIsFull = false;
    lastRawPoint.set(x, y);
    bitmapDrawer.updateDrawParameters();

    quadCurveTrail.touchDown(x, y);
    quillTrailBitmap.touchDown(x, y);
  }

  @Override
  public void touchMove(int x, int y) {
    lastRawPoint.set(x, y);
    if (!quadCurveArrayIsFull) {
      addTouchMove(x, y);
    }
  }
  private void addTouchMove(int x, int y) {
    try {
      doAddTouchMove(x, y);
    } catch (QuadCurveArrayException e) {
      quadCurveArrayIsFull = true;
    }
  }
  private void doAddTouchMove(int x, int y) {
    quadCurveTrail.touchMove(x, y);
    quillTrailBitmap.touchMove(x, y);
    addTouchMoveToTrailEnd(x, y);
  }

  private void addTouchMoveToTrailEnd(int x, int y) {
    if (trailEndEnabled) {
      quadCurveTrailEnd.touchMove(x, y);
      quillTrailBitmapEnd.touchMove(x, y);
    }
  }

  @Override
  public void touchUp() {
    inGesture = false;
    tryCurveTrailTouchUp();
    if (!quadCurveTrail.getQuadCurveArray().isNotEmpty()) {
      drawSinglePointOnTouchUp();
    }
    drawLastPointsOnTouchUp();
  }

  private void tryCurveTrailTouchUp() {
    try {
      quadCurveTrail.touchUp();
    } catch (QuadCurveArrayException e1) {
      // ignore as we want to skip points when buffer is full
    } catch (SlidingWindowIndexException e2) {
      // ignore since it can happen if we receive a "reset/clear" command during a gesture
    }
  }

  private void drawLastPointsOnTouchUp() {
    quillTrailBitmap.touchUp();
    addTouchUpToTrailEnd();
    invalidatePath();
  }

  private void drawSinglePointOnTouchUp() {
    quillTrailBitmap.drawSinglePointAt(lastRawPoint);
  }

  private void addTouchUpToTrailEnd() {
    if (trailEndEnabled) {
      quillTrailBitmapEnd.touchUp();
    }
  }

  @Override
  public void draw(Canvas canvas) {
    checkAnimationForceRedraw();
    quillTrailBitmap.draw(canvas);
    quillTrailBitmapEnd.draw(canvas);
  }

  private void checkAnimationForceRedraw() {
    if (animManager.isRunning() && !animParameters.areStartEndColorsRgbEqual()) {
      forceRedrawForAnimation(true);
    }
  }

  @Override
  public void invalidateAreaOnMove() {
    quillTrailBitmap.invalidateAreaOnMove();
    quillTrailBitmapEnd.invalidateAreaOnMove();
  }

  @Override
  public void invalidatePath() {
    quillTrailBitmap.invalidatePath();
  }

  @Override
  public void onLastDrawnPointChange(int x, int y) {
    if (inGesture) {
      initTrailEnd(x, y);
    }
  }

  private void initTrailEnd(int x, int y) {
    if (trailEndEnabled) {
      quillTrailBitmapEnd.touchUp();
    }
    trailEndEnabled = true;
    simulateTrailEndTouchDown(x, y);
    simulateTrailEndTouchMove(lastVectoPoint);
    simulateTrailEndTouchMove(lastRawPoint);
  }

  private void simulateTrailEndTouchMove(TrailPoint point) {
    int x = point.getX();
    int y = point.getY();
    quadCurveTrailEnd.touchMove(x, y);
    quillTrailBitmapEnd.touchMove(x, y);
  }

  private void simulateTrailEndTouchDown(int x, int y) {
    quadCurveTrailEnd.touchDown(x, y);
    quillTrailBitmapEnd.touchDown(x, y);
  }
}
