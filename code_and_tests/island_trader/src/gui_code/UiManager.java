package gui_code;

import game_code.GameEnvironment;
import game_code.GameStats;
import game_code.Good;
import game_code.Island;
import game_code.Route;
import game_code.Ship;

/**
 * The program that manages all the different windows and ensures they co-operate
 * @author nzoli
 *
 */
public class UiManager {
	/**
	 * The GameEnvironment instance, which holds all game data.
	 * We cannot actually start the game and fully create this instance until after the pre-game window.
	 */
	private GameEnvironment environment = new GameEnvironment();
	
	/**
	 * Main method. Running this program ensures that every window works well in conjunction with the rest of the windows
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		UiManager manager = new UiManager();
		//Let's begin!
		MainMenu openingMenu = new MainMenu(manager);
	}
	
	/**
	 * gets the game environment instance.
	 * @return The game environment instance.
	 */
	public GameEnvironment getEnviroment() {
		return environment;
	}
	
	/**
	 * Finishes setting up the GameEnvironment and all variables inside it, 
	 * allowing the game to begin.
	 * @param name The name selected by the player
	 * @param ship The ship selected by the player
	 * @param duration The game duration selected by the player
	 */
	public void startGame(String name, Ship ship, int duration) {
		environment.beginGame(name, ship, duration);
		//TesterChanger();
	}
	
	/**
	 * Launches the PreGame screen
	 */
	public void launchPreGame() {
		PreGame preGameScreen = new PreGame(this);
	}
	
	/**
	 * Launches the island screen.
	 * Takes the island instance from the ship's current location
	 * So change its location BEFORE you call this method
	 * Also checks to see if a game over should occur whenever the window is loaded.
	 */
	public void launchIsland() {
		if (checkDays()) {
			//End the game
			launchGameOver("Time");
		} else if (checkEco()) {
			//End the game
			launchGameOver("Money");
		} else {
			IslandWindow currentIsland = new IslandWindow(this, environment.getShip().getLocation());
		}
		
	}
	
	/**
	 * Launches the ship viewing screen
	 */
	public void launchShipView() {
		ShipView fineVessel = new ShipView(this);
	}
	
	/**
	 * Launches the Island and Route selection window for sailing
	 */
	public void launchSelectSail() {
		SelectSail roadTrip = new SelectSail(this);
	}
	
	/**
	 * Launches the Sailing Event window
	 * @param path The route that the player is taking there
	 */
	public void launchSailingEvent(Route path) {
		SailingEvent exactlyAsPlanned = new SailingEvent(this, path);
	}
	
	/**
	 * Launches the Game Over screen
	 * @param reason The reason why the game over occured.
	 */
	public void launchGameOver(String reason) {
		GameOver endGame = new GameOver(this, reason);
	}
	
	/**
	 * Launches the island view screen
	 */
	public void launchViewIslands() {
		ViewIslands aGoodLook = new ViewIslands(this);
	}
	
	/**
	 * Launches the shop buy window
	 */
	public void launchBuyWindow() {
		BuyStuffWindow shoppingTrip = new BuyStuffWindow(this);
	}
	
	/**
	 * Launches the shop sell window
	 */
	public void launchSellWindow() {
		SellStuffWindow fineWares = new SellStuffWindow(this);
	}
	
	/**
	 * closes the main menu and opens up the Pre-Game Menu
	 * @param menu the MainMenu instance being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void closeMainMenu(MainMenu menu) {
		menu.closeWindow();
		launchPreGame();
	}
	
	/**
	 * Closes the PreGame menu and opens up the first island window
	 * @param setup The PreGame Instance being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void closePreGame(PreGame setup) {
		setup.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the Island Window and opens the Ship Viewing window.
	 * @param island The IslandWindow instance being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void inspectShip(IslandWindow island) {
		island.closeWindow();
		launchShipView();
	}
	
	/**
	 * Closes the ship view window and opens the Island window
	 * @param fineVessel The ShipView instance being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void closeShipView(ShipView fineVessel) {
		fineVessel.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the island window and opens the island/route selection window for sailing
	 * @param island The island window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void settingSail(IslandWindow island) {
		island.closeWindow();
		launchSelectSail();
	}
	
	/**
	 * Closes the SelectSail window and opens the Island window
	 * @param journey The SelectSail window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void secSailBack(SelectSail journey) {
		journey.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the SelectSail window and opens the SailingEvent window
	 * @param journey The SelectSail window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 * @param path The route that the player is taking
	 * 
	 */
	public void sailEvent(SelectSail journey, Route path) {
		journey.closeWindow();
		launchSailingEvent(path);
	}
	
	/**
	 * closes the SailingEvent window and opens a gameover caused by pirates.
	 * @param event The SailingEvent window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void gameOver(SailingEvent event) {
		event.closeWindow();
		launchGameOver("Pirates");
	}
	
	/**
	 * closes the SailingEvent window and opens the island window for the destination
	 * @param event the SailingEvent window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void finishedVoyage(SailingEvent event) {
		event.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the Island window and opens the viewing island window, for looking at all islands
	 * @param island The island window being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void viewIslands(IslandWindow island) {
		island.closeWindow();
		launchViewIslands();
	}
	
	/**
	 * Closes the ViewIslands window and goes back to the regular island window
	 * @param window The ViewIslands window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void viewIsleBack(ViewIslands window) {
		window.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the Island window and opens the shop buy window
	 * @param island The island window being closed.
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void buyThings(IslandWindow island) {
		island.closeWindow();
		launchBuyWindow();
	}
	
	/**
	 * Closes the shop buy window and opens the island window
	 * @param window The window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void doneShopping(BuyStuffWindow window) {
		window.closeWindow();
		launchIsland();
	}
	
	/**
	 * Closes the island window and opens the shop sell window
	 * @param island The island window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void sellThings(IslandWindow island) {
		island.closeWindow();
		launchSellWindow();
	}
	
	/**
	 * Closes the shop sell window and opens the island window
	 * @param window The window being closed
	 * Should be called in the class, so the parameter 'this' should be used.
	 */
	public void doneSelling(SellStuffWindow window) {
		window.closeWindow();
		launchIsland();
	}
	
	/*
	 * The following are time/money checking functions for game overs
	 */
	
	/**
	 * Checks if all days have passed or the number of remaining days is not enough to sail anywhere.
	 * @return False if there is still time to make an action, True if there is no time.
	 */
	public boolean checkDays() {
		int endCounter = 0;
		int speed = environment.getShip().getSpeed();
		Island source = environment.getShip().getLocation();
		for (Island destination : environment.getIslands().values()) {
			if (destination != environment.getShip().getLocation()) {
				for(Route route : environment.getALLROUTES().get(source).get(destination)) {
					if (route.getLength()/speed > environment.getStats().getTimeLeft()) {
						endCounter += 1;
					}
				}
			}
		}
		if (environment.getStats().getTime() == 0 || endCounter == 12) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the player can not sell on this island
	 * @return False if the player can sell items, True if the player can not sell.
	 */
	public boolean checkCantSell() {
		int cantSell= 0;
		Island location = environment.getShip().getLocation();
		for(Good good: location.getItemsBought()) {
			if (environment.getShip().cargo.get(good) == 0) {
				cantSell += 1;
			}
		}
		if (cantSell == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the player has enough money to make an action, also checks if the player can sell goods and then make an action.
	 * @return True if the player can not sell and 1)do not have enough money to repair the ship. 2) do not have enough money to take
	 * any of the routes to another island, then sets end to true. Otherwise returns false.
	 */
	public boolean checkEco() {
		int brokeCount = 0;
		int speed = environment.getShip().getSpeed();
		int money = environment.getStats().getMoney();
		int cost = environment.getShip().getCrewCost();
		Island source = environment.getShip().getLocation();
		for (Island destination : environment.getIslands().values()) {
			if (destination != environment.getShip().getLocation()) {
				for(Route route : environment.getALLROUTES().get(source).get(destination)) {
					int days = route.getLength()/speed;
					if(days*cost > money) {
						brokeCount += 1;
					}
				}
			}
		}
		if((money < environment.getShip().getEndurance()) && checkCantSell()) {
			return true;
		} else if (checkCantSell() && brokeCount == 12) {
			return true;
		} else {
			return false;
		}
	}
	

}
