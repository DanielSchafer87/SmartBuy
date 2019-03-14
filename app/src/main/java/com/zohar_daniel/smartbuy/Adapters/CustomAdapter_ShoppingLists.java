package com.zohar_daniel.smartbuy.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.R;

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

    }

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
        viewHolder.date.setText(getDateForSearch(dataModel.getCreatedOn()));
        viewHolder.amount.setText(String.valueOf(dataModel.getTotalSum()));
        // Return the completed view to render on screen
        return convertView;
    }

    //map format yyyy-MM-dd to dd/MM/yyyy
    public static String getDateForSearch(String dateInString) {

        String[] d = dateInString.split("-");

        try{
            return d[2]+"/"+d[1]+"/"+d[0];
        }catch (Exception e)
        {
            return "";
        }
    }

}

