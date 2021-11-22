/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

/**
 * GroupProject class
 * Class that extends the application and holds the global variables
 * username and signedIn.
 * @Author Kyle Bricker, Marty Rudolf
 */
public class GroupProject extends Application {

    private boolean signedIn;
    private FirebaseUser firebaseUser;
    private String username;

    /**
     * Method that occurs upon application start
     */
    public GroupProject() {
        this.signedIn = false;
        this.firebaseUser = null; // default user
        this.username = "John Doe"; // default username
    }

    /**
     * Method that occurs upon application start
     */
    public GroupProject(FirebaseUser user, String username) {
        this.signedIn = false;
        this.firebaseUser = user;
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
     * Check if signed in
     * @return signedIn
     *      Returns signedIn as a boolean value
     */
    public boolean isSignedIn() {
        return signedIn;
    }

    /**
     * Get the FirebaseUser attached to this app instance.
     * @return FirebaseUser
     */
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    /**
     * Set the FirebaseUser attached to this app instance.
     * @param user FirebaseUser object
     */
    public void setFirebaseUser(FirebaseUser user) {
        this.firebaseUser = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
