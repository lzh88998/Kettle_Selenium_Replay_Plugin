package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class StoreXpathCount extends BaseCommand {
	public StoreXpathCount(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		List<WebElement> eles = getElements();
		testCase.setVars(getTarget(), eles.size());
		publishEvent(getCommand(), getTarget(), getValue(), Integer.toString(eles.size()));
	}

}
