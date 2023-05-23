package GUI;
import java.awt.Color;
import javax.swing.*;
import pieces.*;
import logic.Coordinates;

public class Tile {

	private JButton myButton;
	private final Coordinates myCoordinates;
	private APiece thePiece;

	protected Tile(int down, int right, String piece, String team){
		
		myCoordinates = new Coordinates(down,right);
		switch(piece) {
		case "King": thePiece = new King(team); break;
		case "Rook": thePiece = new Rook(team); break;
		case "Bishop": thePiece = new Bishop(team); break;
		case "Pawn": thePiece = new Pawn(team); break;
		case "Knight": thePiece = new Knight(team); break;
		}
		setUpIcon();
	}	
	protected Tile(int down, int right){
		
		myCoordinates = new Coordinates(down,right);
		thePiece = null;
		setUpIcon();
	}
	
	// Icon-related methods.
	private void setUpIcon() {																
		
		if(thePiece==null) { myButton = new JButton(); return;}
		int a = thePiece.getType();	
		switch(a) {
		case 1: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/King1.png"))); break;
		case 2: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Knight1.png"))); break;
		case 3: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Bishop1.png"))); break;
		case 4: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Rook1.png"))); break;
		case 5: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Pawn1.png"))); break;
		case 6: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Pawn1.png"))); break;
		case 7: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/King2.png"))); break;
		case 8: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Knight2.png"))); break;
		case 9: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Bishop2.png"))); break;
		case 10: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Rook2.png"))); break;
		case 11: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Pawn2.png"))); break;
		case 12: myButton = new JButton(new ImageIcon(getClass().getResource("/icons/Pawn2.png"))); break;
		}
	}	
	protected void resetUpIcon() { 															
		
		if(thePiece==null) { myButton.setIcon(null); return;}
		int a = thePiece.getType();	
		switch(a) {
		case 1: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/King1.png"))); break;
		case 2: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Knight1.png"))); break;
		case 3: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Bishop1.png"))); break;
		case 4: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Rook1.png"))); break;
		case 5: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Pawn1.png"))); break;
		case 6: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Pawn1.png"))); break;
		case 7: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/King2.png"))); break;
		case 8: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Knight2.png"))); break;
		case 9: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Bishop2.png"))); break;
		case 10: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Rook2.png"))); break;
		case 11: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Pawn2.png"))); break;
		case 12: myButton.setIcon(new ImageIcon(getClass().getResource("/icons/Pawn2.png"))); break;
		}
		myButton.setEnabled(true);
	}	
	
	// Look-related methods.
	protected void setRegularLook() {
		// Black and White background and not clickable if tile is empty.
		int x = (myCoordinates.getDown() + myCoordinates.getRight()) % 2;
		if(x==0) {
			myButton.setBackground(Color.BLACK);
		} else {
			myButton.setBackground(Color.white);
		}
		if(thePiece!=null) {
				myButton.setEnabled(true);	
		} else {
			myButton.setEnabled(false);	
		}
	}
	protected void setEnabledHighlight() {
		myButton.setBackground(Color.BLUE);
		myButton.setEnabled(true);		
	}
	protected void setPawnHighlightDuringMenu() {
		myButton.setBackground(Color.BLUE);
		myButton.setEnabled(false);
	}
	protected void setUnabledGray() {
		myButton.setBackground(Color.gray);
		myButton.setEnabled(false);
		myButton.setDisabledIcon(myButton.getIcon());
	}	
	protected void setHighlightRed() {
		myButton.setBackground(Color.red);
	}
	
	// Internal-processes related methods.
	protected void moveInPiece(APiece piece) {
		
		if(piece instanceof Pawn) {
			((Pawn) piece).setFirstMove(false);
		}
		thePiece = piece;
		resetUpIcon();
	}
	
	// Get methods.
	protected Coordinates getCoordinates() {
		return myCoordinates;
	}
	protected int getDownCoordinate() {
		return myCoordinates.getDown();
	}
	protected int getRightCoordinate() {
		return myCoordinates.getRight();
	}
	protected APiece getPiece() {
		return thePiece;
	}
	protected int getTypeOfPiece() {
		return thePiece.getType();
	}
	protected int getTeamOfPiece() {
		return thePiece.getTeam();
	}
	protected JButton getButton() {															
		return myButton;
	}
	
}