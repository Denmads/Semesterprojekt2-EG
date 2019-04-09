/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acquaintance;

/**
 *
 * @author Peter Brændgaard
 */
public interface ILogic {

    public void injectPersistence(IPersistence PersistenceLayer);
    public boolean login(String username, String password);
}
