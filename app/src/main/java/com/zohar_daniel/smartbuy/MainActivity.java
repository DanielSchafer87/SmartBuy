package com.zohar_daniel.smartbuy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.zohar_daniel.smartbuy.Models.ShoppingList;

public class MainActivity extends AppCompatActivity {

    protected final  String databaseName = "shoppingLists.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DatabaseHelper h = new DatabaseHelper(getApplicationContext(),databaseName , null , 1);
        SQLiteDatabase db =  h.getWritableDatabase();

        ShoppingList l = new ShoppingList(7,"שופרסל דיל נווה שאנן",5,"שפרסל","20/12/2018","חיפה");
        h.addList(l);
        h.allLists();
      // h.DeleteTbl(ShoppingListsSchema.LISTS_TABLE);
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

    public void kuku()
    {

        String k = "Heyy i'm kuku";
    }
}
