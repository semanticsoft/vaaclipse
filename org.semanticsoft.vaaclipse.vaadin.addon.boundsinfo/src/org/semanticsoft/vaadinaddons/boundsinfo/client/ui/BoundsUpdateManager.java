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
package org.semanticsoft.vaadinaddons.boundsinfo.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;

/**
 * @author rushan
 *
 */
public class BoundsUpdateManager
{
	private static class Bounds 
	{
		int x, y, width, height;
		
		public Bounds(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public int getSquare()
		{
			return width*height;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof Bounds)
			{
				Bounds b = (Bounds) obj;
				return this.x == b.x && this.y == b.y && this.width == b.width && this.height == b.height;
			}
			else
				return false;
		}
		
		/**
		 * adopted from java.awt.Rectangle
		 * @param r
		 * @return
		 */
		public Bounds intersection(Bounds r) {
	        int tx1 = this.x;
	        int ty1 = this.y;
	        int rx1 = r.x;
	        int ry1 = r.y;
	        long tx2 = tx1; tx2 += this.width;
	        long ty2 = ty1; ty2 += this.height;
	        long rx2 = rx1; rx2 += r.width;
	        long ry2 = ry1; ry2 += r.height;
	        if (tx1 < rx1) tx1 = rx1;
	        if (ty1 < ry1) ty1 = ry1;
	        if (tx2 > rx2) tx2 = rx2;
	        if (ty2 > ry2) ty2 = ry2;
	        tx2 -= tx1;
	        ty2 -= ty1;
	        // tx2,ty2 will never overflow (they will never be
	        // larger than the smallest of the two source w,h)
	        // they might underflow, though...
	        if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
	        if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
	        return new Bounds(tx1, ty1, (int) tx2, (int) ty2);
	    }
	}
	
	private Widget widget;
	private String paintableId;
	private ApplicationConnection client;
	private Bounds prevBounds;
	private long prevTime;
	private static double OVERLAP = 0.1;
	private static double PERIOD = 1000;
	
	public BoundsUpdateManager(Widget widget, String paintableId, ApplicationConnection client)
	{
		this.widget = widget;
		this.client = client;
		this.paintableId = paintableId;
	}
	
	public void update()
	{
		if (!widget.isAttached() || widget.getParent() == null || !widget.isVisible())
			return;
		
		Bounds bounds = new Bounds(widget.getAbsoluteLeft(), widget.getAbsoluteTop(),
				widget.getOffsetWidth(), widget.getOffsetHeight());
		
		if (bounds.width == 0 && bounds.height == 0)
			return;
		
		if (prevBounds == null)
		{
			doUpdate(bounds);
		}
		else if (!bounds.equals(prevBounds)) //the necessary condition of update - bounds must change since last update
		{
			Bounds overlap = prevBounds.intersection(bounds);
			float prevS = prevBounds.getSquare();
			float s = bounds.getSquare();
			float overlapS = overlap.getSquare();
			
			if ( (s - overlapS) /s < OVERLAP && (prevS - overlapS)/prevS < OVERLAP )
			{//the bounds changes are small
				//so we look on ellapsed time and do update only if ellapsed time  is more the PERIOD
				long time = System.currentTimeMillis();
				if (time - prevTime > PERIOD)
					doUpdate(bounds);
			}
			else //if changes are large, then update
				doUpdate(bounds);
		}
	}
	
	private void doUpdate(Bounds bounds)
	{
		final String boundsStr = BoundsParser.toString(bounds.x, bounds.y, bounds.width, bounds.height);
		client.updateVariable(paintableId, "bounds", boundsStr, true);
		
		prevBounds = bounds;
		prevTime = System.currentTimeMillis();
	}
}
