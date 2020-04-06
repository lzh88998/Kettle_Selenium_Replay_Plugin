package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Assert extends BaseCommand {

	public Assert(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		publishEvent(getCommand(), getTarget(), getValue(), null);
		assertEquals(getVars(getTarget()), getValue());
	}

}
