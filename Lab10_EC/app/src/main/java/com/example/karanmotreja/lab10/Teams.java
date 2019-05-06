package com.example.karanmotreja.lab10;

public class Teams
{
    private String id;
    private String name;
    private String url;

    public Teams(){

    }

    public Teams(String newid, String newName, String newURL){
        id = newid;
        name = newName;
        url = newURL;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }
}
