package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class EditContent extends BaseCommand {

	public EditContent(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		JavascriptExecutor js = (JavascriptExecutor)testCase.getDriver();
		js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = arguments[1]}", getElement(), getValue());
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
