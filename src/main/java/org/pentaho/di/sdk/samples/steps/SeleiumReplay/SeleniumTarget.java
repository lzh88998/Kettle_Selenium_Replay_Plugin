package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import java.util.List;

import org.json.simple.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;

public class SeleniumTarget {
	
	private String path = null;
	private String name = null;
	
	public SeleniumTarget(JSONArray json) {
		path = (String)json.get(0);
		if(json.size() > 1)
			name = (String)json.get(1);
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	public List<WebElement> getElement(SeleniumTestCase testCase) throws KettleException {
		String[] args =  path.split("=");
		if(2 != args.length)
			throw new KettleException("Invalid side file with path: "+path);
		
		String method = args[0];
		String locator = args[1];
		
		By by = null;
		List<WebElement> ret = null;
		switch(method)
		{
		case "id":
			by = By.id(locator);
			break;
		case "name":
			by = By.name(locator);
			break;
		case "link":
			by = By.linkText(locator);
			break;
		case "linkText":
			by = By.linkText(locator);
			break;
		case "partialLikText":
			by = By.partialLinkText(locator);
			break;
		case "css":
			by = By.cssSelector(locator);
			break;
		case "xpath":
			by = By.xpath(locator);
			break;
		default:
			throw new KettleException("Invalid lookup method: "+method);
		}
		
		ret = testCase.getDriver().findElements(by);
		
		return ret;
	}
}
