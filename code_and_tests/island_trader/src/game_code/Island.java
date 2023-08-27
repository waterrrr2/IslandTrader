package game_code;


import java.util.ArrayList;

/**
 * The Island class, representing the islands the player can sail to.
 * @author Klai
 */
public class Island{
	
	/**
	 * the name of the island
	 */
	private String name;
	/**
	 * items the island is selling
	 */
	private ArrayList<Item> itemsSold = new ArrayList<Item>();
	/**
	 * items the island wants to buy
	 */
	private ArrayList<Good> itemsBought = new ArrayList<Good>();
	
	/**
	 * The constructor of the Island class.
	 * Has default values.
	 * @param name The name of the island
	 * @param itemsSold The items being sold on the island
	 * @param itemsBought The items the island is willing to buy
	 */
	public Island(String name, ArrayList<Item> itemsSold, ArrayList<Good> itemsBought) {
		this.name = name;
		this.itemsSold = itemsSold;
		this.itemsBought = itemsBought;
		
	}
	
	/**
	 * Gets the name of the island.
	 * @return the name of the island, in String form.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the items available on the island
	 * @return the items being sold, in ArrayList form.
	 */
	public ArrayList<Item> getItemsSold() {
		
		return itemsSold;
	}
	
	/**
	 * Gets the items the island wants to buy.
	 * @return the bought items, in ArrayList form.
	 */
	public ArrayList<Good> getItemsBought() {
		
		return itemsBought;
	}
	
	/**
	 * * Buying an item from the island
	 * Adds an item to Cargo if conditions are met.
	 * @param ship The player's ship
	 * @param product The Good instance being purchased
	 * @param game The current stats of the game(used to get money)
	 * @param quantity The quantity of the item being purchased
	 */
	public void buyItem(Ship ship, Item product, GameStats game, int quantity) {
		if (this.itemsSold.contains(product)){
		
			if (validPurchase(ship, game, product, quantity)) {
				game.changeMoney(-(product.getPrice()*quantity));
				//if the item's a good - add it to the cargo
				if (product.getClass() == Good.class) {
				ship.changeCargo((Good) product, quantity);
				
				ship.changeCapacity(-(((Good) product).getSize()*quantity));
				//otherwise, it must be an upgrade!
				} else {
					//Apply the upgrade!
					ship.addUpgrade((Upgrade) product);
				}
				System.out.println("Purchase Success!");
			}
		}
	}
		
	
	/**
	 * * Selling goods to the island
	 * sells the good in cargo, increasing Capacity and money, then records the sale.
	 * @param ship The player's ship
	 * @param product The Good instance being purchased
	 * @param game The current stats of the game(used to get money)
	 * @param quantity The quantity of the item being purchased
	 */
	public void sellItem(Ship ship,Good product, GameStats game, int quantity) {
		if (this.itemsBought.contains(product)){
			if(ship.cargo.get(product) >= quantity) {
				ship.changeCargo(product, -quantity);
				ship.changeCapacity(product.getSize()*quantity);
				game.changeMoney(product.getPrice()*quantity);
				game.recordSale(product, quantity);
				System.out.println("Item Sold!");
			} else {
				System.out.println("You do not have enough of this good.");
			}
		}
	}
	
	/**
	 * Checks if the purchase is valid
	 * @param buyer The buyer of the item(player)
	 * @param game The GameStats class, used to get the amount of money the player has
	 * @param product The Item being purchased
	 * @param quantity The number of Good being purchased
	 * @return if the purchase is valid, in boolean form
	 */
	public boolean validPurchase(Ship buyer, GameStats game, Item product, int quantity) {
		//If you're actually buying anything of it
		if (quantity >=0) {
			//If you have enough money to buy it
			if(game.getMoney() >= product.getPrice()*quantity) {
				//If it's an upgrade, or you have enough room
				if(product.getClass() == Upgrade.class || (buyer.getCapacity() >= ((Good) product).getSize()*quantity)) {
					return true;
					}
				}
		}
		System.out.println("Invalid purchase for " +product.getName());
		return false;
		
	}
}






