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

public class CustomExploreList extends ArrayAdapter<String> {
    private final ArrayList<String> users;
    private final Context context;


    public CustomExploreList(Context context, ArrayList<String> users) {
        super(context,0,users);
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        FirebaseFirestore db;
        String username = ((GroupProject) context.getApplicationContext()).getUsername();
        db = FirebaseFirestore.getInstance();

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.explore_custom, parent,false);
        }

        String user = users.get(position);
        Button follow = view.findViewById(R.id.follow_btn);
        TextView userName = view.findViewById(R.id.follow_username);
        userName.setText(user);
        ArrayList<String> followedList = new ArrayList<>();

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //follow.setText("Followed");
                //follow.setEnabled(false);
                Log.d("OnClick", username);
                Map<String, Object> followers = new HashMap<>();
                followers.put("Request",username);
                db.collection("Users").document(user).collection("FriendRequest").document(username)
                        .set(followers, SetOptions.merge());
                Map<String, Object> pending = new HashMap<>();
                pending.put("Pending",user);
                db.collection("Users").document(username).collection("PendingRequest").document(user)
                        .set(pending, SetOptions.merge());
            }
        });

        return view;
    }

}

