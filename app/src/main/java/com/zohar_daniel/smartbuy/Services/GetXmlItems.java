package com.zohar_daniel.smartbuy.Services;


import android.os.AsyncTask;
import android.util.Log;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class GetXmlItems extends AsyncTask<ArrayList<String>,Void, ShoppingListItem[]> {
    @Override
    protected ShoppingListItem[] doInBackground(ArrayList<String>... parms) {

        ArrayList<String> bar2 = parms[0];
        String s=  bar2.get(0);

        String path = "http://smartbuy.spinframe.online/kuku.xml";

        try {

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 );
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream stream = connection.getInputStream();

            /*XML Parser */
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);
            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("Item");


            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    Log.w("ZOHAR TEST","\nItem Code : " + getValue("ItemCode", element2)+"\n"+"ItemName : " + getValue("ItemName", element2)+"\n"+"-----------------------");

                    break;
                }
            }
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }

        return new ShoppingListItem[]{};
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
