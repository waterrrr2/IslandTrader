package game_code;

import java.util.Set;

import com.sun.jdi.InvalidTypeException;

/**
 * The Good subclass of item - for goods that the player can sell and buy.
 * @author nzoli
 *
 */
public class Good extends Item {
	
	/**
	 * The cargo weight/size of the item
	 */
	private int size;
	/**
	 * The set of valid goods. This set is not mutable.
	 */
	private static Set<String> validGoods = Set.<String>of("Banana", "Coconut", "Apple", "Orange",
															"Sugar", "Spice", "Silk", "Tea",
															"Gear", "Compass", "Clock", "Bitcoin",
															"Gold", "Iron", "Steel", "Copper",
															"Gunpowder", "Doubloon", "Grog", "Eyepatch");
	
	/**
	 * Constructor for the Good, but altered to force its name to be one of the valid goods.
	 * @param name Name of the good - must be one of the strings within validGoods
	 * @param value Price of the good.
	 * @param weight how much weight/space the good occupies.
	 * @throws InvalidTypeException Happens if the name of the good is not valid
	 */
	public Good(String name, int value, int weight) throws InvalidTypeException {
		super(name, value);
		if (!(validGoods.contains(name))) {
			throw new InvalidTypeException("The name of the good is not valid! Valid goods are found in Good.validGoods");
		}
		else {
			size = weight;
		}
	}
	
	/**
	 * Gets the size of the item.
	 * @return The size of the item. In integer form.
	 */
	public int getSize() {
		return size;
	}
}
