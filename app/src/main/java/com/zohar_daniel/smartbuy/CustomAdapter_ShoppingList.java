package com.zohar_daniel.smartbuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter_ShoppingList extends ArrayAdapter<InvoiceData_ShoppingList> implements View.OnClickListener{

    private ArrayList<InvoiceData_ShoppingList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItemName;
        TextView txtItemPrice;
        TextView txtItemAmount;
    }

    public CustomAdapter_ShoppingList(ArrayList<InvoiceData_ShoppingList> data, Context context) {
        super(context, R.layout.row_item_shopping_list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InvoiceData_ShoppingList dataModel=(InvoiceData_ShoppingList)object;

        switch (v.getId())
        {
            case R.id.row_shopping_lists:
                //TODO send invoice id to sql to get the list on items.
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InvoiceData_ShoppingList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_shopping_list, parent, false);
            viewHolder.txtItemName = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.txtItemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            viewHolder.txtItemAmount = (TextView) convertView.findViewById(R.id.itemAmount);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;

        viewHolder.txtItemName.setText(dataModel.getItemName());
        viewHolder.txtItemAmount.setText(String.valueOf(dataModel.getItemAmount()));
        viewHolder.txtItemPrice.setText(String.valueOf(dataModel.getItemPrice()));
        // Return the completed view to render on screen
        return convertView;
    }
}
