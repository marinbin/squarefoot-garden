Project: Virtual Square Foot Garden
Programmer: Marina Mizar
Date Last Modified: 13 May 2016
Size Last Modified: 1777 lines of code

Contents: * Driver.java (contains main & most program methods)
          * GardenSquare.java (class representing a square foot)
          * Planter.java (superclass of GardenSquare)
          * SeedType.java (class representing a type of plant)
          * Date.java (class that holds data info)
          * GridDisplay.java (class that creates a GUI window)
          * GridPanel.java (class that creates GUI window panels)
          * GridSquareFullException.java (unique exception class)
          * SeedType.txt (raw bio data for each plant)
          * SeedTypeRef.txt (reference for SeedType.txt contents)
          * SaveFiles.dat (saved user data, can be empty)
          * img folder (contains 19 plant illustrations)
          * screenshots folder (contains 6 prtsc of program in action)

Purpose:

Urban gardening offers increased food security for those with limited access to fresh fruits and vegetables--and limited gardening space. The square foot method lets anyone with a few empty square feet outdoors and some basic gardening tools grow their own plants efficiently and easily.

This Java-based program is designed to help square foot gardeners keep track of when they should plant seeds (based on the frost dates in their area), where they've planted them, and w
hen they will be ready to harvest. Once the user has set up their real garden, they can then record its contents in this virtual garden and get suggestions on how to maintain it daily to reap the most benefits.

Future Goals:
	* Fully convert the command-line-based interface into an easier-to-use GUI with dropdown menus
	* Further encapsulation of driver data and methods for more organized/easier-to-read code
	* Additional images (icons, diagrams) and instructions for a better user experience 
	* Create a module that allows the user to keep track of their vegetable harvest
	s* Create data for or allow users to add additional plant types
