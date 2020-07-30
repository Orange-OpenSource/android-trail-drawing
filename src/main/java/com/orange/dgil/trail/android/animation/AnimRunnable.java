/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.android.animation;

import android.view.View;
import android.view.animation.AnimationUtils;

import lombok.Getter;

class AnimRunnable implements Runnable {

  private final IAnimDrawer drawer;
  private final AnimParameters animParameters;

  @Getter
  private boolean running;
  @Getter
  private float factor;

  private long startTime;


  AnimRunnable(IAnimDrawer drawer, AnimParameters animParameters) {
    this.drawer = drawer;
    this.animParameters = animParameters;
  }

  void start() {
    reset();
    setStartTime(animParameters.getPreAnimDelay());
    running = true;
    drawer.invalidatePath();
    updateAfter(animParameters.getPreAnimDelay());
  }

  void updateAfter(int delayMillis) {
    View v = drawer.getView();
    if (delayMillis == 0) {
      v.post(this);
    } else {
      v.postDelayed(this, delayMillis);
    }
  }

  void reset() {
    factor   = 1f;
    running = false;
    drawer.getView().removeCallbacks(this);
  }

  void setStartTime(int preAnimDelay) {
    startTime = getCurrentTime() + preAnimDelay;
  }

  long getRemainingDuration() {
    long remainingDuration = startTime + animParameters.getAnimDuration() - getCurrentTime();
    return Math.max(remainingDuration, 0);
  }

  // not tested, android stuff
  private long getCurrentTime() {
    return AnimationUtils.currentAnimationTimeMillis();
  }


  @Override
  public void run() {
    if (running) {
      doRun();
    }
    drawer.invalidatePath();
  }

  void doRun() {
    long remainingDuration = getRemainingDuration();
    if (remainingDuration > 0) {
      update(remainingDuration);
    } else {
      end();
    }
  }

  void end() {
    reset();
    drawer.animationFinished();
  }

  void update(long remainingDuration) {
    factor = getTimeFactor(remainingDuration);
    updateAfter(0);
  }

  float getTimeFactor(long remainingDuration) {
    return remainingDuration / (float) animParameters.getAnimDuration();
  }
}