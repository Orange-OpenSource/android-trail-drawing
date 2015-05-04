/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.impl;

import android.graphics.Canvas;
import android.view.View;

import com.orange.dgil.trail.android.ITrailDrawer;
import com.orange.dgil.trail.android.animation.AnimManager;
import com.orange.dgil.trail.android.animation.AnimParameters;
import com.orange.dgil.trail.android.animation.IAnimDrawer;
import com.orange.dgil.trail.android.animation.IAnimListener;
import com.orange.dgil.trail.android.drawingtool.DrawingTools;
import com.orange.dgil.trail.android.drawingtool.IDrawingTool;
import com.orange.dgil.trail.android.drawingtool.TrailOptions;

public class TrailDrawer implements ITrailDrawer, IAnimDrawer {

  private final AnimManager animManager = new AnimManager(this);

  private DrawingTools drawingTools;
  private IDrawingTool drawingTool;
  private View view;
  private IAnimListener animationListener;

  private boolean visible;
  private boolean inGesture;
  private boolean multistrokeEnabled;


  public TrailDrawer(View view) {
    init(view);
  }

  private void init(View view) {
    setView(view);
    drawingTools = new DrawingTools(view, animManager);
    updateDrawingTool();
  }

  private void updateDrawingTool() {
    drawingTool = drawingTools.getDrawingTool();
  }

  void setView(View view) {
    this.view = view;
    view.setWillNotDraw(false);
  }

  @Override
  public void setMultistrokeEnabled(boolean enable) {
    multistrokeEnabled = enable;
  }

  @Override
  public void setAnimationListener(IAnimListener animationListener) {
    this.animationListener = animationListener;
  }


  @Override
  public void touchDown(int x, int y) {
    inGesture = true;
    if (!multistrokeEnabled) {
      clear();
    }
    updateDrawingTool();
    drawingTool.touchDown(x, y);
    animManager.reset();
  }

  @Override
  public void touchMove(int x, int y) {
    if (inGesture) {
      drawingTool.touchMove(x, y);
      if (visible) {
        drawingTool.invalidateAreaOnMove();
      }
    }
  }

  @Override
  public void touchUp() {
    inGesture = false;
    drawingTool.touchUp();
  }

  @Override
  public void touchCancel() {
    inGesture = false;
    hide();
    animManager.reset();
    notifyAnimationFinished();
  }

  @Override
  public void draw(Canvas canvas) {
    if (visible) {
      drawingTool.draw(canvas);
    }
  }

  @Override
  public void invalidatePath() {
    drawingTool.invalidatePath();
  }

  @Override
  public void clear() {
    if (visible) {
      hide();
      drawingTool.reset();
    } else {
      drawingTool.reset();
      hide();
    }
  }

  @Override
  public void hide() {
    visible = false;
    invalidatePath();
  }

  @Override
  public void show() {
    animManager.reset();
    visible = true;
  }

  @Override
  public void showAndRedrawPath() {
    animManager.reset();
    invalidatePath();
    visible = true;
  }

  @Override
  public void animate() {
    visible = true;
    animManager.start();
  }

  @Override
  public void animateAlpha(int color) {
    getAnimationParameters().setColorForAlphaAnimation(color);
    drawingTool.forceRedrawForAnimation();
    animate();
  }

  @Override
  public void animateToColor(int color) {
    getAnimationParameters().setColorProperties(getTrailOptions().getColor(), color);
    animate();
  }

  @Override
  public void show(int color) {
    getTrailOptions().setColor(color);
    show();
  }

  @Override
  public void showAndRedrawPath(int color) {
    getTrailOptions().setColor(color);
    showAndRedrawPath();
  }


  @Override
  public AnimParameters getAnimationParameters() {
    return animManager.getAnimationParameters();
  }


  @Override
  public void animationFinished() {
    if (!multistrokeEnabled) {
      hide();
    }
    animManager.reset();
    notifyAnimationFinished();
  }

  private void notifyAnimationFinished() {
    if (animationListener != null) {
      animationListener.animationFinished();
    }
  }

  @Override
  public TrailOptions getTrailOptions() {
    return drawingTools.getTrailOptions();
  }
  @Override
  public View getView() {
    return view;
  }
}