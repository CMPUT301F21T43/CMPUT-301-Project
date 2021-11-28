package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notifications extends AppCompatActivity {
    ListView notificationList;
    ArrayList<String> friendRequestList;
    ArrayAdapter<String> requestAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        String username = ((GroupProject) this.getApplication()).getUsername();

        notificationList = findViewById(R.id.notification_list);
        friendRequestList = new ArrayList<>();


        FirebaseFirestore db = MainActivity.getFirestoreInstance();

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
