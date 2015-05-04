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
import com.orange.dgil.trail.core.vecto.testvectors.VectoTestVectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RawVectoFilterFunctionalTest implements VectoFilterListener {

  private static final int VECTO_HEIGHT_THRESHOLD = 6;

  private final List<TrailPoint> output = new ArrayList<TrailPoint>();

  private final VectoFilter filter = new VectoFilter(this);

  @Before
  public void setUp() {
    filter.getVectoSettings().setVectorsHeightThreshold(VECTO_HEIGHT_THRESHOLD);
  }

  @Test
  public void shouldGenerateCorrectFilteredOutput() throws IllegalAccessException {
    for (int testIndex = 1; testIndex < VectoTestVectors.inX.length; testIndex++) {
      doShouldGenerateCorrectFilteredOutput(testIndex);
    }
  }

  private void doShouldGenerateCorrectFilteredOutput(int testIndex) {
    // given
    TrailPoint[] input = getTrailPoints(testIndex);
    output.clear();
    filter.reset();

    // do
    for (TrailPoint point : input) {
      filter.addPoint(point.getX(), point.getY());
    }
    filter.epilogue();

    // then
    TrailPoint[] expected = getFilteredTrailPoints(testIndex);
    for (int i = 0; i < output.size(); i++) {
      if (!expected[i].isSameAs(output.get(i))) {
        Assert.fail();
      }
    }
  }

  @Override
  public void onNewVectoPointAvailable(int x, int y) {
    output.add(new TrailPoint(x, y));
  }

  @Override
  public void onLastVectoPointAvailable(int x, int y) {
    onNewVectoPointAvailable(x, y);
  }

  private TrailPoint[] getTrailPoints(int testIndex) {

    int[] inX = VectoTestVectors.inX[testIndex];
    int[] inY = VectoTestVectors.inY[testIndex];

    TrailPoint[] points = new TrailPoint[inX.length];
    for (int i = 0; i < points.length; i++) {
      points[i] = new TrailPoint();
      points[i].set(inX[i], inY[i]);
    }

    return points;
  }

  private TrailPoint[] getFilteredTrailPoints(int testIndex) {
    int[] outX = VectoTestVectors.outX[testIndex];
    int[] outY = VectoTestVectors.outY[testIndex];

    TrailPoint[] points = new TrailPoint[outX.length];
    for (int i = 0; i < points.length; i++) {
      points[i] = new TrailPoint();
      points[i].set(outX[i], outY[i]);
    }

    return points;
  }
}
