package Zgame;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

public class EnemyShips 
{
	LinkedList<EnemyShip> allEnemies;
	
	public EnemyShips() 
	{
		allEnemies = new LinkedList<EnemyShip>();
	}

	public void add(EnemyShip ship)
	{
		allEnemies.add(ship);
	}
	
	public void update()
	{
		
		for (int i = 0; i < allEnemies.size(); i++)
		{	
			allEnemies.get(i).update();
		}
	}
	
	public void draw(Graphics g)
	{
		for (int i = 0; i < allEnemies.size(); i++)
		{
			allEnemies.get(i).draw(g);
		}
	}
}
