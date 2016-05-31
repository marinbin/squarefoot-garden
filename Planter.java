/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          Planter.java
   Programmer:         Marina Mizar 
   Date Last Modified: 07 May 2016
   
   Description:        Defines objects of the Planter class, each of which
                       represents a plant growing in an indoor container.
                       Has a subclass called GardenSquare (squares in the
                       garden grid). Includes an inner class, Plant, which
                       holds the plant's data (seed type, growing status, and
                       relevant dates). Contains a method to update a Plant's
                       status and a method that adds/subtracts days to/from a
                       date.
 *****************************************************************************/
 
import java.io.Serializable; // To write objects to binary file

public class Planter implements Serializable
{
    // CLASS VARIABLES
    private Plant type;  // null = no plant in square
    
    // DEFAULT CONSTRUCTOR
    public Planter()
    {
         type = null;
    }
    
    // CONSTRUCTOR: SET PLANT TYPE
    public Planter(SeedType t, int s, Date d)
    {
        type = new Plant(t, s, d);
    }
    
    // SETTER (MUTATOR) METHOD
    public void setType(SeedType t, int s, Date d)
    {
        type = new Plant(t, s, d);
    }
    
    // GETTER (ACCESSOR) METHOD
    public Plant getType() { return type; }
    public String getInstructions()
    {
        return type.getType().getName() + ": " + type.getType().getSpecialCare();
    }
    
    // EQUALS METHOD
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other instanceof Planter)
        {
            Planter temp = (Planter) other;
            if (temp.getType().equals(this.getType()))
                return true;
        }
        return false;
    }
    
    // TOSTRING METHOD
    public String toString()
    {
        return (type.toString());
    }
    
    /**************************************************************************
     *                        INNER CLASS: PLANT                              
     **************************************************************************/
    
    public class Plant implements Serializable
    {
        // VARIABLES
        private SeedType type;         // type of plant
        private int status;            // 1 = seeds, 2 = seedling, 3 = mature
                                       // plant, 4 = ready to harvest
        private Date plantDate;       // date seeds are planted
        private Date germDate;        // date seeds should germinate by
        private Date matureDate;      // date plants can be transplanted
        private Date harvestDate;     // date plants will be harvestable

        // DEFAULT CONSTRUCTOR
        public Plant()
        {
            type = null;
            status = 0;
            plantDate = null;
            germDate = null;
            matureDate = null;
            harvestDate = null;
        }

        // CONSTRUCTOR: SET TYPE, STATUS & DATES
        public Plant(SeedType t, int s, Date today)
        {
            type = t;
            status = s;
            int day = today.getDayOfYear();
            if (status == 1)                     // if new plant is a seed:
            {                                    // today = plantDate
                plantDate = today;               // (calc germDate)
                germDate = Date.generateDate(type.getDaysToGerm(), plantDate, '+');
                matureDate = Date.generateDate(type.getDaysToMature(), plantDate, '+');
            }
            else if (status == 2)                // if new plant is a transplant:
            {                                    // today = germDate     
                germDate = today;                // (calc plantDate)
                plantDate = Date.generateDate(type.getDaysToGerm(), germDate, '-');
                matureDate = Date.generateDate(type.getDaysToMature(), plantDate, '+');
            }
            else if (status == 3)                // if new plant is a transplant:
            {                                    // today = germDate     
                matureDate = today;                // (calc plantDate)
                plantDate = Date.generateDate(type.getDaysToMature(), matureDate, '-');
                germDate = Date.generateDate(type.getDaysToGerm(), plantDate, '+');
            }
            harvestDate = Date.generateDate(type.getDaysToHarvest(), plantDate, '+');
        }

        // SETTER (MUTATOR) METHODS
        public void setType(SeedType t) { type = t; }
        public void setStatus(int s) { status = s; }
        public void setPlantDate(Date pd) { plantDate = pd; }
        public void setGermDate(Date gd) { germDate = gd; }
        public void setMatureDate(Date md) { matureDate = md; }
        public void setHarvestDate(Date hd) { harvestDate = hd; }

        // GETTER (ACCESSOR) METHODS
        public SeedType getType() { return type; }
        public int getStatus() { return status; }
        public Date getPlantDate() { return plantDate; }
        public Date getGermDate() { return germDate; }
        public Date getMatureDate() { return matureDate; }
        public Date getHarvestDate() { return harvestDate; }

        // EQUALS METHOD
        public boolean equals(Object otherObj)
        {
            if (otherObj == null)
                return false;
            if (!(otherObj instanceof Plant))
                return false;
            Plant other = (Plant) otherObj;
            if (other.getType().equals(this.getType()) &&
                other.getStatus() == this.getStatus() &&
                other.getPlantDate().equals(this.getPlantDate()) &&
                other.getGermDate().equals(this.getGermDate()) &&
                other.getMatureDate().equals(this.getMatureDate()) &&
                other.getHarvestDate().equals(this.getHarvestDate()))
                return true;
            else
                return false;
        }

        // TOSTRING METHOD
        public String toString()
        {
            String contents = " ";
            switch (status)
            {
                case 1: contents = type.getName() + " seeds"; break;
                case 2: contents = "young " + type.getName() + " plants"; break;
                case 3: contents = "mature " + type.getName() + " plants"; break;
                case 4: contents = type.getName() + " plants, ready to harvest"; break;
            }
            return contents;
        }
    }
    
    /**************************************************************************
     *                        SPECIAL CLASS METHODS                           
     **************************************************************************/
    
    // UPDATE STATUS METHOD: Change the status of a Plant based on dates
    public int updateStatus(Date date)
    {
        if (type == null)
            return 0;
        else
        {
            // Date falls before germination date: seeds
            if (type.getGermDate().compareTo(date) == 1)
                this.type.setStatus(1);
            // Date falls between germination & maturity: young plant
            if (type.getGermDate().compareTo(date) == -1 &&
                type.getMatureDate().compareTo(date) == 1)
                this.type.setStatus(2);
            // Date falls between maturity & harvest: mature plant
            if (type.getMatureDate().compareTo(date) == -1 &&
                type.getHarvestDate().compareTo(date) == 1)
                this.type.setStatus(3);
            // Date falls after harvest date: harvestable plant
            if (type.getHarvestDate().compareTo(date) == -1)
                this.type.setStatus(4);
        }
        return type.getStatus();
    }
}