package com.zohar_daniel.smartbuy.Threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zohar_daniel.smartbuy.Services.Constants;

public final class UIHandler extends Handler
{
    Context context;
    ProgressDialog pd;

    public UIHandler(Looper looper, Context context)
    {
        super(looper);
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch(msg.what)
        {
            case Constants.DISPLAY_CREATELIST_PROGRESS_BAR:
            {
                pd = new ProgressDialog(context);
                pd.setTitle("אנא המתן");
                pd.setMessage("יוצר רשימת קניות");
                pd.setCancelable(false);
                pd.show();
                break;
            }
            case Constants.DISPLAY_LOADING_ITEMS_PROGRESS_BAR:
            {
                pd = new ProgressDialog(context);
                pd.setTitle("אנא המתן");
                pd.setMessage("טוען מוצרים");
                pd.setCancelable(false);
                pd.show();
                break;
            }
            case Constants.HIDE_CREATELIST_PROGRESS_BAR:{
                pd.dismiss();
                break;
            }
            case Constants.HIDE_LOADING_ITEMS_PROGRESS_BAR:{
                pd.dismiss();
                break;
            }

            default:
                break;
        }
    }
}