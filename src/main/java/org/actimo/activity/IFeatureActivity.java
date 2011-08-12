/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.activity;

import org.actimo.feature.core.Feature;

/**
 * Interface for additional, feature-specific activity methods.
 * @author m.koziarkiewicz
 *
 */
public interface IFeatureActivity {

	/**
	 * @see FeatureManager#addFeature(Feature)
	 */
	public abstract void addFeature(Feature feature);

	/**
	 * @param feature
	 *            the Feature to be removed
	 * @throws UnsupportedOperationException
	 *             if the contributor is not registered for this activity.
	 */
	public abstract void removeFeature(Feature feature);

	/**
	 * Get the given feature.
	 * @param <T> the feature type
	 * @param clazz the feature's class object
	 * @return the feature instance, or <code>null</code> if not attached.
	 */
	public abstract <T extends Feature> T getFeature(Class<T> clazz);

	
	
}