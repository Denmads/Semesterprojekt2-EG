/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public interface UserInterface {

    /**
     * @return all the departments the current user is a member of.
     */
    ArrayList<Long> getDepartments();

    /**
     * @return the users first name
     */
    String getFirstName();

    /**
     * @return the users last name
     */
    String getLastName();

    /**
     * @return the users id
     */
    String getUserID();

    /**
     * @return the users assigned type
     */
    int getUserType();

    /**
     * @return the users username
     */
    String getUsername();
    
}
