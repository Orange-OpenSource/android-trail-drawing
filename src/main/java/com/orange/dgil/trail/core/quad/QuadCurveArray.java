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
import com.orange.dgil.trail.core.common.TrailRect;

import lombok.Getter;

public class QuadCurveArray {
  private static final int ARRAY_LENGTH_SHORT = 500;
  private static final int ARRAY_LENGTH_LONG = 4000;

  @Getter
  private final TrailRect trailRect = new TrailRect();
  private final TrailPoint[] quadCurveArray;

  private int index;

  QuadCurveArray(boolean longCurve) {
    quadCurveArray = new TrailPoint[longCurve ? ARRAY_LENGTH_LONG : ARRAY_LENGTH_SHORT];
    initArray();
  }

  private void initArray() {
    for (int i = 0; i < quadCurveArray.length; i++) {
      quadCurveArray[i] = new TrailPoint();
    }
  }

  void reset() {
    index = 0;
  }

  void add(int x, int y) {
    if (index == quadCurveArray.length) {
      throw new QuadCurveArrayException("Quad curve array is full");
    } else {
      doAdd(x, y);
    }
  }

  private void doAdd(int x, int y) {
    updateTrailRect(x, y);
    quadCurveArray[index].set(x, y);
    index++;
  }

  private void updateTrailRect(int x, int y) {
    if (index == 0) {
      trailRect.initAtPoint(x, y);
    } else {
      trailRect.union(x, y);
    }
  }

  public TrailPoint get(int index) {
    return quadCurveArray[index];
  }

  public int getLastPointIndex() {
    return index-1;
  }

  public TrailPoint getLastPoint() {
    return get(getLastPointIndex());
  }

  public boolean isNotEmpty() {
    return index > 0;
  }
}
