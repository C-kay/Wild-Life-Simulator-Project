package com.company;

import java.awt.*;

/**
 * Created by Ceekay on 10/8/2015.
 */
public class Plant implements Runnable
{

    private boolean alive;
    private int age;
    public Point location;
    private Object nullPointer = null;

    //Constructor
    public Plant()
    {
        location = new Point();
    }


    //Constructor
    public Plant (int x , int y)
    {
        age = 1;
        location = new Point(x , y);
        alive = true;
    }


    @Override
    public void run() {
        //Plants only live for 20000 secs
        try{
            Thread.sleep(20000);
        }catch (InterruptedException e){
            e.printStackTrace();

        }
        this.killed();
    }


    public void killed()
    {
        Environment theEnvironment = Environment.getInstance();
        int x = (int)location.getX();
        int y = (int)location.getY();
        theEnvironment.setMap(x , y , nullPointer);
        alive = false;
    }


    public void giveBirth()
    {
        Environment theEnvironment = Environment.getInstance();
        int xL = theEnvironment.getWidth();
        int yL = theEnvironment.getLength();

        if (age == 5){
            int x = ((int) Math.random()*(xL-1));
            int y = ((int) Math.random()*(yL-1));

            theEnvironment.setMap(x , y, new Plant(x , y));
            //new Thread( (Plant)theEnvironment.getMapObject(x , y) ).start();
        }
    }

    public boolean isAlive()
    {
        return alive;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString ()
    {
        return "*";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if ( !((Plant)o instanceof Plant))
            return false;
        return true;
    }
}
