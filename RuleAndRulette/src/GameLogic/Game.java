package GameLogic;

import java.awt.event.KeyEvent;
import java.util.List;

import org.jbox2d.common.Vec2;

import phys.Physics;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;
import Resources.R;
import Sound.Sound;

public class Game {
	public static final int GAMESTATUS_PLAYING = 0;
	public static final int GAMESTATUS_FAILED = 1;
	public static final int GAMESTATUS_WON = 2;
	
	private float speed = 2.5f;
	private float jumpspeed = 0.3f;
	
	private Entity selectedCharacter;
	
	private Rule rule;
	private Rulette rulette;
	
	private List<Entity> gameObjects;
	
	private long lasttime = 0l;
	private long levelTransitionTime = 3000;
	
	private int gameStatus = GAMESTATUS_PLAYING;
	private int currentLevel;


	public Game(){
		
	}
	
	
	/**
	 * Starts a new thread and begins the game
	 */
	public void startGame(){
		
		Score.resetScore();

		Sound.playMusic(R.sound.music.getRandom());
		
		currentLevel = 0;
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
		rule = (Rule) gameObjects.get(gameObjects.size()-2);
		rulette = (Rulette) gameObjects.get(gameObjects.size()-1);
		selectedCharacter = rule;
		gameStatus = GAMESTATUS_PLAYING;
	}
	
	public void resetLevel(){
		loadLevel(currentLevel);
	}
	
	public void stopGame(){
		
		// Remove world
		if( gameObjects != null ){
			for(Entity e : gameObjects){
				Physics.world.destroyBody(e.getBody());
			}
		}
		
		Sound.stopAllMusic();
		Sound.stopAllSounds();
		
	}
	
	private void gameWon(){
		
		
		// We have met, so stop the game and change level.
					
		// Pause for a second
		if( gameStatus == Game.GAMESTATUS_PLAYING ){
			gameStatus = GAMESTATUS_WON;
			lasttime = System.currentTimeMillis();
			
			Score.addScore(100000);
			
			if(!Physics.hearts) {
				Physics.spawnHeart(this.getRule().getPosition().x, this.getRule().getPosition().y, 50);
				Physics.spawnHeart(this.getRulette().getPosition().x, this.getRulette().getPosition().y, 50);
			}
			
			rule.setDead(true);
			Physics.world.destroyBody(rule.getBody());
			
			rulette.setDead(true);
			Physics.world.destroyBody(rulette.getBody());
			
			Physics.hearts = true;
			
			Sound.playSound(R.sound.effects.win);
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
	}
	
	private void gameLost(){
		Physics.hearts = false;
		
					
		// Pause for a second
		if( gameStatus == Game.GAMESTATUS_PLAYING ){
			
			gameStatus = GAMESTATUS_FAILED;
			lasttime = System.currentTimeMillis();
			
			// We have met, so stop the game and change level.
			Sound.playSound(R.sound.effects.lose);
			
			Score.subtractScore(Score.getScore()/2);
			
			if( rule.isDead() ){
				Physics.spawnBrokenHeart(this.getRule().getPosition().x, this.getRule().getPosition().y, 50);
				Physics.world.destroyBody(rule.getBody());
			}else if( rulette.isDead() ){
				Physics.world.destroyBody(rulette.getBody());				
			}
		}
		
		long timetaken = System.currentTimeMillis() - lasttime;
		if( timetaken < levelTransitionTime  ){
			return;
		}
		
		
		// Change level			
		loadLevel(currentLevel);
		
		// Reset time
		lasttime = 0l;
		
		Physics.hearts = false;
	}
	
	/**
	 * GameLoop provides the accurate delta time we need to step our entities
	 * @param delta
	 */
	public void gameInteration(float delta){
		// Check if Rule and Rulette have met
		if( rule.getBounds().intersects(rulette.getBounds()) || gameStatus == GAMESTATUS_WON){
			
			gameWon();			
			return;
		}
		else if( rule.isDead() || rulette.isDead() || gameStatus == GAMESTATUS_FAILED ){
			
			// If we have died
			gameLost();
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
		
		
		// SOUND
		if( Input.isKeyDownOnce(KeyEvent.VK_PAGE_UP) ){
			Sound.increaseClipVolume();
		}
		else if( Input.isKeyDownOnce(KeyEvent.VK_PAGE_DOWN) ){
			Sound.decreaseClipVolume();
		}
		else if( Input.isKeyDownOnce(KeyEvent.VK_HOME) ){
			Sound.increaseMusicVolume();
		}
		else if( Input.isKeyDownOnce(KeyEvent.VK_END) ){
			Sound.decreaseMusicVolume();
		}
		else if( Input.isKeyDownOnce(KeyEvent.VK_INSERT) ){
			if( Sound.isMusicMuted() ) Sound.unmuteMusic();
			else Sound.muteMusic();
		}
		else if( Input.isKeyDownOnce(KeyEvent.VK_DELETE) ){
			if( Sound.isClipsMuted() ) Sound.unmuteClips();
			else Sound.muteClips();
		}
		
		
		// Reset Game
		if( Input.isKeyDownOnce(KeyEvent.VK_R) ){
			resetLevel();
		}
	}

	public Entity getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
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

	public void setRulette(Rulette rulette) {
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
