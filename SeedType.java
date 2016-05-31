/*****************************************************************************
   Project:             Virtual Square Foot Garden
   File Name:           SeedType.java
   Programmer:          Marina Mizar 
   Date Last Modified:  06 May 2016
   
   Description:        Defines objects of the SeedType class, each of which
                       represents a kind of seed you can plant (carrots, 
                       tomatoes, lettuce, etc.) This class is basically a
                       storage place for plant info, and its variables store
                       data like plant name, number of seeds to plant per
                       square, when seeds should be planted, etc. Objects of
                       this data class will be created and stored in a binary
                       file prior to program's start, and this "database" will
                       be accessed during runtime.
 *****************************************************************************/
 
import java.io.Serializable; // To make class writable to binary file

public class SeedType implements Serializable
{
    // VARIABLES
    private String name;               // name of plant (carrot, lettuce, etc.)
    private String description;        // brief description of plant
    private String specialCare;        // special care instructions
    private int numPerSquare;          // number of plants to grow in a square
    private int daysToGerm;            // time it takes for seeds to sprout
    private int daysToMature;          // time til plant can be transplanted
    private int daysToHarvest;         // time it takes for fruit/veg to grow
    private boolean seedIndoors;       // true = start seeds inside, transplant
    private Date earliestIndoors;      // start of indoor planting date range
    private Date latestIndoors;        // end of indoor planting date range
    private Date earliestOutside;      // start of outdoor planting date range
    private Date latestOutside;        // end of outdoor planting date range
    
    // DEFAULT CONSTRUCTOR
    public SeedType()
    {
        name = null;    
        description = null;
        specialCare = null;
        numPerSquare = 0;
        daysToGerm = 0;
        daysToMature = 0;
        daysToHarvest = 0;
        seedIndoors = false;
        earliestIndoors = null;
        latestIndoors = null;
        earliestOutside = null;
        latestOutside = null;
    }
    
    // CONSTRUCTOR: SET ALL VARIABLE VALUES
    public SeedType(String n, String d, String sc, int nps, int dtg, int dtm,
                    int dth, boolean si, Date ei, Date li, Date eo, Date lo)
    {
        name = n;    
        description = d;
        specialCare = sc;
        numPerSquare = nps;
        daysToGerm = dtg;
        daysToMature = dtm;
        daysToHarvest = dth;
        seedIndoors = si;
        earliestIndoors = ei;
        latestIndoors = li;
        earliestOutside = eo;
        latestOutside = lo;
    }
    
    // SETTER (MUTATOR) METHODS
    public void setName(String n) { name = n; }
    public void setDescription(String d) { description = d; }
    public void setSpecialCare(String sc) { specialCare = sc; }
    public void setNumPerSquare(int nps) { numPerSquare = nps; }
    public void setDaysToGerm(int dtg) { daysToGerm = dtg; }
    public void setDaysToMature(int dtm) { daysToHarvest = dtm; }
    public void setDaysToHarvest(int dth) { daysToHarvest = dth; }
    public void setSeedIndoors(boolean si) { seedIndoors = si; }
    public void setEarliestIndoors(Date ei) { earliestIndoors = ei; }
    public void setLatestIndoors(Date li) { latestIndoors = li; }
    public void setEarliestOutside(Date eo) { earliestOutside = eo; }
    public void setLatestOutside(Date lo) { latestOutside = lo; }
    
    // GETTER (ACCESSOR) METHODS
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSpecialCare() { return specialCare; }
    public int getNumPerSquare() { return numPerSquare; }
    public int getDaysToGerm() { return daysToGerm; }
    public int getDaysToMature() { return daysToMature; }
    public int getDaysToHarvest() { return daysToHarvest; }
    public boolean getSeedIndoors() { return seedIndoors; }
    public Date getEarliestIndoors() { return earliestIndoors; }
    public Date getLatestIndoors() { return latestIndoors; }
    public Date getEarliestOutside() { return earliestOutside; }
    public Date getLatestOutside() { return latestOutside; }
    
    // EQUALS METHOD
    public boolean equals(Object otherObj)
    {
        if (otherObj == null)
            return false;
        if (!(otherObj instanceof SeedType))
            return false;
        SeedType other = (SeedType) otherObj;
        if (other.getName().equals(this.getName()) && 
            other.getDescription().equals(this.getDescription()) && 
            other.getSpecialCare().equals(this.getSpecialCare()) && 
            other.getNumPerSquare() == this.getNumPerSquare() && 
            other.getDaysToGerm() == this.getDaysToGerm() && 
            other.getDaysToHarvest() == this.getDaysToHarvest() && 
            other.getDaysToMature() == this.getDaysToMature() && 
            other.getSeedIndoors() == this.getSeedIndoors() && 
            other.getEarliestIndoors().equals(this.getEarliestIndoors()) && 
            other.getLatestIndoors().equals(this.getLatestIndoors()) && 
            other.getEarliestOutside().equals(this.getEarliestOutside()) &&
            other.getLatestOutside().equals(this.getLatestOutside()))
            return true;
        else
            return false;
    }
    
    // TOSTRING METHOD
    public String toString()
    {
        return name + ": " + description;
    }
    
}