package com.capstone2022.Models;

public class Equipment {
    public int id;
    public String name;
    public String localizedName;
    public String image;
    public com.capstone2022.Models.Temperature temperature;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getImage() {
        return image;
    }

    public com.capstone2022.Models.Temperature getTemperature() {
        return temperature;
    }
}
