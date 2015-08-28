package GameLogic;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import GameLogic.Characters.Entity;
import phys.Physics;

public class Game {
	
	private static int ORDER_STARTMOVE = 0;
	private static int ORDER_STOPMOVE = 1;
	
	private static Direction BUTTON_RIGHT = Direction.RIGHT;
	private static Direction BUTTON_LEFT = Direction.LEFT;
	private static Direction BUTTON_UP = Direction.UP;
	private static Direction BUTTON_DOWN = Direction.DOWN;
	
	
	private Entity selectedCharacter;
	
	private Entity rule;
	private Entity rulette;
	
	private List GameObjects;
	private long lasttime = 0l;
	
	private Timer gameTimer;

	public Game(){
		
	}
	
	
	/**
	 * Starts a new thread and begins the game
	 */
	public void startGame(){
		
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				gameInteration(System.currentTimeMillis());
			}			
		}, 0, 60);
	}
	
	public void stopGame(){
		gameTimer.cancel();
	}
	
	private void gameInteration(long l){
		long timetaken = lasttime != 0 ? (l-lasttime) : l;
		System.out.println("Print: " + timetaken);
		lasttime = l;
	}

	public Entity getRule() {
		return rule;
	}

	public void setRule(Entity rule) {
		this.rule = rule;
	}


	public Entity getRulette() {
		return rulette;
	}


	public void setRulette(Entity rulette) {
		this.rulette = rulette;
	}
		
	private boolean orderCharacter(int order, Direction d, Entity c){
		//if( c.getFacingDirection() != c.getFacingDirection() ){
			//return false;
		//}
		
		if( order == ORDER_STOPMOVE ){
			//c.stopMoving(d);
			return true;
			
		}else if( order == ORDER_STARTMOVE ){
			//c.startMoving(d);
			return true;
			
		}
		
		
		return false;
	}

	public boolean startMovingCharacterRight(){	return orderCharacter(ORDER_STARTMOVE, BUTTON_RIGHT, selectedCharacter);	}
	public boolean startMovingCharacterLeft(){	return orderCharacter(ORDER_STARTMOVE, BUTTON_LEFT, selectedCharacter);	}
	public boolean startMovingCharacterUp(){		return orderCharacter(ORDER_STARTMOVE, BUTTON_UP, selectedCharacter);		}
	public boolean startMovingCharacterDown(){	return orderCharacter(ORDER_STARTMOVE, BUTTON_DOWN, selectedCharacter);	}
	
	public boolean stopMovingCharacterRight(){	return orderCharacter(ORDER_STOPMOVE, BUTTON_RIGHT, selectedCharacter);	}
	public boolean stopMovingCharacterLeft(){	return orderCharacter(ORDER_STOPMOVE, BUTTON_LEFT, selectedCharacter);	}
	public boolean stopMovingCharacterUp(){		return orderCharacter(ORDER_STOPMOVE, BUTTON_UP, selectedCharacter);		}
	public boolean stopMovingCharacterDown(){	return orderCharacter(ORDER_STOPMOVE, BUTTON_DOWN, selectedCharacter);	}
}
