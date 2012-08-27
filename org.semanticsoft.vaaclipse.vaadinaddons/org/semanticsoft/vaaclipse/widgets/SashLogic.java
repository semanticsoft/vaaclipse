/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.widgets;

import com.vaadin.ui.AbstractSplitPanel;

/**
 * @author rushan
 *
 */
public class SashLogic
{
	private enum State {
		TWO_VISIBLE, ONE_VISIBLE, NO_VISIBLE
	}
	
	private State state = State.TWO_VISIBLE;
	private AbstractSplitPanel splitPanel;
	private boolean refreshStarted = false;
	private Float oldSplitPos;
	
	public SashLogic(AbstractSplitPanel splitPanel)
	{
		this.splitPanel = splitPanel;
	}
	
	public void refreshState()
	{
		if (refreshStarted || splitPanel.getParent() == null)
			return;
		
		refreshStarted = true;
		
		this.splitPanel.setVisible(true);
		boolean firstVisible = splitPanel.getFirstComponent() != null && splitPanel.getFirstComponent().isVisible();
		boolean secondVisible = splitPanel.getSecondComponent() != null && splitPanel.getSecondComponent().isVisible();
		
		if (firstVisible && secondVisible)
		{
			splitPanel.setVisible(true);
			if (oldSplitPos != null)
			{
				splitPanel.setLocked(false);
				splitPanel.setSplitPosition(oldSplitPos);
				splitPanel.removeStyleName("invisible");
				splitPanel.getFirstComponent().requestRepaint();
				//splitPanel.getFirstComponent().setSizeUndefined();
				//splitPanel.getFirstComponent().setSizeFull();
				//splitPanel.requestRepaintAll();
				oldSplitPos = null;
			}
			this.state = State.TWO_VISIBLE;
		}
		else if (!firstVisible && !secondVisible)
		{
			splitPanel.setVisible(false);
			this.state = State.NO_VISIBLE;
		}
		else
		{// only one of two components visible
			splitPanel.setLocked(true);
			splitPanel.addStyleName("invisible");
			
			if (state == State.TWO_VISIBLE)
				oldSplitPos = splitPanel.getSplitPosition();
			
			if (firstVisible)
			{
				splitPanel.setSplitPosition(100);
			}
			else
			{
				splitPanel.setSplitPosition(0);
			}
			
			this.state = State.ONE_VISIBLE;
		}
		
		refreshStarted = false;
	}

}
