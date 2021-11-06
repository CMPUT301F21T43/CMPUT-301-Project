/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 99;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText etTitle;
    EditText etComment;
    ImageButton btnBack;
    ImageButton btnConfirm;
    Button btnSelectImage;
    ImageView ivImage;

    private String eventPhotoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        String username = ((GroupProject) this.getApplication()).getUsername();

        Intent intent = getIntent();
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");
        etTitle = findViewById(R.id.event_title);
        etComment = findViewById(R.id.event_comment);
        btnBack = findViewById(R.id.go_back1);
        btnConfirm = findViewById(R.id.confirm_1);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        ivImage = findViewById(R.id.ivAddEventPhoto);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        eventPhotoID = "default";
        btnConfirm.setOnClickListener(view -> {
            uploadFile();

            Map<String, Object> habitEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = etTitle.getText().toString();
            String eventComment = etComment.getText().toString();

            habitEvent.put("title", eventTitle);
            habitEvent.put("comment", eventComment);
            habitEvent.put("photoID", eventPhotoID);

            db.collection("Users")
                    .document(username)
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(eventTitle)
                    .set(habitEvent, SetOptions.merge());

            Intent intent1 = new Intent(AddEventActivity.this, HabitEventsMainActivity.class);
            intent1.putExtra("HABIT", habit);
            startActivity(intent1);
        });

        btnBack.setOnClickListener(view -> {
            finish();
            return;
        });

        btnSelectImage.setOnClickListener(view -> {
            dispatchTakePictureIntent();
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
            ivImage.setImageBitmap(imageBitmap);
        }
    }

    public void uploadFile() {
        ivImage.setDrawingCacheEnabled(true);
        ivImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        eventPhotoID = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(eventPhotoID + ".jpg");

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.i(TAG, task.getException().toString());
                        }
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                            Log.i(TAG, downloadUri.toString());
                            databaseRef.child(eventPhotoID).setValue(downloadUri.toString());
                        } else {
                            Log.i(TAG,"Failed uploading to Realtime database");
                        }
                    }
                });
            }
        });
    }
}