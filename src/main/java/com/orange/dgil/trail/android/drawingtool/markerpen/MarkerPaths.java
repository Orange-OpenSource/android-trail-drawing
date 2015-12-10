/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.drawingtool.markerpen;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import com.orange.dgil.trail.android.drawingtool.DrawingToolsContext;
import com.orange.dgil.trail.android.drawingtool.IDrawingTool;
import com.orange.dgil.trail.android.drawingtool.InvalidateArea;

public class MarkerPaths implements IDrawingTool {

  private final Path path = new Path();
  private final RectF rectF = new RectF();
  private final InvalidateArea invalidateArea = new InvalidateArea();

  private final View view;
  private final Painters painters;

  private final Point position = new Point();
  private final Point middlePos = new Point();
  private final Point prevPos = new Point();
  private final Point prevMiddlePos = new Point();


  public MarkerPaths(DrawingToolsContext drawingToolsContext) {
    this.view = drawingToolsContext.getView();
    painters = new Painters(drawingToolsContext.getAnimManager(), drawingToolsContext.getTrailOptions());
  }

  @Override
  public void reset() {
    path.rewind();
  }

  @Override
  public void draw(Canvas canvas) {
    painters.drawPaths(canvas, path);
  }

  @Override
  public void touchDown(int x, int y) {
    position.set(x, y);
    middlePos.set(x, y);
    path.moveTo(x, y);
    invalidateArea.setRadius(painters.getRadius());
  }

  @Override
  public void touchMove(int x, int y) {
    updatePositions(x, y);
    path.quadTo(prevPos.x, prevPos.y, middlePos.x, middlePos.y);
  }

  @Override
  public void touchUp() {
  }

  private void updatePositions(int x, int y) {
    prevPos.set(position.x, position.y);
    prevMiddlePos.set(middlePos.x, middlePos.y);
    position.set(x, y);
    middlePos.set((x + prevPos.x) / 2, (y + prevPos.y) / 2);
  }

  @Override
  public void invalidateAreaOnMove() {
    invalidateArea.setOrigin(prevMiddlePos);
    invalidateArea.add(prevPos);
    invalidateArea.add(middlePos);
    view.invalidate(invalidateArea.getRect());
  }

  @Override
  public void invalidatePath() {
    if (!path.isEmpty()) {
      doInvalidatePath();
    }
  }

  private void doInvalidatePath() {
    path.computeBounds(rectF, false);
    addRadiusToRect(rectF);
    rectF.roundOut(invalidateArea.getRect());
    view.invalidate(invalidateArea.getRect());
  }

  private void addRadiusToRect(RectF rF) {
    int radius = painters.getRadius();
    rF.left   = rF.left   - radius;
    rF.top    = rF.top    - radius;
    rF.right  = rF.right  + radius;
    rF.bottom = rF.bottom + radius;
  }

  @Override
  public void forceRedrawForAnimation(boolean eraseBitmap) {
    // not useful
  }

  @Override
  public void trimMemory() {
    // nothing to release :)
  }
}
