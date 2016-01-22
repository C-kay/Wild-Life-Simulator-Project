package com.company;

import java.awt.*;

/**
 * Created by Chukwukaodinaka Obieyisi on 10/8/2015.
 */
public class Carnivore extends Animal {

    //Constructor
    public Carnivore() {
        location = new Point();
    }

    //Constructor
    public Carnivore(int x, int y) {
        age =0;
        energy = 3;
        speed = 1;
        alive = true;
        location = new Point(x, y);
    }


    public void run() {

        Environment theEnvironment = Environment.getInstance();
        while (alive) {

            try {
                Thread.sleep((long) (Math.random() * 501) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //This checks if the herbivore already has a target
            //and also checks if the Herbivore's target is still alive
            //If there are no plant around it, it moves randomly
            if (target == null || ((Herbivore) target).isAlive() == false)
            {
                target = scanEnvironment();
                if(target != null){
                    Point targetLocation = ((Herbivore)target).getLocation();
                    moveTowards(targetLocation);
                }
                else
                    randomMove();
            }
            else {
                moveTowards(((Herbivore) target).getLocation());
            }

            //If the energy reaches a certain amount, the animal dies
            if (energy < 3 && alive == true) {
                this.killed();
                TestDrive.homeScreen.updateTextField("A carnivore ran out of energy and died");
            }

            //Each time the animal moves its gets older
            age += 1;

            if (age > 4 && age < 20 && child < 6 && energy > 3) {
                this.giveBirth();
                child++;
            }

            //If an animal reaches the age of 15 it dies
            if (age == 15 && alive == true){
                TestDrive.homeScreen.updateTextField("A carnivore just died of old age");
                this.killed();
            }
        }

        //This erases the object completely from the map
        int x = (int)location.getX();
        int y = (int)location.getY();
        theEnvironment.setMap(x , y , nullPointer);
    }


    public void moveTowards(Point location) {

        //gets location of the carnivore
        Point thisLocation = getLocation();
        //thisLocation is the location of the target
        double xDist = location.getX() - thisLocation.getX();
        double yDist = location.getY() - thisLocation.getY();

        if(Math.abs(xDist) > Math.abs(yDist))
        {
            // Movement on X axis
            if( xDist < 0)
            {
                move(new Point((int)(thisLocation.getX() - 1) ,(int)thisLocation.getY()));
            }
            else if (xDist > 0)
            {
                move(new Point((int)(thisLocation.getX() + 1) ,(int)thisLocation.getY()));
            }
        }
        else
        {
            // Movement on Y axis
            if( yDist < 0)
            {
                move(new Point((int)thisLocation.getX() ,(int)(thisLocation.getY() - 1) ));
            }
            else
            {
                move(new Point((int)thisLocation.getX() ,(int)(thisLocation.getY() + 1) ));
            }
        }

    }


    public void move(Point location)
    {
        if( validMove(location) ) {
            //Checks if there's a plant or carnivores are in the way
            if( !(Environment.getInstance().getMapObject(location.x,location.y) instanceof Carnivore) ||
                    !(Environment.getInstance().getMapObject(location.x,location.y) instanceof Plant)) {

                //If the location has a Herbivore on it and the carnivore's
                // energy is less than 7, then the herbivore is eaten
                if (Environment.getInstance().getMapObject(location.x, location.y) instanceof Herbivore && energy < 7) {
                    eatHerbivore(location.x, location.y, Environment.getInstance());
                }

                Environment.getInstance().setMap(this.location.x, this.location.y, nullPointer, location.x, location.y, this);
                this.location = location;
            }
        }
    }


    //Checks if animal is going out of the map
    public boolean validMove(Point location)
    {
        int x = (int)location.getX();
        int y = (int)location.getY();

        int width = Environment.getInstance().getWidth();
        int length = Environment.getInstance().getLength();

        return ! ( x > width-1 || y > length-1 || x < 0 || y < 0 );
    }


    public void randomMove()
    {

        int x = (int) location.getX();
        int y = (int) location.getY();
        Point locationHolder = new Point(x , y);

        int randomMove = (int)(Math.random()*4);

        switch (randomMove)
        {
            case NORTH:
                move(new Point(x, y - speed));
                break;
            case SOUTH:
                move(new Point(x, y + speed));
                break;
            case EAST:
                move(new Point(x + speed, y));
                break;
            case WEST:
                move(new Point(x - speed, y));
                break;
        }
    }


    //Scan environment for Herbivores
    public Object scanEnvironment()
    {
        int row1 = ((int)location.getY()) - scanLength;
        int row2 = ((int)location.getY()) + scanLength;

        int coln1 = ((int)location.getX()) - scanWidth;
        int coln2 = ((int)location.getX()) + scanWidth;

        Object thing = Environment.getInstance().scanMapC(row1 , row2 , coln1 , coln2);

        if (thing != null && thing instanceof Herbivore)
            return thing;
        else
            return null;
    }


    public void eatHerbivore(int x , int y , Environment theEnvironment)
    {
        ((Herbivore) (theEnvironment.getMapObject(x, y))).killed();

        //Updates the screen
        TestDrive.homeScreen.updateTextField("A Carnivore eat a Herbivore");
        TestDrive.homeScreen.updateTextField("R.I.P Herbivore");
        energy = energy + 4;
    }


    //Method for when the Object is killed
    public void killed()
    {
        if (alive == true) {
            Environment theEnvironment = Environment.getInstance();
            energy = 0;
            int x = (int) location.getX();
            int y = (int) location.getY();
            theEnvironment.setMap(x, y, nullPointer);
            alive = false;
            //Decreases the population of carnivores
            theEnvironment.setCarnivoresCount();
        }
    }


    public void giveBirth()
    {
        Environment.getInstance().spawnCarnivore(location);
        TestDrive.homeScreen.updateTextField("A Carnivore just gave birth");
        energy -= 3;
    }


    @Override
    public String toString ()
    {
        return "@";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if ( !((Carnivore)o instanceof Carnivore))
            return false;
        return true;
    }

}
