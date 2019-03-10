package com.zohar_daniel.smartbuy.Services;


import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;
import com.zohar_daniel.smartbuy.Models.ShoppingList;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.Models.Store;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class GetAllXmlItems extends AsyncTask<String,Void, ArrayList<ShoppingListItem>> {

    @Override
    protected ArrayList<ShoppingListItem> doInBackground(String... parms) {

        ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();

        String path = "http://smartbuy.spinframe.online/items/"+parms[0]+".xml";

        try {

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setReadTimeout(0);
            //connection.setConnectTimeout(0);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream stream = connection.getInputStream();

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);

            Boolean isItemCode = false;
            Boolean isItemName = false;
            Boolean isItemPrice = false;
            Boolean isBarcodeExist = false;
            Boolean isWeighted = false;
            int event = myParser.getEventType();
            ShoppingListItem item = null;

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("ItemCode")) {
                            isItemCode = true;
                        }
                        else if(name.equals("ItemName")){
                            isItemName = true;
                        }
                        else if(name.equals("ItemPrice")){
                            isItemPrice = true;
                        }
                        else if(name.equals("bIsWeighted")){
                            isWeighted = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String text = myParser.getText().trim();

                        if(!text.equals("")){
                            if(isItemCode) {
                                isBarcodeExist = true;
                                item = new ShoppingListItem();
                                item.setBarcode(text);
                                item.setAmount(1);
                                break;
                            }
                            else if(isBarcodeExist && isItemName){
                                item.setName(text);
                            }
                            else if(isBarcodeExist && isWeighted){
                                item.setIsWeighted(text);
                            }
                            else if(isBarcodeExist && isItemPrice){
                                isBarcodeExist = false;
                                item.setPrice(Double.valueOf(text));
                                shoppingListItems.add(item);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("ItemCode")) {
                            isItemCode = false;
                        }
                        else if(name.equals("ItemName")){
                            isItemName = false;
                        }
                        else if(name.equals("ItemPrice")){
                            isItemPrice = false;
                        }
                        else if(name.equals("bIsWeighted")){
                            isWeighted = false;
                        }
                        break;
                }
                event = myParser.next();
            }

            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }

        return shoppingListItems;
    }
}
