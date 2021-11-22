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

import com.example.GroupProject.HabitEvent;
import com.example.GroupProject.R;

import java.util.ArrayList;

/**
 * CustomList class for Habitevent main page
 * @Author Kourosh Kehtari
 */

public class CustomEventList extends ArrayAdapter<HabitEvent> {

    private final ArrayList<HabitEvent> events;
    private final Context context;

    public CustomEventList(Context context, ArrayList<HabitEvent> events){
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

        HabitEvent event = events.get(position);
        TextView eventName= view.findViewById(R.id.event_name);
        //Button editEvent = view.findViewById(R.id.edit_event);
        eventName.setText(event.getTitle());


        return view;

    }

}