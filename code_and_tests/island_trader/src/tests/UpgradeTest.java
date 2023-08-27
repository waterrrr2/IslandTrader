package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game_code.Upgrade;

/**
 * Tests on the Upgrade class
 */
class UpgradeTest {

	
	/** 
	 * A simple test to check if the created Upgrade instance contains the correct info.
	 */
	@Test
	void testUpgrade() {
		Upgrade test = new Upgrade("Test", 100, "This upgrade is a test");
		String desc = "This upgrade is a test";
		assertEquals(test.getName(),"Test");
		assertEquals(test.getPrice(),100);
		assertEquals(test.getDescription(),desc);
	}

}
