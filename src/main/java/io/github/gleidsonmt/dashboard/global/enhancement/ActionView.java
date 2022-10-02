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
package io.github.gleidsonmt.dashboard.global.enhancement;

import io.github.gleidsonmt.dashboard.global.exceptions.NavigationException;
import io.github.gleidsonmt.dashboard.module.app.App;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/09/2021
 */
public interface ActionView {

    ObservableList<ChangeListener<Number>> widthListeners =
            FXCollections.observableArrayList();

    ObservableList<ChangeListener<Number>> globalListeners =
            FXCollections.observableArrayList();


    default void addListener(Region region, ChangeListener<Number> width) {
        widthListeners.add(width);
        for (ChangeListener<Number> listener : widthListeners) {
            region.widthProperty().addListener(listener);
        }
    }

    default void addListener(Region region, ChangeListener<Number>[] width) {
        widthListeners.addAll(width);
        for (ChangeListener<Number> listener : widthListeners) {
            region.widthProperty().addListener(listener);
        }
    }

    default void removeListener(Region region) {
        for (ChangeListener<Number> listener : widthListeners) {
            region.widthProperty().removeListener(listener);
        }
        widthListeners.clear();
    }

    default void addGlobalListener(ChangeListener<Number> listener) {
        globalListeners.add(listener);
        for (ChangeListener<Number> l : globalListeners) {
            App.widthProperty().addListener(l);
        }
    }

    default void addGlobalListener(ChangeListener<Number>... listener) {
        globalListeners.addAll(listener);
        for (ChangeListener<Number> l : globalListeners) {
            App.widthProperty().addListener(l);
        }
    }

    default void removeGlobalListener() {
        for (ChangeListener<Number> l : globalListeners) {
            App.widthProperty().removeListener(l);
        }
    }

    void onEnter() throws NavigationException;

    void onExit();
}
