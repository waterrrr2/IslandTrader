package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import game_code.CommandInput;
import game_code.GameEnvironment;
import game_code.GameStats;
import game_code.Good;
import game_code.Island;
import game_code.Item;
import game_code.Ship;


/**
 * Tests on the GameEnvironment class
 */
class GameEnvTest {
	/**
	 * Tests to see if the ocean generates correctly and at the right size(s).
	 */
	@Test
	void oceanTest() {
		//A note for testing - you need to set System.in BEFORE making your scanner
		//This means setting it before making the GameEnvironment
		String userInput = String.format("David%s1%s25", System.lineSeparator(), System.lineSeparator());
		ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
		CommandInput.sc = new Scanner(bais);
		//Redirecting the standard output
		//We aren't DOING anything with the standard output for this, but we don't want it crowding the console.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);
		//Create the game environment and start the game
		GameEnvironment laboratory = new GameEnvironment();
		String name = CommandInput.chooseName();
		Ship ship = CommandInput.chooseShip(name);
		int duration = CommandInput.numberSelector(20, 50);
		laboratory.beginGame(name, ship, duration);
		//Get island objects
		Island fruitisland = laboratory.getIslands().get("Fruit Island");
		Island techisland = laboratory.getIslands().get("Tech Island");
		//Get the size of each 'layer'
		int firstLayer = laboratory.getALLROUTES().size();
		int secondLayer = laboratory.getALLROUTES().get(fruitisland).size();
		int thirdLayer = laboratory.getALLROUTES().get(fruitisland).get(techisland).size();
		//There should be five possible source islands
		assertEquals(5, firstLayer);
		//For each source island, there should be four possible destination islands
		assertEquals(4, secondLayer);
		//For each source-to-destination pair, there should be three routes.
		assertEquals(3, thirdLayer);
		CommandInput.sc.close();
	}
	
	/**
	 * Tests to see the shops on an island in the GameEnvironment have the right goods in the right order.
	 * If one is done correctly then the others are likely done correctly as well.
	 * 
	 */
	@Test
	void shopTest() {
		String userInput = String.format("David%s1%s25", System.lineSeparator(), System.lineSeparator());
		ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
		//Setting the scanner to look at this input stream and not System.in
		CommandInput.sc = new Scanner(bais);
		//Redirecting the standard output
		//We aren't DOING anything with the standard output for this, but we don't want it crowding the console.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);
		//Create the game environment and start the game
		GameEnvironment laboratory = new GameEnvironment();
		String name = CommandInput.chooseName();
		Ship ship = CommandInput.chooseShip(name);
		int duration = CommandInput.numberSelector(20, 50);
		laboratory.beginGame(name, ship, duration);
		Hashtable<String, Island> islands = laboratory.getIslands();
		Island fruitIsland = islands.get("Fruit Island");
		ArrayList<Item> itemsSold = fruitIsland.getItemsSold();
		ArrayList<Good> itemsBought = fruitIsland.getItemsBought();
		//Expected outputs
		
		ArrayList<Good> expectedBought = new ArrayList<Good>(); {
			expectedBought.add(GameStats.getGood("Sugar"));
			expectedBought.add(GameStats.getGood("Gear"));
			expectedBought.add(GameStats.getGood("Gold"));
			expectedBought.add(GameStats.getGood("Gunpowder"));
		}
		int index = 0;
		for (Item item : expectedBought) {
			assertEquals(item, itemsBought.get(index));
			index += 1;
		}
		
		ArrayList<Item> expectedSold = new ArrayList<Item>(); {
			expectedSold.add(itemsSold.get(0));
			expectedSold.add(GameStats.getGood("Banana"));
			expectedSold.add(GameStats.getGood("Coconut"));
			expectedSold.add(GameStats.getGood("Apple"));
			expectedSold.add(GameStats.getGood("Orange"));
		}
		index = 0;
		for (Item item : expectedSold) {
			assertEquals(item, itemsSold.get(index));
			index += 1;
		}
		
		CommandInput.sc.close();
		
	}

}
