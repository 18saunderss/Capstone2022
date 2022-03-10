package com.capstone2022.Listeners;


import com.capstone2022.Models.RandomRecipe;

import java.util.List;

public interface RandomAPIResponseListener {
    void didFetch(List<RandomRecipe> responses, String message);
    void didError(String message);
}