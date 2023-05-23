package pieces;

public class Pawn extends APiece{

	private boolean firstMove;
	
	public Pawn(String team){
		if(team.equalsIgnoreCase("team1")) {
			team1 = true;	
		}
		firstMove = true;
	}
	public void setFirstMove(boolean trueness) {	
		firstMove = trueness;
	}
	public int getTeam() {
	
		if(team1==true) {
			return 1;
		} else {
			return 2;
		}
	}
	public int getType() {
		
		if(team1 == true) {
			if(firstMove==true) {
				return 6;
			} else {
				return 5;	
			}
		} else {
			if(firstMove==true) {
				return 12;
			} else {
				return 11;	
			}
		}
	}

}