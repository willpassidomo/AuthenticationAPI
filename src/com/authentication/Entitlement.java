/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import java.util.List;

/**
 * Entitlement is the interface implemented in Permission and Role. Entitlement 
 * has no methods and is mainly functional as the extension of a class which may 
 * be added to a Role. Entitlement’s only method getPermissions() is used to 
 * return either the permissions of a role or the single permission of a 
 * Permission object
 * @author willpassidomo
 */
public interface Entitlement {
    
        /**
         * Returns the Permissions contained within a role, if implement in 
         * Role or the Permission itself if implemented in a Permission instance
         * @return the Permissions associated with the Entitlement. this if the Entitlement is 
         * a Permission object and associated Permissions if Entitlement is a Role
         */
        public List<Permission> getPermissions();
        
        /**
         * Returns the name of the Entitlement
         * @return the name of the Entitlement
         */
        public String getName();
}
