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
import java.util.Arrays;
import java.util.HashMap;

public class RecipeActivity extends AppCompatActivity {


    // Initialize variable
    TextView textview;
    TextView ingredientList;
    TextView addRecipes;
    Dialog dialog;
    Dialog dialog2;
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
        ingredientList=findViewById(R.id.textRecipeIngredients);
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

                        ingredientList.setText(ingredientList.getText()+adapter.getItem(position)+"\t\t");

                        //ingredientList.setText(ingredientList.getText()+adapter.getItem(position)+"\n");                      //I commented this out because it caused ingredients to show up twice when you entered the quantity

                        // Dismiss dialog
                        dialog.dismiss();

                        dialog2=new Dialog(RecipeActivity.this);

                        // set custom dialog
                        dialog2.setContentView(R.layout.dialog_searchable_spinner2);

                        // set custom height and width
                        dialog2.getWindow().setLayout(650,800);

                        // set transparent background
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // show dialog
                        dialog2.show();
                        EditText editText2=dialog2.findViewById(R.id.spinnerSearch2);
                        ListView measurementList=dialog2.findViewById(R.id.measurementList);

                        ArrayList arrayList2=new ArrayList<>();



                        arrayList2.add("1");
                        arrayList2.add("2");
                        arrayList2.add("3");
                        arrayList2.add("4");
                        arrayList2.add("5");
                        arrayList2.add("6");
                        arrayList2.add("7");
                        arrayList2.add("8");
                        arrayList2.add("9");
                        arrayList2.add("10");
                        arrayList2.add("1 Cup");
                        arrayList2.add("1/2 Cup");
                        arrayList2.add("1/3 Cup");
                        arrayList2.add("1/4 Cup");
                        arrayList2.add("Teaspoon");
                        arrayList2.add("TableSpoon");
                        arrayList2.add("1 Quart");
                        arrayList2.add("Pound");
                        arrayList2.add("Pint");


                        ArrayAdapter<String> adapter2=new ArrayAdapter<>(RecipeActivity.this, android.R.layout.simple_list_item_1,arrayList2);

                        measurementList.setAdapter(adapter2);
                        editText2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                adapter2.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        measurementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // when item selected from list
                                // set selected item on textView
                                //textview.setText(adapter.getItem(position));
                                ingredientList.setText(ingredientList.getText()+"-"+adapter2.getItem(position)+"\n");
                                // Dismiss dialog
                                dialog2.dismiss();


                            }
                        });
                    }
                });
            }
        });

        addRecipes.setOnClickListener(new View.OnClickListener() {                                  //METHOD: This method will take data from the EditText textboxes and push the recipe to the Firestore Database
            @Override
            public void onClick(View view) {
                recipeTitle=findViewById(R.id.textRecipeTitle);                                         //Find the view (the text boxes)
                recipeDescription=findViewById(R.id.recipeDescription);
                recipeIngredients=findViewById(R.id.textRecipeIngredients);
                recipeInstructions=findViewById(R.id.recipeInstruction);

                String title;                                                                       //Create strings to hold data from the text boxes
                String description;
                String ingredient;
                String instructions;


                title=recipeTitle.getText().toString();                                             //Assign data from the textboxes to the strings
                description=recipeDescription.getText().toString();
                ingredient=recipeIngredients.getText().toString();
                instructions=recipeInstructions.getText().toString();

                String[] ingredientsSplit = ingredient.split(System.lineSeparator());
                ArrayList<String> ingredientsList = new ArrayList<String>(Arrays.asList(ingredientsSplit));
                HashMap<String, String> data1 = new HashMap<String, String>();                                   //Put all of the strings into a hashmap, push the hashmap to the database's new collection at the path specified below
                data1.put("Title", title);                                                                  //This chunk of code can be used as a template to push data to the Firestore database
                data1.put("Description",description);
                int x = 0;                                                                          //Temporary solution to add multiple ingredients
                for (String s:ingredientsList) {
                    data1.put("Ingredient" + x, s);
                    x = (x + 1);
                }
                //data1.put("Ingredient",ingredient);

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
