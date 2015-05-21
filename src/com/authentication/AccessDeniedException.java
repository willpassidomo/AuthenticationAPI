/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

/**
 *AccessDeniedException is a marker class which is thrown by the 
 * hasPermission(String permission) method in a User instance. AccessDeniedException 
 * signifies that the User has not been assigned the Permission specified in the 
 * parameter. AccessDeniedException displays a message stating the (User’s username) 
 * does not have (Permission’s name) permission
 * @author willpassidomo
 */
public class AccessDeniedException extends Exception {
    
    private User user;
    private String permission;
    
    /**
     * public constructor, constructs a message based on the user who triggered the
     * exception for not having the Permission named permission. passes message to Exception constructor
     * logs the User who attempted to call the restricted method and the permission they 
     * tried to call for the purpose of future analysis by service owner or by AuthenticationAPI
     * @param user the User who's access was denied
     * @param permission the permission the User was denied for not having
     */
    
    public AccessDeniedException(User user, String permission) {
        super("Access Deined! user: "+user.getUserName()+" does not have permission: "+permission);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }
}
