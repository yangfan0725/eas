/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import net.sourceforge.ganttproject.task.TaskSelectionManager;

import org.apache.log4j.Logger;
import org.jdesktop.swing.table.TableColumnExt;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * output class name
 */
public class FDCScheduleTaskPropertiesUI extends AbstractFDCScheduleTaskPropertiesUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCScheduleTaskPropertiesUI.class);
    private ITaskPanelHelper predecessorsPanelHelper=null;
    private ITaskPanelHelper mainPanelHelper=null;
    private ITaskPanelHelper extPanelHelper=null;
    private boolean isExecting=false;
    private List helpers=new ArrayList(3);
    
    /**
     * ��ʱĬ��ѡ���ҳǩ
     */
    public final static String INITSELECTTABINDEX="initSelectTabIndex";
    /**
     * output class constructor
     */
    public FDCScheduleTaskPropertiesUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.txtEffectTimes.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	this.txtNatureTimes.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	this.txtProcess.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	if(getScheduleGanttProject()!=null){
    		selectedTasks= getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
    		TableColumnExt column = getScheduleGanttProject().getGanttTreeTable().getColumn(1);
    		String columnName=column.getTitle();
    		if(columnName!=null&&columnName.equals(GanttTreeTableModelExt.strColPrefixNode)){
    			//ѡ����ǰ������
    			getUIContext().put(FDCScheduleTaskPropertiesUI.INITSELECTTABINDEX,new Integer(1));
    		}
    	}
    	
    	if(getUIContext().get("isScheduleExec")==Boolean.TRUE){
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)getSelectedTask().getScheduleTaskInfo();
    		if(task!=null&&task.get("ScheduleState")==ScheduleStateEnum.EXETING){
        		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
        		
        		//Ӧ����Ҫ��ֻ�й�������־��������־Ϊ�ƻ�������ά���������Ķ��ſ�		- by neo
//        		if(task.getAdminPerson()!=null&&currentUserInfo!=null&&task.getAdminPerson().getId().equals(currentUserInfo.getPerson().getId())){
        			isExecting=true;
        			setOprtState(OprtState.VIEW);
//        		}
        	}
    		
    	}
    	/**
    	 * ������Ϣ
    	 */
    	if(mainPanelHelper==null){
    		mainPanelHelper=new TaskMainPanelHelper(this);
    		registerHelper(mainPanelHelper);
    	}
        /**
         * ǰ�ýڵ����ݼ���
         */
    	if(predecessorsPanelHelper==null){
    		if(getUIContext().get("selectTask")!=null){
    			this.predecessorsPanelHelper=new PredecessorsExtPanelHelper(this);
    		}else{
    			this.predecessorsPanelHelper=new PredecessorsPanelHelper(this);
    		}
    		registerHelper(predecessorsPanelHelper);
    	}
    	/**
    	 * ��չ��Ϣ
    	 */
    	if(extPanelHelper == null){
    		extPanelHelper = new TaskExtProPanelHelper(this);
    		registerHelper(extPanelHelper); //debug
    	}
    	load();
//    	���⴦���������רҵ����༭���棬��ѡ�������������������Ϊ�鿴״̬
    	if(SpecialScheduleEditUI.class.equals(getUIContext().get(UIContext.OWNER).getClass())){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)getSelectedTask().getScheduleTaskInfo();
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(task.getWbs().getTaskType().getId().toString())){
				setOprtState(OprtState.VIEW);
			}
    	}
    	initUIStatus();
    	setSelectTabPanel();
    	//ȥ��������Ϣҳǩ
    	kDTabbedPane1.remove(materialPlanPanel);
    }
    
    protected void load() throws EASBizException, BOSException {
		for(int i=0;i<helpers.size();i++){
			((ITaskPanelHelper)helpers.get(i)).load();
		}
		
	}

    protected void commit() throws EASBizException, BOSException {
		for(int i=0;i<helpers.size();i++){
			((ITaskPanelHelper)helpers.get(i)).commit();
		}
		
	}
    
	protected void registerHelper(ITaskPanelHelper helper){
    	helpers.add(helper);
    }
    
	protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnTaskNext.setVisible(false);
    	this.btnTaskPre.setVisible(false);
    }
    
    public void loadFields() {}

    private List selectedTasks=null;
    
    private KDTask selectTask=null;
    public KDTask getSelectedTask(){
    	if(selectTask==null){
    		//ֱ�Ӵ��������ڵ�������
    		selectTask=(KDTask)getUIContext().get("selectTask");
    	}
    	if(selectTask==null&&selectedTasks!=null){
    		selectTask=(KDTask)selectedTasks.get(0);
    	}
    	return selectTask;
    }
    
    
    
    protected void updateTask(){
    	KDTask selectedTask = getSelectedTask();
    	try{
    		commit();
    	}catch(Exception e){
    		this.handUIExceptionAndAbort(e);
    	}
    	if(getScheduleGanttProject()!=null){
	    	TaskSelectionManager selectMgr=(TaskSelectionManager)getScheduleGanttProject().getTaskSelectionContext();
	    	selectMgr.clear();
	    	
	    	selectTask.getStart();
	    	selectTask.getEnd();
	    	
//	    	selectTask.setStart(ScheduleParserHelper.parseDateToGanttCalendar((Date)txtStartDate.getValue()));
//	    	selectTask.setEnd(ScheduleParserHelper.parseDateToGanttCalendar((Date)txtEndDate.getValue()));
	    	selectMgr.addTask(selectedTask);
	    	getScheduleGanttProject().getArea().repaint();
    	}
    }
      
  
    
    public ScheduleGanttProject getScheduleGanttProject(){
    	return (ScheduleGanttProject)getUIContext().get("ScheduleGanttProject");
    }
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
//    	super.btnOK_actionPerformed(e);
    	updateTask();
//    	updateTaskExtProp(); //ֱ��ʹ��commit�ӿ� 
    	destroyWindow();
    	
    }
    
    /**
     * �Ƿ�������ִ��
     * @return
     */
    protected boolean isExecuting(){
    	return isExecting;
    }
    
    /**
     * ����ִ��״̬�ĸ�UI״̬
     */
    protected void setExecutingUIStatus(){
		for(int i=0;i<helpers.size();i++){
			((ITaskPanelHelper)helpers.get(i)).setExecutingUIStatus();
		}
    }
    
    /**
     * ���ò鿴״̬�ĸ�UI״̬
     */
    protected void setViewUIStatus(){
		for(int i=0;i<helpers.size();i++){
			((ITaskPanelHelper)helpers.get(i)).setViewUIStatus();
		}
    }
    
    /**
     * ���ñ༭״̬��UI״̬--ֻ�мƻ����Ƶ�ʱ��Ż��б༭״̬����������
     */
    protected void setEditUIStatus(){
		for(int i=0;i<helpers.size();i++){
			((ITaskPanelHelper)helpers.get(i)).setEditUIStatus();
		}
    }
    
    /**
     * ����״̬��ʼ��
     */
    private void initUIStatus(){
    	//���ظ�����ť�� modify by zhiqiao_yang at 2010-06-13
    	this.btnAttacthment.setVisible(false);
    	//�鿴�ܽ��ȼƻ�ʱ���������Կ�����Ϣ���ɱ༭��ֻ�ܲ鿴 modify by warship at 2010/07/21
		if (getOprtState().equals("FINDVIEW")) {
	    	setViewUIStatus();
    	}
    	if(isExecuting()){
    		setExecutingUIStatus();
    	} else if (getOprtState().equals(OprtState.VIEW)) {
    		setViewUIStatus();
    	} else if (getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)) {
    		setEditUIStatus();
    	}
		txtStartDate.setEnabled(false);
		txtEndDate.setEnabled(false);
		txtStartDate.setEditable(false);
		txtEndDate.setEditable(false);
    }

    
    /**
     * ��������һ�������ڿؼ��Ƿ���Ա༭
     * @param container
     */
    protected void lockContainer(java.awt.Container container) {
		Component comps[] = container.getComponents();
		if (comps != null && comps.length > 0) {
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof KDPanel
						|| comps[i] instanceof KDScrollPane
						|| comps[i] instanceof KDSplitPane
						|| comps[i] instanceof JTabbedPane
                        || comps[i] instanceof KDLabelContainer) {
					lockContainer((java.awt.Container) comps[i]);
				} else {
					comps[i].setEnabled(false);
				}
			}
		}
	}
    
    /**
     * �����Խ���
     * @param ui
     * @param selectTask
     * @param oprtState
     * @return
     */
    public static Object showUI(CoreUI ui,KDTask selectTask,String oprtState){
		String propertyUIName="com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesUI";
		UIContext context=new UIContext(ui);
		context.put("selectTask", selectTask);
//		context.put("ScheduleGanttProject",);
		try {
			IUIWindow myUi=UIFactory.createUIFactory(UIFactoryName.MODEL).create(propertyUIName, context,null,oprtState,WinStyle.SHOW_TOOLBAR);
			FDCScheduleTaskPropertiesUI uiObject=(FDCScheduleTaskPropertiesUI)myUi.getUIObject();
			myUi.show();
			return uiObject.selectTask;
		} catch (UIException e1) {
			ui.handUIException(e1);
		}
		return null;
    }
    
    /**
     * �����Խ���
     * @param ui
     * @param selectTask
     * @param oprtState
     * @return
     */
    public static Object showUI(CoreUI ui,KDTask selectTask,String oprtState,int selectTabIndex){
		String propertyUIName="com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesUI";
		UIContext context=new UIContext(ui);
		context.put("selectTask", selectTask);
		context.put(FDCScheduleTaskPropertiesUI.INITSELECTTABINDEX,new Integer(selectTabIndex));
//		context.put("ScheduleGanttProject",);
		try {
			IUIWindow myUi=UIFactory.createUIFactory(UIFactoryName.MODEL).create(propertyUIName, context,null,oprtState,WinStyle.SHOW_TOOLBAR);
			FDCScheduleTaskPropertiesUI uiObject=(FDCScheduleTaskPropertiesUI)myUi.getUIObject();
			myUi.show();
			return uiObject.selectTask;
		} catch (UIException e1) {
			ui.handUIException(e1);
		}
		return null;
    }
    
    public void loadMaterial() throws BOSException{
    	((TaskExtProPanelHelper)extPanelHelper).loadMaterial();
    }
    
    public void loadTaskLog()throws BOSException{
    	((TaskExtProPanelHelper)extPanelHelper).loadTaskLog();
    }
    
    protected void setSelectTabPanel(){
    	Integer selectIndex=(Integer)this.getUIContext().get(FDCScheduleTaskPropertiesUI.INITSELECTTABINDEX);
    	if(selectIndex!=null){
    		if(this.kDTabbedPane1.getTabCount()>selectIndex.intValue()&&selectIndex.intValue()>0){
    			this.kDTabbedPane1.setSelectedIndex(selectIndex.intValue());
    		}
    	}
    }
}