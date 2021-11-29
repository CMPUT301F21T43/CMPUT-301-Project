package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    ListView notificationList;
    ArrayList<String> friendRequestList;
    ArrayAdapter<String> requestAdapter;
    ImageButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        String username = ((GroupProject) this.getApplication()).getUsername();

        notificationList = findViewById(R.id.notification_list);
        friendRequestList = new ArrayList<>();
        back = findViewById(R.id.back_notifications);

        FirebaseFirestore db = MainActivity.getFirestoreInstance();

        back.setOnClickListener(view -> {
            Intent intent = new Intent(Notifications.this, MainActivity.class);
            intent.putExtra("SELECTED", R.id.friends);
            startActivity(intent);
        });

        db.collection("Users").document(username).collection("FriendRequest")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userRequest;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userRequest = (String) document.get("Request");
                            requestAdapter.add(userRequest);
                            requestAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("requestPage", "Error getting documents: ", task.getException());
                    }
                });
        requestAdapter = new CustomNotifications(this,friendRequestList);
        notificationList.setAdapter(requestAdapter);
    }
}
