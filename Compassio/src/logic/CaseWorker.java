/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author Kargo
 */
public class CaseWorker extends User {
    ArrayList<Long> departments;
    
    public CaseWorker (String userID, String username, String firstName, String lastName, ArrayList<Long> departments) {
        super(userID, username, firstName, lastName);
        this.departments = departments;
    }
}
