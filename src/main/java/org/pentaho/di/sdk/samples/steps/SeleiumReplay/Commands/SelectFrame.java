package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class SelectFrame extends BaseCommand {
	public SelectFrame(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	public void run() throws KettleException {
		String frameLocation = getTarget();
		if(frameLocation == "relative=top" || frameLocation == "relative=parent")
			testCase.getDriver().switchTo().defaultContent();
		else if(frameLocation.startsWith("index="))
			testCase.getDriver().switchTo().frame(Integer.parseInt(frameLocation.substring(6)));
		else
		{
			By by = getTargetLocator(frameLocation);
			WebElement ele = testCase.getDriver().findElement(by);
			testCase.getDriver().switchTo().frame(ele);
		}
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}
}
