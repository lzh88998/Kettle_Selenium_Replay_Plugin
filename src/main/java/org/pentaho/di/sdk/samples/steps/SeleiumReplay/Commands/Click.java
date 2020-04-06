package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import java.util.Iterator;
import java.util.Set;

//import java.util.List;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Click extends BaseCommand {

	/*
	 *       "opensWindow": true,
     *       "windowHandleName": "win226",
     *       "windowTimeout": 2000
	 */
	
	private boolean opensWindow = false;
	private String windowHandleName=null;
	private int windowTimeout = 2000;
	
	public Click(SeleniumTestCase testCase, JSONObject json) {
		super(testCase, json);
		// TODO Auto-generated constructor stub
		if(null != json.get("opensWindow"))
			opensWindow = (boolean)json.get("opensWindow");
		
		if(null != json.get("windowHandleName"))
			windowHandleName = (String)json.get("windowHandleName");
		
		if(null != json.get("windowTimeout"))
			windowTimeout = ((Long)json.get("windowTimeout")).intValue();
	}

	@Override
	public void run() throws KettleException {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(testCase.getDriver(), 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(getTargetLocator(getTarget())));
		
		Set<String> openedWindows = null;
		
		if(opensWindow)
			openedWindows = testCase.getDriver().getWindowHandles();

		WebElement ele = getElement();
//		List<WebElement> eles = getElements();
//		for(int i = 0; i < eles.size(); i++) {
//			WebElement ele = eles.get(i);
			publishEvent(getCommand(), getTarget(), getValue(), ele.getText());
			ele.click();
//		}
			
		if(opensWindow) {
			try
			{
				Thread.sleep(windowTimeout);
			}
			catch(Exception e) {
				throw new KettleException(e);
			}
	
			Set<String> nowWindows = testCase.getDriver().getWindowHandles();
			nowWindows.removeAll(openedWindows);
			
			Iterator<String> iter = nowWindows.iterator();
			if(iter.hasNext()) {
				String handle = iter.next();
				System.out.println("Get new window name: "+windowHandleName+" handle: "+handle);
				testCase.setVars(windowHandleName, handle);
			}
			else
				throw new KettleException("Error: Expect new window with name: "+windowHandleName);
		}
	}

}
