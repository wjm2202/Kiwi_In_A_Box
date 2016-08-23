package my.tdl.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {
	
	private ArrayList<BufferedImage> frames;
	private volatile boolean running = false;
	public BufferedImage sprite;
	
	private long prevTime, speed;
	private int frameAtPause, currantFrame;

	public Animator(ArrayList<BufferedImage> frames) {
		this.frames=frames;
		
	}
	
	public void setSpeed(long speed)
	{
		this.speed=speed;
	}
	public void update(long time)
	{
		if (running)
		{
			if (time- prevTime >= speed)
			{
				currantFrame++;
				try{
					if(currantFrame <= frames.size())               //testing
					{
						sprite = frames.get(currantFrame);
					}
					else
					{
						reset();
					}
					
				}catch (IndexOutOfBoundsException e){
					reset();
					sprite = frames.get(currantFrame);
					//e.printStackTrace();
				}
				prevTime = time;
			}
		}
	}
	
	public void play()
	{
		running = true;
		prevTime = 0;
		frameAtPause =0;
		currantFrame =0;
	}
	public void stop()
	{
		running = false;
		prevTime = 0;
		frameAtPause =0;
		currantFrame =0;
	}
	public void pause()
	{
		frameAtPause = currantFrame;
		running = false;
	}
	public void resume()
	{
		currantFrame = frameAtPause;
	}
	
	public void reset()
	{
		currantFrame =0;
	}
	
	public boolean isDoneAnimating()
	{
		if(currantFrame == frames.size())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
