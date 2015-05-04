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

public class SlidingWindowIsSameAsLastTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldReturnFalseWhenNoPoints() throws IllegalAccessException {
    // given
    TrailPoint point = new TrailPoint();
    int addedElementsNumber = 0;
    int windowSize = 3;
    TrailPoint[] points = new TrailPoint[windowSize];
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).isSameAsLast(point);
    // do
    boolean ret = slidingWindow.isSameAsLast(point);
    // then
    Assert.assertFalse(ret);
  }

  @Test
  public void shouldReturnFalseWhenNotEqual() throws IllegalAccessException {
    // given
    TrailPoint point = new TrailPoint();
    point.set(1, 10);
    int addedElementsNumber = 1;
    int windowSize = 3;
    TrailPoint[] points = new TrailPoint[windowSize];
    TrailPoint p0 = new TrailPoint();
    p0.set(2, 3);
    points[0] = p0;
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).isSameAsLast(point);
    // do
    boolean ret = slidingWindow.isSameAsLast(point);
    // then
    Assert.assertFalse(ret);
  }

  @Test
  public void shouldReturnTrueWhenEqual() throws IllegalAccessException {
    // given
    TrailPoint point = new TrailPoint();
    point.set(1, 10);
    int addedElementsNumber = 1;
    int windowSize = 3;
    TrailPoint[] points = new TrailPoint[windowSize];
    TrailPoint p0 = new TrailPoint();
    p0.set(1, 10);
    points[0] = p0;
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).isSameAsLast(point);
    // do
    boolean ret = slidingWindow.isSameAsLast(point);
    // then
    Assert.assertTrue(ret);
  }
}
