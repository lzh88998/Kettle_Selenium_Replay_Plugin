package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class WebdriverAnswerOnVisiblePrompt extends BaseCommand {

	public WebdriverAnswerOnVisiblePrompt(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Alert alert = testCase.getDriver().switchTo().alert();
		alert.sendKeys(getValue());
		alert.accept();
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
