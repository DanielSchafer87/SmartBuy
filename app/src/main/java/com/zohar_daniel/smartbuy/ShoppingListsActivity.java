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

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShoppingListsActivity extends AppCompatActivity {

    ArrayList<InvoiceData_ShoppingLists> dataModels;
    ListView listView;
    private static CustomAdapter_ShoppingLists adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("רשימות הקניות שלי");
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.shopping_lists);

        dataModels = new ArrayList<>();

        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));
        dataModels.add(new InvoiceData_ShoppingLists("שופרסל", "חיפה", "Afula", "12/12/12", 123.32, "7290027600007", "1"));

        adapter= new CustomAdapter_ShoppingLists(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DataModel dataModel= dataModels.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        //.setAction("No action", null).show();

                moveToScreen(view);
            }
        });
    }

    public void moveToScreen(View view)
    {
        Intent intent = null;
        intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

}
