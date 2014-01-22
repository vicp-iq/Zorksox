package Zgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BackgroundObject extends Sprite
{
	float rotationSpeed;
	
	public BackgroundObject(String file, double x, double y, double depth, double speed, float rotationSpeed, String name) throws SlickException 
	{
		super((int)x, (int)y, 0);
		setGraphic(file);
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.rotationSpeed = rotationSpeed;
		this.name = name;
	}
	
	public BackgroundObject() throws SlickException
	{
		super(0,0,0);
	}
	
	public void update()
	{
		graphic = setAbsoluteScale(this);
		setRelativePosition(this);
		
		if (Game.isParallax && Game.pShip.movementVector.length() > Game.lDeadZone)
		{
			defaultY -= (Game.pad.getYAxisValue()) * Game.delta * depth * Game.pSpeed * Game.scale ;
			defaultX -= (Game.pad.getXAxisValue()) * Game.delta * depth * Game.pSpeed * Game.scale;
		}
	}

	public void draw(Graphics g)
	{
		graphic.draw((int)x, (int)y);
	}
}
