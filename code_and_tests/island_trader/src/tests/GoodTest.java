package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sun.jdi.InvalidTypeException;

import game_code.Good;

/**
 * Tests on the Good class
 */
class GoodTest {

	
	
	/**
	 * Tests if an error message is thrown if the good is not within invalid goods
	 */
	@Test
	void testInvalidGood() {
		try {
			Good Phone = new Good("Phone", 20, 5);
			assertEquals(Phone.getName(),"Phone");
			
		} catch(InvalidTypeException e) {
			String message = "The name of the good is not valid! Valid goods are found in Good.validGoods";
			assertEquals(message, e.getMessage());
		}
	}
	
	/**
	 * Tests if the constructor works properly
	 */
	@Test
	void testGood() {
		try {
			Good banana = new Good("Banana", 3, 1);
			assertEquals(banana.getName(), "Banana");
			assertEquals(banana.getPrice(),3);
			assertEquals(banana.getSize(),1);
			
		} catch (InvalidTypeException e) {
			e.printStackTrace();
		}
		
		
	}

}
