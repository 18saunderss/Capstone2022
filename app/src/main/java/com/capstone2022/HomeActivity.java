package com.capstone2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.capstone2022.fragments.BookFragment;
import com.capstone2022.fragments.HomeFragment;
import com.capstone2022.fragments.ListFragment;
import com.capstone2022.fragments.ProfileFragment;
import com.capstone2022.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    int NightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    //This chunk of code has to do with saving/pulling the state of "dark mode."
    //Ideally, it would save the user's last setting and set it to whichever mode
    //  the user used last. This is not working correctly. It is tied into the
    //  "The following is used to store/pull info" on line 70ish
   /* @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        NightMode = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt("NightMode", NightMode);
        editor.apply();

    }
*/
    @Override
    public void onBackPressed(){
        //for now, this is empty. So when the back button is pressed, nothing happens.
        //Should code "double tap back to log out" with toast notification
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        //bottomNavigationView.setSelectedItemId(R.id.ic_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        //The following is used to store/pull info on the current state of Dark Mode
         //sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
         //NightMode = sharedPreferences.getInt("NightModeInt", 1);
         //AppCompatDelegate.setDefaultNightMode(NightMode);
        //end shared preferences logic

        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
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

}