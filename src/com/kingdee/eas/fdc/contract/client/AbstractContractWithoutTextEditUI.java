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
public abstract class AbstractContractWithoutTextEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractWithoutTextEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsBgControl;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCostsplit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContrarctRule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbankAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkUrgency;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkNeedPaid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conConCharge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewBudget;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabel txtPayPlanValue;
    protected com.kingdee.bos.ctrl.swing.KDContainer contInvoiceEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRateAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxerNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarketProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMpCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLxNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbFeeType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBcAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrealSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPayeeType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbillName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPCName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksignDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvoiceDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepartment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedDept;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contFeeEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTraEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBgEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtFeeEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTraEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMarket;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsJT;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPaymentRequestBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtattachment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtNoPaidReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConCharge;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayBillType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtInvoiceEntry;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRateAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxerNum;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtMoneyDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarketProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMpCostAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLxNum;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOrgType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAccountView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewProgramContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.eas.fdc.contract.ContractWithoutTextInfo editData = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected actionViewBudget actionViewBudget = null;
    protected ActionProgram actionProgram = null;
    protected ActionViewProgramContract actionViewProgramContract = null;
    protected ActionAccountView actionAccountView = null;
    protected ActionInvoiceILine actionInvoiceILine = null;
    protected ActionInvoiceALine actionInvoiceALine = null;
    protected ActionInvoiceRLine actionInvoiceRLine = null;
    protected ActionMALine actionMALine = null;
    protected ActionMRLine actionMRLine = null;
    protected ActionMKFP actionMKFP = null;
    /**
     * output class constructor
     */
    public AbstractContractWithoutTextEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractWithoutTextEditUI.class.getName());
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
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelVoucher
        actionDelVoucher.setEnabled(true);
        actionDelVoucher.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.NAME");
        actionDelVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionDelVoucher.setBindWorkFlow(true);
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBudget
        this.actionViewBudget = new actionViewBudget(this);
        getActionManager().registerAction("actionViewBudget", actionViewBudget);
         this.actionViewBudget.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewProgramContract
        this.actionViewProgramContract = new ActionViewProgramContract(this);
        getActionManager().registerAction("actionViewProgramContract", actionViewProgramContract);
         this.actionViewProgramContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAccountView
        this.actionAccountView = new ActionAccountView(this);
        getActionManager().registerAction("actionAccountView", actionAccountView);
         this.actionAccountView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvoiceILine
        this.actionInvoiceILine = new ActionInvoiceILine(this);
        getActionManager().registerAction("actionInvoiceILine", actionInvoiceILine);
         this.actionInvoiceILine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvoiceALine
        this.actionInvoiceALine = new ActionInvoiceALine(this);
        getActionManager().registerAction("actionInvoiceALine", actionInvoiceALine);
         this.actionInvoiceALine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvoiceRLine
        this.actionInvoiceRLine = new ActionInvoiceRLine(this);
        getActionManager().registerAction("actionInvoiceRLine", actionInvoiceRLine);
         this.actionInvoiceRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMALine
        this.actionMALine = new ActionMALine(this);
        getActionManager().registerAction("actionMALine", actionMALine);
         this.actionMALine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMRLine
        this.actionMRLine = new ActionMRLine(this);
        getActionManager().registerAction("actionMRLine", actionMRLine);
         this.actionMRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMKFP
        this.actionMKFP = new ActionMKFP(this);
        getActionManager().registerAction("actionMKFP", actionMKFP);
         this.actionMKFP.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsInvoice = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsBgControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkCostsplit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contApplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayContentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiveUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conContrarctRule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbankAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkUrgency = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkNeedPaid = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conConCharge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contPlanProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewBudget = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contApplierOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplierCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPayPlanValue = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contInvoiceEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRateAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxerQua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxerNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMarketProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMpCostAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLxNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbFeeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtPayContentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBcAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtrealSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtreceiveUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPayeeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtcurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPCName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtContractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pksignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankAcct = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtsettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcapitalAmount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkInvoiceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtuseDepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBgEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contFeeEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contTraEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtBgEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtFeeEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtTraEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblMarket = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.cbIsJT = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPaymentRequestBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtattachment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNoPaidReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtConCharge = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtPlanProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplierOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayBillType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtInvoiceEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtRateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbTaxerQua = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtTaxerNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtMoneyDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtMarketProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMpCostAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLxNum = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbOrgType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAccountView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewProgramContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDScrollPane3.setName("kDScrollPane3");
        this.kDPanel1.setName("kDPanel1");
        this.contNumber.setName("contNumber");
        this.contOrg.setName("contOrg");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.cbIsInvoice.setName("cbIsInvoice");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.cbIsBgControl.setName("cbIsBgControl");
        this.chkCostsplit.setName("chkCostsplit");
        this.contApplier.setName("contApplier");
        this.contCostedCompany.setName("contCostedCompany");
        this.contFeeType.setName("contFeeType");
        this.contInvoiceAmt.setName("contInvoiceAmt");
        this.contPayContentType.setName("contPayContentType");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.contamount.setName("contamount");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.contreceiveUnit.setName("contreceiveUnit");
        this.contPayeeType.setName("contPayeeType");
        this.contcurProject.setName("contcurProject");
        this.contbillName.setName("contbillName");
        this.conContrarctRule.setName("conContrarctRule");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.contpayDate.setName("contpayDate");
        this.contbank.setName("contbank");
        this.contbankAcct.setName("contbankAcct");
        this.contsettlementType.setName("contsettlementType");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.contInvoiceDate.setName("contInvoiceDate");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.contCostedDept.setName("contCostedDept");
        this.contAttachment.setName("contAttachment");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contCreateTime.setName("contCreateTime");
        this.contauditTime.setName("contauditTime");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.chkUrgency.setName("chkUrgency");
        this.chkNeedPaid.setName("chkNeedPaid");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.conConCharge.setName("conConCharge");
        this.contAllInvoiceAmt.setName("contAllInvoiceAmt");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.contPlanProject.setName("contPlanProject");
        this.btnViewBudget.setName("btnViewBudget");
        this.contApplierOrgUnit.setName("contApplierOrgUnit");
        this.contApplierCompany.setName("contApplierCompany");
        this.contPayBillType.setName("contPayBillType");
        this.txtPayPlanValue.setName("txtPayPlanValue");
        this.contInvoiceEntry.setName("contInvoiceEntry");
        this.contRateAmount.setName("contRateAmount");
        this.contTaxerQua.setName("contTaxerQua");
        this.contTaxerNum.setName("contTaxerNum");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.contMarketProject.setName("contMarketProject");
        this.contMpCostAccount.setName("contMpCostAccount");
        this.contLxNum.setName("contLxNum");
        this.contOrgType.setName("contOrgType");
        this.txtNumber.setName("txtNumber");
        this.txtOrg.setName("txtOrg");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.prmtPayment.setName("prmtPayment");
        this.prmtcurrency.setName("prmtcurrency");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.prmtApplier.setName("prmtApplier");
        this.prmtCostedCompany.setName("prmtCostedCompany");
        this.cbFeeType.setName("cbFeeType");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.prmtPayContentType.setName("prmtPayContentType");
        this.txtBcAmount.setName("txtBcAmount");
        this.txtamount.setName("txtamount");
        this.prmtrealSupplier.setName("prmtrealSupplier");
        this.prmtreceiveUnit.setName("prmtreceiveUnit");
        this.comboPayeeType.setName("comboPayeeType");
        this.prmtcurProject.setName("prmtcurProject");
        this.txtbillName.setName("txtbillName");
        this.txtPCName.setName("txtPCName");
        this.prmtContractType.setName("prmtContractType");
        this.pksignDate.setName("pksignDate");
        this.txtBank.setName("txtBank");
        this.txtBankAcct.setName("txtBankAcct");
        this.prmtsettlementType.setName("prmtsettlementType");
        this.txtcapitalAmount.setName("txtcapitalAmount");
        this.pkInvoiceDate.setName("pkInvoiceDate");
        this.prmtuseDepartment.setName("prmtuseDepartment");
        this.prmtCostedDept.setName("prmtCostedDept");
        this.tblAttachement.setName("tblAttachement");
        this.contBgEntry.setName("contBgEntry");
        this.contFeeEntry.setName("contFeeEntry");
        this.contTraEntry.setName("contTraEntry");
        this.kDContainer4.setName("kDContainer4");
        this.kdtBgEntry.setName("kdtBgEntry");
        this.kdtFeeEntry.setName("kdtFeeEntry");
        this.kdtTraEntry.setName("kdtTraEntry");
        this.tblMarket.setName("tblMarket");
        this.cbIsJT.setName("cbIsJT");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.pkauditTime.setName("pkauditTime");
        this.txtPaymentProportion.setName("txtPaymentProportion");
        this.txtPaymentRequestBillNumber.setName("txtPaymentRequestBillNumber");
        this.txtattachment.setName("txtattachment");
        this.txtcompletePrjAmt.setName("txtcompletePrjAmt");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtNoPaidReason.setName("txtNoPaidReason");
        this.prmtAccount.setName("prmtAccount");
        this.prmtConCharge.setName("prmtConCharge");
        this.txtAllInvoiceAmt.setName("txtAllInvoiceAmt");
        this.cmbAttachment.setName("cmbAttachment");
        this.prmtPlanProject.setName("prmtPlanProject");
        this.prmtApplierOrgUnit.setName("prmtApplierOrgUnit");
        this.prmtApplierCompany.setName("prmtApplierCompany");
        this.prmtPayBillType.setName("prmtPayBillType");
        this.kdtInvoiceEntry.setName("kdtInvoiceEntry");
        this.txtRateAmount.setName("txtRateAmount");
        this.cbTaxerQua.setName("cbTaxerQua");
        this.txtTaxerNum.setName("txtTaxerNum");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtMoneyDesc.setName("txtMoneyDesc");
        this.prmtMarketProject.setName("prmtMarketProject");
        this.prmtMpCostAccount.setName("prmtMpCostAccount");
        this.prmtLxNum.setName("prmtLxNum");
        this.cbOrgType.setName("cbOrgType");
        this.btnProgram.setName("btnProgram");
        this.btnAccountView.setName("btnAccountView");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.btnViewProgramContract.setName("btnViewProgramContract");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,650));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.separator2.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));
        // kDScrollPane3
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(1013,1000));		
        this.kDPanel1.setMinimumSize(new Dimension(1013,1000));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // cbIsInvoice		
        this.cbIsInvoice.setText(resHelper.getString("cbIsInvoice.text"));		
        this.cbIsInvoice.setVisible(false);
        this.cbIsInvoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbIsInvoice_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelLength(100);		
        this.contInvoiceNumber.setBoundLabelUnderline(true);		
        this.contInvoiceNumber.setVisible(false);
        // cbIsBgControl		
        this.cbIsBgControl.setText(resHelper.getString("cbIsBgControl.text"));		
        this.cbIsBgControl.setVisible(false);
        this.cbIsBgControl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbIsBgControl_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkCostsplit		
        this.chkCostsplit.setText(resHelper.getString("chkCostsplit.text"));		
        this.chkCostsplit.setVisible(false);
        this.chkCostsplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkCostsplit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contApplier		
        this.contApplier.setBoundLabelText(resHelper.getString("contApplier.boundLabelText"));		
        this.contApplier.setBoundLabelLength(100);		
        this.contApplier.setBoundLabelUnderline(true);
        // contCostedCompany		
        this.contCostedCompany.setBoundLabelText(resHelper.getString("contCostedCompany.boundLabelText"));		
        this.contCostedCompany.setBoundLabelLength(100);		
        this.contCostedCompany.setBoundLabelUnderline(true);
        // contFeeType		
        this.contFeeType.setBoundLabelText(resHelper.getString("contFeeType.boundLabelText"));		
        this.contFeeType.setBoundLabelLength(100);		
        this.contFeeType.setBoundLabelUnderline(true);
        // contInvoiceAmt		
        this.contInvoiceAmt.setBoundLabelText(resHelper.getString("contInvoiceAmt.boundLabelText"));		
        this.contInvoiceAmt.setBoundLabelLength(100);		
        this.contInvoiceAmt.setBoundLabelUnderline(true);
        // contPayContentType		
        this.contPayContentType.setBoundLabelText(resHelper.getString("contPayContentType.boundLabelText"));		
        this.contPayContentType.setBoundLabelLength(100);		
        this.contPayContentType.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);		
        this.contamount.setBoundLabelAlignment(7);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setVisible(true);
        // contreceiveUnit		
        this.contreceiveUnit.setBoundLabelText(resHelper.getString("contreceiveUnit.boundLabelText"));		
        this.contreceiveUnit.setBoundLabelLength(100);		
        this.contreceiveUnit.setBoundLabelUnderline(true);		
        this.contreceiveUnit.setVisible(true);		
        this.contreceiveUnit.setBoundLabelAlignment(7);
        // contPayeeType		
        this.contPayeeType.setBoundLabelText(resHelper.getString("contPayeeType.boundLabelText"));		
        this.contPayeeType.setBoundLabelLength(100);		
        this.contPayeeType.setBoundLabelUnderline(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);		
        this.contcurProject.setBoundLabelAlignment(7);
        // contbillName		
        this.contbillName.setBoundLabelText(resHelper.getString("contbillName.boundLabelText"));		
        this.contbillName.setBoundLabelLength(100);		
        this.contbillName.setBoundLabelUnderline(true);		
        this.contbillName.setVisible(true);		
        this.contbillName.setBoundLabelAlignment(7);
        // conContrarctRule		
        this.conContrarctRule.setBoundLabelText(resHelper.getString("conContrarctRule.boundLabelText"));		
        this.conContrarctRule.setBoundLabelLength(100);		
        this.conContrarctRule.setBoundLabelUnderline(true);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // contpayDate		
        this.contpayDate.setBoundLabelText(resHelper.getString("contpayDate.boundLabelText"));		
        this.contpayDate.setBoundLabelLength(100);		
        this.contpayDate.setBoundLabelUnderline(true);		
        this.contpayDate.setVisible(true);		
        this.contpayDate.setBoundLabelAlignment(7);
        // contbank		
        this.contbank.setBoundLabelText(resHelper.getString("contbank.boundLabelText"));		
        this.contbank.setBoundLabelLength(100);		
        this.contbank.setBoundLabelUnderline(true);		
        this.contbank.setVisible(true);
        // contbankAcct		
        this.contbankAcct.setBoundLabelText(resHelper.getString("contbankAcct.boundLabelText"));		
        this.contbankAcct.setBoundLabelLength(100);		
        this.contbankAcct.setBoundLabelUnderline(true);		
        this.contbankAcct.setVisible(true);
        // contsettlementType		
        this.contsettlementType.setBoundLabelText(resHelper.getString("contsettlementType.boundLabelText"));		
        this.contsettlementType.setBoundLabelLength(100);		
        this.contsettlementType.setBoundLabelUnderline(true);		
        this.contsettlementType.setVisible(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.setVisible(true);
        // contInvoiceDate		
        this.contInvoiceDate.setBoundLabelText(resHelper.getString("contInvoiceDate.boundLabelText"));		
        this.contInvoiceDate.setBoundLabelLength(100);		
        this.contInvoiceDate.setBoundLabelUnderline(true);		
        this.contInvoiceDate.setVisible(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // contCostedDept		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contCostedDept.setBoundLabelLength(100);		
        this.contCostedDept.setBoundLabelUnderline(true);
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // kDTabbedPane1
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);		
        this.contauditTime.setBoundLabelAlignment(7);
        // kDSeparator9
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setVisible(false);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setVisible(false);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setVisible(false);		
        this.kDLabelContainer12.setBoundLabelAlignment(7);
        // chkUrgency		
        this.chkUrgency.setText(resHelper.getString("chkUrgency.text"));		
        this.chkUrgency.setVisible(false);
        // chkNeedPaid		
        this.chkNeedPaid.setText(resHelper.getString("chkNeedPaid.text"));		
        this.chkNeedPaid.setVisible(false);
        this.chkNeedPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkNeedPaid_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setVisible(false);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setEnabled(false);		
        this.kDLabelContainer15.setVisible(false);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setVisible(false);
        // conConCharge		
        this.conConCharge.setBoundLabelText(resHelper.getString("conConCharge.boundLabelText"));		
        this.conConCharge.setBoundLabelLength(100);		
        this.conConCharge.setBoundLabelUnderline(true);		
        this.conConCharge.setVisible(false);
        // contAllInvoiceAmt		
        this.contAllInvoiceAmt.setBoundLabelText(resHelper.getString("contAllInvoiceAmt.boundLabelText"));		
        this.contAllInvoiceAmt.setBoundLabelLength(100);		
        this.contAllInvoiceAmt.setBoundLabelUnderline(true);		
        this.contAllInvoiceAmt.setVisible(false);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);		
        this.lblAttachmentContainer.setVisible(false);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));		
        this.btnViewAttachment.setVisible(false);
        // contPlanProject		
        this.contPlanProject.setBoundLabelText(resHelper.getString("contPlanProject.boundLabelText"));		
        this.contPlanProject.setBoundLabelUnderline(true);		
        this.contPlanProject.setBoundLabelLength(100);		
        this.contPlanProject.setVisible(false);
        // btnViewBudget
        this.btnViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudget, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBudget.setText(resHelper.getString("btnViewBudget.text"));		
        this.btnViewBudget.setVisible(false);
        // contApplierOrgUnit		
        this.contApplierOrgUnit.setBoundLabelText(resHelper.getString("contApplierOrgUnit.boundLabelText"));		
        this.contApplierOrgUnit.setBoundLabelLength(100);		
        this.contApplierOrgUnit.setBoundLabelUnderline(true);		
        this.contApplierOrgUnit.setVisible(false);
        // contApplierCompany		
        this.contApplierCompany.setBoundLabelText(resHelper.getString("contApplierCompany.boundLabelText"));		
        this.contApplierCompany.setBoundLabelLength(100);		
        this.contApplierCompany.setBoundLabelUnderline(true);		
        this.contApplierCompany.setVisible(false);
        // contPayBillType		
        this.contPayBillType.setBoundLabelText(resHelper.getString("contPayBillType.boundLabelText"));		
        this.contPayBillType.setBoundLabelLength(100);		
        this.contPayBillType.setBoundLabelUnderline(true);		
        this.contPayBillType.setVisible(false);
        // txtPayPlanValue		
        this.txtPayPlanValue.setForeground(new java.awt.Color(255,0,0));		
        this.txtPayPlanValue.setVisible(false);
        // contInvoiceEntry		
        this.contInvoiceEntry.setTitle(resHelper.getString("contInvoiceEntry.title"));
        // contRateAmount		
        this.contRateAmount.setBoundLabelText(resHelper.getString("contRateAmount.boundLabelText"));		
        this.contRateAmount.setBoundLabelLength(100);		
        this.contRateAmount.setBoundLabelUnderline(true);
        // contTaxerQua		
        this.contTaxerQua.setBoundLabelText(resHelper.getString("contTaxerQua.boundLabelText"));		
        this.contTaxerQua.setBoundLabelLength(100);		
        this.contTaxerQua.setBoundLabelUnderline(true);
        // contTaxerNum		
        this.contTaxerNum.setBoundLabelText(resHelper.getString("contTaxerNum.boundLabelText"));		
        this.contTaxerNum.setBoundLabelLength(100);		
        this.contTaxerNum.setBoundLabelUnderline(true);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // contMarketProject		
        this.contMarketProject.setBoundLabelText(resHelper.getString("contMarketProject.boundLabelText"));		
        this.contMarketProject.setBoundLabelLength(100);		
        this.contMarketProject.setBoundLabelUnderline(true);
        // contMpCostAccount		
        this.contMpCostAccount.setBoundLabelText(resHelper.getString("contMpCostAccount.boundLabelText"));		
        this.contMpCostAccount.setBoundLabelLength(100);		
        this.contMpCostAccount.setBoundLabelUnderline(true);
        // contLxNum		
        this.contLxNum.setBoundLabelText(resHelper.getString("contLxNum.boundLabelText"));		
        this.contLxNum.setBoundLabelLength(100);		
        this.contLxNum.setBoundLabelUnderline(true);
        // contOrgType		
        this.contOrgType.setBoundLabelText(resHelper.getString("contOrgType.boundLabelText"));		
        this.contOrgType.setBoundLabelLength(100);		
        this.contOrgType.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
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
        // txtOrg		
        this.txtOrg.setEditable(false);
        // pkbookedDate
        this.pkbookedDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkbookedDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // prmtPayment		
        this.prmtPayment.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");		
        this.prmtPayment.setVisible(true);		
        this.prmtPayment.setEditable(true);		
        this.prmtPayment.setDisplayFormat("$number$ $name$");		
        this.prmtPayment.setEditFormat("$number$");		
        this.prmtPayment.setCommitFormat("$number$");		
        this.prmtPayment.setRequired(true);
        // prmtcurrency		
        this.prmtcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtcurrency.setVisible(true);		
        this.prmtcurrency.setEditable(true);		
        this.prmtcurrency.setDisplayFormat("$number$ $name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(true);
        this.prmtcurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurrency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtexchangeRate		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setRequired(true);		
        this.txtexchangeRate.setPrecision(3);
        this.txtexchangeRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtexchangeRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtexchangeRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtexchangeRate_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtInvoiceNumber		
        this.txtInvoiceNumber.setRequired(true);
        // prmtApplier		
        this.prmtApplier.setRequired(true);
        this.prmtApplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtApplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCostedCompany		
        this.prmtCostedCompany.setRequired(true);
        this.prmtCostedCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbFeeType		
        this.cbFeeType.setRequired(true);		
        this.cbFeeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.FeeTypeEnum").toArray());
        this.cbFeeType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbFeeType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setSupportedEmpty(true);		
        this.txtInvoiceAmt.setEnabled(false);
        // prmtPayContentType		
        this.prmtPayContentType.setDisplayFormat("$name$");		
        this.prmtPayContentType.setEditFormat("$longNumber$");		
        this.prmtPayContentType.setCommitFormat("$longNumber$");		
        this.prmtPayContentType.setQueryInfo("com.kingdee.eas.fdc.contract.app.PayContentTypeQuery");		
        this.prmtPayContentType.setRequired(true);
        // txtBcAmount		
        this.txtBcAmount.setDataType(1);		
        this.txtBcAmount.setRequired(true);		
        this.txtBcAmount.setPrecision(2);		
        this.txtBcAmount.setEnabled(false);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(true);		
        this.txtamount.setEnabled(true);
        this.txtamount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtamount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtamount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtamount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtrealSupplier		
        this.prmtrealSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtrealSupplier.setVisible(true);		
        this.prmtrealSupplier.setEditable(true);		
        this.prmtrealSupplier.setDisplayFormat("$number$ $name$");		
        this.prmtrealSupplier.setEditFormat("$number$");		
        this.prmtrealSupplier.setCommitFormat("$number$");		
        this.prmtrealSupplier.setRequired(true);
        this.prmtrealSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrealSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtreceiveUnit		
        this.prmtreceiveUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtreceiveUnit.setVisible(true);		
        this.prmtreceiveUnit.setEditable(true);		
        this.prmtreceiveUnit.setDisplayFormat("$number$ $name$");		
        this.prmtreceiveUnit.setEditFormat("$number$");		
        this.prmtreceiveUnit.setCommitFormat("$number$");		
        this.prmtreceiveUnit.setRequired(true);
        this.prmtreceiveUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreceiveUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboPayeeType
        this.comboPayeeType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPayeeType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtcurProject.setVisible(true);		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$displayname$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(true);
        this.prmtcurProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtbillName		
        this.txtbillName.setVisible(true);		
        this.txtbillName.setHorizontalAlignment(2);		
        this.txtbillName.setMaxLength(100);		
        this.txtbillName.setRequired(true);		
        this.txtbillName.setEnabled(true);
        // txtPCName		
        this.txtPCName.setEnabled(false);
        // prmtContractType		
        this.prmtContractType.setCommitFormat("$number$");		
        this.prmtContractType.setDisplayFormat("$name$");		
        this.prmtContractType.setEditFormat("$number$");		
        this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");		
        this.prmtContractType.setRequired(true);		
        this.prmtContractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");
        this.prmtContractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pksignDate		
        this.pksignDate.setVisible(true);		
        this.pksignDate.setEnabled(false);		
        this.pksignDate.setRequired(true);
        // txtBank		
        this.txtBank.setRequired(true);		
        this.txtBank.setEnabled(false);
        // txtBankAcct		
        this.txtBankAcct.setRequired(true);
        // prmtsettlementType		
        this.prmtsettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtsettlementType.setVisible(true);		
        this.prmtsettlementType.setEditable(true);		
        this.prmtsettlementType.setDisplayFormat("$number$ $name$");		
        this.prmtsettlementType.setEditFormat("$number$");		
        this.prmtsettlementType.setCommitFormat("$number$");
        this.prmtsettlementType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtsettlementType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtcapitalAmount		
        this.txtcapitalAmount.setMaxLength(80);		
        this.txtcapitalAmount.setVisible(true);		
        this.txtcapitalAmount.setEnabled(true);		
        this.txtcapitalAmount.setHorizontalAlignment(2);		
        this.txtcapitalAmount.setRequired(false);		
        this.txtcapitalAmount.setText(resHelper.getString("txtcapitalAmount.text"));		
        this.txtcapitalAmount.setEditable(false);
        // pkInvoiceDate
        // prmtuseDepartment		
        this.prmtuseDepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtuseDepartment.setVisible(true);		
        this.prmtuseDepartment.setEditable(true);		
        this.prmtuseDepartment.setDisplayFormat("$number$ $name$");		
        this.prmtuseDepartment.setEditFormat("$number$");		
        this.prmtuseDepartment.setCommitFormat("$number$");		
        this.prmtuseDepartment.setRequired(true);
        // prmtCostedDept		
        this.prmtCostedDept.setRequired(true);
        this.prmtCostedDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblAttachement
		String tblAttachementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"2\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"type\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAttachement.setFormatXml(resHelper.translateString("tblAttachement",tblAttachementStrXML));
        this.tblAttachement.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAttachement_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblAttachement.checkParsed();
        // contBgEntry
        // contFeeEntry
        // contTraEntry
        // kDContainer4
        // kdtBgEntry
		String kdtBgEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"expenseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" /><t:Column t:key=\"bgItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"accountView\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"requestAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{bgItem}</t:Cell><t:Cell>$Resource{accountView}</t:Cell><t:Cell>$Resource{requestAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtBgEntry.setFormatXml(resHelper.translateString("kdtBgEntry",kdtBgEntryStrXML));
        this.kdtBgEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtBgEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtBgEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtBgEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtBgEntry.checkParsed();
        // kdtFeeEntry
		String kdtFeeEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"desc\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtFeeEntry.setFormatXml(resHelper.translateString("kdtFeeEntry",kdtFeeEntryStrXML));
        this.kdtFeeEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtFeeEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtFeeEntry.putBindContents("editData",new String[] {"name","desc","amount"});


        this.kdtFeeEntry.checkParsed();
        KDFormattedTextField kdtFeeEntry_amount_TextField = new KDFormattedTextField();
        kdtFeeEntry_amount_TextField.setName("kdtFeeEntry_amount_TextField");
        kdtFeeEntry_amount_TextField.setVisible(true);
        kdtFeeEntry_amount_TextField.setEditable(true);
        kdtFeeEntry_amount_TextField.setHorizontalAlignment(2);
        kdtFeeEntry_amount_TextField.setDataType(1);
        	kdtFeeEntry_amount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtFeeEntry_amount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtFeeEntry_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtFeeEntry_amount_CellEditor = new KDTDefaultCellEditor(kdtFeeEntry_amount_TextField);
        this.kdtFeeEntry.getColumn("amount").setEditor(kdtFeeEntry_amount_CellEditor);
        // kdtTraEntry
		String kdtTraEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"days\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"startPlace\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endPlace\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"airFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"carFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"cityFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"otherFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"persons\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"liveDays\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"liveFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"allowance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"other\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"total\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{days}</t:Cell><t:Cell>$Resource{startPlace}</t:Cell><t:Cell>$Resource{endPlace}</t:Cell><t:Cell>$Resource{airFee}</t:Cell><t:Cell>$Resource{carFee}</t:Cell><t:Cell>$Resource{cityFee}</t:Cell><t:Cell>$Resource{otherFee}</t:Cell><t:Cell>$Resource{persons}</t:Cell><t:Cell>$Resource{liveDays}</t:Cell><t:Cell>$Resource{liveFee}</t:Cell><t:Cell>$Resource{allowance}</t:Cell><t:Cell>$Resource{other}</t:Cell><t:Cell>$Resource{total}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{startDate_Row2}</t:Cell><t:Cell>$Resource{endDate_Row2}</t:Cell><t:Cell>$Resource{days_Row2}</t:Cell><t:Cell>$Resource{startPlace_Row2}</t:Cell><t:Cell>$Resource{endPlace_Row2}</t:Cell><t:Cell>$Resource{airFee_Row2}</t:Cell><t:Cell>$Resource{carFee_Row2}</t:Cell><t:Cell>$Resource{cityFee_Row2}</t:Cell><t:Cell>$Resource{otherFee_Row2}</t:Cell><t:Cell>$Resource{persons_Row2}</t:Cell><t:Cell>$Resource{liveDays_Row2}</t:Cell><t:Cell>$Resource{liveFee_Row2}</t:Cell><t:Cell>$Resource{allowance_Row2}</t:Cell><t:Cell>$Resource{other_Row2}</t:Cell><t:Cell>$Resource{total_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"1\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"1\" t:right=\"14\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtTraEntry.setFormatXml(resHelper.translateString("kdtTraEntry",kdtTraEntryStrXML));
        this.kdtTraEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtTraEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtTraEntry.putBindContents("editData",new String[] {"startDate","endDate","days","startPlace","endPlace","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});


        this.kdtTraEntry.checkParsed();
        KDDatePicker kdtTraEntry_startDate_DatePicker = new KDDatePicker();
        kdtTraEntry_startDate_DatePicker.setName("kdtTraEntry_startDate_DatePicker");
        kdtTraEntry_startDate_DatePicker.setVisible(true);
        kdtTraEntry_startDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtTraEntry_startDate_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_startDate_DatePicker);
        this.kdtTraEntry.getColumn("startDate").setEditor(kdtTraEntry_startDate_CellEditor);
        KDDatePicker kdtTraEntry_endDate_DatePicker = new KDDatePicker();
        kdtTraEntry_endDate_DatePicker.setName("kdtTraEntry_endDate_DatePicker");
        kdtTraEntry_endDate_DatePicker.setVisible(true);
        kdtTraEntry_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtTraEntry_endDate_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_endDate_DatePicker);
        this.kdtTraEntry.getColumn("endDate").setEditor(kdtTraEntry_endDate_CellEditor);
        KDFormattedTextField kdtTraEntry_days_TextField = new KDFormattedTextField();
        kdtTraEntry_days_TextField.setName("kdtTraEntry_days_TextField");
        kdtTraEntry_days_TextField.setVisible(true);
        kdtTraEntry_days_TextField.setEditable(true);
        kdtTraEntry_days_TextField.setHorizontalAlignment(2);
        kdtTraEntry_days_TextField.setDataType(0);
        KDTDefaultCellEditor kdtTraEntry_days_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_days_TextField);
        this.kdtTraEntry.getColumn("days").setEditor(kdtTraEntry_days_CellEditor);
        KDFormattedTextField kdtTraEntry_airFee_TextField = new KDFormattedTextField();
        kdtTraEntry_airFee_TextField.setName("kdtTraEntry_airFee_TextField");
        kdtTraEntry_airFee_TextField.setVisible(true);
        kdtTraEntry_airFee_TextField.setEditable(true);
        kdtTraEntry_airFee_TextField.setHorizontalAlignment(2);
        kdtTraEntry_airFee_TextField.setDataType(1);
        	kdtTraEntry_airFee_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_airFee_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_airFee_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_airFee_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_airFee_TextField);
        this.kdtTraEntry.getColumn("airFee").setEditor(kdtTraEntry_airFee_CellEditor);
        KDFormattedTextField kdtTraEntry_carFee_TextField = new KDFormattedTextField();
        kdtTraEntry_carFee_TextField.setName("kdtTraEntry_carFee_TextField");
        kdtTraEntry_carFee_TextField.setVisible(true);
        kdtTraEntry_carFee_TextField.setEditable(true);
        kdtTraEntry_carFee_TextField.setHorizontalAlignment(2);
        kdtTraEntry_carFee_TextField.setDataType(1);
        	kdtTraEntry_carFee_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_carFee_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_carFee_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_carFee_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_carFee_TextField);
        this.kdtTraEntry.getColumn("carFee").setEditor(kdtTraEntry_carFee_CellEditor);
        KDFormattedTextField kdtTraEntry_cityFee_TextField = new KDFormattedTextField();
        kdtTraEntry_cityFee_TextField.setName("kdtTraEntry_cityFee_TextField");
        kdtTraEntry_cityFee_TextField.setVisible(true);
        kdtTraEntry_cityFee_TextField.setEditable(true);
        kdtTraEntry_cityFee_TextField.setHorizontalAlignment(2);
        kdtTraEntry_cityFee_TextField.setDataType(1);
        	kdtTraEntry_cityFee_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_cityFee_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_cityFee_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_cityFee_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_cityFee_TextField);
        this.kdtTraEntry.getColumn("cityFee").setEditor(kdtTraEntry_cityFee_CellEditor);
        KDFormattedTextField kdtTraEntry_otherFee_TextField = new KDFormattedTextField();
        kdtTraEntry_otherFee_TextField.setName("kdtTraEntry_otherFee_TextField");
        kdtTraEntry_otherFee_TextField.setVisible(true);
        kdtTraEntry_otherFee_TextField.setEditable(true);
        kdtTraEntry_otherFee_TextField.setHorizontalAlignment(2);
        kdtTraEntry_otherFee_TextField.setDataType(1);
        	kdtTraEntry_otherFee_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_otherFee_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_otherFee_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_otherFee_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_otherFee_TextField);
        this.kdtTraEntry.getColumn("otherFee").setEditor(kdtTraEntry_otherFee_CellEditor);
        KDFormattedTextField kdtTraEntry_persons_TextField = new KDFormattedTextField();
        kdtTraEntry_persons_TextField.setName("kdtTraEntry_persons_TextField");
        kdtTraEntry_persons_TextField.setVisible(true);
        kdtTraEntry_persons_TextField.setEditable(true);
        kdtTraEntry_persons_TextField.setHorizontalAlignment(2);
        kdtTraEntry_persons_TextField.setDataType(0);
        KDTDefaultCellEditor kdtTraEntry_persons_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_persons_TextField);
        this.kdtTraEntry.getColumn("persons").setEditor(kdtTraEntry_persons_CellEditor);
        KDFormattedTextField kdtTraEntry_liveDays_TextField = new KDFormattedTextField();
        kdtTraEntry_liveDays_TextField.setName("kdtTraEntry_liveDays_TextField");
        kdtTraEntry_liveDays_TextField.setVisible(true);
        kdtTraEntry_liveDays_TextField.setEditable(true);
        kdtTraEntry_liveDays_TextField.setHorizontalAlignment(2);
        kdtTraEntry_liveDays_TextField.setDataType(0);
        KDTDefaultCellEditor kdtTraEntry_liveDays_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_liveDays_TextField);
        this.kdtTraEntry.getColumn("liveDays").setEditor(kdtTraEntry_liveDays_CellEditor);
        KDFormattedTextField kdtTraEntry_liveFee_TextField = new KDFormattedTextField();
        kdtTraEntry_liveFee_TextField.setName("kdtTraEntry_liveFee_TextField");
        kdtTraEntry_liveFee_TextField.setVisible(true);
        kdtTraEntry_liveFee_TextField.setEditable(true);
        kdtTraEntry_liveFee_TextField.setHorizontalAlignment(2);
        kdtTraEntry_liveFee_TextField.setDataType(1);
        	kdtTraEntry_liveFee_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_liveFee_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_liveFee_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_liveFee_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_liveFee_TextField);
        this.kdtTraEntry.getColumn("liveFee").setEditor(kdtTraEntry_liveFee_CellEditor);
        KDFormattedTextField kdtTraEntry_allowance_TextField = new KDFormattedTextField();
        kdtTraEntry_allowance_TextField.setName("kdtTraEntry_allowance_TextField");
        kdtTraEntry_allowance_TextField.setVisible(true);
        kdtTraEntry_allowance_TextField.setEditable(true);
        kdtTraEntry_allowance_TextField.setHorizontalAlignment(2);
        kdtTraEntry_allowance_TextField.setDataType(1);
        	kdtTraEntry_allowance_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_allowance_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_allowance_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_allowance_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_allowance_TextField);
        this.kdtTraEntry.getColumn("allowance").setEditor(kdtTraEntry_allowance_CellEditor);
        KDFormattedTextField kdtTraEntry_other_TextField = new KDFormattedTextField();
        kdtTraEntry_other_TextField.setName("kdtTraEntry_other_TextField");
        kdtTraEntry_other_TextField.setVisible(true);
        kdtTraEntry_other_TextField.setEditable(true);
        kdtTraEntry_other_TextField.setHorizontalAlignment(2);
        kdtTraEntry_other_TextField.setDataType(1);
        	kdtTraEntry_other_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_other_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_other_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_other_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_other_TextField);
        this.kdtTraEntry.getColumn("other").setEditor(kdtTraEntry_other_CellEditor);
        KDFormattedTextField kdtTraEntry_total_TextField = new KDFormattedTextField();
        kdtTraEntry_total_TextField.setName("kdtTraEntry_total_TextField");
        kdtTraEntry_total_TextField.setVisible(true);
        kdtTraEntry_total_TextField.setEditable(true);
        kdtTraEntry_total_TextField.setHorizontalAlignment(2);
        kdtTraEntry_total_TextField.setDataType(1);
        	kdtTraEntry_total_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtTraEntry_total_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtTraEntry_total_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtTraEntry_total_CellEditor = new KDTDefaultCellEditor(kdtTraEntry_total_TextField);
        this.kdtTraEntry.getColumn("total").setEditor(kdtTraEntry_total_CellEditor);
        // tblMarket
		String tblMarketStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{rate}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMarket.setFormatXml(resHelper.translateString("tblMarket",tblMarketStrXML));
        this.tblMarket.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMarket_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblMarket.putBindContents("editData",new String[] {"date","rate","amount","content","remark"});


        this.tblMarket.checkParsed();
        KDDatePicker tblMarket_date_DatePicker = new KDDatePicker();
        tblMarket_date_DatePicker.setName("tblMarket_date_DatePicker");
        tblMarket_date_DatePicker.setVisible(true);
        tblMarket_date_DatePicker.setEditable(true);
        KDTDefaultCellEditor tblMarket_date_CellEditor = new KDTDefaultCellEditor(tblMarket_date_DatePicker);
        this.tblMarket.getColumn("date").setEditor(tblMarket_date_CellEditor);
        KDFormattedTextField tblMarket_rate_TextField = new KDFormattedTextField();
        tblMarket_rate_TextField.setName("tblMarket_rate_TextField");
        tblMarket_rate_TextField.setVisible(true);
        tblMarket_rate_TextField.setEditable(true);
        tblMarket_rate_TextField.setHorizontalAlignment(2);
        tblMarket_rate_TextField.setDataType(1);
        tblMarket_rate_TextField.setPrecision(19);
        KDTDefaultCellEditor tblMarket_rate_CellEditor = new KDTDefaultCellEditor(tblMarket_rate_TextField);
        this.tblMarket.getColumn("rate").setEditor(tblMarket_rate_CellEditor);
        KDFormattedTextField tblMarket_amount_TextField = new KDFormattedTextField();
        tblMarket_amount_TextField.setName("tblMarket_amount_TextField");
        tblMarket_amount_TextField.setVisible(true);
        tblMarket_amount_TextField.setEditable(true);
        tblMarket_amount_TextField.setHorizontalAlignment(2);
        tblMarket_amount_TextField.setDataType(1);
        	tblMarket_amount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblMarket_amount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblMarket_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor tblMarket_amount_CellEditor = new KDTDefaultCellEditor(tblMarket_amount_TextField);
        this.tblMarket.getColumn("amount").setEditor(tblMarket_amount_CellEditor);
        // cbIsJT		
        this.cbIsJT.setText(resHelper.getString("cbIsJT.text"));		
        this.cbIsJT.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setEnabled(false);
        this.pkauditTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkauditTime_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentProportion		
        this.txtPaymentProportion.setDataType(1);		
        this.txtPaymentProportion.setEnabled(false);
        this.txtPaymentProportion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtpaymentProportion_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentRequestBillNumber		
        this.txtPaymentRequestBillNumber.setMaxLength(80);		
        this.txtPaymentRequestBillNumber.setVisible(true);		
        this.txtPaymentRequestBillNumber.setEnabled(true);		
        this.txtPaymentRequestBillNumber.setHorizontalAlignment(2);		
        this.txtPaymentRequestBillNumber.setRequired(true);		
        this.txtPaymentRequestBillNumber.setText(resHelper.getString("txtPaymentRequestBillNumber.text"));
        // txtattachment		
        this.txtattachment.setVisible(true);		
        this.txtattachment.setDataType(0);		
        this.txtattachment.setSupportedEmpty(true);		
        this.txtattachment.setRequired(false);		
        this.txtattachment.setEnabled(true);		
        this.txtattachment.setPrecision(0);
        // txtcompletePrjAmt		
        this.txtcompletePrjAmt.setDataType(1);		
        this.txtcompletePrjAmt.setEnabled(false);
        this.txtcompletePrjAmt.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtcompletePrjAmt_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane2
        // txtNoPaidReason		
        this.txtNoPaidReason.setEnabled(false);
        // prmtAccount		
        this.prmtAccount.setEnabled(false);		
        this.prmtAccount.setDisplayFormat("$name$");		
        this.prmtAccount.setEditFormat("$number$");		
        this.prmtAccount.setCommitFormat("$number$");		
        this.prmtAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        this.prmtAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtConCharge		
        this.prmtConCharge.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractChargeTypeQuery");		
        this.prmtConCharge.setDisplayFormat("$name$");		
        this.prmtConCharge.setEditFormat("$number$");		
        this.prmtConCharge.setCommitFormat("$number$");
        // txtAllInvoiceAmt		
        this.txtAllInvoiceAmt.setSupportedEmpty(true);		
        this.txtAllInvoiceAmt.setEnabled(false);		
        this.txtAllInvoiceAmt.setDataType(1);
        // cmbAttachment
        // prmtPlanProject
        // prmtApplierOrgUnit
        this.prmtApplierOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtApplierOrgUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtApplierCompany		
        this.prmtApplierCompany.setEnabled(false);
        // prmtPayBillType		
        this.prmtPayBillType.setQueryInfo("com.kingdee.eas.fi.cas.F7PaymentBillTypeQuery");		
        this.prmtPayBillType.setCommitFormat("$number$");		
        this.prmtPayBillType.setEditFormat("$number$");		
        this.prmtPayBillType.setDisplayFormat("$number$ $name$");		
        this.prmtPayBillType.setVisible(false);
        // kdtInvoiceEntry
		String kdtInvoiceEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"invoiceNumber\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"invoiceTypeDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"totalPriceAndTax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"specialVATTaxRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"totalTaxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"fromMk\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{invoiceTypeDesc}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{totalPriceAndTax}</t:Cell><t:Cell>$Resource{specialVATTaxRate}</t:Cell><t:Cell>$Resource{totalTaxAmount}</t:Cell><t:Cell>$Resource{fromMk}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtInvoiceEntry.setFormatXml(resHelper.translateString("kdtInvoiceEntry",kdtInvoiceEntryStrXML));
        this.kdtInvoiceEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtInvoiceEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtInvoiceEntry.putBindContents("editData",new String[] {"invoiceNumber","invoiceType","bizDate","totalAmount","rate","amount","fromMk"});


        this.kdtInvoiceEntry.checkParsed();
        KDDatePicker kdtInvoiceEntry_bizDate_DatePicker = new KDDatePicker();
        kdtInvoiceEntry_bizDate_DatePicker.setName("kdtInvoiceEntry_bizDate_DatePicker");
        kdtInvoiceEntry_bizDate_DatePicker.setVisible(true);
        kdtInvoiceEntry_bizDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtInvoiceEntry_bizDate_CellEditor = new KDTDefaultCellEditor(kdtInvoiceEntry_bizDate_DatePicker);
        this.kdtInvoiceEntry.getColumn("bizDate").setEditor(kdtInvoiceEntry_bizDate_CellEditor);
        KDFormattedTextField kdtInvoiceEntry_totalPriceAndTax_TextField = new KDFormattedTextField();
        kdtInvoiceEntry_totalPriceAndTax_TextField.setName("kdtInvoiceEntry_totalPriceAndTax_TextField");
        kdtInvoiceEntry_totalPriceAndTax_TextField.setVisible(true);
        kdtInvoiceEntry_totalPriceAndTax_TextField.setEditable(true);
        kdtInvoiceEntry_totalPriceAndTax_TextField.setHorizontalAlignment(2);
        kdtInvoiceEntry_totalPriceAndTax_TextField.setDataType(1);
        	kdtInvoiceEntry_totalPriceAndTax_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtInvoiceEntry_totalPriceAndTax_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtInvoiceEntry_totalPriceAndTax_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtInvoiceEntry_totalPriceAndTax_CellEditor = new KDTDefaultCellEditor(kdtInvoiceEntry_totalPriceAndTax_TextField);
        this.kdtInvoiceEntry.getColumn("totalPriceAndTax").setEditor(kdtInvoiceEntry_totalPriceAndTax_CellEditor);
        KDFormattedTextField kdtInvoiceEntry_specialVATTaxRate_TextField = new KDFormattedTextField();
        kdtInvoiceEntry_specialVATTaxRate_TextField.setName("kdtInvoiceEntry_specialVATTaxRate_TextField");
        kdtInvoiceEntry_specialVATTaxRate_TextField.setVisible(true);
        kdtInvoiceEntry_specialVATTaxRate_TextField.setEditable(true);
        kdtInvoiceEntry_specialVATTaxRate_TextField.setHorizontalAlignment(2);
        kdtInvoiceEntry_specialVATTaxRate_TextField.setDataType(1);
        	kdtInvoiceEntry_specialVATTaxRate_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtInvoiceEntry_specialVATTaxRate_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtInvoiceEntry_specialVATTaxRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtInvoiceEntry_specialVATTaxRate_CellEditor = new KDTDefaultCellEditor(kdtInvoiceEntry_specialVATTaxRate_TextField);
        this.kdtInvoiceEntry.getColumn("specialVATTaxRate").setEditor(kdtInvoiceEntry_specialVATTaxRate_CellEditor);
        KDFormattedTextField kdtInvoiceEntry_totalTaxAmount_TextField = new KDFormattedTextField();
        kdtInvoiceEntry_totalTaxAmount_TextField.setName("kdtInvoiceEntry_totalTaxAmount_TextField");
        kdtInvoiceEntry_totalTaxAmount_TextField.setVisible(true);
        kdtInvoiceEntry_totalTaxAmount_TextField.setEditable(true);
        kdtInvoiceEntry_totalTaxAmount_TextField.setHorizontalAlignment(2);
        kdtInvoiceEntry_totalTaxAmount_TextField.setDataType(1);
        	kdtInvoiceEntry_totalTaxAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtInvoiceEntry_totalTaxAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtInvoiceEntry_totalTaxAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtInvoiceEntry_totalTaxAmount_CellEditor = new KDTDefaultCellEditor(kdtInvoiceEntry_totalTaxAmount_TextField);
        this.kdtInvoiceEntry.getColumn("totalTaxAmount").setEditor(kdtInvoiceEntry_totalTaxAmount_CellEditor);
        KDFormattedTextField kdtInvoiceEntry_fromMk_TextField = new KDFormattedTextField();
        kdtInvoiceEntry_fromMk_TextField.setName("kdtInvoiceEntry_fromMk_TextField");
        kdtInvoiceEntry_fromMk_TextField.setVisible(true);
        kdtInvoiceEntry_fromMk_TextField.setEditable(true);
        kdtInvoiceEntry_fromMk_TextField.setHorizontalAlignment(2);
        kdtInvoiceEntry_fromMk_TextField.setDataType(0);
        KDTDefaultCellEditor kdtInvoiceEntry_fromMk_CellEditor = new KDTDefaultCellEditor(kdtInvoiceEntry_fromMk_TextField);
        this.kdtInvoiceEntry.getColumn("fromMk").setEditor(kdtInvoiceEntry_fromMk_CellEditor);
        // txtRateAmount		
        this.txtRateAmount.setSupportedEmpty(true);		
        this.txtRateAmount.setPrecision(2);		
        this.txtRateAmount.setEnabled(false);
        // cbTaxerQua		
        this.cbTaxerQua.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.TaxerQuaEnum").toArray());		
        this.cbTaxerQua.setRequired(true);
        // txtTaxerNum		
        this.txtTaxerNum.setRequired(true);
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtMoneyDesc		
        this.txtMoneyDesc.setToolTipText(resHelper.getString("txtMoneyDesc.toolTipText"));		
        this.txtMoneyDesc.setText(resHelper.getString("txtMoneyDesc.text"));		
        this.txtMoneyDesc.setMaxLength(1000);		
        this.txtMoneyDesc.setWrapStyleWord(true);		
        this.txtMoneyDesc.setLineWrap(true);
        // prmtMarketProject		
        this.prmtMarketProject.setCommitFormat("$name$");		
        this.prmtMarketProject.setDisplayFormat("$name$");		
        this.prmtMarketProject.setEditFormat("$name$");		
        this.prmtMarketProject.setQueryInfo("com.kingdee.eas.fdc.contract.app.MarketProjectQuery");
        this.prmtMarketProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMarketProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtMpCostAccount		
        this.prmtMpCostAccount.setCommitFormat("$name$");		
        this.prmtMpCostAccount.setDisplayFormat("$name$");		
        this.prmtMpCostAccount.setEditFormat("$name$");		
        this.prmtMpCostAccount.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        this.prmtMpCostAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtMpCostAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtMpCostAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMpCostAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtLxNum		
        this.prmtLxNum.setQueryInfo("com.kingdee.eas.fdc.contract.app.BankNumQuery");		
        this.prmtLxNum.setCommitFormat("$number$");		
        this.prmtLxNum.setDisplayFormat("$number$");		
        this.prmtLxNum.setEditFormat("$number$");		
        this.prmtLxNum.setRequired(true);
        this.prmtLxNum.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtLxNum_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbOrgType		
        this.cbOrgType.setRequired(true);		
        this.cbOrgType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum").toArray());
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));
        // btnAccountView
        this.btnAccountView.setAction((IItemAction)ActionProxyFactory.getProxy(actionAccountView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAccountView.setText(resHelper.getString("btnAccountView.text"));
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));
        // btnViewProgramContract
        this.btnViewProgramContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewProgramContract.setText(resHelper.getString("btnViewProgramContract.text"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));		
        this.menuItemViewBgBalance.setToolTipText(resHelper.getString("menuItemViewBgBalance.toolTipText"));
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
        this.setBounds(new Rectangle(0, 0, 1013, 1000));
this.setLayout(new BorderLayout(0, 0));
        this.add(kDScrollPane3, BorderLayout.CENTER);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 1000));        kDSeparator6.setBounds(new Rectangle(0, 188, 1010, 8));
        kDPanel1.add(kDSeparator6, new KDLayout.Constraints(0, 188, 1010, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(0, 103, 1010, 10));
        kDPanel1.add(kDSeparator5, new KDLayout.Constraints(0, 103, 1010, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(14, 8, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(14, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(14, 30, 270, 19));
        kDPanel1.add(contOrg, new KDLayout.Constraints(14, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(14, 74, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(14, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(367, 74, 270, 19));
        kDPanel1.add(kDLabelContainer13, new KDLayout.Constraints(367, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(367, 52, 270, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(367, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(14, 201, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(14, 201, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(14, 223, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(14, 223, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsInvoice.setBounds(new Rectangle(948, 213, 140, 19));
        kDPanel1.add(cbIsInvoice, new KDLayout.Constraints(948, 213, 140, 19, 0));
        contInvoiceNumber.setBounds(new Rectangle(923, 227, 270, 19));
        kDPanel1.add(contInvoiceNumber, new KDLayout.Constraints(923, 227, 270, 19, 0));
        cbIsBgControl.setBounds(new Rectangle(928, 205, 140, 19));
        kDPanel1.add(cbIsBgControl, new KDLayout.Constraints(928, 205, 140, 19, 0));
        chkCostsplit.setBounds(new Rectangle(937, 191, 121, 19));
        kDPanel1.add(chkCostsplit, new KDLayout.Constraints(937, 191, 121, 19, 0));
        contApplier.setBounds(new Rectangle(14, 290, 270, 19));
        kDPanel1.add(contApplier, new KDLayout.Constraints(14, 290, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedCompany.setBounds(new Rectangle(14, 334, 270, 19));
        kDPanel1.add(contCostedCompany, new KDLayout.Constraints(14, 334, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeType.setBounds(new Rectangle(14, 268, 270, 19));
        kDPanel1.add(contFeeType, new KDLayout.Constraints(14, 268, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceAmt.setBounds(new Rectangle(14, 523, 270, 19));
        kDPanel1.add(contInvoiceAmt, new KDLayout.Constraints(14, 523, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayContentType.setBounds(new Rectangle(720, 52, 270, 19));
        kDPanel1.add(contPayContentType, new KDLayout.Constraints(720, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(367, 223, 270, 19));
        kDPanel1.add(kDLabelContainer11, new KDLayout.Constraints(367, 223, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(367, 201, 270, 19));
        kDPanel1.add(contamount, new KDLayout.Constraints(367, 201, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(367, 157, 270, 19));
        kDPanel1.add(kDLabelContainer7, new KDLayout.Constraints(367, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreceiveUnit.setBounds(new Rectangle(367, 135, 270, 19));
        kDPanel1.add(contreceiveUnit, new KDLayout.Constraints(367, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayeeType.setBounds(new Rectangle(367, 113, 270, 19));
        kDPanel1.add(contPayeeType, new KDLayout.Constraints(367, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(367, 30, 270, 19));
        kDPanel1.add(contcurProject, new KDLayout.Constraints(367, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbillName.setBounds(new Rectangle(367, 8, 270, 19));
        kDPanel1.add(contbillName, new KDLayout.Constraints(367, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContrarctRule.setBounds(new Rectangle(720, 8, 270, 19));
        kDPanel1.add(conContrarctRule, new KDLayout.Constraints(720, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(14, 52, 270, 19));
        kDPanel1.add(kDLabelContainer17, new KDLayout.Constraints(14, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayDate.setBounds(new Rectangle(720, 74, 270, 19));
        kDPanel1.add(contpayDate, new KDLayout.Constraints(720, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbank.setBounds(new Rectangle(720, 135, 270, 19));
        kDPanel1.add(contbank, new KDLayout.Constraints(720, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbankAcct.setBounds(new Rectangle(720, 157, 270, 19));
        kDPanel1.add(contbankAcct, new KDLayout.Constraints(720, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contsettlementType.setBounds(new Rectangle(720, 201, 270, 19));
        kDPanel1.add(contsettlementType, new KDLayout.Constraints(720, 201, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(720, 223, 270, 19));
        kDPanel1.add(kDLabelContainer10, new KDLayout.Constraints(720, 223, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInvoiceDate.setBounds(new Rectangle(923, 242, 270, 19));
        kDPanel1.add(contInvoiceDate, new KDLayout.Constraints(923, 242, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(14, 312, 270, 19));
        kDPanel1.add(kDLabelContainer6, new KDLayout.Constraints(14, 312, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(14, 356, 270, 19));
        kDPanel1.add(contCostedDept, new KDLayout.Constraints(14, 356, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(5, 722, 1000, 113));
        kDPanel1.add(contAttachment, new KDLayout.Constraints(5, 722, 1000, 113, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(367, 268, 636, 248));
        kDPanel1.add(kDTabbedPane1, new KDLayout.Constraints(367, 268, 636, 248, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(14, 946, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(14, 946, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(14, 968, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(14, 968, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(367, 946, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(367, 946, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditTime.setBounds(new Rectangle(367, 968, 270, 19));
        kDPanel1.add(contauditTime, new KDLayout.Constraints(367, 968, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator9.setBounds(new Rectangle(0, 254, 1010, 10));
        kDPanel1.add(kDSeparator9, new KDLayout.Constraints(0, 254, 1010, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(851, 320, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(851, 320, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(850, 367, 270, 19));
        kDPanel1.add(kDLabelContainer9, new KDLayout.Constraints(850, 367, 270, 19, 0));
        kDLabelContainer12.setBounds(new Rectangle(852, 352, 270, 19));
        kDPanel1.add(kDLabelContainer12, new KDLayout.Constraints(852, 352, 270, 19, 0));
        chkUrgency.setBounds(new Rectangle(973, 488, 58, 19));
        kDPanel1.add(chkUrgency, new KDLayout.Constraints(973, 488, 58, 19, 0));
        chkNeedPaid.setBounds(new Rectangle(943, 501, 75, 19));
        kDPanel1.add(chkNeedPaid, new KDLayout.Constraints(943, 501, 75, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(852, 334, 270, 19));
        kDPanel1.add(kDLabelContainer8, new KDLayout.Constraints(852, 334, 270, 19, 0));
        kDLabelContainer15.setBounds(new Rectangle(962, 554, 162, 29));
        kDPanel1.add(kDLabelContainer15, new KDLayout.Constraints(962, 554, 162, 29, 0));
        kDLabelContainer16.setBounds(new Rectangle(849, 424, 469, 19));
        kDPanel1.add(kDLabelContainer16, new KDLayout.Constraints(849, 424, 469, 19, 0));
        conConCharge.setBounds(new Rectangle(852, 400, 469, 19));
        kDPanel1.add(conConCharge, new KDLayout.Constraints(852, 400, 469, 19, 0));
        contAllInvoiceAmt.setBounds(new Rectangle(854, 371, 270, 19));
        kDPanel1.add(contAllInvoiceAmt, new KDLayout.Constraints(854, 371, 270, 19, 0));
        lblAttachmentContainer.setBounds(new Rectangle(856, 375, 378, 19));
        kDPanel1.add(lblAttachmentContainer, new KDLayout.Constraints(856, 375, 378, 19, 0));
        btnViewAttachment.setBounds(new Rectangle(931, 343, 86, 21));
        kDPanel1.add(btnViewAttachment, new KDLayout.Constraints(931, 343, 86, 21, 0));
        contPlanProject.setBounds(new Rectangle(846, 285, 270, 19));
        kDPanel1.add(contPlanProject, new KDLayout.Constraints(846, 285, 270, 19, 0));
        btnViewBudget.setBounds(new Rectangle(919, 313, 93, 21));
        kDPanel1.add(btnViewBudget, new KDLayout.Constraints(919, 313, 93, 21, 0));
        contApplierOrgUnit.setBounds(new Rectangle(951, 534, 270, 19));
        kDPanel1.add(contApplierOrgUnit, new KDLayout.Constraints(951, 534, 270, 19, 0));
        contApplierCompany.setBounds(new Rectangle(951, 509, 270, 19));
        kDPanel1.add(contApplierCompany, new KDLayout.Constraints(951, 509, 270, 19, 0));
        contPayBillType.setBounds(new Rectangle(976, 261, 270, 19));
        kDPanel1.add(contPayBillType, new KDLayout.Constraints(976, 261, 270, 19, 0));
        txtPayPlanValue.setBounds(new Rectangle(720, 552, 151, 19));
        kDPanel1.add(txtPayPlanValue, new KDLayout.Constraints(720, 552, 151, 19, 0));
        contInvoiceEntry.setBounds(new Rectangle(5, 547, 1000, 173));
        kDPanel1.add(contInvoiceEntry, new KDLayout.Constraints(5, 547, 1000, 173, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRateAmount.setBounds(new Rectangle(367, 523, 270, 19));
        kDPanel1.add(contRateAmount, new KDLayout.Constraints(367, 523, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxerQua.setBounds(new Rectangle(14, 135, 270, 19));
        kDPanel1.add(contTaxerQua, new KDLayout.Constraints(14, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxerNum.setBounds(new Rectangle(14, 157, 270, 19));
        kDPanel1.add(contTaxerNum, new KDLayout.Constraints(14, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer18.setBounds(new Rectangle(14, 844, 989, 92));
        kDPanel1.add(kDLabelContainer18, new KDLayout.Constraints(14, 844, 989, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contMarketProject.setBounds(new Rectangle(13, 399, 270, 19));
        kDPanel1.add(contMarketProject, new KDLayout.Constraints(13, 399, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMpCostAccount.setBounds(new Rectangle(13, 422, 270, 19));
        kDPanel1.add(contMpCostAccount, new KDLayout.Constraints(13, 422, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLxNum.setBounds(new Rectangle(720, 113, 270, 19));
        kDPanel1.add(contLxNum, new KDLayout.Constraints(720, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgType.setBounds(new Rectangle(720, 30, 270, 19));
        kDPanel1.add(contOrgType, new KDLayout.Constraints(720, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(pkbookedDate);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(cbPeriod);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(prmtPayment);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtcurrency);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtexchangeRate);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //contApplier
        contApplier.setBoundEditor(prmtApplier);
        //contCostedCompany
        contCostedCompany.setBoundEditor(prmtCostedCompany);
        //contFeeType
        contFeeType.setBoundEditor(cbFeeType);
        //contInvoiceAmt
        contInvoiceAmt.setBoundEditor(txtInvoiceAmt);
        //contPayContentType
        contPayContentType.setBoundEditor(prmtPayContentType);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtBcAmount);
        //contamount
        contamount.setBoundEditor(txtamount);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtrealSupplier);
        //contreceiveUnit
        contreceiveUnit.setBoundEditor(prmtreceiveUnit);
        //contPayeeType
        contPayeeType.setBoundEditor(comboPayeeType);
        //contcurProject
        contcurProject.setBoundEditor(prmtcurProject);
        //contbillName
        contbillName.setBoundEditor(txtbillName);
        //conContrarctRule
        conContrarctRule.setBoundEditor(txtPCName);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(prmtContractType);
        //contpayDate
        contpayDate.setBoundEditor(pksignDate);
        //contbank
        contbank.setBoundEditor(txtBank);
        //contbankAcct
        contbankAcct.setBoundEditor(txtBankAcct);
        //contsettlementType
        contsettlementType.setBoundEditor(prmtsettlementType);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtcapitalAmount);
        //contInvoiceDate
        contInvoiceDate.setBoundEditor(pkInvoiceDate);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(prmtuseDepartment);
        //contCostedDept
        contCostedDept.setBoundEditor(prmtCostedDept);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //kDTabbedPane1
        kDTabbedPane1.add(contBgEntry, resHelper.getString("contBgEntry.constraints"));
        kDTabbedPane1.add(contFeeEntry, resHelper.getString("contFeeEntry.constraints"));
        kDTabbedPane1.add(contTraEntry, resHelper.getString("contTraEntry.constraints"));
        kDTabbedPane1.add(kDContainer4, resHelper.getString("kDContainer4.constraints"));
        //contBgEntry
contBgEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contBgEntry.getContentPane().add(kdtBgEntry, BorderLayout.CENTER);
        //contFeeEntry
contFeeEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contFeeEntry.getContentPane().add(kdtFeeEntry, BorderLayout.CENTER);
        //contTraEntry
contTraEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contTraEntry.getContentPane().add(kdtTraEntry, BorderLayout.CENTER);
        //kDContainer4
        kDContainer4.getContentPane().setLayout(new KDLayout());
        kDContainer4.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 635, 215));        tblMarket.setBounds(new Rectangle(0, 37, 632, 161));
        kDContainer4.getContentPane().add(tblMarket, new KDLayout.Constraints(0, 37, 632, 161, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsJT.setBounds(new Rectangle(4, 7, 228, 19));
        kDContainer4.getContentPane().add(cbIsJT, new KDLayout.Constraints(4, 7, 228, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtPaymentProportion);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtPaymentRequestBillNumber);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtattachment);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtcompletePrjAmt);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtNoPaidReason, null);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(prmtAccount);
        //conConCharge
        conConCharge.setBoundEditor(prmtConCharge);
        //contAllInvoiceAmt
        contAllInvoiceAmt.setBoundEditor(txtAllInvoiceAmt);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //contPlanProject
        contPlanProject.setBoundEditor(prmtPlanProject);
        //contApplierOrgUnit
        contApplierOrgUnit.setBoundEditor(prmtApplierOrgUnit);
        //contApplierCompany
        contApplierCompany.setBoundEditor(prmtApplierCompany);
        //contPayBillType
        contPayBillType.setBoundEditor(prmtPayBillType);
        //contInvoiceEntry
contInvoiceEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contInvoiceEntry.getContentPane().add(kdtInvoiceEntry, BorderLayout.CENTER);
        //contRateAmount
        contRateAmount.setBoundEditor(txtRateAmount);
        //contTaxerQua
        contTaxerQua.setBoundEditor(cbTaxerQua);
        //contTaxerNum
        contTaxerNum.setBoundEditor(txtTaxerNum);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtMoneyDesc, null);
        //contMarketProject
        contMarketProject.setBoundEditor(prmtMarketProject);
        //contMpCostAccount
        contMpCostAccount.setBoundEditor(prmtMpCostAccount);
        //contLxNum
        contLxNum.setBoundEditor(prmtLxNum);
        //contOrgType
        contOrgType.setBoundEditor(cbOrgType);

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
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
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
        menuView.add(menuItemViewBgBalance);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnProgram);
        this.toolBar.add(btnAccountView);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnViewProgramContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isInvoice", boolean.class, this.cbIsInvoice, "selected");
		dataBinder.registerBinding("isBgControl", boolean.class, this.cbIsBgControl, "selected");
		dataBinder.registerBinding("isCostSplit", boolean.class, this.chkCostsplit, "selected");
		dataBinder.registerBinding("isNeedPaid", boolean.class, this.chkNeedPaid, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtInvoiceNumber, "text");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtApplier, "data");
		dataBinder.registerBinding("costedCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCostedCompany, "data");
		dataBinder.registerBinding("feeType", com.kingdee.eas.fdc.contract.FeeTypeEnum.class, this.cbFeeType, "selectedItem");
		dataBinder.registerBinding("invoiceAmt", java.math.BigDecimal.class, this.txtInvoiceAmt, "value");
		dataBinder.registerBinding("payContentType", com.kingdee.eas.fdc.contract.PayContentTypeInfo.class, this.prmtPayContentType, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtBcAmount, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurProject, "data");
		dataBinder.registerBinding("name", String.class, this.txtbillName, "text");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtContractType, "data");
		dataBinder.registerBinding("signDate", java.util.Date.class, this.pksignDate, "value");
		dataBinder.registerBinding("bank", String.class, this.txtBank, "text");
		dataBinder.registerBinding("bankAcct", String.class, this.txtBankAcct, "text");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtsettlementType, "data");
		dataBinder.registerBinding("invoiceDate", java.util.Date.class, this.pkInvoiceDate, "value");
		dataBinder.registerBinding("useDepartment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDepartment, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.prmtCostedDept, "data");
		dataBinder.registerBinding("feeEntry", com.kingdee.eas.fdc.contract.FeeEntryInfo.class, this.kdtFeeEntry, "userObject");
		dataBinder.registerBinding("feeEntry.name", String.class, this.kdtFeeEntry, "name.text");
		dataBinder.registerBinding("feeEntry.desc", String.class, this.kdtFeeEntry, "desc.text");
		dataBinder.registerBinding("feeEntry.amount", java.math.BigDecimal.class, this.kdtFeeEntry, "amount.text");
		dataBinder.registerBinding("traEntry", com.kingdee.eas.fdc.contract.TraEntryInfo.class, this.kdtTraEntry, "userObject");
		dataBinder.registerBinding("traEntry.startDate", java.util.Date.class, this.kdtTraEntry, "startDate.text");
		dataBinder.registerBinding("traEntry.endDate", java.util.Date.class, this.kdtTraEntry, "endDate.text");
		dataBinder.registerBinding("traEntry.days", int.class, this.kdtTraEntry, "days.text");
		dataBinder.registerBinding("traEntry.startPlace", String.class, this.kdtTraEntry, "startPlace.text");
		dataBinder.registerBinding("traEntry.endPlace", String.class, this.kdtTraEntry, "endPlace.text");
		dataBinder.registerBinding("traEntry.airFee", java.math.BigDecimal.class, this.kdtTraEntry, "airFee.text");
		dataBinder.registerBinding("traEntry.carFee", java.math.BigDecimal.class, this.kdtTraEntry, "carFee.text");
		dataBinder.registerBinding("traEntry.cityFee", java.math.BigDecimal.class, this.kdtTraEntry, "cityFee.text");
		dataBinder.registerBinding("traEntry.otherFee", java.math.BigDecimal.class, this.kdtTraEntry, "otherFee.text");
		dataBinder.registerBinding("traEntry.persons", int.class, this.kdtTraEntry, "persons.text");
		dataBinder.registerBinding("traEntry.liveDays", int.class, this.kdtTraEntry, "liveDays.text");
		dataBinder.registerBinding("traEntry.allowance", java.math.BigDecimal.class, this.kdtTraEntry, "allowance.text");
		dataBinder.registerBinding("traEntry.other", java.math.BigDecimal.class, this.kdtTraEntry, "other.text");
		dataBinder.registerBinding("traEntry.total", java.math.BigDecimal.class, this.kdtTraEntry, "total.text");
		dataBinder.registerBinding("traEntry.liveFee", java.math.BigDecimal.class, this.kdtTraEntry, "liveFee.text");
		dataBinder.registerBinding("marketEntry", com.kingdee.eas.fdc.contract.ContractWTMarketEntryInfo.class, this.tblMarket, "userObject");
		dataBinder.registerBinding("marketEntry.date", java.util.Date.class, this.tblMarket, "date.text");
		dataBinder.registerBinding("marketEntry.rate", java.math.BigDecimal.class, this.tblMarket, "rate.text");
		dataBinder.registerBinding("marketEntry.amount", java.math.BigDecimal.class, this.tblMarket, "amount.text");
		dataBinder.registerBinding("marketEntry.content", String.class, this.tblMarket, "content.text");
		dataBinder.registerBinding("marketEntry.remark", String.class, this.tblMarket, "remark.text");
		dataBinder.registerBinding("isJT", boolean.class, this.cbIsJT, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("noPaidReason", String.class, this.txtNoPaidReason, "text");
		dataBinder.registerBinding("account", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtAccount, "data");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtConCharge, "data");
		dataBinder.registerBinding("allInvoiceAmt", double.class, this.txtAllInvoiceAmt, "value");
		dataBinder.registerBinding("fdcDepConPlan", com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo.class, this.prmtPlanProject, "data");
		dataBinder.registerBinding("applierOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtApplierOrgUnit, "data");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtApplierCompany, "data");
		dataBinder.registerBinding("payBillType", com.kingdee.eas.fi.cas.PaymentBillTypeInfo.class, this.prmtPayBillType, "data");
		dataBinder.registerBinding("invoiceEntry", com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryInfo.class, this.kdtInvoiceEntry, "userObject");
		dataBinder.registerBinding("invoiceEntry.invoiceType", com.kingdee.eas.fdc.contract.app.WTInvoiceTypeEnum.class, this.kdtInvoiceEntry, "invoiceTypeDesc.text");
		dataBinder.registerBinding("invoiceEntry.totalAmount", java.math.BigDecimal.class, this.kdtInvoiceEntry, "totalPriceAndTax.text");
		dataBinder.registerBinding("invoiceEntry.rate", java.math.BigDecimal.class, this.kdtInvoiceEntry, "specialVATTaxRate.text");
		dataBinder.registerBinding("invoiceEntry.amount", java.math.BigDecimal.class, this.kdtInvoiceEntry, "totalTaxAmount.text");
		dataBinder.registerBinding("invoiceEntry.invoiceNumber", String.class, this.kdtInvoiceEntry, "invoiceNumber.text");
		dataBinder.registerBinding("invoiceEntry.fromMk", int.class, this.kdtInvoiceEntry, "fromMk.text");
		dataBinder.registerBinding("invoiceEntry.bizDate", java.util.Date.class, this.kdtInvoiceEntry, "bizDate.text");
		dataBinder.registerBinding("rateAmount", java.math.BigDecimal.class, this.txtRateAmount, "value");
		dataBinder.registerBinding("taxerQua", com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.class, this.cbTaxerQua, "selectedItem");
		dataBinder.registerBinding("taxerNum", String.class, this.txtTaxerNum, "text");
		dataBinder.registerBinding("marketProject", com.kingdee.eas.fdc.contract.MarketProjectInfo.class, this.prmtMarketProject, "data");
		dataBinder.registerBinding("mpCostAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.prmtMpCostAccount, "data");
		dataBinder.registerBinding("lxNum", com.kingdee.eas.fdc.contract.BankNumInfo.class, this.prmtLxNum, "data");
		dataBinder.registerBinding("orgType", com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.class, this.cbOrgType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractWithoutTextEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"CostCenter",editData.getString("number"));
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
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
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
        		setAutoNumberByOrg("CostCenter");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("isInvoice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isBgControl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCostSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isNeedPaid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payContentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankAcct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepartment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeEntry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeEntry.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.days", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.startPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.endPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.airFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.carFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.cityFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.otherFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.persons", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.liveDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.allowance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.other", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.total", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("traEntry.liveFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry.rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry.content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isJT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("noPaidReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("allInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcDepConPlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.invoiceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.fromMk", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceEntry.bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxerQua", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxerNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mpCostAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lxNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgType", ValidateHelper.ON_SAVE);    		
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
		            this.actionEdit.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
        }
    }

    /**
     * output cbIsInvoice_itemStateChanged method
     */
    protected void cbIsInvoice_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbIsBgControl_itemStateChanged method
     */
    protected void cbIsBgControl_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output chkCostsplit_actionPerformed method
     */
    protected void chkCostsplit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkNeedPaid_actionPerformed method
     */
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_dataChanged method
     */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtApplier_dataChanged method
     */
    protected void prmtApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCostedCompany_dataChanged method
     */
    protected void prmtCostedCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cbFeeType_itemStateChanged method
     */
    protected void cbFeeType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtrealSupplier_dataChanged method
     */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtreceiveUnit_dataChanged method
     */
    protected void prmtreceiveUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboPayeeType_itemStateChanged method
     */
    protected void comboPayeeType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtcurProject_dataChanged method
     */
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractType_dataChanged method
     */
    protected void prmtContractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtsettlementType_dataChanged method
     */
    protected void prmtsettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCostedDept_dataChanged method
     */
    protected void prmtCostedDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblAttachement_tableClicked method
     */
    protected void tblAttachement_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdtBgEntry_editStopped method
     */
    protected void kdtBgEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtBgEntry_tableSelectChanged method
     */
    protected void kdtBgEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output kdtFeeEntry_editStopped method
     */
    protected void kdtFeeEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtTraEntry_editStopped method
     */
    protected void kdtTraEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblMarket_editStopped method
     */
    protected void tblMarket_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output pkauditTime_dataChanged method
     */
    protected void pkauditTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtApplierOrgUnit_dataChanged method
     */
    protected void prmtApplierOrgUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtInvoiceEntry_editStopped method
     */
    protected void kdtInvoiceEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtMarketProject_dataChanged method
     */
    protected void prmtMarketProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtMpCostAccount_willShow method
     */
    protected void prmtMpCostAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtMpCostAccount_dataChanged method
     */
    protected void prmtMpCostAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtLxNum_dataChanged method
     */
    protected void prmtLxNum_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isInvoice"));
        sic.add(new SelectorItemInfo("isBgControl"));
        sic.add(new SelectorItemInfo("isCostSplit"));
        sic.add(new SelectorItemInfo("isNeedPaid"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("invoiceNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applier.id"));
        	sic.add(new SelectorItemInfo("applier.number"));
        	sic.add(new SelectorItemInfo("applier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedCompany.id"));
        	sic.add(new SelectorItemInfo("costedCompany.number"));
        	sic.add(new SelectorItemInfo("costedCompany.name"));
		}
        sic.add(new SelectorItemInfo("feeType"));
        sic.add(new SelectorItemInfo("invoiceAmt"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payContentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payContentType.id"));
        	sic.add(new SelectorItemInfo("payContentType.number"));
        	sic.add(new SelectorItemInfo("payContentType.name"));
        	sic.add(new SelectorItemInfo("payContentType.longNumber"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("originalAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
        	sic.add(new SelectorItemInfo("curProject.displayname"));
		}
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractType.id"));
        	sic.add(new SelectorItemInfo("contractType.number"));
        	sic.add(new SelectorItemInfo("contractType.name"));
		}
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("bank"));
        sic.add(new SelectorItemInfo("bankAcct"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
        sic.add(new SelectorItemInfo("invoiceDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useDepartment.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useDepartment.id"));
        	sic.add(new SelectorItemInfo("useDepartment.number"));
        	sic.add(new SelectorItemInfo("useDepartment.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedDept.id"));
        	sic.add(new SelectorItemInfo("costedDept.number"));
        	sic.add(new SelectorItemInfo("costedDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("feeEntry.*"));
		}
		else{
			sic.add(new SelectorItemInfo("feeEntry.name"));
		}
    	sic.add(new SelectorItemInfo("feeEntry.desc"));
    	sic.add(new SelectorItemInfo("feeEntry.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("traEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("traEntry.startDate"));
    	sic.add(new SelectorItemInfo("traEntry.endDate"));
    	sic.add(new SelectorItemInfo("traEntry.days"));
    	sic.add(new SelectorItemInfo("traEntry.startPlace"));
    	sic.add(new SelectorItemInfo("traEntry.endPlace"));
    	sic.add(new SelectorItemInfo("traEntry.airFee"));
    	sic.add(new SelectorItemInfo("traEntry.carFee"));
    	sic.add(new SelectorItemInfo("traEntry.cityFee"));
    	sic.add(new SelectorItemInfo("traEntry.otherFee"));
    	sic.add(new SelectorItemInfo("traEntry.persons"));
    	sic.add(new SelectorItemInfo("traEntry.liveDays"));
    	sic.add(new SelectorItemInfo("traEntry.allowance"));
    	sic.add(new SelectorItemInfo("traEntry.other"));
    	sic.add(new SelectorItemInfo("traEntry.total"));
    	sic.add(new SelectorItemInfo("traEntry.liveFee"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("marketEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("marketEntry.date"));
    	sic.add(new SelectorItemInfo("marketEntry.rate"));
    	sic.add(new SelectorItemInfo("marketEntry.amount"));
    	sic.add(new SelectorItemInfo("marketEntry.content"));
    	sic.add(new SelectorItemInfo("marketEntry.remark"));
        sic.add(new SelectorItemInfo("isJT"));
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
        sic.add(new SelectorItemInfo("noPaidReason"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("account.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("account.id"));
        	sic.add(new SelectorItemInfo("account.number"));
        	sic.add(new SelectorItemInfo("account.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conChargeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conChargeType.id"));
        	sic.add(new SelectorItemInfo("conChargeType.number"));
        	sic.add(new SelectorItemInfo("conChargeType.name"));
		}
        sic.add(new SelectorItemInfo("allInvoiceAmt"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fdcDepConPlan.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fdcDepConPlan.id"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierOrgUnit.id"));
        	sic.add(new SelectorItemInfo("applierOrgUnit.number"));
        	sic.add(new SelectorItemInfo("applierOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierCompany.id"));
        	sic.add(new SelectorItemInfo("applierCompany.number"));
        	sic.add(new SelectorItemInfo("applierCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payBillType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payBillType.id"));
        	sic.add(new SelectorItemInfo("payBillType.number"));
        	sic.add(new SelectorItemInfo("payBillType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invoiceEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("invoiceEntry.invoiceType"));
    	sic.add(new SelectorItemInfo("invoiceEntry.totalAmount"));
    	sic.add(new SelectorItemInfo("invoiceEntry.rate"));
    	sic.add(new SelectorItemInfo("invoiceEntry.amount"));
    	sic.add(new SelectorItemInfo("invoiceEntry.invoiceNumber"));
    	sic.add(new SelectorItemInfo("invoiceEntry.fromMk"));
    	sic.add(new SelectorItemInfo("invoiceEntry.bizDate"));
        sic.add(new SelectorItemInfo("rateAmount"));
        sic.add(new SelectorItemInfo("taxerQua"));
        sic.add(new SelectorItemInfo("taxerNum"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("marketProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("marketProject.id"));
        	sic.add(new SelectorItemInfo("marketProject.number"));
        	sic.add(new SelectorItemInfo("marketProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mpCostAccount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mpCostAccount.id"));
        	sic.add(new SelectorItemInfo("mpCostAccount.number"));
        	sic.add(new SelectorItemInfo("mpCostAccount.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lxNum.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lxNum.id"));
        	sic.add(new SelectorItemInfo("lxNum.number"));
        	sic.add(new SelectorItemInfo("lxNum.name"));
		}
        sic.add(new SelectorItemInfo("orgType"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionDelVoucher_actionPerformed method
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBudget_actionPerformed method
     */
    public void actionViewBudget_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewProgramContract_actionPerformed method
     */
    public void actionViewProgramContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAccountView_actionPerformed method
     */
    public void actionAccountView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvoiceILine_actionPerformed method
     */
    public void actionInvoiceILine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvoiceALine_actionPerformed method
     */
    public void actionInvoiceALine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvoiceRLine_actionPerformed method
     */
    public void actionInvoiceRLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMALine_actionPerformed method
     */
    public void actionMALine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMRLine_actionPerformed method
     */
    public void actionMRLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMKFP_actionPerformed method
     */
    public void actionMKFP_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionDelVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionDelVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelVoucher() {
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
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionViewBgBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBgBalance() {
    	return false;
    }
	public RequestContext prepareActionViewAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachment() {
    	return false;
    }
	public RequestContext prepareactionViewBudget(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewBudget() {
    	return false;
    }
	public RequestContext prepareActionProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgram() {
    	return false;
    }
	public RequestContext prepareActionViewProgramContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewProgramContract() {
    	return false;
    }
	public RequestContext prepareActionAccountView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAccountView() {
    	return false;
    }
	public RequestContext prepareActionInvoiceILine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvoiceILine() {
    	return false;
    }
	public RequestContext prepareActionInvoiceALine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvoiceALine() {
    	return false;
    }
	public RequestContext prepareActionInvoiceRLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvoiceRLine() {
    	return false;
    }
	public RequestContext prepareActionMALine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMALine() {
    	return false;
    }
	public RequestContext prepareActionMRLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMRLine() {
    	return false;
    }
	public RequestContext prepareActionMKFP(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMKFP() {
    	return false;
    }

    /**
     * output ActionViewBgBalance class
     */     
    protected class ActionViewBgBalance extends ItemAction {     
    
        public ActionViewBgBalance()
        {
            this(null);
        }

        public ActionViewBgBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBgBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachment class
     */     
    protected class ActionViewAttachment extends ItemAction {     
    
        public ActionViewAttachment()
        {
            this(null);
        }

        public ActionViewAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output actionViewBudget class
     */     
    protected class actionViewBudget extends ItemAction {     
    
        public actionViewBudget()
        {
            this(null);
        }

        public actionViewBudget(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionViewBudget.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "actionViewBudget", "actionViewBudget_actionPerformed", e);
        }
    }

    /**
     * output ActionProgram class
     */     
    protected class ActionProgram extends ItemAction {     
    
        public ActionProgram()
        {
            this(null);
        }

        public ActionProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionViewProgramContract class
     */     
    protected class ActionViewProgramContract extends ItemAction {     
    
        public ActionViewProgramContract()
        {
            this(null);
        }

        public ActionViewProgramContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewProgramContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewProgramContract", "actionViewProgramContract_actionPerformed", e);
        }
    }

    /**
     * output ActionAccountView class
     */     
    protected class ActionAccountView extends ItemAction {     
    
        public ActionAccountView()
        {
            this(null);
        }

        public ActionAccountView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAccountView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAccountView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAccountView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionAccountView", "actionAccountView_actionPerformed", e);
        }
    }

    /**
     * output ActionInvoiceILine class
     */     
    protected class ActionInvoiceILine extends ItemAction {     
    
        public ActionInvoiceILine()
        {
            this(null);
        }

        public ActionInvoiceILine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvoiceILine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceILine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceILine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionInvoiceILine", "actionInvoiceILine_actionPerformed", e);
        }
    }

    /**
     * output ActionInvoiceALine class
     */     
    protected class ActionInvoiceALine extends ItemAction {     
    
        public ActionInvoiceALine()
        {
            this(null);
        }

        public ActionInvoiceALine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvoiceALine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceALine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceALine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionInvoiceALine", "actionInvoiceALine_actionPerformed", e);
        }
    }

    /**
     * output ActionInvoiceRLine class
     */     
    protected class ActionInvoiceRLine extends ItemAction {     
    
        public ActionInvoiceRLine()
        {
            this(null);
        }

        public ActionInvoiceRLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvoiceRLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceRLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoiceRLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionInvoiceRLine", "actionInvoiceRLine_actionPerformed", e);
        }
    }

    /**
     * output ActionMALine class
     */     
    protected class ActionMALine extends ItemAction {     
    
        public ActionMALine()
        {
            this(null);
        }

        public ActionMALine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMALine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMALine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMALine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionMALine", "actionMALine_actionPerformed", e);
        }
    }

    /**
     * output ActionMRLine class
     */     
    protected class ActionMRLine extends ItemAction {     
    
        public ActionMRLine()
        {
            this(null);
        }

        public ActionMRLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMRLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMRLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMRLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionMRLine", "actionMRLine_actionPerformed", e);
        }
    }

    /**
     * output ActionMKFP class
     */     
    protected class ActionMKFP extends ItemAction {     
    
        public ActionMKFP()
        {
            this(null);
        }

        public ActionMKFP(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMKFP.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMKFP.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMKFP.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionMKFP", "actionMKFP_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractWithoutTextEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblAttachement;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("originalAmount",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}