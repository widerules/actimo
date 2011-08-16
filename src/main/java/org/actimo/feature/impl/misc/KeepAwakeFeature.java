/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.impl.misc;

import org.actimo.feature.core.AbstractFeature;

import android.view.View;

/**
 * Causes the activity to wake lock the phone.
 * @author m.koziarkiewicz
 *
 */
public class KeepAwakeFeature extends AbstractFeature {

	private View lockView;
	

	@Override
	public void onResume() {
		if(lockView == null) {
			lockView = getActivity().getWindow().getDecorView().getRootView();
		}
		lockView.setKeepScreenOn(true);
	}
	
	@Override
	public void onPause() {
		lockView.setKeepScreenOn(false);
	}
	
	
	
}
