/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditEventActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 99;
    private static final String TAG = "EditEventActivity";
    private EditText editTitle;
    private EditText editComment;
    private ImageButton btnBack;
    private ImageButton btnConfirmEdit;
    private Button btnSelectImage;
    private ImageView ivEventPhoto;

    private boolean takenPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        String username = ((GroupProject) this.getApplication()).getUsername();

        editTitle = findViewById(R.id.etEditEventTitle);
        editComment = findViewById(R.id.etEditEventComment);
        btnBack = findViewById(R.id.btnBackEditEvent);
        btnConfirmEdit = findViewById(R.id.btnConfirmEditEvent);
        btnSelectImage = findViewById(R.id.btnSelectEditEvent);
        ivEventPhoto = findViewById(R.id.ivEditEventPhoto);

        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        editTitle.setText(event.getTitle());
        editComment.setText(event.getComment());

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

        takenPhoto = false;
        btnConfirmEdit.setOnClickListener(view -> {
            String eventPhotoID = takenPhoto ? AddEventActivity.uploadPhoto(ivEventPhoto) : event.getPhotoID();

            Map<String, Object> editEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = editTitle.getText().toString();
            String eventComment = editComment.getText().toString();

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
            }

            editEvent.put("title", eventTitle);
            editEvent.put("comment", eventComment);
            editEvent.put("photoID", eventPhotoID);
            HabitEvent newEvent = new HabitEvent(eventTitle, eventComment, eventPhotoID);

            db.collection("Users")
                    .document(username)
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(eventTitle)
                    .set(editEvent, SetOptions.merge());

            Intent intentEdit = new Intent(EditEventActivity.this, HabitEventsMainActivity.class);
            intentEdit.putExtra("EVENT", newEvent);
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
        }
    }
}