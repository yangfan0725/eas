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
public abstract class AbstractUserSupplierAssoUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractUserSupplierAssoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlUserInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSupplierInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOprt;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMobileNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPassword;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMobileNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDPasswordField txtPassword;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPosition;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combRegisterState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompnayNameMatch;
    protected com.kingdee.bos.ctrl.swing.KDButton btnMatch;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMatchName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSaveAsso;
    protected ActionSaveAsso actionSaveAsso = null;
    protected ActionMatch actionMatch = null;
    /**
     * output class constructor
     */
    public AbstractUserSupplierAssoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractUserSupplierAssoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSaveAsso
        this.actionSaveAsso = new ActionSaveAsso(this);
        getActionManager().registerAction("actionSaveAsso", actionSaveAsso);
         this.actionSaveAsso.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMatch
        this.actionMatch = new ActionMatch(this);
        getActionManager().registerAction("actionMatch", actionMatch);
         this.actionMatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlUserInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSupplierInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlOprt = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contMobileNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPassword = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtMobileNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPassword = new com.kingdee.bos.ctrl.swing.KDPasswordField();
        this.txtCompanyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPosition = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combRegisterState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tblSupplier = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCompnayNameMatch = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnMatch = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtMatchName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSaveAsso = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlUserInfo.setName("pnlUserInfo");
        this.pnlSupplierInfo.setName("pnlSupplierInfo");
        this.pnlOprt.setName("pnlOprt");
        this.kDSeparator2.setName("kDSeparator2");
        this.kDSeparator3.setName("kDSeparator3");
        this.contMobileNumber.setName("contMobileNumber");
        this.contName.setName("contName");
        this.contPassword.setName("contPassword");
        this.contCompanyName.setName("contCompanyName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.txtMobileNumber.setName("txtMobileNumber");
        this.txtName.setName("txtName");
        this.txtPassword.setName("txtPassword");
        this.txtCompanyName.setName("txtCompanyName");
        this.txtPhone.setName("txtPhone");
        this.txtEmail.setName("txtEmail");
        this.txtPosition.setName("txtPosition");
        this.combRegisterState.setName("combRegisterState");
        this.pkCreateDate.setName("pkCreateDate");
        this.tblSupplier.setName("tblSupplier");
        this.contCompnayNameMatch.setName("contCompnayNameMatch");
        this.btnMatch.setName("btnMatch");
        this.txtMatchName.setName("txtMatchName");
        this.btnSaveAsso.setName("btnSaveAsso");
        // CoreUI		
        this.btnPageSetup.setVisible(false);		
        this.btnCloud.setVisible(false);		
        this.btnXunTong.setVisible(false);		
        this.kDSeparatorCloud.setVisible(false);		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemCloudFeed.setVisible(false);		
        this.menuItemCloudScreen.setEnabled(false);		
        this.menuItemCloudScreen.setVisible(false);		
        this.menuItemCloudShare.setVisible(false);		
        this.kdSeparatorFWFile1.setVisible(false);		
        this.menuItemCalculator.setVisible(true);
        // pnlUserInfo		
        this.pnlUserInfo.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("pnlUserInfo.border.title")));
        // pnlSupplierInfo		
        this.pnlSupplierInfo.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("pnlSupplierInfo.border.title")));
        // pnlOprt
        // kDSeparator2
        // kDSeparator3
        // contMobileNumber		
        this.contMobileNumber.setBoundLabelText(resHelper.getString("contMobileNumber.boundLabelText"));		
        this.contMobileNumber.setBoundLabelUnderline(true);		
        this.contMobileNumber.setBoundLabelLength(100);		
        this.contMobileNumber.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setBoundLabelLength(100);
        // contPassword		
        this.contPassword.setBoundLabelText(resHelper.getString("contPassword.boundLabelText"));		
        this.contPassword.setBoundLabelLength(100);		
        this.contPassword.setBoundLabelUnderline(true);
        // contCompanyName		
        this.contCompanyName.setBoundLabelText(resHelper.getString("contCompanyName.boundLabelText"));		
        this.contCompanyName.setBoundLabelUnderline(true);		
        this.contCompanyName.setBoundLabelLength(100);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // txtMobileNumber		
        this.txtMobileNumber.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // txtPassword		
        this.txtPassword.setText(resHelper.getString("txtPassword.text"));		
        this.txtPassword.setEnabled(false);
        // txtCompanyName		
        this.txtCompanyName.setEnabled(false);
        // txtPhone		
        this.txtPhone.setEnabled(false);
        // txtEmail		
        this.txtEmail.setEnabled(false);
        // txtPosition		
        this.txtPosition.setEnabled(false);
        // combRegisterState		
        this.combRegisterState.setEnabled(false);		
        this.combRegisterState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.RegistStateEnum").toArray());
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // tblSupplier
		String tblSupplierStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supplierId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"supplierNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"supplierName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"inviteType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isAsso\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supplierId}</t:Cell><t:Cell>$Resource{supplierNumber}</t:Cell><t:Cell>$Resource{supplierName}</t:Cell><t:Cell>$Resource{inviteType}</t:Cell><t:Cell>$Resource{isAsso}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblSupplier.setFormatXml(resHelper.translateString("tblSupplier",tblSupplierStrXML));

        

        // contCompnayNameMatch		
        this.contCompnayNameMatch.setBoundLabelText(resHelper.getString("contCompnayNameMatch.boundLabelText"));		
        this.contCompnayNameMatch.setBoundLabelUnderline(true);		
        this.contCompnayNameMatch.setBoundLabelLength(100);
        // btnMatch
        this.btnMatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionMatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMatch.setText(resHelper.getString("btnMatch.text"));
        this.btnMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnMatch_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtMatchName
        // btnSaveAsso
        this.btnSaveAsso.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveAsso, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSaveAsso.setText(resHelper.getString("btnSaveAsso.text"));		
        this.btnSaveAsso.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
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
        this.setBounds(new Rectangle(10, 10, 680, 580));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 580));
        pnlUserInfo.setBounds(new Rectangle(24, 29, 633, 254));
        this.add(pnlUserInfo, new KDLayout.Constraints(24, 29, 633, 254, 0));
        pnlSupplierInfo.setBounds(new Rectangle(24, 378, 633, 150));
        this.add(pnlSupplierInfo, new KDLayout.Constraints(24, 378, 633, 150, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlOprt.setBounds(new Rectangle(24, 311, 633, 49));
        this.add(pnlOprt, new KDLayout.Constraints(24, 311, 633, 49, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator2.setBounds(new Rectangle(24, 295, 633, 10));
        this.add(kDSeparator2, new KDLayout.Constraints(24, 295, 633, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator3.setBounds(new Rectangle(24, 367, 633, 10));
        this.add(kDSeparator3, new KDLayout.Constraints(24, 367, 633, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlUserInfo
        pnlUserInfo.setLayout(new KDLayout());
        pnlUserInfo.putClientProperty("OriginalBounds", new Rectangle(24, 29, 633, 254));        contMobileNumber.setBounds(new Rectangle(45, 22, 502, 19));
        pnlUserInfo.add(contMobileNumber, new KDLayout.Constraints(45, 22, 502, 19, 0));
        contName.setBounds(new Rectangle(45, 72, 502, 19));
        pnlUserInfo.add(contName, new KDLayout.Constraints(45, 72, 502, 19, 0));
        contPassword.setBounds(new Rectangle(45, 47, 502, 19));
        pnlUserInfo.add(contPassword, new KDLayout.Constraints(45, 47, 502, 19, 0));
        contCompanyName.setBounds(new Rectangle(45, 97, 502, 19));
        pnlUserInfo.add(contCompanyName, new KDLayout.Constraints(45, 97, 502, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(45, 122, 502, 19));
        pnlUserInfo.add(kDLabelContainer1, new KDLayout.Constraints(45, 122, 502, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(45, 147, 502, 19));
        pnlUserInfo.add(kDLabelContainer2, new KDLayout.Constraints(45, 147, 502, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(45, 172, 502, 19));
        pnlUserInfo.add(kDLabelContainer3, new KDLayout.Constraints(45, 172, 502, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(45, 197, 502, 19));
        pnlUserInfo.add(kDLabelContainer4, new KDLayout.Constraints(45, 197, 502, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(45, 223, 502, 19));
        pnlUserInfo.add(kDLabelContainer5, new KDLayout.Constraints(45, 223, 502, 19, 0));
        //contMobileNumber
        contMobileNumber.setBoundEditor(txtMobileNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contPassword
        contPassword.setBoundEditor(txtPassword);
        //contCompanyName
        contCompanyName.setBoundEditor(txtCompanyName);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtPhone);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtEmail);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtPosition);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(combRegisterState);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(pkCreateDate);
        //pnlSupplierInfo
pnlSupplierInfo.setLayout(new BorderLayout(0, 0));        pnlSupplierInfo.add(tblSupplier, BorderLayout.CENTER);
        //pnlOprt
        pnlOprt.setLayout(new KDLayout());
        pnlOprt.putClientProperty("OriginalBounds", new Rectangle(24, 311, 633, 49));        contCompnayNameMatch.setBounds(new Rectangle(21, 15, 270, 19));
        pnlOprt.add(contCompnayNameMatch, new KDLayout.Constraints(21, 15, 270, 19, 0));
        btnMatch.setBounds(new Rectangle(316, 13, 73, 21));
        pnlOprt.add(btnMatch, new KDLayout.Constraints(316, 13, 73, 21, 0));
        //contCompnayNameMatch
        contCompnayNameMatch.setBoundEditor(txtMatchName);

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
        this.toolBar.add(btnSaveAsso);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.UserSupplierAssoUIHandler";
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
     * output btnMatch_actionPerformed method
     */
    protected void btnMatch_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSaveAsso_actionPerformed method
     */
    public void actionSaveAsso_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMatch_actionPerformed method
     */
    public void actionMatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSaveAsso(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveAsso() {
    	return false;
    }
	public RequestContext prepareActionMatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMatch() {
    	return false;
    }

    /**
     * output ActionSaveAsso class
     */     
    protected class ActionSaveAsso extends ItemAction {     
    
        public ActionSaveAsso()
        {
            this(null);
        }

        public ActionSaveAsso(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveAsso.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAsso.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAsso.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUserSupplierAssoUI.this, "ActionSaveAsso", "actionSaveAsso_actionPerformed", e);
        }
    }

    /**
     * output ActionMatch class
     */     
    protected class ActionMatch extends ItemAction {     
    
        public ActionMatch()
        {
            this(null);
        }

        public ActionMatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUserSupplierAssoUI.this, "ActionMatch", "actionMatch_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "UserSupplierAssoUI");
    }




}