/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto.vecto;

import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.vecto.linearwindowfilter.LinearWindowFilter;
import com.orange.dgil.trail.core.vecto.linearwindowfilter.LinearWindowFilterListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class VectoFilter implements LinearWindowFilterListener, VectoFilterListener {

  private final LinearWindowFilter linearWindowFilter = new LinearWindowFilter(this);
  private final RawVectoFilter rawVectoFilter = new RawVectoFilter(this);
  private final VectoFilterListener vectoFilterListener;
  private final TrailPoint trailPoint = new TrailPoint();

  public void reset() {
    linearWindowFilter.reset();
    rawVectoFilter.reset();
  }

  public void addPoint(int x, int y) {
    trailPoint.set(x, y);
    linearWindowFilter.addPoint(trailPoint);
  }

  public void epilogue() {
    rawVectoFilter.addPoint(linearWindowFilter.getLastPoint());
    rawVectoFilter.epilogue();
  }

  @Override
  public void onNewFilteredPointAvailable(int x, int y) {
    trailPoint.set(x, y);
    rawVectoFilter.addPoint(trailPoint);
  }

  @Override
  public void onNewVectoPointAvailable(int x, int y) {
    vectoFilterListener.onNewVectoPointAvailable(x, y);
  }

  @Override
  public void onLastVectoPointAvailable(int x, int y) {
    vectoFilterListener.onLastVectoPointAvailable(x, y);
  }

  public VectoSettings getVectoSettings() {
    return rawVectoFilter.getVectoSettings();
  }
}
