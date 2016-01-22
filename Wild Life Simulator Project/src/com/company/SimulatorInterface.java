package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ceekay on 12/25/2015.
 */
public class SimulatorInterface extends JFrame {

    private JLabel map;
    public Container plate;
    public JTextArea text;


    public SimulatorInterface ()
    {
        setTitle("Simulator");
        setLayout(new FlowLayout());
        setLocation(new Point(460,150));
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void initialize()
    {
        plate = getContentPane();
        plate.setBackground(Color.BLACK);

        text = new JTextArea(5,5);
        text.setEditable(false);
        text.setBackground(Color.GREEN);
        text.setSelectedTextColor(Color.BLACK);
    }


    public void updateTextArea(String input)
    {
        text.append(input);
        plate.add(text);
    }


    public void clearTextArea()
    {
        text.setText(null);
    }



}
