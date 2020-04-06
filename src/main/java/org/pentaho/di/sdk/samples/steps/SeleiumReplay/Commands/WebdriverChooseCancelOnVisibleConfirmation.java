package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class WebdriverChooseCancelOnVisibleConfirmation extends BaseCommand {

	public WebdriverChooseCancelOnVisibleConfirmation(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		testCase.getDriver().switchTo().alert().dismiss();
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
