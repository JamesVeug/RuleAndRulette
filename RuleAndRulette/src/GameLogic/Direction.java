package GameLogic;

public class Direction {
	public static final Direction RIGHT = new Direction(1);
	public static final Direction LEFT = new Direction(3);
	public static final Direction UP = new Direction(0);
	public static final Direction DOWN = new Direction(2);
	
	private int direction;
	
	
	public Direction(int direction) {
		// 0 up
		// 1 right
		// 2 down
		// 3 left
		this.direction = direction;
	}



	
	
	
	/**
	 * If direction is left, will turn it to right... etc
	 */
	public void flipDirection(){
		direction += 2;
		if( direction > 3 ){
			direction -= 4;
		}
	}
	
	/**
	 * If the direciton is left, will make it up... etc
	 * left -> up
	 * up -> right
	 */
	public void rotateClockwise(){
		direction += 1;
		if( direction > 3 ){
			direction -= 4;
		}
	}
	
	/**
	 * Reverse order of rotateclockwise
	 */
	public void rotateAntiClockwise(){
		direction -= 1;
		if( direction < 1 ){
			direction += 4;
		}
	}






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direction;
		return result;
	}

	@Override
	public String toString(){
		switch(direction){
			case 1: return "RIGHT";
			case 3: return "LEFT";
			case 0: return "UP";
			case 2: return "DOWN";
		};
		
		return "UNKNOWN DIRECTION: '+direction+'!";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direction other = (Direction) obj;
		if (direction != other.direction)
			return false;
		return true;
	}

	
}
