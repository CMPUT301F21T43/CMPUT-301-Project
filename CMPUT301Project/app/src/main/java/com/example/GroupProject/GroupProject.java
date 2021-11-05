/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.Application;

public class GroupProject extends Application {

    private String username;

    public GroupProject() {
        this.username = "John Doe"; // default username
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
