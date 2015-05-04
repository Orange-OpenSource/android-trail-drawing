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

public class SlidingWindowDoAddTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldDoAdd() throws IllegalAccessException {
    // given
    TrailPoint point = new TrailPoint();
    point.set(1, 10);
    TrailPoint[] points = new TrailPoint[3];
    points[0] = new TrailPoint();
    points[1] = new TrailPoint();
    points[2] = new TrailPoint();
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    int insertedIndex = 2;
    Mockito.when(slidingWindow.getInsertIndex()).thenReturn(insertedIndex);
    int addedElementsNumber = 100;
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    Mockito.doCallRealMethod().when(slidingWindow).doAdd(point.getX(), point.getY());
    // do
    slidingWindow.doAdd(point.getX(), point.getY());
    // then
    Assert.assertEquals(1, points[2].getX());
    Assert.assertEquals(10, points[2].getY());
    Assert.assertEquals(addedElementsNumber+1, TestTools.getObj("addedElementsNumber", SlidingWindow.class, slidingWindow));
  }
}
