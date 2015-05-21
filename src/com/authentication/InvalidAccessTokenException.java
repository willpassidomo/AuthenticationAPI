/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

/**
 *
 * @author willpassidomo
 */
public class InvalidAccessTokenException extends Exception {
    private User user;
    
    /**
     * A public constructor used if the Exception was caused by an expired Token.
     * Logs reference to the User for the eventual purpose of trouble shooting or fine
     * tuning the expiration time of authTokens.
     * @param message the message for the Exceptino
     * @param user the User who's token was expired
     */
    public InvalidAccessTokenException(String message, User user) {
        super(message);
        this.user = user;
        
    }
    
    /**
     * 
     * public constructor, passes message to Exception constructor
     * @param message the message for the Exception
     *
     * @param message 
     */

    InvalidAccessTokenException(String message) {
        super(message);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
}
