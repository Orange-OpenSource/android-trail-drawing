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

import android.graphics.Point;
import android.graphics.Rect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidateArea {
  private final Rect rect = new Rect();
  private int radius;

  public void setOrigin(int x, int y) {
    rect.set(x - radius, y - radius, x + radius, y + radius);
  }
  public void setOrigin(Point p) {
    setOrigin(p.x, p.y);
  }

  public void add(int x, int y) {
    rect.union(x - radius, y - radius, x + radius, y + radius);
  }
  public void add(Point p) {
    add(p.x, p.y);
  }

  public void setEmpty() {
    rect.setEmpty();
  }
}
