package com.example.karanmotreja.afinal;

public class Restaurant
{
    private String id;
    private String name;
    private String url;

    public Restaurant()
    {

    }

    public Restaurant(String newid, String newName, String newUrl)
    {
        id = newid;
        name = newName;
        url = newUrl;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

}
