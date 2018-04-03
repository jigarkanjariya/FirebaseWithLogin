package com.example.devanshi.firebasewithlogin.model;

/**
 * Created by Devanshi on 30-03-2018.
 */

public class UserDetails
{
    String name,email,photo;

    public UserDetails(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDetails(String name, String email, String photo) {
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
