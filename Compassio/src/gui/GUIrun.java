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
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

/**
 *
 * @author Peter Brændgaard
 */
public class GUIrun extends Application implements IGUI {

    private static ILogic logic;

    private static IGUI guiRun;
    
    private static GUIHandler guih;

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
    
    public static void changeFxml (String fxml) throws IOException {
        guih.changeFXMLAction(fxml);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        
        loginController controller = loader.getController();
        
        stage.initStyle(StageStyle.TRANSPARENT);
        
//        Image img = new Image("/img/logo.png");
//        stage.getIcons().add(img);

        Scene scene = new Scene(root);

        guih = new GUIHandler(stage);
        guih.moves(stage, scene);
        
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
