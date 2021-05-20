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
public abstract class AbstractPayRequestFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayRequestFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel plChangeState;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIncludeClose;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbFivouchered;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsCreatePay;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Contract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Payee;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSave;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSubmit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAuditing;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAudited;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioStateAll;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    /**
     * output class constructor
     */
    public AbstractPayRequestFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayRequestFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plChangeState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.chkIncludeClose = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbFivouchered = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsCreatePay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Payee = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.radioSave = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSubmit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAuditing = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAudited = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioStateAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contCompany.setName("contCompany");
        this.contPrjoect.setName("contPrjoect");
        this.contContract.setName("contContract");
        this.contPayee.setName("contPayee");
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.plChangeState.setName("plChangeState");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.chkIncludeClose.setName("chkIncludeClose");
        this.cbFivouchered.setName("cbFivouchered");
        this.cbIsCreatePay.setName("cbIsCreatePay");
        this.txtCompany.setName("txtCompany");
        this.txtProject.setName("txtProject");
        this.f7Contract.setName("f7Contract");
        this.f7Payee.setName("f7Payee");
        this.pkDateFrom.setName("pkDateFrom");
        this.pkDateTo.setName("pkDateTo");
        this.radioSave.setName("radioSave");
        this.radioSubmit.setName("radioSubmit");
        this.radioAuditing.setName("radioAuditing");
        this.radioAudited.setName("radioAudited");
        this.radioStateAll.setName("radioStateAll");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);		
        this.contCompany.setVisible(false);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contContract		
        this.contContract.setBoundLabelText(resHelper.getString("contContract.boundLabelText"));		
        this.contContract.setBoundLabelLength(100);		
        this.contContract.setBoundLabelUnderline(true);
        // contPayee		
        this.contPayee.setBoundLabelText(resHelper.getString("contPayee.boundLabelText"));		
        this.contPayee.setBoundLabelLength(100);		
        this.contPayee.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // plChangeState		
        this.plChangeState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plChangeState.border.title")));
        // btnCompanySelect		
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));		
        this.btnCompanySelect.setVisible(false);
        this.btnCompanySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCompanySelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIncludeClose		
        this.chkIncludeClose.setText(resHelper.getString("chkIncludeClose.text"));		
        this.chkIncludeClose.setVisible(false);
        // cbFivouchered		
        this.cbFivouchered.setText(resHelper.getString("cbFivouchered.text"));
        // cbIsCreatePay		
        this.cbIsCreatePay.setText(resHelper.getString("cbIsCreatePay.text"));
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // txtProject		
        this.txtProject.setEnabled(false);
        // f7Contract		
        this.f7Contract.setEditable(true);		
        this.f7Contract.setEnabledMultiSelection(true);		
        this.f7Contract.setDisplayFormat("$name$");		
        this.f7Contract.setEditFormat("$number$");		
        this.f7Contract.setCommitFormat("$number$");
        // f7Payee		
        this.f7Payee.setEditable(true);		
        this.f7Payee.setDisplayFormat("$name$");		
        this.f7Payee.setEditFormat("$number$");		
        this.f7Payee.setCommitFormat("$number$");
        // pkDateFrom
        // pkDateTo
        // radioSave		
        this.radioSave.setText(resHelper.getString("radioSave.text"));
        // radioSubmit		
        this.radioSubmit.setText(resHelper.getString("radioSubmit.text"));
        // radioAuditing		
        this.radioAuditing.setText(resHelper.getString("radioAuditing.text"));
        // radioAudited		
        this.radioAudited.setText(resHelper.getString("radioAudited.text"));
        // radioStateAll		
        this.radioStateAll.setText(resHelper.getString("radioStateAll.text"));
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.radioSave);
        this.kDButtonGroup2.add(this.radioSubmit);
        this.kDButtonGroup2.add(this.radioAuditing);
        this.kDButtonGroup2.add(this.radioAudited);
        this.kDButtonGroup2.add(this.radioStateAll);
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
        this.setBounds(new Rectangle(10, 10, 400, 400));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(10, 10, 270, 21));
        this.add(contCompany, null);
        contPrjoect.setBounds(new Rectangle(10, 33, 270, 19));
        this.add(contPrjoect, null);
        contContract.setBounds(new Rectangle(9, 54, 270, 19));
        this.add(contContract, null);
        contPayee.setBounds(new Rectangle(10, 75, 270, 19));
        this.add(contPayee, null);
        contDateFrom.setBounds(new Rectangle(10, 97, 270, 19));
        this.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(10, 119, 270, 19));
        this.add(contDateTo, null);
        plChangeState.setBounds(new Rectangle(10, 148, 102, 146));
        this.add(plChangeState, null);
        btnCompanySelect.setBounds(new Rectangle(287, 10, 69, 21));
        this.add(btnCompanySelect, null);
        btnProjectSelect.setBounds(new Rectangle(288, 34, 69, 19));
        this.add(btnProjectSelect, null);
        chkIncludeClose.setBounds(new Rectangle(19, 297, 208, 15));
        this.add(chkIncludeClose, null);
        cbFivouchered.setBounds(new Rectangle(149, 162, 140, 19));
        this.add(cbFivouchered, null);
        cbIsCreatePay.setBounds(new Rectangle(149, 197, 140, 19));
        this.add(cbIsCreatePay, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contPrjoect
        contPrjoect.setBoundEditor(txtProject);
        //contContract
        contContract.setBoundEditor(f7Contract);
        //contPayee
        contPayee.setBoundEditor(f7Payee);
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //plChangeState
        plChangeState.setLayout(null);        radioSave.setBounds(new Rectangle(24, 18, 64, 19));
        plChangeState.add(radioSave, null);
        radioSubmit.setBounds(new Rectangle(24, 42, 64, 19));
        plChangeState.add(radioSubmit, null);
        radioAuditing.setBounds(new Rectangle(24, 65, 64, 19));
        plChangeState.add(radioAuditing, null);
        radioAudited.setBounds(new Rectangle(24, 90, 64, 19));
        plChangeState.add(radioAudited, null);
        radioStateAll.setBounds(new Rectangle(24, 115, 64, 19));
        plChangeState.add(radioStateAll, null);

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
	    return "com.kingdee.eas.fdc.contract.app.PayRequestFullFilterUIHandler";
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
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PayRequestFullFilterUI");
    }




}