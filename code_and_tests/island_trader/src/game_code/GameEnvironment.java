package game_code;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * The game environment that actually, well, contains the game.
 * Note: not responsible for actually running the game, just containing everything within it.
 * @author nzoli
 *
 */
public class GameEnvironment {
	
	/**
	 * The ship that the player sails
	 */
	private Ship ship;
	/**
	 * The player's score.
	 */
	private int score = 0;
	/**
	 * The islands of the game, in a hashtable
	 * where the key is the island's name, and the value is the island object
	 */
	private Hashtable<String, Island> islands;
	/**
	 * An instance of GameStats, used for keeping track of the game's stats
	 */
	private GameStats stats = new GameStats();
	/**
	 * A monstrosity. A terrible creation. The design of a madman.
	 * 
	 * This is a hashtable of all the routes in the game.
	 * How it works is it maps Island instances, to another hashtable.
	 * THAT hashtable then maps island instances to ArrayLists.
	 * Those ArrayLists then contain all the routes associated with that entry.
	 * 
	 * To summarize, this maps
	 * Source island ---> Hashtable, which maps Destination island ---> ArrayList of routes to that destination.
	 * In short, this is Source --> Destination --> Routes
	 * 
	 * Does this have a HORRENDOUS implementation? Yes.
	 * Does this have an incredible amount of overlap considering its generation? Yes.
	 * Does the project spec say I have to create these here? Yes.
	 * 
	 * It does have constant time order for accessing entries, though.
	 * And this ALSO means we don't need to spend time/resources searching for routes with certain destinations/sources.
	 * And this implementation works because the project spec only wants us to tell the user all routes from
	 * our current island to an island we're viewing.
	 * We have both island instances.
	 * 
	 */
	private Hashtable<Island, Hashtable<Island, ArrayList<Route>>> ALLROUTES;
	
	
	/**
	 * The method to start the videogame.
	 * @param name The name that the player has selected
	 * @param ship The ship that the player has selected
	 * @param duration The duration of the game the player has selected
	 */
	public void beginGame(String name, Ship ship, int duration) {
		// I'm initializing it here so that whenever we start a new game, we get a new stats instance
		stats = new GameStats();
		stats.setname(name);
		//Set the ship
		this.ship = ship;
		//Set the game time
		stats.setTime(duration);
		//Create the islands
		//Initialized here for the same reason as stats
		islands = new Hashtable<String, Island>();
		createIslands();
		/*
		 * As part of the project spec, we have to create routes here.
		 * Normally, I would generate them on the fly, with some RNG to spice it up. 
		 * Instead I have created this monstrosity.
		 */
		ALLROUTES = oceanGenerator();
		/*
		 */
		ship.setLocation(islands.get("Fruit Island"));
	}
	
	/**
	 * Returns an 'ocean', or one 'instance' of ALLROUTES.
	 * To find out what that is, please read the javadoc for ALLROUTES.
	 * @return A very complex data set containing all routes in a collection of islands.
	 */
	private Hashtable<Island, Hashtable<Island, ArrayList<Route>>> oceanGenerator() {
		//Create the monstrosity that we will return
		Hashtable<Island, Hashtable<Island, ArrayList<Route>>> ocean = 
				new Hashtable<Island, Hashtable<Island, ArrayList<Route>>>();
		//Go through each island, and generate the ways there for it
		for (Island source : islands.values()) {
			Hashtable<Island, ArrayList<Route>> routesThere = destinationGenerator(source);
			//Add the ways there to the table
			ocean.put(source, routesThere);
		}
		return ocean;
	}
	
	/**
	 * generates a hashtable that maps an island(the destination) to an ArrayList of routes.
	 * @param source The source island, needed so that we don't make routes to itself.
	 * @return A hashtable mapping destination islands to an ArrayList of routes.
	 */
	private Hashtable<Island, ArrayList<Route>> destinationGenerator(Island source) {
		//Create the hashtable for each island -> arraylist and return it
		Hashtable<Island, ArrayList<Route>> routes = new Hashtable<Island, ArrayList<Route>>();
		/*
		 * There is two lines of this, one commented out.
		 * The one here generates three routes, and then has those routes be used for every island.
		 * The one in the for loop generates a new set of three loops for every island.
		 * The one here saves an order(n) amount of space.
		 * The other one increased variety massively.
		 * This is the uncommented one, because the routes don't have all that much room for variety.
		 */
		ArrayList<Route> travelOptions = routeGenerator();
		for (Island destination : islands.values()) {
			//Exclude the source island
			if (source != destination) {
				//get the routes there
				//ArrayList<Route> travelOptions = routeGenerator();
				//put it in the table
				routes.put(destination, travelOptions);
			}
		}
		return routes;
	}
	
	/**
	 * generates three routes in an ArrayList
	 * First entry is most risky.
	 * Second is medium.
	 * Last Entry is slowest.
	 * @return an ArrayList of routes.
	 */
	private ArrayList<Route> routeGenerator() {
		//Create the routes and return an arrayList
		Random rng = new Random();
		ArrayList<Route> travelOptions = new ArrayList<Route>();
		//Create three routes, each of varying length/risk
		//Fastest, but riskiest route
		int risk = rng.nextInt(2) + 4;
		int length = rng.nextInt(41) + 80;
		Route route = new Route(length, risk);
		travelOptions.add(route);
		//Mild spice levels of risk
		risk = rng.nextInt(2) + 2;
		length = rng.nextInt(41) + 120;
		route = new Route(length, risk);
		travelOptions.add(route);
		//The turtle route
		risk = rng.nextInt(2);
		length = rng.nextInt(41) + 160;
		route = new Route(length, risk);
		travelOptions.add(route);
		//With our ArrayList complete, return the list
		return travelOptions;
	}
	
	/**
	 * Since our islands are predetermined, this is just going to create all the islands 'by hand'.
	 */
	private void createIslands() {
		//Fruit Island
		Upgrade shelves = new Upgrade("Shelves", 100, "Increases your cargo capacity by 100kg.");
		String[] fruitsales = {"Banana", "Coconut", "Apple", "Orange"};
		String[] fruitbuys = {"Sugar", "Gear", "Gold", "Gunpowder"};
		islands.put("Fruit Island", makeOneIsland(shelves, fruitsales, fruitbuys, "Fruit Island"));
		//Luxury Island
		Upgrade flames = new Upgrade("Flame Decals", 150, "Increases your speed by 5.");
		String[] luxsales = {"Sugar", "Spice", "Silk", "Tea"};
		String[] luxbuys = {"Banana", "Compass", "Iron", "Doubloon"};
		islands.put("Luxury Island", makeOneIsland(flames, luxsales, luxbuys, "Luxury island"));
		//Tech Island
		Upgrade tradeO = new Upgrade("THE TRADE-O-MATIC 5000", 1000, 
				"Increases firepower by 2, capacity by 100 and speed by 5. The ultimate trading machine!");
		String[] techsales = {"Gear", "Compass", "Clock", "Bitcoin"};
		String[] techbuys = {"Coconut", "Spice", "Steel", "Grog"};
		islands.put("Tech Island", makeOneIsland(tradeO, techsales, techbuys, "Tech Island"));
		//Forge Island
		Upgrade cannons = new Upgrade("Cannons", 250, "Increases your firepower by 2");
		String[] forgesales = {"Gold", "Iron", "Steel", "Copper"};
		String[] forgebuys = {"Apple", "Silk", "Clock", "Eyepatch"};
		islands.put("Forge Island", makeOneIsland(cannons, forgesales, forgebuys, "Forge Island"));
		//Pirate Island
		Upgrade robots = new Upgrade("Robots", 200, "Replaces 5 crew members, reducing wage costs! "
				+ "Stolen from Tech Island");
		String[] pisales = {"Gunpowder", "Doubloon", "Grog", "Eyepatch"};
		String[] pibuys = {"Orange", "Tea", "Bitcoin", "Copper"};
		islands.put("Pirate Island", makeOneIsland(robots, pisales, pibuys, "Pirate Island"));
		
	}
	
	/**
	 * Creates an individual island using simpler-to-make arrays.
	 * Note: helper function for createIslands, should not be used by itself.
	 * @param upgrade An upgrade instance, that will be added to the island's ItemsSold array
	 * @param selling A string array of the names of the items (from economy) that the island sells
	 * @param buying A string array of the names of the items (from economy) that the island buys
	 * @param name The name of the island
	 * @return The island object, in all its sandy glory.
	 */
	private Island makeOneIsland(Upgrade upgrade, String[] selling, String[] buying, String name) {
		Hashtable<String, Good> ECONOMY = GameStats.getEconomy();
		//Construct the items sold array
		ArrayList<Item> itemsSold = new ArrayList<Item>();
		itemsSold.add(upgrade);
		for(String good: selling) {
			itemsSold.add(ECONOMY.get(good));
		}
		//Construct the items bought array
		ArrayList<Good> itemsBought = new ArrayList<Good>();
		for(String good: buying) {
			itemsBought.add(ECONOMY.get(good));
		}
		Island thisIsland = new Island(name, itemsSold, itemsBought);
		return thisIsland;
	}
	
	/**
	 * Method for creating a ship from one of the four types given to the player upon game start.
	 * @param selection The number of the ship being made
	 * @param name The name of the ship being created
	 * @return The ship object.
	 */
	public static Ship initializeShip(int selection, String name) {
		//Switch statement to initialize the ship.
		Ship vessel;
		switch(selection) {
		case 1:
			vessel = new Ship(name, 1000, 40, 10);
			break;
		case 2:
			vessel = new Ship(name, 600, 40, 30);
			break;
		case 3:
			vessel = new Ship(name, 600, 15, 10);
			break;
		case 4:
			vessel = new Ship(name, 400, 20, 30);
			break;
		default:
			/*
			 * This code shouldn't ever end up actually being ran
			 * However the compiler complains if there isn't an absolute guarantee that
			 * vessel will be initialized.
			 * Also if there's a horrendous bug this will stop us from not being able to launch the game.
			 */
			vessel = new Ship("Flying Dutchman", 1000, 0, 30);
		}
		return vessel;
	}
	
	/**
	 * Returns the ship that the game is using
	 * @return The player's Ship object
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 * Returns the current score
	 * maybe score should go into gamestats
	 * @return The current score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * gets the islands hashtable
	 * @return The hashtable of islands, mapping Island name to island instance
	 */
	public Hashtable<String, Island> getIslands() {
		return islands;
	}
	
	/**
	 * Returns the current gamestats instance.
	 * @return The stat-tracking GameStats instance used in the game
	 */
	public GameStats getStats() {
		return stats;
	}
	
	/**
	 * Sets the score of the player
	 * @param value The player's score
	 */
	public void setScore(int value) {
		this.score = value;
	}
	
	/**
	 * Gets the ALLROUTES complex hashtable.
	 * For how to use ALLROUTES, please see its javadoc entry.
	 * @return Returns ALLROUTES in its hashtable form.
	 */
	public Hashtable<Island, Hashtable<Island, ArrayList<Route>>> getALLROUTES() {
		return ALLROUTES;
	}
	
}
