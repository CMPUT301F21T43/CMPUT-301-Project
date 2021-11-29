/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Habit fragment class
 * @Author Martin Rudolf, Kyle Bricker
 */
public class HabitFragment extends Fragment {

    private static final String TAG = "HabitFragment";

    Context thisContext;
    ListView habitList;
    ArrayAdapter<Habit> habitAdapter;
    ArrayList<Habit> habitDataList;
    FloatingActionButton btnAddHabit;

    public HabitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HabitFragment.
     */
    public static HabitFragment newInstance() {
        HabitFragment fragment = new HabitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db;
        db = getFirestoreInstance();

        String username = ((GroupProject) getActivity().getApplication()).getUsername();

        habitList = view.findViewById(R.id.habit_list);
        habitList.setAdapter(habitAdapter);

        habitList.setOnItemClickListener((adapterView, view12, i, l) -> {
            Intent intent = new Intent(thisContext, ViewHabitActivity.class);
            intent.putExtra("HABIT", (Serializable) adapterView.getItemAtPosition(i));
            startActivity(intent);
        });

        habitList.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            Habit habit = (Habit) adapterView.getItemAtPosition(i);
            String habitTitle = habit.getTitle();
            db.collection("Users")
                    .document(username)
                    .collection("Habits")
                    .document(habitTitle)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Habit successfully deleted!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
            habitAdapter.remove((Habit) adapterView.getItemAtPosition(i));
            habitAdapter.notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = ((GroupProject) getActivity().getApplication()).getUsername();
        FirebaseFirestore db;

        // Add habit objects to list in listview
        habitDataList = new ArrayList<>();
        db = getFirestoreInstance();
        db.collection("Users")
                .document(username)
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

                            habitAdapter.add((newHabit));
                            habitAdapter.notifyDataSetChanged();
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        habitAdapter = new CustomList(thisContext, habitDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_habit, null);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit, container, false);
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

}