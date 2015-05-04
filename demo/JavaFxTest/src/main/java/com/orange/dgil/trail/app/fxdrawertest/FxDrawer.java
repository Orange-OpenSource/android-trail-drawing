/**
 * Trail drawing library
 * Copyright (C) 2014 Orange
 * Authors: christophe.maldivi@orange.com, eric.petit@orange.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.orange.dgil.trail.app.fxdrawertest;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxDrawer extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {

    Canvas canvas = new Canvas(400, 400);
    final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    initDraw(graphicsContext);

    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
        new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent event) {
            graphicsContext.beginPath();
            graphicsContext.moveTo(event.getX(), event.getY());
            graphicsContext.stroke();
          }
        }
    );

    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
        new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent event) {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
          }
        }
    );

    Parent root = FXMLLoader.load(getClass().getResource("/com/orange/dgil/trail/app/fxdrawertest/fxml/FxDrawer.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setTitle(getClass().getSimpleName());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void initDraw(GraphicsContext gc) {
    double canvasWidth = gc.getCanvas().getWidth();
    double canvasHeight = gc.getCanvas().getHeight();

    gc.setFill(Color.LIGHTGRAY);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);

    gc.fill();
    gc.strokeRect(
        0,              //x of the upper left corner
        0,              //y of the upper left corner
        canvasWidth,    //width of the rectangle
        canvasHeight);  //height of the rectangle

    gc.setFill(Color.RED);
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(1);
  }
}