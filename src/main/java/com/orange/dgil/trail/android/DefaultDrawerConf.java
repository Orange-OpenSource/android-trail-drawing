/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android;

import android.graphics.Color;

public class DefaultDrawerConf {
  public static final int TRAIL_COLOR = Color.rgb(0xFF, 0x99, 0);
  public static final int TRAIL_WIDTH_UM = 2500;

  public static final float SHADOW_RADIUS_FACTOR = 0.5f;
  public static final float SHADOW_OFFSET_FACTOR = 0.33f;
  public static final int SHADOW_COLOR = Color.BLACK;

  public static final int ANIM_PRE_ANIM_DELAY_MS = 100;
  public static final int ANIM_DURATION_MS = 400;

  public static final int QUILL_WIDTH_UM = 3000;
  public static final int QUILL_HEIGHT_UM = 1000;
  public static final int QUILL_ANGLE_DEG = 45;
}
