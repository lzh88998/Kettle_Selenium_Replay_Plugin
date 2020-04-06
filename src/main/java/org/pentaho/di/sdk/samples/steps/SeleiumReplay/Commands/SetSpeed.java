package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class SetSpeed extends BaseCommand {

	public SetSpeed(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		System.out.println("'Set speed' is a no-op in code export, use 'pause' instead!");
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
