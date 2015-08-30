package Main;

import java.util.HashSet;
import java.util.concurrent.locks.LockSupport;

import phys.Physics;
import phys.Physics.HeartBox;
import phys.Physics.IDeadable;
import phys.Physics.PhysBox;
import GUI.GUIFrame;
import GUI.GUIGame;
import GUI.PixelImage;
import GameLogic.Game;
import GameLogic.Time;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;
import Resources.R;

/**
 * The GameLoop class provides abstract methods for you to implement and <br>
 * then calls these methods at regular intervals
 * @author Hamish Rae-Hodgson
 *
 */
public class GameLoop extends Thread {
	private boolean paused = false;
	private boolean running = true;
	private boolean runningSlow = false;
	
	//standard time
	//nanos
	private long timeGameStart;
	private long timeLastFrame;
	//seconds
	private float timeElapsed;
	private float deltaTime;
	
	//fixed time
	private float fixedDeltaTime;;
	private float fixedTime; //elapsed time in seconds
	
	private long syncRate;
	private Game game;
	private GUIGame panel;
	private GUIFrame frame;
	
	/**
	 * Creates a new GameLoop with the given parameters
	 * @param fps Desired frames per second.
	 * @param fixedUPS fixedUpdate() calls per second.
	 */
	public GameLoop(int fps, int fixedUPS, Game game, GUIGame panel, GUIFrame frame) {
		syncRate = (long) ((1D/fps)*1000000000); //fps in nanoseconds.
		fixedDeltaTime = 1f/fixedUPS;// fixed updates per seconds in seconds.
		
		this.game = game;
		this.panel = panel;
		this.frame = frame;
	}
	
	/**
	 * Returns true if the game is running slow.
	 * @return True if no time is spare after an update loop.
	 */
	public boolean isRunningSlow() {
		return runningSlow;
	}
	
	@Override
	public void run() {
		setupTime();
		while(running) {
			updateTime();
			while(fixedTime < timeElapsed) {
				fixedTick(fixedDeltaTime);
				fixedTime += fixedDeltaTime;
			}
			
			if( !isPaused() ){
				tick(deltaTime);
				render();
			}
			sleepforabit(syncRate-(System.nanoTime()-timeLastFrame)); //syncRate - (time taken)
		}
	}
	
	public void toggledPause(){
		paused = !paused;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void stopGameLoop(){
		running = false;
	}

	/**
	 * Sets up all the time fields.
	 */
	private void setupTime() {
		timeGameStart = System.nanoTime();
		timeLastFrame = timeGameStart;
		timeElapsed = 0;
		deltaTime = 0;
		fixedTime = 0;
	}
	
	/**
	 * Updates/Advances all the time fields.
	 */
	private void updateTime() {
		long now = System.nanoTime();
		timeElapsed = (now-timeGameStart)*0.000000001f;
		deltaTime = (now-timeLastFrame)*0.000000001f;
		timeLastFrame = now;
	}
	
	/**
	 * Called approximately every 1/fps seconds.
	 * @param delta The time since the last call to tick() in seconds.
	 */
	protected void tick(float delta){
		Time.tick(delta);
		
		game.gameInteration(delta);
	}
	
	/**
	 * Called exactly fixedUPS times a second. delta is always exactly 1/fixedUPS<br>
	 * delta critical work should go here - such as physics.
	 * @param delta The step to advance by.
	 */
	protected void fixedTick(float delta){
		//physics simulation stuff goes here
		Physics.world.step(delta, 6, 2);
		
		if(Physics.a != null) {
//			if(Physics.hearts) {
//				Physics.spawnHeart(Physics.a.getPosition().x, Physics.a.getPosition().y, 50);
//			} else {
				Physics.spawn(Physics.a.getPosition().x, Physics.a.getPosition().y, 100);
//			}
			
			Physics.a = null;
		}
		
		if(Physics.b != null) {
//			if(Physics.hearts) {
//				Physics.spawnHeart(Physics.b.getPosition().x, Physics.b.getPosition().y, 50);
//			} else {
				Physics.spawn(Physics.b.getPosition().x, Physics.b.getPosition().y, 100);
//			}
			
			Physics.b = null;
		}
		
		HashSet<Entity> deads = new HashSet<Entity>();
		
		for(Entity e : Physics.spawned) {
			e.update(delta);
			if(((IDeadable)e).isDead()) {
				deads.add(e);
			}
		}
		
//		Physics.spawned.removeAll(deads);
		
		for(Entity e : deads) {
//			Physics.world.destroyBody(e.getBody());
			if(e.getClass() == PhysBox.class) {
				Physics.PhysPool.free((PhysBox) e);
			} else if(e.getClass() == HeartBox.class) {
				Physics.HeartPool.free((HeartBox) e);
			} 
		}
		
//		System.out.println("PhysPool: " + Physics.PhysPool.size());
//		System.out.println("HeartPool: " + Physics.HeartPool.size());
	}
	
	/**
	 * You should do all your rendering from here.
	 */
	protected void render(){
		synchronized(GUIGame.CANVAS_LOCK) {
			panel.getCanvas().clear();
		
			PixelImage.blit(R.environment.level, panel.getCanvas(), 0, 0);
			
			for(Entity e : Physics.spawned) {
				e.render(panel.getCanvas());
			}
		
			if(game != null && game.getEntities() != null) {
				for(Entity e : game.getEntities()) {
					if( e.getClass() == Rule.class ){
						if( ((Rule)e).isDead() ){
							continue;
						}
					}
					
					if( e.getClass() == Rulette.class ){
						if( ((Rulette)e).isDead() ){
							continue;
						}
					}
					e.render(panel.getCanvas());
				}
			}
			
			panel.__RENDER();
	
			frame.repaint();
		}
	}
	
	/** 
	 * If nanos is greater than 0 sleeps the thread for the length of nanos.
	 * @param nanos Desired sleep duration in nanoseconds.
	 */
	private void sleepforabit(long nanos) {
		if(nanos > 0)  {
			runningSlow = false;
			LockSupport.parkNanos(nanos);
		} else {
			runningSlow = true;
		}
	}
	
}
