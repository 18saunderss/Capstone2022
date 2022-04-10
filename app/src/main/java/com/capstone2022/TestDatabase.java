package com.capstone2022;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TestDatabase extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference recipes = db.document("/RecipeApp/RecipeApp/Users/UserData/Recipes/RecipeData/TestRecipeCollection");
    private Button create_test_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdatabase);
        create_test_recipe.findViewById(R.id.create_test_recipe);
        create_test_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> data1 = new HashMap<String, String>();
                data1.put("Description", "this is a test recipe");
                data1.put("Instructions", "put water in it, put it in the microwave");
                data1.put("Recipe", "Easy Mac");
                data1.put("RecipeDescription", "this is the highest quality macaroni and cheese");
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
