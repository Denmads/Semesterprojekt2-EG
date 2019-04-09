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

    public ArrayList<String[]> getCasesByUserID(int userID);
    
    public ArrayList<String[]> getCasesByDepartment(int departmentID);
}
