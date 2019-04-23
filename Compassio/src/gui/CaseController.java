/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Case;

/**
 * FXML Controller class
 *
 * @author Peterzxcvbnm
 */
public class CaseController implements Initializable {
    
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
    private TextArea mainBodyArea;
    @FXML
    private DatePicker dateCreatedField;
    @FXML
    private DatePicker closedDateField;
    @FXML
    private ChoiceBox<Integer> departmentBox;
    @FXML
    private TextArea inquiryArea;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button closeButton;
    
    ArrayList<Node> editableFields;
    Case currentCase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editableFields = new ArrayList<>();
        editableFields.add(caseTypeChoiceBox);
        editableFields.add(mainBodyArea);
        editableFields.add(closedDateField);
        editableFields.add(departmentBox);
        editableFields.add(inquiryArea);
        
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
    private void editButton(ActionEvent event) {
        Button source = (Button) event.getSource();
        if (source.getText().equals("Rediger")) {
            source.setText("Gem");
            editableFields.forEach(nodes -> {
                nodes.setDisable(false);
            });
            
            cancelButton.setVisible(true);
        } else if (source.getText().equals("Gem")) {
            currentCase.setDateClosed(java.util.Date.from(closedDateField.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault()).toInstant()));
            currentCase.saveCase();
        }
    }
    
    @FXML
    private void cancelButton(ActionEvent event) {
    }
    
    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void injectCase(Case currentCase) {
        this.currentCase = currentCase;
        setupCase();
    }
    
    private void setupCase() {
        firstNameField.setText(currentCase.getFirstName());
        lastNameField.setText(currentCase.getLastName());
        caseIDField.setText(currentCase.getCaseID().toString());
        caseTypeChoiceBox.getItems().addAll(GUIrun.getLogic().retrieveCaseTypes());
        caseTypeChoiceBox.getSelectionModel().select(currentCase.getType());
        mainBodyArea.setText(currentCase.getMainBody());
        if (currentCase.getDateCreated() != null) {
            LocalDate date = LocalDate.parse(currentCase.getDateCreated().toString());
            dateCreatedField.setValue(date); //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
        if (currentCase.getDateClosed() != null) {
            LocalDate date = LocalDate.parse(currentCase.getDateClosed().toString());
            closedDateField.setValue(date);//.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
        departmentBox.getItems().add(currentCase.getDepartmentID());
        departmentBox.getSelectionModel().selectFirst();
        inquiryArea.setText(currentCase.getInquiry());
    }
    
}
