/*
 * MainActivity
 *
 * Version 1.5
 *
 * November 4, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MainActivity class that launches the HabitFragment and shows the list of Habits.
 * Also builds the bottomNavigationView.
 *
 * @author Kyle Bricker
 */
public class MainActivity extends AppCompatActivity {
    
    private int selected;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;

//        if (!((GroupProject) this.getApplication()).isSignedIn()) {
//            Intent intent = new Intent(this, EmailPasswordActivity.class);
//            startActivity(intent);
//        }

        Intent intent = getIntent();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment newFragment;
            switch (item.getItemId()) {
                case R.id.habit:
                    newFragment = new HabitFragment();
                    break;
                case R.id.friends:
                    newFragment = new FriendsFragment();
                    break;
                default:
                    newFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, newFragment).commit();
            return true;
        });

        int selected = intent.getIntExtra("SELECTED", R.id.habit);
        bottomNavigationView.setSelectedItemId(selected);
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

    public void addHabit(View view) {
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);
    }
}