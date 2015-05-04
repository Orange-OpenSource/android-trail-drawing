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

import org.junit.Test;

public class LinearWindowFilterCtorTest implements LinearWindowFilterListener {

  @Test
  public void shouldInstantiateWithoutCrash() {
    // given
    // do
    LinearWindowFilter f = new LinearWindowFilter(this);
    // then
    // no crash
  }

  @Override
  public void onNewFilteredPointAvailable(int x, int y) {

  }
}
