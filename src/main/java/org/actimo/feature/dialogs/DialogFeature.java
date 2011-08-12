/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.dialogs;

import org.actimo.feature.core.AbstractFeature;

import android.app.Activity;
import android.app.Dialog;


/**
 * A convenience class for creating contributors that add a dialog.
 * 
 * @author Mikołaj Koziarkiewicz
 */
public abstract class DialogFeature extends AbstractFeature {

	
	/**
	 * Creates the dialog.
	 * 
	 * @return the dialog
	 * @see Activity#onCreateDialog(int id)
	 */
	protected abstract Dialog createDialog();

	/**
	 * Prepares the dialog.
	 * 
	 * @param dialog
	 *            the dialog to be prepared
	 * @see Activity#onPrepareDialog(int id, Dialog dialog)
	 */
	protected void prepareDialog(Dialog dialog) {/*no-op, optional*/}

	/**
	 * Get the unique String ID for this dialog. In general, different dialog
	 * contributors should have different string identifiers.
	 * 
	 * @return the identifier
	 */
	protected abstract String getId();

	/**
	 * Shortcut for {@link Activity#showDialog(int)}.
	 */
	public final void show() {
		getActivity().showDialog(DialogManager.get().getId(getId()));
	}
	
	@Override
	protected void onSetActivity() {
		if (!DialogManager.get().hasId(getId())) {
			DialogManager.get().registerId(getId());
		}
	}

	@Override
	public final Dialog onCreateDialog(int id) {
		if (DialogManager.get().getId(getId()) == id) {
			return createDialog();
		} else {
			return null;
		}
	}

	@Override
	public final void onPrepareDialog(int id, Dialog dialog) {
		if (DialogManager.get().getId(getId()) == id) {
			prepareDialog(dialog);
		}
	}
	
	public final void dismiss() {
		getActivity().dismissDialog(DialogManager.get().getId(getId()));
	}


}
