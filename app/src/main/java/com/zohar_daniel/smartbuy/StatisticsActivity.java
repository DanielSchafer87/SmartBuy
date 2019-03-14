package com.zohar_daniel.smartbuy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;


public class StatisticsActivity extends  AppCompatActivity
{


    private EditText editText_dateFrom ;
    private EditText editText_dateTo ;

    private TextView tvTotalExpenses;
    private TextView tvmostPurchasedProduct;
    private TextView tvpopularStore;


    private int btnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistics);

        //click on logo redirect to mainActivity
        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        /*Date Area*/
        editText_dateFrom = (EditText) findViewById(R.id.inputDateFrom);
        editText_dateTo = (EditText) findViewById(R.id.inputDateTo);
        //makes the editText not editable
        editText_dateFrom.setKeyListener(null);
        editText_dateTo.setKeyListener(null);

        /*Report Area*/
        tvTotalExpenses = (TextView) findViewById(R.id.tvTotalExpenses);
        tvmostPurchasedProduct = (TextView) findViewById(R.id.tvMostPurchasedProduct);
        tvpopularStore = (TextView) findViewById(R.id.tvPopularStore);


        //Show data from begin of the current month to current day
        SetStartData();

    }

    public void SetStartData()
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String Currentfrom =  formatter.format(c.getTime());
        editText_dateFrom.setText(Currentfrom);
        Currentfrom = getDateForSearch(Currentfrom);

        Calendar c2 = Calendar.getInstance();
        String CurrentTo =  formatter.format(c2.getTime());
        editText_dateTo.setText(CurrentTo);
        CurrentTo = getDateForSearch(CurrentTo);


        //Set results to textView
        String f = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName, null , 1);

        f = db.GetTotalExpenses(Currentfrom ,CurrentTo );
        tvTotalExpenses.setText("₪"+f);
        f = db.GetMostPurchasedProduct(Currentfrom ,CurrentTo );
        tvmostPurchasedProduct.setText(f);
        f = db.GetPopularStore(Currentfrom ,CurrentTo );
        tvpopularStore.setText(f);

    }

    public void GetReport(View view)
    {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName, null , 1);


        //Get From and To dates
        String from = editText_dateFrom.getText().toString();
        from = getDateForSearch(from);

        String to = editText_dateTo.getText().toString();
        to = getDateForSearch(to);

       boolean isFromGreateerto =  ISDate1GreaterDate(from,to);

       if(isFromGreateerto)
       {
           Toast.makeText(getBaseContext(), "טווח התאריכים שגוי",Toast.LENGTH_LONG).show();
       }else
       {
        //Set results
        String f = "";
        f = db.GetTotalExpenses(from ,to );
        if(f == " ") {
            Toast.makeText(getBaseContext(), "אין נתונים בין התאריכים שנבחרו",Toast.LENGTH_LONG).show();
        }
        else{
            tvTotalExpenses.setText("₪" + f);
            f = db.GetMostPurchasedProduct(from, to);
            tvmostPurchasedProduct.setText(f);
            f = db.GetPopularStore(from, to);
            tvpopularStore.setText(f);
        }
       }


    }

    public boolean ISDate1GreaterDate(String d1,String d2)
    {
        try{


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);


            if(date1.after(date2)){
               return true;
            }

            return false;
        }
        catch(ParseException ex){
            ex.printStackTrace();

            return false;
        }
    }

    public void SetDate(View view)
    {
        int day=0 , month=0 ,year=1900;

        // indict which button pressed
        btnId = view.getId();

        Calendar c = Calendar.getInstance();
        //set values to date dialog
        switch(btnId)
        {
            case R.id.btnDatefrom:

                c.set(Calendar.DAY_OF_MONTH, 1);
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                break;
            case R.id.btnDateTo:

                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);

                break;
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                       String date = getDateForUser(year ,monthOfYear+1 , dayOfMonth );

                        switch(btnId)
                        {
                            case R.id.btnDatefrom:
                                editText_dateFrom.setText(date);
                                break;
                            case R.id.btnDateTo:
                                editText_dateTo.setText(date);
                                break;
                        }
                    }
                }, year, month, day);

        datePickerDialog.show();

    }

    //map values to date in dd/MM/yyyy format
    public static String getDateForUser(int year, int month, int day) {

        String dateInString = day+"/"+month+"/"+year;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date date = formatter.parse(dateInString);

            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    //map format dd/MM/yyyy to yyyy-MM-dd
    public static String getDateForSearch(String dateInString) {

        String[] d = dateInString.split("/");

        try{
            return d[2]+"-"+d[1]+"-"+d[0];
        }catch (Exception e)
        {
            return "";
        }
    }


}
