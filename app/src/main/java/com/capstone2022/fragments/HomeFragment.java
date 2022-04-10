package com.capstone2022.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capstone2022.APIActivity;
import com.capstone2022.HomeActivity;
import com.capstone2022.LoginActivity;
import com.capstone2022.R;
import com.capstone2022.RecipeListActivity;


public class HomeFragment extends Fragment {
    private Button buttonStartAPI;
    private Button buttonSearchDBrecipes;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView searchAPIrandom = view.findViewById(R.id.bt_searchAPIrandom);
        TextView searchDBrecipes = view.findViewById(R.id.bt_searchDBrecipes);


        buttonStartAPI = (Button) searchAPIrandom;
        buttonSearchDBrecipes = (Button) searchDBrecipes;
        buttonStartAPI.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), APIActivity.class));
            }
        });
        buttonSearchDBrecipes.setOnClickListener(new View.OnClickListener()                                             //Intent to open RegisterActivity when "Register" button is pressed
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RecipeListActivity.class));

            }
        });
        return view;
    }
}
