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
package io.github.gleidsonmt.dashboard.global.badges;

import io.github.gleidsonmt.dashboard.global.enhancement.Avatar;
import io.github.gleidson28.GNAvatarView;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/10/2021
 */
public class BadgeCellMessage extends GridPane {

    private final Label title = new Label("title");
    private final Label time = new Label("5 hours ago");
    private final Label message = new Label("message");
    private final GNAvatarView avatarView = new GNAvatarView();

    public BadgeCellMessage(String title, String message, String time, Image image){
        this.title.setText(title);
        this.message.setText(message);
        this.time.setText(time);
        this.avatarView.setImage(image);
        config();
    }

    public BadgeCellMessage() {
//        this.setMinSize(300, 300);
        this.avatarView.setImage(new Avatar("theme/img/avatars/man@50.png"));
        config();
    }

    private void config(){
        this.getStyleClass().add("badge-cell");

        this.avatarView.setMaxWidth(40D);
        this.time.setStyle("-fx-font-size : 8pt;");

        this.add(avatarView, 0,0);
        this.add(title, 1,0 );
        this.add(time, 2,0);
        this.add(message, 1,1);

        GridPane.setRowSpan(avatarView, GridPane.REMAINING);
        GridPane.setHalignment(time, HPos.RIGHT);

        this.setHgap(2D);

    }
}
