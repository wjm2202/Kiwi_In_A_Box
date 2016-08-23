package my.tdl.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {

	
	SpriteSheet blocks = new SpriteSheet();
	SpriteSheet robot = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();
	
	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	public static BufferedImage robot_1;
	public static BufferedImage mouse_pressed;
	public static BufferedImage mouse_released;
	public static BufferedImage button_heldover;
	public static BufferedImage button_notover; 
	
	public void init()
	{
		
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "stone_1_sprite_sheet16x16x16.png" ));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "player_1.png" ));
		robot.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "player_1.png" ));
		
		
		stone_1 = blocks.getTile(0, 0, 16, 16);  //make a tile
		wall_1 = blocks.getTile(16, 0, 16, 16);  //make a tile
		robot_1 = blocks.getTile(64, 0, 16, 16);
		
		mouse_pressed = player.getTile(64, 0, 16, 16);
		mouse_released = player.getTile(48, 0, 16, 16);
		
		button_heldover = player.getTile(0, 112, 80, 16);
		button_notover = player.getTile(0, 128, 80, 16);
	}
	public static BufferedImage getRobot_1() {
		return robot_1;
	}
	public static BufferedImage getStone_1() {
		return stone_1;
	}
	public static BufferedImage getWall_1() {
		return wall_1;
	}
	public static BufferedImage getMouse_pressed()
	{
		return mouse_pressed;
	}
	public static BufferedImage getMouse_released()
	{
		return mouse_released;
	}
	public static BufferedImage getButton_heldover() {
		return button_heldover;
	}
	public static BufferedImage getButton_notover() {
		return button_notover;
	}
}
