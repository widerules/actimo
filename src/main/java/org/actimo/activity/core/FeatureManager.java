/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.activity.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.actimo.feature.core.Feature;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;


/**
 * The manager for features. Extracted from {@link FeatureActivity} for easier
 * template application.
 * @author m.koziarkiewicz
 *
 */
public class FeatureManager {

	/**
	 * The used when two features and/or the activity provides the same functionality
	 * as this one.
	 */
	private static final String CONFLICT_MSG = "Another feature or the activity provides this functionality, conflict class: ";
	
	/** The feature map and list. */
	private List<Feature> features;
	private Map<Class<? extends Feature>,Feature> featureMap;
	private Activity act;
	
	/**
	 * Create a new instance of the manager.
	 * @param act the activity to attach to.
	 */
	public FeatureManager(Activity act) {
		features = new ArrayList<Feature>();
		featureMap = new HashMap<Class<? extends Feature>, Feature>();
		this.act = act;
	}
	
	
	/**
	 * Add a feature to the collection.
	 * 
	 * @param feature
	 *            the new Feature
	 * @throws UnsupportedOperationException
	 *             if the contributor is already installed or shared with
	 *             another activity.
	 * @throws IllegalStateException
	 *             if the method is unable the set the serviced activity.
	 */
	public final void addFeature(Feature feature) {
		if (featureMap.containsKey(feature.getClass())) {
			throw new UnsupportedOperationException(
					"Feature already included!");
		}

		if (feature.getActivity() != null) {
			throw new UnsupportedOperationException(
					"A contributor may only be added once to any Activity!");
		}

		feature.setActivity(act);

		if (feature.getActivity() != act) {
			throw new IllegalStateException(
					"Setting activity for the contributor of type "
							+ feature.getClass().getSimpleName()
							+ " check it's getActivity() and setActivity() implementation.");
		}

		featureMap.put(feature.getClass(),feature);
		features.add(feature);
	}
	
	/**
	 * @param feature
	 *            the Feature to be removed
	 * @throws UnsupportedOperationException
	 *             if the contributor is not registered for this activity.
	 */
	public final void removeFeature(Feature feature) {
		if (!featureMap.containsKey(feature.getClass())) {
			throw new UnsupportedOperationException(
					"Trying to remove a non-existing contributor ("
							+ feature.getClass().getSimpleName()
							+ "). Check your code!");
		}

		features.remove(feature.getClass());
		featureMap.remove(feature.getClass());
	}
	
	public void onCreate(Bundle savedInstanceState) {
		for (Feature feature : features) {
			feature.onCreate(savedInstanceState);
		}
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		for (Feature feature : features) {
			feature.onRestoreInstanceState(savedInstanceState);
		}
	}

	public void onPostCreate(Bundle savedInstanceState) {
		for (Feature feature : features) {
			feature.onPostCreate(savedInstanceState);
		}
	}

	public void onStart() {
		for (Feature feature : features) {
			feature.onStart();
		}
	}

	public void onRestart() {
		for (Feature feature : features) {
			feature.onRestart();
		}
	}

	public void onResume() {
		for (Feature feature : features) {
			feature.onResume();
		}
	}

	public void onPostResume() {
		for (Feature feature : features) {
			feature.onPostResume();
		}
	}
	
	public Map<Class<? extends Feature>, Feature> getFeatures() {
		return featureMap;
	}

	public void onSaveInstanceState(Bundle outState) {
		for (Feature feature : features) {
			feature.onSaveInstanceState(outState);
		}
	}

	public void onPause() {
		for (Feature feature : features) {
			feature.onPause();
		}
	}

	public void onStop() {
		for (Feature feature : features) {
			feature.onStop();
		}
	}

	public void onDestroy() {
		for (Feature feature : features) {
			feature.onDestroy();
		}
	}
	
	public final Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		Dialog testDialog = null;

		//find the dialog responsible
		for (Feature feature : features) {
			testDialog = feature.onCreateDialog(id);

			// developer error handling
			if (testDialog != null && dialog != null) {
				Set<Feature> featuresDebug = new HashSet<Feature>(features);
				boolean first = false;

				// no point to add StringBuilder/StringBuffer here...
				String output = "[";
				for (Feature featuresTest : featuresDebug) {
					if (featuresTest == feature) {
						break;
					}

					if (first) {
						first = false;
					} else {
						output += ", ";
					}

					output += featuresTest.getClass().getSimpleName();
				}
				output += "]";

				throw new UnsupportedOperationException(
						"Feature "
								+ feature.getClass().getSimpleName()
								+ " tried to initialize a dialog when one was already created by a previous contributor. Previous contributor list: "
								+ output);
			}

			//we've found the dialog responsible, go ahead
			if (testDialog != null) {
				dialog = testDialog;
			}
		}

		return dialog;
	}

	public final void onPrepareDialog(int id, Dialog dialog) {
		for (Feature feature : features) {
			feature.onPrepareDialog(id, dialog);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (Feature feature : features) {
			feature.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void onApplyThemeResource(Theme theme, int resid, boolean first) {
		for (Feature feature : features) {
			feature.onApplyThemeResource(theme, resid, first);
		}
	}

	public void onAttachedToWindow() {
		for (Feature feature : features) {
			feature.onAttachedToWindow();
		}

	}

	public void onBackPressed() {
		for (Feature feature : features) {
			feature.onBackPressed();
		}

	}

	public void onChildTitleChanged(Activity childActivity, CharSequence title) {
		for (Feature feature : features) {
			feature.onChildTitleChanged(childActivity, title);
		}

	}

	public void onConfigurationChanged(Configuration newConfig) {
		for (Feature feature : features) {
			feature.onConfigurationChanged(newConfig);
		}

	}

	public void onContentChanged() {
		for (Feature feature : features) {
			feature.onContentChanged();
		}

	}

	public void onContextMenuClosed(Menu menu) {
		for (Feature feature : features) {
			feature.onContextMenuClosed(menu);
		}

	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		for (Feature feature : features) {
			feature.onCreateContextMenu(menu, v, menuInfo);
		}

	}

	public void onDetachedFromWindow() {
		for (Feature feature : features) {
			feature.onDetachedFromWindow();
		}

	}

	public void onLowMemory() {
		for (Feature feature : features) {
			feature.onLowMemory();
		}

	}

	public void onNewIntent(Intent intent) {
		for (Feature feature : features) {
			feature.onNewIntent(intent);
		}

	}

	public void onOptionsMenuClosed(Menu menu) {
		for (Feature feature : features) {
			feature.onOptionsMenuClosed(menu);
		}

	}

	public void onPanelClosed(int featureId, Menu menu) {
		for (Feature feature : features) {
			feature.onPanelClosed(featureId, menu);
		}

	}

	public void onTitleChanged(CharSequence title, int color) {
		for (Feature feature : features) {
			feature.onTitleChanged(title, color);
		}

	}

	public void onUserInteraction() {
		for (Feature feature : features) {
			feature.onUserInteraction();
		}

	}

	public void onUserLeaveHint() {
		for (Feature feature : features) {
			feature.onUserLeaveHint();
		}

	}

	public void onWindowAttributesChanged(LayoutParams params) {
		for (Feature feature : features) {
			feature.onWindowAttributesChanged(params);
		}

	}

	public void onWindowFocusChanged(boolean hasFocus) {
		for (Feature feature : features) {
			feature.onWindowFocusChanged(hasFocus);
		}

	}

	public boolean onContextItemSelected(boolean value, MenuItem item) {

		for (Feature feature : features) {
			value |= feature.onContextItemSelected(item);
		}
		return value;
	}

	public boolean onCreateOptionsMenu(boolean value, Menu menu) {
		for (Feature feature : features) {
			value |= feature.onCreateOptionsMenu(menu);
		}
		return value;
	}

	public boolean onCreateThumbnail(boolean value, Bitmap outBitmap,
			Canvas canvas) {
		for (Feature feature : features) {
			boolean tempVal = feature.onCreateThumbnail(outBitmap, canvas);
			if (tempVal && value) {
				throw new IllegalStateException(CONFLICT_MSG
						+ feature.getClass());
			}

			value |= tempVal;
		}
		return value;
	}

	public boolean onMenuItemSelected(boolean value, int featureId,
			MenuItem item) {
		for (Feature feature : features) {
			value |= feature.onMenuItemSelected(featureId, item);
		}
		return value;
	}

	public boolean onMenuOpened(boolean value, int featureId, Menu menu) {
		for (Feature feature : features) {
			value |= feature.onMenuOpened(featureId, menu);
		}
		return value;
	}

	public boolean onOptionsItemSelected(boolean value, MenuItem item) {
		for (Feature feature : features) {
			value |= feature.onOptionsItemSelected(item);
		}
		return value;
	}

	public boolean onTrackballEvent(boolean value, MotionEvent event) {
		for (Feature feature : features) {
			value |= feature.onTrackballEvent(event);
		}
		return value;
	}

	public boolean onTouchEvent(boolean value, MotionEvent event) {
		for (Feature feature : features) {
			value |= feature.onTouchEvent(event);
		}
		return value;
	}

	public boolean onSearchRequested(boolean value) {
		for (Feature feature : features) {
			// Here, the behavior is opposite - if one feature
			// blocks, it is blocked. This is consistent with the
			// semantics of the method.
			value &= feature.onSearchRequested();
		}
		return value;
	}

	public Object onRetainNonConfigurationInstance(Object value) {
		for (Feature feature : features) {
			Object tempVal = feature.onRetainNonConfigurationInstance();
			if (tempVal != null && value != null) {
				throw new IllegalStateException(CONFLICT_MSG
						+ feature.getClass());
			}

			value = tempVal;
		}
		return value;
	}

	public boolean onCreatePanelMenu(boolean value, int featureId, Menu menu) {
		for (Feature feature : features) {
			value |= feature.onCreatePanelMenu(featureId, menu);
		}
		return value;
	}

	public boolean onKeyDown(boolean value, int keyCode, KeyEvent event) {
		for (Feature feature : features) {
			value |= feature.onKeyDown(keyCode, event);
		}
		return value;
	}

	public boolean onKeyLongPress(boolean value, int keyCode, KeyEvent event) {
		for (Feature feature : features) {
			value |= feature.onKeyLongPress(keyCode, event);
		}
		return value;
	}

	public boolean onKeyMultiple(boolean value, int keyCode, int repeatCount,
			KeyEvent event) {
		for (Feature feature : features) {
			value |= feature.onKeyMultiple(keyCode, repeatCount, event);
		}
		return value;
	}

	public boolean onKeyUp(boolean value, int keyCode, KeyEvent event) {
		for (Feature feature : features) {
			value |= feature.onKeyUp(keyCode, event);
		}
		return value;
	}

	public boolean onPreparePanel(boolean value, int featureId, View view,
			Menu menu) {
		for (Feature feature : features) {
			value |= feature.onPreparePanel(featureId, view, menu);
		}
		return value;
	}

	public boolean onPrepareOptionsMenu(boolean value, Menu menu) {
		for (Feature feature : features) {
			value |= feature.onPrepareOptionsMenu(menu);
		}
		return value;
	}

}
