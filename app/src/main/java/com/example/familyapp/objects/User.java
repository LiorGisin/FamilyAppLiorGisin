package com.example.familyapp.objects;

import java.util.ArrayList;

public class User {

    private long userId;
    private String firstName;
    private String password;
    private long familyId;
    private String image;
    private String phoneNumber;



    public User(String firstName, String password, long familyId, String image, String phoneNumber) {
        this.firstName = firstName;
        this.password = password;
        this.familyId = familyId;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public long getFamilyId() {
        return familyId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }
}
