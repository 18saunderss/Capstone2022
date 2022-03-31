package com.capstone2022;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GetRecipeActivity extends AppCompatActivity {

    private EditText editTextRecipe;
    private TextView recipeTitle;
    private TextView recipeIngredients;



    ArrayList recipeArrayList = new ArrayList<Recipe>();
    //MyAdapter myAdapter;
    //RecyclerView recipeList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();                                         //Firestore Database connection for the recipes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recipe);

        //recipeList = findViewById(R.id.recipeList);
        //recipeList.setHasFixedSize(true);
        //recipeList.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        recipeArrayList = new ArrayList<Recipe>();
        //myAdapter = new MyAdapter(GetRecipeActivity.this,recipeArrayList);
        //recipeList = findViewById(R.id.recipeList);

        //recipeList.setAdapter(myAdapter);

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
                            recipeTitle = findViewById(R.id.recipeTitle);
                            recipeIngredients = findViewById(R.id.recipeIngredients);
                            Recipe test = new Recipe();
                            test = (Recipe)recipeArrayList.get(1);

                            recipeTitle.setText(test.getIngredient());


                            //System.out.println(recipeArrayList);

                        }
                        //myAdapter.notifyDataSetChanged();

                    }
                });




    }

}

/*
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference recipes = db.collection("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData/TestRecipeCollection");



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recipe);
        ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
        //Let's try creating an arraylist that stores the recipes
        //I dunno what this stuff is
        /*ArrayList<String> recipesArray=new ArrayList<>();
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

         */
        //Query query = recipes.whereEqualTo("Title", true);

/*
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
*/