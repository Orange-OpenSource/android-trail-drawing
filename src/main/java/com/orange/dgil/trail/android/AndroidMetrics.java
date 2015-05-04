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

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@Getter
@RequiredArgsConstructor(suppressConstructorProperties = true)
public class AndroidMetrics {
  private static final int MICROMETERS_PER_INCH = 25400;

  private final float ydpi;
  private final float density;
  private final int   widthPixels;
  private final int   heightPixels;

  public int getMicrometersPerPixel() {
    return (int) (MICROMETERS_PER_INCH/ydpi);
  }

  public int pixelsToMillimeters(int pixels) {
    return pixelsToMicrometers(pixels) / 1000;
  }

  public int pixelsToMicrometers(int pixels) {
    return pixels * getMicrometersPerPixel();
  }

  public int micrometersToPixels(int micrometers) {
    return micrometers / getMicrometersPerPixel();
  }

  // not unit tested, android stuff
  public static AndroidMetrics get(Context ctx) {
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager  wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
    updateMetrics(wm.getDefaultDisplay(), metrics);
    return new AndroidMetrics(metrics.ydpi, metrics.density, metrics.widthPixels, metrics.heightPixels);
  }

  private static void updateMetrics(Display display, DisplayMetrics metrics) {
    try {
      doUpdateMetrics(display, metrics);
    } catch (NoSuchMethodException e) {
      showErrorAndSetDefaults(e, display, metrics);
    } catch (IllegalAccessException e) {
      showErrorAndSetDefaults(e, display, metrics);
    } catch (InvocationTargetException e) {
      showErrorAndSetDefaults(e, display, metrics);
    }
  }

  private static void doUpdateMetrics(Display display, DisplayMetrics metrics) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
      updateMetricsBeforeJBMR1(display, metrics);
    } else if (Build.VERSION.SDK_INT >= 17) {
      updateMetricsStartingWithJBMR1(display, metrics);
    } else {
      throw new NoSuchMethodException("The library does not support devices before android ICE CREAM SANDWICH (api 14)");
    }
  }

  private static void updateMetricsBeforeJBMR1(Display display, DisplayMetrics metrics) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // Mandatory for yDpi & density
    display.getMetrics(metrics);
    metrics.widthPixels = (int) Display.class.getMethod("getRawWidth").invoke(display);
    metrics.heightPixels = (int) Display.class.getMethod("getRawHeight").invoke(display);
  }

  private static void updateMetricsStartingWithJBMR1(Display display, DisplayMetrics metrics) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Display.class.getMethod("getRealMetrics", DisplayMetrics.class).invoke(display, metrics);
  }

  private static void showErrorAndSetDefaults(Exception e, Display display, DisplayMetrics metrics) {
    Log.e(AndroidMetrics.class.getName(), String.format("Failed to get metrics, %s, %s", e.getMessage(), e.getClass().getName()));
    display.getMetrics(metrics);
  }
}
