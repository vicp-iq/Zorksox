package Zgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

public class PlayerShip extends Sprite
{
	double deltaX;
	double deltaY;
	Input input = new Input(0);
	EnemyShip target = null;
	boolean targeting = false; 
	Vector2f targetAngle;
	Vector2f movementVector = new Vector2f(0, 0);
	Vector2f rotationVector = new Vector2f(0, 0);
	
	
	public PlayerShip(double x, double y, double angle) throws SlickException
	{
		super(x,y,0);
	}
	
	public void update(EnemyShips eShips)
	{		
		movementVector.set(Game.pad.getXAxisValue(), Game.pad.getYAxisValue());
		rotationVector.set(Game.pad.getRXAxisValue(), Game.pad.getRYAxisValue());
			
		//right stick controls
		if (rotationVector.length() > Game.rDeadZone)
		{
			vector.y = Game.pad.getRYAxisValue();
			vector.x = Game.pad.getRXAxisValue();
		}
		
		//left stick controls
		if (!Game.isParallax) //This whole block of code is the controls for a fixed screen game.  Probably wont be used.
		{	
			if (movementVector.length() > Game.lDeadZone)
			{	
				if (movementVector.length() > 1)
					movementVector = movementVector.getNormal();
				
				y += (movementVector.y - (movementVector.getNormal().y * Game.lDeadZone)) * Game.delta;
				x += (movementVector.x - (movementVector.getNormal().x * Game.lDeadZone)) * Game.delta;
				
				boundingBox.setX((float) (boundingBox.getX() + (movementVector.x - (movementVector.getNormal().x * Game.lDeadZone))) * Game.delta);
				boundingBox.setY((float) (boundingBox.getY() + (movementVector.y - (movementVector.getNormal().y * Game.lDeadZone))) * Game.delta);
			}
			
			if (x < 0)
				x = 1;
			if (x > Gvars.width - graphic.getWidth())
				x = Gvars.width - graphic.getWidth() - 1;
			if (y < 0)
				y = 1;
			if (y > Gvars.height - graphic.getHeight())
				y = Gvars.height - graphic.getHeight() - 1;
		}
		else  //These are the controls for the open world game.
		{
			if (Game.pad.isButtonPressed(1) && Game.scale <= 5)
			{
				Game.pSpeed = Game.pSpeed * 0.99;
				Game.scale = (float) (Game.scale * 1.01);
			}
			
			if (Game.pad.isButtonPressed(2) && Game.scale >= 0.1)
			{
				Game.pSpeed = Game.pSpeed * 1.01;
				Game.scale = (float) (Game.scale * 0.99);
			}
				
			x = Gvars.width/2 - graphic.getWidth()/2;
	    	y = Gvars.height/2 - graphic.getHeight()/2;
		}
		
		//Implementation of the targeting system
		if (Game.pad.isButtonPressed(0) && !targeting)
		{
			target = Stuff.getClosestEnemy(eShips);
			
			if (target != null)
			{
				Stuff.lookAtSprite(this, target);
				targeting = true;
			}
		}
		else if (Game.pad.isButtonPressed(0) && targeting)
		{
			Stuff.lookAtSprite(this, target);
		}
		else if (!Game.pad.isButtonPressed(0))
		{
			targeting = false;
		}
		
		graphic = setAbsoluteScale(this);
		graphic.setRotation((int)vector.getTheta());
		boundingBox = PolygonStuff.setAngleTo2(vector.getTheta(), graphic, (int)x, (int)y);
		
	}
	
	public void draw(Graphics g)
	{
		graphic.draw((int)x, (int)y);
		
		if (Game.boundingBoxes)
			ShapeRenderer.draw(boundingBox);
	}
}
