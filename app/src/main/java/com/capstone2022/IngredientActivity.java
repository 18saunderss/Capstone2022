package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
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

public class IngredientActivity extends AppCompatActivity {

    private Button buttonAddIngredient;
    private EditText editTextIngredient;
    DatabaseReference addIngredientDbRef;
    DatabaseReference getIngredientDbRef;
    private ListView listView;
    private ListView shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        buttonAddIngredient = findViewById(R.id.addIngredient);
        editTextIngredient = findViewById(R.id.ingredient);
        addIngredientDbRef = FirebaseDatabase.getInstance().getReference().child("Ingredients");

       /* buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = editTextIngredient.getText().toString().trim();
                shoppingList.add(ingredient);
                /*String ingredient = editTextIngredient.getText().toString().trim();
                addIngredientDbRef.push().setValue(ingredient).addOnCompleteListener(new OnCompleteListener<Void>() {
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
*/
        initializeListView();
    }

    private void initializeListView() {
        listView = findViewById(R.id.ingredientList);
        shoppingCart = findViewById(R.id.shoppingCart);

        getIngredientDbRef = FirebaseDatabase.getInstance().getReference("Ingredients");



        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_ingredient_textview, R.id.ingredientTextView, list);

        ArrayList<String> shoppingList = new ArrayList<>();
        ArrayAdapter shoppingAdapter = new ArrayAdapter<String>(this, R.layout.activity_ingredient_textview, R.id.ingredientTextView, shoppingList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                shoppingList.add((String)listItem);
                shoppingAdapter.notifyDataSetChanged();
            }
        });

        shoppingCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = shoppingCart.getItemAtPosition(position);
                shoppingList.remove((String)listItem);
                shoppingAdapter.notifyDataSetChanged();
            }
        });
        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = editTextIngredient.getText().toString().trim();
                shoppingList.add(ingredient);
                shoppingAdapter.notifyDataSetChanged();
            }
        });
        getIngredientDbRef.addChildEventListener(new ChildEventListener() {


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
                shoppingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setAdapter(adapter);
        shoppingCart.setAdapter(shoppingAdapter);

    }

}