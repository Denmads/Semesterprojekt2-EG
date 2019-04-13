/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acquaintance;

import java.util.ArrayList;

/**
 *
 * @author Peterzxcvbnm
 */
public interface IPersistence {
    public ArrayList<String> retrieveCaseTypeNames ();
    public String[] getUser(String username, String password);
    public String getUserType(String userID);
    public ArrayList<Long> getUserDepartments(String userID);
}
