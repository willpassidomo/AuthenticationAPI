/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

/**
 *UserNameAlreadyExistsException is a marker class which is thrown by the 
 * newUser(String username, String password) method or setUserName(String userName)
 * in the User class. UserNameAlreadyExistsException signifies that the username 
 * parameter matches the username, case insensitively, of an existing User instance
 * @author willpassidomo
 */
class UserNameAlreadyExistsException extends Exception {
    private String username;
    private User user;
    private Service service;
    
    /**
     * A public constructor used if the Exception was caused by a an attempt to create
     * a new user with a userName of an existing user. Logs reference to the service
     * the exception occured in, the User who attempted to set their name and the 
     * userName they attempted to set it to for the eventual purpose of trouble shooting 
     * @param userName the name of the new userName
     * @param user the User who attempted to change userName
     * @param service the service in which the exception occured, and the user resides
     */
    public UserNameAlreadyExistsException(String username, User user, Service service) {
        super("Username :"+username+" already exists, name will remain "+user.getUserName());
        this.username = username;
        this.user = user;
    }
    
    /**
     * A public constructor used if the Exception was caused by a an attempt to create
     * a new user with a userName of an existing user. Logs reference to the 
     * userName for the eventual purpose of trouble shooting 
     * @param userName the name of the new userName
     */
    public UserNameAlreadyExistsException(String username) {
        super("Username :"+username+" already exists, please choose a different userName");
        this.username = username;
        this.user = user;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    public Service getService() {
        return service;
    }
    
}
