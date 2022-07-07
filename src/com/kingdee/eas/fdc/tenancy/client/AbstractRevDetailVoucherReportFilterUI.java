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
public abstract class AbstractRevDetailVoucherReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRevDetailVoucherReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTanancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsNeedTotal;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsZero;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTanancyBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    /**
     * output class constructor
     */
    public AbstractRevDetailVoucherReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRevDetailVoucherReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contTanancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.chkIsNeedTotal = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsZero = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtTanancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contTanancyBill.setName("contTanancyBill");
        this.contRoom.setName("contRoom");
        this.cbIsAll.setName("cbIsAll");
        this.contCustomer.setName("contCustomer");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabel1.setName("kDLabel1");
        this.spMonth.setName("spMonth");
        this.kDLabel2.setName("kDLabel2");
        this.chkIsNeedTotal.setName("chkIsNeedTotal");
        this.cbIsZero.setName("cbIsZero");
        this.prmtTanancyBill.setName("prmtTanancyBill");
        this.prmtRoom.setName("prmtRoom");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.spYear.setName("spYear");
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
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // spMonth
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // chkIsNeedTotal		
        this.chkIsNeedTotal.setText(resHelper.getString("chkIsNeedTotal.text"));		
        this.chkIsNeedTotal.setSelected(true);
        // cbIsZero		
        this.cbIsZero.setText(resHelper.getString("cbIsZero.text"));		
        this.cbIsZero.setSelected(true);
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
        // spYear
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
        kDLabelContainer1.setBounds(new Rectangle(27, 101, 189, 19));
        this.add(kDLabelContainer1, null);
        kDLabel1.setBounds(new Rectangle(221, 101, 15, 19));
        this.add(kDLabel1, null);
        spMonth.setBounds(new Rectangle(241, 101, 40, 19));
        this.add(spMonth, null);
        kDLabel2.setBounds(new Rectangle(285, 101, 15, 19));
        this.add(kDLabel2, null);
        chkIsNeedTotal.setBounds(new Rectangle(173, 128, 122, 19));
        this.add(chkIsNeedTotal, null);
        cbIsZero.setBounds(new Rectangle(301, 128, 140, 19));
        this.add(cbIsZero, null);
        //contTanancyBill
        contTanancyBill.setBoundEditor(prmtTanancyBill);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spYear);

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
	    return "com.kingdee.eas.fdc.tenancy.app.RevDetailVoucherReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RevDetailVoucherReportFilterUI");
    }




}