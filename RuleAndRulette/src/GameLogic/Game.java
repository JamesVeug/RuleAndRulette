package GameLogic;

import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;

public class Game {
	private Rule rule;
	private Rulette rulette;
	

	public Game(){
		
	}


	public Rule getRule() {
		return rule;
	}


	public void setRule(Rule rule) {
		this.rule = rule;
	}


	public Rulette getRulette() {
		return rulette;
	}


	public void setRulette(Rulette rulette) {
		this.rulette = rulette;
	}
}
