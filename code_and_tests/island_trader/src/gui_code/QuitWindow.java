package gui_code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A simple quit window that asks if a user wants to quit the program.
 * @author nzoli
 *
 */
public class QuitWindow {

	/**
	 * The window/frame.
	 * Part of WindowBuilder.
	 */
	private JFrame frame;
	/**
	 * The parentFrame
	 * Originates from the window that created this quit window.
	 */
	private JFrame parentFrame;

	/**
	 * Launch the application.
	 * @param args Stores the Java command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuitWindow window = new QuitWindow();
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
	public QuitWindow() {
		initialize();
	}
	
	/**
	 * Modified constructor that takes the frame of the window that called it.
	 * @param spawningWindow The JFrame instance of the window that is creating this.
	 * Essentially, the JFrame of the window you want to quit.
	 */
	public QuitWindow(JFrame spawningWindow) {
		parentFrame = spawningWindow;
		initialize();
		frame.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 350, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Are you sure you want to quit?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 0, 316, 38);
		frame.getContentPane().add(lblNewLabel);
		
		/**
		 * This button just closes the quit window if the player doesn't want to quit
		 */
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		noButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		noButton.setBounds(10, 49, 150, 53);
		frame.getContentPane().add(noButton);
		
		/**
		 * This button closes its parent window, and then itself.
		 * For if the player is sure they want to quit the game.
		 */
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close the frame of the parent/spawning window
				parentFrame.dispose();
				//And now, finally, close ourself
				frame.dispose();
			}
		});
		yesButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		yesButton.setBounds(170, 49, 156, 53);
		frame.getContentPane().add(yesButton);
	}
	

}
