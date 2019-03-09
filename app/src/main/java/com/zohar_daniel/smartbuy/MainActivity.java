package com.zohar_daniel.smartbuy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DatabaseHelper h = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName, null , 1);


        SQLiteDatabase db =  h.getWritableDatabase();


        ShoppingList l = new ShoppingList(7,"שופרסל דיל נווה שאנן","5","שפרסל","2018-07-23","חיפה");
        ShoppingList l2 = new ShoppingList(8,"רמי לוי חיפה","5","רמי לוי","2018-09-25","חיפה");
        ShoppingList l3 = new ShoppingList(8,"רמי לוי עכו","5","רמי לוי","2019-03-05","עכו");

        h.addList(l);
        h.addList(l2);
        h.addList(l3);
        ShoppingListItem item = new ShoppingListItem("חלב",4,4.3,1,"0");
        ShoppingListItem item1 = new ShoppingListItem("חלבה",41,4.31,1,"1");
        ShoppingListItem item2 = new ShoppingListItem("חלבון",42,4.32,3,"0");

        h.addItem(item);
        h.addItem(item2);
        h.addItem(item1);

        ShoppingListItem item3 = new ShoppingListItem("גבינה צהובה עמק",2,4.3,2,"1");
        ShoppingListItem item4 = new ShoppingListItem("גבינה צהובה עמק",8,4.31,2,"1");
        ShoppingListItem item5 = new ShoppingListItem("קולה",1,4.32,3,"0");

        h.addItem(item3);
        h.addItem(item4);
        h.addItem(item5);



       // h.allItems();
       //h.allLists();
       // h.GetMostPurchasedProduct("23/07/2018","25/09/2018");
    //h.GetTotalExpenses("2018-04-23","2018-10-01");
      //  h.GetMostPurchasedProduct("2018-04-23","2018-10-01");
       // h.GetMostPreferredBranch("2018-04-23","2018-10-01");





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void moveToScreen(View view)
    {
        Intent intent = null;

        switch(view.getId()) {
            case R.id.btnShoppingLists:
                intent = new Intent(this, ShoppingListsActivity.class);
                break;
            case R.id.btnStatistics:
                intent = new Intent(this, StatisticsActivity.class);
                break;
        }

        if(intent !=null)
            startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        //do noting.
    }
}
