/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.fxdrawertest.fxcontroller;

import com.orange.dgil.trail.app.fxdrawertest.curves.Curves;
import com.orange.dgil.trail.app.fxdrawertest.drawer.GraphicContextDrawer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MouseController {

  private static final int VECTO_WINDOW_SIZE = 10;
  private static final int VECTO_HEIGHT_THRESHOLD = 6;

  @FXML
  VBox vboxLayout;
  @FXML
  private Canvas canvas;
  @FXML
  private CheckBox rawCheckBox;
  @FXML
  private CheckBox linearFilterCheckBox;
  @FXML
  private CheckBox vectoCheckBox;
  @FXML
  private CheckBox quadCheckBox;
  @FXML
  private Slider xOffsetSlider;
  @FXML
  private Slider yOffsetSlider;
  @FXML
  private TextField vectoErrorPixelsText;
  @FXML
  private TextField vectoWindowSizeText;

  private GraphicContextDrawer drawer;
  private final Curves curves = new Curves();


  @FXML
  void initialize() {
    initCanvas();
    initDrawer();
    initCurveSettings();
  }

  @FXML
  void onMousePressed(MouseEvent event) {
    clearStrokes();
    newPoint(event.getX(), event.getY());
  }

  @FXML
  void onMouseDragged(MouseEvent event) {
    newPoint(event.getX(), event.getY());
  }

  @FXML
  void onMouseReleased(MouseEvent event) {
    endPoint(event.getX(), event.getY());
  }


  private void initCanvas() {
    canvas.widthProperty().bind(vboxLayout.widthProperty());
    canvas.heightProperty().bind(vboxLayout.heightProperty());
  }


  private void newPoint(double x, double y) {
    curves.addPoint(x, y);
    drawer.draw(curves);
  }
  private void endPoint(double x, double y) {
    curves.addPoint(x, y);
    curves.end();
    drawer.draw(curves);
  }

  private void clearStrokes() {
    drawer.clear();
    curves.clear();
  }

  private void initCurveSettings() {
    initCurveSettingObserver(rawCheckBox);
    initCurveSettingObserver(linearFilterCheckBox);
    initCurveSettingObserver(vectoCheckBox);
    initCurveSettingObserver(quadCheckBox);
    initVectoErrorPixels();
    initVectoErrorPixelsTextObserver();
    initVectoWindowSize();
    initVectoWindowSizeTextObserver();
    updateCurvesSettings();
  }

  private void initCurveSettingObserver(CheckBox checkBox) {
    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
        updateCurvesSettings();
        drawer.draw(curves);
      }
    });
  }

  private void initVectoErrorPixels() {
    vectoErrorPixelsText.setText(String.valueOf(VECTO_HEIGHT_THRESHOLD));
    curves.setVectoErrorPixels(VECTO_HEIGHT_THRESHOLD);
  }
  private void initVectoErrorPixelsTextObserver() {
    vectoErrorPixelsText.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
        try {
          int error = Integer.parseInt(s2);
          curves.setVectoErrorPixels(error);
          System.out.println(String.format("Vecto Error = %d Pixels", error));
        } catch (NumberFormatException e) {
        }
      }
    });
  }

  private void initVectoWindowSize() {
    vectoWindowSizeText.setText(String.valueOf(VECTO_WINDOW_SIZE));
    curves.setVectoWindowSize(VECTO_WINDOW_SIZE);
  }
  private void initVectoWindowSizeTextObserver() {
    vectoWindowSizeText.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
        try {
          int windowSize = Integer.parseInt(s2);
          curves.setVectoWindowSize(windowSize);
          System.out.println(String.format("Vecto Window size = %d", windowSize));
        } catch (NumberFormatException e) {
        }
      }
    });
  }

  private void initDrawer() {
    drawer = new GraphicContextDrawer(canvas.getGraphicsContext2D());
    initOffsetObserver(xOffsetSlider);
    initOffsetObserver(yOffsetSlider);
    updateDrawerSettings();
  }

  private void initOffsetObserver(Slider offsetSlider) {
    offsetSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        updateDrawerSettings();
        drawer.draw(curves);
      }
    });
  }

  private void updateCurvesSettings() {
    drawer.clear();
    curves.setRawCurveEnabled(rawCheckBox.isSelected());
    curves.setLinearFilterCurveEnabled(linearFilterCheckBox.isSelected());
    curves.setVectoCurveEnabled(vectoCheckBox.isSelected());
    curves.setQuadCurveEnabled(quadCheckBox.isSelected());
  }

  private void updateDrawerSettings() {
    drawer.clear();
    drawer.setOffsets((int)xOffsetSlider.getValue(), (int)yOffsetSlider.getValue());
  }
}
