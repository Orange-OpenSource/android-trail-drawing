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
import com.orange.dgil.trail.core.vecto.SlidingWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LinearWindowFilterIsNewPointAvailableTest {

  private LinearWindowFilter filter;

  @Before
  public void setUp() {
    filter = Mockito.mock(LinearWindowFilter.class);
  }

  @Test
  public void shouldUpdateHasNewPointAvailableWhenNotFirstPointAndWindowNotFull() throws IllegalAccessException {
    // given
    int addedElementsNumber = 2;
    boolean isFull = false;
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(addedElementsNumber);
    Mockito.when(slidingWindow.isFull()).thenReturn(isFull);
    Mockito.doCallRealMethod().when(filter).isNewPointAvailable();
    // do
    boolean newPointAvailable = filter.isNewPointAvailable();
    // then
    Assert.assertFalse(newPointAvailable);
  }

  @Test
  public void shouldUpdateHasNewPointAvailableWhenFirstPointAndWindowNotFull() throws IllegalAccessException {
    // given
    int addedElementsNumber = 1;
    boolean isFull = false;
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(addedElementsNumber);
    Mockito.when(slidingWindow.isFull()).thenReturn(isFull);
    Mockito.doCallRealMethod().when(filter).isNewPointAvailable();
    // do
    boolean newPointAvailable = filter.isNewPointAvailable();
    // then
    Assert.assertTrue(newPointAvailable);
  }

  @Test
  public void shouldUpdateHasNewPointAvailableWhenFirstPointAndWindowFull() throws IllegalAccessException {
    // given
    int addedElementsNumber = 1;
    boolean isFull = true;
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(addedElementsNumber);
    Mockito.when(slidingWindow.isFull()).thenReturn(isFull);
    Mockito.doCallRealMethod().when(filter).isNewPointAvailable();
    // do
    boolean newPointAvailable = filter.isNewPointAvailable();
    // then
    Assert.assertTrue(newPointAvailable);
  }

  @Test
  public void shouldUpdateHasNewPointAvailableWhenNotFirstPointAndWindowFull() throws IllegalAccessException {
    // given
    int addedElementsNumber = 2;
    boolean isFull = true;
    SlidingWindow slidingWindow = Mockito.mock(SlidingWindow.class);
    TestTools.setObj("slidingWindow", LinearWindowFilter.class, filter, slidingWindow);
    Mockito.when(slidingWindow.getAddedElementsNumber()).thenReturn(addedElementsNumber);
    Mockito.when(slidingWindow.isFull()).thenReturn(isFull);
    Mockito.doCallRealMethod().when(filter).isNewPointAvailable();
    // do
    boolean newPointAvailable = filter.isNewPointAvailable();
    // then
    Assert.assertTrue(newPointAvailable);
  }
}
