/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_menu.setVisible(false);
        menu_click.setVisible(true);
        menu_close.setVisible(false);
    }    

    @FXML
    private void user_menu_slide(MouseEvent event) {
        user_menu.setVisible(true);
        menu_click.setVisible(false);
        menu_close.setVisible(true);
    }

    @FXML
    private void user_menu_close(MouseEvent event) {
        user_menu.setVisible(false);
        menu_click.setVisible(true);
        menu_close.setVisible(false);
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
    
}
