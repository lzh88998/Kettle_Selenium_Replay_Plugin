package org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.SeleniumTestCase;

public class Factory {
	public static BaseCommand Create(JSONObject json, SeleniumTestCase parent) throws KettleException {
		BaseCommand ret = null;
		String command = (String)json.get("command");

//		  answerOnNextPrompt: skip,
//		  chooseCancelOnNextConfirmation: skip,
//		  chooseCancelOnNextPrompt: skip,
//		  chooseOkOnNextConfirmation: skip,
//		  debugger: skip,
		
//		  executeScript: skip,
//		  executeAsyncScript: skip,
//		  runScript: emitRunScript,
		
//		  do: emitControlFlowDo,
//		  while: emitControlFlowWhile,
//		  forEach: emitControlFlowForEach,
//		  if: emitControlFlowIf,
//		  else: emitControlFlowElse,
//		  elseIf: emitControlFlowElseIf,
//		  end: emitControlFlowEnd,
//		  repeatIf: emitControlFlowRepeatIf,
//		  times: emitControlFlowTimes,
//		  run: emitRun,
		
		switch(command)
		{
//			  addSelection: emitSelect,
//			  removeSelection: emitSelect,
//			  select: emitSelect,
		case "addSelection":
		case "removeSelection":
		case "select":
			ret = new AddSelection(parent, json);
			break;
//			  assert: emitAssert,
//			  verify: emitAssert,
		case "assert":
		case "verify":
			ret = new Assert(parent, json);
			break;
//			  assertAlert: emitAssertAlert,
//			  assertConfirmation: emitAssertAlert,
//			  assertPrompt: emitAssertAlert,
		case "assertAlert":
		case "assertConfirmation":
		case "assertPrompt":
			ret = new AssertAlert(parent, json);
			break;
//			  assertChecked: emitVerifyChecked,
//			  verifyChecked: emitVerifyChecked,
		case "assertChecked":
		case "verifyChecked":
			ret = new VerifyChecked(parent, json);
			break;
//			  assertNotChecked: emitVerifyNotChecked,
//			  verifyNotChecked: emitVerifyNotChecked,
		case "assertNotChecked":
		case "verifyNotChecked":
			ret = new VerifyNotChecked(parent, json);
			break;
//			  assertEditable: emitVerifyEditable,
//			  verifyEditable: emitVerifyEditable,
		case "assertEditable":
		case "verifyEditable":
			ret = new VerifyEditable(parent, json);
			break;
//			  assertNotEditable: emitVerifyNotEditable,
//			  verifyNotEditable: emitVerifyNotEditable,
		case "assertNotEditable":
		case "verifyNotEditable":
			ret = new VerifyNotEditable(parent, json);
			break;
//			  assertElementPresent: emitVerifyElementPresent,
//			  verifyElementPresent: emitVerifyElementPresent,
		case "assertElementPresent":
		case "verifyElementPresent":
			ret = new VerifyElementPresent(parent, json);
			break;
//			  assertElementNotPresent: emitVerifyElementNotPresent,
//			  verifyElementNotPresent: emitVerifyElementNotPresent,
		case "assertElementNotPresent":
		case "verifyElementNotPresent":
			ret = new VerifyElementNotPresent(parent, json);
			break;
//			  assertValue: emitVerifyValue,
//			  verifyValue: emitVerifyValue,
//			  assertSelectedValue: emitVerifySelectedValue,
//			  verifySelectedValue: emitVerifySelectedValue,
		case "assertValue":
		case "verifyValue":
		case "assertSelectedValue":
		case "verifySelectedValue":
			ret = new VerifyValue(parent, json);
			break;
//			  assertNotSelectedValue: emitVerifyNotSelectedValue,
//			  verifyNotSelectedValue: emitVerifyNotSelectedValue,
		case "assertNotSelectedValue":
		case "verifyNotSelectedValue":
			ret = new VerifyNotSelectedValue(parent, json);
			break;
//			  assertText: emitVerifyText,
//			  verifyText: emitVerifyText,
		case "assertText":
		case "verifyText":
			ret = new VerifyText(parent, json);
			break;
//			  assertNotText: emitVerifyNotText,
//			  verifyNotText: emitVerifyNotText,
		case "assertNotText":
		case "verifyNotText":
			ret = new VerifyNotText(parent, json);
			break;
//			  assertSelectedLabel: emitVerifySelectedLabel,
//			  verifySelectedLabel: emitVerifySelectedLabel,
		case "assertSelectedLabel":
		case "verifySelectedLabel":
			ret = new VerifySelectedLabel(parent, json);
			break;
//			  assertTitle: emitVerifyTitle,
//			  verifyTitle: emitVerifyTitle,
		case "assertTitle":
		case "verifyTitle":
			ret = new VerifyTitle(parent, json);
			break;
//			  check: emitCheck,
		case "check":
			ret = new Check(parent, json);
			break;
//			  click: emitClick,
//			  clickAt: emitClick,
		case "click":
		case "clickAt":
			ret = new Click(parent, json);
			break;
//			  close: emitClose,
		case "close":
			ret = new Close(parent, json);
			break;
//			  open: emitOpen,
		case "open":
			ret = new Open(parent, json);
			break;
//			  doubleClick: emitDoubleClick,
//			  doubleClickAt: emitDoubleClick,
		case "doubleClick":
		case "doubleClickAt":
			ret = new DoubleClick(parent, json);
			break;
//			  dragAndDropToObject: emitDragAndDrop,
		case "dragAndDropToObject":
			ret = new DragAndDrop(parent, json);
			break;
//			  echo: emitEcho,
		case "echo":
			ret = new Echo(parent, json);
			break;
//			  editContent: emitEditContent,
		case "editContent":
			ret = new EditContent(parent, json);
			break;
//			  mouseDown: emitMouseDown,
//			  mouseDownAt: emitMouseDown,
		case "mouseDown":
		case "mouseDownAt":
			ret = new MouseDown(parent, json);
			break;
//			  mouseMove: emitMouseMove,
//			  mouseMoveAt: emitMouseMove,
//			  mouseOver: emitMouseMove,
		case "mouseMove":
		case "mouseMoveAt":
		case "mouseOver":
			ret = new MouseMove(parent, json);
			break;
//			  mouseOut: emitMouseOut,
		case "mouseOut":
			ret = new MouseOut(parent, json);
			break;
//			  mouseUp: emitMouseUp,
//			  mouseUpAt: emitMouseUp,
		case "mouseUp":
		case "mouseUpAt":
			ret = new MouseUp(parent, json);
			break;
//			  pause: emitPause,
		case "pause":
			ret = new Pause(parent, json);
			break;
//			  selectFrame: emitSelectFrame,
		case "selectFrame":
			ret = new SelectFrame(parent, json);
			break;
//			  selectWindow: emitSelectWindow,
		case "selectWindow":
			ret = new SelectWindow(parent, json);
			break;
//			  setSpeed: emitSetSpeed,
		case "setSpeed":
			ret = new SetSpeed(parent, json);
			break;
//			  setWindowSize: emitSetWindowSize,
		case "setWindowSize":
			ret = new SetWindowSize(parent, json);
			break;
//			  store: emitStore,
		case "store":
			ret = new Store(parent, json);
			break;
//			  storeAttribute: emitStoreAttribute,
		case "storeAttribute":
			ret = new StoreAttribute(parent, json);
			break;
//			  storeText: emitStoreText,
		case "storeText":
			ret = new StoreText(parent, json);
			break;
//			  storeTitle: emitStoreTitle,
		case "storeTitle":
			ret = new StoreTitle(parent, json);
			break;
//			  storeValue: emitStoreValue,
		case "storeValue":
			ret = new StoreValue(parent, json);
			break;
//			  storeWindowHandle: emitStoreWindowHandle,
		case "storeWindowHandle":
			ret = new StoreWindowHandle(parent, json);
			break;
//			  storeXpathCount: emitStoreXpathCount,
		case "storeXpathCount":
			ret = new StoreXpathCount(parent, json);
			break;
//			  submit: emitSubmit,
		case "submit":
			ret = new Submit(parent, json);
			break;
//			  type: emitType,
//			  sendKeys: emitSendKeys,
		case "type":
		case "sendKeys":
			ret = new Type(parent, json);
			break;
//			  uncheck: emitUncheck,
		case "uncheck":
			ret = new Uncheck(parent, json);
			break;
//			  waitForElementEditable: emitWaitForElementEditable,
		case "waitForElementEditable":
			ret = new WaitForElementEditable(parent, json);
			break;
//			  waitForElementPresent: emitWaitForElementPresent,
		case "waitForElementPresent":
			ret = new WaitForElementPresent(parent, json);
			break;
//			  waitForElementVisible: emitWaitForElementVisible,
		case "waitForElementVisible":
			ret = new WaitForElementVisible(parent, json);
			break;
//			  waitForElementNotEditable: emitWaitForElementNotEditable,
		case "waitForElementNotEditable":
			ret = new WaitForElementNotEditable(parent, json);
			break;
//			  waitForElementNotPresent: emitWaitForElementNotPresent,
		case "waitForElementNotPresent":
			ret = new WaitForElementNotPresent(parent, json);
			break;
//			  waitForElementNotVisible: emitWaitForElementNotVisible,
		case "waitForElementNotVisible":
			ret = new WaitForElementNotVisible(parent, json);
			break;
//			  waitForText: emitWaitForText,
		case "waitForText":
			ret = new WaitForText(parent, json);
			break;
//			  webdriverAnswerOnVisiblePrompt: emitAnswerOnNextPrompt,
		case "webdriverAnswerOnVisiblePrompt":
			ret = new WebdriverAnswerOnVisiblePrompt(parent, json);
			break;
//			  webdriverChooseCancelOnVisibleConfirmation: emitChooseCancelOnNextConfirmation,
//			  webdriverChooseCancelOnVisiblePrompt: emitChooseCancelOnNextConfirmation,
		case "webdriverChooseCancelOnVisibleConfirmation":
		case "webdriverChooseCancelOnVisiblePrompt":
			ret = new WebdriverChooseCancelOnVisibleConfirmation(parent, json);
			break;
//			  webdriverChooseOkOnVisibleConfirmation: emitChooseOkOnNextConfirmation,
		case "webdriverChooseOkOnVisibleConfirmation":
			ret = new WebdriverChooseOkOnVisibleConfirmation(parent, json);
			break;
		default:
			throw new KettleException("Unknow command: "+command); 
		}
		
		return ret;
	}
}
