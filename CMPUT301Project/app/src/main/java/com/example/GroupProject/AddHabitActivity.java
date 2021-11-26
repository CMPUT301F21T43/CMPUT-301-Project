/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddHabitActivity extends AppCompatActivity {
    private ChipGroup cgDaysOfWeek;
    private EditText etHabitTitle;
    private EditText etHabitReason;
    private EditText etDateToStartYear;
    private EditText etDateToStartMonth;
    private EditText etDateToStartDay;
    private ToggleButton toggleIsPublic;
    private Boolean isPublic = true;

    String habitTitle = null;
    String habitReason = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        String username = ((GroupProject) this.getApplication()).getUsername();

        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        etDateToStartYear = findViewById(R.id.etDateToStartYear);
        etDateToStartMonth = findViewById(R.id.etDateToStartMonth);
        etDateToStartDay = findViewById(R.id.etDateToStartDay);
        cgDaysOfWeek = findViewById(R.id.cgDaysOfWeek);
        ImageButton btnBack = findViewById(R.id.btnViewEventBack);
        ImageButton btnConfirmAddHabit = findViewById(R.id.btnConfirmAddHabit);
        ToggleButton toggleIsPublic = findViewById(R.id.toggleIsPublic);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();

        btnConfirmAddHabit.setOnClickListener(view -> {
            Map<String, Object> habit = new HashMap<>();

            // Add all these to Firestore
            habitTitle = etHabitTitle.getText().toString();
            habitReason = etHabitReason.getText().toString();

            if (habitTitle.matches("")) {
                Toast.makeText(this, "A habit title is needed.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (habitReason.matches("")) {
                Toast.makeText(this, "A habit reason is needed.", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer year = Integer.parseInt(etDateToStartYear.getText().toString());
            Integer month = Integer.parseInt(etDateToStartMonth.getText().toString());
            Integer day = Integer.parseInt(etDateToStartDay.getText().toString());

            if (!isDateToStartGood(year, month, day)) {
                Toast.makeText(this, "Month and date need to be valid.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month - 1, day, 4, 30);

            habit.put("title", habitTitle);
            habit.put("reason", habitReason);
            habit.put("isPublic", isPublic);
            habit.put("dateToStart", new Timestamp(dateToStart));
            habit.put("activeDays", checkedDaysChips());

            db.collection("Users").document(username).collection("Habits").document(habitTitle)
                    .set(habit, SetOptions.merge());

            Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
            startActivity(intent);
        });

        toggleIsPublic.setOnCheckedChangeListener((compoundButton, b) -> isPublic = b);
    }


    public Map<String, Boolean> checkedDaysChips(){
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

    public Boolean isDateToStartGood(Integer year, Integer month, Integer day) {
        Boolean dayGood = false;
        switch (month) {
            case 0:
                dayGood = day <= 31;
                break;
            case 1:
                if (year % 4 == 0){
                    dayGood = day <= 29;
                } else {
                    dayGood = day <= 28;
                }
                break;
            case 2:
                dayGood = day <= 31;
                break;
            case 3:
                dayGood = day <= 30;
                break;
            case 4:
                dayGood = day <= 31;
                break;
            case 5:
                dayGood = day <= 30;
                break;
            case 6:
                dayGood = day <= 31;
                break;
            case 7:
                dayGood = day <= 31;
                break;
            case 8:
                dayGood = day <= 30;
                break;
            case 9:
                dayGood = day <= 31;
                break;
            case 10:
                dayGood = day <= 30;
                break;
            case 11:
                dayGood = day <= 31;
                break;
            default:
                break;
        }
        return dayGood;
    }

}