package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    EditText usernameEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameEdit = findViewById(R.id.editTextUsernameSignIn);
        passwordEdit = findViewById(R.id.editTextPassword);

    }

    public void confirm(View view) {
        if (!TextUtils.isEmpty(usernameEdit.getText().toString())) {
            FirebaseFirestore db;
            db = getFirestoreInstance();

            String username = usernameEdit.getText().toString();

            ((GroupProject) this.getApplication()).setUsername(username);

            if (!TextUtils.isEmpty(passwordEdit.getText().toString())) {
                String password = passwordEdit.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("password", password);

                db.collection("Users").document(username)
                        .set(user, SetOptions.merge());
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }
}