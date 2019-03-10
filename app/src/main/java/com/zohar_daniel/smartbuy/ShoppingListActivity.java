package com.zohar_daniel.smartbuy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.zohar_daniel.smartbuy.Adapters.CustomAdapter_ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.Services.Constants;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.GetAllXmlItems;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;
import com.zohar_daniel.smartbuy.Threads.UIHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingListActivity extends AppCompatActivity {

    List<ShoppingListItem> dataModels;
    ListView listView;
    private CustomAdapter_ShoppingList adapter;
    long listID;
    View numberPickerView;
    View addItemView;
    AlertDialog.Builder alertBuilder;
    AlertDialog amountPicker;
    AlertDialog notWeightedAmountPicker;
    AlertDialog addItemDialog;
    DatabaseHelper dbHelper;
    String chainAndStore;
    UIHandler uiHandler;
    ArrayList<ShoppingListItem> items = null;
    AutoCompleteTextView autoCompleteTextView;
    ShoppingListItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listID = getIntent().getLongExtra(Constants.LIST_ID, 0);
        setContentView(R.layout.activity_shopping_list);
        listView=(ListView)findViewById(R.id.shopping_list);

        alertBuilder = new AlertDialog.Builder(ShoppingListActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        //TODO after amount change fix the amount*price.

        addItemView = inflater.inflate(R.layout.add_item_dialog,null,false);
        autoCompleteTextView = addItemView.findViewById(R.id.auto_complete_item_name);
        alertBuilder.setView(addItemView)
                .setPositiveButton(R.string.add_new_item,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNewItem();
                    }
                }).setNegativeButton(R.string.reject_amount_change, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        addItemDialog = alertBuilder.create();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (ShoppingListItem) parent.getItemAtPosition(position);
            }
        });

        //Weighted number picker.
        numberPickerView = inflater.inflate(R.layout.number_picker_dialog, null, false);
        final NumberPicker before_point_picker = (NumberPicker) numberPickerView.findViewById(R.id.before_point_picker);
        final NumberPicker after_point_picker = (NumberPicker) numberPickerView.findViewById(R.id.after_point_picker);
        before_point_picker.setMinValue(1);
        before_point_picker.setMaxValue(100);
        after_point_picker.setMinValue(0);
        after_point_picker.setMaxValue(9);
        after_point_picker.setValue(0);
        alertBuilder.setView(numberPickerView)
                .setPositiveButton(R.string.accept_amount_change,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double value = Double.valueOf(before_point_picker.getValue() + "." + after_point_picker.getValue());
                        selectedItem.setAmount(value);
                        dbHelper.updateItem(selectedItem);
                        adapter.clear();
                        adapter.addAll(dbHelper.allListItems(listID));
                        adapter.notifyDataSetChanged();
                        listView.invalidateViews();
                    }
                }).setNegativeButton(R.string.reject_amount_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        });
        amountPicker = alertBuilder.create();

        //not weighted number picker.;
        numberPickerView = inflater.inflate(R.layout.number_picker_not_weighted_dialog, null, false);
        final NumberPicker number_picker_not_weighted = (NumberPicker) numberPickerView.findViewById(R.id.number_picker_not_weighted);
        number_picker_not_weighted.setMinValue(1);
        number_picker_not_weighted.setMaxValue(100);
        alertBuilder.setView(numberPickerView)
                .setPositiveButton(R.string.accept_amount_change,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedItem.setAmount(number_picker_not_weighted.getValue());
                        dbHelper.updateItem(selectedItem);
                        adapter.clear();
                        adapter.addAll(dbHelper.allListItems(listID));
                        adapter.notifyDataSetChanged();
                        listView.invalidateViews();
                    }
                }).setNegativeButton(R.string.reject_amount_change, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        notWeightedAmountPicker = alertBuilder.create();

        dbHelper = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName , null , 1);
        dataModels = dbHelper.allListItems(listID);
        adapter = new CustomAdapter_ShoppingList(dataModels,getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                selectedItem = dataModels.get(pos);
                new AlertDialog.Builder(ShoppingListActivity.this)
                        .setTitle("אנא בחר את הפעולה הרצויה")
                        .setNeutralButton("ביטול", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing;
                            }
                        })
                        .setPositiveButton("מחיקת מוצר", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem();
                            }
                        })
                        .setNegativeButton("עדכון כמות", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedItem.getIsWeighted().equals("1"))
                                    amountPicker.show();
                                else
                                    notWeightedAmountPicker.show();
                            }
                        }).show();
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllItemsFromXML();

            }
        });
    }

    private void deleteItem(){
        new AlertDialog.Builder(ShoppingListActivity.this)
                .setTitle("מחיקת מוצר")
                .setMessage("האם ברצונך למחוק את המוצר:" +
                        "\n" + selectedItem.getName())
                .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteItem(selectedItem);
                        adapter.remove(selectedItem);
                        adapter.notifyDataSetChanged();
                        listView.invalidateViews();
                    }
                })
                .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing.
                    }
                }).show();
    }

    private void getAllItemsFromXML(){
        List<ShoppingList> shoppingLists =  dbHelper.allLists();

        HandlerThread uiThread = new HandlerThread("UIHandler");
        uiThread.start();
        uiHandler = new UIHandler(uiThread.getLooper(),ShoppingListActivity.this);
        handleUIRequest(Constants.DISPLAY_LOADING_ITEMS_PROGRESS_BAR);

        if(items == null) {
            for (ShoppingList sl : shoppingLists) {
                if (sl.getId() == listID) {
                    chainAndStore = sl.getChainId() + "-" + sl.getStoreId();
                }
            }
            GetAllXmlItems xmlAllItems = new GetAllXmlItems();
            try {
                items = xmlAllItems.execute(chainAndStore).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayAdapter<ShoppingListItem> adapter = new ArrayAdapter<ShoppingListItem>
                    (this, android.R.layout.select_dialog_item, items);
            autoCompleteTextView.setAdapter(adapter);
        }

        handleUIRequest(Constants.HIDE_LOADING_ITEMS_PROGRESS_BAR);
        addItemDialog.show();
    }

    protected void handleUIRequest(int message)
    {
        Message msg = null;

        switch (message){
            case Constants.DISPLAY_CREATELIST_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
            case Constants.HIDE_CREATELIST_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
            case Constants.DISPLAY_LOADING_ITEMS_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
            case Constants.HIDE_LOADING_ITEMS_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
        }

        if(msg != null)
            uiHandler.sendMessage(msg);
    }

    public void addNewItem(){

        selectedItem.setListId(listID);
        dbHelper.addItem(selectedItem);
        adapter.clear();
        adapter.addAll(dbHelper.allListItems(listID));
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        autoCompleteTextView.setText("");

        if(selectedItem.getIsWeighted().equals("1"))
            amountPicker.show();
        else
            notWeightedAmountPicker.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ShoppingListsActivity.class);
        startActivity(intent);
    }

}
