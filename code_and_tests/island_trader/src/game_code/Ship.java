package game_code;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * The ship class, representing the ship that the player sails around in.
 * @author nzoli
 *
 */
public class Ship {
	/**
	 * The name of the ship
	 */
	private String name;
	/**
	 * The ship's physical state i.e. how damaged it is
	 */
	private int endurance = 0;
	/**
	 * How much weight the ship can carry
	 */
	private int capacity;
	/**
	 * How many crew members the ship has
	 */
	private int crew;
	/**
	 * The travel speed of the ship
	 */
	private int speed;
	/**
	 * The combat strength of the ship
	 */
	private int firepower = 0;
	/**
	 * The cargo that the ship is currently carrying
	 * Initialization of the hash table requires non-primitive data types
	 * So int is replaced with its wrapper class Integer - Good thing to remember if using this in the future!
	 * 
	 * Maps instances of Good to integers, representing how many of that good the ship contains.
	 * Capacity management should be done in the island's selling/buying methods - specifically the validPurchase method.
	 * Likewise, checking if there is enough of the item to sell should also be done in validPurchase.
	 * 
	 * This property is public due to the sheer frequency and variety of ways that we must access it.
	 */
	public Hashtable<Good, Integer> cargo = new Hashtable<Good, Integer>();
	/**
	 * The upgrades that the ship currently has
	 */
	private HashSet<Upgrade> upgrades = new HashSet<Upgrade>();
	/**
	 * The current location of the ship
	 */
	private Island location;
	
	
	/**
	 * The constructor for the Ship class. 
	 * As these values do not have defaults, this is the only way a ship should be initialized.
	 * Doubly so because this initiates a ship's cargo array!
	 * 
	 * 
	 * @param name The name of the ship
	 * @param capacity How much cargo weight the ship can carry
	 * @param crew How many crew members the ship has
	 * @param speed The travel speed of the ship
	 */
	public Ship(String name, int capacity,  int crew, int speed) {
		this.name = name;
		this.capacity = capacity;
		this.crew = crew;
		this.speed = speed;
		
		createCargo(GameStats.getEconomy());
	}
	
	/**
	 * Use a loop to iterate through a variable of GameEnvironment that holds all the goods.
	 * use that to then set up the cargo, and use that for the constructor.
	 * 
	 * Update the UML diagram accordingly.
	 * @param ECONOMY The economy that the ship works in: a set of goods it is able to store
	 */
	private void createCargo(Hashtable<String, Good> ECONOMY) {
		for (Good product : ECONOMY.values()) {
			cargo.put(product, 0);
		}
	}
	
	
	/**
	 * Gets the name of the ship.
	 * @return name of the ship, in string form.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Gets the endurance of the ship
	 * @return ship's endurance, in integer form.
	 */
	public int getEndurance() {
		return endurance;
	}
	
	/**
	 * Gets the cargo capacity of the ship
	 * @return ship's cargo capacity, in integer form.
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * increases the capacity by the given amount.
	 * @param amount Increases capacity by this amount. For reduction, make this negative.
	 */
	public void changeCapacity(int amount) {
		capacity += amount;
	}
	
	/**
	 * increases the quantity of the given Good by the given amount.
	 * @param product The Good instance you are increasing the quantity of
	 * @param quantity The quantity of goods you're adding. For reduction, make this negative.
	 */
	public void changeCargo(Good product, int quantity) {
		int oldQuantity = cargo.get(product);
		cargo.put(product, (oldQuantity + quantity));
	}
	
	
	/**
	 * Gets the number of crew members aboard the ship
	 * @return number of ship's crew members, in integer form.
	 */
	public int getCrew() {
		return crew;
	}
	
	/**
	 * Calculates and gets the cost for paying the crew one day's wage
	 * @return one day's wages for the entire crew, in integer form.
	 */
	public int getCrewCost() {
		//The actual cost isn't set in stone - feel free to suggest a tweak
		//Give me a heads up though so we don't end up with merge hell
		return crew;
	}
	
	/**
	 * gets the travel speed of the ship
	 * @return ship's travel speed, in integer form.
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * gets the ship's fire power. Will be zero by default, unless the ship possesses upgrades.
	 * This is the bonus the ship gets to its combat roll.
	 * @return ship's combat bonus, in integer form.
	 */
	public int getFirePower() {
		return firepower;
	}

	
	/**
	 * Gets the list of upgrades that the ship currently has.
	 * @return Upgrades the ship has active. In ArrayList Form.
	 */
	
	public HashSet<Upgrade> getUpgrades() {
		return upgrades;
	}
	
	
	/**
	 * Gets the current island the ship is docked at.
	 * @return The island that the ship is docked at - in Island class form.
	 */
	
	public Island getLocation() {
		return location;
	}
	
	
	/**
	 * sets the location that the island is docked at.
	 * Should be used after a ship has traveled/finished its journey.
	 * @param destination the Island (class) that the ship is currently docked at.
	 */
	
	public void setLocation(Island destination) {
		location = destination;
	}
	
	
	/**
	 * Adds an upgrade to the ship.
	 * This is done by modifying the ship's properties, which saves us having to use a bunch of if-statements elsewhere.
	 * 
	 * The upgrade will only be added after its effects have been applied, to ensure that only valid upgrades are stored.
	 * @param upgrade The upgrade to be added. Should be one of the five upgrades - in its class form.
	 */
	public void addUpgrade(Upgrade upgrade) {
		String up_name = upgrade.getName();
		switch(up_name) {
			
		case "Cannons":
			firepower += 2;
			upgrades.add(upgrade);
			break;
		case "Flame Decals":
			speed += 5;
			upgrades.add(upgrade);
			break;
		case "Robots":
			if (crew <= 5)
				crew = 0;
			else
				crew -= 5;
			upgrades.add(upgrade);
			break;
		case "Shelves":
			capacity += 100;
			upgrades.add(upgrade);
			break;
		case "THE TRADE-O-MATIC 5000":
			firepower += 2;
			speed += 5;
			capacity += 100;
			upgrades.add(upgrade);
			break;
		default:
			System.out.println("That upgrade isn't a real upgrade! Get a valid one!");
		
		}
	}
	
	
	/**
	 * sets the endurance of the ship - mainly used for damage.
	 * @param value the value of the ship's endurance. A higher value indicates a more damaged ship.
	 */
	public void setEndurance(int value) {
		endurance = value;
	}
	
	
	
	
}
