package com.capstone2022;

public class Recipe {

    String Title;
    String Description;
    String Ingredient;
    String Instructions;

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }


    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Recipe(){}

    public Recipe(String recipeName) {
        this.Instructions = Instructions;
        this.Ingredient = Ingredient;
        this.Description = Description;
        this.Title = Title;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String recipeName) {
        this.Title = Title;
    }
}
