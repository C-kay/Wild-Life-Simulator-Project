package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ceekay on 12/27/2015.
 */
public class SimulatorUI extends JFrame{
    public JPanel mainPanel;
    private JPanel pane;
    private JTextField sizeOfEnvironment;
    private JTextField numberOfHerbivores;
    private JTextField numberOfCarnivores;
    private JTextField numberOfFrame;
    private JLabel Dimensions;
    private JLabel noHerbivores;
    private JLabel noCarnivores;
    private JLabel noFrames;
    private JButton startButton;
    private JTextArea mapField;
    private JButton animalCountButton;
    private JTextArea updateField;
    private JButton stopButton;
    private JPanel plate;
    private JPanel eastPane;
    private JPanel externalPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel stopButtonPanel;
    private JScrollPane mapScrollPanel;
    private JScrollPane updateScrollPane;
    private JButton frameUpdateButton;

    //Constructor
    public SimulatorUI()
    {
        super("Wild Life Simulator");

        setContentPane(mainPanel);
        pack();
        setSize(400,180);
        setLocation(new Point(500,200));
        setDefaultLookAndFeelDecorated(true);
        setIconImage(new ImageIcon("c:/users/ceekay/pictures/black goat.jpg").getImage()); //Change icon image
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        initialization();
    }

   private void createUIComponents() {

   }

    private void initialization() {

        startButton.setBackground(Color.GREEN); //set start button colour to red
        stopButton.setBackground(Color.red);  //Set stop button colour to red

        mapField.setBackground(Color.GREEN);
        mapField.setSelectedTextColor(Color.BLACK);

        startButton.addActionListener((e) -> {

            //This checks if a user forgot to fill a box or in
            try {
                //Integer.parseInt() converts the TextField text to integer
                TestDrive.setDimensions(Integer.parseInt(sizeOfEnvironment.getText()));
                //Number of carnivores to be initialized
                Environment.getInstance().setNumCarnivores(Integer.parseInt(numberOfCarnivores.getText()));
                //Number of herbivores to be initialized
                Environment.getInstance().setNumHerbivores(Integer.parseInt(numberOfHerbivores.getText()));
                //Number of frames to run before the program terminates
                TestDrive.setFrames(Integer.parseInt(numberOfFrame.getText()));
            }catch (NumberFormatException ea){
                JOptionPane.showMessageDialog(this.getComponent(0), "You missed a box !!");
                System.exit(0);
            }
            setSize(820, 450);
            setLocation(new Point(250, 150));

            //Starts the main simulation when all the fields are initialized
            worker.execute();
        });


        stopButton.addActionListener((e) -> {
            //Switches to home screen
            plate.setVisible(false);
            pane.setVisible(true);
            setSize(400, 180);
            setLocation(new Point(500, 200));
            System.exit(1);
        });
    }

    //This draws the map on the screen
    public void updateTextArea(String input)
    {
        mapField.append(input);
    }

    //Clears the map screen
    public void clearTextArea()
    {
        mapField.setText(null);
    }

    //This updates the feed screen
    public void updateTextField(String input)
    {
       updateField.append(input);
        updateField.append("\n");
    }

    //Updates the header of the screen with animal count
    public void updateButtonField(String input)
    {
        animalCountButton.setText(input);
    }


    public void updateFrameButton(String input)
    {
        frameUpdateButton.setText(input);
    }


    //This creates a different thread in the button that works on the simulation
   private SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
        @Override
        protected Boolean doInBackground() throws Exception {
            pane.setVisible(false);
            plate.setVisible(true); // Show the window

            TestDrive.simulate();

            return false;
        }

        @Override
        protected void done() { // Process completed
            plate.setVisible(false); // Hide the progress window
        }
    };
}
