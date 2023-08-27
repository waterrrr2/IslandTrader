package game_code;

import java.util.Hashtable;

/**
 * A class that processes the raw information and turns that into descriptions/text that the player can read.
 * The constructor takes a ship object so that you only need to pass it the ship once.
 * @author nzoli
 *
 */
public class descriptionMaker {
	
	/**
	 * The ship that the descriptionMaker describes/describes aspects of
	 */
	Ship ship;
	/**
	 * The current status of the game being described.(time left, money left.etc)
	 */
	GameStats statTracker;
	
	
	
	/**
	 * Constructor for the class.
	 * Takes a ship during construction so that other description methods don't need arguments.
	 * Takes a GameStats instance for the same reason.
	 * @param ship The ship that the descriptionMaker will describe/describe aspects of
	 * @param stats GameStats The instance of GameStats that the game is using.
	 */
	public descriptionMaker(Ship ship, GameStats stats) {
		this.ship = ship;
		statTracker = stats;
	}
	
	/*
	 * This section is for general stuff
	 */
	/**
	 * Returns a string description of the player's financial status
	 * @return The money the player has. NOT followed by a newline.
	 */
	public String describeMoney() {
		return "You have " + statTracker.getMoney() + " dollars.\n";
	}
	
	/**
	 * Returns a string description of the remaining time left in the game.
	 * @return The time left in the game. NOT followed by a newline.
	 */
	public String describeTime() {
		return "Out of a total of " + statTracker.getTime() + " days, you have " 
				+ statTracker.getTimeLeft() + " days remaining.\n";
	}
	
	/*
	 * This section is for the ship parts
	 */
	/**
	 * Returns a string description of the ship - not including cargo contents.
	 * @return A string version of the description, formatted with newlines.
	 */
	public String describeShip() {
		String description = new String();
		if(ship.getName() != null) {
			String name = ship.getName();
			description += "This vessel's name is: " + name + ".\n";
		}
		String crew = Integer.toString(ship.getCrew());
		String capacity = Integer.toString(ship.getCapacity());
		String wages = Integer.toString(ship.getCrewCost());
		
		if (!ship.getUpgrades().isEmpty()) {
			String upgrades = upgradeCollector();
			description += "Has the current upgrades equipped:\n" + upgrades;
		}
		
		description += "The ship has a crew of " + crew + 
				" which costs " + wages + " gold pieces per day to sail.\n";
		description += "Has " + capacity + " kilograms of available cargo space.\n";
		
		
		return description;
		
	}
	
	/**
	 * Describes the physical state of the player's ship
	 * @return A string description of the current damage state of the ship
	 */
	public String describeEndurance() {
		String description = new String();
		if (ship.getEndurance() != 0) {
			String damage = Integer.toString(ship.getEndurance());
			description += "The ship has taken " + damage + 
					" points of damage, and costs the same amount to repair.\n";
		}else {
			description += "There is no damage to your ship.\n";
		}
		return description;
	}
	
	/**
	 * Private method for handling the upgrades part of describeShip.
	 * generally, should not be called by itself.
	 * @return A series of upgrades and their description, each followed by a newline character.
	 */
	private String upgradeCollector() {
		String upgradelist = new String();
		for (Upgrade upgrade : ship.getUpgrades()) {
			String name = upgrade.getName();
			String description = upgrade.getDescription();
			upgradelist += name + ": " + description + "\n";
		}
		return upgradelist;
	}
	
	/**
	 * returns a String describing the state of the ship's cargo hold.
	 * If there is 0 of a good stored in the ship, its entry will be omitted.
	 * Each good summary is printed on a newline
	 * @return The string description of the cargo, formatted with newline characters.
	 */
	public String describeCargo() {
		String description = new String();
		description += "Summary of the ship's cargo:\n";
		//Go through each product and give a summary
		for (Good product : ship.cargo.keySet()) {
			//Don't give a summary if we have nothing stored
			if (ship.cargo.get(product) != 0) {
				//Data gathering
				String name = product.getName();
				int weight = product.getSize();
				int price = product.getPrice();
				int quantity = ship.cargo.get(product);
				//String building
				description += "\n" + name + ": " + quantity + " currently stored." 
						+ "Each item weighs " + weight + " kilograms - with a total weight of "
						+ (weight * quantity) + ".\n"
						+ price + " dollars was paid for each item, with a total price paid of " 
						+ (price * quantity) + " dollars.\n";
			}
		}
		return description;
	}
	
	/**
	 * Describes the sales the player has made over the course of the game
	 * @return A string version of a 'trade manifest', showing each good sold, quantity, where, and for how much
	 */
	public String describeSales() {
		String description = new String();
		description += "Summary of the ship's sales:\n";
		Hashtable<Good, Integer> sales = statTracker.getGoodsSold();
		//Go through each product and give a summary
		for (Good product : sales.keySet()) {
			//Don't give a summary if we have nothing stored
			if (sales.get(product) != 0) {
				//Data gathering
				String name = product.getName();
				String location = new String();
				int price = product.getPrice();
				int quantity = sales.get(product);
				switch(name) {
				case "Sugar": case "Gear": case "Gold": case "Gunpowder":
					location = "Fruit Island";
					break;
				case "Banana": case "Compass": case "Iron": case "Doubloon":
					location = "Luxury Island";
					break;
				case "Coconut": case "Spice":  case "Steel": case "Grog":
					location = "Tech Island";
					break;
				case "Apple": case "Silk": case "Clock": case "Eyepatch":
					location = "Forge Island";
					break;
				case "Orange": case "Tea": case "Bitcoin": case "Copper":
					location = "Pirate Island";
					break;
				}
				
				//String building
				description += name + ": " + quantity + " sold, each for a price of "
						+ price + " dollars, totalling " + (price * quantity) + " dollars. -Sold on "+ location + "\n\n";
			}
		}
		return description;		
	}
	
	/**
	 * Returns the island which the good was sold at
	 * Finish this when we have decided which goods are sold where.
	 * Just use a switch statement with good names.
	 * @param good The good being determined
	 * @return The name of the island which buys the good.
	 */
	public String determineIsland(Good good) {
		String description = new String();
		switch(good.getName()) {
		
			case "Banana": case "Coconut": case "Apple": case "Orange":
				description += "Fruit Island\n";
				break;
			
			case "Sugar": case "Spice": case "Silk": case "Tea":
				description += "Luxury Island\n";
				break;
				
			case "Gear": case "Compass": case "Clock": case "Bitcoin":
				description += "Tech Island\n";
				break;
				
			case "Gold": case "Iron": case "Steel": case "Copper":
				description += "Forge Island\n";
				break;
				
			case "Gunpowder": case "Doubloon": case "Grog": case "Eyepatch":
				description += "Pirate Island\n";
				break;
		}
		
		return description;
	}
	
	/**
	 * Returns Where the route is leading to and the risk of triggering an event
	 * @param island The destination of the route
	 * @param route The route to the destination Island
	 * @return The string version of the destination, time it takes, and the risk of triggering an event
	 */
	public String describeRoute(Island island, Route route) {
		String description = new String();
		int length = route.getLength();
		int risk = route.getRisk();
		description += "This route will take " + length/ship.getSpeed() + " day to reach your destination. (RISK LEVEL " + risk +")\n";
		return description;
	}
	
	/**
	 * returns a string describing the goods sold on the island and the goods the island wants
	 * @param island The Island being described
	 * @return A String version of the name of the island.
	 */
	public String describeIsland(Island island) {
		String description = new String();
		
		description += island.getName() + "\n";
		return description;
	}
	
	/**
	 * returns a string describing the goods sold on the island
	 * @param island The Island being described
	 * @return A String version of the goods available.
	 */
	public String describeSold(Island island) {
		String description = new String();
		int num = 1;
		description += "\nGoods Avaliable(Cost): \n";
		for (Item item: island.getItemsSold()) {
			description += num + ")" + item.getName() + "($" + item.getPrice()+")";
			if (item.getClass() == Upgrade.class)
				description += ((Upgrade) item).getDescription();
			description += "\n";
			num += 1;
		}
		return description;
		
	}
	
	/**
	 * returns a string describing the goods the island wants
	 * @param island The Island being described
	 * @return A String version of the goods wanted.
	 */
	public String describeWant(Island island) {
		String description = new String();
		int num = 1;
		description += "\nGoods Wanted(Offer): \n";
		for(Good good: island.getItemsBought()) {
			description += num + ")" + good.getName() + "($" + good.getPrice() + ")\n";
			num += 1;
		}
		return description;
	}
}
