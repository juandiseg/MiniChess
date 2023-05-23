package pieces;

public class Rook extends APiece{
	
	public Rook(String team){
		if(team.equalsIgnoreCase("team1")) {
			team1 = true;	
		}
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
			return 4;
		} else {
			return 10;
		}
	}

}