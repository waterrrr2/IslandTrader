package tests;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.Test;

import game_code.Route;

/**
 * Some simple testing on the Route class
 * Mainly testing if the route generated has the intended variables stored
 */
class RouteTest {

	
	/**
	 * Testing if the correct Route instance is created, also tests the get functions.
	 */
	@Test
	void testRoute() {
		Route route = new Route(20, 5);
		assertEquals(route.getLength(), 20);
		assertEquals(route.getRisk(), 5);
	}
	
	/**
	 * Tests on the Chance method to see if it returns the correct boolean
	 */
	@Test
	void testChance() {
		Route route1 = new Route(20, -1);
		Route route2 = new Route(30, 6);
		assertEquals(route1.Chance(route1.getRisk()),false);
		assertEquals(route2.Chance(route2.getRisk()),true);
	}

}
