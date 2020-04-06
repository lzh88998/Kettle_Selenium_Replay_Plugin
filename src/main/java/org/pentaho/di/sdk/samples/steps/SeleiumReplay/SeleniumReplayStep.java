package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SeleniumReplayStep extends BaseStep implements StepInterface  {
	  private static final Class<?> PKG = SeleniumReplayStepMeta.class; // for i18n purposes
	  
	  private SeleniumTestSuite suite = null;
	  private RowMetaInterface metaInfo = null;
	  
	  public SeleniumReplayStep( StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis ) {
	    super( s, stepDataInterface, c, t, dis );
	  }
	  
	  public boolean init( StepMetaInterface smi, StepDataInterface sdi ) {
	    // Casting to step-specific implementation classes is safe
		  SeleniumReplayStepMeta meta = (SeleniumReplayStepMeta) smi;
		  SeleniumReplayStepData data = (SeleniumReplayStepData) sdi;
	    if ( !super.init( meta, data ) ) {
	      return false;
	    }
	    
	    // load .side file
	    JSONParser parser = new JSONParser();
	    try {
	    	Object object = parser.parse(new FileReader(meta.getFilePath()));
	    	JSONObject jsonObject = (JSONObject)object;
	    	suite = new SeleniumTestSuite(this, meta, jsonObject);
	    }
	    catch(FileNotFoundException fe) {
	    	fe.printStackTrace();
	    	return false;
	    } catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    
	    log.logDebug("SeleniumReplayStep: Selenium IDE file loaded!");

		metaInfo = new RowMeta();
		try {
			meta.getFields(metaInfo, getStepname(), null, null, this, repository, metaStore);
		} catch (KettleStepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	    // Add any step-specific initialization that may be needed here
	    suite.initialSetUp();
	    return true;
	  }
	  
	  public void dispose( StepMetaInterface smi, StepDataInterface sdi ) {

	    // Casting to step-specific implementation classes is safe
		  SeleniumReplayStepMeta meta = (SeleniumReplayStepMeta) smi;
		  SeleniumReplayStepData data = (SeleniumReplayStepData) sdi;

	    // Add any step-specific initialization that may be needed here

	    // Call superclass dispose()
		  suite.finalTearDown();
	    super.dispose( meta, data );
	  }

	  public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {

		    // safely cast the step settings (meta) and runtime info (data) to specific implementations 
		  SeleniumReplayStepMeta meta = (SeleniumReplayStepMeta) smi;
//		  SeleniumReplayStepData data = (SeleniumReplayStepData) sdi;
		  
		    // get incoming row, getRow() potentially blocks waiting for more rows, returns null if no more rows expected
		    Object[] r = getRow();

		    // if no more rows are expected, indicate step is finished and processRow() should not be called again
		    if ( r == null ) {
		      setOutputDone();
		      return false;
		    }

		    // the "first" flag is inherited from the base step implementation
		    // it is used to guard some processing tasks, like figuring out field indexes
		    // in the row structure that only need to be done once
		    if ( first ) {
		    	first = false;
		    }

		    String[] ids = meta.getReplaceIDs();
		    for(int i = 0; i < ids.length; i++) {
		    	String valueField = meta.getReplaceValueField(ids[i]);
		    	if(null != valueField) {
			    	int index = getInputRowMeta().indexOfValue(valueField);
			    	String value = getInputRowMeta().getString(r, index);
			    	suite.clearReplaceValues();
			    	suite.setReplaceValue(ids[i], value);
		    	}
		    }
		    
		    try {
				suite.run();
			} catch (MalformedURLException | KettleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new KettleException(e.getMessage());
			}

		    // log progress if it is time to to so
		    if ( checkFeedback( getLinesRead() ) ) {
		      logBasic( BaseMessages.getString( PKG, "SeleniumReplayStep.Linenr", getLinesRead() ) ); // Some basic logging
		    }

		  // get a row and run test case
		  // get another row and run again
		    return true;
		  }
	  
	  public void publishEvent(String command, String target, String value, String text) {
		  Object[] r = RowDataUtil.allocateRowData(metaInfo.getFieldNames().length);
		  r[0] = command;
		  r[1] = target;
		  r[2] = value;
		  r[3] = text;
		  try {
			putRow(metaInfo, r);
		} catch (KettleStepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
