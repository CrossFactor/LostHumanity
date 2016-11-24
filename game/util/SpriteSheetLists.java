package game.util;

import java.util.List;

import org.newdawn.slick.*;
public class SpriteSheetLists {
	List<SpriteSheet> move;
	List<SpriteSheet> idle;
	List<SpriteSheet> battleMove;
	
	public SpriteSheetLists(List<SpriteSheet> move, List<SpriteSheet> idle, List<SpriteSheet> battleMove){
		this.move = move;
		this.idle = idle;
		this.battleMove = battleMove;
	}
}
