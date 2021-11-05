package com.example.GroupProject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditHabitEvent extends AppCompatActivity {

    EditText editTitle;
    EditText editComment;
    ImageButton backEdit;
    ImageButton confirmEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit_event);

        String username = ((GroupProject) this.getApplication()).getUsername();

        editTitle = findViewById(R.id.edit_title);
        editComment = findViewById(R.id.edit_comment);
        backEdit = findViewById(R.id.back_edit);
        confirmEdit = findViewById(R.id.confirm_edit);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();
        Intent intent = getIntent();
        HabitEvents event = (HabitEvents) intent.getSerializableExtra("EVENT");
        editTitle.setText(event.getEventTitle());
        editComment.setText(event.getComment());

        backEdit.setOnClickListener(view -> {
            Intent intentBack = new Intent(EditHabitEvent.this, HabitEventsMain.class);
            startActivity(intentBack);
        });

        confirmEdit.setOnClickListener(view -> {
            Map<String, Object> editEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = editTitle.getText().toString();
            String eventComment = editComment.getText().toString();


            editEvent.put("title", eventTitle);
            editEvent.put("comment", eventComment);

            HabitEvents newEvent = new HabitEvents(eventTitle, eventComment);

            db.collection("Users")
                    .document(username)
                    .collection("Habits")
                    .document(event.getTitle())
                    .collection("Events")
                    .document(event.getEventTitle())
                    .set(editEvent, SetOptions.merge());

            Intent intentEdit = new Intent(EditHabitEvent.this, HabitEventsMain.class);
            intentEdit.putExtra("EVENT", newEvent);
            startActivity(intentEdit);
        });

    }
}
