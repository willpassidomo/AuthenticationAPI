/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *ImplementsVisitor (a class designed in accordance with the basic Visitor pattern). 
 * InventoryVisitor uses its method getInventory to Iterate through the list of 
 * Users of the service, their Roles and their Entitlements and returns a clean, 
 * list in String format.
 * @author willpassidomo
 */
public class InventoryVisitor implements Visitor {
    private String inventory = "";
    public void visitElement(Permission permission) {
        String name = permission.getName();
        String description = permission.getDescription();
        inventory = inventory.concat(name + ": "+description+"\n");
    }
    public void visitElement(Service service) {
        String name = service.getName();
        String description = service.getDescription();
        List<User> list = new ArrayList<User>(service.getUsers().values());
        Collections.sort(list);
        String users = list+"";
        String roles = service.getRoles()+"";
        inventory = inventory.concat(name+": "+description+"\n\tusers: " +users+"\n\trole: "+roles+"\n");
    }
    public void visitElement(Role role) {
        String name = role.getName();
        String description = role.getDescription();
        String permissions = role.getPermissions()+"";
        inventory = inventory.concat(name+": "+description+"\n\t permissions: "+permissions+"\n");
    }
    public void visitElement(User user) {
        String name = user.getUserName();
        String password = user.getPassword();
        String roles = user.getRoles()+"";
        inventory = inventory.concat(name+ ": password-"+password+": "+roles+"\n");
    }  
        
    /**
     * Returns an organized Inventory of all Users, Roles, Entitlements, 
     * Permissions and Tokens
     * @param service the service to retrieve Inventory for
     * @return the inventory of the service
     */
    public String getInventory(Service service) {
        getUsers(service);
        getRoles(service);
        getPermissions(service);
        inventory = inventory.concat("INVENTORY COMPLETE\n");
        return inventory;
    }
    
    private void getPermissions(Service service) {
        inventory = inventory.concat("PERMISSIONS:\n");
        for (Permission permission: service.getPermissions()) {
            permission.acceptVisitor(this);
        }
    }
    
    private void getRoles(Service service) {
        inventory = inventory.concat("ROLES:\n");
        for (Role role: service.getRoles()) {
            role.acceptVisitor(this);
        }
    }
    
    private void getUsers(Service service) {
        inventory = inventory.concat("USERS:\n");
        List<User> list = new ArrayList<>(service.getUsers().values());
        Collections.sort(list);
        for (User user: list) {
            user.acceptVisitor(this);
        }
    }
}
