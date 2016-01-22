package com.company;

/**
 * Created by Ceekay on 11/24/2015.
 */

//This enables the animals to scan the environment for other objects around them
public interface Sense {
    //This sets how far the animals can see
    int scanLength = 10;//(35 / 100) * TestDrive.getDimensions();
    int scanWidth =10;//(35 / 100) * TestDrive.getDimensions();

    Object scanEnvironment();

}
