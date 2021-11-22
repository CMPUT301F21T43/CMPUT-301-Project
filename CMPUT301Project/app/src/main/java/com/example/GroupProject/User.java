/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.Application;

import java.io.Serializable;

/**
 * User class
 * Class of a user, containing username, first name and last name
 * @Author Kyle Bricker
 *
 */
public class User implements Serializable {

    private String username;
    private String firstName;
    private String lastName;

    User (String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get username
     * @return username
     *      returns users username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get first name
     * @return firstName
     *      returns users first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get last name
     * @return lastName
     *      returns users last name as a string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set username
     * @param username
     *      Username as a string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set first name
     * @param firstName
     *      First name as a string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set last name
     * @param lastName
     *      Last name as a string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
