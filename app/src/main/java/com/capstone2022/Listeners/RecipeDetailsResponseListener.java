package com.capstone2022.Listeners;


import com.capstone2022.Models.RecipeDetailsResponse;

public interface RecipeDetailsResponseListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}