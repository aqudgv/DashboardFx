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
package io.github.gleidson28.global.controls.textField;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class FieldFooter extends GridPane {

    private final Text count = new Text("0/..");

    private final TextFlow textFlow = new TextFlow();
    private final Text message = new Text("Helper Message.");

    private IntegerProperty maxCount = new SimpleIntegerProperty(0);

    public FieldFooter() {

        getStyleClass().add("field-footer");
        textFlow.getStyleClass().add("text-flow");
        message.getStyleClass().add("message");
        count.getStyleClass().add("count");
//        this.setPadding(new Insets(5));

        message.getStyleClass().add("text-grapefruit");

        textFlow.getChildren().add(message);

        getChildren().addAll(textFlow);

        GridPane.setConstraints(textFlow, 0, 0, 1, 1,
                HPos.LEFT, VPos.CENTER,
                Priority.SOMETIMES, Priority.SOMETIMES);


        getChildren().add(count);

        count.setStyle("-fx-font-width : 10px;");
        this.setPadding(new Insets(1, 0, 0, 0));
        GridPane.setConstraints(
                count, 1, 0, 1, 1,
                HPos.RIGHT, VPos.TOP, Priority.SOMETIMES, Priority.SOMETIMES);


        textFlow.setVisible(false);
        count.setVisible(false);
//        this.setGridLinesVisible(true);

//        System.out.println(getMaxCount());
//        updateSize(0, getMaxCount());
        maxCount.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0) {
                updateSize(0, newValue.intValue());
            }
        });

    }


    public void updateSize(int length, int maxLength) {

        String max = String.valueOf(maxLength);
        String size = String.valueOf(length);

        if(max.equals("0") || maxLength == -1D) {
            max = "..";

        }
        getCount().setText(size + "/" + max);
    }


    public int getMaxCount() {
        return maxCount.get();
    }

    public IntegerProperty maxCountProperty() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount.set(maxCount);
    }

    public Text getCount() {
        return this.count;
    }

//    public void setVisibleCount(boolean value) {
//        count.setVisible(value);
//    }

    public BooleanProperty visibleCountProperty() {
        return count.visibleProperty();
    }

    public TextFlow getTextFlow() {
        return textFlow;
    }

    public void setErrorVisible(boolean value) {
        textFlow.setVisible(value);
    }


    public StringProperty messageProperty() {
        return message.textProperty();
    }
}
