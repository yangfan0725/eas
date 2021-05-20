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
public abstract class AbstractNewSupplierStockEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewSupplierStockEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBase;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStorageDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommended;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankCount;
    protected com.kingdee.bos.ctrl.swing.KDContainer contContractEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteTypeList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierFileType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierBusinessMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServiceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierSplAreaEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManagerPhone;
    protected com.kingdee.bos.ctrl.swing.KDContainer contLinkPerson;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSupplierAttachList;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSuppDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLxNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProvince;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStorageNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtPunish;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStorageDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbIsPass;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPurchaseOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommended;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankCount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtContractEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteTypeList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierFileType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierBusinessMode;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboEnterpriseKind;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtServiceType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierSplAreaEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBuildDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurrency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBizRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManager;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManagerPhone;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtLinkPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSupplierAttachList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtGrade;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbTaxerQua;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLxNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSupplierPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWebSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkMail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessNum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPeopleCount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSupplierPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWebSite;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkMail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusinessNum;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAssets;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFixedAssets;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrentAssets;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrentLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastYearProfit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAbility;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScale;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainWork;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAssets;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFixedAssets;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCurrentAssets;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLongLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCurrentLiabilities;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastYearProfit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAbility;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtScale;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtMainWork;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWorkFlowG;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAuditResult;
    protected com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo editData = null;
    protected ActionPersonAddLine actionPersonAddLine = null;
    protected ActionPersonInsertLine actionPersonInsertLine = null;
    protected ActionPersonRemoveLine actionPersonRemoveLine = null;
    protected ActionLinkPersonRemoveLine actionLinkPersonRemoveLine = null;
    protected ActionLinkPersonAddLine actionLinkPersonAddLine = null;
    protected ActionLinkPersonInsertLine actionLinkPersonInsertLine = null;
    protected ActionAttachListAddLine actionAttachListAddLine = null;
    protected ActionAttachListRemoveLine actionAttachListRemoveLine = null;
    protected ActionAttachListInsertLine actionAttachListInsertLine = null;
    protected ActionContractAddRow actionContractAddRow = null;
    protected ActionContractDelRow actionContractDelRow = null;
    protected ActionContractInsertRow actionContractInsertRow = null;
    protected ActionWorkFlowG actionWorkFlowG = null;
    protected ActionAuditResult actionAuditResult = null;
    /**
     * output class constructor
     */
    public AbstractNewSupplierStockEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewSupplierStockEditUI.class.getName());
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
        //actionPersonAddLine
        this.actionPersonAddLine = new ActionPersonAddLine(this);
        getActionManager().registerAction("actionPersonAddLine", actionPersonAddLine);
         this.actionPersonAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonInsertLine
        this.actionPersonInsertLine = new ActionPersonInsertLine(this);
        getActionManager().registerAction("actionPersonInsertLine", actionPersonInsertLine);
         this.actionPersonInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonRemoveLine
        this.actionPersonRemoveLine = new ActionPersonRemoveLine(this);
        getActionManager().registerAction("actionPersonRemoveLine", actionPersonRemoveLine);
         this.actionPersonRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLinkPersonRemoveLine
        this.actionLinkPersonRemoveLine = new ActionLinkPersonRemoveLine(this);
        getActionManager().registerAction("actionLinkPersonRemoveLine", actionLinkPersonRemoveLine);
         this.actionLinkPersonRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLinkPersonAddLine
        this.actionLinkPersonAddLine = new ActionLinkPersonAddLine(this);
        getActionManager().registerAction("actionLinkPersonAddLine", actionLinkPersonAddLine);
         this.actionLinkPersonAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLinkPersonInsertLine
        this.actionLinkPersonInsertLine = new ActionLinkPersonInsertLine(this);
        getActionManager().registerAction("actionLinkPersonInsertLine", actionLinkPersonInsertLine);
         this.actionLinkPersonInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachListAddLine
        this.actionAttachListAddLine = new ActionAttachListAddLine(this);
        getActionManager().registerAction("actionAttachListAddLine", actionAttachListAddLine);
         this.actionAttachListAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachListRemoveLine
        this.actionAttachListRemoveLine = new ActionAttachListRemoveLine(this);
        getActionManager().registerAction("actionAttachListRemoveLine", actionAttachListRemoveLine);
         this.actionAttachListRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachListInsertLine
        this.actionAttachListInsertLine = new ActionAttachListInsertLine(this);
        getActionManager().registerAction("actionAttachListInsertLine", actionAttachListInsertLine);
         this.actionAttachListInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractAddRow
        this.actionContractAddRow = new ActionContractAddRow(this);
        getActionManager().registerAction("actionContractAddRow", actionContractAddRow);
         this.actionContractAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractDelRow
        this.actionContractDelRow = new ActionContractDelRow(this);
        getActionManager().registerAction("actionContractDelRow", actionContractDelRow);
         this.actionContractDelRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractInsertRow
        this.actionContractInsertRow = new ActionContractInsertRow(this);
        getActionManager().registerAction("actionContractInsertRow", actionContractInsertRow);
         this.actionContractInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWorkFlowG
        this.actionWorkFlowG = new ActionWorkFlowG(this);
        getActionManager().registerAction("actionWorkFlowG", actionWorkFlowG);
         this.actionWorkFlowG.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAuditResult
        this.actionAuditResult = new ActionAuditResult(this);
        getActionManager().registerAction("actionAuditResult", actionAuditResult);
         this.actionAuditResult.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelBase = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contStorageDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommended = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuaLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contInviteTypeList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierFileType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierBusinessMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServiceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierSplAreaEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizRegisterNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxRegisterNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractorPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManagerPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPerson = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSupplierAttachList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxerQua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSuppDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contLxNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmtProvince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStorageNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPunish = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkStorageDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPurchaseOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRecommended = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtQuaLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBankName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankCount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtContractEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtInviteTypeList = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSupplierFileType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSupplierBusinessMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtServiceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSupplierSplAreaEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBuildDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtRegisterMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboCurrency = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBizRegisterNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxRegisterNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManager = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManagerPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtLinkPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSupplierAttachList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbTaxerQua = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtLxNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierPerson = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contWebSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkMail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPostNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusinessNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtSupplierPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtWebSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkMail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPostNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBusinessNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTotalAssets = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFixedAssets = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrentAssets = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalLiabilities = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongLiabilities = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrentLiabilities = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastYearProfit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAbility = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScale = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainWork = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTotalAssets = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFixedAssets = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCurrentAssets = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalLiabilities = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLongLiabilities = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCurrentLiabilities = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLastYearProfit = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAbility = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtScale = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtMainWork = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnWorkFlowG = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAuditResult = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDScrollPane3.setName("kDScrollPane3");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.panelBase.setName("panelBase");
        this.panelBill.setName("panelBill");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel4.setName("kDPanel4");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditDate.setName("contAuditDate");
        this.kDPanel3.setName("kDPanel3");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contProvince.setName("contProvince");
        this.contCity.setName("contCity");
        this.contAddress.setName("contAddress");
        this.contLinkPhone.setName("contLinkPhone");
        this.contLinkFax.setName("contLinkFax");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDLabel1.setName("kDLabel1");
        this.contStorageDate.setName("contStorageDate");
        this.contIsPass.setName("contIsPass");
        this.contInviteType.setName("contInviteType");
        this.contPurchaseOrgUnit.setName("contPurchaseOrgUnit");
        this.contRecommended.setName("contRecommended");
        this.contQuaLevel.setName("contQuaLevel");
        this.contBankName.setName("contBankName");
        this.contBankCount.setName("contBankCount");
        this.contContractEntry.setName("contContractEntry");
        this.contInviteTypeList.setName("contInviteTypeList");
        this.contSupplierFileType.setName("contSupplierFileType");
        this.contSupplierBusinessMode.setName("contSupplierBusinessMode");
        this.contEnterpriseKind.setName("contEnterpriseKind");
        this.contServiceType.setName("contServiceType");
        this.contSupplierSplAreaEntry.setName("contSupplierSplAreaEntry");
        this.contEnterpriseMaster.setName("contEnterpriseMaster");
        this.contBuildDate.setName("contBuildDate");
        this.contRegisterMoney.setName("contRegisterMoney");
        this.contCurrency.setName("contCurrency");
        this.contBizRegisterNo.setName("contBizRegisterNo");
        this.contTaxRegisterNo.setName("contTaxRegisterNo");
        this.contContractor.setName("contContractor");
        this.contContractorPhone.setName("contContractorPhone");
        this.contManager.setName("contManager");
        this.contManagerPhone.setName("contManagerPhone");
        this.contLinkPerson.setName("contLinkPerson");
        this.contSupplierAttachList.setName("contSupplierAttachList");
        this.kDSeparator5.setName("kDSeparator5");
        this.kDSeparator6.setName("kDSeparator6");
        this.contGrade.setName("contGrade");
        this.contTaxerQua.setName("contTaxerQua");
        this.contDescription1.setName("contDescription1");
        this.txtSuppDesc.setName("txtSuppDesc");
        this.contLxNumber.setName("contLxNumber");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtProvince.setName("prmtProvince");
        this.prmtCity.setName("prmtCity");
        this.txtAddress.setName("txtAddress");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.txtLinkFax.setName("txtLinkFax");
        this.txtStorageNumber.setName("txtStorageNumber");
        this.txtPunish.setName("txtPunish");
        this.pkStorageDate.setName("pkStorageDate");
        this.cbIsPass.setName("cbIsPass");
        this.prmtInviteType.setName("prmtInviteType");
        this.prmtPurchaseOrgUnit.setName("prmtPurchaseOrgUnit");
        this.txtRecommended.setName("txtRecommended");
        this.prmtQuaLevel.setName("prmtQuaLevel");
        this.txtBankName.setName("txtBankName");
        this.txtBankCount.setName("txtBankCount");
        this.kdtContractEntry.setName("kdtContractEntry");
        this.prmtInviteTypeList.setName("prmtInviteTypeList");
        this.prmtSupplierFileType.setName("prmtSupplierFileType");
        this.prmtSupplierBusinessMode.setName("prmtSupplierBusinessMode");
        this.comboEnterpriseKind.setName("comboEnterpriseKind");
        this.prmtServiceType.setName("prmtServiceType");
        this.prmtSupplierSplAreaEntry.setName("prmtSupplierSplAreaEntry");
        this.txtEnterpriseMaster.setName("txtEnterpriseMaster");
        this.pkBuildDate.setName("pkBuildDate");
        this.txtRegisterMoney.setName("txtRegisterMoney");
        this.comboCurrency.setName("comboCurrency");
        this.txtBizRegisterNo.setName("txtBizRegisterNo");
        this.txtTaxRegisterNo.setName("txtTaxRegisterNo");
        this.txtContractor.setName("txtContractor");
        this.txtContractorPhone.setName("txtContractorPhone");
        this.txtManager.setName("txtManager");
        this.txtManagerPhone.setName("txtManagerPhone");
        this.kdtLinkPerson.setName("kdtLinkPerson");
        this.kdtSupplierAttachList.setName("kdtSupplierAttachList");
        this.prmtGrade.setName("prmtGrade");
        this.cbTaxerQua.setName("cbTaxerQua");
        this.txtLxNumber.setName("txtLxNumber");
        this.contPeopleCount.setName("contPeopleCount");
        this.contSupplierPerson.setName("contSupplierPerson");
        this.contWebSite.setName("contWebSite");
        this.contLinkMail.setName("contLinkMail");
        this.contPostNumber.setName("contPostNumber");
        this.contBusinessNum.setName("contBusinessNum");
        this.txtPeopleCount.setName("txtPeopleCount");
        this.kdtSupplierPerson.setName("kdtSupplierPerson");
        this.txtWebSite.setName("txtWebSite");
        this.txtLinkMail.setName("txtLinkMail");
        this.txtPostNumber.setName("txtPostNumber");
        this.txtBusinessNum.setName("txtBusinessNum");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditDate.setName("pkAuditDate");
        this.contTotalAssets.setName("contTotalAssets");
        this.contFixedAssets.setName("contFixedAssets");
        this.contCurrentAssets.setName("contCurrentAssets");
        this.contTotalLiabilities.setName("contTotalLiabilities");
        this.contLongLiabilities.setName("contLongLiabilities");
        this.contCurrentLiabilities.setName("contCurrentLiabilities");
        this.contLastYearProfit.setName("contLastYearProfit");
        this.contAbility.setName("contAbility");
        this.contScale.setName("contScale");
        this.contMainWork.setName("contMainWork");
        this.txtTotalAssets.setName("txtTotalAssets");
        this.txtFixedAssets.setName("txtFixedAssets");
        this.txtCurrentAssets.setName("txtCurrentAssets");
        this.txtTotalLiabilities.setName("txtTotalLiabilities");
        this.txtLongLiabilities.setName("txtLongLiabilities");
        this.txtCurrentLiabilities.setName("txtCurrentLiabilities");
        this.txtLastYearProfit.setName("txtLastYearProfit");
        this.txtAbility.setName("txtAbility");
        this.txtScale.setName("txtScale");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtMainWork.setName("txtMainWork");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.btnWorkFlowG.setName("btnWorkFlowG");
        this.btnAuditResult.setName("btnAuditResult");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSave.setVisible(true);		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuBiz.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);
        // kDScrollPane3		
        this.kDScrollPane3.setPreferredSize(new Dimension(10,10));
        // kDTabbedPane1		
        this.kDTabbedPane1.setPreferredSize(new Dimension(1013,1100));		
        this.kDTabbedPane1.setMinimumSize(new Dimension(1013,1100));
        // panelBase		
        this.panelBase.setBorder(null);
        // panelBill		
        this.panelBill.setBorder(null);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));		
        this.kDPanel2.setVisible(false);
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));		
        this.kDPanel4.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(100);		
        this.contAuditDate.setBoundLabelUnderline(true);		
        this.contAuditDate.setEnabled(false);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));		
        this.kDPanel3.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setBoundLabelLength(100);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(100);		
        this.contLinkPhone.setBoundLabelUnderline(true);
        // contLinkFax		
        this.contLinkFax.setBoundLabelText(resHelper.getString("contLinkFax.boundLabelText"));		
        this.contLinkFax.setBoundLabelLength(100);		
        this.contLinkFax.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDScrollPane1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // contStorageDate		
        this.contStorageDate.setBoundLabelText(resHelper.getString("contStorageDate.boundLabelText"));		
        this.contStorageDate.setBoundLabelLength(100);		
        this.contStorageDate.setBoundLabelUnderline(true);
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);		
        this.contInviteType.setEnabled(false);
        // contPurchaseOrgUnit		
        this.contPurchaseOrgUnit.setBoundLabelText(resHelper.getString("contPurchaseOrgUnit.boundLabelText"));		
        this.contPurchaseOrgUnit.setBoundLabelLength(100);		
        this.contPurchaseOrgUnit.setBoundLabelUnderline(true);
        // contRecommended		
        this.contRecommended.setBoundLabelText(resHelper.getString("contRecommended.boundLabelText"));		
        this.contRecommended.setBoundLabelLength(100);		
        this.contRecommended.setBoundLabelUnderline(true);
        // contQuaLevel		
        this.contQuaLevel.setBoundLabelText(resHelper.getString("contQuaLevel.boundLabelText"));		
        this.contQuaLevel.setBoundLabelLength(100);		
        this.contQuaLevel.setBoundLabelUnderline(true);
        // contBankName		
        this.contBankName.setBoundLabelText(resHelper.getString("contBankName.boundLabelText"));		
        this.contBankName.setBoundLabelLength(100);		
        this.contBankName.setBoundLabelUnderline(true);
        // contBankCount		
        this.contBankCount.setBoundLabelText(resHelper.getString("contBankCount.boundLabelText"));		
        this.contBankCount.setBoundLabelUnderline(true);		
        this.contBankCount.setBoundLabelLength(100);
        // contContractEntry		
        this.contContractEntry.setTitle(resHelper.getString("contContractEntry.title"));		
        this.contContractEntry.setAutoscrolls(true);		
        this.contContractEntry.setEnableActive(false);
        // contInviteTypeList		
        this.contInviteTypeList.setBoundLabelText(resHelper.getString("contInviteTypeList.boundLabelText"));		
        this.contInviteTypeList.setBoundLabelLength(100);		
        this.contInviteTypeList.setBoundLabelUnderline(true);
        // contSupplierFileType		
        this.contSupplierFileType.setBoundLabelText(resHelper.getString("contSupplierFileType.boundLabelText"));		
        this.contSupplierFileType.setBoundLabelLength(100);		
        this.contSupplierFileType.setBoundLabelUnderline(true);
        // contSupplierBusinessMode		
        this.contSupplierBusinessMode.setBoundLabelText(resHelper.getString("contSupplierBusinessMode.boundLabelText"));		
        this.contSupplierBusinessMode.setBoundLabelLength(100);		
        this.contSupplierBusinessMode.setBoundLabelUnderline(true);
        // contEnterpriseKind		
        this.contEnterpriseKind.setBoundLabelText(resHelper.getString("contEnterpriseKind.boundLabelText"));		
        this.contEnterpriseKind.setBoundLabelLength(100);		
        this.contEnterpriseKind.setBoundLabelUnderline(true);
        // contServiceType		
        this.contServiceType.setBoundLabelText(resHelper.getString("contServiceType.boundLabelText"));		
        this.contServiceType.setBoundLabelLength(100);		
        this.contServiceType.setBoundLabelUnderline(true);
        // contSupplierSplAreaEntry		
        this.contSupplierSplAreaEntry.setBoundLabelText(resHelper.getString("contSupplierSplAreaEntry.boundLabelText"));		
        this.contSupplierSplAreaEntry.setBoundLabelLength(100);		
        this.contSupplierSplAreaEntry.setBoundLabelUnderline(true);
        // contEnterpriseMaster		
        this.contEnterpriseMaster.setBoundLabelText(resHelper.getString("contEnterpriseMaster.boundLabelText"));		
        this.contEnterpriseMaster.setBoundLabelLength(100);		
        this.contEnterpriseMaster.setBoundLabelUnderline(true);
        // contBuildDate		
        this.contBuildDate.setBoundLabelText(resHelper.getString("contBuildDate.boundLabelText"));		
        this.contBuildDate.setBoundLabelLength(100);		
        this.contBuildDate.setBoundLabelUnderline(true);
        // contRegisterMoney		
        this.contRegisterMoney.setBoundLabelText(resHelper.getString("contRegisterMoney.boundLabelText"));		
        this.contRegisterMoney.setBoundLabelLength(100);		
        this.contRegisterMoney.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelUnderline(true);
        // contBizRegisterNo		
        this.contBizRegisterNo.setBoundLabelText(resHelper.getString("contBizRegisterNo.boundLabelText"));		
        this.contBizRegisterNo.setBoundLabelLength(100);		
        this.contBizRegisterNo.setBoundLabelUnderline(true);
        // contTaxRegisterNo		
        this.contTaxRegisterNo.setBoundLabelText(resHelper.getString("contTaxRegisterNo.boundLabelText"));		
        this.contTaxRegisterNo.setBoundLabelLength(100);		
        this.contTaxRegisterNo.setBoundLabelUnderline(true);
        // contContractor		
        this.contContractor.setBoundLabelText(resHelper.getString("contContractor.boundLabelText"));		
        this.contContractor.setBoundLabelLength(100);		
        this.contContractor.setBoundLabelUnderline(true);
        // contContractorPhone		
        this.contContractorPhone.setBoundLabelText(resHelper.getString("contContractorPhone.boundLabelText"));		
        this.contContractorPhone.setBoundLabelLength(100);		
        this.contContractorPhone.setBoundLabelUnderline(true);
        // contManager		
        this.contManager.setBoundLabelText(resHelper.getString("contManager.boundLabelText"));		
        this.contManager.setBoundLabelLength(100);		
        this.contManager.setBoundLabelUnderline(true);
        // contManagerPhone		
        this.contManagerPhone.setBoundLabelText(resHelper.getString("contManagerPhone.boundLabelText"));		
        this.contManagerPhone.setBoundLabelLength(100);		
        this.contManagerPhone.setBoundLabelUnderline(true);
        // contLinkPerson		
        this.contLinkPerson.setTitle(resHelper.getString("contLinkPerson.title"));		
        this.contLinkPerson.setAutoscrolls(true);		
        this.contLinkPerson.setEnableActive(false);
        // contSupplierAttachList		
        this.contSupplierAttachList.setAutoscrolls(true);		
        this.contSupplierAttachList.setEnableActive(false);		
        this.contSupplierAttachList.setTitle(resHelper.getString("contSupplierAttachList.title"));
        // kDSeparator5
        // kDSeparator6
        // contGrade		
        this.contGrade.setBoundLabelText(resHelper.getString("contGrade.boundLabelText"));		
        this.contGrade.setBoundLabelLength(100);		
        this.contGrade.setBoundLabelUnderline(true);
        // contTaxerQua		
        this.contTaxerQua.setBoundLabelText(resHelper.getString("contTaxerQua.boundLabelText"));		
        this.contTaxerQua.setBoundLabelLength(100);		
        this.contTaxerQua.setBoundLabelUnderline(true);
        // contDescription1		
        this.contDescription1.setBoundLabelText(resHelper.getString("contDescription1.boundLabelText"));
        // txtSuppDesc
        // contLxNumber		
        this.contLxNumber.setBoundLabelText(resHelper.getString("contLxNumber.boundLabelText"));		
        this.contLxNumber.setBoundLabelLength(100);		
        this.contLxNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtName
        // prmtProvince		
        this.prmtProvince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProvinceQuery");
        this.prmtProvince.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProvince_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCity		
        this.prmtCity.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");
        // txtAddress		
        this.txtAddress.setMaxLength(80);
        // txtLinkPhone		
        this.txtLinkPhone.setMaxLength(80);
        // txtLinkFax		
        this.txtLinkFax.setMaxLength(80);
        // txtStorageNumber		
        this.txtStorageNumber.setMaxLength(80);		
        this.txtStorageNumber.setEnabled(false);
        // txtPunish
        // pkStorageDate		
        this.pkStorageDate.setEnabled(false);
        // cbIsPass		
        this.cbIsPass.setEnabled(false);		
        this.cbIsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
        // prmtInviteType		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setCommitFormat("$name$");		
        this.prmtInviteType.setEditFormat("$name$");		
        this.prmtInviteType.setDisplayFormat("$name$");
        this.prmtInviteType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPurchaseOrgUnit		
        this.prmtPurchaseOrgUnit.setRequired(true);		
        this.prmtPurchaseOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.PurchaseOrgUnitQuery");		
        this.prmtPurchaseOrgUnit.setCommitFormat("$number$");		
        this.prmtPurchaseOrgUnit.setEditFormat("$number$");		
        this.prmtPurchaseOrgUnit.setDisplayFormat("$name$");
        // txtRecommended		
        this.txtRecommended.setMaxLength(255);
        // prmtQuaLevel		
        this.prmtQuaLevel.setDisplayFormat("$name$");		
        this.prmtQuaLevel.setEditFormat("$number$");		
        this.prmtQuaLevel.setCommitFormat("$number$");		
        this.prmtQuaLevel.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7QuaLevelQuery");
        // txtBankName		
        this.txtBankName.setRequired(true);
        // txtBankCount		
        this.txtBankCount.setRequired(true);
        // kdtContractEntry
		String kdtContractEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"contractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"manager\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"workModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"place\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"supplierName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{contractName}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{manager}</t:Cell><t:Cell>$Resource{workModel}</t:Cell><t:Cell>$Resource{place}</t:Cell><t:Cell>$Resource{supplierName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtContractEntry.setFormatXml(resHelper.translateString("kdtContractEntry",kdtContractEntryStrXML));

        

        // prmtInviteTypeList		
        this.prmtInviteTypeList.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.InviteListTypeQuery");		
        this.prmtInviteTypeList.setCommitFormat("$name$");		
        this.prmtInviteTypeList.setDisplayFormat("$name$");		
        this.prmtInviteTypeList.setEditFormat("$name$");		
        this.prmtInviteTypeList.setRequired(true);
        // prmtSupplierFileType		
        this.prmtSupplierFileType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.SupplierFileTypeQuery");		
        this.prmtSupplierFileType.setCommitFormat("$number$");		
        this.prmtSupplierFileType.setEditFormat("$number$");		
        this.prmtSupplierFileType.setDisplayFormat("$name$");		
        this.prmtSupplierFileType.setRequired(true);
        this.prmtSupplierFileType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplierFileType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSupplierBusinessMode		
        this.prmtSupplierBusinessMode.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.SupplierBusinessModeQuery");		
        this.prmtSupplierBusinessMode.setCommitFormat("$number$");		
        this.prmtSupplierBusinessMode.setEditFormat("$number$");		
        this.prmtSupplierBusinessMode.setDisplayFormat("$name$");
        // comboEnterpriseKind		
        this.comboEnterpriseKind.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum").toArray());
        // prmtServiceType		
        this.prmtServiceType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierServiceTypeQuery");		
        this.prmtServiceType.setRequired(false);		
        this.prmtServiceType.setEditFormat("$number$");		
        this.prmtServiceType.setDisplayFormat("$name$");		
        this.prmtServiceType.setCommitFormat("$number$");		
        this.prmtServiceType.setEnabledMultiSelection(true);
        // prmtSupplierSplAreaEntry		
        this.prmtSupplierSplAreaEntry.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAreaQuery");		
        this.prmtSupplierSplAreaEntry.setEnabledMultiSelection(true);		
        this.prmtSupplierSplAreaEntry.setEditFormat("$number$");		
        this.prmtSupplierSplAreaEntry.setDisplayFormat("$name$");		
        this.prmtSupplierSplAreaEntry.setCommitFormat("$number$");
        // txtEnterpriseMaster		
        this.txtEnterpriseMaster.setMaxLength(80);
        // pkBuildDate
        // txtRegisterMoney		
        this.txtRegisterMoney.setDataType(1);		
        this.txtRegisterMoney.setPrecision(2);
        // comboCurrency		
        this.comboCurrency.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.CurrencyEnum").toArray());
        // txtBizRegisterNo		
        this.txtBizRegisterNo.setMaxLength(80);
        // txtTaxRegisterNo		
        this.txtTaxRegisterNo.setMaxLength(80);
        // txtContractor		
        this.txtContractor.setMaxLength(200);		
        this.txtContractor.setRequired(true);
        // txtContractorPhone		
        this.txtContractorPhone.setMaxLength(200);		
        this.txtContractorPhone.setRequired(true);
        // txtManager		
        this.txtManager.setMaxLength(200);
        // txtManagerPhone		
        this.txtManagerPhone.setMaxLength(200);
        // kdtLinkPerson
		String kdtLinkPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"personName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"workPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"personFax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"isDefault\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"contact\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{personName}</t:Cell><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{workPhone}</t:Cell><t:Cell>$Resource{personFax}</t:Cell><t:Cell>$Resource{isDefault}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{contact}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtLinkPerson.setFormatXml(resHelper.translateString("kdtLinkPerson",kdtLinkPersonStrXML));
        this.kdtLinkPerson.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtLinkPerson_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtSupplierAttachList
		String kdtSupplierAttachListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"attachment\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{attachment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSupplierAttachList.setFormatXml(resHelper.translateString("kdtSupplierAttachList",kdtSupplierAttachListStrXML));
        this.kdtSupplierAttachList.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtSupplierAttachList_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // prmtGrade		
        this.prmtGrade.setDisplayFormat("$name$");		
        this.prmtGrade.setEditFormat("$number$");		
        this.prmtGrade.setCommitFormat("$number$");		
        this.prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.prmtGrade.setRequired(true);		
        this.prmtGrade.setEnabled(false);
        this.prmtGrade.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtGrade_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbTaxerQua		
        this.cbTaxerQua.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.TaxerQuaEnum").toArray());		
        this.cbTaxerQua.setRequired(true);
        // txtLxNumber		
        this.txtLxNumber.setRequired(true);
        // contPeopleCount		
        this.contPeopleCount.setBoundLabelText(resHelper.getString("contPeopleCount.boundLabelText"));		
        this.contPeopleCount.setBoundLabelLength(100);		
        this.contPeopleCount.setBoundLabelUnderline(true);
        // contSupplierPerson		
        this.contSupplierPerson.setTitle(resHelper.getString("contSupplierPerson.title"));		
        this.contSupplierPerson.setAutoscrolls(true);		
        this.contSupplierPerson.setEnableActive(false);
        // contWebSite		
        this.contWebSite.setBoundLabelText(resHelper.getString("contWebSite.boundLabelText"));		
        this.contWebSite.setBoundLabelLength(100);		
        this.contWebSite.setBoundLabelUnderline(true);
        // contLinkMail		
        this.contLinkMail.setBoundLabelText(resHelper.getString("contLinkMail.boundLabelText"));		
        this.contLinkMail.setBoundLabelLength(100);		
        this.contLinkMail.setBoundLabelUnderline(true);
        // contPostNumber		
        this.contPostNumber.setBoundLabelText(resHelper.getString("contPostNumber.boundLabelText"));		
        this.contPostNumber.setBoundLabelLength(100);		
        this.contPostNumber.setBoundLabelUnderline(true);
        // contBusinessNum		
        this.contBusinessNum.setBoundLabelText(resHelper.getString("contBusinessNum.boundLabelText"));		
        this.contBusinessNum.setBoundLabelLength(100);		
        this.contBusinessNum.setBoundLabelUnderline(true);		
        this.contBusinessNum.setVisible(false);
        // txtPeopleCount		
        this.txtPeopleCount.setDataVerifierType(12);		
        this.txtPeopleCount.setSupportedEmpty(true);
        // kdtSupplierPerson
		String kdtSupplierPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSupplierPerson.setFormatXml(resHelper.translateString("kdtSupplierPerson",kdtSupplierPersonStrXML));

        

        // txtWebSite		
        this.txtWebSite.setMaxLength(80);
        // txtLinkMail		
        this.txtLinkMail.setMaxLength(80);
        // txtPostNumber		
        this.txtPostNumber.setMaxLength(80);
        // txtBusinessNum		
        this.txtBusinessNum.setMaxLength(80);
        // prmtCreator		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setRequired(true);		
        this.prmtAuditor.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setRequired(true);		
        this.pkCreateTime.setEnabled(false);
        // pkAuditDate		
        this.pkAuditDate.setEnabled(false);
        // contTotalAssets		
        this.contTotalAssets.setBoundLabelText(resHelper.getString("contTotalAssets.boundLabelText"));		
        this.contTotalAssets.setBoundLabelLength(100);		
        this.contTotalAssets.setBoundLabelUnderline(true);
        // contFixedAssets		
        this.contFixedAssets.setBoundLabelText(resHelper.getString("contFixedAssets.boundLabelText"));		
        this.contFixedAssets.setBoundLabelLength(100);		
        this.contFixedAssets.setBoundLabelUnderline(true);
        // contCurrentAssets		
        this.contCurrentAssets.setBoundLabelText(resHelper.getString("contCurrentAssets.boundLabelText"));		
        this.contCurrentAssets.setBoundLabelLength(100);		
        this.contCurrentAssets.setBoundLabelUnderline(true);
        // contTotalLiabilities		
        this.contTotalLiabilities.setBoundLabelText(resHelper.getString("contTotalLiabilities.boundLabelText"));		
        this.contTotalLiabilities.setBoundLabelLength(100);		
        this.contTotalLiabilities.setBoundLabelUnderline(true);
        // contLongLiabilities		
        this.contLongLiabilities.setBoundLabelText(resHelper.getString("contLongLiabilities.boundLabelText"));		
        this.contLongLiabilities.setBoundLabelLength(100);		
        this.contLongLiabilities.setBoundLabelUnderline(true);
        // contCurrentLiabilities		
        this.contCurrentLiabilities.setBoundLabelText(resHelper.getString("contCurrentLiabilities.boundLabelText"));		
        this.contCurrentLiabilities.setBoundLabelLength(100);		
        this.contCurrentLiabilities.setBoundLabelUnderline(true);
        // contLastYearProfit		
        this.contLastYearProfit.setBoundLabelText(resHelper.getString("contLastYearProfit.boundLabelText"));		
        this.contLastYearProfit.setBoundLabelLength(100);		
        this.contLastYearProfit.setBoundLabelUnderline(true);
        // contAbility		
        this.contAbility.setBoundLabelText(resHelper.getString("contAbility.boundLabelText"));		
        this.contAbility.setBoundLabelUnderline(true);		
        this.contAbility.setBoundLabelLength(200);
        // contScale		
        this.contScale.setBoundLabelText(resHelper.getString("contScale.boundLabelText"));		
        this.contScale.setBoundLabelUnderline(true);		
        this.contScale.setBoundLabelLength(200);
        // contMainWork		
        this.contMainWork.setBoundLabelText(resHelper.getString("contMainWork.boundLabelText"));		
        this.contMainWork.setBoundLabelLength(100);		
        this.contMainWork.setBoundLabelUnderline(true);
        // txtTotalAssets		
        this.txtTotalAssets.setDataType(1);		
        this.txtTotalAssets.setPrecision(2);
        // txtFixedAssets		
        this.txtFixedAssets.setDataType(1);		
        this.txtFixedAssets.setPrecision(2);
        // txtCurrentAssets		
        this.txtCurrentAssets.setPrecision(2);		
        this.txtCurrentAssets.setDataType(1);
        // txtTotalLiabilities		
        this.txtTotalLiabilities.setDataType(1);		
        this.txtTotalLiabilities.setPrecision(2);
        // txtLongLiabilities
        // txtCurrentLiabilities		
        this.txtCurrentLiabilities.setDataType(1);		
        this.txtCurrentLiabilities.setPrecision(2);
        // txtLastYearProfit		
        this.txtLastYearProfit.setDataType(1);		
        this.txtLastYearProfit.setPrecision(2);
        // txtAbility		
        this.txtAbility.setMaxLength(200);
        // txtScale		
        this.txtScale.setMaxLength(200);
        // kDScrollPane2
        // txtMainWork		
        this.txtMainWork.setMaxLength(1000);
        // kDTabbedPane2
        // btnWorkFlowG
        this.btnWorkFlowG.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlowG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWorkFlowG.setText(resHelper.getString("btnWorkFlowG.text"));
        // btnAuditResult
        this.btnAuditResult.setAction((IItemAction)ActionProxyFactory.getProxy(actionAuditResult, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAuditResult.setText(resHelper.getString("btnAuditResult.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 1500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 1500));
        kDScrollPane3.setBounds(new Rectangle(5, 20, 1013, 1500));
        this.add(kDScrollPane3, new KDLayout.Constraints(5, 20, 1013, 1500, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDScrollPane3
        kDScrollPane3.getViewport().add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(panelBase, resHelper.getString("panelBase.constraints"));
        kDTabbedPane1.add(panelBill, resHelper.getString("panelBill.constraints"));
        //panelBase
        panelBase.setLayout(new KDLayout());
        panelBase.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 1067));        kDPanel1.setBounds(new Rectangle(-2, 3, 1013, 947));
        panelBase.add(kDPanel1, new KDLayout.Constraints(-2, 3, 1013, 947, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(14, 1169, 724, 96));
        panelBase.add(kDPanel2, new KDLayout.Constraints(14, 1169, 724, 96, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel4.setBounds(new Rectangle(14, 1289, 946, 41));
        panelBase.add(kDPanel4, new KDLayout.Constraints(14, 1289, 946, 41, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(5, 957, 270, 19));
        panelBase.add(contCreator, new KDLayout.Constraints(5, 957, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(5, 979, 270, 19));
        panelBase.add(contAuditor, new KDLayout.Constraints(5, 979, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 957, 270, 19));
        panelBase.add(contCreateTime, new KDLayout.Constraints(371, 957, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditDate.setBounds(new Rectangle(371, 979, 270, 19));
        panelBase.add(contAuditDate, new KDLayout.Constraints(371, 979, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel3.setBounds(new Rectangle(19, 1345, 708, 58));
        panelBase.add(kDPanel3, new KDLayout.Constraints(19, 1345, 708, 58, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(-2, 3, 1013, 947));        contNumber.setBounds(new Rectangle(15, 15, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(15, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(370, 15, 270, 19));
        kDPanel1.add(contName, new KDLayout.Constraints(370, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProvince.setBounds(new Rectangle(15, 149, 270, 19));
        kDPanel1.add(contProvince, new KDLayout.Constraints(15, 149, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(370, 149, 270, 19));
        kDPanel1.add(contCity, new KDLayout.Constraints(370, 149, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAddress.setBounds(new Rectangle(723, 171, 270, 19));
        kDPanel1.add(contAddress, new KDLayout.Constraints(723, 171, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkPhone.setBounds(new Rectangle(15, 171, 270, 19));
        kDPanel1.add(contLinkPhone, new KDLayout.Constraints(15, 171, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkFax.setBounds(new Rectangle(370, 171, 270, 19));
        kDPanel1.add(contLinkFax, new KDLayout.Constraints(370, 171, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(15, 103, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(15, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(15, 351, 346, 125));
        kDPanel1.add(kDScrollPane1, new KDLayout.Constraints(15, 351, 346, 125, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(15, 328, 270, 19));
        kDPanel1.add(kDLabel1, new KDLayout.Constraints(15, 328, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStorageDate.setBounds(new Rectangle(370, 103, 270, 19));
        kDPanel1.add(contStorageDate, new KDLayout.Constraints(370, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPass.setBounds(new Rectangle(723, 15, 270, 19));
        kDPanel1.add(contIsPass, new KDLayout.Constraints(723, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteType.setBounds(new Rectangle(15, 59, 270, 19));
        kDPanel1.add(contInviteType, new KDLayout.Constraints(15, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurchaseOrgUnit.setBounds(new Rectangle(15, 81, 270, 19));
        kDPanel1.add(contPurchaseOrgUnit, new KDLayout.Constraints(15, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommended.setBounds(new Rectangle(723, 149, 270, 19));
        kDPanel1.add(contRecommended, new KDLayout.Constraints(723, 149, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQuaLevel.setBounds(new Rectangle(723, 81, 270, 19));
        kDPanel1.add(contQuaLevel, new KDLayout.Constraints(723, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBankName.setBounds(new Rectangle(15, 237, 270, 19));
        kDPanel1.add(contBankName, new KDLayout.Constraints(15, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBankCount.setBounds(new Rectangle(370, 237, 270, 19));
        kDPanel1.add(contBankCount, new KDLayout.Constraints(370, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractEntry.setBounds(new Rectangle(370, 328, 624, 144));
        kDPanel1.add(contContractEntry, new KDLayout.Constraints(370, 328, 624, 144, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteTypeList.setBounds(new Rectangle(370, 59, 270, 19));
        kDPanel1.add(contInviteTypeList, new KDLayout.Constraints(370, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierFileType.setBounds(new Rectangle(15, 37, 270, 19));
        kDPanel1.add(contSupplierFileType, new KDLayout.Constraints(15, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierBusinessMode.setBounds(new Rectangle(370, 37, 270, 19));
        kDPanel1.add(contSupplierBusinessMode, new KDLayout.Constraints(370, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriseKind.setBounds(new Rectangle(723, 59, 270, 19));
        kDPanel1.add(contEnterpriseKind, new KDLayout.Constraints(723, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contServiceType.setBounds(new Rectangle(723, 103, 270, 19));
        kDPanel1.add(contServiceType, new KDLayout.Constraints(723, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierSplAreaEntry.setBounds(new Rectangle(370, 81, 270, 19));
        kDPanel1.add(contSupplierSplAreaEntry, new KDLayout.Constraints(370, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriseMaster.setBounds(new Rectangle(15, 193, 270, 19));
        kDPanel1.add(contEnterpriseMaster, new KDLayout.Constraints(15, 193, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildDate.setBounds(new Rectangle(370, 193, 270, 19));
        kDPanel1.add(contBuildDate, new KDLayout.Constraints(370, 193, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegisterMoney.setBounds(new Rectangle(723, 193, 163, 19));
        kDPanel1.add(contRegisterMoney, new KDLayout.Constraints(723, 193, 163, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(900, 193, 93, 18));
        kDPanel1.add(contCurrency, new KDLayout.Constraints(900, 193, 93, 18, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizRegisterNo.setBounds(new Rectangle(15, 215, 270, 19));
        kDPanel1.add(contBizRegisterNo, new KDLayout.Constraints(15, 215, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxRegisterNo.setBounds(new Rectangle(723, 215, 270, 19));
        kDPanel1.add(contTaxRegisterNo, new KDLayout.Constraints(723, 215, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractor.setBounds(new Rectangle(15, 259, 270, 19));
        kDPanel1.add(contContractor, new KDLayout.Constraints(15, 259, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractorPhone.setBounds(new Rectangle(370, 259, 270, 19));
        kDPanel1.add(contContractorPhone, new KDLayout.Constraints(370, 259, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contManager.setBounds(new Rectangle(15, 281, 270, 19));
        kDPanel1.add(contManager, new KDLayout.Constraints(15, 281, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contManagerPhone.setBounds(new Rectangle(370, 281, 270, 19));
        kDPanel1.add(contManagerPhone, new KDLayout.Constraints(370, 281, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPerson.setBounds(new Rectangle(370, 478, 624, 165));
        kDPanel1.add(contLinkPerson, new KDLayout.Constraints(370, 478, 624, 165, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierAttachList.setBounds(new Rectangle(15, 646, 979, 284));
        kDPanel1.add(contSupplierAttachList, new KDLayout.Constraints(15, 646, 979, 284, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(7, 136, 996, 10));
        kDPanel1.add(kDSeparator5, new KDLayout.Constraints(7, 136, 996, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator6.setBounds(new Rectangle(7, 313, 996, 10));
        kDPanel1.add(kDSeparator6, new KDLayout.Constraints(7, 313, 996, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contGrade.setBounds(new Rectangle(723, 37, 270, 19));
        kDPanel1.add(contGrade, new KDLayout.Constraints(723, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaxerQua.setBounds(new Rectangle(370, 215, 270, 19));
        kDPanel1.add(contTaxerQua, new KDLayout.Constraints(370, 215, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription1.setBounds(new Rectangle(15, 478, 270, 19));
        kDPanel1.add(contDescription1, new KDLayout.Constraints(15, 478, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtSuppDesc.setBounds(new Rectangle(15, 505, 345, 137));
        kDPanel1.add(txtSuppDesc, new KDLayout.Constraints(15, 505, 345, 137, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLxNumber.setBounds(new Rectangle(723, 237, 270, 19));
        kDPanel1.add(contLxNumber, new KDLayout.Constraints(723, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contProvince
        contProvince.setBoundEditor(prmtProvince);
        //contCity
        contCity.setBoundEditor(prmtCity);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contLinkFax
        contLinkFax.setBoundEditor(txtLinkFax);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtStorageNumber);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtPunish, null);
        //contStorageDate
        contStorageDate.setBoundEditor(pkStorageDate);
        //contIsPass
        contIsPass.setBoundEditor(cbIsPass);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contPurchaseOrgUnit
        contPurchaseOrgUnit.setBoundEditor(prmtPurchaseOrgUnit);
        //contRecommended
        contRecommended.setBoundEditor(txtRecommended);
        //contQuaLevel
        contQuaLevel.setBoundEditor(prmtQuaLevel);
        //contBankName
        contBankName.setBoundEditor(txtBankName);
        //contBankCount
        contBankCount.setBoundEditor(txtBankCount);
        //contContractEntry
contContractEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contContractEntry.getContentPane().add(kdtContractEntry, BorderLayout.CENTER);
        //contInviteTypeList
        contInviteTypeList.setBoundEditor(prmtInviteTypeList);
        //contSupplierFileType
        contSupplierFileType.setBoundEditor(prmtSupplierFileType);
        //contSupplierBusinessMode
        contSupplierBusinessMode.setBoundEditor(prmtSupplierBusinessMode);
        //contEnterpriseKind
        contEnterpriseKind.setBoundEditor(comboEnterpriseKind);
        //contServiceType
        contServiceType.setBoundEditor(prmtServiceType);
        //contSupplierSplAreaEntry
        contSupplierSplAreaEntry.setBoundEditor(prmtSupplierSplAreaEntry);
        //contEnterpriseMaster
        contEnterpriseMaster.setBoundEditor(txtEnterpriseMaster);
        //contBuildDate
        contBuildDate.setBoundEditor(pkBuildDate);
        //contRegisterMoney
        contRegisterMoney.setBoundEditor(txtRegisterMoney);
        //contCurrency
        contCurrency.setBoundEditor(comboCurrency);
        //contBizRegisterNo
        contBizRegisterNo.setBoundEditor(txtBizRegisterNo);
        //contTaxRegisterNo
        contTaxRegisterNo.setBoundEditor(txtTaxRegisterNo);
        //contContractor
        contContractor.setBoundEditor(txtContractor);
        //contContractorPhone
        contContractorPhone.setBoundEditor(txtContractorPhone);
        //contManager
        contManager.setBoundEditor(txtManager);
        //contManagerPhone
        contManagerPhone.setBoundEditor(txtManagerPhone);
        //contLinkPerson
contLinkPerson.getContentPane().setLayout(new BorderLayout(0, 0));        contLinkPerson.getContentPane().add(kdtLinkPerson, BorderLayout.CENTER);
        //contSupplierAttachList
contSupplierAttachList.getContentPane().setLayout(new BorderLayout(0, 0));        contSupplierAttachList.getContentPane().add(kdtSupplierAttachList, BorderLayout.CENTER);
        //contGrade
        contGrade.setBoundEditor(prmtGrade);
        //contTaxerQua
        contTaxerQua.setBoundEditor(cbTaxerQua);
        //contLxNumber
        contLxNumber.setBoundEditor(txtLxNumber);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(14, 1169, 724, 96));        contPeopleCount.setBounds(new Rectangle(345, 68, 270, 19));
        kDPanel2.add(contPeopleCount, new KDLayout.Constraints(345, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierPerson.setBounds(new Rectangle(15, 130, 979, 110));
        kDPanel2.add(contSupplierPerson, new KDLayout.Constraints(15, 130, 979, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contWebSite.setBounds(new Rectangle(724, 90, 270, 19));
        kDPanel2.add(contWebSite, new KDLayout.Constraints(724, 90, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkMail.setBounds(new Rectangle(725, 55, 270, 19));
        kDPanel2.add(contLinkMail, new KDLayout.Constraints(725, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPostNumber.setBounds(new Rectangle(47, 75, 270, 19));
        kDPanel2.add(contPostNumber, new KDLayout.Constraints(47, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBusinessNum.setBounds(new Rectangle(712, 32, 270, 19));
        kDPanel2.add(contBusinessNum, new KDLayout.Constraints(712, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contPeopleCount
        contPeopleCount.setBoundEditor(txtPeopleCount);
        //contSupplierPerson
contSupplierPerson.getContentPane().setLayout(new BorderLayout(0, 0));        contSupplierPerson.getContentPane().add(kdtSupplierPerson, BorderLayout.CENTER);
        //contWebSite
        contWebSite.setBoundEditor(txtWebSite);
        //contLinkMail
        contLinkMail.setBoundEditor(txtLinkMail);
        //contPostNumber
        contPostNumber.setBoundEditor(txtPostNumber);
        //contBusinessNum
        contBusinessNum.setBoundEditor(txtBusinessNum);
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(14, 1289, 946, 41));        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditDate
        contAuditDate.setBoundEditor(pkAuditDate);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(19, 1345, 708, 58));        contTotalAssets.setBounds(new Rectangle(15, 15, 270, 19));
        kDPanel3.add(contTotalAssets, new KDLayout.Constraints(15, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFixedAssets.setBounds(new Rectangle(370, 15, 270, 19));
        kDPanel3.add(contFixedAssets, new KDLayout.Constraints(370, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrentAssets.setBounds(new Rectangle(725, 15, 270, 19));
        kDPanel3.add(contCurrentAssets, new KDLayout.Constraints(725, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalLiabilities.setBounds(new Rectangle(15, 37, 270, 19));
        kDPanel3.add(contTotalLiabilities, new KDLayout.Constraints(15, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLongLiabilities.setBounds(new Rectangle(370, 37, 270, 19));
        kDPanel3.add(contLongLiabilities, new KDLayout.Constraints(370, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrentLiabilities.setBounds(new Rectangle(725, 37, 270, 19));
        kDPanel3.add(contCurrentLiabilities, new KDLayout.Constraints(725, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastYearProfit.setBounds(new Rectangle(15, 59, 270, 19));
        kDPanel3.add(contLastYearProfit, new KDLayout.Constraints(15, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAbility.setBounds(new Rectangle(15, 81, 629, 19));
        kDPanel3.add(contAbility, new KDLayout.Constraints(15, 81, 629, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScale.setBounds(new Rectangle(15, 103, 629, 19));
        kDPanel3.add(contScale, new KDLayout.Constraints(15, 103, 629, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMainWork.setBounds(new Rectangle(15, 125, 629, 70));
        kDPanel3.add(contMainWork, new KDLayout.Constraints(15, 125, 629, 70, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contTotalAssets
        contTotalAssets.setBoundEditor(txtTotalAssets);
        //contFixedAssets
        contFixedAssets.setBoundEditor(txtFixedAssets);
        //contCurrentAssets
        contCurrentAssets.setBoundEditor(txtCurrentAssets);
        //contTotalLiabilities
        contTotalLiabilities.setBoundEditor(txtTotalLiabilities);
        //contLongLiabilities
        contLongLiabilities.setBoundEditor(txtLongLiabilities);
        //contCurrentLiabilities
        contCurrentLiabilities.setBoundEditor(txtCurrentLiabilities);
        //contLastYearProfit
        contLastYearProfit.setBoundEditor(txtLastYearProfit);
        //contAbility
        contAbility.setBoundEditor(txtAbility);
        //contScale
        contScale.setBoundEditor(txtScale);
        //contMainWork
        contMainWork.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtMainWork, null);
        //panelBill
        panelBill.setLayout(new KDLayout());
        panelBill.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 1067));        kDTabbedPane2.setBounds(new Rectangle(0, 0, 1013, 600));
        panelBill.add(kDTabbedPane2, new KDLayout.Constraints(0, 0, 1013, 600, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnAuditResult);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("supplierDescription", String.class, this.txtSuppDesc, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("province", com.kingdee.eas.basedata.assistant.ProvinceInfo.class, this.prmtProvince, "data");
		dataBinder.registerBinding("city", com.kingdee.eas.basedata.assistant.CityInfo.class, this.prmtCity, "data");
		dataBinder.registerBinding("address", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("linkPhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("linkFax", String.class, this.txtLinkFax, "text");
		dataBinder.registerBinding("storageNumber", String.class, this.txtStorageNumber, "text");
		dataBinder.registerBinding("punish", String.class, this.txtPunish, "text");
		dataBinder.registerBinding("storageDate", java.util.Date.class, this.pkStorageDate, "value");
		dataBinder.registerBinding("isPass", com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.class, this.cbIsPass, "selectedItem");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("purchaseOrgUnit", com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo.class, this.prmtPurchaseOrgUnit, "data");
		dataBinder.registerBinding("recommended", String.class, this.txtRecommended, "text");
		dataBinder.registerBinding("bankName", String.class, this.txtBankName, "text");
		dataBinder.registerBinding("bankCount", String.class, this.txtBankCount, "text");
		dataBinder.registerBinding("supplierFileType", com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo.class, this.prmtSupplierFileType, "data");
		dataBinder.registerBinding("supplierBusinessMode", com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeInfo.class, this.prmtSupplierBusinessMode, "data");
		dataBinder.registerBinding("enterpriseKind", com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum.class, this.comboEnterpriseKind, "selectedItem");
		dataBinder.registerBinding("enterpriseMaster", String.class, this.txtEnterpriseMaster, "text");
		dataBinder.registerBinding("buildDate", java.util.Date.class, this.pkBuildDate, "value");
		dataBinder.registerBinding("registerMoney", java.math.BigDecimal.class, this.txtRegisterMoney, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.fdc.invite.supplier.CurrencyEnum.class, this.comboCurrency, "selectedItem");
		dataBinder.registerBinding("bizRegisterNo", String.class, this.txtBizRegisterNo, "text");
		dataBinder.registerBinding("taxRegisterNo", String.class, this.txtTaxRegisterNo, "text");
		dataBinder.registerBinding("contractor", String.class, this.txtContractor, "text");
		dataBinder.registerBinding("contractorPhone", String.class, this.txtContractorPhone, "text");
		dataBinder.registerBinding("manager", String.class, this.txtManager, "text");
		dataBinder.registerBinding("managerPhone", String.class, this.txtManagerPhone, "text");
		dataBinder.registerBinding("grade", com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo.class, this.prmtGrade, "data");
		dataBinder.registerBinding("taxerQua", com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.class, this.cbTaxerQua, "selectedItem");
		dataBinder.registerBinding("lxNumber", String.class, this.txtLxNumber, "text");
		dataBinder.registerBinding("peopleCount", java.math.BigDecimal.class, this.txtPeopleCount, "value");
		dataBinder.registerBinding("webSite", String.class, this.txtWebSite, "text");
		dataBinder.registerBinding("linkMail", String.class, this.txtLinkMail, "text");
		dataBinder.registerBinding("postNumber", String.class, this.txtPostNumber, "text");
		dataBinder.registerBinding("businessNum", String.class, this.txtBusinessNum, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkAuditDate, "value");
		dataBinder.registerBinding("totalAssets", java.math.BigDecimal.class, this.txtTotalAssets, "value");
		dataBinder.registerBinding("fixedAssets", java.math.BigDecimal.class, this.txtFixedAssets, "value");
		dataBinder.registerBinding("currentAssets", java.math.BigDecimal.class, this.txtCurrentAssets, "value");
		dataBinder.registerBinding("totalLiabilities", java.math.BigDecimal.class, this.txtTotalLiabilities, "value");
		dataBinder.registerBinding("longLiabilities", java.math.BigDecimal.class, this.txtLongLiabilities, "value");
		dataBinder.registerBinding("currentLiabilities", java.math.BigDecimal.class, this.txtCurrentLiabilities, "value");
		dataBinder.registerBinding("lastYearProfit", java.math.BigDecimal.class, this.txtLastYearProfit, "value");
		dataBinder.registerBinding("ability", String.class, this.txtAbility, "text");
		dataBinder.registerBinding("scale", String.class, this.txtScale, "text");
		dataBinder.registerBinding("mainWork", String.class, this.txtMainWork, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)ov;
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
		getValidateHelper().registerBindProperty("supplierDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("storageNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("punish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("storageDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierFileType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBusinessMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriseKind", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriseMaster", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("registerMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractorPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("manager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxerQua", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lxNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("peopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("webSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkMail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalAssets", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fixedAssets", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currentAssets", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalLiabilities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longLiabilities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currentLiabilities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastYearProfit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ability", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainWork", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output prmtProvince_dataChanged method
     */
    protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteType_dataChanged method
     */
    protected void prmtInviteType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSupplierFileType_dataChanged method
     */
    protected void prmtSupplierFileType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtLinkPerson_editStopped method
     */
    protected void kdtLinkPerson_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSupplierAttachList_tableClicked method
     */
    protected void kdtSupplierAttachList_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output prmtGrade_dataChanged method
     */
    protected void prmtGrade_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("supplierDescription"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("province.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("province.id"));
        	sic.add(new SelectorItemInfo("province.number"));
        	sic.add(new SelectorItemInfo("province.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("city.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("city.id"));
        	sic.add(new SelectorItemInfo("city.number"));
        	sic.add(new SelectorItemInfo("city.name"));
		}
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("linkPhone"));
        sic.add(new SelectorItemInfo("linkFax"));
        sic.add(new SelectorItemInfo("storageNumber"));
        sic.add(new SelectorItemInfo("punish"));
        sic.add(new SelectorItemInfo("storageDate"));
        sic.add(new SelectorItemInfo("isPass"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("purchaseOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("purchaseOrgUnit.id"));
        	sic.add(new SelectorItemInfo("purchaseOrgUnit.number"));
        	sic.add(new SelectorItemInfo("purchaseOrgUnit.name"));
		}
        sic.add(new SelectorItemInfo("recommended"));
        sic.add(new SelectorItemInfo("bankName"));
        sic.add(new SelectorItemInfo("bankCount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplierFileType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplierFileType.id"));
        	sic.add(new SelectorItemInfo("supplierFileType.number"));
        	sic.add(new SelectorItemInfo("supplierFileType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplierBusinessMode.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplierBusinessMode.id"));
        	sic.add(new SelectorItemInfo("supplierBusinessMode.number"));
        	sic.add(new SelectorItemInfo("supplierBusinessMode.name"));
		}
        sic.add(new SelectorItemInfo("enterpriseKind"));
        sic.add(new SelectorItemInfo("enterpriseMaster"));
        sic.add(new SelectorItemInfo("buildDate"));
        sic.add(new SelectorItemInfo("registerMoney"));
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("bizRegisterNo"));
        sic.add(new SelectorItemInfo("taxRegisterNo"));
        sic.add(new SelectorItemInfo("contractor"));
        sic.add(new SelectorItemInfo("contractorPhone"));
        sic.add(new SelectorItemInfo("manager"));
        sic.add(new SelectorItemInfo("managerPhone"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("grade.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("grade.id"));
        	sic.add(new SelectorItemInfo("grade.number"));
        	sic.add(new SelectorItemInfo("grade.name"));
		}
        sic.add(new SelectorItemInfo("taxerQua"));
        sic.add(new SelectorItemInfo("lxNumber"));
        sic.add(new SelectorItemInfo("peopleCount"));
        sic.add(new SelectorItemInfo("webSite"));
        sic.add(new SelectorItemInfo("linkMail"));
        sic.add(new SelectorItemInfo("postNumber"));
        sic.add(new SelectorItemInfo("businessNum"));
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
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("totalAssets"));
        sic.add(new SelectorItemInfo("fixedAssets"));
        sic.add(new SelectorItemInfo("currentAssets"));
        sic.add(new SelectorItemInfo("totalLiabilities"));
        sic.add(new SelectorItemInfo("longLiabilities"));
        sic.add(new SelectorItemInfo("currentLiabilities"));
        sic.add(new SelectorItemInfo("lastYearProfit"));
        sic.add(new SelectorItemInfo("ability"));
        sic.add(new SelectorItemInfo("scale"));
        sic.add(new SelectorItemInfo("mainWork"));
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
     * output actionPersonAddLine_actionPerformed method
     */
    public void actionPersonAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPersonInsertLine_actionPerformed method
     */
    public void actionPersonInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPersonRemoveLine_actionPerformed method
     */
    public void actionPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLinkPersonRemoveLine_actionPerformed method
     */
    public void actionLinkPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLinkPersonAddLine_actionPerformed method
     */
    public void actionLinkPersonAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLinkPersonInsertLine_actionPerformed method
     */
    public void actionLinkPersonInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachListAddLine_actionPerformed method
     */
    public void actionAttachListAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachListRemoveLine_actionPerformed method
     */
    public void actionAttachListRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachListInsertLine_actionPerformed method
     */
    public void actionAttachListInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractAddRow_actionPerformed method
     */
    public void actionContractAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractDelRow_actionPerformed method
     */
    public void actionContractDelRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractInsertRow_actionPerformed method
     */
    public void actionContractInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWorkFlowG_actionPerformed method
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAuditResult_actionPerformed method
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPersonAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonAddLine() {
    	return false;
    }
	public RequestContext prepareActionPersonInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonInsertLine() {
    	return false;
    }
	public RequestContext prepareActionPersonRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonRemoveLine() {
    	return false;
    }
	public RequestContext prepareActionLinkPersonRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLinkPersonRemoveLine() {
    	return false;
    }
	public RequestContext prepareActionLinkPersonAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLinkPersonAddLine() {
    	return false;
    }
	public RequestContext prepareActionLinkPersonInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLinkPersonInsertLine() {
    	return false;
    }
	public RequestContext prepareActionAttachListAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachListAddLine() {
    	return false;
    }
	public RequestContext prepareActionAttachListRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachListRemoveLine() {
    	return false;
    }
	public RequestContext prepareActionAttachListInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachListInsertLine() {
    	return false;
    }
	public RequestContext prepareActionContractAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractAddRow() {
    	return false;
    }
	public RequestContext prepareActionContractDelRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractDelRow() {
    	return false;
    }
	public RequestContext prepareActionContractInsertRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractInsertRow() {
    	return false;
    }
	public RequestContext prepareActionWorkFlowG(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWorkFlowG() {
    	return false;
    }
	public RequestContext prepareActionAuditResult(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAuditResult() {
    	return false;
    }

    /**
     * output ActionPersonAddLine class
     */     
    protected class ActionPersonAddLine extends ItemAction {     
    
        public ActionPersonAddLine()
        {
            this(null);
        }

        public ActionPersonAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPersonAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionPersonAddLine", "actionPersonAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionPersonInsertLine class
     */     
    protected class ActionPersonInsertLine extends ItemAction {     
    
        public ActionPersonInsertLine()
        {
            this(null);
        }

        public ActionPersonInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPersonInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionPersonInsertLine", "actionPersonInsertLine_actionPerformed", e);
        }
    }

    /**
     * output ActionPersonRemoveLine class
     */     
    protected class ActionPersonRemoveLine extends ItemAction {     
    
        public ActionPersonRemoveLine()
        {
            this(null);
        }

        public ActionPersonRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPersonRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionPersonRemoveLine", "actionPersonRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output ActionLinkPersonRemoveLine class
     */     
    protected class ActionLinkPersonRemoveLine extends ItemAction {     
    
        public ActionLinkPersonRemoveLine()
        {
            this(null);
        }

        public ActionLinkPersonRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionLinkPersonRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionLinkPersonRemoveLine", "actionLinkPersonRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output ActionLinkPersonAddLine class
     */     
    protected class ActionLinkPersonAddLine extends ItemAction {     
    
        public ActionLinkPersonAddLine()
        {
            this(null);
        }

        public ActionLinkPersonAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionLinkPersonAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionLinkPersonAddLine", "actionLinkPersonAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionLinkPersonInsertLine class
     */     
    protected class ActionLinkPersonInsertLine extends ItemAction {     
    
        public ActionLinkPersonInsertLine()
        {
            this(null);
        }

        public ActionLinkPersonInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionLinkPersonInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLinkPersonInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionLinkPersonInsertLine", "actionLinkPersonInsertLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachListAddLine class
     */     
    protected class ActionAttachListAddLine extends ItemAction {     
    
        public ActionAttachListAddLine()
        {
            this(null);
        }

        public ActionAttachListAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachListAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionAttachListAddLine", "actionAttachListAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachListRemoveLine class
     */     
    protected class ActionAttachListRemoveLine extends ItemAction {     
    
        public ActionAttachListRemoveLine()
        {
            this(null);
        }

        public ActionAttachListRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachListRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionAttachListRemoveLine", "actionAttachListRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachListInsertLine class
     */     
    protected class ActionAttachListInsertLine extends ItemAction {     
    
        public ActionAttachListInsertLine()
        {
            this(null);
        }

        public ActionAttachListInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachListInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachListInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionAttachListInsertLine", "actionAttachListInsertLine_actionPerformed", e);
        }
    }

    /**
     * output ActionContractAddRow class
     */     
    protected class ActionContractAddRow extends ItemAction {     
    
        public ActionContractAddRow()
        {
            this(null);
        }

        public ActionContractAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContractAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionContractAddRow", "actionContractAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionContractDelRow class
     */     
    protected class ActionContractDelRow extends ItemAction {     
    
        public ActionContractDelRow()
        {
            this(null);
        }

        public ActionContractDelRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionContractDelRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractDelRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractDelRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionContractDelRow", "actionContractDelRow_actionPerformed", e);
        }
    }

    /**
     * output ActionContractInsertRow class
     */     
    protected class ActionContractInsertRow extends ItemAction {     
    
        public ActionContractInsertRow()
        {
            this(null);
        }

        public ActionContractInsertRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionContractInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionContractInsertRow", "actionContractInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionWorkFlowG class
     */     
    protected class ActionWorkFlowG extends ItemAction {     
    
        public ActionWorkFlowG()
        {
            this(null);
        }

        public ActionWorkFlowG(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionWorkFlowG.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionWorkFlowG", "actionWorkFlowG_actionPerformed", e);
        }
    }

    /**
     * output ActionAuditResult class
     */     
    protected class ActionAuditResult extends ItemAction {     
    
        public ActionAuditResult()
        {
            this(null);
        }

        public ActionAuditResult(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAuditResult.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditResult.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditResult.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewSupplierStockEditUI.this, "ActionAuditResult", "actionAuditResult_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "NewSupplierStockEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}