/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.quad;

import com.orange.dgil.trail.core.common.TrailPoint;

import org.junit.Assert;
import org.junit.Test;

public class QuadInterpolatorRawInterpolateFunctionalTest {

  private final QuadCurveArray quadCurveArray = new QuadCurveArray(false);


  private final TrailPoint p0 = new TrailPoint(10, 100);
  private final TrailPoint p1 = new TrailPoint(110, 10);
  private final TrailPoint p2 = new TrailPoint(210, 200);

  private final int [] expected = { 10, 100, 10, 99, 11, 98, 12, 97, 13, 97, 13, 96, 14, 95, 15, 95, 16, 94, 16, 94, 17, 93, 18, 92, 19, 92, 20, 91, 20, 91, 21, 90, 22, 89, 23, 89, 23, 88, 24, 88, 25, 87, 26, 87, 26, 86, 27, 86, 28, 85, 29, 85, 30, 84, 30, 84, 31, 83, 32, 83, 33, 82, 33, 82, 34, 82, 35, 81, 36, 81, 37, 80, 37, 80, 38, 80, 39, 79, 40, 79, 40, 78, 41, 78, 42, 78, 43, 77, 43, 77, 44, 77, 45, 76, 46, 76, 47, 76, 47, 75, 48, 75, 49, 75, 50, 75, 50, 74, 51, 74, 52, 74, 53, 74, 54, 73, 54, 73, 55, 73, 56, 73, 57, 73, 57, 72, 58, 72, 59, 72, 60, 72, 60, 72, 61, 72, 62, 72, 63, 71, 64, 71, 64, 71, 65, 71, 66, 71, 67, 71, 67, 71, 68, 71, 69, 71, 70, 71, 71, 71, 71, 71, 72, 71, 73, 71, 74, 71, 74, 71, 75, 71, 76, 71, 77, 71, 77, 71, 78, 71, 79, 71, 80, 71, 81, 71, 81, 71, 82, 71, 83, 71, 84, 71, 84, 71, 85, 71, 86, 72, 87, 72, 87, 72, 88, 72, 89, 72, 90, 72, 91, 73, 91, 73, 92, 73, 93, 73, 94, 73, 94, 74, 95, 74, 96, 74, 97, 74, 98, 75, 98, 75, 99, 75, 100, 75, 101, 76, 101, 76, 102, 76, 103, 77, 104, 77, 104, 77, 105, 78, 106, 78, 107, 78, 108, 79, 108, 79, 109, 79, 110, 80, 111, 80, 111, 80, 112, 81, 113, 81, 114, 82, 115, 82, 115, 83, 116, 83, 117, 84, 118, 84, 118, 84, 119, 85, 120, 85, 121, 86, 121, 86, 122, 87, 123, 88, 124, 88, 125, 89, 125, 89, 126, 90, 127, 90, 128, 91, 128, 91, 129, 92, 130, 93, 131, 93, 132, 94, 132, 95, 133, 95, 134, 96, 135, 96, 135, 97, 136, 98, 137, 98, 138, 99, 138, 100, 139, 101, 140, 101, 141, 102, 142, 103, 142, 103, 143, 104, 144, 105, 145, 106, 145, 106, 146, 107, 147, 108, 148, 109, 148, 110, 149, 110, 150, 111, 151, 112, 152, 113, 152, 114, 153, 115, 154, 116, 155, 116, 155, 117, 156, 118, 157, 119, 158, 120, 159, 121, 159, 122, 160, 123, 161, 124, 162, 125, 162, 126, 163, 126, 164, 127, 165, 128, 165, 129, 166, 130, 167, 131, 168, 132, 169, 133, 169, 134, 170, 136, 171, 137, 172, 138, 172, 139, 173, 140, 174, 141, 175, 142, 176, 143, 176, 144, 177, 145, 178, 146, 179, 147, 179, 149, 180, 150, 181, 151, 182, 152, 182, 153, 183, 154, 184, 156, 185, 157, 186, 158, 186, 159, 187, 160, 188, 162, 189, 163, 189, 164, 190, 165, 191, 167, 192, 168, 193, 169, 193, 171, 194, 172, 195, 173, 196, 174, 196, 176, 197, 177, 198, 178, 199, 180, 199, 181, 200, 182, 201, 184, 202, 185, 203, 187, 203, 188, 204, 189, 205, 191, 206, 192, 206, 194, 207, 195, 208, 197, 209, 198 };

  @Test
  public void shouldInterpolate() {
    // given
    QuadInterpolator quadInterpolator = new QuadInterpolator(true);
    quadInterpolator.setDensity(1);
    QuadDat quadDat = quadInterpolator.getQuadDat();
    quadDat.getInterpStartPoint().deepCopy(p0);
    quadDat.setPoint1(p1);
    quadDat.getInterpEndPoint().deepCopy(p2);
    quadInterpolator.reset();
    // do
    quadInterpolator.rawInterpolate();
    // then
    Assert.assertEquals(expected.length / 2 - 1, quadInterpolator.getQuadCurveArray().getLastPointIndex());
    for (int i = 0; i <= quadCurveArray.getLastPointIndex(); i++) {
      TrailPoint point = quadCurveArray.get(i);
      Assert.assertEquals(expected[2*i],   point.getX());
      Assert.assertEquals(expected[2*i+1], point.getY());
    }
  }
}
