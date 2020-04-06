package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Open extends BaseCommand {

	public Open(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		String url=getTarget();
		if(!url.startsWith("file://") && !url.startsWith("http://") && !url.startsWith("https://"))
			testCase.getDriver().get(testCase.getUrl()+url);
		else
			testCase.getDriver().get(url);
		
		publishEvent(getCommand(), getTarget(), getValue(), testCase.getDriver().getPageSource());
	}

}
