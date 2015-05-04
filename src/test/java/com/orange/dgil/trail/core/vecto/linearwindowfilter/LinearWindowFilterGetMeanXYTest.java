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

import com.orange.dgil.trail.TestTools;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.vecto.SlidingWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LinearWindowFilterGetMeanXYTest {

  private LinearWindowFilter filter;

  @Before
  public void setUp() {
    filter = Mockito.mock(LinearWindowFilter.class);
  }

  @Test
  public void shouldGetMeanX() throws IllegalAccessException {
    // given
    int[] windowWeights = {1, 2, 1};
    TestTools.setObj("windowWeights", LinearWindowFilter.class, filter, windowWeights);
    TestTools.setObj("weightsSum", LinearWindowFilter.class, filter, 4);

    SlidingWindow slidingWindow = new SlidingWindow(windowWeights.length);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);

    TrailPoint p0 = new TrailPoint();
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    p0.set(2, 10);
    p1.set(5, 20);
    p2.set(4, 18);
    slidingWindow.add(p0);
    slidingWindow.add(p1);
    slidingWindow.add(p2);

    Mockito.doCallRealMethod().when(filter).getMeanX();
    // do
    int res = filter.getMeanX();
    // then
    Assert.assertEquals(4, res);
  }

  @Test
  public void shouldGetMeanY() throws IllegalAccessException {
    // given
    int[] windowWeights = {1, 2, 1};
    TestTools.setObj("windowWeights", LinearWindowFilter.class, filter, windowWeights);
    TestTools.setObj("weightsSum", LinearWindowFilter.class, filter, 4);

    SlidingWindow slidingWindow = new SlidingWindow(windowWeights.length);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);

    TrailPoint p0 = new TrailPoint();
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    p0.set(2, 10);
    p1.set(5, 20);
    p2.set(4, 18);
    slidingWindow.add(p0);
    slidingWindow.add(p1);
    slidingWindow.add(p2);

    Mockito.doCallRealMethod().when(filter).getMeanY();
    // do
    int res = filter.getMeanY();
    // then
    Assert.assertEquals(17, res);
  }
}
