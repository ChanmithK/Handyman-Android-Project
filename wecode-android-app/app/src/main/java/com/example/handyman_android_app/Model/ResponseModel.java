package com.example.handyman_android_app.Model;

public class ResponseModel {
    private double responseID;
    private String category,description,location,title,customerName;

    public ResponseModel(double responseID, String category, String description, String location, String title, String customerName) {
        this.responseID = responseID;
        this.category = category;
        this.description = description;
        this.location = location;
        this.title = title;
        this.customerName = customerName;

    }

    public double getResponseID() {
        return responseID;
    }

    public void setResponseID(double responseID) {
        this.responseID = responseID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
