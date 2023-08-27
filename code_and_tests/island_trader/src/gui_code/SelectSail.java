package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import game_code.Island;
import game_code.Route;
import game_code.Ship;
import game_code.descriptionMaker;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionEvent;

/**
 * The window for choosing an island to sail to, as well as a route, and then setting sail.
 * @author nzoli
 *
 */
public class SelectSail {
	
	/**
	 * The frame/window that displays everything
	 * part of Window Builder
	 */
	private JFrame frame;
	
	/**
	 * The UiManager instance that ensures team cohesion amongst the game's windows
	 */
	private UiManager manager;
	
	/**
	 * The island we are currently at/sailing from
	 */
	private Island currentIsland;
	
	/**
	 * The ship the player is sailing.
	 * Relevant for speed display
	 */
	private Ship ship;
	
	/**
	 * A The selection of islands.
	 * Due it being a hashtable, with no order, an ArrayList must be made as well to ensure order.
	 */
	private Hashtable<String, Island> islands;
	
	/**
	 * The names of the islands, in a constant order.
	 */
	private ArrayList<String> islandNames = new ArrayList<String>();
	
	/**
	 * A descriptionMaker Instance that will output strings for describing our routes
	 * A toString method would have been way too messy,
	 * as part of the description is dependent on ship details
	 */
	private descriptionMaker describer;
	
	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectSail window = new SelectSail();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectSail() {
		initialize();
	}
	
	/**
	 * Modified constructor for use in the manager.
	 * @param incomingManager The UiManager instance that will be managing this Window
	 */
	public SelectSail(UiManager incomingManager) {
		manager = incomingManager;
		islands = manager.getEnviroment().getIslands();
		ship = manager.getEnviroment().getShip();
		currentIsland = ship.getLocation();
		describer = new descriptionMaker(ship, manager.getEnviroment().getStats());
		makeIslandNames();
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Helper function to ensure the island names always have the same order.
	 */
	private void makeIslandNames() {
		islandNames.add("Fruit Island");
		islandNames.add("Luxury Island");
		islandNames.add("Tech Island");
		islandNames.add("Forge Island");
		islandNames.add("Pirate Island");
	}
	
	/**
	 * closes the window
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * Tells the manager to go back to the island window
	 */
	public void goBack() {
		manager.secSailBack(this);
	}
	
	/**
	 * Tells the manager to go to the SailingEvent window
	 * @param route The route that the ship is taking to its destination
	 */
	public void beginJourney(Route route) {
		manager.sailEvent(this, route);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultListModel<String> islandListModel = new DefaultListModel<String>();
		//This allows the window to run in a somewhat limited form without the manager
		if (!islandNames.isEmpty()) {
			islandListModel.addAll(islandNames);
		}
		
		JList<String> islandList = new JList<String>(islandListModel);
		islandList.setSelectedIndex(0);
		islandList.setBounds(10, 11, 400, 300);
		frame.getContentPane().add(islandList);
		
		JList<String> routeList = new JList<String>();
		routeList.setBounds(476, 11, 400, 300);
		frame.getContentPane().add(routeList);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		backButton.setBounds(10, 392, 150, 60);
		frame.getContentPane().add(backButton);
		
		JButton routesButton = new JButton("Update Routes");
		routesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the island object of the current selection
				String currentSelection =  islandList.getSelectedValue();
				Island destination = islands.get(currentSelection);
				//Get the routes to that island and set the model to it
				DefaultListModel<String> routeListModel = routeModeller(destination);
				routeList.setModel(routeListModel);
				/*
				 * So far, the model is either displaying
				 * You're already here!
				 * or
				 * Most Risky Route
				 * Medium Risky Route
				 * Least Risky Route
				 * This is the same order as the ArrayList.
				 */
			}
		});
		routesButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		routesButton.setBounds(726, 334, 150, 60);
		frame.getContentPane().add(routesButton);
		
		
		JTextArea errorBox = new JTextArea();
		errorBox.setLineWrap(true);
		errorBox.setWrapStyleWord(true);
		errorBox.setText("");
		errorBox.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		errorBox.setBounds(530, 334, 190, 60);
		frame.getContentPane().add(errorBox);
		
		JButton sailButton = new JButton("Set Sail!");
		sailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentSelection =  islandList.getSelectedValue();
				Island destination = islands.get(currentSelection);
				if (currentIsland == destination) {
					errorBox.setText("You can't sail - You're already here!");
				} else {
					//First, gather information
					int routeIndex = routeList.getSelectedIndex();
					if (routeIndex == -1) {
						errorBox.setText("Please Select a Route!");
					} else {
						//A kind of horrendous line here, but this will get us our desired route object
						Route chosenRoute = 
								manager.getEnviroment().getALLROUTES().get(currentIsland).get(destination).get(routeIndex);
						
						int length = chosenRoute.getLength();
						double days = length/ship.getSpeed();
						int crewCost = ship.getCrewCost();
						int money = manager.getEnviroment().getStats().getMoney();
						int totalCost = crewCost*(int)days;
						
						//Error catching
						if(manager.getEnviroment().getStats().getTimeLeft() < days) {
							errorBox.setText("There is not enough time left to take this route.");
						}else if (money < totalCost) {
							errorBox.setText("You do not have enought money to pay your crew. \n");
						}else {
							//We advance the time here, and then event handling will be in SailingEvent
							manager.getEnviroment().getStats().advanceTime(days);
							ship.setLocation(destination);
							manager.getEnviroment().getStats().changeMoney(-totalCost);
							//Hence why we need to pass it the route
							beginJourney(chosenRoute);
						}
					}
				}
			}
		});
		sailButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		sailButton.setBounds(370, 334, 150, 60);
		frame.getContentPane().add(sailButton);
	}
	
	/**
	 * Helper function for the update routes button
	 * Externalized like this because it will be useful for displaying island info in other windows
	 * @param destination The Island that the player wants to view routes to
	 * @return A DefaultListModel of strings describing the routes
	 */
	private DefaultListModel<String> routeModeller(Island destination) {
		DefaultListModel<String> routeListModel = new DefaultListModel<String>();
		if (currentIsland == destination) {
			//Tell the user they're already here
			routeListModel.add(0, "You're already here!");
			
		} else {
			//Get the routes for them
			Hashtable<Island, Hashtable<Island, ArrayList<Route>>> ALLROUTES = manager.getEnviroment().getALLROUTES();
			ArrayList<Route> routesThere = ALLROUTES.get(currentIsland).get(destination);
			
			for (Route route : routesThere) {
				routeListModel.add(routeListModel.getSize(), describer.describeRoute(currentIsland, route));
			}
			
		}
		return routeListModel;
	}
}
