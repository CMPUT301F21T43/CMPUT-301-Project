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

public class HabitEventsMainActivity extends AppCompatActivity{
    ListView eventList;
    ArrayList<HabitEvent> eventDataList;
    ArrayAdapter<HabitEvent> eventAdapter;
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
                        String title, comment, photoID;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            title = (String) document.get("title");
                            comment = (String) document.get("comment");
                            photoID = (String) document.get("photoID");
                            eventAdapter.add((new HabitEvent(title, comment, photoID)));
                            eventAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("HabitEvent","Error getting documents: ", task.getException());
                    }
                });


        eventAdapter = new CustomEventList(this,eventDataList);
        eventList.setAdapter(eventAdapter);


        goBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(HabitEventsMainActivity.this, MainActivity.class);
            startActivity(intent1);
        });

        addEvent.setOnClickListener(view -> {
            Intent intent12 = new Intent(HabitEventsMainActivity.this, AddEventActivity.class);
            intent12.putExtra("HABIT", habit);
            startActivity(intent12);
        });


        eventList.setOnItemClickListener((adapterView, view12, i, l) -> {
            HabitEvent event = (HabitEvent) adapterView.getItemAtPosition(i);
            Intent intentEvent = new Intent(HabitEventsMainActivity.this, ViewEventActivity.class);
            intentEvent.putExtra("EVENT", event);
            intentEvent.putExtra("HABIT", habit);
            startActivity(intentEvent);
        });

        eventList.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            HabitEvent event = (HabitEvent) adapterView.getItemAtPosition(i);
            db.collection("Users")
                    .document("John Doe")
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(event.getTitle())
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d("Event", "Event successfully deleted!"))
                    .addOnFailureListener(e -> Log.w("Event", "Error deleting document", e));
            eventAdapter.remove((HabitEvent) adapterView.getItemAtPosition(i));
            eventAdapter.notifyDataSetChanged();
            return true;
        });
    }
}