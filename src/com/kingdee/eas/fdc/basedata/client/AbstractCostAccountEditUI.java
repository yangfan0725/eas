/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractCostAccountEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCostAccountEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAssigned;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea bizDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsProgramming;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsMarket;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYjType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbYjType;
    protected com.kingdee.eas.fdc.basedata.CostAccountInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCostAccountEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCostAccountEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkAssigned = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bizDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsCostAccount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsProgramming = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsMarket = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contYjType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtParentNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.boxType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbYjType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contLongNumber.setName("contLongNumber");
        this.chkAssigned.setName("chkAssigned");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabel1.setName("kDLabel1");
        this.bizDescription.setName("bizDescription");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.chkIsCostAccount.setName("chkIsCostAccount");
        this.cbIsProgramming.setName("cbIsProgramming");
        this.contRate.setName("contRate");
        this.cbIsMarket.setName("cbIsMarket");
        this.contYjType.setName("contYjType");
        this.bizName.setName("bizName");
        this.txtNumber.setName("txtNumber");
        this.txtLongNumber.setName("txtLongNumber");
        this.txtParentNumber.setName("txtParentNumber");
        this.boxType.setName("boxType");
        this.txtRate.setName("txtRate");
        this.cbYjType.setName("cbYjType");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(100);		
        this.contLongNumber.setBoundLabelUnderline(true);
        // chkAssigned		
        this.chkAssigned.setText(resHelper.getString("chkAssigned.text"));		
        this.chkAssigned.setVisible(false);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // bizDescription		
        this.bizDescription.setMaxLength(200);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // chkIsCostAccount		
        this.chkIsCostAccount.setText(resHelper.getString("chkIsCostAccount.text"));
        // cbIsProgramming		
        this.cbIsProgramming.setText(resHelper.getString("cbIsProgramming.text"));
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelLength(100);		
        this.contRate.setBoundLabelUnderline(true);
        // cbIsMarket		
        this.cbIsMarket.setText(resHelper.getString("cbIsMarket.text"));
        // contYjType		
        this.contYjType.setBoundLabelText(resHelper.getString("contYjType.boundLabelText"));		
        this.contYjType.setBoundLabelLength(100);		
        this.contYjType.setBoundLabelUnderline(true);
        // bizName		
        this.bizName.setRequired(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        this.txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtLongNumber		
        this.txtLongNumber.setEnabled(false);
        // txtParentNumber		
        this.txtParentNumber.setEnabled(false);		
        this.txtParentNumber.setText(resHelper.getString("txtParentNumber.text"));
        // boxType		
        this.boxType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.CostAccountTypeEnum").toArray());		
        this.boxType.setRequired(true);
        // txtRate		
        this.txtRate.setDataType(1);		
        this.txtRate.setPrecision(2);
        // cbYjType		
        this.cbYjType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum").toArray());
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
        this.setBounds(new Rectangle(10, 10, 290, 300));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contNumber, null);
        contLongNumber.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contLongNumber, null);
        chkAssigned.setBounds(new Rectangle(207, 180, 58, 19));
        this.add(chkAssigned, null);
        chkIsEnabled.setBounds(new Rectangle(147, 180, 52, 19));
        this.add(chkIsEnabled, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabel1.setBounds(new Rectangle(10, 200, 39, 19));
        this.add(kDLabel1, null);
        bizDescription.setBounds(new Rectangle(10, 251, 270, 39));
        this.add(bizDescription, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(kDLabelContainer2, null);
        chkIsCostAccount.setBounds(new Rectangle(168, 200, 60, 18));
        this.add(chkIsCostAccount, null);
        cbIsProgramming.setBounds(new Rectangle(50, 200, 129, 19));
        this.add(cbIsProgramming, null);
        contRate.setBounds(new Rectangle(9, 162, 270, 19));
        this.add(contRate, null);
        cbIsMarket.setBounds(new Rectangle(227, 200, 140, 19));
        this.add(cbIsMarket, null);
        contYjType.setBounds(new Rectangle(11, 224, 270, 19));
        this.add(contYjType, null);
        //contName
        contName.setBoundEditor(bizName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contLongNumber
        contLongNumber.setBoundEditor(txtLongNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtParentNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(boxType);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contYjType
        contYjType.setBoundEditor(cbYjType);

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
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "_multiLangItem");
		dataBinder.registerBinding("isCostAccount", boolean.class, this.chkIsCostAccount, "selected");
		dataBinder.registerBinding("isProgramming", boolean.class, this.cbIsProgramming, "selected");
		dataBinder.registerBinding("isMarket", boolean.class, this.cbIsMarket, "selected");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("type", com.kingdee.eas.fdc.basedata.CostAccountTypeEnum.class, this.boxType, "selectedItem");
		dataBinder.registerBinding("rate", java.math.BigDecimal.class, this.txtRate, "value");
		dataBinder.registerBinding("yjType", com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum.class, this.cbYjType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.CostAccountEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.CostAccountInfo)ov;
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
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCostAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isProgramming", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isMarket", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjType", ValidateHelper.ON_SAVE);    		
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
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isCostAccount"));
        sic.add(new SelectorItemInfo("isProgramming"));
        sic.add(new SelectorItemInfo("isMarket"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("yjType"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "CostAccountEditUI");
    }




}