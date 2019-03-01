package com.zohar_daniel.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zohar_daniel.smartbuy.Adapters.CustomAdapter_ShoppingList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listID = getIntent().getLongExtra(Constants.LIST_ID, 0);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.shopping_list);

        DatabaseHelper h = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName , null , 1);
        dataModels = h.allListItems(listID);

        adapter = new CustomAdapter_ShoppingList(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DataModel dataModel= dataModels.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                //.setAction("No action", null).show();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ShoppingListsActivity.class);
        startActivity(intent);
    }

}
