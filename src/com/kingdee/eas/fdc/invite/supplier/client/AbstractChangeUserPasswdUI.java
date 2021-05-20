/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractChangeUserPasswdUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeUserPasswdUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancle;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewPasswd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConfirmPasswd;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUserPhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUserName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDPasswordField txtNewPasswd;
    protected com.kingdee.bos.ctrl.swing.KDPasswordField txtPasswdConfirm;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyName;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractChangeUserPasswdUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeUserPasswdUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancle = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contNewPasswd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConfirmPasswd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contUserPhoneNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUserName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNewPasswd = new com.kingdee.bos.ctrl.swing.KDPasswordField();
        this.txtPasswdConfirm = new com.kingdee.bos.ctrl.swing.KDPasswordField();
        this.txtPhoneNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompanyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDSeparator2.setName("kDSeparator2");
        this.btnSave.setName("btnSave");
        this.btnCancle.setName("btnCancle");
        this.kDSeparator3.setName("kDSeparator3");
        this.contNewPasswd.setName("contNewPasswd");
        this.contConfirmPasswd.setName("contConfirmPasswd");
        this.kDTextField1.setName("kDTextField1");
        this.contUserPhoneNumber.setName("contUserPhoneNumber");
        this.contUserName.setName("contUserName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNewPasswd.setName("txtNewPasswd");
        this.txtPasswdConfirm.setName("txtPasswdConfirm");
        this.txtPhoneNumber.setName("txtPhoneNumber");
        this.txtName.setName("txtName");
        this.txtCompanyName.setName("txtCompanyName");
        // CoreUI
        // kDSeparator2
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnCancle
        this.btnCancle.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancle.setText(resHelper.getString("btnCancle.text"));
        // kDSeparator3
        // contNewPasswd		
        this.contNewPasswd.setBoundLabelText(resHelper.getString("contNewPasswd.boundLabelText"));		
        this.contNewPasswd.setBoundLabelUnderline(true);		
        this.contNewPasswd.setBoundLabelLength(100);
        // contConfirmPasswd		
        this.contConfirmPasswd.setBoundLabelText(resHelper.getString("contConfirmPasswd.boundLabelText"));		
        this.contConfirmPasswd.setBoundLabelUnderline(true);		
        this.contConfirmPasswd.setBoundLabelLength(100);
        // kDTextField1
        // contUserPhoneNumber		
        this.contUserPhoneNumber.setBoundLabelText(resHelper.getString("contUserPhoneNumber.boundLabelText"));		
        this.contUserPhoneNumber.setBoundLabelUnderline(true);		
        this.contUserPhoneNumber.setBoundLabelLength(100);		
        this.contUserPhoneNumber.setEnabled(false);
        // contUserName		
        this.contUserName.setBoundLabelText(resHelper.getString("contUserName.boundLabelText"));		
        this.contUserName.setBoundLabelUnderline(true);		
        this.contUserName.setBoundLabelLength(100);		
        this.contUserName.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setEnabled(false);
        // txtNewPasswd
        // txtPasswdConfirm
        // txtPhoneNumber		
        this.txtPhoneNumber.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // txtCompanyName		
        this.txtCompanyName.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 556, 359));
        this.setLayout(null);
        kDSeparator2.setBounds(new Rectangle(13, 135, 533, 4));
        this.add(kDSeparator2, null);
        btnSave.setBounds(new Rectangle(127, 320, 73, 21));
        this.add(btnSave, null);
        btnCancle.setBounds(new Rectangle(382, 320, 73, 21));
        this.add(btnCancle, null);
        kDSeparator3.setBounds(new Rectangle(11, 311, 533, 6));
        this.add(kDSeparator3, null);
        contNewPasswd.setBounds(new Rectangle(64, 179, 399, 19));
        this.add(contNewPasswd, null);
        contConfirmPasswd.setBounds(new Rectangle(64, 229, 399, 19));
        this.add(contConfirmPasswd, null);
        kDTextField1.setBounds(new Rectangle(550, 273, 170, 19));
        this.add(kDTextField1, null);
        contUserPhoneNumber.setBounds(new Rectangle(64, 22, 399, 19));
        this.add(contUserPhoneNumber, null);
        contUserName.setBounds(new Rectangle(64, 60, 399, 19));
        this.add(contUserName, null);
        kDLabelContainer1.setBounds(new Rectangle(64, 100, 399, 19));
        this.add(kDLabelContainer1, null);
        //contNewPasswd
        contNewPasswd.setBoundEditor(txtNewPasswd);
        //contConfirmPasswd
        contConfirmPasswd.setBoundEditor(txtPasswdConfirm);
        //contUserPhoneNumber
        contUserPhoneNumber.setBoundEditor(txtPhoneNumber);
        //contUserName
        contUserName.setBoundEditor(txtName);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtCompanyName);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.ChangeUserPasswdUIHandler";
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
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeUserPasswdUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "ChangeUserPasswdUI");
    }




}