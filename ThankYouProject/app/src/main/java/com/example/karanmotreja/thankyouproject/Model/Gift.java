package com.example.karanmotreja.thankyouproject.Model;

public class Gift
{

    private String id, name, gift;

    public Gift()
    {

    }

    public Gift(String id, String name, String gift) {
        this.id = id;
        this.name = name;
        this.gift = gift;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
