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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import lombok.Getter;

class QuillBitmap {

  private final View view;
  private final Paint paint = new Paint();

  @Getter
  private Bitmap bitmap;
  @Getter
  private Canvas bitmapCanvas;


  QuillBitmap(View view) {
    this.view = view;
  }

  void lazyLoading() {
    if (bitmap == null) {
      bitmap = getNewBitmap();
      bitmapCanvas = new Canvas(bitmap);
    }
  }

  void drawBitmap(Canvas canvas) {
    canvas.drawBitmap(bitmap, 0, 0, paint);
  }

  boolean isLoaded() {
    return bitmap != null;
  }

  void reset() {
    if (isLoaded()) {
      eraseBitmap();
    }
    paint.setAlpha(255);
  }

  private void eraseBitmap() {
    bitmap.eraseColor(Color.TRANSPARENT);
  }

  void setAlpha(int alpha) {
    paint.setAlpha(alpha);
  }

  private Bitmap getNewBitmap() {
    return Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
  }
}
