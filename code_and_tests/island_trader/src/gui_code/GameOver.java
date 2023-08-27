package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.UIManager;

import game_code.GameStats;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The window that appears at the end of the game
 * @author nzoli
 *
 */
public class GameOver {

	/**
	 * The window being rendered
	 * Part of Window Builder
	 */
	private JFrame frame;
	
	/**
	 * The manager that gives this window that sweet sweet gameplay data
	 */
	private UiManager manager;
	
	/**
	 * The reason why the game has ended.
	 * The reason must be one of the following (in string form):
	 * Pirates
	 * Money
	 * Time
	 */
	private String reason = new String();

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOver window = new GameOver();
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
	public GameOver() {
		initialize();
	}
	
	/**
	 * Modified constructor for use in the manager
	 * @param incomingManager The UiManager instance responsible for managing this window
	 * @param incomingReason The reason for the game ending.
	 * The reason must be one of the following (in string form):
	 * Pirates
	 * Money
	 * Time
	 */
	public GameOver(UiManager incomingManager, String incomingReason) {
		manager = incomingManager;
		reason = incomingReason;
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Makes the summary of the game, and sets the text of the given text area to it
	 * @param summary The JText Area that the summary will be displayed in.
	 */
	public void makeSummary(JTextArea summary) {
		String gameSummary = new String();
		gameSummary += "Here ends the career of :" + manager.getEnviroment().getShip().getName() + "\n";
		//First, tell the player why the game has ended
		switch(reason) {
		case "Pirates":
			gameSummary += "You were forced to walk the plank by pirates.";
			break;
		case "Money":
			gameSummary += "You ran out of money to pay your crew!";
			break;
		case "Time":
			gameSummary += "Time's up! Let's see how you did.";
			break;
		default:
			gameSummary += "The game unded for an unknown reason.";
			break;
		}
		//add some space
		GameStats stats = manager.getEnviroment().getStats();
		gameSummary += "\n\n";
		//Find out how much money the player has
		int profit = stats.getProfit();
		gameSummary += "You ended the game with " + profit + " dollars made in profit.\n";
		//Summarize the time
		double duration = stats.getTime() - stats.getTimeLeft();
		gameSummary += "Out of a total of " + stats.getTime() + " days, you traded for " 
				+ duration + " days.";
		//Show their score
		manager.getEnviroment().setScore(profit + (int)duration*100);
		gameSummary += "Your final score is: " + manager.getEnviroment().getScore();
		summary.setText(gameSummary);
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
		
		JLabel gameOverLabel = new JLabel("GAME OVER");
		gameOverLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameOverLabel.setBounds(350, 11, 200, 40);
		frame.getContentPane().add(gameOverLabel);
		
		JTextArea summaryBox = new JTextArea();
		summaryBox.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		summaryBox.setLineWrap(true);
		summaryBox.setWrapStyleWord(true);
		summaryBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		summaryBox.setEditable(false);
		summaryBox.setBounds(10, 70, 876, 306);
		//Line for setting the summary
		if (manager != null)
			makeSummary(summaryBox);
		frame.getContentPane().add(summaryBox);
		
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open up a quit window and then close the thing
				QuitWindow wannaQuit = new QuitWindow(frame);
			}
		});
		quitButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		quitButton.setBounds(350, 387, 200, 75);
		frame.getContentPane().add(quitButton);
	}
}
