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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SlidingWindowResetTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldReset() throws IllegalAccessException {
    // given
    int addedElementsNumber = 10;
    TestTools.setObj("addedElementsNumber", SlidingWindow.class, slidingWindow, addedElementsNumber);
    Mockito.doCallRealMethod().when(slidingWindow).reset();
    // do
    slidingWindow.reset();
    // then
    Assert.assertEquals(0, TestTools.getObj("addedElementsNumber", SlidingWindow.class, slidingWindow));
  }
}
