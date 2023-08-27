package game_code;

/**
 * The item superclass, which covers both Upgrades and Goods
 * @author nzoli
 *
 */
public class Item {
	
	/**
	 * The (buying) price of the item. Can also be thought of as the base price.
	 */
	private int price;
	/**
	 * The name of the item
	 */
	private String name;
	
	/**
	 * Constructor for the Item class
	 * @param name The name of the item
	 * @param value The base/buying price of the item
	 */
	public Item(String name, int value) {
		this.name = name;
		price = value;
	}
	
	/**
	 * gets the price of the item
	 * @return The item's price. in integer form.
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * gets the name of the item.
	 * @return The item's name. in string form.
	 */
	public String getName() {
		return name;
	}
	
}
