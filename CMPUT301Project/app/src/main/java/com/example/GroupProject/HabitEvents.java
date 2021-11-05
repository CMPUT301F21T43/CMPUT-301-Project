/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import java.io.Serializable;

public class HabitEvents extends Habit {
    private String title;
    private String comment;

    public HabitEvents(String title, String comment) {
        super();
        this.title = title;
        this.comment = comment;
    }

    public String getEventTitle() {
        return title;
    }

    public void setEventTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}