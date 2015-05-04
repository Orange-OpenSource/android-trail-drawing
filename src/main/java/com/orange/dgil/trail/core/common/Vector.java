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

import lombok.Getter;

public class Vector {

  @Getter
  private final TrailPoint point1 = new TrailPoint();
  @Getter
  private final TrailPoint point2 = new TrailPoint();
  @Getter
  private final TrailPoint point3 = new TrailPoint();

  private int norm12;
  private int norm13;
  private int norm23;

  public void setPoint1(TrailPoint point) {
    point1.set(point.getX(), point.getY());
  }
  public void setPoint2(TrailPoint point) {
    point2.set(point.getX(), point.getY());
  }
  public void setPoint3(TrailPoint point) {
    point3.set(point.getX(), point.getY());
  }

  /**
   * @return the projection height of point 3 on vector v12
   */
  public int getHeightP3ToV12() {
    int norm12 = getNorm(point1, point2);
    float height = Math.abs((float) crossProduct() / norm12);
    return (int) (height + 0.5);
  }

  /**
   * @param normMin minimum distance between points to consider a valid height
   * @return the projection height of point 2 on vector v13
   */
  public int getHeightP2ToV13(int normMin) {
    updateNorms();

    if (areP1P2orP2P3TooClose(normMin)) {
      return 0;
    } else if (areP1P3TooClose(normMin)) {
      return getRawHeightEstimation();
    } else {
      return doGetHeightP2ToV13();
    }
  }

  private void updateNorms() {
    norm12 = getNorm(point1, point2);
    norm13 = getNorm(point1, point3);
    norm23 = getNorm(point2, point3);
  }

  private boolean areP1P2orP2P3TooClose(int normMin) {
    return norm12 < normMin || norm23 < normMin;
  }

  private boolean areP1P3TooClose(int normMin) {
    return norm13 < normMin;
  }

  private int getRawHeightEstimation() {
    return (norm12 + norm23)/2;
  }

  private int doGetHeightP2ToV13() {
    int crossProduct = Math.abs(crossProduct());
    float height = (float)crossProduct / norm13 + 0.5f;
    return (int)height;
  }


  private int crossProduct() {
    int v1x = getDx(point1, point2);
    int v1y = getDy(point1, point2);
    int v2x = getDx(point1, point3);
    int v2y = getDy(point1, point3);
    return v1x * v2y - v1y * v2x;
  }

  private int getNorm(TrailPoint point1, TrailPoint point2) {
    return (int) Math.sqrt(Math.pow(getDx(point1, point2), 2) + Math.pow(getDy(point1, point2), 2));
  }

  private int getDx(TrailPoint point1, TrailPoint point2) {
    return point2.getX() - point1.getX();
  }

  private int getDy(TrailPoint point1, TrailPoint point2) {
    return point2.getY() - point1.getY();
  }
}
