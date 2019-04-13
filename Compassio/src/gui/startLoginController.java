/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
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

import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
public class startLoginController implements Initializable {

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

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField caseIDField;
    @FXML
    private TextField CPRField;
    @FXML
    private ChoiceBox<String> caseTypeChoiceBox;
    @FXML
    private ChoiceBox<Integer> departmentBox;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea mainBodyArea;
    @FXML
    private DatePicker dateCreatedField;
    @FXML
    private DatePicker closedDateField;
    @FXML
    private TextArea inquiryArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visibleMenu();
        visibleCases();

        ArrayList<Case> testCases = new ArrayList<>();
//        testCases.add(new Case("John", "Lars Larsen", 1234, 1234569999L, "Handicap", "", Calendar.getInstance().getTime(), null, 1, ""));
//        testCases.add(new Case("John", "Ole Larsen", 1235, 2234569999L, "Handicap", "", Calendar.getInstance().getTime(), null, 1, ""));
//        testCases.add(new Case("Lone", "Borgersen", 1236, 3112191111L, "Ældre", "", Calendar.getInstance().getTime(), new Date(System.currentTimeMillis() + 123456), 1, ""));

        viewableCases = FXCollections.observableArrayList(testCases);
        filteredCases = new FilteredList<>(viewableCases, p -> true);
        listview_cases.setItems(filteredCases);

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
        accessToCreateCase(true);

    }

    @FXML
    private void seeCases(ActionEvent event) {
        visibleCases();
        see_cases_ancher.setVisible(true);
        create_case.setVisible(false);
        visibleMenu();
    }

    @FXML
    private void changePassword(ActionEvent event) {
    }

    @FXML
    private void createCaseButton(ActionEvent event) {
        LocalDate localDate = dateCreatedField.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date dateCreated = Date.from(instant);

        localDate = closedDateField.getValue();
        instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date dateClosed = Date.from(instant);
        
        GUIrun.getLogic().createCase(firstNameField.getText(), lastNameField.getText(), Long.parseLong(CPRField.getText()), 
                caseTypeChoiceBox.getValue(), mainBodyArea.getText(), dateCreated, dateClosed, departmentBox.getValue(), inquiryArea.getText(), null);

    }

    @FXML
    private void cancelButton(ActionEvent event) {
    }

    @FXML
    private void close(ActionEvent event) {
    }

    private void accessToCreateCase(boolean editable) {
        firstNameField.setEditable(editable);
        lastNameField.setEditable(editable);
        caseIDField.setEditable(editable);
        CPRField.setEditable(editable);
        mainBodyArea.setEditable(editable);
        dateCreatedField.setEditable(editable);
        closedDateField.setEditable(editable);
        inquiryArea.setEditable(editable);
    }

}
