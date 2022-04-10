package com.capstone2022.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.capstone2022.IngredientActivity;
import com.capstone2022.LoginActivity;
import com.capstone2022.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SearchFragment extends Fragment {


    private Button buttonGoToIngredients;
    //private FloatingActionButton fabIngredients;
    TextView goToIngredients;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        TextView buttonGoToIngredients = view.findViewById(R.id.bt_ingredients);
        //TextView fabIngredients = view.findViewById(R.id.fab_ingredients);
        buttonGoToIngredients = (Button) buttonGoToIngredients;
        //fabIngredients = view.findViewById(R.id.fab_ingredients);
        //fabIngredients = (FloatingActionButton) fabIngredients;

        buttonGoToIngredients.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), IngredientActivity.class));
            }
        });

        /*fabIngredients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), IngredientActivity.class));
            }
        });
*/
        return view;
    }


}