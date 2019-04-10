/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.IPersistence;

/**
 *
 * @author Morten Kargo Lyngesen
 */
public class User {
    private String userID;
    private final String username;
    private String firstName;
    private String lastName;
    
    public User (String userID, String username, String firstName, String lastName) {
        this.username = username;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
}
