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

public class VectorGetHeightToTest {

  @Test
  public void shouldGetHeightToVectorWhenSameYBase() {
    // given
    int yBase = 100;
    int expectedHeight = 23;
    TrailPoint point1 = new TrailPoint(10, yBase);
    TrailPoint point2 = new TrailPoint(20, yBase);
    TrailPoint point3 = new TrailPoint(100, yBase + expectedHeight);

    Vector v1 = new Vector();
    v1.setPoint1(point1);
    v1.setPoint2(point2);
    v1.setPoint3(point3);
    // do
    int height = v1.getHeightP3ToV12();
    // then
    Assert.assertEquals(expectedHeight, height);
  }

  @Test
  public void shouldGetHeightToVectorWhenSameYBaseReverse() {
    // given
    int yBase = 100;
    int expectedHeight = 23;
    TrailPoint point1 = new TrailPoint(10, yBase);
    TrailPoint point2 = new TrailPoint(100, yBase);
    TrailPoint point3 = new TrailPoint(50, yBase + expectedHeight);

    Vector v1 = new Vector();
    v1.setPoint1(point1);
    v1.setPoint2(point2);
    v1.setPoint3(point3);
    // do
    int height = v1.getHeightP3ToV12();
    // then
    Assert.assertEquals(expectedHeight, height);
  }

  @Test
  public void shouldGetHeightToVectorWhenSameXBase() {
    // given
    int xBase = 100;
    int expectedHeight = 23;
    TrailPoint point1 = new TrailPoint(xBase, 10);
    TrailPoint point2 = new TrailPoint(xBase, 100);
    TrailPoint point3 = new TrailPoint(xBase + expectedHeight, 1000);

    Vector v1 = new Vector();
    v1.setPoint1(point1);
    v1.setPoint2(point2);
    v1.setPoint3(point3);
    // do
    int height = v1.getHeightP3ToV12();
    // then
    Assert.assertEquals(expectedHeight, height);
  }

  @Test
  public void shouldGetHeightToVectorWhenSameXBaseReverse() {
    // given
    int xBase = 100;
    int expectedHeight = 23;
    TrailPoint point1 = new TrailPoint(xBase, 10);
    TrailPoint point2 = new TrailPoint(xBase, 1000);
    TrailPoint point3 = new TrailPoint(xBase + expectedHeight, 100);

    Vector v1 = new Vector();
    v1.setPoint1(point1);
    v1.setPoint2(point2);
    v1.setPoint3(point3);
    // do
    int height = v1.getHeightP3ToV12();
    // then
    Assert.assertEquals(expectedHeight, height);
  }
}
