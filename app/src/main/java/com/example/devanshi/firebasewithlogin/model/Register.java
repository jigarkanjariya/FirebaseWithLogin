package com.example.devanshi.firebasewithlogin.model;

/**
 * Created by Devanshi on 28-03-2018.
 */

public class Register
{
    String id;
    String name;
    String address;
    String phone;
    String website;
    String description;
    String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Register()
    {
    }

    public Register(String id, String name, String address, String phone, String website, String description, String photo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.description = description;
        this.photo=photo;
    }


}
