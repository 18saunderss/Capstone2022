package com.capstone2022;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;

    private FirebaseAuth fAuth;

    TextView resetPassword;
    TextView register;
    TextView loginActivityChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        register=findViewById(R.id.register);
        resetPassword=findViewById(R.id.resetPassword);
        loginActivityChange = findViewById(R.id.login);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonSignIn = findViewById(R.id.login);


        //editTextUsername.addTextChangedListener(loginTextWatcher);
        //editTextPassword.addTextChangedListener(loginTextWatcher);

        buttonSignIn.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                fAuth = FirebaseAuth.getInstance();
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //loginUser();
                //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


    }
    /*private TextWatcher loginTextWatcher = new TextWatcher()                                              //This method "watches" for changes in the username and password fields.
    {                                                                                                     //If user inputs text into username and password fields, it enables the Register button
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editTextUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();
            buttonSignIn.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
*/
    public void loginUser() {
        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsername.setError("Please provide valid email");
            editTextUsername.requestFocus();
            return;
        }

    }

}