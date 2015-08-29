package GameLogic;

import java.util.ArrayList;
import java.util.List;

import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class Level {
	
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	public static final char KEY_BLOCK = '#';
	public static final char KEY_MOVEABLE_BLOCK = '@';
	public static final char KEY_BLANK = ' ';
	public static final char KEY_RULE = 'B';
	public static final char KEY_RULETTE = 'G';
	public static final char KEY_SPIKE = 'X';
	
	private static final String[] levels = new String[]{
		"################################\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#      B                       #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                       G      #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"################################\n",
		
		"################################\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#        B          G          #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#   ## ####      #    #        #\n" +
		"#   #            ######        #\n" +
		"#   #                          #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"################################\n",

		"################################\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#        B            G        #\n" +
		"#        @            @        #\n" +
		"#       @@@          @@@       #\n" +
		"#      @@@@@        @@@@@      #\n" +
		"#     @@@@@@@      @@@@@@@     #\n" +
		"#      #####        #####      #\n" +
		"#       ###          ###       #\n" +
		"#        #            #        #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"################################\n",		
		
		"################################\n" +
		"#                              #\n" +
		"#   @@@@@@@                    #\n" +
		"#   @     @                    #\n" +
		"#   @  B  @                    #\n" +
		"#   @     @                    #\n" +
		"#   @@@@@@@                    #\n" +
		"#      @             @#####@   #\n" +
		"#      @             @     @   #\n" +
		"#      @             @  G  @   #\n" +
		"#      @             @     @   #\n" +
		"#      @             #######   #\n" +
		"#      @             @  @  @   #\n" +
		"#      @             @  @  @   #\n" +
		"#      @             @  @  @   #\n" +
		"#      @             @  @  @   #\n" +
		"#      @             @  @  @   #\n" +
		"################################\n",
		
		"################################\n" +
		"#B                            G#\n" +
		"#  @   @  @##                  #\n" +
		"#  @   @  @                    #\n" +
		"#  @ @ @  @##                  #\n" +
		"#  @ @ @  @                    #\n" +
		"#  #####  ###                  #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"#                              #\n" +
		"################################\n",
		
	};
	
	public static List<Entity> load(int level) {
		level = level%levels.length;
		return load(levels[level]);
	}
	
	private static List<Entity> load(String level) {
		List<Entity> entities = new ArrayList<Entity>();
		
		String[] lines = level.split("\\n");
		
		Rule rule = null;
		Rulette rulette = null;
		
		for(int y = 0; y < lines.length; y++) {
			for(int x = 0; x < lines[y].length(); x++) {
				switch(lines[y].charAt(x)) {
				case KEY_BLOCK :
					entities.add(new Block(x*TILE_WIDTH, y*TILE_HEIGHT, true));
					break;
				case KEY_MOVEABLE_BLOCK :
					entities.add(new Block(x*TILE_WIDTH, y*TILE_HEIGHT, false));
					break;
				case KEY_RULE :
					rule = new Rule(x*TILE_WIDTH, y*TILE_HEIGHT);
					break;
				case KEY_RULETTE :
					rulette = new Rulette(x*TILE_WIDTH, y*TILE_HEIGHT);
					break;
				}
			}
		}
		
		entities.add(rule);
		entities.add(rulette);
		
		return entities;
	}

}

