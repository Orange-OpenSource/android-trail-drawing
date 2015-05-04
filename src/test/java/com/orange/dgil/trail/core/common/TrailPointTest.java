/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.common;

import org.junit.Assert;
import org.junit.Test;

public class TrailPointTest {

  @Test
  public void shouldInstantiateWithDefaultValues() {
    // given
    // do
    TrailPoint p = new TrailPoint();
    Assert.assertEquals(-1, p.getX());
    Assert.assertEquals(-1, p.getY());
  }

  @Test
  public void shouldInstantiateWithGivenValues() {
    // given
    int x = 12;
    int y = 34;
    // do
    TrailPoint p = new TrailPoint(x, y);
    Assert.assertEquals(x, p.getX());
    Assert.assertEquals(y, p.getY());
  }

  @Test
  public void shouldSetAndGet() {
    // given
    int x = 12;
    int y = 34;
    TrailPoint p = new TrailPoint();
    // do
    p.set(x, y);
    // then
    Assert.assertEquals(x, p.getX());
    Assert.assertEquals(y, p.getY());
  }

  @Test
  public void shouldHandleEqualPoints() {
    // given
    int x = 12;
    int y = 34;
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    // do
    p1.set(x, y);
    p2.set(x, y);
    // then
    Assert.assertTrue(p1.isSameAs(p2));
  }

  @Test
  public void shouldHandleNotEqualPointsX() {
    // given
    int x = 12;
    int y = 34;
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    // do
    p1.set(x, y);
    p2.set(x+1, y);
    // then
    Assert.assertFalse(p1.isSameAs(p2));
  }

  @Test
  public void shouldHandleNotEqualPointsY() {
    // given
    int x = 12;
    int y = 34;
    TrailPoint p1 = new TrailPoint();
    TrailPoint p2 = new TrailPoint();
    // do
    p1.set(x, y);
    p2.set(x, y+1);
    // then
    Assert.assertFalse(p1.isSameAs(p2));
  }

  @Test
  public void shouldDeepCopy() {
    // given
    int x = 12;
    int y = 34;
    TrailPoint p1 = new TrailPoint();
    p1.set(x, y);
    TrailPoint p2 = new TrailPoint();
    // do
    p2.deepCopy(p1);
    // then
    Assert.assertEquals(x, p2.getX());
    Assert.assertEquals(y, p2.getY());
  }
}
