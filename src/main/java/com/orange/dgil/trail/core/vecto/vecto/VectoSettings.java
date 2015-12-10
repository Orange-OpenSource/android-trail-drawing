/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto.vecto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class VectoSettings {
  private static final int DEFAULT_VECTO_ERROR_HEIGHT_PIXELS = 1;

  private final WindowAnalysis windowAnalysis;

  @Getter
  @Setter
  private int vectorsHeightThreshold = DEFAULT_VECTO_ERROR_HEIGHT_PIXELS;

  public void setWindowSize(int windowSize) {
    windowAnalysis.setWindowSize(windowSize);
  }
}
