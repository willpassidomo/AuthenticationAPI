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
 * The Role class represents a classification of a set of Permissions. The permissions 
 * within a role usually constitute the functionality needed to fulfill the abilities 
 * of one of the  actors in a Use case within an actual service. Role has 
 * 4 associations, an id, a name, a description and a HashMap representing the 
 * Permision Instances associated with the service.  Role has 2 methods, the 
 * implementation of the abstract acceptVisitor(Visitor) and newRole(String, String) 
 * which uses the Flyweight pattern to construct new Role objects or return existing 
 * Role objects
 * @author willpassidomo
 */
public class Role implements Comparable, Visitable, Entitlement {
    private UUID id;
    private String name;
    private String description;
    private HashMap<String, Entitlement> permissions = new HashMap<>();
    private static HashMap<String, Role> roles = new HashMap<>();
    
    private Role (String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }
    
    public static Role newRole(Service service, String name, String description) {
        if (roles.containsKey(name.toLowerCase())) {
            return roles.get(name);
        } else {
            Role newRole = new Role(name, description);
            roles.put(name.toLowerCase(), newRole);
            service.addRole(newRole);
            return newRole;
        }
    }
    
    public static Role getRole(String roleName) {
        return roles.get(roleName);
    }

    
    public static void deleteRole(String name) {
        Role role = roles.remove(name);
        roles.remove(role.getName());
        role = null;
    }
    
    public static List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>(roles.values());
        Collections.sort(list);
        return list;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Role) {
            Role r = (Role) o;
            return r.getName().toLowerCase().equals(this.getName().toLowerCase());
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Role) {
            Role r = (Role) o;
            return r.getName().compareToIgnoreCase(this.getName());
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public void addPermission(Entitlement permission) {
        permissions.put(permission.getName(), permission);
    }
    
    public void addPermission(List<Entitlement> permissionss) {
        for (Entitlement permission: permissionss) {
            this.addPermission(permission);
        }
    }
    
    public void removePermission(String permissionID) {
        permissions.remove(permissionID);
    }
    
    public void removePermissions() {
        permissions.clear();
    }
    
    @Override
    public List<Permission> getPermissions() {
        List<Entitlement> list = new ArrayList<>(permissions.values());
        List<Permission> permissionslist = new ArrayList<>();
        for (Entitlement entitlement: list) {
            permissionslist.addAll(entitlement.getPermissions());
        }
        Collections.sort(permissionslist);
        return permissionslist;
    }
    
    public boolean hasPermission(String permission) {
        return permissions.containsKey(permission);
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
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitElement(this);
    }
    
}
