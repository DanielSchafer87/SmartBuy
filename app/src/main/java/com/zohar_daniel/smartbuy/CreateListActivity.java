package com.zohar_daniel.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.zohar_daniel.smartbuy.Models.Store;
import com.zohar_daniel.smartbuy.Services.GetXmlStores;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CreateListActivity extends AppCompatActivity {

    Spinner dropdown_chain;
    Spinner dropdown_store;
    ArrayAdapter<String> adapter_store;
    String[] chainId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the chain spinner from the xml.
        dropdown_chain = findViewById(R.id.spinner_chain);
        String[] items_chain = new String[]{"בחר רשת","רמי לוי", "שופרסל"};
        chainId = new String[] {"","7290058140886","7290027600007"};
        ArrayAdapter<String> adapter_chain = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_chain);
        dropdown_chain.setAdapter(adapter_chain);


        //get the chain spinner from the xml.
        dropdown_store = findViewById(R.id.spinner_store);
        String[] items_store = new String[]{"עפולה", "חיפה"};
         adapter_store = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_store);
        dropdown_store.setAdapter(adapter_store);


        dropdown_chain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              // int u = 0 ;
               String[] items_store = null;
               String chain = chainId[position];
               ArrayList<Store> storeList;
               GetXmlStores task = new GetXmlStores();

               if(position == 0)
               {
                   items_store = new String[]{};
               }
               else if(position == 1)
               {
                   //TODO call xml parser to get all chain stores

                   try {

                       storeList = task.execute(chain).get();
                       items_store = GetStoresName(storeList);

                       int x = 1;

                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
               else if(position == 2)
               {

                   int f = 2;
                   //TODO call xml parser to get all chain stores
                   try {

                       storeList = task.execute(chain).get();
                       items_store = GetStoresName(storeList);



                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

                adapter_store = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, items_store);
                dropdown_store.setAdapter(adapter_store);
                adapter_store.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    private String[] GetStoresName(ArrayList<Store> stores)
    {
        String[] storeNames = new String[stores.size()];

        for(int i=0 ; i<storeNames.length ; i++)
        {
            storeNames[i] = stores.get(i).getName();
        }

        return  storeNames;
    }

    private void kuku()
    {

    }


    public void moveToScreen(View view)
    {
        Intent intent = null;

        switch(view.getId()) {
            case R.id.btnCamera:
                intent = new Intent(this, PhotoPreviewActivity.class);
                break;
            case R.id.btnManual:
                //intent = new Intent(this, StatisticsActivity.class);
                break;
        }

        if(intent !=null)
            startActivity(intent);

    }
}
