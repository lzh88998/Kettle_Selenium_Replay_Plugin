package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.Dimension;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class SetWindowSize extends BaseCommand {

	public SetWindowSize(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		String[] strs = getTarget().split("x");
		testCase.getDriver().manage().window().setSize(new Dimension(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
