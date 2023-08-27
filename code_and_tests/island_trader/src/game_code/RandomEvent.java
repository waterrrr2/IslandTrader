package game_code;

import java.util.Random;

/**
 * The random events of the game.
 * @author nzoli
 *
 */
public class RandomEvent {
	
	/**
	 * The type of event this is.
	 */
	private String type = new String();
	/**
	 * How severe the event is - different based on type
	 */
	private int magnitude = 0;
	/**
	 * The combat result of the player
	 */
	private int combat = 0;
	/**
	 * The ship that the random event is affecting
	 */
	private Ship ship;
	/**
	 * The GameStats instance that the game is working with
	 */
	private GameStats stats;
	/**
	 * A random number generator
	 */
	private Random rng = new Random();
	
	/**
	 * Constructor for the class.
	 * Randomly determines type with equal chance.
	 * @param ship The ship that the random event is affecting
	 * @param stats The GmaeStats instance that the game is working with.
	 */
	public RandomEvent(Ship ship, GameStats stats) {
		//Determine type randomly.
		this.ship = ship;
		this.stats = stats;
		determineType();
	}
	
	/**
	 * Randomly determines the type of event, with equal chances.
	 * Types are Pirate attack, Bad Weather, and Marooned Sailors.
	 */
	private void determineType() {
		switch(rng.nextInt(3)) {
		case 0:
			//Pirates!
			type = "Pirates!";
			magnitude = 3;
			break;
		case 1:
			//Weather!
			type = "A Terrible Storm!";
			magnitude = rng.nextInt(100);
			break;
		case 2:
			//Sailors!
			type = "Marooned Sailors!";
			magnitude = rng.nextInt(100) + 50;
			break;
		}
	}
	
	/**
	 * Setter method for the type.
	 * Should only be used in tests, or to force a certain type of event.
	 * @param type The type of event, which must be in string form and be one of the following:
	 * "Pirates!",
	 * "A Terrible Storm!", or 
	 * "Marooned Sailors!"
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Setter method for magnitude.
	 * Should only be used for tests.
	 * @param mag The magnitude of the event.
	 * For pirates a 0 will always let the player win and 10 will always make the player lose
	 * For Weather this is the amount of damage the ship takes
	 * For Sailors this is how much money they'll give you
	 * 
	 */
	public void setMagnitude(int mag) {
		magnitude = mag;
	}
	
	/**
	 * Activates the event.
	 * Requires no parameters as this method handles everything else from here until the event ends.
	 * @return Returns False if the game should continue, and True if pirates have killed the player.
	 * So it should be used like
	 * if (activateEvent) {
	 * 		GameOver()
	 * }
	 */
	public boolean activateEvent() {
		boolean outcome = false;
		switch(type) {
		case "Pirates!":
			System.out.println("Pirates are about to raid your ship!");
			combat = rng.nextInt(6) + 1 + ship.getFirePower();
			if (combat >= magnitude) {
				System.out.println("You rolled a "+combat +". You have defeated the pirates in battle!");
				//Event Over
			} else {
				System.out.println("You rolled a " + combat + ". The pirates have defeated you."
						+ "\nThey have stolen all your goods!");
				String judgement = rob();
				if (judgement == "Spared") {
					System.out.println("The pirates are satisfied with your cargo and spare you!\n");
					//Event Over
				} else {
					System.out.println("The pirates are not satisfied with this - walk the plank!\n");
					//GAME OVER
					outcome = true;
				}
			}
			break;
		case "A Terrible Storm!":
			System.out.println("You sail through a terrible storm!");
			ship.setEndurance(magnitude);
			System.out.println("You've taken " + magnitude + " points of damage!\n");
			//Event Over
			break;
		case "Marooned Sailors!":
			System.out.println("You come across marooned sailors!\n");
			stats.changeMoney(magnitude);
			System.out.println("They give you " + magnitude + "dollars as a reward for saving them!\n");
			//Event Over
			break;
		}
		return outcome;
	}
	
	/**
	 * The pirate method for robbing the ship of all its contents.
	 * @return Returns a string indicating whether or not the pirates spare the player.
	 */
	private String rob() {
		int spoils = 0;
		for (Good good : ship.cargo.keySet()) {
			int price = good.getPrice();
			int quantity = ship.cargo.get(good);
			spoils += (price * quantity);
			ship.cargo.put(good, 0);
		}
		if (spoils >= 100) {
			return "Spared";
		}
		else {
			return "GAME OVER.";
		}
	}
	
	/**
	 * Gets the string-version type of event this is
	 * @return The type of event this is, being one of
	 * Pirates!
	 * A Terrible Storm!
	 * Marooned Sailors!
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the combat roll of the player
	 * @return The int version of the combat roll the player got
	 */
	public int getCombat() {
		return combat;
	}
	
	/**
	 * Gets the magnitude of the event
	 * @return The magnitude of the current random event
	 */
	public int getMagnitude() {
		return magnitude;
	}

}
