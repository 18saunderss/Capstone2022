package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {


    // create instance of Firestore database


    // Initialize variable
    TextView textview;
    TextView ingredientList;
    ArrayList<String> arrayList;
    Dialog dialog;
    DatabaseReference getIngredientDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // assign variable
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
    }
}
