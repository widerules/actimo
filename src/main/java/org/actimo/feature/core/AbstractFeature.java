/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.core;

import org.actimo.activity.IFeatureActivity;

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
 * Convenience class for implementing  {@link Feature}s.
 * @author  Mikołaj Koziarkiewicz
 */
public abstract class AbstractFeature implements Feature {

	private Activity activity;

	public void onCreate(Bundle savedInstanceState) {
	}

	public Dialog onCreateDialog(int id) {
		return null;
	} 

	public void onDestroy() {
	}

	public void onPause() {
	}

	public void onPostCreate(Bundle savedInstanceState) {
	}

	public void onPostResume() {
	}

	
	public void onPrepareDialog(int id, Dialog dialog) {
	}

	
	public void onRestart() {
	}

	
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	}

	
	public void onResume() {
	}

	
	public void onSaveInstanceState(Bundle outState) {
	}

	
	public void onStart() {
	}

	
	public void onStop() {
	}

	public final void setActivity(Activity activity) {
		this.activity = activity;
		onSetActivity();
	}

	/**
	 * Hook for initialization stuff when an activity is set.
	 */
	protected void onSetActivity() {
	};

	/**
	 * @return
	 * @uml.property  name="activity"
	 */
	
	public final Activity getActivity() {
		return activity;
	}
	
	public final <T extends Feature> T getFeature(Class<T> clazz) {
		return ((IFeatureActivity) activity).getFeature(clazz);
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
	}

	public void onApplyThemeResource(Theme theme, int resid, boolean first) {
		
		
	}

	public void onAttachedToWindow() {
		
		
	}

	public void onBackPressed() {
		
		
	}

	public void onChildTitleChanged(Activity childActivity, CharSequence title) {
		
		
	}

	public void onConfigurationChanged(Configuration newConfig) {
		
		
	}

	public void onContentChanged() {
		
		
	}

	public void onContextMenuClosed(Menu menu) {
		
		
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		
	}

	public void onDetachedFromWindow() {
		
		
	}

	public void onLowMemory() {
		
		
	}

	public void onNewIntent(Intent intent) {
		
		
	}

	public void onOptionsMenuClosed(Menu menu) {
		
		
	}

	public void onPanelClosed(int featureId, Menu menu) {
		
		
	}

	public void onTitleChanged(CharSequence title, int color) {
		
		
	}

	public void onUserInteraction() {
		
		
	}

	public void onUserLeaveHint() {
		
		
	}

	public void onWindowAttributesChanged(LayoutParams params) {
		
		
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		
		
	}

	public boolean onContextItemSelected(MenuItem item) {
		
		return false;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		
		return false;
	}

	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
		
		return false;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		return false;
	}

	public boolean onMenuOpened(int featureId, Menu menu) {
		
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		
		return false;
	}

	public boolean onTrackballEvent(MotionEvent event) {
		
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		
		return false;
	}

	public boolean onSearchRequested() {
		//consistent with method semantics
		return true;
	}

	public Object onRetainNonConfigurationInstance() {
		
		return null;
	}

	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		return false;
	}

	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		return false;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onPreparePanel(int featureId, View view, Menu menu) {
		return false;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}
	
	

}
