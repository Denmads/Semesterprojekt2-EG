package gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Case;

/**
 * FXML Controller class
 *
 * @author L530
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane user_menu;
    @FXML
    private ImageView menu_close;
    @FXML
    private ImageView menu_click;
    @FXML
    private VBox buttons;

    GUIHandler guih = new GUIHandler();

    @FXML
    private Label userName;
    @FXML
    private Label userRole;
    
    @FXML
    private GridPane content;
    private Parent currentContent;
    
    @FXML
    private Label lblWelcome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visibleMenu();
 
        loadButtons();

        //Add first and lastname, and role to hamburger menu. 
        this.userName.setText(GUIrun.getLogic().getUserName());
        String role = GUIrun.getLogic().getUserType();
        //Capitalize first letter, lowercase the rest
        role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
        this.userRole.setText(role);
        user_menu_open(null);
    }

    private void loadButtons () {
        try {
            //Buttons
            Button btnCreateCase = createButton("Opret sag", "createCase");
            Button btnSeeCases = createButton("Se sager", "seeCases");
            Button btnChangePassword = createButton("Ændre password", "changePassword");
            Button btnCreateUser = createButton("Opret bruger", null);
            Button btnEditUsers = createButton("Rediger brugere", "editUsers");
            
            //Won't do buttons
            Button btnPlanning = createButton("Planlægning", null);
            Button btnDiary = createButton("Dagbog", null);
            Button btnSeeInformation = createButton("Se Oplysninger", null);
            Button btnRequestCase = createButton("Anmod om sag", null);
            
            String type = GUIrun.getLogic().getUserType();
            
            if (type.equals("ADMIN")) {
                buttons.getChildren().addAll(btnCreateUser, btnEditUsers, btnChangePassword, btnSeeInformation);
            }
            else if (type.equals("CASEWORKER")) {
                buttons.getChildren().addAll(btnCreateCase, btnSeeCases, btnChangePassword, btnSeeInformation);
            }
            else if (type.equals("SOCIALWORKER")) {
                buttons.getChildren().addAll(btnSeeCases, btnChangePassword, btnPlanning, btnDiary, btnSeeInformation);
            }
            else if (type.equals("USER")) {
                buttons.getChildren().addAll(btnSeeInformation, btnRequestCase);
            }
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }
    
    private Button createButton (String text, String fxmlName) throws NoSuchMethodException {
            Button newBtn = new Button(text);
            newBtn.getStyleClass().add("btn");
            newBtn.setPrefWidth(130);
            
            if (fxmlName != null) {
                Method m = getClass().getMethod("loadContent", String.class);
                newBtn.setOnAction(event -> {
                    try {
                        m.invoke(this, fxmlName + ".fxml");
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        Platform.exit();
                    }
                });
            }
            return newBtn;
    }
    
    

    @FXML
    private void user_menu_close(MouseEvent event) {
        visibleMenu();
    }

    @FXML
    private void user_menu_open(MouseEvent event) {
        visibleMenu();
        user_menu.setVisible(true);
        menu_click.setVisible(false);
        menu_close.setVisible(true);
    }

    private void visibleMenu() {
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

    @FXML
    private void user_logout(ActionEvent event) throws IOException {
        guih.changeFXMLAction("/gui/login.fxml", event);
    }
    
    public void loadContent (String fxmlName) throws IOException {
        lblWelcome.setVisible(false);
        
        if (currentContent != null) {
            content.getChildren().remove(currentContent);
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("innercontent/" + fxmlName));        
        content.add(root, 1, 0, 3, 3);
        currentContent = root;
        user_menu_close(null);
    }
}
