package com.capstone2022;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;
    FirebaseAuth fAuth;
    //private FirebaseAuth mAuth;

    TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        fAuth=FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.login);

        register = findViewById(R.id.login);


       // if(fAuth.getCurrentUser()!= null)
       // {
       //     startActivity(new Intent(getApplicationContext(),LoginActivity.class));
       //     finish();
        //}
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        /*TextWatcher registerTextWatcher = new TextWatcher()                                         //This method "watches" for changes in the username and password fields.
        {                                                                                                   //If user inputs text into username and password fields, it enables the Register button
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameInput = editTextUsername.getText().toString().trim();
                String passwordInput = editTextPassword.getText().toString().trim();
                buttonRegister.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        */



    /*public void registerActivity(){

        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextUsername.setError("Please provide valid email");
            editTextUsername.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   User user = new User(email,password);

                     FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                     Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                     progressBar.setVisibility(View.VISIBLE);
                                       System.out.println("worked");

                                   }
                                   else
                                   {
                                     Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_LONG).show();
                                     progressBar.setVisibility(View.VISIBLE);

                                   }

                                }
                    });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });

    */

    }
}