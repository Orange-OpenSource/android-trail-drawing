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

import android.graphics.Canvas;

public interface IDrawingTool {
  void reset();

  void touchDown(int x, int y);
  void touchMove(int x, int y);
  void touchUp();

  void draw(Canvas c);

  void invalidateAreaOnMove();
  void invalidatePath();

  void forceRedrawForAnimation(boolean eraseBitmap);

  void trimMemory();
}
