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

@Getter
public class TrailRect {
  private int left;
  private int top;
  private int right;
  private int bottom;

  public void initAtPoint(int x, int y) {
    left = x;
    top = y;
    right = x;
    bottom = y;
  }

  public void union(int x, int y) {
    left = Math.min(left, x);
    right = Math.max(right, x);
    top = Math.min(top, y);
    bottom = Math.max(bottom, y);
  }
}
