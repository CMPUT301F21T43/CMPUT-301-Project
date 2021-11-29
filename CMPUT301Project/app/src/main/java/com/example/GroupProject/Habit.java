/*
 * Habit
 *
 * Version 1.0
 *
 * November 3, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Habit class
 * Class of habit objects with a title, date, and reason
 * @Author Marcus Bengert, Martin Rudolf
 */
public class Habit implements Serializable {

    private String title;
    private String reason;
    private Long yearToStart;
    private Long monthToStart;
    private Long dayToStart;
    private Map<String, Boolean> activeDays;
    private Boolean isPublic;
    private int progress;

    /**
     * Generic constructor for Habit.
     * @param title name of the habit, needs to be unique for a particular user.
     * @param reason reason for the habit
     * @param yearToStart Long year to start
     * @param monthToStart Long month to start
     * @param dayToStart Long day to start
     * @param activeDays days of the week that the habit should be active
     * @param isPublic whether the habit is publicly visible or not
     */
    Habit(String title, String reason, Long yearToStart, Long monthToStart, Long dayToStart, Map<String, Boolean> activeDays, Boolean isPublic){
        this.title = title;
        this.reason = reason;
        this.yearToStart = yearToStart;
        this.monthToStart = monthToStart;
        this.dayToStart = dayToStart;
        this.activeDays = activeDays;
        this.isPublic = isPublic;
        this.progress = 0;
    }

    /**
     * Function to return habit title
     * @return title
     *      Returns title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set habit title
     * @param title
     *      title of habit as a string
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Function to return habit progress
     * @return progress
     *      Returns progress as int
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Function to set habit progress
     * @param progress
     *      progress for habit as int
     */
    public void setProgress(int progress) {
        this.progress = progress;
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
     * Get the year for habit to start.
     * @return Long year
     */
    public Long getYearToStart() {
        return yearToStart;
    }

    /**
     * Set the year for habit to start.
     * @param yearToStart
     */
    public void setYearToStart(Long yearToStart) {
        this.yearToStart = yearToStart;
    }

    /**
     * Get the month for habit to start.
     * @return Long month
     */
    public Long getMonthToStart() {
        return monthToStart;
    }

    /**
     * Set the month for habit to start.
     * @param monthToStart
     */
    public void setMonthToStart(Long monthToStart) {
        this.monthToStart = monthToStart;
    }

    /**
     * Get the day for habit to start.
     * @return Long day
     */
    public Long getDayToStart() {
        return dayToStart;
    }

    /**
     * Set the day for habit to start.
     * @param dayToStart
     */
    public void setDayToStart(Long dayToStart) {
        this.dayToStart = dayToStart;
    }

    /**
     * Returns date to start formatted as "YYYY-MM-DD" string
     * @return "YYYY-MM-DD"
     *      Returns the date to start formatted as "YYYY-MM-DD"
     */
    public String getDateToStartAsString() {
        String yyyy = yearToStart.toString();
        String mm = monthToStart.toString();
        String dd = dayToStart.toString();
        return yyyy + "-" + mm + "-" + dd;
    }

    /**
     * Set the date to start for a habit.
     * @param year Long year to start
     * @param month Long month to start
     * @param day Long day to start
     */
    public void setDateToStart(Long year, Long month, Long day) {
        setYearToStart(year);
        setMonthToStart(month);
        setDayToStart(day);
    }

    /**
     * Get active days of week.
     * @return activeDays
     *      activeDays as a Map with K: day of week (String) and V: active (Boolean)
     */
    // TODO: This function name is a misnomer as it gets "Inactive" days too
    public Map<String, Boolean> getActiveDays() {
        return activeDays;
    }

    /**
     * Set active days of week.
     * @param activeDays
     *      activeDays is a Map with K: day of week (String) and V: active (Boolean)
     */
    public void setActiveDays(Map<String, Boolean> activeDays) {
        this.activeDays = activeDays;
    }

    /**
     * Returns the value for the given day key.
     * @param day_of_week
     *      int day_of_week is the result of calling .get(Calendar.day_of_week) on
     *          a calendar instance.
     * @return value
     *      value is a boolean mapped to the day param that
     *          returns true if the day is active, false otherwise
     */
    public boolean isDayActive(int day_of_week){
        String todayDayOfWeek = "";
        switch (day_of_week) {
            case Calendar.SUNDAY:
                todayDayOfWeek = "Sunday";
                break;
            case Calendar.MONDAY:
                todayDayOfWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                todayDayOfWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                todayDayOfWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                todayDayOfWeek = "Thursday";
                break;
            case Calendar.FRIDAY:
                todayDayOfWeek = "Friday";
                break;
            case Calendar.SATURDAY:
                todayDayOfWeek = "Saturday";
                break;
        }
        return activeDays.get(todayDayOfWeek);
    }

    /**
     * Get whether Habit is public or not.
     * @return isPublic
     *      isPublic as Boolean
     */
    public Boolean getPublic() {
        return isPublic;
    }

    /**
     * Set Habit as public or not.
     * @param isPublic
     *      isPublic is Boolean
     */
    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
