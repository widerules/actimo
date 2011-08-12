/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.activity;

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
 * An activity that allows the use of contributors.
 * 
 * @author Mikołaj Koziarkiewicz
 */
public abstract class FeatureActivity extends Activity implements
		IFeatureActivity,IFeatureMarkerV1 {

	protected final FeatureManager featureManager;

	/**
	 * Create a new activity prepared to accept contributors.
	 */
	public FeatureActivity() {
		super();
		featureManager = new FeatureManager(this);
		initializeFeatures();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.actimo.activity.IFeatureActivity#addFeature(org.actimo.feature.core
	 * .Feature)
	 */
	public final void addFeature(Feature feature) {
		featureManager.addFeature(feature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.actimo.activity.IFeatureActivity#removeFeature(org.actimo.feature
	 * .core.Feature)
	 */
	public final void removeFeature(Feature feature) {
		featureManager.removeFeature(feature);
	}

	/**
	 * Called in {@link #onCreate(Bundle)}, should be used to initialize
	 * contributors.
	 */
	protected abstract void initializeFeatures();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		featureManager.onCreate(savedInstanceState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		featureManager.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		featureManager.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		featureManager.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		featureManager.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		featureManager.onResume();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		featureManager.onPostResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		featureManager.onSaveInstanceState(outState);
	}

	@SuppressWarnings("unchecked")
	public <T extends Feature> T getFeature(Class<T> clazz) {
		return (T) featureManager.getFeatures().get(clazz);
	}

	@Override
	protected void onPause() {
		super.onPause();

		featureManager.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

		featureManager.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		featureManager.onDestroy();
	}

	@Override
	protected final Dialog onCreateDialog(int id) {
		Dialog dialog = super.onCreateDialog(id);
		if (dialog == null) {
			// future-proofing, in case some standard dialogs
			// come in later releases (this will never happen
			// currently).
			return featureManager.onCreateDialog(id);
		}
		return dialog;
	}

	@Override
	protected final void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		featureManager.onPrepareDialog(id, dialog);
	}

	/**
	 * Added for compatbility with Android 2.2+.
	 * 
	 * @param id
	 * @param args
	 * @return
	 */
	protected final Dialog onCreateDialog(int id, Bundle args) {
		// this is just to block trying going around the final in the other
		// method
		return this.onCreateDialog(id);
	}

	/**
	 * Added for compatbility with Android 2.2+.
	 * 
	 * @param id
	 * @param dialog
	 * @param args
	 */
	protected final void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		// this is just to block trying going around the final in the other
		// method
		this.onPrepareDialog(id, dialog);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		featureManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onApplyThemeResource(Theme theme, int resid, boolean first) {

		super.onApplyThemeResource(theme, resid, first);
		featureManager.onApplyThemeResource(theme, resid, first);
	}

	@Override
	public void onAttachedToWindow() {

		super.onAttachedToWindow();
		featureManager.onAttachedToWindow();
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		featureManager.onBackPressed();
	}

	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {

		super.onChildTitleChanged(childActivity, title);
		featureManager.onChildTitleChanged(childActivity, title);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		featureManager.onConfigurationChanged(newConfig);
	}

	@Override
	public void onContentChanged() {

		super.onContentChanged();
		featureManager.onContentChanged();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return featureManager.onContextItemSelected(
				super.onContextItemSelected(item), item);
	}

	@Override
	public void onContextMenuClosed(Menu menu) {

		super.onContextMenuClosed(menu);
		featureManager.onContextMenuClosed(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		featureManager.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		return featureManager.onCreatePanelMenu(
				super.onCreatePanelMenu(featureId, menu), featureId, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return featureManager.onCreateOptionsMenu(
				super.onCreateOptionsMenu(menu), menu);
	}

	@Override
	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {

		return featureManager.onCreateThumbnail(
				super.onCreateThumbnail(outBitmap, canvas), outBitmap, canvas);
	}

	@Override
	public void onDetachedFromWindow() {

		super.onDetachedFromWindow();
		featureManager.onDetachedFromWindow();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return featureManager.onKeyDown(super.onKeyDown(keyCode, event),
				keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return featureManager.onKeyLongPress(
				super.onKeyLongPress(keyCode, event), keyCode, event);
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {

		return featureManager.onKeyMultiple(
				super.onKeyMultiple(keyCode, repeatCount, event), keyCode,
				repeatCount, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		return featureManager.onKeyUp(super.onKeyUp(keyCode, event), keyCode,
				event);
	}

	@Override
	public void onLowMemory() {

		super.onLowMemory();
		featureManager.onLowMemory();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		return featureManager.onMenuItemSelected(
				super.onMenuItemSelected(featureId, item), featureId, item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		return featureManager.onMenuOpened(super.onMenuOpened(featureId, menu),
				featureId, menu);
	}

	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		featureManager.onNewIntent(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return featureManager.onOptionsItemSelected(
				super.onOptionsItemSelected(item), item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {

		super.onOptionsMenuClosed(menu);
		featureManager.onOptionsMenuClosed(menu);
	}

	@Override
	public void onPanelClosed(int featureId, Menu menu) {

		super.onPanelClosed(featureId, menu);
		featureManager.onPanelClosed(featureId, menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return featureManager.onPrepareOptionsMenu(
				super.onPrepareOptionsMenu(menu), menu);
	}

	@Override
	public boolean onPreparePanel(int featureId, View view, Menu menu) {

		return featureManager.onPreparePanel(
				super.onPreparePanel(featureId, view, menu), featureId, view,
				menu);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {

		return featureManager.onRetainNonConfigurationInstance(super
				.onRetainNonConfigurationInstance());
	}

	@Override
	public boolean onSearchRequested() {

		return featureManager.onSearchRequested(super.onSearchRequested());
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {

		super.onTitleChanged(title, color);
		featureManager.onTitleChanged(title, color);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return featureManager.onTouchEvent(super.onTouchEvent(event), event);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {

		return featureManager.onTrackballEvent(super.onTrackballEvent(event),
				event);
	}

	@Override
	public void onUserInteraction() {

		super.onUserInteraction();
		featureManager.onUserInteraction();
	}

	@Override
	protected void onUserLeaveHint() {

		super.onUserLeaveHint();
		featureManager.onUserLeaveHint();
	}

	@Override
	public void onWindowAttributesChanged(LayoutParams params) {

		super.onWindowAttributesChanged(params);
		featureManager.onWindowAttributesChanged(params);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);
		featureManager.onWindowFocusChanged(hasFocus);
	}

}
