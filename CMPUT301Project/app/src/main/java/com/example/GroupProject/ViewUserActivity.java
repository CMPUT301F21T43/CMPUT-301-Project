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
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Implements the functionality behind viewing the details of a User.
 * Get here by clicking on a User in the FriendsFragment list of users.
 */
public class ViewUserActivity extends AppCompatActivity {

    private static final String TAG = "ViewUserActivity";

    ImageButton btnBack;
    TextView tvUserUsername;
    TextView tvUserFirstName;
    TextView tvUserLastName;

    ListView userHabitsList;
    ArrayAdapter<Habit> userHabitsAdapter;
    ArrayList<Habit> userHabitsDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        btnBack  = findViewById(R.id.btnBack);
        tvUserUsername = findViewById(R.id.tvUserUsername);
        tvUserFirstName = findViewById(R.id.tvUserFirstName);
        tvUserLastName = findViewById(R.id.tvUserLastName);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("USER");

        btnBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(ViewUserActivity.this, MainActivity.class);
            intent1.putExtra("SELECTED", R.id.friends);
            startActivity(intent1);
        });

        tvUserUsername.setText(user.getUsername());
        tvUserFirstName.setText(user.getFirstName());
        tvUserLastName.setText(user.getLastName());

        FirebaseFirestore db;
        db = getFirestoreInstance();

        // Add habit objects to list in listview
        userHabitsDataList = new ArrayList<>();

            db.collection("Users")
                    .document(user.getUsername())
                    .collection("Habits")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String habitTitle;
                            String habitReason;
                            Long yearToStart;
                            Long monthToStart;
                            Long dayToStart;
                            Boolean isPublic;
                            Long numEvents;
                            Long numEventsDone;
                            Map<String, Boolean> activeDays;
                            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                habitTitle = (String) document.get("title");
                                habitReason = (String) document.get("reason");
                                yearToStart = (Long) document.get("yearToStart");
                                monthToStart = (Long) document.get("monthToStart");
                                dayToStart = (Long) document.get("dayToStart");
                                activeDays = (Map<String, Boolean>) document.get("activeDays");
                                isPublic = (Boolean) document.get("isPublic");
                                Habit newHabit = new Habit(habitTitle, habitReason, yearToStart, monthToStart, dayToStart, activeDays, isPublic);

                                numEvents = (Long) document.get("numEvents");
                                numEventsDone = (Long) document.get("numEventsDone");
                                if (numEvents != 0 && numEventsDone != 0) {
                                    newHabit.setProgress(Math.round(100 * numEventsDone / numEvents));
                                }

                                userHabitsAdapter.add((newHabit));
                                userHabitsAdapter.notifyDataSetChanged();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        userHabitsAdapter = new CustomList(this, userHabitsDataList);
        userHabitsList = findViewById(R.id.user_habits_list);
        userHabitsList.setAdapter(userHabitsAdapter);
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }
}