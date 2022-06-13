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
public abstract class AbstractMarketProjectReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketProjectReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsYE;
    protected com.kingdee.bos.ctrl.swing.KDLabel type;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbContract;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbWithoutContract;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbJZ;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromConBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToConBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbIsYE;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromConBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToConBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToBizDate;
    /**
     * output class constructor
     */
    public AbstractMarketProjectReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketProjectReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsYE = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.type = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cbContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbWithoutContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbJZ = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contFromConBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToConBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbIsYE = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkFromConBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToConBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkFromBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contIsYE.setName("contIsYE");
        this.type.setName("type");
        this.cbContract.setName("cbContract");
        this.cbWithoutContract.setName("cbWithoutContract");
        this.cbJZ.setName("cbJZ");
        this.contFromConBizDate.setName("contFromConBizDate");
        this.contToConBizDate.setName("contToConBizDate");
        this.contFromBizDate.setName("contFromBizDate");
        this.contToBizDate.setName("contToBizDate");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.cbIsYE.setName("cbIsYE");
        this.pkFromConBizDate.setName("pkFromConBizDate");
        this.pkToConBizDate.setName("pkToConBizDate");
        this.pkFromBizDate.setName("pkFromBizDate");
        this.pkToBizDate.setName("pkToBizDate");
        // CustomerQueryPanel
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contIsYE		
        this.contIsYE.setBoundLabelText(resHelper.getString("contIsYE.boundLabelText"));		
        this.contIsYE.setBoundLabelLength(100);		
        this.contIsYE.setBoundLabelUnderline(true);
        // type		
        this.type.setText(resHelper.getString("type.text"));
        // cbContract		
        this.cbContract.setText(resHelper.getString("cbContract.text"));
        // cbWithoutContract		
        this.cbWithoutContract.setText(resHelper.getString("cbWithoutContract.text"));
        // cbJZ		
        this.cbJZ.setText(resHelper.getString("cbJZ.text"));
        // contFromConBizDate		
        this.contFromConBizDate.setBoundLabelText(resHelper.getString("contFromConBizDate.boundLabelText"));		
        this.contFromConBizDate.setBoundLabelUnderline(true);		
        this.contFromConBizDate.setBoundLabelLength(100);
        // contToConBizDate		
        this.contToConBizDate.setBoundLabelText(resHelper.getString("contToConBizDate.boundLabelText"));		
        this.contToConBizDate.setBoundLabelLength(100);		
        this.contToConBizDate.setBoundLabelUnderline(true);
        // contFromBizDate		
        this.contFromBizDate.setBoundLabelText(resHelper.getString("contFromBizDate.boundLabelText"));		
        this.contFromBizDate.setBoundLabelLength(100);		
        this.contFromBizDate.setBoundLabelUnderline(true);
        // contToBizDate		
        this.contToBizDate.setBoundLabelText(resHelper.getString("contToBizDate.boundLabelText"));		
        this.contToBizDate.setBoundLabelLength(100);		
        this.contToBizDate.setBoundLabelUnderline(true);
        // pkFromDate
        // pkToDate
        // cbIsYE		
        this.cbIsYE.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.YesOrNoEnum").toArray());
        // pkFromConBizDate
        // pkToConBizDate
        // pkFromBizDate
        // pkToBizDate
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
        this.setBounds(new Rectangle(10, 10, 600, 160));
        this.setLayout(null);
        contFromDate.setBounds(new Rectangle(25, 67, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(322, 67, 270, 19));
        this.add(contToDate, null);
        contIsYE.setBounds(new Rectangle(25, 123, 270, 19));
        this.add(contIsYE, null);
        type.setBounds(new Rectangle(25, 11, 100, 19));
        this.add(type, null);
        cbContract.setBounds(new Rectangle(126, 11, 99, 19));
        this.add(cbContract, null);
        cbWithoutContract.setBounds(new Rectangle(276, 11, 99, 19));
        this.add(cbWithoutContract, null);
        cbJZ.setBounds(new Rectangle(427, 11, 99, 19));
        this.add(cbJZ, null);
        contFromConBizDate.setBounds(new Rectangle(25, 95, 270, 19));
        this.add(contFromConBizDate, null);
        contToConBizDate.setBounds(new Rectangle(322, 95, 270, 19));
        this.add(contToConBizDate, null);
        contFromBizDate.setBounds(new Rectangle(25, 39, 270, 19));
        this.add(contFromBizDate, null);
        contToBizDate.setBounds(new Rectangle(322, 39, 270, 19));
        this.add(contToBizDate, null);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contIsYE
        contIsYE.setBoundEditor(cbIsYE);
        //contFromConBizDate
        contFromConBizDate.setBoundEditor(pkFromConBizDate);
        //contToConBizDate
        contToConBizDate.setBoundEditor(pkToConBizDate);
        //contFromBizDate
        contFromBizDate.setBoundEditor(pkFromBizDate);
        //contToBizDate
        contToBizDate.setBoundEditor(pkToBizDate);

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
	    return "com.kingdee.eas.fdc.contract.app.MarketProjectReportFilterUIHandler";
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
	 * ????????§µ??
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "MarketProjectReportFilterUI");
    }




}