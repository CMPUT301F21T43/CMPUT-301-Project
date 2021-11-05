/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */


package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

/**
 * Implements the functionality behind viewing the details of a Habit.
 * Get here by clicking on a Habit in the MainActivity list of habits.
 */
public class ViewHabitActivity extends AppCompatActivity {
    ImageButton btnBack;
    ImageButton btnEditHabit;
    Button goEvents;
    TextView tvHabitTitle;
    TextView tvHabitReason;
    TextView tvHabitDateToStart;
    TextView tvHabitActiveDays;
    TextView tvHabitIsPublic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        btnBack  = findViewById(R.id.btnBack);
        btnEditHabit = findViewById(R.id.btnEditHabit);
        goEvents =findViewById(R.id.go_events1);
        tvHabitTitle = findViewById(R.id.tvHabitTitle);
        tvHabitReason = findViewById(R.id.tvHabitReason);
        tvHabitDateToStart = findViewById(R.id.tvHabitDateToStart);
        tvHabitActiveDays = findViewById(R.id.tvHabitActiveDays);
        tvHabitIsPublic = findViewById(R.id.tvHabitIsPublic);

        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        btnBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(ViewHabitActivity.this, MainActivity.class);
            startActivity(intent1);
        });

        btnEditHabit.setOnClickListener(view -> {
            Intent intent12 = new Intent(ViewHabitActivity.this, EditHabitActivity.class);
            intent12.putExtra("HABIT", habit);
            startActivity(intent12);
        });

        goEvents.setOnClickListener(view -> {
            Intent intent123 = new Intent(ViewHabitActivity.this, HabitEventsMain.class);
            intent123.putExtra("HABIT", habit);
            startActivity(intent123);
        });


        tvHabitTitle.setText(habit.getTitle());
        tvHabitReason.setText(habit.getReason());
        tvHabitDateToStart.setText(habit.getDateToStartAsString());

        StringBuilder sbActiveDays = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : habit.getActiveDays().entrySet()){
            // Each day should be accessible from habits to see if it's true or not
            if (entry.getValue()){
                sbActiveDays.append(" ");
                sbActiveDays.append(entry.getKey());
            }
        }
        tvHabitActiveDays.setText(sbActiveDays);
        tvHabitIsPublic.setText(habit.getPublic() ? "Public" : "Private");
    }
}