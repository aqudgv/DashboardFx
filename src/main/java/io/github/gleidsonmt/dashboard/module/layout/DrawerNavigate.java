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
package io.github.gleidsonmt.dashboard.module.layout;

import io.github.gleidson28.GNAvatarView;
import io.github.gleidsonmt.dashboard.global.controls.control.GNComboBox;
import io.github.gleidsonmt.dashboard.global.controls.control.GNTextArea;
import io.github.gleidsonmt.dashboard.global.controls.control.GNTextField;
import io.github.gleidsonmt.dashboard.global.enhancement.ActionView;
import io.github.gleidsonmt.dashboard.global.exceptions.NavigationException;
import io.github.gleidsonmt.dashboard.global.material.icon.IconContainer;
import io.github.gleidsonmt.dashboard.global.material.icon.Icons;
import io.github.gleidsonmt.dashboard.global.model.Model;
import io.github.gleidsonmt.dashboard.global.plugin.ViewManager;
import io.github.gleidsonmt.dashboard.global.properties.*;
import io.github.gleidsonmt.dashboard.module.app.ConfigApp;
import io.github.gleidsonmt.dashboard.module.controls.LayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/09/2020
 */
public class DrawerNavigate implements Initializable, ActionView {

    @FXML
    private VBox drawerBox;
    @FXML
    private HBox searchBox;
    @FXML
    private Hyperlink language;
    @FXML
    private ChoiceBox<Label> choiceLanguage;
    @FXML
    private Label title;
    @FXML
    private Button btn_settings;

    @FXML
    private GNAvatarView avatarView;

    private io.github.gleidsonmt.dashboard.module.controls.LayoutController layoutController;

    private Properties properties = new Properties();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new DrawerController(drawerBox, searchBox);

        try {
            InputStream input = new FileInputStream("app/controls.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


    }

    @FXML
    private void goDashboard() throws NavigationException {
        ViewManager.INSTANCE.setContent("dashboard");
    }

    @FXML
    private void goSales() throws NavigationException {

    }

    @FXML
    private void goDash() throws NavigationException {
        ViewManager.INSTANCE.setContent("dashboard");
    }

    @FXML
    private void goCarousel() throws NavigationException {
        ViewManager.INSTANCE.setContent("carousel");
    }

    @FXML
    private void goAreaChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("area_chart");
    }

    @FXML
    private void goBarChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("bar_chart");
    }

    @FXML
    private void goBubbleChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("bubble_chart");
    }

    @FXML
    private void goLineChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("line_chart");
    }

    @FXML
    private void goPieChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("pie_chart");
    }

    @FXML
    private void goScatter() throws NavigationException {
        ViewManager.INSTANCE.setContent("scatter_chart");
    }

    @FXML
    private void goStackedAreaChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("stacked_area_chart");
    }

    @FXML
    private void goStackedBarChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("stacked_bar_chart");
    }

    @FXML
    private void goColors() throws NavigationException {
        ViewManager.INSTANCE.setContent("color");
    }

    @FXML
    private void goPopupCreator() throws NavigationException {
        ViewManager.INSTANCE.setContent("popup-creator");
    }


    @FXML
    private void goDataTable() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_index");

    }

    @FXML
    private void goSettings() throws NavigationException {
        ViewManager.INSTANCE.setContent("settings");
    }

    @FXML
    private void goAbout() throws NavigationException {
        ViewManager.INSTANCE.setContent("about");
    }

    @FXML
    private void openOptions() {
        ContextMenu contextSettings = new ContextMenu();
        MenuItem profile = new MenuItem("Profile");
        profile.setGraphic(new IconContainer(Icons.CONTACT));
        MenuItem logout = new MenuItem("Logout");
        logout.setGraphic(new IconContainer(Icons.LOGOUT));

        contextSettings.getItems().addAll(profile, logout);

        Bounds bounds = btn_settings.localToScreen(btn_settings.getBoundsInLocal());
//
        contextSettings.show(ConfigApp.INSTANCE.getDecorator().getWindow(),
                bounds.getMaxX(), bounds.getMinY()
        );
    }

    private void configLayoutControl(Control control, List<Tab> tabs, Selector... selectors)
            throws NavigationException {

        defineLayout();

        String className = control.getClass().getSimpleName();

        ViewManager.INSTANCE.setContent("control-layout", className);

        layoutController.setControl(
                control,
                properties.getProperty(className + ".about"),
                tabs,
                selectors
        );

    }

    private void configLayoutControl(Control control, Selector... selectors) throws NavigationException {
        configLayoutControl(control, null, selectors);
    }

    private void defineLayout() {
        if (layoutController == null) {
            layoutController = (LayoutController)
                    ViewManager.INSTANCE.getController("control-layout");
        }
    }

    private Tab createContent(String text) {
        Tab first = new Tab(text);
        StackPane firstContent = new StackPane(new Button(text));
        firstContent.setStyle("-fx-background-color : -light-gray;");
        firstContent.setAlignment(Pos.CENTER);
        first.setContent(firstContent);
        return first;
    }

    @FXML
    private void goTabPane() throws NavigationException {

        TabPane tabPane = new TabPane();

        tabPane.getTabs().addAll(
                createContent("First"),
                createContent("Second"),
                createContent("Third")
        );

        tabPane.getProperties().putIfAbsent("prefix", "tab-");

        tabPane.setMinSize(500, 500);

        configLayoutControl(
                tabPane
        );

    }


    @FXML
    private void goCheckBox() throws NavigationException {
        CheckBox checkBox = new CheckBox("CheckBox");
        checkBox.getProperties().putIfAbsent("prefix", "check-");
        configLayoutControl(
                checkBox,
                new ColorSelector(
                        checkBox,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(checkBox, "small")
        );
    }

    @FXML
    private void goChoiceBox() throws NavigationException {
        ChoiceBox<String> choice = new ChoiceBox<>();
        choice.getItems().addAll("Option 01", "Option 02", "Option 03");
        choice.getProperties().putIfAbsent("prefix", "ch-");
        configLayoutControl(
                choice,
                new ColorSelector(
                        choice,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),

                new SizeSelector(choice, "small")
        );
    }

    @FXML
    private void goSplitButton() throws NavigationException {
        SplitMenuButton menuButton = new SplitMenuButton();
        menuButton.setText("Split Menu Button");
        Menu menuOptions = new Menu("Option 03");
        menuOptions.getItems().addAll(
                new MenuItem("Option 01"),
                new MenuItem("Option 02"),
                new MenuItem("Option 03")
        );
        menuButton.getItems().addAll(
                new MenuItem("Option 01"),
                new MenuItem("Option 02"),
                menuOptions,
                new MenuItem("Option 04")
        );

        menuButton.getProperties().putIfAbsent("prefix", "smb-");
        configLayoutControl(
                menuButton,
                new ColorSelector(
                        menuButton,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(menuButton, "small")
        );
    }


    @FXML
    private void goMenuButton() throws NavigationException {

        MenuButton menuButton = new MenuButton("Menu Button");
        Menu menuOptions = new Menu("Option 03");
        menuOptions.getItems().addAll(
                new MenuItem("Option 01"),
                new MenuItem("Option 02"),
                new MenuItem("Option 03")
        );
        menuButton.getItems().addAll(
                new MenuItem("Option 01"),
                new MenuItem("Option 02"),
                menuOptions,
                new MenuItem("Option 04")
        );
        menuButton.getProperties().putIfAbsent("prefix", "mb-");
        configLayoutControl(
                menuButton,
                new ColorSelector(
                        menuButton,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(menuButton, "small")
        );
    }

    @FXML
    private void goSpinner() throws NavigationException {

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        spinner.getProperties().putIfAbsent("prefix", "spn-");
        configLayoutControl(
                spinner,
                new ColorSelector(
                        spinner,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(spinner, "small")
        );
    }

    @FXML
    private void goPagination() throws NavigationException {
        Pagination pagination = new Pagination();
        pagination.getProperties().putIfAbsent("prefix", "pag-");
        configLayoutControl(
                pagination,
                new ColorSelector(
                        pagination,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                )

        );

    }


    @FXML
    private void goButton() throws NavigationException {

        Button button = new Button("Button");
//        button.setGraphic(iconCopy);
        button.setPrefSize(80, 60);

        button.getProperties().putIfAbsent("prefix", "btn-");

        configLayoutControl(
                button,
                Collections.singletonList(
                        createTabSkin(
                                ViewManager.INSTANCE.getRoot("button-skin")
                        )
                ),
                new TypeSelector(button, "Skin", "Centralize", "Ripple"),
                new TypeSelector(button, "Type", "Round"),
                new SizeSelector(button, "medium")
        );
    }

    @FXML
    private void goLabel() throws NavigationException {

        Label label = new Label("Label");

//        button.setGraphic(iconCopy);
        label.setPrefSize(80, 60);

        label.getProperties().putIfAbsent("prefix", "lbl-");

        configLayoutControl(
                label,

                new ColorSelector(
                        label,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(label, "medium")
        );
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void gotTableView() throws NavigationException {

        TableView<Model> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setMinWidth(250);

        Model model = new Model();
        model.setId(0);
        model.setName("Jhon");

        Model model1 = new Model();
        model1.setId(1);
        model1.setName("Katerine");

        Model model2 = new Model();
        model2.setId(2);
        model2.setName("Rachel");

        TableColumn<Model, Number> idColumn = new TableColumn<>("#");
        TableColumn<Model, String> nameColumn = new TableColumn<>("Name");

        tableView.getColumns().addAll(idColumn, nameColumn);
        tableView.getItems().addAll(model, model1, model2);

        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());

//        button.setGraphic(iconCopy);
        tableView.getProperties().putIfAbsent("prefix", "tbv-");

        configLayoutControl(
                tableView,

                new ColorSelector(
                        tableView,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                )
        );
    }

    @FXML
    private void goTextField() throws NavigationException {

        GNTextField textField = new GNTextField();
        textField.setPromptText("Text Field");

//        textField.setFloatPrompt(true);
//        textField.setVisibleCount(true);
//        textField.setMaxLength(30);

        textField.getProperties().putIfAbsent("prefix", "tf-");

        defineLayout();

        configLayoutControl(
                textField,
                Collections.singletonList(
                        createTabImplementation(
                                ViewManager.INSTANCE.getRoot("text-field-implement"))
                ),
                new ColorSelector(textField),
                new CheckSelector(textField,
                        "Float", "Counter", "Helper", "Lead Icon", "Action Clear"),
                new TypeSelector(textField, "Type", "Filled"),
                new TypeSelector(textField,
                        "Validator", "Warning", "Error", "Checked")

        );
    }

    @FXML
    private void goPasswordField() throws NavigationException {
        PasswordField textField = new PasswordField();
        textField.setPromptText("Password Field");
        textField.setPrefSize(200D, 40D);

        textField.getProperties().putIfAbsent("prefix", "pw-");

        defineLayout();

        configLayoutControl(
                textField,
                new CheckSelector(textField,
                        "Float", "Lead Icon", "Action Button"
                ),
                new SizeSelector(textField, "medium")


        );
    }


    @FXML
    private void goTextArea() throws NavigationException {
        GNTextArea textArea = new GNTextArea();

        textArea.setPromptText("Text Area");

        textArea.setPrefSize(200D, 100);

        textArea.getProperties().putIfAbsent("prefix", "ta-");

        defineLayout();

        configLayoutControl(
                textArea,
                new ColorSelector(textArea),
                new CheckSelector(textArea,
                        "Float"
                ),
                new TypeSelector(textArea, "Type", "Filled")
        );
    }

    @FXML
    private void goComboBox() throws NavigationException {

        GNComboBox<String> comboBox = new GNComboBox<>();
        comboBox.setPromptText("Select an option");
        comboBox.setEditable(true);

        ObservableList<String> items = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten");

        comboBox.setItems(items);

        comboBox.setFiltered(true);
//
        comboBox.getProperties().putIfAbsent("prefix", "cmb-");
        defineLayout();

        configLayoutControl(
                comboBox,
                new ColorSelector(
                        comboBox,
                        "Gray", "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new SizeSelector(comboBox, "medium")


        );
    }

    @FXML
    private void goLogin() throws NavigationException {
        ViewManager.INSTANCE.navigate("login");
    }


    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    private Tab createTabSkin(Parent content) {
        Tab tab = new Tab("Skins");
        SVGPath icon = new SVGPath();
        icon.getStyleClass().add("icon");
        icon.setContent("M11.99 18.54l-7.37-5.73L3 14.07l9 7 9-7-1.63-1.27zM12 16l7.36-5.73L21 9l-9-7-9 7 1.63 1.27L12 16zm0-11.47L17.74 9 12 13.47 6.26 9 12 4.53z");
        tab.setGraphic(icon);
        tab.setContent(content);
        return tab;
    }

    private Tab createTabImplementation(Parent content) {
        Tab tab = new Tab("Implementation");
        SVGPath icon = new SVGPath();
        icon.getStyleClass().add("icon");

        icon.setContent("M9.4 16.6L4.8 12l4.6-4.6L8 6l-6 6 6 6 1.4-1.4zm5.2 0l4.6-4.6-4.6-4.6L16 6l6 6-6 6-1.4-1.4z");
        tab.setGraphic(icon);
        tab.setContent(content);
        return tab;
    }
}
