/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrailPoint {
  private int x = -1;
  private int y = -1;

  public TrailPoint(int x, int y) {
    set(x, y);
  }

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean isSameAs(TrailPoint point) {
    return x == point.x && y == point.y;
  }

  public double getDistanceTo(TrailPoint point) {
    int dx = x - point.getX();
    int dy = y - point.getY();
    return Math.sqrt(dx * dx + dy * dy);
  }

  public void deepCopy(TrailPoint point) {
    x = point.x;
    y = point.y;
  }
}
