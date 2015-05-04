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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SlidingWindowGetLastElementTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldGetLastElement() throws IllegalAccessException {
    // given
    int lastIndex = 3;
    Mockito.when(slidingWindow.getLastElementIndex()).thenReturn(lastIndex);
    TrailPoint lastPoint = new TrailPoint();
    Mockito.when(slidingWindow.getElementAt(lastIndex)).thenReturn(lastPoint);
    Mockito.doCallRealMethod().when(slidingWindow).getLastElement();
    // do
    TrailPoint ret = slidingWindow.getLastElement();
    // then
    Assert.assertEquals(lastPoint, ret);
  }
}
