package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class StoreTitle extends BaseCommand {

	public StoreTitle(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		testCase.setVars(getTarget(), testCase.getDriver().getTitle());
		publishEvent(getCommand(), getTarget(), getValue(), testCase.getDriver().getTitle());
	}

}
