package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewEventActivity extends AppCompatActivity {

    private static final String TAG = "HabitEventDetails";

    ImageButton btnBack;
    ImageButton btnEditEvent;
    TextView tvEventTitle;
    TextView tvEventComment;
    ImageView ivEventPhoto;

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
        ivEventPhoto = findViewById(R.id.ivEventPhoto);

        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        db = MainActivity.getFirestoreInstance();

        tvEventTitle.setText(event.getTitle());
        tvEventComment.setText(event.getComment());

        getImage = databaseReference.child(event.getPhotoID());

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
