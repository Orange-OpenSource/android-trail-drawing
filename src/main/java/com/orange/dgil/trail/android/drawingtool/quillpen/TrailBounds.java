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

import android.view.View;

import com.orange.dgil.trail.android.drawingtool.InvalidateArea;
import com.orange.dgil.trail.core.common.TrailPoint;
import com.orange.dgil.trail.core.common.TrailRect;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
class TrailBounds {

  private final QuillParameters quillParameters;
  private final TrailRect trailRect;
  private final InvalidateArea trailBounds = new InvalidateArea();
  private final InvalidateArea lastMoveBounds = new InvalidateArea();
  private final TrailPoint lastPoint = new TrailPoint();

  void updateTrailBoundsOnTouchDown(int x, int y) {
    updateRadius();
    lastMoveBounds.setEmpty();
    lastPoint.set(x, y);
  }

  void updateRadius() {
    trailBounds.setRadius(quillParameters.getRadius());
    lastMoveBounds.setRadius(quillParameters.getRadius());
  }

  void updateTrailBounds(int x, int y) {
    lastMoveBounds.setOrigin(lastPoint.getX(), lastPoint.getY());
    lastMoveBounds.add(x, y);
    lastPoint.set(x, y);
  }

  void invalidateAreaOnMove(View view) {
    view.invalidate(lastMoveBounds.getRect());
  }

  void invalidatePath(View view) {
    trailBounds.setOrigin(trailRect.getLeft(), trailRect.getTop());
    trailBounds.add(trailRect.getRight(), trailRect.getBottom());
    view.invalidate(trailBounds.getRect());
  }
}
