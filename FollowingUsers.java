package com.example.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FollowingUsers extends AppCompatActivity {
    ListView userList;
    ArrayList<String> userDataList;
    ArrayAdapter<String> userAdapter;
    Button follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.followinguser));

        String username = ((GroupProject) this.getApplication()).getUsername();

        follow = findViewById(R.id.follow_btn);
        userList = findViewById(R.id.user_list);
        userDataList = new ArrayList<>();


        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();


        //Bundle getUser = getIntent().getExtras();
        //String userName = getUser.getString("User");

        //ArrayList<String> friendsList = new ArrayList<>();
        //if (userName != null) {
        //    friendsList.add(userName);
        //}


        CustomFollowList followList = new CustomFollowList(this,userDataList);
        followList.getUser();

        if (!followList.isEmpty()) {
            db.collection("Users").document(username).collection("Friends").document("friendList")
                    .set(followList);
        }

        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String user;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            user = (String) document.get("Username");
                            //boolean exists = friendsList.contains(user);
                            //if (!exists){
                                userAdapter.add(user);
                                userAdapter.notifyDataSetChanged();
                            }//}
                    } else {
                        Log.d("userPage", "Error getting documents: ", task.getException());
                    }
                });



        userAdapter = new CustomFollowList(this, userDataList);
        userList.setAdapter(userAdapter);
    };
    }



