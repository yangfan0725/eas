/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� 
 * ������ 		����ƻ�ִ�������ҳ
 * 	
 * @author 		�ܺ���
 * @version 	EAS7.0
 * @createDate 	2011-08-29
 * @see
 */
public class MainScheduleExecuteUI extends AbstractMainScheduleExecuteUI {
	private static final long serialVersionUID = 5091351194989858469L;
	private static final Logger logger = CoreUIObject.getLogger(MainScheduleExecuteUI.class);
	public ScheduleGanttProject scheduleGanttProject;

	public MainScheduleExecuteUI() throws Exception {
		super();
	}
	

	/**
	 * 
	 * @description 	Load��ʼ������
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return
	 * 
	 * @version 		EAS7.0
	 */
	public void onLoad() throws Exception {
		/*
		 * ����UI״̬��������Table˫���¼���������
		 */
		setOprtState("MAIN_SCHEDULE_VIEW");
		super.onLoad();
		
		/* modified by zhaoqin for R140103-0128,R140113-0141 on 2014/03/31 */
		//whenComTaskFilterChange();
	}
	
	


	/**
	 * 
	 * @description 	��ʼ�����ò˵�
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return
	 * 
	 * @version 		EAS7.0
	 */
	protected void initWorkButton() {

		this.MenuService.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.btnSaveNewTask.setVisible(false);
		
		/*
		 * ���Ȼ㱨���������ۡ���Ŀ�ܱ�����Ŀ�±���ť
		 */
		this.btnScheduleReport.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.btnTaskApprise.setIcon(EASResource.getIcon("imgTbtn_manualcount"));
		this.btnWeekReport.setIcon(EASResource.getIcon("imgTbtn_newdialog"));
		this.btnMonthReport.setIcon(EASResource.getIcon("imgTbtn_newdialog"));
		
		this.btnScheduleReport.setEnabled(true);
		this.btnTaskApprise.setEnabled(true);
		this.btnWeekReport.setEnabled(true);
		this.btnMonthReport.setEnabled(true);
		
		super.initWorkButton();
	}

	public void onShow() throws Exception {
		super.onShow();
		initSetButton();	
		
		String paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_SINGLE");
		if("false".equals(paramValue)){
			this.btnScheduleReport.setEnabled(false);
		}
		this.btnMonthReport.setEnabled(false);
	}
	
	private  void addStatePanel(){
		JComponent statePanel = null;
		if(getStatePanel() != null){
			statePanel = getStatePanel();
		}else{
			statePanel = new KDTaskStatePanel();
		}
		statePanel.setVisible(true);
		this.paySubway.add(statePanel);
		this.paySubway.setVisible(true);
	}
	/**
	 * 
	 * �������ṩ��Զ��ο����Ľӿ�
	 * @return
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2012-2-23
	 */
	public JComponent getStatePanel(){
		return null;		
	}
	

	/**
	 * 
	 * @description 	��ʼ���������ݡ���ť
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return
	 * 
	 * @version 		EAS7.0
	 */
	public void initSetButton() {

		// this.btnZoomOut.setVisible(false);
		// this.btnZoomIn.setVisible(false);
		this.btnAttachment.setVisible(true);
		this.btnSubmit.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnBatChangeRespDept.setVisible(false);
		this.btnCompareVer.setVisible(false);
		this.btnProperty.setText("�鿴");
		btnProperty.setIcon(EASResource.getIcon("imgTbtn_view"));
		
		/*
		 * ����״̬pane*/
	     addStatePanel();

		this.cbState.setVisible(false);
		this.contState.setVisible(false);

		
		this.btnHisVerion.setVisible(false);
		this.btnRestore.setVisible(false);
		this.btnAdjust.setVisible(false);
		
		this.btnImportProject.setVisible(false);
		this.btnImportProject.setEnabled(false);
		this.btnImportPlanTemplate.setVisible(false);
		this.btnImportPlanTemplate.setEnabled(false);
		
		this.btnSave.setVisible(false);
		this.btnExportPlanTemplate.setVisible(false);
		this.btnAudit.setVisible(false);
		//����������������ϵİ�ť״̬��Ҫ�仯
		if(editData.getState().equals(ScheduleStateEnum.AUDITTED ) && prmtCurproject.getData()!= null && this.prmtCurproject.getData().equals(editData.getProject())){
			this.btnWeekReport.setEnabled(true);
			this.btnMonthReport.setEnabled(true);
			this.btnScheduleReport.setEnabled(true);
			this.btnTaskApprise.setEnabled(true);
		}else{
			this.btnWeekReport.setEnabled(false);
			this.btnMonthReport.setEnabled(false);
			this.btnScheduleReport.setEnabled(false);
			this.btnTaskApprise.setEnabled(false);
		}	
		
		this.btnAdjust.setVisible(false);
		this.actionAdjust.setVisible(false);
		this.actionCopy.setVisible(false);
		remove(btnAdjust);
		
		try {
			String	paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_SINGLE");
			if("false".equals(paramValue)){
				this.btnScheduleReport.setEnabled(false);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 	���ݱ����,������֤�����Ƿ��޸�ȷ�ϱ���
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return 			boolean
	 * 
	 * @version 		EAS7.0
	 */
	public boolean isModify() {
		return false;
	}

	/**
	 * 
	 * @description 	��ͼ���˵�ǰ������Ŀ��������
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return
	 * 
	 * @version 		EAS7.0
	 */
	protected void kDComTaskFilter_itemStateChanged(ItemEvent e) throws Exception {
		FDCScheduleExecuteEnum enumType = (FDCScheduleExecuteEnum) e.getItem();
		if (e.getStateChange() == 1) {
			whenComTaskFilterChange();
			initSetButton();
		}
	}

	/**
	 * ��kDComTaskFilter_itemStateChanged��ȡ�������ɹ������ط����ã���ر���������ҳǩ�����Ȼ㱨����������ʱ��
	 * @Author��owen_wen
	 * @CreateTime��2013-8-26
	 */
	protected void whenComTaskFilterChange() throws BOSException, EASBizException, SQLException, Exception {
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		Map whenComTaskFilterChangeMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(),
				"whenComTaskFilterChange");
		// ////////////////////////////////////////////////////////////////////////
		
		// 1��ȡ��������
		Map getFDCScheduleTaskCollectionMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(),
				"IFDCScheduleTask.getFDCScheduleTaskCollection");
		EntityViewInfo view = FDCScheduleTaskExecuteHelper.getTaskFilterEntityView(((FDCScheduleExecuteEnum) kDComTaskFilter.getSelectedItem()),
				editData.getId().toString());
		FDCScheduleTaskCollection taskColl = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "IFDCScheduleTask.getFDCScheduleTaskCollection",
				getFDCScheduleTaskCollectionMap);
		
		// 2����������״̬
		FDCScheduleTaskStateHelper.putState(null, taskColl, editData.getProject());
		FDCScheduleTaskStateHelper.putEvaluationState(null, taskColl);
		
		// 3��������ӵ�������
		editData.getTaskEntrys().clear();
		editData.getTaskEntrys().addCollection(taskColl);
		
		// 4��ˢ�½���
		refresh(editData);
		
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "whenComTaskFilterChange", whenComTaskFilterChangeMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	/**
	 * �򿪽��Ȼ㱨����
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-9-20 void
	 * @version EAS7.0
	 * @see
	 */
	public void actionScheduleReport_actionPerformed(ActionEvent e)
			throws Exception {
		
		/*
		 * ��ȡѡ���������ڵ����
		 */
		int [] location = null ;
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			location = getScheduleGanttProject().getTable().getSelectedRows();
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
            FDCScheduleTaskExecuteHelper.checkLeafTask(task, "���Ȼ㱨");
		} else {

			MsgBox.showWarning("��ѡ�����������н��Ȼ㱨!");
			SysUtil.abort();
		}
		
		FDCScheduleTaskExecuteHelper.checkAndShowMsgInfoWhenCompleted(task);

		UIContext uiContext = getPropertyUIContext();
		uiContext.put("taskinfo", task);
		IUIFactory uiFactory = null;
		IUIWindow uiWindow = null;
		try {
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			//uiWindow = uiFactory.create(ScheduleTaskProgressReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			//���ݽ�����Ҫ������ ���ڽ�������������ǩ�Ľ��Ȼ㱨���� Added By Owen_wen 2013-09-06
			uiWindow = uiFactory.create(getPropertityUIName(), uiContext, null, OprtState.ADDNEW);
		} catch (UIException ue) {
			ue.printStackTrace();
		}
		if(uiWindow != null){
			uiWindow.show();
			/*
			 * ��Ҫ��ʹ��refresh()��������Ϊ�������������������ʾ��������Ҫ����ͼ�����˸���whenComTaskFilterChange()
			 * added by Owen-wen 2013-8-26
			 */
			/*
			 * �Ѿ���FDCScheduleTaskPropertiesNewUI.destroyWindow()�����е��ù���,�����ظ�����
			 * modified by zhaoqin for R140317-0259 on 2015/01/29
			 */
			//whenComTaskFilterChange();
			if(location != null && location.length>0){
				getScheduleGanttProject().getTree().selectTreeRow(location[0]);
			}
		}else{
			abort();
		}
		
		
	}
	
	
	/**
	 * @description 	�������۳�ʼ�����¼�
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param
	 * @return 			boolean
	 * 
	 * @version 		EAS7.0
	 */
	public void actionTaskApprise_actionPerformed(ActionEvent e)
			throws Exception {
		
		UIContext uiContext = new UIContext(this);
		
		/*
		 * ��ȡѡ���������ڵ����
		 */
		int [] location = null;
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			location = getScheduleGanttProject().getTable().getSelectedRows();
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("businessType.id");
			sic.add("schedule.projectSpecial.id");
			sic.add("schedule.projectSpecial.name");
			sic.add("schedule.projectSpecial.number");
			sic.add("adminPerson.id");
			sic.add("adminPerson.name");
			sic.add("adminPerson.number");
			sic.add("adminDept.number");
			sic.add("adminDept.name");
			sic.add("adminDept.id");
			sic.add("qualityEvaluatePerson.id");
			sic.add("qualityEvaluatePerson.name");
			sic.add("qualityEvaluatePerson.number");
			sic.add("qualityEvaluatePersonOther.id");
			sic.add("qualityEvaluatePersonOther.name");
			sic.add("qualityEvaluatePersonOther.number");
			task = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(task.getId()),sic);
            FDCScheduleTaskExecuteHelper.checkLeafTask(task, "���Ȼ㱨");
			FDCScheduleTaskExecuteHelper.beforeOperator(task, "��������");
		} else {

			MsgBox.showWarning("��ѡ����������������!");
			SysUtil.abort();
		}
		uiContext.put("taskId", task);
		IUIFactory uiFactory = null;
		IUIWindow uiWindow = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			uiWindow = uiFactory.create(MainTaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		} catch (UIException ue) {
			ue.printStackTrace();
			logger.info(ue.getMessage());
		}
		if(uiWindow != null){
			uiWindow.show();
			/*
			 * ��Ҫ��ʹ��refresh()��������Ϊ�������������������ʾ��������Ҫ����ͼ�����˸���whenComTaskFilterChange()
			 * added by Owen-wen 2013-8-26
			 */
			whenComTaskFilterChange();
			if(location != null && location.length>0){
				getScheduleGanttProject().getTree().selectTreeRow(location[0]);
			}
		}
	
	}
	
	/**
	 * 
	 * 
	 * ������			GetUIName<����ִ��>
	 *		
	 * @author			�ܺ���
	 * @createDate		2011-9-16
	 * @param			 
	 * @return			
	 *
	 * @version		    EAS7.0	
	 *
	 */
	protected String getUIName() {
		return MainScheduleExecuteUI.class.getName();
	}

	protected void prmtCurproject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.prmtCurproject_dataChanged(e);
		this.initSetButton();
		whenComTaskFilterChange();
	}

	public CurProjectInfo curprojectInfo;

	/**
	 * @description ��Ŀ�ܱ���
	 * @author ����ΰ
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public void actionWeekReport_actionPerformed(ActionEvent e) throws Exception {
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning(" ��ѡ�񹤳���Ŀ");
			SysUtil.abort();
		}
		CurProjectInfo curprojectInfo = (CurProjectInfo) prmtCurproject.getValue();
		this.curprojectInfo = curprojectInfo;
		getUIContext().put("curprojectInfo", curprojectInfo);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curprojectInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(getCurrYear())));
		filter.getFilterItems().add(new FilterItemInfo("week", new Integer(getCurrWeekOfYear())));
		 filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id",null,CompareType.EQUALS));
		 filter.getFilterItems().add(new FilterItemInfo("creator.id",SysContext.getSysContext().getCurrentUserInfo().getId().toString(),CompareType.EQUALS));
		view.setFilter(filter);
		ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
		if (collection.size() > 0) {// �༭
			ProjectWeekReportInfo weekReport = collection.get(0);
			FDCScheduleBaseEditUI scheduleUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, weekReport.getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectWeekReportEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} else {
			FDCScheduleBaseEditUI scheduleUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, null);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectWeekReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}

	/**
	 * @description �õ������ǵڼ���
	 * @author ����ΰ
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * ����Ŀ�±��� ��� �޸��ˣ�duhongming 2011-12-26 ��ȡʱ���д���
	 * 
	 */
	public void actionMonthReport_actionPerformed(ActionEvent e) throws Exception {
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning(" ��ѡ�񹤳���Ŀ");
			SysUtil.abort();
		}
		CurProjectInfo curprojectInfo = (CurProjectInfo) prmtCurproject.getValue();
		this.curprojectInfo = curprojectInfo;
		getUIContext().put("curprojectInfo", curprojectInfo);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curprojectInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(getCurrYear())));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(getCurrMonthOfYear())));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id",null,CompareType.EQUALS));
		view.setFilter(filter);
		ProjectMonthReportCollection monthCollection = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
		if (monthCollection.size() > 0) {
			ProjectMonthReportInfo weekReport = monthCollection.get(0);
			UIContext uiContext = new UIContext(this);
			uiContext.put("btnIsVisable", "true");
			uiContext.put(UIContext.ID, weekReport.getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectMonthReportEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} else {
			UIContext uiContext = new UIContext(this);
			uiContext.put("btnIsVisable", "false");
			uiContext.put(UIContext.ID, null);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectMonthReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}
	

	/**
	 * @description �õ������ǵڼ���
	 * @author ���
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public int getMonthOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), 1, 0, 0, 0);
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * ���Ȼ㱨��дҳ���������Ϣ������
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-9-20 void
	 * @version EAS7.0
	 * @throws Exception
	 * @see
	 */
	public void writeback(FDCScheduleTaskInfo info) throws Exception {
		/*
		 * ��ȡѡ���������ڵ����
		 */
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
			// ʵ���깤����
			if (info.getActualEndDate() != null) {
				// userObj.getCustomValues().setValue(GanttTreeTableModelExt.
				// strColActualEndDate, info.getActualEndDate());
			}
			// �����
			if (info.getComplete() != null) {
				task.setComplete(info.getComplete());
			}
			// ʵ�ʿ�ʼ
			if (info.getActualStartDate() != null) {
				task.setActualStartDate(info.getActualStartDate());
			}
			// ʵ�����
			if (info.getActualEndDate() != null) {
				task.setActualEndDate(info.getActualEndDate());
			}
			// �ƻ���ʼ
			if (info.getStart() != null) {
				task.setStart(info.getStart());
			}
			// �ƻ����
			if (info.getEnd() != null) {
				task.setEnd(info.getEnd());
			}
		}
		loadData2Gantt(this.editData);
	}
	/**
	 * 
	 * @description ��ȡ��ǰʱ�����
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @description ��ȡ��ǰʱ�����
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @description ��ȡ��ǰʱ����
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrWeekOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info = getAttacheInfo();
		if (info == null)
			info = new AttachmentUIContextInfo();
		if (info.getBoID() == null || info.getBoID().trim().equals("")) {
			String boID = getSelectBOID();
			info.setBoID(boID);
		}
		info.setEdit(true);
		// 2007-10-26 Ϊ�������޸ĸ���
		String multi = (String) getUIContext().get("MultiapproveAttachment");
		if (multi != null && multi.equals("true")) {
			acm.showAttachmentListUIByBoIDNoAlready(this, info);
		} else {
			acm.showAttachmentListUIByBoID(this, info);
		}
	}
	
	protected String getPropertityUIName() {
		// String propertyUIName = "com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI";
		// String propertyUIName = "com.kingdee.eas.fdc.schedule.client.MainProgressReportBaseSchTaskPropertiesNewUI";
		String propertyUIName = MainProgressReportBaseSchTaskPropertiesNewUI.class.getName();
		
		return propertyUIName;
	}
}