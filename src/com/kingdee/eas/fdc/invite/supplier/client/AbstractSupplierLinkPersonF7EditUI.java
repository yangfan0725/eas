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
public abstract class AbstractSupplierLinkPersonF7EditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierLinkPersonF7EditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierFileType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPersonName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPosition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPersonFax;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsDefault;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContact;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierFileType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPersonName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPosition;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWorkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPersonFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContact;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtType;
    protected com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info editData = null;
    /**
     * output class constructor
     */
    public AbstractSupplierLinkPersonF7EditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSupplierLinkPersonF7EditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSupplierFileType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPersonName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPosition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPersonFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsDefault = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContact = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSupplierFileType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPersonName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPosition = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWorkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPersonFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContact = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSupplierFileType.setName("contSupplierFileType");
        this.contPersonName.setName("contPersonName");
        this.contPosition.setName("contPosition");
        this.contPhone.setName("contPhone");
        this.contWorkPhone.setName("contWorkPhone");
        this.contPersonFax.setName("contPersonFax");
        this.cbIsDefault.setName("cbIsDefault");
        this.contEmail.setName("contEmail");
        this.contContact.setName("contContact");
        this.contType.setName("contType");
        this.prmtSupplierFileType.setName("prmtSupplierFileType");
        this.txtPersonName.setName("txtPersonName");
        this.txtPosition.setName("txtPosition");
        this.txtPhone.setName("txtPhone");
        this.txtWorkPhone.setName("txtWorkPhone");
        this.txtPersonFax.setName("txtPersonFax");
        this.txtEmail.setName("txtEmail");
        this.txtContact.setName("txtContact");
        this.txtType.setName("txtType");
        // CoreUI
        // contSupplierFileType		
        this.contSupplierFileType.setBoundLabelText(resHelper.getString("contSupplierFileType.boundLabelText"));		
        this.contSupplierFileType.setBoundLabelLength(100);		
        this.contSupplierFileType.setBoundLabelUnderline(true);
        // contPersonName		
        this.contPersonName.setBoundLabelText(resHelper.getString("contPersonName.boundLabelText"));		
        this.contPersonName.setBoundLabelLength(100);		
        this.contPersonName.setBoundLabelUnderline(true);
        // contPosition		
        this.contPosition.setBoundLabelText(resHelper.getString("contPosition.boundLabelText"));		
        this.contPosition.setBoundLabelLength(100);		
        this.contPosition.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contWorkPhone		
        this.contWorkPhone.setBoundLabelText(resHelper.getString("contWorkPhone.boundLabelText"));		
        this.contWorkPhone.setBoundLabelLength(100);		
        this.contWorkPhone.setBoundLabelUnderline(true);
        // contPersonFax		
        this.contPersonFax.setBoundLabelText(resHelper.getString("contPersonFax.boundLabelText"));		
        this.contPersonFax.setBoundLabelLength(100);		
        this.contPersonFax.setBoundLabelUnderline(true);
        // cbIsDefault		
        this.cbIsDefault.setText(resHelper.getString("cbIsDefault.text"));
        // contEmail		
        this.contEmail.setBoundLabelText(resHelper.getString("contEmail.boundLabelText"));		
        this.contEmail.setBoundLabelLength(100);		
        this.contEmail.setBoundLabelUnderline(true);
        // contContact		
        this.contContact.setBoundLabelText(resHelper.getString("contContact.boundLabelText"));		
        this.contContact.setBoundLabelLength(100);		
        this.contContact.setBoundLabelUnderline(true);
        // contType		
        this.contType.setBoundLabelText(resHelper.getString("contType.boundLabelText"));		
        this.contType.setBoundLabelLength(100);		
        this.contType.setBoundLabelUnderline(true);
        // prmtSupplierFileType		
        this.prmtSupplierFileType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.SupplierFileTypeQuery");		
        this.prmtSupplierFileType.setEnabled(false);		
        this.prmtSupplierFileType.setEditFormat("$number$");		
        this.prmtSupplierFileType.setDisplayFormat("$name$");		
        this.prmtSupplierFileType.setCommitFormat("$number$");
        // txtPersonName
        // txtPosition
        // txtPhone
        // txtWorkPhone
        // txtPersonFax
        // txtEmail
        // txtContact
        // txtType
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
        this.setBounds(new Rectangle(10, 10, 290, 240));
        this.setLayout(null);
        contSupplierFileType.setBounds(new Rectangle(12, 11, 270, 19));
        this.add(contSupplierFileType, null);
        contPersonName.setBounds(new Rectangle(12, 57, 270, 19));
        this.add(contPersonName, null);
        contPosition.setBounds(new Rectangle(12, 79, 270, 19));
        this.add(contPosition, null);
        contPhone.setBounds(new Rectangle(12, 101, 270, 19));
        this.add(contPhone, null);
        contWorkPhone.setBounds(new Rectangle(12, 123, 270, 19));
        this.add(contWorkPhone, null);
        contPersonFax.setBounds(new Rectangle(12, 145, 270, 19));
        this.add(contPersonFax, null);
        cbIsDefault.setBounds(new Rectangle(12, 168, 140, 19));
        this.add(cbIsDefault, null);
        contEmail.setBounds(new Rectangle(12, 188, 270, 19));
        this.add(contEmail, null);
        contContact.setBounds(new Rectangle(12, 210, 270, 19));
        this.add(contContact, null);
        contType.setBounds(new Rectangle(12, 34, 270, 19));
        this.add(contType, null);
        //contSupplierFileType
        contSupplierFileType.setBoundEditor(prmtSupplierFileType);
        //contPersonName
        contPersonName.setBoundEditor(txtPersonName);
        //contPosition
        contPosition.setBoundEditor(txtPosition);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contWorkPhone
        contWorkPhone.setBoundEditor(txtWorkPhone);
        //contPersonFax
        contPersonFax.setBoundEditor(txtPersonFax);
        //contEmail
        contEmail.setBoundEditor(txtEmail);
        //contContact
        contContact.setBoundEditor(txtContact);
        //contType
        contType.setBoundEditor(txtType);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isDefault", boolean.class, this.cbIsDefault, "selected");
		dataBinder.registerBinding("supplierFileType", com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo.class, this.prmtSupplierFileType, "data");
		dataBinder.registerBinding("personName", String.class, this.txtPersonName, "text");
		dataBinder.registerBinding("position", String.class, this.txtPosition, "text");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("workPhone", String.class, this.txtWorkPhone, "text");
		dataBinder.registerBinding("personFax", String.class, this.txtPersonFax, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("contact", String.class, this.txtContact, "text");
		dataBinder.registerBinding("type", String.class, this.txtType, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierLinkPersonF7EditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info)ov;
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
		getValidateHelper().registerBindProperty("isDefault", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierFileType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contact", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isDefault"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplierFileType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplierFileType.id"));
        	sic.add(new SelectorItemInfo("supplierFileType.number"));
        	sic.add(new SelectorItemInfo("supplierFileType.name"));
		}
        sic.add(new SelectorItemInfo("personName"));
        sic.add(new SelectorItemInfo("position"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("workPhone"));
        sic.add(new SelectorItemInfo("personFax"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("contact"));
        sic.add(new SelectorItemInfo("type"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierLinkPersonF7EditUI");
    }




}