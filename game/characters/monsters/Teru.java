package game.characters.monsters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import game.characters.BattleCharacterInfo;

public class Teru extends Monster {
	/*
	 * hp = 80
	 * damage = 10
	 * widthBattle = 100
	 * heightBattle = 80
	 * hitboxWidth = 20
	 * hitboxHeight = 55
	 * distanceFromTop = 4
	 * hurtboxWidth = ?
	 * gapFromCenter = ?
	 */
	public Teru() throws SlickException {
		super(new BattleCharacterInfo("Teru", 80, 10, 100, 80, 20, 55, 4, 10, 0, 10));
	}
}