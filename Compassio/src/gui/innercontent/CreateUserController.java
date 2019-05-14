/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.innercontent;

import gui.GUIrun;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author madsh
 */
public class CreateUserController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ChoiceBox<String> chbType;
    private ObservableList<String> userTypes;
    @FXML
    private ChoiceBox<String> chbDepartment;
    private ObservableList<String> departments;
    private HashMap<String, Integer> departmentMap;
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTypes = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();
        chbType.setItems(userTypes);
        chbDepartment.setItems(departments);
        departmentMap = new HashMap<>();
        
        String[] types = GUIrun.getLogic().getUserTypes();
        for (String t : types) {
            userTypes.add(t);
        }
        
        ArrayList<String> deps = GUIrun.getLogic().getDepartmentInfo();
        
        for (String d : deps) {
            String[] tokens = d.split(" ");
            departments.add(tokens[1]);
            departmentMap.put(tokens[1], Integer.parseInt(tokens[0]));
        }
        
        chbType.getSelectionModel().selectFirst();
        chbDepartment.getSelectionModel().selectFirst();
    }    

    @FXML
    private void createUser(ActionEvent event) {
    
        if (validateInput()) {
            //Create user - everything is good
            GUIrun.getLogic().createUser(txtFirstName.getText(), txtLastName.getText(), txtUsername.getText(), txtPassword.getText(), chbType.getSelectionModel().getSelectedItem(), departmentMap.get(chbDepartment.getSelectionModel().getSelectedItem()));
            clear();
        }
    }
    
    private boolean validateInput () {
        if (txtFirstName.getText().length() == 0 || txtLastName.getText().length() == 0 || txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0) {
            errorLabel.setText("Alle felter skal være udfyldt!");
            errorLabel.setTextFill(Color.RED);
            return false;
        }
        
        if (txtUsername.getText().length() < 4) {
            errorLabel.setText("Brugernavnet skal minimum være 4 tegn!");
            errorLabel.setTextFill(Color.RED);
            return false;
        }
        
        if (txtPassword.getText().length() < 4) {
            errorLabel.setText("Koden skal minimum være 4 tegn!");
            errorLabel.setTextFill(Color.RED);
            return false;
        }
        
        return true;
    }
    
    private void clear () {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        chbDepartment.getSelectionModel().selectFirst();
        chbType.getSelectionModel().selectFirst();
        
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText("Bruger oprettet!");
    }
}
