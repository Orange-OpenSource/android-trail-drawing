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

import com.orange.dgil.trail.android.AndroidMetrics;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.quad.QuadCurve;
import com.orange.dgil.trail.core.quad.QuadCurveArray;
import com.orange.dgil.trail.core.vecto.vecto.VectoSettings;


class QuadCurveTrail {
  private static final int VECTO_WINDOW_SIZE = 6;
  private static final int VECTO_HEIGHT_THRESHOLD_UM = 1000;

  private final QuadCurve quadCurve;

  QuadCurveTrail(boolean vectoEnabled, AndroidMetrics metrics) {
    quadCurve = new QuadCurve(vectoEnabled);
    init(vectoEnabled, metrics);
  }

  private void init(boolean vectoEnabled, AndroidMetrics metrics) {
    if (vectoEnabled) {
      initQuadCurveVecto(metrics);
    }
    initQuadCurveDensity(metrics);
  }

  private void initQuadCurveDensity(AndroidMetrics metrics) {
    int density = (int)(metrics.getDensity() + 0.5f);
    quadCurve.setDensity(density);
  }

  private void initQuadCurveVecto(AndroidMetrics metrics) {
    int heightPx = metrics.micrometersToPixels(VECTO_HEIGHT_THRESHOLD_UM);
    VectoSettings vectoSettings = quadCurve.getVectoSettings();
    vectoSettings.setWindowSize(VECTO_WINDOW_SIZE);
    vectoSettings.setVectorsHeightThreshold(heightPx);
  }

  void reset() {
    quadCurve.reset();
  }

  void touchDown(int x, int y) {
    quadCurve.reset();
    addPoint(x, y);
  }

  void touchMove(int x, int y) {
    addPoint(x, y);
  }

  void touchUp() {
    quadCurve.epilogue();
  }

  private void addPoint(int x, int y) {
    quadCurve.addPoint(x, y);
  }

  QuadCurveArray getQuadCurveArray() {
    return quadCurve.getQuadCurveArray();
  }

  VectoSettings getVectoSettings() {
    return quadCurve.getVectoSettings();
  }

  TrailPoint getLastVectoPoint() {
    return quadCurve.getLastVectoPoint();
  }
}
