package my.tdl.managers;

import java.awt.Color;
import java.awt.Graphics2D;

import my.tdl.main.Main;
import my.tdl.moveableObjects.Player;

public class HUDmanager {

	
	private Player player;
	public HUDmanager(Player player) {
		this.player=player;
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.width, Main.height / 6);
		g.fillRect(0, 750, Main.width, Main.height / 6);
		g.setColor(Color.WHITE);
		
		//g.drawString(player.getPos().xpos+"", 200, 200);
	}
}
