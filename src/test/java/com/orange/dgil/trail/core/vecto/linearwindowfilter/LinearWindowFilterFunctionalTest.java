/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto.linearwindowfilter;

import com.orange.dgil.trail.core.common.TrailPoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LinearWindowFilterFunctionalTest implements LinearWindowFilterListener {

  private final LinearWindowFilter filter = new LinearWindowFilter(this);
  private final List<TrailPoint> output = new ArrayList<TrailPoint>();


  @Test
  public void shouldGenerateCorrectFilteredOutput() throws IllegalAccessException {
    // given
    TrailPoint[] input = getTrailPoints();

    // do
    for (TrailPoint point : input) {
      filter.addPoint(point);
    }
    // then
    TrailPoint[] expected = getFilteredTrailPoints();
    for (int i = 0; i < output.size(); i++) {
      if (!expected[i].isSameAs(output.get(i))) {
        Assert.fail();
      }
    }
  }


  @Override
  public void onNewFilteredPointAvailable(int x, int y) {
    output.add(new TrailPoint(x, y));
  }

  private TrailPoint[] getTrailPoints() {
    TrailPoint[] points = new TrailPoint[10];
    for (int i = 0; i < points.length; i++) {
      points[i] = new TrailPoint();
    }
    points[0].set(100, 1000);
    points[1].set(110, 1110);
    points[2].set(120, 1200);
    points[3].set(130, 1100);
    points[4].set(140, 1000);
    points[5].set(140, 1000);
    points[6].set(140, 1000);
    points[7].set(130, 3100);
    points[8].set(120, 3200);
    points[9].set(110, 3300);

    return points;
  }

  private TrailPoint[] getFilteredTrailPoints() {
    TrailPoint[] points = new TrailPoint[7];
    for (int i = 0; i < points.length; i++) {
      points[i] = new TrailPoint();
    }
    points[0].set(100, 1000);
    points[1].set(110, 1105);
    points[2].set(120, 1153);
    points[3].set(130, 1100);
    points[4].set(135, 1550);
    points[5].set(130, 2600);
    points[6].set(120, 3200);

    return points;
  }
}
