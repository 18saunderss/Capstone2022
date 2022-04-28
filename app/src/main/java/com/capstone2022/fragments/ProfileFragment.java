package com.capstone2022.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone2022.HomeActivity;
import com.capstone2022.IngredientActivity;
import com.capstone2022.LoginActivity;
import com.capstone2022.R;
import com.capstone2022.RecipeActivity;
import com.capstone2022.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private Button buttonSignOut;
    private Button buttonToggleDarkMode;
    TextView signOut;
    TextView toggleDarkMode;
    private FirebaseAuth fAuth;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView signOut = view.findViewById(R.id.bt_sign_out);
        TextView toggleDarkMode = view.findViewById(R.id.bt_toggle_dark_mode);
        //TextView createRecipe = view.findViewById(R.id.bt_createRecipe);


        buttonSignOut = (Button) signOut;
        buttonToggleDarkMode = (Button) toggleDarkMode;
        //buttonCreateRecipe = (Button) createRecipe;
        buttonSignOut.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v)
            {
                fAuth = FirebaseAuth.getInstance();
                fAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });



        //https://stackoverflow.com/questions/60138485/android-appcompatdelegate-setdefaultnightmode-not-recreating-parent-activity-in

        buttonToggleDarkMode.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v) {
                int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (nightModeFlag){
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case Configuration.UI_MODE_NIGHT_UNDEFINED:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }

            }
        });

        return view;
    }

}