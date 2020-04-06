package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Check extends BaseCommand {

	public Check(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	public void run() throws KettleException {
		WebElement ele = getElement();
		publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
		if(!ele.isSelected())
			ele.click();
	}
}
