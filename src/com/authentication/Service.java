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
 *The Service class represents a service which is associated with an instance of 
 * AuthenticationAPI. Service has 1 method, the abstract acceptVisitor(Visitor). 
 * Service has 4 fields, id, name, description and a HashMap representing the 
 * User accounts associated with the service.
 * @author willpassidomo
 */
public class Service implements Comparable, Visitable {
    private UUID id;
    private String name;
    private String description;
    private AuthTokenGenerator tokenGen = new AuthTokenGenerator();
    private HashMap<String,User> users = new HashMap<>();
    private HashMap<String,Role> roles = new HashMap<>();
    private HashMap<String,Permission> permissions = new HashMap<>();
    private static HashMap<String, Service> services = new HashMap<>();
    
    private Service(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }
    
    public static Service newService(String name, String description) {
        if (services.containsKey(name.toLowerCase())) {
            return services.get(name.toLowerCase());
        } else {
            Service newService = new Service(name, description.toLowerCase());
            services.put(name.toLowerCase(), newService);
            return newService;
        }
    }
    
    public void addRole(Role role) {
        roles.put(role.getName().trim().toLowerCase(), role);
    }
    
    public void removeRole(UUID roleID) {
        roles.remove(roleID);
    }
    
    public List<Role> getRoles() {
        List<Role> roleL = new ArrayList<>(roles.values());
        Collections.sort(roleL);
        return roleL;
    }
    
    public Role getRole(String roleName) {
        return roles.get(roleName);
    }
    
    public void deleteRole(String roleName) {
        roles.remove(roleName);
        Role.deleteRole(roleName);
    }
    
    public void addPermission(Permission permission) {
        permissions.put(permission.getName(), permission);
    }
    
    public void removePermission(String permissionName) {
        permissions.remove(permissionName);
        for (Role role: this.getRoles()) {
            role.removePermission(permissionName);
        }
    }
    
    public Permission getPermission(String permissionID) {
        return permissions.get(permissionID);
    }
    
    public List<Permission> getPermissions() {
        List<Permission> permission = new ArrayList<>(permissions.values());
        Collections.sort(permission);
        return permission;
    }
    
    public static List<Service> getServices() {
        List<Service> service = new ArrayList<>(services.values());
        Collections.sort(service);
        return service;
    }
    
    @Override
    public int compareTo(Object o) {
        if (o instanceof Service) {
            Service s = (Service) o;
            return s.getName().compareToIgnoreCase(this.getName());
        } else { 
            return 0;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Service) {
            Service s = (Service) o;
            return s.getName().toLowerCase().equals(this.getName().toLowerCase());
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the users
     */
    public HashMap<String,User> getUsers() {
        return users;
        
    }
    
    public User getUser(String userName) {
        return users.get(userName.trim().toLowerCase());
    }
    
    public User getUser(String userName, String password) throws AuthenticationException {
        User user = users.get(userName);
        if (user != null) {
            if (user.checkPassword(password)){
                return user;
            }
        }
        throw new AuthenticationException("Incorrect UserName and/or Password", userName, password);
    }
    
    public void addUser(User user) {
        users.put(user.getUserName().trim().toLowerCase(), user);
    }
    
    public void removeUser(String user) {
        if (users.containsKey(user)){
            users.remove(user).getUserName();
        } else {
            throw new IllegalArgumentException("Service does not contain user with ID: "+user);
        }
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(HashMap<String,User> users) {
        this.users = users;
    }
    
    public AuthTokenGenerator getAuthTokenGenerator() {
        return this.tokenGen;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitElement(this);
    }
    
}
