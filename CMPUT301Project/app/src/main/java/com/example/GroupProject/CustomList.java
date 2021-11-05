/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * CustomList class for storing a list of Habit object
 * @Author Marcus Bengert, Martin Rudolf
 */
//TODO: Add encapsulation on variables
public class CustomList extends ArrayAdapter<Habit> {


    private final ArrayList<Habit> habits;
    private final Context context;
    private Calendar today = Calendar.getInstance();


    public CustomList(Context context, ArrayList<Habit> habits){
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        Habit habit = habits.get(position);

        TextView tvHabitName = view.findViewById(R.id.habit_name);
        TextView tvHabitReason = view.findViewById(R.id.habit_reason);
        TextView tvHabitDateToStart = view.findViewById(R.id.habit_dateToStart);
        TextView tvDoToday = view.findViewById(R.id.tvDoToday);
        Button goEvents = view.findViewById(R.id.go_events);

        tvHabitName.setText(habit.getTitle());
        tvHabitReason.setText(habit.getReason());
        tvHabitDateToStart.setText(habit.getDateToStartAsString());
        tvDoToday.setVisibility(habit.isDayActive(today.get(Calendar.DAY_OF_WEEK)) ? View.VISIBLE : View.INVISIBLE);

        goEvents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), HabitEventsMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
                intent.putExtra("HABIT", habit);
                view.getContext().startActivity(intent);
            }
        });

        return view;

    }
}
