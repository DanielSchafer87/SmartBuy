package com.zohar_daniel.smartbuy.Models;

public class Store {

    private  int Id;
    private String Name;
    private String City;

    public Store(int id, String name, String city) {
        Id = id;
        Name = name;
        City = city;
    }

    public Store()
    {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }


}
