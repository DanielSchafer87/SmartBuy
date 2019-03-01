package com.zohar_daniel.smartbuy.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;

import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(Context context , String name, SQLiteDatabase.CursorFactory factory, int version)
    {

        super(context , name , factory , version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create ShoppingList Table if not exist
         db.execSQL(ShoppingListsSchema.CREATE_TABLE_LISTS);
        //Create Items table if not exist
       db.execSQL(ShoppingListsSchema.CREATE_TABLE_ITEMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void DeleteTbl(String tblName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE IF EXISTS "+tblName;
        db.rawQuery(query, null);
        db.close();
    }

    //**ITEM SECTION**//

    //Update one ShoppingItem,ID and Amount
    public int updateItem(ShoppingListItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ShoppingListsSchema.COLUMN_ITEMS_AMOUNT, shoppingItem.getAmount());

        int i = db.update(ShoppingListsSchema.ITEMS_TABLE, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(shoppingItem.getId()) });

        db.close();

        return i;
    }

    //Delete one shoppingItem
    public void deleteItem(ShoppingListItem shoppingItem) {
         // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ShoppingListsSchema.ITEMS_TABLE, "id = ?", new String[] { String.valueOf(shoppingItem.getId()) });
        db.close();
    }

     //Create new shoppingItem
     public void addItem(ShoppingListItem shoppingItem) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();

         //Set shoppingItem values
         //name
         values.put(ShoppingListsSchema.COLUMN_ITEMS_NAME, shoppingItem.getName());
         //amount
         values.put(ShoppingListsSchema.COLUMN_ITEMS_AMOUNT, shoppingItem.getAmount());
         //listId
         values.put(ShoppingListsSchema.COLUMN_ITEMS_LISTID, shoppingItem.getListId());
         //price
         values.put(ShoppingListsSchema.COLUMN_ITEMS_PRICE, shoppingItem.getPrice());

         // run insert
         db.insert(ShoppingListsSchema.ITEMS_TABLE,null, values);
         db.close();
     }

     //Get all items by list id
    public List<ShoppingListItem> allListItems(long id) {

        List<ShoppingListItem> shoppingItems = new LinkedList<ShoppingListItem>();
        String query = "SELECT  * FROM " + ShoppingListsSchema.ITEMS_TABLE+" where listId="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ShoppingListItem shoppingItem = null;

        if (cursor.moveToFirst()) {
            do {
                shoppingItem = new ShoppingListItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setAmount(Integer.parseInt(cursor.getString(2)));
                shoppingItem.setPrice(Double.parseDouble(cursor.getString(3)));
                shoppingItem.setListId(Integer.parseInt(cursor.getString(4)));

                shoppingItems.add(shoppingItem);
            } while (cursor.moveToNext());
        }

        db.close();

        return shoppingItems;
    }
    //Get all items
     public List<ShoppingListItem> allItems() {

         List<ShoppingListItem> shoppingItems = new LinkedList<ShoppingListItem>();
         String query = "SELECT  * FROM " + ShoppingListsSchema.ITEMS_TABLE;
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(query, null);
         ShoppingListItem shoppingItem = null;

         if (cursor.moveToFirst()) {
             do {
                 shoppingItem = new ShoppingListItem();
                 shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                 shoppingItem.setName(cursor.getString(1));
                 shoppingItem.setAmount(Integer.parseInt(cursor.getString(2)));
                 shoppingItem.setPrice(Double.parseDouble(cursor.getString(3)));
                 shoppingItem.setListId(Integer.parseInt(cursor.getString(4)));

                 shoppingItems.add(shoppingItem);
             } while (cursor.moveToNext());
         }

         db.close();

         return shoppingItems;
     }

    //**LIST SECTION**//

    //Get all Lists
    public List<ShoppingList> allLists() {

        List<ShoppingList> lists = new LinkedList<ShoppingList>();
        String query = "SELECT  * FROM " + ShoppingListsSchema.LISTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ShoppingList list = null;

        if (cursor.moveToFirst()) {
            do {
                list = new ShoppingList();
                list.setId(Integer.parseInt(cursor.getString(0)));
                list.setStoreId(Integer.parseInt(cursor.getString(1)));
                list.setStoreName(cursor.getString(2));
                list.setChainId(cursor.getString(3));
                list.setChainName(cursor.getString(4));
                list.setCreatedOn(cursor.getString(5));
                list.setCity(cursor.getString(6));

                lists.add(list);
            } while (cursor.moveToNext());
        }
        db.close();
        return lists;
    }


    //Create new shoppingList
    public long addList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        //Set shoppingItem values
        //store id
        values.put(ShoppingListsSchema.COLUMN_LISTS_STORE_ID, shoppingList.getStoreId());
        //store name
        values.put(ShoppingListsSchema.COLUMN_LISTS_STORE_NAME, shoppingList.getStoreName());
        //chain id
        values.put(ShoppingListsSchema.COLUMN_LISTS_CHAIN_ID, shoppingList.getChainId());
        //chain name
        values.put(ShoppingListsSchema.COLUMN_LISTS_CHAIN_NAME, shoppingList.getChainName());
        //date created
        values.put(ShoppingListsSchema.COLUMN_LISTS_DATE, shoppingList.getCreatedOn());
        //store city
        values.put(ShoppingListsSchema.COLUMN_LISTS_CITY, shoppingList.getCity());
        // run insert
        long newID = db.insert(ShoppingListsSchema.LISTS_TABLE,null, values);
        db.close();

        return newID;
    }

 }
