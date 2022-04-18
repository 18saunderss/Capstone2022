package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {


    ArrayList searchRecipeArrayList;
    ArrayList recipeArrayList;
    MyAdapter myAdapter;
    RecyclerView recipeList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView searchRecipe;//Firestore Database connection for the recipes
    CollectionReference getRecipeDBref =  db.collection("RecipeApp").document("RecipeApp").collection("Users").document("UserData")
            .collection("Recipes").document("RecipeData").collection("TestRecipeCollection");
    Dialog dialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        searchRecipe = findViewById(R.id.searchRecipe);
        recipeList = findViewById(R.id.recipeList);
        recipeList.setHasFixedSize(true);
        recipeList.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        recipeArrayList = new ArrayList<Recipe>();
        searchRecipeArrayList = new ArrayList<Recipe>();
        myAdapter = new MyAdapter(RecipeListActivity.this,recipeArrayList);
        recipeList = findViewById(R.id.recipeList);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(RecipeListActivity.this, android.R.layout.simple_list_item_1,searchRecipeArrayList);
        recipeList.setAdapter(myAdapter);

        EventChangeListener();

        searchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize dialog
                dialog3 = new Dialog(RecipeListActivity.this);

                // set custom dialog
                dialog3.setContentView(R.layout.dialog_searchable_spinner3);

                // set custom height and width
                dialog3.getWindow().setLayout(650, 800);

                // set transparent background
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog3.show();

                // Initialize and assign variable
                EditText editText = dialog3.findViewById(R.id.spinnerSearch3);
                ListView listView = dialog3.findViewById(R.id.spinnerRecipeList);

                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        searchRecipe.setText(adapter.getItem(position));
                        String returnRecipeTitle = searchRecipe.getText().toString();
                        Intent myIntent = new Intent(getApplicationContext(), GetRecipeActivity.class);
                        myIntent.putExtra("title",returnRecipeTitle);
                        GetRecipeActivity.setRecipeTitle(returnRecipeTitle);
                        v.getContext().startActivity(myIntent);
                        dialog3.dismiss();
                    }
                });
            }
        });
    }

    private void EventChangeListener() {

        getRecipeDBref.orderBy("Title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            Recipe recipe = dc.getDocument().toObject(Recipe.class);
                            recipeArrayList.add(recipe);
                            searchRecipeArrayList.add(recipe.getTitle());
                            System.out.println(recipeArrayList);
                        }
                        myAdapter.notifyDataSetChanged();

                    }
                });
            }
        }