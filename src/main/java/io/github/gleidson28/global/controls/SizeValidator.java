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

import io.github.gleidson28.global.controls.control.GNTextField;
import javafx.beans.value.ChangeListener;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class SizeValidator implements ValidatorBase {

    private final GNTextField textField;
    private final String errorMessage;

    private final int min;
    private final int max;

    public SizeValidator(GNTextField textField, String errorMessage, int max) {
        this(textField, errorMessage, 0, max);
    }

    public SizeValidator(GNTextField textField, String errorMessage, int min, int max) {
        this.textField = textField;
        this.errorMessage = errorMessage;
        this.min = min;
        this.max = max;

    }

    private final ChangeListener<String> validateListener = (observable, oldValue, newValue) -> validate();

    @Override
    public boolean validate() {
//        GNTextFieldSkin skin = (GNTextFieldSkin) textField.getSkin();

        textField.textProperty().addListener(validateListener);

        if(textField.getLength() >= min && textField.getLength() <= max ) {
            textField.getStyleClass().removeAll("input-error");
            return true;
        } else {
            textField.getStyleClass().addAll("input-error");
            textField.setMessage(errorMessage);

            return false;
        }
    }

    @Override
    public void reset() {
        textField.textProperty().removeListener(validateListener);
        textField.getStyleClass().removeAll("input-error");
    }
}
