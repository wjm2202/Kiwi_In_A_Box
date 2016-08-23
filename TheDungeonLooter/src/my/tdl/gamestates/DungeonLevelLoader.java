package my.tdl.gamestates;

import java.awt.Graphics2D;


import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.gamestate.GameState;
import my.tdl.gamestate.GameStateManager;
import my.tdl.generator.World;
import my.tdl.main.Main;
import my.tdl.moveableObjects.Player;

public class DungeonLevelLoader extends GameState{
	World world;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		world = new World("world");
		world.setSize(100,100);
		world.addPlayer(new Player());
		world.init();
		world.generate("map_3");
		
	}

	@Override
	public void tick(double deltaTime) {
		world.tick(deltaTime);
	}

	@Override
	public void render(Graphics2D g) {
		world.render(g);
		g.clipRect(0, 0, Main.width, Main.height);
	}
}
