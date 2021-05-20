/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractSHEReceivingBillListUI extends com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHEReceivingBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdateSubject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchCreateBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelatePaymentBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUpdateSubject;
    protected ActionUpdateSubject actionUpdateSubject = null;
    protected ActionCreateBill actionCreateBill = null;
    protected actionBatchCreateBill actionBatchCreateBill = null;
    protected acitonRelatePaymentBill actionRelatePaymentBill = null;
    /**
     * output class constructor
     */
    public AbstractSHEReceivingBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHEReceivingBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHEReceivingBillQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAudit
        actionAudit.setEnabled(false);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdateSubject
        this.actionUpdateSubject = new ActionUpdateSubject(this);
        getActionManager().registerAction("actionUpdateSubject", actionUpdateSubject);
         this.actionUpdateSubject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateBill
        this.actionCreateBill = new ActionCreateBill(this);
        getActionManager().registerAction("actionCreateBill", actionCreateBill);
         this.actionCreateBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchCreateBill
        this.actionBatchCreateBill = new actionBatchCreateBill(this);
        getActionManager().registerAction("actionBatchCreateBill", actionBatchCreateBill);
         this.actionBatchCreateBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelatePaymentBill
        this.actionRelatePaymentBill = new acitonRelatePaymentBill(this);
        getActionManager().registerAction("actionRelatePaymentBill", actionRelatePaymentBill);
         this.actionRelatePaymentBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnUpdateSubject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreateBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchCreateBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRelatePaymentBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuUpdateSubject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnUpdateSubject.setName("btnUpdateSubject");
        this.btnCreateBill.setName("btnCreateBill");
        this.btnBatchCreateBill.setName("btnBatchCreateBill");
        this.btnRelatePaymentBill.setName("btnRelatePaymentBill");
        this.menuUpdateSubject.setName("menuUpdateSubject");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"billStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"fiVouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"revBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"revBizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"moneyDefine.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"entries.revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"originalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"customer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"revAccount.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"revAccountBank.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"oppAccount.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"currency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"entries.bankNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"settlementType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"entries.settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"txtReceiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"receiptState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"invoiceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"moneyDefine.moneyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"room.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"roomLongNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"room.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"isCreateBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{revBillType}</t:Cell><t:Cell>$Resource{revBizType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{moneyDefine.name}</t:Cell><t:Cell>$Resource{entries.revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{customer.name}</t:Cell><t:Cell>$Resource{revAccount.name}</t:Cell><t:Cell>$Resource{revAccountBank.name}</t:Cell><t:Cell>$Resource{oppAccount.name}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{entries.bankNumber}</t:Cell><t:Cell>$Resource{settlementType.name}</t:Cell><t:Cell>$Resource{entries.settlementNumber}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell><t:Cell>$Resource{txtReceiptNumber}</t:Cell><t:Cell>$Resource{receiptState}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{invoiceAmount}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{moneyDefine.moneyType}</t:Cell><t:Cell>$Resource{room.id}</t:Cell><t:Cell>$Resource{roomLongNumber}</t:Cell><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isCreateBill}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","sellProject.name","room.displayName","billStatus","fiVouchered","revBillType","revBizType","number","moneyDefine.name","entries.revAmount","amount","originalAmount","customer.name","revAccount.name","revAccountBank.name","oppAccount.name","currency.name","entries.bankNumber","settlementType.name","entries.settlementNumber","receipt.number","receiptNumber","receiptState","invoice.number","invoice.amount","bizDate","moneyDefine.moneyType","room.id","","","state","createTime","description",""});


        // btnUpdateSubject
        this.btnUpdateSubject.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateSubject.setText(resHelper.getString("btnUpdateSubject.text"));		
        this.btnUpdateSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_subjectsetting"));
        // btnCreateBill
        this.btnCreateBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateBill.setText(resHelper.getString("btnCreateBill.text"));		
        this.btnCreateBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_notice"));
        // btnBatchCreateBill
        this.btnBatchCreateBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchCreateBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchCreateBill.setText(resHelper.getString("btnBatchCreateBill.text"));
        // btnRelatePaymentBill
        this.btnRelatePaymentBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelatePaymentBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelatePaymentBill.setText(resHelper.getString("btnRelatePaymentBill.text"));
        // menuUpdateSubject
        this.menuUpdateSubject.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUpdateSubject.setText(resHelper.getString("menuUpdateSubject.text"));		
        this.menuUpdateSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_subjectsetting"));
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
        pnlMain.setBounds(new Rectangle(8, 10, 993, 610));
        this.add(pnlMain, new KDLayout.Constraints(8, 10, 993, 610, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuAudit);
        menuBiz.add(menuReceive);
        menuBiz.add(menuBatchReceiving);
        menuBiz.add(menuUnAudit);
        menuBiz.add(menuCanceReceive);
        menuBiz.add(menuItemAdjust);
        menuBiz.add(menuReceipt);
        menuBiz.add(menuUpdateSubject);
        menuBiz.add(menuRetakeReceipt);
        menuBiz.add(menuCreateInvoice);
        menuBiz.add(menuClearInvoice);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnReceive);
        this.toolBar.add(btnBatchReceiving);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCanceReceive);
        this.toolBar.add(btnAdjust);
        this.toolBar.add(btnReceipt);
        this.toolBar.add(btnUpdateSubject);
        this.toolBar.add(btnRetakeReceipt);
        this.toolBar.add(btnCreateInvoice);
        this.toolBar.add(btnClearInvoice);
        this.toolBar.add(btnCreateBill);
        this.toolBar.add(btnBatchCreateBill);
        this.toolBar.add(btnRelatePaymentBill);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("entries.revAmount"));
        sic.add(new SelectorItemInfo("entries.bankNumber"));
        sic.add(new SelectorItemInfo("entries.settlementNumber"));
        sic.add(new SelectorItemInfo("moneyDefine.name"));
        sic.add(new SelectorItemInfo("settlementType.name"));
        sic.add(new SelectorItemInfo("moneyDefine.moneyType"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("revBillType"));
        sic.add(new SelectorItemInfo("revBizType"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("buildUnit.name"));
        sic.add(new SelectorItemInfo("room.roomNo"));
        sic.add(new SelectorItemInfo("tenancyUser.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("revAccountBank.name"));
        sic.add(new SelectorItemInfo("revAccount.name"));
        sic.add(new SelectorItemInfo("oppAccount.name"));
        sic.add(new SelectorItemInfo("customer.name"));
        sic.add(new SelectorItemInfo("invoice.number"));
        sic.add(new SelectorItemInfo("receiptState"));
        sic.add(new SelectorItemInfo("invoice.amount"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("receipt.number"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        sic.add(new SelectorItemInfo("room.displayName"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUpdateSubject_actionPerformed method
     */
    public void actionUpdateSubject_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCreateBill_actionPerformed method
     */
    public void actionCreateBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchCreateBill_actionPerformed method
     */
    public void actionBatchCreateBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output acitonRelatePaymentBill_actionPerformed method
     */
    public void acitonRelatePaymentBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUpdateSubject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateSubject() {
    	return false;
    }
	public RequestContext prepareActionCreateBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateBill() {
    	return false;
    }
	public RequestContext prepareactionBatchCreateBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionBatchCreateBill() {
    	return false;
    }
	public RequestContext prepareacitonRelatePaymentBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareacitonRelatePaymentBill() {
    	return false;
    }

    /**
     * output ActionUpdateSubject class
     */     
    protected class ActionUpdateSubject extends ItemAction {     
    
        public ActionUpdateSubject()
        {
            this(null);
        }

        public ActionUpdateSubject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpdateSubject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateSubject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateSubject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEReceivingBillListUI.this, "ActionUpdateSubject", "actionUpdateSubject_actionPerformed", e);
        }
    }

    /**
     * output ActionCreateBill class
     */     
    protected class ActionCreateBill extends ItemAction {     
    
        public ActionCreateBill()
        {
            this(null);
        }

        public ActionCreateBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCreateBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEReceivingBillListUI.this, "ActionCreateBill", "actionCreateBill_actionPerformed", e);
        }
    }

    /**
     * output actionBatchCreateBill class
     */     
    protected class actionBatchCreateBill extends ItemAction {     
    
        public actionBatchCreateBill()
        {
            this(null);
        }

        public actionBatchCreateBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionBatchCreateBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBatchCreateBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBatchCreateBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEReceivingBillListUI.this, "actionBatchCreateBill", "actionBatchCreateBill_actionPerformed", e);
        }
    }

    /**
     * output acitonRelatePaymentBill class
     */     
    protected class acitonRelatePaymentBill extends ItemAction {     
    
        public acitonRelatePaymentBill()
        {
            this(null);
        }

        public acitonRelatePaymentBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("acitonRelatePaymentBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("acitonRelatePaymentBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("acitonRelatePaymentBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEReceivingBillListUI.this, "acitonRelatePaymentBill", "acitonRelatePaymentBill_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SHEReceivingBillListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}