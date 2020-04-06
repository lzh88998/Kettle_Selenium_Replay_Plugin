package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pentaho.di.core.exception.KettleException;

import org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands.BaseCommand;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands.Factory;


public class SeleniumTestCase {
	
	// used for selenium run
	private SeleniumTestSuite suite = null;
	private WebDriver driver = null;
	private Map<String, Object> vars = null;
//	private JavascriptExecutor js = null;
	
	// from suite
	private String url = null;
	
	// self variables
	private String id = null;
	private String name = null;
//	private ArrayList<SeleniumCommand> commands = null;
	private ArrayList<BaseCommand> commands = null;
	
	public SeleniumTestCase(SeleniumTestSuite suite, String url, JSONObject json) throws KettleException {
		this.suite = suite;
		this.url = url;
		
		id = (String)json.get("id");
		name = (String)json.get("name");
		// commands = new ArrayList<SeleniumCommand>();
		commands = new ArrayList<BaseCommand>();
		
		JSONArray testCases = (JSONArray)json.get("commands");
		
//		Stack<BaseCommand> stack = new Stack<BaseCommand>();
//		BaseCommand parent = null;
		
		for(int i = 0; i < testCases.size(); i++) {
			JSONObject jsonCommand = (JSONObject)testCases.get(i);
//			SeleniumCommand command = new SeleniumCommand(this, jsonCommand);
			BaseCommand command = Factory.Create(jsonCommand, this);
			commands.add(command);

			// control flow process, use child commands
//			  do: emitControlFlowDo,
//			  while: emitControlFlowWhile,
//			  forEach: emitControlFlowForEach,
//			  if: emitControlFlowIf,
//			  else: emitControlFlowElse,
//			  elseIf: emitControlFlowElseIf,
//			  end: emitControlFlowEnd,
//			  repeatIf: emitControlFlowRepeatIf,
//			  times: emitControlFlowTimes,
//			  run: emitRun,
/*			switch(command.getCommand()) {
			case "do":
				break;
			case "while":
				break;
			case "forEach":
				break;
			case "if":
				break;
			case "else":
				break;
			case "elseIf":
				break;
			case "end":
				break;
			case "repeatIf":
				break;
			case "times":
				break;
			case "run":
				break;
			default:
				break;
			}*/
		}
	}
	
	public void setUp() {
		driver = new ChromeDriver();
//		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}
	
	public void tearDown() {
		driver.quit();
	}
	
	public void run() throws KettleException {
		for(int i = 0; i < commands.size(); i++) {
			// SeleniumCommand command = commands.get(i);
			BaseCommand command = commands.get(i);
			command.run();
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
/*	public SeleniumCommand[] getCommands() {
		return (SeleniumCommand[])commands.toArray();
	}*/
	
	public BaseCommand[] getCommands() {
		ArrayList<BaseCommand> ret = new ArrayList<BaseCommand>();
		
		for(int i = 0; i < commands.size(); i ++) {
			BaseCommand[] cmds = commands.get(i).getCommands();
			for(int j = 0; j < cmds.length; j++) {
				ret.add(cmds[j]);
			}
		}
		
		if(ret.size() > 0)
			return ret.toArray(new BaseCommand[0]);
		else
			return null;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void setVars(String name, Object value) {
		vars.put(name, value);
	}
	
	public Object getVars(String name) {
		if(name.startsWith("${"))
			name = name.substring(2, name.length()-1);
		return vars.get(name);
	}
	
	public String getReplaceValue(String key) {
		return suite.getReplaceValue(key);
	}

	public void publishEvent(String command, String target, String value, String text) {
		suite.publishEvent(command, target, value, text);
	}
}
