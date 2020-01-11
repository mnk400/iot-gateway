/**
 * Copyright (c) 2019-2020. Andrew D. King. All Rights Reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.labbenchstudios.iot.common;

import java.util.logging.Logger;

import com.labbenchstudios.iot.common.DeviceApplicationException;
import com.labbenchstudios.iot.common.BaseDeviceApp;

/**
 * This is a simple example of a main application derived
 * from {@link com.labbenchstudios.iot.common.BaseDeviceApp}.
 * Functionally, it simply starts the app, logs a message, and exits.
 * 
 */
public class SampleDeviceApp extends BaseDeviceApp
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(SampleDeviceApp.class.getSimpleName());
	
	/**
	 * Main method - application entrance.
	 * 
	 * @param args Command line args.
	 */
	public static void main(String[] args)
	{
		SampleDeviceApp app = new SampleDeviceApp(args);
		app.startApp();
	}
	
	// private var's
	
	
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public SampleDeviceApp()
	{
		super(SampleDeviceApp.class.getSimpleName());
	}
	
	/**
	 * Constructor.
	 * 
	 * @param args Command line args.
	 */
	public SampleDeviceApp(String[] args)
	{
		super(SampleDeviceApp.class.getSimpleName(), args);
	}
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.iot.common.BaseDeviceApp#start()
	 */
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - SampleDeviceApp here!");
		
	}
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.iot.common.BaseDeviceApp#stop()
	 */
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping SampleDeviceApp app...");
	}
	
}
