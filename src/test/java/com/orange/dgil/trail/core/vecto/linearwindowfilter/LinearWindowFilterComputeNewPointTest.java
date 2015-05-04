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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LinearWindowFilterComputeNewPointTest {

  private LinearWindowFilter filter;

  @Before
  public void setUp() {
    filter = Mockito.mock(LinearWindowFilter.class);
  }

  @Test
  public void shouldComputeNewPointWithOnePoint() throws IllegalAccessException {
    // given
    LinearWindowFilterListener linearWindowFilterListener = Mockito.mock(LinearWindowFilterListener.class);
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("linearWindowFilterListener", LinearWindowFilter.class, filter, linearWindowFilterListener);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    int x = 12;
    int y = 34;
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(1);
    Mockito.when(slidingWindow.getX(0)).thenReturn(x);
    Mockito.when(slidingWindow.getY(0)).thenReturn(y);
    Mockito.when(filter.getMeanX()).thenReturn(0);
    Mockito.when(filter.getMeanY()).thenReturn(0);
    Mockito.doCallRealMethod().when(filter).computeNewPointAndNotifyListener();
    // do
    filter.computeNewPointAndNotifyListener();
    // then
    Mockito.verify(linearWindowFilterListener, Mockito.times(1)).onNewFilteredPointAvailable(x, y);
  }

  @Test
  public void shouldComputeNewPointWithAllPoints() throws IllegalAccessException {
    // given
    LinearWindowFilterListener linearWindowFilterListener = Mockito.mock(LinearWindowFilterListener.class);
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("linearWindowFilterListener", LinearWindowFilter.class, filter, linearWindowFilterListener);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    int x = 12;
    int y = 34;
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(3);
    Mockito.when(slidingWindow.getX(0)).thenReturn(0);
    Mockito.when(slidingWindow.getY(0)).thenReturn(0);
    Mockito.when(filter.getMeanX()).thenReturn(x);
    Mockito.when(filter.getMeanY()).thenReturn(y);
    Mockito.doCallRealMethod().when(filter).computeNewPointAndNotifyListener();
    // do
    filter.computeNewPointAndNotifyListener();
    // then
    Mockito.verify(linearWindowFilterListener, Mockito.times(1)).onNewFilteredPointAvailable(x, y);
  }
}
