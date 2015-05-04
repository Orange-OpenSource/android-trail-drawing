/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto;

import com.orange.dgil.trail.TestTools;
import com.orange.dgil.trail.core.common.TrailPoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SlidingWindowSlidePointsToTheLeftTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldSlidePointsToTheLeft() throws IllegalAccessException {
    // given
    TrailPoint[] points = new TrailPoint[3];
    TrailPoint p0 = new TrailPoint();
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    p0.set(1, 10);
    p1.set(2, 20);
    p2.set(3, 30);
    points[0] = p0;
    points[1] = p1;
    points[2] = p2;
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).slidePointsToTheLeft();
    // do
    slidingWindow.slidePointsToTheLeft();
    // then
    Assert.assertEquals(2, points[0].getX());
    Assert.assertEquals(3, points[1].getX());
    Assert.assertEquals(20, points[0].getY());
    Assert.assertEquals(30, points[1].getY());
    Assert.assertEquals(p0, points[2]);
  }
}
