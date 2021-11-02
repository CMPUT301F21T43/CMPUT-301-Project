package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditHabitActivity extends AppCompatActivity {
    ChipGroup cgDaysOfWeek;
    EditText etHabitTitle;
    EditText etHabitReason;
    DatePicker dpDateToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);


        etHabitTitle = findViewById(R.id.etHabitTitle);
        etHabitReason = findViewById(R.id.etHabitReason);
        dpDateToStart = findViewById(R.id.dpDateToStart);
        cgDaysOfWeek = findViewById(R.id.cgDaysOfWeek);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnConfirmEditHabit = findViewById(R.id.btnConfirmEditHabit);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();
        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");
        etHabitTitle.setText(habit.getTitle());
        etHabitReason.setText(habit.getReason());

        btnBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(EditHabitActivity.this, ViewHabitActivity.class);
            intentBack.putExtra("HABIT", (Serializable) habit);
            startActivity(intentBack);
        });

        btnConfirmEditHabit.setOnClickListener(view -> {
            Map<String, Object> editHabit = new HashMap<>();

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

            editHabit.put("title", habitTitle);
            editHabit.put("reason", habitReason);
            editHabit.put("isPublic", isPublic);
            editHabit.put("dateToStart", new Timestamp(dateToStart));
            editHabit.put("activeDays", checkedDaysChips());

            Habit newHabit = new Habit(habitTitle, habitReason, new Timestamp(dateToStart).toDate(), checkedDaysChips(), isPublic);

            db.collection("Users").document("John Doe").collection("Habits").document(habitTitle)
                    .set(editHabit, SetOptions.merge());

            Intent intentEdit = new Intent(EditHabitActivity.this, MainActivity.class);
            intentEdit.putExtra("HABIT", (Serializable) newHabit);
            startActivity(intentEdit);
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