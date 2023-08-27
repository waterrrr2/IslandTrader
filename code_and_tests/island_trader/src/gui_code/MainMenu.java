package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The Main Menu of the game
 * Is essentially a title screen and a start button.
 * I'm going to include a quit screen for posterity.
 * @author nzoli
 *
 */
public class MainMenu {

	/**
	 * The window/frame
	 * part of WindowBuilder
	 */
	private JFrame frame;
	/**
	 * The UiManager instance that ensures team cohesion amongst the game's windows
	 */
	private UiManager manager;

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		initialize();
	}
	
	/**
	 * Modified constructor to be used in the manager
	 * @param incomingManager The UiManager instance that will be managing this Window
	 */
	public MainMenu(UiManager incomingManager) {
		manager = incomingManager;
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
	 * Tells the manager that we're finished with this window and to move on to the next one
	 * Which, since this is only called in one context, will open up a PreGame window
	 */
	public void finishedWindow() {
		manager.closeMainMenu(this);
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
		
		JLabel titleText = new JLabel("Island Trader");
		titleText.setHorizontalAlignment(SwingConstants.CENTER);
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 35));
		titleText.setBounds(325, 80, 250, 60);
		frame.getContentPane().add(titleText);
		
		/**
		 * This button starts the game
		 */
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//We're done here, to the next!
				finishedWindow();
			}
		});
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
		startButton.setBounds(350, 250, 200, 70);
		frame.getContentPane().add(startButton);
		
		/**
		 * This is a quit button, pretty much only here for posterity's sake
		 */
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open up a quit window and then close the thing
				QuitWindow wannaQuit = new QuitWindow(frame);
			}
		});
		quitButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		quitButton.setBounds(10, 402, 117, 50);
		frame.getContentPane().add(quitButton);
	}
	

}
