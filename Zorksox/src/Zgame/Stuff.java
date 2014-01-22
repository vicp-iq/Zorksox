package Zgame;

import org.newdawn.slick.geom.Vector2f;

import Zgame.PlayerWeapon.Shot;

public class Stuff 
{
    public static void lookAtSprite(Sprite aimer, Sprite target)
    {
    	float xDiff = (float) ((target.x + target.graphic.getWidth() / 2) - (aimer.x + aimer.graphic.getWidth() / 2));
    	float yDiff = (float) ((target.y + target.graphic.getHeight() / 2) - (aimer.y + aimer.graphic.getHeight() / 2));
    	
    	aimer.vector = new Vector2f(xDiff, yDiff);
    }
    
    //returns a vector at sprite1 that is pointing at sprite2 
    public static Vector2f pointAtSprite(Sprite sprite1, Sprite player)
    {
    	double xComponent = (player.x + player.graphic.getWidth()/2) - (sprite1.x + sprite1.graphic.getWidth()/2);
    	double yComponent = (player.y + player.graphic.getHeight()/2) - (sprite1.y + sprite1.graphic.getHeight()/2);
    	Vector2f angle = new Vector2f((float)xComponent, (float)yComponent);

    	return angle;
    }
    
    public static EnemyShip getClosestEnemy(EnemyShips eShips)
	{
		EnemyShip temp = null;
		double shortestDistance = Double.MAX_VALUE;
		double distanceX = 0;
		double distanceY = 0;
		double distance = 0;
		
		for (int i = 0; i < eShips.allEnemies.size(); i++)
		{
			if (eShips.allEnemies.get(i).isOnScreen())
			{
				distanceX = Math.abs(eShips.allEnemies.get(i).x - Game.pShip.x);
				distanceY = Math.abs(eShips.allEnemies.get(i).y - Game.pShip.y);
				distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
				
				if (distance < shortestDistance)
				{
					temp = eShips.allEnemies.get(i);
					shortestDistance = distance;
				}
			}
		}
		
		return temp;
	}
    
    public static int collided(Shot shot, EnemyShips eShips)
    {
    	int shipNum = -1;
    	int listSize = eShips.allEnemies.size();
    	
    	for (int i = 0; i < listSize; i++)
    	{
    		if ((Math.abs(shot.x - eShips.allEnemies.get(i).x) < 100 && (Math.abs(shot.y - eShips.allEnemies.get(i).y) < 100)))
    		{
    			return i;
    		}
    	}
    	
    	return shipNum;
    }
    
    public static double getDistance(double x1, double y1, double x2, double y2)
    {
    	//The distance formula.  This is used about 400 times every millisecond.
    	double distanceX = Math.abs(x1 - x2);
    	double distanceY = Math.abs(y1 - y2);
    	double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    	return distance;
    }
}
