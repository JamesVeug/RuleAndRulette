package GameLogic;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import GameLogic.Characters.Entity;

public class Game {
	
	
	private Entity selectedCharacter;
	
	private Entity rule;
	private Entity rulette;
	
	private List<Entity> gameObjects;
	
	private long lasttime = 0l;
	
	private Timer gameTimer;

	public Game(){
		
	}
	
	
	/**
	 * Starts a new thread and begins the game
	 */
	public void startGame(){
		
		gameObjects = World.CreateWorld();
		
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
	
	/**
	 * Everytime the game iterates. This runs moving all the characters
	 * @param l
	 */
	private void gameInteration(long l){
		long timetaken = lasttime != 0 ? (l-lasttime) : l;
		//System.out.println("Print: " + timetaken);
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
	
	public Entity getNotSelectedCharacter(){
		if( selectedCharacter == rule ){
			return rulette;
		}
		
		return rule;
	}

	public void setRulette(Entity rulette) {
		this.rulette = rulette;
	}
	
	public List<Entity> getEntities(){
		return gameObjects;
	}

	public void startMovingCharacterRight(){	
		selectedCharacter.setVelocityX(1);
		getNotSelectedCharacter().setVelocityX(-1);
	}
	
	public void startMovingCharacterLeft(){		
		selectedCharacter.setVelocityX(-1);
		getNotSelectedCharacter().setVelocityX(1);
	}
	
	public void startMovingCharacterUp(){		
		selectedCharacter.setVelocityY(1);
		getNotSelectedCharacter().setVelocityY(-1);
	}
	
	public void startMovingCharacterDown(){	
		selectedCharacter.setVelocityY(-1);
		getNotSelectedCharacter().setVelocityY(1);
	}
	
	public void stopMovingCharacterRight(){		
		selectedCharacter.setVelocityX(0);
		getNotSelectedCharacter().setVelocityX(0);
	}
	
	public void stopMovingCharacterLeft(){	
		selectedCharacter.setVelocityX(0);
		getNotSelectedCharacter().setVelocityX(0);
	}
	
	public void stopMovingCharacterUp(){		
		selectedCharacter.setVelocityY(0);
		getNotSelectedCharacter().setVelocityY(0);	
	}
	
	public void stopMovingCharacterDown(){	
		selectedCharacter.setVelocityY(0);
		getNotSelectedCharacter().setVelocityY(0);
	}
}
