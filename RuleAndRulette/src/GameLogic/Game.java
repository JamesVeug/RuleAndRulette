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
	public static final int GAMESTATUS_PLAYING = 0;
	public static final int GAMESTATUS_FAILED = 1;
	public static final int GAMESTATUS_WON = 2;
	
	private float speed = 2.5f;
	private float jumpspeed = 0.3f;
	
	private Entity selectedCharacter;
	
	private Entity rule;
	private Entity rulette;
	
	private List<Entity> gameObjects;
	
	private long lasttime = 0l;
	private long levelTransitionTime = 100;
	
	private int gameStatus = GAMESTATUS_PLAYING;
	private Timer gameTimer;
	private int currentLevel;


	public Game(){
		
	}
	
	
	/**
	 * Starts a new thread and begins the game
	 */
	public void startGame(){
		

		Sound.playSound(R.sound.music.getRandom());
		
		currentLevel = 1;
		loadLevel(currentLevel);		
	}
	
	public void loadLevel(int number){
		
		// Remove world
		if( gameObjects != null ){
			for(Entity e : gameObjects){
				Physics.world.destroyBody(e.getBody());
			}
		}
		
		
		gameObjects = Level.load(number);
		rule = gameObjects.get(gameObjects.size()-2);
		rulette = gameObjects.get(gameObjects.size()-1);
		selectedCharacter = rule;
		gameStatus = GAMESTATUS_PLAYING;
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
			
			Physics.hearts = true;
			
			Physics.spawnHeart(this.getRule().getPosition().x, this.getRule().getPosition().y, 25);
			Physics.spawnHeart(this.getRulette().getPosition().x, this.getRulette().getPosition().y, 25);
			
			// We have met, so stop the game and change level.
						
			// Pause for a second
			gameStatus = GAMESTATUS_WON;
			
			if( lasttime == 0l ){
				lasttime = System.currentTimeMillis();
			}
			
			long timetaken = System.currentTimeMillis() - lasttime;
			if( timetaken < levelTransitionTime  ){
				return;
			}
			
			// Change level			
			loadLevel(++currentLevel);
			
			// Reset time
			lasttime = 0l;
			
			Physics.hearts = false;
			
			return;
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
				Sound.playSound(R.sound.effects.jump);
				selectedCharacter.getBody().applyLinearImpulse(new Vec2(0, -jumpspeed), selectedCharacter.getBody().getLocalCenter());
			}
			if(getNotSelectedCharacter().touching > 0) {
				Sound.playSound(R.sound.effects.jump);
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


	public int getStatus() {
		return gameStatus;
	}
}
