/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The Permission class represents a single permission which should correspond 
 * to a requirement that a “user” of a service must be granted said permission 
 * to access an otherwise restricted method piece of data. Permission has 3 associations, 
 * an id, a name and a description. Permission has 2 methods, the implementation 
 * of the abstract acceptVisitor(Visitor) and newPermission(String, String) which 
 * uses the Flyweight pattern to construc new Permission objects or return existing 
 * Permission objects.
 * @author willpassidomo
 */
public class Permission implements Comparable, Visitable, Entitlement {
    private String name;
    private UUID id;
    private String description;
    private static HashMap<String, Permission> permissions = new HashMap<String, Permission>();
    
    private Permission (String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }
    
    public static Permission newPermission(Service service, String name, String description) {
        if (permissions.containsKey(name.toLowerCase())) {
            return permissions.get(name);
        } else {
            Permission permission = new Permission(name, description);
            permissions.put(name.toLowerCase(), permission);
            service.addPermission(permission);
            return permission;
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
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
    public int compareTo(Object o) {
        if (o instanceof Permission) {
            Permission p = (Permission) o;
            return p.getName().compareToIgnoreCase(this.getName());
        } else {
            return 0;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Permission) {
            Permission p = (Permission) o;
            return p.getName().toLowerCase().equals(this.getName().toLowerCase());
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitElement(this);
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> list = new ArrayList<>();
        list.add(this);
        return list;
    }
    
}
