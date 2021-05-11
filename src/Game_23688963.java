import java.io.File;

public class Game_23688963 {
	
	// Validation methods
	
	public static void validation1(String[] args) {
		// Usage exception for command line arguments
		File tmpFile = new File(args[0]);
		if (!tmpFile.exists() || !args[0].endsWith(".txt")) {
			StdOut.println("Usage: < program name > < name of the game setup file > .txt");
    		System.exit(0); 
		}
	}
	
	public static void validation2(WarriorTypeInterface_23688963[] warriors, int cols, int rows) {
		// Exception for grid setup configuration
		// Check that there aren't more than 10 warriors on a cell
		int[][] board = new int[cols][rows];
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			++board[warriors[w].getX()][warriors[w].getY()];
			if (board[warriors[w].getX()][warriors[w].getY()]>10) { 
				StdOut.println( "Error: more than 10 warrior pieces were configured at the same position on the game grid." );
				System.exit(0);
			}
		}
		
	}
		
	public static void validation2(char[][] board, double[][] objects, String sname, int cols, int rows) {
		// Exception for grid setup configuration
		// Check that there aren't more than 1 objects on a cell. Works with potions, healers and restorers
		char c = sname.charAt(0);
		for (int i=0; i<objects.length; ++i) {
			if (board[(int) objects[i][0]][(int) objects[i][1]] == c) {
				StdOut.println("Error: multiple " + sname + " pieces were configured at the same position on the game grid.");
				System.exit(0);
			}
			else
				board[(int) objects[i][0]][(int) objects[i][1]] = c;
		}
	}
	
	public static void validation2(char[][] board, Weapon_23688963[] weapons) {
		// Exception for grid setup configuration
		// Check that there aren't more than 1 weapon on a cell.
		for (int i=0; i<weapons.length; ++i) {
			if (board[weapons[i].getX()][weapons[i].getY()] == 'x') {
				StdOut.println("Error: multiple weapon pieces were configured at the same position on the game grid.");
				System.exit(0);
			}
			else
				board[weapons[i].getX()][weapons[i].getY()] = 'x';			
		}
	}
	
	public static void validation2(int[][][] water, int cols, int rows) {
		// Exception for grid setup configuration
		// Check that there aren't more than 10 waters on a cell
		for (int i=0; i<cols; ++i)
			for (int j=0; j<rows; ++j)
				if (water[i][j][0] > 1) {
					System.out.println("Error: multiple water pieces were configured at the same position on the game grid.");
					System.exit(0);
				}
	}
	
	public static void validation3(WarriorTypeInterface_23688963[] warriors, int cols, int rows) {
		// Exception for bound on the number of warrior pieces per cell
		int[][] board = new int[cols][rows];
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			++board[warriors[w].getX()][warriors[w].getY()];
			if (board[warriors[w].getX()][warriors[w].getY()]>10) { 
				StdOut.println( "Error: warrior limit exceeded at cell " + warriors[w].getY() + " " + warriors[w].getX());
				System.exit(0);
			}
		}		
	}
	
	// Output methods
	
	public static void printBoard(char[][] board, int cols, int rows) {
		// Print board of chars using colours from ConsoleColours.java
		for (int i=0; i<rows; ++i) {
			String sline = "";
			for (int j=0; j<cols; ++j) {
				char c = board[j][i];
				if ((c-'0'>1 && c-'0'<=10) || c=='W' || c=='S' || c=='F' || c=='A')
					sline = sline + ConsoleColours.WARRIOR_COLOUR;
				else if (c=='w') 
					sline = sline + ConsoleColours.WATER_COLOUR;
				else if (c=='.')
					sline = sline + ConsoleColours.EMPTY_CELL_COLOUR;
				else
					sline = sline + ConsoleColours.OTHER_PIECES_COLOUR;
				if (c-'0'!=10)
					sline = sline + Character.toString(c);
				else 
					sline = sline + "10";
				sline = sline + ConsoleColours.RESET + " ";
			}
			StdOut.println(sline);
		}
		StdOut.print(ConsoleColours.RESET);
	}
	
	public static void printStatistics(WarriorTypeInterface_23688963[] warriors) {
		// Sort warriors using insertion sort and print statistics 
		for (int i=1; i<Warrior_23688963.counter; ++i) { 
    		WarriorTypeInterface_23688963 wh = warriors[i]; // warrior holder, helper variable
    		int j = i-1;
    		while (j >= 0 && wh.isBefore(warriors[j])) {
    			warriors[j+1] = warriors[j];
    			--j;
    		}
    		warriors[j+1] = wh;
    	}
		for (int i=0; i<Warrior_23688963.counter; ++i) 
			StdOut.println(warriors[i].toString());
		StdOut.println();
	}
	
	// Update board methods
	
	public static void fillBoard(char[][] board, int cols, int rows) {
		// Fill array of chars, the board, with empty cells
		for (int i=0; i<rows; ++i)
			for (int j=0; j<cols; ++j)
				board[j][i] = '.';
	}
	
	public static void addWeapons(char[][] board, Weapon_23688963[] weapons) {
		// Add weapons to the board IF they are available
		for (int i=0; i<weapons.length; ++i) 
			if (weapons[i].getAvailable())
				board[weapons[i].getX()][weapons[i].getY()] = 'x';
	}
	
	public static void addMagicCrystal(char[][] board, int[] crystal) {
		// Add magic crystal to the board if it hasn't been activated
		// (crystal[2] is the number of iterations since activation
		if (crystal[2] == 0) board[crystal[0]][crystal[1]] = 'c';
	}
	
	public static void addWarriors(char[][] board, WarriorTypeInterface_23688963[] warriors) {
		// Iterate through warriors and add them to char array for board where necessary
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			int x = warriors[w].getX();
			int y = warriors[w].getY();
			char c = board[x][y];
			if (c=='W' || c=='S' || c=='F' || c=='A')
				board[x][y] = '2';
			else if (c-'0'>1 && c-'0'<11) // TODO is this maths correct
				++board[x][y];
			else
				board[x][y] = warriors[w].getType().charAt(0);
		}
	}
	
	public static void addObjects(char[][] board, double[][] arr, String sname) {
		// Adds c to board at coordinates specified by arr[i][0] and [i][1]
		// Works with restorers, peacemakers and potions
		// Also checks if more than 1 of any piece has been configured on a single cell and throws an error if this is the case
		char c = sname.charAt(0);
		for (int i=0; i<arr.length; ++i) {
			if (board[(int) arr[i][0]][(int) arr[i][1]] == c) {
				System.out.println("Error: multiple " + sname + " pieces were configured at the same position on the game grid.");
				System.exit(0);
			}
			else
				board[(int) arr[i][0]][(int) arr[i][1]] = c;
		}
	}
	
	public static void addWater(char[][] board, int[][][] water, int cols, int rows) {
		// Add water to board[x][y] if water[x][y] is 1
		for (int i=0; i<cols; ++i)
			for (int j=0; j<rows; ++j)
				if (water[i][j][0] == 1)
					board[i][j] = 'w';
	}
	
	// Update warrior methods
	
	public static void setInitialValues(WarriorTypeInterface_23688963[] warriors) {
		// Stores initial defence and offence of warriors for use during battle
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			warriors[w].setInitialDefence(warriors[w].getDefenceStrength());
			warriors[w].setInitialOffence(warriors[w].getOffencePower());
		}
	}
	
	public static void moveWarriors(WarriorTypeInterface_23688963[] warriors, int iteration, int cols, int rows) {
		// Use warrior interface method, move, to move warriors depending on iteration number
		for (int i=0; i<Warrior_23688963.counter; ++i) 
    		warriors[i].move(iteration, cols, rows); 
	}
	
	public static void crystalizeWarriors(WarriorTypeInterface_23688963[] warriors, int[] crystal, int cols, int rows) {
		// Check if the crystal has been activated and get rid off the necessary warriors if it has been
		if (crystal[2] > 0) {
			++crystal[2];
			return;
		}
		int counter = 0;
		boolean[] neighbouring_types = new boolean[4]; // stores whether or not a warrior of each type neighbours the crystal
		char[] corresponding_types = {'A', 'F', 'S', 'W'};
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			if (warriors[w].neighbours(crystal[0], crystal[1], cols, rows) &&
					warriors[w].getX() != crystal[0] && warriors[w].getY() != crystal[1]) {
				++counter;
				char type = warriors[w].getType().charAt(0);
				for (int i=0; i<4; ++i) {
					if (type == corresponding_types[i] && !neighbouring_types[i]) {
						warriors[w].setProtect(true);
						neighbouring_types[i] = true;
					}
				}
			}
		}
		if (neighbouring_types[0] && neighbouring_types[1] && neighbouring_types[2] && neighbouring_types[3] && counter == 4) {
			// Remove all the warriors except those neighbouring the crystal
			StdOut.println("The Magic Crystal has been activated! Four warriors remain...");
			crystal[2] = 1;
			for (int w=0; w<Warrior_23688963.counter; ++w) {
				if (!warriors[w].getProtect()) {
					--Warrior_23688963.counter;
					for (int i=w; i<Warrior_23688963.counter; ++i)
						warriors[i] = warriors[i+1];
					--w;
				}
			}
			
		}
		// Set protect instance variable of warriors to false
		for (int w=0; w<Warrior_23688963.counter; ++w) 
			warriors[w].setProtect(false);
	}
	
	public static void hydrateWarriors(WarriorTypeInterface_23688963[] warriors, int[][][] water, int cols, int rows) {
		// Increase the warriors health by 3 if there is water nearby, otherwise decrease by 0.5
		// Also increase water warriors offense power by 1*#water in neighboring cells
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			int waters = 0;
			for (int dx=-1; dx<=1; ++dx) {
				for (int dy=-1; dy<=1; ++dy) {
					int nx = (cols+warriors[w].getX()+dx)%cols, ny = (rows+warriors[w].getY()+dy)%rows;
					if ((dx!=0 || dy!=0) && water[nx][ny][0]==1) 
						++waters;
				}
			}
			if (waters>0) 
				warriors[w].setHealth(warriors[w].getHealth() + 3);
			else
				warriors[w].setHealth(warriors[w].getHealth() - 0.5);
			if (warriors[w].getType().equals("Water"))
				warriors[w].setOffencePower(warriors[w].getOffencePower() + 1*waters); // 1 percent or 1 percent of current offense power???
		}
	}
	
	public static void restoreWarriors(WarriorTypeInterface_23688963[] warriors, double[][] restorers, int cols, int rows) {
		// Increase warriors health by restorers unique amount (restorers[i][2]) if
		// the restorer is in the warriors neighborhood
		for (int i=0; i<Warrior_23688963.counter; ++i) 
			for (int j=0; j<restorers.length; ++j) 
				if (warriors[i].neighbours((int) restorers[j][0], (int) restorers[j][1], cols, rows)) 
					warriors[i].setHealth(warriors[i].getHealth() + restorers[j][2]);
	}
	
	public static void defendWarriors(WarriorTypeInterface_23688963[] warriors) {
		// Warriors share battle information
		// Increase defense strength by 2 for each warrior of the same type on the same cell
		for (int i=0; i<Warrior_23688963.counter-1; ++i) {
			for (int j=i+1; j<Warrior_23688963.counter; ++j) {
				if (warriors[i].getX()==warriors[j].getX() && warriors[i].getY()==warriors[j].getY() && warriors[i].getType().equals(warriors[j].getType())) {
					warriors[i].setDefenceStrength(warriors[i].getDefenceStrength() + 2);
					warriors[j].setDefenceStrength(warriors[j].getDefenceStrength() + 2);
				}
			}
		}		
	}
	
	public static void ageWarriors(WarriorTypeInterface_23688963[] warriors) {
		//Increment age and update defense strength where necessary (done in class function)
		for (int i=0; i<Warrior_23688963.counter; ++i) 
			warriors[i].incrementAge();	
	}
	
	public static void loseWarriors(WarriorTypeInterface_23688963[] warriors) {
		// Check if any warriors should leave the game and decrement the counter
		// and get rid of the warriors if necessary
		for (int w=0; w<Warrior_23688963.counter; ++w) {
			if (warriors[w].getHealth() <= 0) {
				StdOut.println("A warrior has left the game!");
				--Warrior_23688963.counter;
				for (int i=w; i<Warrior_23688963.counter; ++i)
					warriors[i] = warriors[i+1];
				--w;
			}
		}
	}
	
	public static void weaponizeWarriors(WarriorTypeInterface_23688963[] warriors, Weapon_23688963[] weapons) {
		// Check if any warriors are on the same tile as a weapon and give the weapon to the warrior with the highest
		// offence power
		for (int i=0; i<Warrior_23688963.counter; ++i) {
			for (int j=0; j<weapons.length; ++j) {
				if (warriors[i].getX() == weapons[j].getX() && warriors[i].getY() == weapons[j].getY() &&
						weapons[j].getAvailable() && warriors[i].getOffencePower() > weapons[j].getWarriorStrength()) {
					weapons[j].setWarriorStrength(warriors[i].getOffencePower());
					weapons[j].setWarrior(warriors[i]);
				}
			}
		}
		// update availability and offence strengths due to weapons picked up in this iteration
		for (int i=0; i<weapons.length; ++i) 
			if (weapons[i].getWarriorStrength() > -1 && weapons[i].getAvailable())
				weapons[i].getWarrior().pickupWeapon(weapons[i]);		
	}
	
	public static void ableWarriors(WarriorTypeInterface_23688963[] warriors) {
		// Perform/complete warriors special abilities where necessary
		for (int w=0; w<Warrior_23688963.counter; ++w) { // w is warrior index
			if (warriors[w].getHealth() <= 10 && !warriors[w].getUsedAbility()) // warriors[w].getHealth() > 0 && TODO
				warriors[w].performSpecialAbility();
			else if (warriors[w].getUsedAbility()) { 
				warriors[w].decrementIterations();
				if (warriors[w].getIterations()==0) 
					warriors[w].specialAbilityCompleted();
			}
		}
	}
	
	public static void poisonWarriors(WarriorTypeInterface_23688963[] warriors, double[][] potions, int cols, int rows) {
		// first decrement invisibilty iterations where necessary
		for (int i=0; i<Warrior_23688963.counter; ++i) 
			if (warriors[i].getInvisible()) {
				warriors[i].setInvisibleIterations(warriors[i].getInvisibleIterations()-1);
				if (warriors[i].getInvisibleIterations()==0)
					warriors[i].setInvisible(false);
			}
		// check if warriors neighbour any potions and update their attributes accordingly
		for (int i=0; i<Warrior_23688963.counter; ++i) {
			boolean[] drink = new boolean[3];
			int invisibility_iterations = 0;
			for (int j=0; j<potions.length; ++j) {
				if (warriors[i].neighbours((int) potions[j][0], (int) potions[j][1], cols, rows)) {
					drink[(int) potions[j][2]] = true;
					if (potions[j][2] == 2)  
						// invisibility potion
						invisibility_iterations = Math.max(invisibility_iterations, (int) potions[j][3]);
				}
			}
			if (drink[0]) // trance causing mixture
				warriors[i].setConfused(true);
			if (drink[1]) // trance healing mixture
				warriors[i].setConfused(false);
			if (drink[2]) { // invisibility mixture
				warriors[i].setInvisible(true);
				warriors[i].setInvisibleIterations(invisibility_iterations); // TODO what if its for 0 iterations			
			}
		}
	}
	
	public static void protectWarriors(WarriorTypeInterface_23688963[] warriors, double[][] peacemakers, int cols, int rows) {
		// Set warrior[i].protect to true if it has a peacemaker in its neighborhood 
		// This will ensure that the warrior will not take damage during the next battle
		for (int i=0; i<Warrior_23688963.counter; ++i) {
			for (int j=0; j<peacemakers.length; ++j)  {
				if (warriors[i].neighbours((int) peacemakers[j][0], (int) peacemakers[j][1], cols, rows))
					warriors[i].setProtect(true);
				else
					warriors[i].setProtect(false);
			}
		}
	}
	
	public static void battle(WarriorTypeInterface_23688963[] warriors, int cols, int rows) {
		// Iterate through the warriors array and simulate battles where necessary
		// This is the updated version using initial values
		for (int i=0; i<Warrior_23688963.counter; ++i) 
			for (int j=0; j<Warrior_23688963.counter; ++j)
				if (i!=j && !warriors[j].getProtect()) 
					if (warriors[i].neighbours(warriors[j].getX(), warriors[j].getY(), cols, rows) && 
							warriors[i].getInitialDefence()>warriors[j].getInitialDefence() &&
							!(warriors[i].getInvisible() || warriors[j].getInvisible()))
						warriors[j].setHealth(warriors[j].getHealth() - warriors[i].getInitialOffence());
	}
	
	// Other methods
	
	public static double[][] getGeneralInput(In in, int num, int indices) {
		// Used to read in input for peacemaker, restorer, weapon and potion
		double[][] arr = new double[num][indices];
		for (int i=0; i<num; ++i) {
			String[] doubles = in.readLine().split(" ");
			for (int j=0; j<doubles.length; ++j) 
				arr[i][j] = Double.parseDouble(doubles[j]);
		} 
		// swap row and column number so that they refer to (x, y) coordinates rather
		for (int i=0; i<num; ++i) {
			double helper = arr[i][0];
			arr[i][0] = arr[i][1];
			arr[i][1] = helper;
		}
		return arr;
	}

	public static int[][][] updateWater(int[][][] water, int cols, int rows) {
		// Iterates through water array and adds water to new array, water2, 
		// where necessary. The contents of water2 are then returned
		int[][][] water2 = new int[cols][rows][2]; // Will replace water at the end
		for (int x=0; x<cols; ++x) {
			for (int y=0; y<rows; ++y) {
				if (water[x][y][0] == 1 && (water[x][y][1] == 2 || water[x][y][1] == 3)) {
					water2[x][y][0] = 1;
					// Water with 2 or 3 water neighbours
					for (int dx=-1; dx<=1; ++dx)
        				for (int dy=-1; dy<=1; ++dy)
        					if (dx!=0 || dy!=0)
        						++water2[(cols+x+dx)%cols][(rows+y+dy)%rows][1];
				}
				else if (water[x][y][0] == 0 && water[x][y][1] == 3) {
					// Non-water object with 3 water neighbours
					water2[x][y][0] = 1;
					//StdOut.println(x + " " + y);
					for (int dx=-1; dx<=1; ++dx)
        				for (int dy=-1; dy<=1; ++dy)
        					if (dx!=0 || dy!=0)
        						++water2[(cols+x+dx)%cols][(rows+y+dy)%rows][1];
				}
			}
		}
		return water2;
	}
	
	// MAIN
	
    public static void main(String[] args) {
    	
    	// First validation, check args are in a valid format
    	validation1(args);
    	
    	String path = args[0];
    	int outputVersion = Integer.parseInt(args[1]);
    	
        // Read in input from file at path
        In in = new In(path);
        int rows = in.readInt(), cols = in.readInt(), iterations = in.readInt();
        in.readLine(); // so that 'in' moves onto next line
        
        // OBJECT ARRAYS
        
        char[][] board = new char[cols][rows];
        
        WarriorTypeInterface_23688963[] warriors = new WarriorTypeInterface_23688963[0]; //will there always be a warrior in input?
        
        // [x][y][0] stores whether or not cell is water, [x][y][1] stores how many of its 
        // neighbors are water
        int[][][] water = new int[cols][rows][2]; 
        
        // used to store the x and y coordinates of the magic crystal, as well as the number of iterations since 
        // it was activated (0 for hasn't been activated, 1 for this iteration, and 2 for more than 1 iterations ago)
        int[] magic_crystal = new int[3]; 
        magic_crystal[2] = 1; // ensures there isn't a crystal at position (0, 0) if there isn't one in input file
         
        Weapon_23688963[] weapons = new Weapon_23688963[0];
        
        // For the rest of the objects, [i][0] is the x coordinate, [i][1] the y coordinate,
        // [i][2] the difference restorer and weapons make, and the type of potion,
        // and [i][3] is the difference that the potion makes
        double[][] restorers = new double[0][0];
        double[][] peacemakers = new double[0][0];
        double[][] potions = new double[0][0];
        
        // GET INPUT

        while (in.hasNextLine()) {
        	String[] sline = in.readLine().split(" ");
        	String piece = sline[0];
        	if (sline[1].equals("Crystal:")) {
        		piece += " " + sline[1];
        		sline[1] = sline[2]; // so that sline[1] now represents the number of pieces, used in next line
        	}
        	int num = Integer.parseInt(sline[1]);
        	if (piece.equals("Warrior:")) {
        		warriors = new WarriorTypeInterface_23688963[num]; 
        		for (int i=0; i<num; ++i) {
        			String[] vals = in.readLine().split(" "); 
        			if (vals[3].equals("Stone")) 
        				warriors[i] = new StoneWarrior_23688963(vals);
        			else if (vals[3].equals("Flame")) 
        				warriors[i] = new FlameWarrior_23688963(vals);
        			else if (vals[3].equals("Air")) 
        				warriors[i] = new AirWarrior_23688963(vals);
        			else
        				warriors[i] = new WaterWarrior_23688963(vals);
        		}	

        	}
        	else if (piece.equals("Water:")) {
        		for (int i=0; i<num; ++i) {
        			int y = in.readInt(), x = in.readInt();
        			in.readLine(); // To move onto next line
        			++water[x][y][0]; // 1 for water objects, 0 for neighbours, greater then 1 leads to exception in validation2()
        			for (int dx=-1; dx<=1; ++dx)
        				for (int dy=-1; dy<=1; ++dy)
        					if (dx!=0 || dy!=0)
        						++water[(cols+x+dx)%cols][(rows+y+dy)%rows][1];
        		}
        	}
        	else if (piece.equals("Magic Crystal:")) {
	        	if (num == 1) {
	        		sline = in.readLine().split(" ");
	        		magic_crystal[1] = Integer.parseInt(sline[0]);
	        		magic_crystal[0] = Integer.parseInt(sline[1]);
	        		magic_crystal[2] = 0;
	        	}
	        	else if (num > 1) {
	        		StdOut.println("Error: multiple magic crystal pieces configured on the grid.");
					System.exit(0);
	        	}
        	}
        	else if (piece.equals("Weapon:")) {
        		double[][] weapons_helper = getGeneralInput(in, num, 3);
        		weapons = new Weapon_23688963[num];
        		for (int i=0; i<num; ++i)
        			weapons[i] = new Weapon_23688963(weapons_helper[i]);
        	}
        	else if (piece.equals("Peacemaker:")) 
        		peacemakers = getGeneralInput(in, num, 2);
        	else if (piece.equals("Restorer:")) 
        		restorers = getGeneralInput(in, num, 3);
        	else if (piece.equals("Potion:")) 
        		potions = getGeneralInput(in, num, 4);
        }
        
        // VALIDATE INPUT
        
        fillBoard(board, cols, rows); 
        validation2(board, restorers, "healer", cols, rows);
        validation2(board, peacemakers, "healer", cols, rows);
        validation2(board, potions, "potion", cols, rows);
        validation2(water, cols, rows);
        validation2(warriors, cols, rows);
        
        // SIMULATE GAME
                
        // Initial output
        if (outputVersion == 1) {
            // warrior statistics with visualisation mode
        	fillBoard(board, cols, rows);
        	addObjects(board, potions, "potion");
        	addObjects(board, restorers, "healer");
        	addObjects(board, peacemakers, "healer");
        	addWeapons(board, weapons);
        	addWater(board, water, cols, rows);
        	addMagicCrystal(board, magic_crystal);
        	addWarriors(board, warriors);
        	printBoard(board, cols, rows);
        }
       	if (Warrior_23688963.counter > 0) // so that an empty line is not printed unecessarily
       		printStatistics(warriors); 	  // (like in testsFirstDemo/input21.txt)
        // samples2/input12.txt and testsFirstDemo/input21.txt are inconsistent TODO
       	
       	
        for (int iteration=0; iteration<iterations && Warrior_23688963.counter > 1; ++iteration) {	
        	setInitialValues(warriors); // record inital defence and offence for battle
        	
        	crystalizeWarriors(warriors, magic_crystal, cols, rows);
        	hydrateWarriors(warriors, water, cols, rows);
        	restoreWarriors(warriors, restorers, cols, rows);
        	defendWarriors(warriors);        	
        	ableWarriors(warriors);
        	poisonWarriors(warriors, potions, cols, rows);
        	protectWarriors(warriors, peacemakers, cols, rows);
        	battle(warriors, cols, rows);
        	ageWarriors(warriors); // TODO what other attributes should be updated
        	loseWarriors(warriors);
        	weaponizeWarriors(warriors, weapons);       	
        	// Move warriors
        	moveWarriors(warriors, iteration, cols, rows);
        	// Update water position
	        water = updateWater(water, cols, rows);
	        
	        validation3(warriors, cols, rows);
	                	
	        if (Warrior_23688963.counter > 1) {
	        	if (outputVersion == 1) {
		            // warrior statistics with visualisation mode
		        	fillBoard(board, cols, rows);
		        	
		        	// TODO add rest of the objects
		        	addObjects(board, potions, "potion");
		        	addObjects(board, restorers, "healer");
		        	addObjects(board, peacemakers, "healer");
		        	addWeapons(board, weapons);
		        	addWater(board, water, cols, rows);
		        	addMagicCrystal(board, magic_crystal);
		        	addWarriors(board, warriors);
		        	printBoard(board, cols, rows);
		        }
	        	// Note: Statistics should be printed before the game begins 
	        	// even if a warrior has won
	        	printStatistics(warriors); 
	        }
        }
        
        if (Warrior_23688963.counter == 1)
        	StdOut.println("A warrior has been proven victor!");
        else if (Warrior_23688963.counter == 0) 
        	StdOut.println("No warriors are left!");

    }

}
