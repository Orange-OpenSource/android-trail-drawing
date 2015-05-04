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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SlidingWindowIsIndexValidTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldReturnTrueWhenIndexIsValid() throws IllegalAccessException {
    // given
    int index = 1;
    int maxIndex = 2;
    Mockito.when(slidingWindow.getMaxIndex()).thenReturn(maxIndex);
    Mockito.doCallRealMethod().when(slidingWindow).isIndexValid(index);
    // do
    boolean ret = slidingWindow.isIndexValid(index);
    // then
    Assert.assertTrue(ret);
  }

  @Test
  public void shouldReturnTrueWhenIndexIsValidLast() throws IllegalAccessException {
    // given
    int index = 2;
    int maxIndex = 2;
    Mockito.when(slidingWindow.getMaxIndex()).thenReturn(maxIndex);
    Mockito.doCallRealMethod().when(slidingWindow).isIndexValid(index);
    // do
    boolean ret = slidingWindow.isIndexValid(index);
    // then
    Assert.assertTrue(ret);
  }

  @Test
  public void shouldReturnFalseWhenIndexIsNotValidIndexNegative() throws IllegalAccessException {
    // given
    int index = -1;
    int maxIndex = 2;
    Mockito.when(slidingWindow.getMaxIndex()).thenReturn(maxIndex);
    Mockito.doCallRealMethod().when(slidingWindow).isIndexValid(index);
    // do
    boolean ret = slidingWindow.isIndexValid(index);
    // then
    Assert.assertFalse(ret);
  }

  @Test
  public void shouldReturnFalseWhenIndexIsNotValidIndexTooLarge() throws IllegalAccessException {
    // given
    int index = 3;
    int maxIndex = 2;
    Mockito.when(slidingWindow.getMaxIndex()).thenReturn(maxIndex);
    Mockito.doCallRealMethod().when(slidingWindow).isIndexValid(index);
    // do
    boolean ret = slidingWindow.isIndexValid(index);
    // then
    Assert.assertFalse(ret);
  }
}
