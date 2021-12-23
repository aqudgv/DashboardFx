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
package io.github.gleidson28.global.controls.skin;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import io.github.gleidson28.global.controls.control.GNTextField;
import io.github.gleidson28.global.controls.textField.FieldFooter;
import io.github.gleidson28.global.controls.types.FieldType;
import io.github.gleidson28.global.material.icon.IconContainer;
import io.github.gleidson28.global.material.icon.Icons;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class GNTextFieldSkinT extends TextFieldSkin {

    private GNTextField textField;

    private final Label prompt = new Label();
    private final Timeline animation = new Timeline();
    private final Rectangle border = new Rectangle();
    private final Button leadButton = new Button();
    private final Button actionButton = new Button();

    private final FieldFooter fieldFooter = new FieldFooter();

    private boolean up = false;
    private Pane pane = null;

    private static final PseudoClass FLOAT_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("float");

    public GNTextFieldSkinT(GNTextField textField) {
        super(textField);

        this.textField = textField;

        config();

        if (textField.getText() != null) {
            if (!textField.getText().isEmpty()) {
                Platform.runLater(this::upPrompt);
            }
        }

        if (textField.isFloatPrompt()) {
            if (textField.getText() != null) {
                if (textField.getText().isEmpty()) {
                    addFloating();
                }
            }
        } else removeFloating();


        updateLeadIcon();

        hidePrompt();

        textField.setPadding(new Insets(10, 10,10,10));

        fieldFooter.visibleCountProperty().bind(textField.visibleCountProperty());
        fieldFooter.maxCountProperty().bind(textField.maxLengthProperty());
        fieldFooter.messageProperty().bind(textField.messageProperty());

        registerChangeListener(textField.floatPromptProperty(), "FLOAT_PROMPT");
        registerChangeListener(textField.fieldTypeProperty(), "BUTTON_TYPE");
        registerChangeListener(textField.leadIconTypeProperty(), "LEAD_ICON_TYPE");
        registerChangeListener(textField.actionTypeProperty(), "ACTION_TYPE");

        registerChangeListener(textField.maxLengthProperty(), "MAX_LENGTH");
        registerChangeListener(textField.visibleCountProperty(), "VISIBLE_COUNT");

        registerChangeListener(textField.visibleMessageProperty(), "VISIBLE_MESSAGE");

    }

    private void updateLeadIcon() {

        if (textField.getLeadIconType() == null
            || textField.getLeadIconType().equals(Icons.NONE)
            || textField.getLeadIconType().equals(Icons.NULL)) {

            getChildren().removeAll(leadButton);

            Platform.runLater(() -> {
                if (textField.isFloatPrompt()) {
                    if (textField.getLength() > 0) {
                        upPrompt();
                        prompt.setTranslateX(-5);
                    }
                    else downPrompt();
                }
            });
        } else {
            IconContainer iconContainer = new IconContainer(textField.getLeadIconType());
            iconContainer.getStyleClass().add("lead-icon");
            leadButton.setGraphic(iconContainer);
            if (!getChildren().contains(leadButton))
                getChildren().add(leadButton);

            Platform.runLater(() -> {
                if (textField.isFloatPrompt()) {
                    if (textField.getLength() > 0) {
                        upPrompt();
                        prompt.setTranslateX( (leadButton.getWidth() * -1) -5);
                    }
                    else downPrompt();
                }
            });

        }
    }

    private void updateActionType() {
        if (textField.getActionType() != null) {
            if (textField.getActionType() == Icons.CLEAR) {
                if (!getChildren().contains(actionButton)) {
                    getChildren().add(actionButton);
                    actionButton.setVisible(false);
                    actionButton.setOnMouseClicked(clearAction);
                    IconContainer iconContainer = new IconContainer(Icons.CLEAR);
                    iconContainer.getStyleClass().add("action-icon");
                    actionButton.setGraphic(iconContainer);
                    textField.textProperty().addListener(clearListener);
                    textField.focusedProperty().addListener(clearFocused);
                }
            }
        } else {
            actionButton.setVisible(true);
            getChildren().remove(actionButton);
            actionButton.setOnMouseClicked(null);
            textField.textProperty().removeListener(clearListener);
            textField.focusedProperty().removeListener(clearFocused);
        }
    }

    private final EventHandler<MouseEvent> clearAction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            textField.clear();
            actionButton.setVisible(false);
        }
    };

    private final ChangeListener<String> clearListener = (observable, oldValue, newValue) -> {
        if (newValue != null) {
            actionButton.setVisible(!newValue.isEmpty());
        } else actionButton.setVisible(false);
    };

    private final ChangeListener<Boolean> clearFocused = (observable, oldValue, newValue) -> {
        if (newValue) {
            actionButton.setVisible(textField.getLength() > 0);
        } else {
            actionButton.setVisible(false);
        }
    };

    @Override
    protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);

        if ("FLOAT_PROMPT".equals(propertyReference)) {
            if (textField.isFloatPrompt()) {
                addFloating();
            }  else {
                removeFloating();
            }
        } else if ("MAX_LENGTH".equals(propertyReference)) {
            if (textField.getMaxLength() != null) {
                if (textField.getMaxLength().intValue() != -1) {
                    updateCountSize(textField.getLength());
                }
            }
        } else if ("VISIBLE_COUNT".equals(propertyReference)) {

            if (textField.isVisibleCount()) addFooter(true);
            fieldFooter.updateSize(textField.getLength(),
            textField.getMaxLength().intValue());

        } else if ("VISIBLE_MESSAGE".equals(propertyReference)) {

            if (textField.isVisibleMessage()) addFooter(true);
            fieldFooter.setErrorVisible(textField.isVisibleMessage());

        } else if ("LEAD_ICON_TYPE".equals(propertyReference)) {
            updateLeadIcon();
        } else if ("ACTION_TYPE".equals(propertyReference)) {
            updateActionType();
        }

    }

    private final EventHandler<KeyEvent> maxEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (textField.getLength() >= textField.getMaxLength().intValue()) {
                if (textField.getMaxLength().intValue() != -1)
                event.consume();
            }
        }
    };

    private void updateCountSize(int length) {

        String max = String.valueOf(textField.getMaxLength().intValue());
        String size = String.valueOf(length);

        if(max.equals("0") || textField.getMaxLength().intValue() == -1D) {
            max = "..";
            fieldFooter.getCount().setText(size + "/" + max);

        } else {
            fieldFooter.getCount().setText(size + "/" + max);

        }
        textField.addEventFilter(KeyEvent.KEY_TYPED, maxEvent);
    }

    @Override
    public void dispose() {
        getChildren().remove(leadButton);
        getChildren().remove(fieldFooter);
        getChildren().remove(fieldFooter);
    }

    private void config() {

        prompt.setText(textField.getPromptText());
        prompt.fontProperty().bind(textField.fontProperty());
        prompt.getStyleClass().add("prompt-text");

        pane = (Pane) getChildren().get(0);


        pane.getStyleClass().add("content");
        getChildren().add(prompt);
        prompt.setPadding(new Insets(0,2,0,2));
        prompt.setText(prompt.getText());
        prompt.setMouseTransparent(true);

        getChildren().add(border);

        prompt.toFront();
        border.setArcWidth(5);
        border.setArcHeight(5);

        border.widthProperty().bind(textField.widthProperty());
        border.heightProperty().bind(textField.heightProperty());

        border.getStyleClass().add("border");

        border.setStrokeType(StrokeType.OUTSIDE);
        border.setStrokeLineCap(StrokeLineCap.ROUND);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        border.setSmooth(false);
        border.setStrokeMiterLimit(10);
        
        leadButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        actionButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        leadButton.setMouseTransparent(true);

        leadButton.getStyleClass().addAll("lead-button", "btn-flat", "flat");
        actionButton.getStyleClass().addAll("action-button", "btn-flat", "flat");

        leadButton.setPadding(new Insets(0,3,0,3));
        actionButton.setPadding(new Insets(0,3,0,3));

        leadButton.setFocusTraversable(false);
        actionButton.setFocusTraversable(false);

        textField.focusedProperty().addListener(focusedListener);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateCountSize(textField.getLength());
                if (!newValue.isEmpty()) {
                    if (textField.isFloatPrompt()) {
                        upPrompt();
                    } else downPrompt();
                }
            } else downPrompt();
        });

        fieldFooter.setFocusTraversable(false);
        fieldFooter.setMouseTransparent(true);
        fieldFooter.setCursor(Cursor.DEFAULT);

        addFooter( textField.isVisibleMessage()
                || textField.isVisibleCount()
        );
    }

    private void addFooter(boolean value) {
        if (value) {
            if (!getChildren().contains(fieldFooter)) {
                getChildren().add(fieldFooter);
                fieldFooter.toBack();
            }
        } else getChildren().remove(fieldFooter);
    }

    ChangeListener<Boolean> focusedListener = (observable, oldValue, newValue) -> {
        if (newValue) { // focused
            if (!up) {
                upPrompt();
            }
        } else { // no focus

            if (textField.getText() != null) {
                if (textField.getText().isEmpty()) {
                    downPrompt();
                }
            } else downPrompt();
        }
    };

    private void addFloating() {
        hidePrompt();

        if (!getChildren().contains(prompt)) getChildren().add(prompt);

        if (textField.getText() == null) return;
        if (textField.getText().isEmpty()) return;

        upPrompt();
    }

    private void removeFloating() {
        hidePrompt();
        getChildren().remove(prompt);
    }

    private void hidePrompt( ) {
        if (textField.isFloatPrompt())
            textField.setStyle("-fx-prompt-text-fill : transparent;");
        else {
            textField.setStyle("-fx-prompt-text-fill : -prompt-fill;");
        }
    }



    public void upPrompt() {

        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (up) return;

        double translateY = 0;

        if (textField.getFieldType().equals(FieldType.FILLED)) {

            translateY = -prompt.getFont().getSize()-4;
        } else {
            translateY = - (getSkinnable().getHeight()/2);
        }

        up = true;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), translateY -2, Interpolator.EASE_BOTH));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 1));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 0.9));

        KeyFrame xInitNoLEad = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), prompt.getTranslateX()));

        KeyFrame xEndNoLEad = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), 0 ));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(),
                        (leadButton.getWidth() *-1) -5));

        if (textField.getLeadIconType() == null
            || textField.getLeadIconType().equals(Icons.NONE)
            || textField.getLeadIconType().equals(Icons.NULL) ) {

            animation.getKeyFrames().setAll(
                    yIni, yEnd, xsInit, xsEnd,
                    ysInit, ysEnd, xInitNoLEad, xEndNoLEad);

        } else {
            animation.getKeyFrames().setAll(
                    yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd,
                    xInitLead, xEndLead);
        }

        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, true);

        animation.play();

    }

    private void downPrompt() {
        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (!up) return;

        up = false;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), prompt.getTranslateY(), Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 0.9));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 1));

        KeyFrame xInitNoLEad = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), prompt.getTranslateX()));

        KeyFrame xEndNoLEad = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), 0));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(),  prompt.getTranslateX()));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));


        if (textField.getLeadIconType() == null){
            animation.getKeyFrames().setAll(
                    yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd, xInitNoLEad, xEndNoLEad);
        } else {
            animation.getKeyFrames().setAll(
                    yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd, xInitLead, xEndLead);
        }

        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, false);
        animation.play();
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 50;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return textField.getMinHeight() + fieldFooter.getTextFlow().getHeight();
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {

        super.layoutChildren(x, y, w, h);

        if (getChildren().contains(leadButton) && getChildren().contains(actionButton)) {

            layoutInArea(actionButton, x, y, w , h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(leadButton, x - 5, y, w, h, 0, HPos.LEFT, VPos.CENTER);

            layoutInArea(pane,
                    x + leadButton.getWidth(), y,
                    w - ( actionButton.getWidth() +2), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x + (leadButton.getWidth()), y,
                    prompt.getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if (getChildren().contains(actionButton)) {
            layoutInArea(actionButton, x, y, w , h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane,
                    x , y,
                    w - (actionButton.getWidth() +2), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt, 10, y,
                    prompt.getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if (getChildren().contains(leadButton)) {
            layoutInArea(leadButton, x - 5, y, w, h, 0, HPos.LEFT, VPos.CENTER);

            layoutInArea(pane,
                    x + (leadButton.getWidth()), y,
                    w - (leadButton.getWidth() + getSkinnable().getInsets().getLeft()), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x + (leadButton.getWidth()), y,
                    prompt.getWidth(), h, 0, HPos.LEFT, VPos.CENTER);
        } else {
            layoutInArea(prompt, 10, y,
                    prompt.getWidth(), h, 0, HPos.LEFT, VPos.CENTER);
        }

        double _h = 0;
        if (fieldFooter.getTextFlow().getHeight() <= 0) {
            _h = getSkinnable().getHeight();
        } else _h = fieldFooter.getTextFlow().getHeight();

        layoutInArea(fieldFooter,
                x, h + fieldFooter.getHeight() ,
                getSkinnable().getWidth() - getSkinnable().getInsets().getRight(),
                _h, 0,
                HPos.LEFT, VPos.BOTTOM);


        updateCaretOff();
    }
}
