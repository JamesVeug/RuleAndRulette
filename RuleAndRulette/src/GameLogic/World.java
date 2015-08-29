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
		return Level.load(4);
	}
	
	public static List<Entity> getLevelTwo(){
		return Level.load(1);
	}
	
	
	public static List<Entity> scaleEntities( List<Entity> list){
		
		for( Entity e : list ){
			e.setPosition(e.getPosition().mul(32));
		}
		
		return list;
		
	}

}
