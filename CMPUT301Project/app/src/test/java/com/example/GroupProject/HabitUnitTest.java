package com.example.GroupProject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HabitUnitTest {
    public Habit testHabit;
    @Before
    public void setUpHabit() {
        Map<String, Boolean> testActiveDays = new HashMap<>();
        testActiveDays.put("Monday", true);
        testActiveDays.put("Tuesday", false);
        testHabit = new Habit("Testing", "Robustness", new Date(), testActiveDays, false);
    }

    @Test
    public void testIsDayActive() {
        assertEquals(testHabit.isDayActive(Calendar.MONDAY), true);
        assertEquals(testHabit.isDayActive(Calendar.TUESDAY), false);
    }

}
