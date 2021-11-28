package com.example.GroupProject;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
    public static Boolean isDateToStartGood(Integer year, Integer month, Integer day) {
        Boolean dayGood = false;
        switch (month) {
            // JAN
            case 0:
                dayGood = day <= 31;
                break;
            // FEB
            case 1:
                if (year % 4 == 0){
                    dayGood = day <= 29;
                } else {
                    dayGood = day <= 28;
                }
                break;
            // MAR
            case 2:
                dayGood = day <= 31;
                break;
            // APR
            case 3:
                dayGood = day <= 30;
                break;
            // MAY
            case 4:
                dayGood = day <= 31;
                break;
            // JUN
            case 5:
                dayGood = day <= 30;
                break;
            // JUL
            case 6:
                dayGood = day <= 31;
                break;
            // AUG
            case 7:
                dayGood = day <= 31;
                break;
            // SEP
            case 8:
                dayGood = day <= 30;
                break;
            // OCT
            case 9:
                dayGood = day <= 31;
                break;
            // NOV
            case 10:
                dayGood = day <= 30;
                break;
            // DEC
            case 11:
                dayGood = day <= 31;
                break;
            default:
                break;
        }
        return dayGood;
    }
}
