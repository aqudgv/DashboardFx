/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidsonmt.dashboard.global.controls.button;

import io.github.gleidsonmt.dashboard.global.controls.GNButtonSKin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/12/2018
 */
public class CentralizeSkin extends GNButtonSKin {

    private Paint firstColor;

    private final StackPane rect =  new StackPane();

    public CentralizeSkin(Button control) {
        super(control);

        rect.setShape(null);

        rect.setPrefWidth(0);
        rect.setMaxWidth(0);

        rect.setPrefHeight(Region.USE_COMPUTED_SIZE);
        rect.setMaxHeight(Region.USE_COMPUTED_SIZE);

        getChildren().add(rect);

        rect.setStyle("-fx-background-color : blue;");

        rect.toBack();

        Rectangle clip = new Rectangle();
        clip.setArcWidth(0);
        clip.setArcHeight(0);
        getSkinnable().setClip(clip);

        clip.widthProperty().bind(getSkinnable().widthProperty());
        clip.heightProperty().bind(getSkinnable().heightProperty());


        Timeline timeEntered = new Timeline();
        Timeline timeExited = new Timeline();

        firstColor = getSkinnable().getTextFill();

        getSkinnable().textFillProperty().addListener((observable, oldValue, newValue) -> {
            if(timeEntered.getStatus() == Animation.Status.STOPPED && timeExited.getStatus() == Animation.Status.STOPPED ) {
                firstColor = newValue;
            }
        });


        setAnimated(true);

        control.setOnMouseEntered(event -> {
            timeEntered.getKeyFrames().clear();
            setAnimated(true);

            timeEntered.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(rect.prefWidthProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(rect.maxWidthProperty(), 0)),

                    new KeyFrame(getVelocity(), new KeyValue(rect.maxWidthProperty(), getSkinnable().getWidth())),
                    new KeyFrame(getVelocity(), new KeyValue(rect.prefWidthProperty(), getSkinnable().getWidth()))

            );

            if(timeEntered.getStatus() == Animation.Status.RUNNING){
                timeEntered.playFromStart();
                return;
            }

            timeEntered.play();

        });

        control.setOnMouseExited(event -> {

            timeExited.getKeyFrames().clear();
             setAnimated(true);

            timeExited.getKeyFrames().addAll(

                    new KeyFrame(Duration.ZERO, new KeyValue(rect.prefWidthProperty(), getSkinnable().getWidth())),
                    new KeyFrame(Duration.ZERO, new KeyValue(rect.maxWidthProperty(), getSkinnable().getWidth())),

                    new KeyFrame(getVelocity(), new KeyValue(rect.maxWidthProperty(), 0)),
                    new KeyFrame(getVelocity(), new KeyValue(rect.prefWidthProperty(), 0))

            );

            if(timeExited.getStatus() == Animation.Status.RUNNING){
                timeExited.playFromStart();
                return;
            }
            timeExited.play();
        });

        transitionColorProperty().addListener((observable, oldValue, newValue) -> {
            rect.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }


    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        layoutInArea(rect, x, y, w, h, 0,
                HPos.CENTER, VPos.CENTER);
    }
}