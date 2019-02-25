package com.zohar_daniel.smartbuy;

public  class ShoppingListsSchema {

    protected final static String databaseName = "ShppingLists.db";


    //Shopping lists
    public final static String LISTS_TABLE = "ShoppingLists";
    public final static String COLUMN_LISTS_LIST_ID = "Id";
    public final static String COLUMN_LISTS_STORE_ID = "StoreId";
    public final static String COLUMN_LISTS_STORE_NAME = "StoreName";
    public final static String COLUMN_LISTS_CHAIN_ID = "ChainId";
    public final static String COLUMN_LISTS_CHAIN_NAME = "ChainName";
    public final static String COLUMN_LISTS_DATE = "Date";
    public final static String COLUMN_LISTS_CITY = "City";

    protected final static String CREATE_TABLE_LISTS = "CREATE TABLE " + LISTS_TABLE + "( "
            + COLUMN_LISTS_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LISTS_STORE_ID + " INTEGER, "
            + COLUMN_LISTS_STORE_NAME + " TEXT NOT NULL, "
            + COLUMN_LISTS_CHAIN_ID + " INTEGER  NOT NULL,"
            + COLUMN_LISTS_CHAIN_NAME + " TEXT NOT NULL, "
            + COLUMN_LISTS_DATE + " DATE,"
            + COLUMN_LISTS_CITY + " TEXT)";


    //ShoppingList items
    public final static String ITEMS_TABLE = "ListItems";
    public final static String COLUMN_ITEMS_ID = "Id";
    public final static String COLUMN_ITEMS_NAME = "Name";
    public final static String COLUMN_ITEMS_PRICE = "Price";
    public final static String COLUMN_ITEMS_AMOUNT = "Amount";
    public final static String COLUMN_ITEMS_TOTAL_PRICE = "TotalPrice";
    public final static String COLUMN_ITEMS_LISTID = "ListId";



    protected final static String CREATE_TABLE_ITEMS = "CREATE TABLE " + ITEMS_TABLE + "( "
            + COLUMN_ITEMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEMS_NAME + " TEXT, "
            + COLUMN_ITEMS_AMOUNT + " INTEGER ,"
            + COLUMN_ITEMS_PRICE + " DOUBLE NOT NULL, "
            + COLUMN_ITEMS_LISTID + " INTEGER NOT NULL,"
            + " FOREIGN KEY ("+COLUMN_ITEMS_LISTID+") REFERENCES "+ LISTS_TABLE +" )";
    }


