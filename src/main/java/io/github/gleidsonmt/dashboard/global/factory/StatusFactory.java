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
package io.github.gleidsonmt.dashboard.global.factory;

import io.github.gleidsonmt.dashboard.global.model.Status;
import io.github.gleidsonmt.dashboard.global.model.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/09/2021
 */
public class StatusFactory<E extends User> implements Callback<TableColumn<E, Status>, TableCell<E, Status>> {

    @Override
    public TableCell<E, Status> call(TableColumn<E, Status> param) {

        return new TableCell<E, Status>() {

            @Override
            protected void updateItem(Status item, boolean empty) {

                super.updateItem(item, empty);

                if(item != null) {

                    String res = item.toString().substring(1).toLowerCase();
                    String fl = item.toString().substring(0,1);
                    setText(null);

                    Label lbl = new Label(fl + res);
                    lbl.setPadding(new Insets(5D));

                    HBox body = new HBox();
                    body.getChildren().add(lbl);

                    String _default = "-fx-text-fill : white; " +
                        "-fx-border-width : 2px;  -fx-border-color : white;";

                    switch (item) {
                        case UNAVAILABLE: lbl.setStyle("-fx-background-color : white,  -amber; " + _default);
                        break;
                        case FREE:  lbl.setStyle("-fx-background-color :  white, -success; " + _default);
                        break;
                        default:  lbl.setStyle("-fx-background-color :  white, -grapefruit; " + _default);
                        break;
                    }

                    setItem(item);
                    setGraphic(body);

                } else {
                    setText(null);
                    setItem(null);
                    setGraphic(null);
                }
            }
        };
    };
}
