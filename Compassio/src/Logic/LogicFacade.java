/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Acquaintance.ILogic;
import Acquaintance.IPersistence;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {

    private IPersistence persistence;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

}
