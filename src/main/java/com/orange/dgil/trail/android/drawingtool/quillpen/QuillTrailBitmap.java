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
import android.view.View;

import com.orange.dgil.trail.android.animation.AnimManager;
import com.orange.dgil.trail.android.drawingtool.DrawingToolsContext;
import com.orange.dgil.trail.android.drawingtool.IDrawingTool;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.quad.QuadCurveArray;


class QuillTrailBitmap implements IDrawingTool {

  private final View view;
  private final AnimManager animManager;
  private final BitmapDrawer bitmapDrawer;
  private final QuillBitmap quillBitmap;
  private final TrailBounds trailBounds;
  private final QuillTrailBitmapListener quillTrailBitmapListener;
  private final QuadCurveArray quadCurveArray;

  private boolean shouldDrawTrailEnd;
  private boolean inGesture;

  private int readIndex;


  QuillTrailBitmap(DrawingToolsContext drawingToolsContext, BitmapDrawer bitmapDrawer, QuadCurveArray quadCurveArray, QuillTrailBitmapListener quillTrailBitmapListener) {
    this.view = drawingToolsContext.getView();
    this.animManager = drawingToolsContext.getAnimManager();
    this.quadCurveArray = quadCurveArray;
    this.bitmapDrawer = bitmapDrawer;
    this.quillTrailBitmapListener = quillTrailBitmapListener;

    quillBitmap = new QuillBitmap(view);
    trailBounds = new TrailBounds(drawingToolsContext.getTrailOptions().getQuillParameters(), quadCurveArray.getTrailRect());
  }

  @Override
  public void reset() {
    if (quillBitmap.isLoaded()) {
      readIndex = 0;
      quillBitmap.reset();
    }
  }

  @Override
  public void trimMemory() {
    quillBitmap.releaseBitmap();
  }

  @Override
  public void forceRedrawForAnimation(boolean eraseBitmap) {
    bitmapDrawer.forceColor(animManager.getAnimColor());
    if (eraseBitmap) {
      quillBitmap.reset();
    }
    for (int i = 0; i <= quadCurveArray.getLastPointIndex(); i++) {
      TrailPoint point = quadCurveArray.get(i);
      bitmapDrawer.drawPoint(quillBitmap.getBitmapCanvas(), point.getX(), point.getY());
    }
  }

  @Override
  public void touchDown(int x, int y) {
    inGesture = true;
    readIndex = 0;
    quillBitmap.resetAlpha();
    trailBounds.updateTrailBoundsOnTouchDown(x, y);
  }

  @Override
  public void touchMove(int x, int y) {
    updateTrailBounds();
  }

  private void updateTrailBounds() {
    if (quadCurveArray.isNotEmpty()) {
      TrailPoint lastPoint = quadCurveArray.getLastPoint();
      trailBounds.updateTrailBounds(lastPoint.getX(), lastPoint.getY());
    }
  }

  @Override
  public void touchUp() {
    updateTrailBounds();
    invalidateAreaOnMove();
    shouldDrawTrailEnd = true;
    inGesture = false;
  }

  @Override
  public void draw(Canvas canvas) {
    handleBitmapDrawing();
    if (quillBitmap.isLoaded()) {
      quillBitmap.drawBitmap(canvas);
    }
  }

  private void handleBitmapDrawing() {
    if (shouldDrawTrailInBitmap()) {
      drawTrailInBitmap();
    } else if (animManager.isAlphaAnimationRunning()) {
      animateBitmap();
    }
  }

  private boolean shouldDrawTrailInBitmap() {
    return shouldDrawTrailEnd || inGesture;
  }

  private void drawTrailInBitmap() {
    quillBitmap.lazyLoading();
    shouldDrawTrailEnd = false;
    drawTrail(quillBitmap.getBitmapCanvas());
    checkDrawChange();
    updateReadIndex();
  }

  private void drawTrail(Canvas canvas) {
    for (int i = readIndex; i <= quadCurveArray.getLastPointIndex(); i++) {
      TrailPoint point = quadCurveArray.get(i);
      bitmapDrawer.drawPoint(canvas, point.getX(), point.getY());
    }
  }

  private void checkDrawChange() {
    int lastIndex = quadCurveArray.getLastPointIndex();
    if (readIndex < lastIndex) {
      TrailPoint point = quadCurveArray.getLastPoint();
      quillTrailBitmapListener.onLastDrawnPointChange(point.getX(), point.getY());
    }
  }

  private void updateReadIndex() {
    readIndex = quadCurveArray.getLastPointIndex() + 1;
  }


  private void animateBitmap() {
    quillBitmap.setAlpha((int) (255 * animManager.getFactor()));
  }

  @Override
  public void invalidateAreaOnMove() {
    trailBounds.invalidateAreaOnMove(view);
  }

  @Override
  public void invalidatePath() {
    trailBounds.invalidatePath(view);
  }

  void drawSinglePointAt(TrailPoint point) {
    quillBitmap.lazyLoading();
    bitmapDrawer.drawCirclePoint(quillBitmap.getBitmapCanvas(), point.getX(), point.getY());
    trailBounds.updateTrailBounds(point.getX(), point.getY());
  }
}
