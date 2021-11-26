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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Habit fragment class
 * @Author Simon Chen
 */
public class FriendsFragment extends Fragment {

    private static final String TAG = "FriendsFragment";

    Context thisContext;
    ListView userList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FriendsFragment.
     */
    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
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

        userList = view.findViewById(R.id.users_list);
        userList.setAdapter(userAdapter);

        userList.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = new Intent(thisContext, ViewUserActivity.class);
            intent.putExtra("USER", (Serializable) adapterView.getItemAtPosition(i));
            startActivity(intent);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = ((GroupProject) getActivity().getApplication()).getUsername();

        FirebaseFirestore db;
        db = getFirestoreInstance();

        // Add user objects to list in listview
        userDataList = new ArrayList<>();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userUsername;
                        String userFirstName;
                        String userLastName;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userUsername = document.getId();
                            userFirstName = (String) document.get("FirstName");
                            userLastName = (String) document.get("LastName");
                            if (!userUsername.equals(username)) {
                                userAdapter.add((new User(userUsername, userFirstName, userLastName)));
                                userAdapter.notifyDataSetChanged();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        userAdapter = new CustomUserList(thisContext, userDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friends, null);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

}