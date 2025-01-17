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
public abstract class AbstractLandDeveloperListUI extends com.kingdee.eas.basedata.framework.client.DataBaseSIListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLandDeveloperListUI.class);
    protected ActionEnabled actionEnabled = null;
    protected ActionDisEnabled actionDisEnabled = null;
    /**
     * output class constructor
     */
    public AbstractLandDeveloperListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLandDeveloperListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "LandDeveloperQuery");
        //actionPageSetup
        String _tempStr = null;
        actionPageSetup.setEnabled(true);
        actionPageSetup.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionPageSetup.SHORT_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.LONG_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.NAME");
        actionPageSetup.putValue(ItemAction.NAME, _tempStr);
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionExitCurrent
        actionExitCurrent.setEnabled(true);
        actionExitCurrent.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExitCurrent.SHORT_DESCRIPTION");
        actionExitCurrent.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExitCurrent.LONG_DESCRIPTION");
        actionExitCurrent.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExitCurrent.NAME");
        actionExitCurrent.putValue(ItemAction.NAME, _tempStr);
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionExitCurrent.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionHelp
        actionHelp.setEnabled(true);
        actionHelp.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionHelp.SHORT_DESCRIPTION");
        actionHelp.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.LONG_DESCRIPTION");
        actionHelp.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.NAME");
        actionHelp.putValue(ItemAction.NAME, _tempStr);
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAbout
        actionAbout.setEnabled(true);
        actionAbout.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAbout.SHORT_DESCRIPTION");
        actionAbout.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbout.LONG_DESCRIPTION");
        actionAbout.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbout.NAME");
        actionAbout.putValue(ItemAction.NAME, _tempStr);
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAbout.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionOnLoad
        actionOnLoad.setEnabled(true);
        actionOnLoad.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionOnLoad.SHORT_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.LONG_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.NAME");
        actionOnLoad.putValue(ItemAction.NAME, _tempStr);
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSendMessage
        actionSendMessage.setEnabled(true);
        actionSendMessage.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionSendMessage.SHORT_DESCRIPTION");
        actionSendMessage.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSendMessage.LONG_DESCRIPTION");
        actionSendMessage.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSendMessage.NAME");
        actionSendMessage.putValue(ItemAction.NAME, _tempStr);
         this.actionSendMessage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionView
        actionView.setEnabled(true);
        actionView.setDaemonRun(false);

        actionView.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
        _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
        actionView.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
        actionView.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.NAME");
        actionView.putValue(ItemAction.NAME, _tempStr);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEdit
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRefresh
        actionRefresh.setEnabled(true);
        actionRefresh.setDaemonRun(false);

        actionRefresh.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
        actionRefresh.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
        actionRefresh.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.NAME");
        actionRefresh.putValue(ItemAction.NAME, _tempStr);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionLocate
        actionLocate.setEnabled(true);
        actionLocate.setDaemonRun(false);

        actionLocate.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
        _tempStr = resHelper.getString("ActionLocate.SHORT_DESCRIPTION");
        actionLocate.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLocate.LONG_DESCRIPTION");
        actionLocate.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionLocate.NAME");
        actionLocate.putValue(ItemAction.NAME, _tempStr);
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionLocate.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionQuery
        actionQuery.setEnabled(true);
        actionQuery.setDaemonRun(false);

        actionQuery.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F"));
        _tempStr = resHelper.getString("ActionQuery.SHORT_DESCRIPTION");
        actionQuery.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.LONG_DESCRIPTION");
        actionQuery.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.NAME");
        actionQuery.putValue(ItemAction.NAME, _tempStr);
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionImportData
        actionImportData.setEnabled(true);
        actionImportData.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionImportData.SHORT_DESCRIPTION");
        actionImportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.LONG_DESCRIPTION");
        actionImportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.NAME");
        actionImportData.putValue(ItemAction.NAME, _tempStr);
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportData
        actionExportData.setEnabled(true);
        actionExportData.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExportData.SHORT_DESCRIPTION");
        actionExportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.LONG_DESCRIPTION");
        actionExportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.NAME");
        actionExportData.putValue(ItemAction.NAME, _tempStr);
         this.actionExportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionToExcel
        actionToExcel.setEnabled(true);
        actionToExcel.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionToExcel.SHORT_DESCRIPTION");
        actionToExcel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionToExcel.LONG_DESCRIPTION");
        actionToExcel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionToExcel.NAME");
        actionToExcel.putValue(ItemAction.NAME, _tempStr);
         this.actionToExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStartWorkFlow
        actionStartWorkFlow.setEnabled(true);
        actionStartWorkFlow.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionStartWorkFlow.SHORT_DESCRIPTION");
        actionStartWorkFlow.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionStartWorkFlow.LONG_DESCRIPTION");
        actionStartWorkFlow.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionStartWorkFlow.NAME");
        actionStartWorkFlow.putValue(ItemAction.NAME, _tempStr);
         this.actionStartWorkFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPublishReport
        actionPublishReport.setEnabled(true);
        actionPublishReport.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionPublishReport.SHORT_DESCRIPTION");
        actionPublishReport.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPublishReport.LONG_DESCRIPTION");
        actionPublishReport.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPublishReport.NAME");
        actionPublishReport.putValue(ItemAction.NAME, _tempStr);
         this.actionPublishReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        actionCancel.setEnabled(true);
        actionCancel.setDaemonRun(false);

        actionCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F8"));
        _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
        actionCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
        actionCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.NAME");
        actionCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelCancel
        actionCancelCancel.setEnabled(true);
        actionCancelCancel.setDaemonRun(false);

        actionCancelCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F9"));
        _tempStr = resHelper.getString("ActionCancelCancel.SHORT_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.LONG_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.NAME");
        actionCancelCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnabled
        this.actionEnabled = new ActionEnabled(this);
        getActionManager().registerAction("actionEnabled", actionEnabled);
         this.actionEnabled.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisEnabled
        this.actionDisEnabled = new ActionDisEnabled(this);
        getActionManager().registerAction("actionDisEnabled", actionDisEnabled);
         this.actionDisEnabled.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        // CoreUI
        this.btnPageSetup.setAction((IItemAction)ActionProxyFactory.getProxy(actionPageSetup, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPageSetup.setText(resHelper.getString("btnPageSetup.text"));		
        this.btnPageSetup.setToolTipText(resHelper.getString("btnPageSetup.toolTipText"));		
        this.btnPageSetup.setVisible(false);		
        this.menuFile.setText(resHelper.getString("menuFile.text"));		
        this.menuFile.setToolTipText(resHelper.getString("menuFile.toolTipText"));		
        this.menuFile.setMnemonic(70);
        this.menuItemPageSetup.setAction((IItemAction)ActionProxyFactory.getProxy(actionPageSetup, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPageSetup.setText(resHelper.getString("menuItemPageSetup.text"));		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemPageSetup.setToolTipText(resHelper.getString("menuItemPageSetup.toolTipText"));
        this.menuItemExitCurrent.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExitCurrent.setText(resHelper.getString("menuItemExitCurrent.text"));		
        this.menuItemExitCurrent.setToolTipText(resHelper.getString("menuItemExitCurrent.toolTipText"));		
        this.menuTool.setText(resHelper.getString("menuTool.text"));		
        this.menuTool.setToolTipText(resHelper.getString("menuTool.toolTipText"));		
        this.menuTool.setMnemonic(84);
        this.menuItemSendMessage.setAction((IItemAction)ActionProxyFactory.getProxy(actionSendMessage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSendMessage.setText(resHelper.getString("menuItemSendMessage.text"));		
        this.menuItemSendMessage.setToolTipText(resHelper.getString("menuItemSendMessage.toolTipText"));		
        this.menuHelp.setText(resHelper.getString("menuHelp.text"));		
        this.menuHelp.setMnemonic(72);
        this.menuItemHelp.setAction((IItemAction)ActionProxyFactory.getProxy(actionHelp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHelp.setText(resHelper.getString("menuItemHelp.text"));		
        this.menuItemHelp.setToolTipText(resHelper.getString("menuItemHelp.toolTipText"));
        this.menuItemAbout.setAction((IItemAction)ActionProxyFactory.getProxy(actionAbout, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAbout.setText(resHelper.getString("menuItemAbout.text"));		
        this.menuItemAbout.setToolTipText(resHelper.getString("menuItemAbout.toolTipText"));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblMain_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","isEnabled","description","id","CU.id"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        this.btnAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));		
        this.btnAddNew.setToolTipText(resHelper.getString("btnAddNew.toolTipText"));
        this.btnView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnView.setText(resHelper.getString("btnView.text"));		
        this.btnView.setToolTipText(resHelper.getString("btnView.toolTipText"));
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));
        this.btnRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemove.setText(resHelper.getString("btnRemove.text"));		
        this.btnRemove.setToolTipText(resHelper.getString("btnRemove.toolTipText"));
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        this.btnLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLocate.setText(resHelper.getString("btnLocate.text"));		
        this.btnLocate.setToolTipText(resHelper.getString("btnLocate.toolTipText"));
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));		
        this.btnQuery.setToolTipText(resHelper.getString("btnQuery.toolTipText"));
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));		
        this.btnPrint.setToolTipText(resHelper.getString("btnPrint.toolTipText"));
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));		
        this.btnPrintPreview.setToolTipText(resHelper.getString("btnPrintPreview.toolTipText"));
        this.menuItemAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddNew.setText(resHelper.getString("menuItemAddNew.text"));		
        this.menuItemAddNew.setToolTipText(resHelper.getString("menuItemAddNew.toolTipText"));
        this.menuItemImportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setVisible(false);
        this.menuItemPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setToolTipText(resHelper.getString("menuItemPrint.toolTipText"));
        this.menuItemPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintPreview.setText(resHelper.getString("menuItemPrintPreview.text"));		
        this.menuItemPrintPreview.setToolTipText(resHelper.getString("menuItemPrintPreview.toolTipText"));		
        this.menuEdit.setText(resHelper.getString("menuEdit.text"));		
        this.menuEdit.setMnemonic(69);
        this.menuItemEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));
        this.menuItemRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRemove.setText(resHelper.getString("menuItemRemove.text"));		
        this.menuItemRemove.setToolTipText(resHelper.getString("menuItemRemove.toolTipText"));		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.menuView.setMnemonic(86);
        this.menuItemView.setAction((IItemAction)ActionProxyFactory.getProxy(actionView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemView.setText(resHelper.getString("menuItemView.text"));		
        this.menuItemView.setToolTipText(resHelper.getString("menuItemView.toolTipText"));
        this.menuItemLocate.setAction((IItemAction)ActionProxyFactory.getProxy(actionLocate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLocate.setText(resHelper.getString("menuItemLocate.text"));		
        this.menuItemLocate.setToolTipText(resHelper.getString("menuItemLocate.toolTipText"));
        this.menuItemQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuery.setText(resHelper.getString("menuItemQuery.text"));		
        this.menuItemQuery.setToolTipText(resHelper.getString("menuItemQuery.toolTipText"));
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setToolTipText(resHelper.getString("menuItemRefresh.toolTipText"));
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));
        this.menuItemExportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportData.setText(resHelper.getString("menuItemExportData.text"));		
        this.menuItemExportData.setToolTipText(resHelper.getString("menuItemExportData.toolTipText"));		
        this.menuItemExportData.setVisible(false);		
        this.menuTools.setText(resHelper.getString("menuTools.text"));		
        this.menuTools.setToolTipText(resHelper.getString("menuTools.toolTipText"));		
        this.menuTools.setEnabled(false);		
        this.menuTools.setVisible(false);
        this.menuItemStartWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionStartWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemStartWorkFlow.setText(resHelper.getString("menuItemStartWorkFlow.text"));		
        this.menuItemStartWorkFlow.setToolTipText(resHelper.getString("menuItemStartWorkFlow.toolTipText"));
        this.menuItemPublishReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionPublishReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPublishReport.setText(resHelper.getString("menuItemPublishReport.text"));		
        this.menuItemPublishReport.setToolTipText(resHelper.getString("menuItemPublishReport.toolTipText"));		
        this.menuMail.setText(resHelper.getString("menuMail.text"));		
        this.menuMail.setToolTipText(resHelper.getString("menuMail.toolTipText"));
        this.menuItemToExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionToExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemToExcel.setText(resHelper.getString("menuItemToExcel.text"));		
        this.menuItemToExcel.setToolTipText(resHelper.getString("menuItemToExcel.toolTipText"));		
        this.menuItemToHTML.setText(resHelper.getString("menuItemToHTML.text"));		
        this.menuItemToHTML.setToolTipText(resHelper.getString("menuItemToHTML.toolTipText"));		
        this.menuItemToHTML.setEnabled(false);		
        this.menuItemCopyScreen.setText(resHelper.getString("menuItemCopyScreen.text"));		
        this.menuItemCopyScreen.setToolTipText(resHelper.getString("menuItemCopyScreen.toolTipText"));		
        this.menuItemCopyScreen.setEnabled(false);
        this.MenuItemAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setVisible(true);
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
        tblMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(MenuItemAttachment);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(separatorView1);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
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
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnLocate);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.LandDeveloperListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

	public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			return sic;
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("CU.id"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        sorterFieldList.add("number"); 
        return sorterFieldList; 
    } 
    protected java.util.List getQueryPKFields() 
    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        return pkList;
    }
    	

    /**
     * output actionPageSetup_actionPerformed method
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }
    	

    /**
     * output actionExitCurrent_actionPerformed method
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }
    	

    /**
     * output actionHelp_actionPerformed method
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }
    	

    /**
     * output actionAbout_actionPerformed method
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }
    	

    /**
     * output actionOnLoad_actionPerformed method
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }
    	

    /**
     * output actionSendMessage_actionPerformed method
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionLocate_actionPerformed method
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }
    	

    /**
     * output actionQuery_actionPerformed method
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
    	

    /**
     * output actionImportData_actionPerformed method
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionExportData_actionPerformed method
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }
    	

    /**
     * output actionToExcel_actionPerformed method
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }
    	

    /**
     * output actionStartWorkFlow_actionPerformed method
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }
    	

    /**
     * output actionPublishReport_actionPerformed method
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
         //write your code here
    }
    	

    /**
     * output actionCancelCancel_actionPerformed method
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
         //write your code here
    }
    	

    /**
     * output actionEnabled_actionPerformed method
     */
    public void actionEnabled_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisEnabled_actionPerformed method
     */
    public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPageSetup(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPageSetup(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPageSetup() {
    	return false;
    }
	public RequestContext prepareActionExitCurrent(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExitCurrent(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExitCurrent() {
    	return false;
    }
	public RequestContext prepareActionHelp(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionHelp(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHelp() {
    	return false;
    }
	public RequestContext prepareActionAbout(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAbout(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAbout() {
    	return false;
    }
	public RequestContext prepareActionOnLoad(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionOnLoad(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOnLoad() {
    	return false;
    }
	public RequestContext prepareActionSendMessage(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSendMessage(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSendMessage() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionView(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
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
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRefresh(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionLocate(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionLocate(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLocate() {
    	return false;
    }
	public RequestContext prepareActionQuery(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionQuery(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuery() {
    	return false;
    }
	public RequestContext prepareActionImportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionImportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportData() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionExportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportData() {
    	return false;
    }
	public RequestContext prepareActionToExcel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionToExcel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToExcel() {
    	return false;
    }
	public RequestContext prepareActionStartWorkFlow(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionStartWorkFlow(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStartWorkFlow() {
    	return false;
    }
	public RequestContext prepareActionPublishReport(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPublishReport(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPublishReport() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionCancelCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancelCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelCancel() {
    	return false;
    }
	public RequestContext prepareActionEnabled(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEnabled() {
    	return false;
    }
	public RequestContext prepareActionDisEnabled(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisEnabled() {
    	return false;
    }

    /**
     * output ActionEnabled class
     */     
    protected class ActionEnabled extends ItemAction {     
    
        public ActionEnabled()
        {
            this(null);
        }

        public ActionEnabled(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEnabled.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnabled.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnabled.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLandDeveloperListUI.this, "ActionEnabled", "actionEnabled_actionPerformed", e);
        }
    }

    /**
     * output ActionDisEnabled class
     */     
    protected class ActionDisEnabled extends ItemAction {     
    
        public ActionDisEnabled()
        {
            this(null);
        }

        public ActionDisEnabled(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDisEnabled.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisEnabled.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisEnabled.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLandDeveloperListUI.this, "ActionDisEnabled", "actionDisEnabled_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "LandDeveloperListUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.basedata.client.LandDeveloperEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basedata.LandDeveloperFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basedata.LandDeveloperInfo objectValue = new com.kingdee.eas.fdc.basedata.LandDeveloperInfo();		
        return objectValue;
    }




}