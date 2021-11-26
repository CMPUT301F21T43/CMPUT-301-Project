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
    ChipGroup cgDaysOfWeek;
    EditText etHabitTitle;
    EditText etHabitReason;
    DatePicker dpDateToStart;
    ToggleButton toggleIsPublic;
    Boolean isPublic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        String username = ((GroupProject) this.getApplication()).getUsername();

        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        dpDateToStart = findViewById(R.id.dpDateToStart);
        cgDaysOfWeek = findViewById(R.id.cgDaysOfWeek);
        ImageButton btnBack = findViewById(R.id.btnViewEventBack);
        ImageButton btnConfirmAddHabit = findViewById(R.id.btnConfirmAddHabit);
        ToggleButton toggleIsPublic = findViewById(R.id.toggleIsPublic);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();

        btnConfirmAddHabit.setOnClickListener(view -> {
            Map<String, Object> habit = new HashMap<>();

            // Add all these to Firestore
            String habitTitle = etHabitTitle.getText().toString();
            String habitReason = etHabitReason.getText().toString();

            int day = dpDateToStart.getDayOfMonth();
            int month = dpDateToStart.getMonth();
            int year = dpDateToStart.getYear();

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month, day, 4, 30);

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

}