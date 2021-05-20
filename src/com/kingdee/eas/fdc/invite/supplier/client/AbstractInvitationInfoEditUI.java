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
public abstract class AbstractInvitationInfoEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInvitationInfoEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtId;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOwnerDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkphone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkfax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteDoc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenderEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOwnerDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInviteDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkphone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkman;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkfax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combFile;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpTenderEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractInvitationInfoEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInvitationInfoEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOwnerDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkphone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkfax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteDoc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contTenderEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOwnerDept = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkInviteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtLinkphone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkman = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkfax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combFile = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdpTenderEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contId.setName("contId");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.kDPanel1.setName("kDPanel1");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDPanel2.setName("kDPanel2");
        this.kDSeparator9.setName("kDSeparator9");
        this.txtId.setName("txtId");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.contInviteProject.setName("contInviteProject");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.contNumber.setName("contNumber");
        this.contInviteProjectName.setName("contInviteProjectName");
        this.contOwnerDept.setName("contOwnerDept");
        this.contInviteDate.setName("contInviteDate");
        this.contLinkphone.setName("contLinkphone");
        this.contProject.setName("contProject");
        this.contLinkman.setName("contLinkman");
        this.contLinkfax.setName("contLinkfax");
        this.contEmail.setName("contEmail");
        this.contInviteDoc.setName("contInviteDoc");
        this.contDescription.setName("contDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contTenderEndDate.setName("contTenderEndDate");
        this.contInviteType.setName("contInviteType");
        this.txtNumber.setName("txtNumber");
        this.txtInviteProjectName.setName("txtInviteProjectName");
        this.txtOwnerDept.setName("txtOwnerDept");
        this.pkInviteDate.setName("pkInviteDate");
        this.txtLinkphone.setName("txtLinkphone");
        this.txtProject.setName("txtProject");
        this.txtLinkman.setName("txtLinkman");
        this.txtLinkfax.setName("txtLinkfax");
        this.txtEmail.setName("txtEmail");
        this.combFile.setName("combFile");
        this.txtDescription.setName("txtDescription");
        this.kdpTenderEndDate.setName("kdpTenderEndDate");
        this.prmtInviteType.setName("prmtInviteType");
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
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
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
        // kDPanel1
        // kDSeparator8
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("kDPanel2.border.title")));
        // kDSeparator9
        // txtId
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // prmtInviteProject		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contInviteProjectName		
        this.contInviteProjectName.setBoundLabelText(resHelper.getString("contInviteProjectName.boundLabelText"));		
        this.contInviteProjectName.setBoundLabelLength(100);		
        this.contInviteProjectName.setBoundLabelUnderline(true);
        // contOwnerDept		
        this.contOwnerDept.setBoundLabelText(resHelper.getString("contOwnerDept.boundLabelText"));		
        this.contOwnerDept.setBoundLabelLength(100);		
        this.contOwnerDept.setBoundLabelUnderline(true);
        // contInviteDate		
        this.contInviteDate.setBoundLabelText(resHelper.getString("contInviteDate.boundLabelText"));		
        this.contInviteDate.setBoundLabelLength(100);		
        this.contInviteDate.setBoundLabelUnderline(true);
        // contLinkphone		
        this.contLinkphone.setBoundLabelText(resHelper.getString("contLinkphone.boundLabelText"));		
        this.contLinkphone.setBoundLabelLength(100);		
        this.contLinkphone.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contLinkman		
        this.contLinkman.setBoundLabelText(resHelper.getString("contLinkman.boundLabelText"));		
        this.contLinkman.setBoundLabelLength(100);		
        this.contLinkman.setBoundLabelUnderline(true);
        // contLinkfax		
        this.contLinkfax.setBoundLabelText(resHelper.getString("contLinkfax.boundLabelText"));		
        this.contLinkfax.setBoundLabelLength(100);		
        this.contLinkfax.setBoundLabelUnderline(true);
        // contEmail		
        this.contEmail.setBoundLabelText(resHelper.getString("contEmail.boundLabelText"));		
        this.contEmail.setBoundLabelLength(100);		
        this.contEmail.setBoundLabelUnderline(true);
        // contInviteDoc		
        this.contInviteDoc.setBoundLabelText(resHelper.getString("contInviteDoc.boundLabelText"));		
        this.contInviteDoc.setBoundLabelLength(100);		
        this.contInviteDoc.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);
        // kDScrollPane1
        // contTenderEndDate		
        this.contTenderEndDate.setBoundLabelText(resHelper.getString("contTenderEndDate.boundLabelText"));		
        this.contTenderEndDate.setBoundLabelUnderline(true);		
        this.contTenderEndDate.setBoundLabelLength(100);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelUnderline(true);		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtInviteProjectName		
        this.txtInviteProjectName.setMaxLength(255);		
        this.txtInviteProjectName.setRequired(true);
        // txtOwnerDept		
        this.txtOwnerDept.setMaxLength(255);		
        this.txtOwnerDept.setRequired(true);
        // pkInviteDate		
        this.pkInviteDate.setRequired(true);
        // txtLinkphone		
        this.txtLinkphone.setMaxLength(50);		
        this.txtLinkphone.setRequired(true);
        // txtProject		
        this.txtProject.setMaxLength(255);		
        this.txtProject.setRequired(true);
        // txtLinkman		
        this.txtLinkman.setMaxLength(255);		
        this.txtLinkman.setRequired(true);
        // txtLinkfax		
        this.txtLinkfax.setMaxLength(50);
        // txtEmail		
        this.txtEmail.setMaxLength(50);		
        this.txtEmail.setRequired(true);
        // combFile
        // txtDescription
        // kdpTenderEndDate		
        this.kdpTenderEndDate.setRequired(true);
        // prmtInviteType		
        this.prmtInviteType.setEnabled(false);		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setCommitFormat("$number$");
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
        this.setBounds(new Rectangle(10, 10, 650, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 629));
        contId.setBounds(new Rectangle(580, 595, 270, 19));
        this.add(contId, new KDLayout.Constraints(580, 595, 270, 19, 0));
        contCreator.setBounds(new Rectangle(13, 574, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 574, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(341, 574, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(341, 574, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(13, 600, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(342, 600, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(342, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(12, 14, 622, 53));
        this.add(kDPanel1, new KDLayout.Constraints(12, 14, 622, 53, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(11, 562, 622, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(11, 562, 622, 10, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(11, 102, 622, 450));
        this.add(kDPanel2, new KDLayout.Constraints(11, 102, 622, 450, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator9.setBounds(new Rectangle(17, 72, 617, 1));
        this.add(kDSeparator9, new KDLayout.Constraints(17, 72, 617, 1, 0));
        //contId
        contId.setBoundEditor(txtId);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(12, 14, 622, 53));        contInviteProject.setBounds(new Rectangle(19, 15, 505, 19));
        kDPanel1.add(contInviteProject, new KDLayout.Constraints(19, 15, 505, 19, 0));
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(11, 102, 622, 450));        contNumber.setBounds(new Rectangle(25, 41, 554, 19));
        kDPanel2.add(contNumber, new KDLayout.Constraints(25, 41, 554, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteProjectName.setBounds(new Rectangle(25, 69, 554, 19));
        kDPanel2.add(contInviteProjectName, new KDLayout.Constraints(25, 69, 554, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOwnerDept.setBounds(new Rectangle(25, 98, 554, 19));
        kDPanel2.add(contOwnerDept, new KDLayout.Constraints(25, 98, 554, 19, 0));
        contInviteDate.setBounds(new Rectangle(25, 196, 270, 19));
        kDPanel2.add(contInviteDate, new KDLayout.Constraints(25, 196, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkphone.setBounds(new Rectangle(25, 223, 270, 19));
        kDPanel2.add(contLinkphone, new KDLayout.Constraints(25, 223, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(25, 128, 554, 19));
        kDPanel2.add(contProject, new KDLayout.Constraints(25, 128, 554, 19, 0));
        contLinkman.setBounds(new Rectangle(311, 223, 270, 19));
        kDPanel2.add(contLinkman, new KDLayout.Constraints(311, 223, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkfax.setBounds(new Rectangle(313, 248, 270, 19));
        kDPanel2.add(contLinkfax, new KDLayout.Constraints(313, 248, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEmail.setBounds(new Rectangle(25, 248, 270, 19));
        kDPanel2.add(contEmail, new KDLayout.Constraints(25, 248, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteDoc.setBounds(new Rectangle(25, 276, 554, 19));
        kDPanel2.add(contInviteDoc, new KDLayout.Constraints(25, 276, 554, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(25, 303, 270, 19));
        kDPanel2.add(contDescription, new KDLayout.Constraints(25, 303, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(33, 337, 554, 102));
        kDPanel2.add(kDScrollPane1, new KDLayout.Constraints(33, 337, 554, 102, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTenderEndDate.setBounds(new Rectangle(311, 196, 270, 19));
        kDPanel2.add(contTenderEndDate, new KDLayout.Constraints(311, 196, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteType.setBounds(new Rectangle(25, 162, 554, 19));
        kDPanel2.add(contInviteType, new KDLayout.Constraints(25, 162, 554, 19, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contInviteProjectName
        contInviteProjectName.setBoundEditor(txtInviteProjectName);
        //contOwnerDept
        contOwnerDept.setBoundEditor(txtOwnerDept);
        //contInviteDate
        contInviteDate.setBoundEditor(pkInviteDate);
        //contLinkphone
        contLinkphone.setBoundEditor(txtLinkphone);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contLinkman
        contLinkman.setBoundEditor(txtLinkman);
        //contLinkfax
        contLinkfax.setBoundEditor(txtLinkfax);
        //contEmail
        contEmail.setBoundEditor(txtEmail);
        //contInviteDoc
        contInviteDoc.setBoundEditor(combFile);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contTenderEndDate
        contTenderEndDate.setBoundEditor(kdpTenderEndDate);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);

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
		dataBinder.registerBinding("id", com.kingdee.bos.util.BOSUuid.class, this.txtId, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("inviteProjectName", String.class, this.txtInviteProjectName, "text");
		dataBinder.registerBinding("ownerDept", String.class, this.txtOwnerDept, "text");
		dataBinder.registerBinding("inviteDate", java.util.Date.class, this.pkInviteDate, "value");
		dataBinder.registerBinding("linkphone", String.class, this.txtLinkphone, "text");
		dataBinder.registerBinding("project", String.class, this.txtProject, "text");
		dataBinder.registerBinding("linkman", String.class, this.txtLinkman, "text");
		dataBinder.registerBinding("linkfax", String.class, this.txtLinkfax, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("tenderEndDate", java.util.Date.class, this.kdpTenderEndDate, "value");
		dataBinder.registerBinding("inviteProject.inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.InvitationInfoEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo)ov;
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
		getValidateHelper().registerBindProperty("id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProjectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ownerDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkphone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkfax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenderEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteType", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteProject.id"));
        	sic.add(new SelectorItemInfo("inviteProject.number"));
        	sic.add(new SelectorItemInfo("inviteProject.name"));
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("inviteProjectName"));
        sic.add(new SelectorItemInfo("ownerDept"));
        sic.add(new SelectorItemInfo("inviteDate"));
        sic.add(new SelectorItemInfo("linkphone"));
        sic.add(new SelectorItemInfo("project"));
        sic.add(new SelectorItemInfo("linkman"));
        sic.add(new SelectorItemInfo("linkfax"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("tenderEndDate"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "InvitationInfoEditUI");
    }




}