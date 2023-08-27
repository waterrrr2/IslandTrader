package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game_code.Item;

/**
 * Tests on the Item class
 */
class ItemTest {

	/**
	 * Just some basic tests to check if the class is generating the correct instances.
	 */
	@Test
	void testItem() {
		Item item = new Item("Banana",3);
		assertEquals(item.getName(),"Banana");
		assertEquals(item.getPrice(), 3);
		
		Item test = new Item("Phone", 999);
		assertEquals(test.getName(), "Phone");
		assertEquals(test.getPrice(), 999);
	}

}
