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
package io.github.gleidsonmt.dashboard.global.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2021
 */
public class Test extends Application {

    public Test() {
        BankAccount newAccount = new BankAccount
                .BankAccountBuilder("Jon", "22738022275")
                .withEmail("jon@example.com")
                .wantNewsletter(true)
                .build();


    }

    private Property createButton() {
        Button control = new Button("Text Field");
        control.getProperties().putIfAbsent("prefix", "btn-");
        return new Property
                .PropertyBuilder()
                .control(control)
                .selectors(
                        new SizeSelector(control, "medium")
                )
                .build();
    }

    private void createCheck() {
        CheckBox control = new CheckBox("Text Field");
        control.getProperties().putIfAbsent("prefix", "check-");
         Property p = new Property
                .PropertyBuilder()
                .control(control)
                .selectors(
                        new SizeSelector(control, "medium")
                )
                .build();
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();

        ComboBox<String> cb = new ComboBox<String>();
        cb.setEditable(true);

        // Create a list with some dummy values.
        ObservableList<String> items = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten");

        // Create a FilteredList wrapping the ObservableList.
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cb.getEditor();
            final String selected = cb.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        cb.setItems(filteredItems);

        root.getChildren().add(cb);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
