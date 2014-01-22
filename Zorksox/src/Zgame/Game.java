package Zgame;

import java.io.FileNotFoundException;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;

public class Game extends BasicGame 
{
	//adding a comment
	//adding a second comment
	static boolean boundingBoxes = false;
	static boolean usingKeyboard = false;
	static double lDeadZone = 0.0;
	static double rDeadZone = 0.9;
	static Controller pad;
	static int selectedPad = 0;
	static boolean isParallax = true;
	static double worldX = Gvars.width/2;
	static double worldY = Gvars.height/2;
	static float scale = 1f;
	static double pSpeed = 1;
	static GameContainer container;
	
	static PlayerShip pShip;
	
	static int delta;
	
	ParallaxBackGround back2;
	ParallaxBackGround center;
	
	Background back1;
	PlayerWeapon laser;
	EnemyShips eShips;
	
	public enum ScrollType
	{
		NONE,
		VERTICAL,
		HORIZONTAL,
		PARALLAX;
	}
	
	public Game(String title) 
	{
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException 
	{
		pad = Controllers.getController(selectedPad);
		pShip = new PlayerShip(0, 0, 0);
		pShip.setPoly("assets/ship.png");
		pShip.x = Gvars.width/2 - pShip.graphic.getWidth()/2;
		pShip.y = Gvars.height/2 - pShip.graphic.getHeight()/2;
		
		eShips = new EnemyShips();
		
		Level.level1(eShips);
		
		laser = new PlayerWeapon();
		
		back1 = new Background("assets/stars02.png", 0, 0, 1, ScrollType.NONE, 0.1);
		
		try 
		{
			back2 = new ParallaxBackGround("data/planets.txt");
			center = new ParallaxBackGround("data/center.txt");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		container.setShowFPS(true);
	}
	
	@Override
	public void render(GameContainer container, Graphics g)	throws SlickException 
	{
		back1.draw();
		back2.draw(g);
		center.draw(g);
		laser.draw();
		pShip.draw(g);
		eShips.draw(g);
		
		//draw HUD
		g.draw(new Circle(Gvars.width - 100, Gvars.height - 100, 50));
		g.draw(new Line(Gvars.width - 100, Gvars.height - 100, pShip.movementVector.x * 50, pShip.movementVector.y * 50, true));
		g.drawString("" + (int)pShip.movementVector.getTheta(), Gvars.width - 110, Gvars.height - 30);
			
		g.drawString("Global X: " + (int)worldX, 10, 650);
		g.drawString("Global Y: " + (int)worldY, 10, 670);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException 
	{	
		Game.delta = delta;
		pShip.update(eShips);
		eShips.update();
		back1.update();
		back2.update();
		center.update();
		laser.update(eShips);
		
		worldX = (int)(((pShip.x - center.list.get(0).x) * (1/scale)) / 1000) ;
		worldY = (int)(((pShip.y - center.list.get(0).y) * (1/scale)) / 1000);
	}

	public static void main(String[] args) throws SlickException 
	{
		Game game = new Game("Zorksox Game");
		AppGameContainer container;
		Display.sync(60);
		container = new AppGameContainer(game);
		container.setDisplayMode(1800, 900, false);
		container.setTargetFrameRate(500);
		
		Gvars.height = container.getHeight();
		Gvars.width = container.getWidth();
		container.start();
	}
}
