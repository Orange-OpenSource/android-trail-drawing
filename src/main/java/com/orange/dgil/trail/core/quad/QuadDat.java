/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.quad;

import com.orange.dgil.trail.core.common.TrailPoint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class QuadDat {
  private final TrailPoint interpStartPoint = new TrailPoint();
  private final TrailPoint interpEndPoint = new TrailPoint();
  private final TrailPoint middlePoint = new TrailPoint();

  private long coefA;
  private long coefB;
  private long coefC;
  private long coefD;
  private long coefE;
  private long coefF;

  private int interpNbPoints;

  private boolean curveStart;
  private boolean curveEnd;

  private TrailPoint point0;
  private TrailPoint point1;
  private TrailPoint point2;


  void reset() {
    curveStart = true;
    curveEnd = false;
  }
}
