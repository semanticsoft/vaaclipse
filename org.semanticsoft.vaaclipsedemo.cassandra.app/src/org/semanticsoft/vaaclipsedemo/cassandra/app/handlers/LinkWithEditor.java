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

package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.semanticsoft.vaaclipsedemo.cassandra.app.constants.CassandraConstants;

/**
 * @author rushan
 *
 */
public class LinkWithEditor
{
	private boolean link = false;
	
	@Execute
	public void execute(MToolItem toolItem, IEventBroker eventBroker)
	{
		eventBroker.send(CassandraConstants.LINK_WITH_EDITOR, toolItem.isSelected());
	}
}
