/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Instrumented test, which tests the UI functions such as add,
 * edit, and delete habit, and corresponding habit event functions
 * @Author Marcus Bengert
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * TODO: Implement a custom data set for bete date verification
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@RunWith(AndroidJUnit4.class)
public class UserInterfaceTest {

    @Rule
    public ActivityScenarioRule<EmailPasswordActivity> activityScenarioRule =
            new ActivityScenarioRule<EmailPasswordActivity>(EmailPasswordActivity.class);


    @Test

    public void ATestEditHabit(){
        signIn();
        onView(withText("Walking"))
                .perform(click());
        onView(withId(R.id.btnEditHabit))
                .perform(click());
        onView(withId(R.id.etHabitTitle))
                .check(matches(withText("Walking")));
        onView(withId(R.id.etHabitReason))
                .perform(replaceText("Get Excercise"))
                .check(matches(withText("Get Excercise")));

        onView(withId(R.id.etDateToStartYear))
                .perform(replaceText("2020"));
        onView(withId(R.id.etDateToStartMonth))
                .perform(replaceText("10"));
        onView(withId(R.id.etDateToStartDay))
                .perform(replaceText("15"), closeSoftKeyboard());


        // Alter active days. They all should be enabled, so this operation should change the active days from every day of the week
        // to no active days in the week
        SystemClock.sleep(100);
        onView(withId(R.id.cMonday))
                .perform(click());
        onView(withId(R.id.cTuesday))
                .perform(click());
        onView(withId(R.id.cWednesday))
                .perform(click());
        onView(withId(R.id.cThursday))
                .perform(click());
        onView(withId(R.id.cTFriday))
                .perform(click());
        onView(withId(R.id.cSaturday))
                .perform(click());
        onView(withId(R.id.cSunday))
                .perform(click());
        SystemClock.sleep(100);
        onView(withId(R.id.toggleIsPublic))
                .perform(click());
        onView(withId(R.id.btnConfirmEditHabit))
                .perform(click());
        // Confirm new habit exists in the view
        onView(withText("Walking"))
        .check(matches(isDisplayed()));
    }


    @Test
    public void BTestHabitEventAdd(){
        signIn();

        // Navigate to the Walking habit ViewHabitActivity
        onView(withText("Walking"))
                .perform(click());

        // Navigate to the EventMainActivity
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());

        // Navigate to the AddEventActivity
        onView(withId(R.id.btnAddMainEvent))
                .perform(click());
        onView(withId(R.id.etAddEventTitle))
                .perform(typeText("Morning Walk")); // Add title
        onView(withId(R.id.etAddEventComment))
                .perform(typeText("Walk by bridge")); //  Add comment
        onView(withId(R.id.btnConfirmAddEvent))
                .perform(click());
        SystemClock.sleep(1000);
        // Confirm Test Event name is in view
        onView(withText("Morning Walk"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void CTestEditEvent(){
        signIn();

        // Navigate to Walking habit
        onView(withText("Walking"))
                .perform(click());

        // Navigate to EventMainActivity
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());

        // Navigate to ViewHabitActivity
        onView(withText("Morning Walk"))
                .perform(click());

        // Navigate to EditEventActivity
        onView(withId(R.id.btnEditEvent))
                .perform(click());

        // Navigate to EditEventActivity
        onView(withId(R.id.etEditEventTitle)) // Change event title
                .perform(replaceText("Afternoon Walk"));
        onView(withId(R.id.etEditEventComment)) // Change event Comment
                .perform(replaceText("Walk by the mall"), closeSoftKeyboard());
        onView(withId(R.id.cbEditEventDone)) // Denote habit as done
                .perform(click());
        onView(withId(R.id.btnConfirmEditEvent))
                .perform(click());
        // Confirm edit is in view
        onView(withText("Afternoon Walk"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void DTestDeleteEvent(){
        signIn();

        // Navigate to Walking habit
        onView(withText("Walking"))
                .perform(click());

        // Navigate to EventMainActivity
        onView(withId(R.id.btnGoToEvents))
                .perform(scrollTo(), click());
        onView(withText("Afternoon Walk"))
                .perform(longClick()); // Delete the event
    }

    @Test
    public void ETestDeleteHabit(){
        signIn();

        // Navigate to ViewHabitActivity
        onView(withId(R.id.habit))
                .perform(click());
        onView(withText("Walking"))
                .perform(longClick()); //  Delete the habit
    }

    @Test
    public void FTestAddHabit(){
        signIn();

        // Click add habit button
        onView(withId(R.id.btnAddHabit))
                .perform(click());

        // Set name, reason, and date
        onView(withId(R.id.etHabitTitle))
                .perform(typeText("Walking"), pressImeActionButton())
                .check(matches(withText("Walking")));
        onView(withId(R.id.etHabitReason))
                .perform(typeText("Be Healthy"), pressImeActionButton())
                .check(matches(withText("Be Healthy")));
        onView(withId(R.id.etDateToStartYear))
                .perform(typeText("2021"));
        onView(withId(R.id.etDateToStartMonth))
                .perform(typeText("11"));
        onView(withId(R.id.etDateToStartDay))
                .perform(typeText("11"), closeSoftKeyboard());

        // Set active days
        onView(withId(R.id.cMonday))
                .perform(click());
        onView(withId(R.id.cTuesday))
                .perform(click());
        onView(withId(R.id.cWednesday))
                .perform(click());
        onView(withId(R.id.cThursday))
                .perform(click());
        onView(withId(R.id.cTFriday))
                .perform(click());
        onView(withId(R.id.cSaturday))
                .perform(click());
        onView(withId(R.id.cSunday))
                .perform(click());
        SystemClock.sleep(100);

        // Set as private
        onView(withId(R.id.toggleIsPublic))
                .perform(click());

        // Confirm the new habit
        onView(withId(R.id.btnConfirmAddHabit))
                .perform(click());

        // Confirm new habit exists in the view and clickable
        SystemClock.sleep(1000);
        onView(withText("Walking"))
                .check(matches(isDisplayed()));

    }

    @Test
    public void GTestViewFriends(){
        signIn();

        // Navigate to the friends tab
        onView(withId(R.id.friends))
                .perform(click());

        // Navigate to friend Jane Doe
        onView(withText("JaneDoe"))
                .perform(click());

        // Confirm her public habit is displayed
        onView(withText("Study math"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void FTestSignOut(){
        signIn();

        // Navigate to profile tab
        onView(withId(R.id.profile))
                .perform(click());
        SystemClock.sleep(100);
        onView(withText("SIGN OUT")) // Sign user out
                .perform(click());
    }

    public void signIn(){
        // Enter test user John Doe's email and password and sign in
        onView(withId(R.id.etEmail))
                .perform(typeText("JohnDoe@gmail.com"))
                .check(matches(withText("JohnDoe@gmail.com")));
        onView(withId(R.id.etPassword))
                .perform(typeText("JohnDoe"), pressImeActionButton())
                .check(matches(withText("JohnDoe")));
        onView(withText("SIGN IN"))
                .perform(click());
        SystemClock.sleep(2500);
    }
}