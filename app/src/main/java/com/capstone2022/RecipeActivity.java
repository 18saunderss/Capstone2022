package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    private Button buttonAddRecipe;
    private EditText editTextRecipe;
    DatabaseReference addRecipeDbRef;
    DatabaseReference getRecipeDbRef;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        buttonAddRecipe = findViewById(R.id.addRecipe);
        editTextRecipe = findViewById(R.id.recipe);
        addRecipeDbRef = FirebaseDatabase.getInstance().getReference().child("Recipes");


        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipe = editTextRecipe.getText().toString().trim();
                addRecipeDbRef.push().setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecipeActivity.this, "Recipe added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                        } else {
                            Toast.makeText(RecipeActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        initializeListView();
    }

    private void initializeListView() {
        listView = findViewById(R.id.recipeList);

        getRecipeDbRef = FirebaseDatabase.getInstance().getReference("Recipes");

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_recipe_textview, R.id.recipeTextView, list);

        getRecipeDbRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                list.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                list.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setAdapter(adapter);
    }
}
