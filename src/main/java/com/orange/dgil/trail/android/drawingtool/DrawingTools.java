/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.drawingtool;

import android.view.View;

import com.orange.dgil.trail.android.AndroidMetrics;
import com.orange.dgil.trail.android.animation.AnimManager;
import com.orange.dgil.trail.android.drawingtool.markerpen.MarkerPaths;
import com.orange.dgil.trail.android.drawingtool.quillpen.QuillPen;

import lombok.Getter;

public class DrawingTools {

  @Getter
  private IDrawingTool drawingTool;
  @Getter
  private TrailOptions trailOptions;

  private MarkerPaths markerPaths;
  private QuillPen quillPen;

  public DrawingTools(View view, AnimManager animManager) {
    initDrawingTools(view, animManager);
  }

  private void initDrawingTools(View view, AnimManager animManager) {
    AndroidMetrics androidMetrics = AndroidMetrics.get(view.getContext());
    trailOptions = new TrailOptions(androidMetrics, this);
    DrawingToolsContext drawingToolsContext = new DrawingToolsContext(view, animManager, androidMetrics, trailOptions);
    markerPaths = new MarkerPaths(drawingToolsContext);
    quillPen = new QuillPen(drawingToolsContext);
    selectQuillPen();
  }

  public void selectMarkerPen() {
    drawingTool = markerPaths;
  }

  public void selectQuillPen() {
    drawingTool = quillPen;
  }
}
