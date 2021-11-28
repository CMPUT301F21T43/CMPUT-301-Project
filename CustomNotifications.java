package com.example.GroupProject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomNotifications extends ArrayAdapter<String> {
    private final ArrayList<String> usersRequests;
    private final Context context;

    public CustomNotifications(Context context, ArrayList<String> userRequests) {
        super(context,0,userRequests);
        this.context = context;
        this.usersRequests = userRequests;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        FirebaseFirestore db;
        String username = ((GroupProject) context.getApplicationContext()).getUsername();
        db = FirebaseFirestore.getInstance();

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.custom_notification, parent,false);
        }

        String userRequest = usersRequests.get(position);
        Button accept = view.findViewById(R.id.accept_req);
        Button decline = view.findViewById(R.id.decline_req);
        TextView userReq = view.findViewById(R.id.text_req);
        userReq.setText(userRequest + "has requested to follow you");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setText("accepted");
                Log.d("OnClick", username);
                Map<String, Object> followerReq = new HashMap<>();
                followerReq.put("FriendUsername",username);
                Map<String, Object> followerReq2 = new HashMap<>();
                followerReq.put("FriendUsername",userRequest);
                db.collection("Users").document(userRequest).collection("Friends")
                        .document(username)
                        .set(followerReq, SetOptions.merge());
                db.collection("Users").document(username).collection("Friends")
                        .document(userRequest)
                        .set(followerReq2, SetOptions.merge());
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline.setText("declined");
                db.collection("Users").document(username)
                        .collection("FriendRequest")
                        .document(userRequest)
                        .delete();
            }
        });

        return view;
    }

}
