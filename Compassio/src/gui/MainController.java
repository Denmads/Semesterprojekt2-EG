/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Case;

/**
 * FXML Controller class
 *
 * @author L530
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane user_menu;
    @FXML
    private ImageView menu_close;
    @FXML
    private ImageView menu_click;

    GUIHandler guih = new GUIHandler();

    @FXML
    private ListView<Case> listview_cases;
    private ObservableList<Case> viewableCases;
    private FilteredList<Case> filteredCases;

    @FXML
    private AnchorPane see_cases_ancher;
    @FXML
    private AnchorPane create_case;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> caseType;
    private ObservableList<String> caseTypes;
    private ArrayList<Case> cases;
    
    @FXML
    private Label user_name;
    @FXML
    private Label user_role;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visibleMenu();
        visibleCases();
        
        updateCases();
        
        //Add first and lastname, and role to hamburger menu. 
        this.user_name.setText(GUIrun.getLogic().getUserName());
        String role = GUIrun.getLogic().getUserType();
        //Capitalize first letter, lowercase the rest
        role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
        this.user_role.setText(role);

        listview_cases.setCellFactory(view -> new GUICaseCell());

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCaseFilter();
            }
        });

        caseTypes = FXCollections.observableArrayList();
        caseTypes.add("All");
        caseTypes.addAll(GUIrun.getLogic().retrieveCaseTypes());
        caseType.setItems(caseTypes);
        caseType.getSelectionModel().select(0);

        caseType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCaseFilter();
            }
        });
    }

    private void updateCaseFilter() {
        Predicate<Case> text = c -> {
            String input = searchField.getText().trim().toLowerCase();
            String fullName = c.getFirstName().toLowerCase() + " " + c.getLastName().toLowerCase();
            String cpr = "" + c.getCprNumber();
            String caseNumber = "" + c.getCaseID();

            if (searchField.getText().trim().length() == 0) {
                return true;
            }

            return fullName.startsWith(input) || cpr.startsWith(input) || caseNumber.startsWith(input);
        };
        Predicate<Case> type = c -> {
            if (caseType.getSelectionModel().getSelectedIndex() == 0) {
                return true;
            }

            return c.getType().equals(caseType.getValue());
        };

        filteredCases.setPredicate(text.and(type));

    }

    @FXML
    private void user_menu_close(MouseEvent event) {
        visibleMenu();
    }

    @FXML
    private void user_menu_slide(MouseEvent event) {
        visibleMenu();
        user_menu.setVisible(true);
        menu_click.setVisible(false);
        menu_close.setVisible(true);
    }

    private void visibleMenu() {
        user_menu.setVisible(false);
        menu_click.setVisible(true);
        menu_close.setVisible(false);
    }

    private void visibleCases() {
        see_cases_ancher.setVisible(false);
        create_case.setVisible(false);
    }

    @FXML
    private void minimise(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void buttonBack(MouseEvent event) {
        //are u sure u want to log out.. then cng.changeFXMLAction("/gui/login.fxml", event); or change to front page start_user_login.fxml
    }

    @FXML
    private void user_logout(ActionEvent event) throws IOException {
        guih.changeFXMLAction("/gui/login.fxml", event);
    }

    @FXML
    private void createCase(ActionEvent event) {
        visibleCases();
        see_cases_ancher.setVisible(false);
        create_case.setVisible(true);
        visibleMenu();
    }

    @FXML
    private void seeCases(ActionEvent event) {
        visibleCases();
        updateCases();
        see_cases_ancher.setVisible(true);
        create_case.setVisible(false);
        visibleMenu();
    }

    @FXML
    private void changePassword(ActionEvent event) {
    }

    private void updateCases() {
        cases = GUIrun.getLogic().getCases();

        viewableCases = FXCollections.observableArrayList(cases);
        filteredCases = new FilteredList<>(viewableCases, p -> true);
        listview_cases.setItems(filteredCases);
    }

    public void openCase(MouseEvent event) {

    }

}
