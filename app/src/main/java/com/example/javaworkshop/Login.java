package com.example.javaworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText LogUserName, LogEmail, LogPassword;
    Button LogBtn;
    TextView gotoreg;
    FirebaseAuth firebaseAuth; //again using the firebase authentication class to login the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // used to hide the status bar.

        /*Initialising and Binding the above created variables to the layout */

        LogUserName = findViewById(R.id.editUserName);
        LogEmail = findViewById(R.id.editTextTextEmailAddress);
        LogPassword = findViewById(R.id.editTextTextPassword);
        LogBtn = findViewById(R.id.Loginbutton);
        gotoreg = findViewById(R.id.textView);

        //initialising the firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        /* taking user to register activity if he/she has not already signed up*/
        gotoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  now checking if the fields are empty or not */

                if(LogUserName.getText().toString().isEmpty() ){
                    LogUserName.setError("Field is missing!");
                    return;
                }
                if(LogEmail.getText().toString().isEmpty() ){
                    LogEmail.setError("Field is missing!");
                    return;
                }
                if(LogPassword.getText().toString().isEmpty() ){
                    LogPassword.setError("Field is missing!");
                    return;
                }

                //using the built-in method of Firebase to sign in the user
                firebaseAuth.signInWithEmailAndPassword(LogEmail.getText().toString(),LogPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //when login is successful
                        Toast.makeText(Login.this, "Logging you in ",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*if the login is unsuccessful then send an error message*/
                        Toast.makeText(Login.this, "Error Message:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // now firstly we need to check if the user has already logged into our app before or not
    // if the user has then we need to send him directly to our main activity of coffee counter
    // we will check that in the onStart() method of the activity

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            //if this condition is true then we will send them to the main activity of coffee counter
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("logusername",LogUserName.getText().toString());
            startActivity(intent);
            finish();
        }    }
}