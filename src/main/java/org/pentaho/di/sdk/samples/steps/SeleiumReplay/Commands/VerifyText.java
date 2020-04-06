package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyText extends BaseCommand {

	public VerifyText(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebElement ele = getElement();
		assertThat(ele.getText(), is(getValue()));
		publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
	}

}
