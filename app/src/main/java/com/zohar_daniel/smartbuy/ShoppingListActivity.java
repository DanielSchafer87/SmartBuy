package com.zohar_daniel.smartbuy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.zohar_daniel.smartbuy.Adapters.CustomAdapter_ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.Services.Constants;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    List<ShoppingListItem> dataModels;
    ListView listView;
    private CustomAdapter_ShoppingList adapter;
    long listID;
    View numberPickerView;
    AlertDialog.Builder alertBuilder;
    AlertDialog amountPicker;
    AlertDialog notWeightedAmountPicker;
    DatabaseHelper dbHelper;
    int itemClickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listID = getIntent().getLongExtra(Constants.LIST_ID, 0);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.shopping_list);

        alertBuilder = new AlertDialog.Builder(ShoppingListActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        //TODO after amount change fix the amount*price.

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
                        ShoppingListItem item = dataModels.get(itemClickedPosition);
                        item.setAmount(value);
                        dbHelper.updateItem(item);
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
                        ShoppingListItem item = dataModels.get(itemClickedPosition);
                        item.setAmount(number_picker_not_weighted.getValue());
                        dbHelper.updateItem(item);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //DataModel dataModel= dataModels.get(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                final int position = pos;
                itemClickedPosition = pos;
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
                                deleteItem(position);
                            }
                        })
                        .setNegativeButton("עדכון כמות", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShoppingListItem item = dataModels.get(position);
                                if(item.getIsWeighted().equals("1"))
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void deleteItem(int pos){
        final int position = pos;
        new AlertDialog.Builder(ShoppingListActivity.this)
                .setTitle("מחיקת מוצר")
                .setMessage("האם ברצונך למחוק את המוצר:" +
                        "\n" + dataModels.get(pos).getName())
                .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShoppingListItem itemToDelete = dataModels.get(position);
                        dbHelper.deleteItem(itemToDelete);
                        adapter.remove(itemToDelete);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ShoppingListsActivity.class);
        startActivity(intent);
    }

}
