package com.capstone2022.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone2022.APIActivity;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = new Intent(getContext(), APIActivity.class);
        startActivity(intent);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);

        return null;
    }
}
