package Zgame;

import java.util.Random;

import org.newdawn.slick.SlickException;

//temporary class.  just distributes enemies randomly.
public class Level 
{
	static Random x = new Random();
	static Random y = new Random();
	
	public static void level1(EnemyShips eShips) throws SlickException
	{
		for (int i = 0; i < 400; i++)
		{
			eShips.add(new EnemyShip(20, x.nextInt(20000) - 10000, y.nextInt(20000) - 10000, 30, 0.5, 20));
			eShips.allEnemies.get(i).setGraphic("assets/enemy.png");
		}
	}
	
	public static void level2(EnemyShips eShips) throws SlickException
	{
		EnemyShip guy1 = new EnemyShip(10, x.nextInt(Gvars.width), y.nextInt(Gvars.height), 30, 0.5, 20);
		guy1.setGraphic("assets/enemy08.png");
		
		eShips.add(guy1);
	}
}
