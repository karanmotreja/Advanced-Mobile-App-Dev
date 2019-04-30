package com.example.karanmotreja.lab9;

public class Item
{
    private String mImageURL;
    private String mCreator;
    private int mLikes;


    public Item(String imageURL, String creator, int likes)
    {
        mImageURL = imageURL;
        mCreator = creator;
        mLikes = likes;
    }

    public String getImageURL()
    {
        return mImageURL;
    }

    public String getCreator()
    {
        return mCreator;
    }

    public int getLikeCount()
    {
        return mLikes;
    }

}


