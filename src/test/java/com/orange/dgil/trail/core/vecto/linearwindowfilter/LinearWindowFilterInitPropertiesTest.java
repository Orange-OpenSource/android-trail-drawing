/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.core.vecto.linearwindowfilter;

import com.orange.dgil.trail.TestTools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LinearWindowFilterInitPropertiesTest {

  private LinearWindowFilter filter;

  @Before
  public void setUp() {
    filter = Mockito.mock(LinearWindowFilter.class);
  }

  @Test
  public void shouldInitProperties() throws IllegalAccessException {
    // given
    int[] windowWeights = {1, 2, 1};
    int weightsSum = 4;
    TestTools.setObj("windowWeights", LinearWindowFilter.class, filter, windowWeights);
    Mockito.when(filter.getWeightsSum()).thenReturn(weightsSum);
    Mockito.doCallRealMethod().when(filter).initProperties();
    // do
    filter.initProperties();
    // then
    Assert.assertEquals(weightsSum, TestTools.getObj("weightsSum", LinearWindowFilter.class, filter));
  }
}
