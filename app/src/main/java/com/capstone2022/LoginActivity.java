package com.capstone2022;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;
    private Button buttonSignIn;
    private Button goToIngredients;
    private FirebaseAuth fAuth;

    TextView register;
    TextView resetPassword;
    TextView loginActivityChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        register=findViewById(R.id.register);
        resetPassword=findViewById(R.id.reset);
        loginActivityChange = findViewById(R.id.login);
        editTextLoginEmail = findViewById(R.id.username);
        editTextLoginPassword = findViewById(R.id.password);
        buttonSignIn = findViewById(R.id.login);
        goToIngredients = findViewById(R.id.goToIngredients);

        buttonSignIn.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                String email = editTextLoginEmail.getText().toString().trim();
                String password = editTextLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    editTextLoginEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    editTextLoginPassword.setError("Password is required");
                    return;
                }

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
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
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

        goToIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, IngredientActivity.class));
            }
        });
    }



    public void loginUser() {                                                                               //I don't know if this method actually does anything
        String email = editTextLoginEmail.getText().toString().trim();
        String password = editTextLoginPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextLoginEmail.setError("Please provide valid email");
            editTextLoginPassword.requestFocus();
            return;
        }

    }

}