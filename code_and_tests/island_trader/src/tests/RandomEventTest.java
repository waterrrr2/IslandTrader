package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import game_code.CommandInput;
import game_code.GameStats;
import game_code.Good;
import game_code.RandomEvent;
import game_code.Ship;


/**
 * Tests on the RandomEvent class
 */
class RandomEventTest {

	
	/**
	 * Tests the event of a player losing a battle to pirates
	 */
	@Test
	void LossTest() {
		//Automation setup
		//Setting the user's input
		String userInput = "Anything\nAnything";
		ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
		/*
		 * Changing our scanner to use this input stream instead of System.in
		 * If we do use System.in, then we'll encounter some errors
		 * This is because - as far as I know, if we want to correctly set
		 * the input stream we have to do it BEFORE the scanner is created
		 * Trouble is - since our scanner is static it gets created in previous tests
		 * This results in tests failing when ran together, but passing when ran individually
		 */
		CommandInput.sc = new Scanner(bais);
		//Redirecting the standard output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
	    System.setOut(printStream);
	    //Begin setup of the event
		Ship waka = new Ship("Waka", 10, 10, 10);
		GameStats stats = new GameStats();
		RandomEvent robbery = new RandomEvent(waka, stats);
		robbery.setType("Pirates!");
		robbery.setMagnitude(10);
		//Event is now ready to go, but the cargo is not.
		Hashtable<String, Good> ECONOMY = GameStats.getEconomy();
		Good apples = ECONOMY.get("Apple");
		waka.cargo.put(apples, 20);
		//Pirates should spare the player
		robbery.activateEvent();
		//Getting the output
		String[] lines = baos.toString().split(System.lineSeparator());
		String LastLine = lines[lines.length - 1];
		String expectedLine = "The pirates are satisfied with your cargo and spare you!";
		//Do they spare? or KILL?
		assertEquals(LastLine, expectedLine);
		//Reset a few things, aiming for the pirates to make the player walk the plank.
		waka.cargo.put(apples, 10);
		robbery.activateEvent();
		lines = baos.toString().split(System.lineSeparator());
		LastLine = lines[lines.length - 1];
		expectedLine = "The pirates are not satisfied with this - walk the plank!";
		assertEquals(LastLine, expectedLine);
		/*
		 * We CAN close this scanner instance because it's using a pre-baked input stream
		 * and not System.in - so closing it closes our custom input stream
		 * which, since we make a new input stream for each test, is fine,
		 * and is in fact good for memory.
		 * Don't close the scanner ANYWHERE in the game_code package, though
		 * unless you're not going to get any more input from the user for the rest of the program's runtime.
		 */
		CommandInput.sc.close();
		
	}
	
	/**
	 * Tests the even of a player winning against pirates.
	 */
	@Test
	void victoryTest() {
		//Automation setup
		//Setting the user's input
		String userInput = "Anything\nAnything";
		ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
		/*
		 * Changing our scanner to use this input stream instead of System.in
		 * If we do use System.in, then we'll encounter some errors
		 * This is because - as far as I know, if we want to correctly set
		 * the input stream we have to do it BEFORE the scanner is created
		 * Trouble is - since our scanner is static it gets created in previous tests
		 * This results in tests failing when ran together, but passing when ran individually
		 */
		CommandInput.sc = new Scanner(bais);
		//Redirecting the standard output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);
		//Begin setup of the event
		//GameEnvironment.sc = new Scanner(System.in);
		Ship waka = new Ship("Waka", 10, 10, 10);
		GameStats stats = new GameStats();
		RandomEvent robbery = new RandomEvent(waka, stats);
		robbery.setType("Pirates!");
		robbery.setMagnitude(0);
		//Pirates should fail to defeat the player
		robbery.activateEvent();
		//Getting the output
		String[] lines = baos.toString().split(System.lineSeparator());
		String LastLine = lines[lines.length - 1];
		LastLine = LastLine.substring(16);
		String expectedLine = "You have defeated the pirates in battle!";
		//Did we get what we wanted?
		assertEquals(LastLine, expectedLine);
		/*
		 * We CAN close this scanner instance because it's using a pre-baked input stream
		 * and not System.in - so closing it closes our custom input stream
		 * which, since we make a new input stream for each test, is fine,
		 * and is in fact good for memory.
		 * Don't close the scanner ANYWHERE in the game_code package, though
		 * unless you're not going to get any more input from the user for the rest of the program's runtime.
		 */
		CommandInput.sc.close();
	}

}
