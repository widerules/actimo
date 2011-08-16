/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.impl.misc;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Feature that causes the activity to wake the phone up,
 * and keep it awake until paused/closed.
 * 
 * <p>NOTE: Not all activities may be started that way, this 
 * applies especially for transparent activities on some 
 * phones!
 * @author m.koziarkiewicz
 *
 */
public class FullAwakeFeature extends KeepAwakeFeature {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

		getActivity().getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED 
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD 
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON 
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
}
