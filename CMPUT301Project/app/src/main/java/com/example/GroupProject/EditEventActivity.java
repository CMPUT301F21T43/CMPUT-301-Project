/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditEventActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 99;
    public static final int REQUEST_LOCATION_CHANGE = 100;
    private static final String TAG = "EditEventActivity";
    private EditText editTitle;
    private EditText editComment;
    private TextView tvEventLocation;
    private ImageButton btnBack;
    private ImageButton btnConfirmEdit;
    private Button btnSelectImage;
    private Button btnChangeLocation;
    private ImageView ivEventPhoto;
    private CheckBox cbEventDone;

    private HabitEvent changedLocationEvent;
    private boolean takenPhoto;
    private boolean changedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        String username = ((GroupProject) this.getApplication()).getUsername();

        editTitle = findViewById(R.id.etEditEventTitle);
        editComment = findViewById(R.id.etEditEventComment);
        tvEventLocation = findViewById(R.id.tvEditEventLocation);
        btnBack = findViewById(R.id.btnBackEditEvent);
        btnConfirmEdit = findViewById(R.id.btnConfirmEditEvent);
        btnSelectImage = findViewById(R.id.btnSelectEditEvent);
        btnChangeLocation = findViewById(R.id.btnChangeLocation);
        ivEventPhoto = findViewById(R.id.ivEditEventPhoto);
        cbEventDone = findViewById(R.id.cbEditEventDone);

        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        editTitle.setText(event.getTitle());
        editComment.setText(event.getComment());
        tvEventLocation.setText(event.getLocationString());
        cbEventDone.setChecked(event.getDone());

        FirebaseFirestore db = MainActivity.getFirestoreInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        DatabaseReference getImage = databaseReference.child(event.getPhotoID());
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(ivEventPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditEventActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(view -> {
            finish();
            return;
        });

        btnSelectImage.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });

        changedLocation = false;
        btnChangeLocation.setOnClickListener(view -> {
            Intent intentEdit = new Intent(EditEventActivity.this, GetLocationActivity.class);
            intentEdit.putExtra("EVENT", event);
            intentEdit.putExtra("HABIT", habit);
            startActivityForResult(intentEdit, REQUEST_LOCATION_CHANGE);
        });

        takenPhoto = false;
        btnConfirmEdit.setOnClickListener(view -> {
            String eventPhotoID = takenPhoto ? AddEventActivity.uploadPhoto(ivEventPhoto) : event.getPhotoID();
            Map<String, Object> editEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = editTitle.getText().toString();
            String eventComment = editComment.getText().toString();
            boolean eventDone = cbEventDone.isChecked();
            double eventLat, eventLon;

            if (changedLocation) {
                eventLat = changedLocationEvent.getLatitude();
                eventLon = changedLocationEvent.getLongitude();
            } else {
                eventLat = event.getLatitude();
                eventLon = event.getLongitude();
            }

            if (!eventTitle.equals(event.getTitle())) {
                db.collection("Users")
                        .document(username)
                        .collection("Habits")
                        .document(habit.getTitle())
                        .collection("Events")
                        .document(event.getTitle())
                        .delete()
                        .addOnSuccessListener(aVoid -> Log.d("Event", "Event successfully deleted!"))
                        .addOnFailureListener(e -> Log.w("Event", "Error deleting document", e));

                db.collection("Users")
                        .document(username)
                        .collection("Habits")
                        .document(habit.getTitle())
                        .update("numEvents", FieldValue.increment(-1),
                                "numEventsDone", FieldValue.increment(-1));
            }

            editEvent.put("title", eventTitle);
            editEvent.put("comment", eventComment);
            editEvent.put("photoID", eventPhotoID);
            editEvent.put("latitude", eventLat);
            editEvent.put("longitude", eventLon);
            editEvent.put("isDone", eventDone);

            db.collection("Users")
                    .document(username)
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(eventTitle)
                    .set(editEvent, SetOptions.merge());

            if (eventDone) {
                db.collection("Users")
                        .document(username)
                        .collection("Habits")
                        .document(habit.getTitle())
                        .update("numEventsDone", FieldValue.increment(1));
            }

            Intent intentEdit = new Intent(EditEventActivity.this, HabitEventsMainActivity.class);
            intentEdit.putExtra("HABIT", habit);
            startActivity(intentEdit);
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivEventPhoto.setImageBitmap(imageBitmap);
            takenPhoto = true;
        } else if (requestCode == REQUEST_LOCATION_CHANGE) {
            Bundle extras = data.getExtras();
            changedLocationEvent = (HabitEvent) extras.get("EVENT");
            tvEventLocation.setText(changedLocationEvent.getLocationString());
            changedLocation = true;
        }
    }
}