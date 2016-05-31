/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          Date.java
   Programmer:         Marina Mizar 
   Date Last Modified: 08 May 2016
   
   Description:        Defines objects of a simple Date class to store year,
                       month, and day info. Besides constructors, setters,
                       getters, equals and toString, this class contains a
                       method to convert a date into a day of the year (int)
                       and vice-versa, a method that adds or subtracts days
                       to or from a date, and implements the Comparable
                       interface with a compareTo method override that
                       indicates whether a date is before or after another
                       given date (or neither).
 *****************************************************************************/

import java.io.Serializable; // To write objects to file

public class Date implements Comparable, Serializable
{
    // CLASS VARIABLES
    private int year;
    private int month;
    private int day;
    
    // DEFAULT CONSTRUCTOR
    public Date()
    {
        year = 0;
        month = 0;
        day = 0;
    }
    
    // CONSTRUCTOR: SET ALL VALUES
    public Date(int y, int m, int d)
    {
        year = y;
        month = m;
        day = d;
    }
    
    // CONSTRUCTOR: CREATE DATE WITH YEAR & DAY OF YEAR
    public Date(int d, int y)
    {
        year = y;
        
        if (d >= 1 && d < 32)
        {
            month = 1;
            day = d;
        }
        else if (d >= 32 && d < 60)
        {
            month = 2;
            day = d - 31;
        }
        else if (d >= 60 && d < 91)
        {
            month = 3;
            day = d - 59;
        }
        else if (d >= 91 && d < 121)
        {
            month = 4;
            day = d - 90;
        }
        else if (d >= 121 && d < 152)
        {
            month = 5;
            day = d - 120;
        }
        else if (d >= 152 && d < 182)
        {
            month = 6;
            day = d - 151;
        }
        else if (d >= 182 && d < 213)
        {
            month = 7;
            day = d - 181;
        }
        else if (d >= 213 && d < 244)
        {
            month = 8;
            day = d - 212;
        }
        else if (d >= 244 && d < 274)
        {
            month = 9;
            day = d - 244;
        }
        else if (d >= 274 && d < 305)
        {
            month = 10;
            day = d - 274;
        }
        else if (d >= 305 && d < 335)
        {
            month = 11;
            day = d - 304;
        }
        else if (d >= 335 && d < 366)
        {
            month = 12;
            day = d - 334;
        }
        else // Error
        {
            month = 0;
            day = 0;
        }
    }
    
    // SETTER (MUTATOR) METHODS
    public void setYear(int y) { year = y; }
    public void setMonth(int m) { month = m; }
    public void setDay(int d) { day = d; }
    
    // GETTER (ACCESSOR) METHODS
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getDayOfYear()
    {
        int dayOfYear = 0;
        switch (month)
        {
            case 2: dayOfYear += 31; break;
            case 3: dayOfYear += 59; break;
            case 4: dayOfYear += 90; break;
            case 5: dayOfYear += 120; break;
            case 6: dayOfYear += 151; break;
            case 7: dayOfYear += 181; break;
            case 8: dayOfYear += 212; break;
            case 9: dayOfYear += 243; break;
            case 10: dayOfYear += 273; break;
            case 11: dayOfYear += 304; break;
            case 12: dayOfYear += 334; break;
        }
        dayOfYear += day;
        return dayOfYear;
    }
    
    // EQUALS METHOD
    public boolean equals(Object otherObj)
    {
        if (otherObj == null)
            return false;
        if (!(otherObj instanceof Date))
            return false;
        Date other = (Date) otherObj;
        if (other.getDay() == this.getDay() &&
            other.getMonth() == this.getMonth() &&
            other.getYear() == this.getYear())
            return true;
        return false;
    }
    
    // TO STRING METHOD
    public String toString()
    {
        return (month + "/" + day + "/" + year);
    }
    
    // GENERATE DATE METHOD: Add or subtract days to/from a LocalDate object
    public static Date generateDate(int numDays, Date date, char change)
    {
        int day = date.getDayOfYear();
        int year = date.getYear();
        if (change == '+')
            day += numDays;
        else if (change == '-')
            day -= numDays;
        if (day < 0)
        {
            day += 365;
            year--;
        }
        else if (day > 365)
        {
            day += 365;
            year++;
        }
        Date newDate = new Date(day, year);
        return newDate;
    }
    
    // COMPARE TO METHOD: Compare two given dates to see which comes first
    // 1 = original is later date, -1 = original is earler date, 0 = neither
    public int compareTo(Object otherObj)
    {  
        if (otherObj == null || !(otherObj instanceof Date) ||
            this.equals(otherObj))
            return 0;
        Date other = (Date) otherObj;
        if (this.getYear() > other.getYear())
        {
            return 1;
        }
        if (this.getYear() == other.getYear())
        {
            if(this.getDayOfYear() > other.getDayOfYear())
                return 1;
            else
                return -1;
        }
        else
            return -1;
    }
}