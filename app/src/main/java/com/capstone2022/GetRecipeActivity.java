package com.capstone2022;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GetRecipeActivity extends AppCompatActivity {

    private static String recipeToSearch;
    private EditText editTextRecipe;
    private EditText recipeTitle;
    private EditText recipeIngredients;
    private EditText recipeInstructions;
    private EditText recipeDescription;



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
                    @RequiresApi(api = Build.VERSION_CODES.N)
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

                        //TODO: Insert query statement to get the element that matches the title of recipe
                        //https://firebase.google.com/docs/firestore/query-data/queries


                        //use RecipeTitleToSearch string and set method with
                        // query function to get the recipe from database
                        // that matches the title
                        Bundle extras = getIntent().getExtras();                        //Get the recipe title that was selected from the onclick method from MyAdapter
                        if (extras !=null)
                        {
                            recipeToSearch = extras.getString("title");
                            //Toast.makeText(GetRecipeActivity.this, recipeToSearch, Toast.LENGTH_SHORT).show();
                        }
                        //CollectionReference recipeRef = db.collection("RecipeApp").document("RecipeApp").collection("Users").document("UserData")
                          //      .collection("Recipes").document("RecipeData").collection("TestRecipeCollection");
                        //Query recipeQuery = recipeRef.whereEqualTo("Title", recipeToSearch);


                        Recipe test;
                        //new Recipe();

                        for (int k = 0; k < recipeArrayList.size(); k++)
                        {
                            test = (Recipe) recipeArrayList.get(k);
                            //Toast.makeText(GetRecipeActivity.this, test.getTitle(), Toast.LENGTH_SHORT).show();
                            if (test.getTitle().equals(recipeToSearch))
                            {
                                recipeTitle.setText(test.getTitle());
                                recipeIngredients.setText(test.getIngredient());
                                recipeInstructions.setText(test.getInstructions());
                                recipeDescription.setText(test.getDescription());
                                break;
                            }
                        }
                        //test = recipeArrayList.stream()
                          //      .filter(recipeArrayList -> recipeToSearch.equals(Recipe.getTitle()))
                            //    .findAny()
                              //  .orElse(null);
                        //test = (Recipe)recipeArrayList.get(2);



                    }
                });




    }
    public static void setRecipeTitle(String recipeTitleBroughtIn){
        recipeToSearch = recipeTitleBroughtIn;
    }

   // public static Recipe DisplayRecipeFromCardClick(String recipeName) {

    //}

}
