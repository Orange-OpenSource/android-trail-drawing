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
import android.graphics.Paint;
import android.graphics.Path;

import com.orange.dgil.trail.android.drawingtool.TrailOptions;
import com.orange.dgil.trail.android.animation.AnimManager;

class Painters {

  private final Paint painter = new Paint();
  private final Path shadowPath = new Path();
  private final AnimManager animManager;

  private Paint shadowPainter;
  private final TrailOptions trailOptions;

  Painters(AnimManager fadingManager, TrailOptions trailOptions) {
    this.animManager = fadingManager;
    this.trailOptions = trailOptions;
    init();
  }

  void init() {
    initStrokeStyle();
    if (trailOptions.isShadowAvailable()) {
      initShadowPainter();
      removeShadowLayers();
    } else {
      enableDithering();
    }
  }

  private void initStrokeStyle() {
    painter.setAntiAlias(true);
    painter.setStyle(Paint.Style.STROKE);
    painter.setStrokeJoin(Paint.Join.ROUND);
    painter.setStrokeCap(Paint.Cap.ROUND);
  }

  private void initShadowPainter() {
    shadowPainter = new Paint(painter);
  }

  private void removeShadowLayers() {
    painter.setShadowLayer(0, 0, 0, 0);
    shadowPainter.setShadowLayer(0, 0, 0, 0);
  }

  private void enableDithering() {
    painter.setDither(true);
  }


  void drawPaths(Canvas c, Path path) {
    updateColors();
    updateWidth();
    if (isShadowEnabled()) {
      drawShadowPath(c, path);
    }
    c.drawPath(path, painter);
  }

  private boolean isShadowEnabled() {
    if (animManager.isRunning()) {
      return animManager.getAnimationParameters().isShadowEnabled();
    } else {
      return trailOptions.isShadowEnabled();
    }
  }

  private void drawShadowPath(Canvas c, Path path) {
    int offset = trailOptions.getShadowOffsetPixels();
    shadowPath.set(path);
    shadowPath.offset(offset, offset);
    c.drawPath(shadowPath, shadowPainter);
  }

  private void updateColors() {
    painter.setColor(getColor());
    if (trailOptions.isShadowEnabled()) {
      shadowPainter.setColor(getShadowColor());
    }
  }
  private int getColor() {
    return animManager.isRunning() ? animManager.getAnimColor() : trailOptions.getColor();
  }
  private int getShadowColor() {
    return animManager.isRunning() ? animManager.getAnimShadowColor() : trailOptions.getShadowColor();
  }

  private void updateWidth() {
    float width = trailOptions.getWidthPixels() * animManager.getWidthFactor();
    painter.setStrokeWidth(width);
    if (trailOptions.isShadowEnabled()) {
      shadowPainter.setStrokeWidth(width);
    }
  }


  int getRadius() {
    return getSinglePathRadius() + getShadowExtraRadius();
  }
  private int getSinglePathRadius() {
    return (int)(trailOptions.getWidthPixels()/2 * animManager.getWidthFactor() + 1);
  }
  private int getShadowExtraRadius() {
    return trailOptions.isShadowEnabled() ? 2 * trailOptions.getShadowOffsetPixels() + 1 : 0;
  }
}
