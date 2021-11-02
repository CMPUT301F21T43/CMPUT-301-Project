package com.example.GroupProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;

        ProfileFragment profileFragment = new ProfileFragment();
        HabitFragment habitFragment = new HabitFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
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