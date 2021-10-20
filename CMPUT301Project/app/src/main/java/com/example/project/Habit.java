package com.example.project;

import java.util.Date;

/**
 * Habit class
 * Class of habit objects with a name, date, and reason
 * @Author Marcus Bengert
 */

public class Habit {

    private String name;
    private String reason;
    private Date date;

    /**
     * Function to return habit name
     * @return name
     *      Returns name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set habit name
     * @param name
     *      name of habit as a string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to return habit reason
     * @return reason
     *      Returns reason as a String
     */
    public String getReason() {
        return reason;
    }

    /**
     * Function to set habit reason
     * @param reason
     *      reason for habit as a string
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Function to return habit date
     * @return date
     *      Returns date as a Date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * Function to set habit date
     * @param date
     *      date started as a Date object
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
