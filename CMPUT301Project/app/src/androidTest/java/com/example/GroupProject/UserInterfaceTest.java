package com.example.GroupProject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import android.os.SystemClock;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * UI Test class that test the various UI features,
 * like add, delete, and edit habits
 * @Author Marcus Bengert
 * TODO: Implement a mock data set for  better date verification
 */

public class UserInterfaceTest {

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

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
        // Confirm new habit exists in the view
        SystemClock.sleep(1000);
        onView(withText("Walking")).perform(click());

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
    public void testDeleteHabit(){
        SystemClock.sleep(1000);
        onData(anything()).atPosition(0).perform(longClick());
        SystemClock.sleep(100);
    }


}
