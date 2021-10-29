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
 * CustomList class for Habitevents main page
 * @Author Kourosh Kehtari
 */

public class EventsCustom extends ArrayAdapter<HabitEvents> {

    private final ArrayList<HabitEvents> events;
    private final Context context;

    public EventsCustom(Context context, ArrayList<HabitEvents> events){
        super(context,0,events);
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.eventscustom, parent,false);
        }

        HabitEvents event = events.get(position);

        TextView eventName= view.findViewById(R.id.event_name);

        eventName.setText(event.getTitle());

        return view;

    }
}
