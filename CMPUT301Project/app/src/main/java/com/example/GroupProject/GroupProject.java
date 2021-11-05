/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.Application;

/**
 * GroupProject class
 * Class that extends the application and holds the global variables
 * username and signedIn.
 * @Author Kyle Bricker
 */
public class GroupProject extends Application {

    private String username;
    private boolean signedIn;

    /**
     * Method that occurs upon application start
     */
    public GroupProject() {
        this.signedIn = false;
        this.username = "John Doe"; // default username
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
     * Set signedIn
     * @param signedIn
     *      Boolean value describing the state of the application
     */
    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * Get username
     * @return username
     *      Returns username as a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Check if signed in
     * @return signedIn
     *      Returns signedIn as a boolean value
     */
    public boolean isSignedIn() {
        return signedIn;
    }
}
