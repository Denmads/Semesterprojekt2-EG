/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    GUIHandler guih = new GUIHandler() {
    };
    @FXML
    private TextField user_textfield;
    @FXML
    private PasswordField password_passwordsFiled;
    @FXML
    private Label label_check;
    @FXML
    private VBox loadingOverlay;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        visible();
        password_passwordsFiled.setPromptText("Din adgangskode");
        user_textfield.setPromptText("Dit brugernavn");
    }

    public void setupDragWindow(Stage stage) {
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
        loadingOverlay.setVisible(false);
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

    private void setLoadingOverlay(boolean show) {
        if (show) {
            loadingOverlay.setVisible(true);
            login_grid.setEffect(new GaussianBlur());
            user_textfield.setDisable(true);
            password_passwordsFiled.setDisable(true);
        } else {
            loadingOverlay.setVisible(false);
            login_grid.setEffect(null);
            user_textfield.setDisable(false);
            password_passwordsFiled.setDisable(false);
        }
    }

    @FXML
    private void login_button(ActionEvent event) throws IOException, InterruptedException {
        setLoadingOverlay(true);

        new Thread(() -> {
            if (!GUIrun.getLogic().login(user_textfield.getText(), password_passwordsFiled.getText())) {
                Platform.runLater(() -> {
                    label_check.setText("Ugyldigt brugernavn/password!");
                    label_check.setTextFill(Color.rgb(210, 39, 30));
                });
            } else {
                Platform.runLater(() -> {
                    try {
                        guih.changeFXMLAction("/gui/main.fxml", event);
                    } catch (IOException ex) {
                        Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            
            }
            
            Platform.runLater(() -> {
                setLoadingOverlay(false);
            });
            
            
        }).start();

    }
}
