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

public class EditHabitActivity extends AppCompatActivity {
    private ChipGroup cgDaysOfWeek;
    private EditText etHabitTitle;
    private EditText etHabitReason;
    private DatePicker dpDateToStart;
    private ToggleButton toggleIsPublic;
    private Boolean isPublic;
    private ImageButton btnBack;
    private ImageButton btnConfirmEditHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        String username = ((GroupProject) this.getApplication()).getUsername();

        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        dpDateToStart = findViewById(R.id.dpDateToStart);
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

        // TODO: Set the calendar to correspond to Habit
        Date oldDateToStart = habit.getDateToStart();
        dpDateToStart.init(oldDateToStart.getYear(), oldDateToStart.getMonth(), oldDateToStart.getDay(), null);

        btnBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(EditHabitActivity.this, ViewHabitActivity.class);
            intentBack.putExtra("HABIT", habit);
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

            int day = dpDateToStart.getDayOfMonth();
            int month = dpDateToStart.getMonth();
            int year = dpDateToStart.getYear();

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month, day);
            Timestamp timestampDateToStart = new Timestamp(dateToStart);

            editHabit.put("title", habitTitle);
            editHabit.put("reason", habitReason);
            editHabit.put("isPublic", isPublic);
            editHabit.put("dateToStart", new Timestamp(dateToStart));
            editHabit.put("activeDays", checkedDaysChips());

            Habit newHabit = new Habit(habitTitle, habitReason, new Timestamp(dateToStart).toDate(), checkedDaysChips(), isPublic);

            db.collection("Users").document(username).collection("Habits").document(habitTitle)
                    .set(editHabit, SetOptions.merge());

            Intent intentEdit = new Intent(EditHabitActivity.this, MainActivity.class);
            intentEdit.putExtra("HABIT", newHabit);
            startActivity(intentEdit);
        });

        toggleIsPublic.setOnCheckedChangeListener((compoundButton, b) -> isPublic = b);
    }

    public Map<String, Boolean> checkedDaysChips() {
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