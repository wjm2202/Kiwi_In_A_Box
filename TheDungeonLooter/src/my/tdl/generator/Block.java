package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.tdl.main.Assets;

public class Block extends Rectangle{
	
	Vector2F pos = new Vector2F();
	private int BlockSize = 48;
	private BlockType blockType;
	private BufferedImage block;
	private boolean isSolid;                                  
	private boolean isAlive;
	private boolean dropped = false;

	public Block(Vector2F pos, BlockType blockType) {
		setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		this.blockType=blockType;
		this.pos=pos;
		isAlive = true;
		init();
	}
	
	

	public Block isSolid(boolean isSolid)
	{
		this.isSolid=isSolid;
		return this;
	}
	
	public void init()
	{
		switch(blockType){
		case STONE_1:
			block = Assets.getStone_1();
			break;
		case WALL_1:
			block = Assets.getWall_1();
			break;
		case ROBOT_1:
			block = Assets.getRobot_1();
			break;
		
		}
		
	}
	
	
	public void tick(double deltaTime)
	{
		if(isAlive)
		{
			setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		}
		
	}
	public void render(Graphics2D g)
	{
		if(isAlive)
		{
			//g.drawRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			g.drawImage(block, (int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize, null);
			
			if(isSolid)
			{
				g.drawRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			}
		}
		else
		{
			if(!dropped)
			{
				float xpos = pos.xpos +24 - 12;
				float ypos = pos.ypos +24 - 12;
				
				Vector2F newpos = new Vector2F(xpos, ypos);
				//World.dropBlockEntity(newpos, block);
				dropped = true;
			}
		}
		
	}
	
	public enum BlockType{
		STONE_1,
		WALL_1, 
		ROBOT_1
	}

	public boolean isSolid() {
		return isSolid;
	}
	public boolean isAlive() {
		return isSolid;
	}

	public void setAlive(boolean isAlive)
	{
		this.isAlive=isAlive;
	}
	
	
	
	
	
	
}
