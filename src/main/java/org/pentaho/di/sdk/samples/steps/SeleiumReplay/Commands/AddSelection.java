package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class AddSelection extends BaseCommand {

	public AddSelection(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		String value=getValue();
		String[] args =  value.split("=");
		if(2 != args.length)
			throw new KettleException("Invalid side file with value: "+value);
		
		String method = args[0];
		String locator = args[1];
		
		By by = null;
		switch(method)
		{
		case "id":
			by = By.cssSelector("*[id='"+locator+"']");
			break;
		case "value":
			by = By.cssSelector("*[value='"+locator+"']");
			break;
		case "label":
			by = By.xpath("//option[.='"+locator+"']");
			break;
		case "index":
			by = By.cssSelector("*:nth-child("+locator+")1");
			break;
		default:
			throw new KettleException("Invalid lookup method: "+method);
		}
		
		WebElement ele = getElement();
		publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
		ele.findElement(by).click();
	}

}
