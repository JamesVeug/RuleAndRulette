package GameLogic;

import java.util.ArrayList;
import java.util.List;

import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class World {
	
	public static List<Entity> scaleEntities( List<Entity> list){
		
		for( Entity e : list ){
			e.setPosition(e.getPosition().mul(32));
		}
		
		return list;
		
	}

}
