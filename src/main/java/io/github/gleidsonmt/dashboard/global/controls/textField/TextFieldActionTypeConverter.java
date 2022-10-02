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
package io.github.gleidsonmt.dashboard.global.controls.textField;

import io.github.gleidsonmt.dashboard.global.material.icon.Icons;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class TextFieldActionTypeConverter extends StyleConverter<String, Icons> {

    private TextFieldActionTypeConverter() {
    }

    public static StyleConverter<String, Icons> getInstance() {
        return TextFieldActionTypeConverter.Holder.INSTANCE;
    }

    public Icons convert(ParsedValue<String, Icons> value, Font notUsedFont) {

        String string = (String) value.getValue();

        try {
            return Icons.valueOf(string.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException var5) {
            Logger.getLogger(TextFieldActionTypeConverter.class.getName()).info(String.format("Invalid text field type value '%s'", string));
            return Icons.FAVORITE;
        }
    }

    public String toString() {
        return "TextFieldActionTypeConverter";
    }

    private static class Holder {

        static final TextFieldActionTypeConverter INSTANCE = new TextFieldActionTypeConverter();

        private Holder() {
            throw new IllegalAccessError("Holder class");
        }
    }
}
