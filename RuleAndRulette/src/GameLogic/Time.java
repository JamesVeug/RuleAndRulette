package GameLogic;

public class Time {
	
	public static float deltaTime = 0f;
	public static float time = 0f;

	public static void tick(float delta) {
		deltaTime = delta;
		time += delta;
	}
	
}
