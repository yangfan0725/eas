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
public abstract class AbstractMarketProjectFKReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketProjectFKReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spFromYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spFromMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spToYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spToMonth;
    /**
     * output class constructor
     */
    public AbstractMarketProjectFKReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketProjectFKReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contFromYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spFromYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spFromMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spToYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spToMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contFromYear.setName("contFromYear");
        this.contFromMonth.setName("contFromMonth");
        this.contToYear.setName("contToYear");
        this.contToMonth.setName("contToMonth");
        this.kDLabel1.setName("kDLabel1");
        this.spFromYear.setName("spFromYear");
        this.spFromMonth.setName("spFromMonth");
        this.spToYear.setName("spToYear");
        this.spToMonth.setName("spToMonth");
        // CustomerQueryPanel
        // contFromYear		
        this.contFromYear.setBoundLabelText(resHelper.getString("contFromYear.boundLabelText"));		
        this.contFromYear.setBoundLabelLength(50);		
        this.contFromYear.setBoundLabelUnderline(true);
        // contFromMonth		
        this.contFromMonth.setBoundLabelText(resHelper.getString("contFromMonth.boundLabelText"));		
        this.contFromMonth.setBoundLabelLength(50);		
        this.contFromMonth.setBoundLabelUnderline(true);
        // contToYear		
        this.contToYear.setBoundLabelText(resHelper.getString("contToYear.boundLabelText"));		
        this.contToYear.setBoundLabelLength(50);		
        this.contToYear.setBoundLabelUnderline(true);
        // contToMonth		
        this.contToMonth.setBoundLabelText(resHelper.getString("contToMonth.boundLabelText"));		
        this.contToMonth.setBoundLabelLength(50);		
        this.contToMonth.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // spFromYear
        // spFromMonth
        // spToYear
        // spToMonth
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
        contFromYear.setBounds(new Rectangle(15, 64, 140, 19));
        this.add(contFromYear, null);
        contFromMonth.setBounds(new Rectangle(158, 63, 118, 19));
        this.add(contFromMonth, null);
        contToYear.setBounds(new Rectangle(322, 63, 140, 19));
        this.add(contToYear, null);
        contToMonth.setBounds(new Rectangle(465, 63, 118, 19));
        this.add(contToMonth, null);
        kDLabel1.setBounds(new Rectangle(290, 65, 28, 19));
        this.add(kDLabel1, null);
        //contFromYear
        contFromYear.setBoundEditor(spFromYear);
        //contFromMonth
        contFromMonth.setBoundEditor(spFromMonth);
        //contToYear
        contToYear.setBoundEditor(spToYear);
        //contToMonth
        contToMonth.setBoundEditor(spToMonth);

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
	    return "com.kingdee.eas.fdc.contract.app.MarketProjectFKReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "MarketProjectFKReportFilterUI");
    }




}