import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GUI {
	
	private JFrame frame;
	public static JLabel chatCounterLabel;
	public static JTextField botMessage;
	public static JTextField userInterests;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 446, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStartBot = new JButton("Start Bot");
		btnStartBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main();
				try {
					Main.main(null);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnStartBot.setForeground(new Color(0, 0, 0));
		btnStartBot.setBackground(new Color(0, 204, 0));
		btnStartBot.setBounds(314, 125, 105, 34);
		frame.getContentPane().add(btnStartBot);
		
		botMessage = new JTextField();
		botMessage.setBounds(10, 125, 294, 34);
		frame.getContentPane().add(botMessage);
		botMessage.setColumns(10);
		
		JLabel messageLabel = new JLabel("Enter your desired message here");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		messageLabel.setBounds(10, 91, 294, 34);
		frame.getContentPane().add(messageLabel);
		
		JLabel enterInterests = new JLabel("Enter interests in the form of: \"Add\", \"like\", \"this\"");
		enterInterests.setHorizontalAlignment(SwingConstants.CENTER);
		enterInterests.setFont(new Font("Tahoma", Font.PLAIN, 18));
		enterInterests.setBounds(10, 11, 409, 34);
		frame.getContentPane().add(enterInterests);
		
		userInterests = new JTextField();
		userInterests.setBounds(10, 45, 410, 34);
		frame.getContentPane().add(userInterests);
		userInterests.setColumns(10);
		
		chatCounterLabel = new JLabel("Total conversations: 0");
		chatCounterLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chatCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chatCounterLabel.setBounds(10, 170, 409, 14);
		frame.getContentPane().add(chatCounterLabel);
		
		JButton btnHelpAbout = new JButton("Help / About");
		btnHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "1. Enter interests in quotation marks \" \" separated by a comma ,\n    Do not place a comma after the final interest\n2. Click 'Start Bot' \n\nUpon exiting the total number of conversations will be displayed\n\n               Created by Carlisle Miller 09-03-2019\n              https://carlislem.github.io/resume.html");
			}
		});
		btnHelpAbout.setBounds(167, 196, 105, 34);
		frame.getContentPane().add(btnHelpAbout);

	}
}
