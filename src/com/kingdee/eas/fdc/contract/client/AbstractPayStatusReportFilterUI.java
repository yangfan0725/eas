/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractPayStatusReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayStatusReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgItem;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSaved;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSubmitted;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAuditting;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAuditted;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isPayed;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBgItem;
    /**
     * output class constructor
     */
    public AbstractPayStatusReportFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPayStatusReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgItem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isSaved = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isSubmitted = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isAuditting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isAuditted = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isPayed = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtBgItem = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contBgItem.setName("contBgItem");
        this.isSaved.setName("isSaved");
        this.isSubmitted.setName("isSubmitted");
        this.isAuditting.setName("isAuditting");
        this.isAuditted.setName("isAuditted");
        this.isPayed.setName("isPayed");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.prmtBgItem.setName("prmtBgItem");
        // CustomerQueryPanel
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contBgItem		
        this.contBgItem.setBoundLabelText(resHelper.getString("contBgItem.boundLabelText"));		
        this.contBgItem.setBoundLabelLength(100);		
        this.contBgItem.setBoundLabelUnderline(true);
        // isSaved		
        this.isSaved.setText(resHelper.getString("isSaved.text"));
        // isSubmitted		
        this.isSubmitted.setText(resHelper.getString("isSubmitted.text"));
        // isAuditting		
        this.isAuditting.setText(resHelper.getString("isAuditting.text"));
        // isAuditted		
        this.isAuditted.setText(resHelper.getString("isAuditted.text"));
        // isPayed		
        this.isPayed.setText(resHelper.getString("isPayed.text"));
        // pkFromDate
        // pkToDate
        // prmtBgItem
        this.prmtBgItem.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBgItem_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 120));
        this.setLayout(null);
        contFromDate.setBounds(new Rectangle(18, 50, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(314, 50, 270, 19));
        this.add(contToDate, null);
        contBgItem.setBounds(new Rectangle(18, 20, 566, 19));
        this.add(contBgItem, null);
        isSaved.setBounds(new Rectangle(18, 88, 69, 19));
        this.add(isSaved, null);
        isSubmitted.setBounds(new Rectangle(66, 88, 69, 19));
        this.add(isSubmitted, null);
        isAuditting.setBounds(new Rectangle(116, 88, 69, 19));
        this.add(isAuditting, null);
        isAuditted.setBounds(new Rectangle(178, 88, 69, 19));
        this.add(isAuditted, null);
        isPayed.setBounds(new Rectangle(239, 88, 69, 19));
        this.add(isPayed, null);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contBgItem
        contBgItem.setBoundEditor(prmtBgItem);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.PayStatusReportFilterUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output prmtBgItem_dataChanged method
     */
    protected void prmtBgItem_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PayStatusReportFilterUI");
    }




}