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

public class ExploreUsers extends AppCompatActivity {
    ListView userList;
    ArrayList<String> userDataList;
    ArrayAdapter<String> userAdapter;
    Button follow;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.explore_user));

        String username = ((GroupProject) this.getApplication()).getUsername();

        follow = findViewById(R.id.follow_btn);
        userList = findViewById(R.id.user_list);
        userDataList = new ArrayList<>();
        back = findViewById(R.id.back_explore);

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        back.setOnClickListener(view -> {
            Intent intent = new Intent(ExploreUsers.this, MainActivity.class);
            startActivity(intent);
        });

        //get friends
        ArrayList<String> userFriendList;
        userFriendList = new ArrayList<>();
        db.collection("Users").document(username).collection("Friends")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String friendUsername;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            friendUsername = (String) document.get("FriendUsername");
                            userFriendList.add(friendUsername);
                        }
                    }
                });

        ArrayList<String> pendingRequest;
        pendingRequest = new ArrayList<>();
        db.collection("Users").document(username).collection("PendingRequest")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String pendingUser;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            pendingUser = (String) document.get("Pending");
                            Log.d("Pending", "hello" + pendingUser);
                            pendingRequest.add(pendingUser);
                        }
                    }
                });

        //get users no self or friends
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String user;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            user = (String) document.get("Username");
                            if ((!user.equals(username)) & (!userFriendList.contains(user)) & (!pendingRequest.contains(user))){
                                Log.d("userPage", "hello"+user);
                                userAdapter.add(user);
                                userAdapter.notifyDataSetChanged();
                            }}
                    } else {
                        Log.d("userPage", "Error getting documents: ", task.getException());
                    }
                });



        userAdapter = new CustomExploreList(this, userDataList);
        userList.setAdapter(userAdapter);
    };
}
