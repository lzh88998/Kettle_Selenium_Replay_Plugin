package org.pentaho.di.sdk.samples.steps.SeleiumReplay;

import java.io.FileReader;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.sdk.samples.steps.SeleiumReplay.Commands.BaseCommand;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.dialog.ErrorDialog;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class SeleniumReplayStepDialog extends BaseStepDialog implements StepDialogInterface {

	  private TextVar wFilename = null;
	  private TableView wKey;

	  private static Class<?> PKG = SeleniumReplayStepMeta.class; // for i18n purposes  

	  private SeleniumReplayStepMeta meta;


	  public SeleniumReplayStepDialog( Shell parent, Object in, TransMeta transMeta, String sname ) {
	    super( parent, (BaseStepMeta) in, transMeta, sname );
	    meta = (SeleniumReplayStepMeta) in;
	  }

	  public String open() {
	    // store some convenient SWT variables  
	    Shell parent = getParent();
	    Display display = parent.getDisplay();

	    // SWT code for preparing the dialog
	    shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX );
	    props.setLook( shell );
	    setShellImage( shell, meta );

	    // Save the value of the changed flag on the meta object. If the user cancels
	    // the dialog, it will be restored to this saved value.
	    // The "changed" variable is inherited from BaseStepDialog
	    changed = meta.hasChanged();

	    // The ModifyListener used on all controls. It will update the meta object to  
	    // indicate that changes are being made.
	    ModifyListener lsMod = new ModifyListener() {
	      public void modifyText( ModifyEvent e ) {
	        meta.setChanged();
	      }
	    };

	    FormLayout formLayout = new FormLayout();
	    formLayout.marginWidth = Const.FORM_MARGIN;
	    formLayout.marginHeight = Const.FORM_MARGIN;
	    shell.setLayout( formLayout );
	    shell.setText( BaseMessages.getString( PKG, "SeleniumReplay.Shell.Title" ) );
	    int middle = props.getMiddlePct();
	    int margin = Const.MARGIN;

	    // Stepname line
	    wlStepname = new Label( shell, SWT.RIGHT );
	    wlStepname.setText( BaseMessages.getString( PKG, "System.Label.StepName" ) );
	    props.setLook( wlStepname );
	    fdlStepname = new FormData();
	    fdlStepname.left = new FormAttachment( 0, 0 );
	    fdlStepname.right = new FormAttachment( middle, -margin );
	    fdlStepname.top = new FormAttachment( 0, margin );
	    wlStepname.setLayoutData( fdlStepname );

	    wStepname = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
	    wStepname.setText( stepname );
	    props.setLook( wStepname );
	    wStepname.addModifyListener( lsMod );
	    fdStepname = new FormData();
	    fdStepname.left = new FormAttachment( middle, 0 );
	    fdStepname.top = new FormAttachment( 0, margin );
	    fdStepname.right = new FormAttachment( 100, 0 );
	    wStepname.setLayoutData( fdStepname );

	    Control lastControl = wStepname;
	    
	    Button wbbFilename = new Button( shell, SWT.PUSH | SWT.CENTER );
	    props.setLook( wbbFilename );
	    wbbFilename.setText( BaseMessages.getString( PKG, "System.Button.Browse" ) );
	    wbbFilename.setToolTipText( BaseMessages.getString( PKG, "System.Tooltip.BrowseForFileOrDirAndAdd" ) );
	    FormData fdbFilename = new FormData();
	    fdbFilename.top = new FormAttachment( lastControl, margin );
	    fdbFilename.right = new FormAttachment( 100, 0 );
	    wbbFilename.setLayoutData( fdbFilename );

	    // The field itself...
	    //
	    Label wlFilename = new Label( shell, SWT.RIGHT );
	    wlFilename.setText( BaseMessages.getString( PKG, "SeleniumReplay.Shell.Filename" ) );
	    props.setLook( wlFilename );
	    FormData fdlFilename = new FormData();
	    fdlFilename.top = new FormAttachment( lastControl, margin );
	    fdlFilename.left = new FormAttachment( 0, 0 );
	    fdlFilename.right = new FormAttachment( middle, -margin );
	    wlFilename.setLayoutData( fdlFilename );
	    wFilename = new TextVar( transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
	    props.setLook( wFilename );
	    wFilename.addModifyListener( lsMod );
	    FormData fdFilename = new FormData();
	    fdFilename.top = new FormAttachment( lastControl, margin );
	    fdFilename.left = new FormAttachment( middle, 0 );
	    fdFilename.right = new FormAttachment( wbbFilename, -margin );
	    wFilename.setLayoutData( fdFilename );

	    wbbFilename.addSelectionListener( new SelectionAdapter() {
	        public void widgetSelected( SelectionEvent e ) {
	          FileDialog dialog = new FileDialog( shell, SWT.OPEN );
	          dialog.setFilterExtensions( new String[] { "*.side", "*" } );
	          if ( wFilename.getText() != null ) {
	            String fname = transMeta.environmentSubstitute( wFilename.getText() );
	            dialog.setFileName( fname );
	          }

	          dialog.setFilterNames( new String[] {
	            BaseMessages.getString( PKG, "SeleniumReplay.FileType.SideFIles" ),
	            BaseMessages.getString( PKG, "System.FileType.AllFiles" ) } );

	          if ( dialog.open() != null ) {
	            String str = dialog.getFilterPath() + System.getProperty( "file.separator" ) + dialog.getFileName();
	            updateTable(str);
	            wFilename.setText( str );
	          }
	        }
	      } 
	    );

	    lastControl = wFilename;

	    Label wlKey = new Label( shell, SWT.NONE );
	    wlKey.setText( BaseMessages.getString( PKG, "SeleniumReplayStepDialog.Keys.Label" ) );
	    props.setLook( wlKey );
	    FormData fdlKey = new FormData();
	    fdlKey.left = new FormAttachment( 0, 0 );
	    fdlKey.top = new FormAttachment( lastControl, margin );
	    wlKey.setLayoutData( fdlKey );

	    // table column number
	    int nrKeyCols = 5;
	    // need to change to the count of commands in .side file
	    int nrKeyRows = 1;

	    // column info
	    ColumnInfo[] ciKey = new ColumnInfo[nrKeyCols];
	    // declare column information including type and title
	    // last paramter show whether it is read only
	    ciKey[0] =
	      new ColumnInfo(
	        BaseMessages.getString( PKG, "SeleniumReplayStepDialog.ColumnInfo.ID" ),
	        ColumnInfo.COLUMN_TYPE_TEXT, new String[] { "" }, true );
	    ciKey[1] =
	      new ColumnInfo(
	        BaseMessages.getString( PKG, "SeleniumReplayStepDialog.ColumnInfo.Command" ),
	        ColumnInfo.COLUMN_TYPE_TEXT, new String[] { "" }, true );
	    ciKey[2] =
	      new ColumnInfo(
	        BaseMessages.getString( PKG, "SeleniumReplayStepDialog.ColumnInfo.Target" ),
	        ColumnInfo.COLUMN_TYPE_TEXT, new String[] { "" }, true );
	    ciKey[3] =
	      new ColumnInfo(
	        BaseMessages.getString( PKG, "SeleniumReplayStepDialog.ColumnInfo.Value" ),
	        ColumnInfo.COLUMN_TYPE_TEXT, new String[] { "" }, true );
	    ciKey[4] =
	  	      new ColumnInfo(
	  	        BaseMessages.getString( PKG, "SeleniumReplayStepDialog.ColumnInfo.ReplaceField" ),
	  	        ColumnInfo.COLUMN_TYPE_CCOMBO, new String[] { "" }, false ); // replace the new String[] to values from fields from step

	    // add items to table 
	    try {
			RowMetaInterface r = transMeta.getPrevStepFields( stepname );
	        String[] prevStepFieldNames = r.getFieldNames();
	        Arrays.sort( prevStepFieldNames );
	        ciKey[4].setComboValues(prevStepFieldNames);
		} catch (KettleStepException ke) {
			// TODO Auto-generated catch block
			ke.printStackTrace();
		      new ErrorDialog(
		    	        shell, BaseMessages.getString( PKG, "DatabaseLookupDialog.GetFieldsFailed.DialogTitle" ), BaseMessages
		    	          .getString( PKG, "DatabaseLookupDialog.GetFieldsFailed.DialogMessage" ), ke );
		}

	    //  with no relation about showing items, only used when trying to update items
	    wKey =
	      new TableView(
	        transMeta, shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL, ciKey,
	        nrKeyRows, lsMod, props );

	    FormData fdKey = new FormData();
	    fdKey.left = new FormAttachment( 0, 0 );
	    fdKey.top = new FormAttachment( wlKey, margin );
	    fdKey.right = new FormAttachment( 100, 0 );
	    fdKey.bottom = new FormAttachment( wlKey, 190 );
	    wKey.setLayoutData( fdKey );

	    lastControl = wKey;

	    // need to add select items according to model loaded
	    
	    // OK and cancel buttons
	    wOK = new Button( shell, SWT.PUSH );
	    wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
	    wCancel = new Button( shell, SWT.PUSH );
	    wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
	    setButtonPositions( new Button[] { wOK, wCancel }, margin, lastControl );

	    // Add listeners for cancel and OK
	    lsCancel = new Listener() {
	      public void handleEvent( Event e ) {
	        cancel();
	      }
	    };
	    lsOK = new Listener() {
	      public void handleEvent( Event e ) {
	        ok();
	      }
	    };
	    wCancel.addListener( SWT.Selection, lsCancel );
	    wOK.addListener( SWT.Selection, lsOK );

	    // default listener (for hitting "enter")
	    lsDef = new SelectionAdapter() {
	      public void widgetDefaultSelected( SelectionEvent e ) {
	        ok();
	      }
	    };
	    wStepname.addSelectionListener( lsDef );

	    // Detect X or ALT-F4 or something that kills this window and cancel the dialog properly
	    shell.addShellListener( new ShellAdapter() {
	      public void shellClosed( ShellEvent e ) {
	        cancel();
	      }
	    } );

	    // Set/Restore the dialog size based on last position on screen
	    // The setSize() method is inherited from BaseStepDialog
	    setSize();

	    wFilename.setText( meta.getFilePath() == null ? "" : meta.getFilePath());
	    updateTable();
	    
	    // populate the dialog with the values from the meta object
	    populateDialog();

	    // restore the changed flag to original value, as the modify listeners fire during dialog population  
	    meta.setChanged( changed );

	    // open dialog and enter event loop  
	    shell.open();
	    while ( !shell.isDisposed() ) {
	      if ( !display.readAndDispatch() ) {
	        display.sleep();
	      }
	    }

	    // at this point the dialog has closed, so either ok() or cancel() have been executed
	    // The "stepname" variable is inherited from BaseStepDialog
	    return stepname;
	  }

	  /**
	   * This helper method puts the step configuration stored in the meta object
	   * and puts it into the dialog controls.
	   */
	  private void populateDialog() {
	    wStepname.selectAll();
	  }

	  /**
	   * Called when the user cancels the dialog.  
	   */
	  private void cancel() {
	    stepname = null;
	    meta.setChanged( changed );
	    dispose();
	  }

	  /**
	   * Called when the user confirms the dialog
	   */
	  private void ok() {
	    stepname = wStepname.getText();
	    meta.setFilePath(wFilename.getText());
	    meta.clearReplaceValueFields();
	    Table table = wKey.getTable();
	    for ( int i = 0; i < table.getItemCount(); i++ ) {
	        TableItem item = wKey.getTable().getItem(i);
	        String id = item.getText(1);
	        String newValue = item.getText(5);
	        if(null != id && "" != id && null != newValue && "" != newValue) {
	        	meta.setReplaceValue(id, newValue);
	        }
	      }

	    // need to load data from tableview and store it
	    dispose();
	  }
	  
	  private void updateTable() {
		  updateTable(wFilename.getText());
	  }
	  
	  private void updateTable(String fileName) {
		if(null != fileName && "" != fileName) {
			System.out.println(wFilename.getText());
			SeleniumTestSuite suite = null;
			JSONParser parser = new JSONParser();
			try {
				Object object = parser.parse(new FileReader(fileName));
				JSONObject jsonObject = (JSONObject)object;
				suite = new SeleniumTestSuite(null, null, jsonObject);
			}
			catch(Exception e) {
				e.printStackTrace();
			      new ErrorDialog(
			    	        shell, BaseMessages.getString( PKG, "SeleniumReplayStepDialog.GetCommands.DialogTitle" ), BaseMessages
			    	          .getString( PKG, "SeleniumReplayStepDialog.GetCommands.DialogMessage" ), e );
				return;
			}
			
			wKey.table.removeAll();
			
			BaseCommand[] cmds = suite.getCommands();
			System.out.println(cmds.length);
		    for(int i = 0; i < cmds.length; i++) {
		        TableItem tableItem = new TableItem( wKey.table, SWT.NONE );
		        tableItem.setText(1, Const.NVL(cmds[i].getID(), ""));
		        tableItem.setText(2, Const.NVL(cmds[i].getCommand(), ""));
		        tableItem.setText(3, Const.NVL(cmds[i].getTarget(), ""));
		        tableItem.setText(4, Const.NVL(cmds[i].getValue(), ""));
		        if(meta.getReplaceValueField(cmds[i].getID()) != null)
		        	tableItem.setText(5, Const.NVL( meta.getReplaceValueField(cmds[i].getID()), "" ) );
		        else
		        	tableItem.setText(5, "");
		    }
		}
	  }
}
