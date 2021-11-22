package com.example.GroupProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                updateUI(user);
                ((GroupProject) this.getApplication()).setFirebaseUser(user);
                // Reload UI for this user.
                startMainActivity(false);
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
                updateUI(null);
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
                        startMainActivity(true);
                        // Go to add username. There, set username. Then bring back here.
                        updateUI(user);
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
                        updateUI(null);
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
                        startMainActivity(false);
                        updateUI(user);
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
                        updateUI(null);
                    }
                });
        // [END sign_in_with_email]
    }


    private void startMainActivity(Boolean accountJustCreated) {
        ((GroupProject) this.getApplication()).setSignedIn(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CREATED", accountJustCreated);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}