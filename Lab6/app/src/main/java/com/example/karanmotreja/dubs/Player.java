package com.example.karanmotreja.dubs;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private String name;
    private int imageResouceID;


    private Player(String newname, int newID)
    {
        this.name = newname;
        this.imageResouceID = newID;
    }

    public static List<Player>players = new ArrayList<Player>()
    {{

        add(new Player("Stephen Curry", R.drawable.steph));
        add(new Player("Klay Thompson", R.drawable.klay));
        add(new Player("Kevin Durant", R.drawable.kd));
        add(new Player("Draymond Green", R.drawable.draymond));
        add(new Player("DeMarcus Cousins", R.drawable.boogie));

    }};


    public String getName()
    {
        return name;
    }

    public int getImageResouceID()
    {
        return imageResouceID;
    }


    public String toString()
    {
        return this.name;
    }

}



