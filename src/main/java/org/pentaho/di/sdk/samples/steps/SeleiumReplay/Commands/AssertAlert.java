package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class AssertAlert extends BaseCommand {

	public AssertAlert(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		publishEvent(getCommand(), getTarget(), getValue(), testCase.getDriver().switchTo().alert().getText());
		assertThat(testCase.getDriver().switchTo().alert().getText(), is(getTarget()));
	}

}
