package com.zohar_daniel.smartbuy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zohar_daniel.smartbuy.Adapters.CustomAdapter_ShoppingLists;
import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Services.Constants;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;

import java.util.List;

public class ShoppingListsActivity extends AppCompatActivity {

    List<ShoppingList> dataModels;
    ListView listView;
    long listID;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("רשימות הקניות שלי");
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.shopping_lists);
        final DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName , null , 1);

        dataModels = dbHelper.allLists();
        CustomAdapter_ShoppingLists adapter = new CustomAdapter_ShoppingLists(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listID = dataModels.get(position).getId();
                moveToList(view);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                new AlertDialog.Builder(ShoppingListsActivity.this)
                        .setTitle("מחיקת רשימה")
                        .setMessage("האם ברצונך למחוק את הרשימה?")
                        .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO implement delete list.
                            }
                        })
                        .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing.
                            }
                        }).show();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_lists);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCreateList(view);
            }
        });
    }

    public void moveToList(View view) {
        Intent intent = null;
        intent = new Intent(this, ShoppingListActivity.class);
        intent.putExtra(Constants.LIST_ID,listID);
        startActivity(intent);
    }

    public void moveToCreateList(View view)
    {
        Intent intent = null;
        intent = new Intent(this, CreateListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
