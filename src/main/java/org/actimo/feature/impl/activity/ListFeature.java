/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 * ----------------------------------------------------
 * ----------------------------------------------------
 * This file contains code from the Android project, with the following license:
 * ------------------------
 * 
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.actimo.feature.impl.activity;

import org.actimo.feature.core.AbstractFeature;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Reimplementation of ListActivity as a Feature. Conforms to Android 2.1-2.3, not 
 * tested on 3+.
 * @author m.koziarkiewicz
 *
 */
public class ListFeature extends AbstractFeature {
    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    private ListAdapter mAdapter;
    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    private ListView mList;

    private Handler mHandler = new Handler();
    private boolean mFinishedStart = false;

    private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };
    
    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     * 
     * @param l The ListView where the click happened
     * @param v The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id The row id of the item that was clicked
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
    }
    
    /**
     * Ensures the list view has been created before Activity restores all
     * of the view states.
     * 
     *@see Activity#onRestoreInstanceState(Bundle)
     */
    @Override
    public void onRestoreInstanceState(Bundle state) {
        ensureList();
        super.onRestoreInstanceState(state);
    }
    
    /**
     * @see Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRequestFocus);
        super.onDestroy();
    }

    /**
     * Updates the screen state (current list and other views) when the
     * content changes.
     * 
     * @see Activity#onContentChanged()
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        View emptyView = getActivity().findViewById(Resources.getSystem().getIdentifier("empty", "id", "android"));
        mList = (ListView)getActivity().findViewById(Resources.getSystem().getIdentifier("list", "id", "android"));
        if (mList == null) {
            throw new RuntimeException(
                    "Your content must have a ListView whose id attribute is " +
                    "'android.R.id.list'");
        }
        if (emptyView != null) {
            mList.setEmptyView(emptyView);
        }
        mList.setOnItemClickListener(mOnClickListener);
        if (mFinishedStart) {
            setListAdapter(mAdapter);
        }
        mHandler.post(mRequestFocus);
        mFinishedStart = true;
    }

    /**
     * Provide the cursor for the list view.
     */
    public void setListAdapter(ListAdapter adapter) {
        synchronized (this) {
            ensureList();
            mAdapter = adapter;
            mList.setAdapter(adapter);
        }
    }

    /**
     * Set the currently selected list item to the specified
     * position with the adapter's data
     * 
     * @param position
     */
    public void setSelection(int position) {
        mList.setSelection(position);
    }

    /**
     * Get the position of the currently selected list item.
     */
    public int getSelectedItemPosition() {
        return mList.getSelectedItemPosition();
    }

    /**
     * Get the cursor row ID of the currently selected list item.
     */
    public long getSelectedItemId() {
        return mList.getSelectedItemId();
    }

    /**
     * Get the activity's list view widget.
     */
    public ListView getListView() {
        ensureList();
        return mList;
    }
    
    /**
     * Get the ListAdapter associated with this activity's ListView.
     */
    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    private void ensureList() {
        if (mList != null) {
            return;
        }
        getActivity().setContentView(Resources.getSystem().getIdentifier("list_content", "layout", "android"));
        
    }

    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            onListItemClick((ListView)parent, v, position, id);
        }
    };
}
