/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.fxdrawertest.drawer;

import com.orange.dgil.trail.app.fxdrawertest.curves.Curves;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphicContextDrawer {

  private static final double CIRCLE_RADIUS = 4;

  private static final Color RAW_COLOR = Color.GREY;
  private static final Color LINEAR_FILTER_COLOR = Color.BLUE;
  private static final Color VECTO_COLOR = Color.RED;
  private static final Color QUAD_COLOR = Color.GREEN;

  private final GraphicsContext graphicsContext;

  private int xOffset;
  private int yOffset;

  private boolean plumeEffect;
  private int plumeCurveIndex;

  public GraphicContextDrawer(GraphicsContext graphicsContext) {
    this.graphicsContext = graphicsContext;
    initDrawer();
  }

  private void initDrawer() {
    graphicsContext.setLineWidth(1);
  }

  private void initColor(Color color) {
    graphicsContext.setFill(color);
  }

  public void drawCircle(double x, double y) {
    graphicsContext.fillOval(x, y, CIRCLE_RADIUS, CIRCLE_RADIUS);
  }

  public void clear() {
    plumeCurveIndex = 0;
    Canvas canvas = graphicsContext.getCanvas();
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public void setOffsets(int xOffset, int yOffset) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public void draw(Curves curves) {
    plumeEffect = false;
    drawRaw(curves);
    drawLinearFilter(curves);
    drawVecto(curves);
    drawQuad(curves);
  }

  private void drawRaw(Curves curves) {
    if (curves.isRawCurveEnabled()) {
      doDraw(curves.getRawCurvePoints(), RAW_COLOR, 0, 0);
    }
  }
  private void drawLinearFilter(Curves curves) {
    if (curves.isLinearFilterCurveEnabled()) {
      doDraw(curves.getLinearFilterCurvePoints(), LINEAR_FILTER_COLOR, xOffset, yOffset);
    }
  }
  private void drawVecto(Curves curves) {
    if (curves.isVectoCurveEnabled()) {
      doDraw(curves.getVectoCurvePoints(), VECTO_COLOR, 2*xOffset, 2*yOffset);
    }
  }
  private void drawQuad(Curves curves) {
    if (curves.isQuadCurveEnabled()) {
      plumeEffect = true;
      doDraw(curves.getQuadCurvePoints(), QUAD_COLOR, 3*xOffset, 3*yOffset);
      plumeCurveIndex = curves.getQuadCurvePoints().size();
    }
  }

  private void doDraw(List<Point2D> points, Color color, int xOffset, int yOffset) {
    initColor(color);
    for (int i = plumeEffect ? plumeCurveIndex : 0; i < points.size(); i++) {
      Point2D point = points.get(i);
      drawShape(point.getX() + xOffset, point.getY() + yOffset);
    }
  }

  private void drawShape(double x, double y) {
    if (plumeEffect) {
      drawPointWithPlume(x, y);
    } else {
      drawCircle(x, y);
    }
  }

  private void drawPointWithPlume(double x, double y) {
    graphicsContext.fillOval(x, y, 12, 4);
  }
}
