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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditHabitActivity extends AppCompatActivity {
    private ChipGroup cgDaysOfWeek;
    private EditText etHabitTitle;
    private EditText etHabitReason;
    private EditText etDateToStartYear;
    private EditText etDateToStartMonth;
    private EditText etDateToStartDay;
    private DatePicker dpDateToStart;
    private ToggleButton toggleIsPublic;
    private Boolean isPublic;
    private ImageButton btnBack;
    private ImageButton btnConfirmEditHabit;

    private Long year = null;
    private Long month = null;
    private Long day = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        String username = ((GroupProject) this.getApplication()).getUsername();

        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        etDateToStartYear = findViewById(R.id.etDateToStartYear);
        etDateToStartMonth = findViewById(R.id.etDateToStartMonth);
        etDateToStartDay = findViewById(R.id.etDateToStartDay);
        cgDaysOfWeek = findViewById(R.id.cgDaysOfWeek);
        btnBack = findViewById(R.id.btnViewEventBack);
        btnConfirmEditHabit = findViewById(R.id.btnConfirmEditHabit);
        toggleIsPublic = findViewById(R.id.toggleIsPublic);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();
        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");
        etHabitTitle.setText(habit.getTitle());
        etHabitReason.setText(habit.getReason());
        isPublic = habit.getPublic();
        toggleIsPublic.setChecked(isPublic);

        cgDaysOfWeek.clearCheck();
        for (int i = 0; i < cgDaysOfWeek.getChildCount(); i++) {
            Chip chip = (Chip) cgDaysOfWeek.getChildAt(i);
            String chipText = (String) chip.getText();
            if (habit.getActiveDays().get(chipText)) { cgDaysOfWeek.check(chip.getId()); }
        }

        etDateToStartDay.setText(habit.getDayToStart().toString());
        etDateToStartMonth.setText(habit.getMonthToStart().toString());
        etDateToStartYear.setText(habit.getYearToStart().toString());

        btnBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(EditHabitActivity.this, ViewHabitActivity.class);
            intentBack.putExtra("HABIT", habit);
            intent.putExtra("SELECTED", R.id.habit);
            startActivity(intentBack);
        });

        btnConfirmEditHabit.setOnClickListener(view -> {
            Map<String, Object> editHabit = new HashMap<>();

            // Add all these to Firestore
            String habitTitle = etHabitTitle.getText().toString();
            String habitReason = etHabitReason.getText().toString();

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


            editHabit.put("title", habitTitle);
            editHabit.put("reason", habitReason);
            editHabit.put("isPublic", isPublic);
            editHabit.put("yearToStart", year);
            editHabit.put("monthToStart", month);
            editHabit.put("dayToStart", day);
            editHabit.put("activeDays", HabitActivityHelper.checkedDaysChips(cgDaysOfWeek));

            Habit newHabit = new Habit(habitTitle, habitReason, year, month, day, HabitActivityHelper.checkedDaysChips(cgDaysOfWeek), isPublic);

            db.collection("Users").document(username).collection("Habits").document(habitTitle)
                    .set(editHabit, SetOptions.merge());

            Intent intentEdit = new Intent(EditHabitActivity.this, MainActivity.class);
            intentEdit.putExtra("HABIT", newHabit);
            intentEdit.putExtra("SELECTED", R.id.habit);
            startActivity(intentEdit);
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
        year = Long.parseLong(yearStr);
        month = Long.parseLong(monthStr);
        day = Long.parseLong(dayStr);
        return 0;
    }

}