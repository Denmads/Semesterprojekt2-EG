/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.innercontent;

import gui.GUIrun;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author madsh
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private AnchorPane changePasswordAnchor;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private Label changePasswordErrorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clearButton(ActionEvent event) {
        changePasswordErrorLabel.setTextFill(Color.RED);
        newPasswordField.clear();
        repeatPasswordField.clear();
        oldPasswordField.clear();
        changePasswordErrorLabel.setText("");
    }

    @FXML
    private void changePasswordButton(ActionEvent event) {
        changePasswordErrorLabel.setTextFill(Color.RED);
        if (newPasswordField.getText().length() < 8) {
            changePasswordErrorLabel.setText("Adganskode skal være minimum 8 karakterer");
            return;
        }
        if (!newPasswordField.getText().equals(repeatPasswordField.getText())) {
            changePasswordErrorLabel.setText("De nye adgangskoder er ikke ens");
            return;
        }
        if (GUIrun.getLogic().changePassword(newPasswordField.getText(), oldPasswordField.getText())) {
            clearButton(event);
            changePasswordErrorLabel.setTextFill(Color.BLACK);
            changePasswordErrorLabel.setText("Adgangskode er blevet ændret");
        } else {
            changePasswordErrorLabel.setText("Forkert adgangskode");
        }
    }
    
}
