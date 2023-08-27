package game_code;

import java.util.Hashtable;

import com.sun.jdi.InvalidTypeException;

/**
 * A class that holds most of the game's data and info.
 * Holds things like money, time, the economy and so on.
 * @author nzoli
 *
 */
public class GameStats {
	
	/**
	 * The collection of goods that the game runs with.
	 * Should not be changed during the course of the game.
	 */
	private static Hashtable<String, Good> ECONOMY = new Hashtable<String, Good>();
	/**
	 * The maximum amount of time left in the game.
	 * The player will select this.
	 */
	private int time;
	/**
	 * The amount of time remaining in the game before it ends.
	 */
	private double timeLeft;
	/**
	 * The amount of money that the player has for paying wages and buying items.
	 * has a default starting amount.
	 */
	private int money = 500;
	/**
	 * The trader's name.
	 * The player should pick this at the start of the game.
	 */
	private String name;
	/**
	 * Very similar to the ship's cargo HashTable, but this is used to keep track of what goods have been sold.
	 * We must do this, as it is a project requirement.
	 * 
	 * This is private because we only need to either get it, or increase a value.
	 * In other words, our needs for this are much more constrained.
	 */
	private Hashtable<Good, Integer> goodsSold = new Hashtable<Good, Integer>();
	/**
	 * The profit made by the player, displayed at the end of the game.
	 */
	private int profit = 0;
	
	
	/**
	 * Constructor for the class.
	 * Also makes the economy.
	 * Since the economy is static, it only needs to be made once.
	 */
	public GameStats() {
		//The brackets aren't necessary but they give me peace of mind
		if (ECONOMY.isEmpty()) {
		createEconomy();
		}
		//Create our HashTable for keeping track of sold goods.
		createGoodsSold(ECONOMY);
	}
	/**
	 * Creates the game's economy, a collection of Good instances
	 */
	private void createEconomy() {
		// Set up the economy
		try {
		//Fruit Island
		Good banana = new Good("Banana", 3, 1);
		ECONOMY.put("Banana", banana);
		Good coconut = new Good("Coconut", 5, 2);
		ECONOMY.put("Coconut", coconut);
		Good apple = new Good("Apple", 8, 3);
		ECONOMY.put("Apple", apple);
		Good orange = new Good("Orange", 10, 3);
		ECONOMY.put("Orange", orange);
		//Luxury Island
		Good sugar = new Good("Sugar", 10, 2);
		ECONOMY.put("Sugar", sugar);
		Good spice = new Good("Spice", 20, 5);
		ECONOMY.put("Spice", spice);
		Good silk = new Good("Silk", 30, 10);
		ECONOMY.put("Silk", silk);
		Good tea = new Good("Tea", 50, 20);
		ECONOMY.put("Tea", tea);
		//Tech Island
		Good gear = new Good("Gear", 50, 15);
		ECONOMY.put("Gear", gear);
		Good compass = new Good("Compass", 60, 20);
		ECONOMY.put("Compass", compass);
		Good clock = new Good("Clock", 80, 30);
		ECONOMY.put("Clock", clock);
		Good bitcoin = new Good("Bitcoin", 100, 25);
		ECONOMY.put("Bitcoin", bitcoin);
		Good gold = new Good("Gold", 200, 40);
		//Forge Island
		ECONOMY.put("Gold", gold);
		Good iron = new Good("Iron", 20, 7);
		ECONOMY.put("Iron", iron);
		Good steel = new Good("Steel", 80, 25);
		ECONOMY.put("Steel", steel);
		Good copper = new Good("Copper", 120, 40);
		ECONOMY.put("Copper", copper);
		//Pirate Island
		Good gunpowder = new Good("Gunpowder", 150, 50);
		ECONOMY.put("Gunpowder", gunpowder);
		Good doubloon = new Good("Doubloon", 500, 100);
		ECONOMY.put("Doubloon", doubloon);
		Good grog = new Good("Grog", 50, 20);
		ECONOMY.put("Grog", grog);
		Good eyepatch = new Good("Eyepatch", 10, 2);
		ECONOMY.put("Eyepatch", eyepatch); 

		} catch (InvalidTypeException e) {
			System.out.println("One of these goods has an invalid name!");
			e.printStackTrace();
		}
	}
	
	/**
	 * A small redundancy as Ship has the same code.
	 * Creates the goodsSold HashSet.
	 * I could possibly make a different function that returned the HashTable this generates/modifies
	 * But that would complicate the code more than having this duplicate code here.
	 * @param ECONOMY the economy that the game is working with.
	 */
	private void createGoodsSold(Hashtable<String, Good> ECONOMY) {
		for (Good product : ECONOMY.values()) {
			goodsSold.put(product, 0);
		}
	}
	
	/**
	 * Adds the specified quantity to the amount of that Good sold
	 * @param product the type of Good that is being sold.
	 * @param quantity How many of it is being sold.
	 */
	public void recordSale(Good product, int quantity) {
		int oldQuantity = goodsSold.get(product);
		goodsSold.put(product, (oldQuantity + quantity));
		profit += product.getPrice()*2*quantity;
		
	}
	
	/**
	 * gets the goodsSold hashtable.
	 * @return The Hashtable containing the goods sold and how much of them is sold.
	 */
	public Hashtable<Good, Integer> getGoodsSold() {
		return goodsSold;
	}
	
	/**
	 * Changes the money by the specified amount.
	 * @param amount Increases money by amount. To reduce money, make this negative.
	 */
	public void changeMoney(int amount) {
		money += amount;
	}
	
	/**
	 * gets the amount of money the player has.
	 * @return The player's money, in integer form.
	 */
	public int getMoney() {
		return money;
	}
	
	/**
	 * Gets the economy
	 * @return The game's economy, in the form of a HashSet containing 'Good' instances
	 */
	public static Hashtable<String, Good> getEconomy() {
		return ECONOMY;
	}
	
	/**
	 * Gets a specific Good instance, for access, from the game's economy.
	 * @param name The name of the good. And must be a string.
	 * @return The desired Good instance.
	 */
	public static Good getGood(String name) {
		return ECONOMY.get(name);
	}
	/**
	 * Gets the time - specifically the max amount of days in the game
	 * @return The max numbers of days in the game. In integer form.
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Gets how much time the game has left before it ends.
	 * @return The number of days left in the game before it ends. In double form.
	 */
	public double getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Sets the maximum amount of time for the game.
	 * Also sets the timeLeft to the same value.
	 * Should only be used once - during the start of a game.
	 * @param days The maximum number of days in the game.
	 */
	public void setTime(int days) {
		time = days;
		// This may/may not work - days is an int but setTimeleft needs a double. Not sure is casting is automatic.
		setTimeLeft(days);
	}
	
	/**
	 * Sets the time left before the game ends to the given double.
	 * Should not be used to change the time - use advanceTime instead.
	 * @param days The number of days left before the game ends.
	 */
	private void setTimeLeft(double days) {
		timeLeft = days;
	}
	
	/**
	 * reduces timeLeft by the given amount.
	 * So a positive value indicates that days have passed.
	 * A negative value indicates time is flowing backwards.
	 * @param days The number of days that have passed.
	 */
	public void advanceTime(double days) {
		timeLeft -= days;
	}
	
	/**
	 * gets the trader's name
	 * @return name The trader's name. In string form.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the profit made by the player
	 * @return profit The profit made by the player, in int form.
	 */
	public int getProfit() {
		return profit;
	}
	
	/**
	 * Sets the name of the trader.
	 * Should only be done at the start of the game.
	 * See characterCheck for valid name conditions.
	 * @param text The name of the trader-to-be. Should be a string.
	 * @throws IllegalArgumentException Is thrown if the name is not valid.
	 */
	public void setname(String text) throws IllegalArgumentException {
		characterCheck(text);
		name = text;
	}
	
	/**
	 * Checks the string to see if all of its characters are valid.
	 * Only accepts names of 3 to 15 characters in length (inclusive),
	 * containing only the characters a-z, A-Z, and the whitespace.
	 * Two consecutive whitespaces are not allowed, however.
	 * Does nothing if it is valid.
	 * if invalid, throws an illegal argument exception.
	 * @param text The string that is being checked for validity.
	 * @throws IllegalArgumentException This is thrown if the string is the wrong length or contains illegal characters
	 */
	public static void characterCheck(String text) throws IllegalArgumentException {
		//Setting up characters for filtering purposes.
		char A = 'A';
		char Z = 'Z';
		char a = 'a';
		char z = 'z';
		char whitespace = ' ';
		char prev_char = 'a';
		
		if((text.length() < 3) || (text.length() > 15)) {
			throw new IllegalArgumentException("Names must be between 3 and 15 characters in length!");
		}
		
		char[] symbols = text.toCharArray();
		
		
		for (char ch: symbols) {
			if((A <= ch) && (ch <= Z)) {
				prev_char = ch;
				//This character is valid!
			}
			else if((a <= ch) && (ch <= z)) {
				prev_char = ch;
				//This character is valid!
			}
			else if(ch == whitespace) {
				//I'm intentionally nesting if statements here
				if(prev_char == whitespace) {
					throw new IllegalArgumentException("You cannot have two consecutive whitespaces in your name!");
				}
				else {
					prev_char = ch;
					//This character is valid... for now...
				}
			}
			else {
				throw new IllegalArgumentException("The character '" + ch + "' is not a valid character!");
			}
			
		}
	}

}
