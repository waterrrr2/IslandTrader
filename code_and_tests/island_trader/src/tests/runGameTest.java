package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game_code.runGame;

/**
 *Activating the Commandline version of the game
 */
class runGameTest {

	/**
	 * running the method.
	 */
	@Test
	void testStart() {
		runGame run = new runGame();
		run.start();
	}

}
