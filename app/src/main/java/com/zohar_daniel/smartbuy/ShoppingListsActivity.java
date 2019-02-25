package com.zohar_daniel.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
    private static CustomAdapter_ShoppingLists adapter;
    int listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("רשימות הקניות שלי");
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.shopping_lists);
        DatabaseHelper h = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName , null , 1);

        dataModels = h.allLists();
        adapter= new CustomAdapter_ShoppingLists(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listID = dataModels.get(position).getId();
                moveToList(view);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_lists);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
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

}
