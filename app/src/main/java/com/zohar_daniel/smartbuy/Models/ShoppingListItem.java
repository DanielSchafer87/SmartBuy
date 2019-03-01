package com.zohar_daniel.smartbuy.Models;

public class ShoppingListItem {

    private  int Id;
    private String Name;
    private int Amount;
    private double Price;
    private long ListId;

    public ShoppingListItem(String name, int amount, double price, int listId) {
        Name = name;
        Amount = amount;
        Price = price;
        ListId = listId;
    }
    public ShoppingListItem(int id, String name, int amount, double price, long listId) {

        Id = id;
        Name = name;
        Amount = amount;
        Price = price;
        ListId = listId;
    }
    public ShoppingListItem()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public long getListId() {
        return ListId;
    }

    public void setListId(long listId) {
        ListId = listId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
