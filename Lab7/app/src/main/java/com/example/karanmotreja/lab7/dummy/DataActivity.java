package com.example.karanmotreja.lab7.dummy;

import android.content.res.XmlResourceParser;

import com.example.karanmotreja.lab7.MyApplication;
import com.example.karanmotreja.lab7.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataActivity
{
    public List<DummyContent.DummyItem> loadXML() throws XmlPullParserException, IOException {
        String new_name = new String();
        String new_url = new String();
        int id_counter = 0;
        List<DummyContent.DummyItem> heroes=new ArrayList<DummyContent.DummyItem>();

        StringBuffer stringBuffer = new StringBuffer();

        XmlResourceParser xpp = MyApplication.getAppContext().getResources().getXml(R.xml.marvel);

        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("hero")) {
                        stringBuffer.append("\nSTART_TAG: " + xpp.getName());
                    }
                    if (xpp.getName().equals("name")) {
                        stringBuffer.append("\nSTART_TAG: " + xpp.getName());
                        eventType = xpp.next();
                        new_name = xpp.getText();
                    }
                    else if (xpp.getName().equals("url")) {
                        stringBuffer.append("\nSTART_TAG: " + xpp.getName());
                        eventType = xpp.next();
                        new_url = xpp.getText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("hero")) {
                        id_counter++;

                        DummyContent.DummyItem new_item = new DummyContent.DummyItem(String.valueOf(id_counter), new_name, new_url);
                        heroes.add(new_item);
                    }
                    break;

                case XmlPullParser.TEXT:
                    break;
            }
            eventType = xpp.next();
        }
        return heroes;
    }

}
