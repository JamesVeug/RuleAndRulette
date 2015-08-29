package GUI;

import java.util.HashSet;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import phys.Physics;
import phys.Physics.IDeadable;
import GameLogic.Game;
import GameLogic.Time;
import GameLogic.Characters.Entity;
import Main.GameLoop;
import Resources.R;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -1822196509785001746L;

	GUIPanel entireScreenPanel; 
	Game game;

	private PixelImage canvas;
	
	public GUIFrame(){
		super();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.canvas = new PixelImage(1024, 576);
		
		entireScreenPanel = new GUIPanel(this.canvas);
		add(entireScreenPanel);
		
		
		setSize(1024,600);
		setVisible(true);
		entireScreenPanel.requestFocus();
		
		setupGame();
	}
	
	/**
	 * Sets up a new game
	 */
	public void setupGame(){
		game = new Game();
		
		entireScreenPanel.setGame(game);
		
		game.startGame();
		
		//starts the game loop
		GameLoop loop = new GameLoop(60, 50) {
//			World world;
//			{
//				world = new World(new Vec2(0, -9.8f));
//				
//				for(Entity e : game.getEntities()) {
//					BodyDef bdef = new BodyDef();
//					bdef.userData = e;
//					if(!(e instanceof Block)) { bdef.type = BodyType.DYNAMIC; }
//					bdef.position.set(e.getPosition());
//					Body body = world.createBody(bdef);
//					PolygonShape pshape = new PolygonShape();
//					pshape.setAsBox((float)(e.getBounds().getWidth()/2f), (float)(e.getBounds().getHeight()/2f));
//					body.createFixture(pshape, e instanceof Block ? 0 : 1);
//				}
//			}
			
			@Override
			protected void tick(float delta) {
				Time.tick(delta);
				
				game.gameInteration(delta);
				
				//System.out.println("Rule: " + game.getRule().touching + " Rulette " + game.getRulette().touching);
			}

			@Override
			protected void fixedTick(float delta) {
				//physics simulation stuff goes here
				Physics.world.step(delta, 6, 2);
				
				if(Physics.a != null) {
//					if(Physics.hearts) {
//						Physics.spawnHeart(Physics.a.getPosition().x, Physics.a.getPosition().y, 50);
//					} else {
						Physics.spawn(Physics.a.getPosition().x, Physics.a.getPosition().y, 100);
//					}
					
					Physics.a = null;
				}
				
				if(Physics.b != null) {
//					if(Physics.hearts) {
//						Physics.spawnHeart(Physics.b.getPosition().x, Physics.b.getPosition().y, 50);
//					} else {
						Physics.spawn(Physics.b.getPosition().x, Physics.b.getPosition().y, 100);
//					}
					
					Physics.b = null;
				}
				
				HashSet<Entity> deads = new HashSet<Entity>();
				
				for(Entity e : Physics.spawned) {
					e.update(delta);
					if(((IDeadable)e).isDead()) {
						deads.add(e);
					}
				}
				
				Physics.spawned.removeAll(deads);
				
				for(Entity e : deads) {
					Physics.world.destroyBody(e.getBody());
				}
			}

			@Override
			protected void render() {
				canvas.clear();
				
				PixelImage.blit(R.environment.level, canvas, 0, 0);
				
				for(Entity e : game.getEntities()) {
					e.render(canvas);
				}
				
				for(Entity e : Physics.spawned) {
					e.render(canvas);
				}
				
//				boolean first = true;
//				for(Iterator<Entity> iter = Physics.spawned.iterator(); iter.hasNext();) {
//					
//					if(first) {
//						Entity e = iter.next();
//						Physics.world.destroyBody(e.getBody());
//						iter.remove();
//						first = false;
//					} else {
//						Entity entity = iter.next();
//						entity.getBody().getFixtureList().setSensor(true);
//						break;
//					}
//				}

				entireScreenPanel.repaint();
			}
			
		};
		
		loop.start();
		
//		gameTimer = new Timer();
//		gameTimer.schedule(new TimerTask(){
//
//			@Override
//			public void run() {
//				game.gameInteration(System.currentTimeMillis());
//				entireScreenPanel.repaint();
//			}			
//		}, 0, 20);
	}
}
