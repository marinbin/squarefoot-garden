/*****************************************************************************
   Project:            Virtual Square Foot Garden
   File Name:          Driver.java
   Programmer:         Marina Mizar 
   Date Last Modified: 12 May 2016
   
   Description:             
   
   		Create a program that simulates a square foot garden. User will
   	    build and plant things in their real-life garden and then record
   	    it in their virtual garden. The program will allow the user to: 
                                    
        	* keep track of which seeds they planted & where
            * see when their plants will be ready to harvest
            * figure out which seeds should be planted when
            * know what to do each day to maintain their garden
            * know whether to start seeds indoors or outdoors
   
   Algorithm: (**SEE EACH METHOD FOR DETAILED ALGORITHM**)
   
      1. Check if there is any previous data saved to file using object reader.
           If not:
      		- prompt user for their frost dates & size of their garden.
      		- generate a list of materials they will need to construct it.
      2. If data is saved to file: 
            - display menu and allow user to choose an action.
      3. If user chooses to view their garden:
            - specify today's date.
      		- launch a GUI JFrame window displaying contents of each square.
      4. If user chooses to view projection:
            - have user input future date.
            - launch a GUI JFrame window displaying contents of each square.
      5. If user chooses to plant seeds indoors:
            - display list of best seeds to plant indoors for season.
            - have user input seed type.
            - add seeds to a list of indoor growing plants.
      6. If user chooses to plant outdoors:
      		- display list of available (empty) squares to plant in.
      		- have user input choice of square.
      		- display list of best seeds & starters to plant outdoors.
      		- have user input plant type of choice.
      		- have user specify if their starter plant is coming from their list of
      		  indoor plants or from a different source (bought at a store, etc.)
      		- add plant of appropriate type & status to garden square.
      7. If user chooses to clear a square:
      		- have user select square & confirm deletion of contents.
      		- return square contents to null (soil).
      8. If user chooses to save & exit
      		- write their updated data to binary file using object writer.
      		- close the program.
      9. If user chooses to restart their garden:
      		- open file input stream with append to false, but write no objects,
      		  clearing the file of data.
       10. Have menu reiterate in a while loop until the user selects the option
             that closes the file.
      
   Classes Needed and Purpose:
   
      SeedType -  Stores plant-raising data
      Plant - Inner class of Planter, stores a SeedType and plant's dates
      Planter - Holds a Plant
      GardenSquare - Subclass of Planter, represents a grid space
      Date - Holds date info
      GridPanel - A JPanel class that displays garden contents
      GridDisplay - A JFrame class that launches a display window
      GridSquareFullException - Prevents user from planting in full square
      Calendar - Gets current date
      Scanner - Reads from file & keyboard
      ObjectInputStream & FileInputStream - Read from binary file
      ObjectOutputStream & FileOutputStream - Write from binary file
 *****************************************************************************/
 
import java.io.*; // Reading from text file, r & w to/from binary file
import java.util.*; // Scanner and Calendar classes

public class Driver
{   
    /**************************************************************************
     *  PUBLIC STATIC VARIABLES FOR PROGRAM                    
     *  ----------------------------------                     
     *  Used throughout various static methods in the program.          
     **************************************************************************/
    
    public static final String SEEDFILES = "SeedType.txt"; // Plant species data
    public static final String SAVEFILES = "SaveFiles.dat";    // Previous input                      
    public static GardenSquare[][] grid;                          // Garden grid
    public static int size;                             // One dimension of grid
    public static ArrayList<SeedType> seedList; // Stores all plant species data
    public static LinkedList<Planter> indoorPlants; // Seedlings started indoors
    public static Date springFrost, fallFrost;                    // Frost dates
    public static Scanner keyboard = new Scanner(System.in);   // For user input
    public static Scanner fileInput;                    // For reading SEEDFILES
    public static Calendar now = Calendar.getInstance(); // Generates today's date
    public static Date today = new Date(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                                        now.get(Calendar.DAY_OF_MONTH));
    public static ObjectOutputStream objOutput;        // To read SAVEFILES data
    public static ObjectInputStream objInput;         // To write SAVEFILES data
    
    /**************************************************************************
     *  MAIN METHOD                                
     *  ----------------------------------                     
     *  Drives the program.                            
     **************************************************************************/
    
    public static void main(String[] args)
    {
        getSaveFile();
        while (true)
        {
            printMenu();
        }
    }

    /**************************************************************************
     *  METHOD: STARTING OUT                           
     *  ----------------------------------                     
     *  This method is called when a user starts or restarts the program from 
     *  scratch. The methods it calls prompt the user for some basic info     
     *  about their garden & initialize the variables that store that data.   
     **************************************************************************/
    
    public static void startingOut()
    {
        getFrostDates();
        getGridSize();
        listMaterials();
        getSeedList();
        getGrid();
        indoorPlants = new LinkedList<>();
        makeSaveFile();
    }
        
    /**************************************************************************
     *   METHOD: GET FROST DATES                           
     *  ----------------------------------                     
     *  Prompts user for last spring frost and first fall frost to determine  
     *  optimal planting periods for each kind of fruit or vegetable plant.   
     *  Uses validDate and exception catching to ensure input is valid. Saves 
     *  dates to static variables, which can be saved to file later.          
     **************************************************************************/
    
    public static void getFrostDates()
    {
        System.out.println("\nWhat is the usual last spring frost for your " +
                           "region?\nYou may want to consult an almanac.");
        System.out.println("(If your region has no frost, use January 1st.)");
        Date spring;
        Date fall;
        while (true)
        {
            try
            {
                spring = validDate();
                break; // Breaks while loop once input is valid
            }
            catch (InputMismatchException e)
            {
                System.out.println("\nNot a valid date.");
                keyboard.nextLine();
            }
        }
        springFrost = new Date(spring.getYear(), spring.getMonth(),
                               spring.getDay());
        System.out.println("\nWhat is the usual first fall frost for your " +
                           "region?\nYou may want to consult an almanac.");
        System.out.println("(If your region has no frost, use December 31st.)");
        while (true)
        {
            try
            {
                fall = validDate();
                break; // Breaks while loop once input is valid
            }
            catch (InputMismatchException e)
            {
                System.out.println("Not a valid date.");
                keyboard.nextLine();
            }
        }
        fallFrost = new Date(fall.getYear(), fall.getMonth(), fall.getDay());
    }
    
    /**************************************************************************
     *  METHOD: VALID DATE                             
     *  ----------------------------------                     
     *  Prompts a user for the date and checks to make sure it is valid.      
     *  If not, it re-prompts the user by calling itself recursively. Will    
     *  throw an InputMismatchException that must be caught elsewhere if      
     *  any input besides numbers are given. Returns valid date.              
     **************************************************************************/
    
    public static Date validDate() throws InputMismatchException
    {
        System.out.println("\nEnter the date (ex: 5 5 2016):");
        int month = keyboard.nextInt();
        int day = keyboard.nextInt();
        int year = keyboard.nextInt();
        if ((month > 12 || month < 0) || ((month == 1 || month == 3 ||
             month == 5 || month == 7 || month == 8 || month == 10 ||
             month == 12) && day > 31) || ((month == 4 || month == 6 ||
             month == 9 || month == 11) && day > 30) || (month == 2 && day > 28))
        {
            System.out.println("\nNot a valid date.");
            keyboard.nextLine(); // Clears buffer for recursive call
            validDate(); // Reprompt recursively
        }
        // If date passes all conditions without reprompting:
        Date valid = new Date(year, month, day);
        return valid;
    }
    
    /**************************************************************************
     *  METHOD: GET GRID SIZE                           
     *  ----------------------------------                     
     *  Prompts user for the size of their square foot garden (one dimension) 
     *  and validates number input with a try-catch in a loop. Saves value to 
     *  static size variable.                                                 
     **************************************************************************/
    
    public static void getGridSize()
    {
        System.out.println("\nWhat size square foot garden will you construct?");
        System.out.println("\t1) Small (2ft x 2ft)");
        System.out.println("\t2) Medium (3ft x 3ft)");
        System.out.println("\t3) Large (4ft x 4ft)");
        int input;
        while (true)
        {
            try
            {
                System.out.println("\nEnter number of selection (1-3):\n");
                input = keyboard.nextInt();
                break;
            }
            catch (InputMismatchException e)
            {
                keyboard.nextLine();
                System.out.println("\nERROR: Not a valid number.");
            }
        }
        if (input > 0 && input < 4)
            size = (input + 1);
        else
        {
            keyboard.nextLine();
            System.out.println("\nNot a valid selection.");
            getGridSize(); // Reprompts user, reminds them of valid options
        }
    }
    
    /**************************************************************************
     *  METHOD: GET SEED LIST                           
     *   ----------------------------------                     
     *  Data for each type of seed is stored sequentially in a text file.     
     *  This method reads each line with a Scanner initialized with a         
     *  FileInputStream linked to SEEDFILES and stores the read values in a   
     *  SeedType object. It then adds all these SeedTypes to a static         
     *  ArrayList variable. A FileNotFoundException is thrown if the file     
     *  cannot be located.                                                    
     **************************************************************************/
    
    public static void getSeedList()
    {
        seedList = new ArrayList<>();
        try
        {
            fileInput = new Scanner(new FileInputStream(SEEDFILES));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("\nFile called " + SEEDFILES + " not found.");
        }

        while (fileInput.hasNext())
        {
            String name = fileInput.nextLine();
            String description = fileInput.nextLine();
            String specialCare = fileInput.nextLine();
            int plantsPerSquare = Integer.parseInt(fileInput.nextLine());
            int daysToGerm = Integer.parseInt(fileInput.nextLine());
            int daysToMature = Integer.parseInt(fileInput.nextLine());
            int daysToHarvest = Integer.parseInt(fileInput.nextLine());
            String indoors = fileInput.nextLine();
            int firstIndoorDay = Integer.parseInt(fileInput.nextLine());
            int lastIndoorDay = Integer.parseInt(fileInput.nextLine());
            int firstOutdoorDay = Integer.parseInt(fileInput.nextLine());
            int lastOutdoorDay = Integer.parseInt(fileInput.nextLine());
            boolean seedIndoors = false;
            Date earliestIndoors = null;
            Date latestIndoors = null;
            Date earliestOutside = Date.generateDate(firstOutdoorDay,
                                                     springFrost, '+');
            Date latestOutside = Date.generateDate(lastOutdoorDay, fallFrost,
                                                   '-');
            if (indoors.equals("yes"))
            {
                seedIndoors = true;
                earliestIndoors = Date.generateDate(firstIndoorDay, springFrost, '+');
                latestIndoors = Date.generateDate(lastIndoorDay, fallFrost, '-');
            }
            SeedType temp = new SeedType(name, description, specialCare,
                                         plantsPerSquare, daysToGerm,
                                         daysToMature, daysToHarvest, seedIndoors,
                                         earliestIndoors, latestIndoors,
                                         earliestOutside, latestOutside);
            seedList.add(temp);
            fileInput.nextLine();
        }
        fileInput.close();
    }
    
    /**************************************************************************
     *  METHOD: GET GRID                              
     *  ----------------------------------                     
     *  Initializes a 2D Array of GardenSquares, the entire virtual square    
     *  foot garden, with a for loop and the size input by user. Each grid    
     *  square is assigned a row and column but no Plant inner object yet.    
     **************************************************************************/
    
    public static void getGrid()
    {
        grid = new GardenSquare[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
                grid[j][i] = new GardenSquare(j, i); 
        }
    }
    
    /**************************************************************************
     *  METHOD: LIST MATERIALS                           
     *   ----------------------------------                     
     *  Uses the size constraints given by the user to calculate for them a   
     *  basic list of materials they will need to construct their garden      
     *  (boards to build the frame, wood to delineate the squares, proper     
     *  ingredients and amounts, etc.)                                        
     **************************************************************************/
    
    public static void listMaterials()
    {
        System.out.println("\nMaterials you will need to build your square foot" +
                           " garden:");
        System.out.println("\n\t*4 wooden boards (" + size + " x 0.5 feet each)");
        System.out.println("\t*" + (2 * (size - 1)) + " pieces of wooden lath" +
                           " (" + size + " feet long each)");
        double soilAmtEach = ((size * size) / 2) / 3;
        System. out.println("\t*At least " + soilAmtEach +
                            " cubic feet of coarse vermiculite");
        System.out.println("\t*At least " + soilAmtEach +
                            " cubic feet of peat moss");
        System.out.println("\t*At least " + soilAmtEach +
                            " cubic feet of a varied-source compost blend");
        System.out.println("");
    } 	
    	  
    /**************************************************************************
     *  METHOD: MAKE SAVE FILE                          
     *  ----------------------------------                     
     *  Initializes an ObjectOutputStream (and FileOutputStream) connected
     *  to a binary file (append set to false--will overwrite old save data).    
     *  Writes each piece of user data (their garden grid, its contents,      
     *  relevant dates) as objects into file. Throws IOException if invalid.  
     **************************************************************************/
    
    public static void makeSaveFile()
    {
        try
        {
            objOutput = new ObjectOutputStream(new FileOutputStream(SAVEFILES,
                                                                    false));
            objOutput.writeObject(size);
            objOutput.writeObject(springFrost);
            objOutput.writeObject(fallFrost);
            objOutput.writeObject(grid);
            objOutput.writeObject(seedList);
            objOutput.writeObject(indoorPlants);
            objOutput.close();
        }
        catch (IOException e)
        {
            System.out.println("Couldn't write save file because of " + e);
        }
    }

    /**************************************************************************
     *  METHOD: GET SAVE FILE                            
     *  ----------------------------------                     
     *  Initializes ObjectInputStream (and FileInputStream) connected to a    
     *  binary file where user data is stored in the form of objects. Reads   
     *  and type casts each object. Catches exceptions if the proper objects  
     *  are not found. Calls startingOut method to initialize data if there   
     *  is none found on file.                                                
     **************************************************************************/
    
    public static void getSaveFile()
    {
        try
        {
            objInput = new ObjectInputStream(new FileInputStream(SAVEFILES));
            size = (Integer) objInput.readObject();
            springFrost = (Date) objInput.readObject();
            fallFrost = (Date) objInput.readObject();
            grid = (GardenSquare[][]) objInput.readObject();
            seedList = (ArrayList<SeedType>) objInput.readObject();
            indoorPlants = (LinkedList<Planter>) objInput.readObject();
            objInput.close();
        }
        // If there is no object to read from file:
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("No save file found.");
            System.out.println("Would you like to create a new file?");
            System.out.println("Enter 'Y' or 'N':\n");
            String input = keyboard.next();
            if (input.equalsIgnoreCase("y"))
                startingOut(); // Will ask user to enter new save file data
            else
            {
            	System.out.println("\nClosing program.");
            	System.exit(0);
            }
        }
    }
    
    /**************************************************************************
     *  METHOD: PRINT MENU                               
     *  ----------------------------------                    
     *  Prints a menu for user to choose from. Uses a do-while loop to repeat 
     *  menu prompt if user input is invalid or NumberFormatException is      
     *  thrown. Uses a switch statement to call the method that corresponds   
     *  to the user's choice of action.                                       
     **************************************************************************/
    
    public static void printMenu()
    {
        boolean repeat; // Flag to repeat menu
        System.out.println("\n--------------------------");
        System.out.println("Square Foot Garden Planner");
        System.out.println("--------------------------\n");
        System.out.println("Main Menu:\n");
        System.out.println("\t1) View Garden Map");
        System.out.println("\t2) View Garden Projection");
        System.out.println("\t3) Daily Care Instructions");
        System.out.println("\t4) Plant Seed Starters Indoors");
        System.out.println("\t5) Plant in a Garden Square");
        System.out.println("\t6) Clear a Square");
        System.out.println("\t7) Save & Exit");
        System.out.println("\t8) Restart Garden");
        System.out.println("\n--------------------------");
        System.out.println("\nPlease select a number.\n");
        do
        {
            repeat = false;
            int selection = 0; // If input is invalid, will go to default switch
            String input = keyboard.nextLine();
            try
            {
                selection = Integer.parseInt(input);
            }
            catch (NumberFormatException e) { /* do nothing */ }
            switch (selection)
            {
                case 1: displayGarden(today); break;
                case 2: viewProjection(); break;
                case 3: dailyCare(); break;
                case 4: addSeedStarter(); break;
                case 5: plantSquare(); break;
                case 6: clearSquare(); break;
                case 7: saveExit(); break;
                case 8: restart(); break;
                default:
                    System.out.println("\nSorry, that is not a valid menu " +
                                       "option.\nPlease enter a number from 1 " +
                                       "to 8.");
                    repeat = true;
                    break;
            }
        } while (repeat);
        keyboard.nextLine();
    }
        
    /**************************************************************************
     *  METHOD: DISPLAY GARDEN                           
     *  ----------------------------------                     
     *  Uses a for loop to access each garden square element to a) make sure  
     *  the plant's status matches the given date (have seeds sprouted? have  
     *  plants matured?) and b) print the contents of each garden square for  
     *  the user to see using a JFrame GUI.                                   
     **************************************************************************/
    
    public static void displayGarden(Date date)
    {
        // Loop through every square in 2D array (grid)
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid[j][i].getType() != null)
                    // Update plants' status if needed based on given date
                    grid[j][i].updateStatus(date);
            }
        }
        // Pop-up window displays garden square as a table of buttons
        GridDisplay displayWindow = new GridDisplay(size, grid);
    }
    	
     /**************************************************************************
     *  METHOD: VIEW PROJECTION                        
     *  ----------------------------------                     
     *  Asks user to input a date in the future and outputs a projection of   
     *  the garden at that time. Shows user which plants of theirs will be    
     *  mature or ready to harvest by then.                                   
     **************************************************************************/
    
    public static void viewProjection()
    {
        System.out.println("\nYou can see what your garden might look like" +
                           "\nat a future date.");
        Date futureDate;
        try
        {
            futureDate = validDate();
            displayGarden(futureDate);
        }
        catch (InputMismatchException e)
        {
            System.out.println("\nNot a valid date.");
        }
        keyboard.nextLine();
    }
       
    /**************************************************************************
     *  METHOD: DAILY CARE                             
     *  ----------------------------------                     
     *  Checks squares for plants of each status (seeds, mature plants, etc.) 
     *  and lists corresponding instructions for them.                        
     **************************************************************************/
    
    public static void dailyCare()
    {
        System.out.println("\nWhat can you do with your garden today?");
        System.out.println("\nPlant seeds or young plants in these squares:");
        checkSquaresFor(0); // For empty squares
        System.out.println("\nWater and weed these squares as necessary:");
        checkSquaresFor(1); // For seeds
        checkSquaresFor(2); // For germinated plants
        System.out.println("\nCheck these squares for harvestable crops:");
        checkSquaresFor(3); // For mature plants
        checkSquaresFor(4); // For harvestable plants
        getTransplantable(); // For indoor plants ready to transplant
        System.out.println("\nSpecial instructions:\n");
        getSpecialInstructions(); // For all plants
    } 
    	
    /**************************************************************************
     *  METHOD: GET TRANSPLANTABLE                         
     *  ----------------------------------                     
     *  Uses a for-each loop to search through indoor seed starters and check 
     *  if they're ready to be transplanted. If they're mature, method prints 
     *  a line suggesting that the user plants them outside.                  
     **************************************************************************/
    
    public static void getTransplantable()
    {
        if (indoorPlants != null)
        {
            for (Planter transplant : indoorPlants)
            {
                if (transplant.updateStatus(today) >= 3) // If plants are mature
                    System.out.print("\n" + transplant.getType().getType().getName() +
                                     " plants indoors may be ready to" +
                                     " transplant.\n");
            }
        }
    }
    
    /**************************************************************************
     *  METHOD: GET SPECIAL INSTRUCTIONS                      
     *  ----------------------------------                     
     *  Uses nested for loops to access every grid square and print the       
     *  special instructions data saved for the SeedType of the Plant within. 
     **************************************************************************/
    
    public static void getSpecialInstructions()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid[j][i].updateStatus(today) > 0)
                    System.out.println(grid[j][i].getInstructions());
            }
        }
    }
        
    /**************************************************************************
     *  METHOD: ADD SEED STARTER                           
     *  ----------------------------------                     
     *  Provides the user with a list of plants that would be good to start   
     *  growing indoors given today's date. Prompts user to enter the name of 
     *  the plant they wish to start. If the name is not valid, method will   
     *  display an error message and user will be returned to the main menu.  
     **************************************************************************/
    
    public static void addSeedStarter()
    {
        System.out.println("\nIndoor seeds you have started:\n");
        for (Planter transplant : indoorPlants)
        	System.out.println(transplant.getType().getType().getName());
        System.out.println("\nAvailable seeds to plant indoors:\n");
        for (SeedType plant : seedList)
        {
            if (plant.getSeedIndoors() && 
                (plant.getEarliestIndoors().compareTo(today) == -1) &&
                (plant.getLatestIndoors().compareTo(today) == 1) &&
                plant.getSeedIndoors())
                System.out.println(plant.getName());
        }
        System.out.println("\nEnter the name of the plant you would " +
        	               "\nlike to start indoors.\n");
        String name = keyboard.nextLine();
        SeedType seeds = findSeedType(name);
        if (seeds == null)
        {
            System.out.println("\nSorry, plant not found.");
            return;
        }
        Planter newStarter = new Planter(seeds, 1, today);
        indoorPlants.add(newStarter);
        System.out.println("\n" +newStarter.getType().getType().getName()
        	               + " seeds are now maturing indoors.");
    }
 
    /**************************************************************************
     *  METHOD: PLANT SQUARE                           
     *  ----------------------------------                     
     *  Allows the user to plant seeds or mature plant starters in a garden   
     *  square of their choice. Validates numerical input by catching index   
     *  out-of-bounds & input mismatch exceptions. Prevents user from         
     *  planting in a full garden square by throwing a grid square full       
     *  exception. If user wishes to plant an indoor seed starter they grew  
     *  themselves & documented previously with the program, this method      
     *  calls the moveTransplant method to check for its existence & then     
     *  removes it from the indoorPlants LinkedList and adds its info to the  
     *  correct garden square.
     **************************************************************************/
    
    public static void plantSquare()
    {
        System.out.println("\nAvailable squares to plant in:\n");
        checkSquaresFor(0);
        int column;
        int row;
        while (true)
        {
            System.out.println("\nEnter the row number, followed by column " +
                           "number, \nseparated by a space:\n");
            try
            {
                row = keyboard.nextInt();
                column = keyboard.nextInt();
                if (grid[column - 1][row - 1].getType() != null)
                    throw new GridSquareFullException(row, column);
                break; // Breaks loop if numbers are valid
            }
            // If numbers entered are too high or low:
            // If input is not in the form of numbers:
            catch (InputMismatchException | ArrayIndexOutOfBoundsException e)
            {
                System.out.println("\nNot a valid garden square.");
                keyboard.nextLine(); // Clears buffer for next loop
            }
            // If the grid square they select is already full:
            catch (GridSquareFullException e) { return; } // End method: in case all squares are full
        }
        GardenSquare squareFoot = grid[column - 1][row - 1];
        availableSeedTypes(today);
        keyboard.nextLine(); // Clears buffer
        System.out.println("\nEnter the name of the plant you would like to" +
                           "\nput in your garden.\n");
        String name = keyboard.nextLine();
        SeedType seeds = findSeedType(name); // Returns null if input is invalid
        if (seeds == null) // Prints error message & returns to main menu
        {
            System.out.println("\nSorry, plant not found.");
            return;
        }
        else if (seeds.getSeedIndoors())
        {
            int selection;
            System.out.println("\nAre you transplanting young plants that you" +
                               "\n1) started indoors or 2) got elsewhere?");
            while (true)
            {
                try
                {
                    System.out.println("Please enter the number of your selection:\n");
                    selection = keyboard.nextInt();
                    break; // Breaks while loop
                }
                catch (InputMismatchException e)
                {
                    System.out.println("\nNot a valid selection");
                    keyboard.nextLine(); // Clears buffer for reiteration of loop
                }
            }
            if (selection == 2)
            {
                // Assumes user is planting a mature plant starter
                Date plantDate = Date.generateDate(seeds.getDaysToMature(),
                                                   today, '-');
                squareFoot.setType(seeds, 3, plantDate);
                System.out.println("\nYou planted some " + seeds.getName() + "!");
            }
            else if (selection == 1)
            {
                // Checks LinkedList of indoor plant starters
                // If matching plant w/ status does not exist, returns null
                Planter transplant = moveTransplant(seeds, 3);
                if (transplant == null)
                {
                    System.out.println("\nNo records of this indoor seed " +
                                       "starter found.");
                    return;
                }
                // Adds starter plant's info to garden square:
                 squareFoot.setType(transplant.getType().getType(),
                                    transplant.getType().getStatus(),
                                    transplant.getType().getPlantDate());
                 indoorPlants.remove(transplant); // Removes from indoor list
            }
        }
        else
        {
            squareFoot.setType(seeds, 1, today);
            System.out.println("\nYou planted some " + seeds.getName() + "!");
            System.out.println(squareFoot);
        }
    }
    
     /**************************************************************************
     *  METHOD: MOVE TRANSPLANT                          
     *  ----------------------------------                     
     *  Removes a plant of given type & status from user's LinkedList of      
     *  indoor starter plants, to be transplanted to a garden square.         
     **************************************************************************/
    
    public static Planter moveTransplant(SeedType plantType, int status)
    {
        Planter transplant = null;
        for (Planter plant : indoorPlants)
        {
            // If plant type exists & is mature:
            if (plant.getType().getType().equals(plantType) &&
                plant.getType().getStatus() == status)
                transplant = plant;
            // If plant type exists but is not mature yet:
            else if (plant.getType().getType().equals(plantType) &&
                plant.getType().getStatus() != status)
                System.out.println("\nTransplant is not ready to move outdoors.");
        }
        return transplant; // Returns null if no matching plant is found
    }
    
     /**************************************************************************
     *  METHOD: AVAILABLE SEED TYPES                        
     *  ----------------------------------                     
     *  Displays the best seeds or young plants to put in user's garden,      
     *  relative to the frost dates they originally input. Uses a for-each    
     *  loop to search SeedType list for plants whose optimal planting        
     *  date ranges match up with today's date and displays them.             
     **************************************************************************/
    
    public static void availableSeedTypes(Date today)
    {
        // Seeds you can plant directly in outdoor garden
        System.out.println("\nPlant these seeds in your garden:\n");
        for (SeedType plant : seedList)
        {
            if ((plant.getEarliestOutside().compareTo(today) == -1) &&
                (plant.getLatestOutside().compareTo(today) == 1)
                 && !plant.getSeedIndoors())
                System.out.println(plant.getName());
        }
        // Plants that are better to start indoors or buy as young plants
        System.out.println("\nPlant these young plants in your garden:\n");
        for (SeedType plant : seedList)
        {
            if ((plant.getEarliestOutside().compareTo(today) == -1) &&
                (plant.getLatestOutside().compareTo(today) == 1) &&
                 plant.getSeedIndoors())
                System.out.println(plant.getName());
        }
    }
    
    /**************************************************************************
     *  METHOD: CHECK SQUARES FOR (PLANTS W/ GIVEN STATUS)            
     *  ----------------------------------                    
     *  Uses nested for loops to search the garden grid for all plants of a   
     *  particular status (updates their status based on today's date first). 
     **************************************************************************/
    
    public static void checkSquaresFor(int status)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid[j][i].updateStatus(today) == status)
                    System.out.println("Square " + (i+ 1) + "-" + (j + 1));
            }
        }
    }
    
    /**************************************************************************
     *  METHOD: FIND SEED TYPE (OF A GIVEN NAME)                 
     *  ----------------------------------                     
     *  Takes a String name as an argument and compares it to the names of    
     *  every SeedType in the seedList ArrayList. Returns the matching plant  
     *  (or null if no match is found).                                       
     **************************************************************************/
    
    public static SeedType findSeedType(String name)
    {
        for (SeedType plant : seedList)
        {
            if (name.equalsIgnoreCase(plant.getName()))
            {
                return plant;
            }
        }
        return null; // If no plant is found
    }
    
    /**************************************************************************
     *  METHOD: CLEAR SQUARE                           
     *  ----------------------------------                     
     *  Clears the contents of a garden square so new seeds/plants may be     
     *  placed there. User inputs the grid coordinate, and if it is a valid   
     *  selection, they are asked to confirm the clear. If confirmed, that    
     *  square will be reconstructed with old position data but blank plant   
     *  data, "clearing" it.                                                  
     **************************************************************************/
    
    public static void clearSquare()
    {
        boolean repeatPrompt; // Flag to reprompt for numbers
        int column = 0;
        int row = 0;
        do
        {
            repeatPrompt = false;
            System.out.println("\nSelect a square to clear for new plantings.");
            System.out.println("Enter the row number, followed by column " +
                                   "number,\nseparated by a space (ex: '1 1' ):\n");
            String num1 = keyboard.next();
            String num2 = keyboard.next();
            try
            {
                row = Integer.parseInt(num1);
                column = Integer.parseInt(num2);
            }
            catch (NumberFormatException e) { /* do nothing */ }
            if (column < 1 || column > size || row < 1 || row > size)
            {
                System.out.println("\nNot a valid grid square.");
                repeatPrompt = true;
            }
        } while (repeatPrompt);
        System.out.println("\n" + grid[column - 1][row - 1]); // Grid starts at [0][0]
        System.out.println("Do you wish to clear this square?\n");
        String reply = keyboard.next();
        if (reply.equalsIgnoreCase("yes") || reply.equalsIgnoreCase("y"))
        {
            grid[column - 1][row - 1] = new GardenSquare(row - 1, column - 1);
            System.out.println("\nSquare cleared.");
            System.out.println(grid[column - 1][row - 1]);
        }
        else
            System.out.println("Square not cleared.");
        keyboard.nextLine(); // Clears buffer for return to main menu
    }
    
    /**************************************************************************
     *  METHOD: SAVE and EXIT                            
     *  ----------------------------------                    
     *  Creates a new save file based on any changes the user has made to     
     *  data during program's runtime by calling makeSaveFile method. Exits   
     *  the program.                                                          
     **************************************************************************/
    
    public static void saveExit()
    {
        makeSaveFile();
        System.exit(0);
    }
    
    /**************************************************************************
     * METHOD: RESTART                               
     *  ----------------------------------                     
     *  Overwrites user's old saved data by linking the ObjectOutputStream    
     *  and FileOutputStream to SAVEFILES with append set to false. Does not  
     *  write any objects to file. If no Exceptions are thrown & overwrite is 
     *  successful, calls startingOut method to allow user to make new file.  
     **************************************************************************/
    
    public static void restart()
    {
        System.out.println("\nAre you sure you want to restart your entire garden?");
        System.out.println("Enter 'Y' or 'N':\n");
        String selection = keyboard.next();
        if (selection.equalsIgnoreCase("y"))
        {
            try
            {
                objOutput = new ObjectOutputStream(new
                                FileOutputStream(SAVEFILES, false));
                objOutput.close();
            }
            catch (IOException e)
            {
                System.out.println("\nCouldn't delete save file because of " + e);
            }
            startingOut();
        }
        else
            System.out.println("\nOverwrite cancelled.");
    }
}