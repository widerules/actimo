/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.activity.core;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.actimo.activity.core.FeatureActivity;
import org.actimo.feature.core.AbstractFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowBitmap;
import com.xtremelabs.robolectric.shadows.ShadowMotionEvent;
import com.xtremelabs.robolectric.view.TestMenu;
import com.xtremelabs.robolectric.view.TestMenuItem;

/**
 * Central unit test for FeatureActivity.
 * @author m.koziarkiewicz
 *
 */
@RunWith(RobolectricTestRunner.class)
public class FeatureActivityTest {

	//TODO: add testing for removal of features
	//TODO: add testing for conflict detection
	//TODO: add testing for dialogs
	
	private static final String SECOND_FEATURE_SUFFIX = "2";

	private FeatureActivity testActivity;
	
	private String testedString;

	private String refString;

	private static final Set<String> METHODS_TO_IGNORE;
	
	static {
		METHODS_TO_IGNORE = new HashSet<String>();
		for(Method method : Object.class.getMethods()) {
			METHODS_TO_IGNORE.add(method.getName()); 
		}
		
		METHODS_TO_IGNORE.add("getActivity");
		METHODS_TO_IGNORE.add("setActivity");
		METHODS_TO_IGNORE.add("getFeature");
	}
	
	
	@Before
	public void setUp() throws Exception {
		refString = "";
		testedString = "";
		testActivity = new FeatureActivity() {
			
			@Override
			protected void initializeFeatures() {
				addFeature(new CheckFeature() {
					
					@Override
					protected String getIdent() {
						return "";
					}
				});
				
			}
		};

	}
	/**
	 * Test class for context menu.
	 * @author m.koziarkiewicz
	 *
	 */
	private class TestContextMenu extends TestMenu implements ContextMenu{

		public ContextMenu setHeaderTitle(int titleRes) {
			return this;
		}

		public ContextMenu setHeaderTitle(CharSequence title) {
			return this;
		}

		public ContextMenu setHeaderIcon(int iconRes) {
			return this;
		}

		public ContextMenu setHeaderIcon(Drawable icon) {
			return this;
		}

		public ContextMenu setHeaderView(View view) {
			return this;
		}

		public void clearHeader() {
		}
		
	}
	
	@Test
	public void testActivityLifeCycle() throws Exception {
		refString = "onSetActivityonCreateonStartonResumeonPauseonStoponDestroy";
		
		testActivity.onCreate(new Bundle());
		testActivity.onStart();
		testActivity.onResume();
		testActivity.onPause();
		testActivity.onStop();
		testActivity.onDestroy();
		
		Assert.assertEquals(refString, testedString);
	}
	

	@Test
	public void testAll() throws Exception {
		
		refString = "onSetActivityonSetActivity2";
		
		testActivity.addFeature(new CheckFeature() {
			
			@Override
			protected String getIdent() {
				return SECOND_FEATURE_SUFFIX;
			}
		});
		
		//test for all methods used 
		
		for(Method refMethod : AbstractFeature.class.getMethods()) {
			if(METHODS_TO_IGNORE.contains(refMethod.getName())) {
				continue;
			}
			
			Method actMethod = FeatureActivity.class.getDeclaredMethod(refMethod.getName(), refMethod.getParameterTypes());
			 Object [] params = new Object[actMethod.getParameterTypes().length];
			 for(int i = 0;i < params.length;i++) {
				 Class<?> clazz = actMethod.getParameterTypes()[i];
				 if(clazz.isPrimitive()) {
					 if(clazz == boolean.class) {
						 params[i] = false;
					 } else {
						 //numeric classes in our case
						 params[i] = 1;
					 }
				 } else {
					 //TODO: replace this with something a bit less ugly
					 if(clazz == CharSequence.class) {
						 params[i] = "";
					 } else if(clazz == KeyEvent.class) {
						 params[i] = new KeyEvent(0,0);
					 } else if(clazz == MotionEvent.class) {
						 params[i] = ShadowMotionEvent.obtain(0, 0, 0, 0, 0, 0);
					 } else if (clazz == MenuItem.class) {
						 params[i] = new TestMenuItem();
					 } else if (clazz == Menu.class) {
						 params[i] = new TestMenu();
					 } else if (clazz == ContextMenu.class) {
						 params[i] = new TestContextMenu();
					 } else if (clazz == ContextMenuInfo.class) {
						 params[i] = new ContextMenuInfo() {};
					 } else if (clazz == Bitmap.class) {
						 params[i] = ShadowBitmap.create("");
					 } else if (clazz == Object.class) {
						 params[i] = null;
					 } else {
						 params[i] = clazz.newInstance();
					 }
					
				 }
				
			 }

			actMethod.invoke(testActivity,params);
			refString += actMethod.getName()+actMethod.getName()+SECOND_FEATURE_SUFFIX;
		}
		Assert.assertEquals(refString, testedString);
	}
	
	/**
	 * Base Feature for testing. Has an abstract getIdent() method used to
	 * distinguish between different features.
	 * @author m.koziarkiewicz
	 *
	 */
	private abstract class CheckFeature extends AbstractFeature {
		protected abstract String getIdent();

		/**
		 * Utility method for adding stack information.
		 * This hack replaces the need to explicitly name the calling 
		 * method in each of them.
		 */
		private void addFromStack() {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			testedString += stackTrace[2].getMethodName()+getIdent();
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			addFromStack();
		}

		@Override
		public Dialog onCreateDialog(int id) {
			addFromStack();
			return null;
		}

		@Override
		public void onDestroy() {
			addFromStack();
		}

		@Override
		public void onPause() {
			addFromStack();
		}

		@Override
		public void onPostCreate(Bundle savedInstanceState) {
			addFromStack();
		}

		@Override
		public void onPostResume() {
			addFromStack();
		}

		@Override
		public void onPrepareDialog(int id, Dialog dialog) {
			addFromStack();
		}

		@Override
		public void onRestart() {
			addFromStack();
		}

		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
			addFromStack();
		}

		@Override
		public void onResume() {
			addFromStack();
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			addFromStack();
		}

		@Override
		public void onStart() {
			addFromStack();
		}

		@Override
		public void onStop() {
			addFromStack();
		}

		@Override
		protected void onSetActivity() {
			addFromStack();
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			addFromStack();
		}

		@Override
		public void onApplyThemeResource(Theme theme, int resid, boolean first) {
			addFromStack();
		}

		@Override
		public void onAttachedToWindow() {
			addFromStack();
		}

		@Override
		public void onBackPressed() {
			addFromStack();
		}

		@Override
		public void onChildTitleChanged(Activity childActivity,
				CharSequence title) {
			addFromStack();
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			addFromStack();
		}

		@Override
		public void onContentChanged() {
			addFromStack();
		}

		@Override
		public void onContextMenuClosed(Menu menu) {
			addFromStack();
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			addFromStack();
		}

		@Override
		public void onDetachedFromWindow() {
			addFromStack();
		}

		@Override
		public void onLowMemory() {
			addFromStack();
		}

		@Override
		public void onNewIntent(Intent intent) {
			addFromStack();
		}

		@Override
		public void onOptionsMenuClosed(Menu menu) {
			addFromStack();
		}

		@Override
		public void onPanelClosed(int featureId, Menu menu) {
			addFromStack();
		}

		@Override
		public void onTitleChanged(CharSequence title, int color) {
			addFromStack();
		}

		@Override
		public void onUserInteraction() {
			addFromStack();
		}

		@Override
		public void onUserLeaveHint() {
			addFromStack();
		}

		@Override
		public void onWindowAttributesChanged(LayoutParams params) {
			addFromStack();
		}

		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			addFromStack();
		}

		@Override
		public boolean onContextItemSelected(MenuItem item) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onMenuOpened(int featureId, Menu menu) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onTrackballEvent(MotionEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onSearchRequested() {
			addFromStack();
			return true;
		}

		@Override
		public Object onRetainNonConfigurationInstance() {
			addFromStack();
			return null;
		}

		@Override
		public boolean onCreatePanelMenu(int featureId, Menu menu) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onKeyLongPress(int keyCode, KeyEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onKeyMultiple(int keyCode, int repeatCount,
				KeyEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onPreparePanel(int featureId, View view, Menu menu) {
			addFromStack();
			return false;
		}

		@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
			addFromStack();
			return false;
		}

	}
}
