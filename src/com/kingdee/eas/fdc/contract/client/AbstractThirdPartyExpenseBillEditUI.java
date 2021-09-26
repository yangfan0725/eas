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
public abstract class AbstractThirdPartyExpenseBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractThirdPartyExpenseBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalcMgrBonus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTotalInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer contManager;
    protected com.kingdee.bos.ctrl.swing.KDContainer contHand;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTotal;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMgrBonus;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblHand;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo editData = null;
    protected ActionCalcMgrBonus actionCalcMgrBonus = null;
    protected ActionAddMgrBonus actionAddMgrBonus = null;
    protected ActionRemoveMgrBonus actionRemoveMgrBonus = null;
    protected ActionAddHand actionAddHand = null;
    protected ActionRemoveHand actionRemoveHand = null;
    /**
     * output class constructor
     */
    public AbstractThirdPartyExpenseBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractThirdPartyExpenseBillEditUI.class.getName());
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
        //actionCalcMgrBonus
        this.actionCalcMgrBonus = new ActionCalcMgrBonus(this);
        getActionManager().registerAction("actionCalcMgrBonus", actionCalcMgrBonus);
         this.actionCalcMgrBonus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddMgrBonus
        this.actionAddMgrBonus = new ActionAddMgrBonus(this);
        getActionManager().registerAction("actionAddMgrBonus", actionAddMgrBonus);
         this.actionAddMgrBonus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveMgrBonus
        this.actionRemoveMgrBonus = new ActionRemoveMgrBonus(this);
        getActionManager().registerAction("actionRemoveMgrBonus", actionRemoveMgrBonus);
         this.actionRemoveMgrBonus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddHand
        this.actionAddHand = new ActionAddHand(this);
        getActionManager().registerAction("actionAddHand", actionAddHand);
         this.actionAddHand.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveHand
        this.actionRemoveHand = new ActionRemoveHand(this);
        getActionManager().registerAction("actionRemoveHand", actionRemoveHand);
         this.actionRemoveHand.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnCalcMgrBonus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTotalInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contManager = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contHand = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTotal = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblMgrBonus = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblHand = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.btnCalcMgrBonus.setName("btnCalcMgrBonus");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contSellProject.setName("contSellProject");
        this.contBizDate.setName("contBizDate");
        this.prmtCreator.setName("prmtCreator");
        this.kdpCreateTime.setName("kdpCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.contTotalInfo.setName("contTotalInfo");
        this.contManager.setName("contManager");
        this.contHand.setName("contHand");
        this.tblTotal.setName("tblTotal");
        this.tblMgrBonus.setName("tblMgrBonus");
        this.tblHand.setName("tblHand");
        this.txtDescription.setName("txtDescription");
        this.prmtSellProject.setName("prmtSellProject");
        this.pkBizDate.setName("pkBizDate");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
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
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // kDSeparator8
        // kDTabbedPane1
        // btnCalcMgrBonus
        this.btnCalcMgrBonus.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalcMgrBonus, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalcMgrBonus.setText(resHelper.getString("btnCalcMgrBonus.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));
        // kDScrollPane1
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(100);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kdpCreateTime		
        this.kdpCreateTime.setEnabled(false);
        // txtNumber
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtOrgUnit
        // txtName
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // contTotalInfo
        // contManager
        // contHand
        // tblTotal
		String tblTotalStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"recommendType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"sqjl\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{recommendType}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{sqjl}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblTotal.setFormatXml(resHelper.translateString("tblTotal",tblTotalStrXML));
        this.tblTotal.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblTotal_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblMgrBonus
		String tblMgrBonusStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Protection locked=\"true\" /><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"purDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"dealTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"saleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"recommended\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"qdPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"nowSaleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"recommendType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"11\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"jtType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"13\" /><t:Column t:key=\"dsxs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"tsxs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"jl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"xz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"sqjl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"signManageId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{purDate}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{dealTotalAmount}</t:Cell><t:Cell>$Resource{saleMan}</t:Cell><t:Cell>$Resource{recommended}</t:Cell><t:Cell>$Resource{qdPerson}</t:Cell><t:Cell>$Resource{nowSaleMan}</t:Cell><t:Cell>$Resource{recommendType}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{jtType}</t:Cell><t:Cell>$Resource{dsxs}</t:Cell><t:Cell>$Resource{tsxs}</t:Cell><t:Cell>$Resource{jl}</t:Cell><t:Cell>$Resource{xz}</t:Cell><t:Cell>$Resource{sqjl}</t:Cell><t:Cell>$Resource{signManageId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMgrBonus.setFormatXml(resHelper.translateString("tblMgrBonus",tblMgrBonusStrXML));
        this.tblMgrBonus.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMgrBonus_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblMgrBonus.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMgrBonus_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblMgrBonus.putBindContents("editData",new String[] {"bizType","room","customer","purDate","signDate","buildingArea","dealTotalAmount","saleMan","recommended","qdPerson","nowSaleMan","recommendType","costAccount","jtType","dsxs","tsxs","jl","xz","sqjl","signManageId"});


        // tblHand
		String tblHandStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"purDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol2\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"4\" t:styleID=\"sCol3\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol4\" /><t:Column t:key=\"dealTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol5\" /><t:Column t:key=\"saleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"7\" /><t:Column t:key=\"recommended\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"8\" /><t:Column t:key=\"qdPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"9\" /><t:Column t:key=\"nowSaleMan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"10\" /><t:Column t:key=\"recommendType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"true\" t:index=\"11\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol11\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"sqjl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"18\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{purDate}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{dealTotalAmount}</t:Cell><t:Cell>$Resource{saleMan}</t:Cell><t:Cell>$Resource{recommended}</t:Cell><t:Cell>$Resource{qdPerson}</t:Cell><t:Cell>$Resource{nowSaleMan}</t:Cell><t:Cell>$Resource{recommendType}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{sqjl}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblHand.setFormatXml(resHelper.translateString("tblHand",tblHandStrXML));
        this.tblHand.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblHand_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblHand.putBindContents("editData",new String[] {"room","customer","purDate","signDate","buildingArea","dealTotalAmount","saleMan","recommended","qdPerson","nowSaleMan","recommendType","costAccount","remark","sqjl"});


        // txtDescription
        // prmtSellProject
        // pkBizDate
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
        contCreator.setBounds(new Rectangle(12, 568, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(12, 568, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(12, 594, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(12, 594, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(12, 15, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(730, 568, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(730, 568, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(687, 37, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(687, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(359, 15, 270, 19));
        this.add(contName, new KDLayout.Constraints(359, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(728, 594, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(728, 594, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(-4, 555, 1019, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(-4, 555, 1019, 10, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(12, 187, 999, 353));
        this.add(kDTabbedPane1, new KDLayout.Constraints(12, 187, 999, 353, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnCalcMgrBonus.setBounds(new Rectangle(814, 152, 147, 30));
        this.add(btnCalcMgrBonus, new KDLayout.Constraints(814, 152, 147, 30, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(13, 60, 73, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(13, 60, 73, 19, 0));
        kDScrollPane1.setBounds(new Rectangle(14, 80, 947, 65));
        this.add(kDScrollPane1, new KDLayout.Constraints(14, 80, 947, 65, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(687, 15, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(687, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(12, 37, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(12, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kdpCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(contTotalInfo, resHelper.getString("contTotalInfo.constraints"));
        kDTabbedPane1.add(contManager, resHelper.getString("contManager.constraints"));
        kDTabbedPane1.add(contHand, resHelper.getString("contHand.constraints"));
        //contTotalInfo
contTotalInfo.getContentPane().setLayout(new BorderLayout(0, 0));        contTotalInfo.getContentPane().add(tblTotal, BorderLayout.CENTER);
        //contManager
contManager.getContentPane().setLayout(new BorderLayout(0, 0));        contManager.getContentPane().add(tblMgrBonus, BorderLayout.CENTER);
        //contHand
contHand.getContentPane().setLayout(new BorderLayout(0, 0));        contHand.getContentPane().add(tblHand, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kdpCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryInfo.class, this.tblMgrBonus, "userObject");
		dataBinder.registerBinding("entry.bizType", com.kingdee.eas.fdc.sellhouse.TransactionStateEnum.class, this.tblMgrBonus, "bizType.text");
		dataBinder.registerBinding("entry.room", String.class, this.tblMgrBonus, "room.text");
		dataBinder.registerBinding("entry.customer", String.class, this.tblMgrBonus, "customer.text");
		dataBinder.registerBinding("entry.purDate", java.util.Date.class, this.tblMgrBonus, "purDate.text");
		dataBinder.registerBinding("entry.signDate", java.util.Date.class, this.tblMgrBonus, "signDate.text");
		dataBinder.registerBinding("entry.buildingArea", java.math.BigDecimal.class, this.tblMgrBonus, "buildingArea.text");
		dataBinder.registerBinding("entry.dealTotalAmount", java.math.BigDecimal.class, this.tblMgrBonus, "dealTotalAmount.text");
		dataBinder.registerBinding("entry.saleMan", String.class, this.tblMgrBonus, "saleMan.text");
		dataBinder.registerBinding("entry.recommended", String.class, this.tblMgrBonus, "recommended.text");
		dataBinder.registerBinding("entry.qdPerson", String.class, this.tblMgrBonus, "qdPerson.text");
		dataBinder.registerBinding("entry.nowSaleMan", String.class, this.tblMgrBonus, "nowSaleMan.text");
		dataBinder.registerBinding("entry.recommendType", com.kingdee.eas.fdc.contract.RecommendTypeInfo.class, this.tblMgrBonus, "recommendType.text");
		dataBinder.registerBinding("entry.costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.tblMgrBonus, "costAccount.text");
		dataBinder.registerBinding("entry.jtType", com.kingdee.eas.fdc.contract.JTTypeEnum.class, this.tblMgrBonus, "jtType.text");
		dataBinder.registerBinding("entry.dsxs", java.math.BigDecimal.class, this.tblMgrBonus, "dsxs.text");
		dataBinder.registerBinding("entry.tsxs", java.math.BigDecimal.class, this.tblMgrBonus, "tsxs.text");
		dataBinder.registerBinding("entry.jl", java.math.BigDecimal.class, this.tblMgrBonus, "jl.text");
		dataBinder.registerBinding("entry.xz", java.math.BigDecimal.class, this.tblMgrBonus, "xz.text");
		dataBinder.registerBinding("entry.sqjl", java.math.BigDecimal.class, this.tblMgrBonus, "sqjl.text");
		dataBinder.registerBinding("entry.signManageId", String.class, this.tblMgrBonus, "signManageId.text");
		dataBinder.registerBinding("handEntry", com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillHandEntryInfo.class, this.tblHand, "userObject");
		dataBinder.registerBinding("handEntry.room", String.class, this.tblHand, "room.text");
		dataBinder.registerBinding("handEntry.customer", String.class, this.tblHand, "customer.text");
		dataBinder.registerBinding("handEntry.purDate", java.util.Date.class, this.tblHand, "purDate.text");
		dataBinder.registerBinding("handEntry.signDate", java.util.Date.class, this.tblHand, "signDate.text");
		dataBinder.registerBinding("handEntry.buildingArea", java.math.BigDecimal.class, this.tblHand, "buildingArea.text");
		dataBinder.registerBinding("handEntry.dealTotalAmount", java.math.BigDecimal.class, this.tblHand, "dealTotalAmount.text");
		dataBinder.registerBinding("handEntry.saleMan", String.class, this.tblHand, "saleMan.text");
		dataBinder.registerBinding("handEntry.recommended", String.class, this.tblHand, "recommended.text");
		dataBinder.registerBinding("handEntry.qdPerson", String.class, this.tblHand, "qdPerson.text");
		dataBinder.registerBinding("handEntry.nowSaleMan", String.class, this.tblHand, "nowSaleMan.text");
		dataBinder.registerBinding("handEntry.recommendType", com.kingdee.eas.fdc.contract.RecommendTypeInfo.class, this.tblHand, "recommendType.text");
		dataBinder.registerBinding("handEntry.costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.tblHand, "costAccount.text");
		dataBinder.registerBinding("handEntry.remark", String.class, this.tblHand, "remark.text");
		dataBinder.registerBinding("handEntry.sqjl", java.math.BigDecimal.class, this.tblHand, "sqjl.text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ThirdPartyExpenseBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.purDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.buildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.dealTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.qdPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.nowSaleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recommendType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.jtType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.dsxs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.tsxs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.jl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.xz", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.sqjl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.signManageId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.purDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.buildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.dealTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.qdPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.nowSaleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.recommendType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handEntry.sqjl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    		
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
     * output tblTotal_editStopped method
     */
    protected void tblTotal_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblMgrBonus_tableClicked method
     */
    protected void tblMgrBonus_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblMgrBonus_editStopped method
     */
    protected void tblMgrBonus_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblHand_editStopped method
     */
    protected void tblHand_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.bizType"));
    	sic.add(new SelectorItemInfo("entry.room"));
    	sic.add(new SelectorItemInfo("entry.customer"));
    	sic.add(new SelectorItemInfo("entry.purDate"));
    	sic.add(new SelectorItemInfo("entry.signDate"));
    	sic.add(new SelectorItemInfo("entry.buildingArea"));
    	sic.add(new SelectorItemInfo("entry.dealTotalAmount"));
    	sic.add(new SelectorItemInfo("entry.saleMan"));
    	sic.add(new SelectorItemInfo("entry.recommended"));
    	sic.add(new SelectorItemInfo("entry.qdPerson"));
    	sic.add(new SelectorItemInfo("entry.nowSaleMan"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.recommendType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.recommendType.id"));
			sic.add(new SelectorItemInfo("entry.recommendType.name"));
        	sic.add(new SelectorItemInfo("entry.recommendType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.costAccount.id"));
			sic.add(new SelectorItemInfo("entry.costAccount.name"));
        	sic.add(new SelectorItemInfo("entry.costAccount.number"));
		}
    	sic.add(new SelectorItemInfo("entry.jtType"));
    	sic.add(new SelectorItemInfo("entry.dsxs"));
    	sic.add(new SelectorItemInfo("entry.tsxs"));
    	sic.add(new SelectorItemInfo("entry.jl"));
    	sic.add(new SelectorItemInfo("entry.xz"));
    	sic.add(new SelectorItemInfo("entry.sqjl"));
    	sic.add(new SelectorItemInfo("entry.signManageId"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("handEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("handEntry.room"));
    	sic.add(new SelectorItemInfo("handEntry.customer"));
    	sic.add(new SelectorItemInfo("handEntry.purDate"));
    	sic.add(new SelectorItemInfo("handEntry.signDate"));
    	sic.add(new SelectorItemInfo("handEntry.buildingArea"));
    	sic.add(new SelectorItemInfo("handEntry.dealTotalAmount"));
    	sic.add(new SelectorItemInfo("handEntry.saleMan"));
    	sic.add(new SelectorItemInfo("handEntry.recommended"));
    	sic.add(new SelectorItemInfo("handEntry.qdPerson"));
    	sic.add(new SelectorItemInfo("handEntry.nowSaleMan"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("handEntry.recommendType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("handEntry.recommendType.id"));
			sic.add(new SelectorItemInfo("handEntry.recommendType.name"));
        	sic.add(new SelectorItemInfo("handEntry.recommendType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("handEntry.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("handEntry.costAccount.id"));
			sic.add(new SelectorItemInfo("handEntry.costAccount.name"));
        	sic.add(new SelectorItemInfo("handEntry.costAccount.number"));
		}
    	sic.add(new SelectorItemInfo("handEntry.remark"));
    	sic.add(new SelectorItemInfo("handEntry.sqjl"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sellProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sellProject.id"));
        	sic.add(new SelectorItemInfo("sellProject.number"));
        	sic.add(new SelectorItemInfo("sellProject.name"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
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
     * output actionCalcMgrBonus_actionPerformed method
     */
    public void actionCalcMgrBonus_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddMgrBonus_actionPerformed method
     */
    public void actionAddMgrBonus_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveMgrBonus_actionPerformed method
     */
    public void actionRemoveMgrBonus_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddHand_actionPerformed method
     */
    public void actionAddHand_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveHand_actionPerformed method
     */
    public void actionRemoveHand_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCalcMgrBonus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalcMgrBonus() {
    	return false;
    }
	public RequestContext prepareActionAddMgrBonus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddMgrBonus() {
    	return false;
    }
	public RequestContext prepareActionRemoveMgrBonus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveMgrBonus() {
    	return false;
    }
	public RequestContext prepareActionAddHand(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddHand() {
    	return false;
    }
	public RequestContext prepareActionRemoveHand(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveHand() {
    	return false;
    }

    /**
     * output ActionCalcMgrBonus class
     */     
    protected class ActionCalcMgrBonus extends ItemAction {     
    
        public ActionCalcMgrBonus()
        {
            this(null);
        }

        public ActionCalcMgrBonus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCalcMgrBonus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalcMgrBonus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalcMgrBonus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractThirdPartyExpenseBillEditUI.this, "ActionCalcMgrBonus", "actionCalcMgrBonus_actionPerformed", e);
        }
    }

    /**
     * output ActionAddMgrBonus class
     */     
    protected class ActionAddMgrBonus extends ItemAction {     
    
        public ActionAddMgrBonus()
        {
            this(null);
        }

        public ActionAddMgrBonus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddMgrBonus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMgrBonus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMgrBonus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractThirdPartyExpenseBillEditUI.this, "ActionAddMgrBonus", "actionAddMgrBonus_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveMgrBonus class
     */     
    protected class ActionRemoveMgrBonus extends ItemAction {     
    
        public ActionRemoveMgrBonus()
        {
            this(null);
        }

        public ActionRemoveMgrBonus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveMgrBonus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveMgrBonus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveMgrBonus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractThirdPartyExpenseBillEditUI.this, "ActionRemoveMgrBonus", "actionRemoveMgrBonus_actionPerformed", e);
        }
    }

    /**
     * output ActionAddHand class
     */     
    protected class ActionAddHand extends ItemAction {     
    
        public ActionAddHand()
        {
            this(null);
        }

        public ActionAddHand(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddHand.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddHand.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddHand.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractThirdPartyExpenseBillEditUI.this, "ActionAddHand", "actionAddHand_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveHand class
     */     
    protected class ActionRemoveHand extends ItemAction {     
    
        public ActionRemoveHand()
        {
            this(null);
        }

        public ActionRemoveHand(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveHand.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveHand.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveHand.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractThirdPartyExpenseBillEditUI.this, "ActionRemoveHand", "actionRemoveHand_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ThirdPartyExpenseBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}