package com.capstone2022.Listeners;


import com.capstone2022.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> responses, String message);
    void didError(String message);
}
