package com.example.handyman_android_app.Model;

public class RequestsModel {
    private double requestID;
    private String category,description,location,title,customerName;

    public RequestsModel(double requestID, String category, String description, String location, String title, String customerName) {
        this.requestID = requestID;
        this.category = category;
        this.description = description;
        this.location = location;
        this.title = title;
        this.customerName = customerName;
    }

    public double getRequestID() {
        return requestID;
    }

    public void setRequestID(double requestID) {
        this.requestID = requestID;
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
