/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.quad;

import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.vecto.vecto.VectoFilter;
import com.orange.dgil.trail.core.vecto.vecto.VectoFilterListener;
import com.orange.dgil.trail.core.vecto.vecto.VectoSettings;

import lombok.Getter;

public class QuadCurve implements VectoFilterListener {

  @Getter
  private final TrailPoint lastVectoPoint = new TrailPoint();
  private final boolean vectoEnabled;

  private QuadInterpolator quadInterpolator;
  private VectoFilter vectoFilter = new VectoFilter(this);


  public QuadCurve(boolean vectoEnabled) {
    this.vectoEnabled = vectoEnabled;
    vectoFilter = vectoEnabled ? new VectoFilter(this) : null;
    quadInterpolator = new QuadInterpolator(vectoEnabled);
  }

  public void reset() {
    resetPointsContainer();
    quadInterpolator.reset();
  }

  private void resetPointsContainer() {
    if (vectoEnabled) {
      vectoFilter.reset();
    }
  }

  public void addPoint(int x, int y) {
    if (vectoEnabled) {
      addPointToVecto(x, y);
    } else {
      addRawPoint(x, y);
    }
  }

  private void addPointToVecto(int x, int y) {
      vectoFilter.addPoint(x, y);
  }

  private void addRawPoint(int x, int y) {
    quadInterpolator.newPointAdded(x, y);
  }

  public void epilogue() {
    if (vectoEnabled) {
      doVectoEpilogue();
    } else {
      quadInterpolator.lastPointAddedPreviously();
    }
  }

  private void doVectoEpilogue() {
    vectoFilter.epilogue();
  }

  @Override
  public void onNewVectoPointAvailable(int x, int y) {
    lastVectoPoint.set(x, y);
    quadInterpolator.newPointAdded(x, y);
  }

  @Override
  public void onLastVectoPointAvailable(int x, int y) {
    quadInterpolator.lastPointAdded(x, y);
  }

  public QuadCurveArray getQuadCurveArray() {
    return quadInterpolator.getQuadCurveArray();
  }

  public VectoSettings getVectoSettings() {
    if (vectoEnabled) {
      return vectoFilter.getVectoSettings();
    } else {
      throw new QuadCurveException("Vecto not enabled");
    }
  }

  public void setDensity(int density) {
    quadInterpolator.setDensity(density);
  }
}
