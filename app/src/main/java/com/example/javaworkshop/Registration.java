package com.example.javaworkshop;

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

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    /*Declaring all the variables.*/

    EditText RegUserName, RegEmail, RegPassword, RegConfirmPass;
    Button RegBtn;
    TextView gotologin;
    /* We will be using the Firebase Authentication class for the app to authenticate the user*/
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide(); // used to hide the status bar.

        /*Initialising and Binding the above created variables to the layout */

        RegUserName = findViewById(R.id.editUserName);
        RegEmail = findViewById(R.id.editTextTextEmailAddress);
        RegPassword = findViewById(R.id.editTextTextPassword); // the password should be more than the length of 6 ( we are not putting this firebase ensures that all by itself )
        RegConfirmPass = findViewById(R.id.editTextConfirmPassword);
        RegBtn = findViewById(R.id.signupbutton);
        gotologin = findViewById(R.id.loginpagebtn);

        /* we will ge the instance of our Firebase Authentication class here*/
        firebaseAuth = FirebaseAuth.getInstance();

        /* taking user to login activity if he/she has already signed up*/
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* extracting the data from the specific fields*/

                String Username = RegUserName.getText().toString();
                String Email = RegEmail.getText().toString();
                String Password = RegPassword.getText().toString();
                String ConfirmPassword = RegConfirmPass.getText().toString();

                /*  now checking if the fields are empty or not */

                if(Username.isEmpty() ){
                    RegUserName.setError("Field is missing!");
                    return;
                }
                if( Email.isEmpty()){
                    RegEmail.setError("Field is missing!");
                    return;
                }
                if(Password.isEmpty()){
                    RegPassword.setError("Field is missing!");
                    return;
                }
                if(ConfirmPassword.isEmpty()){
                    RegConfirmPass.setError("Field is missing!");
                    return;
                }

                /* now checking if the field ConfirmPassword and Password are same or not  */

                if (!Password.equals(ConfirmPassword)){
                    RegConfirmPass.setError("Passwords do not match");
                    return;
                }

                /*data is validated*/


                /* now calling the firebaseauth instance here and attaching with the in-built methods of Firebase to register the user*/
                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Registration.this, "Data is validated and Registering you", Toast.LENGTH_LONG).show();
                        /* if the registration is successful then send him directly to our main activity of coffee counter*/
                        Intent intent = new Intent(Registration.this, MainActivity.class);
                        startActivity(intent);
                        finish(); //so that user doesn't comeback to the register activity and all the activities opened prior to this all gets closed

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*if the registration is unsuccessful then send an error message*/
                        Toast.makeText(Registration.this, "Error Message :"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}