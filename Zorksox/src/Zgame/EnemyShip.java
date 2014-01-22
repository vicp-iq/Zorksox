package Zgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;

public class EnemyShip extends Sprite
{
	private double hp;
	Vector2f playerDirection;
	int playerAngle;
	int currentAngle;
	RefreshTimer variableTimer; 
	RefreshTimer constantTimer;
	int initialDelay;
	private int variableDelay = initialDelay;
	int constantDelay;
	double turnSpeed;
	float radians;

	public EnemyShip(double hp, int x, int y, int initialDelay, double turnSpeed, int constantDelay) throws SlickException 
	{
		super(x,y,0);
		this.hp = hp;
		this.initialDelay = initialDelay;
		this.constantDelay = constantDelay;
		this.turnSpeed = turnSpeed;
		playerDirection = new Vector2f();
		variableTimer = new RefreshTimer(variableDelay);
		constantTimer = new RefreshTimer(constantDelay);
		variableTimer.start();
		constantTimer.start();
	}
	
	public void update()
	{
		graphic = setAbsoluteScale(this);
		setRelativePosition(this);
		
		playerDirection = Stuff.pointAtSprite(this, Game.pShip);
		turnToPlayer(Game.delta);
		graphic.setRotation((float)vector.getTheta());
		
		if (Game.isParallax)
		{
			if (Game.pad.getYAxisValue() < (0 - Game.lDeadZone) || Game.pad.getYAxisValue() > Game.lDeadZone || Game.pad.getXAxisValue() < (0 - Game.lDeadZone) || Game.pad.getXAxisValue() > Game.lDeadZone)
			{
				defaultY -= (Game.pad.getYAxisValue())  * Game.delta * Game.pSpeed * Game.scale;
				defaultX -= (Game.pad.getXAxisValue())  * Game.delta * Game.pSpeed * Game.scale;
				boundingBox.setX((float) (boundingBox.getX() - (Game.pad.getXAxisValue())  * Game.delta * Game.pSpeed));// * Game.scale));
				boundingBox.setY((float) (boundingBox.getY() - (Game.pad.getYAxisValue())  * Game.delta * Game.pSpeed));// * Game.scale));
			}
			
			
			boundingBox = PolygonStuff.setAngleTo(vector.getTheta(), graphic, (int)x, (int)y);
		}
	}
	
	private void turnToPlayer(int delta)
	{
		playerAngle = 360 - (int)playerDirection.getTheta();
		currentAngle = 360 - (int)vector.getTheta();

		
		
		if (isOnScreen() && variableTimer.newActionPossible())
		{	
			if (playerAngle > 270 && currentAngle > (playerAngle - 180) && currentAngle < playerAngle)
				turnLeft(delta);
			else if (playerAngle > 270 && (currentAngle <= (playerAngle - 180) || currentAngle < playerAngle))
				turnRight(delta);
			else if (playerAngle > 180 && (currentAngle <= (playerAngle - 180) || currentAngle > playerAngle))
				turnRight(delta);
			else if (playerAngle > 180 && currentAngle > (playerAngle - 180) && currentAngle < playerAngle)
				turnLeft(delta);
			else if (playerAngle > 90 && currentAngle <= (playerAngle + 180) && currentAngle > playerAngle)
				turnRight(delta);
			else if (playerAngle > 90 && (currentAngle > (playerAngle + 180) || currentAngle < playerAngle))
				turnLeft(delta);
			else if (playerAngle > 0 && currentAngle <= (playerAngle + 180) && currentAngle > playerAngle)
				turnRight(delta);
			else if (playerAngle > 0 && (currentAngle > (playerAngle + 180) || currentAngle < playerAngle))
				turnLeft(delta);
			
			if (constantTimer.newActionPossible())
			{
				variableDelay--;
				variableTimer.changeDelay(variableDelay);
				constantTimer.reset();
			}
			
			if (playerAngle == currentAngle)
			{
				variableDelay = initialDelay;
				variableTimer.changeDelay(variableDelay);
			}
			
			variableTimer.reset();
		}
	}
	
	public void turnLeft(int delta)
	{
		vector.setTheta(vector.getTheta() - (turnSpeed * delta));
		boundingBox = (Polygon)boundingBox.transform(Transform.createRotateTransform((float) (Math.toRadians(0 - turnSpeed*delta)), boundingBox.getCenterX(), boundingBox.getCenterY()));
	}
	
	public void turnRight(int delta)
	{
		vector.setTheta(vector.getTheta() + (turnSpeed * delta));
		boundingBox = (Polygon)boundingBox.transform(Transform.createRotateTransform((float) (Math.toRadians(turnSpeed*delta)), boundingBox.getCenterX(), boundingBox.getCenterY()));
	}

	
	
	public void draw(Graphics g)
	{
		if (isOnScreen())
		{
			if (boundingBox.intersects(Game.pShip.boundingBox))
			{
				//What happens when the player rams into an enemy?
			}
			graphic.draw((int)x,(int)y);
			
			if (Game.boundingBoxes)
				ShapeRenderer.draw(boundingBox);
		}
	}

	public double getHp() 
	{
		return hp;
	}

	public void reduceHp(double strength) 
	{
		this.hp -= strength;
	}

}
