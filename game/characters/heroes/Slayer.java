package game.characters.heroes;

import java.io.File;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.SpriteSheet;

public class Slayer extends Hero {
	public static final Slayer slayer = new Slayer();
	SpriteSheet moveUp, moveDown, moveRight, idleLeft, idleRight, attackLeft, attackRight;

	public Slayer() {
		super("slayer", 100);
		/*try {
			File file = new File("bin\\res\\slayer\\out\\moveUp.png");
			if (file.exists()) 
				System.out.println("asaas");
			SpriteSheet moveUp = new SpriteSheet("bin/res/slayer/out/moveUp.png", 45, 80);
			SpriteSheet moveDown = new SpriteSheet("bin/res/slayer/out/moveDown.png", 45, 80);
			SpriteSheet moveLeft = new SpriteSheet("bin/res/slayer/out/moveLeft.png", 45, 80);
			SpriteSheet moveRight = new SpriteSheet("bin/res/slayer/out/moveRight.png", 45, 80);
			SpriteSheet idleLeft = new SpriteSheet("bin/res/slayer/attack/idleLeft.png", 180, 170);
			SpriteSheet idleRight = new SpriteSheet("bin/res/slayer/attack/idleRight.png", 180, 170);
			SpriteSheet attackLeft = new SpriteSheet("bin/res/slayer/attack/attackLeft.png", 180, 170);
			SpriteSheet attackRight = new SpriteSheet("bin/res/slayer/attack/attackRight.png", 180, 170);
			super.setSpriteSheets(moveUp, moveDown, moveLeft, moveRight, idleLeft, idleRight, attackLeft, attackRight);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "Missing img in Slayer", "Error", 2, 0);
		}*/

	}
}
