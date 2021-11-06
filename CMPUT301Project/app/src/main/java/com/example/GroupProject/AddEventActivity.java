/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    EditText title;
    EditText comment;
    ImageButton goBack;
    ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");
        title = findViewById(R.id.event_title);
        comment = findViewById(R.id.event_comment);
        goBack = findViewById(R.id.go_back1);
        confirm = findViewById(R.id.confirm_1);

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        confirm.setOnClickListener(view -> {
            Map<String, Object> habitEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = title.getText().toString();
            String eventComment = comment.getText().toString();

            habitEvent.put("title", eventTitle);
            habitEvent.put("comment", eventComment);

            db.collection("Users")
                    .document("John Doe")
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(eventTitle)
                    .set(habitEvent, SetOptions.merge());

            Intent intent1 = new Intent(AddEventActivity.this, HabitEventsMainActivity.class);
            startActivity(intent1);
        });

        goBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(AddEventActivity.this, HabitEventsMainActivity.class);
            startActivity(intent1);
        });
    }
}