package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Pause extends BaseCommand {

	public Pause(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		try
		{
			long timeout = Long.parseLong(getTarget());
			Thread.sleep(timeout);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
