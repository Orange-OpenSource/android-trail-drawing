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

import com.orange.dgil.trail.android.AndroidMetrics;
import com.orange.dgil.trail.android.DefaultDrawerConf;
import com.orange.dgil.trail.android.drawingtool.quillpen.QuillParameters;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TrailOptions {
  @Getter(AccessLevel.NONE)
  private DrawingTools drawingTools;
  @Getter(AccessLevel.NONE)
  private AndroidMetrics metrics;

  @Setter
  private int color = DefaultDrawerConf.TRAIL_COLOR;
  @Setter
  private int shadowColor;

  private QuillParameters quillParameters;

  private boolean shadowEnabled;
  private int shadowOffsetPixels;
  private int widthPixels;

  public TrailOptions(AndroidMetrics metrics, DrawingTools drawingTools) {
    init(metrics, drawingTools);
  }

  private void init(AndroidMetrics metrics, DrawingTools drawingTools) {
    this.metrics = metrics;
    this.drawingTools = drawingTools;
    quillParameters = new QuillParameters(metrics);
    setTrailWidthMicrometers(DefaultDrawerConf.TRAIL_WIDTH_UM);
  }

  public void setTrailWidthMicrometers(int micrometers) {
    widthPixels  = metrics.micrometersToPixels(micrometers);
    shadowOffsetPixels = (int) (widthPixels * DefaultDrawerConf.SHADOW_OFFSET_FACTOR);
  }

  public boolean isShadowAvailable() {
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public void setShadowEnabled(boolean shadowEnabled) {
    this.shadowEnabled = shadowEnabled && isShadowAvailable();
  }

  public void selectMarkerPen() {
    drawingTools.selectMarkerPen();
  }

  public void selectQuillPen() {
    drawingTools.selectQuillPen();
  }
}
