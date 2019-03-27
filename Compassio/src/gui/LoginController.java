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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Peterzxcvbnm
 */
public class LoginController implements Initializable {

    @FXML
    private GridPane login_grid;
    @FXML
    private GridPane start_grid;

    ChangeFXML cng = new ChangeFXML(){};
    @FXML
    private TextField user_textfield;
    @FXML
    private TextField password_textfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visible();
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
        cng.changeFXMLMouse("/gui/login.fxml", event);
    }

    @FXML
    private void start_button(ActionEvent event) {
        visible();
        start_grid.setVisible(false);
        login_grid.setVisible(true);
    }

    @FXML
    private void login_button(ActionEvent event) throws IOException {
        cng.changeFXMLAction("/gui/start_user_login.fxml", event);
    }

}
