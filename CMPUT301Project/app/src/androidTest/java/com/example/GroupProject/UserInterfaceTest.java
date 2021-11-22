/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

/**
 * Instrumented test, which tests the UI funtions such as add,
 * edit, and delete habit
 * @Author Marcus Bengert
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * TODO: Implement a custom data set for bete date verification
 */
//@RunWith(AndroidJUnit4.class)
public class UserInterfaceTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testDeleteHabit(){
        onView(withId(R.id.editTextUsernameSignIn))
                .perform(typeText("#TestUser"), pressImeActionButton(), pressImeActionButton())
                .check(matches(withText("#TestUser")));
        onView(withText("Confirm"))
                .perform(click());
        SystemClock.sleep(1000);

        SystemClock.sleep(1000);
        onData(anything()).atPosition(0).perform(longClick());
        SystemClock.sleep(100);
    }
    @Test
    public void testEditHabit(){
        SystemClock.sleep(1000);
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.btnEditHabit))
                .perform(click());
        onView(withId(R.id.etHabitTitle))
                .perform(replaceText("Swimming"))
                .check(matches(withText("Swimming")));
        onView(withId(R.id.etHabitReason))
                .perform(replaceText("Get muscles"))
                .check(matches(withText("Get muscles")));
        onView(withId(R.id.btnConfirmEditHabit))
                .perform(click());
        // Confirm new habit exists in the view
        onView(withText("Swimming")).perform(click());
    }

    @Test
    // TODO: Implement tests for datepicker/Calender and
    public void testAddHabit(){

        // Click add habit button
        onView(withId(R.id.btnAddHabit))
                .perform(click());
        // Set name and reason
        onView(withId(R.id.etHabitTitle))
                .perform(typeText("Walking"))
                .check(matches(withText("Walking")));
        onView(withId(R.id.etHabitReason))
                .perform(typeText("Be Healthy"))
                .check(matches(withText("Be Healthy")));
        // Select active days
        onView(withId(R.id.cMonday))
                .perform(scrollTo(), click());
        onView(withId(R.id.cTuesday))
                .perform(scrollTo(), click());
        onView(withId(R.id.cWednesday))
                .perform(scrollTo() ,click());
        onView(withId(R.id.cThursday))
                .perform(scrollTo(), click());
        onView(withId(R.id.cTFriday))
                .perform(scrollTo(), click());
        onView(withId(R.id.cSaturday))
                .perform(scrollTo(), click());
        onView(withId(R.id.cSunday))
                .perform(scrollTo(), click());
        onView(withId(R.id.toggleIsPublic))
                .perform(scrollTo(), click());
        onView(withId(R.id.btnConfirmAddHabit))
                .perform(click());
        // Confirm new habit exists in the view and clickable
        SystemClock.sleep(1000);
        onView(withText("Walking"))
                .perform(click());

    }

    @Test
    public void testHabitEventAdd(){
        SystemClock.sleep(1000);
        onView(withText("Walking"))
                .perform(click());
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());
        onView(withId(R.id.btnAddMainEvent))
                .perform(click());
        onView(withId(R.id.etAddEventTitle))
                .perform(typeText("Test Event Name"))
                .check(matches(withText("Test Event Name")));;
        onView(withId(R.id.etAddEventComment))
                .perform(typeText("Test Comment"))
                .check(matches(withText("Test Comment")));
        onView(withId(R.id.btnConfirmAddEvent))
                .perform(click());
        SystemClock.sleep(1000);
        // Confirm Test Event name is in view and clickable
        onView(withText("Test Event Name"))
                .perform(click());
    }

    @Test
    public void testEditEvent(){
        onView(withText("Walking"))
                .perform(click());
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());
        onView(withText("Test Event Name"))
                .perform(click());
        onView(withId(R.id.btnEditEvent))
                .perform(click());
        onView(withId(R.id.etEditEventTitle))
                .perform(replaceText("Test Event Name Edit"));
        onView(withId(R.id.etEditEventComment))
                .perform(replaceText("Test Comment Edit"));
        onView(withId(R.id.btnConfirmEditEvent))
                .perform(click());
        // Confirm edit is in view and clickable
        onView(withText("Test Event Name Edit"))
                .perform(click());
    }

    @Test
    public void TestDeleteEvent(){
        onView(withText("Walking"))
                .perform(click());
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());
        onView(withText("Test Event Name Edit"))
                .perform(longClick());
        SystemClock.sleep(1000);

    }
}