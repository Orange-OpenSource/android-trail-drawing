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

public class SlidingWindowGetXYIsIndexValidGetMaxIndexTest {

  private SlidingWindow slidingWindow;

  @Before
  public void setUp() {
    slidingWindow = Mockito.mock(SlidingWindow.class);
  }

  @Test
  public void shouldGetXWhenNoPoints() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    // do
    boolean getException = false;
    try {
      slidingWindow.getX(0);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }

  @Test
  public void shouldGetXWhenNegativeIndex() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    // do
    boolean getException = false;
    try {
      slidingWindow.getX(-1);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }

  @Test
  public void shouldGetXWhenPointsAndIndexOk() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 2);
    slidingWindow.add(point);
    point.set(10, 20);
    slidingWindow.add(point);
    // do
    int x0 = slidingWindow.getX(0);
    int x1 = slidingWindow.getX(1);
    // then
    Assert.assertEquals(1, x0);
    Assert.assertEquals(10, x1);
  }

  @Test
  public void shouldGetXWhenPointsAndIndexOkAndLotsOfAddedPoints() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 10);
    slidingWindow.add(point);
    point.set(2, 20);
    slidingWindow.add(point);
    point.set(3, 30);
    slidingWindow.add(point);
    point.set(4, 40);
    slidingWindow.add(point);
    point.set(5, 50);
    slidingWindow.add(point);
    // do
    int x0 = slidingWindow.getX(0);
    int x1 = slidingWindow.getX(1);
    int x2 = slidingWindow.getX(2);
    // then
    Assert.assertEquals(3, x0);
    Assert.assertEquals(4, x1);
    Assert.assertEquals(5, x2);
  }

  @Test
  public void shouldGetXWhenPointsButIndexTooLarge() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 2);
    slidingWindow.add(point);
    point.set(10, 20);
    slidingWindow.add(point);
    // do
    boolean getException = false;
    try {
      slidingWindow.getX(2);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }



  @Test
  public void shouldGetYWhenNoPoints() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    // do
    boolean getException = false;
    try {
      slidingWindow.getY(0);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }

  @Test
  public void shouldGetYWhenNegativeIndex() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    // do
    boolean getException = false;
    try {
      slidingWindow.getY(-1);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }

  @Test
  public void shouldGetYWhenPointsAndIndexOk() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 2);
    slidingWindow.add(point);
    point.set(10, 20);
    slidingWindow.add(point);
    // do
    int y0 = slidingWindow.getY(0);
    int y1 = slidingWindow.getY(1);
    // then
    Assert.assertEquals(2, y0);
    Assert.assertEquals(20, y1);
  }

  @Test
  public void shouldGetYWhenPointsAndIndexOkAndLotsOfAddedPoints() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 10);
    slidingWindow.add(point);
    point.set(2, 20);
    slidingWindow.add(point);
    point.set(3, 30);
    slidingWindow.add(point);
    point.set(4, 40);
    slidingWindow.add(point);
    point.set(5, 50);
    slidingWindow.add(point);
    // do
    int y0 = slidingWindow.getY(0);
    int y1 = slidingWindow.getY(1);
    int y2 = slidingWindow.getY(2);
    // then
    Assert.assertEquals(30, y0);
    Assert.assertEquals(40, y1);
    Assert.assertEquals(50, y2);
  }

  @Test
  public void shouldGetYWhenPointsButIndexTooLarge() throws IllegalAccessException {
    // given
    int windowSize = 3;
    slidingWindow = new SlidingWindow(windowSize);
    TrailPoint point = new TrailPoint();
    point.set(1, 2);
    slidingWindow.add(point);
    point.set(10, 20);
    slidingWindow.add(point);
    // do
    boolean getException = false;
    try {
      slidingWindow.getY(2);
    } catch (SlidingWindowIndexException e) {
      getException = true;
    }
    // then
    Assert.assertTrue(getException);
  }
}
