package com.capstone2022;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeActivity extends AppCompatActivity {


    // Initialize variable
    TextView textview;
    TextView ingredientList;
    TextView addRecipes;
    ArrayList<String> arrayList;
    Dialog dialog;
    DatabaseReference getIngredientDbRef;                                                           //Realtime Database connection for ingredients only
    EditText recipeTitle;                                                                           //The following are for the EditText textboxes
    EditText recipeDescription;
    EditText recipeIngredients;
    EditText recipeInstructions;

    FirebaseFirestore db = FirebaseFirestore.getInstance();                                         //Firestore Database connection for the recipes
    DocumentReference recipes = db.document("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // assign variable
        addRecipes=findViewById(R.id.addRecipe);
        textview=findViewById(R.id.selectIngredient);
        ingredientList=findViewById(R.id.recipeIngredients);
        // initialize array list


        // set value in array list

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(RecipeActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.spinnerSearch);
                ListView listView=dialog.findViewById(R.id.spinnerList);

                // Initialize array adapter
                ArrayList<String> arrayList=new ArrayList<>();
                ArrayAdapter<String> adapter=new ArrayAdapter<>(RecipeActivity.this, android.R.layout.simple_list_item_1,arrayList);
                getIngredientDbRef = FirebaseDatabase.getInstance().getReference("Ingredients");
                getIngredientDbRef.addChildEventListener(new ChildEventListener() {


                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        arrayList.add(snapshot.getValue(String.class));

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        arrayList.remove(snapshot.getValue(String.class));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // set adapter
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
                        // when item selected from list
                        // set selected item on textView
                        textview.setText(adapter.getItem(position));
                        ingredientList.setText(ingredientList.getText()+adapter.getItem(position)+"\n");
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

        addRecipes.setOnClickListener(new View.OnClickListener() {                                  //METHOD: This method will take data from the EditText textboxes and push the recipe to the Firestore Database
            @Override
            public void onClick(View view) {
                recipeTitle=findViewById(R.id.recipeTitle);                                         //Find the view (the text boxes)
                recipeDescription=findViewById(R.id.recipeDescription);
                recipeIngredients=findViewById(R.id.recipeIngredients);
                recipeInstructions=findViewById(R.id.recipeInstruction);

                String title;                                                                       //Create strings to hold data from the text boxes
                String description;
                String ingredient;
                String instructions;

                title=recipeTitle.getText().toString();                                             //Assign data from the textboxes to the strings
                description=recipeDescription.getText().toString();
                ingredient=recipeIngredients.getText().toString();
                instructions=recipeInstructions.getText().toString();

                HashMap<String, String> data1 = new HashMap<String, String>();                                   //Put all of the strings into a hashmap, push the hashmap to the database's new collection at the path specified below
                data1.put("Title", title);                                                                  //This chunk of code can be used as a template to push data to the Firestore database
                data1.put("Description",description);
                data1.put("Ingredient",ingredient);
                data1.put("Instructions",instructions);
                db.collection("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData/TestRecipeCollection")
                        .add(data1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
        });
    }
}
