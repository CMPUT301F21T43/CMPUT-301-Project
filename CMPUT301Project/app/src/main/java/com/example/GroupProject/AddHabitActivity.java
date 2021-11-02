package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

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
    Chip chipMonday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        dpDateToStart = findViewById(R.id.dpDateToStart);
        cgDaysOfWeek = findViewById(R.id.cgDaysOfWeek);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnConfirmAddHabit = findViewById(R.id.btnConfirmAddHabit);
        chipMonday = findViewById(R.id.cMonday);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();

        btnConfirmAddHabit.setOnClickListener(view -> {
            Map<String, Object> habit = new HashMap<>();

            // Add all these to Firestore
            String habitTitle = etHabitTitle.getText().toString();
            String habitReason = etHabitReason.getText().toString();

            Boolean isPublic = false;

            int day = dpDateToStart.getDayOfMonth();
            int month = dpDateToStart.getMonth();
            int year = dpDateToStart.getYear();

            // Year is being given as 3921 for some reason.
            Date dateToStart = new Date(year, month, day);
            Timestamp timestampDateToStart = new Timestamp(dateToStart);

            habit.put("title", habitTitle);
            habit.put("reason", habitReason);
            habit.put("isPublic", isPublic);
            habit.put("dateToStart", new Timestamp(dateToStart));
            habit.put("activeDays", checkedDaysChips());

            db.collection("Users").document("John Doe").collection("Habits").document(habitTitle)
                    .set(habit, SetOptions.merge());
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
            startActivity(intent);
        });

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