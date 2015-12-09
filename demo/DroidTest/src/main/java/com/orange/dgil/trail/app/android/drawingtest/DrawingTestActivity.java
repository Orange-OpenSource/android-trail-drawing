/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.android.drawingtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.orange.dgil.trail.app.android.drawing.R;
import com.orange.dgil.trail.app.android.drawingtest.view.DrawingArea;

public class DrawingTestActivity extends Activity {

  private DrawingArea drawingArea;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  @Override
  public void onPause() {
    super.onPause();
    drawingArea.trimMemory();
  }

  @Override
  public void onResume() {
    super.onResume();
    initDrawingArea();
  }

  private void initDrawingArea() {
    if (drawingArea == null) {
      drawingArea = (DrawingArea) findViewById(R.id.drawing_area);
      drawingArea.initTrailDrawer();
    }
  }

  /** on click, see layout.xml */
  public void onQuillSelected(View view) {
    drawingArea.onQuillSelected();
  }

  /** on click, see layout.xml */
  public void onMarkerSelected(View view) {
    drawingArea.onMarkerSelected();
  }

  /** on click, see layout.xml */
  public void onClearSelected(View view) {
    drawingArea.onClearSelected();
  }
}
