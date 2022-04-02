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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    RequestManager manager;
    RandomMealAdapter adapter;
    RecyclerView recyclerView;
    List<String> tags = new ArrayList<>();
    Spinner spinner;
    SearchView searchView_home;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        //bottomNavigationView.setSelectedItemId(R.id.ic_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation);
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
                Toast.makeText(HomeActivity.this, "Will be added soon!", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                BookFragment bookFragment = new BookFragment();
                HomeFragment homeFragment = new HomeFragment();
                ListFragment listFragment = new ListFragment();
                ProfileFragment profileFragment = new ProfileFragment();
                SearchFragment searchFragment = new SearchFragment();
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, homeFragment).commit();
                        break;
                    case R.id.ic_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, listFragment).commit();
                        break;
                    case R.id.ic_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, searchFragment).commit();
                        break;
                    case R.id.ic_books:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, bookFragment).commit();
                        break;
                    case R.id.ic_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profileFragment).commit();
                        break;

                }
                return true;
            }
        });

    }

    private final RandomAPIResponseListener listener = new RandomAPIResponseListener() {
        @Override
        public void didFetch(List<RandomRecipe> responses, String message) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            adapter = new RandomMealAdapter(HomeActivity.this, responses, customOnClickListener);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        public void didError(String message) {
            recyclerView.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);
            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(HomeActivity.this, RecipeDetailActivity.class)
                    .putExtra("id", text));
        }
    };
    //BookFragment bookFragment = new BookFragment();
    //HomeFragment homeFragment = new HomeFragment();
    //ListFragment listFragment = new ListFragment();
    //ProfileFragment profileFragment = new ProfileFragment();
    //SearchFragment searchFragment = new SearchFragment();

    //@Override
  /*  public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.ic_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, bookFragment).commit();
                return true;

            case R.id.ic_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.ic_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
                return true;

            case R.id.ic_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;

            case R.id.ic_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                return true;
        }
        return false;
    }
*/

    //public void logout(View view)
    // {
    //     FirebaseAuth.getInstance().signOut();
    //     startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
    //     finish();
    // }
}