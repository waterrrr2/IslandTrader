package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;

import game_code.RandomEvent;
import game_code.Route;
import game_code.Ship;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;

/**
 * The window that displays and runs random events during a player's voyage
 * @author nzoli
 *
 */
public class SailingEvent {

	/**
	 * The display frame/window
	 * part of Window Builder
	 */
	private JFrame frame;
	
	/**
	 * The ui manager that makes sure every window has the data it needs
	 */
	private UiManager manager;
	
	/**
	 * The route that the player is taking
	 */
	private Route route;
	
	/**
	 * The ship that the player is sailing
	 */
	private Ship ship;
	
	/**
	 * A summary of the events that have occured during the trip
	 */
	private String summary = new String();
	
	/**
	 * A bool of whether or not the player has perished on their trip
	 * ten out of ten perishings are caused by pirates
	 */
	private boolean perished = false;

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SailingEvent window = new SailingEvent();
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
	public SailingEvent() {
		initialize();
	}
	
	/**
	 * Modified constructor for use in the manager
	 * @param incomingManager The UiManager instance in charge of mananging this window
	 * @param incomingRoute The route that the player is taking
	 */
	public SailingEvent(UiManager incomingManager, Route incomingRoute) {
		/*
		 * This redirects the standard output into an unused variable
		 * Just so that while the GUI is running, nothing gets printed to the console.
		 */
		final PrintStream oldStdout = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream cleaner = new PrintStream(baos);
		System.setOut(cleaner);
		//Actual construction
		manager = incomingManager;
		route = incomingRoute;
		ship = manager.getEnviroment().getShip();
		runEvent();
		//After the event is done, restore System.out to what it was before.
		System.setOut(oldStdout);
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Superficially runs the event
	 * Really just checks whether or not it should run
	 * then follows the appropriate course of action.
	 */
	private void runEvent() {
		boolean event = route.Chance(route.getRisk());
		if (event) {
			causeEvent();
		} else {
			calmJourney();
		}
	}
	
	/**
	 * Edits the summary to describe a calm journey over the seas
	 */
	private void calmJourney() {
		summary += "You experience a calm journey where nothing happens.";
		summary += "\nPress the button to continue";
	}
	
	/**
	 * Actually runs the event.
	 * Also calls helper functions to set the summary to describe what is happening.
	 */
	private void causeEvent() {
		RandomEvent event = new RandomEvent(ship, manager.getEnviroment().getStats());
		perished = event.activateEvent();
		String type = event.getType();
		
		switch (type) {
		case "Pirates!":
			pirateSummary(event);
			break;
		case "A Terrible Storm!":
			stormSummary(event);
			break;
		case "Marooned Sailors!":
			sailorSummary(event);
			break;
		}
	}
	
	/**
	 * Helper function to give a play-by-play of pirates trying to illegally download the player's goods.
	 * @param event The event being summarized. Needed for combat display, and loss/win checking.
	 */
	private void pirateSummary(RandomEvent event) {
		int combat = event.getCombat();
		int magnitude = event.getMagnitude();
		
		summary += "Pirates have found you! Prepare for battle!\n";
		
		if (combat >= magnitude) {
			summary += "You rolled a "+combat+". You have defeated the pirates in battle!\n";
			summary += "\nThe rest of your journey passes without incident.";
		} else {
			summary += "You rolled a " + combat + ". The pirates have defeated you.\nThey have stolen all your goods!\n";
			if (perished) {
				summary += "The pirates are not satisfied with this - walk the plank!\n";
				summary += "\nGAME OVER";
			} else {
				summary += "The pirates are satisfied with your cargo and spare you!\n";
				summary += "\nLuckily, no more pirates accost you on the rest of your voyage.";
			}
		}
	}
	
	/**
	 * Sets the summary to describe a regular day in [insert place with bad weather].
	 * @param event The event being summarized. Needed for damage display.
	 */
	private void stormSummary(RandomEvent event) {
		int magnitude = event.getMagnitude();
		summary += "You sail through a terrible storm!\n";
		summary += "\nYou've taken "+ magnitude + " points of damage!\n";
		summary += "\nAt least lightning doesn't strike twice...";
	}
	
	/**
	 * Sets the summary to describe the player's heroic rescue of marooned sailors.
	 * @param event The event being summarized. Needed for the monetary reward.
	 */
	private void sailorSummary(RandomEvent event) {
		int magnitude = event.getMagnitude();
		summary += "You come across marooned sailors!\n";
		summary += "They give you " + magnitude + " dollars as a reward for saving them!\n";
		summary += "\nWhatever marooned them is long gone.";
	}
	
	/**
	 * closes the window
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * Tells the manager to close the window and open a game over screen.
	 */
	public void gameOver() {
		manager.gameOver(this);
	}
	
	/**
	 * Tells the manager to close the window and open an island window.
	 */
	public void finishedVoyage() {
		manager.finishedVoyage(this);
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
		
		JButton contButton = new JButton("Continue");
		contButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If we've perished, take us to the game over screen
				//Otherwise, back to the island screen!
				if (perished) {
					gameOver();
				} else {
					finishedVoyage();
				}
			}
		});
		contButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contButton.setBounds(350, 370, 200, 70);
		frame.getContentPane().add(contButton);
		
		JTextArea eventSummary = new JTextArea();
		eventSummary.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eventSummary.setWrapStyleWord(true);
		eventSummary.setLineWrap(true);
		eventSummary.setEditable(false);
		eventSummary.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		//Put the summary of events into the box.
		eventSummary.setText("Event time! Time for eventing!");
		if (summary.length() > 0) {
			eventSummary.setText(summary);
		}
		eventSummary.setBounds(10, 11, 876, 337);
		frame.getContentPane().add(eventSummary);
	}
}
