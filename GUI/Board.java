package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logic.*;
import pieces.*;

public class Board {

	private JFrame myFrame = new JFrame("Chess");
	private JPanel generalPanel = new JPanel();
	private GridLayout myLayout = new GridLayout(5,4);
	private Coordinates SelectedPiece_coordinates = null;
	private MovementCalculator myCalculator = new MovementCalculator();
	private int turn = 1;
	private boolean pauseGame = false;
	private Tile[][] myTiles = 	{{new Tile(0,0,"King", "team1"), new Tile(0,1,"Knight", "team1"), new Tile(0,2,"Bishop", "team1"), new Tile(0,3,"Rook", "team1")},
								 {new Tile(1,0,"Pawn", "team1"), new Tile(1,1), new Tile(1,2), new Tile(1,3)},
								 {new Tile(2,0), new Tile(2,1), new Tile(2,2), new Tile(2,3)},
								 {new Tile(3,0), new Tile(3,1), new Tile(3,2), new Tile(3,3,"Pawn", "team2")},
								 {new Tile(4,0,"Rook", "team2"), new Tile(4,1,"Bishop", "team2"), new Tile(4,2,"Knight", "team2"), new Tile(4,3,"King", "team2")},};
	private int[][] matrixBoard =  {{1, 2, 3, 4},
									{6, 0, 0, 0},
									{0, 0, 0, 0},
									{0, 0, 0, 12},
									{10, 9, 8, 7},};
	// TEAM 1 -> 1 = King // 2 = Knight // 3 = Bishop // 4 = Rook // 5 - Pawn // 6 - Pawn with double forward move
	// TEAM 2 -> 7 = King // 8 = Knight // 9 = Bishop // 10 = Rook // 11 - Pawn // 12 - Pawn with double forward move
	private boolean[][] toHighlightMatrix = {{false, false, false, false},
											 {false, false, false, false},
											 {false, false, false, false},
											 {false, false, false, false},
											 {false, false, false, false},};
	
	public Board(){
		
		MessageMenu welcomeMenu = new MessageMenu(-1);
		welcomeMenu.getStartButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(true);
				welcomeMenu.close();
			}
		});	
		initializeGUI();
		setUpButtons();
		loadButtons();
	}
		
	private void initializeGUI() {
	
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(600, 750);
		myFrame.setContentPane(generalPanel);
		myFrame.setLocationRelativeTo(null);
		generalPanel.setLayout(myLayout);
	}	
	private void setUpButtons(){
		
		for(int down = 0; down<5; down++) {
			for(int right = 0; right<4 ; right++) {
				Tile temp = myTiles[down][right];
				temp.getButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(SelectedPiece_coordinates==null) {
							toHighlightMatrix = myCalculator.callPossibleFor(matrixBoard, temp.getTypeOfPiece(), temp.getDownCoordinate(), temp.getRightCoordinate());
							checkSafeMoves(temp.getCoordinates());
							if(checkEmptyMatrix(toHighlightMatrix)) {
								JOptionPane.showMessageDialog(myFrame, "There are no possible movements for the chosen piece.", "No available movements", JOptionPane.ERROR_MESSAGE);
							} else {
								SelectedPiece_coordinates = temp.getCoordinates();
								setDark();
								setHighlights();
							}
						} else {
							if(myTiles[SelectedPiece_coordinates.getDown()][SelectedPiece_coordinates.getRight()].getTypeOfPiece() == 5 && temp.getDownCoordinate()==4) {
								displayPromotionMenu(1, temp.getCoordinates());
							}
							if(myTiles[SelectedPiece_coordinates.getDown()][SelectedPiece_coordinates.getRight()].getTypeOfPiece() == 11 && temp.getDownCoordinate()==0) {
								displayPromotionMenu(2, temp.getCoordinates());
							}
							updateTileAndMatrix(SelectedPiece_coordinates, temp.getCoordinates());
							emptyHighlightMatrix();
							if(pauseGame==true) {
								setDark();
								myTiles[temp.getDownCoordinate()][temp.getRightCoordinate()].setPawnHighlightDuringMenu();
							}  else {
								setRegularLook();
								updateTurn();
								setTurns();
								checkForWinner();
								checkForTie();
							}
						}
					}
				});
			}
		}
	}	
	private void loadButtons() {
		
		for(int down=0; down<5; down++) {
			for(int right=0; right<4; right++) {
				myTiles[down][right].setRegularLook();
				generalPanel.add(myTiles[down][right].getButton());
			}
		}
		setTurns();
	}

	// Checking methods.
	private boolean checkEmptyMatrix(boolean[][] matrix) {
	
		for(int down = 0; down < 5; down++) {
			for(int right = 0; right < 4; right++) {
				if(matrix[down][right]==true) {
					return false;
				}
			}
		}
		return true;
	}
	private void checkForWinner() {
		
		int altTurn = 0;
		if(turn==1) {
			altTurn = 2;
		} else {
			altTurn = 1;
		}
		if (myCalculator.isOnCheck(matrixBoard, turn)) {
			if(myCalculator.isOnCheckMate(matrixBoard, turn)) {
				getTileWithKing(turn).setHighlightRed();
				JOptionPane.showMessageDialog(myFrame, "Player " + turn + " is on check-mate.", "CHECK ALERT", JOptionPane.WARNING_MESSAGE);
				displayMessageMenu(altTurn);
			} else {
				getTileWithKing(turn).setHighlightRed();
				JOptionPane.showMessageDialog(myFrame, "Player " + turn + " is on check.", "CHECK ALERT", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private void checkForTie() {
		
		if(myCalculator.isOnCheck(matrixBoard, turn) == false && myCalculator.isOnCheckMate(matrixBoard, turn) == true) {
			JOptionPane.showMessageDialog(myFrame, "That's a tie!.", "TIE ALERT", JOptionPane.WARNING_MESSAGE);
			displayMessageMenu(0);
			return;
		}
		int count = 0;
		for(int down=0; down<5; down++) {
			for(int right=0; right<4; right++) {
				if(myTiles[down][right].getPiece()!=null) {
					count++;
				}
			}
		}
		if(count==2) {
			JOptionPane.showMessageDialog(myFrame, "That's a tie!.", "TIE ALERT", JOptionPane.WARNING_MESSAGE);
			displayMessageMenu(0);
			return;
		}
	}
	private void checkSafeMoves(Coordinates theCoordinates) {
		
		boolean[][] temp = {{false, false, false, false},
				  			{false, false, false, false},
				  			{false, false, false, false},
				  			{false, false, false, false},
				  			{false, false, false, false},};
		
		if(checkEmptyMatrix(toHighlightMatrix)) {
			return;
		} else {				
			for(int d = 0; d < 5; d++) {
				for(int r = 0; r < 4; r++) {
					if(toHighlightMatrix[d][r]==true) {
						if(myCalculator.checkSafeMove(matrixBoard, theCoordinates, d, r, turn)){
							temp[d][r]=true;
						}
					}
				}
			}
			emptyHighlightMatrix();
			copyToHighlightMatrix(temp, toHighlightMatrix);
		}
	}
		
	// Congratulations Menu methods.
	private void displayMessageMenu(int team) {
		
		myFrame.setVisible(false);
		MessageMenu messageMenu = new MessageMenu(team);
		setMessageMenuButtons(messageMenu);
	}
	private void setMessageMenuButtons(MessageMenu menu) {
		
		menu.getRestartButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetTiles();
				setRegularLook();
				turn = 1;
				setTurns();
				menu.close();
				myFrame.setVisible(true);
			}
		});	
		menu.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});	
	}
	private void resetTiles() {
		
		myTiles[0][0].moveInPiece(new King("team1"));
		myTiles[0][1].moveInPiece(new Knight("team1"));
		myTiles[0][2].moveInPiece(new Bishop("team1"));
		myTiles[0][3].moveInPiece(new Rook("team1"));
		myTiles[1][0].moveInPiece(new Pawn("team1"));
		((Pawn)myTiles[1][0].getPiece()).setFirstMove(true);
		myTiles[1][1].moveInPiece(null);
		myTiles[1][2].moveInPiece(null);
		myTiles[1][3].moveInPiece(null);
		myTiles[2][0].moveInPiece(null);
		myTiles[2][1].moveInPiece(null);
		myTiles[2][2].moveInPiece(null);
		myTiles[2][3].moveInPiece(null);
		myTiles[3][0].moveInPiece(null);
		myTiles[3][1].moveInPiece(null);
		myTiles[3][2].moveInPiece(null);
		myTiles[3][3].moveInPiece(new Pawn("team2"));
		((Pawn)myTiles[3][3].getPiece()).setFirstMove(true);
		myTiles[4][0].moveInPiece(new Rook("team2"));
		myTiles[4][1].moveInPiece(new Bishop("team2"));
		myTiles[4][2].moveInPiece(new Knight("team2"));
		myTiles[4][3].moveInPiece(new King("team2"));
		matrixBoard[0][0] = 1;
		matrixBoard[0][1] = 2;
		matrixBoard[0][2] = 3;
		matrixBoard[0][3] = 4;
		matrixBoard[1][0] = 6;
		matrixBoard[1][1] = 0;
		matrixBoard[1][2] = 0;
		matrixBoard[1][3] = 0;
		matrixBoard[2][0] = 0;
		matrixBoard[2][1] = 0;
		matrixBoard[2][2] = 0;
		matrixBoard[2][3] = 0;
		matrixBoard[3][0] = 0;
		matrixBoard[3][1] = 0;
		matrixBoard[3][2] = 0;
		matrixBoard[3][3] = 12;
		matrixBoard[4][0] = 10;
		matrixBoard[4][1] = 9;
		matrixBoard[4][2] = 8;
		matrixBoard[4][3] = 7;
		for(int x = 0; x<5; x++) {
			for(int y = 0; y<4 ; y++) {
				myTiles[x][y].resetUpIcon();
			}
		}
	}
	
	// Pawn Promotion Menu methods.
	private void displayPromotionMenu(int team, Coordinates theLocation) {
		
		pauseGame = true;
		PawnMenu tempPawnPromotion = new PawnMenu(team);
		if(team==1) {
			setPromotionButtons(tempPawnPromotion, theLocation.getDown(), theLocation.getRight(), "team1");
		} else {
			setPromotionButtons(tempPawnPromotion, theLocation.getDown(), theLocation.getRight(), "team2");
		}
	}
	private void setPromotionButtons(PawnMenu menu, int down, int right, String team) {
		
		menu.getBishopButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myTiles[down][right].moveInPiece(new Bishop(team));
				myTiles[down][right].resetUpIcon();
				if(team.contentEquals("team1")) {
					matrixBoard[down][right] = 3;
				} else {
					matrixBoard[down][right] = 9;
				}
				setRegularLook();
				updateTurn();
				menu.close();
				setTurns();
				checkForWinner();
				checkForTie();
				pauseGame = false;
			}
		});
		menu.getKnightButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myTiles[down][right].moveInPiece(new Knight(team));
				myTiles[down][right].resetUpIcon();
				if(team.contentEquals("team1")) {
					matrixBoard[down][right] = 2;
				} else {
					matrixBoard[down][right] = 8;
				}
				setRegularLook();
				updateTurn();
				menu.close();
				setTurns();
				checkForWinner();
				checkForTie();
				pauseGame = false;
			}
		});
		menu.getRookButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myTiles[down][right].moveInPiece(new Rook(team));
				myTiles[down][right].resetUpIcon();
				if(team.contentEquals("team1")) {
					matrixBoard[down][right] = 4;
				} else {
					matrixBoard[down][right] = 10;
				}
				setRegularLook();
				updateTurn();
				menu.close();
				setTurns();
				checkForWinner();
				checkForTie();
				pauseGame = false;
			}
		});
	}
	
	// Internal logic methods.
	private void emptyHighlightMatrix() {
		
		for(int d = 0; d< 5; d++) {
			for(int r = 0; r< 4; r++) {
				toHighlightMatrix[d][r] = false;
			}
		}
	}
	private void copyToHighlightMatrix(boolean[][] copyFrom, boolean[][] copyTo) {
		
		for(int d = 0; d< 5; d++) {
			for(int r = 0; r< 4; r++) {
				copyTo[d][r] = copyFrom[d][r];
			}
		}
	}
	private void updateTurn() {
		
		if(turn == 1) {
			turn = 2;
		} else {
			turn = 1;
		}
	}
	private void updateTileAndMatrix(Coordinates Tile1, Coordinates Tile2){
		//Update Tile
		myTiles[Tile2.getDown()][Tile2.getRight()].moveInPiece(myTiles[Tile1.getDown()][Tile1.getRight()].getPiece());
		myTiles[Tile1.getDown()][Tile1.getRight()].moveInPiece(null);
		myTiles[Tile1.getDown()][Tile1.getRight()].resetUpIcon();
		myTiles[Tile2.getDown()][Tile2.getRight()].resetUpIcon();
		myTiles[Tile1.getDown()][Tile1.getRight()].setRegularLook();
		myTiles[Tile2.getDown()][Tile2.getRight()].setRegularLook();
		
		//Update Matrix
		int a = matrixBoard[Tile1.getDown()][Tile1.getRight()];
		matrixBoard[Tile1.getDown()][Tile1.getRight()] = 0;
		switch(a) {
		case 6: matrixBoard[Tile2.getDown()][Tile2.getRight()] = 5; SelectedPiece_coordinates = null; return;
		case 12: matrixBoard[Tile2.getDown()][Tile2.getRight()] = 11; SelectedPiece_coordinates = null; return;
		default: matrixBoard[Tile2.getDown()][Tile2.getRight()] = a; SelectedPiece_coordinates = null; return;
		}
	}	
	private Tile getTileWithKing(int team) {
		
		int a = 0;
		if(team==1) {
			a = 1;
		} else {
			a = 7;
		}
		for(int x = 0; x<5; x++) {
			for(int y = 0; y<4 ; y++) {
				if(myTiles[x][y].getPiece()!=null) {
					if(myTiles[x][y].getTypeOfPiece()==a) {
						return myTiles[x][y];
					}	
				}
			}
		}
		return null;
	}
	
	// Visual methods (GUI).
	private void setHighlights() {
		
		for(int d = 0; d < 5; d++) {
			for(int r = 0; r < 4; r++) {
				if(toHighlightMatrix[d][r]==true) {
					myTiles[d][r].setEnabledHighlight();
				}
			}
		}
	}
	private void setDark() {
	
		for(int x = 0; x<5; x++) {
			for(int y = 0; y<4 ; y++) {
				myTiles[x][y].setUnabledGray();
			}
		}
	}
	private void setRegularLook() {
		
		for(int down=0; down<5; down++) {
			for(int right=0; right<4; right++) {
				myTiles[down][right].setRegularLook();
			}
		}
	}	
	private void setTurns() {
		
		for(int down=0; down<5; down++) {
			for(int right=0; right<4; right++) {
				if(myTiles[down][right].getPiece()!=null) {
					if(myTiles[down][right].getTeamOfPiece()==turn) {
						myTiles[down][right].setRegularLook();
					} else {
					myTiles[down][right].getButton().setEnabled(false);	
					myTiles[down][right].getButton().setDisabledIcon(myTiles[down][right].getButton().getIcon());
					}	
				}
			}
		}
	}

}