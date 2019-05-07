/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class GUIHandler {

    private double xOffset = 0;
    private double yOffset = 0;
    
    private Stage window;

    public GUIHandler(Stage window) {
        this.window = window;
    }

    /**
     *
     * @param fxml Takes the attribute fxml as a String
     * @throws IOException Throws an Exception Changes the fxml scene you are in
     * Sets a new Scene in the new location
     */
    public void changeFXMLAction(String fxml) throws IOException {
        Parent gameDisplay = FXMLLoader.load(getClass().getResource(fxml));
        Scene gameDisplays = new Scene(gameDisplay);

        window.setScene(gameDisplays);
        moves(window, gameDisplays);

        window.show();
    }

    public void moves(Stage stage, Scene scene) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getY() < 36) {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getY() < 36) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            }
        });
    }

}
