package Zgame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class Sprite 
{
	double x;
	double y;
	int defaultWidth;
	int defaultHeight;
	double defaultX;
	double defaultY;
	Image graphic;
	Vector2f vector;
	Polygon boundingBox;
	float[] corners = new float[8];
	SpriteSheet spriteSheet;
	double depth = 1;
	double centerX;
	double centerY;
	String name;
	
	public Sprite(double x, double y, double angle) throws SlickException 
	{
		this.x = x;
		this.y = y;
		defaultX = x;
		defaultY = y;
		vector = new Vector2f(angle);
	}
	
	//these are test methods.  setGraphics, setPoly, and setBox will eventually be merged into one method.  getCorners will be removed as well.
	public void setGraphic(String file) throws SlickException
	{
		graphic = new Image(file);
		getCorners(corners);
		boundingBox = new Polygon(corners);
		boundingBox.setCenterX((float) (x + graphic.getWidth()/2));
		boundingBox.setCenterY((float) (y + graphic.getHeight()/2));
		defaultWidth = graphic.getWidth();
		defaultHeight = graphic.getHeight();
	}
	
	
	public void setPoly(String file) throws SlickException
	{
		graphic = new Image(file);
		float[] poly = {34,66,35,300,11,313,13,349,24,353,144,349,206,328,228,304,97,300,98,271,111,245,142,231,181,250,236,247,238,218,287,204,300,185,285,160,237,148,235,120,178,115,146,135,121,123,102,102,97,70,232,61,180,29,123,14,28,12,15,20,14,55};
		boundingBox = new Polygon(poly);
		boundingBox.setCenterX((float) (x + graphic.getWidth()/2));
		boundingBox.setCenterY((float) (y + graphic.getHeight()/2));
		defaultWidth = (int) (graphic.getWidth() * 0.287);
		defaultHeight = (int) (graphic.getHeight() * 0.287);
	}
	
	public void setBox(String file, int tileWidth, int tileHeight) throws SlickException
	{
		float[] poly = {34,66,35,300,11,313,13,349,24,353,144,349,206,328,228,304,97,300,98,271,111,245,142,231,181,250,236,247,238,218,287,204,300,185,285,160,237,148,235,120,178,115,146,135,121,123,102,102,97,70,232,61,180,29,123,14,28,12,15,20,14,55};
		spriteSheet = new SpriteSheet(file, tileWidth, tileHeight);
		graphic = spriteSheet.getSprite(0,2);
		getCorners(corners);
		boundingBox = new Polygon(poly);
		boundingBox.setCenterX((float) (x + tileWidth/2));
		boundingBox.setCenterY((float) (y + tileHeight/2));
		defaultWidth = (int) (tileWidth * 0.287);
		defaultHeight = (int) (tileHeight * 0.287);
	}
	
	public void getCorners(float[] corners)
	{
		corners[0] = (float) x;
		corners[1] = (float) y;
	    corners[2] = (float) x + graphic.getWidth();
	    corners[3] = (float) y;
	    corners[4] = (float) x + graphic.getWidth();
	    corners[5] = (float) y + graphic.getHeight();
	    corners[6] = (float) x;
	    corners[7] = (float) y + graphic.getHeight();
	}
	
	public Image setAbsoluteScale(Sprite object)
    {
    	//This returns the properly sized image.  Just trust me.
    	return graphic.getScaledCopy((int)(object.defaultWidth * (1 + (Game.scale - 1) * object.depth)), (int)(object.defaultHeight * (1 + (Game.scale - 1) * object.depth)));
    }
	
	public void setRelativePosition(Sprite object)
	{
		object.x = object.defaultX;
    	object.y = object.defaultY;
    	  
    	//This takes the object's original position and displaces it by an amount that is altered by a scalar that is, in turn, altered by another scalar
    	//Please don't change this!
    	double newX = object.x + (Gvars.width/2 - object.x) * ((1-(Game.scale)) * object.depth);
    	double newY = object.y + (Gvars.height/2 - object.y) * ((1-(Game.scale)) * object.depth);
    	
    	object.x = newX;
    	object.y = newY;
	}
	
	public boolean isOnScreen()
	{
		boolean isOnScreen = false;
		
		if (x < Gvars.width && x > (0 - graphic.getWidth()) && y < Gvars.height && y > (0 - graphic.getHeight()))
			isOnScreen = true;
			
		return isOnScreen;
	}
}
