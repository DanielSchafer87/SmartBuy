package com.zohar_daniel.smartbuy;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zohar_daniel.smartbuy.Models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter_ShoppingLists extends ArrayAdapter<ShoppingList> implements View.OnClickListener{

    private List<ShoppingList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStoreName;
        TextView txtChainName;
        TextView date;
        TextView amount;
    }

    public CustomAdapter_ShoppingLists(List<ShoppingList> data, Context context) {
        super(context, R.layout.row_item_shopping_lists, data);
        this.dataSet = data;
        this.mContext=context;
    }


    @Override
    public void onClick(View v) {
        /*
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InvoiceData_ShoppingLists dataModel=(InvoiceData_ShoppingLists)object;

        switch (v.getId())
        {
            case R.id.row_shopping_lists:
                //TODO send invoice id to sql to get the list on items.
                break;
        }
        */
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ShoppingList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_shopping_lists, parent, false);
            viewHolder.txtStoreName = (TextView) convertView.findViewById(R.id.storeName);
            viewHolder.txtChainName = (TextView) convertView.findViewById(R.id.chainName);
            viewHolder.date = (TextView) convertView.findViewById(R.id.invoiceDate_date);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.invoiceAmount);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;

        viewHolder.txtStoreName.setText(dataModel.getStoreName());
        viewHolder.txtChainName.setText(dataModel.getChainName());
        viewHolder.date.setText(dataModel.getCreatedOn());
        //TODO calculate amount in SQL and create method to get the amount.
        viewHolder.amount.setText(String.valueOf(123.32));
        // Return the completed view to render on screen
        return convertView;
    }
}

