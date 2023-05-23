package pieces;

public abstract class APiece {

	protected boolean team1 = false;
	
	public abstract int getTeam();
	public abstract int getType();
	// TEAM 1 -> 1 = King // 2 = Knight // 3 = Bishop // 4 = Rook // 5 - Pawn // 6 - Pawn with double forward move
	// TEAM 2 -> 7 = King // 8 = Knight // 9 = Bishop // 10 = Rook // 11 - Pawn // 12 - Pawn with double forward move
	
}