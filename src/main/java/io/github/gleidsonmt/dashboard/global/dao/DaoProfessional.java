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
package io.github.gleidsonmt.dashboard.global.dao;

import io.github.gleidsonmt.dashboard.global.enhancement.Avatar;
import io.github.gleidsonmt.dashboard.global.model.Professional;
import io.github.gleidsonmt.dashboard.global.model.Status;
import io.github.gleidsonmt.dashboard.global.util.MoneyUtil;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
public final class DaoProfessional extends AbstractDao<Professional> {

    private final ListProperty<Professional> professionals =
            new SimpleListProperty<>(DaoProfessional.class, "ListProfessionals", FXCollections.observableArrayList());

    private static DaoProfessional instance;

    public static DaoProfessional getInstance() {
        if(instance == null) {
            instance = new DaoProfessional();
        }
        return instance;
    }

    private DaoProfessional() {
        professionals.get().addListener((ListChangeListener<Professional>) c -> {
            if(c.next()) {
                if(c.wasRemoved()) {
                    delete(c.getRemoved());
                }
            }
        });
    }

    private void delete(List<? extends Professional> professionals) {
        for (Professional model : professionals) {
            delete(model);
        }
    }

    @Override
    public boolean delete(Professional model) {
        connect();
        PreparedStatement prepare = prepare("delete from professional where id = " + model.getId());
//        professionals.remove(model);

        try {
            return prepare.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public Professional find(long id) {
        return null;
    }

    @Override
    public boolean store(Professional professional) {

        try {
            connect();


            PreparedStatement prepare = prepare("insert into professional(name, lastName, avatar, price, status," +
                    " rating) values(?, ?, ?, ?, ?, ?);");
            prepare.setString(1, professional.getName());
            prepare.setString(2, professional.getLastName());
            prepare.setString(3, professional.getAvatar().getPath());
            prepare.setBigDecimal(4, professional.getPrice() == null ? MoneyUtil.get("0") : professional.getPrice());
            prepare.setString(5, String.valueOf(Status.convertChar(professional.getStatus())));
            prepare.setFloat(6, professional.getRating());

            prepare.execute();

            prepare = prepare("select LAST_INSERT_ID()");
            prepare.execute();

            while (prepare.getResultSet().next()) {
                professional.setId(prepare.getResultSet().getInt(1));
            }

            professionals.get().add(professional);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    @Override
    public boolean update(Professional professional) {
        try {

            connect();
            PreparedStatement prepare = prepare("update professional set name = ?, avatar = ?, " +
                    "price = ?, teams = ?, rating = ?, avatar = ? where id = " + professional.getId() + "; ");

            prepare.setString(1, professional.getName());
            prepare.setString(2, professional.getAvatar().getPath());
            prepare.setBigDecimal(3, professional.getPrice());
            prepare.setString(4, professional.getTeams());
            prepare.setFloat(5, professional.getRating());

            prepare.setString(6, professional.getAvatar().getPath());

            prepare.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    public boolean executeQuery(String sql) throws SQLException {
        connect();
        executeSQL(sql);
        return result().first();
    }

    public ObservableList<Professional> getAll() {
        return professionals;
    }

    public Task<ObservableList<Professional>> getTask() {

        return new Task<ObservableList<Professional>>() {

            @Override
            protected void failed() {
                super.failed();
            }

            @Override
            protected ObservableList<Professional> call() {

                professionals.clear();
                connect();
                executeSQL("select * from professional;");
                ResultSet result = result();

                try {
                    while (result.next()) {

                        Professional professional = new Professional();
                        professional.setId(result.getInt("id"));
                        professional.setName(result.getString("name"));
                        professional.setLastName(result.getString("lastName"));

                        String path = result.getString("avatar");

                        if(path.startsWith("theme")) {
                            professional.setAvatar(new Avatar(path));
                        } else {

                            File file = new File(path);
                            FileInputStream fileInputStream = new FileInputStream(file);
                            Avatar avatar = new Avatar(fileInputStream, path);
                            professional.setAvatar(avatar);

                        }

                        if(result.getString("status").equals("F")) professional.setStatus(Status.FREE);
                        else if(result.getString("status").equals("U")) professional.setStatus(Status.UNAVAILABLE);
                        else professional.setStatus(Status.BUSY);

                        professional.setPrice(result.getBigDecimal("price"));

                        professional.setRating(result.getFloat("rating"));
                        professional.setLocation(result.getString("location"));
                        professional.setEmail(result.getString("email"));
                        professional.setTeams(result.getString("teams"));

                        professionals.get().add(professional);

                        Thread.sleep(100);

                    }
                } catch (SQLException | FileNotFoundException | InterruptedException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return professionals;
            }
        };
    }
}


