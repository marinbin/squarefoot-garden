/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          GardenSquare.java
   Programmer:         Marina Mizar 
   Date Last Modified: 07 May 2016
   
   Description:        Defines objects of the GardenSquare class, each of
                       which represents a square foot of garden space in a
                       wooden planter box. Each GardenSquare can contain bare
                       soil or a type of plant, and has integer variables that
                       represent its position in the grid (and in a 2d Array).
 *****************************************************************************/

import java.io.Serializable; // To write objects to file

public class GardenSquare extends Planter implements Serializable
{
    // CLASS VARIABLES
    private int column;    // horizontal (x) position in grid (0 to 3)
    private int row;       // vertical (y) position in grid (0 to 3)
                           // corresponds with 2D array (grid)
    
    // DEFAULT CONSTRUCTOR
    public GardenSquare()
    {
        super();
        column = 4; // not a valid grid column
        row = 4;    // not a valid grid row
    }
    
    // CONSTRUCTOR: SET POSITION
    public GardenSquare(int y, int x)
    {
        super();
        row = y;
        column = x;
    }
    
    // SETTER (MUTATOR) METHODS
    public void setXPosition(int x) { column = x; }
    public void setYPosition(int y) { row = y; }
    
    // GETTER (ACCESSOR) METHODS
    public int getColumn() { return column; }
    public int getRow() { return row; }
    
    // EQUALS METHOD
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other instanceof GardenSquare)
        {
            GardenSquare temp = (GardenSquare) other;
            if (temp.getColumn() == this.getColumn() &&
                temp.getRow() == this.getRow() &&  super.equals(temp))
                return true;
        }
        return false;
    } 
    
    // TOSTRING METHOD
    public String toString()
    {
        if (this == null)
            return "Null square.";
      
        String contents;
        
        if (this.getType() == null)
            contents = "soil";
        else
            contents = super.toString();
        if (column < 4)
            return ("Square " + (column + 1) + "-" + (row + 1) + " contains: " +
                    contents);
        else 
            return ("Indoor seed starters contain: " + contents);
    }
}