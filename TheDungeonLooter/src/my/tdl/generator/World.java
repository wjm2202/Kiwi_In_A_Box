package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.generator.Block.BlockType;
import my.tdl.main.Main;
import my.tdl.moveableObjects.Player;

public class World {
	private String worldName;
	private BufferedImage map;
	private int world_width;
	private int world_height;
	private int blockSize = 48;
	private Player player;
	
	//Lists
	private CopyOnWriteArrayList<BlockEntity> blockents;
	TileManager tiles;
	//booleans
	private boolean hasSize = false;

	public World(String worldName) {
		
		this.worldName = worldName;
	}

	public void init() {
		 blockents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager();
		if(player!=null)
		{
			player.init(this);
		}
	}

	public void tick(double deltaTime) {
		tiles.tick(deltaTime);
		
		if(!blockents.isEmpty())
		{
			for(BlockEntity ent :blockents)
			{
				if(player.render.intersects(ent))
				{
					ent.tick(deltaTime);
					ent.setAlive(true);
				}
				else
				{
					ent.setAlive(false);
				}
				
			}
			
		}
		
		if(player!=null)
		{
			player.tick(deltaTime);
		}
		
		
	}

	public void render(Graphics2D g) {
		tiles.render(g);
		
		if(!blockents.isEmpty())
		{
			for(BlockEntity ent :blockents)
			{
				if(player.render.intersects(ent))
				{
					ent.render(g);
				}
				else
				{
					
				}
				
			}
			
		}
		
		
		if(player!=null)
		{
			player.render(g);
		}
		
	}


	public void generate(String world_image_name) {
		map=null;
		
		if(hasSize)
		{
			try{
				map = loadImageFrom.LoadImageFrom(Main.class, world_image_name+".png");
			}catch(Exception e){
				
			}
			for(int x=0;x<world_width;x++)
			{
				for(int y=0;y<world_height;y++)
				{
					int col = map.getRGB(x,y);
					switch(col & 0xffffff)
					{
					case 0x808080:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE_1));
						break;
					case 0x404040:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_1).isSolid(true));
						break;
					case 0x202020:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.ROBOT_1));
						break;
					}
				}
			}
		}
		
	}

	public void setSize(int world_width, int world_height) {
		this.world_width=world_width;
		this.world_height=world_height;
		hasSize = true;
	}

	public void addPlayer(Player player) {
		
		this.player=player;
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage block_image)
	{
		BlockEntity ent = new BlockEntity(pos, block_image);
		if(!blockents.contains(ent))
		{
			blockents.add(ent);
		}
	}
	public void removeDroppedBlockEntity(BlockEntity blockEntity)
	{
		if(blockents.contains(blockEntity))
		{
			blockents.remove(blockEntity);
		}
	}
	
	
	
	
}
