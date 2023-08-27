package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import game_code.GameStats;
import game_code.Ship;
import game_code.descriptionMaker;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 * The window for viewing the properties, cargo and sales of a ship
 * @author nzoli
 *
 */
public class ShipView {

	/**
	 * The window/frame.
	 * Part of Window Builder.
	 */
	private JFrame frame;
	
	/**
	 * The UiManager instance that ensures team cohesion amongst the game's windows
	 */
	private UiManager manager;
	
	/**
	 * The ship that the player is sailing
	 */
	private Ship ship;
	
	/**
	 * The stat-tracking Gamestats instance
	 */
	private GameStats stats;
	
	/**
	 * A description-making instance for producing descriptions of properties, items, etc.
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
					ShipView window = new ShipView();
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
	public ShipView() {
		initialize();
	}
	
	/**
	 * Modified constructor to be used in the manager
	 * Does not need an island instance, as the manager is keeping track of which island we're at
	 * and we cannot leave the island from this screen.
	 * @param incomingManager The UiManager instance that will be managing this Window
	 */
	public ShipView(UiManager incomingManager) {
		manager = incomingManager;
		ship = manager.getEnviroment().getShip();
		stats = manager.getEnviroment().getStats();
		describer = new descriptionMaker(ship, stats);
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * Tells the manager we're finished with this window and to move on to the next
	 */
	public void finishedWindow() {
		manager.closeShipView(this);
	}
	
	/**
	 * Repairs the ship if the player has enough money
	 * Also updates the text area that is describing the ship
	 * @param output The JTextArea that contains the ship description
	 */
	private void repair(JTextArea output) {
		int endurance = ship.getEndurance();
		int money = manager.getEnviroment().getStats().getMoney();
		if (money < endurance)
			output.setText("You do not have enough money to repair your ship!");
		else {
			stats.changeMoney(-endurance);
			ship.setEndurance(0);
			output.setText(describer.describeShip() + describer.describeEndurance());
		}
		
		
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
		
		JTextArea shipField = new JTextArea();
		shipField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		shipField.setText("Here is a description of the ship!");
		if (manager != null)
			shipField.setText(describer.describeShip() + describer.describeEndurance());
		shipField.setWrapStyleWord(true);
		shipField.setLineWrap(true);
		shipField.setEditable(false);
		shipField.setBounds(10, 11, 400, 350);
		frame.getContentPane().add(shipField);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(476, 11, 400, 350);
		frame.getContentPane().add(scrollPane);
		
		JTextArea goodsField = new JTextArea();
		scrollPane.setViewportView(goodsField);
		goodsField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		goodsField.setText("This starts with a description of the Cargo!\r\nAnd then later becomes a description of the sales!");
		if (manager != null)
			goodsField.setText(describer.describeCargo());
		goodsField.setWrapStyleWord(true);
		goodsField.setLineWrap(true);
		goodsField.setEditable(false);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//We're done here
				finishedWindow();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		backButton.setBounds(350, 375, 200, 60);
		frame.getContentPane().add(backButton);
		
		JButton repairButton = new JButton("Repair Ship");
		repairButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Try to repair the ship
				repair(shipField);
			}
		});
		repairButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		repairButton.setBounds(10, 372, 130, 40);
		frame.getContentPane().add(repairButton);
		
		JButton salesButton = new JButton("View Sales");
		salesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goodsField.setText(describer.describeSales());
			}
		});
		salesButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salesButton.setBounds(746, 372, 130, 40);
		frame.getContentPane().add(salesButton);
		
		JButton cargoButton = new JButton("View Cargo");
		cargoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goodsField.setText(describer.describeCargo());
			}
		});
		cargoButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cargoButton.setBounds(606, 372, 130, 40);
		frame.getContentPane().add(cargoButton);
	}
}
