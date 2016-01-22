package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ceekay on 12/24/2015.
 */
public class UserInterface extends JFrame
{


    public JTextArea text;
    private Container pane;
    private Container plate;
    private JTextField update;
    public JPanel mapPanel;
    public JPanel buttonPanel;
    public JPanel updatePanel;

    public UserInterface ()
    {
        setTitle("Wild Life Simulator");
        //Remember to put setVisible(True);
        setSize(400,150);
        setLocation(new Point(500,200));
        getContentPane().setLayout(new CardLayout(0, 0));
        setDefaultLookAndFeelDecorated(true);
        setIconImage(new ImageIcon("c:/users/ceekay/pictures/black goat.jpg").getImage()); //Change icon image
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initialization1();
    }


    public void initialization1()
    {
        pane = new JPanel();
        pane.setLayout(new GridLayout(4,2));
        //setting the labels
        JLabel Dimensions = new JLabel("Enter size of the environment");
        JLabel noHerbivores = new JLabel("Enter number of herbivores");
        JLabel noCarnivores = new JLabel("Enter number of carnivores");

        TextField sizeOfEnvironment = new TextField("Enter a number");
        TextField numberOfHerbivore = new TextField();
        TextField numberOfCarnivore = new TextField();

        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");

        //Add the labels and text box to the frame
        pane.add(Dimensions);
        pane.add(sizeOfEnvironment);

        pane.add(noHerbivores);
        pane.add(numberOfHerbivore);

        pane.add(noCarnivores);
        pane.add(numberOfCarnivore);

        //Add the start button in the first screen
        pane.add(start);
        start.setBackground(Color.green); //Set start button colour to red
        //Set second screen
        plate = new JPanel();
        plate.setLayout(new GridLayout(2,2));
        plate.setBackground(Color.BLACK);  //Set second screen background to black

        //////////////////////////////////////////////////
        mapPanel = new JPanel();
        mapPanel.setLayout(new FlowLayout());
        plate.add(mapPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        plate.add(buttonPanel);

        updatePanel = new JPanel();
        updatePanel.setLayout(new FlowLayout());
        plate.add(updatePanel);
        ///////////////////////////////////////////////////

        stop.setBackground(Color.red);  //Set stop button colour to red

        update = new JTextField();
        update.setEditable(false);
        update.setColumns(15);

        text = new JTextArea(40,40);
        text.setEditable(false);
        text.setBackground(Color.GREEN);
        text.setSelectedTextColor(Color.BLACK);

        mapPanel.add(text);
        buttonPanel.add(stop);
        updatePanel.add(update);

        start.addActionListener((e) -> {
            //Integer.parseInt() converts the TextField text to integer
            TestDrive.setDimensions( Integer.parseInt(sizeOfEnvironment.getText()) );
            //Number of carnivores to be initialized
            Environment.getInstance().setNumCarnivores(Integer.parseInt(numberOfCarnivore.getText()));
            //Number of herbivores to be initialized
            Environment.getInstance().setNumHerbivores(Integer.parseInt(numberOfHerbivore.getText()));

            /*if (Integer.parseInt(sizeOfEnvironment.getText()) <= 0)
                JOptionPane.showMessageDialog(null,"Invalid environment size");
            if (Integer.parseInt(numberOfCarnivore.getText()) <= 0)
                JOptionPane.showMessageDialog(null,"Invalid number of Carnivores");
            if (Integer.parseInt(numberOfHerbivore.getText()) <= 0)
                JOptionPane.showMessageDialog(null,"Invalid number of Herbivores");*/

            setSize(500,500);
            setLocation(new Point(400,150));

            //Starts the main simulation when all the fields are initialized
            worker.execute();
        });

        getContentPane().add(pane);
        getContentPane().add(plate);


        stop.addActionListener((e) -> {
            worker.cancel(true);
        });


    }


    public void updateTextArea(String input)
    {
        text.append(input);
        mapPanel.add(text);
    }


    public void clearTextArea()
    {
        text.setText(null);
    }


    public void updateTextField(String input)
    {
        update.setText(input);
    }

    //This creates a different a thread in the button
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
           // plate.setVisible(false); // Hide the progress window
        }
    };


}
