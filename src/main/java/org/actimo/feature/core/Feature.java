/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.core;

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
 * Main interface for contributors. Lets functionality for activities be written in a modular way.
 * @author   Mikołaj Koziarkiewicz
 */
public interface Feature {

	
	/**
	 * Set the activity assigned for this contributor. In general, a Feature should service only one Activity per its lifetime.
	 * @param activity   the activity to service
	 */
	public void setActivity(Activity activity);

	/**
	 * @return   the serviced Activity, or <code>null</code> if none is serviced
	 */
	public Activity getActivity();
	
	/**
	 * Convenience method for accessing other features from this one.
	 * @param <T>
	 * @param clazz
	 * @return the requested feature.
	 */
	public <T extends Feature> T getFeature(Class<T> clazz);
	
	/**
	 * @see Activity#onCreate(Bundle savedInstanceState)
	 */
	public void onCreate(Bundle savedInstanceState);

	/**
	 * @see Activity#onCreateDialog(int id)
	 */
	public Dialog onCreateDialog(int id);

	/**
	 * @see Activity#onDestroy()
	 */
	public void onDestroy();

	/**
	 * @see Activity#onPause()
	 */
	public void onPause();

	/**
	 * @see Activity#onPostCreate(Bundle savedInstanceState)
	 */
	public void onPostCreate(Bundle savedInstanceState);

	/**
	 * @see Activity#onPostResume()
	 */
	public void onPostResume();

	/**
	 * @see Activity#onPrepareDialog(int id, Dialog dialog)
	 */
	public void onPrepareDialog(int id, Dialog dialog);

	/**
	 * @see Activity#onRestart()
	 */
	public void onRestart();

	/**
	 * @see Activity#onRestoreInstanceState(Bundle savedInstanceState)
	 */
	public void onRestoreInstanceState(Bundle savedInstanceState);

	/**
	 * @see Activity#onResume()
	 */
	public void onResume();

	/**
	 * @see Activity#onSaveInstanceState(Bundle outState)
	 */
	public void onSaveInstanceState(Bundle outState);

	/**
	 * @see Activity#onStart()
	 */
	public void onStart();

	/**
	 * @see Activity#onStop()
	 */
	public void onStop();


	public void onActivityResult(int requestCode, int resultCode, Intent data);

	public void onApplyThemeResource(Theme theme, int resid, boolean first);

	public void onAttachedToWindow();

	public void onBackPressed();

	public void onChildTitleChanged(Activity childActivity, CharSequence title);

	public void onConfigurationChanged(Configuration newConfig);

	public void onContentChanged();

	public void onContextMenuClosed(Menu menu);

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo);

	public void onDetachedFromWindow();

	public void onLowMemory();

	public void onNewIntent(Intent intent);

	public void onOptionsMenuClosed(Menu menu);

	public void onPanelClosed(int featureId, Menu menu);

	public void onTitleChanged(CharSequence title, int color);

	public void onUserInteraction();

	public void onUserLeaveHint();

	public void onWindowAttributesChanged(LayoutParams params);

	public void onWindowFocusChanged(boolean hasFocus);

	public boolean onContextItemSelected(MenuItem item);

	public boolean onCreateOptionsMenu(Menu menu);

	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas);

	public boolean onMenuItemSelected(int featureId, MenuItem item);

	public boolean onMenuOpened(int featureId, Menu menu);

	public boolean onOptionsItemSelected(MenuItem item);

	public boolean onTrackballEvent(MotionEvent event);

	public boolean onTouchEvent(MotionEvent event);

	public boolean onSearchRequested();

	public Object onRetainNonConfigurationInstance();

	public boolean onCreatePanelMenu(int featureId, Menu menu);

	public boolean onKeyDown(int keyCode, KeyEvent event);

	public boolean onKeyLongPress(int keyCode, KeyEvent event);

	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event);

	public boolean onKeyUp(int keyCode, KeyEvent event);

	public boolean onPreparePanel(int featureId, View view, Menu menu);

	public boolean onPrepareOptionsMenu(Menu menu);


}
