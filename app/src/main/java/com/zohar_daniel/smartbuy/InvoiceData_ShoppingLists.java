package com.zohar_daniel.smartbuy;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceData_ShoppingLists {

    private String chainName;
    private String chainID;
    private String storeID;
    private String storeName;
    private String city;
    private String date;
    private double invoiceAmount;

    public InvoiceData_ShoppingLists(String chainName, String storeName, String city, String date, double invoiceAmount, String chainID, String storeID) {
        this.storeID = storeID;
        this.storeName=storeName;
        this.chainID = chainID;
        this.chainName = chainName;
        this.city = city;
        this.date = date;
        this.invoiceAmount = invoiceAmount;
    }

    public String getStoreID() {
        return storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getChainID() {
        return storeID;
    }

    public String getChainName() {
        return chainName;
    }

    public String getCity() { return  city; }

    public String getDate() {
        return date;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

}
