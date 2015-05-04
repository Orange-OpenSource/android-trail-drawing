/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto.vecto;

import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.common.Vector;
import com.orange.dgil.trail.core.vecto.SlidingWindow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class WindowAnalysis {

  private static final int DEFAULT_WINDOW_SIZE = 8;

  @Getter
  private final VectoSettings vectoSettings = new VectoSettings(this);
  @Getter
  private final SlidingWindow slidingWindow = new SlidingWindow(DEFAULT_WINDOW_SIZE);

  private final Vector v13 = new Vector();

  private final OnWindowSizeListener onWindowSizeListener;

  @Getter
  private boolean salientPointFoundInWindow;
  @Getter
  private int lastSalientPointIndex;

  private boolean salientPointFoundInRange;
  private int lastIndex;


  public void analyse() {
    lastIndex = slidingWindow.getLastElementIndex();
    lastSalientPointIndex = lastIndex;
    salientPointFoundInWindow = false;
    doAnalysis();
  }

  private void doAnalysis() {
    updatePoint1();
    for (int endIndex = lastIndex; endIndex > 2; endIndex--) {
      analyseRange(endIndex);
      if (!salientPointFoundInRange) {
        lastSalientPointIndex--;
        break;
      }
    }
  }

  private void updatePoint1() {
    v13.setPoint1(slidingWindow.getElementAt(0));
  }
  private void updatePoint3(int index) {
    v13.setPoint3(slidingWindow.getElementAt(index));
  }

  private void analyseRange(int endIndex) {
    salientPointFoundInRange = false;
    updatePoint3(endIndex);
    doAnalyseRange(endIndex-1);
  }

  private void doAnalyseRange(int point2EndIndex) {
    for (int i = 1; i <= point2EndIndex; i++) {
      TrailPoint p2 = slidingWindow.getElementAt(i);
      v13.setPoint2(p2);
      analyseHeight(point2EndIndex);
    }
  }

  private void analyseHeight(int point2EndIndex) {
    int vectorsHeightThreshold = vectoSettings.getVectorsHeightThreshold();
    int height = v13.getHeightP2ToV13(vectorsHeightThreshold);
    if (height > vectorsHeightThreshold) {
      lastSalientPointIndex = point2EndIndex;
      salientPointFoundInRange = true;
      salientPointFoundInWindow = true;
    }
  }

  public void setWindowSize(int windowSize) {
    slidingWindow.setWindowSize(windowSize);
    if (onWindowSizeListener != null) {
      onWindowSizeListener.onWindowSizeChanged(windowSize);
    }
  }
}
