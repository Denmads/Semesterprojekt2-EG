/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import acquaintance.IGUI;
import acquaintance.ILogic;

/**
 *
 * @author Peter Brændgaard
 */
public class GUIrun extends Application implements IGUI {

    private static ILogic logic;

    private static IGUI guiRun;

    @Override
    public void injectLogic(ILogic LogicLayer) {
        logic = LogicLayer;
    }

    public static IGUI getInstance() {
        return guiRun;
    }

    public static ILogic getLogic() {
        return logic;
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void startApplication(String[] args) {
        guiRun = this;
        launch(args);
    }

}
