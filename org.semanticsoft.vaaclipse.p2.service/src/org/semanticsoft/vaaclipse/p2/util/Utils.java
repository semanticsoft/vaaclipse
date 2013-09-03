package org.semanticsoft.vaaclipse.p2.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.query.IQueryResult;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class Utils {

	
	public static List<IInstallableUnit> toList(IQueryResult<IInstallableUnit> query) {
		List<IInstallableUnit> list = new ArrayList<IInstallableUnit>();
		for (IInstallableUnit iInstallableUnit : query) {

			System.out.println(iInstallableUnit);
			list.add(iInstallableUnit);

		}
		return list;
	}	
	public static boolean containsString(String original, String tobeChecked,
			boolean caseSensitive) {
		if (caseSensitive) {
			return original.contains(tobeChecked);

		} else {
			return original.toLowerCase().contains(tobeChecked.toLowerCase());
		}

	}
}
