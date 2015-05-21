/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The user class represents a User in a service. Users are assigned roles 
 * (containing permissions) which correspond to the methods which the root user 
 * would like to allow the user to perform within the service. Users have 4 associations, 
 * an id, a username, a password and 0 or more Roles. User’s have 2 methods, 
 * which are called internally, checkPassword(String) which return’s true if the 
 * hashed password provided matches the stored hashed password, and 
 * hasPermission(Permission),  which returns true if the user has been assigned 
 * the passed permission.
 * @author willpassidomo
 */
public class User implements Visitable, Comparable{
    private UUID id;
    private String userName;
    private String password;
    private Service service;
    HashMap<String, Role> roles = new HashMap<>();
    
    /**
     * the private constructor for User
     * @param userName the new User's userName
     * @param password the new User's password
     */
    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.id = UUID.randomUUID();
    }
    
    /**
     * creates a new User object with the specified name, userName and the password, 
     * password
     * @param userName the new User's userName
     * @param password the new Users's password
     * @return the newly created User object
     */
    static User newUser(String userName, String password) {
       return new User(userName, password);
        
    }
    
    /**
     * creates a new User object with the specified name, userName and the password, 
     * password and addes it to the List of Users associated with the specified
     * service
     * @param service the service for the User to be associated with
     * @param userName the new User's userName
     * @param password the new Users's password
     * @return the newly created User object
     */
    
    public static User newUser(Service service, String userName, String password) throws UserNameAlreadyExistsException {
        if(service.getUsers().containsKey(userName.toLowerCase())) {
            throw new UserNameAlreadyExistsException("this username already exists in service: "+service.getName());
        } else {
            User user = new User(userName, password);
            service.addUser(user);
            user.setService(service);
            return user;
        }
    }
    
    /**
     * Tests whether the parameter password matches the password of the User 
     * @param password the password to be tested
     * @return true if the password matches the User's password, false if it does not match
     */
    
    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }
    
    /**
     * tests whether the user has the Permission which has the name specified by the
     * paramter permission
     * @param permission the name of the Permission to be tested
     * @return true if the user has the permission, false if they do not have the permission
     * @throws AccessDeniedException thrown if the User does not have the Permission with
     * a name matching the parameter permission
     */
    
    public boolean hasPermission(String permission) throws AccessDeniedException {
        for (Role role: roles.values()) {
            if (role.hasPermission(permission)) {
                return true;
            }
        }
        throw new AccessDeniedException(this, permission);
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public List<Role> getRoles() {
        List<Role> role = new ArrayList<>(roles.values());
        Collections.sort(role);
        return role;
    }
    
    public void addRole(Role role) {
        roles.put(role.getName(), role);
    }
    
    public void removeRole(String roleID) {
        roles.remove(roleID);
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    
    /**
     * Sets the User's username to the parameter userName, if the service does not already
     * have a User with the userName, userName
     * @param userName the User's new UserName
     * @throws UserNameAlreadyExistsException if the userName already belongs to another
     * user in the service
     */
    public void setUserName(String userName) throws UserNameAlreadyExistsException {
        if(getService().getUsers().containsKey(userName.toLowerCase())) {
            throw new UserNameAlreadyExistsException(userName, this, service);
        } else {
            this.userName = userName;
        }
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitElement(this);
    }
    
    /**
     * compares to other Users based on Name, returns 0 if obeject is not a User
     * @param o the object to be compared
     * @return 0 if o is not a User, this.getUserName().compareToIgnoreCase(o.name) if o is a User
     */

    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            return u.getUserName().compareToIgnoreCase(this.userName);
        } else {
            return 0;
        }
    }

    /**
     * @param service the service to set
     */
    private void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }
    
}
