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

import com.sun.javafx.scene.control.skin.TableViewSkin;
import io.github.gleidson28.global.controls.control.GNTableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class GNTableViewSkin<T> extends TableViewSkin<T> {

    private final Rectangle border = new Rectangle();

    public GNTableViewSkin(GNTableView<T> tableView) {
        super(tableView);
        config();
    }

    private void config() {
        getChildren().add(border);

        border.setArcWidth(5);
        border.setArcHeight(5);

        border.setStroke(Color.TRANSPARENT);

        border.widthProperty().bind(getSkinnable().widthProperty().add(8));
        border.heightProperty().bind(getSkinnable().heightProperty().subtract(10));

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

    }

}
