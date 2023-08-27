package game_code;

/**
 * The ship upgrades that the player can purchase for their ship throughout the game.
 * @author nzoli
 *
 */
public class Upgrade extends Item {
	
	/**
	 * The description of the upgrade that will be returned by this class
	 */
	private String description;
	
	/**
	 * Constructor for the Upgrade class
	 * Note: Actual application of the upgrade is handled in the ship class because it modifies ship properties.
	 * @param name Name of the upgrade
	 * @param value Price of the upgrade - How much it costs
	 * @param desc A description of what the upgrade does
	 */
	public Upgrade(String name, int value, String desc) {
		super(name, value);
		description = desc;
	}
	
	/**
	 * Returns the description of the upgrade
	 * @return description The description of the upgrade, in String form.
	 */
	public String getDescription() {
		return description;
	}

}
