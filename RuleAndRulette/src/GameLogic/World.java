package GameLogic;

import java.util.ArrayList;
import java.util.List;

import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class World {
	
	public static List<Entity> CreateWorld(int level){
		switch(level){
			case 1: return getLevelOne();
			case 2: return getLevelTwo();
		}
		
		return null;
	}
	
	public static List<Entity> getLevelOne(){
//		List<Entity> world = new ArrayList<>();
//		
//		int scale = 1;
//		
//		// Create blocks
//		for( int i = 0; i < 32; i++){
//			Block block = new Block(i*scale, 4*scale);			
//			world.add(block);
//		}
//		
//		// Walls
//		world.add(new Block(0, 3*scale));
//		world.add(new Block(0, 2*scale));
//		world.add(new Block(31*scale, 3*scale));
//		world.add(new Block(31*scale, 2*scale));
//		
//		//
//		// Create Characters
//		// Characters need to be the last 2 in the list!
//		//
//		Rule rule = new Rule(1*scale,3*scale);
//		world.add(rule);
//		
//		Rulette rulette = new Rulette(30*scale,3*scale);
//		world.add(rulette); 
//		
////		return scaleEntities(world);
		
		return Level.load(4);
		
//		return world;
	}
	
	public static List<Entity> getLevelTwo(){
//		List<Entity> world = new ArrayList<>();
//		
//		// Create blocks
//		for( int i = 0; i < 32; i++){
//			Block block = new Block(i, 4);			
//			world.add(block);
//		}
//		
//		// Walls
//		world.add(new Block(0, 3));
//		world.add(new Block(0, 2));
//		world.add(new Block(31, 3));
//		world.add(new Block(31, 2));
//		
//		
//		//
//		// Create Characters
//		// Characters need to be the last 2 in the list!
//		//
//		Rule rule = new Rule(1,3);
//		world.add(rule);
//		
//		Rulette rulette = new Rulette(30,3);
//		world.add(rulette); 
		
//		return scaleEntities(world);
		
		return Level.load(1);
		
//		return world;
	}
	
	
	public static List<Entity> scaleEntities( List<Entity> list){
		
		for( Entity e : list ){
			e.setPosition(e.getPosition().mul(32));
		}
		
		return list;
		
	}

}
