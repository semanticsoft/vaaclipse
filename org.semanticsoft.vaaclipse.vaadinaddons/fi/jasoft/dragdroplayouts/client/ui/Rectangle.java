/**
 * 
 */
package fi.jasoft.dragdroplayouts.client.ui;

/**
 * @author rushan
 *
 */
public class Rectangle 
{
	private Vector start, sides;
	
	public Rectangle(double x, double y, double w, double h) {
		this.start = new Vector(x, y);
		this.sides = new Vector(w, h);
	}
	
	public Vector getStart() {
		return start;
	}
	
	public Vector getSides() {
		return sides;
	}
	
	public Vector getCorner1()
	{
		return start;
	}
	
	public Vector getCorner2()
	{
		return start.plus(sides.getYProectionVector());
	}
	
	public Vector getCorner3()
	{
		return start.plus(sides);
	}
	
	public Vector getCorner4()
	{
		return start.plus(sides.getXProectionVector());
	}
}
