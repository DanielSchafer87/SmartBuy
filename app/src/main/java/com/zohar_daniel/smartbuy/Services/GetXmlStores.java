package com.zohar_daniel.smartbuy.Services;


import android.os.AsyncTask;
import android.util.Log;

import com.zohar_daniel.smartbuy.Models.Store;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class GetXmlStores extends AsyncTask<String,Void, ArrayList<Store>> {
    @Override
    protected ArrayList<Store> doInBackground(String... parms) {

        //ArrayList<String> bar2 = parms[0];
      //  String s=  bar2.get(0);

        String path = "http://smartbuy.spinframe.online/stores/"+parms[0]+".xml";
        ArrayList<Store> storesList = new ArrayList<>();

        try {

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 );
            connection.setConnectTimeout(15000 );
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

            NodeList nList = doc.getElementsByTagName(GetTagByChain("store",parms[0]));
            storesList = new ArrayList<>();

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element)node;
                    Store store  = new Store();
                    //Store id
                    store.setId( Integer.parseInt(getValue(GetTagByChain("storeid",parms[0]),element2)));
                    //Store name
                    store.setName( getValue(GetTagByChain("storename",parms[0]),element2));
                    //Store city
                    store.setCity( getValue(GetTagByChain("city",parms[0]),element2));

                    //Add new store
                    storesList.add(store);

                    /*
                    Log.w("ID:",store.getId()+"");
                    Log.w("NAME:",store.getName()+"");
                    Log.w("CITY:",store.getCity()+"");
                    Log.w("CITY:","------------------------");
                    */
                }
            }
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }

        return storesList;
    }

    private String GetTagByChain(String tag , String chain)
    {
        String res = "";
        if(chain.equals(Constants.RAMILEVI))
        {
            switch(tag){

                case "storeid":
                      res = "StoreId";
                    break;
                case "storename":
                    res = "StoreName";
                    break;
                case "city":
                    res = "City";
                    break;
                case "store":
                    res = "Store";
                    break;
            }
        }
        else if (chain.equals(Constants.SHUFERSAL))
        {
            switch(tag){
                case "storeid":
                    res = "STOREID";
                    break;
                case "storename":
                    res = "STORENAME";
                    break;
                case "city":
                    res = "CITY";
                    break;
                case "store":
                    res = "STORE";
                    break;
            }
        }
        return res;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
