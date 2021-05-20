/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.forewarn.ForewarnRunTimeFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� 
 * ������ 		��������
 * 	
 * @author 		�ܺ���
 * @version 	EAS7.0
 * @createDate 	2011-08-25
 * @see
 */
public class TaskEvaluationBillEditUI extends AbstractTaskEvaluationBillEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3880197265734492283L;
	private static final Logger logger = CoreUIObject.getLogger(TaskEvaluationBillEditUI.class);
	private String srcId = "";
    
    /**
     * output class constructor
     */
    public TaskEvaluationBillEditUI() throws Exception
    {
        super();
    }
    protected void verifyInputForSave() throws Exception {
    	//super.verifyInputForSave();
    }
    public void loadFields() {
    	// TODO Auto-generated method stub
    	super.loadFields();
    	if(getUIContext().get("taskId")!=null){
    		editData.setRelateTask((FDCScheduleTaskInfo) getUIContext().get("taskId"));
    	}
    	fillPnlTaskInfo();
    	
    }
    
    private void fillPnlTaskInfo(){
    	FDCScheduleTaskInfo info = editData.getRelateTask();
    	if(info != null){
    		this.txtTaskName.setText(info.getName());
    		this.txtTaskNumber.setText(info.getNumber());
    		this.txtTotalPercent.setValue(info.getComplete());
    		this.txtTotalWorkload.setValue(info.getWorkLoad());
    		this.prmtAdminDept.setValue(info.getAdminDept());
    		this.prmtAdminPerson.setValue(info.getAdminPerson());
    		this.kdpActualEnd.setValue(info.getActualEndDate());
    		this.kdpActualStart.setValue(info.getActualStartDate());
    		this.kdpPlanEnd.setValue(info.getPlanEnd());
    		this.kdpPlanStart.setValue(info.getPlanStart());
    	}
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	// ��֤�ǿ�
    	FDCClientVerifyHelper.verifyRequire(this);
    	
    	if(null == editData.getEvaluationType() )
    	{
    		MsgBox.showWarning("�������Ͳ�����Ϊ��!");
    		SysUtil.abort();
    	}
    	if(null == editData.getEvaluationResult() )
    	{
    		MsgBox.showWarning("���۽��������Ϊ��!");
    		SysUtil.abort();
    	}
    	if(!StringUtils.isEmpty(this.txtEvaluationDes.getText()) && this.txtEvaluationDes.getText().trim().length()>1024){
    		FDCMsgBox.showError("����˵��ֻ����¼��1024���ַ�.");
    		abort();
    	}
    	
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	initSeting();
    	controlButtonState();
    }
    
    /**
	 * @discription  <���������(ר)��ƻ�������������룬����Ϊ���ܽ��б༭����>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/09/27> <p>
	 * @param        <��>
	 * @return       <����ֵ����>
	 * 
	 * modifier      <��> <p>
	 * modifyDate    <��> <p>
	 * modifyInfo	 <��> <p>
	 * @see          <��ص���>
	 */
	public void controlButtonState(){
		Object obj = this.getUIContext().get("enableState");
		if(null != obj && "enableState".equals(obj.toString().trim())){
			this.btnEdit.setEnabled(false);
			this.btnAudit.setEnabled(false);
		}
		
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	getContextSrcId();
    	FDCClientUtils.setPersonF7(this.prmtEvaluationPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	this.prmtCreator.setDisplayFormat("$person.name$");
    	this.prmtCreator.setEnabled(false);
    	txtEvaluationDes.setMaxLength(1024);
    
    }
    
    /**
	 * 
	 * @description		������ȡ������Id
	 * @author			�ܺ���	
	 * @createDate		2011-08-25
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void getContextSrcId()
    {
//    	if(null != getUIContext().get("taskId"));
//    	{
//    		srcId = getUIContext().get("taskId").toString();
//    	}
    	srcId = editData.getRelateTask().getSrcID();
    	/*
    	 * �û�F7��
    	 */
    	this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.UserInfoQuery");
    }
    
    public boolean isModify() {
    	return false;
    }
    /**
	 * 
	 * @description		��ʼ������
	 * @author			�ܺ���	
	 * @createDate		2011-08-25
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void initSeting()
    {
    	/*
    	 * ��������������鿴��ť����
    	 */
    	this.btnAuditResult.setVisible(false);
    	this.btnAttachment.setVisible(false);
    	
    	/*
    	 * ��ʼ���ؼ�����
    	 */
    	this.pkCreateTime.setEnabled(true);
    	this.prmtCreator.setEditable(false);
    	this.comboEvaluationType.setRequired(true);
    	
    	/*
    	 * F7 �ؼ�����
    	 */
    	this.prmtEvaluationResult.setDisplayFormat("$evaluationResult$");
    	this.prmtEvaluationResult.setEditFormat("$evaluationResult$");
    	this.prmtEvaluationResult.setCommitFormat("$evaluationResult$");
    	
    	this.prmtEvaluationResult.setRequired(true);
    	this.prmtEvaluationResult.setEditable(false);
    	
    	this.pkCreateTime.setValue(new Date());
    	this.pkCreateTime.setEnabled(false);
    	
    	this.prmtCreator.setEnabled(false);
    	
    	this.actionAudit.setVisible(false);
    	
    	this.prmtEvaluationResult.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				/* TODO �Զ����ɷ������ */
				if(comboEvaluationType.getSelectedItem() == null){
					FDCMsgBox.showError("����ѡ���������ͣ�");
					abort();
				}
				EntityViewInfo info = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();

				if (comboEvaluationType.getSelectedItem().equals(TaskEvaluationTypeEnum.QUALITY)) {
					filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.QUALITY_VALUE);
					//R130617-0330����ƻ�ִ��_��������ʱ��ѡ���������ۣ����۽����F7������ʾ��ѡ������ȥ�����Ƿ�ͨ����һ������������ά��ʱ����δ�и��ֶ�
					prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQualityQuery");
				} else {
					filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.SCHEDULE_VALUE);
					prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQuery");
				}
				info.setFilter(filter);
				prmtEvaluationResult.getQueryAgent().resetRuntimeEntityView();
				prmtEvaluationResult.setEntityViewInfo(info);
			}});
    	this.comboEvaluationType.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				
				prmtEvaluationResult.setData(null);
				EntityViewInfo info = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				if (comboEvaluationType.getSelectedItem().equals(TaskEvaluationTypeEnum.QUALITY)) {
					filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.QUALITY_VALUE);
					//R130617-0330����ƻ�ִ��_��������ʱ��ѡ���������ۣ����۽����F7������ʾ��ѡ������ȥ�����Ƿ�ͨ����һ������������ά��ʱ����δ�и��ֶ�
					prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQualityQuery");
				} else {
					filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.SCHEDULE_VALUE);
					prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQuery");
				}
				info.setFilter(filter);
				prmtEvaluationResult.getQueryAgent().resetRuntimeEntityView();
				prmtEvaluationResult.setEntityViewInfo(info);
			}
		});
      
    	this.actionEdit.setVisible(false);
    }
    
   
 

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TaskEvaluationBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}
	protected IObjectValue createNewData() {
		TaskEvaluationBillInfo info = new TaskEvaluationBillInfo();
		if(null != getUIContext().get("taskId")){
			try{
				
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) getUIContext().get("taskId");
				info.setRelateTask(fDCScheduleTaskInfo);
				if(fDCScheduleTaskInfo.getPlanEvaluatePerson() != null && SysContext.getSysContext().getCurrentUserInfo().getPerson() != null && SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString().equals(fDCScheduleTaskInfo.getPlanEvaluatePerson().getId().toString())){
					info.setEvaluationType(TaskEvaluationTypeEnum.SCHEDULE);
					this.prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQuery");
				}
				if(fDCScheduleTaskInfo.getQualityEvaluatePerson() != null && SysContext.getSysContext().getCurrentUserInfo().getPerson() != null && SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().equals(fDCScheduleTaskInfo.getQualityEvaluatePerson().getId())){
					info.setEvaluationType(TaskEvaluationTypeEnum.QUALITY);
					//R130617-0330����ƻ�ִ��_��������ʱ��ѡ���������ۣ����۽����F7������ʾ��ѡ������ȥ�����Ƿ�ͨ����һ������������ά��ʱ����δ�и��ֶ�
					this.prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQualityQuery");
				}
			}catch(Exception ex){
				handUIException(ex);
			}
		}
		
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}
	
	/**
	 * 
	 * @description		SaveData
	 * @author			�ܺ���	
	 * @createDate		2011-08-25
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		/*
		 * ����id
		 */
		if (editData.getSrcRelateTask() == null || editData.getRelateTask() == null
				|| editData.getRelateTask().getSchedule() == null) {
			editData.setRelateTask((FDCScheduleTaskInfo) getUIContext().get("taskId"));
		}
		
		editData.setSrcRelateTask(editData.getRelateTask().getSrcID());
		editData.setNumber(BOSUuid.create((new TaskEvaluationInfo()).getBOSType()).toString());
		editData.setName(BOSUuid.create((new TaskEvaluationInfo()).getBOSType()).toString());
		super.actionSave_actionPerformed(e);
		//����BOSԤ��actionSave���ܴ�����ֱ���ֶ�����
		ForewarnRunTimeFactory.getRemoteInstance().execRightnowForewarn(this.getClass().getName(), "ActionSave", editData.getId().toString());
	}
	
	public void storeFields() {
		editData.setCreator((UserInfo) this.prmtCreator.getValue());
		super.storeFields();
		editData.setEvaluationDes(txtEvaluationDes.getText().trim());
		
	}
	
	
	/**
	 * 
	 * @description		�������͸ı��¼����˶�Ӧ���͵����۽��
	 * @author			�ܺ���	
	 * @createDate		2011-08-25
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
	protected void comboEvaluationType_itemStateChanged(ItemEvent e)
			throws Exception {
		TaskEvaluationTypeEnum item =(TaskEvaluationTypeEnum) this.comboEvaluationType.getSelectedItem();
		if(null != item){
		
			EntityViewInfo info = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(item.getValue().equals(TaskEvaluationTypeEnum.QUALITY_VALUE))
			{
				filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.QUALITY_VALUE);
				//R130617-0330����ƻ�ִ��_��������ʱ��ѡ���������ۣ����۽����F7������ʾ��ѡ������ȥ�����Ƿ�ͨ����һ������������ά��ʱ����δ�и��ֶ�
				this.prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQualityQuery");
			}else{
				filter.appendFilterItem("evaluationType", TaskEvaluationTypeEnum.SCHEDULE_VALUE);
				this.prmtEvaluationResult.setQueryInfo("com.kingdee.eas.fdc.schedule.app.TaskEvaluationQuery");
			}
			info.setFilter(filter);
			this.prmtEvaluationResult.setEntityViewInfo(info);
			
		}
	}
	
	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = super.getSelectors();
        sic.add("creator.person.id");
        sic.add("creator.person.name");
        sic.add("creator.person.number");
		return sic;
	}
}