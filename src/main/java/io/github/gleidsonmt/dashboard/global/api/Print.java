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
package io.github.gleidsonmt.dashboard.global.api;

import io.github.gleidsonmt.dashboard.global.material.color.Colors;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/11/2021
 */
public class Print {
    public static void main(String[] args) {

//        for (Icons i : Icons.values()) {
//            System.out.println(".lead-" + i.toString()
//                    .replaceAll("_", "-").toLowerCase() +
//                    " {\n \t -gn-lead-icon : " + i.toString().toLowerCase() + "; \n" +
//                    "}"
//            );
//            System.out.println();
//        }

        System.out.println();
        System.out.println();
        System.out.println();

//        for (Colors c : Colors.values()) {
//            String _class = c.getName().toLowerCase();
//            System.out.println(".tf-" + _class + ":focused,\n" +
//                    ".tf-" + _class + ":focused .border {\n" +
//                    "    -fx-border-color : -" + _class + ";\n" +
//                    "    -fx-stroke : -" + _class + ";\n" +
//                    "}");
//            System.out.println();
//        }

        for (Colors c : Colors.values()) {
            String _class = c.getName().toLowerCase();
            System.out.println(".border-" + _class + ":focused,\n" +
                    ".border-" + _class + ":focused .border {\n" +
                    "    -fx-border-color : -" + _class + ";\n" +
                    "    -fx-stroke : -" + _class + ";\n" +
                    "}");

            System.out.println();
            System.out.println(".border-" + _class + ":float:focused .prompt-text .text {\n" +
                    "    -fx-fill : -"  + _class + ";\n" +
                    "}");
            System.out.println();

            System.out.println(".border-" + _class + ":focused .lead-button .lead-icon {\n" +
                    "    -fx-fill : -"  + _class + ";\n" +
                    "}");

        }

    }
}
