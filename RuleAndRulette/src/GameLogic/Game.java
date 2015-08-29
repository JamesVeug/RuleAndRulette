package GameLogic;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;

import org.jbox2d.common.Vec2;

import phys.Physics;
import GameLogic.Characters.Entity;
import Resources.R;
import Sound.Sound;

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
	private int currentLevel;


	public Game(){
		
	}
	
	
	/**
	 * Starts a new thread and begins the game
	 */
	public void startGame(){

		
		currentLevel = 1;
		loadLevel(currentLevel);		
	}
	
	public void loadLevel(int number){
		
		// Remvoe world
		if( gameObjects != null ){
			for(Entity e : gameObjects){
				Physics.world.destroyBody(e.getBody());
			}
		}
		
		
		gameObjects = Level.load(number);
		rule = gameObjects.get(gameObjects.size()-2);
		rulette = gameObjects.get(gameObjects.size()-1);
		selectedCharacter = rule;
		DEBUG_ENTITY_LIST = gameObjects;
	}
	
	public void stopGame(){
		gameTimer.cancel();
	}
	
	/**
	 * GameLoop provides the accurate delta time we need to step our entities
	 * @param delta
	 */
	public void gameInteration(float delta){
		// Check if Rule and Rulette have met
		if( rule.getBounds().intersects(rulette.getBounds()) ){
			
			// We have met, so stop the game and change level.
						
			// Pause for a second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Change level			
			loadLevel(++currentLevel);
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
				Sound.playSound(R.sound.jump);
				selectedCharacter.getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
			}
			if(getNotSelectedCharacter().touching > 0) {
				Sound.playSound(R.sound.jump);
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
	}

	public void setRulette(Entity rulette) {
		this.rulette = rulette;
	}
	
	public List<Entity> getEntities(){
		return gameObjects;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void jump() {
		currentLevel++;
	}
}
