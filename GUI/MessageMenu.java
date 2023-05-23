package GUI;
import java.awt.*;
import javax.swing.*;

public class MessageMenu {

	private JFrame myFrame;
	private JPanel myPanel;
	private JPanel bottomPanel = new JPanel();;
	private GridLayout bottomLayout = new GridLayout(1,2);
	private JButton restart = new JButton("Restart");
	private JButton exit = new JButton("Exit");
	private JButton start = new JButton("Start");
	private JLabel message = new JLabel();
	
	protected MessageMenu(int winner){
		
		if(winner==1) {
			myFrame = new JFrame("CONGRATULATIONS PLAYER 1");
			message.setText("<html><center>PLAYER 1 WINS!</center><br>Now you can choose to play again or exit the game.</html>");
			message.setIcon(new ImageIcon(getClass().getResource("/icons/WINS1.png")));
		} else if (winner == 2){
			myFrame = new JFrame("CONGRATULATIONS PLAYER 2");
			message.setText("<html><center>PLAYER 2 WINS!</center><br>Now you can choose to play again or exit the game.</html>");
			message.setIcon(new ImageIcon(getClass().getResource("/icons/WINS2.png")));
		} else if (winner == 0) {
			myFrame = new JFrame("WELL PLAYED");
			message.setText("<html><center>IT'S A TIE!</center><br>Now you can choose to play again or exit the game.</html>");
			message.setIcon(new ImageIcon(getClass().getResource("/icons/tie.png")));
		} else {
			myFrame = new JFrame("WELCOME");
			message.setText("<html><center>GAME RULES:</center><center>- If you click on a piece you're obligated to move it.<br>- A piece can't be moved if it means king exposure.</center></html>");
			message.setIcon(new ImageIcon(getClass().getResource("/icons/intro.png")));
			myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			myFrame.setSize(600, 450);
			myFrame.setLocationRelativeTo(null);
			myPanel = new JPanel();
			myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			myPanel.setBackground(Color.gray);
			myFrame.add(myPanel, BorderLayout.CENTER);
			myPanel.add(message);
			message.setForeground(Color.white);
			message.setFont(new Font("Sans-serif", Font.BOLD, 20));
			myFrame.setVisible(true);
			message.setIconTextGap(10);
			message.setHorizontalTextPosition(SwingConstants.CENTER);
			message.setVerticalTextPosition(SwingConstants.BOTTOM);
			myFrame.add(start, BorderLayout.SOUTH);
			return;
		}
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(600, 450);
		myFrame.setLocationRelativeTo(null);
		myPanel = new JPanel();
		myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		myPanel.setBackground(Color.gray);
		myFrame.add(myPanel, BorderLayout.CENTER);
		myPanel.add(message);
		message.setForeground(Color.white);
		message.setFont(new Font("Sans-serif", Font.BOLD, 20));
		myFrame.setVisible(true);
		message.setIconTextGap(10);
		message.setHorizontalTextPosition(SwingConstants.CENTER);
		message.setVerticalTextPosition(SwingConstants.BOTTOM);
		bottomPanel.setLayout(bottomLayout);
		bottomPanel.add(restart);
		bottomPanel.add(exit);
		myFrame.add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	protected JButton getExitButton() {
		return exit;
	}
	protected JButton getRestartButton() {
		return restart;
	}
	protected JButton getStartButton() {
		return start;
	}
	protected void close() {
		myFrame.dispose();
	}
	
}