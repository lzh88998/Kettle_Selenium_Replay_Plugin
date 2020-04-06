package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTarget;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public abstract class BaseCommand {
	protected String id = null;
	protected String comment = null;
	protected String command = null;
	protected String target = null;
	protected ArrayList<SeleniumTarget> targets = null;
	protected String value = null;
	protected ArrayList<BaseCommand> childCommands = null;

	protected SeleniumTestCase testCase = null;

	public BaseCommand(SeleniumTestCase testCase, JSONObject json){
		this.testCase = testCase;
		id = (String)json.get("id");
		comment = (String)json.get("comment");
		command = (String)json.get("command");
		target = (String)json.get("target");
		value = (String)json.get("value");
		targets = new ArrayList<SeleniumTarget>();
		childCommands = new ArrayList<BaseCommand>();
		this.testCase = testCase;
		
		JSONArray testCases = (JSONArray)json.get("targets");
		
		for(int i = 0; i < testCases.size(); i++) {
			JSONArray jsonTarget = (JSONArray)testCases.get(i);
			SeleniumTarget target = new SeleniumTarget(jsonTarget);
			targets.add(target);
		}
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public String getCommand() {
		return this.command;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public String getValue() {
		if(null != testCase) {
			String replaceValue = testCase.getReplaceValue(getID());
			if(null != replaceValue) {
				return replaceValue;
			}
		}
		return this.value;
	}
	
	public BaseCommand[] getCommands() {
		BaseCommand[] ret = new BaseCommand[1];
		ret[0] = this;
		return ret;
	}
	
	public void AddChild(BaseCommand cmd) {
		childCommands.add(cmd);
	}
	
	public abstract void run() throws KettleException;

	protected By getTargetLocator(String target) throws KettleException {
		int pos = target.indexOf('=');
		if(pos < 0)
			throw new KettleException("Invalid side file with target: "+target);

		String method = target.substring(0, pos);
		String locator = target.substring(pos+1);
		
		By by = null;
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
		return by;
	}
	
	protected WebElement getElement(String locator) throws KettleException {
		return testCase.getDriver().findElement(getTargetLocator(locator));
	}
	
	protected WebElement getElement() throws KettleException {
		return getElement(getTarget());
	}
	
	protected List<WebElement> getElements() throws KettleException {
		return testCase.getDriver().findElements(getTargetLocator(target));
	}

	protected CharSequence[] getKeycode(String s){
		ArrayList<CharSequence> lst = new ArrayList<CharSequence>();
		lst.add(s);
		return lst.toArray(new CharSequence[0]);
	}
	
	protected Object getVars(String name) {
		return testCase.getVars(name);
	}

	protected void publishEvent(String command, String target, String value, String text) {
		testCase.publishEvent(command, target, value, text);
	}
}
