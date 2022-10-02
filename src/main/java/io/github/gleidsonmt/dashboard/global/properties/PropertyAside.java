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
package io.github.gleidsonmt.dashboard.global.properties;

import io.github.gleidsonmt.dashboard.global.controls.control.GNTextArea;
import io.github.gleidsonmt.dashboard.global.creators.SnackBarCreator;
import io.github.gleidsonmt.dashboard.global.material.icon.IconContainer;
import io.github.gleidsonmt.dashboard.global.material.icon.Icons;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/11/2021
 */
public class PropertyAside extends ScrollPane {

    private final VBox customSelectors = new VBox();

    private final TextArea styleArea  = new GNTextArea();
    private final TextArea javaArea  = new GNTextArea();
    private final TextArea fxmlArea  = new GNTextArea();

    private Control control;

    private final ObservableList<String> styleClass = FXCollections.observableArrayList();

    private enum BoxType {
        STYLE, JAVA, FXML
    }

    public PropertyAside() {
        this(null);
    }

    public PropertyAside(Control control) {

        this.control = control;

        styleArea.setEditable(false);
        javaArea.setEditable(false);
        fxmlArea.setEditable(false);

        this.getStyleClass().addAll("border", "border-r-1");

        Label title = new Label();
        title.setGraphic(new IconContainer(Icons.STYLE));
        title.getStyleClass().add("h3");

        title.setText("Css Style");
        title.setGraphicTextGap(10D);

//        BorderPane.setMargin(this, new Insets(20));
        this.setPadding(new Insets(10));

        VBox body = new VBox();
        body.getChildren().add(title);
        body.getChildren().add(1, customSelectors);
        body.getChildren().addAll(createSeparator(), createBox("StyleClass", BoxType.STYLE));
        body.getChildren().addAll(createSeparator(), createBox("Java", BoxType.JAVA));
        body.getChildren().addAll(createSeparator(), createBox("FXML", BoxType.FXML));

        body.setSpacing(20);

        this.setContent(body);

        this.setPrefWidth(250);

        this.setFitToWidth(true);
        this.setFitToHeight(true);

        styleClass.addListener((ListChangeListener<String>) c -> {
            if(c.next()) {
//                if(c.wasAdded())
                    read();
                if(c.getList().size() < 1) {
                    reset();
                }
            }
        });



    }

    private void areaDisabled(TextArea textArea, Node node) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) node.setDisable(true);
            else if (!textArea.getText().isEmpty()) node.setDisable(false);
        });
    }

    @Deprecated
    public void addSelectors(Selector... selectors) {
        customSelectors.getChildren().addAll(selectors);
    }

    public void addStyleClass(String _class) {
        styleClass.addAll(_class);
    }

    public void removeStyleClass(String _class) {
        styleClass.removeAll(_class);
    }

    public Control getControl() {
        return this.control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public void clear() {
        customSelectors.getChildren().clear();

        styleClass.clear();
        styleArea.setText(null);
        javaArea.setText(null);
        fxmlArea.setText(null);
    }

    private VBox createBox(String title, BoxType type) {
        return createBox(title, type, null);
    }

    private VBox createBox(String title,  BoxType type, String text) {
        VBox box = new VBox();

        box.setMinHeight(200);

        StackPane content = new StackPane();


        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("h5");

        box.setPrefSize(300, 300);

        box.setPadding(new Insets(5D));
        box.setSpacing(10D);

        TextArea boxArea = new TextArea();
        boxArea.setText(text);

        Button btnCopy = new Button();
        btnCopy.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnCopy.getStyleClass().add("btn-flat");

        btnCopy.setGraphic(new IconContainer(Icons.CONTENT_COPY));
        content.setDisable(true);

        TextArea area = null;

        switch (type) {
            case STYLE:
                content.getChildren().addAll(styleArea, btnCopy);
                styleArea.setText(text);
                area = styleArea;
                areaDisabled(styleArea, content);
                break;
            case JAVA:
                content.getChildren().addAll(javaArea, btnCopy);
                javaArea.setText(text);
                area = javaArea;
                areaDisabled(javaArea, content);
                break;
            case FXML:
                content.getChildren().addAll(fxmlArea, btnCopy);
                fxmlArea.setText(text);
                area = fxmlArea;
                areaDisabled(fxmlArea, content);
                break;
        }

        TextArea finalArea = area;

        btnCopy.setOnMouseClicked(event -> {
            if (finalArea.getText() != null) {
                if(!finalArea.getText().isEmpty()) {
                    Clipboard clipboard  = Clipboard.getSystemClipboard();
                    ClipboardContent c = new ClipboardContent();
                    c.putString(finalArea.getText());
                    clipboard.setContent(c);
                    finalArea.selectAll();

                    SnackBarCreator.INSTANCE.createSnackBar("Copied to clipboard.");
                }
            }
        });


        btnCopy.setTooltip(new Tooltip("Copy"));
        box.getChildren().addAll(lblTitle, content);
        content.setAlignment(Pos.CENTER_LEFT);
        StackPane.setAlignment(btnCopy, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnCopy, new Insets(0,10,2,0));
        return box;
    }

    public void reset() {
        styleArea.setText(null);
        javaArea.setText(null);
        fxmlArea.setText(null);
    }

    private void read() {
        reset();
        javaArea.setText(
                "CheckBox checkBox = new CheckBox(\"CheckBox\");" +
                        "\ncheckBox.getStyleClass()\n.addAll("
        );
        fxmlArea.setText("<CheckBox text=\"CheckBox\">\n" +
                "\t<styleClass>\n");

        for (String newValue : styleClass) {

            styleArea.appendText(newValue + " ");
            javaArea.appendText("\"" + newValue.replaceAll(",","") + "\", ");
            fxmlArea.appendText(
                    "\t\t <String fx:value=\""+ newValue
                            .replace(",", "") + "\" />\n");
        }

        if(styleArea.getText() != null) {
            if(styleArea.getText().contains(","))
                styleArea.replaceText(styleArea.getText().lastIndexOf(","), styleArea.getText().length(), "");
        }

        if(javaArea.getText() != null && javaArea.getText().contains(",")) {

            javaArea.setText(javaArea.getText().replaceAll(" ", ""));
            javaArea.replaceText(
                    javaArea.getText().
                            lastIndexOf(","),
                    javaArea.getText().length(), "");

            javaArea.appendText(");");
        }

        if(fxmlArea.getText() != null) {
            fxmlArea.setText(fxmlArea.getText().replaceAll(" ", ""));
            fxmlArea.appendText("\t</styleClass>\n" +
                    "</CheckBox>");
        }
    }

    private VBox createSeparator() {
        VBox separator = new VBox();

        separator.getStyleClass().addAll("border");
        separator.setStyle("-fx-background-color : -separator-color;");

        separator.setPrefHeight(1);
        return separator;
    }

    public void removeSelector(Selector selector) {
        if (selector != null)
            selector.reset();
            customSelectors.getChildren().remove(selector);
    }

    public void addSelector(Selector selector) {
        customSelectors.getChildren().add(selector);
    }
}
