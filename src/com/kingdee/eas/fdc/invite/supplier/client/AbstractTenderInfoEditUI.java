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
public abstract class AbstractTenderInfoEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenderInfoEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenderState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWebTenderId;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApprove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReject;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenderState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWebTenderId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectManager;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvitation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOwnDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNmae;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer 发标时间;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInvitation;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOwnDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpInviteDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtInviteDescription;
    protected com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo editData = null;
    protected ActionApproveReport actionApproveReport = null;
    protected ActionRejectReport actionRejectReport = null;
    /**
     * output class constructor
     */
    public AbstractTenderInfoEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenderInfoEditUI.class.getName());
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
        //actionApproveReport
        this.actionApproveReport = new ActionApproveReport(this);
        getActionManager().registerAction("actionApproveReport", actionApproveReport);
         this.actionApproveReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRejectReport
        this.actionRejectReport = new ActionRejectReport(this);
        getActionManager().registerAction("actionRejectReport", actionRejectReport);
         this.actionRejectReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenderState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWebTenderId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnApprove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTenderState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWebTenderId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProjectManager = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contInvitation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOwnDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectNmae = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.发标时间 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.prmtInvitation = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtOwnDept = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdpInviteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInviteDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contInviteProject.setName("contInviteProject");
        this.contTenderState.setName("contTenderState");
        this.contWebTenderId.setName("contWebTenderId");
        this.kDPanel1.setName("kDPanel1");
        this.btnApprove.setName("btnApprove");
        this.btnReject.setName("btnReject");
        this.kDPanel2.setName("kDPanel2");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.txtNumber.setName("txtNumber");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.txtTenderState.setName("txtTenderState");
        this.txtWebTenderId.setName("txtWebTenderId");
        this.contSupplier.setName("contSupplier");
        this.contProjectManager.setName("contProjectManager");
        this.contPhone.setName("contPhone");
        this.contDescription.setName("contDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.prmtSupplier.setName("prmtSupplier");
        this.txtProjectManager.setName("txtProjectManager");
        this.txtPhone.setName("txtPhone");
        this.txtDescription.setName("txtDescription");
        this.contInvitation.setName("contInvitation");
        this.contOwnDept.setName("contOwnDept");
        this.contInviteProjectName.setName("contInviteProjectName");
        this.contProjectNmae.setName("contProjectNmae");
        this.发标时间.setName("发标时间");
        this.contInviteDescription.setName("contInviteDescription");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.prmtInvitation.setName("prmtInvitation");
        this.txtOwnDept.setName("txtOwnDept");
        this.txtInviteProjectName.setName("txtInviteProjectName");
        this.txtProjectName.setName("txtProjectName");
        this.kdpInviteDate.setName("kdpInviteDate");
        this.txtInviteDescription.setName("txtInviteDescription");
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
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);		
        this.contInviteProject.setEnabled(false);
        // contTenderState		
        this.contTenderState.setBoundLabelText(resHelper.getString("contTenderState.boundLabelText"));		
        this.contTenderState.setBoundLabelLength(100);		
        this.contTenderState.setBoundLabelUnderline(true);		
        this.contTenderState.setVisible(false);
        // contWebTenderId		
        this.contWebTenderId.setBoundLabelText(resHelper.getString("contWebTenderId.boundLabelText"));		
        this.contWebTenderId.setBoundLabelLength(100);		
        this.contWebTenderId.setBoundLabelUnderline(true);		
        this.contWebTenderId.setVisible(false);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("kDPanel1.border.title")));
        // btnApprove
        this.btnApprove.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproveReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApprove.setText(resHelper.getString("btnApprove.text"));
        // btnReject
        this.btnReject.setAction((IItemAction)ActionProxyFactory.getProxy(actionRejectReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReject.setText(resHelper.getString("btnReject.text"));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), resHelper.getString("kDPanel2.border.title")));
        // kDSeparator8
        // kDSeparator9
        // txtNumber		
        this.txtNumber.setVisible(false);
        // prmtInviteProject		
        this.prmtInviteProject.setEnabled(false);
        // txtTenderState		
        this.txtTenderState.setMaxLength(10);
        // txtWebTenderId		
        this.txtWebTenderId.setMaxLength(50);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);		
        this.contSupplier.setEnabled(false);
        // contProjectManager		
        this.contProjectManager.setBoundLabelText(resHelper.getString("contProjectManager.boundLabelText"));		
        this.contProjectManager.setBoundLabelLength(100);		
        this.contProjectManager.setBoundLabelUnderline(true);		
        this.contProjectManager.setEnabled(false);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);		
        this.contPhone.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);
        // kDScrollPane1
        // prmtSupplier		
        this.prmtSupplier.setEnabled(false);		
        this.prmtSupplier.setRequired(true);
        // txtProjectManager		
        this.txtProjectManager.setMaxLength(255);		
        this.txtProjectManager.setEnabled(false);		
        this.txtProjectManager.setRequired(true);
        // txtPhone		
        this.txtPhone.setMaxLength(50);		
        this.txtPhone.setEnabled(false);		
        this.txtPhone.setRequired(true);
        // txtDescription		
        this.txtDescription.setEnabled(false);
        // contInvitation		
        this.contInvitation.setBoundLabelText(resHelper.getString("contInvitation.boundLabelText"));		
        this.contInvitation.setBoundLabelLength(100);		
        this.contInvitation.setBoundLabelUnderline(true);		
        this.contInvitation.setVisible(false);
        // contOwnDept		
        this.contOwnDept.setBoundLabelText(resHelper.getString("contOwnDept.boundLabelText"));		
        this.contOwnDept.setBoundLabelLength(100);		
        this.contOwnDept.setBoundLabelUnderline(true);		
        this.contOwnDept.setEnabled(false);
        // contInviteProjectName		
        this.contInviteProjectName.setBoundLabelText(resHelper.getString("contInviteProjectName.boundLabelText"));		
        this.contInviteProjectName.setBoundLabelUnderline(true);		
        this.contInviteProjectName.setBoundLabelLength(100);		
        this.contInviteProjectName.setEnabled(false);
        // contProjectNmae		
        this.contProjectNmae.setBoundLabelText(resHelper.getString("contProjectNmae.boundLabelText"));		
        this.contProjectNmae.setBoundLabelUnderline(true);		
        this.contProjectNmae.setBoundLabelLength(100);		
        this.contProjectNmae.setEnabled(false);
        // 发标时间		
        this.发标时间.setBoundLabelText(resHelper.getString("发标时间.boundLabelText"));		
        this.发标时间.setBoundLabelUnderline(true);		
        this.发标时间.setBoundLabelLength(100);		
        this.发标时间.setEnabled(false);
        // contInviteDescription		
        this.contInviteDescription.setBoundLabelText(resHelper.getString("contInviteDescription.boundLabelText"));
        // kDScrollPane2
        // prmtInvitation		
        this.prmtInvitation.setEnabled(false);		
        this.prmtInvitation.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7InvitationQuery");
        this.prmtInvitation.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInvitation_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtOwnDept		
        this.txtOwnDept.setEnabled(false);
        // txtInviteProjectName		
        this.txtInviteProjectName.setEnabled(false);
        // txtProjectName		
        this.txtProjectName.setEnabled(false);
        // kdpInviteDate		
        this.kdpInviteDate.setEnabled(false);
        // txtInviteDescription		
        this.txtInviteDescription.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 640, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 640, 629));
        contInviteProject.setBounds(new Rectangle(23, 29, 589, 19));
        this.add(contInviteProject, new KDLayout.Constraints(23, 29, 589, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTenderState.setBounds(new Rectangle(247, 610, 270, 19));
        this.add(contTenderState, new KDLayout.Constraints(247, 610, 270, 19, 0));
        contWebTenderId.setBounds(new Rectangle(539, 605, 270, 19));
        this.add(contWebTenderId, new KDLayout.Constraints(539, 605, 270, 19, 0));
        kDPanel1.setBounds(new Rectangle(12, 321, 608, 249));
        this.add(kDPanel1, new KDLayout.Constraints(12, 321, 608, 249, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnApprove.setBounds(new Rectangle(101, 590, 95, 19));
        this.add(btnApprove, new KDLayout.Constraints(101, 590, 95, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnReject.setBounds(new Rectangle(413, 590, 95, 19));
        this.add(btnReject, new KDLayout.Constraints(413, 590, 95, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel2.setBounds(new Rectangle(12, 90, 608, 217));
        this.add(kDPanel2, new KDLayout.Constraints(12, 90, 608, 217, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(12, 62, 608, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(12, 62, 608, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator9.setBounds(new Rectangle(12, 578, 608, 10));
        this.add(kDSeparator9, new KDLayout.Constraints(12, 578, 608, 10, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        txtNumber.setBounds(new Rectangle(544, 594, 170, 19));
        this.add(txtNumber, new KDLayout.Constraints(544, 594, 170, 19, 0));
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contTenderState
        contTenderState.setBoundEditor(txtTenderState);
        //contWebTenderId
        contWebTenderId.setBoundEditor(txtWebTenderId);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(12, 321, 608, 249));        contSupplier.setBounds(new Rectangle(16, 24, 573, 19));
        kDPanel1.add(contSupplier, new KDLayout.Constraints(16, 24, 573, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProjectManager.setBounds(new Rectangle(15, 54, 270, 19));
        kDPanel1.add(contProjectManager, new KDLayout.Constraints(15, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPhone.setBounds(new Rectangle(319, 55, 270, 19));
        kDPanel1.add(contPhone, new KDLayout.Constraints(319, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(17, 81, 270, 19));
        kDPanel1.add(contDescription, new KDLayout.Constraints(17, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(15, 112, 573, 115));
        kDPanel1.add(kDScrollPane1, new KDLayout.Constraints(15, 112, 573, 115, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contProjectManager
        contProjectManager.setBoundEditor(txtProjectManager);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(12, 90, 608, 217));        contInvitation.setBounds(new Rectangle(23, 21, 561, 19));
        kDPanel2.add(contInvitation, new KDLayout.Constraints(23, 21, 561, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOwnDept.setBounds(new Rectangle(23, 51, 270, 19));
        kDPanel2.add(contOwnDept, new KDLayout.Constraints(23, 51, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProjectName.setBounds(new Rectangle(313, 51, 270, 19));
        kDPanel2.add(contInviteProjectName, new KDLayout.Constraints(313, 51, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProjectNmae.setBounds(new Rectangle(23, 83, 270, 19));
        kDPanel2.add(contProjectNmae, new KDLayout.Constraints(23, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        发标时间.setBounds(new Rectangle(313, 83, 270, 19));
        kDPanel2.add(发标时间, new KDLayout.Constraints(313, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteDescription.setBounds(new Rectangle(23, 112, 270, 19));
        kDPanel2.add(contInviteDescription, new KDLayout.Constraints(23, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane2.setBounds(new Rectangle(23, 140, 561, 53));
        kDPanel2.add(kDScrollPane2, new KDLayout.Constraints(23, 140, 561, 53, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contInvitation
        contInvitation.setBoundEditor(prmtInvitation);
        //contOwnDept
        contOwnDept.setBoundEditor(txtOwnDept);
        //contInviteProjectName
        contInviteProjectName.setBoundEditor(txtInviteProjectName);
        //contProjectNmae
        contProjectNmae.setBoundEditor(txtProjectName);
        //发标时间
        发标时间.setBoundEditor(kdpInviteDate);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtInviteDescription, null);

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
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("tenderState", String.class, this.txtTenderState, "text");
		dataBinder.registerBinding("webTenderId", String.class, this.txtWebTenderId, "text");
		dataBinder.registerBinding("supplier", com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("projectManager", String.class, this.txtProjectManager, "text");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("invitation", com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo.class, this.prmtInvitation, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.TenderInfoEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenderState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("webTenderId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectManager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitation", ValidateHelper.ON_SAVE);    		
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
     * output prmtInvitation_dataChanged method
     */
    protected void prmtInvitation_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
			sic.add(new SelectorItemInfo("inviteProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteProject.id"));
        	sic.add(new SelectorItemInfo("inviteProject.number"));
        	sic.add(new SelectorItemInfo("inviteProject.name"));
		}
        sic.add(new SelectorItemInfo("tenderState"));
        sic.add(new SelectorItemInfo("webTenderId"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplier.id"));
        	sic.add(new SelectorItemInfo("supplier.number"));
        	sic.add(new SelectorItemInfo("supplier.name"));
		}
        sic.add(new SelectorItemInfo("projectManager"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invitation.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invitation.id"));
        	sic.add(new SelectorItemInfo("invitation.number"));
        	sic.add(new SelectorItemInfo("invitation.name"));
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
     * output actionApproveReport_actionPerformed method
     */
    public void actionApproveReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRejectReport_actionPerformed method
     */
    public void actionRejectReport_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionApproveReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproveReport() {
    	return false;
    }
	public RequestContext prepareActionRejectReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRejectReport() {
    	return false;
    }

    /**
     * output ActionApproveReport class
     */     
    protected class ActionApproveReport extends ItemAction {     
    
        public ActionApproveReport()
        {
            this(null);
        }

        public ActionApproveReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApproveReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenderInfoEditUI.this, "ActionApproveReport", "actionApproveReport_actionPerformed", e);
        }
    }

    /**
     * output ActionRejectReport class
     */     
    protected class ActionRejectReport extends ItemAction {     
    
        public ActionRejectReport()
        {
            this(null);
        }

        public ActionRejectReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRejectReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejectReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRejectReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenderInfoEditUI.this, "ActionRejectReport", "actionRejectReport_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "TenderInfoEditUI");
    }




}