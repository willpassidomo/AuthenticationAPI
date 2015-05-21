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
public interface Visitor {
    public void visitElement(Permission permission);
    public void visitElement(Service service);
    public void visitElement(Role role);
    public void visitElement(User user);
}
