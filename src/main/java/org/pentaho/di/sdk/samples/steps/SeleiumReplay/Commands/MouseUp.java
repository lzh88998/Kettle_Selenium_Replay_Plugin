package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class MouseUp extends BaseCommand {

	public MouseUp(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		Actions act = new Actions(testCase.getDriver());
		WebElement ele = getElement();
		publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
		act.moveToElement(ele).release().perform();
	}

}
