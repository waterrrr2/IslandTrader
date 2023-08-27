package game_code;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that contains all the command line input stuff.
 * Ideally, everything here should be static.
 * @author nzoli
 *
 */
public class CommandInput {
	/**
	Opens the Scanner;
	*/
	public static Scanner sc = runGame.sc;
	
	/**
	 * The method prompts the player to choose a name
	 * prompts error message when the name chosen is invalid
	 * @return candidate The trader's name, in String form.
	 */
	public static String chooseName() {
		System.out.println("Please choose a name for your trader!");
		Boolean done = false;
		String candidate = new String();
		while (!done) {
			try {
				candidate = sc.nextLine();
				System.out.println(candidate + " Is your name?");
				GameStats.characterCheck(candidate);
				done = true;
			} catch(IllegalArgumentException e) {
				String message = e.getMessage();
				System.out.println(message);
			}
		}
		return candidate;
	}
	
	/**
	 * The method that prompts a player to choose their ship.
	 * Automatically calls another method to create that ship object.
	 * @param name The name of the ship to be created.
	 * @return vessel The ship object that was selected.
	 */
	public static Ship chooseShip(String name) {
		System.out.println("Please choose your ship:\n");
		System.out.println("1: A big and powerful vessel - "
				+ "\nCargo Capacity: 1000kg\nSpeed: 10\nCrew: 40");
		System.out.println("2: This one has five masts - "
				+ "\nCargo Capacity: 600kg\nSpeed: 30\nCrew: 40");
		System.out.println("3: Essentially a floating barrel - "
				+ "\nCargo Capacity: 600kg\nSpeed: 10\nCrew: 15");
		System.out.println("4: A speedy thing, if a bit cramped - "
				+ "\nCargo Capacity: 400kg\nSpeed: 30\nCrew: 20");
		System.out.println("Please make your selection now");
		int choice = numberSelector(1, 4);
		Ship vessel = GameEnvironment.initializeShip(choice, name);
		return vessel;
	}
	
	/**
	 * A number-selector method that gets an int input from the user and then returns that
	 * This method handles things like their input being out of bounds, or invalid.
	 * Public and static so that it can be used elsewhere
	 * @param min The minimum/lowest acceptable number option
	 * @param max The maximum/highest acceptable number option.
	 * @return The number that the user selected.
	 */
	public static int numberSelector(int min, int max) {
		int selection = -1;
		while (selection < min || selection > max) {
			try {
				selection = sc.nextInt();
				System.out.println("YOUR SELECTION IS: " + selection + "\n");
				if (selection < min || selection > max) {
					System.out.println("Please enter a number between "+min+" and "+max);
				}
			} catch(InputMismatchException e) {
				System.out.println("Please enter a valid number");
				//The input hasn't been consumed, so skip it to avoid trying to consume it again.
				sc.nextLine();
			} 
		}
		return selection;
	}

}
