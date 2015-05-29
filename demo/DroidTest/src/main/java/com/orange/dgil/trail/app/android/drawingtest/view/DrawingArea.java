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
import com.orange.dgil.trail.android.animation.IAnimListener;
import com.orange.dgil.trail.android.impl.TrailDrawer;

public class DrawingArea extends RelativeLayout implements IAnimListener {

  private static final int TRAIL_COLOR = Color.BLACK;

  private ITrailDrawer trailDrawer;

  public DrawingArea(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void initTrailDrawer() {
    setWillNotDraw(false);
    trailDrawer = new TrailDrawer(this);
    trailDrawer.getTrailOptions().setColor(TRAIL_COLOR);
    trailDrawer.setAnimationListener(this);
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
      trailDrawer.touchDown((int)event.getX(), (int)event.getY());
      break;
    case MotionEvent.ACTION_MOVE:
      dispatchMove(event);
      break;
    case MotionEvent.ACTION_UP:
      trailDrawer.touchUp();
      break;
    default:
      trailDrawer.touchCancel();
    }
  }

  private void dispatchMove(MotionEvent event) {
    for (int i = 0; i < event.getHistorySize(); i++) {
      onMove(event.getHistoricalX(i), event.getHistoricalY(i));
    }
    onMove(event.getX(), event.getY());
  }

  private void onMove(float x, float y) {
    trailDrawer.touchMove((int) x, (int) y);
  }

  @Override
  public void animationFinished() {
    trailDrawer.clear();
  }

  public void onQuillSelected() {
    trailDrawer.clear();
    trailDrawer.getTrailOptions().selectQuillPen();
  }

  public void onMarkerSelected() {
    trailDrawer.clear();
    trailDrawer.getTrailOptions().selectMarkerPen();
  }

  public void onClearSelected() {
    trailDrawer.getAnimationParameters().setTimeProperties(0, 500);
    trailDrawer.animateAlpha(TRAIL_COLOR);
  }
}
