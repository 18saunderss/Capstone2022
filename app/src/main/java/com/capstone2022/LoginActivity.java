package com.capstone2022;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;
    private Button buttonSignIn;
    private Button goToRecipe;
    private Button recipeList;
    private FirebaseAuth fAuth;

    //test database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference recipes = db.document("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData");
    //test database



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
        goToRecipe=findViewById(R.id.goToRecipe);
        recipeList=findViewById(R.id.recipeList);

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

        /*goToRecipe.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, TestDatabase.class));
            }
        });
*/
        goToRecipe.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
                                      {
                                          @Override
                                          public void onClick(View v) {
                                             /* HashMap<String, String> data1 = new HashMap<String, String>();
                                              data1.put("Description", "this is a test recipe");
                                              data1.put("Instructions", "put water in it, put it in the microwave");
                                              data1.put("Recipe", "Easy Mac");
                                              data1.put("RecipeDescription", "this is the highest quality macaroni and cheese");
                                              db.collection("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData/TestRecipeCollection")
                                                      .add(data1)
                                                      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                        }
                                                      })
                                                      .addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception e) {
                                                              Log.w(TAG, "Error adding document", e);
                                                          }
                                                      });
                                          }

                                              */
                                              startActivity(new Intent(LoginActivity.this, RecipeActivity.class));
                                       }
                                      });

        recipeList.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                //I CHANGED THE BUTTON HERE TO GO TO THE TEST GETRECIPEACTIVITY CLASS, IT SHOULD BE THE RECIPE LIST ACTIVITY
                startActivity(new Intent(LoginActivity.this, GetRecipeActivity.class));
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