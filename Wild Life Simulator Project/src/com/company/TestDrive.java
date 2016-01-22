package com.company;

//This is where the simulation prints and all the windows are updated
public class TestDrive  {

    private static int Dimensions;
    private static int Frames;
    private static final int TIME = 500;

    //public static UserInterface homeScreen;
    public static SimulatorUI homeScreen;


    public static void main(String[] args) throws InterruptedException {
        //Initializing the welcome screen
        homeScreen = new SimulatorUI();
    }


    public static void simulate() {

        Environment world = Environment.createInstance(Dimensions , Dimensions);
        world.start();

        for (int i = 0; i < Frames; i++) {

            //This statement increments the day.
            //There are 5 hours in a day
            if (world.getTime() == 6){
                world.setTime(0);
            }else{
                world.setTime(world.getTime()+1); //Increments time by one
            }

            //At 2 and 5 o'clock a plant grows at a random location
            if (world.getTime() == 2 || world.getTime() == 5){
                world.spawnPlant();
                world.spawnPlant();
                world.spawnPlant();



            }
            //updates Frame counter
            homeScreen.updateFrameButton("FRAMES: " + i + " " + " TIME: " + world.getTime());

            //updates the animal count screen
            homeScreen.updateButtonField("Population:   Carnivores =  " + world.getCarnivoreCount() + "  " + " Herbivores =  " + world.getHerbivoreCount() );
            //Prints the world map
            printMap(world);

            try{
                Thread.sleep(TIME);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            //thread that sleeps for 1000ms
            // change location in map
        }
    }


    //Prints the world
    private static void printMap(Environment world)
    {

        //This clears the screen when a new iteration of the map
        homeScreen.clearTextArea();

       ///length is the rows and width is the columns
        for (int i = 0; i < world.getLength(); i++) {

            for (int j = 0; j < world.getWidth(); j++) {

                if (world.getMapObject(j,i) ==  null)
                    homeScreen.updateTextArea("  .  ");
                else{
                    Object thing = world.getMapObject(j, i);

                    if (thing instanceof Plant)
                        homeScreen.updateTextArea("  *  ");
                    if (thing instanceof Carnivore)
                        homeScreen.updateTextArea("  @  ");
                    if (thing instanceof Herbivore)
                        homeScreen.updateTextArea("  &  ");
                }
            }
            homeScreen.updateTextArea("\n");
        }
    }


    public static void setDimensions(int dimensions) {
        Dimensions = dimensions;
    }

    public static int getDimensions() {
        return Dimensions;
    }

    public static void setFrames(int frames) {
        TestDrive.Frames = frames;
    }

}

