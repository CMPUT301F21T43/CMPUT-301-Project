package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {



    ListView habitList;
    ArrayAdapter<Habit> habitAdapter;
    ArrayList<Habit> habitDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;
        habitList = findViewById(R.id.habit_list);


        // Random Habits to be removed eventually
        String []names ={"Walk dog", "Brush Teeth", "Take vitamins"};
        String []reasons = {"Exercise", "Clean teeth", "Health"};
        Date[]dates = new Date[3];


        // Add habit objects to list in listview
        habitDataList = new ArrayList<>();
        for(int i=0;i<names.length;i++){
            habitDataList.add((new Habit(names[i], reasons[i], dates[i])));
        }

        habitAdapter = new CustomList(this, habitDataList);
        habitList.setAdapter(habitAdapter);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }
}