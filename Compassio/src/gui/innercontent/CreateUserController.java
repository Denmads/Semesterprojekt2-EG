/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.innercontent;

import gui.GUIrun;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTypes = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();
        
        String[] types = GUIrun.getLogic().getUserTypes();
        for (String t : types) {
            userTypes.add(t);
        }
        
        ArrayList<String> deps = GUIrun.getLogic().getDepartmentInfo();
        deps.remove(deps.size()-1);
        
        for (String d : deps) {
            String[] tokens = d.split(" ");
            departments.add(tokens[0]);
        }
    }    

    @FXML
    private void createUser(ActionEvent event) {
    
        if (validateInput()) {
            //Create user - everything is good
        }
    }
    
    private boolean validateInput () {
        if (txtFirstName.getText().length() == 0 || txtLastName.getText().length() == 0 || txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0) {
            errorLabel.setText("Alle felter skal være udfyldt!");
            return false;
        }
        
        if (txtUsername.getText().length() < 4) {
            errorLabel.setText("Brugernavnet skal minimum være 4 tegn!");
            return false;
        }
        
        if (txtPassword.getText().length() < 4) {
            errorLabel.setText("Koden skal minimum være 4 tegn!");
            return false;
        }
        
        return true;
    }
}
