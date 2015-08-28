package GameLogic;

import java.util.ArrayList;
import java.util.List;

import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class World {
	
	public static List<Entity> CreateWorld(){
		List<Entity> world = new ArrayList<>();
		
		// Create blocks
		for( int i = 0; i < 32; i++){
			Entity block = new Entity(i*32, 128);			
			world.add(block);
		}
		
		// Create Characters
		Rule rule = new Rule(0,96);
		world.add(rule);
		
		Rulette rulette = new Rulette(32*32,96);
		world.add(rulette);
		
		return world;
	}

}
