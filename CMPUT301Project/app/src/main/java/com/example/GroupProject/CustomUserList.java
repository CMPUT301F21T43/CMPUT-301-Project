/*
 * CustomUserList
 *
 * Version 1.0
 *
 * November 20, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * CustomUserList class for storing a list of User object
 * @Author Simon Chen
 */
public class CustomUserList extends ArrayAdapter<User> {
    private final ArrayList<User> users;
    private final Context context;

    /**
     * Constructor for CustomUserList
     * @param context the context of the fragment when attached
     * @param users users to pass in to the CustomUserList
     */
    public CustomUserList(Context context, ArrayList<User> users){
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    /**
     * Sets the contents for each of the user instances.
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
            view = LayoutInflater.from(context).inflate(R.layout.user_custom, parent,false);
        }

        User user = users.get(position);

        TextView tvUserUsername = view.findViewById(R.id.user_username);
        TextView tvUserFirstName = view.findViewById(R.id.user_first_name);
        TextView tvUserLastName = view.findViewById(R.id.user_last_name);

        tvUserUsername.setText(user.getUsername());
        tvUserFirstName.setText(user.getFirstName());
        tvUserLastName.setText(user.getLastName());

        return view;
    }
}
