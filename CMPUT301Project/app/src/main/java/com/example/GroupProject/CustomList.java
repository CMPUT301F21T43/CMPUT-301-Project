package com.example.GroupProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * CustomList class for storing a list of Habit object
 * @Author Marcus Bengert
 */

public class CustomList extends ArrayAdapter<Habit> {


    private final ArrayList<Habit> habits;
    private final Context context;

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

        tvHabitName.setText(habit.getTitle());
        tvHabitReason.setText(habit.getReason());
        tvHabitDateToStart.setText(habit.getDateToStartAsString());

        return view;

    }
}
