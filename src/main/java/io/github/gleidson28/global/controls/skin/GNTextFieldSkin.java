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
import io.github.gleidson28.global.material.icon.IconContainer;
import io.github.gleidson28.global.material.icon.Icons;
import io.github.gleidson28.global.controls.control.GNTextField;
import io.github.gleidson28.global.controls.textField.FieldFooter;
import javafx.animation.*;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
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
public class GNTextFieldSkin extends TextFieldSkin {

    private final IconContainer leadIcon        = new IconContainer();
    private final IconContainer actionIcon      = new IconContainer();
    private final Button        actionButton    = new Button();
    private final Button        leadButton      = new Button();

    private  FieldFooter   fieldFooter = null;
    private  Pane          pane = null;

    private final Label prompt = new Label();
    private final Timeline animation = new Timeline();

//    private final StyleableObjectProperty<ActionButtonType> actionButtonType;
//    private final StyleableObjectProperty<Icons>            leadIconType;
//    private final StyleableObjectProperty<Number>           maxLength;
////    private final StyleableObjectProperty<Boolean>          floatPrompt;
//    private final StyleableObjectProperty<Boolean>          errorTextVisible;

    private final Rectangle clip = new Rectangle();

    private final int   btnSize = 25;
    private boolean     up      = false;

    private static final PseudoClass FLOAT_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("float");

    private GNTextField textField;

    @Override
    protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);

        System.out.println("float");



    }

    public GNTextFieldSkin(PasswordField passwordField) {
        super(passwordField);
//        floatPrompt = new SimpleStyleableObjectProperty<>(FLOAT_PROMPT, false);
//        errorTextVisible = new SimpleStyleableObjectProperty<>(ERROR_TEXT_VISIBLE, false);
//
//        this.actionButtonType = new SimpleStyleableObjectProperty<>(
//                ACTION_BUTTON_TYPE, ActionButtonType.CLEAR);
//        this.maxLength = new SimpleStyleableObjectProperty<>(
//                MAX_LENGTH, -1);
//        this.leadIconType = new SimpleStyleableObjectProperty<>(
//                LEAD_ICON_TYPE);
    }

    public GNTextFieldSkin(GNTextField textField) {
        super(textField);

        this.textField = textField;


//        floatPrompt = new SimpleStyleableObjectProperty<>(FLOAT_PROMPT, false);
//
//        textField.floatPromptProperty().bindBidirectional(floatPrompt);
//        floatPrompt.bindBidirectional(textField.floatPromptProperty());

//        errorTextVisible = new SimpleStyleableObjectProperty<>(ERROR_TEXT_VISIBLE, false);

        prompt.setText(textField.getPromptText());
        prompt.fontProperty().bind(textField.fontProperty());
        prompt.getStyleClass().add("prompt-text");

        actionIcon.setContent(Icons.CLEAR);
        actionIcon.setId("actionIcon");
        actionIcon.getStyleClass().add("icon");

        actionButton.getStyleClass().add("btn-flat");
        leadButton.getStyleClass().add("btn-flat");

        actionButton.setMaxSize(btnSize,btnSize);
        actionButton.setPrefSize(btnSize,btnSize);
        actionButton.setMinSize(btnSize,btnSize);
        leadButton.setMaxSize(btnSize,btnSize);
        leadButton.setPrefSize(btnSize,btnSize);
        leadButton.setMinSize(btnSize,btnSize);

        actionButton.setGraphic(actionIcon);
        leadButton.setGraphic(leadIcon);
        actionButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        leadButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        actionButton.getStyleClass().add("action-button");
        leadButton.getStyleClass().add("lead-button");

        pane = (Pane) getChildren().get(0);

        actionButton.setMaxWidth(btnSize);
        actionButton.setPrefWidth(btnSize);

        actionButton.setVisible(true);

//        this.actionButtonType = new SimpleStyleableObjectProperty<>(
//                ACTION_BUTTON_TYPE, ActionButtonType.CLEAR);
//        this.maxLength = new SimpleStyleableObjectProperty<>(
//                MAX_LENGTH, -1);
//        this.leadIconType = new SimpleStyleableObjectProperty<>(
//                LEAD_ICON_TYPE);

        createClearAction();

        leadIcon.getStyleClass().add("icon");
        leadIcon.getStyleClass().add("lead-icon");
        leadButton.setMouseTransparent(true);

//        leadIconType.addListener((observable, oldValue, newValue) -> {
//
//            if (newValue != null) {
//
////                System.out.println("newValue = " + newValue);
//                if (newValue.equals(Icons.NONE) || newValue.equals(Icons.NULL)) {
//                    getChildren().remove(leadButton);
////                    two();
//                } else {
//
//                    if (!getChildren().contains(leadButton)) {
//                        leadIcon.setContent(newValue);
//                        getChildren().add(leadButton);
////                        setUp();
//                    }
//                }
//            } else {
////                two();
//                getChildren().remove(leadButton);
//            }
//
//        });

//        fieldFooter = new FieldFooter();
        getChildren().add(fieldFooter);
        leadButton.setFocusTraversable(false);

//        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//
//            setUp();
//
//            if(newValue != null) {
//
//                if (actionButtonType.get() != null) {
//                    if (!getChildren().contains(actionButton))
//                        getChildren().add(actionButton);
//                } else {
//                    getChildren().remove(actionButton);
//                }
//            } else {
//                getChildren().remove(actionButton);
//            }
//        });

//        getSkinnable().lengthProperty()
//                .addListener((observable, oldValue, newValue) -> {
//            if (getMaxLength().intValue() > 0) {
//                if (textField.getText().length() > getMaxLength().intValue()) {
//                    textField.setText(
//                            textField.getText()
//                                    .substring(0, getMaxLength().intValue()));
//                } else updateFooterSize(newValue.intValue());
//            }
//        });

        if(getSkinnable().getText() != null) {
            updateFooterSize(textField.getLength());
        }

//        maxLength.addListener((observable, oldValue, newValue) -> updateFooterSize(textField.getLength()));
//
//        fieldFooter.setVisibleCount(false);
        actionButton.setFocusTraversable(false);

//        actionButtonType.addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                if(!getChildren().contains(actionButton)) {
//                    getChildren().add(actionButton);
//                        switch (newValue) {
//                            case NONE:
//                            case NULL:
//                                getChildren().remove(actionButton);
//                                break;
//                            case CLEAR:
//                                createClearAction();
//                                break;
//                        }
//                }
//            } else {
//                getChildren().remove(actionButton);
//            }
//        });

//        floatPrompt.addListener((observable, oldValue, newValue) -> {
//            setUp();
//
//            if (newValue) {
//                addFloating(textField);
//            } else {
//                removeFloating(textField);
//            }
//        });

        clip.setArcWidth(5);
        clip.setArcHeight(5);

        clip.widthProperty().bind(getSkinnable().widthProperty());
        clip.heightProperty().bind(getSkinnable().heightProperty());

        getChildren().add(clip);

        clip.getStyleClass().add("border");

        clip.setStrokeType(StrokeType.OUTSIDE);
        clip.setStrokeLineCap(StrokeLineCap.ROUND);
        clip.setStrokeLineJoin(StrokeLineJoin.ROUND);
        clip.setSmooth(false);
        clip.setStrokeMiterLimit(10);

//        errorTextVisible.addListener((observable, oldValue, newValue) ->
//                fieldFooter.setErrorVisible(newValue));


        registerChangeListener(textField.floatPromptProperty(), "FLOAT_PROMPT");

        System.out.println("textField.floatPromptProperty() = " +
                textField.floatPromptProperty());

        getChildren().add(prompt);

//        setUp();
    }

    private void updateFooterSize(int length) {

//        String max = String.valueOf(getMaxLength().intValue());
//        String size = String.valueOf(length);
//
//        if(max.equals("0") || getMaxLength().intValue() == -1D) {
////            max = "..";
//            fieldFooter.setVisibleCount(false);
//        } else {
//            fieldFooter.setVisibleCount(true);
//            fieldFooter.getCount().setText(size + "/" + max);
//        }

    }

    private void createClearAction() {
        actionButton.setOnMouseClicked(event ->   {
            getSkinnable().clear();
            getChildren().remove(actionButton);
        });
    }

    private void removeFloating(TextField textField) {
        downPrompt();
        getChildren().remove(prompt);
        textField.setStyle("-fx-prompt-text-fill : -prompt-fill;");
    }

    private void setUp() {

        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (getSkinnable().getText() != null) {

            if (up) return;


            if(getSkinnable().getText() == null) {
                upPrompt();
                return;
            }


            if (!getSkinnable().getText().isEmpty()) {
                upPrompt();
            }


        } else {
            if (animation.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            downPrompt();
        }
    }


    private void addFloating(TextField textField) {

        // to remove prompt text
        textField.setStyle("-fx-prompt-text-fill : transparent;");

        pane.getStyleClass().add("content");
        getChildren().add(prompt);
        prompt.setPadding(new Insets(2));
        prompt.setText(prompt.getText());
        prompt.setMouseTransparent(true);

        getSkinnable().focusedProperty().addListener((observable, oldValue, newValue) -> {

            actionButton.setVisible(newValue);
            clip.setVisible(true);

            if (animation.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            System.out.println("up = " + up);

            if (newValue) {

                if (up) return;

                if(getSkinnable().getText() == null) {
                    upPrompt();
                    return;
                }

                if (!getSkinnable().getText().isEmpty()) {
                    if (up) return;
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
        });



    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        TextField textField = getSkinnable();

        double characterWidth = fontMetrics.get().computeStringWidth("W");

        int columnCount = textField.getPrefColumnCount();
        return  columnCount * characterWidth + leftInset + rightInset + btnSize;
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        if(getChildren().contains(actionButton) && getChildren().contains(leadButton)) {

            layoutInArea(leadButton,
                    x , y,
                    leadButton.getWidth(),
                    h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(actionButton, x + (w - actionButton.getWidth()), y,
                    actionButton.getWidth(), h,
                    0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane, x + leadButton.getWidth(), y,
                    w - (actionButton.getWidth() + leadButton.getWidth()), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt, x + leadButton.getWidth(), y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if(getChildren().contains(actionButton)) {

            layoutInArea(actionButton, x, y, w , h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane, x, y,
                    w - actionButton.getWidth(), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt, x, y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if(getChildren().contains(leadButton)) {

            layoutInArea(leadButton,
                    x, y,
                    leadButton.getWidth(),
                    h, 0, HPos.RIGHT, VPos.CENTER);


            layoutInArea(pane,
                    x + leadButton.getWidth(), y,
                    w -  leadButton.getWidth(), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x + leadButton.getWidth(), y,
                    prompt.getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else {
            layoutInArea(pane, x, y, w, h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x , y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);
            }

        layoutInArea(fieldFooter,
                x, h + 10,
                getSkinnable().getWidth() - getSkinnable().getInsets().getRight(),
                fieldFooter.getTextFlow().getHeight(), 0,
                HPos.LEFT, VPos.TOP);

        fieldFooter.setCursor(Cursor.DEFAULT);

        layoutInArea(clip, x - getSkinnable().getInsets().getLeft(), y, getSkinnable().getWidth(), h,0,
                HPos.LEFT, VPos.CENTER);

        updateCaretOff();
    }

    private void downPrompt() {
        animation.getKeyFrames().clear();

        up = false;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), prompt.getTranslateY(), Interpolator.EASE_BOTH));
        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), 0 , Interpolator.EASE_BOTH));

        KeyFrame xIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), leadButton.getWidth() * -1, Interpolator.EASE_BOTH));

        KeyFrame xEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 0.9));
        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 1.0));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 0.9));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 1.0));

        KeyFrame xInitNoLEad = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), -5));

        KeyFrame xEndNoLEad = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), 0));

        if (getChildren().contains(leadButton))
            animation.getKeyFrames().setAll(yIni, xIni, yEnd, xEnd, xsInit, xsEnd, ysInit, ysEnd);
        else animation.getKeyFrames().setAll(yIni, yEnd, xsInit, xInitNoLEad, xEndNoLEad, xsEnd, ysInit, ysEnd);

//        animation.setOnFinished(event ->
            pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, false);
//      );
        animation.play();
    }

    public void upPrompt() {

        double padding = getSkinnable().getPadding().getTop() +
                getSkinnable().getPadding().getBottom();

        double size = getSkinnable().getPrefHeight();

        double translateY = ( size - (prompt.getFont().getSize() + padding
                - ( clip.getStrokeWidth() + 2) )) *-1;

        if (up) return;

        up = true;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), translateY, Interpolator.EASE_BOTH));

        KeyFrame xIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), 0));

        double x = leadButton.getWidth() == 0 ? 25 : leadButton.getWidth();

        KeyFrame xEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), (x * -1) ));

        KeyFrame xInitNoLEad = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), 0));

        KeyFrame xEndNoLEad = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 1));
        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 1));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 0.9));

        // hack.. if skinable don't have leadbutton.. others methods don't work
        boolean isLead = false;
        for (String s : getSkinnable().getStyleClass()) {
            String ver = "";
            if (s.contains("-") )
                ver = s.substring(s.lastIndexOf("-")+1).toLowerCase();
            if (s.startsWith("lead") || ver.equals("checked") ||
                    ver.equals("error") || ver.equals("warning")) {
                isLead = true;
                break;
            }
        }

        if (isLead ) {
            animation.getKeyFrames().setAll(
                    yIni, xIni, yEnd, xEnd, xsInit, xsEnd,ysInit, ysEnd);
        } else {
            animation.getKeyFrames().setAll(
                    yIni, yEnd, xsInit, xInitNoLEad, xEndNoLEad, xsEnd,ysInit, ysEnd);
        }
        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, true);

        animation.play();

    }

    @Override
    public void dispose() {
        super.dispose();
//        floatPrompt.set(false);
//        leadIconType.set(Icons.NONE);
        getChildren().removeAll(leadButton, actionButton, fieldFooter);
    }

//    private static final CssMetaData<TextField, ActionButtonType> ACTION_BUTTON_TYPE =
//            new CssMetaData<TextField, ActionButtonType>(
//                    "-gn-action-type",
//                    ActionButtonTypeConverter.getInstance(), ActionButtonType.CLEAR
//
//            ) {
//                @Override
//                public boolean isSettable(TextField styleable) {
//                    return ((GNTextFieldSkin) styleable.getSkin()).actionButtonType == null ||
//                            !((GNTextFieldSkin) styleable.getSkin()).actionButtonType.isBound();
//                }
//
//                @Override
//                public StyleableProperty<ActionButtonType> getStyleableProperty(TextField styleable) {
//                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
//                        return ((GNTextFieldSkin) styleable.getSkin()).actionButtonTypeProperty();
//                    } else return null;
//
//                }
//            };
//
//    private static final CssMetaData<TextField, Icons> LEAD_ICON_TYPE =
//            new CssMetaData<TextField, Icons>(
//                    "-gn-lead-icon",
//                    LeadIconTypeConverter.getInstance()
//
//            ) {
//                @Override
//                public boolean isSettable(TextField styleable) {
//                    return ((GNTextFieldSkin) styleable.getSkin()).leadIconType == null ||
//                            !((GNTextFieldSkin) styleable.getSkin()).leadIconType.isBound();
//                }
//
//                @Override
//                public StyleableProperty<Icons> getStyleableProperty(TextField styleable) {
//                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
//                        return ((GNTextFieldSkin) styleable.getSkin()).leadIconTypeProperty();
//                    } else return null;
//
//                }
//            };
//
//    private static final CssMetaData<TextField, Number> MAX_LENGTH =
//            new CssMetaData<TextField, Number>(
//                    "-gn-max-length",
//                    StyleConverter.getSizeConverter(), 0
//
//            ) {
//                @Override
//                public boolean isSettable(TextField styleable) {
//                    return ((GNTextFieldSkin) styleable.getSkin()).maxLength == null ||
//                            !((GNTextFieldSkin) styleable.getSkin()).maxLength.isBound();
//                }
//
//                @Override
//                public StyleableProperty<Number> getStyleableProperty(TextField styleable) {
//                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
//                        return ((GNTextFieldSkin) styleable.getSkin()).maxLengthProperty();
//                    } else return null;
//
//                }
//            };
//
////    private static final CssMetaData<TextField, Boolean> FLOAT_PROMPT =
////            new CssMetaData<TextField, Boolean>(
////                    "-gn-float-prompt",
////                    StyleConverter.getBooleanConverter(), false
////
////            ) {
////                @Override
////                public boolean isSettable(TextField styleable) {
////                    return ((GNTextFieldSkin) styleable.getSkin()).floatPrompt == null ||
////                            !((GNTextFieldSkin) styleable.getSkin()).floatPrompt.isBound();
////                }
////
////                @Override
////                public StyleableProperty<Boolean> getStyleableProperty(TextField styleable) {
////                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
////                        return ((GNTextFieldSkin) styleable.getSkin()).floatPromptProperty();
////                    } else return null;
////
////                }
////            };
//
//    private static final CssMetaData<TextField, Boolean> ERROR_TEXT_VISIBLE =
//            new CssMetaData<TextField, Boolean>(
//                    "-gn-error-text-visible",
//                    StyleConverter.getBooleanConverter(), false
//
//            ) {
//                @Override
//                public boolean isSettable(TextField styleable) {
//                    return ((GNTextFieldSkin) styleable.getSkin()).errorTextVisible == null ||
//                            !((GNTextFieldSkin) styleable.getSkin()).errorTextVisible.isBound();
//                }
//
//                @Override
//                public StyleableProperty<Boolean> getStyleableProperty(TextField styleable) {
//                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
//                        return ((GNTextFieldSkin) styleable.getSkin()).errorTextVisibleProperty();
//                    } else return null;
//
//                }
//            };
//
//    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
//
//    static {
//        final List<CssMetaData<? extends Styleable, ?>> styleables =
//                new ArrayList<>(TextFieldSkin.getClassCssMetaData());
//        styleables.add(ERROR_TEXT_VISIBLE);
////        styleables.add(FLOAT_PROMPT);
//        styleables.add(ACTION_BUTTON_TYPE);
//        styleables.add(MAX_LENGTH);
//        styleables.add(LEAD_ICON_TYPE);
//        STYLEABLES = Collections.unmodifiableList(styleables);
//    }
//
//    @Contract(pure = true)
//    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
//        return STYLEABLES;
//    }
//
//    @Override
//    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
//        return getClassCssMetaData();
//    }

//    public StyleableObjectProperty<ActionButtonType> actionButtonTypeProperty() {
//        return actionButtonType;
//    }
//
//    public StyleableObjectProperty<Number> maxLengthProperty() {
//        return maxLength;
//    }
//
//    public Number getMaxLength() {
//        return maxLength.get();
//    }
//
//    protected Button getActionButton() {
//        return actionButton;
//    }
//
//    protected IconContainer getActionIcon() {
//        return actionIcon;
//    }
//
////    public StyleableObjectProperty<Boolean> floatPromptProperty() {
////        return floatPrompt;
////    }
//
//    public StyleableObjectProperty<Icons> leadIconTypeProperty() {
//        return leadIconType;
//    }
//
//    public StyleableObjectProperty<Boolean> errorTextVisibleProperty() {
//        return errorTextVisible;
//    }
//
//    public void setErrorMessage(String text) {
//        fieldFooter.setMessage(text);
//    }
}
