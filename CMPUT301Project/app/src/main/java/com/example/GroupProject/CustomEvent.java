package com.example.GroupProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.GroupProject.HabitEvents;
import com.example.GroupProject.R;

import java.util.ArrayList;

/**
 * CustomList class for Habitevents main page
 * @Author Kourosh Kehtari
 */

public class CustomEvent extends ArrayAdapter<HabitEvents> {

    private final ArrayList<HabitEvents> events;
    private final Context context;

    public CustomEvent(Context context, ArrayList<HabitEvents> events){
        super(context,0,events);
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.eventcustom, parent,false);
        }

        HabitEvents event = events.get(position);

        TextView eventName= view.findViewById(R.id.event_name);

        eventName.setText(event.getTitle());

        return view;

    }

}