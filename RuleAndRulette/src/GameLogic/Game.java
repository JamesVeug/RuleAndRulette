package GameLogic;

import GameLogic.Characters.Direction;
import GameLogic.Characters.MoveableCharacter;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Character;
import GameLogic.Characters.Rulette;

public class Game {
	
	private static int ORDER_STARTMOVE = 0;
	private static int ORDER_STOPMOVE = 1;
	
	private static Direction BUTTON_RIGHT = Direction.RIGHT;
	private static Direction BUTTON_LEFT = Direction.LEFT;
	private static Direction BUTTON_UP = Direction.UP;
	private static Direction BUTTON_DOWN = Direction.DOWN;
	
	
	private Rule rule;
	private Rulette rulette;
	

	public Game(){
		
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}


	public Rulette getRulette() {
		return rulette;
	}


	public void setRulette(Rulette rulette) {
		this.rulette = rulette;
	}
	
	public boolean startMovingCharacterRight(MoveableCharacter c){	return orderCharacter(ORDER_STARTMOVE, BUTTON_RIGHT, c);	}
	public boolean startMovingCharacterLeft(MoveableCharacter c){	return orderCharacter(ORDER_STARTMOVE, BUTTON_LEFT, c);	}
	public boolean startMovingCharacterUp(MoveableCharacter c){		return orderCharacter(ORDER_STARTMOVE, BUTTON_UP, c);		}
	public boolean startMovingCharacterDown(MoveableCharacter c){	return orderCharacter(ORDER_STARTMOVE, BUTTON_DOWN, c);	}
	
	public boolean stopMovingCharacterRight(MoveableCharacter c){	return orderCharacter(ORDER_STOPMOVE, BUTTON_RIGHT, c);	}
	public boolean stopMovingCharacterLeft(MoveableCharacter c){	return orderCharacter(ORDER_STOPMOVE, BUTTON_LEFT, c);	}
	public boolean stopMovingCharacterUp(MoveableCharacter c){		return orderCharacter(ORDER_STOPMOVE, BUTTON_UP, c);		}
	public boolean stopMovingCharacterDown(MoveableCharacter c){	return orderCharacter(ORDER_STOPMOVE, BUTTON_DOWN, c);	}
	
	private boolean orderCharacter(int order, Direction d, MoveableCharacter c){
		if( c.getFacingDirection() != c.getFacingDirection() ){
			return false;
		}
		
		if( order == ORDER_STOPMOVE ){
			c.stopMoving(d);
			return true;
			
		}else if( order == ORDER_STARTMOVE ){
			c.startMoving(d);
			return true;
			
		}
		
		
		return false;
	}
}
