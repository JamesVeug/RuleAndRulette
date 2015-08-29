package GameLogic;

public class Score {

	private static long currentScore = 0;
	
	public static void addScore(int amount){
		currentScore += amount;
	}
	
	public static void subtractScore(int amount){
		currentScore -= amount;
	}
	
	public static void resetScore(){
		currentScore = 0;
	}
	
	public static long getScore(){
		return currentScore;
	}
}
