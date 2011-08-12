/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.dialogs;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages dialog IDs in order to prevent and minimize conflicts.
 * 
 * @author Mikołaj Koziarkiewicz
 * 
 */
public final class DialogManager {

	private static DialogManager instance;

	private static int START_ID = 1000;

	private int lastId;

	private Map<String, Integer> idMap;

	private DialogManager() {
		lastId = START_ID;
		idMap = new HashMap<String, Integer>();
	}

	/**
	 * @return the Manager instance
	 */
	public static DialogManager get() {
		if (instance == null) {
			instance = new DialogManager(); 
		}

		return instance;
	}

	private int newId() {
		return lastId++;
	}

	/**
	 * Registers an int ID for this string ID.
	 * 
	 * @param id
	 *            the string ID
	 * @return the appropriate int ID
	 * @throws IllegalStateException
	 *             if this string ID is already registered
	 */
	protected int registerId(String id) {
		if (idMap.containsKey(id)) {
			throw new IllegalStateException("Dialog ID '" + id
					+ "' already registered!");
		}

		idMap.put(id, newId());

		return getId(id);
	}

	/**
	 * @param id
	 *            the string ID
	 * @return the corresponding int ID
	 */
	public int getId(String id) {
		return idMap.get(id);
	}

	/**
	 * @param id
	 *            the string ID
	 * @return <code>true</code> if the ID is already registered,
	 *         <code>false</code> otherwise
	 */
	protected boolean hasId(String id) {
		return idMap.containsKey(id);
	}

}
