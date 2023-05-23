package GUI;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

public class PawnMenu {
	
	private JFrame myFrame;
	private JPanel generalPanel;
	private GridLayout myLayout;
	private JButton bishop;
	private JButton rook;
	private JButton knight;

	protected PawnMenu(int team){
		
		myFrame = new JFrame("Pawn Promotion");
		generalPanel = new JPanel();
		bishop = new JButton();
		rook = new JButton();
		knight = new JButton();
		if(team == 1) {
			JOptionPane.showMessageDialog(myFrame, "You must exchange your pawn for one of the following pieces.", "PLAYER 1 - PAWN PROMOTION", JOptionPane.PLAIN_MESSAGE);
			bishop.setIcon(new ImageIcon(getClass().getResource("/icons/Bishop1.png")));
			rook.setIcon(new ImageIcon(getClass().getResource("/icons/Rook1.png")));
			knight.setIcon(new ImageIcon(getClass().getResource("/icons/Knight1.png")));
			bishop.setBackground(Color.WHITE);
			rook.setBackground(Color.WHITE);
			knight.setBackground(Color.WHITE);
		}
		if(team == 2) {
			JOptionPane.showMessageDialog(myFrame, "You must exchange your pawn for one of the following pieces.", "PLAYER 2 - PAWN PROMOTION", JOptionPane.PLAIN_MESSAGE);
			bishop.setIcon(new ImageIcon(getClass().getResource("/icons/Bishop2.png")));
			rook.setIcon(new ImageIcon(getClass().getResource("/icons/Rook2.png")));
			knight.setIcon(new ImageIcon(getClass().getResource("/icons/Knight2.png")));
			bishop.setBackground(Color.WHITE);
			rook.setBackground(Color.WHITE);
			knight.setBackground(Color.WHITE);
		}
		
		myLayout = new GridLayout(1,3);
		myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		myFrame.setSize(600, 230);
		myFrame.setContentPane(generalPanel);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		generalPanel.setLayout(myLayout);
		generalPanel.add(bishop);
		generalPanel.add(rook);
		generalPanel.add(knight);
	}
	
	protected JButton getBishopButton() {
		return bishop;
	}
	protected JButton getRookButton() {
		return rook;
	}
	protected JButton getKnightButton() {
		return knight;
	}
	protected void close() {
		myFrame.dispose();
	}
	
}