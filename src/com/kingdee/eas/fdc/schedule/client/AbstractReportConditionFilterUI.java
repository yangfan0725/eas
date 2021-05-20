/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractReportConditionFilterUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractReportConditionFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtQualityPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtPlanPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuery;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReset;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQualityPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEndDate;
    protected ActionConditionQuery actionConditionQuery = null;
    protected ActionResetCondition actionResetCondition = null;
    /**
     * output class constructor
     */
    public AbstractReportConditionFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractReportConditionFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionConditionQuery
        this.actionConditionQuery = new ActionConditionQuery(this);
        getActionManager().registerAction("actionConditionQuery", actionConditionQuery);
         this.actionConditionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionResetCondition
        this.actionResetCondition = new ActionResetCondition(this);
        getActionManager().registerAction("actionResetCondition", actionResetCondition);
         this.actionResetCondition.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kdtRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtQualityPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtPlanPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReset = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtRespPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQualityPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPlanPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtRespPerson.setName("kdtRespPerson");
        this.kdtQualityPerson.setName("kdtQualityPerson");
        this.kdtPlanPerson.setName("kdtPlanPerson");
        this.kdtStartDate.setName("kdtStartDate");
        this.kdtEndDate.setName("kdtEndDate");
        this.btnQuery.setName("btnQuery");
        this.btnReset.setName("btnReset");
        this.prmtRespPerson.setName("prmtRespPerson");
        this.prmtQualityPerson.setName("prmtQualityPerson");
        this.prmtPlanPerson.setName("prmtPlanPerson");
        this.kdpStartDate.setName("kdpStartDate");
        this.kdpEndDate.setName("kdpEndDate");
        // CoreUI		
        this.setMaximumSize(new Dimension(400,300));		
        this.setMinimumSize(new Dimension(400,300));		
        this.setPreferredSize(new Dimension(400,300));		
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
        // kdtRespPerson		
        this.kdtRespPerson.setBoundLabelText(resHelper.getString("kdtRespPerson.boundLabelText"));		
        this.kdtRespPerson.setBoundLabelUnderline(true);		
        this.kdtRespPerson.setBoundLabelLength(100);
        // kdtQualityPerson		
        this.kdtQualityPerson.setBoundLabelText(resHelper.getString("kdtQualityPerson.boundLabelText"));		
        this.kdtQualityPerson.setBoundLabelUnderline(true);		
        this.kdtQualityPerson.setBoundLabelLength(100);
        // kdtPlanPerson		
        this.kdtPlanPerson.setBoundLabelText(resHelper.getString("kdtPlanPerson.boundLabelText"));		
        this.kdtPlanPerson.setBoundLabelUnderline(true);		
        this.kdtPlanPerson.setBoundLabelLength(100);
        // kdtStartDate		
        this.kdtStartDate.setBoundLabelText(resHelper.getString("kdtStartDate.boundLabelText"));		
        this.kdtStartDate.setBoundLabelUnderline(true);		
        this.kdtStartDate.setBoundLabelLength(100);
        // kdtEndDate		
        this.kdtEndDate.setBoundLabelText(resHelper.getString("kdtEndDate.boundLabelText"));		
        this.kdtEndDate.setBoundLabelUnderline(true);		
        this.kdtEndDate.setBoundLabelLength(100);
        // btnQuery
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionConditionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));
        // btnReset
        this.btnReset.setAction((IItemAction)ActionProxyFactory.getProxy(actionResetCondition, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReset.setText(resHelper.getString("btnReset.text"));
        // prmtRespPerson
        // prmtQualityPerson
        // prmtPlanPerson
        // kdpStartDate
        // kdpEndDate
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
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 300));
        kdtRespPerson.setBounds(new Rectangle(69, 42, 270, 19));
        this.add(kdtRespPerson, new KDLayout.Constraints(69, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtQualityPerson.setBounds(new Rectangle(69, 79, 270, 19));
        this.add(kdtQualityPerson, new KDLayout.Constraints(69, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtPlanPerson.setBounds(new Rectangle(69, 122, 270, 19));
        this.add(kdtPlanPerson, new KDLayout.Constraints(69, 122, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtStartDate.setBounds(new Rectangle(69, 164, 270, 19));
        this.add(kdtStartDate, new KDLayout.Constraints(69, 164, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEndDate.setBounds(new Rectangle(69, 202, 270, 19));
        this.add(kdtEndDate, new KDLayout.Constraints(69, 202, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnQuery.setBounds(new Rectangle(149, 255, 66, 19));
        this.add(btnQuery, new KDLayout.Constraints(149, 255, 66, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnReset.setBounds(new Rectangle(251, 255, 66, 19));
        this.add(btnReset, new KDLayout.Constraints(251, 255, 66, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kdtRespPerson
        kdtRespPerson.setBoundEditor(prmtRespPerson);
        //kdtQualityPerson
        kdtQualityPerson.setBoundEditor(prmtQualityPerson);
        //kdtPlanPerson
        kdtPlanPerson.setBoundEditor(prmtPlanPerson);
        //kdtStartDate
        kdtStartDate.setBoundEditor(kdpStartDate);
        //kdtEndDate
        kdtEndDate.setBoundEditor(kdpEndDate);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ReportConditionFilterUIHandler";
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
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    	

    /**
     * output actionConditionQuery_actionPerformed method
     */
    public void actionConditionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionResetCondition_actionPerformed method
     */
    public void actionResetCondition_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionConditionQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConditionQuery() {
    	return false;
    }
	public RequestContext prepareActionResetCondition(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionResetCondition() {
    	return false;
    }

    /**
     * output ActionConditionQuery class
     */     
    protected class ActionConditionQuery extends ItemAction {     
    
        public ActionConditionQuery()
        {
            this(null);
        }

        public ActionConditionQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConditionQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConditionQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConditionQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReportConditionFilterUI.this, "ActionConditionQuery", "actionConditionQuery_actionPerformed", e);
        }
    }

    /**
     * output ActionResetCondition class
     */     
    protected class ActionResetCondition extends ItemAction {     
    
        public ActionResetCondition()
        {
            this(null);
        }

        public ActionResetCondition(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionResetCondition.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionResetCondition.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionResetCondition.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReportConditionFilterUI.this, "ActionResetCondition", "actionResetCondition_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ReportConditionFilterUI");
    }




}