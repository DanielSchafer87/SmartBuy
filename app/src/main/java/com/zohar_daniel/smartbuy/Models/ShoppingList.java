package com.zohar_daniel.smartbuy.Models;

public class ShoppingList {

    private  int Id;
    private int StoreId;
    private String StoreName;
    private int ChainId;
    private String ChainName;
    private String CreatedOn;
    private String City;

    public ShoppingList(int id, int storeId, String storeName, int chainId, String chainName, String createdOn, String city) {
        Id = id;
        StoreId = storeId;
        StoreName = storeName;
        ChainId = chainId;
        ChainName = chainName;
        CreatedOn = createdOn;
        City = city;
    }

    public ShoppingList(int storeId, String storeName, int chainId, String chainName, String createdOn, String city) {
        StoreId = storeId;
        StoreName = storeName;
        ChainId = chainId;
        ChainName = chainName;
        CreatedOn = createdOn;
        City = city;
    }
    public ShoppingList()
    {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getChainId() {
        return ChainId;
    }

    public void setChainId(int chainId) {
        ChainId = chainId;
    }

    public String getChainName() {
        return ChainName;
    }

    public void setChainName(String chainName) {
        ChainName = chainName;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }













}
