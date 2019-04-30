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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.UserInfo;

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
    private ListView<UserInfo> usersListview;
    private ObservableList<UserInfo> users;
    @FXML
    private Label userInformation;
    @FXML
    private ChoiceBox<String> chbUserRole;
    private ObservableList<String> roles;
    @FXML
    private Button btnUserState;
    private boolean state;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        users = FXCollections.observableArrayList();
        usersListview.setItems(users);
        roles = FXCollections.observableArrayList();
        chbUserRole.setItems(roles);
        setEditDisable(true);

        String[] r = GUIrun.getLogic().getUserTypes();

        for (String role : r) {
            if (role.equals("UNKNOWN")) {
                continue;
            }
            
            roles.add(role);
        }
        chbUserRole.getSelectionModel().select(0);

        updateList();

        usersListview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserInfo>() {
            @Override
            public void changed(ObservableValue<? extends UserInfo> observable, UserInfo oldValue, UserInfo newValue) {
                if (newValue != null) {
                    setEditDisable(false);
                    userInformation.setText(newValue.toString());
                    chbUserRole.getSelectionModel().select(newValue.getType());

                    state = newValue.isInactive();

                    if (state) {
                        btnUserState.setText("Reactivate user");
                    } else {
                        btnUserState.setText("Disable user");
                        btnUserState.setStyle("-fx-background-color: #00cc00");
                    }
                }
            }
        });
    }

    private void updateList() {
        new Thread(() -> {
            ArrayList<UserInfo> u = GUIrun.getLogic().getAllUsers();

            users.addAll(u);
        }).start();
    }

    private void setEditDisable(boolean state) {
        chbUserRole.setDisable(state);
        btnUserState.setDisable(state);
        btnSave.setDisable(state);
    }

    @FXML
    private void changeUserState(ActionEvent event) {
        state = !state;
        
        if (state) {
            btnUserState.setText("Reactivate user");
            btnUserState.setStyle("-fx-background-color: #cc0000");
        } else {
            btnUserState.setText("Disable user");
            btnUserState.setStyle("-fx-background-color: #00cc00");
        }
    }

    @FXML
    private void save(ActionEvent event) {
    }

}
