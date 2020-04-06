package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class StoreAttribute extends BaseCommand {

	public StoreAttribute(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		String locator = getTarget();
		int attributePos = locator.lastIndexOf('@');
		String elementLocator = locator.substring(0, attributePos);
		String attributeName = locator.substring(attributePos + 1);
		By by = getTargetLocator(elementLocator);
		List<WebElement> eles = testCase.getDriver().findElements(by);
		for(int i = 0; i < eles.size(); i++) {
			WebElement ele = eles.get(i);
			WebDriverWait wait = new WebDriverWait(testCase.getDriver(), 10);
			wait.until(ExpectedConditions.visibilityOf(ele));
			String attribute = ele.getAttribute(attributeName);
			testCase.setVars(getValue(), attribute);
			publishEvent(getCommand(), getTarget(), getValue(), attribute);
		}
	}

}
