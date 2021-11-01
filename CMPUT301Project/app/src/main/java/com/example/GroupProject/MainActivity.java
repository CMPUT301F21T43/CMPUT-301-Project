package com.example.GroupProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView habitList;
    ArrayAdapter<Habit> habitAdapter;
    ArrayList<Habit> habitDataList;
    FloatingActionButton btnAddHabit;

    DocumentSnapshot habitDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db;
        habitList = findViewById(R.id.habit_list);
        btnAddHabit = findViewById(R.id.btnAddHabit);

        btnAddHabit.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            startActivity(intent);
        });


        // Add habit objects to list in listview
        habitDataList = new ArrayList<>();
        db = getFirestoreInstance();
        db.collection("Users")
            .document("John Doe")
            .collection("Habits")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String habitTitle;
                    String habitReason;
                    Timestamp timestamp;
                    Date dateToStart;
                    Boolean isPublic;
                    Map<String, Boolean> activeDays;
                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        habitTitle = (String) document.get("title");
                        habitReason = (String) document.get("reason");
                        timestamp = (Timestamp) document.get("dateToStart");
                        assert timestamp != null;
                        dateToStart = timestamp.toDate();
                        activeDays = (Map<String, Boolean>) document.get("activeDays");
                        isPublic = (Boolean) document.get("isPublic");
                        habitAdapter.add((new Habit(habitTitle, habitReason, dateToStart, activeDays, isPublic)));
                        habitAdapter.notifyDataSetChanged();
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewHabitActivity.class);
                intent.putExtra("HABIT", (Serializable) adapterView.getItemAtPosition(i));
                startActivity(intent);
            }
        });

        habitList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit = (Habit) adapterView.getItemAtPosition(i);
                String habitTitle = habit.getTitle();
                db.collection("Users")
                        .document("John Doe")
                        .collection("Habits")
                        .document(habitTitle)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Habit successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                habitAdapter.remove((Habit) adapterView.getItemAtPosition(i));
                habitAdapter.notifyDataSetChanged();
                return true;
            }
        });

        habitAdapter = new CustomList(this, habitDataList);
        habitList.setAdapter(habitAdapter);
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }
}