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
public abstract class AbstractMarketMonthProjectEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketMonthProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.eas.fdc.contract.MarketMonthProjectInfo editData = null;
    protected ActionALine actionALine = null;
    protected ActionRLine actionRLine = null;
    protected ActionACLine actionACLine = null;
    protected ActionRCLine actionRCLine = null;
    protected ActionAULine actionAULine = null;
    protected ActionRULine actionRULine = null;
    /**
     * output class constructor
     */
    public AbstractMarketMonthProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketMonthProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionALine
        this.actionALine = new ActionALine(this);
        getActionManager().registerAction("actionALine", actionALine);
         this.actionALine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRLine
        this.actionRLine = new ActionRLine(this);
        getActionManager().registerAction("actionRLine", actionRLine);
         this.actionRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionACLine
        this.actionACLine = new ActionACLine(this);
        getActionManager().registerAction("actionACLine", actionACLine);
         this.actionACLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRCLine
        this.actionRCLine = new ActionRCLine(this);
        getActionManager().registerAction("actionRCLine", actionRCLine);
         this.actionRCLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAULine
        this.actionAULine = new ActionAULine(this);
        getActionManager().registerAction("actionAULine", actionAULine);
         this.actionAULine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRULine
        this.actionRULine = new ActionRULine(this);
        getActionManager().registerAction("actionRULine", actionRULine);
         this.actionRULine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contOrgUnit.setName("contOrgUnit");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contYear.setName("contYear");
        this.kdtEntry.setName("kdtEntry");
        this.contMonth.setName("contMonth");
        this.contSellProject.setName("contSellProject");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtDescription.setName("txtDescription");
        this.spYear.setName("spYear");
        this.spMonth.setName("spMonth");
        this.txtSellProject.setName("txtSellProject");
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
        this.btnCancelCancel.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.kDSeparator2.setVisible(false);		
        this.menuItemPrint.setVisible(true);		
        this.menuItemPrintPreview.setVisible(true);		
        this.kDSeparator4.setVisible(false);		
        this.kDSeparator4.setEnabled(false);		
        this.rMenuItemSubmit.setVisible(false);		
        this.rMenuItemSubmit.setEnabled(false);		
        this.rMenuItemSubmitAndAddNew.setVisible(false);		
        this.rMenuItemSubmitAndAddNew.setEnabled(false);		
        this.rMenuItemSubmitAndPrint.setVisible(false);		
        this.rMenuItemSubmitAndPrint.setEnabled(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.btnReset.setEnabled(false);		
        this.btnReset.setVisible(false);		
        this.menuItemReset.setEnabled(false);		
        this.menuItemReset.setVisible(false);		
        this.btnSignature.setVisible(false);		
        this.btnSignature.setEnabled(false);		
        this.btnViewSignature.setEnabled(false);		
        this.btnViewSignature.setVisible(false);		
        this.separatorFW4.setVisible(false);		
        this.separatorFW4.setEnabled(false);		
        this.btnNumberSign.setEnabled(false);		
        this.btnNumberSign.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
        this.btnCopyFrom.setEnabled(false);		
        this.btnCreateTo.setVisible(false);		
        this.separatorFW5.setVisible(false);		
        this.separatorFW5.setEnabled(false);		
        this.btnCopyLine.setVisible(false);		
        this.separatorFW6.setVisible(false);		
        this.separatorFW6.setEnabled(false);		
        this.btnVoucher.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnWFViewdoProccess.setEnabled(false);		
        this.btnWFViewdoProccess.setVisible(false);		
        this.btnWFViewSubmitProccess.setEnabled(false);		
        this.btnWFViewSubmitProccess.setVisible(false);		
        this.menuItemCreateTo.setVisible(false);		
        this.separatorEdit1.setVisible(false);		
        this.menuItemEnterToNextRow.setVisible(false);		
        this.separator2.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.MenuItemVoucher.setVisible(false);		
        this.menuItemDelVoucher.setVisible(false);		
        this.menuItemStartWorkFlow.setVisible(false);		
        this.separatorWF1.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewSubmitProccess.setEnabled(false);		
        this.menuItemViewDoProccess.setEnabled(false);		
        this.menuItemViewDoProccess.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));
        // kDScrollPane1
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(100);		
        this.contYear.setBoundLabelUnderline(true);
        // kdtEntry
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contMonth		
        this.contMonth.setBoundLabelText(resHelper.getString("contMonth.boundLabelText"));		
        this.contMonth.setBoundLabelLength(100);		
        this.contMonth.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // txtNumber
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // txtDescription		
        this.txtDescription.setText(resHelper.getString("txtDescription.text"));		
        this.txtDescription.setRequired(true);
        // spYear
        this.spYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spYear_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spMonth
        this.spMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spMonth_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSellProject		
        this.txtSellProject.setRequired(true);
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
        contNumber.setBounds(new Rectangle(30, 18, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(30, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(376, 18, 270, 19));
        this.add(contName, new KDLayout.Constraints(376, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(704, 18, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(704, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(30, 66, 73, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(30, 66, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(30, 86, 947, 65));
        this.add(kDScrollPane1, new KDLayout.Constraints(30, 86, 947, 65, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contYear.setBounds(new Rectangle(30, 41, 270, 19));
        this.add(contYear, new KDLayout.Constraints(30, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntry.setBounds(new Rectangle(31, 168, 947, 440));
        this.add(kdtEntry, new KDLayout.Constraints(31, 168, 947, 440, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contMonth.setBounds(new Rectangle(376, 41, 270, 19));
        this.add(contMonth, new KDLayout.Constraints(376, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(704, 41, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(704, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contYear
        contYear.setBoundEditor(spYear);
        //contMonth
        contMonth.setBoundEditor(spMonth);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("year", int.class, this.spYear, "value");
		dataBinder.registerBinding("month", int.class, this.spMonth, "value");
		dataBinder.registerBinding("sellProjecttxt", String.class, this.txtSellProject, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.MarketMonthProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.MarketMonthProjectInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProjecttxt", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output spYear_stateChanged method
     */
    protected void spYear_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spMonth_stateChanged method
     */
    protected void spMonth_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("month"));
        sic.add(new SelectorItemInfo("sellProjecttxt"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionALine_actionPerformed method
     */
    public void actionALine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRLine_actionPerformed method
     */
    public void actionRLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionACLine_actionPerformed method
     */
    public void actionACLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRCLine_actionPerformed method
     */
    public void actionRCLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAULine_actionPerformed method
     */
    public void actionAULine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRULine_actionPerformed method
     */
    public void actionRULine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionALine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionALine() {
    	return false;
    }
	public RequestContext prepareActionRLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRLine() {
    	return false;
    }
	public RequestContext prepareActionACLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionACLine() {
    	return false;
    }
	public RequestContext prepareActionRCLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRCLine() {
    	return false;
    }
	public RequestContext prepareActionAULine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAULine() {
    	return false;
    }
	public RequestContext prepareActionRULine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRULine() {
    	return false;
    }

    /**
     * output ActionALine class
     */     
    protected class ActionALine extends ItemAction {     
    
        public ActionALine()
        {
            this(null);
        }

        public ActionALine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionALine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionALine", "actionALine_actionPerformed", e);
        }
    }

    /**
     * output ActionRLine class
     */     
    protected class ActionRLine extends ItemAction {     
    
        public ActionRLine()
        {
            this(null);
        }

        public ActionRLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionRLine", "actionRLine_actionPerformed", e);
        }
    }

    /**
     * output ActionACLine class
     */     
    protected class ActionACLine extends ItemAction {     
    
        public ActionACLine()
        {
            this(null);
        }

        public ActionACLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionACLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionACLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionACLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionACLine", "actionACLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRCLine class
     */     
    protected class ActionRCLine extends ItemAction {     
    
        public ActionRCLine()
        {
            this(null);
        }

        public ActionRCLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRCLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRCLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRCLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionRCLine", "actionRCLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAULine class
     */     
    protected class ActionAULine extends ItemAction {     
    
        public ActionAULine()
        {
            this(null);
        }

        public ActionAULine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAULine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAULine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAULine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionAULine", "actionAULine_actionPerformed", e);
        }
    }

    /**
     * output ActionRULine class
     */     
    protected class ActionRULine extends ItemAction {     
    
        public ActionRULine()
        {
            this(null);
        }

        public ActionRULine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRULine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRULine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRULine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketMonthProjectEditUI.this, "ActionRULine", "actionRULine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "MarketMonthProjectEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}