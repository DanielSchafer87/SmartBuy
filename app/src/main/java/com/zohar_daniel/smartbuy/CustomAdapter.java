package com.zohar_daniel.smartbuy;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<InvoiceData> implements View.OnClickListener{

    private ArrayList<InvoiceData> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStoreName;
        TextView txtBranchName;
        TextView date;
        TextView amount;
    }

    public CustomAdapter(ArrayList<InvoiceData> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InvoiceData dataModel=(InvoiceData)object;

        switch (v.getId())
        {
            case R.id.row:
                //TODO send invoice id to sql to get the list on items.
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InvoiceData dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtStoreName = (TextView) convertView.findViewById(R.id.storeName);
            viewHolder.txtBranchName = (TextView) convertView.findViewById(R.id.branchName);
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
        viewHolder.txtBranchName.setText(dataModel.getBranchName());
        viewHolder.date.setText(dataModel.getDate().toString());
        viewHolder.amount.setText(dataModel.getInvoiceAmount().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}

