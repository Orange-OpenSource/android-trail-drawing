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

import com.orange.dgil.trail.android.DefaultDrawerConf;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AnimParameters {

  private int preAnimDelay = DefaultDrawerConf.ANIM_PRE_ANIM_DELAY_MS;
  private int animDuration = DefaultDrawerConf.ANIM_DURATION_MS;
  private int startColor = DefaultDrawerConf.TRAIL_COLOR;
  private int endColor = getColorWithNullAlpha(startColor);

  @Setter
  private boolean shadowEnabled;
  private int shadowStartColor = DefaultDrawerConf.SHADOW_COLOR;
  private int shadowEndColor = shadowStartColor;

  @Setter
  private float widthDilatationFactor;

  private static int getColorWithNullAlpha(int col) {
    return Color.argb(0, Color.red(col), Color.green(col), Color.blue(col));
  }

  public void setTimeProperties(int preAnimDelay, int animDuration) {
    this.preAnimDelay = preAnimDelay;
    this.animDuration = animDuration;
  }


  /**
   * Will start the animation with provided color, and decrease the alpha channel.
   * @param color animation start color
   */
  public void setColorForAlphaAnimation(int color) {
    setColorProperties(color, getColorWithNullAlpha(color));
  }

  public void setColorProperties(int startColor, int endColor) {
    this.startColor = startColor;
    this.endColor = endColor;
  }

  /**
   * Will start the animation with provided color, and decrease the alpha channel.
   * @param color animation start color
   */
  public void setShadowColorForAlphaAnimation(int color) {
    setShadowColorProperties(color, getColorWithNullAlpha(color));
  }

  public void setShadowColorProperties(int startColor, int endColor) {
    this.shadowStartColor = startColor;
    this.shadowEndColor = endColor;
  }

  public boolean areStartEndColorsRgbEqual() {
    return (Color.red(startColor) == Color.red(endColor)) &&
           (Color.green(startColor) == Color.green(endColor)) &&
           (Color.blue(startColor) == Color.blue(endColor));
  }
}
