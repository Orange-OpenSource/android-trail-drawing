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

import com.orange.dgil.trail.core.common.TrailPoint;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SlidingWindowAddTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldAddWhenWindowFull() throws IllegalAccessException {
    // given
    boolean full = true;
    TrailPoint point = new TrailPoint(12, 34);
    Mockito.when(slidingWindow.isFull()).thenReturn(full);
    Mockito.doCallRealMethod().when(slidingWindow).add(point);
    Mockito.doCallRealMethod().when(slidingWindow).add(point.getX(), point.getY());
    // do
    slidingWindow.add(point);
    // then
    Mockito.verify(slidingWindow, Mockito.times(1)).slidePointsToTheLeft();
    Mockito.verify(slidingWindow, Mockito.times(1)).doAdd(point.getX(), point.getY());
  }

  @Test
  public void shouldAddWhenWindowNotFull() throws IllegalAccessException {
    // given
    boolean full = false;
    TrailPoint point = new TrailPoint(12, 34);
    Mockito.when(slidingWindow.isFull()).thenReturn(full);
    Mockito.doCallRealMethod().when(slidingWindow).add(point);
    Mockito.doCallRealMethod().when(slidingWindow).add(point.getX(), point.getY());
    // do
    slidingWindow.add(point);
    // then
    Mockito.verify(slidingWindow, Mockito.times(0)).slidePointsToTheLeft();
    Mockito.verify(slidingWindow, Mockito.times(1)).add(point.getX(), point.getY());
  }
}
