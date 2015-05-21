/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.UUID;

/**
 *Is a class for creating new AuthTokens, for testing validity of submitted tokens 
 * and for matching token with the User it belongs to. AuthToken’s are String 
 * representations of UUID objects, a universally Unique 128 bit value.  
 * When a new authToken is generated, it is put in a HashMap which maps the 
 * String value of the Token to an AuthTokenStamp object. AuthTokenStamp is an 
 * inner class of AuthTokenGenerator which is responsibility for logging the 
 * time a stamp is set to expire and the user it was assigned to.  
 * @author willpassidomo
 */
public class AuthTokenGenerator {
    HashMap<String, authTokenStamp> tokens = new HashMap<>();
    
    /**
     * Returns a new, valid authtoken mapped to the User, user
     * @param user the user to be associated with the authToken
     * @return the newly created authToken
     */
    public String newAuthToken(User user) {
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.HOUR, 24);
        authTokenStamp token = new authTokenStamp(user, date);
        String authToken = UUID.randomUUID().toString();
        tokens.put(authToken, token);
        return authToken;
    }
    
    /**
     * Returns the User associated with the token if it is valid,. Throws 
     * InvaliAccessTokenException if token is not mapped or if token has expired.
     * @param authToken the authToken to be checked
     * @return the User associated with the AuthToken
     * @throws InvalidAccessTokenException if the authToken is not a valid authToken 
     * of if the authToken is expired
     */
    
    public User checkToken(String authToken) throws InvalidAccessTokenException {
        authTokenStamp token = tokens.get(authToken);
        if (token != null) {
            if (token.getDate().after(Calendar.getInstance())){
                return token.getUser();
            } else {
                throw new InvalidAccessTokenException("Token is expired",token.getUser());
            }
        } else {
            throw new InvalidAccessTokenException("Not a valid Token!");
        }
    }
    
    /**
     * Removes the passed token, authToken from the map. Throws InvalidAccessTokenException 
     * if token is not mapped.
     * @param authToken the authToken to be removed
     * @throws InvalidAccessTokenException if the authToken is not a valid authToken 
     * of if the authToken is expired
     */
    public void removeToken(String authToken) throws InvalidAccessTokenException {
        if (tokens.remove(authToken) == null) {
            throw new InvalidAccessTokenException("Not a valid Token, could not be removed");
        }
    }

    /**
     * AuthTokenStamp is an Immutable class with only 2 properties, a Date, 
     * expirationDate and a User, user. AuthTokenStamps are created by AuthTokenGenerator, 
     * each to track a particular authToken. Every authToken maps to an instance 
     * of AuthTokenStamp in AuthTokenGenerator HashTable “tokens”
     */

    private class authTokenStamp {
        private GregorianCalendar date;
        private User user;
        
        public authTokenStamp(User user, GregorianCalendar date) {
            this.user = user;
            this.date = date;
        }

        /**
         * @return the date
         */
        public GregorianCalendar getDate() {
            return date;
        }

        /**
         * @return the user
         */
        public User getUser() {
            return user;
        }
    }
    
}
