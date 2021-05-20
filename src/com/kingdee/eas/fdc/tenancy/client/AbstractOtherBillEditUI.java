/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractOtherBillEditUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLeaseTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinLeaseTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton adjustTaxRate;
    protected com.kingdee.eas.fdc.tenancy.OtherBillInfo editData = null;
    protected ActionAdjustTaxRate actionAdjustTaxRate = null;
    /**
     * output class constructor
     */
    public AbstractOtherBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOtherBillEditUI.class.getName());
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
        //actionAdjustTaxRate
        this.actionAdjustTaxRate = new ActionAdjustTaxRate(this);
        getActionManager().registerAction("actionAdjustTaxRate", actionAdjustTaxRate);
         this.actionAdjustTaxRate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLeaseTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTenancy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSaleMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spinLeaseTime = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtContractNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.adjustTaxRate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCompany.setName("contCompany");
        this.contRoom.setName("contRoom");
        this.contCustomer.setName("contCustomer");
        this.contTenancy.setName("contTenancy");
        this.contSellProject.setName("contSellProject");
        this.contNumber.setName("contNumber");
        this.contSaleMan.setName("contSaleMan");
        this.panel.setName("panel");
        this.contName.setName("contName");
        this.contDescription.setName("contDescription");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditTime.setName("contAuditTime");
        this.contStartDate.setName("contStartDate");
        this.contEndDate.setName("contEndDate");
        this.contLeaseTime.setName("contLeaseTime");
        this.contContractNo.setName("contContractNo");
        this.contDept.setName("contDept");
        this.contDes.setName("contDes");
        this.txtCompany.setName("txtCompany");
        this.txtRoom.setName("txtRoom");
        this.txtCustomer.setName("txtCustomer");
        this.txtTenancy.setName("txtTenancy");
        this.txtSellProject.setName("txtSellProject");
        this.txtNumber.setName("txtNumber");
        this.txtSaleMan.setName("txtSaleMan");
        this.contEntry.setName("contEntry");
        this.tblPayList.setName("tblPayList");
        this.kdtEntry.setName("kdtEntry");
        this.txtName.setName("txtName");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.spinLeaseTime.setName("spinLeaseTime");
        this.txtContractNo.setName("txtContractNo");
        this.prmtDept.setName("prmtDept");
        this.txtDes.setName("txtDes");
        this.adjustTaxRate.setName("adjustTaxRate");
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
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelUnderline(true);		
        this.contCustomer.setBoundLabelLength(100);
        // contTenancy		
        this.contTenancy.setBoundLabelText(resHelper.getString("contTenancy.boundLabelText"));		
        this.contTenancy.setBoundLabelLength(100);		
        this.contTenancy.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // panel
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);		
        this.contStartDate.setVisible(true);		
        this.contStartDate.setBoundLabelAlignment(7);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setVisible(true);		
        this.contEndDate.setBoundLabelAlignment(7);
        // contLeaseTime		
        this.contLeaseTime.setBoundLabelText(resHelper.getString("contLeaseTime.boundLabelText"));		
        this.contLeaseTime.setBoundLabelLength(100);		
        this.contLeaseTime.setBoundLabelUnderline(true);
        // contContractNo		
        this.contContractNo.setBoundLabelText(resHelper.getString("contContractNo.boundLabelText"));		
        this.contContractNo.setBoundLabelLength(100);		
        this.contContractNo.setBoundLabelUnderline(true);		
        this.contContractNo.setVisible(true);		
        this.contContractNo.setBoundLabelAlignment(7);
        // contDept		
        this.contDept.setBoundLabelText(resHelper.getString("contDept.boundLabelText"));		
        this.contDept.setBoundLabelLength(100);		
        this.contDept.setBoundLabelUnderline(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);		
        this.contDes.setVisible(true);		
        this.contDes.setBoundLabelAlignment(7);
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // txtRoom		
        this.txtRoom.setEnabled(false);
        // txtCustomer		
        this.txtCustomer.setEnabled(false);
        // txtTenancy		
        this.txtTenancy.setEnabled(false);
        // txtSellProject		
        this.txtSellProject.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtSaleMan		
        this.txtSaleMan.setEnabled(false);
        // contEntry
        // tblPayList
        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"rate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"amountNoTax\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"workload\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{rate}</t:Cell><t:Cell>$Resource{amountNoTax}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{workload}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"moneyDefine","amount","moneyDefine.rate","","price","workload","description"});


        // txtName		
        this.txtName.setRequired(true);
        // kDScrollPane1
        // txtDescription
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // pkStartDate		
        this.pkStartDate.setVisible(true);		
        this.pkStartDate.setEnabled(true);		
        this.pkStartDate.setRequired(true);
        this.pkStartDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkStartDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkEndDate		
        this.pkEndDate.setVisible(true);		
        this.pkEndDate.setEnabled(true);		
        this.pkEndDate.setRequired(true);
        this.pkEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spinLeaseTime		
        this.spinLeaseTime.setRequired(true);
        // txtContractNo		
        this.txtContractNo.setVisible(true);		
        this.txtContractNo.setHorizontalAlignment(2);		
        this.txtContractNo.setMaxLength(50);		
        this.txtContractNo.setEnabled(true);
        // prmtDept		
        this.prmtDept.setDisplayFormat("$name$");		
        this.prmtDept.setEditFormat("$number");		
        this.prmtDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtDept.setCommitFormat("$number$");		
        this.prmtDept.setEditable(true);		
        this.prmtDept.setRequired(true);
        // txtDes		
        this.txtDes.setVisible(true);		
        this.txtDes.setHorizontalAlignment(2);		
        this.txtDes.setMaxLength(50);		
        this.txtDes.setEnabled(true);
        // adjustTaxRate
        this.adjustTaxRate.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjustTaxRate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.adjustTaxRate.setText(resHelper.getString("adjustTaxRate.text"));		
        this.adjustTaxRate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_budgetadjust"));
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
        contCompany.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(367, 32, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(367, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustomer.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(10, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTenancy.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contTenancy, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(367, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(367, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(733, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSaleMan.setBounds(new Rectangle(367, 54, 270, 19));
        this.add(contSaleMan, new KDLayout.Constraints(367, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panel.setBounds(new Rectangle(10, 220, 994, 354));
        this.add(panel, new KDLayout.Constraints(10, 220, 994, 354, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(733, 32, 270, 19));
        this.add(contName, new KDLayout.Constraints(733, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(10, 120, 628, 92));
        this.add(contDescription, new KDLayout.Constraints(10, 120, 628, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(10, 580, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(10, 602, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(367, 580, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(367, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(367, 602, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(367, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStartDate.setBounds(new Rectangle(10, 76, 270, 19));
        this.add(contStartDate, new KDLayout.Constraints(10, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEndDate.setBounds(new Rectangle(367, 76, 270, 19));
        this.add(contEndDate, new KDLayout.Constraints(367, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLeaseTime.setBounds(new Rectangle(10, 98, 270, 19));
        this.add(contLeaseTime, new KDLayout.Constraints(10, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractNo.setBounds(new Rectangle(733, 76, 270, 19));
        this.add(contContractNo, new KDLayout.Constraints(733, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDept.setBounds(new Rectangle(733, 54, 270, 19));
        this.add(contDept, new KDLayout.Constraints(733, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDes.setBounds(new Rectangle(733, 98, 270, 19));
        this.add(contDes, new KDLayout.Constraints(733, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contRoom
        contRoom.setBoundEditor(txtRoom);
        //contCustomer
        contCustomer.setBoundEditor(txtCustomer);
        //contTenancy
        contTenancy.setBoundEditor(txtTenancy);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contSaleMan
        contSaleMan.setBoundEditor(txtSaleMan);
        //panel
        panel.add(contEntry, resHelper.getString("contEntry.constraints"));
        panel.add(tblPayList, resHelper.getString("tblPayList.constraints"));
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contName
        contName.setBoundEditor(txtName);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //contLeaseTime
        contLeaseTime.setBoundEditor(spinLeaseTime);
        //contContractNo
        contContractNo.setBoundEditor(txtContractNo);
        //contDept
        contDept.setBoundEditor(prmtDept);
        //contDes
        contDes.setBoundEditor(txtDes);

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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
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
        this.toolBar.add(adjustTaxRate);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("orgUnit.name", String.class, this.txtCompany, "text");
		dataBinder.registerBinding("tenancyBill.tenRoomsDes", String.class, this.txtRoom, "text");
		dataBinder.registerBinding("tenancyBill.tenCustomerDes", String.class, this.txtCustomer, "text");
		dataBinder.registerBinding("tenancyBill.tenancyName", String.class, this.txtTenancy, "text");
		dataBinder.registerBinding("tenancyBill.sellProject.name", String.class, this.txtSellProject, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("tenancyBill.tenancyAdviser.name", String.class, this.txtSaleMan, "text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.tenancy.OtherBillEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.description", String.class, this.kdtEntry, "description.text");
		dataBinder.registerBinding("entry.moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.kdtEntry, "moneyDefine.text");
		dataBinder.registerBinding("entry.price", java.math.BigDecimal.class, this.kdtEntry, "price.text");
		dataBinder.registerBinding("entry.workload", java.math.BigDecimal.class, this.kdtEntry, "workload.text");
		dataBinder.registerBinding("entry.moneyDefine.rate", java.math.BigDecimal.class, this.kdtEntry, "rate.text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkStartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");
		dataBinder.registerBinding("leaseTime", int.class, this.spinLeaseTime, "value");
		dataBinder.registerBinding("contractNo", String.class, this.txtContractNo, "text");
		dataBinder.registerBinding("dept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtDept, "data");
		dataBinder.registerBinding("des", String.class, this.txtDes, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.OtherBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.OtherBillInfo)ov;
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
		getValidateHelper().registerBindProperty("orgUnit.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill.tenRoomsDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill.tenCustomerDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill.tenancyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill.sellProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyBill.tenancyAdviser.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.workload", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.moneyDefine.rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("des", ValidateHelper.ON_SAVE);    		
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
     * output tblPayList_editStopped method
     */
    protected void tblPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output pkStartDate_dataChanged method
     */
    protected void pkStartDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkEndDate_dataChanged method
     */
    protected void pkEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("tenancyBill.tenRoomsDes"));
        sic.add(new SelectorItemInfo("tenancyBill.tenCustomerDes"));
        sic.add(new SelectorItemInfo("tenancyBill.tenancyName"));
        sic.add(new SelectorItemInfo("tenancyBill.sellProject.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("tenancyBill.tenancyAdviser.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.amount"));
    	sic.add(new SelectorItemInfo("entry.description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.moneyDefine.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.moneyDefine.id"));
			sic.add(new SelectorItemInfo("entry.moneyDefine.name"));
        	sic.add(new SelectorItemInfo("entry.moneyDefine.number"));
		}
    	sic.add(new SelectorItemInfo("entry.price"));
    	sic.add(new SelectorItemInfo("entry.workload"));
    	sic.add(new SelectorItemInfo("entry.moneyDefine.rate"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("leaseTime"));
        sic.add(new SelectorItemInfo("contractNo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("dept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("dept.id"));
        	sic.add(new SelectorItemInfo("dept.number"));
        	sic.add(new SelectorItemInfo("dept.name"));
		}
        sic.add(new SelectorItemInfo("des"));
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
     * output actionAdjustTaxRate_actionPerformed method
     */
    public void actionAdjustTaxRate_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAdjustTaxRate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdjustTaxRate() {
    	return false;
    }

    /**
     * output ActionAdjustTaxRate class
     */     
    protected class ActionAdjustTaxRate extends ItemAction {     
    
        public ActionAdjustTaxRate()
        {
            this(null);
        }

        public ActionAdjustTaxRate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdjustTaxRate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustTaxRate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustTaxRate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillEditUI.this, "ActionAdjustTaxRate", "actionAdjustTaxRate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "OtherBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}