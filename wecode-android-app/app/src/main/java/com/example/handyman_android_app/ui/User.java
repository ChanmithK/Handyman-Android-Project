package com.example.handyman_android_app.ui;

public class User {

    public String FirstName, LastName, Email, Phone, Location, Role;

    public User(String firstName, String lastName, String email, String phone, String location, String role) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Phone = phone;
        Location = location;
        Role = role;
    }

    public User(){
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
