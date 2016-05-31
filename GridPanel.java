/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          GridPanel.java
   Programmer:         Marina Mizar 
   Date Last Modified: 11 May 2016
   
   Description:        A JPanel class that creates a grid of panels whose
                       size and contents are given by the user's square foot
                       garden data.
 *****************************************************************************/

import java.awt.*; // To work with colors & other interfacing details
import javax.swing.*; // To access JPanel superclass
import javax.swing.border.*; // To make borders

public class GridPanel extends JPanel
{
	// CLASS VARIABLES
	private static ImageIcon plantImage;
	// Names for plant images
	private static final String bean = "img/bean.png";
	private static final String beet = "img/beet.png";
	private static final String broccoli = "img/broccoli.png";
	private static final String cabbage = "img/cabbage.png";
	private static final String carrot = "img/carrot.png";
	private static final String cauliflower = "img/cauliflower.png";
	private static final String chard = "img/chard.png";
	private static final String corn = "img/corn.png";
	private static final String cucumber = "img/cucumber.png";
	private static final String eggplant = "img/eggplant.png";
	private static final String lettuce = "img/lettuce.png";
	private static final String melon = "img/melon.png";
	private static final String parsley = "img/parsley.png";
	private static final String pea = "img/pea.png";
	private static final String pepper = "img/pepper.png";
	private static final String radish = "img/radish.png";
	private static final String spinach = "img/spinach.png";
	private static final String tomato = "img/tomato.png";
	private static final String wintersquash = "img/wintersquash.png";
	
	public static final Border boundary =
            BorderFactory.createLineBorder(new Color(64, 56, 41), 2);
	
	// CONSTRUCTOR
    public GridPanel(int size, GardenSquare[][] garden)
    {
        // Creates a grid with garden size dimensions
        setLayout(new GridLayout(size, size));
        
        // Prints the contents of each GardenSquare to a panel
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
            	if (garden[j][i].getType() != null)
            	{
            		// Calls setImg method to get correct plant image
            		setImg(garden[j][i].getType().getType().getName());
            		JPanel innerSquare = new JPanel(new BorderLayout());
            		
            		// Label displays # and status of plants
            		JLabel squareLabel =
            			new JLabel(statusLabel(
            			garden[j][i].getType().getStatus(),
            			garden[j][i].getType().getType().getNumPerSquare()),
            			SwingConstants.CENTER);
            			
            		// Label displays picture of plant type
            		JLabel imageLabel = new JLabel();
            		imageLabel.setIcon(plantImage);
            		innerSquare.add(imageLabel, BorderLayout.NORTH);
            		innerSquare.add(squareLabel, BorderLayout.SOUTH);
            		innerSquare.setBorder(boundary);
            		innerSquare.setSize(160, 170);
            		innerSquare.setBackground(new Color(230, 204, 168));
            		add(innerSquare);
            	}
            	else
            	{
            		JPanel innerSquare = new JPanel(new FlowLayout());
            		JLabel squareLabel = new JLabel("EMPTY SOIL");
            		innerSquare.add(squareLabel);
            		innerSquare.setBorder(boundary);
            		innerSquare.setSize(160, 170);
            		innerSquare.setBackground(new Color(230, 204, 168));
            		add(innerSquare);
            	}
            }
        }
    }
    
    // SET IMG METHOD: Matches correct image with plant name
    public static void setImg(String plantName)
    {
    	if (plantName.equalsIgnoreCase("Beans"))
    		plantImage = new ImageIcon(bean);
    	else if (plantName.equalsIgnoreCase("Beets"))
    		plantImage = new ImageIcon(beet);
    	else if (plantName.equalsIgnoreCase("Broccoli"))
    		plantImage = new ImageIcon(broccoli);
    	else if (plantName.equalsIgnoreCase("Cabbage"))
    		plantImage = new ImageIcon(cabbage);
    	else if (plantName.equalsIgnoreCase("Cauliflower"))
    		plantImage = new ImageIcon(cauliflower);
    	else if (plantName.equalsIgnoreCase("Carrot"))
    		plantImage = new ImageIcon(carrot);
    	else if (plantName.equalsIgnoreCase("Chard"))
    		plantImage = new ImageIcon(chard);
    	else if (plantName.equalsIgnoreCase("Corn"))
    		plantImage = new ImageIcon(corn);
    	else if (plantName.equalsIgnoreCase("Cucumber"))
    		plantImage = new ImageIcon(cucumber);
    	else if (plantName.equalsIgnoreCase("Eggplant"))
    		plantImage = new ImageIcon(eggplant);
    	else if (plantName.equalsIgnoreCase("Lettuce"))
    		plantImage = new ImageIcon(lettuce);
    	else if (plantName.equalsIgnoreCase("Melon"))
    		plantImage = new ImageIcon(melon);
    	else if (plantName.equalsIgnoreCase("Parsley"))
    		plantImage = new ImageIcon(parsley);
    	else if (plantName.equalsIgnoreCase("Pea"))
    		plantImage = new ImageIcon(pea);
    	else if (plantName.equalsIgnoreCase("Pepper"))
    		plantImage = new ImageIcon(pepper);
    	else if (plantName.equalsIgnoreCase("Radish"))
    		plantImage = new ImageIcon(radish);
    	else if (plantName.equalsIgnoreCase("Spinach"))
    		plantImage = new ImageIcon(spinach);
    	else if (plantName.equalsIgnoreCase("Tomato"))
    		plantImage = new ImageIcon(tomato);
    	else if (plantName.equalsIgnoreCase("Winter Squash"))
    		plantImage = new ImageIcon(wintersquash);
    	else // In case of error: blank image printed
    		plantImage = new ImageIcon("img/none.png");		
    }
    
    // STATUS LABEL METHOD: Gets String with plant's status
    public static String statusLabel(int status, int numPlants)
    {
    	if (status == 1)
    		return "" +  numPlants + " seed(s)";
    	else if (status == 2)
    		return "" +  numPlants + " young plant(s)";
    	else if (status == 3)
    		return "" +  numPlants + " mature plant(s)";
    	else if (status == 4)
    		return "" +  numPlants + " harvestable plant(s)";
    	else
    		return "";
    }
}