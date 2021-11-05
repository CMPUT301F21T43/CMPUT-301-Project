/*
 * EditHabitActivity
 *
 * Version 1.0
 *
 * November 4, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */


package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

/**
 * Implements the functionality behind viewing the details of a Habit.
 * Get here by clicking on a Habit in the MainActivity list of habits.
 * @author martyrudolf
 */
public class ViewHabitActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageButton btnEditHabit;
    private TextView tvHabitTitle;
    private TextView tvHabitReason;
    private TextView tvHabitDateToStart;
    private TextView tvHabitActiveDays;
    private TextView tvHabitIsPublic;

    /**
     * This method is run to build the Habit detail View when ViewHabitActivity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        btnBack  = findViewById(R.id.btnBack);
        btnEditHabit = findViewById(R.id.btnEditHabit);
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