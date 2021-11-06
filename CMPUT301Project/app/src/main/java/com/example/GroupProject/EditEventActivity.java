package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditEventActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editComment;
    private ImageButton btnBack;
    private ImageButton btnConfirmEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        String username = ((GroupProject) this.getApplication()).getUsername();

        editTitle = findViewById(R.id.edit_title);
        editComment = findViewById(R.id.edit_comment);
        btnBack = findViewById(R.id.back_edit);
        btnConfirmEdit = findViewById(R.id.confirm_edit);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();
        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        editTitle.setText(event.getTitle());
        editComment.setText(event.getComment());

        btnBack.setOnClickListener(view -> {
            finish();
            return;
        });

        btnConfirmEdit.setOnClickListener(view -> {
            Map<String, Object> editEvent = new HashMap<>();

            // Add all these to Firestore
            String eventTitle = editTitle.getText().toString();
            String eventComment = editComment.getText().toString();

            editEvent.put("title", eventTitle);
            editEvent.put("comment", eventComment);
            editEvent.put("photoID", event.getPhotoID());
            HabitEvent newEvent = new HabitEvent(eventTitle, eventComment, event.getPhotoID());

            db.collection("Users")
                    .document("John Doe")
                    .collection("Habits")
                    .document(habit.getTitle())
                    .collection("Events")
                    .document(event.getTitle())
                    .set(editEvent, SetOptions.merge());

            Intent intentEdit = new Intent(EditEventActivity.this, HabitEventsMainActivity.class);
            intentEdit.putExtra("EVENT", newEvent);
            intentEdit.putExtra("HABIT", habit);
            startActivity(intentEdit);
        });

    }
}