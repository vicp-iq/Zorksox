package Zgame;

import java.util.LinkedList;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

public class PlayerWeapon
{
	LinkedList<Shot> allShots = null;
	Input input = new Input(0);
	Controller pad = Controllers.getController(0);
	String file = "/assets/laser01_11.png";
	RefreshTimer timer = new RefreshTimer(100);
	int shipNum = -1;
	double strength = 1;
	
	public PlayerWeapon() throws SlickException 
	{
		allShots = new LinkedList<Shot>();
		timer.start();
	}
	
	public void update(EnemyShips eShips) throws SlickException
	{	
		
		//add a new instance of Shot
		if (pad.getZAxisValue() < -0.3 && timer.newActionPossible())
		{	
			allShots.add(new Shot(file, 5));
			timer.reset();
		}
		
		//remove shots that leave the screen
		for (int i = 0; i < allShots.size(); i++)
		{
			if (allShots.get(i).x < -40 || allShots.get(i).x > Gvars.width || allShots.get(i).y < -30 || allShots.get(i).y > Gvars.height)
			{
				allShots.remove(i);
			}
		}
		
		//update the positions of all the shots in the list
		for (int i = 0; i < allShots.size(); i++)
		{
			allShots.get(i).update();			
		}
		
		//remove a shot if it hits an enemy		
		for (int i = 0; i < allShots.size(); i++)
		{
			for (int j = 0; j < eShips.allEnemies.size(); j++)
			{
				if (eShips.allEnemies.get(j).isOnScreen() 
						&& allShots.get(i).boundingBox.intersects(eShips.allEnemies.get(j).boundingBox) 
						&& allShots.get(i).isVisible)
				{
					allShots.get(i).isVisible = false;
					eShips.allEnemies.get(j).reduceHp(strength);
					
					if (eShips.allEnemies.get(j).getHp() <= 0)
					{
						eShips.allEnemies.remove(j);
					}
				}
			}
		}
	}
	
	public void draw()
	{
		for (int i = 0; i < allShots.size(); i++)
		{
			allShots.get(i).draw();
		}
	}
	
	
	
	
	
	//I need to create a weapon class that will define a variety of weapon types
	class Shot extends Sprite
	{
		double speed;
		double originalSpeed;
		boolean isVisible = true;
		
		public Shot(String file, double speed) throws SlickException
		{
			super(0,0,0);
			vector = new Vector2f(0); 
			vector.x = Game.pShip.vector.getNormal().x;
			vector.y = Game.pShip.vector.getNormal().y;
			setGraphic("assets/laser01_11.png");
			x = Game.pShip.x + 21 * Game.scale;
			y = Game.pShip.y + 42 * Game.scale;
			this.speed = speed;// * Game.scale;
			originalSpeed = speed;
			defaultX = x;
			defaultY = y;
		}
		
		public void update() throws SlickException
		{
			if (Game.isParallax)
			{
				graphic = setAbsoluteScale(this);
				
				speed = originalSpeed * Game.scale;
				
				x += vector.x * speed * Game.delta;
				y += vector.y * speed * Game.delta;
				
				x -= Game.pad.getXAxisValue() * Game.scale * Game.delta;
				y -= Game.pad.getYAxisValue() * Game.scale * Game.delta;
			}
			else
			{
				x += vector.x * speed;
				y += vector.y * speed;
			}
			
			boundingBox = PolygonStuff.setAngleTo(vector.getTheta()+90, graphic, (int)x, (int)y);
			graphic.setRotation((float) vector.getTheta() + 90);
			
		}
		
		public void draw()
		{
			if (isVisible)
				graphic.draw((int)x, (int)y);
			
			if (Game.boundingBoxes)
				ShapeRenderer.draw(boundingBox);
		}
	}
}
