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

import com.google.common.annotations.VisibleForTesting;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.vecto.SlidingWindow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Polynomial approximation of degree 2, for the polygon P0 -- P1 -- P2
 * <p/>
 * Description:
 * x = A + B*t + C*t^2
 * y = D + E*t + F*t^2 with t in [O, 1]
 * t=0 -> p0 ; t=1 -> p2
 * <p/>
 * at p0, the curve is tangent with P0P1
 * at p2, the curve is tangent with P2P1
 */
class QuadInterpolator {
  private static final int QUAD_INTERPOLATION_DENSITY = 1;
  private static final int NB_POINTS_FOR_INTERP = 3;

  @Getter(AccessLevel.PACKAGE)
  private final QuadDat quadDat = new QuadDat();
  @Getter(AccessLevel.PACKAGE)
  private final QuadCurveArray quadCurveArray;

  private final SlidingWindow slidingWindow = new SlidingWindow(NB_POINTS_FOR_INTERP);

  @Setter
  private int density = QUAD_INTERPOLATION_DENSITY;


  QuadInterpolator(boolean longCurve) {
    quadCurveArray = new QuadCurveArray(longCurve);
  }


  void reset() {
    slidingWindow.reset();
    quadCurveArray.reset();
    quadDat.reset();
  }

  void newPointAdded(int x, int y) {
    slidingWindow.add(x, y);
    if (hasSufficientPoints()) {
      doInterpolation();
    }
  }

  void lastPointAdded(int x, int y) {
    quadDat.setCurveEnd(true);
    newPointAdded(x, y);
  }

  void lastPointAddedPreviously() {
    quadDat.setCurveEnd(true);
    if (hasSufficientPoints()) {
      doInterpolation();
    }
  }

  private boolean hasSufficientPoints() {
    return slidingWindow.isFull();
  }

  private void doInterpolation() {
    quadDat.setPoint0(slidingWindow.getElementAt(0));
    quadDat.setPoint1(slidingWindow.getElementAt(1));
    quadDat.setPoint2(slidingWindow.getElementAt(2));
    interpolate();
    quadDat.setCurveStart(false);
  }



  @VisibleForTesting
  void interpolate() {
    updateInterpStartAndEndPoints();
    rawInterpolate();
    handleCurveEnd();
  }

  private void updateInterpStartAndEndPoints() {
    updateInterpStartEndWithMiddles();
    if (quadDat.isCurveStart()) {
      quadDat.getInterpStartPoint().deepCopy(quadDat.getPoint0());
    } else if (quadDat.isCurveEnd()) {
      quadDat.getInterpEndPoint().deepCopy(quadDat.getPoint2());
    }
  }

  private void updateInterpStartEndWithMiddles() {
    updateWithMiddle(quadDat.getInterpStartPoint(), quadDat.getPoint0(), quadDat.getPoint1());
    updateWithMiddle(quadDat.getInterpEndPoint(), quadDat.getPoint1(), quadDat.getPoint2());
  }

  private void updateWithMiddle(TrailPoint destPoint, TrailPoint point0, TrailPoint point1) {
    destPoint.set((point0.getX() + point1.getX() + 1)/2, (point0.getY() + point1.getY() + 1)/2);
  }

  private void handleCurveEnd() {
    if (quadDat.isCurveEnd()) {
      TrailPoint lastPoint = quadDat.getPoint2();
      quadCurveArray.add(lastPoint.getX(), lastPoint.getY());
    }
  }


  @VisibleForTesting
  void rawInterpolate() {
    initInterpolation();
    doInterpolate();
  }

  private void doInterpolate() {
    for (int t = 0; t < quadDat.getInterpNbPoints(); t++) {
      doInterpolateAtT(t);
    }
  }

  private void doInterpolateAtT(int t) {
    int x = getInterp(t, quadDat.getCoefA(), quadDat.getCoefB(), quadDat.getCoefC(), quadDat.getInterpNbPoints());
    int y = getInterp(t, quadDat.getCoefD(), quadDat.getCoefE(), quadDat.getCoefF(), quadDat.getInterpNbPoints());
   quadCurveArray.add(x, y);
  }

  private int getInterp(int t, long coef1, long coef2, long coef3, int nbPoints) {
    long mod1 = ((coef3 * t) << 12) / nbPoints + (coef2 << 12);
    long mod2 = (mod1 * t) / nbPoints + (coef1 << 12);
    return (int) ((mod2 + 2 ^ 11) >> 12);
  }

  private void initInterpolation() {
    initMiddlePoint();
    initInterpNbPoints();
    initCoefficients();
  }

  private void initCoefficients() {
    TrailPoint p0 = quadDat.getInterpStartPoint();
    TrailPoint p1 = quadDat.getPoint1();
    TrailPoint p2 = quadDat.getInterpEndPoint();

    quadDat.setCoefA(p0.getX());
    quadDat.setCoefB((p1.getX() - p0.getX()) << 1);
    quadDat.setCoefC(p2.getX() + p0.getX() - (p1.getX() << 1));

    quadDat.setCoefD(p0.getY());
    quadDat.setCoefE((p1.getY() - p0.getY()) << 1);
    quadDat.setCoefF(p2.getY() + p0.getY() - (p1.getY() << 1));
  }

  /** Found middle point, at t = 0.5 */
  private void initMiddlePoint() {
    TrailPoint p0 = quadDat.getInterpStartPoint();
    TrailPoint p1 = quadDat.getPoint1();
    TrailPoint p2 = quadDat.getInterpEndPoint();

    int x = getMiddleApproximation(p0.getX(), p1.getX(), p2.getX());
    int y = getMiddleApproximation(p0.getY(), p1.getY(), p2.getY());

    quadDat.getMiddlePoint().set(x, y);
  }

  private int getMiddleApproximation(int coord0, int coord1, int coord2) {
    return ( ((coord0 + coord1 + 1) >> 1) + ((coord1 + coord2 + 1) >> 1) + 1) >> 1;
  }


  private void initInterpNbPoints() {
    int curveLength = getCurveLengthApproximation();
    quadDat.setInterpNbPoints(curveLength / density + 1);
  }

  private int getCurveLengthApproximation() {
    TrailPoint p0 = quadDat.getInterpStartPoint();
    TrailPoint p2 = quadDat.getInterpEndPoint();
    TrailPoint middlePoint = quadDat.getMiddlePoint();
    return (int) (p0.getDistanceTo(middlePoint) + middlePoint.getDistanceTo(p2) + 0.5f);
  }
}
