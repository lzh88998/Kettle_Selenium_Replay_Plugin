package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.junit.Assert.assertFalse;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyNotChecked extends BaseCommand {

	public VerifyNotChecked(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebElement ele = getElement();
		assertFalse(getElement().isSelected());
		publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
	}

}
