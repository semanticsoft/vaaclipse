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
package org.semanticsoft.vaaclipse.widgets.common;

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
