package logic;
import java.util.Stack;

public class MovementCalculator {

	private Stack<int[][]> stackPossibleBoards;
	
	public MovementCalculator(){
		stackPossibleBoards = new Stack<int[][]>();
	}
	
	public boolean checkSafeMove(int[][] matrix, Coordinates previousCoord, int newDown, int newRight, int team) {
	// Checks if a given move would cause the player to expose his king.
		int[][]	temp = {{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},};
		for(int d=0; d<5; d++) {
			for(int r=0; r<4; r++) {
				temp[d][r] = matrix[d][r];
			}
		}
		temp[newDown][newRight] = temp[previousCoord.getDown()][previousCoord.getRight()];
		temp[previousCoord.getDown()][previousCoord.getRight()] = 0;
		if(isOnCheck(temp, team)) {
			return false;
		} else {
			return true;
		}
	}	
	public boolean isOnCheck(int[][] matrix, int team) {
		
		if(team==1) {
			boolean[][] temp = allPossibleMovements(matrix, 2);
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrix[down][right] == 1 && temp[down][right] == true) {
						return true;
					}
				}
			}
			return false;
		} else {
			boolean[][] temp = allPossibleMovements(matrix, 1);
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrix[down][right] == 7 && temp[down][right] == true) {
						return true;
					}
				}
			}
			return false;
		}
	}
	private boolean[][] allPossibleMovements(int[][] matrix, int team){
	// Gets all the possible movements a team can make.
		boolean[][] tempMatrix = createEmptyBoolean();
		if(team==1) {
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrix[down][right]<7 && matrix[down][right] != 0) {
						addUpMatrices(tempMatrix, callPossibleFor(matrix, matrix[down][right], down, right));
					}
				}
			}
			return tempMatrix;
		} else {
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrix[down][right]>6) {
						addUpMatrices(tempMatrix, callPossibleFor(matrix, matrix[down][right], down, right));
					}
				}
			}
			return tempMatrix;
		}
	}
	private void addUpMatrices(boolean[][] matrix1, boolean[][] matrix2) {
	// If a content of matrix2 is true copies it to matrix1.
		for(int down=0; down<5; down++) {
			for(int right=0; right<4; right++) {
				if(matrix2[down][right] == true) {
					matrix1[down][right] = true;
				}
			}
		}	
	}	
	public boolean isOnCheckMate(int[][] matrix, int team) {
		
		stackPossibleBoards.clear();
		setStack(matrix, team);
		while (stackPossibleBoards.empty()!=true) {
			if(isOnCheck(stackPossibleBoards.pop(), team)==false) {
				return false;
			}
		}
		return true;
	}
	
	private void setStack(int[][] matrixOriginal, int team) {
	// Calls "pushNewPossibleBoards()" for every piece in the matrix that is part of the same team.
		if(team==1) {
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrixOriginal[down][right] < 7 && matrixOriginal[down][right] != 0) {
						pushNewPossibleBoards(matrixOriginal, callPossibleFor(matrixOriginal, matrixOriginal[down][right], down, right), down, right);
					}
				}
			}
		} else {
			for(int down=0; down<5; down++) {
				for(int right=0; right<4; right++) {
					if(matrixOriginal[down][right] > 6) {
						pushNewPossibleBoards(matrixOriginal, callPossibleFor(matrixOriginal, matrixOriginal[down][right], down, right), down, right);
					}
				}
			}
		}
	}
	private void pushNewPossibleBoards(int[][] matrixINT, boolean[][] matrixPossible, int down, int right) {
	// Creates a matrix of what the board would look like for every possible movement of every piece of a team.
		for(int d=0; d<5; d++) {
			for(int r=0; r<4; r++) {
				if(matrixPossible[d][r] == true) {
					stackPossibleBoards.push(createSwappedPosition(matrixINT, down, right, d, r));
				}
			}
		}
	}
	private int[][] createSwappedPosition(int[][] matrixINT, int oldDown, int oldRight, int newDown, int newRight){
	// Creates a matrix of what the board would look like for the given movement.
		int[][]	temp = {{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0},};
		
		for(int d=0; d<5; d++) {
			for(int r=0; r<4; r++) {
				temp[d][r] = matrixINT[d][r];
			}
		}
		temp[newDown][newRight] = temp[oldDown][oldRight];
		temp[oldDown][oldRight] = 0;		
		return temp;
	}
	
	public boolean[][] callPossibleFor(int[][] matrix, int piece, int down, int right){
	// Redirects each piece to its "getMovements" function.
		switch(piece) {
		case 1: return getMovementsKingTeam1(matrix, down, right);
		case 2: return getMovementsKnightTeam1(matrix, down, right);
		case 3: return getMovementsBishopTeam1(matrix, down, right);
		case 4: return getMovementsRookTeam1(matrix, down, right);
		case 5: return getMovementsPawnTeam1(matrix, down, right, false);
		case 6: return getMovementsPawnTeam1(matrix, down, right, true);
		case 7: return getMovementsKingTeam2(matrix, down, right);
		case 8: return getMovementsKnightTeam2(matrix, down, right);
		case 9: return getMovementsBishopTeam2(matrix, down, right);
		case 10: return getMovementsRookTeam2(matrix, down, right);
		case 11: return getMovementsPawnTeam2(matrix, down, right, false);
		case 12: return getMovementsPawnTeam2(matrix, down, right, true);
		default: return null;
		}
	}
	private boolean[][] getMovementsBishopTeam1(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(down!=4 && right!=0) {
			int tempDown = down+1; int tempRight = right-1;
			while(tempDown<5 && tempRight>-1) {
				if(matrix[tempDown][tempRight]>6 || matrix[tempDown][tempRight]==0) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown++; tempRight--;
			}
		}
		if(down!=0 && right!=3) {	
			int tempDown = down-1; int tempRight = right+1;
			while(tempDown>-1 && tempRight<4) {
				if(matrix[tempDown][tempRight]>6 || matrix[tempDown][tempRight]==0) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown--; tempRight++;
			}
		}
		if(down!=4 && right!=3) {	
			int tempDown = down+1; int tempRight = right+1;
			while(tempDown<5 && tempRight<4) {
				if(matrix[tempDown][tempRight]>6 || matrix[tempDown][tempRight]==0) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown++; tempRight++;
			}
		}
		if(down!=0 && right!=0) {	
			int tempDown = down-1; int tempRight = right-1;
			while(tempDown>-1 && tempRight>-1) {
				if(matrix[tempDown][tempRight]>6 || matrix[tempDown][tempRight]==0) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown--; tempRight--;
			}
		}
		return tempMatrix;
	}
	private boolean[][] getMovementsBishopTeam2(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		
		if(down!=4 && right!=0) {
			int tempDown = down+1; int tempRight = right-1;
			while(tempDown<5 && tempRight>0) {
				if(matrix[tempDown][tempRight]<7 || matrix[tempDown][tempRight]==0) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown++; tempRight--;
			}
		}
		if(down!=0 && right!=3) {	
			int tempDown = down-1; int tempRight = right+1;
			while(tempDown>-1 && tempRight<4) {
				if(matrix[tempDown][tempRight]<7) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown--; tempRight++;
			}
		}
		if(down!=4 && right!=3) {	
			int tempDown = down+1; int tempRight = right+1;
			while(tempDown<5 && tempRight<4) {
				if(matrix[tempDown][tempRight]<7) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown++; tempRight++;
			}
		}
		if(down!=0 && right!=0) {	
			int tempDown = down-1; int tempRight = right-1;
			while(tempDown>-1 && tempRight>-1) {
				if(matrix[tempDown][tempRight]<7) {
					tempMatrix[tempDown][tempRight] = true;
				}
				if(matrix[tempDown][tempRight] != 0) {
					break;
				}
				tempDown--; tempRight--;
			}
		}
		return tempMatrix;
	}	
	private boolean[][] getMovementsKingTeam1(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(down != 0) {
			if(matrix[down-1][right]>6 || matrix[down-1][right]==0) {
				tempMatrix[down-1][right] = true;
			}
			if(right!=0) {
				if(matrix[down-1][right-1]>6 || matrix[down-1][right-1]==0) {
					tempMatrix[down-1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down-1][right+1]>6 || matrix[down-1][right+1]==0) {
					tempMatrix[down-1][right+1] = true;
				}
			}
		}
		if(right!=0) {
			if(matrix[down][right-1]>6 || matrix[down][right-1]==0) {
				tempMatrix[down][right-1] = true;
			}
		}
		if(right!=3) {
			if(matrix[down][right+1]>6 || matrix[down][right+1]==0) {
				tempMatrix[down][right+1] = true;	
			}
		}
		if(down != 4) {
			if(matrix[down+1][right]>6 || matrix[down+1][right]==0) {
				tempMatrix[down+1][right] = true;	
			}
			if(right!=0) {
				if(matrix[down+1][right-1]>6 || matrix[down+1][right-1]==0) {
					tempMatrix[down+1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down+1][right+1]>6 || matrix[down+1][right+1]==0) {
					tempMatrix[down+1][right+1] = true;
				}
			}
		}
		return tempMatrix;
	}	
	private boolean[][] getMovementsKingTeam2(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(down != 0) {
			if(matrix[down-1][right]<7) {
				tempMatrix[down-1][right] = true;
			}
			if(right!=0) {
				if(matrix[down-1][right-1]<7) {
					tempMatrix[down-1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down-1][right+1]<7) {
					tempMatrix[down-1][right+1] = true;
				}
			}
		}
		if(right!=0) {
			if(matrix[down][right-1]<7) {
				tempMatrix[down][right-1] = true;
			}
		}
		if(right!=3) {
			if(matrix[down][right+1]<7) {
				tempMatrix[down][right+1] = true;	
			}
		}
		if(down != 4) {
			if(matrix[down+1][right]<7) {
				tempMatrix[down+1][right] = true;	
			}
			if(right!=0) {
				if(matrix[down+1][right-1]<7) {
					tempMatrix[down+1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down+1][right+1]<7) {
					tempMatrix[down+1][right+1] = true;
				}
			}
		}
		return tempMatrix;
	}
	private boolean[][] getMovementsKnightTeam1(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(down<=2 && right!=0) {
			if(matrix[down+2][right-1] > 6 || matrix[down+2][right-1]==0) {
				tempMatrix[down+2][right-1] = true;
			}
		}
		if(down<=2 && right!=3) {
			if(matrix[down+2][right+1] > 6 || matrix[down+2][right+1]==0) {
				tempMatrix[down+2][right+1] = true;
			}
		}
		if(down>1 && right!=0) {
			if(matrix[down-2][right-1] > 6 || matrix[down-2][right-1]==0) {
				tempMatrix[down-2][right-1] = true;
			}
		}
		if(down>1 && right!=3) {
			if(matrix[down-2][right+1] > 6 || matrix[down-2][right+1]==0) {
				tempMatrix[down-2][right+1] = true;
			}
		}
		if(down!=4 && right>1) {
			if(matrix[down+1][right-2] > 6 || matrix[down+1][right-2]==0) {
				tempMatrix[down+1][right-2] = true;
			}
		}
		if(down!=4 && right<2) {
			if(matrix[down+1][right+2] > 6 || matrix[down+1][right+2]==0) {
				tempMatrix[down+1][right+2] = true;
			}
		}
		if(down!=0 && right>1) {
			if(matrix[down-1][right-2] > 6 || matrix[down-1][right-2]==0) {
				tempMatrix[down-1][right-2] = true;
			}
		}
		if(down!=0 && right<2) {
			if(matrix[down-1][right+2] > 6 || matrix[down-1][right+2]==0) {
				tempMatrix[down-1][right+2] = true;
			}
		}
		return tempMatrix;
	}	
	private boolean[][] getMovementsKnightTeam2(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(down<=2 && right!=0) {
			if(matrix[down+2][right-1] < 7) {
				tempMatrix[down+2][right-1] = true;
			}
		}
		if(down<=2 && right!=3) {
			if(matrix[down+2][right+1] < 7) {
				tempMatrix[down+2][right+1] = true;
			}
		}
		if(down>1 && right!=0) {
			if(matrix[down-2][right-1] < 7) {
				tempMatrix[down-2][right-1] = true;
			}
		}
		if(down>1 && right!=3) {
			if(matrix[down-2][right+1] < 7) {
				tempMatrix[down-2][right+1] = true;
			}
		}
		if(down!=4 && right>1) {
			if(matrix[down+1][right-2] < 7) {
				tempMatrix[down+1][right-2] = true;
			}
		}
		if(down!=4 && right<2) {
			if(matrix[down+1][right+2] < 7) {
				tempMatrix[down+1][right+2] = true;
			}
		}
		if(down!=0 && right>1) {
			if(matrix[down-1][right-2] < 7) {
				tempMatrix[down-1][right-2] = true;
			}
		}
		if(down!=0 && right<2) {
			if(matrix[down-1][right+2] < 7) {
				tempMatrix[down-1][right+2] = true;
			}
		}
		return tempMatrix;
	}
	private boolean[][] getMovementsPawnTeam1(int[][] matrix, int down, int right, boolean firstMove) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(firstMove == true) {
			if(matrix[down+1][right]==0 && matrix[down+2][right]==0) {
				tempMatrix[down+2][right] = true;	
			}
		}
		if(down!=4) {
			if(matrix[down+1][right]==0) {
				tempMatrix[down+1][right] = true;
			}
			if(right!=0) {
				if(matrix[down+1][right-1] > 6) {
					tempMatrix[down+1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down+1][right+1] > 6) {
					tempMatrix[down+1][right+1] = true;
				}
			}
		}
		return tempMatrix;
	}
	private boolean[][] getMovementsPawnTeam2(int[][] matrix, int down, int right, boolean firstMove) {

		boolean[][] tempMatrix = createEmptyBoolean();
		if(firstMove == true) {
			if(matrix[down-1][right]==0 && matrix[down-2][right]==0) {
				tempMatrix[down-2][right] = true;	
			}
		}
		if(down!=0) {
			if(matrix[down-1][right] == 0) {
				tempMatrix[down-1][right] = true;
			}
			if(right!=0) {
				if(matrix[down-1][right-1] < 7 && matrix[down-1][right-1] !=0) {
					tempMatrix[down-1][right-1] = true;
				}
			}
			if(right!=3) {
				if(matrix[down-1][right+1] < 7 && matrix[down-1][right+1] != 0) {
					tempMatrix[down-1][right+1] = true;
				}
			}
		}
		return tempMatrix;
	}
	private boolean[][] getMovementsRookTeam1(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();	
		int dSaved = down, rSaved = right, aux;
		if(right!=3) {
			for(aux=right+1; aux<4;aux++) {
				if(matrix[down][aux] > 6 || matrix[down][aux] == 0) {
					tempMatrix[down][aux] = true;
					if(matrix[down][aux] != 0) {
						break;
					}
				}
				if(matrix[down][aux] < 7 && matrix[down][aux] != 0) {
					break;
				}	
			}
		}
		down = dSaved; right = rSaved;
		if(right!=0) {
			for(aux=right-1; aux>-1;aux--) {
				if(matrix[down][aux] > 6 || matrix[down][aux] == 0) {
					tempMatrix[down][aux] = true;
					if(matrix[down][aux] != 0) {
						break;
					}
				}
				if(matrix[down][aux] < 7 && matrix[down][aux] != 0) {
					break;
				}
			}
		}
		down = dSaved; right = rSaved;
		if(down!=4) {
			for(aux=down+1; aux<5;aux++) {
				if(matrix[aux][right] > 6 || matrix[aux][right] == 0) {
					tempMatrix[aux][right] = true;	
					if(matrix[aux][right] != 0) {
						break;
					}
				}
				if(matrix[aux][right] < 7 && matrix[aux][right] != 0) {
					break;
				}
			}
		}
		down = dSaved; right = rSaved;
		if(down!=0) {
			for(aux=down-1; aux>-1;aux--) {
				if(matrix[aux][right] > 6 || matrix[aux][right] == 0) {
					tempMatrix[aux][right] = true;
					if(matrix[aux][right] != 0) {
						break;
					}
				}
				if(matrix[aux][right] < 7 && matrix[aux][right] != 0) {
					break;
				}
			}
		}
	return tempMatrix;
}	
	private boolean[][] getMovementsRookTeam2(int[][] matrix, int down, int right) {

		boolean[][] tempMatrix = createEmptyBoolean();	
		int dSaved = down, rSaved = right, aux;
		if(right!=3) {
			for(aux=right+1; aux<4;aux++) {
				if(matrix[down][aux] < 7) {
					tempMatrix[down][aux] = true;
					if(matrix[down][aux] != 0) {
						break;
					}
				}
				if(matrix[down][aux] > 6) {
					break;
				}	
			}
		}
		down = dSaved; right = rSaved;
		if(right!=0) {
			for(aux=right-1; aux>-1;aux--) {
				if(matrix[down][aux] < 7) {
					tempMatrix[down][aux] = true;
					if(matrix[down][aux] != 0) {
						break;
					}
				}
				if(matrix[down][aux] > 6) {
					break;
				}
			}
		}
		down = dSaved; right = rSaved;
		if(down!=4) {
			for(aux=down+1; aux<5;aux++) {
				if(matrix[aux][right] < 7) {
					tempMatrix[aux][right] = true;	
					if(matrix[aux][right] != 0) {
						break;
					}
				}
				if(matrix[aux][right] > 6) {
					break;
				}
			}
		}
		down = dSaved; right = rSaved;
		if(down!=0) {
			for(aux=down-1; aux>-1;aux--) {
				if(matrix[aux][right] < 7) {
					tempMatrix[aux][right] = true;
					if(matrix[aux][right] != 0) {
						break;
					}
				}
				if(matrix[aux][right] > 6) {
					break;
				}
			}
		}
	return tempMatrix;
}
		
	private boolean[][] createEmptyBoolean(){
		boolean[][] tempMatrix = 	{{false, false, false, false},
									 {false, false, false, false},
									 {false, false, false, false},
									 {false, false, false, false},
									 {false, false, false, false},};
		return tempMatrix;
	}

}