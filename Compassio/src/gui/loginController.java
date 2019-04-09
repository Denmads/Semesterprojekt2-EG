/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Peterzxcvbnm
 */
public class loginController implements Initializable {

    @FXML
    private GridPane login_grid;
    @FXML
    private GridPane start_grid;

    GUIHandler guih = new GUIHandler(){};
    @FXML
    private TextField user_textfield;
    @FXML
    private PasswordField password_passwordsFiled;
    @FXML
    private Label label_check;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visible();
        password_passwordsFiled.setPromptText("Din adgangskode");
        user_textfield.setPromptText("Dit brugernavn");
    }
    
    public void setupDragWindow (Stage stage) {
        guih.moves(stage, stage.getScene());
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

    public void visible() {
        start_grid.setVisible(true);
        login_grid.setVisible(false);
    }

    @FXML
    private void buttonBack(MouseEvent event) throws IOException {
        guih.changeFXMLMouse("/gui/login.fxml", event);
    }

    @FXML
    private void start_button(ActionEvent event) {
        visible();
        start_grid.setVisible(false);
        login_grid.setVisible(true);
    }

    @FXML
    private void login_button(ActionEvent event) throws IOException {
        if (user_textfield.getText().equals("") || password_passwordsFiled.getText().equals("")) {
            label_check.setText("Du har ikke skrevet noget!");
            label_check.setTextFill(Color.rgb(210, 39, 30));
        } else {
            guih.changeFXMLAction("/gui/start_user_login.fxml", event);
        }
    }

}
