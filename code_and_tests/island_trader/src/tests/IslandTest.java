package tests;

import static org.junit.jupiter.api.Assertions.*;  

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sun.jdi.InvalidTypeException;

import game_code.Item;
import game_code.Good;
import game_code.Ship;
import game_code.Upgrade;
import game_code.descriptionMaker;
import game_code.Island;
import game_code.GameStats;
import game_code.Route;

/**
 * Tests on the Island class
 */
class IslandTest{
	/**
	 * The Economy that the island is working with, used to get the goods selling/wanted
	 */
	private static Hashtable<String, Good> ECONOMY;
	/**
	 * The current stats of the game, contains the economy
	 */
	static GameStats stat = new GameStats();
	
	/**
	 *setting up the economy of the game
	 *@throws Exception An error message will be thrown when occurred 
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Set up the economy
		ECONOMY = stat.getEconomy();
	}
	
	/** 
	 *Tests to check that the name and goods being sold/wanting to buy is correct.
	 */
	@Test
	void innitTest() {
		ArrayList<Item> Selling = new ArrayList();
		ArrayList<Good> Buying = new ArrayList();
		Selling.add(ECONOMY.get("Apple"));
		Selling.add(ECONOMY.get("Banana"));
		Selling.add(ECONOMY.get("Coconut"));
		Selling.add(ECONOMY.get("Orange"));
		Buying.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Spice"));
		Buying.add(ECONOMY.get("Silk"));
		Buying.add(ECONOMY.get("Tea"));
		Island fruit = new Island("Fruit",Selling,Buying);
		System.out.println(Selling);
		assertEquals(fruit.getName(), "Fruit");
		assertEquals(fruit.getItemsSold(),Selling);
		assertEquals(fruit.getItemsBought(),Buying);
	}
	
	/**
	 *Testing validPurchase method
	 *@throws InvalidTypeException An error message will be thrown when the variable type is incorrect
	 */
	@Test
	void validPurchaseTest() throws InvalidTypeException {
		
		ArrayList<Item> Selling = new ArrayList();
		ArrayList<Good> Buying = new ArrayList();
		Selling.add(ECONOMY.get("Apple"));
		Selling.add(ECONOMY.get("Banana"));
		Selling.add(ECONOMY.get("Coconut"));
		Selling.add(ECONOMY.get("Orange"));
		Buying.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Spice"));
		Buying.add(ECONOMY.get("Silk"));
		Buying.add(ECONOMY.get("Tea"));
		Island fruit = new Island("Fruit",Selling,Buying);
		
		GameStats game = new GameStats();
		Ship waka = new Ship("Waka", 100, 10, 10);
		
		assertEquals(fruit.validPurchase(waka,game,ECONOMY.get("Apple"),1),true);
		assertEquals(fruit.validPurchase(waka,game,ECONOMY.get("Apple"),34),false);
		assertEquals(fruit.validPurchase(waka,game,ECONOMY.get("Gold"),2),true);
		assertEquals(fruit.validPurchase(waka,game,ECONOMY.get("Gold"),3),false);
	}
	
	/** 
	 *Testing buyItem method
	 */
	@Test
	void buyItemTest() {
		ArrayList<Item> Selling = new ArrayList();
		ArrayList<Good> Buying = new ArrayList();
		Selling.add(ECONOMY.get("Apple"));
		Selling.add(ECONOMY.get("Banana"));
		Selling.add(ECONOMY.get("Coconut"));
		Selling.add(ECONOMY.get("Orange"));
		Buying.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Spice"));
		Buying.add(ECONOMY.get("Silk"));
		Buying.add(ECONOMY.get("Tea"));
		Island fruit = new Island("Fruit",Selling,Buying);
		
		GameStats game = new GameStats();
		Ship waka = new Ship("Waka", 150, 10, 10);
		
		fruit.buyItem(waka,ECONOMY.get("Apple"),game, 5);
		assertEquals(waka.cargo.get(ECONOMY.get("Apple")),5);
		assertEquals(game.getMoney(),460);
		assertEquals(waka.getCapacity(),135);
		
		fruit.buyItem(waka, ECONOMY.get("Gold"), game, 1);
		assertEquals(game.getMoney(),460);	
	}
	
	/** 
	 *Testing sellItem method
	 */
	@Test
	 void sellItemTest() {
		ArrayList<Item> Selling = new ArrayList();
		ArrayList<Good> Buying = new ArrayList();
		Selling.add(ECONOMY.get("Apple"));
		Selling.add(ECONOMY.get("Banana"));
		Selling.add(ECONOMY.get("Coconut"));
		Selling.add(ECONOMY.get("Orange"));
		Selling.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Spice"));
		Buying.add(ECONOMY.get("Silk"));
		Buying.add(ECONOMY.get("Tea"));
		Island fruit = new Island("Fruit",Selling,Buying);
		
		GameStats game = new GameStats();
		Ship waka = new Ship("Waka", 150, 10, 10);
		fruit.buyItem(waka,ECONOMY.get("Sugar"),game, 5);
		assertEquals(waka.cargo.get(ECONOMY.get("Sugar")),5);
		assertEquals(game.getMoney(),450);
		assertEquals(waka.getCapacity(),140);
		
		fruit.sellItem(waka, ECONOMY.get("Sugar"), game, 3);
		assertEquals(waka.cargo.get(ECONOMY.get("Sugar")),2);
		assertEquals(game.getMoney(),480);
		assertEquals(waka.getCapacity(),146);
		
	}
	
	/**
	 *Testing the describe class on the aspects of the island
	 */
	@Test
	void describeTest() {
		
		ArrayList<Item> Selling = new ArrayList();
		ArrayList<Good> Buying = new ArrayList();
		Upgrade shelves = new Upgrade("Shelves", 100, "Increases your cargo capacity by 100kg.");
		Selling.add(ECONOMY.get("Apple"));
		Selling.add(ECONOMY.get("Banana"));
		Selling.add(ECONOMY.get("Coconut"));
		Selling.add(ECONOMY.get("Orange"));
		Selling.add(ECONOMY.get("Sugar"));
		Selling.add(shelves);
		Buying.add(ECONOMY.get("Sugar"));
		Buying.add(ECONOMY.get("Spice"));
		Buying.add(ECONOMY.get("Silk"));
		Buying.add(ECONOMY.get("Tea"));
		Island fruit = new Island("Fruit Island",Selling,Buying);
		
		GameStats game = new GameStats();
		Ship waka = new Ship("Waka", 150, 10, 10);
		descriptionMaker describe = new descriptionMaker(waka, stat);
		System.out.println(describe.describeIsland(fruit));
		System.out.println(describe.describeSold(fruit));
		System.out.println(describe.describeWant(fruit));
		Route route = new Route(1,5);
		System.out.println(describe.describeRoute(fruit, route));
	}
	
	
	
	
}