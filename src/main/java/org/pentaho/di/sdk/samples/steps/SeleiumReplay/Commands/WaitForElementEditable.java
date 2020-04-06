package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class WaitForElementEditable extends BaseCommand {

	public WaitForElementEditable(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(testCase.getDriver(), Long.parseLong(getValue())/1000);
		wait.until(ExpectedConditions.elementToBeClickable(getTargetLocator(getTarget())));
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
