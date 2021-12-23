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

import com.sun.javafx.css.StyleConverterImpl;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class ActionButtonTypeConverter extends StyleConverterImpl<String, ActionButtonType> {

    private ActionButtonTypeConverter() {
    }

    public static StyleConverter<String, ActionButtonType> getInstance() {
        return ActionButtonTypeConverter.Holder.INSTANCE;
    }

    public ActionButtonType convert(ParsedValue<String, ActionButtonType> value, Font notUsedFont) {

        String string = (String) value.getValue();

        try {
            return ActionButtonType.valueOf(string.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException var5) {
            Logger.getLogger(TextFieldType.class.getName()).info(String.format("Invalid button type value '%s'", string));
            return ActionButtonType.CLEAR;
        }
    }

    public String toString() {
        return "ActionButtonTypeConverter";
    }

    private static class Holder {

        static final ActionButtonTypeConverter INSTANCE = new ActionButtonTypeConverter();

        private Holder() {
            throw new IllegalAccessError("Holder class");
        }
    }
}
