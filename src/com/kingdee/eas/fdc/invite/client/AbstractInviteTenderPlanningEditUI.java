/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractInviteTenderPlanningEditUI extends com.kingdee.eas.fdc.invite.client.BaseInviteEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteTenderPlanningEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayMethod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contZbt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWfType;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtScope;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtPayMethod;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtZbt;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane4;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSh;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane5;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtJs;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane6;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBd;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane7;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDc;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane8;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtJc;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbWfType;
    protected com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractInviteTenderPlanningEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteTenderPlanningEditUI.class.getName());
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
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
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
        this.contScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayMethod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contZbt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWfType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtScope = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtPayMethod = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtZbt = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane4 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtSh = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane5 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtJs = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane6 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBd = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane7 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane8 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtJc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.cbType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbWfType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contScope.setName("contScope");
        this.contPayMethod.setName("contPayMethod");
        this.contZbt.setName("contZbt");
        this.contSh.setName("contSh");
        this.contJs.setName("contJs");
        this.contBd.setName("contBd");
        this.contDc.setName("contDc");
        this.contJc.setName("contJc");
        this.contType.setName("contType");
        this.contWfType.setName("contWfType");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtScope.setName("txtScope");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtPayMethod.setName("txtPayMethod");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtZbt.setName("txtZbt");
        this.kDScrollPane4.setName("kDScrollPane4");
        this.txtSh.setName("txtSh");
        this.kDScrollPane5.setName("kDScrollPane5");
        this.txtJs.setName("txtJs");
        this.kDScrollPane6.setName("kDScrollPane6");
        this.txtBd.setName("txtBd");
        this.kDScrollPane7.setName("kDScrollPane7");
        this.txtDc.setName("txtDc");
        this.kDScrollPane8.setName("kDScrollPane8");
        this.txtJc.setName("txtJc");
        this.cbType.setName("cbType");
        this.cbWfType.setName("cbWfType");
        // CoreUI		
        this.setMinimumSize(new Dimension(1000,600));		
        this.setPreferredSize(new Dimension(1000,600));		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);		
        this.contName.setEnabled(true);		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);		
        this.contEntry.setTitle(resHelper.getString("contEntry.title"));		
        this.btnShowProject.setVisible(false);		
        this.contProject.setVisible(false);		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.pkCreateTime.setEnabled(false);		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.txtNumber.setRequired(true);		
        this.pkAuditTime.setEnabled(false);		
        this.prmtRespDept.setRequired(true);
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"attach\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"time\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{attach}</t:Cell><t:Cell>$Resource{time}</t:Cell><t:Cell>$Resource{content}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

        

        // contScope		
        this.contScope.setBoundLabelText(resHelper.getString("contScope.boundLabelText"));		
        this.contScope.setBoundLabelLength(100);		
        this.contScope.setBoundLabelUnderline(true);
        // contPayMethod		
        this.contPayMethod.setBoundLabelText(resHelper.getString("contPayMethod.boundLabelText"));		
        this.contPayMethod.setBoundLabelLength(100);		
        this.contPayMethod.setBoundLabelUnderline(true);		
        this.contPayMethod.setVisible(false);
        // contZbt		
        this.contZbt.setBoundLabelText(resHelper.getString("contZbt.boundLabelText"));		
        this.contZbt.setBoundLabelLength(100);		
        this.contZbt.setBoundLabelUnderline(true);		
        this.contZbt.setVisible(false);
        // contSh		
        this.contSh.setBoundLabelText(resHelper.getString("contSh.boundLabelText"));		
        this.contSh.setBoundLabelLength(100);		
        this.contSh.setBoundLabelUnderline(true);		
        this.contSh.setVisible(false);
        // contJs		
        this.contJs.setBoundLabelText(resHelper.getString("contJs.boundLabelText"));		
        this.contJs.setBoundLabelLength(270);		
        this.contJs.setBoundLabelUnderline(true);		
        this.contJs.setVisible(false);
        // contBd		
        this.contBd.setBoundLabelText(resHelper.getString("contBd.boundLabelText"));		
        this.contBd.setBoundLabelLength(270);		
        this.contBd.setBoundLabelUnderline(true);		
        this.contBd.setVisible(false);
        // contDc		
        this.contDc.setBoundLabelText(resHelper.getString("contDc.boundLabelText"));		
        this.contDc.setBoundLabelLength(270);		
        this.contDc.setBoundLabelUnderline(true);		
        this.contDc.setVisible(false);
        // contJc		
        this.contJc.setBoundLabelText(resHelper.getString("contJc.boundLabelText"));		
        this.contJc.setBoundLabelLength(270);		
        this.contJc.setBoundLabelUnderline(true);		
        this.contJc.setVisible(false);
        // contType		
        this.contType.setBoundLabelText(resHelper.getString("contType.boundLabelText"));		
        this.contType.setBoundLabelLength(100);		
        this.contType.setBoundLabelUnderline(true);
        // contWfType		
        this.contWfType.setBoundLabelText(resHelper.getString("contWfType.boundLabelText"));		
        this.contWfType.setBoundLabelLength(100);		
        this.contWfType.setBoundLabelUnderline(true);
        // kDScrollPane1
        // txtScope		
        this.txtScope.setMaxLength(50000);		
        this.txtScope.setRequired(true);
        // kDScrollPane2
        // txtPayMethod		
        this.txtPayMethod.setMaxLength(50000);		
        this.txtPayMethod.setRequired(true);
        // kDScrollPane3
        // txtZbt		
        this.txtZbt.setMaxLength(50000);		
        this.txtZbt.setRequired(true);
        // kDScrollPane4
        // txtSh		
        this.txtSh.setMaxLength(50000);		
        this.txtSh.setRequired(true);
        // kDScrollPane5
        // txtJs		
        this.txtJs.setMaxLength(50000);		
        this.txtJs.setRequired(true);
        // kDScrollPane6
        // txtBd		
        this.txtBd.setMaxLength(50000);		
        this.txtBd.setRequired(true);
        // kDScrollPane7
        // txtDc		
        this.txtDc.setMaxLength(50000);		
        this.txtDc.setRequired(true);
        // kDScrollPane8
        // txtJc		
        this.txtJc.setMaxLength(50000);		
        this.txtJc.setRequired(true);
        // cbType		
        this.cbType.setRequired(true);		
        this.cbType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InviteTenderTypeEnum").toArray());
        // cbWfType		
        this.cbWfType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.TenderwfTypeEnum").toArray());		
        this.cbWfType.setRequired(true);
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
        this.setBounds(new Rectangle(10, 10, 1000, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 600));
        contCreator.setBounds(new Rectangle(10, 551, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(357, 551, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(357, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(10, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(10, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(357, 573, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(357, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(357, 12, 270, 19));
        this.add(contName, new KDLayout.Constraints(357, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespDept.setBounds(new Rectangle(702, 551, 270, 19));
        this.add(contRespDept, new KDLayout.Constraints(702, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEntry.setBounds(new Rectangle(10, 388, 973, 147));
        this.add(contEntry, new KDLayout.Constraints(10, 388, 973, 147, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnShowProject.setBounds(new Rectangle(886, 15, 166, 19));
        this.add(btnShowProject, new KDLayout.Constraints(886, 15, 166, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(883, -4, 270, 19));
        this.add(contProject, new KDLayout.Constraints(883, -4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScope.setBounds(new Rectangle(12, 61, 973, 320));
        this.add(contScope, new KDLayout.Constraints(12, 61, 973, 320, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayMethod.setBounds(new Rectangle(693, 111, 973, 34));
        this.add(contPayMethod, new KDLayout.Constraints(693, 111, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contZbt.setBounds(new Rectangle(708, 154, 973, 34));
        this.add(contZbt, new KDLayout.Constraints(708, 154, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSh.setBounds(new Rectangle(688, 182, 973, 34));
        this.add(contSh, new KDLayout.Constraints(688, 182, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contJs.setBounds(new Rectangle(695, 216, 973, 34));
        this.add(contJs, new KDLayout.Constraints(695, 216, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBd.setBounds(new Rectangle(652, 248, 973, 34));
        this.add(contBd, new KDLayout.Constraints(652, 248, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDc.setBounds(new Rectangle(676, 264, 973, 49));
        this.add(contDc, new KDLayout.Constraints(676, 264, 973, 49, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contJc.setBounds(new Rectangle(715, 327, 973, 34));
        this.add(contJc, new KDLayout.Constraints(715, 327, 973, 34, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contType.setBounds(new Rectangle(711, 12, 270, 19));
        this.add(contType, new KDLayout.Constraints(711, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWfType.setBounds(new Rectangle(711, 36, 270, 19));
        this.add(contWfType, new KDLayout.Constraints(711, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contName
        contName.setBoundEditor(txtName);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contScope
        contScope.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtScope, null);
        //contPayMethod
        contPayMethod.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtPayMethod, null);
        //contZbt
        contZbt.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtZbt, null);
        //contSh
        contSh.setBoundEditor(kDScrollPane4);
        //kDScrollPane4
        kDScrollPane4.getViewport().add(txtSh, null);
        //contJs
        contJs.setBoundEditor(kDScrollPane5);
        //kDScrollPane5
        kDScrollPane5.getViewport().add(txtJs, null);
        //contBd
        contBd.setBoundEditor(kDScrollPane6);
        //kDScrollPane6
        kDScrollPane6.getViewport().add(txtBd, null);
        //contDc
        contDc.setBoundEditor(kDScrollPane7);
        //kDScrollPane7
        kDScrollPane7.getViewport().add(txtDc, null);
        //contJc
        contJc.setBoundEditor(kDScrollPane8);
        //kDScrollPane8
        kDScrollPane8.getViewport().add(txtJc, null);
        //contType
        contType.setBoundEditor(cbType);
        //contWfType
        contWfType.setBoundEditor(cbWfType);

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
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("inviteProject.name", String.class, this.txtProject, "text");
		dataBinder.registerBinding("scope", String.class, this.txtScope, "text");
		dataBinder.registerBinding("payMethod", String.class, this.txtPayMethod, "text");
		dataBinder.registerBinding("zbtzpsms", String.class, this.txtZbt, "text");
		dataBinder.registerBinding("shsjfams", String.class, this.txtSh, "text");
		dataBinder.registerBinding("jsbzyq", String.class, this.txtJs, "text");
		dataBinder.registerBinding("bdhfyq", String.class, this.txtBd, "text");
		dataBinder.registerBinding("dcbdwdyq", String.class, this.txtDc, "text");
		dataBinder.registerBinding("jcldx", String.class, this.txtJc, "text");
		dataBinder.registerBinding("type", com.kingdee.eas.fdc.invite.InviteTenderTypeEnum.class, this.cbType, "selectedItem");
		dataBinder.registerBinding("wfType", com.kingdee.eas.fdc.invite.TenderwfTypeEnum.class, this.cbWfType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteTenderPlanningEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMethod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zbtzpsms", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shsjfams", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jsbzyq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bdhfyq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dcbdwdyq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jcldx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wfType", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respDept.id"));
        	sic.add(new SelectorItemInfo("respDept.number"));
        	sic.add(new SelectorItemInfo("respDept.name"));
		}
        sic.add(new SelectorItemInfo("inviteProject.name"));
        sic.add(new SelectorItemInfo("scope"));
        sic.add(new SelectorItemInfo("payMethod"));
        sic.add(new SelectorItemInfo("zbtzpsms"));
        sic.add(new SelectorItemInfo("shsjfams"));
        sic.add(new SelectorItemInfo("jsbzyq"));
        sic.add(new SelectorItemInfo("bdhfyq"));
        sic.add(new SelectorItemInfo("dcbdwdyq"));
        sic.add(new SelectorItemInfo("jcldx"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("wfType"));
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteTenderPlanningEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}