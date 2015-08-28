package GameLogic;

import java.util.List;
import java.util.Timer;
import GameLogic.Characters.Entity;

public class Game {
	
	private float speed = 2.5f;
	private float jumpspeed = 5f;
	
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
		
		gameObjects = World.CreateWorld(1);
		rule = gameObjects.get(gameObjects.size()-1);
		rulette = gameObjects.get(gameObjects.size()-2);
		selectedCharacter = rule;
	}
	
	public void stopGame(){
		gameTimer.cancel();
	}
	
	/**
	 * Everytime the game iterates. This runs moving all the characters
	 * @param l
	 */
	public void gameInteration(long l){
		long timetaken = lasttime != 0 ? (l-lasttime) : l;
		
		for(int i = 0; i < gameObjects.size(); i++){
			Entity e = gameObjects.get(i);
			e.update(1/timetaken);			
		}
		
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
		selectedCharacter.setVelocityX(-speed);
		getNotSelectedCharacter().setVelocityX(speed);
	}
	
	public void startMovingCharacterLeft(){		
		selectedCharacter.setVelocityX(speed);
		getNotSelectedCharacter().setVelocityX(-speed);
	}
	
	public void startMovingCharacterUp(){		
		selectedCharacter.setVelocityY(-jumpspeed);
		getNotSelectedCharacter().setVelocityY(-jumpspeed);
	}
	
	public void startMovingCharacterDown(){	
	//		selectedCharacter.setVelocityY(-speed);
	//		getNotSelectedCharacter().setVelocityY(-speed);
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
	//		selectedCharacter.setVelocityY(0);
	//		getNotSelectedCharacter().setVelocityY(0);
	}
}
