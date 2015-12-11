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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class QuillBitmap {
  private final View view;
  private final Paint paint = new Paint();

  @Getter
  private Bitmap bitmap;
  @Getter
  private Canvas bitmapCanvas;

  void lazyLoading() {
    if (shouldAllocateNewBitmap()) {
      releaseBitmap();
      bitmap = getNewBitmap();
      bitmapCanvas = new Canvas(bitmap);
    }
  }

  private boolean shouldAllocateNewBitmap() {
    return bitmap == null || bitmap.getWidth() != view.getWidth() || bitmap.getHeight() != view.getHeight();
  }

  /**
   * Release bitmap memory allocation; we are quite paranoiac here but it is worthy:
   *  - make sure the bitmap ref is not kept by the canvas (java/native objects)
   *  - indicates that the bitmap can be recycled rapidly (call to recycle())
   * So we release memory, but also try to let the system understand that it can freed it at once.
   * We are fair with other apps, but it is also a way to keep our app in the background alive.
   */
  void releaseBitmap() {
    if (bitmap != null) {
      bitmapCanvas.setBitmap(null);
      bitmapCanvas = null;
      bitmap.recycle();
      bitmap = null;
    }
  }

  void drawBitmap(Canvas canvas) {
    canvas.drawBitmap(bitmap, 0, 0, paint);
  }

  boolean isLoaded() {
    return bitmap != null;
  }

  void reset() {
    resetAlpha();
    eraseBitmap();
  }

  void resetAlpha() {
    paint.setAlpha(255);
  }

  private void eraseBitmap() {
    if (isLoaded()) {
      bitmap.eraseColor(Color.TRANSPARENT);
    }
  }

  void setAlpha(int alpha) {
    paint.setAlpha(alpha);
  }

  private Bitmap getNewBitmap() {
    return Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
  }
}
