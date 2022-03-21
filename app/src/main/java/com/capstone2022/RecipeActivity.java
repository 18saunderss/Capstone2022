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
    Dialog dialog;
    Dialog dialog2;
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
                        ingredientList.setText(ingredientList.getText()+adapter.getItem(position)+"\t\t");
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
    }
}
