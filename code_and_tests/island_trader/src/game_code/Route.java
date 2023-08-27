package game_code;

import java.util.Random;

/** The Route class, representing the route the player takes to travel to different Islands.
 * @author Klai
 */

public class Route {

	/** 
	 * The length of the route
	 */
	private int length;
	
	/**
	 * The risks of taking the route
	 */
	private int risk;
	
	/**
	 * A random number generator
	 */
	private Random rng = new Random();
	
	
	/** The constructor of the routes
	 * @param length The length of the route
	 * @param risk The risk in taking the route
	 */
	public Route(int length, int risk) {
		this.length = length;
		this.risk = risk;
	}
	
	/** Gets the Length of the route
	 * @return the Length of the route, in integer form
	 */
	public int getLength() {
		return length;
	}
	
	/** Gets the risk in taking the route
	 * @return the risk in taking the route, in integer form
	 */
	public int getRisk() {
		return risk;
	}
	
	/** The chance of triggering an RandomEvent, this increases with higher risk
	 * returns true if random event is triggered, otherwise returns false
	 * @param risk The risk in taking the route
	 * @return A boolean representing if an RandomEvent is activated or not.
	 */
	public boolean Chance(int risk) {
		risk = this.risk;
		if (rng.nextInt(6) < risk){
			return true;
		}
		return false;
	}
	
}
