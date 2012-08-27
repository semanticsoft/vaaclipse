package org.semanticsoft.vaadinaddons.boundsinfo;

import java.util.Map;

import org.semanticsoft.commons.geom.Bounds;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.VerticalLayout;

/**
 * Server side component for the VBoundsinfoVerticalLayout widget.
 */
@com.vaadin.ui.ClientWidget(org.semanticsoft.vaadinaddons.boundsinfo.client.ui.VBoundsinfoVerticalLayout.class)
public class BoundsinfoVerticalLayout extends VerticalLayout
{

	private String message = "Click here.";
	private int clicks = 0;
	
	private Integer absoluteLeft;
	private Integer absoluteTop;
	private Integer offsetWidth;
	private Integer offsetHeight;

	@Override
	public void paintContent(PaintTarget target) throws PaintException
	{
		super.paintContent(target);

		// Paint any component specific content by setting attributes
		// These attributes can be read in updateFromUIDL in the widget.
		target.addAttribute("clicks", clicks);
		target.addAttribute("message", message);

		// We could also set variables in which values can be returned
		// but declaring variables here is not required
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables)
	{
		super.changeVariables(source, variables);
		
		if (variables.containsKey("absolute_left"))
		{
			absoluteLeft = (Integer) variables.get("absolute_left");	
		}
		
		if (variables.containsKey("absolute_top"))
		{
			absoluteTop = (Integer) variables.get("absolute_top");	
		}
		
		if (variables.containsKey("offset_width"))
		{
			offsetWidth = (Integer) variables.get("offset_width");	
		}
		
		if (variables.containsKey("offset_height"))
		{
			offsetHeight = (Integer) variables.get("offset_height");	
		}
		
//		System.out.println("absoluteLeft: " + absoluteLeft);
//		System.out.println("absoluteTop: " + absoluteTop);
//		System.out.println("offsetWidth: " + offsetWidth);
//		System.out.println("offsetHeight: " + offsetHeight);
		
	}
	
	public boolean hasBoundsInfo()
	{
		return this.absoluteTop != null;
	}
	
	public Integer getAbsoluteLeft()
	{
		return absoluteLeft;
	}
	
	public Integer getAbsoluteTop()
	{
		return absoluteTop;
	}
	
	public Integer getOffsetWidth()
	{
		return offsetWidth;
	}
	
	public Integer getOffsetHeight()
	{
		return offsetHeight;
	}
	
	public Bounds getBounds()
	{
		if (hasBoundsInfo())
			return new Bounds(absoluteLeft, absoluteTop, offsetWidth, offsetHeight);
		else
			return null;
	}
}
