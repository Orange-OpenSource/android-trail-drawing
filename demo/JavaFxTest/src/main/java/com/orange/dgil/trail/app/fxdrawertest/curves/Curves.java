/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.fxdrawertest.curves;

import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.quad.QuadCurve;
import com.orange.dgil.trail.core.quad.QuadCurveArray;
import com.orange.dgil.trail.core.vecto.linearwindowfilter.LinearWindowFilter;
import com.orange.dgil.trail.core.vecto.linearwindowfilter.LinearWindowFilterListener;
import com.orange.dgil.trail.core.vecto.vecto.VectoFilter;
import com.orange.dgil.trail.core.vecto.vecto.VectoFilterListener;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class Curves implements LinearWindowFilterListener, VectoFilterListener {

  private boolean rawCurveEnabled;
  private boolean linearFilterCurveEnabled;
  private boolean vectoCurveEnabled;
  private boolean quadCurveEnabled;

  private List<Point2D> rawCurvePoints = new ArrayList<Point2D>();
  private List<Point2D> linearFilterCurvePoints = new ArrayList<Point2D>();
  private List<Point2D> vectoCurvePoints = new ArrayList<Point2D>();
  private List<Point2D> quadCurvePoints = new ArrayList<Point2D>();

  private final LinearWindowFilter linearWindowFilter = new LinearWindowFilter(this);
  private final VectoFilter vectoFilter = new VectoFilter(this);
  private final QuadCurve quadCurve = new QuadCurve(true);

  public void clear() {
    rawCurvePoints.clear();
    linearFilterCurvePoints.clear();
    vectoCurvePoints.clear();
    quadCurvePoints.clear();
    linearWindowFilter.reset();
    vectoFilter.reset();
    quadCurve.reset();
  }

  public void end() {
    TrailPoint lastPoint = linearWindowFilter.getLastPoint();
    linearFilterCurvePoints.add(new Point2D(lastPoint.getX(), lastPoint.getY()));
    endVecto();
    endQuadCurve();
  }

  private void endVecto() {
    vectoFilter.epilogue();
  }

  private void endQuadCurve() {
    quadCurve.epilogue();
    fillQuadCurve();
  }


  public void addPoint(double x, double y) {
    rawCurvePoints.add(new Point2D(x, y));
    addPointToLinearFilter(x, y);
    addPointToVecto(x, y);
    addPointToQuadCurve(x, y);
  }

  private void addPointToLinearFilter(double x, double y) {
    linearWindowFilter.addPoint(new TrailPoint((int)x, (int)y));
  }

  private void addPointToVecto(double x, double y) {
    vectoFilter.addPoint((int)x, (int)y);
  }

  private void addPointToQuadCurve(double x, double y) {
    quadCurve.addPoint((int)x, (int)y);
    fillQuadCurve();
  }

  private void fillQuadCurve() {
    QuadCurveArray quadCurveArray = quadCurve.getQuadCurveArray();
    for (int i = quadCurvePoints.size(); i <= quadCurveArray.getLastPointIndex(); i++) {
      TrailPoint p = quadCurveArray.get(i);
      quadCurvePoints.add(new Point2D(p.getX(), p.getY()));
    }
  }

  public boolean isRawCurveEnabled() {
    return rawCurveEnabled;
  }
  public void setRawCurveEnabled(boolean rawCurveEnabled) {
    this.rawCurveEnabled = rawCurveEnabled;
  }

  public boolean isLinearFilterCurveEnabled() {
    return linearFilterCurveEnabled;
  }
  public void setLinearFilterCurveEnabled(boolean linearFilterCurveEnabled) {
    this.linearFilterCurveEnabled = linearFilterCurveEnabled;
  }

  public boolean isVectoCurveEnabled() {
    return vectoCurveEnabled;
  }
  public void setVectoCurveEnabled(boolean vectoCurveEnabled) {
    this.vectoCurveEnabled = vectoCurveEnabled;
  }

  public boolean isQuadCurveEnabled() {
    return quadCurveEnabled;
  }
  public void setQuadCurveEnabled(boolean quadCurveEnabled) {
    this.quadCurveEnabled = quadCurveEnabled;
  }

  public List<Point2D> getRawCurvePoints() {
    return rawCurvePoints;
  }

  public List<Point2D> getLinearFilterCurvePoints() {
    return linearFilterCurvePoints;
  }

  public List<Point2D> getVectoCurvePoints() {
    return vectoCurvePoints;
  }

  public List<Point2D> getQuadCurvePoints() {
    return quadCurvePoints;
  }

  public void setVectoErrorPixels(int error) {
    vectoFilter.getVectoSettings().setVectorsHeightThreshold(error);
  }
  public void setVectoWindowSize(int windowSize) {
    vectoFilter.getVectoSettings().setWindowSize(windowSize);
  }

  @Override
  public void onNewFilteredPointAvailable(int x, int y) {
    linearFilterCurvePoints.add(new Point2D(x, y));
  }

  @Override
  public void onNewVectoPointAvailable(int x, int y) {
    vectoCurvePoints.add(new Point2D(x, y));
  }
  @Override
  public void onLastVectoPointAvailable(int x, int y) {
    onNewVectoPointAvailable(x, y);
  }
}
