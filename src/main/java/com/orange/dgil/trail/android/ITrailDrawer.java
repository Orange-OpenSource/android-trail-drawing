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

  void clear();
  void hide();
  void show();
  void showAndRedrawPath();

  void show(int color);
  void showAndRedrawPath(int color);

  void animate();
  void animateAlpha(int color);
  void animateToColor(int color);

  void setMultistrokeEnabled(boolean enable);

  void touchDown(int x, int y);
  void touchMove(int x, int y);
  void touchUp();
  void touchCancel();

  void draw(Canvas c);
}
