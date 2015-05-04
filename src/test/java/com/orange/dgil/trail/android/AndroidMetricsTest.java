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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Tests the methods of {@link AndroidMetrics}. */
public final class AndroidMetricsTest {

  private static final float YDPI = 254f;
  private static final float DENSITY = 1f;
  private static final int W = 480;
  private static final int H = 800;

  private AndroidMetrics metrics;

  @Before
  public void setUp() {
    metrics = new AndroidMetrics(YDPI, DENSITY, W, H);
  }

  @Test
  public void shouldGetDensity() {
    Assert.assertEquals(DENSITY, metrics.getDensity(), 0);
  }

  @Test
  public void shouldGetWidth() {
    Assert.assertEquals(W, metrics.getWidthPixels());
  }

  @Test
  public void shouldGetHeight() {
    Assert.assertEquals(H, metrics.getHeightPixels());
  }

  @Test
  public void shouldGetMicrometersPerPixel() {
    Assert.assertEquals(100, metrics.getMicrometersPerPixel());
  }

  @Test
  public void shouldConvertPixelsToMicrometers() {
    Assert.assertEquals(100, metrics.pixelsToMicrometers(1));
  }

  @Test
  public void shouldConvertPixelsToMillimeters() {
    Assert.assertEquals(1, metrics.pixelsToMillimeters(10));
  }

  @Test
  public void shouldConvertMicrometersToPixels() {
    Assert.assertEquals(1, metrics.micrometersToPixels(100));
  }
}
