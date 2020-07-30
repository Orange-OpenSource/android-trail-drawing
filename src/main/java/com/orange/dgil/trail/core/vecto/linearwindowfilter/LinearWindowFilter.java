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

import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.vecto.SlidingWindow;

public class LinearWindowFilter {

  /** window elements weight */
  private final int[] windowWeights = {1, 2, 1};

  /** window elements */
  private final SlidingWindow slidingWindow = new SlidingWindow(windowWeights.length);

  private final LinearWindowFilterListener linearWindowFilterListener;

  private int weightsSum;


  public LinearWindowFilter(LinearWindowFilterListener linearWindowFilterListener) {
    this.linearWindowFilterListener = linearWindowFilterListener;
    initProperties();
  }

  void initProperties() {
    weightsSum = getWeightsSum();
  }

  int getWeightsSum() {
    int sum = 0;
    for (int weight : windowWeights) {
      sum+= weight;
    }
    return sum;
  }


  public void reset() {
    slidingWindow.reset();
  }

  public TrailPoint getLastPoint() {
    return slidingWindow.getLastElement();
  }

  public void addPoint(TrailPoint point) {
    if (!slidingWindow.isSameAsLast(point)) {
      doAddPoint(point);
    }
  }

  void doAddPoint(TrailPoint point) {
    slidingWindow.add(point);
    if (isNewPointAvailable()) {
      computeNewPointAndNotifyListener();
    }
  }

  boolean isNewPointAvailable() {
    return slidingWindow.getAddedElementsNumber() == 1 || slidingWindow.isFull();
  }

  void computeNewPointAndNotifyListener() {
    int x;
    int y;
    if (slidingWindow.getAddedElementsNumber() == 1) {
      x = slidingWindow.getX(0);
      y = slidingWindow.getY(0);
    } else {
      x = getMeanX();
      y = getMeanY();
    }
    linearWindowFilterListener.onNewFilteredPointAvailable(x, y);
  }

  int getMeanX() {
    int sumX = 0;
    for (int i = 0; i < windowWeights.length; i++) {
      sumX += slidingWindow.getX(i) * windowWeights[i];
    }
    return (sumX + weightsSum/2) / weightsSum;
  }
  int getMeanY() {
    int sumY = 0;
    for (int i = 0; i < windowWeights.length; i++) {
      sumY += slidingWindow.getY(i) * windowWeights[i];
    }
    return (sumY + weightsSum/2) / weightsSum;
  }
}
