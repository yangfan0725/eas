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
public abstract class AbstractTaskTimePeriodQueryUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTaskTimePeriodQueryUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAdminDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSearch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabReport;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conSchedule;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSchedule;
    protected SerchAction serchAction = null;
    protected ExportExcelAction exportExcelAction = null;
    protected ActionPrint actionPrint = null;
    protected ActionPreview actionPreview = null;
    /**
     * output class constructor
     */
    public AbstractTaskTimePeriodQueryUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTaskTimePeriodQueryUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOnLoad
        String _tempStr = null;
        actionOnLoad.setEnabled(true);
        actionOnLoad.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionOnLoad.SHORT_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.LONG_DESCRIPTION");
        actionOnLoad.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionOnLoad.NAME");
        actionOnLoad.putValue(ItemAction.NAME, _tempStr);
        this.actionOnLoad.setBindWorkFlow(true);
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionOnLoad.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //serchAction
        this.serchAction = new SerchAction(this);
        getActionManager().registerAction("serchAction", serchAction);
        this.serchAction.setBindWorkFlow(true);
         this.serchAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //exportExcelAction
        this.exportExcelAction = new ExportExcelAction(this);
        getActionManager().registerAction("exportExcelAction", exportExcelAction);
         this.exportExcelAction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrint
        this.actionPrint = new ActionPrint(this);
        getActionManager().registerAction("actionPrint", actionPrint);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPreview
        this.actionPreview = new ActionPreview(this);
        getActionManager().registerAction("actionPreview", actionPreview);
         this.actionPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.conAdminDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtAdminDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tabReport = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDMenu1 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.kDMenuItem1 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDWorkButton3 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton4 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuItem2 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem3 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.conProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conSchedule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSchedule = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conAdminDept.setName("conAdminDept");
        this.conAdminPerson.setName("conAdminPerson");
        this.conStartDate.setName("conStartDate");
        this.conEndDate.setName("conEndDate");
        this.btnSearch.setName("btnSearch");
        this.prmtAdminDept.setName("prmtAdminDept");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        this.pkEndDate.setName("pkEndDate");
        this.pkStartDate.setName("pkStartDate");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.tabReport.setName("tabReport");
        this.tblMain.setName("tblMain");
        this.kDMenu1.setName("kDMenu1");
        this.kDMenuItem1.setName("kDMenuItem1");
        this.kDWorkButton3.setName("kDWorkButton3");
        this.kDWorkButton4.setName("kDWorkButton4");
        this.kDMenuItem2.setName("kDMenuItem2");
        this.kDMenuItem3.setName("kDMenuItem3");
        this.conProject.setName("conProject");
        this.prmtProject.setName("prmtProject");
        this.conSchedule.setName("conSchedule");
        this.prmtSchedule.setName("prmtSchedule");
        // CoreUI
        // conAdminDept		
        this.conAdminDept.setBoundLabelText(resHelper.getString("conAdminDept.boundLabelText"));		
        this.conAdminDept.setBoundLabelLength(100);		
        this.conAdminDept.setBoundLabelUnderline(true);
        // conAdminPerson		
        this.conAdminPerson.setBoundLabelText(resHelper.getString("conAdminPerson.boundLabelText"));		
        this.conAdminPerson.setBoundLabelLength(100);		
        this.conAdminPerson.setBoundLabelUnderline(true);
        // conStartDate		
        this.conStartDate.setBoundLabelText(resHelper.getString("conStartDate.boundLabelText"));		
        this.conStartDate.setBoundLabelLength(100);		
        this.conStartDate.setBoundLabelUnderline(true);
        // conEndDate		
        this.conEndDate.setBoundLabelText(resHelper.getString("conEndDate.boundLabelText"));		
        this.conEndDate.setBoundLabelLength(100);		
        this.conEndDate.setBoundLabelUnderline(true);
        // btnSearch
        this.btnSearch.setAction((IItemAction)ActionProxyFactory.getProxy(serchAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));		
        this.btnSearch.setToolTipText(resHelper.getString("btnSearch.toolTipText"));
        // prmtAdminDept		
        this.prmtAdminDept.setCommitFormat("$number$");		
        this.prmtAdminDept.setDisplayFormat("$name$");		
        this.prmtAdminDept.setEditFormat("$number$");
        this.prmtAdminDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAdminDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAdminPerson		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$number$");		
        this.prmtAdminPerson.setCommitFormat("$number$");		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.F7PersonQuery");
        this.prmtAdminPerson.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAdminPerson_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkEndDate
        // pkStartDate
        // kDWorkButton2
        this.kDWorkButton2.setAction((IItemAction)ActionProxyFactory.getProxy(exportExcelAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton2.setText(resHelper.getString("kDWorkButton2.text"));		
        this.kDWorkButton2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // tabReport
        // tblMain

        

        // kDMenu1		
        this.kDMenu1.setText(resHelper.getString("kDMenu1.text"));		
        this.kDMenu1.setMnemonic(66);
        // kDMenuItem1
        this.kDMenuItem1.setAction((IItemAction)ActionProxyFactory.getProxy(exportExcelAction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem1.setText(resHelper.getString("kDMenuItem1.text"));		
        this.kDMenuItem1.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // kDWorkButton3
        this.kDWorkButton3.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton3.setText(resHelper.getString("kDWorkButton3.text"));		
        this.kDWorkButton3.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // kDWorkButton4
        this.kDWorkButton4.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton4.setText(resHelper.getString("kDWorkButton4.text"));		
        this.kDWorkButton4.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // kDMenuItem2
        this.kDMenuItem2.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem2.setText(resHelper.getString("kDMenuItem2.text"));		
        this.kDMenuItem2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // kDMenuItem3
        this.kDMenuItem3.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem3.setText(resHelper.getString("kDMenuItem3.text"));		
        this.kDMenuItem3.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // conProject		
        this.conProject.setBoundLabelText(resHelper.getString("conProject.boundLabelText"));		
        this.conProject.setBoundLabelLength(100);		
        this.conProject.setBoundLabelUnderline(true);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setEditFormat("$longNumber$");		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setCommitFormat("$longNumber$");
        this.prmtProject.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProject_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // conSchedule		
        this.conSchedule.setBoundLabelText(resHelper.getString("conSchedule.boundLabelText"));		
        this.conSchedule.setBoundLabelLength(100);		
        this.conSchedule.setBoundLabelUnderline(true);
        // prmtSchedule		
        this.prmtSchedule.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleQuery");		
        this.prmtSchedule.setEditFormat("$number$");		
        this.prmtSchedule.setDisplayFormat("$name$");		
        this.prmtSchedule.setCommitFormat("$number$");
        this.prmtSchedule.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSchedule_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        conAdminDept.setBounds(new Rectangle(630, 10, 270, 19));
        this.add(conAdminDept, new KDLayout.Constraints(630, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conAdminPerson.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(conAdminPerson, new KDLayout.Constraints(10, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conStartDate.setBounds(new Rectangle(320, 35, 270, 19));
        this.add(conStartDate, new KDLayout.Constraints(320, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conEndDate.setBounds(new Rectangle(630, 35, 270, 19));
        this.add(conEndDate, new KDLayout.Constraints(630, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSearch.setBounds(new Rectangle(940, 35, 60, 19));
        this.add(btnSearch, new KDLayout.Constraints(940, 35, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tabReport.setBounds(new Rectangle(10, 62, 993, 550));
        this.add(tabReport, new KDLayout.Constraints(10, 62, 993, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(conProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conSchedule.setBounds(new Rectangle(320, 10, 270, 19));
        this.add(conSchedule, new KDLayout.Constraints(320, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conAdminDept
        conAdminDept.setBoundEditor(prmtAdminDept);
        //conAdminPerson
        conAdminPerson.setBoundEditor(prmtAdminPerson);
        //conStartDate
        conStartDate.setBoundEditor(pkStartDate);
        //conEndDate
        conEndDate.setBoundEditor(pkEndDate);
        //tabReport
        tabReport.add(tblMain, resHelper.getString("tblMain.constraints"));
        //conProject
        conProject.setBoundEditor(prmtProject);
        //conSchedule
        conSchedule.setBoundEditor(prmtSchedule);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(kDMenu1);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //kDMenu1
        kDMenu1.add(kDMenuItem2);
        kDMenu1.add(kDMenuItem3);
        kDMenu1.add(kDMenuItem1);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(kDWorkButton3);
        this.toolBar.add(kDWorkButton4);
        this.toolBar.add(kDWorkButton2);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.TaskTimePeriodQueryUIHandler";
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
	 * ????????��??
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
     * output prmtAdminDept_dataChanged method
     */
    protected void prmtAdminDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtAdminPerson_willShow method
     */
    protected void prmtAdminPerson_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_willShow method
     */
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSchedule_willShow method
     */
    protected void prmtSchedule_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    	

    /**
     * output actionOnLoad_actionPerformed method
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }
    	

    /**
     * output serchAction_actionPerformed method
     */
    public void serchAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output exportExcelAction_actionPerformed method
     */
    public void exportExcelAction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPreview_actionPerformed method
     */
    public void actionPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionOnLoad(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionOnLoad(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOnLoad() {
    	return false;
    }
	public RequestContext prepareSerchAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareSerchAction() {
    	return false;
    }
	public RequestContext prepareExportExcelAction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareExportExcelAction() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPreview() {
    	return false;
    }

    /**
     * output SerchAction class
     */     
    protected class SerchAction extends ItemAction {     
    
        public SerchAction()
        {
            this(null);
        }

        public SerchAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("SerchAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SerchAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SerchAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTaskTimePeriodQueryUI.this, "SerchAction", "serchAction_actionPerformed", e);
        }
    }

    /**
     * output ExportExcelAction class
     */     
    protected class ExportExcelAction extends ItemAction {     
    
        public ExportExcelAction()
        {
            this(null);
        }

        public ExportExcelAction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ExportExcelAction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ExportExcelAction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ExportExcelAction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTaskTimePeriodQueryUI.this, "ExportExcelAction", "exportExcelAction_actionPerformed", e);
        }
    }

    /**
     * output ActionPrint class
     */     
    protected class ActionPrint extends ItemAction {     
    
        public ActionPrint()
        {
            this(null);
        }

        public ActionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
            _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTaskTimePeriodQueryUI.this, "ActionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPreview class
     */     
    protected class ActionPreview extends ItemAction {     
    
        public ActionPreview()
        {
            this(null);
        }

        public ActionPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
            _tempStr = resHelper.getString("ActionPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTaskTimePeriodQueryUI.this, "ActionPreview", "actionPreview_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "TaskTimePeriodQueryUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}