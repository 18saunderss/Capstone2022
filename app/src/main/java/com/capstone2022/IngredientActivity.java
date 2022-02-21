package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capstone2022.fragments.BookFragment;
import com.capstone2022.fragments.HomeFragment;
import com.capstone2022.fragments.ListFragment;
import com.capstone2022.fragments.ProfileFragment;
import com.capstone2022.fragments.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngredientActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Button buttonAddIngredient;
    private EditText editTextIngredient;
    DatabaseReference ingredientDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        buttonAddIngredient = findViewById(R.id.addIngredient);
        editTextIngredient = findViewById(R.id.ingredient);



        ingredientDbRef = FirebaseDatabase.getInstance().getReference().child("Ingredients");





        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = editTextIngredient.getText().toString().trim();
                ingredientDbRef.push().setValue(ingredient).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(IngredientActivity.this, "Ingredient added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
                        } else {
                            Toast.makeText(IngredientActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }

}