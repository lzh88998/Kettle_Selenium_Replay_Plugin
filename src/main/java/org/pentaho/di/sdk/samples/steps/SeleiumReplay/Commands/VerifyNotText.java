package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyNotText extends BaseCommand {

	public VerifyNotText(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		assertThat(getElement().getText(), is(not(getValue())));
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
