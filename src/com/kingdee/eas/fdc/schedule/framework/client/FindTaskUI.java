/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sourceforge.ganttproject.task.CustomColumnsValues;
import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleBaseEditUI;
import com.kingdee.eas.fdc.schedule.client.MainScheduleEditUI;
import com.kingdee.eas.fdc.schedule.client.MainScheduleExecuteUI;
import com.kingdee.eas.fdc.schedule.client.TotalScheduleEditUI;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.util.enums.EnumUtils;

/**
 * ����ͼ����λ����
 * <p>
 * Ĭ��ֻ֧�ֱ��롢�����ж�λ
 * 2012-2-27 �������β��š�ҵ�����͡�����������
 * 
 * @author emanon
 */
public class FindTaskUI extends AbstractFindTaskUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FindTaskUI.class);
	FDCScheduleBaseEditUI owner;
	// ����ͼ
	ScheduleGanttProject gantt;
	// ����ͼ���������񼯺�
	private Task[] tasks;
	// ��ǰѡ�е�������
	private int curSelecte = -1;
	
	private FindTaskUI ui = this;

	/**
	 * output class constructor
	 */
	public FindTaskUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		Object ownerUI = getUIContext().get("Owner");
		if (ownerUI == null || !(ownerUI instanceof FDCScheduleBaseEditUI)) {
			logger.warn("��ǰ��λ����ֻ֧�ָ���ͼ�༭���涨λ����̳�FDCScheduleBaseEditUI��");
			destroyWindow();
		}
		owner = (FDCScheduleBaseEditUI) ownerUI;
		gantt = owner.ganttProject;
		tasks = gantt.getTaskManager().getTasks();
		List tasks = gantt.getTaskSelectionContext().getSelectedTasks();
		if (tasks != null && tasks.size() > 0) {
			curSelecte = ((KDTask) tasks.get(0)).getTaskID();
		}
		Object lstItem = null;
		List newValue = new ArrayList();
		if(!(owner instanceof MainScheduleEditUI) && !(owner instanceof TotalScheduleEditUI) && !(owner instanceof MainScheduleExecuteUI)){
			for(int i=0;i<lstColumns.getElementCount();i++){
				lstItem  = lstColumns.getModel().getElementAt(i);
				if(!lstItem.equals(GanttTreeTableModelExt.strColBizType)){
					newValue.add(lstItem.toString());
				}
			}
			lstColumns.removeAllElements();
			lstColumns.setListData(newValue.toArray());
			
		}
		
		
		//�������β���f7
		final KDBizPromptBox prmtRespDept = new KDBizPromptBox();
		FDCClientUtils.setRespDeptF7(prmtRespDept, this);
		prmtRespDept.setSize(txtContent.getSize());
		prmtRespDept.setBounds(txtContent.getBounds());
		add(prmtRespDept);
		prmtRespDept.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				AdminOrgUnitInfo org =(AdminOrgUnitInfo) prmtRespDept.getValue();
				txtContent.setText(org.getName());
			}});
		prmtRespDept.setEditable(false);
		
		//����ҵ������f7
		final KDBizPromptBox prmtBizType = new KDBizPromptBox();
		prmtBizType.setSize(txtContent.getSize());
		prmtBizType.setBounds(txtContent.getBounds());
		prmtBizType.setEnabledMultiSelection(true);
		prmtBizType.setDisplayFormat("$name$");
		prmtBizType.setEditFormat("$name");
		add(prmtBizType);
		prmtBizType.setEditable(false);
		
		prmtBizType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.ScheduleBizTypeQuery");
		prmtBizType.getQueryAgent().getQueryInfo().setAlias("ҵ������");
		prmtBizType.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				Object[] values = (Object[]) prmtBizType.getValue();
				ScheduleBizTypeInfo bizType = null;
				StringBuffer str = new StringBuffer();
				for(int i=0;i<values.length;i++){
					bizType = (ScheduleBizTypeInfo) values[i];
					str.append(bizType.getName());
					if(i<values.length-1){
						str.append(",");
					}
				}
				txtContent.setText(str.toString());
			}});
		
		//�����������f7
		final KDComboBox cmb = new KDComboBox();
    	cmb.setSize(txtContent.getSize());
    	cmb.setBounds(txtContent.getBounds());
    	cmb.addItems(EnumUtils.getEnumAliases("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum"));
    	cmb.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				if(cmb!=null && cmb.getSelectedItem() != null){
					txtContent.setText(cmb.getSelectedItem().toString());
				}
				
			}});
    	add(cmb);
    	cmb.setSelectedIndex(0);
		lstColumns.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e) {
			    if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColTaskType)){
			    	txtContent.setVisible(false);
			    	prmtBizType.setVisible(false);
			    	prmtRespDept.setVisible(false);
                    cmb.setVisible(true);
                    txtContent.setText("һ������");
			    	
			    }else if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColBizType)){
			    	txtContent.setVisible(false);
			    	prmtRespDept.setVisible(false);
			    	cmb.setVisible(false);
			    	prmtBizType.setVisible(true);
			    }else if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColAdminDept)){
			    	txtContent.setVisible(false);
			    	prmtRespDept.setVisible(true);
			    	cmb.setVisible(false);
			    }else{
			    	cmb.setVisible(false);
			    	prmtBizType.setVisible(false);
			    	prmtRespDept.setVisible(false);
			    	txtContent.setText("");
			    	txtContent.setVisible(true);
			    }
				
				
			}});
	}

	/**
	 * û�м̳�CoreUI���˴�onShow()����û�ã���doLayout��ѡ�������
	 */
	public void doLayout() {
		super.doLayout();
		txtContent.requestFocus();
	}

	/**
	 * ��λ
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		// ȡֵ����
		String columnName = (String) lstColumns.getSelectedValue();
		// �Ƿ�ģ��
		boolean isFuzzy = cbFuzzy.isSelected();
		// ��������
		String content = txtContent.getText();
		// ���ҷ���
		boolean isBack = rbBack.isSelected();
		if (FDCHelper.isEmpty(content)) {
			return;
		}
		if (isBack) {
			for (int i = curSelecte + 1; i < tasks.length; i++) {
				if (isThisTask(columnName, isFuzzy, content, i)) {
					break;
				}
			}
		} else {
			for (int i = curSelecte - 1; i >= 0; i--) {
				if (isThisTask(columnName, isFuzzy, content, i)) {
					break;
				}
			}
		}
	}

	/**
	 * �������Ƿ������ѯ����
	 * <p>
	 * ��ȡ�˷�����������ǰ������ѯʱ���ɵ��ã�ֻ�жϵ�ǰ��i���Ƿ��������
	 * 
	 * @param columnName
	 *            ��ѯ����
	 * @param isFuzzy
	 *            �Ƿ�ģ��
	 * @param content
	 *            ��ѯ����
	 * @param i
	 *            �к�
	 * @return
	 */
	protected boolean isThisTask(String columnName, boolean isFuzzy,
			String content, int i) {
		if (tasks[i] instanceof KDTask) {
			KDTask task = (KDTask) tasks[i];
			CustomColumnsValues cusValues = task.getCustomValues();
			if (cusValues != null) {
				String value = null;
				Object taskValue = cusValues.getValue(columnName);
				if( taskValue instanceof String){
					value = (String) cusValues.getValue(columnName);
				}else if(taskValue instanceof DataBaseInfo)
				   value = ((DataBaseInfo) cusValues.getValue(columnName)).getName();
				if (FDCHelper.isEmpty(value)) {
					return false;
				} else {
					if ("�������".equals(columnName)) {
						value = value.replaceAll("!", ".");
					}
					if (isFuzzy) {
						// ģ����ѯ
						if (value.indexOf(content) >= 0) {
							selectAndCenter(i, task);
							return true;
						} else {
							return false;
						}
					} else {
						// ��ȷ��ѯ
						if (content.equals(value)) {
							selectAndCenter(i, task);
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * ѡ��һ�����񲢾���
	 * 
	 * @param i
	 * @param task
	 */
	protected void selectAndCenter(int i, KDTask task) {
		gantt.getTree().selectTask(task, false);
		curSelecte = i;
		try {
			owner.actionZoomCenter_actionPerformed(null);
		} catch (Exception e) {
			// ���ܾ��в�ǿ�󣬲������쳣
			logger.warn(e);
		}
	}

	/**
	 * �ر�
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

}