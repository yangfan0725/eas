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
public abstract class AbstractContractInvoiceEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractInvoiceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmountCapital;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxerNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractInfo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPartB;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbInvoiceType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuyAddressAndPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuyBankNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuyTaxNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuyName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuyAddressAndPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuyBankNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuyTaxNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleAddressAndPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleBankNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleTaxNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleAddressAndPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleBankNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleTaxNo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTotalAmountCapital;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxerNum;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPublish;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevise;
    protected com.kingdee.eas.fdc.contract.ContractInvoiceInfo editData = null;
    protected ActionALine actionALine = null;
    protected ActionILine actionILine = null;
    protected ActionRLine actionRLine = null;
    /**
     * output class constructor
     */
    public AbstractContractInvoiceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractInvoiceEditUI.class.getName());
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
        this.contContractInfo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxerQua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contTotalAmountCapital = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contTaxerNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractInfo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPartB = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxerQua = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbInvoiceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBuyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuyAddressAndPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuyBankNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuyTaxNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuyAddressAndPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuyBankNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuyTaxNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSaleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleAddressAndPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleBankNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleTaxNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSaleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSaleAddressAndPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSaleBankNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSaleTaxNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtTotalAmountCapital = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtTaxerNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.menuItemPublish = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevise = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contContractInfo.setName("contContractInfo");
        this.contOrg.setName("contOrg");
        this.contContractAmount.setName("contContractAmount");
        this.contBizDate.setName("contBizDate");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contCreator.setName("contCreator");
        this.contProject.setName("contProject");
        this.contContractType.setName("contContractType");
        this.contPartB.setName("contPartB");
        this.contTaxerQua.setName("contTaxerQua");
        this.contInvoiceType.setName("contInvoiceType");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.kDContainer1.setName("kDContainer1");
        this.kDLabel1.setName("kDLabel1");
        this.kDContainer2.setName("kDContainer2");
        this.contEntry.setName("contEntry");
        this.contTotalAmountCapital.setName("contTotalAmountCapital");
        this.contTotalAmount.setName("contTotalAmount");
        this.contDescription.setName("contDescription");
        this.kDSeparator8.setName("kDSeparator8");
        this.contTaxerNum.setName("contTaxerNum");
        this.txtNumber.setName("txtNumber");
        this.txtContractInfo.setName("txtContractInfo");
        this.txtOrg.setName("txtOrg");
        this.txtContractAmount.setName("txtContractAmount");
        this.pkBizDate.setName("pkBizDate");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtCreator.setName("prmtCreator");
        this.txtProject.setName("txtProject");
        this.txtContractType.setName("txtContractType");
        this.txtPartB.setName("txtPartB");
        this.txtTaxerQua.setName("txtTaxerQua");
        this.cbInvoiceType.setName("cbInvoiceType");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.contBuyName.setName("contBuyName");
        this.contBuyAddressAndPhone.setName("contBuyAddressAndPhone");
        this.contBuyBankNo.setName("contBuyBankNo");
        this.contBuyTaxNo.setName("contBuyTaxNo");
        this.txtBuyName.setName("txtBuyName");
        this.txtBuyAddressAndPhone.setName("txtBuyAddressAndPhone");
        this.txtBuyBankNo.setName("txtBuyBankNo");
        this.txtBuyTaxNo.setName("txtBuyTaxNo");
        this.contSaleName.setName("contSaleName");
        this.contSaleAddressAndPhone.setName("contSaleAddressAndPhone");
        this.contSaleBankNo.setName("contSaleBankNo");
        this.contSaleTaxNo.setName("contSaleTaxNo");
        this.txtSaleName.setName("txtSaleName");
        this.txtSaleAddressAndPhone.setName("txtSaleAddressAndPhone");
        this.txtSaleBankNo.setName("txtSaleBankNo");
        this.txtSaleTaxNo.setName("txtSaleTaxNo");
        this.kdtEntry.setName("kdtEntry");
        this.txtTotalAmountCapital.setName("txtTotalAmountCapital");
        this.txtTotalAmount.setName("txtTotalAmount");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtTaxerNum.setName("txtTaxerNum");
        this.menuItemPublish.setName("menuItemPublish");
        this.menuItemRevise.setName("menuItemRevise");
        // CoreUI		
        this.setBorder(null);		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemMultiapprove.setEnabled(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.btnCalculator.setVisible(false);		
        this.btnCalculator.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contContractInfo		
        this.contContractInfo.setBoundLabelText(resHelper.getString("contContractInfo.boundLabelText"));		
        this.contContractInfo.setBoundLabelLength(100);		
        this.contContractInfo.setBoundLabelUnderline(true);		
        this.contContractInfo.setEnabled(false);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);		
        this.contOrg.setBoundLabelAlignment(7);		
        this.contOrg.setVisible(true);		
        this.contOrg.setEnabled(false);
        // contContractAmount		
        this.contContractAmount.setBoundLabelText(resHelper.getString("contContractAmount.boundLabelText"));		
        this.contContractAmount.setBoundLabelLength(100);		
        this.contContractAmount.setBoundLabelUnderline(true);		
        this.contContractAmount.setEnabled(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(100);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelLength(100);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setBoundLabelAlignment(7);		
        this.contProject.setVisible(true);		
        this.contProject.setEnabled(false);
        // contContractType		
        this.contContractType.setBoundLabelText(resHelper.getString("contContractType.boundLabelText"));		
        this.contContractType.setBoundLabelLength(100);		
        this.contContractType.setBoundLabelUnderline(true);		
        this.contContractType.setBoundLabelAlignment(7);		
        this.contContractType.setVisible(true);		
        this.contContractType.setEnabled(false);
        // contPartB		
        this.contPartB.setBoundLabelText(resHelper.getString("contPartB.boundLabelText"));		
        this.contPartB.setBoundLabelLength(100);		
        this.contPartB.setBoundLabelUnderline(true);		
        this.contPartB.setBoundLabelAlignment(7);		
        this.contPartB.setVisible(true);		
        this.contPartB.setEnabled(false);
        // contTaxerQua		
        this.contTaxerQua.setBoundLabelText(resHelper.getString("contTaxerQua.boundLabelText"));		
        this.contTaxerQua.setBoundLabelLength(100);		
        this.contTaxerQua.setBoundLabelUnderline(true);		
        this.contTaxerQua.setBoundLabelAlignment(7);		
        this.contTaxerQua.setVisible(true);		
        this.contTaxerQua.setEnabled(false);
        // contInvoiceType		
        this.contInvoiceType.setBoundLabelText(resHelper.getString("contInvoiceType.boundLabelText"));		
        this.contInvoiceType.setBoundLabelLength(100);		
        this.contInvoiceType.setBoundLabelUnderline(true);
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelLength(100);		
        this.contInvoiceNumber.setBoundLabelUnderline(true);		
        this.contInvoiceNumber.setBoundLabelAlignment(7);		
        this.contInvoiceNumber.setVisible(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setFont(resHelper.getFont("kDLabel1.font"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // contEntry		
        this.contEntry.setTitle(resHelper.getString("contEntry.title"));
        // contTotalAmountCapital		
        this.contTotalAmountCapital.setBoundLabelText(resHelper.getString("contTotalAmountCapital.boundLabelText"));		
        this.contTotalAmountCapital.setBoundLabelLength(100);		
        this.contTotalAmountCapital.setBoundLabelUnderline(true);
        // contTotalAmount		
        this.contTotalAmount.setBoundLabelText(resHelper.getString("contTotalAmount.boundLabelText"));		
        this.contTotalAmount.setBoundLabelLength(100);		
        this.contTotalAmount.setBoundLabelUnderline(true);		
        this.contTotalAmount.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDSeparator8
        // contTaxerNum		
        this.contTaxerNum.setBoundLabelText(resHelper.getString("contTaxerNum.boundLabelText"));		
        this.contTaxerNum.setBoundLabelLength(100);		
        this.contTaxerNum.setBoundLabelUnderline(true);		
        this.contTaxerNum.setBoundLabelAlignment(7);		
        this.contTaxerNum.setVisible(true);		
        this.contTaxerNum.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtContractInfo		
        this.txtContractInfo.setEnabled(false);
        // txtOrg		
        this.txtOrg.setMaxLength(80);		
        this.txtOrg.setVisible(true);		
        this.txtOrg.setEnabled(false);		
        this.txtOrg.setHorizontalAlignment(2);		
        this.txtOrg.setRequired(false);		
        this.txtOrg.setEditable(false);
        // txtContractAmount		
        this.txtContractAmount.setEnabled(false);		
        this.txtContractAmount.setDataType(1);		
        this.txtContractAmount.setPrecision(2);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // pkAuditTime		
        this.pkAuditTime.setVisible(true);		
        this.pkAuditTime.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setVisible(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setVisible(true);		
        this.prmtAuditor.setEditable(true);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");		
        this.prmtAuditor.setRequired(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setVisible(true);		
        this.prmtCreator.setEditable(true);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setRequired(false);
        // txtProject		
        this.txtProject.setMaxLength(80);		
        this.txtProject.setVisible(true);		
        this.txtProject.setEnabled(false);		
        this.txtProject.setHorizontalAlignment(2);		
        this.txtProject.setRequired(false);		
        this.txtProject.setEditable(false);
        // txtContractType		
        this.txtContractType.setMaxLength(80);		
        this.txtContractType.setVisible(true);		
        this.txtContractType.setEnabled(false);		
        this.txtContractType.setHorizontalAlignment(2);		
        this.txtContractType.setRequired(false);		
        this.txtContractType.setEditable(false);
        // txtPartB		
        this.txtPartB.setMaxLength(80);		
        this.txtPartB.setVisible(true);		
        this.txtPartB.setEnabled(false);		
        this.txtPartB.setHorizontalAlignment(2);		
        this.txtPartB.setRequired(false);		
        this.txtPartB.setEditable(false);
        // txtTaxerQua		
        this.txtTaxerQua.setMaxLength(80);		
        this.txtTaxerQua.setVisible(true);		
        this.txtTaxerQua.setEnabled(false);		
        this.txtTaxerQua.setHorizontalAlignment(2);		
        this.txtTaxerQua.setRequired(false);		
        this.txtTaxerQua.setEditable(false);
        // cbInvoiceType		
        this.cbInvoiceType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.InvoiceTypeEnum").toArray());		
        this.cbInvoiceType.setRequired(true);
        // txtInvoiceNumber		
        this.txtInvoiceNumber.setMaxLength(80);		
        this.txtInvoiceNumber.setVisible(true);		
        this.txtInvoiceNumber.setHorizontalAlignment(2);		
        this.txtInvoiceNumber.setRequired(true);
        // contBuyName		
        this.contBuyName.setBoundLabelText(resHelper.getString("contBuyName.boundLabelText"));		
        this.contBuyName.setBoundLabelLength(100);		
        this.contBuyName.setBoundLabelUnderline(true);
        // contBuyAddressAndPhone		
        this.contBuyAddressAndPhone.setBoundLabelText(resHelper.getString("contBuyAddressAndPhone.boundLabelText"));		
        this.contBuyAddressAndPhone.setBoundLabelLength(100);		
        this.contBuyAddressAndPhone.setBoundLabelUnderline(true);
        // contBuyBankNo		
        this.contBuyBankNo.setBoundLabelText(resHelper.getString("contBuyBankNo.boundLabelText"));		
        this.contBuyBankNo.setBoundLabelLength(100);		
        this.contBuyBankNo.setBoundLabelUnderline(true);
        // contBuyTaxNo		
        this.contBuyTaxNo.setBoundLabelText(resHelper.getString("contBuyTaxNo.boundLabelText"));		
        this.contBuyTaxNo.setBoundLabelLength(100);		
        this.contBuyTaxNo.setBoundLabelUnderline(true);
        // txtBuyName		
        this.txtBuyName.setRequired(true);
        // txtBuyAddressAndPhone		
        this.txtBuyAddressAndPhone.setRequired(true);
        // txtBuyBankNo		
        this.txtBuyBankNo.setRequired(true);
        // txtBuyTaxNo		
        this.txtBuyTaxNo.setRequired(true);
        // contSaleName		
        this.contSaleName.setBoundLabelText(resHelper.getString("contSaleName.boundLabelText"));		
        this.contSaleName.setBoundLabelLength(100);		
        this.contSaleName.setBoundLabelUnderline(true);
        // contSaleAddressAndPhone		
        this.contSaleAddressAndPhone.setBoundLabelText(resHelper.getString("contSaleAddressAndPhone.boundLabelText"));		
        this.contSaleAddressAndPhone.setBoundLabelLength(100);		
        this.contSaleAddressAndPhone.setBoundLabelUnderline(true);
        // contSaleBankNo		
        this.contSaleBankNo.setBoundLabelText(resHelper.getString("contSaleBankNo.boundLabelText"));		
        this.contSaleBankNo.setBoundLabelLength(100);		
        this.contSaleBankNo.setBoundLabelUnderline(true);
        // contSaleTaxNo		
        this.contSaleTaxNo.setBoundLabelText(resHelper.getString("contSaleTaxNo.boundLabelText"));		
        this.contSaleTaxNo.setBoundLabelLength(100);		
        this.contSaleTaxNo.setBoundLabelUnderline(true);
        // txtSaleName		
        this.txtSaleName.setRequired(true);
        // txtSaleAddressAndPhone		
        this.txtSaleAddressAndPhone.setRequired(true);
        // txtSaleBankNo		
        this.txtSaleBankNo.setRequired(true);
        // txtSaleTaxNo		
        this.txtSaleTaxNo.setRequired(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"goods\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quantity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{goods}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{rate}</t:Cell><t:Cell>$Resource{rateAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntry.putBindContents("editData",new String[] {"goods","model","unit","quantity","price","amount","rate","rateAmount"});


        // txtTotalAmountCapital		
        this.txtTotalAmountCapital.setEnabled(false);
        // txtTotalAmount		
        this.txtTotalAmount.setEnabled(false);		
        this.txtTotalAmount.setDataType(1);		
        this.txtTotalAmount.setPrecision(2);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(500);
        // txtTaxerNum		
        this.txtTaxerNum.setMaxLength(80);		
        this.txtTaxerNum.setVisible(true);		
        this.txtTaxerNum.setEnabled(false);		
        this.txtTaxerNum.setHorizontalAlignment(2);		
        this.txtTaxerNum.setRequired(false);		
        this.txtTaxerNum.setEditable(false);
        // menuItemPublish		
        this.menuItemPublish.setText(resHelper.getString("menuItemPublish.text"));
        // menuItemRevise		
        this.menuItemRevise.setText(resHelper.getString("menuItemRevise.text"));
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
        contNumber.setBounds(new Rectangle(15, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(15, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractInfo.setBounds(new Rectangle(370, 8, 270, 19));
        this.add(contContractInfo, new KDLayout.Constraints(370, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(721, 8, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(721, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractAmount.setBounds(new Rectangle(15, 52, 270, 19));
        this.add(contContractAmount, new KDLayout.Constraints(15, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(721, 120, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(721, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(370, 602, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(370, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(370, 580, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(370, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(15, 602, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(15, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(15, 580, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(15, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(15, 30, 270, 19));
        this.add(contProject, new KDLayout.Constraints(15, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractType.setBounds(new Rectangle(370, 30, 270, 19));
        this.add(contContractType, new KDLayout.Constraints(370, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartB.setBounds(new Rectangle(721, 30, 270, 19));
        this.add(contPartB, new KDLayout.Constraints(721, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaxerQua.setBounds(new Rectangle(370, 52, 270, 19));
        this.add(contTaxerQua, new KDLayout.Constraints(370, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceType.setBounds(new Rectangle(15, 120, 270, 19));
        this.add(contInvoiceType, new KDLayout.Constraints(15, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceNumber.setBounds(new Rectangle(370, 120, 270, 19));
        this.add(contInvoiceNumber, new KDLayout.Constraints(370, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(2, 145, 495, 126));
        this.add(kDContainer1, new KDLayout.Constraints(2, 145, 495, 126, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(414, 88, 251, 23));
        this.add(kDLabel1, new KDLayout.Constraints(414, 88, 251, 23, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(502, 145, 509, 126));
        this.add(kDContainer2, new KDLayout.Constraints(502, 145, 509, 126, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEntry.setBounds(new Rectangle(2, 276, 1010, 174));
        this.add(contEntry, new KDLayout.Constraints(2, 276, 1010, 174, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalAmountCapital.setBounds(new Rectangle(15, 460, 628, 19));
        this.add(contTotalAmountCapital, new KDLayout.Constraints(15, 460, 628, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalAmount.setBounds(new Rectangle(721, 460, 270, 19));
        this.add(contTotalAmount, new KDLayout.Constraints(721, 460, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(15, 482, 628, 87));
        this.add(contDescription, new KDLayout.Constraints(15, 482, 628, 87, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(-2, 78, 1018, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(-2, 78, 1018, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaxerNum.setBounds(new Rectangle(721, 52, 270, 19));
        this.add(contTaxerNum, new KDLayout.Constraints(721, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contContractInfo
        contContractInfo.setBoundEditor(txtContractInfo);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contContractAmount
        contContractAmount.setBoundEditor(txtContractAmount);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contContractType
        contContractType.setBoundEditor(txtContractType);
        //contPartB
        contPartB.setBoundEditor(txtPartB);
        //contTaxerQua
        contTaxerQua.setBoundEditor(txtTaxerQua);
        //contInvoiceType
        contInvoiceType.setBoundEditor(cbInvoiceType);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(2, 145, 495, 126));        contBuyName.setBounds(new Rectangle(14, 8, 472, 19));
        kDContainer1.getContentPane().add(contBuyName, new KDLayout.Constraints(14, 8, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuyAddressAndPhone.setBounds(new Rectangle(14, 52, 472, 19));
        kDContainer1.getContentPane().add(contBuyAddressAndPhone, new KDLayout.Constraints(14, 52, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuyBankNo.setBounds(new Rectangle(14, 76, 472, 19));
        kDContainer1.getContentPane().add(contBuyBankNo, new KDLayout.Constraints(14, 76, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuyTaxNo.setBounds(new Rectangle(14, 30, 472, 19));
        kDContainer1.getContentPane().add(contBuyTaxNo, new KDLayout.Constraints(14, 30, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBuyName
        contBuyName.setBoundEditor(txtBuyName);
        //contBuyAddressAndPhone
        contBuyAddressAndPhone.setBoundEditor(txtBuyAddressAndPhone);
        //contBuyBankNo
        contBuyBankNo.setBoundEditor(txtBuyBankNo);
        //contBuyTaxNo
        contBuyTaxNo.setBoundEditor(txtBuyTaxNo);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(502, 145, 509, 126));        contSaleName.setBounds(new Rectangle(14, 8, 472, 19));
        kDContainer2.getContentPane().add(contSaleName, new KDLayout.Constraints(14, 8, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSaleAddressAndPhone.setBounds(new Rectangle(14, 52, 472, 19));
        kDContainer2.getContentPane().add(contSaleAddressAndPhone, new KDLayout.Constraints(14, 52, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSaleBankNo.setBounds(new Rectangle(14, 76, 472, 19));
        kDContainer2.getContentPane().add(contSaleBankNo, new KDLayout.Constraints(14, 76, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSaleTaxNo.setBounds(new Rectangle(14, 30, 472, 19));
        kDContainer2.getContentPane().add(contSaleTaxNo, new KDLayout.Constraints(14, 30, 472, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSaleName
        contSaleName.setBoundEditor(txtSaleName);
        //contSaleAddressAndPhone
        contSaleAddressAndPhone.setBoundEditor(txtSaleAddressAndPhone);
        //contSaleBankNo
        contSaleBankNo.setBoundEditor(txtSaleBankNo);
        //contSaleTaxNo
        contSaleTaxNo.setBoundEditor(txtSaleTaxNo);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contTotalAmountCapital
        contTotalAmountCapital.setBoundEditor(txtTotalAmountCapital);
        //contTotalAmount
        contTotalAmount.setBoundEditor(txtTotalAmount);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contTaxerNum
        contTaxerNum.setBoundEditor(txtTaxerNum);

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
        menuBiz.add(menuItemPublish);
        menuBiz.add(menuItemRevise);
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
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
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
		dataBinder.registerBinding("contract.orgUnit.displayName", String.class, this.txtOrg, "text");
		dataBinder.registerBinding("contract.amount", java.math.BigDecimal.class, this.txtContractAmount, "value");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("contract.curProject.displayName", String.class, this.txtProject, "text");
		dataBinder.registerBinding("contract.contractType.name", String.class, this.txtContractType, "text");
		dataBinder.registerBinding("contract.partB.name", String.class, this.txtPartB, "text");
		dataBinder.registerBinding("invoiceType", com.kingdee.eas.fdc.contract.app.InvoiceTypeEnum.class, this.cbInvoiceType, "selectedItem");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtInvoiceNumber, "text");
		dataBinder.registerBinding("buyName", String.class, this.txtBuyName, "text");
		dataBinder.registerBinding("buyAddressAndPhone", String.class, this.txtBuyAddressAndPhone, "text");
		dataBinder.registerBinding("buyBankNo", String.class, this.txtBuyBankNo, "text");
		dataBinder.registerBinding("buyTaxNo", String.class, this.txtBuyTaxNo, "text");
		dataBinder.registerBinding("saleName", String.class, this.txtSaleName, "text");
		dataBinder.registerBinding("saleAddressAndPhone", String.class, this.txtSaleAddressAndPhone, "text");
		dataBinder.registerBinding("saleBankNo", String.class, this.txtSaleBankNo, "text");
		dataBinder.registerBinding("saleTaxNo", String.class, this.txtSaleTaxNo, "text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.ContractInvoiceEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.goods", String.class, this.kdtEntry, "goods.text");
		dataBinder.registerBinding("entry.model", String.class, this.kdtEntry, "model.text");
		dataBinder.registerBinding("entry.unit", String.class, this.kdtEntry, "unit.text");
		dataBinder.registerBinding("entry.quantity", java.math.BigDecimal.class, this.kdtEntry, "quantity.text");
		dataBinder.registerBinding("entry.price", java.math.BigDecimal.class, this.kdtEntry, "price.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.rate", java.math.BigDecimal.class, this.kdtEntry, "rate.text");
		dataBinder.registerBinding("entry.rateAmount", java.math.BigDecimal.class, this.kdtEntry, "rateAmount.text");
		dataBinder.registerBinding("totalAmountCapital", String.class, this.txtTotalAmountCapital, "text");
		dataBinder.registerBinding("totalAmount", java.math.BigDecimal.class, this.txtTotalAmount, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractInvoiceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractInvoiceInfo)ov;
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
		getValidateHelper().registerBindProperty("contract.orgUnit.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.curProject.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.contractType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.partB.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buyAddressAndPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buyBankNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buyTaxNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleAddressAndPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleBankNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleTaxNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.goods", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.rateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalAmountCapital", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("contract.orgUnit.displayName"));
        sic.add(new SelectorItemInfo("contract.amount"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("createTime"));
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
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("contract.curProject.displayName"));
        sic.add(new SelectorItemInfo("contract.contractType.name"));
        sic.add(new SelectorItemInfo("contract.partB.name"));
        sic.add(new SelectorItemInfo("invoiceType"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("buyName"));
        sic.add(new SelectorItemInfo("buyAddressAndPhone"));
        sic.add(new SelectorItemInfo("buyBankNo"));
        sic.add(new SelectorItemInfo("buyTaxNo"));
        sic.add(new SelectorItemInfo("saleName"));
        sic.add(new SelectorItemInfo("saleAddressAndPhone"));
        sic.add(new SelectorItemInfo("saleBankNo"));
        sic.add(new SelectorItemInfo("saleTaxNo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.goods"));
    	sic.add(new SelectorItemInfo("entry.model"));
    	sic.add(new SelectorItemInfo("entry.unit"));
    	sic.add(new SelectorItemInfo("entry.quantity"));
    	sic.add(new SelectorItemInfo("entry.price"));
    	sic.add(new SelectorItemInfo("entry.amount"));
    	sic.add(new SelectorItemInfo("entry.rate"));
    	sic.add(new SelectorItemInfo("entry.rateAmount"));
        sic.add(new SelectorItemInfo("totalAmountCapital"));
        sic.add(new SelectorItemInfo("totalAmount"));
        sic.add(new SelectorItemInfo("description"));
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
            innerActionPerformed("eas", AbstractContractInvoiceEditUI.this, "ActionALine", "actionALine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractInvoiceEditUI.this, "ActionILine", "actionILine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractInvoiceEditUI.this, "ActionRLine", "actionRLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractInvoiceEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}