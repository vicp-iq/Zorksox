package Zgame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Zgame.Game.ScrollType;

public class Background
{
	Image bg;
	int x;
	int y;
	double alpha;
	boolean isScrolling = false;
	double speed;
	float pointer = 0;
	Game.ScrollType scrollType;
	
	public Background(String file, int x, int y, double alpha, ScrollType scrollType, double speed) throws SlickException 
	{
		bg = new Image(file);
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		this.speed = speed;
		this.scrollType = scrollType;
		bg.setAlpha((float)alpha);
	}
	
	public void scroll(double value)
	{
		y += value;
	}
	
	public void update()
	{		
		if (scrollType == ScrollType.VERTICAL)
			y += speed;
		else if (scrollType == ScrollType.HORIZONTAL)
			x += speed;
		else if (scrollType == ScrollType.PARALLAX)
		{
			//more code goes here
		}
	}
	
	public void draw()
	{
		bg.draw(x, y, Gvars.width, Gvars.height);
	}
}
