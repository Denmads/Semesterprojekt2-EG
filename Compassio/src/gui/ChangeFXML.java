/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author L530
 */
public class ChangeFXML {
    
    /**
     * 
     * @param fxml Takes the attribute fxml as a String
     * @param event Does an event when the ActionEvent is triggered
     * @throws IOException Throws an Exception
     * Changes the fxml scene you are in
     * Sets a new Scene in the new location
     */
    public void changeFXMLAction(String fxml, ActionEvent event) throws IOException {
        Parent gameDisplay = FXMLLoader.load(getClass().getResource(fxml));
        Scene gameDisplays = new Scene(gameDisplay);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(gameDisplays);
        window.show();
    }
    
    /**
     * 
     * @param fxml Takes the attribute fxml as a String
     * @param event Does an event when the MouseEvent is triggered
     * @throws IOException Throws an Exception
     * Changes the fxml scene you are in
     * Sets a new Scene in the new location
     */
    public void changeFXMLMouse(String fxml, MouseEvent event) throws IOException {
        Parent gameDisplay = FXMLLoader.load(getClass().getResource(fxml));
        Scene gameDisplays = new Scene(gameDisplay);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(gameDisplays);
        window.show();
    }
    
}
