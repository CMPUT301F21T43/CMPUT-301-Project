/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import java.io.Serializable;

/**
 * Habit class
 * Class of habit event objects
 */
public class HabitEvent implements Serializable {
    private String title;
    private String comment;
    private String photoID;

    /**
     * Generic constructor for HabitEvent.
     * @param title name of the habit event, needs to be unique for a particular habit.
     * @param comment optional comment
     * @param photoID optional photo (references a link stored in a realtime database)
     */
    public HabitEvent(String title, String comment, String photoID) {
        this.title = title;
        this.comment = comment;
        this.photoID = photoID;
    }

    /**
     * Function to return habit event title
     * @return title
     *      Returns title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set habit event title
     * @param title
     *      title of event as a string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to return an event comment
     * @return comment
     *      Returns comment as a String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Function to set event comment
     * @param comment
     *      comment as a String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Function to return photoID
     * @return photoID
     *      Returns photoID as a String
     */
    public String getPhotoID() {
        return photoID;
    }

    /**
     * Function to set photoID
     * @param photoID
     *      photoID as a String
     */
    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }
}