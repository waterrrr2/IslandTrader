package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;

import game_code.Island;
import game_code.Route;
import game_code.Ship;
import game_code.descriptionMaker;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 * The window for looking at all islands currently in the game
 * As well as their shops and routes
 * @author nzoli
 *
 */
public class ViewIslands {
	
	/**
	 * The window being rendered/displayed
	 * part of Window Builder
	 */
	private JFrame frame;
	
	/**
	 * The UiManager instance responsible for multi-window cooperation
	 */
	private UiManager manager;
	
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
	 * The island we are currently at/sailing from
	 */
	private Island currentIsland;
	
	/**
	 * The ship the player is sailing.
	 * Relevant for speed display
	 */
	private Ship ship;

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewIslands window = new ViewIslands();
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
	public ViewIslands() {
		initialize();
	}
	
	/**
	 * Modified constructor to be used in the manager
	 * @param incomingManager The UiManager instance that will be managing this window
	 */
	public ViewIslands(UiManager incomingManager) {
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
		manager.viewIsleBack(this);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(486, 11, 400, 300);
		frame.getContentPane().add(scrollPane);
		
		JTextArea descBox = new JTextArea();
		scrollPane.setViewportView(descBox);
		
		JButton salesButton = new JButton("View Sales");
		salesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the island object of the current selection
				String currentSelection =  islandList.getSelectedValue();
				Island destination = islands.get(currentSelection);
				
				String description = describer.describeSold(destination);
				descBox.setText(description);
			}
		});
		salesButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		salesButton.setBounds(476, 322, 130, 50);
		frame.getContentPane().add(salesButton);
		
		JButton buysButton = new JButton("View Buys");
		buysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the island object of the current selection
				String currentSelection =  islandList.getSelectedValue();
				Island destination = islands.get(currentSelection);
				
				String description = describer.describeWant(destination);
				descBox.setText(description);
			}
		});
		buysButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		buysButton.setBounds(616, 322, 130, 50);
		frame.getContentPane().add(buysButton);
		
		JButton routeButton = new JButton("View Routes");
		routeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the island object of the current selection
				String currentSelection =  islandList.getSelectedValue();
				Island destination = islands.get(currentSelection);
				//Get the routes to that island and set the model to it
				String routeDescription = routeModeller(destination);
				descBox.setText(routeDescription);
			}
		});
		routeButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		routeButton.setBounds(756, 322, 130, 50);
		frame.getContentPane().add(routeButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		backButton.setBounds(10, 361, 180, 90);
		frame.getContentPane().add(backButton);
	}
	
	/**
	 * Helper function for the update routes button
	 * Externalized like this because it will be useful for displaying island info in other windows
	 * copied and modified from SelectSail
	 * @param destination The Island that the player wants to view routes to
	 * @return A DefaultListModel of strings describing the routes
	 */
	private String routeModeller(Island destination) {
		String routeListModel = new String();
		//Get the routes for them
		Hashtable<Island, Hashtable<Island, ArrayList<Route>>> ALLROUTES = manager.getEnviroment().getALLROUTES();
		ArrayList<Route> routesThere = ALLROUTES.get(currentIsland).get(destination);
		
		if (routesThere == null) {
			return "You're already here!";
		} else {
		
			for (Route route : routesThere) {
				routeListModel += describer.describeRoute(currentIsland, route) + "\n";
			}
				
			return routeListModel;
		}
	}
}
