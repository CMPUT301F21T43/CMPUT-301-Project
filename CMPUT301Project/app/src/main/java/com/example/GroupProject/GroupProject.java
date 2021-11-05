/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.Application;

public class GroupProject extends Application {

    private String username;
    private boolean signedIn;

    public GroupProject() {
        this.signedIn = false;
        this.username = "John Doe"; // default username
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSignedIn() {
        return signedIn;
    }
}
