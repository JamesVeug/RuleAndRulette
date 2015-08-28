package GameLogic;

import java.util.ArrayList;
import java.util.List;

import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class World {
	
	public static List<Entity> CreateWorld(){
		List<Entity> world = new ArrayList<>();
		
		// Create blocks
		for( int i = 0; i < 32; i++){
			Block block = new Block(i*32, 128);			
			world.add(block);
		}
		
		// Walls
		world.add(new Block(0, 128-32));
		world.add(new Block(0, 128-64));
		world.add(new Block(31*32, 128-32));
		world.add(new Block(31*32, 128-64));
		
		
		//
		// Create Characters
		// Characters need to be the last 2 in the list!
		//
		Rule rule = new Rule(1*32,96);
		world.add(rule);
		
		Rulette rulette = new Rulette(30*32,96);
		world.add(rulette);
		
		return world;
	}

}
