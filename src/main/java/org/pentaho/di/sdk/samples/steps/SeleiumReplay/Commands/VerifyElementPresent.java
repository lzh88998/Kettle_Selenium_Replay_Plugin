package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyElementPresent extends BaseCommand {

	public VerifyElementPresent(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		assertTrue(getElements().size() > 0);
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
