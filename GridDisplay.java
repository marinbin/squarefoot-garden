/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          GridDisplay.java
   Programmer:         Marina Mizar 
   Date Last Modified: 11 May 2016
   
   Description:        A JFrame class that displays a visual representation
                       of the user's virtual square foot garden. Has a central
                       panel with a button for each garden square (GridPanel)
                       and an close button with an event listener that
                       disposes of the window.
 *****************************************************************************/

import java.awt.*; // Interfaces
import java.awt.event.*; // ActionEvents
import javax.swing.*; // JPanels & JFrames

public class GridDisplay extends JFrame
{
    // CLASS VARIABLES
    private GridPanel grid;
    private JButton exitButton;
	
    // DEFAULT CONSTRUCTOR
    public GridDisplay(int size, GardenSquare[][] garden)
    {
        // Call superclass constructor & set title
        super("Your Square Foot Garden");
		setLayout(new BorderLayout());
        
        // Create a GridPanel object to hold garden square buttons
        grid = new GridPanel(size, garden);
        add(grid, BorderLayout.CENTER);
        
        // Create an exit button & set up event listener
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitButtonListener());
        add(exitButton, BorderLayout.SOUTH);
        
        // Enter size & other window specifications
        pack();
		setVisible(true);
        setResizable(false);
        //setSize(700, 700);
		setLocationRelativeTo(null);
    }
    
    // Clicking the exit button disposes of the window
    private class ExitButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }
}