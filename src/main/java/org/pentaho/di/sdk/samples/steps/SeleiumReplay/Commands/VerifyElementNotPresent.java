package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class VerifyElementNotPresent extends BaseCommand {

	public VerifyElementNotPresent(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		List<WebElement> eles = getElements();
		publishEvent(getCommand(), getTarget(), getValue(), null);
		assertTrue(eles.size() == 0);
	}

}
