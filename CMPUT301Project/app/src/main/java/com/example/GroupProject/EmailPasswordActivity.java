/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.Map;

public class EmailPasswordActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;

    private String email;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnSignIn.setOnClickListener(view -> {
            setEmail();
            setPassword();
            signIn(email, password);
        });

        btnCreateAccount.setOnClickListener(view -> {
            setEmail();
            setPassword();
            createAccount(email, password);
        });

        // [START auth_state_listener] ,this method execute as soon as there is a change in Auth status , such as user sign in or sign out.
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // User is signed in
                //redirect
                ((GroupProject) this.getApplication()).setFirebaseUser(user);
                // Reload UI for this user.
                //startMainActivity(false);
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }

        };
        // [END auth_state_listener]
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            ((GroupProject) this.getApplication()).setFirebaseUser(currentUser);
//            // Reload UI for this user.
//            startMainActivity();
//        }
    }

    public void setEmail() {
        email = etEmail.getText().toString();
    }

    public void setPassword() {
        password = etPassword.getText().toString();
    }


    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Create account success.
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(EmailPasswordActivity.this, "Account created.",
                                Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        ((GroupProject) this.getApplication()).setFirebaseUser(user);
                        ((GroupProject) this.getApplication()).setEmail(email);
                        promptUsername();
                        // Go to add username. There, set username. Then bring back here.
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Log.d(TAG, "FirebaseAuthInvalidUserException");
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Log.d(TAG, "FirebaseAuthInvalidCredentialsException");
                        } catch (FirebaseNetworkException e) {
                            Log.d(TAG, "FirebaseNetworkException");
                        } catch (FirebaseAuthUserCollisionException e) {
                            Log.d(TAG, "FirebaseAuthUserCollisionException");
                        } catch (Exception e) {
                            Log.d(TAG, "Exception");
                        }
                        // If create account fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(EmailPasswordActivity.this, "Account creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        ((GroupProject) this.getApplication()).setFirebaseUser(user);
                        ((GroupProject) this.getApplication()).setEmail(email);
                        getUsername();
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Log.d(TAG, "FirebaseAuthInvalidUserException");
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Log.d(TAG, "FirebaseAuthInvalidCredentialsException");
                        } catch (FirebaseNetworkException e) {
                            Log.d(TAG, "FirebaseNetworkException");
                        } catch (Exception e) {
                            Log.d(TAG, "Exception");
                        }
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(EmailPasswordActivity.this,
                                "Authentication failed. Create an account if you don't have one.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END sign_in_with_email]
    }

    private void getUsername() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userEmail;
                        String email = ((GroupProject) this.getApplication()).getEmail();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userEmail = (String) document.get("Email");
                            if (userEmail.equals(email)) {
                                username = (String) document.get("Username");
                                ((GroupProject) this.getApplication()).setUsername(username);
                                startMainActivity(false);
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    private void promptUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Username Prompt");

        final EditText input = new EditText(this);
        builder.setView(input);
        GroupProject thisGP = (GroupProject) this.getApplicationContext();

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> docData = new HashMap<>();
                docData.put("Joined", Timestamp.now());
                docData.put("Email", thisGP.getEmail());
                username = input.getText().toString();
                docData.put("Username", username);
                thisGP.setUsername(username);
                db.collection("Users").document(username).set(docData, SetOptions.merge());
                startMainActivity(true);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                promptUsername();
                Toast.makeText(EmailPasswordActivity.this, "Username needs to be entered for account.",
                        Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private void startMainActivity(Boolean accountJustCreated) {
        ((GroupProject) this.getApplication()).setSignedIn(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CREATED", accountJustCreated);
        intent.putExtra("SELECTED", R.id.habit);
        startActivity(intent);
    }

    private void startProfileFragment() {
        Fragment newFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, newFragment).commit();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}