/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */
package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HabitEventsMain extends AppCompatActivity{
    ListView eventList;
    ArrayList<HabitEvents> eventDataList;
    ArrayAdapter<HabitEvents> eventAdapter;
    ImageButton goBack;
    FloatingActionButton addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.eventlist));

        goBack = findViewById(R.id.back_button_events);
        addEvent = findViewById(R.id.add_events);
        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");
        eventList = findViewById(R.id.event_list);
        eventDataList = new ArrayList<>();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .document("John Doe")
                .collection("Habits")
                .document(habit.getTitle())
                .collection("Events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String habitEvent;
                        String comment;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            habitEvent = (String) document.get("title");
                            comment = (String) document.get("comment");
                            eventAdapter.add((new HabitEvents(habitEvent, comment)));
                            eventAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("HabitEvent","Error getting documents: ", task.getException());
                    }
                });


        eventAdapter = new CustomEvent(this,eventDataList);
        eventList.setAdapter(eventAdapter);


        goBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(HabitEventsMain.this, MainActivity.class);
            startActivity(intent1);
        });

        addEvent.setOnClickListener(view -> {
            Intent intent1 = new Intent(HabitEventsMain.this, AddHabitEvent.class);
            intent1.putExtra("HABIT", habit);
            startActivity(intent1);
        });

    }

}