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
public abstract class AbstractSupplierChangeConfirmEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierChangeConfirmEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommed;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxRegisterNumber;
    protected com.kingdee.bos.ctrl.swing.KDContainer contCurrInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer ContWeCharInfo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kspContent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combIsPass;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierGrade;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommed;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegisterNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaster;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTelephone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContractor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPM;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxQua;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProvince;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRegisterNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaster;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTelePhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPMPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPM;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWechatBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWechatBankAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWechatTaxRegister;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProvinceNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtQuaLevleNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCityNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriseKindNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRegisterNumberNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMasterNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTelephoneNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFaxNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddressNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRegisterMoneyNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorPhoneNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPMPhoneNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPMNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxerQuaNew;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWechatBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSpl;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWechatBankAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegisterNo;
    protected com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSupplierChangeConfirmEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierChangeConfirmEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setExtendProperty("canForewarn", "true");
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contSupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommed = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxRegisterNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.ContWeCharInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kspContent = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSupplierNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtInviteType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSupplierGrade = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRecommed = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxRegisterNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuaLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaster = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTelephone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conContractor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conContractorPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPM = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxQua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProvince = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtQuaLevel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCity = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMaster = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTelePhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterMoney = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPMPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPM = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxerQua = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conTaxerQua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWechatBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWechatBankAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWechatTaxRegister = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtProvinceNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtQuaLevleNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCityNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEnterpriseKindNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterNumberNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMasterNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTelephoneNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFaxNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAddressNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterMoneyNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorPhoneNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPMPhoneNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPMNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxerQuaNew = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWechatBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSpl = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWechatBankAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxRegisterNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.kdtEntry.setName("kdtEntry");
        this.contSupplierName.setName("contSupplierName");
        this.contSupplierNumber.setName("contSupplierNumber");
        this.contIsPass.setName("contIsPass");
        this.contInviteType.setName("contInviteType");
        this.contSupplierGrade.setName("contSupplierGrade");
        this.contBank.setName("contBank");
        this.contBankAccount.setName("contBankAccount");
        this.contRecommed.setName("contRecommed");
        this.contTaxRegisterNumber.setName("contTaxRegisterNumber");
        this.contCurrInfo.setName("contCurrInfo");
        this.ContWeCharInfo.setName("ContWeCharInfo");
        this.kspContent.setName("kspContent");
        this.prmtCreator.setName("prmtCreator");
        this.kdpCreateTime.setName("kdpCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtSupplierName.setName("txtSupplierName");
        this.txtSupplierNumber.setName("txtSupplierNumber");
        this.combIsPass.setName("combIsPass");
        this.txtInviteType.setName("txtInviteType");
        this.txtSupplierGrade.setName("txtSupplierGrade");
        this.txtBank.setName("txtBank");
        this.txtBankAccount.setName("txtBankAccount");
        this.txtRecommed.setName("txtRecommed");
        this.txtTaxRegisterNumber.setName("txtTaxRegisterNumber");
        this.contProvince.setName("contProvince");
        this.contQuaLevel.setName("contQuaLevel");
        this.contCity.setName("contCity");
        this.contEnterpriseKind.setName("contEnterpriseKind");
        this.contRegisterNumber.setName("contRegisterNumber");
        this.contMaster.setName("contMaster");
        this.contTelephone.setName("contTelephone");
        this.contFax.setName("contFax");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.contRegisterMoney.setName("contRegisterMoney");
        this.conContractor.setName("conContractor");
        this.conContractorPhone.setName("conContractorPhone");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contPM.setName("contPM");
        this.contTaxQua.setName("contTaxQua");
        this.txtProvince.setName("txtProvince");
        this.txtQuaLevel.setName("txtQuaLevel");
        this.txtCity.setName("txtCity");
        this.txtEnterpriseKind.setName("txtEnterpriseKind");
        this.txtRegisterNumber.setName("txtRegisterNumber");
        this.txtMaster.setName("txtMaster");
        this.txtTelePhone.setName("txtTelePhone");
        this.txtFax.setName("txtFax");
        this.txtAddress.setName("txtAddress");
        this.txtRegisterMoney.setName("txtRegisterMoney");
        this.txtContractor.setName("txtContractor");
        this.txtContractorPhone.setName("txtContractorPhone");
        this.txtPMPhone.setName("txtPMPhone");
        this.txtPM.setName("txtPM");
        this.txtTaxerQua.setName("txtTaxerQua");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.conTaxerQua.setName("conTaxerQua");
        this.contWechatBank.setName("contWechatBank");
        this.contSpl.setName("contSpl");
        this.contWechatBankAccount.setName("contWechatBankAccount");
        this.contWechatTaxRegister.setName("contWechatTaxRegister");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.txtProvinceNew.setName("txtProvinceNew");
        this.txtQuaLevleNew.setName("txtQuaLevleNew");
        this.txtCityNew.setName("txtCityNew");
        this.txtEnterpriseKindNew.setName("txtEnterpriseKindNew");
        this.txtRegisterNumberNew.setName("txtRegisterNumberNew");
        this.txtMasterNew.setName("txtMasterNew");
        this.txtTelephoneNew.setName("txtTelephoneNew");
        this.txtFaxNew.setName("txtFaxNew");
        this.txtAddressNew.setName("txtAddressNew");
        this.txtRegisterMoneyNew.setName("txtRegisterMoneyNew");
        this.txtContractorNew.setName("txtContractorNew");
        this.txtContractorPhoneNew.setName("txtContractorPhoneNew");
        this.txtPMPhoneNew.setName("txtPMPhoneNew");
        this.txtPMNew.setName("txtPMNew");
        this.txtTaxerQuaNew.setName("txtTaxerQuaNew");
        this.txtWechatBank.setName("txtWechatBank");
        this.txtSpl.setName("txtSpl");
        this.txtWechatBankAccount.setName("txtWechatBankAccount");
        this.txtTaxRegisterNo.setName("txtTaxRegisterNo");
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
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"propertyValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"propertyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{propertyValue}</t:Cell><t:Cell>$Resource{propertyName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));		
        this.kdtEntry.setVisible(false);

                this.kdtEntry.putBindContents("editData",new String[] {"propertyValue","propertyName"});


        // contSupplierName		
        this.contSupplierName.setBoundLabelText(resHelper.getString("contSupplierName.boundLabelText"));		
        this.contSupplierName.setBoundLabelUnderline(true);		
        this.contSupplierName.setBoundLabelLength(100);		
        this.contSupplierName.setEnabled(false);
        // contSupplierNumber		
        this.contSupplierNumber.setBoundLabelText(resHelper.getString("contSupplierNumber.boundLabelText"));		
        this.contSupplierNumber.setBoundLabelUnderline(true);		
        this.contSupplierNumber.setBoundLabelLength(100);		
        this.contSupplierNumber.setEnabled(false);
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelUnderline(true);		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setEnabled(false);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelUnderline(true);		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setEnabled(false);
        // contSupplierGrade		
        this.contSupplierGrade.setBoundLabelText(resHelper.getString("contSupplierGrade.boundLabelText"));		
        this.contSupplierGrade.setBoundLabelUnderline(true);		
        this.contSupplierGrade.setBoundLabelLength(100);		
        this.contSupplierGrade.setEnabled(false);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelUnderline(true);		
        this.contBank.setBoundLabelLength(100);		
        this.contBank.setEnabled(false);		
        this.contBank.setVisible(false);
        // contBankAccount		
        this.contBankAccount.setBoundLabelText(resHelper.getString("contBankAccount.boundLabelText"));		
        this.contBankAccount.setEnabled(false);		
        this.contBankAccount.setBoundLabelUnderline(true);		
        this.contBankAccount.setBoundLabelLength(100);		
        this.contBankAccount.setVisible(false);
        // contRecommed		
        this.contRecommed.setBoundLabelText(resHelper.getString("contRecommed.boundLabelText"));		
        this.contRecommed.setBoundLabelUnderline(true);		
        this.contRecommed.setBoundLabelLength(100);		
        this.contRecommed.setEnabled(false);
        // contTaxRegisterNumber		
        this.contTaxRegisterNumber.setBoundLabelText(resHelper.getString("contTaxRegisterNumber.boundLabelText"));		
        this.contTaxRegisterNumber.setBoundLabelUnderline(true);		
        this.contTaxRegisterNumber.setBoundLabelLength(100);		
        this.contTaxRegisterNumber.setEnabled(false);		
        this.contTaxRegisterNumber.setVisible(false);
        // contCurrInfo		
        this.contCurrInfo.setTitle(resHelper.getString("contCurrInfo.title"));		
        this.contCurrInfo.setOpaque(false);		
        this.contCurrInfo.setVisible(false);
        // ContWeCharInfo		
        this.ContWeCharInfo.setTitle(resHelper.getString("ContWeCharInfo.title"));		
        this.ContWeCharInfo.setOpaque(false);
        // kspContent
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kdpCreateTime		
        this.kdpCreateTime.setEnabled(false);
        // txtNumber
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // txtSupplierName		
        this.txtSupplierName.setEnabled(false);
        // txtSupplierNumber		
        this.txtSupplierNumber.setEnabled(false);
        // combIsPass		
        this.combIsPass.setEnabled(false);
        // txtInviteType		
        this.txtInviteType.setEnabled(false);
        // txtSupplierGrade		
        this.txtSupplierGrade.setEnabled(false);
        // txtBank		
        this.txtBank.setEnabled(false);
        // txtBankAccount		
        this.txtBankAccount.setEnabled(false);
        // txtRecommed		
        this.txtRecommed.setEnabled(false);
        // txtTaxRegisterNumber		
        this.txtTaxRegisterNumber.setEnabled(false);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelUnderline(true);		
        this.contProvince.setEnabled(false);		
        this.contProvince.setBoundLabelLength(100);
        // contQuaLevel		
        this.contQuaLevel.setBoundLabelText(resHelper.getString("contQuaLevel.boundLabelText"));		
        this.contQuaLevel.setBoundLabelUnderline(true);		
        this.contQuaLevel.setEnabled(false);		
        this.contQuaLevel.setBoundLabelLength(100);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelUnderline(true);		
        this.contCity.setBoundLabelLength(100);
        // contEnterpriseKind		
        this.contEnterpriseKind.setBoundLabelText(resHelper.getString("contEnterpriseKind.boundLabelText"));		
        this.contEnterpriseKind.setBoundLabelUnderline(true);		
        this.contEnterpriseKind.setEnabled(false);		
        this.contEnterpriseKind.setBoundLabelLength(100);
        // contRegisterNumber		
        this.contRegisterNumber.setBoundLabelText(resHelper.getString("contRegisterNumber.boundLabelText"));		
        this.contRegisterNumber.setEnabled(false);		
        this.contRegisterNumber.setBoundLabelUnderline(true);		
        this.contRegisterNumber.setBoundLabelLength(100);
        // contMaster		
        this.contMaster.setBoundLabelText(resHelper.getString("contMaster.boundLabelText"));		
        this.contMaster.setEnabled(false);		
        this.contMaster.setBoundLabelUnderline(true);		
        this.contMaster.setBoundLabelLength(100);
        // contTelephone		
        this.contTelephone.setBoundLabelText(resHelper.getString("contTelephone.boundLabelText"));		
        this.contTelephone.setEnabled(false);		
        this.contTelephone.setBoundLabelUnderline(true);		
        this.contTelephone.setBoundLabelLength(100);
        // contFax		
        this.contFax.setBoundLabelText(resHelper.getString("contFax.boundLabelText"));		
        this.contFax.setEnabled(false);		
        this.contFax.setBoundLabelUnderline(true);		
        this.contFax.setBoundLabelLength(100);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setEnabled(false);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // contRegisterMoney		
        this.contRegisterMoney.setBoundLabelText(resHelper.getString("contRegisterMoney.boundLabelText"));		
        this.contRegisterMoney.setEnabled(false);		
        this.contRegisterMoney.setBoundLabelUnderline(true);		
        this.contRegisterMoney.setBoundLabelLength(100);
        // conContractor		
        this.conContractor.setBoundLabelText(resHelper.getString("conContractor.boundLabelText"));		
        this.conContractor.setEnabled(false);		
        this.conContractor.setBoundLabelUnderline(true);		
        this.conContractor.setBoundLabelLength(100);
        // conContractorPhone		
        this.conContractorPhone.setBoundLabelText(resHelper.getString("conContractorPhone.boundLabelText"));		
        this.conContractorPhone.setEnabled(false);		
        this.conContractorPhone.setBoundLabelUnderline(true);		
        this.conContractorPhone.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setEnabled(false);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // contPM		
        this.contPM.setBoundLabelText(resHelper.getString("contPM.boundLabelText"));		
        this.contPM.setEnabled(false);		
        this.contPM.setBoundLabelUnderline(true);		
        this.contPM.setBoundLabelLength(100);
        // contTaxQua		
        this.contTaxQua.setBoundLabelText(resHelper.getString("contTaxQua.boundLabelText"));		
        this.contTaxQua.setEnabled(false);		
        this.contTaxQua.setBoundLabelUnderline(true);		
        this.contTaxQua.setBoundLabelLength(100);
        // txtProvince		
        this.txtProvince.setEnabled(false);
        // txtQuaLevel		
        this.txtQuaLevel.setEnabled(false);
        // txtCity		
        this.txtCity.setEnabled(false);
        // txtEnterpriseKind		
        this.txtEnterpriseKind.setEnabled(false);
        // txtRegisterNumber		
        this.txtRegisterNumber.setEnabled(false);
        // txtMaster		
        this.txtMaster.setEnabled(false);
        // txtTelePhone		
        this.txtTelePhone.setEnabled(false);
        // txtFax		
        this.txtFax.setEnabled(false);
        // txtAddress		
        this.txtAddress.setEnabled(false);
        // txtRegisterMoney		
        this.txtRegisterMoney.setEnabled(false);
        // txtContractor		
        this.txtContractor.setEnabled(false);
        // txtContractorPhone		
        this.txtContractorPhone.setEnabled(false);
        // txtPMPhone		
        this.txtPMPhone.setEnabled(false);
        // txtPM		
        this.txtPM.setEnabled(false);
        // txtTaxerQua		
        this.txtTaxerQua.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setEnabled(false);		
        this.kDLabelContainer1.setBoundLabelLength(130);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setEnabled(false);		
        this.kDLabelContainer3.setBoundLabelLength(130);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(130);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setEnabled(false);		
        this.kDLabelContainer6.setBoundLabelLength(130);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setEnabled(false);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(130);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setEnabled(false);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(130);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setEnabled(false);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelLength(130);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setEnabled(false);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelLength(130);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setEnabled(false);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setBoundLabelLength(130);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setEnabled(false);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setBoundLabelLength(130);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setEnabled(false);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);		
        this.kDLabelContainer13.setBoundLabelLength(130);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setEnabled(false);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);		
        this.kDLabelContainer14.setBoundLabelLength(130);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setEnabled(false);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setBoundLabelLength(130);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setEnabled(false);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setBoundLabelLength(130);
        // conTaxerQua		
        this.conTaxerQua.setBoundLabelText(resHelper.getString("conTaxerQua.boundLabelText"));		
        this.conTaxerQua.setEnabled(false);		
        this.conTaxerQua.setBoundLabelUnderline(true);		
        this.conTaxerQua.setBoundLabelLength(130);
        // contWechatBank		
        this.contWechatBank.setBoundLabelText(resHelper.getString("contWechatBank.boundLabelText"));		
        this.contWechatBank.setBoundLabelUnderline(true);		
        this.contWechatBank.setBoundLabelLength(130);		
        this.contWechatBank.setEnabled(false);
        // contSpl		
        this.contSpl.setBoundLabelText(resHelper.getString("contSpl.boundLabelText"));		
        this.contSpl.setBoundLabelUnderline(true);		
        this.contSpl.setBoundLabelLength(130);		
        this.contSpl.setEnabled(false);
        // contWechatBankAccount		
        this.contWechatBankAccount.setBoundLabelText(resHelper.getString("contWechatBankAccount.boundLabelText"));		
        this.contWechatBankAccount.setBoundLabelUnderline(true);		
        this.contWechatBankAccount.setBoundLabelLength(130);		
        this.contWechatBankAccount.setEnabled(false);
        // contWechatTaxRegister		
        this.contWechatTaxRegister.setBoundLabelText(resHelper.getString("contWechatTaxRegister.boundLabelText"));		
        this.contWechatTaxRegister.setBoundLabelUnderline(true);		
        this.contWechatTaxRegister.setBoundLabelLength(130);		
        this.contWechatTaxRegister.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));
        // txtDescription		
        this.txtDescription.setEnabled(false);
        // txtProvinceNew		
        this.txtProvinceNew.setEnabled(false);
        // txtQuaLevleNew		
        this.txtQuaLevleNew.setEnabled(false);		
        this.txtQuaLevleNew.setForeground(new java.awt.Color(255,0,0));		
        this.txtQuaLevleNew.setDisabledTextColor(new java.awt.Color(255,0,0));
        // txtCityNew		
        this.txtCityNew.setEnabled(false);
        // txtEnterpriseKindNew		
        this.txtEnterpriseKindNew.setEnabled(false);
        // txtRegisterNumberNew		
        this.txtRegisterNumberNew.setEnabled(false);
        // txtMasterNew		
        this.txtMasterNew.setEnabled(false);
        // txtTelephoneNew		
        this.txtTelephoneNew.setEnabled(false);
        // txtFaxNew		
        this.txtFaxNew.setEnabled(false);
        // txtAddressNew		
        this.txtAddressNew.setEnabled(false);
        // txtRegisterMoneyNew		
        this.txtRegisterMoneyNew.setEnabled(false);
        // txtContractorNew		
        this.txtContractorNew.setEnabled(false);
        // txtContractorPhoneNew		
        this.txtContractorPhoneNew.setEnabled(false);
        // txtPMPhoneNew		
        this.txtPMPhoneNew.setEnabled(false);
        // txtPMNew		
        this.txtPMNew.setEnabled(false);
        // txtTaxerQuaNew		
        this.txtTaxerQuaNew.setEnabled(false);
        // txtWechatBank		
        this.txtWechatBank.setEnabled(false);
        // txtSpl		
        this.txtSpl.setEnabled(false);
        // txtWechatBankAccount		
        this.txtWechatBankAccount.setEnabled(false);
        // txtTaxRegisterNo		
        this.txtTaxRegisterNo.setEnabled(false);
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
        contCreator.setBounds(new Rectangle(728, 585, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(728, 585, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(13, 585, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(13, 585, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(417, 615, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(417, 615, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(728, 606, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(728, 606, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(14, 606, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(14, 606, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntry.setBounds(new Rectangle(864, 616, 150, 100));
        this.add(kdtEntry, new KDLayout.Constraints(864, 616, 150, 100, 0));
        contSupplierName.setBounds(new Rectangle(20, 29, 270, 19));
        this.add(contSupplierName, new KDLayout.Constraints(20, 29, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierNumber.setBounds(new Rectangle(20, 59, 270, 19));
        this.add(contSupplierNumber, new KDLayout.Constraints(20, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPass.setBounds(new Rectangle(370, 59, 270, 19));
        this.add(contIsPass, new KDLayout.Constraints(370, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(720, 59, 270, 19));
        this.add(contInviteType, new KDLayout.Constraints(720, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierGrade.setBounds(new Rectangle(23, 88, 270, 19));
        this.add(contSupplierGrade, new KDLayout.Constraints(23, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBank.setBounds(new Rectangle(936, 7, 270, 19));
        this.add(contBank, new KDLayout.Constraints(936, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBankAccount.setBounds(new Rectangle(348, 2, 270, 19));
        this.add(contBankAccount, new KDLayout.Constraints(348, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRecommed.setBounds(new Rectangle(372, 87, 270, 19));
        this.add(contRecommed, new KDLayout.Constraints(372, 87, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxRegisterNumber.setBounds(new Rectangle(636, 2, 270, 19));
        this.add(contTaxRegisterNumber, new KDLayout.Constraints(636, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrInfo.setBounds(new Rectangle(247, 605, 479, 413));
        this.add(contCurrInfo, new KDLayout.Constraints(247, 605, 479, 413, 0));
        ContWeCharInfo.setBounds(new Rectangle(25, 115, 973, 459));
        this.add(ContWeCharInfo, new KDLayout.Constraints(25, 115, 973, 459, 0));
        kspContent.setBounds(new Rectangle(759, 573, 268, 184));
        this.add(kspContent, new KDLayout.Constraints(759, 573, 268, 184, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kdpCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSupplierName
        contSupplierName.setBoundEditor(txtSupplierName);
        //contSupplierNumber
        contSupplierNumber.setBoundEditor(txtSupplierNumber);
        //contIsPass
        contIsPass.setBoundEditor(combIsPass);
        //contInviteType
        contInviteType.setBoundEditor(txtInviteType);
        //contSupplierGrade
        contSupplierGrade.setBoundEditor(txtSupplierGrade);
        //contBank
        contBank.setBoundEditor(txtBank);
        //contBankAccount
        contBankAccount.setBoundEditor(txtBankAccount);
        //contRecommed
        contRecommed.setBoundEditor(txtRecommed);
        //contTaxRegisterNumber
        contTaxRegisterNumber.setBoundEditor(txtTaxRegisterNumber);
        //contCurrInfo
        contCurrInfo.getContentPane().setLayout(new KDLayout());
        contCurrInfo.getContentPane().putClientProperty("OriginalBounds", new Rectangle(247, 605, 479, 413));        contProvince.setBounds(new Rectangle(10, 37, 445, 19));
        contCurrInfo.getContentPane().add(contProvince, new KDLayout.Constraints(10, 37, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQuaLevel.setBounds(new Rectangle(10, 12, 445, 19));
        contCurrInfo.getContentPane().add(contQuaLevel, new KDLayout.Constraints(10, 12, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(10, 62, 445, 19));
        contCurrInfo.getContentPane().add(contCity, new KDLayout.Constraints(10, 62, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriseKind.setBounds(new Rectangle(10, 87, 445, 19));
        contCurrInfo.getContentPane().add(contEnterpriseKind, new KDLayout.Constraints(10, 87, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegisterNumber.setBounds(new Rectangle(10, 112, 445, 19));
        contCurrInfo.getContentPane().add(contRegisterNumber, new KDLayout.Constraints(10, 112, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaster.setBounds(new Rectangle(10, 162, 445, 19));
        contCurrInfo.getContentPane().add(contMaster, new KDLayout.Constraints(10, 162, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTelephone.setBounds(new Rectangle(10, 187, 445, 19));
        contCurrInfo.getContentPane().add(contTelephone, new KDLayout.Constraints(10, 187, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFax.setBounds(new Rectangle(10, 212, 445, 19));
        contCurrInfo.getContentPane().add(contFax, new KDLayout.Constraints(10, 212, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(10, 237, 445, 19));
        contCurrInfo.getContentPane().add(kDLabelContainer5, new KDLayout.Constraints(10, 237, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegisterMoney.setBounds(new Rectangle(10, 262, 445, 19));
        contCurrInfo.getContentPane().add(contRegisterMoney, new KDLayout.Constraints(10, 262, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContractor.setBounds(new Rectangle(10, 287, 445, 19));
        contCurrInfo.getContentPane().add(conContractor, new KDLayout.Constraints(10, 287, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContractorPhone.setBounds(new Rectangle(10, 312, 445, 19));
        contCurrInfo.getContentPane().add(conContractorPhone, new KDLayout.Constraints(10, 312, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 362, 445, 19));
        contCurrInfo.getContentPane().add(kDLabelContainer2, new KDLayout.Constraints(10, 362, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPM.setBounds(new Rectangle(10, 337, 445, 19));
        contCurrInfo.getContentPane().add(contPM, new KDLayout.Constraints(10, 337, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxQua.setBounds(new Rectangle(10, 137, 445, 19));
        contCurrInfo.getContentPane().add(contTaxQua, new KDLayout.Constraints(10, 137, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contProvince
        contProvince.setBoundEditor(txtProvince);
        //contQuaLevel
        contQuaLevel.setBoundEditor(txtQuaLevel);
        //contCity
        contCity.setBoundEditor(txtCity);
        //contEnterpriseKind
        contEnterpriseKind.setBoundEditor(txtEnterpriseKind);
        //contRegisterNumber
        contRegisterNumber.setBoundEditor(txtRegisterNumber);
        //contMaster
        contMaster.setBoundEditor(txtMaster);
        //contTelephone
        contTelephone.setBoundEditor(txtTelePhone);
        //contFax
        contFax.setBoundEditor(txtFax);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtAddress);
        //contRegisterMoney
        contRegisterMoney.setBoundEditor(txtRegisterMoney);
        //conContractor
        conContractor.setBoundEditor(txtContractor);
        //conContractorPhone
        conContractorPhone.setBoundEditor(txtContractorPhone);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtPMPhone);
        //contPM
        contPM.setBoundEditor(txtPM);
        //contTaxQua
        contTaxQua.setBoundEditor(txtTaxerQua);
        //ContWeCharInfo
        ContWeCharInfo.getContentPane().setLayout(new KDLayout());
        ContWeCharInfo.getContentPane().putClientProperty("OriginalBounds", new Rectangle(25, 115, 973, 459));        kDLabelContainer1.setBounds(new Rectangle(518, 12, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer1, new KDLayout.Constraints(518, 12, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(15, 12, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer3, new KDLayout.Constraints(15, 12, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(15, 43, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer4, new KDLayout.Constraints(15, 43, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(518, 43, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer6, new KDLayout.Constraints(518, 43, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(15, 74, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer7, new KDLayout.Constraints(15, 74, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(518, 136, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer8, new KDLayout.Constraints(518, 136, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(518, 105, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer9, new KDLayout.Constraints(518, 105, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(15, 105, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer10, new KDLayout.Constraints(15, 105, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(15, 136, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer11, new KDLayout.Constraints(15, 136, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(15, 167, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer12, new KDLayout.Constraints(15, 167, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(15, 198, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer13, new KDLayout.Constraints(15, 198, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(15, 229, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer14, new KDLayout.Constraints(15, 229, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(13, 297, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer15, new KDLayout.Constraints(13, 297, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(15, 260, 445, 19));
        ContWeCharInfo.getContentPane().add(kDLabelContainer16, new KDLayout.Constraints(15, 260, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conTaxerQua.setBounds(new Rectangle(518, 74, 445, 19));
        ContWeCharInfo.getContentPane().add(conTaxerQua, new KDLayout.Constraints(518, 74, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWechatBank.setBounds(new Rectangle(518, 229, 445, 19));
        ContWeCharInfo.getContentPane().add(contWechatBank, new KDLayout.Constraints(518, 229, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSpl.setBounds(new Rectangle(518, 167, 445, 19));
        ContWeCharInfo.getContentPane().add(contSpl, new KDLayout.Constraints(518, 167, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWechatBankAccount.setBounds(new Rectangle(518, 260, 445, 19));
        ContWeCharInfo.getContentPane().add(contWechatBankAccount, new KDLayout.Constraints(518, 260, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWechatTaxRegister.setBounds(new Rectangle(518, 198, 445, 19));
        ContWeCharInfo.getContentPane().add(contWechatTaxRegister, new KDLayout.Constraints(518, 198, 445, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(17, 326, 270, 19));
        ContWeCharInfo.getContentPane().add(contDescription, new KDLayout.Constraints(17, 326, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtDescription.setBounds(new Rectangle(18, 354, 950, 66));
        ContWeCharInfo.getContentPane().add(txtDescription, new KDLayout.Constraints(18, 354, 950, 66, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtProvinceNew);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtQuaLevleNew);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtCityNew);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtEnterpriseKindNew);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtRegisterNumberNew);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtMasterNew);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtTelephoneNew);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtFaxNew);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtAddressNew);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtRegisterMoneyNew);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtContractorNew);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtContractorPhoneNew);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtPMPhoneNew);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtPMNew);
        //conTaxerQua
        conTaxerQua.setBoundEditor(txtTaxerQuaNew);
        //contWechatBank
        contWechatBank.setBoundEditor(txtWechatBank);
        //contSpl
        contSpl.setBoundEditor(txtSpl);
        //contWechatBankAccount
        contWechatBankAccount.setBoundEditor(txtWechatBankAccount);
        //contWechatTaxRegister
        contWechatTaxRegister.setBoundEditor(txtTaxRegisterNo);

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
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.propertyValue", String.class, this.kdtEntry, "propertyValue.text");
		dataBinder.registerBinding("entry.propertyName", String.class, this.kdtEntry, "propertyName.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kdpCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("supplier.name", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("supplier.number", String.class, this.txtSupplierNumber, "text");
		dataBinder.registerBinding("supplier.isPass", com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.class, this.combIsPass, "selectedItem");
		dataBinder.registerBinding("supplier.inviteType.name", String.class, this.txtInviteType, "text");
		dataBinder.registerBinding("supplier.grade.name", String.class, this.txtSupplierGrade, "text");
		dataBinder.registerBinding("supplier.bankName", String.class, this.txtBank, "text");
		dataBinder.registerBinding("supplier.bankCount", String.class, this.txtBankAccount, "text");
		dataBinder.registerBinding("supplier.recommended", String.class, this.txtRecommed, "text");
		dataBinder.registerBinding("supplier.taxRegisterNo", String.class, this.txtTaxRegisterNumber, "text");
		dataBinder.registerBinding("supplier.province.name", String.class, this.txtProvince, "text");
		dataBinder.registerBinding("supplier.quaLevelEntry", com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection.class, this.txtQuaLevel, "userObject");
		dataBinder.registerBinding("supplier.city.name", String.class, this.txtCity, "text");
		dataBinder.registerBinding("supplier.enterpriseKind", com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum.class, this.txtEnterpriseKind, "text");
		dataBinder.registerBinding("supplier.bizRegisterNo", String.class, this.txtRegisterNumber, "text");
		dataBinder.registerBinding("supplier.authorizePerson", String.class, this.txtMaster, "text");
		dataBinder.registerBinding("supplier.linkPhone", String.class, this.txtTelePhone, "text");
		dataBinder.registerBinding("supplier.linkFax", String.class, this.txtFax, "text");
		dataBinder.registerBinding("supplier.address", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("supplier.registerMoney", java.math.BigDecimal.class, this.txtRegisterMoney, "text");
		dataBinder.registerBinding("supplier.contractor", String.class, this.txtContractor, "text");
		dataBinder.registerBinding("supplier.contractorPhone", String.class, this.txtContractorPhone, "text");
		dataBinder.registerBinding("supplier.managerPhone", String.class, this.txtPMPhone, "text");
		dataBinder.registerBinding("supplier.taxerQua", com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.class, this.txtTaxerQua, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierChangeConfirmEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo)ov;
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
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.propertyValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.propertyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.isPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.inviteType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.grade.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.bankName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.bankCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.taxRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.province.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.quaLevelEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.city.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.enterpriseKind", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.bizRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.authorizePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.linkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.linkFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.registerMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.contractor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.contractorPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.managerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier.taxerQua", ValidateHelper.ON_SAVE);    		
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
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.propertyValue"));
    	sic.add(new SelectorItemInfo("entry.propertyName"));
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
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("supplier.name"));
        sic.add(new SelectorItemInfo("supplier.number"));
        sic.add(new SelectorItemInfo("supplier.isPass"));
        sic.add(new SelectorItemInfo("supplier.inviteType.name"));
        sic.add(new SelectorItemInfo("supplier.grade.name"));
        sic.add(new SelectorItemInfo("supplier.bankName"));
        sic.add(new SelectorItemInfo("supplier.bankCount"));
        sic.add(new SelectorItemInfo("supplier.recommended"));
        sic.add(new SelectorItemInfo("supplier.taxRegisterNo"));
        sic.add(new SelectorItemInfo("supplier.province.name"));
        sic.add(new SelectorItemInfo("supplier.quaLevelEntry"));
        sic.add(new SelectorItemInfo("supplier.city.name"));
        sic.add(new SelectorItemInfo("supplier.enterpriseKind"));
        sic.add(new SelectorItemInfo("supplier.bizRegisterNo"));
        sic.add(new SelectorItemInfo("supplier.authorizePerson"));
        sic.add(new SelectorItemInfo("supplier.linkPhone"));
        sic.add(new SelectorItemInfo("supplier.linkFax"));
        sic.add(new SelectorItemInfo("supplier.address"));
        sic.add(new SelectorItemInfo("supplier.registerMoney"));
        sic.add(new SelectorItemInfo("supplier.contractor"));
        sic.add(new SelectorItemInfo("supplier.contractorPhone"));
        sic.add(new SelectorItemInfo("supplier.managerPhone"));
        sic.add(new SelectorItemInfo("supplier.taxerQua"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierChangeConfirmEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}