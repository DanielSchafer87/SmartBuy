package com.zohar_daniel.smartbuy.Models;

public class ShoppingListItem {

    private  int Id;
    private String Name;
    private double Amount;
    private double Price;
    private long ListId;
    private String IsWeighted;
    private String TotalPrice;

    public ShoppingListItem(String name, int amount, double price, int listId, String isWeighted) {
        Name = name;
        Amount = amount;
        Price = price;
        ListId = listId;
        IsWeighted = isWeighted;
    }
    public ShoppingListItem(int id, String name, int amount, double price, long listId, String isWeighted) {
        Id = id;
        Name = name;
        Amount = amount;
        Price = price;
        ListId = listId;
        IsWeighted = isWeighted;
    }
    public ShoppingListItem()
    {

    }

    public void setIsWeighted(String isWeighted){ IsWeighted = isWeighted; }

    public String getIsWeighted(){ return IsWeighted; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
