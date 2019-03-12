package com.zohar_daniel.smartbuy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
    CustomAdapter_ShoppingLists adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);



        //click on logo redirect to mainActivity
        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        listView=(ListView)findViewById(R.id.shopping_lists);
        dbHelper = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName , null , 1);

        dataModels = dbHelper.allLists();
        adapter = new CustomAdapter_ShoppingLists(dataModels,getApplicationContext());

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
                final int position = pos;
                new AlertDialog.Builder(ShoppingListsActivity.this)
                        .setTitle("מחיקת רשימה")
                        .setMessage("האם ברצונך למחוק את הרשימה?")
                        .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShoppingList list = dataModels.get(position);
                                dbHelper.deleteList(list);
                                adapter.remove(list);
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

                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_lists);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean internetOk = isNetworkAvailable();

                if(internetOk)
                {
                    moveToCreateList(view);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "אין חיבור לרשת",Toast.LENGTH_LONG).show();

                }

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
