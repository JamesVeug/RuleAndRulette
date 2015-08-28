package GameLogic.Characters;

public class Character {
	private float x;
	private float y;
	private Direction facingDirection = Direction.RIGHT;
	
	public Character(){
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(Direction facingDirection) {
		this.facingDirection = facingDirection;
	}
}
