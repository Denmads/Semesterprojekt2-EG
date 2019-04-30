package gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    GUIHandler guih = new GUIHandler();

    @FXML
    private ListView<Case> listview_cases;

    private ObservableList<Case> viewableCases;
    private FilteredList<Case> filteredCases;

    @FXML
    private AnchorPane see_cases_ancher;
    @FXML
    private AnchorPane create_case;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> caseType;
    private ObservableList<String> caseTypes;
    private ArrayList<Case> cases;
    
    @FXML
    private Label userName;
    @FXML
    private Label userRole;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField caseIDField;
    @FXML
    private TextField CPRField;
    @FXML
    private ChoiceBox<String> caseTypeChoiceBox;
    private ObservableList<String> caseTypeChoices;
    @FXML
    private ChoiceBox<String> departmentBox;
    private ObservableList<String> departmentTypes;
    @FXML
    private VBox buttons;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea mainBodyArea;
    @FXML
    private TextArea inquiryArea;
    @FXML
    private HBox departmentPlace;
    @FXML
    private TextField userIDTextField;
    @FXML
    private Button addSocialWorkerBtn;

    private ArrayList<String> addedUsers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visibleMenu();
        visibleCases();
        
        loadButtons();
        updateCases();
        
        //Add first and lastname, and role to hamburger menu. 
        this.userName.setText(GUIrun.getLogic().getUserName());
        String role = GUIrun.getLogic().getUserType();
        //Capitalize first letter, lowercase the rest
        role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
        this.userRole.setText(role);

        listview_cases.setCellFactory(view -> new GUICaseCell());

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCaseFilter();
            }
        });

        caseTypes = FXCollections.observableArrayList();
        caseTypes.add("All");
        caseTypes.addAll(GUIrun.getLogic().retrieveCaseTypes());
        caseType.setItems(caseTypes);
        caseType.getSelectionModel().select(0);

        caseType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCaseFilter();
            }
        });

        departmentTypes = FXCollections.observableArrayList(GUIrun.getLogic().getDepartmentInfo());
        departmentBox.setItems(departmentTypes);
        caseTypeChoices = FXCollections.observableArrayList(GUIrun.getLogic().retrieveCaseTypes());
        caseTypeChoiceBox.setItems(caseTypeChoices);
        addedUsers = new ArrayList<>();
    }

    private void loadButtons () {
        try {
            //Buttons
            Button btnCreateCase = createButton("Opret sag", "createCase");
            Button btnSeeCases = createButton("Se sager", "seeCases");
            Button btnChangePassword = createButton("Ændre password", null);
            Button btnCreateUser = createButton("Opret bruger", null);
            Button btnEditUsers = createButton("Rediger brugere", null);
            
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
    
    private Button createButton (String text, String methodName) throws NoSuchMethodException {
            Button newBtn = new Button(text);
            newBtn.getStyleClass().add("btn");
            newBtn.setPrefWidth(130);
            
            if (methodName != null) {
                Method m = getClass().getMethod(methodName);
                newBtn.setOnAction(event -> {
                    try {
                        m.invoke(this);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        Platform.exit();
                    }
                });
            }
            return newBtn;
    }
    
    private void updateCaseFilter() {
        Predicate<Case> text = c -> {
            String input = searchField.getText().trim().toLowerCase();
            String fullName = c.getFirstName().toLowerCase() + " " + c.getLastName().toLowerCase();
            String cpr = "" + c.getCprNumber();
            String caseNumber = "" + c.getCaseID();

            if (searchField.getText().trim().length() == 0) {
                return true;
            }

            return fullName.startsWith(input) || cpr.startsWith(input) || caseNumber.startsWith(input);
        };
        Predicate<Case> type = c -> {
            if (caseType.getSelectionModel().getSelectedIndex() == 0) {
                return true;
            }

            return c.getType().equals(caseType.getValue());
        };

        filteredCases.setPredicate(text.and(type));

    }

    @FXML
    private void user_menu_close(MouseEvent event) {
        visibleMenu();
    }

    @FXML
    private void user_menu_slide(MouseEvent event) {
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

    private void visibleCases() {
        see_cases_ancher.setVisible(false);
        create_case.setVisible(false);
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

    public void createCase() {
        visibleCases();
        see_cases_ancher.setVisible(false);
        create_case.setVisible(true);
        visibleMenu();
        accessToCreateCase(true);
        addedUsers.clear();

    }

    public void seeCases() {
        visibleCases();
        updateCases();
        see_cases_ancher.setVisible(true);
        create_case.setVisible(false);
        visibleMenu();
    }

    @FXML
    private void changePassword(ActionEvent event) {
    }

    @FXML
    private void createCaseButton(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        try {
            if (CPRField.getText().trim().length() == 10) {
                String[] departmentInfo = departmentBox.getValue().split(" ");
                int departmentID = Integer.parseInt(departmentInfo[0]);
                if (GUIrun.getLogic().createCase(firstNameField.getText().trim(), lastNameField.getText().trim(), Long.parseLong(CPRField.getText().trim()),
                        caseTypeChoiceBox.getValue(), mainBodyArea.getText().trim(), new Date(), null, departmentID, inquiryArea.getText().trim(), addedUsers)) {
                    alert.setContentText("Sag oprettet");
                    alert.showAndWait();
                    clearCreateCase();
//                    accessToCreateCase(false);

                } else {
                    alert.setContentText("Fejl! Sagen kunne ikke oprettes");
                    alert.showAndWait();
                }

            } else {
                alert.setContentText("CPR nummer skal være 10 numre langt");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            alert.setContentText("CPR må kun indeholde numre");
            alert.showAndWait();
        } catch (NullPointerException e) {
            alert.setContentText("Vælg både bostedsafdeling og sagstype");
            alert.showAndWait();
        }

    }



    private void accessToCreateCase(boolean editable) {
        firstNameField.setEditable(editable);
        lastNameField.setEditable(editable);
        CPRField.setEditable(editable);
        mainBodyArea.setEditable(editable);
        inquiryArea.setEditable(editable);
        departmentBox.setDisable(!editable);
        caseTypeChoiceBox.setDisable(!editable);
        userIDTextField.setDisable(!editable);
        addSocialWorkerBtn.setDisable(!editable);
    }

    @FXML
    private void addSocialWorker(ActionEvent event) {
        if (GUIrun.getLogic().checkUserID(userIDTextField.getText()) && !userIDTextField.getText().equals(GUIrun.getLogic().getUserID())) {
            addedUsers.add(userIDTextField.getText());
            userIDTextField.setText("Tilføjet socialarbejder");
        } else {
            userIDTextField.setText("Forkert indtastet brugerID");
        }
    }

    private void updateCases() {
        cases = GUIrun.getLogic().getCases();

        viewableCases = FXCollections.observableArrayList(cases);
        filteredCases = new FilteredList<>(viewableCases, p -> true);
        listview_cases.setItems(filteredCases);
    }

    public void openCase(MouseEvent event) {

    }

    private void clearCreateCase() {
        firstNameField.clear();
        lastNameField.clear();
        CPRField.clear();
        mainBodyArea.clear();
        inquiryArea.clear();
        userIDTextField.clear();
        caseTypeChoiceBox.getSelectionModel().clearSelection();
        departmentBox.getSelectionModel().clearSelection();

    }

}
