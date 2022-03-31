package com.capstone2022;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
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
    private TextView recipeInstructions;
    private TextView recipeDescription;



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



                            //System.out.println(recipeArrayList);

                        }
                        //myAdapter.notifyDataSetChanged();
                        recipeTitle = findViewById(R.id.textRecipeTitle);
                        recipeIngredients = findViewById(R.id.textRecipeIngredients);
                        recipeInstructions = findViewById(R.id.textRecipeInstructions);
                        recipeDescription = findViewById(R.id.textRecipeDescription);

                        Recipe test = new Recipe();
                        test = (Recipe)recipeArrayList.get(1);

                        recipeTitle.setText(test.getTitle());
                        recipeIngredients.setText(test.getIngredient());
                        recipeInstructions.setText(test.getInstructions());
                        recipeDescription.setText(test.getDescription());

                    }
                });




    }

}
