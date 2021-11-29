/*
 * CustomList
 *
 * Version 1.0
 *
 * November 4, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * CustomList class for storing a list of Habit object
 * @Author Marcus Bengert, Martin Rudolf
 */
public class CustomList extends ArrayAdapter<Habit> {
    private final ArrayList<Habit> habits;
    private final Context context;
    private final Calendar today = Calendar.getInstance();

    /**
     * Constructor for CustomList
     * @param context the context of the fragment when attached
     * @param habits habits to pass in to the CustomList
     */
    public CustomList(Context context, ArrayList<Habit> habits){
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    /**
     * Sets the contents for each of the habit instances.
     * @param position in custom list
     * @param convertView
     * @param parent
     * @return returns the View to plugin
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habit_custom, parent,false);
        }

        Habit habit = habits.get(position);

        TextView tvHabitName = view.findViewById(R.id.habit_name);
        TextView tvHabitReason = view.findViewById(R.id.habit_reason);
        TextView tvHabitDateToStart = view.findViewById(R.id.habit_dateToStart);
        TextView tvDoToday = view.findViewById(R.id.tvDoToday);
        CircularProgressIndicator cpiHabitProgressIndicator = view.findViewById(R.id.habit_progress_indicator);

        tvHabitName.setText(habit.getTitle());
        tvHabitReason.setText(habit.getReason());
        tvHabitDateToStart.setText(habit.getDateToStartAsString());
        tvDoToday.setVisibility(habit.isDayActive(today.get(Calendar.DAY_OF_WEEK)) ? View.VISIBLE : View.INVISIBLE);
        cpiHabitProgressIndicator.setProgress(habit.getProgress());

        return view;
    }
}
