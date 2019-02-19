package com.zohar_daniel.smartbuy;

public class InvoiceData_ShoppingList {

    private String itemName;
    private double itemPrice;
    private double itemAmount;

    public InvoiceData_ShoppingList(String itemName, double itemPrice, double itemAmount) {
        this.itemName=itemName;
        this.itemAmount=itemAmount;
        this.itemPrice=itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public double getItemPrice() { return itemPrice; }
}
