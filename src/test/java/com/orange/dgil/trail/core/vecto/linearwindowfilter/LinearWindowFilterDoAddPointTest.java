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

public class LinearWindowFilterDoAddPointTest {

  private LinearWindowFilter filter;

  @Before
  public void setUp() {
    filter = Mockito.mock(LinearWindowFilter.class);
  }

  @Test
  public void shouldDoAddPointWhenNewPointAvailable() throws IllegalAccessException {
    // given
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TrailPoint point = new TrailPoint();
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.when(filter.isNewPointAvailable()).thenReturn(true);
    Mockito.doCallRealMethod().when(filter).doAddPoint(point);
    // do
    filter.doAddPoint(point);
    // then
    Mockito.verify(slidingWindow, Mockito.times(1)).add(point);
    Mockito.verify(filter, Mockito.times(1)).isNewPointAvailable();
    Mockito.verify(filter, Mockito.times(1)).computeNewPointAndNotifyListener();
  }

  @Test
  public void shouldDoAddPointWhenNoNewPointAvailable() throws IllegalAccessException {
    // given
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TrailPoint point = new TrailPoint();
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.doCallRealMethod().when(filter).doAddPoint(point);
    // do
    filter.doAddPoint(point);
    // then
    Mockito.verify(slidingWindow, Mockito.times(1)).add(point);
    Mockito.verify(filter, Mockito.times(1)).isNewPointAvailable();
    Mockito.verify(filter, Mockito.times(0)).computeNewPointAndNotifyListener();
  }
}
