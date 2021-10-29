package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {



    ListView habitList;
    ArrayAdapter<Habit> habitAdapter;
    ArrayList<Habit> habitDataList;
    FloatingActionButton btnAddHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;
        habitList = findViewById(R.id.habit_list);
        btnAddHabit = findViewById(R.id.btnAddHabit);

        btnAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(intent);
            }
        });


        // Random Habits to be removed eventually
        String []names ={"Walk dog", "Brush Teeth", "Take vitamins"};
        String []reasons = {"Exercise", "Clean teeth", "Health"};
        Date[]dates = new Date[3];
        String []activeDays = {"Monday", "Wednesday"};



        // Add habit objects to list in listview
        habitDataList = new ArrayList<>();
        for(int i=0;i<names.length;i++){
            habitDataList.add((new Habit(names[i], reasons[i], dates[i], activeDays)));
        }

        habitAdapter = new CustomList(this, habitDataList);
        habitList.setAdapter(habitAdapter);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}