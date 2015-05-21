/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

/**
 *AuthenticationException is a marker class which is thrown by the 
 * getUser(String username, String password) method of a Service class instance. 
 * AuthenticationException signifies that the credentials supplied are not valid, 
 * either the Username provided does not exist or the Password does not exist. 
 * Taking into account where AuthenticationExceptionis thrown, it would be trivial 
 * to include a message about which one of the two (username or password) was 
 * incorrect, but due to security concerns, the difference is not noted, rather 
 * the more generic message “Incorrect Username and/or password” is displayed
 * @author willpassidomo
 */
public class AuthenticationException extends Exception {

    private String userName;
    private String password;
    
    /**
     * public constructor, passes message to Exception constructor. Logs the attempted
     * userName and the attempted password. The data gathered by this exception 
     * should eventually be used to set a control on how many times the same userName
     * could be attempted with incorrect password's before a lock-out mechanism kicks in
     * @param message the message for the Exception
     * @param userName the attempted userName
     * @param password the attempted password
     */
    public AuthenticationException(String message, String userName, String password) {
    super(message);
    this.userName = userName;
    this.password = password;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }


    
}
