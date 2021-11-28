package com.example.GroupProject;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class HabitActivityHelper {

    /**
     * Retrieves the chips for active days of the week.
     * @return Map with key := String representing the name of the day of the week
     *                  value := Boolean whether it is checked or not.
     */
    public static Map<String, Boolean> checkedDaysChips(ChipGroup cgDaysOfWeek){
        Map<String, Boolean> checkedDays = new HashMap<>();
        for (int i = 0; i < cgDaysOfWeek.getChildCount(); i++){
            Chip chip  = (Chip) cgDaysOfWeek.getChildAt(i);
            if (chip.isChecked()) {
                checkedDays.put((String) chip.getText(), true);
            } else {
                checkedDays.put((String) chip.getText(), false);
            }
        }
        return checkedDays;
    }

    /**
     * Checks that the DateToStart is a valid combination of Year, Month and Day.
     * @param year Integer year to check.
     * @param month Integer month to check.
     * @param day Integer day to check.
     * @return true if it is a valid day, false otherwise.
     */
    public static Boolean isDateToStartGood(Long year, Long month, Long day) {
        Boolean dayGood = false;
        // JAN
        if (month == 1) {
            dayGood = day <= 31;
        // FEB
        } else if (month == 2) {
            if (year % 4 == 0) {
                dayGood = day <= 29;
            } else {
                dayGood = day <= 28;
            }
        // MAR
        } else if (month == 3) {
            dayGood = day <= 31;
        // APR
        } else if (month == 4) {
            dayGood = day <= 30;
        // MAY
        } else if (month == 5) {
            dayGood = day <= 31;
        // JUN
        } else if (month == 6) {
            dayGood = day <= 30;
        // JUL
        } else if (month == 7) {
            dayGood = day <= 31;
        // AUG
        } else if (month == 8) {
            dayGood = day <= 31;
        // SEP
        } else if (month == 9) {
            dayGood = day <= 30;
        // OCT
        } else if (month == 10) {
            dayGood = day <= 31;
        // NOV
        } else if (month == 11) {
            dayGood = day <= 30;
        // DEC
        } else if (month == 12) {
            dayGood = day <= 31;
        }
        return dayGood;
    }
}
