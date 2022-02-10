package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextUsername2;
    private Button verify;

    FirebaseAuth Fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextUsername2 = findViewById(R.id.username2);
        verify = (Button) findViewById(R.id.verify);

        String email = editTextUsername2.getText().toString().trim();
        Fauth = FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextUsername2.getText().toString().trim();
                Fauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Invalid email" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}

