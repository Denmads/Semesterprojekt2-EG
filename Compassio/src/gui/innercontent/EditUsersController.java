/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.innercontent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author madsh
 */
public class EditUsersController implements Initializable {

    @FXML
    private AnchorPane editUsers;
    @FXML
    private TextField searchField1;
    @FXML
    private ListView<String> usersListview;
    @FXML
    private Label userInformation;
    @FXML
    private ChoiceBox<String> chbUserRole;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeUserState(ActionEvent event) {
    }
    
}
