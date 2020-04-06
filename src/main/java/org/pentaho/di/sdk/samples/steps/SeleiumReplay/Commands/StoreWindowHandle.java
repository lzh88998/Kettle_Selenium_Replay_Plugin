package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class StoreWindowHandle extends BaseCommand {

	public StoreWindowHandle(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		testCase.setVars(getTarget(), testCase.getDriver().getWindowHandle());
		publishEvent(getCommand(), getTarget(), getValue(), testCase.getDriver().getWindowHandle());
	}

}
