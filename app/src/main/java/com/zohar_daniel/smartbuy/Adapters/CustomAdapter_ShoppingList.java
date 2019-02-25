package com.zohar_daniel.smartbuy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter_ShoppingList extends ArrayAdapter<ShoppingListItem> implements View.OnClickListener{

    private List<ShoppingListItem> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItemName;
        TextView txtItemPrice;
        TextView txtItemAmount;
    }

    public CustomAdapter_ShoppingList(List<ShoppingListItem> data, Context context) {
        super(context, R.layout.row_item_shopping_list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ShoppingListItem dataModel=(ShoppingListItem)object;

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
        ShoppingListItem dataModel = getItem(position);
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

        viewHolder.txtItemName.setText(dataModel.getName());
        viewHolder.txtItemAmount.setText(String.valueOf(dataModel.getAmount()));
        viewHolder.txtItemPrice.setText(String.valueOf(dataModel.getPrice()));
        // Return the completed view to render on screen
        return convertView;
    }
}
