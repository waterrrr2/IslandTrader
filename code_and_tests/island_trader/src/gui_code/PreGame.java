package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import game_code.GameEnvironment;
import game_code.Ship;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * The GUI window that gets the setup prompts from the player, those being
 * Their Trader name
 * Their Choice of Ship
 * Their desired duration of days
 * @author nzoli
 *
 */
public class PreGame {
	
	/**
	 * The window/frame
	 * Part of WindowBuilder
	 */
	private JFrame frame;
	/**
	 * The frame for player's name
	 */
	private JTextField nameField;
	/**
	 * The frame for time of the game
	 */
	private JTextField timeField;
	/**
	 * The UiManager instance that manages this window
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
					PreGame window = new PreGame();
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
	public PreGame() {
		initialize();
	}
	
	/**
	 * Modified constructor to be used in the manager.
	 * @param incomingManager The UiManager instance that will be managing this Window
	 */
	public PreGame(UiManager incomingManager) {
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
	 * Tells the manager we are finished with this window and to move on to the next window.
	 */
	public void finishedWindow() {
		manager.closePreGame(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("Enter Your Name!");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(310, 11, 280, 42);
		frame.getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(310, 64, 280, 20);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		

		
		
		JList<String> shipList = new JList<>();
		shipList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		shipList.setModel(new DefaultListModel<String>() {
			String[] values = new String[] {
					"1: A big and powerful vessel - Cargo Capacity: 1000kg Speed: 10 Crew: 40", 
					"2: This one has five masts - Cargo Capacity: 600kg Speed: 30 Crew: 40", 
					"3: Essentially a floating barrel - Cargo Capacity: 600kg Speed: 10 Crew: 15", 
					"4: A speedy thing, if a bit cramped - Cargo Capacity: 400kg Speed: 30 Crew: 20"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		shipList.setSelectedIndex(0);
		shipList.setBounds(33, 152, 825, 144);
		frame.getContentPane().add(shipList);
		
		

		
		JLabel shipLabel = new JLabel("Select Your Ship!");
		shipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shipLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		shipLabel.setBounds(344, 105, 215, 36);
		frame.getContentPane().add(shipLabel);
		
		timeField = new JTextField();
		timeField.setBounds(405, 376, 100, 20);
		frame.getContentPane().add(timeField);
		timeField.setColumns(10);
		
		JLabel timeLabel = new JLabel("How long would you like the game to last?");
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(254, 307, 410, 58);
		frame.getContentPane().add(timeLabel);
		
		//This needs to be before the start button in the code, so that its text can be changed
		JTextArea errorBox = new JTextArea();
		errorBox.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		errorBox.setForeground(Color.BLACK);
		errorBox.setEditable(false);
		errorBox.setBounds(10, 357, 373, 95);
		frame.getContentPane().add(errorBox);
		
		JButton btnNewButton = new JButton("Start The Game!");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//No error catching for this statement needed
				int shipChoice = shipList.getSelectedIndex() + 1;
				//This statement needs error catching!
				try {
				String name = nameField.getText(); 
				Ship ship = GameEnvironment.initializeShip(shipChoice, name);
				int time = Integer.parseInt(timeField.getText());
				if(time < 20 || time > 50) 
					throw new NumberFormatException();
				manager.startGame(name, ship, time);
				//We are now done gathering information, and have begun the game!
				finishedWindow();
				}  catch(NumberFormatException invalidNumber) {
					String errormessage = "Please enter a valid number between 20 and 50.";
					errorBox.setText(errormessage);
				} catch(IllegalArgumentException invalidName) {
					String errormessage = invalidName.getMessage();
					errorBox.setText(errormessage);
				} 
			}
		});
		btnNewButton.setBounds(678, 378, 180, 74);
		frame.getContentPane().add(btnNewButton);
		

	}
	

}
