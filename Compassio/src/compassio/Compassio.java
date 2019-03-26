/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compassio;

import Acquaintance.IGUI;
import Acquaintance.ILogic;
import Acquaintance.IPersistence;
import GUI.GUIrun;
import Logic.LogicFacade;
import Persistence.PersistenceFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Peterzxcvbnm
 */
public class Compassio extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGUI gui = new GUIrun();
        ILogic logic = new LogicFacade();
        IPersistence persistence = new PersistenceFacade();
        logic.injectPersistence(persistence);
        gui.injectLogic(logic);
        gui.startApplication(args);
    }
    
}
