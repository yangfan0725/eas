/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractRevDetailFinReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRevDetailFinReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTanancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTanancyBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    /**
     * output class constructor
     */
    public AbstractRevDetailFinReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRevDetailFinReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contTanancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtTanancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTanancyBill.setName("contTanancyBill");
        this.contRoom.setName("contRoom");
        this.cbIsAll.setName("cbIsAll");
        this.contCustomer.setName("contCustomer");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.prmtTanancyBill.setName("prmtTanancyBill");
        this.prmtRoom.setName("prmtRoom");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        // CustomerQueryPanel
        // contTanancyBill		
        this.contTanancyBill.setBoundLabelText(resHelper.getString("contTanancyBill.boundLabelText"));		
        this.contTanancyBill.setBoundLabelLength(100);		
        this.contTanancyBill.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // cbIsAll		
        this.cbIsAll.setText(resHelper.getString("cbIsAll.text"));
        this.cbIsAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbIsAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // prmtTanancyBill		
        this.prmtTanancyBill.setCommitFormat("$name$");		
        this.prmtTanancyBill.setEditFormat("$name$");		
        this.prmtTanancyBill.setDisplayFormat("$name$");		
        this.prmtTanancyBill.setEnabledMultiSelection(true);		
        this.prmtTanancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
        // prmtRoom		
        this.prmtRoom.setEditFormat("$name$");		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setCommitFormat("$name$");		
        this.prmtRoom.setEnabledMultiSelection(true);
        // prmtCustomer
        // prmtMoneyDefine
        // pkFromDate		
        this.pkFromDate.setRequired(true);
        // pkToDate		
        this.pkToDate.setRequired(true);
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
        contTanancyBill.setBounds(new Rectangle(27, 13, 552, 19));
        this.add(contTanancyBill, null);
        contRoom.setBounds(new Rectangle(27, 35, 552, 19));
        this.add(contRoom, null);
        cbIsAll.setBounds(new Rectangle(27, 128, 140, 19));
        this.add(cbIsAll, null);
        contCustomer.setBounds(new Rectangle(27, 57, 552, 19));
        this.add(contCustomer, null);
        contMoneyDefine.setBounds(new Rectangle(27, 79, 552, 19));
        this.add(contMoneyDefine, null);
        kDLabelContainer1.setBounds(new Rectangle(27, 101, 252, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(327, 101, 252, 19));
        this.add(kDLabelContainer2, null);
        //contTanancyBill
        contTanancyBill.setBoundEditor(prmtTanancyBill);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkFromDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(pkToDate);

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
	    return "com.kingdee.eas.fdc.tenancy.app.RevDetailFinReportFilterUIHandler";
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
     * output cbIsAll_actionPerformed method
     */
    protected void cbIsAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RevDetailFinReportFilterUI");
    }




}