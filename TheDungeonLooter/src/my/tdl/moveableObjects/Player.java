package my.tdl.moveableObjects;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.gamestate.GameStateButton;
import my.tdl.generator.World;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUImanager;
import my.tdl.managers.HUDmanager;
import my.tdl.managers.Mousemanager;

public class Player implements KeyListener {

	Vector2F pos;
	private World world;
	private int width = 32;
	private int height = 32;
	private int scale =2;
	
	private static boolean up,down,left,right,running;
	private float maxSpeed = 3*32F;


	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	

	private float slowDown = 4.093F;

	private float fixDt = 1F/60F;
	private long animationSpeed = 180;
	
	Mousemanager playerMM = new Mousemanager();
	/*
	 * 
	 * rendering
	 */
	private int renderDistenceW =48;
	private int renderDistenceH =28;
	public static Rectangle render;
	
	//todo
	private int animationState =0;
	/*
	 * 0 =up
	 * 1 =down
	 * 2 =right
	 * 3 =left
	 * 4 =idel
	 */
	private ArrayList<BufferedImage> listup;
	Animator ani_up;
	private ArrayList<BufferedImage> listdown;
	Animator ani_down;
	private ArrayList<BufferedImage> listleft;
	Animator ani_left;
	private ArrayList<BufferedImage> listright;
	Animator ani_right;
	private ArrayList<BufferedImage> listidle;
	Animator ani_idle;
	
	private HUDmanager hudm;
	private GUImanager guim;
	
	//private boolean mapMove = true;

	public Player() {
		
		pos = new Vector2F(Main.width/2 - width/2, Main.height/2 - height/2);

	}

	public void init(World world) {
		hudm = new HUDmanager(this);
		guim = new GUImanager();
		this.world=world;
		
		//System.out.println(world.getWorldName()+"");
		
		render = new Rectangle((int) (pos.xpos- pos.getWorldLocation().xpos+pos.xpos -renderDistenceW*32/2+width/2), 
				(int) (pos.ypos- pos.getWorldLocation().ypos+pos.ypos -renderDistenceH*32/2+height/2),
				renderDistenceW*32, renderDistenceH*32);
		

		listup = new ArrayList<BufferedImage>();
		listdown = new ArrayList<BufferedImage>();
		listleft = new ArrayList<BufferedImage>();
		listright = new ArrayList<BufferedImage>();
		listidle = new ArrayList<BufferedImage>();
		
		//adding animation here note add left and right walking player spritesheet
		listup.add(Assets.player.getTile(0, 0, 16, 16));   //2nd x top
		listup.add(Assets.player.getTile(16, 0, 16, 16));  //2nd y bottom
		listup.add(Assets.player.getTile(32, 0, 16, 16));  //2nd y bottom
		
		listdown.add(Assets.player.getTile(0, 16, 16, 16));   //2nd x top
		listdown.add(Assets.player.getTile(16, 16, 16, 16));  //2nd y bottom
		listdown.add(Assets.player.getTile(32, 16, 16, 16));  //2nd y bottom
		
		listright.add(Assets.player.getTile(0, 32, 16, 16));   //2nd x top
		listright.add(Assets.player.getTile(16, 32, 16, 16));  //2nd y bottom
		listright.add(Assets.player.getTile(32, 32, 16, 16));  //2nd y bottom
		
		listleft.add(Assets.player.getTile(0, 48, 16, 16));   //2nd x top
		listleft.add(Assets.player.getTile(16, 48, 16, 16));  //2nd y bottom
		listleft.add(Assets.player.getTile(32, 48, 16, 16));  //2nd y bottom
		
		listidle.add(Assets.player.getTile(0, 64, 16, 16)); //idle d2
		listidle.add(Assets.player.getTile(16, 64, 16, 16)); //idle d2
		listidle.add(Assets.player.getTile(32, 64, 16, 16)); //idle d2
		listidle.add(Assets.player.getTile(48, 64, 16, 16)); //idle d2
		listidle.add(Assets.player.getTile(64, 64, 16, 16)); //idle d2
		
		//up
		ani_up = new Animator(listup);
		ani_up.setSpeed(animationSpeed);
		ani_up.play();
		//down
		ani_down = new Animator(listdown);
		ani_down.setSpeed(animationSpeed);
		ani_down.play();
		//left
		ani_left = new Animator(listleft);
		ani_left.setSpeed(animationSpeed);
		ani_left.play();
		//right
		ani_right = new Animator(listright);
		ani_right.setSpeed(animationSpeed);
		ani_right.play();
		//idle
		ani_idle = new Animator(listidle);
		ani_idle.setSpeed(animationSpeed);
		ani_idle.play();
		

	}

	public void tick(double deltaTime) {
		
		playerMM.tick();
		//button1.tick();
		render = new Rectangle(
				(int) (pos.xpos- pos.getWorldLocation().xpos+pos.xpos -renderDistenceW*32/2+width/2), 
				(int) (pos.ypos- pos.getWorldLocation().ypos+pos.ypos -renderDistenceH*32/2+height/2),
				renderDistenceW*32, renderDistenceH*32);

		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);

		
		if(up)
		{
			moveMapUp(moveAmountu);
			animationState = 0;
		}
		else
		{
			moveMapUpGlide(moveAmountu);
		}
		if(down)
		{
			moveMapDown(moveAmountd);
			animationState = 1;
		}
		else
		{
			moveMapDownGlide(moveAmountd);
		}
		if(left)
		{
			moveMapLeft(moveAmountl);
			animationState = 3;
		}
		else
		{
			moveMapLeftGlide(moveAmountl);
		}
		if(right)
		{
			moveMapRight(moveAmountr);
			animationState = 2;
		}
		else
		{
			moveMapRightGlide(moveAmountr);
		}
		if(!up && !down && !right && !left)
		{
			/*
			 * standing still
			 */
			animationState =4;
		}
		if (running)
		{
			if(animationSpeed != 100)
			{
				animationSpeed = 100;
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed+=64;
			}
		}
		else
		{
			if (animationSpeed != 180)
			{
				animationSpeed = 180;
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed-=64;
			}
		}
		
	}
	public void playerMoverCode()
	{
		/*if(!mapMove)
		{
			if(up)                                         //map moves
			{
				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								(int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
								(int) (pos.ypos + GameLoop.map.ypos - moveAmountu)) )){
					if(speedUp < maxSpeed){
						speedUp += slowDown;
					}else{
						speedUp = maxSpeed;
					}

					pos.ypos-=moveAmountu;
				}
				else
				{
					speedUp = 0;
				}



			}	
			else
			{
				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								(int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
								(int) (pos.ypos + GameLoop.map.ypos - moveAmountu)) )){
					if(speedUp !=0)
					{
						speedUp -= slowDown;

						if(speedUp< 0)
						{
							speedUp =0;
						}
					}

					pos.ypos-=moveAmountu;
				}else
				{
					speedUp = 0;
				}



			}


			if(down){

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								(int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
								(int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)) )){

					if(speedDown < maxSpeed)
					{
						speedDown += slowDown;
					}
					else
					{
						speedDown = maxSpeed;
					}
					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}




			}
			else
			{

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								(int) (pos.ypos + GameLoop.map.ypos +height + moveAmountd)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
								(int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)) )){

					if(speedDown !=0)
					{
						speedDown -= slowDown;

						if(speedDown< 0)
						{
							speedDown =0;
						}
					}

					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}



			}
			if(left)
			{

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								(int) (pos.ypos + GameLoop.map.ypos +height)),
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								(int) (pos.ypos + GameLoop.map.ypos)) )){
					if(speedLeft < maxSpeed)
					{
						speedLeft += slowDown;
					}
					else
					{
						speedLeft = maxSpeed;
					}
					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}


			}
			else
			{

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								(int) (pos.ypos + GameLoop.map.ypos +height)),
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								(int) (pos.ypos + GameLoop.map.ypos)) )){

					if(speedLeft !=0)
					{
						speedLeft -= slowDown;

						if(speedLeft< 0)
						{
							speedLeft =0;
						}
					}

					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}



			}
			if(right)
			{

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								(int) (pos.ypos + GameLoop.map.ypos)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								(int) (pos.ypos + GameLoop.map.ypos + height)) )){


					if(speedRight < maxSpeed)
					{
						speedRight += slowDown;
					}
					else
					{
						speedRight = maxSpeed;
					}
					pos.xpos+=moveAmountr;

				}else{
					speedRight = 0;
				}


			}
			else
			{

				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								(int) (pos.ypos + GameLoop.map.ypos)),
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								(int) (pos.ypos + GameLoop.map.ypos + height)) )){

					if(speedRight !=0)
					{
						speedRight -= slowDown;

						if(speedRight< 0)
						{
							speedRight =0;
						}
					}

					pos.xpos+=moveAmountr;
				}else{
					speedRight = 0;
				}



			}
		}
		else                                              //player moves 
		{
			
		}*/
	}
 	public void moveMapUp(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						(int) (pos.ypos + GameLoop.map.ypos - speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
						(int) (pos.ypos + GameLoop.map.ypos - speed)) )){
			if(speedUp < maxSpeed)
			{
				speedUp += slowDown;
			}
			else
			{
				speedUp = maxSpeed;
			}

			GameLoop.map.ypos-=speed;
		}else{
			speedUp =0;
		}

	}
	public void moveMapUpGlide(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						(int) (pos.ypos + GameLoop.map.ypos - speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
						(int) (pos.ypos + GameLoop.map.ypos - speed)) )){

			if(speedUp !=0)
			{
				speedUp -= slowDown;

				if(speedUp< 0)
				{
					speedUp =0;
				}
			}

			GameLoop.map.ypos-=speed;
		}else{
			speedUp = 0;
		}
	}
	public void moveMapDown(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)) )){

			if(speedDown < maxSpeed)
			{
				speedDown += slowDown;
			}
			else
			{
				speedDown = maxSpeed;
			}
			GameLoop.map.ypos+=speed;
		}else{
			speedDown =0;
		}
	}
	public void moveMapDownGlide(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) ,
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)) )){

			if(speedDown !=0)
			{
				speedDown -= slowDown;

				if(speedDown< 0)
				{
					speedDown =0;
				}
			}

			GameLoop.map.ypos+=speed;
		}else{
			speedDown = 0;
		}
	}
	public void moveMapRight(float speed)
	{
		


		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						(int) (pos.ypos + GameLoop.map.ypos)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						(int) (pos.ypos + GameLoop.map.ypos + height)) )){

			if(speedRight < maxSpeed)
			{
				speedRight += slowDown;
			}
			else
			{
				speedRight = maxSpeed;
			}
			GameLoop.map.xpos+=speed;
		}else{
			speedRight =0;
		}



	}
	public void moveMapRightGlide(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						(int) (pos.ypos + GameLoop.map.ypos)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						(int) (pos.ypos + GameLoop.map.ypos + height)) )){

			if(speedRight !=0)
			{
				speedRight -= slowDown;

				if(speedRight< 0)
				{
					speedRight =0;
				}
			}

			GameLoop.map.xpos+=speed;
		}else{
			speedRight =0;
		}
	}
	public void moveMapLeft(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						(int) (pos.ypos + GameLoop.map.ypos +height)),
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						(int) (pos.ypos + GameLoop.map.ypos)) )){
			if(speedLeft < maxSpeed)
			{
				speedLeft += slowDown;
			}
			else
			{
				speedLeft = maxSpeed;
			}
			GameLoop.map.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}
	public void moveMapLeftGlide(float speed)
	{
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						(int) (pos.ypos + GameLoop.map.ypos +height)),
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						(int) (pos.ypos + GameLoop.map.ypos)) )){

			if(speedLeft !=0)
			{
				speedLeft -= slowDown;

				if(speedLeft< 0)
				{
					speedLeft =0;
				}
			}

			GameLoop.map.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}
	
	//GameStateButton button1 = new GameStateButton(50,50); //button location
	
	public void render(Graphics2D g) {

		//g.fillRect((int)pos.xpos, (int)pos.ypos, width, height);
		
		
		//g.clipRect(0, 0, Main.width, Main.height);  //clip screen size
		//up
		if(animationState == 0)
		{
			g.drawImage(ani_up.sprite, (int)pos.xpos-width/2, (int)pos.ypos-height, width*scale, height*scale, null);
			if(up)
			{
				ani_up.update(System.currentTimeMillis());
			}
			
		}
		//down
		if(animationState == 1)
		{
			g.drawImage(ani_down.sprite, (int)pos.xpos-width/2, (int)pos.ypos-height, width*scale, height*scale, null);
			if (down)
			{
				ani_down.update(System.currentTimeMillis());
			}
		}
		//right
		if(animationState == 2)
		{
			
			g.drawImage(ani_right.sprite, (int)pos.xpos - width/2, (int)pos.ypos-height, width*scale, height*scale, null);
			if (right)
			{
				ani_right.update(System.currentTimeMillis());
			}
		}
		//left
		if(animationState == 3)
		{
			g.drawImage(ani_left.sprite, (int)pos.xpos - width/2, (int)pos.ypos -height, width*scale, height*scale, null);
			if (left)
			{ 
				ani_left.update(System.currentTimeMillis());
			}
		}
		//idle
		if(animationState == 4)
		{
			g.drawImage(ani_idle.sprite, (int)pos.xpos-width/2, (int)pos.ypos-height, width*scale, height*scale, null);
			ani_idle.update(System.currentTimeMillis());
			
		}
		g.drawRect((int)pos.xpos -renderDistenceW*32/2+width/2, (int)pos.ypos-renderDistenceH*32/2+height/2, renderDistenceW*32, renderDistenceH*32);
		guim.render(g);
		hudm.render(g);
		//button1.render(g);
		playerMM.render(g);
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W)
		{
			up = true;
		}
		if(key == KeyEvent.VK_S)
		{
			down = true;
		}
		if(key == KeyEvent.VK_A)
		{
			left = true;
		}
		if(key == KeyEvent.VK_D)
		{
			right = true;
		}
		if(key == KeyEvent.VK_SHIFT)
		{
			running = true;
		}
		if(key == KeyEvent.VK_ESCAPE)
		{
			System.exit(1);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W)
		{
			up = false;
		}
		if(key == KeyEvent.VK_S)
		{
			down = false;
		}
		if(key == KeyEvent.VK_A)
		{
			left = false;
		}
		if(key == KeyEvent.VK_D)
		{
			right = false;
		}
		if(key == KeyEvent.VK_SHIFT)
		{
			running = false;
		}

	}
	@Override
	public void keyTyped(KeyEvent e) {


	}

	public Vector2F getPos() {
		return pos;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public float getSlowDown() {
		return slowDown;
	}
	
	
}
