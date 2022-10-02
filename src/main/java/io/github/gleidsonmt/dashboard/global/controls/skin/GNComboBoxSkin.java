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

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import io.github.gleidsonmt.dashboard.global.controls.control.GNComboBox;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class GNComboBoxSkin<T> extends ComboBoxListViewSkin<T> {

    private final GNComboBox<T> comboBox;

    private final Label prompt = new Label();
    private final Timeline animation = new Timeline();
    private final Rectangle border = new Rectangle();
    private final Button leadButton = new Button();

    private boolean up = false;

    private final ListCell<T> buttonCell = new ListCell<>();

    private final TextField editor;
    private final ListView<T> listView;
    private FilteredList<T> filteredItems;

    private static final PseudoClass FLOAT_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("float");

    public GNComboBoxSkin(GNComboBox<T> comboBox) {
        super(comboBox);

        this.comboBox = comboBox;
        this.editor = comboBox.getEditor();
        this.comboBox.setButtonCell(buttonCell);
        this.listView = getListView();


        config();

        hidePrompt();

        registerChangeListener(comboBox.floatPromptProperty(),
                "FLOAT_PROMPT");
        registerChangeListener(comboBox.fieldTypeProperty(), "FIELD_TYPE");

        getListView().setTranslateY(2);

        comboBox.setVisibleRowCount(5);

        if (comboBox.isFiltered()) {
            filteredItems = new FilteredList<>(
                    comboBox.getItems(), p -> true);
            comboBox.setItems(filteredItems);
            addFilter();
        }

    }

    private void addFilter() {

        filteredItems.addListener((ListChangeListener<? super T>) c -> {
            if (c.next()) {
                System.out.println(c.getList());
                if (c.getList().size() < 5) {

                    getListView().resize(comboBox.getWidth(),
                            (getListView().getItems().size() * 47));

                } else {
                    getListView().resize(comboBox.getWidth(),
                            comboBox.getVisibleRowCount() * 47);
                }


                refresh();
                show();
            }
        });

        getListView().setOnMouseClicked(event -> {
            refresh();
            hide();
        });

        getListView().addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {
                refresh();
                hide();
                getEditor().selectAll();
            } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                comboBox.setValue(null);
            } else if (event.getCode().equals(KeyCode.DELETE)) {
                comboBox.setValue(null);
            } else if (event.getCode().equals(KeyCode.SHIFT)) {
                event.consume();
            }
        });

        comboBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            refresh();

            if (event.getCode().equals(KeyCode.ENTER)) {
                hide();
            } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                comboBox.setValue(null);
            } else if (event.getCode().equals(KeyCode.DELETE)) {
                comboBox.setValue(null);
            }
        });

        editor.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    final TextField editor = comboBox.getEditor();
                    final Object selected = comboBox.getSelectionModel()
                            .getSelectedItem();

                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredItems.setPredicate(new Predicate<T>() {
                            @Override
                            public boolean test(T t) {
                                return t.toString().toLowerCase()
                                        .contains(newValue.toLowerCase());
                            }
                        });
                    }
                });
    }




    private void refresh() {
        getListView().refresh();
//        getListView().requestLayout();
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);

        if ("FLOAT_PROMPT".equals(propertyReference)) {
            if (comboBox.isFloatPrompt()) {
                addFloating();
            }  else {
                removeFloating();
            }


        }
    }

    private void config() {

        prompt.setText(comboBox.getPromptText());
        prompt.getStyleClass().add("prompt-text");

        getChildren().add(prompt);
        prompt.setPadding(new Insets(0,2,0,2));
        prompt.setText(prompt.getText());

        prompt.setMouseTransparent(true);

        getChildren().add(border);

        border.setArcWidth(5);
        border.setArcHeight(5);

        border.widthProperty().bind(getSkinnable().widthProperty());
        border.heightProperty().bind(getSkinnable().heightProperty());

        border.getStyleClass().add("border");

        border.setStrokeType(StrokeType.OUTSIDE);
        border.setStrokeLineCap(StrokeLineCap.ROUND);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        border.setSmooth(false);
        border.setStrokeMiterLimit(10);

        border.setMouseTransparent(true);
        border.setFocusTraversable(false);
        border.toBack();

//        prompt.toFront();

        leadButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        
        leadButton.getStyleClass().addAll("btn-flat", "flat");

        leadButton.setPadding(new Insets(0,3,0,3));

        comboBox.focusedProperty().addListener(focusedListener);


//        scrollPane.setStyle("-fx-background-color : -medium-gray;");

    }

    ChangeListener<Boolean> focusedListener = (observable, oldValue, newValue) -> {

        if (newValue) { // focused
//            if (!up) {
                upPrompt();
//            }
        } else { // no focus
//
//            if (comboBox.getText() != null) {
//                if (comboBox.getText().isEmpty()) {
                    downPrompt();
//                }
            }
//        }
    };

    private boolean updateDisplayText(ListCell<T> cell, T item, boolean empty) {
        if (empty) {
            if (cell == null) return true;
            cell.setGraphic(null);
            cell.setText(null);
            return true;
        } else if (item instanceof Node) {
            Node currentNode = cell.getGraphic();
            Node newNode = (Node) item;
            if (currentNode == null || ! currentNode.equals(newNode)) {
                cell.setText(null);
                cell.setGraphic(newNode);
            }
            return newNode == null;
        } else {
            // run item through StringConverter if it isn't null
            StringConverter<T> c = comboBox.getConverter();
            String s = item == null ? comboBox.getPromptText() : (c == null ? item.toString() : c.toString(item));
            System.out.println("s = " + s);
            cell.setText(prompt.getText());
            cell.setGraphic(null);
            return s == null || s.isEmpty();
        }
    }


    private void addFloating() {
        hidePrompt();

        if (!getChildren().contains(prompt)) getChildren().add(prompt);

//        if (comboBox.getText() == null) return;
//        if (comboBox.getText().isEmpty()) return;

        if (comboBox.getSelectionModel().getSelectedItem() == null) return;

        upPrompt();
//        textField.setFloatPrompt(true);
    }

    private void removeFloating() {
        hidePrompt();

//        downPrompt();
        getChildren().remove(prompt);
//        textField.setFloatPrompt(false);

    }

    private void hidePrompt( ) {


        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            comboBox.setPromptText(null);
        }


    }

    public void upPrompt() {

        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (up) return;

        double  translateY = - ( (getSkinnable().getHeight()/2) +2 );

        up = true;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), translateY, Interpolator.EASE_BOTH));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 1));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 0.9));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), prompt.getTranslateX()));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(),
                        (-leadButton.getWidth()) -10));

        KeyFrame xInitNoLEad = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), prompt.getTranslateX()));

        KeyFrame xEndNoLEad = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));

        animation.getKeyFrames().setAll(
                yIni, yEnd, xsInit, xsEnd,
                ysInit, ysEnd, xInitNoLEad, xEndNoLEad);

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
                new KeyValue(prompt.translateYProperty(), 0));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 0.9));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 1));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(),  prompt.getTranslateX()));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));


        animation.getKeyFrames().setAll(
                yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd, xInitLead, xEndLead);

        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, false);

        animation.play();
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 45;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return prompt.getWidth() + 45;
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        layoutInArea(prompt, 10, y,
                getSkinnable().getWidth() + 30, h, 0, HPos.LEFT, VPos.CENTER);

//        layoutInArea(border,x - getSkinnable().getInsets().getLeft(), y,
//                w ,
//               h,
//                0,
//                HPos.LEFT, VPos.CENTER);

//        comboBox.requestLayout();



    }
}
