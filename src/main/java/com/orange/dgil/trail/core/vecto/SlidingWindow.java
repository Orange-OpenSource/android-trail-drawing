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

import lombok.Getter;

public class SlidingWindow {

  private TrailPoint[] points;

  @Getter
  private int addedElementsNumber;

  public SlidingWindow(int windowSize) {
    setWindowSize(windowSize);
  }

  public void setWindowSize(int windowSize) {
    points = new TrailPoint[windowSize];
    allocatePoints();
  }

  public int getWindowSize() {
    return points.length;
  }

  private void allocatePoints() {
    for (int i = 0; i < points.length; i++) {
      points[i] = new TrailPoint();
    }
  }

  public void reset() {
    addedElementsNumber = 0;
  }

  public void add(int x, int y) {
    if (isFull()) {
      slidePointsToTheLeft();
    }
    doAdd(x, y);
  }

  public void add(TrailPoint point) {
    add(point.getX(), point.getY());
  }

  public int getX(int index) {
    if (isIndexValid(index)) {
      return points[index].getX();
    } else {
      throw new SlidingWindowIndexException(String.format("Invalid index '%d' (max index %d)", index, getMaxIndex()));
    }
  }

  public int getY(int index) {
    if (isIndexValid(index)) {
      return points[index].getY();
    } else {
      throw getInvalidIndexException(index);
    }
  }

  boolean isIndexValid(int index) {
    return index >= 0 && index <= getMaxIndex();
  }

  int getMaxIndex() {
    return Math.min(addedElementsNumber, points.length) - 1;
  }

  void slidePointsToTheLeft() {
    TrailPoint p0 = points[0];
    for (int i = 0; i < points.length-1; i++) {
      points[i] = points[i+1];
    }
    points[points.length-1] = p0;
  }

  void doAdd(int x, int y) {
    points[getInsertIndex()].set(x, y);
    addedElementsNumber++;
  }

  public TrailPoint getElementAt(int index) {
    if (isIndexValid(index)) {
      return points[index];
    } else {
      throw getInvalidIndexException(index);
    }
  }

  public TrailPoint getLastElement() {
    return getElementAt(getLastElementIndex());
  }

  public boolean isSameAsLast(TrailPoint p) {
    if (addedElementsNumber == 0) {
      return false;
    } else {
      return p.isSameAs(points[getLastElementIndex()]);
    }
  }

  int getInsertIndex() {
    return Math.min(addedElementsNumber, points.length - 1);
  }

  public int getLastElementIndex() {
    return Math.min(addedElementsNumber - 1, points.length - 1);
  }

  public boolean isFull() {
    return addedElementsNumber >= points.length;
  }

  public void removeElementsAtLeftOf(int index) {
    if (isIndexValid(index)) {
      doRemoveElementsAtLeftOf(index);
    } else {
      throw getInvalidIndexException(index);
    }
  }

  private void doRemoveElementsAtLeftOf(int index) {
    for (int i = 0; i <= getLastElementIndex()-index; i++) {
      int sourceIndex = index + i;
      shiftPoint(i, sourceIndex);
    }
    addedElementsNumber -= index;
  }

  private void shiftPoint(int destIndex, int sourceIndex) {
    if (sourceIndex < points.length) {
      TrailPoint source = points[sourceIndex];
      points[destIndex].set(source.getX(), source.getY());
    }
  }

  private SlidingWindowIndexException getInvalidIndexException(int index) {
    return new SlidingWindowIndexException(String.format("Invalid index '%d' (max index %d)", index, getMaxIndex()));
  }
}
