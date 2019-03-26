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
public interface IGUI {

    public void injectLogic(ILogic LogicLayer);

    public void startApplication(String[] args);

}
