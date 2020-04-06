package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyValue extends BaseCommand {

	public VerifyValue(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		assertThat(getElement().getAttribute("value"), is(getValue()));
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
