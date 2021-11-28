package com.example.GroupProject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.GroupProject.HabitEvent;
import com.example.GroupProject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomList class for Habitevent main page
 * @Author Kourosh Kehtari
 */

public class CustomFollowList extends ArrayAdapter<String> {
    private final ArrayList<String> users;
    private final Context context;
    private ArrayList<String> followedList;


    public CustomFollowList(Context context, ArrayList<String> users) {
        super(context,0,users);
        this.context = context;
        this.users = users;
        //FirebaseFirestore db;
        //db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        FirebaseFirestore db;
        String username = ((GroupProject) context.getApplicationContext()).getUsername();
        db = FirebaseFirestore.getInstance();

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.follow_custom, parent,false);
        }

        String user = users.get(position);
        Button follow = view.findViewById(R.id.follow_btn);
        TextView userName = view.findViewById(R.id.follow_username);
        userName.setText(user);
        ArrayList<String> followedList = new ArrayList<>();

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow.setText("Followed");
                Log.d("OnClick", username);
                Map<String, Object> followers = new HashMap<>();
                followers.put("Request",username);
                db.collection("Users").document(user).collection("FriendRequest").document(username)
                        .set(followers, SetOptions.merge());
                //followedList.add(user);

                //Intent intentUser = new Intent(context,FollowingUsers.class);
                //intentUser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
                //intentUser.putExtra("User",user);
                //context.startActivity(intentUser);
            }
        });

        return view;
    }

    public ArrayList getUser() {
        return followedList;
    }
}
