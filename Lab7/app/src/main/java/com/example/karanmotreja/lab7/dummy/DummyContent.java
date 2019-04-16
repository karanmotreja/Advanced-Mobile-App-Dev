package com.example.karanmotreja.lab7.dummy;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DummyContent {


    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    public void dataSetup(){
        List<DummyContent.DummyItem> xmlData=new ArrayList<DummyContent.DummyItem>();
        DataActivity xmlDataActivity = new DataActivity();
        if (ITEMS.size() == 0) {
            try {
                xmlData = xmlDataActivity.loadXML();
                for (int i = 0; i < xmlData.size(); i++) {
                    addItem(xmlData.get(i));
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class DummyItem {
        public final String id;
        public final String name;
        public final String url;

        public DummyItem(String id, String name, String url) {
            this.id = id;
            this.name = name;
            this.url = url;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
