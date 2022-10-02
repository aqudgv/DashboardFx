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

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/12/2018
 */
public class SwipeSkin extends ButtonSkin {

    private Paint firstColor;

    private final StackPane rect = new StackPane();

    private final ObjectProperty<Duration> velocity =
            new SimpleObjectProperty<>(this, "velocity",
            Duration.millis(200)
    );

    private static final PseudoClass ANIMATED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("animated");

    private final BooleanProperty animated = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return SwipeSkin.this;
        }

        @Override public String getName() {
            return "animated";
        }
    };

    private StyleableObjectProperty<Paint> transitionColor ;

    private ObservableList<Node> customs = FXCollections.observableArrayList();

    public SwipeSkin(Button control) {

        super(control);

        customs.add(rect);

        rect.setShape(null);

        rect.setPrefWidth(0);
        rect.setMaxWidth(0);

        rect.setPrefHeight(Region.USE_COMPUTED_SIZE);
        rect.setMaxHeight(Region.USE_COMPUTED_SIZE);

//        getChildren().clear();

        getChildren().add(rect);
//        getChildren().add(title);
        Text title;
        if (getSkinnable().getChildrenUnmodifiable().get(1) instanceof Text)
            title = (Text) getSkinnable().getChildrenUnmodifiable().get(1);
        else title = (Text) getSkinnable().getChildrenUnmodifiable().get(0);
        rect.toBack();


        Timeline timeEntered = new Timeline();
        Timeline timeExited = new Timeline();

        firstColor = getSkinnable().getTextFill();

        getSkinnable().textFillProperty().addListener((observable, oldValue, newValue) -> {
            if(timeEntered.getStatus() == Animation.Status.STOPPED && timeExited.getStatus() == Animation.Status.STOPPED ) {
                firstColor = newValue;
            }
        });

        rect.borderProperty().bind(getSkinnable().borderProperty());

        pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, animated.get());

        getSkinnable().setOnMouseEntered(event -> {
            timeEntered.getKeyFrames().clear();

            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, true);

            if (getChildren().contains(rect)) {

                timeEntered.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(rect.prefWidthProperty(), rect.getPrefWidth())),
                        new KeyFrame(Duration.ZERO, new KeyValue(rect.maxWidthProperty(), rect.getPrefWidth())),
//                    new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill())),

                        new KeyFrame(velocity.get(), new KeyValue(rect.prefWidthProperty(), getSkinnable().getWidth())),
                        new KeyFrame(velocity.get(), new KeyValue(rect.maxWidthProperty(), getSkinnable().getWidth()))
//                    new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), ((GNButton) getSkinnable()).getTransitionText()))
//
                );

                if (timeExited.getStatus() == Animation.Status.RUNNING) {
                    timeExited.stop();
                }

                timeEntered.play();
            }

        });

        getSkinnable().setOnMouseExited(event -> {
                timeExited.getKeyFrames().clear();
                pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, false);
                timeExited.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(rect.prefWidthProperty(), rect.getPrefWidth())),
                        new KeyFrame(Duration.ZERO, new KeyValue(rect.maxWidthProperty(), rect.getPrefWidth())),
//                        new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill())),

                        new KeyFrame(velocity.get(), new KeyValue(rect.prefWidthProperty(), 0D)),
                        new KeyFrame(velocity.get(), new KeyValue(rect.maxWidthProperty(), 0D))

//                        new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), firstColor))

                );

                if (timeEntered.getStatus() == Animation.Status.RUNNING) {
                    timeEntered.stop();
                }

                timeExited.play();
            });


        this.transitionColor = new SimpleStyleableObjectProperty<Paint>(TRANSITION_COLOR, this, "transitionColor");

        transitionColorProperty().addListener((observable, oldValue, newValue) -> {
            rect.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
//        layoutInArea(
//                rect,
//                contentX - (snappedLeftInset() + rect.getBorder().getInsets().getRight()),
//                contentY - snappedTopInset() ,
//                contentWidth + (snappedRightInset() + snappedLeftInset()),
//                contentHeight + (snappedBottomInset() + snappedTopInset()) + bottomLabelPadding(),
//                0,
//                HPos.LEFT,  VPos.CENTER);

    }

    private static final CssMetaData<Button, Paint> TRANSITION_COLOR =
            new CssMetaData<Button, Paint>("-gn-transition-color", PaintConverter.getInstance(), Color.RED) {
                @Override
                public boolean isSettable(Button styleable) {
                    if (styleable.getSkin() instanceof SwipeSkin)
                        return ( (SwipeSkin) styleable.getSkin()).transitionColor == null ||
                                !( (SwipeSkin) styleable.getSkin()).transitionColor.isBound();
                    else return false;
                }

                @Override
                public StyleableProperty<Paint> getStyleableProperty(Button styleable) {
                    if (styleable.getSkin() instanceof SwipeSkin) {
                        return ( (SwipeSkin) styleable.getSkin() ).transitionColorProperty();
                    } else return null;
                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static  {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(SkinBase.getClassCssMetaData());
        styleables.add(TRANSITION_COLOR);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public Paint getTransitionColor() {
        return transitionColor.get();
    }

    public StyleableObjectProperty<Paint> transitionColorProperty() {
        return transitionColor;
    }

    public void setTransitionColor(Paint transitionColor) {
        this.transitionColor.set(transitionColor);
    }

    @Override
    public void dispose() {
        super.dispose();

//        getSkinnable().setOnMouseEntered(null);
//        getSkinnable().setOnMouseExited(null);

//        getChildren().removeAll(customs);
    }
}