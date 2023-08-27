package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Enumeration;
import java.util.Hashtable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import game_code.GameStats;
import game_code.Good;
import game_code.Ship;
import game_code.Upgrade;
import game_code.descriptionMaker;

/**
 * Tests on the Ship class
 */
class ShipTest {
	/**
	 *The Economy that the island is working with, used to get the goods selling/wanted
	 */
	private static Hashtable<String, Good> ECONOMY;
	/**
	 * The current stats of the game, contains the economy
	 */
	static GameStats capitalism = new GameStats();
	
	/**
	 *setting up the economy of the game
	 *@throws Exception An error message will be thrown when occurred 
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Set up the stat tracker and economy
		ECONOMY = GameStats.getEconomy();
		
	}
	
	/**
	 *Tests to check that the class initializes properly and that Cargo has all 0's.
	 */
	@Test
	void innitTest() {
		Ship waka = new Ship("Waka", 10, 10, 10);
		for(Enumeration<Integer> quantity = waka.cargo.elements(); quantity.hasMoreElements();) {
			assertEquals(quantity.nextElement(), 0);
		
		}
		
	}
	
	/**
	 *Tests to see if upgrades work correctly.
	 *Upgrade stacking should be handled in the Island.validPurchase method.
	 */
	@Test
	void upgradeTest() {
		Ship waka = new Ship("Waka", 10, 10, 10);
		Upgrade cannons = new Upgrade("Cannons", 50, "BOOM");
		Upgrade flames = new Upgrade("Flame Decals", 50, "BOOM");
		Upgrade robots = new Upgrade("Robots", 50, "BOOM");
		Upgrade shelves = new Upgrade("Shelves", 50, "BOOM");
		Upgrade trade_o = new Upgrade("THE TRADE-O-MATIC 5000", 50, "BOOM");
		
		waka.addUpgrade(cannons);
		waka.addUpgrade(flames);
		waka.addUpgrade(robots);
		waka.addUpgrade(shelves);
		assertEquals(waka.getFirePower(), 2);
		assertEquals(waka.getSpeed(), 15);
		assertEquals(waka.getCrew(), 5);
		assertEquals(waka.getCapacity(), 110);
		waka.addUpgrade(trade_o);
		assertEquals(waka.getFirePower(), 4);
		assertEquals(waka.getSpeed(), 20);
		assertEquals(waka.getCapacity(), 210);		
	}
	
	/**
	 *Tests the description of the ship.
	 */
	@Test
	void descriptionTest() {
		Ship waka = new Ship("Waka", 10, 10, 10);
		descriptionMaker describer = new descriptionMaker(waka, capitalism);
		System.out.println(describer.describeShip());
	}

}
