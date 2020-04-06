package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class WaitForElementNotPresent extends BaseCommand {
	public WaitForElementNotPresent(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(testCase.getDriver(), Long.parseLong(getValue())/1000);
		WebElement ele = testCase.getDriver().findElement(getTargetLocator(getTarget()));
		wait.until(ExpectedConditions.stalenessOf(ele));
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}
}
