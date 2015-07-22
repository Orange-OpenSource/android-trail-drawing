/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android;

import android.graphics.Canvas;

import com.orange.dgil.trail.android.animation.AnimParameters;
import com.orange.dgil.trail.android.animation.IAnimListener;
import com.orange.dgil.trail.android.drawingtool.TrailOptions;

public interface ITrailDrawer {
  void setAnimationListener(IAnimListener i);

  TrailOptions getTrailOptions();
  AnimParameters getAnimationParameters();

  /** hide the trail AND clear all points from the underlying buffer */
  void clear();

  /** hide the trail BUT keep all points in the underlying buffer for later use */
  void hide();

  /** show the trail with all points contained in the underlying buffer */
  void show();
  /** same as {@link com.orange.dgil.trail.android.ITrailDrawer#show show} , BUT force path redraw */
  void showAndRedrawPath();

  /** same as {@link com.orange.dgil.trail.android.ITrailDrawer#show show} , BUT set color as well */
  void show(int color);
  /** same as {@link com.orange.dgil.trail.android.ITrailDrawer#show show} , BUT set color as well AND force path redraw */
  void showAndRedrawPath(int color);

  /** vanish alpha animation */
  void animate();
  /** vanish alpha animation BUT set the trail color before starting the animation */
  void animateAlpha(int color);
  /** current trail color to provided color transformation animation */
  void animateToColor(int color);

  /** whether we allow to save several strokes or not. If not the trail is cleared at the {@link com.orange.dgil.trail.android.ITrailDrawer#touchDown touchDown} event */
  void setMultistrokeEnabled(boolean enable);

  void touchDown(int x, int y);
  void touchMove(int x, int y);
  void touchUp();
  void touchCancel();

  void draw(Canvas c);
}
