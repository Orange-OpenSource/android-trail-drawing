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

import android.view.View;

import com.orange.dgil.trail.android.AndroidMetrics;
import com.orange.dgil.trail.android.animation.AnimManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DrawingToolsContext {
  private final View view;
  private final AnimManager animManager;
  private final AndroidMetrics androidMetrics;
  private final TrailOptions trailOptions;
}
