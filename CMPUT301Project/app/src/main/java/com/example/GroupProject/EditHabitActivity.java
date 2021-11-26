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
    private EditText etDateToStartYear;
    private EditText etDateToStartMonth;
    private EditText etDateToStartDay;
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

        etDateToStartDay.setText(habit.getDateToStartDay());
        etDateToStartMonth.setText(habit.getDateToStartMonth());
        etDateToStartYear.setText(habit.getDateToStartYear());

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

            Integer year = Integer.parseInt(etDateToStartYear.getText().toString());
            Integer month = Integer.parseInt(etDateToStartMonth.getText().toString());
            Integer day = Integer.parseInt(etDateToStartDay.getText().toString());

            if (!isDateToStartGood(year, month - 1, day)) {
                Toast.makeText(this, "Month and date need to be valid.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month - 1, day);
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
            intentEdit.putExtra("SELECTED", R.id.habit);
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