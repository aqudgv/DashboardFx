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
package io.github.gleidson28.global.controls;

import com.sun.javafx.scene.control.skin.ComboBoxBaseSkin;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/11/2021
 */
public class GNComboBoxSkin<T> extends ComboBoxListViewSkin<T> {


    private boolean up = false;

    private ListCell<T> buttonCell = null;

    private final Timeline  animation   = new Timeline();
    private final Label     prompt      = new Label();

    private final TextField editor;


    private final StyleableObjectProperty<Boolean> floatPrompt;

    public GNComboBoxSkin(ComboBox<T> comboBox) {
        super(comboBox);

        this.editor = comboBox.getEditor();
//
        floatPrompt = new SimpleStyleableObjectProperty<>(FLOAT_PROMPT, false);

//        comboBox.setEditable(true);

//        if(floatPrompt.get()) {
//            addFloatingNotEditable(comboBox);
////            addFloatingEditable(comboBox);
//        }
//
//        floatPrompt.addListener((observable, oldValue, newValue) -> {
//            if(newValue && !comboBox.isEditable()) addFloatingNotEditable(comboBox);
//        });


        FilteredList<String> filteredList = (FilteredList<String>) comboBox.getItems();
        filteredList.addListener((ListChangeListener<String>) c -> {
            if (c.next()) {
                if (c.wasAdded()) {


                    if (editor.getLength() > 0) {
                        getListView().resize(comboBox.getWidth(),
                                (filteredList.size() * 47));

                        getListView().refresh();
                        getListView().requestLayout();
                        show();
                    } else {

                        getListView().resize(comboBox.getWidth(),
                                (filteredList.size() * 47));

                        getListView().refresh();
                        getListView().requestLayout();
                    }
                }
            }
        });

        editor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                hide();
            }
        });

        System.out.println("getChildren() = " + getChildren());

    }


    private void addFloatingEditable(ComboBox comboBox) {

        FilteredList<String> fullList = new FilteredList<>(FXCollections.observableArrayList(
                "Jorge",
                "Luiza", "Paulo", "Jorge", "Andre"),
                p -> true
        );

        comboBox.setItems(fullList);



        final ListView[] _listView = {null}; // it is necessary...

        comboBox.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(_listView[0] == null) {
                _listView[0] = (ListView) comboBox.lookup(".list-view"); // getting a listview from combo..
                _listView[0].setFixedCellSize(30);
            }
        });

        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fullList.setPredicate(professional -> professional.toLowerCase().contains(newValue.toLowerCase()));
                _listView[0].resize(
                        comboBox.getWidth(),
                        (fullList.size() * 30) + // number of items.. sum list padding
                                (_listView[0].getPadding().getTop() + _listView[0].getPadding().getBottom()));
                if(fullList.size() == 0){
                    comboBox.hide();
                } else {
                    comboBox.show();
                }
            }
        });
    }

    private void addFloatingNotEditable(ComboBox<T> comboBox) {

        buttonCell = (ListCell<T>) getDisplayNode();

        getChildren().remove(buttonCell);

        prompt.setText(buttonCell.getText());

        prompt.setStyle("-fx-text-fill : -text-color;");

        getChildren().add(prompt);

        prompt.toFront();


        prompt.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.WHITE,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        prompt.setPadding(new Insets(0,2,0,2));

        buttonCell.toFront();

        comboBox.setOnMouseClicked(event -> {
            if(!up && floatPrompt.get())
                upPrompt();
        });


        comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && !up && floatPrompt.get()) {
                    upPrompt();
                } else if (floatPrompt.get() && up && comboBox.getValue() == null) {
                    downPrompt();
                }
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !up && floatPrompt.get()) {
                upPrompt();
//            } else if (up && floatPrompt.get()) {
//                downPrompt();
            }
        });
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        if (floatPrompt.get())
            layoutInArea(prompt, x + 6, y, w + 6, h, 0, HPos.LEFT, VPos.CENTER);

    }

    private void upPrompt() {
        double newY = ((getSkinnable().getHeight()) / 2);
        up = true;

        animation.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateYProperty(),
                                0)),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(prompt.translateYProperty(),
                                newY * -1 ))

//                new KeyFrame(Duration.millis(200),
//                        new KeyValue(buttonCell.translateXProperty(),
//                                leadButton.getWidth() * -1)),
//
//                new KeyFrame(Duration.millis(200),
//                        new KeyValue(prompt.translateYProperty(),
//                                newY * -1 ))

        );

        animation.play();
    }


    private void downPrompt() {
        animation.getKeyFrames().clear();

        up = false;
        animation.getKeyFrames().setAll(

                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateYProperty(), prompt.getTranslateY())
                ),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(prompt.translateYProperty(),
                                0))

        );
        animation.play();
    }

    private static final CssMetaData<ComboBox<?>, Boolean> FLOAT_PROMPT =
            new CssMetaData<ComboBox<?>, Boolean>(
                    "-gn-float-prompt",
                    StyleConverter.getBooleanConverter(), false

            ) {
                @Override
                public boolean isSettable(ComboBox styleable) {
                    return ((GNComboBoxSkin<?>) styleable.getSkin()).floatPrompt == null ||
                            !((GNComboBoxSkin<?>) styleable.getSkin()).floatPrompt.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(ComboBox styleable) {
                    if (styleable.getSkin() instanceof GNComboBoxSkin) {
                        return ((GNComboBoxSkin<?>) styleable.getSkin()).floatPromptProperty();
                    } else return null;

                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(ComboBoxBaseSkin.getClassCssMetaData());
        styleables.add(FLOAT_PROMPT);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
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
