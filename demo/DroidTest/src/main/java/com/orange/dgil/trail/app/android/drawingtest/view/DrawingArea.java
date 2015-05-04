/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.android.drawingtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.orange.dgil.trail.android.ITrailDrawer;
import com.orange.dgil.trail.android.impl.TrailDrawer;

public class DrawingArea extends RelativeLayout {

  private static final int TRAIL_COLOR = Color.BLACK;

  private ITrailDrawer trailDrawer;

  public DrawingArea(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void initTrailDrawer() {
    setWillNotDraw(false);
    trailDrawer = new TrailDrawer(this);
    trailDrawer.getTrailOptions().setColor(TRAIL_COLOR);
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    if (trailDrawer != null) {
      trailDrawer.draw(canvas);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    dispatchEvent(event);
    return true;
  }

  private void dispatchEvent(MotionEvent event) {
    switch (event.getActionMasked()) {
    case MotionEvent.ACTION_DOWN:
      onDown(event.getX(), event.getY());
      break;
    case MotionEvent.ACTION_MOVE:
        dispatchMove(event);
      break;
    case MotionEvent.ACTION_UP:
        onUp();
      break;
    case MotionEvent.ACTION_CANCEL:
      onCancel();
    default:
      onCancel();
    }
  }

  private void dispatchMove(MotionEvent event) {
    for (int i = 0; i < event.getHistorySize(); i++) {
      onMove(event.getHistoricalX(i), event.getHistoricalY(i));
    }
    onMove(event.getX(), event.getY());
  }

  private void onDown(float x, float y) {
    onClear();
    trailDrawer.touchDown((int)x, (int)y);
    trailDrawer.show();
  }

  private void onMove(float x, float y) {
    trailDrawer.touchMove((int) x, (int) y);
  }

  private void onUp() {
    trailDrawer.touchUp();
    trailDrawer.getAnimationParameters().setTimeProperties(2000, 500);
    trailDrawer.animateAlpha(TRAIL_COLOR);
  }

  private void onCancel() {
    trailDrawer.touchCancel();
  }

  private void onClear() {
    trailDrawer.clear();
  }

  public void onQuillSelected() {
    onClear();
    trailDrawer.getTrailOptions().selectQuillPen();
  }

  public void onMarkerSelected() {
    onClear();
    trailDrawer.getTrailOptions().selectMarkerPen();
  }
}
