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

    private String habitTitle = null;
    private String habitReason = null;

    private Integer year = null;
    private Integer month = null;
    private Integer day = null;

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

            if (getDateTexts() == -1) {
                Toast.makeText(this, "Date To Start fields are needed.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!HabitActivityHelper.isDateToStartGood(year, month, day)) {
                Toast.makeText(this, "Month and date need to be valid.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month - 1, day, 4, 30);

            habit.put("title", habitTitle);
            habit.put("reason", habitReason);
            habit.put("isPublic", isPublic);
            habit.put("dateToStart", new Timestamp(dateToStart));
            habit.put("activeDays", HabitActivityHelper.checkedDaysChips(cgDaysOfWeek));

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

    /**
     * Checks that the Date To Start fields aren't empty and sets them if not.
     * @return -1 if at least one is empty, 0 otherwise.
     */
    private Integer getDateTexts() {
        String yearStr = etDateToStartYear.getText().toString();
        String monthStr = etDateToStartMonth.getText().toString();
        String dayStr = etDateToStartDay.getText().toString();
        if (yearStr.matches("") || monthStr.matches("") || dayStr.matches("")) {
            return -1;
        }
        year = Integer.parseInt(yearStr);
        month = Integer.parseInt(monthStr);
        day = Integer.parseInt(dayStr);
        return 0;
    }




}