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
public abstract class AbstractContractRecBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractRecBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBillReceive;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettlementNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBillReceive;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.eas.fdc.contract.ContractRecBillInfo editData = null;
    protected ActionALine actionALine = null;
    protected ActionILine actionILine = null;
    protected ActionRLine actionRLine = null;
    /**
     * output class constructor
     */
    public AbstractContractRecBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractRecBillEditUI.class.getName());
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
        //actionILine
        this.actionILine = new ActionILine(this);
        getActionManager().registerAction("actionILine", actionILine);
         this.actionILine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRLine
        this.actionRLine = new ActionRLine(this);
        getActionManager().registerAction("actionRLine", actionRLine);
         this.actionRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOriAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBillReceive = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSettlementNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOriAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtContractBillReceive = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.contAuditor.setName("contAuditor");
        this.contAmount.setName("contAmount");
        this.contDescription.setName("contDescription");
        this.contCustomer.setName("contCustomer");
        this.contAccountBank.setName("contAccountBank");
        this.contRevAccount.setName("contRevAccount");
        this.contBank.setName("contBank");
        this.contSettlementType.setName("contSettlementType");
        this.contSettlementNumber.setName("contSettlementNumber");
        this.contCurrency.setName("contCurrency");
        this.contRate.setName("contRate");
        this.contOriAmount.setName("contOriAmount");
        this.contContractBillReceive.setName("contContractBillReceive");
        this.contEntry.setName("contEntry");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtCurProject.setName("txtCurProject");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtAmount.setName("txtAmount");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtAccountBank.setName("prmtAccountBank");
        this.prmtRevAccount.setName("prmtRevAccount");
        this.prmtBank.setName("prmtBank");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtSettlementNumber.setName("txtSettlementNumber");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtRate.setName("txtRate");
        this.txtOriAmount.setName("txtOriAmount");
        this.prmtContractBillReceive.setName("prmtContractBillReceive");
        this.kdtEntry.setName("kdtEntry");
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
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contAccountBank		
        this.contAccountBank.setBoundLabelText(resHelper.getString("contAccountBank.boundLabelText"));		
        this.contAccountBank.setBoundLabelUnderline(true);		
        this.contAccountBank.setBoundLabelLength(100);
        // contRevAccount		
        this.contRevAccount.setBoundLabelText(resHelper.getString("contRevAccount.boundLabelText"));		
        this.contRevAccount.setBoundLabelLength(100);		
        this.contRevAccount.setBoundLabelUnderline(true);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelLength(100);		
        this.contBank.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contSettlementNumber		
        this.contSettlementNumber.setBoundLabelText(resHelper.getString("contSettlementNumber.boundLabelText"));		
        this.contSettlementNumber.setBoundLabelLength(100);		
        this.contSettlementNumber.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelLength(100);		
        this.contRate.setBoundLabelUnderline(true);
        // contOriAmount		
        this.contOriAmount.setBoundLabelText(resHelper.getString("contOriAmount.boundLabelText"));		
        this.contOriAmount.setBoundLabelLength(100);		
        this.contOriAmount.setBoundLabelUnderline(true);
        // contContractBillReceive		
        this.contContractBillReceive.setBoundLabelText(resHelper.getString("contContractBillReceive.boundLabelText"));		
        this.contContractBillReceive.setBoundLabelLength(100);		
        this.contContractBillReceive.setBoundLabelUnderline(true);
        // contEntry
        // txtNumber
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtCurProject		
        this.txtCurProject.setEditable(false);		
        this.txtCurProject.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setEnabled(false);
        // kDScrollPane1
        // txtDescription
        // prmtCustomer		
        this.prmtCustomer.setRequired(true);
        // prmtAccountBank		
        this.prmtAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");		
        this.prmtAccountBank.setRequired(true);
        this.prmtAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRevAccount		
        this.prmtRevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtRevAccount.setRequired(true);
        // prmtBank		
        this.prmtBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");
        this.prmtBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSettlementType		
        this.prmtSettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtSettlementType.setCommitFormat("$number$");		
        this.prmtSettlementType.setEditFormat("$number$");		
        this.prmtSettlementType.setDisplayFormat("$name$");
        // txtSettlementNumber		
        this.txtSettlementNumber.setMaxLength(44);
        // prmtCurrency		
        this.prmtCurrency.setRequired(true);		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setEnabled(false);
        // txtRate		
        this.txtRate.setDataType(1);		
        this.txtRate.setEditable(false);		
        this.txtRate.setPrecision(2);		
        this.txtRate.setEnabled(false);
        // txtOriAmount		
        this.txtOriAmount.setDataType(1);		
        this.txtOriAmount.setPrecision(2);		
        this.txtOriAmount.setEnabled(false);
        // prmtContractBillReceive		
        this.prmtContractBillReceive.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillReceiveQuery");		
        this.prmtContractBillReceive.setCommitFormat("$name$");		
        this.prmtContractBillReceive.setDisplayFormat("$name$");		
        this.prmtContractBillReceive.setEditFormat("$name$");		
        this.prmtContractBillReceive.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"oppAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntry.putBindContents("editData",new String[] {"moneyDefine","amount","oppAccount","remark"});


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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 600));
        contNumber.setBounds(new Rectangle(34, 23, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(34, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(711, 45, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(711, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(371, 23, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(371, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(34, 545, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(34, 545, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 545, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 545, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(371, 567, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(371, 567, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(34, 567, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(34, 567, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(33, 112, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(33, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(33, 135, 607, 69));
        this.add(contDescription, new KDLayout.Constraints(33, 135, 607, 69, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustomer.setBounds(new Rectangle(371, 45, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(371, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccountBank.setBounds(new Rectangle(34, 45, 270, 19));
        this.add(contAccountBank, new KDLayout.Constraints(34, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevAccount.setBounds(new Rectangle(710, 67, 270, 19));
        this.add(contRevAccount, new KDLayout.Constraints(710, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBank.setBounds(new Rectangle(34, 89, 270, 19));
        this.add(contBank, new KDLayout.Constraints(34, 89, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlementType.setBounds(new Rectangle(34, 67, 270, 19));
        this.add(contSettlementType, new KDLayout.Constraints(34, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlementNumber.setBounds(new Rectangle(371, 67, 270, 19));
        this.add(contSettlementNumber, new KDLayout.Constraints(371, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(371, 89, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(371, 89, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRate.setBounds(new Rectangle(711, 89, 270, 19));
        this.add(contRate, new KDLayout.Constraints(711, 89, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOriAmount.setBounds(new Rectangle(371, 112, 270, 19));
        this.add(contOriAmount, new KDLayout.Constraints(371, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBillReceive.setBounds(new Rectangle(711, 23, 270, 19));
        this.add(contContractBillReceive, new KDLayout.Constraints(711, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEntry.setBounds(new Rectangle(34, 213, 606, 323));
        this.add(contEntry, new KDLayout.Constraints(34, 213, 606, 323, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtCurProject);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(pkAuditTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contAccountBank
        contAccountBank.setBoundEditor(prmtAccountBank);
        //contRevAccount
        contRevAccount.setBoundEditor(prmtRevAccount);
        //contBank
        contBank.setBoundEditor(prmtBank);
        //contSettlementType
        contSettlementType.setBoundEditor(prmtSettlementType);
        //contSettlementNumber
        contSettlementNumber.setBoundEditor(txtSettlementNumber);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contOriAmount
        contOriAmount.setBoundEditor(txtOriAmount);
        //contContractBillReceive
        contContractBillReceive.setBoundEditor(prmtContractBillReceive);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);

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
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("contractBillReceive.curProject.name", String.class, this.txtCurProject, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("customer", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtCustomer, "data");
		dataBinder.registerBinding("accountBank", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtAccountBank, "data");
		dataBinder.registerBinding("revAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtRevAccount, "data");
		dataBinder.registerBinding("bank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtBank, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("settlementNumber", String.class, this.txtSettlementNumber, "text");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("rate", java.math.BigDecimal.class, this.txtRate, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtOriAmount, "value");
		dataBinder.registerBinding("contractBillReceive", com.kingdee.eas.fdc.contract.ContractBillReceiveInfo.class, this.prmtContractBillReceive, "data");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.ContractRecBillEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.kdtEntry, "moneyDefine.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.kdtEntry, "oppAccount.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractRecBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractRecBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBillReceive.curProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBillReceive", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    		
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
     * output prmtAccountBank_dataChanged method
     */
    protected void prmtAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBank_dataChanged method
     */
    protected void prmtBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Currency_dataChanged method
     */
    protected void f7Currency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("contractBillReceive.curProject.name"));
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
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("customer.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("customer.id"));
        	sic.add(new SelectorItemInfo("customer.number"));
        	sic.add(new SelectorItemInfo("customer.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accountBank.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accountBank.id"));
        	sic.add(new SelectorItemInfo("accountBank.number"));
        	sic.add(new SelectorItemInfo("accountBank.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("revAccount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("revAccount.id"));
        	sic.add(new SelectorItemInfo("revAccount.number"));
        	sic.add(new SelectorItemInfo("revAccount.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bank.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("bank.id"));
        	sic.add(new SelectorItemInfo("bank.number"));
        	sic.add(new SelectorItemInfo("bank.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
        sic.add(new SelectorItemInfo("settlementNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("originalAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractBillReceive.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractBillReceive.id"));
        	sic.add(new SelectorItemInfo("contractBillReceive.number"));
        	sic.add(new SelectorItemInfo("contractBillReceive.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.moneyDefine.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.moneyDefine.id"));
			sic.add(new SelectorItemInfo("entry.moneyDefine.name"));
        	sic.add(new SelectorItemInfo("entry.moneyDefine.number"));
		}
    	sic.add(new SelectorItemInfo("entry.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.oppAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.oppAccount.id"));
			sic.add(new SelectorItemInfo("entry.oppAccount.name"));
        	sic.add(new SelectorItemInfo("entry.oppAccount.number"));
		}
    	sic.add(new SelectorItemInfo("entry.remark"));
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
     * output actionILine_actionPerformed method
     */
    public void actionILine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRLine_actionPerformed method
     */
    public void actionRLine_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionILine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionILine() {
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
            innerActionPerformed("eas", AbstractContractRecBillEditUI.this, "ActionALine", "actionALine_actionPerformed", e);
        }
    }

    /**
     * output ActionILine class
     */     
    protected class ActionILine extends ItemAction {     
    
        public ActionILine()
        {
            this(null);
        }

        public ActionILine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionILine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionILine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionILine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractRecBillEditUI.this, "ActionILine", "actionILine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractRecBillEditUI.this, "ActionRLine", "actionRLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractRecBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}