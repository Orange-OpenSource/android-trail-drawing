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
import com.orange.dgil.trail.core.vecto.SlidingWindow;

/**
 * Fine vectorization method based on the height analysis of all possible vectors
 * of the sliding window.
 */
public class RawVectoFilter implements OnWindowSizeListener {

  private final WindowAnalysis windowAnalysis = new WindowAnalysis(this);
  private final SlidingWindow slidingWindow = windowAnalysis.getSlidingWindow();
  private final VectoFilterListener vectoFilterListener;

  private TrailPoint[] pointsForListener = new TrailPoint[slidingWindow.getWindowSize()];
  private int nbPointsForListener;
  private boolean firstPoint = true;


  public RawVectoFilter(VectoFilterListener vectoFilterListener) {
    this.vectoFilterListener = vectoFilterListener;
    allocatePoints();
  }

  private void allocatePoints() {
    for (int i = 0; i < pointsForListener.length; i++) {
      pointsForListener[i] = new TrailPoint();
    }
  }

  public void reset() {
    firstPoint = true;
    nbPointsForListener = 0;
    slidingWindow.reset();
  }

  public void addPoint(TrailPoint point) {
    doAddPoint(point);
    notifyListenerForNewPoints(false);
  }

  public void epilogue() {
    addRemainingSalientPointsInWindow();
    addRawReleasePointToVecto();
    notifyListenerForNewPoints(true);
  }

  private void addRemainingSalientPointsInWindow() {
    boolean salientPointFound = true;
    while (isAnalysisPossible() && salientPointFound) {
      windowAnalysis.analyse();
      salientPointFound = windowAnalysis.isSalientPointFoundInWindow();
      if (salientPointFound) {
        addSalientPoint();
      }
    }
  }
  private boolean isAnalysisPossible() {
    return slidingWindow.getAddedElementsNumber() >= 3;
  }

  private void addRawReleasePointToVecto() {
    addPointToVecto(slidingWindow.getLastElement());
  }

  private void doAddPoint(TrailPoint p) {
    slidingWindow.add(p);
    if (firstPoint) {
      firstPoint = false;
      addPointToVecto(p);
    } else if (slidingWindow.isFull()) {
      analyseWindow();
    }
  }

  private void analyseWindow() {
    windowAnalysis.analyse();
    addSalientPoint();
  }

  private void addSalientPoint() {
    int salientPointIndex = windowAnalysis.getLastSalientPointIndex();
    addPointToVecto(slidingWindow.getElementAt(salientPointIndex));
    slidingWindow.removeElementsAtLeftOf(salientPointIndex);
  }


  private void addPointToVecto(TrailPoint p) {
    pointsForListener[nbPointsForListener].set(p.getX(), p.getY());
    nbPointsForListener++;
  }

  private void notifyListenerForNewPoints(boolean epilogue) {
    for (int i = 0; i < nbPointsForListener; i++) {
      boolean lastPoint = epilogue && (i == (nbPointsForListener - 1));
      notifyListenerForNewPoint(pointsForListener[i], lastPoint);
    }
    nbPointsForListener =0;
  }
  private void notifyListenerForNewPoint(TrailPoint point, boolean lastPoint) {
    int x = point.getX();
    int y = point.getY();
    if (lastPoint) {
      vectoFilterListener.onLastVectoPointAvailable(x, y);
    } else {
      vectoFilterListener.onNewVectoPointAvailable(x, y);
    }
  }

  public VectoSettings getVectoSettings() {
    return windowAnalysis.getVectoSettings();
  }

  @Override
  public void onWindowSizeChanged(int size) {
    pointsForListener = new TrailPoint[size];
    allocatePoints();
  }
}
