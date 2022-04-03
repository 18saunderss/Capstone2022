package com.capstone2022;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.capstone2022.Adapters.RandomMealAdapter;
import com.capstone2022.Listeners.CustomOnClickListener;
import com.capstone2022.Listeners.RandomAPIResponseListener;
import com.capstone2022.Models.RandomRecipe;
import com.capstone2022.fragments.BookFragment;
import com.capstone2022.fragments.HomeFragment;
import com.capstone2022.fragments.ListFragment;
import com.capstone2022.fragments.ProfileFragment;
import com.capstone2022.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//import com.github.ybq.android.spinkit.sprite.Sprite;
//import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class APIActivity extends AppCompatActivity {
    RequestManager manager;
    RandomMealAdapter adapter;
    //    ProgressDialog dialog;
    RecyclerView recyclerView;
    List<String> tags = new ArrayList<>();
    Spinner spinner;
    SearchView searchView_home;
   // ProgressBar progressBar;
   //menu
   BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        //api
        recyclerView = findViewById(R.id.recycler_random);
        spinner = findViewById(R.id.spinner_tags);
        searchView_home = findViewById(R.id.searchView_home);
        //progressBar = (ProgressBar)findViewById(R.id.loader);
        //Sprite doubleBounce = new Wave();
        //progressBar.setIndeterminateDrawable(doubleBounce);
        manager = new RequestManager(this);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(APIActivity.this, "Will be added soon!", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private final RandomAPIResponseListener listener = new RandomAPIResponseListener() {
        @Override
        public void didFetch(List<RandomRecipe> responses, String message) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            adapter = new RandomMealAdapter(APIActivity.this, responses, customOnClickListener);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
           // progressBar.setVisibility(View.GONE);
        }

        @Override
        public void didError(String message) {
            recyclerView.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);
            Toast.makeText(APIActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString().toLowerCase());
            manager.GetRandomRecipes(listener, tags);
            recyclerView.setVisibility(View.GONE);
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final CustomOnClickListener customOnClickListener = new CustomOnClickListener() {
        @Override
        public void onClick(String text) {
            startActivity(new Intent(APIActivity.this, RecipeDetailActivity.class)
                    .putExtra("id", text));
        }
    };


}