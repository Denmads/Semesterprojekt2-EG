/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import acquaintance.IPersistence;


/**
 *
 * @author Morten Kargo Lyngesen
 */
public class SocialWorker extends User {
    ArrayList<Long> departments;
    
    public SocialWorker (String userID, String username, String firstName, String lastName,  ArrayList<Long> departments) {
        super(userID, username, firstName, lastName);
        this.departments = departments;
    }
    
    public ArrayList<Long> getDepartments(){
        return departments;
    }
    
    
}
