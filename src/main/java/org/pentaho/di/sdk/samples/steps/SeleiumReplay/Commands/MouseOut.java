package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class MouseOut extends BaseCommand {

	public MouseOut(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebElement ele = testCase.getDriver().findElement(By.tagName("body"));
		Actions act = new Actions(testCase.getDriver());
		publishEvent(getCommand(), getTarget(), getValue(), null);
		act.moveToElement(ele, 0, 0).perform();
	}

}
