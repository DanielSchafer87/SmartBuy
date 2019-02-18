package com.zohar_daniel.smartbuy;

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

    ArrayList<InvoiceData> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("רשימות הקניות שלי");
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list);

        dataModels= new ArrayList<>();

        //InvoiceData data = new InvoiceData("רמי לוי", "חיפה", Date.valueOf("12.12.12"),(BigDecimal.valueOf(123.32)));

        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));
        dataModels.add(new InvoiceData("רמי לוי", "חיפה", "12/12/1992",(BigDecimal.valueOf(123.32))));




        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DataModel dataModel= dataModels.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        //.setAction("No action", null).show();
            }
        });
    }



}
