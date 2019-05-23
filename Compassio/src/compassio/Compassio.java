package compassio;

import acquaintance.IGUI;
import acquaintance.ILogic;
import acquaintance.IPersistence;
import gui.GUIrun;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import logic.LogicFacade;
import persistence.PersistenceFacade;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Peter Andreas Brændgaard
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
