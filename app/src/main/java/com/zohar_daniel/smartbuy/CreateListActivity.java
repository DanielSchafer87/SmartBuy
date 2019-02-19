package com.zohar_daniel.smartbuy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the chain spinner from the xml.
        Spinner dropdown_chain = findViewById(R.id.spinner_chain);
        String[] items_chain = new String[]{"רמי לוי", "שופרסל"};
        ArrayAdapter<String> adapter_chain = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_chain);
        dropdown_chain.setAdapter(adapter_chain);

        //get the chain spinner from the xml.
        Spinner dropdown_store = findViewById(R.id.spinner_store);
        String[] items_store = new String[]{"עפולה", "חיפה"};
        ArrayAdapter<String> adapter_store = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_store);
        dropdown_store.setAdapter(adapter_store);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
