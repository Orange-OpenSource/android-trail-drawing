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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orange.dgil.trail.android.drawingtool.TrailOptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BitmapDrawer {

  private final Paint paint = new Paint();
  private final RectF rectF = new RectF();

  private final TrailOptions trailOptions;

  private int wRadius;
  private int hRadius;

  void updateDrawParameters() {
    paint.setColor(trailOptions.getColor());
    updateRadius();
  }

  private void updateRadius() {
    QuillParameters quillParameters = trailOptions.getQuillParameters();
    wRadius = quillParameters.getQuillWidthPixels()/2;
    hRadius = quillParameters.getQuillHeightPixels()/2;
  }

  void forceColor(int color) {
    paint.setColor(color);
  }

  void drawPoint(Canvas canvas, int x, int y) {
    rectF.set(x - wRadius, y - hRadius, x + wRadius, y + hRadius);
    canvas.drawOval(rectF, paint);
  }

  void drawCirclePoint(Canvas canvas, int x, int y) {
    rectF.set(x - wRadius, y - wRadius, x + wRadius, y + wRadius);
    canvas.drawOval(rectF, paint);
  }
}
