/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    Context thisContext;

    EditText usernameEdit;
    EditText fnameEdit;
    EditText lnameEdit;
    ImageButton confirmUserbtn;

    User thisUser;
    String username;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String username) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
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

        confirmUserbtn.setOnClickListener(view1 -> {
            Map<String, Object> user = new HashMap<>();
            if (usernameEdit.getText() != null) {
                String newUsername = usernameEdit.getText().toString();
                thisUser.setUsername(newUsername);
            }
            if (fnameEdit.getText() != null) {
                String newFname = fnameEdit.getText().toString();
                thisUser.setFirstName(newFname);
            }
            if (lnameEdit.getText() != null) {
                String newLname = lnameEdit.getText().toString();
                thisUser.setLastName(newLname);
            }
            user.put("FirstName", thisUser.getFirstName());
            user.put("LastName", thisUser.getLastName());
            ((GroupProject) getActivity().getApplication()).setUsername(thisUser.getUsername());

            db.collection("Users").document(thisUser.getUsername())
                    .set(user, SetOptions.merge());
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //username = getArguments().getString("username");
        username = ((GroupProject) getActivity().getApplication()).getUsername();
        thisUser = new User(username, "", "");

        FirebaseFirestore db;
        db = getFirestoreInstance();
        db.collection("Users")
                .document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        String FirstName = (String) document.get("FirstName");
                        String LastName = (String) document.get("LastName");
                        thisUser.setFirstName(FirstName);
                        thisUser.setLastName(LastName);
                        usernameEdit.setText(thisUser.getUsername());
                        fnameEdit.setText(thisUser.getFirstName());
                        lnameEdit.setText(thisUser.getLastName());
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameEdit = view.findViewById(R.id.editTextUsername);
        fnameEdit = view.findViewById(R.id.editTextFirstName);
        lnameEdit = view.findViewById(R.id.editTextLastName);
        confirmUserbtn = view.findViewById(R.id.btnConfirmUser);

        return view;
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }
}