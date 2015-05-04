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

public abstract class TrailException extends RuntimeException {

  public TrailException(String message) {
    super(message);
    trace(message);
  }

  private void trace(String message) {
    System.out.println(String.format("Trail exception: '%s'", message));
  }
}
