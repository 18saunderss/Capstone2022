package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {



    ArrayList recipeArrayList = new ArrayList<Recipe>();
    MyAdapter myAdapter;
    RecyclerView recipeList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();                                         //Firestore Database connection for the recipes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeList = findViewById(R.id.recipeList);
        recipeList.setHasFixedSize(true);
        recipeList.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        recipeArrayList = new ArrayList<Recipe>();
        myAdapter = new MyAdapter(RecipeListActivity.this,recipeArrayList);
        recipeList = findViewById(R.id.recipeList);

        recipeList.setAdapter(myAdapter);

        EventChangeListener();
    }





    private void EventChangeListener() {

        db.collection("RecipeApp").document("RecipeApp").collection("Users").document("UserData")
                .collection("Recipes").document("RecipeData").collection("TestRecipeCollection").orderBy("Title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            if (error != null){

                Log.e("Firestore error", error.getMessage());
                return;

            }
            for (DocumentChange dc : value.getDocumentChanges()){

                recipeArrayList.add(dc.getDocument().toObject(Recipe.class));

                System.out.println(recipeArrayList);

            }
                myAdapter.notifyDataSetChanged();

            }
        });


    }

}