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
package io.github.gleidsonmt.dashboard.global.controls.skin;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.ObservableValue;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/11/2021
 */
public class GNTextAreaSkinB extends TextAreaSkin {

    private final Timeline  animation   = new Timeline();
    private final Label     prompt      = new Label();

    private final Rectangle border = new Rectangle();

    private boolean up = false;

    private final StyleableObjectProperty<Boolean> floatPrompt;

    private static final PseudoClass FLOAT_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("float");

    private final BooleanProperty _float = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return this;
        }

        @Override public String getName() {
            return "float";
        }
    };

    ScrollPane scrollPane;
    Region region;

    public GNTextAreaSkinB(TextArea textArea) {

        super(textArea);

        floatPrompt = new SimpleStyleableObjectProperty<>(FLOAT_PROMPT, false);

        if (isFloat()) {
            addFloating(textArea);
        }

        floatPrompt.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addFloating(textArea);
            } else {
                removeFloating(textArea);
            }
        });

        border.setArcWidth(5);
        border.setArcHeight(5);

        getChildren().add(border);

        scrollPane = (ScrollPane) textArea.getChildrenUnmodifiable().get(0);
        region = (Region) scrollPane.getContent();
        region.setMouseTransparent(true);
//        getSkinnable().setPadding(Insets.EMPTY);

        border.setX(0);
        border.setY(0);

        border.widthProperty().bind(getSkinnable().widthProperty());
        border.heightProperty().bind(getSkinnable().heightProperty());

//        getSkinnable().setStyle("-fx-background-color : red;");
        border.toBack();

        border.getStyleClass().add("border");

        border.setStrokeType(StrokeType.OUTSIDE);
        border.setStrokeLineCap(StrokeLineCap.ROUND);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        border.setSmooth(false);
        border.setStrokeMiterLimit(10);

        border.setMouseTransparent(true);

    }

    private void addFloating(TextArea textArea) {

        textArea.setStyle("-fx-prompt-text-fill : transparent;");

        getChildren().add(prompt);
        prompt.getStyleClass().add("prompt-text");
        prompt.setMouseTransparent(true);
        prompt.setPadding(new Insets(2));
//        prompt.setText(textArea.getPromptText());
        prompt.setText("Text Field");

//        prompt.setStyle("-fx-text-fill : -prompt-fill;");

        getSkinnable().focusedProperty().addListener(this::changed);
    }

    private void removeFloating(TextArea textArea) {
        downPrompt();
        getChildren().remove(prompt);
        textArea.setStyle("-fx-prompt-text-fill : -prompt-fill;");
    }

    private void upPrompt() {
        up = true;

        double newY = (prompt.getHeight() / 2)
                + getSkinnable().getInsets().getTop()
                + getSkinnable().getInsets().getBottom()+2 ;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(),
                        prompt.getTranslateY(),
                        Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(),
                        newY * -1  ,
                        Interpolator.EASE_BOTH));

        animation.getKeyFrames().setAll(yIni, yEnd);

        animation.setOnFinished(event -> pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, true));
        animation.play();
    }

    private void downPrompt() {
        animation.getKeyFrames().clear();

        up = false;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), prompt.getTranslateY(), Interpolator.EASE_BOTH));
        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), 0 , Interpolator.EASE_BOTH));

        animation.getKeyFrames().setAll(yIni, yEnd);

        animation.setOnFinished(event -> pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, false));
        animation.play();
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 50;
    }
    

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        layoutInArea(prompt,
                x + prompt.getPadding().getLeft() ,
                y + prompt.getPadding().getTop()  ,
                getSkinnable().getWidth(),
                prompt.getHeight(),
                0,
                HPos.LEFT,
                VPos.TOP);


//        System.out.println(getSkinnable().getInsets());

        layoutInArea(border,x - getSkinnable().getInsets().getLeft(),y
                        -10,
                getSkinnable().getWidth() ,

                getSkinnable().getHeight(),
                0,
                HPos.LEFT, VPos.CENTER);


    }


    private void changed(ObservableValue<? extends Boolean> observable,
                         Boolean oldValue, Boolean newValue) {

//        getSkinnable().requestLayout();

        if (newValue) {

            if (up) return;

            if (animation.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            upPrompt();

        } else {

            if (animation.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            if (getSkinnable().getText() != null) {
                if(!getSkinnable().getText().isEmpty())
                    return;
            }

            downPrompt();
        }
    }

    private static final CssMetaData<TextArea, Boolean> FLOAT_PROMPT =
            new CssMetaData<TextArea, Boolean>(
                    "-gn-float-prompt",
                    StyleConverter.getBooleanConverter(), false

            ) {
                @Override
                public boolean isSettable(TextArea styleable) {
                    return ((GNTextAreaSkinB) styleable.getSkin()).floatPrompt == null ||
                            !((GNTextAreaSkinB) styleable.getSkin()).floatPrompt.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(TextArea styleable) {
                    if (styleable.getSkin() instanceof GNTextAreaSkinB) {
                        return ((GNTextAreaSkinB) styleable.getSkin()).floatPromptProperty();
                    } else return null;

                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(TextAreaSkin.getClassCssMetaData());
        styleables.add(FLOAT_PROMPT);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    @Contract(pure = true)
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public boolean isFloat() {
        return _float.get();
    }

    public BooleanProperty floatProperty() {
        return _float;
    }

    public void setFloat(boolean _float) {
        this._float.set(_float);
    }

    public Boolean getFloatPrompt() {
        return floatPrompt.get();
    }

    public StyleableObjectProperty<Boolean> floatPromptProperty() {
        return floatPrompt;
    }

    public void setFloatPrompt(Boolean floatPrompt) {
        this.floatPrompt.set(floatPrompt);
    }
}
