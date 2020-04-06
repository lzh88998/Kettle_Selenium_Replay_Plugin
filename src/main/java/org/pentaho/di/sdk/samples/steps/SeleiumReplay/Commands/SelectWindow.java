package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class SelectWindow extends BaseCommand {

	public SelectWindow(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		String[] strs = testCase.getDriver().getWindowHandles().toArray(new String[] {});
		for(int i =0; i < strs.length; i++)
			System.out.println("Windows Handle: "+strs[i]);
		
		
		String windowLocation = getTarget();
		System.out.println("Selecting Window: "+ windowLocation);
		

		if(windowLocation.startsWith("handle=")) {
			String handleName = windowLocation.substring(7);
			System.out.println("Selecting window with handle name: " + handleName);
			String handle = testCase.getVars(handleName).toString();
			System.out.println("Selecting window with handle: " + handle);

			testCase.getDriver().switchTo().window(handle);
		}
		else if(windowLocation.startsWith("name="))
			testCase.getDriver().switchTo().window(windowLocation.substring(5));
		else if(windowLocation.startsWith("win_ser_")) {
			if(windowLocation == "win_ser_local") {
				ArrayList<String> handles = new ArrayList<String>(testCase.getDriver().getWindowHandles());
				testCase.getDriver().switchTo().window(handles.get(0));
			}
			else {
				int index = Integer.parseInt(windowLocation.substring(8));
				ArrayList<String> handles = new ArrayList<String>(testCase.getDriver().getWindowHandles());
				testCase.getDriver().switchTo().window(handles.get(index));
			}
		}
		else {
			throw new KettleException("Invalid side file with unknown select window handles!");
		}
		publishEvent(getCommand(), getTarget(), getValue(), null);
	}

}
