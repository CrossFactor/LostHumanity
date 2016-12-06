package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ChatBox {
	Image chatBox;
	String line1, line2, line3;
	private int lineX = 150;

	public ChatBox(Image chatBox, String line1, String line2, String line3) {
		setChatBox(chatBox);
		setLine1(line1);
		setLine2(line2);
		setLine3(line3);
	}

	public ChatBox(Image chatBox, String line1, String line2) {
		setChatBox(chatBox);
		setLine1(line1);
		setLine2(line2);
		setLine3("");
	}

	public ChatBox(Image chatBox, String line1) {
		setChatBox(chatBox);
		setLine1(line1);
		setLine2("");
		setLine3("");
	}

	public void render(Graphics g) {
		chatBox.draw(0, 0);
		g.drawString(line1, lineX, 515);
		g.drawString(line2, lineX, 535);
		g.drawString(line3, lineX, 555);
	}

	public Image getChatBox() {
		return chatBox;
	}

	public void setChatBox(Image chatBox) {
		this.chatBox = chatBox;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}
}
