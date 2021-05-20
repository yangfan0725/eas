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
public abstract class AbstractWebUserEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWebUserEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWebUserID;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlWebUser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtId;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pklastUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWebUserID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMobileNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPassword;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTelephone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPosition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisteTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMobileNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDPasswordField txtPassword;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTelephone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPosition;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRegisterState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRegistedDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSpecialty;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.eas.fdc.invite.supplier.WebUserInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractWebUserEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWebUserEditUI.class.getName());
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
        this.contId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWebUserID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlWebUser = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pklastUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtWebUserID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contMobileNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPassword = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTelephone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPosition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisteTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtMobileNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPassword = new com.kingdee.bos.ctrl.swing.KDPasswordField();
        this.txtTelephone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPosition = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboRegisterState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCompanyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkRegistedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSpecialty = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contId.setName("contId");
        this.contCreator.setName("contCreator");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contWebUserID.setName("contWebUserID");
        this.pnlWebUser.setName("pnlWebUser");
        this.txtNumber.setName("txtNumber");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.txtId.setName("txtId");
        this.prmtCreator.setName("prmtCreator");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pklastUpdateDate.setName("pklastUpdateDate");
        this.txtWebUserID.setName("txtWebUserID");
        this.contMobileNumber.setName("contMobileNumber");
        this.contName.setName("contName");
        this.contPassword.setName("contPassword");
        this.contTelephone.setName("contTelephone");
        this.contEmail.setName("contEmail");
        this.contPosition.setName("contPosition");
        this.contRegisterState.setName("contRegisterState");
        this.contCompanyName.setName("contCompanyName");
        this.contRegisteTime.setName("contRegisteTime");
        this.contSpecialty.setName("contSpecialty");
        this.contCreateTime.setName("contCreateTime");
        this.contDescription.setName("contDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtMobileNumber.setName("txtMobileNumber");
        this.txtName.setName("txtName");
        this.txtPassword.setName("txtPassword");
        this.txtTelephone.setName("txtTelephone");
        this.txtEmail.setName("txtEmail");
        this.txtPosition.setName("txtPosition");
        this.comboRegisterState.setName("comboRegisterState");
        this.txtCompanyName.setName("txtCompanyName");
        this.pkRegistedDate.setName("pkRegistedDate");
        this.txtSpecialty.setName("txtSpecialty");
        this.pkCreateDate.setName("pkCreateDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        // CoreUI		
        this.setMaximumSize(new Dimension(680,380));		
        this.setMinimumSize(new Dimension(680,380));		
        this.setPreferredSize(new Dimension(680,380));		
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
        // contId		
        this.contId.setBoundLabelText(resHelper.getString("contId.boundLabelText"));		
        this.contId.setBoundLabelLength(100);		
        this.contId.setBoundLabelUnderline(true);		
        this.contId.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);		
        this.contCreator.setVisible(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contWebUserID		
        this.contWebUserID.setBoundLabelText(resHelper.getString("contWebUserID.boundLabelText"));		
        this.contWebUserID.setBoundLabelLength(100);		
        this.contWebUserID.setBoundLabelUnderline(true);		
        this.contWebUserID.setVisible(false);
        // pnlWebUser		
        this.pnlWebUser.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("pnlWebUser.border.title")));
        // txtNumber		
        this.txtNumber.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setEnabled(false);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelLength(100);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // txtId
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // pklastUpdateDate		
        this.pklastUpdateDate.setEnabled(false);
        // txtWebUserID		
        this.txtWebUserID.setMaxLength(50);
        // contMobileNumber		
        this.contMobileNumber.setBoundLabelText(resHelper.getString("contMobileNumber.boundLabelText"));		
        this.contMobileNumber.setBoundLabelLength(100);		
        this.contMobileNumber.setBoundLabelUnderline(true);		
        this.contMobileNumber.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // contPassword		
        this.contPassword.setBoundLabelText(resHelper.getString("contPassword.boundLabelText"));		
        this.contPassword.setBoundLabelLength(100);		
        this.contPassword.setBoundLabelUnderline(true);		
        this.contPassword.setEnabled(false);
        // contTelephone		
        this.contTelephone.setBoundLabelText(resHelper.getString("contTelephone.boundLabelText"));		
        this.contTelephone.setBoundLabelLength(100);		
        this.contTelephone.setBoundLabelUnderline(true);		
        this.contTelephone.setEnabled(false);
        // contEmail		
        this.contEmail.setBoundLabelText(resHelper.getString("contEmail.boundLabelText"));		
        this.contEmail.setBoundLabelLength(100);		
        this.contEmail.setBoundLabelUnderline(true);		
        this.contEmail.setEnabled(false);
        // contPosition		
        this.contPosition.setBoundLabelText(resHelper.getString("contPosition.boundLabelText"));		
        this.contPosition.setBoundLabelLength(100);		
        this.contPosition.setBoundLabelUnderline(true);		
        this.contPosition.setEnabled(false);
        // contRegisterState		
        this.contRegisterState.setBoundLabelText(resHelper.getString("contRegisterState.boundLabelText"));		
        this.contRegisterState.setBoundLabelLength(100);		
        this.contRegisterState.setBoundLabelUnderline(true);		
        this.contRegisterState.setEnabled(false);
        // contCompanyName		
        this.contCompanyName.setBoundLabelText(resHelper.getString("contCompanyName.boundLabelText"));		
        this.contCompanyName.setBoundLabelLength(100);		
        this.contCompanyName.setBoundLabelUnderline(true);		
        this.contCompanyName.setEnabled(false);
        // contRegisteTime		
        this.contRegisteTime.setBoundLabelText(resHelper.getString("contRegisteTime.boundLabelText"));		
        this.contRegisteTime.setBoundLabelUnderline(true);		
        this.contRegisteTime.setBoundLabelLength(100);		
        this.contRegisteTime.setEnabled(false);
        // contSpecialty		
        this.contSpecialty.setBoundLabelText(resHelper.getString("contSpecialty.boundLabelText"));		
        this.contSpecialty.setBoundLabelUnderline(true);		
        this.contSpecialty.setBoundLabelLength(100);		
        this.contSpecialty.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));
        // kDScrollPane1
        // txtMobileNumber		
        this.txtMobileNumber.setMaxLength(255);		
        this.txtMobileNumber.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // txtPassword		
        this.txtPassword.setText(resHelper.getString("txtPassword.text"));		
        this.txtPassword.setEnabled(false);
        // txtTelephone		
        this.txtTelephone.setMaxLength(255);		
        this.txtTelephone.setEnabled(false);
        // txtEmail		
        this.txtEmail.setMaxLength(255);		
        this.txtEmail.setEnabled(false);
        // txtPosition		
        this.txtPosition.setMaxLength(255);		
        this.txtPosition.setEnabled(false);
        // comboRegisterState		
        this.comboRegisterState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.RegistStateEnum").toArray());		
        this.comboRegisterState.setEnabled(false);
        // txtCompanyName		
        this.txtCompanyName.setMaxLength(255);		
        this.txtCompanyName.setEnabled(false);
        // pkRegistedDate		
        this.pkRegistedDate.setEnabled(false);
        // txtSpecialty		
        this.txtSpecialty.setEnabled(false);
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 680, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 600));
        contId.setBounds(new Rectangle(301, 587, 270, 19));
        this.add(contId, new KDLayout.Constraints(301, 587, 270, 19, 0));
        contCreator.setBounds(new Rectangle(585, 588, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(585, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(25, 538, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(25, 538, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(380, 533, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(380, 533, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWebUserID.setBounds(new Rectangle(42, 588, 270, 19));
        this.add(contWebUserID, new KDLayout.Constraints(42, 588, 270, 19, 0));
        pnlWebUser.setBounds(new Rectangle(8, 20, 662, 541));
        this.add(pnlWebUser, new KDLayout.Constraints(8, 20, 662, 541, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        txtNumber.setBounds(new Rectangle(444, 479, 170, 19));
        this.add(txtNumber, new KDLayout.Constraints(444, 479, 170, 19, 0));
        contAuditor.setBounds(new Rectangle(24, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(24, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(380, 564, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(380, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contId
        contId.setBoundEditor(txtId);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pklastUpdateDate);
        //contWebUserID
        contWebUserID.setBoundEditor(txtWebUserID);
        //pnlWebUser
        pnlWebUser.setLayout(new KDLayout());
        pnlWebUser.putClientProperty("OriginalBounds", new Rectangle(8, 20, 662, 541));        contMobileNumber.setBounds(new Rectangle(29, 31, 621, 19));
        pnlWebUser.add(contMobileNumber, new KDLayout.Constraints(29, 31, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(29, 104, 621, 19));
        pnlWebUser.add(contName, new KDLayout.Constraints(29, 104, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPassword.setBounds(new Rectangle(29, 65, 621, 19));
        pnlWebUser.add(contPassword, new KDLayout.Constraints(29, 65, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTelephone.setBounds(new Rectangle(29, 186, 621, 19));
        pnlWebUser.add(contTelephone, new KDLayout.Constraints(29, 186, 621, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contEmail.setBounds(new Rectangle(29, 220, 621, 19));
        pnlWebUser.add(contEmail, new KDLayout.Constraints(29, 220, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPosition.setBounds(new Rectangle(29, 266, 621, 19));
        pnlWebUser.add(contPosition, new KDLayout.Constraints(29, 266, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRegisterState.setBounds(new Rectangle(29, 305, 621, 19));
        pnlWebUser.add(contRegisterState, new KDLayout.Constraints(29, 305, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompanyName.setBounds(new Rectangle(29, 144, 621, 19));
        pnlWebUser.add(contCompanyName, new KDLayout.Constraints(29, 144, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRegisteTime.setBounds(new Rectangle(29, 347, 621, 19));
        pnlWebUser.add(contRegisteTime, new KDLayout.Constraints(29, 347, 621, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSpecialty.setBounds(new Rectangle(29, 384, 624, 19));
        pnlWebUser.add(contSpecialty, new KDLayout.Constraints(29, 384, 624, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(458, 493, 270, 19));
        pnlWebUser.add(contCreateTime, new KDLayout.Constraints(458, 493, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(29, 411, 270, 19));
        pnlWebUser.add(contDescription, new KDLayout.Constraints(29, 411, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(29, 435, 624, 68));
        pnlWebUser.add(kDScrollPane1, new KDLayout.Constraints(29, 435, 624, 68, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contMobileNumber
        contMobileNumber.setBoundEditor(txtMobileNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contPassword
        contPassword.setBoundEditor(txtPassword);
        //contTelephone
        contTelephone.setBoundEditor(txtTelephone);
        //contEmail
        contEmail.setBoundEditor(txtEmail);
        //contPosition
        contPosition.setBoundEditor(txtPosition);
        //contRegisterState
        contRegisterState.setBoundEditor(comboRegisterState);
        //contCompanyName
        contCompanyName.setBoundEditor(txtCompanyName);
        //contRegisteTime
        contRegisteTime.setBoundEditor(pkRegistedDate);
        //contSpecialty
        contSpecialty.setBoundEditor(txtSpecialty);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateDate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);

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
		dataBinder.registerBinding("id", com.kingdee.bos.util.BOSUuid.class, this.txtId, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pklastUpdateDate, "value");
		dataBinder.registerBinding("webUserID", String.class, this.txtWebUserID, "text");
		dataBinder.registerBinding("mobileNumber", String.class, this.txtMobileNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("password", String.class, this.txtPassword, "text");
		dataBinder.registerBinding("telephone", String.class, this.txtTelephone, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("position", String.class, this.txtPosition, "text");
		dataBinder.registerBinding("registerState", com.kingdee.eas.fdc.invite.supplier.RegistStateEnum.class, this.comboRegisterState, "selectedItem");
		dataBinder.registerBinding("companyName", String.class, this.txtCompanyName, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkRegistedDate, "value");
		dataBinder.registerBinding("specialty", String.class, this.txtSpecialty, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.WebUserEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.WebUserInfo)ov;
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
		getValidateHelper().registerBindProperty("id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("webUserID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mobileNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("password", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("registerState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("companyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("id"));
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
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("webUserID"));
        sic.add(new SelectorItemInfo("mobileNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("password"));
        sic.add(new SelectorItemInfo("telephone"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("position"));
        sic.add(new SelectorItemInfo("registerState"));
        sic.add(new SelectorItemInfo("companyName"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("specialty"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "WebUserEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}