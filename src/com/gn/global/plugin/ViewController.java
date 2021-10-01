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
package com.gn.global.plugin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/03/2020
 */
public class ViewController {

    private final Module view;
    private final FXMLLoader  loader;

    ViewController(Module view, FXMLLoader loader){
        this.view = view;
        this.loader = loader;
    }

    public String getTitle() {
        return view.getTitle();
    }

    public String getName(){
        return view.getName();
    }

    public Module getView() {
        return view;
    }

    public Object getController() {
        return loader.getController();
    }

    public Parent getRoot(){
        return loader.getRoot();
    }
}
