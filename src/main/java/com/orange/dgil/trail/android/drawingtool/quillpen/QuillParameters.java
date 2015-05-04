/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.drawingtool.quillpen;

import com.orange.dgil.trail.android.AndroidMetrics;
import com.orange.dgil.trail.android.DefaultDrawerConf;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class QuillParameters {

  @Getter(AccessLevel.NONE)
  private final AndroidMetrics metrics;

  private int quillWidthPixels;
  private int quillHeightPixels;
  private int quillAngleDeg;
  private int quillAngleXOffset;
  private int radius;

  public QuillParameters(AndroidMetrics metrics) {
    this.metrics = metrics;
    init();
  }

  private void init() {
    setQuillWidthHeightMicrometers(DefaultDrawerConf.QUILL_WIDTH_UM, DefaultDrawerConf.QUILL_HEIGHT_UM);
    setQuillAngleDeg(DefaultDrawerConf.QUILL_ANGLE_DEG);
  }

  public void setQuillWidthHeightMicrometers(int widthMicrometers, int heightMicrometers) {
    quillWidthPixels = metrics.micrometersToPixels(widthMicrometers);
    quillHeightPixels = metrics.micrometersToPixels(heightMicrometers);
    radius = 2 * quillWidthPixels;
    setQuillAngleDeg(quillAngleDeg);
  }

  public void setQuillAngleDeg(int angleDeg) {
    // FIXME - TODO
    quillAngleDeg = angleDeg;
    quillAngleXOffset = quillHeightPixels;
  }
}
