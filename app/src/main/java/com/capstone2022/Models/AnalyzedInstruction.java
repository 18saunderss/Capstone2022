package com.capstone2022.Models;

import java.util.List;

public class AnalyzedInstruction {
    public String name;
    public List<com.capstone2022.Models.Step> steps;

    public String getName() {
        return name;
    }

    public List<com.capstone2022.Models.Step> getSteps() {
        return steps;
    }
}
