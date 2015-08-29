package GameLogic;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;

import org.jbox2d.common.Vec2;

import GameLogic.Characters.Entity;

public class Game {
	
	public static List<Entity> DEBUG_ENTITY_LIST;
	
	private float speed = 2.5f;
	private float jumpspeed = 0.3f;
	
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
		rule = gameObjects.get(gameObjects.size()-2);
		rulette = gameObjects.get(gameObjects.size()-1);
		selectedCharacter = rule;
		
		DEBUG_ENTITY_LIST = gameObjects;
	}
	
	public void stopGame(){
		gameTimer.cancel();
	}
	
	/**
	 * Everytime the game iterates. This runs moving all the characters
	 * @param l
	 */
//	public void gameInteration(long l){
//		float timetaken = lasttime != 0 ? (l-lasttime) : l;
//		
//		// Check if Rule and Rulette have met
//		if( false ){
//			
//			// We have met, so stop the game and change level.
//			
//			// Pause for a second
//			
//			// Change level
//		}
//		
//		// Move all the tokens
//		for(int i = 0; i < gameObjects.size(); i++){
//			Entity e = gameObjects.get(i);
//			e.update(1f/timetaken);			
//		}
//		
//		lasttime = l;		
//	}
	
	/**
	 * GameLoop provides the accurate delta time we need to step our entities
	 * @param delta
	 */
	public void gameInteration(float delta){
		// Check if Rule and Rulette have met
		if( false ){
			
			// We have met, so stop the game and change level.
			
			// Pause for a second
			
			// Change level
		}
		
		Vec2 move = new Vec2();
		if(Input.isKeyDown(KeyEvent.VK_A)) move.x -= 1;
		if(Input.isKeyDown(KeyEvent.VK_D)) move.x += 1;
		move.mulLocal(speed);
		
		this.selectedCharacter.setVelocityX(move.x);
		
		move.x = -move.x;
		
		this.getNotSelectedCharacter().setVelocityX(move.x);
		
		if(Input.isKeyDown(KeyEvent.VK_SPACE)) {
			Input.removeKey(KeyEvent.VK_SPACE);
			if(selectedCharacter.touching > 0) {
				selectedCharacter.getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
			}
			if(getNotSelectedCharacter().touching > 0) {
				getNotSelectedCharacter().getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
			}
		}
		
		// Move all the tokens
		for(int i = 0; i < gameObjects.size(); i++){
			Entity e = gameObjects.get(i);
			e.update(delta);			
		}	
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
		
//		return selectedCharacter == rule ? rulette : rule;
	}

	public void setRulette(Entity rulette) {
		this.rulette = rulette;
	}
	
	public List<Entity> getEntities(){
		return gameObjects;
	}

	public void startMovingCharacterRight(){
//		selectedCharacter.setVelocityX(speed);
//		getNotSelectedCharacter().setVelocityX(-speed);
	}
	
	public void startMovingCharacterLeft(){
//		selectedCharacter.setVelocityX(-speed);
//		getNotSelectedCharacter().setVelocityX(speed);
	}
	
	public void startMovingCharacterUp(){ }
	public void startMovingCharacterDown(){ }
	
	public void stopMovingCharacterRight(){		
//		selectedCharacter.setVelocityX(0);
//		getNotSelectedCharacter().setVelocityX(0);
	}
	
	public void stopMovingCharacterLeft(){	
//		selectedCharacter.setVelocityX(0);
//		getNotSelectedCharacter().setVelocityX(0);
	}
	
	public void stopMovingCharacterUp(){ }
	public void stopMovingCharacterDown(){ }
	
	public void jumpMovingCharacter() {
//		if(selectedCharacter.touching > 0) {
//			selectedCharacter.getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
//		}
//		
//		if(getNotSelectedCharacter().touching > 0) {
//			getNotSelectedCharacter().getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
//		}
	}
}
