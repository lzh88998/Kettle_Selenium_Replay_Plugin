package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

@Step(
		  id = "SeleniumReplayStep",
		  name = "SeleniumReplayStep.Name",
		  description = "SeleniumReplayStep.TooltipDesc",
		  image = "org/pentaho/di/sdk/samples/steps/demo/resources/demo.svg",
		  categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
		  i18nPackageName = "org.pentaho.di.sdk.samples.steps.SeleniumReplay",
		  documentationUrl = "SeleniumReplaytep.DocumentationURL",
		  casesUrl = "SeleniumReplayStep.CasesURL",
		  forumUrl = "SeleniumReplayStep.ForumURL"
		  )
public class SeleniumReplayStepMeta extends BaseStepMeta implements StepMetaInterface  {
	  private static final Class<?> PKG = SeleniumReplayStepMeta.class; // for i18n purposes

	  private String filePath = "";
	  private HashMap<String, String> replaceValues = new HashMap<String, String>();
	  
	  public SeleniumReplayStepMeta() {
	    super();
	  }

	  public StepDialogInterface getDialog( Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
	    return new SeleniumReplayStepDialog( shell, meta, transMeta, name );
	  }

	  public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
	      Trans disp ) {
	    return new SeleniumReplayStep( stepMeta, stepDataInterface, cnr, transMeta, disp );
	  }

	  public StepDataInterface getStepData() {
	    return new SeleniumReplayStepData();
	  }

	  public void setDefault() {
		  setFilePath( "" );
	  }

	  public String getFilePath() {
	    return filePath;
	  }

	  public void setFilePath( String outputField ) {
	    this.filePath = outputField;
	  }

	  public Object clone() {
	    Object retval = super.clone();
	    return retval;
	  }
	  
	  public String[] getReplaceIDs() {
		  return replaceValues.keySet().toArray(new String[0]);
	  }
	  
	  public String getReplaceValueField(String id) {
		  if(replaceValues.containsKey(id))
			  return replaceValues.get(id);
		  else
			  return null;
	  }
	  
	  public void clearReplaceValueFields() {
		  replaceValues.clear();
	  }
	  
	  public void setReplaceValue(String key, String value) {
		  replaceValues.put(key, value);
	  }

	  public String getXML() throws KettleValueException {
	    StringBuilder xml = new StringBuilder();

	    // only one field to serialize
	    xml.append( XMLHandler.addTagValue( "filePath", filePath ) );
        Iterator<Map.Entry<String, String>> it = replaceValues.entrySet().iterator();
        xml.append( "      <replaceValue>" ).append( Const.CR );
        while (it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            xml.append( "      <item>" ).append( Const.CR );
            xml.append( "        " ).append( XMLHandler.addTagValue( "key", entry.getKey() ) );
            xml.append( "        " ).append( XMLHandler.addTagValue( "value", entry.getValue() ) );
            xml.append( "      </item>" ).append( Const.CR );
        }
        xml.append( "      </replaceValue>" ).append( Const.CR );

	    return xml.toString();
	  }

	  public void loadXML( Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
	    try {
	    	setFilePath( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, "filePath" ) ) );
	        Node lookup = XMLHandler.getSubNode( stepnode, "replaceValue" );
	        int nrkeys = XMLHandler.countNodes( lookup, "item" );
	        replaceValues.clear();
	        for ( int i = 0; i < nrkeys; i++ ) {
	            Node item = XMLHandler.getSubNodeByNr( lookup, "item", i );
	            replaceValues.put(XMLHandler.getTagValue( item, "key" ), XMLHandler.getTagValue( item, "value" ));
	          }
	    } catch ( Exception e ) {
	      throw new KettleXMLException( "Selenium Replay plugin unable to read step info from XML node", e );
	    }
	  }

	  @Override
	  public void saveRep( Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
	      throws KettleException {
		  System.out.println("Saving to repo");
	    try {
			  System.out.println("Saving to repo1");
	      rep.saveStepAttribute( id_transformation, id_step, "filePath", filePath ); //$NON-NLS-1$
		  System.out.println("Saving to repo2");
	        Iterator<Map.Entry<String, String>> it = replaceValues.entrySet().iterator();
			  System.out.println("Saving to repo3");
	        int i = 0;
	        while (it.hasNext()){
	            Map.Entry<String, String> entry = it.next();
	            rep.saveStepAttribute( id_transformation, id_step, i, "replaceValue_key", entry.getKey() );
	            rep.saveStepAttribute( id_transformation, id_step, i, "replaceValue_value", entry.getValue() );
	            i++;
	        }
			  System.out.println("Saving to repo4");
	    } catch ( Exception e ) {
	      throw new KettleException( "Unable to save step into repository: " + id_step, e );
	    }
	  }

	  @Override
	  public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
	      throws KettleException {
	    try {
	    	filePath  = rep.getStepAttributeString( id_step, "outputfield" ); //$NON-NLS-1$
	        int nrkeys = rep.countNrStepAttributes( id_step, "replaceValue_key" );
	        replaceValues.clear();
	        for ( int i = 0; i < nrkeys; i++ ) {
	        	replaceValues.put(rep.getStepAttributeString( id_step, i, "replaceValue_key" ),
	        			rep.getStepAttributeString( id_step, i, "replaceValue_value" ));
	          }
	    } catch ( Exception e ) {
	      throw new KettleException( "Unable to load step from repository", e );
	    }
	  }

	  public void getFields( RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep,
	      VariableSpace space, Repository repository, IMetaStore metaStore ) throws KettleStepException {
		inputRowMeta.clear();

	    ValueMetaInterface v = new ValueMetaString( "Command" );
	    v.setOrigin( name );

	    inputRowMeta.addValueMeta( v );

	    ValueMetaInterface v2 = new ValueMetaString( "Target" );
	    v.setOrigin( name );

	    inputRowMeta.addValueMeta( v2 );
	  
	    ValueMetaInterface v3 = new ValueMetaString( "Value" );
	    v.setOrigin( name );

	    inputRowMeta.addValueMeta( v3 );
	  
	    ValueMetaInterface v4 = new ValueMetaString( "Text" );
	    v.setOrigin( name );

	    inputRowMeta.addValueMeta( v4 );
	  }

	  public void check( List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev,
	      String[] input, String[] output, RowMetaInterface info, VariableSpace space, Repository repository,
	      IMetaStore metaStore ) {
	    CheckResult cr;

	    // See if there are input streams leading to this step!
	    if ( filePath != null && filePath != "" ) {
	      cr = new CheckResult( CheckResult.TYPE_RESULT_OK,
	        BaseMessages.getString( PKG, "SeleniumReplay.CheckResult.ReceivingRows.OK" ), stepMeta );
	      remarks.add( cr );
	    } else {
	      cr = new CheckResult( CheckResult.TYPE_RESULT_ERROR,
	        BaseMessages.getString( PKG, "SeleniumReplay.CheckResult.ReceivingRows.ERROR" ), stepMeta );
	      remarks.add( cr );
	    }
	  }
}
