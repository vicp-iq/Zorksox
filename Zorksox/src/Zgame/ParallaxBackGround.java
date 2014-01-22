package Zgame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ParallaxBackGround 
{
	//Something Else
	ArrayList<BackgroundObject> list = new ArrayList<BackgroundObject>();
	Scanner file;
	
	public ParallaxBackGround(String objects) throws SlickException, FileNotFoundException 
	{
		file = new Scanner(new FileReader(objects));
		loadObjects();
	}
	
	public void loadObjects() throws SlickException
	{
		String name;
		String image;
		double x;
		double y;
		double depth;
		
		while (file.hasNextLine())
		{
			name = file.nextLine();
			image = file.nextLine();
			x = Double.parseDouble(file.nextLine());
			y = Double.parseDouble(file.nextLine());
			depth = Double.parseDouble(file.nextLine());
			
			list.add(new BackgroundObject(image, x, y, depth, 0, 0, name));
		}
	}
	
	
	public void update()
	{		
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).update();
		}
	}
	
	public void draw(Graphics g)
	{
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).draw(g);
		}
	}
}
