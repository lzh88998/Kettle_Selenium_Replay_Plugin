package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.junit.Assert.assertFalse;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyNotEditable extends BaseCommand {

	public VerifyNotEditable(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebElement ele = getElement();
		assertFalse(ele.isEnabled() && ele.getAttribute("readonly") == null);
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
