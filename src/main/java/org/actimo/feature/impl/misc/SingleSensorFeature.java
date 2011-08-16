/**
 * Copyright 2011 Actimo contributors.
 * This project is subject to the 2-clause BSD License, as outlined 
 * in the LICENSE file.
 */
package org.actimo.feature.impl.misc;

import org.actimo.feature.core.AbstractFeature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

/**
 * A feature that allows one to use data from a single sensor.
 * @author m.koziarkiewicz
 *
 */
public abstract class SingleSensorFeature extends AbstractFeature implements SensorEventListener {

	private static final int DEFAULT_DELAY = SensorManager.SENSOR_DELAY_NORMAL;
	
	private SensorManager sensorManager;
	
	protected Sensor sensor;
	
	private int delay;

	private int sensorType;
	
	/**
	 * Create a new sensor feature with default delay.
	 * @param sensorType type, from {@link SensorManager}
	 */
	public SingleSensorFeature(int sensorType) {
		this(sensorType,-1);
	}
	
	/**
	 * Create a new sensor feature.
	 * @param sensorType type, from {@link SensorManager}
	 * @param delay delay, from {@link SensorManager}
	 */
	public SingleSensorFeature(int sensorType,int delay) {
		super();
		this.delay = (delay != -1) ? delay : DEFAULT_DELAY;
		this.sensorType = sensorType;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensorType);

	}
	
	@Override
	public void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor, delay);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//default no-op implementation, since not every sensor requires this
	}
	
}
