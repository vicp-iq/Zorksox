package Zgame;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

public class PolygonStuff 
{
	static Polygon boundingBox;
	static float[] corners = new float[8];
		
	public static void getCorners(Image graphic, int x, int y)
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
	
	public static Polygon setAngleTo(double angle, Image graphic, int x, int y)
	{
		getCorners(graphic, x, y);
		boundingBox = new Polygon(corners);
		boundingBox = (Polygon) boundingBox.transform(Transform.createRotateTransform((float) Math.toRadians(angle), boundingBox.getCenterX(), boundingBox.getCenterY()));
		return boundingBox;
	}
	
	public static Polygon setAngleTo2(double angle, Image graphic, int x, int y)
	{
		float[] poly = {34,66,35,300,11,313,13,349,24,353,144,349,206,328,228,304,97,300,98,271,111,245,142,231,181,250,236,247,238,218,287,204,300,185,285,160,237,148,235,120,178,115,146,135,121,123,102,102,97,70,232,61,180,29,123,14,28,12,15,20,14,55};
		
		for (int i = 0; i < poly.length; i++)
			poly[i] = (float) (poly[i] * 0.287 * Game.scale);
			
		boundingBox = new Polygon(poly);		
		boundingBox = (Polygon) boundingBox.transform(Transform.createRotateTransform((float) Math.toRadians(angle), boundingBox.getCenterX(), boundingBox.getCenterY()));		
		boundingBox.setLocation(x, y);
		
		return boundingBox;
	}
}
