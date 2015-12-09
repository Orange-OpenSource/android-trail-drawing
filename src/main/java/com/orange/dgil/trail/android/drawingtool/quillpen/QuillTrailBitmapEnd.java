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
import android.graphics.Color;
import android.view.View;

import com.orange.dgil.trail.android.drawingtool.DrawingToolsContext;
import com.orange.dgil.trail.android.drawingtool.IDrawingTool;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.quad.QuadCurveArray;


class QuillTrailBitmapEnd implements IDrawingTool {

  private final View view;
  private final BitmapDrawer bitmapDrawer;
  private final QuillBitmap quillBitmap;
  private final TrailBounds trailBounds;
  private final QuadCurveArray quadCurveArray;

  private boolean inGesture;
  private int readIndex;


  QuillTrailBitmapEnd(DrawingToolsContext drawingToolsContext, BitmapDrawer bitmapDrawer, QuadCurveArray quadCurveArray) {
    this.view = drawingToolsContext.getView();
    this.quadCurveArray = quadCurveArray;
    this.bitmapDrawer = bitmapDrawer;

    quillBitmap = new QuillBitmap(view);
    trailBounds = new TrailBounds(drawingToolsContext.getTrailOptions().getQuillParameters(), quadCurveArray.getTrailRect());
  }

  @Override
  public void reset() {
  }

  @Override
  public void trimMemory() {
    quillBitmap.releaseBitmap();
  }

  @Override
  public void touchDown(int x, int y) {
    inGesture = true;
    readIndex = 0;
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
    inGesture = false;
    quillBitmap.reset();
    invalidatePath();
  }

  @Override
  public void draw(Canvas canvas) {
    if (inGesture) {
      handleBitmapDrawing(canvas);
    }
  }

  private void handleBitmapDrawing(Canvas canvas) {
      quillBitmap.lazyLoading();
      drawTrail(quillBitmap.getBitmapCanvas());
      quillBitmap.drawBitmap(canvas);
  }

  private void drawTrail(Canvas canvas) {
    for (int i = readIndex; i <= quadCurveArray.getLastPointIndex(); i++) {
      TrailPoint point = quadCurveArray.get(i);
      bitmapDrawer.drawPoint(canvas, point.getX(), point.getY());
    }
    readIndex = quadCurveArray.getLastPointIndex() + 1;
  }

  @Override
  public void invalidateAreaOnMove() {
    trailBounds.invalidateAreaOnMove(view);
  }

  @Override
  public void invalidatePath() {
    trailBounds.invalidatePath(view);
  }

  @Override
  public void forceRedrawForAnimation(boolean eraseBitmap) {
    // not used
  }
}
