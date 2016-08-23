package my.tdl.gameloop;

import java.awt.Component;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.Vector2F;
import my.tdl.gamestate.GameStateManager;
import my.tdl.main.Assets;

public class GameLoop extends IDGameLoop {
	
	GameStateManager gsm;
	public static Assets assets = new Assets();
	//public static float xOffset;
	//public static float yOffset;
	public static Vector2F map = new Vector2F();
	

	public GameLoop(int width, int height) {
		super(width, height);
		
	}
	@Override
	public void init() {
		assets.init();
		Vector2F.setWorldVariables(map.xpos, map.ypos);
		gsm = new GameStateManager();
		gsm.init();
		super.init();
	}
	@Override
	public void tick(double deltaTime) {
		Vector2F.setWorldVariables(map.xpos, map.ypos);
		gsm.tick(deltaTime);
	}
	@Override
	public void render() {
		super.render();
		gsm.render(graphics2D);
		clear();
	}
	@Override
	public void clear() {
		super.clear();
	}
	
	
}