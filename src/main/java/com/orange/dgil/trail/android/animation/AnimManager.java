/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.animation;

import android.graphics.Color;

public class AnimManager {

  private final AnimRunnable animRunnable;
  private final AnimParameters animParameters = new AnimParameters();

  public AnimManager(IAnimDrawer drawer) {
    animRunnable = new AnimRunnable(drawer, animParameters);
  }

  public void start() {
    animRunnable.start();
  }

  public int getAnimColor() {
    int startColor = animParameters.getStartColor();
    int endColor = animParameters.getEndColor();
    return getInterpolatedColor(startColor, endColor, animRunnable.getFactor());
  }

  public int getAnimShadowColor() {
    int startColor = animParameters.getShadowStartColor();
    int endColor = animParameters.getShadowEndColor();
    return getInterpolatedColor(startColor, endColor, animRunnable.getFactor());
  }

  private int getInterpolatedColor(int startColor, int endColor, float factor) {
    return Color.argb(
        getInterp(Color.alpha(startColor), Color.alpha(endColor), factor),
        getInterp(Color.red  (startColor), Color.red  (endColor), factor),
        getInterp(Color.green(startColor), Color.green(endColor), factor),
        getInterp(Color.blue (startColor), Color.blue (endColor), factor));
  }
  private int getInterp(int start, int end, float factor) {
    return (int) (start * factor + end * (1 - factor));
  }


  public float getFactor() {
    return animRunnable.getFactor();
  }

  /**
   * The width factor is made to dilate the trail during fading
   * @return the width factor
   */
  public float getWidthFactor() {
    float factor = getFactor();
    return 1 + animParameters.getWidthDilatationFactor() * (1-factor);
  }

  public void reset() {
    animRunnable.reset();
  }

  public AnimParameters getAnimationParameters() {
    return animParameters;
  }

  public boolean isRunning() {
    return animRunnable.isRunning();
  }

  public boolean isAlphaAnimationRunning() {
    return isRunning() && animParameters.areStartEndColorsRgbEqual();
  }
}
