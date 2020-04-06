package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands.BaseCommand;

public class SeleniumTestSuite {
	
	private String id = null;
	private String version = null;
	private String name = null;
	private String url = null;
	private ArrayList<SeleniumTestCase> tests = null;
	private HashMap<String, String> replaceValues = null;
	private SeleniumReplayStep step = null;
	private SeleniumReplayStepMeta meta = null;

	public SeleniumTestSuite(SeleniumReplayStep step, SeleniumReplayStepMeta meta, JSONObject json) throws KettleException {
		this.id = (String)json.get("id");
		this.version = (String)json.get("version");
		this.name = (String)json.get("name");
		this.url = (String)json.get("url");
		this.tests = new ArrayList<SeleniumTestCase>();
		this.step = step;
		this.meta = meta;
		
		JSONArray testCases = (JSONArray)json.get("tests");
		
		for(int i = 0; i < testCases.size(); i++) {
			JSONObject jsonTest = (JSONObject)testCases.get(i);
			SeleniumTestCase testCase = new SeleniumTestCase(this, url, jsonTest);
			tests.add(testCase);
		}
		
		replaceValues = new HashMap<String, String>();
	}

	public String getId(){
		return id;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public SeleniumTestCase[] getTestCases(){
		return (SeleniumTestCase[])tests.toArray();
	}
	
	public void initialSetUp() {
	}
	
	public void finalTearDown() {
	}
	
	public String getReplaceValueField(String key) {
		return meta.getReplaceValueField(key);
	}
	
	public void run() throws MalformedURLException, KettleException {
		
		initialSetUp();
		
		for(int i = 0; i < tests.size(); i++) {
			SeleniumTestCase testCase = tests.get(i);
			testCase.setUp();
			testCase.run();
			testCase.tearDown();
		}
		
		finalTearDown();
	}
	
	public void publishEvent(String command, String target, String value, String text) {
		step.publishEvent(command, target, value, text);
	}
	
	public BaseCommand[] getCommands() {
		ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();

		for(int i = 0; i < tests.size(); i++) {
			BaseCommand[] cmds = tests.get(i).getCommands();
			for(int j = 0; j < cmds.length; j++)
			commands.add(cmds[j]);
		}

		if(commands.size() > 0)
			return commands.toArray(new BaseCommand[0]);
		else
			return null;
	}
	
	public void clearReplaceValues() {
		replaceValues.clear();
	}
	
	public void setReplaceValue(String id, String value) {
		replaceValues.put(id, value);
	}
	
	public String getReplaceValue(String id) {
		if(replaceValues.containsKey(id))
			return replaceValues.get(id);
		else
			return null;
	}
}
