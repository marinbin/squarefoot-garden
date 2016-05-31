/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          GridSquareFullException.java
   Programmer:         Marina Mizar 
   Date Last Modified: 11 May 2016
   
   Description:        An exception that is thrown when a user tries to plant
                       something in a grid square that is already full.
 *****************************************************************************/

public class GridSquareFullException extends Exception
{
    public GridSquareFullException(int column, int row)
    {
        super("ERROR:");
        System.out.println("Grid square " + (row) + "-" + (column) +
                           " is already planted! Please clear it before " +
                           "planting something new.");
    }
}