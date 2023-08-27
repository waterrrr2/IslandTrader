package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import game_code.GameStats;
import game_code.Island;
import game_code.Item;
import game_code.Ship;
import game_code.descriptionMaker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 * The window for purchasing goods/upgrades from an island's shop
 * @author nzoli
 *
 */
public class BuyStuffWindow {
	
	/**
	 * The current window being rendered
	 * part of Window Builder
	 */
	private JFrame frame;
	
	/**
	 * A textfield for number input for
	 */
	private JTextField numField;
	
	/**
	 * The Ui manager responsible for window teamwork
	 */
	private UiManager manager;
	
	/**
	 * The ship the player is sailing
	 */
	private Ship ship;
	
	/**
	 * The current stat-tracking instance of the game
	 */
	private GameStats stats;
	
	/**
	 * A descriptionMaker Instance that will output strings for our goods
	 */
	private descriptionMaker describer;
	
	/**
	 * The island we are currently at/sailing from
	 */
	private Island currentIsland;

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuyStuffWindow window = new BuyStuffWindow();
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
	public BuyStuffWindow() {
		initialize();
	}
	
	/**
	 * Modified constructor for use in the manager
	 * @param incomingManager The UiManager instance responsible for managing this window
	 */
	public BuyStuffWindow(UiManager incomingManager) {
		manager = incomingManager;
		ship = manager.getEnviroment().getShip();
		currentIsland = ship.getLocation();
		stats = manager.getEnviroment().getStats();
		describer = new descriptionMaker(ship, manager.getEnviroment().getStats());
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * closes the window
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * Tells the manager to close this window and go back to the island window
	 */
	public void goBack() {
		manager.doneShopping(this);
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
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		backButton.setBounds(10, 402, 180, 60);
		frame.getContentPane().add(backButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 400, 300);
		frame.getContentPane().add(scrollPane);
		
		JList<String> shopList = new JList<String>();
		scrollPane.setViewportView(shopList);
		shopList.setSelectedIndex(0);
		if (manager != null) {
			shopList.setModel(itemModeller());
		}
		
		
		JLabel shopLabel = new JLabel("Goods for Sale");
		shopLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		shopLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shopLabel.setBounds(10, 11, 400, 52);
		frame.getContentPane().add(shopLabel);
		
		numField = new JTextField();
		numField.setText("1");
		numField.setBounds(564, 230, 300, 40);
		frame.getContentPane().add(numField);
		numField.setColumns(10);
		
		JTextArea numTextBox = new JTextArea();
		numTextBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		numTextBox.setText("How many would you like to buy?\r\nIf you\u2019re buying an upgrade, you\u2019ll only buy one, no matter what number you put in.");
		numTextBox.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		numTextBox.setEditable(false);
		numTextBox.setWrapStyleWord(true);
		numTextBox.setLineWrap(true);
		numTextBox.setBounds(564, 109, 300, 90);
		frame.getContentPane().add(numTextBox);
		
		JButton buyButton = new JButton("Buy");
		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				//gather info
				int quantity = Integer.parseInt(numField.getText());
				int itemIndex = shopList.getSelectedIndex();
				Item desiredItem = currentIsland.getItemsSold().get(itemIndex);
				
				if (currentIsland.validPurchase(ship, stats, desiredItem, quantity)) {
					//If the purchase is valid, then go ahead with it!
					currentIsland.buyItem(ship, desiredItem, stats, quantity);
					numTextBox.setText("Purchase Success!");
				} else {
					numTextBox.setText("Purchase failed - do you have enough money and/or cargo room?");
				}
				
				//We are now done gathering information, and have begun the game!
				}  catch(NumberFormatException invalidNumber) {
					String errormessage = "Please enter a valid number.";
					numTextBox.setText(errormessage);
				}
			}
		});
		buyButton.setBounds(639, 285, 150, 40);
		frame.getContentPane().add(buyButton);
	}
	
	/**
	 * Helper function that turns the string description of the goods into a defaultListModel
	 * @return A DefaultListModel containing strings for the item descriptions.
	 */
	private DefaultListModel<String> itemModeller() {
		
		String description = describer.describeSold(currentIsland);
		String[] descLines = description.split("\n");
		
		DefaultListModel<String> itemListModel = new DefaultListModel<String>();
		
		for (String line : descLines) {
			itemListModel.add(itemListModel.getSize(), line);
		}
		
		/*
		 * The first two elements in the model are an empty line,
		 * and the header, both of which we do not need.
		 * This deletes those two.
		 */
		itemListModel.removeRange(0, 1);
		
		return itemListModel;
	}
}
