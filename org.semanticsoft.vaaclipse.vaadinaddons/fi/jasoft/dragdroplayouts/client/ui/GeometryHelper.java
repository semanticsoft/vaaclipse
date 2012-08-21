/**
 * 
 */
package fi.jasoft.dragdroplayouts.client.ui;

/**
 * @author rushan
 *
 */
public class GeometryHelper 
{
//	public static boolean containsInRectangle(Rectangle rect, Vector point)
//	{
//		return point.getX() >= rect.getCorner1().getX() && point.getX() <= rect.getCorner4().getX() && 
//				point.getY() >= rect.getCorner1().getY() && point.getX() <= rect.getCorner2().getY();
//	}
	
	public static boolean containsInRectangle(Vector start, Vector sides, Vector point)
	{
		//return containsInRectangle(new Rectangle(start, sides), point);
		return point.getX() >= start.getX() && point.getX() <= start.getX() + sides.getX() && 
				point.getY() >= start.getY() && point.getY() <= start.getY() + sides.getY();
	}
	
	public static boolean containsInRightTriangle(Vector triangleRigthAngle, double catetX, double catetY, Vector point)
	{
		if (catetX < 0)
		{
			//coordinate translation
			catetX = -catetX;
			triangleRigthAngle = new Vector(-triangleRigthAngle.getX(), triangleRigthAngle.getY());
			point = new Vector(-point.getX(), point.getY());
		}
		
		if (catetY < 0)
		{
			//coordinate translation
			catetY = -catetY;
			triangleRigthAngle = new Vector(triangleRigthAngle.getX(), -triangleRigthAngle.getY());
			point = new Vector(point.getX(), -point.getY());
		}
		
		return point.getX() >= triangleRigthAngle.getX() && point.getX() <= triangleRigthAngle.getX() + catetX &&
				Math.abs(triangleRigthAngle.getY() - point.getY()) <= (catetY/catetX)*Math.abs(triangleRigthAngle.getX() + catetX - point.getX());
	}
}
