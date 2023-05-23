package logic;

public class Coordinates {
 
	private int down;
	private int right;
	
	public Coordinates(int dCor, int rCor){
		down = dCor;
		right = rCor;
	}
	public int getRight() {
		return right;
	}
	public int getDown() {
		return down;
	}
}
