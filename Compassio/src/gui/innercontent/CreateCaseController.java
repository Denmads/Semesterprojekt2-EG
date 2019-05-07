/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.innercontent;

import gui.GUIrun;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author madsh
 */
public class CreateCaseController implements Initializable {

    @FXML
    private AnchorPane create_case;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField CPRField;
    @FXML
    private ChoiceBox<String> caseTypeChoiceBox;
    private ObservableList<String> caseTypeChoices;
    @FXML
    private TextArea mainBodyArea;
    @FXML
    private TextField userIDTextField;
    @FXML
    private Button addSocialWorkerBtn;
    private ArrayList<String> addedUsers;
    @FXML
    private ChoiceBox<String> departmentBox;
    private ObservableList<String> departmentTypes;
    @FXML
    private TextArea inquiryArea;
    @FXML
    private Button editButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departmentTypes = FXCollections.observableArrayList(GUIrun.getLogic().getDepartmentInfo());
        departmentBox.setItems(departmentTypes);
        caseTypeChoices = FXCollections.observableArrayList(GUIrun.getLogic().retrieveCaseTypes());
        caseTypeChoiceBox.setItems(caseTypeChoices);
        addedUsers = new ArrayList<>();
    }    

    @FXML
    private void addSocialWorker(ActionEvent event) {
        if (GUIrun.getLogic().checkUserID(userIDTextField.getText()) && !userIDTextField.getText().equals(GUIrun.getLogic().getUserID())) {
            addedUsers.add(userIDTextField.getText());
            userIDTextField.setText("Tilføjet socialarbejder");
        } else {
            userIDTextField.setText("Forkert indtastet brugerID");
        }
    }

    @FXML
    private void createCaseButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if (CPRField.getText().trim().length() == 10) {
                String[] departmentInfo = departmentBox.getValue().split(" ");
                int departmentID = Integer.parseInt(departmentInfo[0]);
                if (GUIrun.getLogic().createCase(firstNameField.getText().trim(), lastNameField.getText().trim(), Long.parseLong(CPRField.getText().trim()),
                        caseTypeChoiceBox.getValue(), mainBodyArea.getText().trim(), LocalDate.now(), null, departmentID, inquiryArea.getText().trim(), addedUsers)) {
                    alert.setContentText("Sag oprettet");
                    alert.showAndWait();
                    clearCreateCase();
//                    accessToCreateCase(false);

                } else {
                    alert.setContentText("Fejl! Sagen kunne ikke oprettes");
                    alert.showAndWait();
                }

            } else {
                alert.setContentText("CPR nummer skal være 10 numre langt");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            alert.setContentText("CPR må kun indeholde numre");
            alert.showAndWait();
        } catch (NullPointerException e) {
            alert.setContentText("Vælg både bostedsafdeling og sagstype");
            alert.showAndWait();
        }
    }
    
    private void accessToCreateCase(boolean editable) {
        firstNameField.setEditable(editable);
        lastNameField.setEditable(editable);
        CPRField.setEditable(editable);
        mainBodyArea.setEditable(editable);
        inquiryArea.setEditable(editable);
        departmentBox.setDisable(!editable);
        caseTypeChoiceBox.setDisable(!editable);
        userIDTextField.setDisable(!editable);
        addSocialWorkerBtn.setDisable(!editable);
    }
    
    private void clearCreateCase() {
        firstNameField.clear();
        lastNameField.clear();
        CPRField.clear();
        mainBodyArea.clear();
        inquiryArea.clear();
        userIDTextField.clear();
        caseTypeChoiceBox.getSelectionModel().clearSelection();
        departmentBox.getSelectionModel().clearSelection();

    }
}
