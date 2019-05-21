package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.CaseInterface;

/**
 * FXML Controller class
 *
 * @author Peterzxcvbnm
 */
public class SocialWorkerCaseController implements Initializable {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField caseIDField;
    @FXML
    private TextField CPRField;
    @FXML
    private TextArea inquiryArea;
    @FXML
    private Button closeButton;

    CaseInterface currentCase;
    @FXML
    private TextField typeField;
    @FXML
    private TextField departmentField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void injectCase(CaseInterface currentCase) {
        this.currentCase = currentCase;
        setupCase();
    }

    private void setupCase() {
        firstNameField.setText(currentCase.getFirstName());
        lastNameField.setText(currentCase.getLastName());
        caseIDField.setText(currentCase.getCaseID().toString());
        CPRField.setText("" + currentCase.getCprNumber());
        typeField.setText(currentCase.getType());
        departmentField.setText(GUIrun.getLogic().getDepartmentNameById(currentCase.getDepartmentID()));
        inquiryArea.setText(currentCase.getInquiry());
    }

}
