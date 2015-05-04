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

public class SlidingWindowGetElementAtTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldGetElementAt() throws IllegalAccessException {
    // given
    TrailPoint[] points = new TrailPoint[3];
    points[0] = new TrailPoint();
    TestTools.setObj("points", SlidingWindow.class, slidingWindow, points);
    int index = 0;
    Mockito.when(slidingWindow.isIndexValid(index)).thenReturn(true);
    Mockito.doCallRealMethod().when(slidingWindow).getElementAt(index);
    // do
    TrailPoint ret = slidingWindow.getElementAt(index);
    // then
    Assert.assertEquals(points[0], ret);
  }

  @Test
  public void shouldGetElementAtWithException() throws IllegalAccessException {
    // given
    int index = 0;
    Mockito.when(slidingWindow.isIndexValid(index)).thenReturn(false);
    Mockito.doCallRealMethod().when(slidingWindow).getElementAt(index);
    // do
    boolean getException = false;
    try {
      slidingWindow.getElementAt(index);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }
}
