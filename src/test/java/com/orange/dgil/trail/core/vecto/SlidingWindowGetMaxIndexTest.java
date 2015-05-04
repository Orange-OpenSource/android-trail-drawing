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

public class SlidingWindowGetMaxIndexTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldDoGetMaxIndexWithAddedElementsNumber() throws IllegalAccessException {
    // given
    int addedElementsNumber = 1;
    int windowSize = 3;
    TrailPoint[] points = new TrailPoint[windowSize];
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).getMaxIndex();
    // do
    int ret = slidingWindow.getMaxIndex();
    // then
    Assert.assertEquals(0, ret);
  }

  @Test
  public void shouldDoGetMaxIndexWithWindowSize() throws IllegalAccessException {
    // given
    int addedElementsNumber = 10;
    int windowSize = 3;
    TrailPoint[] points = new TrailPoint[windowSize];
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    Mockito.doCallRealMethod().when(slidingWindow).getMaxIndex();
    // do
    int ret = slidingWindow.getMaxIndex();
    // then
    Assert.assertEquals(2, ret);
  }
}
