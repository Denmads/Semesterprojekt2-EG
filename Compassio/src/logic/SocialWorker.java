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
    ArrayList<String> departments;
    
    public SocialWorker (String userID, String username, String firstName, String lastName) {
        super(userID, username, firstName, lastName);
        
    }
}
