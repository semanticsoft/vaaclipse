/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/
package org.semanticsoft.commons.geom;



/**
 * @author rushan
 *
 */
public class Vector
{
	private double x;
	private double y;
	
	public static Vector ortX = new Vector(1, 0);
	public static Vector ortY = new Vector(0, 1);
	public static Vector ZERO = new Vector(0, 0);
	
	public static Vector vectorX(double x)
	{
		return new Vector(x, 0);
	}
	
	public static Vector vectorY(double y)
	{
		return new Vector(0, y);
	}
	
	/**
	 * shortcut for vectorX
	 */
	public static Vector vX(double x)
	{
		return vectorX(x);
	}
	
	/**
	 * shortcut for vectorY
	 */
	public static Vector vY(double y)
	{
		return vectorY(y);
	}
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public static Vector valueOf(double x, double y)
	{
		return new Vector(x, y);
	}
	
	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setXY(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public Vector getXProectionVector()
	{
		return Vector.vectorX(this.x);
	}
	
	public Vector getYProectionVector()
	{
		return Vector.vectorY(this.y);
	}
	
	public double getLength()
	{
		return Math.sqrt(getX()*getX() + getY()*getY());
	}
	
	public Vector opposite()
	{
		return new Vector(-this.getX(), -this.getY());
	}
	
	public Vector normalize()
	{
		double len = getLength();
		return new Vector(getX()/len, getY()/len);
	}
	
	public boolean isNull()
	{
		return getX() == 0.0 && getY() == 0.0;
	}
	
	public Vector scale(double n)
	{
		return new Vector(this.getX()*n, this.getY()*n);
	}
	
	public Vector div(double n)
	{
		return scale(1/n);
	}
	
	public Vector scaleX(double n)
	{
		return new Vector(this.getX()*n, this.getY());
	}
	
	public Vector scaleY(double n)
	{
		return new Vector(this.getX(), this.getY()*n);
	}
	
	public Vector plus(Vector v)
	{
		return new Vector(this.getX() + v.getX(), this.getY() + v.getY());
	}
	
	public Vector minus(Vector v)
	{
		return new Vector(this.getX() - v.getX(), this.getY() - v.getY());
	}
	
	public static double dotProduct(Vector v1, Vector v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY();
	}
	
	public static double getAngle(Vector v1, Vector v2)
	{
		//косинус угла между векторами
		double cos = dotProduct(v1, v2)/(v1.getLength()*v2.getLength());
		return Math.acos(cos);
	}
	
	public static Vector[] getOrtogonalOrts(Vector v)
	{
		double len = v.getLength();
		Vector ort1 = new Vector(v.getY()/len, -v.getX()/len);
		Vector ort2 = new Vector(-v.getY()/len, v.getX()/len);
		return new Vector[] {ort1, ort2};
	}
	
	@Override
	public String toString()
	{
		return String.format("(%s, %s)", this.getX(), this.getY());
	}
	
	private static boolean sameSign(double val1, double val2)
	{
		if (val1 == 0 && val2 == 0)
			return true;
		if (val1 == 0 && val2 != 0 || val2 == 0 && val1 != 0)
			return false;
		return (val1 > 0 && val2 > 0) || (val1<0 && val2 <0);
	}
	
	public static boolean isEqual(Vector v1, Vector v2, double E)
	{
		return Math.abs(v1.getX() - v2.getX()) < E && Math.abs(v1.getY() - v2.getY()) < E;
	}
	
	public static boolean isEqual(Vector v1, Vector v2)
	{
		return v1.getX() == v2.getX() && v1.getY() == v2.getY();
	}
	
	public static boolean isSameDirection(Vector v1, Vector v2, double E)
	{
		if (v1.isNull() || v2.isNull())
			return false;
		
		double s = v2.getX() !=0? v1.getX()/v2.getX() : v1.getY()/v2.getY();
		Vector test2 = v2.scale(s);
		if (isEqual(v1, test2, E))
		{
			//Проверка, что вектора направлены в одну и ту же сторону
			return sameSign(v1.getX(), v2.getX()) && sameSign(v1.getY(), v2.getY()); 
		}
		else
			return false;
	}
	
	public static boolean nonStrictCompare(Vector v1, Vector v2, double E)
	{
		if (Math.abs(v1.getX() - v2.getX()) < E)
			if (Math.abs(v1.getY() - v2.getY()) < E)
				return true;
		return false;
	}
	
	public static Vector maximum(Vector v1, Vector v2)
	{
		return v1.getLength()>v2.getLength() ? v1.getCopy() : v2.getCopy();
	}
	
	public Vector getCopy()
	{
		return new Vector(getX(), getY());
	}
}
