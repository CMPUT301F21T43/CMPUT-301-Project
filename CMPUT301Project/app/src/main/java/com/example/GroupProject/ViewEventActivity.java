/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ViewEventActivity extends AppCompatActivity {

    private static final String TAG = "HabitEventDetails";

    private ImageButton btnBack;
    private ImageButton btnEditEvent;
    private TextView tvEventTitle;
    private TextView tvEventComment;
    private TextView tvEventLocation;
    private ImageView ivEventPhoto;
    private CheckBox cbEventDone;

    DatabaseReference getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        FirebaseFirestore db;
        btnBack = findViewById(R.id.btnViewEventBack);
        btnEditEvent = findViewById(R.id.btnEditEvent);
        tvEventTitle = findViewById(R.id.tvEventTitle);
        tvEventComment = findViewById(R.id.tvEventComment);
        tvEventLocation = findViewById(R.id.tvEventLocation);
        ivEventPhoto = findViewById(R.id.ivViewEventPhoto);
        cbEventDone = findViewById(R.id.cbEventDone);

        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        db = MainActivity.getFirestoreInstance();

        tvEventTitle.setText(event.getTitle());
        tvEventComment.setText(event.getComment());
        tvEventLocation.setText(event.getLocationString());
        getImage = databaseReference.child(event.getPhotoID());
        cbEventDone.setChecked(event.getDone());

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(ivEventPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewEventActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(view -> {
            finish();
            return;
        });

        btnEditEvent.setOnClickListener(view -> {
            Intent intent12 = new Intent(ViewEventActivity.this, EditEventActivity.class);
            intent12.putExtra("EVENT", event);
            intent12.putExtra("HABIT", habit);
            startActivity(intent12);
        });
    }
}
