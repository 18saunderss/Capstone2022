package com.capstone2022.Listeners;

import com.capstone2022.Models.RandomRecipeResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeResponse response, String message);
    void didError(String message);
}
