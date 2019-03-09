package com.zohar_daniel.smartbuy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.Store;
import com.zohar_daniel.smartbuy.Services.Constants;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.GetXmlStores;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CreateListActivity extends AppCompatActivity {

    Spinner dropdown_chain;
    Spinner dropdown_store;
    ArrayAdapter<String> adapter_store;
    String[] chainId;
    int[] storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        //get the chain spinner from the xml.
        dropdown_chain = findViewById(R.id.spinner_chain);
        String[] items_chain = new String[]{"בחר רשת","רמי לוי", "שופרסל"};
        chainId = new String[] {"", Constants.RAMILEVI,Constants.SHUFERSAL};
        ArrayAdapter<String> adapter_chain = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_chain);
        dropdown_chain.setAdapter(adapter_chain);

        //get the chain spinner from the xml.
        dropdown_store = findViewById(R.id.spinner_store);

        dropdown_chain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                   try {
                       storeList = task.execute(chain).get();
                       items_store = GetStoresName(storeList);
                       storeId = GetStoreID(storeList);
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
               else if(position == 2)
               {
                   try {

                       storeList = task.execute(chain).get();
                       items_store = GetStoresName(storeList);
                       storeId = GetStoreID(storeList);
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

    private int[] GetStoreID(ArrayList<Store> stores){
        int[] storeIDs = new int[stores.size()];

        for(int i=0 ; i<storeIDs.length ; i++)
        {
            storeIDs[i] = stores.get(i).getId();
        }

        return storeIDs;
    }

    public void moveToScreen(View view)
    {
        Intent intent = null;
        long newID = 0;
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName, null, 1);
        Date date = new Date();
        String todayDate= new SimpleDateFormat("dd-MM-yyyy").format(date);

        if(dropdown_store.getSelectedItem() == null){
            new AlertDialog.Builder(CreateListActivity.this)
                    .setTitle("לא נבחרו רשת וחנות")
                    .setMessage("אנא בחר רשת וחנות")
                    .setNeutralButton("חזור", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            return;
        }

        ShoppingList newList = new ShoppingList(
                storeId[dropdown_store.getSelectedItemPosition()],
                dropdown_store.getSelectedItem().toString(),
                chainId[dropdown_chain.getSelectedItemPosition()],
                dropdown_chain.getSelectedItem().toString(),
                todayDate,
                "KUKU");
        newID = dbHelper.addList(newList);

        switch(view.getId()) {
            case R.id.btnCamera:
                String chainAndStoreCode =  chainId[dropdown_chain.getSelectedItemPosition()]
                                            + "-" +
                                            storeId[dropdown_store.getSelectedItemPosition()];
                intent = new Intent(this, PhotoPreviewActivity.class);
                intent.putExtra(Constants.CHAIN_AND_STORE_CODE,chainAndStoreCode);
                intent.putExtra(Constants.LIST_ID,newID);
                break;
            case R.id.btnManual:
                //intent = new Intent(this, StatisticsActivity.class);
                break;
        }

        if(intent !=null)
            startActivity(intent);

    }
}
