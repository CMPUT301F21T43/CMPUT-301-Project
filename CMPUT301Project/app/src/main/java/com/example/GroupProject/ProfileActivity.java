package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etFirstName;
    EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etUsername = findViewById(R.id.editTextUsername);
        etFirstName = findViewById(R.id.editTextFirstName);
        etLastName = findViewById(R.id.editTextLastName);
    }

    public void confirm(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String username = etUsername.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();

        // Save to firestore here
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}