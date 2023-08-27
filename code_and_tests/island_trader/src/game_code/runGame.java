package game_code;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
/**
 * The command line version of the game
 * @author Klai
 *
 */

public class runGame {
	/** 
	 * Setting up the game
	 */
	GameEnvironment game = new GameEnvironment();
	/**
	 * A boolean used to check if the game has ended
	 */
	private boolean end = false;
	/**
	 * A scanner. This is static and public so we don't have to create new scanners.
	 * Closing a scanner looking at the standard input closes the standard input as well.
	 * This ends up crashing the application if you want any more input.
	 * 
	 * Creating a bunch of new scanners is using up more memory than necessary.
	 * So just use this one.
	 */
	public static Scanner sc = new Scanner(System.in);
	
	
	/**
	 * Begins the game and determine if the game ends
	 * Displays the initialized status of the game
	 */
	public void start() {
		//Chooses the ship
		String name = CommandInput.chooseName();
		//Chooses the Ship
		Ship ship = CommandInput.chooseShip(name);
		//Chooses the game's duration.
		System.out.println("Please type how many days you would like the game to last.\n"
				+ "Your selection must be between 20 and 50 days.\n");
		int duration = CommandInput.numberSelector(20, 50);
		//Start the game
		game.beginGame(name, ship, duration);
	
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		
		
		System.out.println(describe.describeTime());
		System.out.println(describe.describeShip());
		System.out.println(describe.describeMoney());
		
		while (!end) {
			chooseAction();
		}
		System.out.println("GameOver\n");
		ending();
		
		
	}
	/**
	 * prompts the player available actions to choose from
	 * player chooses from a list of numbered actions
	 */
	public void chooseAction() {
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		while(!end) {
			if(!checkDays()&&!checkEco()) {
				System.out.println("Welcome to the " +describe.describeIsland(game.getShip().getLocation()));
				System.out.println("What would you like to do?(enter a number)\n");
				System.out.println("1)View Money \n2)View Time left \n3)View Ship property \n4)View Cargo"
						+ "\n5)View your Sales \n6)View Island properties \n7)Visit Shop \n8)Set Sail!");
				int choice = CommandInput.numberSelector(1, 8);
				Actions(choice);
			}
		}
		end = true;
	}
	
	/**
	 * The actions the player can perform. An integer is assigned to each action
	 * @param choice The player's choice of action in integer
	 */
	public void Actions(int choice) {
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		
		switch(choice) {
		case 1:
			System.out.println(describe.describeMoney());
			break;
		case 2:
			System.out.println(describe.describeTime());
			break;
		case 3:
			System.out.println(describe.describeShip()+describe.describeEndurance());
			break;
		case 4:
			System.out.println(describe.describeCargo());
			break;
		case 5:
			System.out.println(describe.describeSales());
			break;
		case 6:
			islandProp();
			break;
		case 7:
			shop();
			break;
		case 8:
			setSail();
			break;
		}
	}
	
	/**
	 * The view island Property action
	 * The player choose from a list of numbered islands, the properties of the island is then displayed
	 */
	public void islandProp() {
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		System.out.println("Which Island would you like to investigate? \n1)Fruit Island \n2)Luxury Island"
				+ "\n3)Tech Island \n4)Forge Island \n5)Pirate Island");
		int choice = CommandInput.numberSelector(1, 5);
		switch(choice) {
		case 1:
			System.out.println(describe.describeIsland(game.getIslands().get("Fruit Island")) + describe.describeSold(game.getIslands().get("Fruit Island")) + "\n" +describe.describeWant(game.getIslands().get("Fruit Island")) + "\n");
			break;
		case 2:
			System.out.println(describe.describeIsland(game.getIslands().get("Luxury Island")) + describe.describeSold(game.getIslands().get("Luxury Island")) + "\n" +describe.describeWant(game.getIslands().get("Luxury Island")) + "\n");
			break;
		
		case 3:
			System.out.println(describe.describeIsland(game.getIslands().get("Tech Island")) + describe.describeSold(game.getIslands().get("Tech Island")) + "\n" +describe.describeWant(game.getIslands().get("Tech Island")) + "\n");
			break;
		
		case 4:
			System.out.println(describe.describeIsland(game.getIslands().get("Forge Island")) + describe.describeSold(game.getIslands().get("Forge Island")) + "\n" +describe.describeWant(game.getIslands().get("Forge Island")) + "\n");
			break;
		
		case 5:
			System.out.println(describe.describeIsland(game.getIslands().get("Pirate Island")) + describe.describeSold(game.getIslands().get("Pirate Island")) + "\n" +describe.describeWant(game.getIslands().get("Pirate Island")) + "\n");
			break;
		}
	}
	
	/**
	 * The Shop action
	 * Player can choose to buy, sell or leave the shop by entering an integer
	 */
	public void shop() {
		boolean exitShop = false;
		
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		System.out.println("Welcome to the Shop!\n");
		System.out.println(describe.describeSold(game.getShip().getLocation()) + "\n" +describe.describeWant(game.getShip().getLocation()) + "\n");
		System.out.println(describe.describeMoney());
		
		while (!exitShop){
			System.out.println("\nWhat would you like to do?\n"
					+ "1)Buy \n2)Sell \n3)Repair Ship \n4)Leave Shop");
			int choice = CommandInput.numberSelector(1, 4);
			
			switch(choice) {
			case 1:
				buy();
				break;
			case 2:
				sell();
				break;
			case 3:
				repair();
				break;
			case 4:
				exitShop = true;
			}
		}
	}
	
	
	/**
	 * The repair ship action in the Shop action
	 * Repairs the ship at the a cost
	 */
	public void repair() {
		int endurance = game.getShip().getEndurance();
		int money = game.getStats().getMoney();
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		describe.describeEndurance();
		if (endurance == 0) {
			System.out.println("You do not need to repair your ship");
		}else {
			System.out.println("Would you like to repair your ship?\n1)Yes \n2)No");
			int choice = CommandInput.numberSelector(1, 2);
			switch(choice) {
			case 1:
				if (money < endurance) {
					System.out.println("You do not have enought money.");
				}else {
					game.getStats().changeMoney(-endurance);
					game.getShip().setEndurance(0);
					System.out.println("Repair Success!\n");
					break;
				}
			case 2:
				break;
			}
		}
		
		
	}
	/**
	 * The Buy action in the Shop action
	 * The player enters the integer assigned to an item to attempt an purchase
	 */
	public void buy() {
		ArrayList<Item> itemsSold = game.getShip().getLocation().getItemsSold();
		int amount = 0;
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		Item upgrade = itemsSold.get(0);
		Item itmOne = itemsSold.get(1);
		Item itmTwo = itemsSold.get(2);
		Item itmThree = itemsSold.get(3);
		Item itmFour = itemsSold.get(4);
		Item buying = null;
		System.out.println("What would you like to purchase?(Please enter a number)\n");
		System.out.println(describe.describeSold(game.getShip().getLocation()));
		System.out.println("6)Exit Buy");
		
		int choice = CommandInput.numberSelector(1, 6);
		switch(choice) {
		case 1:
			buying = upgrade;
			break;
		case 2:
			buying = itmOne;
			break;
		case 3:
			buying = itmTwo;
			break;
		case 4:
			buying = itmThree;
			break;
		case 5:
			buying = itmFour;
			break;
		case 6:
			break;
		}
		if(choice < 6) {
			System.out.println("How many would you like to purchase?(Please select from 1-99, enter 0 to exit)");
			amount = CommandInput.numberSelector(0, 99);
			game.getShip().getLocation().buyItem(game.getShip(), buying, game.getStats(), amount);
		}
	}
	
	/**
	 * The Sell action in the Shop action
	 * The player enters the integer assigned to an item to attempt sell it
	 */
	public void sell() {
		int amount = 0;
		descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
		ArrayList<Good> itemsWant = game.getShip().getLocation().getItemsBought();
		Good itmOne = itemsWant.get(0);
		Good itmTwo = itemsWant.get(1);
		Good itmThree = itemsWant.get(2);
		Good itmFour = itemsWant.get(3);
		Good selling = null;
		
		System.out.println("What would you like to sell?(Please enter a number)\n");
		System.out.println(describe.describeWant(game.getShip().getLocation()));
		System.out.println("5)Exit Sell");
		int choice = CommandInput.numberSelector(1, 5);
		switch(choice) {
		case 1:
			selling= itmOne;
			break;
		case 2:
			selling = itmTwo;
			break;
		case 3:
			selling = itmThree;
			break;
		case 4:
			selling = itmFour;
			break;
		case 5:
			break;
		}
		if(choice < 5) {
			System.out.println("How many would you like to Sell?(Please select from 1-99, enter 0 to exit)\n");
			amount = CommandInput.numberSelector(0, 99);
			game.getShip().getLocation().sellItem(game.getShip(), selling, game.getStats(), amount);
		}
	}
	
	/**
	 * The setSail action
	 * The player can choose an island to sail to, and then choose a route to take.
	 * Only lets player travel if the ship if repaired.
	 */
	public void setSail() {
		if(game.getShip().getEndurance() != 0) {
			System.out.println("Please repair the ship in the shop before setting sail.\n");
		}else {
			descriptionMaker  describe = new descriptionMaker(game.getShip(),game.getStats());
			Hashtable<Integer,Island> numDest = new Hashtable<Integer,Island>();
			Hashtable<Integer,Route> numRoute = new Hashtable<Integer,Route>();
			int destNum = 0;
			int routeNum = 0;
			int speed = game.getShip().getSpeed();
			Island source = game.getShip().getLocation();
			Island destination = game.getShip().getLocation();
			Route chosenRoute = null;
			System.out.println("Please choose your destination\n");
			for (Island dest : game.getIslands().values()) {
				if (dest != game.getShip().getLocation()) {
					destNum += 1;
					numDest.put(destNum, dest);
					System.out.println(destNum + ")" + dest.getName());
				}	
			}
			destNum += 1;
			System.out.println("\n" + destNum + ")Back to current Island");
			int landChoice = CommandInput.numberSelector(1, 5);
			switch(landChoice) {
			case 1:
				destination = numDest.get(1);
				break;
			case 2:
				destination = numDest.get(2);
				break;
			case 3:
				destination = numDest.get(3);
				break;
			case 4:
				destination = numDest.get(4);
				break;
			case 5:
				break;
			}
			
			if (destination != source) {
				System.out.println("Please choose a route\n");
				for(Route route : game.getALLROUTES().get(source).get(destination)) {
					routeNum += 1;
					numRoute.put(routeNum, route);
					System.out.println(routeNum + ")" + describe.describeRoute(destination, route));
					
				}
				System.out.println(routeNum+1 + ")Back to Island selection");
				int routeChoice = CommandInput.numberSelector(1, 4);
				switch(routeChoice) {
				case 1:
					chosenRoute = numRoute.get(1);
					break;
				case 2:
					chosenRoute = numRoute.get(2);
					break;
				case 3:
					chosenRoute = numRoute.get(3);
					break;
				case 4:
					setSail();
					break;
				}
				if(chosenRoute != null) {
					
					int length = chosenRoute.getLength();
					double days = length/speed;
					int crewCost = game.getShip().getCrewCost();
					int money = game.getStats().getMoney();
					int totalCost = game.getShip().getCrewCost()*(int)days;
					
					if(game.getStats().getTimeLeft() < days) {
						System.out.println("There is not enough time left to take this route.\n");
					}else if (money < crewCost*days) {
						System.out.println("You do not have enought money to pay your crew. \n");
					}else {
						game.getStats().advanceTime(days);
						game.getShip().setLocation(destination);
						game.getStats().changeMoney(-totalCost);
						System.out.println("You paid your crew $" + totalCost+" to set sail.\n");
						if (chosenRoute.Chance(chosenRoute.getRisk())){
							RandomEvent event = new RandomEvent(game.getShip(),game.getStats());
							boolean fate = event.activateEvent();
							System.out.println("Enter Anything to Continue:\n");
							sc.nextLine();
							if (fate) {
								end = true;
							}
						}
					
					}
				}
			}
		}
	}
	
	/**
	 * This method is ran when the game ends
	 * Displays the player's name, game duration, profit made and final score.
	 */
	public void ending() {
		double duration = game.getStats().getTime() - game.getStats().getTimeLeft();
		int profit = game.getStats().getProfit();
		game.setScore(profit + (int)duration*100);
		int score = game.getScore();
		System.out.println("Trader name: " + game.getShip().getName()+"\nGame duration: " + duration + "/" + game.getStats().getTime() + "\n"
				+ "Profit made: $" + profit + "\nScore: " + score);
	}
	
	/**
	 * Checks if all days have passed or the number of remaining days is not enough to sail anywhere.
	 * @return False if there is still time to make an action, True if there is no time.
	 */
	public boolean checkDays() {
		int endCounter = 0;
		int speed = game.getShip().getSpeed();
		Island source = game.getShip().getLocation();
		for (Island destination : game.getIslands().values()) {
			if (destination != game.getShip().getLocation()) {
				for(Route route : game.getALLROUTES().get(source).get(destination)) {
					if (route.getLength()/speed > game.getStats().getTimeLeft()) {
						endCounter += 1;
					}
				}
			}
		}
		if (game.getStats().getTime() == 0 || endCounter == 12) {
			System.out.println("The number of remaining days is not enough to sail anywhere.\n");
			end = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player can not sell on this island
	 * @return False if the player can sell items, True if the player can not sell.
	 */
	public boolean checkCantSell() {
		int cantSell= 0;
		Island location = game.getShip().getLocation();
		for(Good good: location.getItemsBought()) {
			if (game.getShip().cargo.get(good) == 0) {
				cantSell += 1;
			}
		}
		if (cantSell == 4) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player has enough money to make an action, also checks if the player can sell goods and then make an action.
	 * @return True if the player can not sell and 1)do not have enough money to repair the ship. 2) do not have enough money to take
	 * any of the routes to another island, then sets end to true. Otherwise returns false.
	 */
	public boolean checkEco() {
		int brokeCount = 0;
		int speed = game.getShip().getSpeed();
		int money = game.getStats().getMoney();
		int cost = game.getShip().getCrewCost();
		Island source = game.getShip().getLocation();
		for (Island destination : game.getIslands().values()) {
			if (destination != game.getShip().getLocation()) {
				for(Route route : game.getALLROUTES().get(source).get(destination)) {
					int days = route.getLength()/speed;
					if(days*cost > money) {
						brokeCount += 1;
					}
				}
			}
		}
		if((money < game.getShip().getEndurance()) && checkCantSell()) {
			System.out.println("\nYou do not have enough money to repair your ship and there is nothing you can sell on this island.\n");
			end = true;
			return true;
		}
		if (checkCantSell() && brokeCount == 12) {
			System.out.println("\nYou are too poor to travel and there is nothing you can sell on this island.\n");
			end = true;
			return true;
		}
		return false;
	}
}
