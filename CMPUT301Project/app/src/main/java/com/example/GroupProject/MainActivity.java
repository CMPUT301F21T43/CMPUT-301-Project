/*
 * MainActivity
 *
 * Version 1.0
 *
 * November 4, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * MainActivity class that launches the HabitFragment and shows the list of Habits.
 * Also builds the bottomNavigationView.
 *
 * @author Kyle Bricker
 */
public class MainActivity extends AppCompatActivity {
    
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;

        if (!((GroupProject) this.getApplication()).isSignedIn()) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }


        ProfileFragment profileFragment = new ProfileFragment();
        HabitFragment habitFragment = new HabitFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.habit:
                    selected = R.id.habit;
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, habitFragment).commit();
                    break;
                case R.id.profile:
                    selected = R.id.profile;
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, profileFragment).commit();
                    break;
            }
            return true;
        });
        if (selected == 0) {
            bottomNavigationView.setSelectedItemId(R.id.habit);
            selected = R.id.habit;
        }

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

    public void addHabit(View view) {
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);
    }
}