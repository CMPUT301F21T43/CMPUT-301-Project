package com.example.GroupProject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.explore_user));

        String username = ((GroupProject) this.getApplication()).getUsername();

        follow = findViewById(R.id.follow_btn);
        userList = findViewById(R.id.user_list);
        userDataList = new ArrayList<>();


        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

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

        //get users no self or friends
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String user;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            user = (String) document.get("Username");
                            if ((!user.equals(username)) & (!userFriendList.contains(user))){
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
