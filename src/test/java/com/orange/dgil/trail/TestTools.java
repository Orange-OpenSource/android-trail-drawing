/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail;

import org.powermock.api.mockito.PowerMockito;

public class TestTools {

  public static Object getObj(String fieldName, Class clazz, Object instance) throws IllegalArgumentException, IllegalAccessException {
    return PowerMockito.field(clazz, fieldName).get(instance);
  }

  public static void setObj(String fieldName, Class clazz, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
    PowerMockito.field(clazz, fieldName).set(instance, value);
  }
}
