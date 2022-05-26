package com.dbs_subsystem.database_structure;

public class Vehicle {

    private String model;
    private String license;
    private String category;
    private int numberOfWheels;
    private int price;

    public Vehicle(String model, String license, String category, int numberOfWheels, int price) {
        this.model = model;
        this.license = license;
        this.category = category;
        this.numberOfWheels = numberOfWheels;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getLicense() {
        return license;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public int getPrice() {
        return price;
    }
}
