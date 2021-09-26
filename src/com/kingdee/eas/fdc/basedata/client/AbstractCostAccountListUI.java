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
public abstract class AbstractCostAccountListUI extends com.kingdee.eas.framework.client.TreeDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCostAccountListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssignToOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCostAccountImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTemplateImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssign;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssignToOrg;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDisAssign;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEnterDB;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelEnterDB;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTemplateImport;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProjectAttachment;
    protected ActionAssign actionAssign = null;
    protected ActionDisAssign actionDisAssign = null;
    protected ActionImport actionImport = null;
    protected ActionProjectAttachment actionProjectAttachment = null;
    protected ActionTemplateImport actionTemplateImport = null;
    protected ActionEnterDB actionEnterDB = null;
    protected ActionCancelEnterDB actionCancelEnterDB = null;
    protected ActionAssignToOrg actionAssignToOrg = null;
    /**
     * output class constructor
     */
    public AbstractCostAccountListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCostAccountListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "CostAccountQuery");
        //actionAssign
        this.actionAssign = new ActionAssign(this);
        getActionManager().registerAction("actionAssign", actionAssign);
         this.actionAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisAssign
        this.actionDisAssign = new ActionDisAssign(this);
        getActionManager().registerAction("actionDisAssign", actionDisAssign);
         this.actionDisAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectAttachment
        this.actionProjectAttachment = new ActionProjectAttachment(this);
        getActionManager().registerAction("actionProjectAttachment", actionProjectAttachment);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTemplateImport
        this.actionTemplateImport = new ActionTemplateImport(this);
        getActionManager().registerAction("actionTemplateImport", actionTemplateImport);
         this.actionTemplateImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnterDB
        this.actionEnterDB = new ActionEnterDB(this);
        getActionManager().registerAction("actionEnterDB", actionEnterDB);
         this.actionEnterDB.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelEnterDB
        this.actionCancelEnterDB = new ActionCancelEnterDB(this);
        getActionManager().registerAction("actionCancelEnterDB", actionCancelEnterDB);
         this.actionCancelEnterDB.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssignToOrg
        this.actionAssignToOrg = new ActionAssignToOrg(this);
        getActionManager().registerAction("actionAssignToOrg", actionAssignToOrg);
         this.actionAssignToOrg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAssignToOrg = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCostAccountImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTemplateImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProjectAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemAssign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAssignToOrg = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDisAssign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemEnterDB = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelEnterDB = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTemplateImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemProjectAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAssign.setName("btnAssign");
        this.btnAssignToOrg.setName("btnAssignToOrg");
        this.btnDisAssign.setName("btnDisAssign");
        this.btnCostAccountImport.setName("btnCostAccountImport");
        this.btnTemplateImport.setName("btnTemplateImport");
        this.btnProjectAttachment.setName("btnProjectAttachment");
        this.kDSeparator4.setName("kDSeparator4");
        this.menuItemAssign.setName("menuItemAssign");
        this.menuItemAssignToOrg.setName("menuItemAssignToOrg");
        this.menuItemDisAssign.setName("menuItemDisAssign");
        this.kDSeparator7.setName("kDSeparator7");
        this.menuItemEnterDB.setName("menuItemEnterDB");
        this.menuItemCancelEnterDB.setName("menuItemCancelEnterDB");
        this.kDSeparator5.setName("kDSeparator5");
        this.menuItemImport.setName("menuItemImport");
        this.menuItemTemplateImport.setName("menuItemTemplateImport");
        this.kDSeparator6.setName("kDSeparator6");
        this.menuItemProjectAttachment.setName("menuItemProjectAttachment");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"assigned\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"curProject.longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"fullOrgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"fullOrgUnit.longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"parent.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"isSource\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"srcCostAccountId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"isEnterDB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isCostAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isMarket\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"isProgramming\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"rate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"yjType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{assigned}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{curProject.longNumber}</t:Cell><t:Cell>$Resource{fullOrgUnit.id}</t:Cell><t:Cell>$Resource{fullOrgUnit.longNumber}</t:Cell><t:Cell>$Resource{parent.id}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{isSource}</t:Cell><t:Cell>$Resource{srcCostAccountId}</t:Cell><t:Cell>$Resource{isEnterDB}</t:Cell><t:Cell>$Resource{isCostAccount}</t:Cell><t:Cell>$Resource{isMarket}</t:Cell><t:Cell>$Resource{isProgramming}</t:Cell><t:Cell>$Resource{rate}</t:Cell><t:Cell>$Resource{yjType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"transLongNumber","name","id","type","isEnabled","assigned","description","isLeaf","level","curProject.id","curProject.longNumber","fullOrgUnit.id","fullOrgUnit.longNumber","parent.id","longNumber","isSource","srcCostAccountId","isEnterDB","isCostAccount","isMarket","isProgramming","rate","yjType"});

		
        this.kDSeparator2.setVisible(false);		
        this.separatorFile1.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setVisible(true);		
        this.pnlMain.setDividerLocation(240);		
        this.treeView.setShowControlPanel(false);		
        this.chkIncludeChild.setText(resHelper.getString("chkIncludeChild.text"));		
        this.btnMoveTree.setVisible(false);		
        this.menuItemGroupAddNew.setVisible(false);		
        this.menuItemGroupAddNew.setEnabled(false);		
        this.menuItemGroupEdit.setVisible(false);		
        this.menuItemGroupRemove.setVisible(false);		
        this.menuItemGroupView.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator1.setEnabled(false);		
        this.separatorEdit1.setVisible(false);		
        this.separatorEdit2.setVisible(false);		
        this.menuItemMoveTree.setVisible(false);		
        this.menuItemGroupMoveTree.setVisible(false);
        // btnAssign
        this.btnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssign.setText(resHelper.getString("btnAssign.text"));		
        this.btnAssign.setToolTipText(resHelper.getString("btnAssign.toolTipText"));
        // btnAssignToOrg
        this.btnAssignToOrg.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignToOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssignToOrg.setText(resHelper.getString("btnAssignToOrg.text"));		
        this.btnAssignToOrg.setToolTipText(resHelper.getString("btnAssignToOrg.toolTipText"));
        // btnDisAssign
        this.btnDisAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisAssign.setText(resHelper.getString("btnDisAssign.text"));		
        this.btnDisAssign.setToolTipText(resHelper.getString("btnDisAssign.toolTipText"));
        // btnCostAccountImport
        this.btnCostAccountImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCostAccountImport.setText(resHelper.getString("btnCostAccountImport.text"));		
        this.btnCostAccountImport.setToolTipText(resHelper.getString("btnCostAccountImport.toolTipText"));
        // btnTemplateImport
        this.btnTemplateImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplateImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTemplateImport.setText(resHelper.getString("btnTemplateImport.text"));		
        this.btnTemplateImport.setToolTipText(resHelper.getString("btnTemplateImport.toolTipText"));
        // btnProjectAttachment
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));		
        this.btnProjectAttachment.setToolTipText(resHelper.getString("btnProjectAttachment.toolTipText"));
        // kDSeparator4
        // menuItemAssign
        this.menuItemAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssign.setText(resHelper.getString("menuItemAssign.text"));		
        this.menuItemAssign.setToolTipText(resHelper.getString("menuItemAssign.toolTipText"));
        this.menuItemAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemAssign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuItemAssignToOrg
        this.menuItemAssignToOrg.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignToOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssignToOrg.setText(resHelper.getString("menuItemAssignToOrg.text"));		
        this.menuItemAssignToOrg.setToolTipText(resHelper.getString("menuItemAssignToOrg.toolTipText"));
        // menuItemDisAssign
        this.menuItemDisAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDisAssign.setText(resHelper.getString("menuItemDisAssign.text"));		
        this.menuItemDisAssign.setToolTipText(resHelper.getString("menuItemDisAssign.toolTipText"));
        // kDSeparator7
        // menuItemEnterDB
        this.menuItemEnterDB.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnterDB, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEnterDB.setText(resHelper.getString("menuItemEnterDB.text"));		
        this.menuItemEnterDB.setToolTipText(resHelper.getString("menuItemEnterDB.toolTipText"));
        // menuItemCancelEnterDB
        this.menuItemCancelEnterDB.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelEnterDB, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelEnterDB.setText(resHelper.getString("menuItemCancelEnterDB.text"));		
        this.menuItemCancelEnterDB.setToolTipText(resHelper.getString("menuItemCancelEnterDB.toolTipText"));
        // kDSeparator5
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));
        // menuItemTemplateImport
        this.menuItemTemplateImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplateImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTemplateImport.setText(resHelper.getString("menuItemTemplateImport.text"));		
        this.menuItemTemplateImport.setToolTipText(resHelper.getString("menuItemTemplateImport.toolTipText"));
        // kDSeparator6
        // menuItemProjectAttachment
        this.menuItemProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProjectAttachment.setText(resHelper.getString("menuItemProjectAttachment.text"));		
        this.menuItemProjectAttachment.setToolTipText(resHelper.getString("menuItemProjectAttachment.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        pnlMain.setBounds(new Rectangle(5, 12, 996, 608));
        this.add(pnlMain, new KDLayout.Constraints(5, 12, 996, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(pnlTable, "right");
        //treeView
        treeView.setTree(treeMain);
        //pnlTable
pnlTable.setLayout(new BorderLayout(0, 0));        pnlTable.add(tblMain, BorderLayout.CENTER);
        pnlTable.add(chkIncludeChild, BorderLayout.NORTH);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudShare);
        menuFile.add(separatorFile1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(SeparatorFile2);
        menuFile.add(menuItemGroupAddNew);
        menuFile.add(separator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator2);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemGroupEdit);
        menuEdit.add(menuItemGroupRemove);
        menuEdit.add(separatorEdit2);
        menuEdit.add(menuItemGroupMoveTree);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        menuView.add(separatorView2);
        menuView.add(menuItemGroupView);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(kDSeparator4);
        menuBiz.add(menuItemAssign);
        menuBiz.add(menuItemAssignToOrg);
        menuBiz.add(menuItemDisAssign);
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemEnterDB);
        menuBiz.add(menuItemCancelEnterDB);
        menuBiz.add(kDSeparator5);
        menuBiz.add(menuItemImport);
        menuBiz.add(menuItemTemplateImport);
        menuBiz.add(kDSeparator6);
        menuBiz.add(menuItemProjectAttachment);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnGroupAddNew);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnGroupView);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnGroupEdit);
        this.toolBar.add(btnGroupRemove);
        this.toolBar.add(btnGroupMoveTree);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnAssign);
        this.toolBar.add(btnAssignToOrg);
        this.toolBar.add(btnDisAssign);
        this.toolBar.add(btnCostAccountImport);
        this.toolBar.add(btnTemplateImport);
        this.toolBar.add(btnProjectAttachment);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.CostAccountListUIHandler";
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
     * output menuItemAssign_actionPerformed method
     */
    protected void menuItemAssign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("transLongNumber"));
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("isSource"));
        sic.add(new SelectorItemInfo("curProject.longNumber"));
        sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
        sic.add(new SelectorItemInfo("srcCostAccountId"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("isEnterDB"));
        sic.add(new SelectorItemInfo("isCostAccount"));
        sic.add(new SelectorItemInfo("isProgramming"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("isMarket"));
        sic.add(new SelectorItemInfo("yjType"));
        return sic;
    }        
    	

    /**
     * output actionAssign_actionPerformed method
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisAssign_actionPerformed method
     */
    public void actionDisAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTemplateImport_actionPerformed method
     */
    public void actionTemplateImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEnterDB_actionPerformed method
     */
    public void actionEnterDB_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelEnterDB_actionPerformed method
     */
    public void actionCancelEnterDB_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssignToOrg_actionPerformed method
     */
    public void actionAssignToOrg_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssign() {
    	return false;
    }
	public RequestContext prepareActionDisAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisAssign() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionProjectAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectAttachment() {
    	return false;
    }
	public RequestContext prepareActionTemplateImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTemplateImport() {
    	return false;
    }
	public RequestContext prepareActionEnterDB(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEnterDB() {
    	return false;
    }
	public RequestContext prepareActionCancelEnterDB(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelEnterDB() {
    	return false;
    }
	public RequestContext prepareActionAssignToOrg(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssignToOrg() {
    	return false;
    }

    /**
     * output ActionAssign class
     */     
    protected class ActionAssign extends ItemAction {     
    
        public ActionAssign()
        {
            this(null);
        }

        public ActionAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionAssign", "actionAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionDisAssign class
     */     
    protected class ActionDisAssign extends ItemAction {     
    
        public ActionDisAssign()
        {
            this(null);
        }

        public ActionDisAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionDisAssign", "actionDisAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionProjectAttachment class
     */     
    protected class ActionProjectAttachment extends ItemAction {     
    
        public ActionProjectAttachment()
        {
            this(null);
        }

        public ActionProjectAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionProjectAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionProjectAttachment", "actionProjectAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionTemplateImport class
     */     
    protected class ActionTemplateImport extends ItemAction {     
    
        public ActionTemplateImport()
        {
            this(null);
        }

        public ActionTemplateImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTemplateImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplateImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplateImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionTemplateImport", "actionTemplateImport_actionPerformed", e);
        }
    }

    /**
     * output ActionEnterDB class
     */     
    protected class ActionEnterDB extends ItemAction {     
    
        public ActionEnterDB()
        {
            this(null);
        }

        public ActionEnterDB(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEnterDB.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterDB.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterDB.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionEnterDB", "actionEnterDB_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelEnterDB class
     */     
    protected class ActionCancelEnterDB extends ItemAction {     
    
        public ActionCancelEnterDB()
        {
            this(null);
        }

        public ActionCancelEnterDB(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelEnterDB.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelEnterDB.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelEnterDB.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionCancelEnterDB", "actionCancelEnterDB_actionPerformed", e);
        }
    }

    /**
     * output ActionAssignToOrg class
     */     
    protected class ActionAssignToOrg extends ItemAction {     
    
        public ActionAssignToOrg()
        {
            this(null);
        }

        public ActionAssignToOrg(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributebatch"));
            _tempStr = resHelper.getString("ActionAssignToOrg.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToOrg.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToOrg.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountListUI.this, "ActionAssignToOrg", "actionAssignToOrg_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "CostAccountListUI");
    }




}