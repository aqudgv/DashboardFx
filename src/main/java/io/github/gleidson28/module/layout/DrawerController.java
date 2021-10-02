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
 * along with popOver program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidson28.module.layout;

//import com.calendarfx.view.CalendarView;
//import com.calendarfx.view.CalendarView;
//import com.calendarfx.model.Calendar;
//import com.calendarfx.model.CalendarSource;
//import com.calendarfx.view.CalendarView;
//import com.calendarfx.view.SpeedAgenda;
//import impl.com.calendarfx.view.SpeedSkin;

import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.dao.UserPresenter;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.User;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.util.DrawerNavigate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/09/2020
 */
public class DrawerController implements Initializable {

    @FXML private VBox drawer;
    @FXML private Hyperlink language;
    @FXML private ChoiceBox<Label> choiceLanguage;
    @FXML private Label title;
    @FXML private Button btn_settings;

    @FXML private GNAvatarView avatarView;


//    private SpeedAgenda speedAgenda;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new DrawerNavigate(drawer);

//        if(path == null) {
//            path = "app/media/img/avatars/man@400.png";
//            try {
//                avatarView.setImage(new Image(new FileInputStream(path)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } else {
//            avatarView.setImage(new Image(in));
//        }


//        avatarView.setImage(new Image( new UserPresenter().getActive().getAvatar("50")));
//



    }

    @FXML
    private void goAgenda(){

    }

    @FXML private void goDashboard() throws NavigationException {
        ViewManager.INSTANCE.setContent("dashboard");
    }

    @FXML
    private void goCashier() throws NavigationException {
    }

    @FXML
    private void goSales() throws NavigationException {


    }

    @FXML
    private void goCashierCommand() throws NavigationException {
    }
    
    @FXML
    private void goCashiers() throws NavigationException {

    }

    @FXML
    private void goSettings() throws NavigationException {
    }

    @FXML
    private void goButton() throws NavigationException {
        ViewManager.INSTANCE.setContent("button");
    }

    @FXML
    private void goDataTable() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional");
    }

    @FXML
    private void openOptions() {
    }


}