/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class BatchModifyResPersonUI extends AbstractBatchModifyResPersonUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BatchModifyResPersonUI.class);

	private FDCScheduleInfo scheduleInfo = null;
	private FDCScheduleInfo newScheduleInfo = null;

	/**
	 * output class constructor
	 */
	public BatchModifyResPersonUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/*						
	  */
	public void onLoad() throws Exception {
		super.onLoad();
		initTableData();
		kDRightTable.removeRows(false);
		kDRightTable.getStyleAttributes().setLocked(true);
		// ������Ϣ������֯F7�����˿ؼ��ֲ���ʾ
		FDCClientUtils.setRespDeptF7(prmtDutyDep, this);
		FDCClientUtils.setPersonF7(prmtDutyPerson, this, null);
		FDCClientUtils.setPersonF7(prmtQualityAppraisePerson, this, null);
		FDCClientUtils.setPersonF7(prmtScheduleAppraisePerson, this, null);
		cboPanelPoint.setSelected(true);
	}

	protected void initWorkButton() {
		this.btnAllLeftMove.setEnabled(true);
		this.btnAllRightMove.setEnabled(true);
		this.btnLeftMove.setEnabled(true);
		this.btnRightMove.setEnabled(true);
		this.btnClose.setEnabled(true);
		this.btnmMakeSure.setEnabled(true);
		this.btnSaveAndClose.setEnabled(true);
		super.initWorkButton();
	}

	private Set idSet = new HashSet();
	/*���е������б� */
	private List scheduleList = new ArrayList();
	/*�����˺����β���Ϊ�յ������б� */
	List taskList = new ArrayList();

	/**
	 * @description ��ȡ��ǰ�ƻ��ĵ���������䵽��ߴ�ѡ���
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void initTableData() throws Exception {

		if (getUIContext().get("Owner") != null
				&& getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI) {
			FDCScheduleBaseEditUI baseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			scheduleInfo = baseEditUI.scheduleInfo;

			if (baseEditUI.getScheduleGanttProject() == null) {
				FDCMsgBox.showWarning("�ù�����Ŀ��û������");
				SysUtil.abort();
			}
			List tasks = baseEditUI.getScheduleGanttProject().getSortKDTask();
			FDCScheduleTaskInfo taskInfo = null;
			for (int i = 0; i < tasks.size(); i++) {
				taskInfo = (FDCScheduleTaskInfo) ((KDTask) tasks.get(i)).getScheduleTaskInfo();
				scheduleInfo.getTaskEntrys().add(taskInfo);
				if (taskInfo.getAdminPerson() == null 
						|| taskInfo.getAdminDept() == null 
						|| taskInfo.getPlanEvaluatePerson() == null 
						|| taskInfo.getQualityEvaluatePerson() == null) {
					taskList.add(taskInfo);
				}
				scheduleList.add(taskInfo);
			}
		}
		fillTableData(taskList);
	}

	/**
	 * @description Ϊ��ߵı���������
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void fillTableData(List list) {
		kDLeftTable.checkParsed();
		kDLeftTable.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		if (list.size() > 0) {
			IRow row  = null;
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) list.get(i);
				row = kDLeftTable.addRow();
				row.setUserObject(taskInfo);
				row.getCell("id").setValue(taskInfo.getId());
				row.getCell("taskName").setValue(taskInfo.getName());
				if (taskInfo.getAdminPerson() == null) {
					row.getCell("resPerson").setValue(null);
				} else {
					row.getCell("resPerson").setValue(taskInfo.getAdminPerson());
				}
				if (taskInfo.getAdminDept() == null) {
					row.getCell("resDep").setValue(null);
				} else {
					row.getCell("resDep").setValue(taskInfo.getAdminDept());
				}

				row.getCell("level").setValue(new Integer(taskInfo.getLevel()));
				row.getCell("isLeaf").setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
				row.getCell("longNumber").setValue(taskInfo.getLongNumber());
				row.getCell("planEvaluatePerson").setValue(taskInfo.getPlanEvaluatePerson());
				row.getCell("qualityEvaluatePerson").setValue(taskInfo.getQualityEvaluatePerson());
			}
			initTree();
		}
	}

	/**
	 * @description Ϊ��ߵı�񹹽����ṹ
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void initTree() {
		int count = this.kDLeftTable.getRowCount();
		kDLeftTable.getStyleAttributes().setLocked(true);
		IRow row = null;
		String value = "";
		for (int i = 0; i < count; i++) {
			row = this.kDLeftTable.getRow(i);
			if (row.getCell("taskName").getValue() instanceof String) {
				value = (String) row.getCell("taskName").getValue();
			}
			// ȡ�������κ��Ƿ���ϸ�ڵ�
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			CellTreeNode treeNode = new CellTreeNode() {
				public void doTreeClick(KDTable table, ICell currCell) {
					/* TODO �Զ����ɷ������ */
					super.doTreeClick(table, currCell);
					hideAlreadyInRightTable();
				}
			};
			treeNode.setValue(value);// ��ʾ��ֵ
			treeNode.setTreeLevel(level + 1);// ���Σ�
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			row.getCell("taskName").setValue(treeNode);// ����ǰ�湹���ĵ�Ԫ��������
			row.getCell("taskName").getStyleAttributes().setLocked(false);
			
		}


	}
	
	
	public void hideAlreadyInRightTable() {
		List rightTask = getRightData();
		for (int i = 0; i < kDLeftTable.getRowCount(); i++) {
			IRow row = kDLeftTable.getRow(i);
			String id = row.getCell("id").getValue().toString();
			if (rightTask.contains(id)) {
				row.getStyleAttributes().setHided(true);
			}
		}

	}

	/**
	 * @description �޸ĺ�õ���������
	 * @author ����ΰ
	 * @createDate 2011-9-23
	 * @version EAS7.0
	 * @see
	 */
	private Map getAllTask() {
		Map taskMap = new HashMap();
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryInfoId = row.getCell("id").getValue().toString();
				taskInfo.setId(BOSUuid.read(entryInfoId));
				if (row.getCell("resPerson").getValue() != null) {
					taskInfo.setAdminPerson((PersonInfo) row.getCell("resPerson").getValue());
				}
				if (row.getCell("resDep").getValue() != null) {
					taskInfo.setAdminDept((FullOrgUnitInfo) row.getCell("resDep").getValue());
				}

				taskMap.put(entryInfoId, taskInfo);

			}

		}
		return taskMap;

	}

	/**
	 * @description ������ȫ����ʾ���߲�����������ʱ����Ӧ�ı�����ݷ����仯�����ػ���ȫ����ʾ������ҲҪ��Ӧ�ı仯
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void updateTableData(Map taskMap) {

		// ѭ����������
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String taskId = row.getCell("id").getValue().toString().trim();
				if (taskMap.size() > 0 && !taskMap.isEmpty()) {// ���ϴ���0
					if (taskMap.get(taskId) != null) {// ����ID�ж�Map
						// �Ƿ���ڶ���,�������޸Ķ�Ӧ������
						FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) taskMap
								.get(taskId);
						if (scheduleTaskInfo.getAdminPerson() != null) {
							row.getCell("resPerson").setValue(
									scheduleTaskInfo.getAdminPerson());
						}
						if (scheduleTaskInfo.getAdminDept() != null) {
							row.getCell("resDep").setValue(
									scheduleTaskInfo.getAdminDept());
						}

					}

				}

			}
		}

	}

	/**
	 * @description ������ʾ��������ѡ���Ĳ�������ʾ����
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	protected void cboTask_mouseClicked(MouseEvent e) throws Exception {
		// Map taskMap=getAllTask();
		if (cboTask.isSelected()) {
			kDLeftTable.removeRows(false);
			fillTableData(scheduleList);

		} else {
			kDLeftTable.removeRows(false);
			fillTableData(taskList);
		}
		// updateTableData(taskMap);

		// if(cboTask.isSelected()){
		// // kDLeftTable.removeRows(false);
		// // resetTable();
		// int count =kDLeftTable.getRowCount();
		// if(count>0){
		// for(int i=0;i<count;i++){
		// IRow row =kDLeftTable.getRow(i);
		// String entryId=row.getCell("id").getValue().toString().trim();
		// if(idSet.size()>0){
		// for(Iterator it=idSet.iterator();it.hasNext();){
		// String id=(String) it.next();
		// if(id.equals(entryId)){
		// row.getStyleAttributes().setHided(false);
		// }
		// }
		// }
		// }
		// }
		// }else{
		// int count =kDLeftTable.getRowCount();
		// if(count>0){
		// for(int i=0;i<count;i++){
		// IRow row =kDLeftTable.getRow(i);
		// String entryId=row.getCell("id").getValue().toString().trim();
		// if(idSet.size()>0){
		// for(Iterator it=idSet.iterator();it.hasNext();){
		// String id=(String) it.next();
		// if(id.equals(entryId)){
		// row.getStyleAttributes().setHided(true);
		// }
		// }
		// }
		// }
		// }
		//			
		// }
	}

	/**
	 * �õ��ұ��������ݵ�ID
	 * 
	 * @return
	 */
	public List getRightData() {
		List list = new ArrayList();
		int count = kDRightTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDRightTable.getRow(i);
				String entryId = row.getCell("id").getValue().toString().trim();
				list.add(entryId);
			}
		}
		return list;
	}

	/**
	 * @description ���ұ������ȫ���Ƶ����
	 * @author ����ΰ
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void actionAllLeftMove_actionPerformed(ActionEvent e)
			throws Exception {

		if (cboTask.isSelected()) {
			kDRightTable.removeRows(false);
			kDLeftTable.removeRows(false);
			fillTableData(scheduleList);
		} else {
			kDRightTable.removeRows(false);
			kDLeftTable.removeRows(false);
			fillTableData(taskList);
		}
		//		
		// kDLeftTable.removeRows(false);
		// fullTableData(scheduleList);
		// }else{
		// kDLeftTable.removeRows(false);
		// fullTableData(taskList);
	}

	/**
	 * @description ����������ȫ���ƶ����ұ�,������ȫ��������
	 * @author ����ΰ
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void actionAllRightMove_actionPerformed(ActionEvent e)
			throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = kDLeftTable.getRow(i);
			String entryInfoId = row.getCell("id").getValue().toString();
			String taskName = row.getCell("taskName").getValue().toString();
			row.getStyleAttributes().setHided(true);
			FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
			taskInfo.setId(BOSUuid.read(entryInfoId));
			taskInfo.setName(taskName);
			list.add(taskInfo);
		}
		//TODO fixerror �ǲ���ֻ��Ҫ��editData�ж�ȡ������
		fullRightTable(list);
	}

	/**
	 * @description ����ұ�����
	 * @author ����ΰ
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void fullRightTable(List list) {
		if (list.size() > 0 && list != null) {
			kDRightTable.removeRows(false);
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list
						.get(i);
				IRow row = kDRightTable.addRow();
				row.getCell("id").setValue(taskinfo.getId());
				row.getCell("taskName").setValue(taskinfo.getName().trim());
				row.setUserObject(taskinfo);
			}
		}
	}

	/**
	 * @description ���ұ�������ƶ������
	 * @author ����ΰ
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */

	public void actionLeftMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int rows[] = KDTableUtil.getSelectedRows(kDRightTable);

		if (rows.length <= 0) {
			FDCMsgBox.showWarning("��û��ѡ��Ҫ�ƶ����л�����ѡ�����б�û������");
		} else {
			for (int i = rows.length; i > 0; i--) {
				String entryInfoId = kDRightTable.getRow(rows[i - 1]).getCell("id").getValue().toString().trim();
				String taskName = kDRightTable.getRow(rows[i - 1]).getCell("taskName").getValue().toString().trim();
				kDRightTable.removeRow(rows[i - 1]);
				FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
				taskInfo.setId(BOSUuid.read(entryInfoId));
				taskInfo.setName(taskName);

				list.add(taskInfo);
			}
			if (list.size() > 0) {
				int count = this.kDLeftTable.getRowCount();
				for (int i = 0; i < count; i++) {
					IRow row = kDLeftTable.getRow(i);
					String entryID = row.getCell("id").getValue().toString().trim();
					for (Iterator it = list.iterator(); it.hasNext();) {
						FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) it.next();
						if (scheduleTaskInfo.getId().toString().trim().equals(// ���ID��ͬ����ʾ����
								entryID)) {
							row.getStyleAttributes().setHided(false);
						}
					}
				}

			}
		}
	}

	/**
	 * @description �����������ƶ����ұ�
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionRightMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int rows[] = KDTableUtil.getSelectedRows(kDLeftTable);
		if (rows.length <= 0) {
			FDCMsgBox.showWarning("��ѡ��Ҫ�ƶ�����");
		} else {
			getAllSelectedTask(list, rows);
			fillRightTalbe(list);
		}

	}
    /**
     * 
     * ��������ȡ�����ѡȡ�����е�����
     * @param list
     * @param rows
     * @throws Exception
     * �����ˣ�yuanjun_lan
     * ����ʱ�䣺2012-2-27
     */
	private void getAllSelectedTask(List list, int[] rows) throws Exception {
		FDCScheduleTaskInfo taskInfo = null;
		for (int i = 0; i < rows.length; i++) {
//				String entryInfoId = kDLeftTable.getRow(rows[i]).getCell("id").getValue().toString();
//				String taskName = kDLeftTable.getRow(rows[i]).getCell("taskName").getValue().toString();
//				String longNumber = "";
//				if (kDLeftTable.getRow(rows[i]).getCell("longNumber").getValue() != null) {
//					longNumber = kDLeftTable.getRow(rows[i]).getCell("longNumber").getValue().toString();
//				}
			kDLeftTable.getRow(rows[i]).getStyleAttributes().setHided(true);// ����
			taskInfo = (FDCScheduleTaskInfo) kDLeftTable.getRow(rows[i]).getUserObject();
//				taskInfo.setId(BOSUuid.read(entryInfoId));
//				taskInfo.setLongNumber(longNumber);
//				taskInfo.setName(taskName);
			if (cboPanelPoint.isSelected()) {// ѡ�а�����Ҷ�ڵ�
				if (!taskInfo.isIsLeaf()) {// ����Ҷ�ڵ�
					List entryList = getChildenNode(taskInfo);
					if (entryList.size() > 0) {
						for (Iterator entry = entryList.iterator(); entry.hasNext();) {
							FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) entry.next();
							for (int j = 0; j < kDLeftTable.getRowCount(); j++) {
								IRow row = kDLeftTable.getRow(j);
								if (scheduleTaskInfo.getId().toString().trim().equals(row.getCell("id").getValue().toString().trim())) {// ID��ͬ����
									row.getStyleAttributes().setHided(true);
									if(!list.contains(scheduleTaskInfo)){
										list.add(scheduleTaskInfo);
									}
								
								}
							}
						}
					}
				}else{
				  list.add(taskInfo);
				}
			} else {
				list.add(taskInfo);
			}
		}
	}
    /**
     * 
     * ���������������������ұ�
     * @param list
     * �����ˣ�yuanjun_lan
     * ����ʱ�䣺2012-2-27
     */
	private void fillRightTalbe(List list) {
		if (list.size() > 0) {
			int rightCount = kDRightTable.getRowCount();
			if (rightCount > 0) {
				Map idMap = new HashMap();
				for (int j = 0; j < rightCount; j++) {
					IRow rowNew = kDRightTable.getRow(j);
					String entryId = rowNew.getCell("id").getValue().toString().trim();
					idMap.put(entryId, entryId);
				}
				if (idMap.size() > 0 && !idMap.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list.get(i);
						if (!idMap.containsKey(taskinfo.getId().toString())) {
							IRow row = kDRightTable.addRow();
							row.getCell("id").setValue(taskinfo.getId());
							row.getCell("taskName").setValue(taskinfo.getName().trim());
							row.setUserObject(list.get(i));
						}
					}
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list.get(i);
					IRow row = kDRightTable.addRow();
					row.getCell("id").setValue(taskinfo.getId());
					row.getCell("taskName").setValue(taskinfo.getName().trim());
					row.setUserObject(taskinfo);
				}
			}

		}
	}

	/**
	 * @description ����ʵ�����õ�������������ӽڵ�
	 * 
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public List getChildenNode(FDCScheduleTaskInfo taskInfo) throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			FDCScheduleTaskInfo entryInfo = null;
			IRow row = null;
			for (int i = 0; i < count; i++) {
				row = kDLeftTable.getRow(i);
				entryInfo = (FDCScheduleTaskInfo) row.getUserObject();
				if (entryInfo.getLongNumber().startsWith(taskInfo.getLongNumber().trim())) {
					list.add(entryInfo);
				}
			}
		}

		return list;
	}
	
	public void getChildrenNode(Set childrenSet,FDCScheduleTaskInfo taskInfo){
		int count = kDLeftTable.getRowCount();
		childrenSet.add(taskInfo);
		if(count>0){
			FDCScheduleTaskInfo entryInfo = null;
			IRow row = null;
			for(int i=0;i<count;i++){
				row = kDLeftTable.getRow(i);
				entryInfo = (FDCScheduleTaskInfo) row.getUserObject();
				if(entryInfo.getId().equals(taskInfo.getId())){
					continue;
				}
				if(entryInfo.isIsLeaf()){
					if(entryInfo.getLongNumber().startsWith(taskInfo.getLongNumber())){
						childrenSet.add(entryInfo);
					}
				}else{
					if(childrenSet.contains(entryInfo)||entryInfo.getId().equals(taskInfo.getId())){
						continue;
					}
					childrenSet.add(entryInfo);
					getChildrenNode(childrenSet,entryInfo);
				}
			}
		}
		
	}
	

	/**
	 * @description �رղ���
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

	/**
	 * @description ������رղ���
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionSaveAndClose_actionPerformed(ActionEvent e)
			throws Exception {
		FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		ScheduleStateEnum state = editUI.editData.getState();
		boolean mustUpdateBySql = true;
	    if(!state.equals(ScheduleStateEnum.AUDITTED)
	    		&& !state.equals(ScheduleStateEnum.AUDITTING)){
//	    	editUI.actionSave_actionPerformed(e);
	    	mustUpdateBySql = false;
	    }
		 
		if (prmtDutyPerson.getValue() == null 
				&& prmtDutyDep.getValue() == null
				&& prmtScheduleAppraisePerson.getValue() == null
				&& prmtQualityAppraisePerson.getValue() == null) {
			FDCMsgBox.showWarning("��ѡ����Ҫ�޸ĵ���Ϣ");
			SysUtil.abort();
		}
		PersonInfo dutyPerson = (PersonInfo) prmtDutyPerson.getValue();
		AdminOrgUnitInfo dutyDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
		PersonInfo scheduleAppraisePerson = (PersonInfo) prmtScheduleAppraisePerson.getValue();
		PersonInfo qualityAppraisePerson = (PersonInfo) prmtQualityAppraisePerson.getValue();
		int count = kDRightTable.getRowCount();
		if (count <= 0) {
			FDCMsgBox.showWarning("��ѡ�����б���Ϊ��");
			SysUtil.abort();
		}
		
		FDCScheduleTaskInfo taskInfo = null;
		CoreBaseCollection collection = new CoreBaseCollection();
		for (int i = 0; i < count; i++) {
			IRow row = kDRightTable.getRow(i);
//			String entryPk = row.getCell("id").getValue().toString().trim();
//			FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory
//					.getRemoteInstance().getFDCScheduleTaskInfo(
//							new ObjectStringPK(entryPk));
			taskInfo = (FDCScheduleTaskInfo) row.getUserObject();
			if (dutyPerson != null) {
				taskInfo.setAdminPerson(dutyPerson);
			}
			if (dutyDep != null) {
				taskInfo.setAdminDept(dutyDep.castToFullOrgUnitInfo());
			}
			if (scheduleAppraisePerson != null) {
				taskInfo.setPlanEvaluatePerson(scheduleAppraisePerson);
			}
			if (qualityAppraisePerson != null) {
				taskInfo.setQualityEvaluatePerson(qualityAppraisePerson);
			}
			collection.add(taskInfo);

		}
		
		if(mustUpdateBySql){
			updateInfoWhenAuditted();
		}
		
		newScheduleInfo = getSaveAndCloseTableData();
		// FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
		// cols = ScheduleTaskHelper.getRelationReverseTasks(cols,
		// newScheduleInfo.getTaskEntrys());
		// newScheduleInfo.getTaskEntrys().clear();
		// newScheduleInfo.getTaskEntrys().addCollection(cols);
		editUI.loadData2Gantt(newScheduleInfo);
		destroyWindow();
	}

	/**
	 * @description ����
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionMakeSure_actionPerformed(ActionEvent e) throws Exception {
		
		FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		ScheduleStateEnum state = editUI.editData.getState();
		boolean mustUpdateBySql = true;
	    if(!state.equals(ScheduleStateEnum.AUDITTED)
	    		&& !state.equals(ScheduleStateEnum.AUDITTING)){
	    	editUI.actionSave_actionPerformed(e);
	    	mustUpdateBySql = false;
	    }
		// ���β������β��ŵȲ�Ϊ��
		if (prmtDutyPerson.getValue() == null && prmtDutyDep.getValue() == null
				&& prmtScheduleAppraisePerson.getValue() == null
				&& prmtQualityAppraisePerson.getValue() == null) {
			FDCMsgBox.showWarning("��ѡ����Ҫ�޸ĵ���Ϣ");
			SysUtil.abort();
		}
		
		PersonInfo dutyPerson = (PersonInfo) prmtDutyPerson.getValue();
		AdminOrgUnitInfo dutyDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
		PersonInfo scheduleAppraisePerson = (PersonInfo) prmtScheduleAppraisePerson
				.getValue();
		PersonInfo qualityAppraisePerson = (PersonInfo) prmtQualityAppraisePerson
				.getValue();
		int count = kDRightTable.getRowCount();
		if (count <= 0) {
			FDCMsgBox.showWarning("��ѡ�����б���Ϊ��");
			SysUtil.abort();
		}
		
		
		List entryList = new ArrayList();
		CoreBaseCollection collection = new CoreBaseCollection();
		for (int i = 0; i < count; i++) {
			IRow row = kDRightTable.getRow(i);
			String entryPk = row.getCell("id").getValue().toString().trim();
			// �޸�����
			FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
			taskInfo.setId(BOSUuid.read(entryPk));
			if (dutyPerson != null) {
				taskInfo.setAdminPerson(dutyPerson);
			}
			if (dutyDep != null) {
				taskInfo.setAdminDept(dutyDep.castToFullOrgUnitInfo());
			}
			if (scheduleAppraisePerson != null) {
				taskInfo.setPlanEvaluatePerson(scheduleAppraisePerson);
			}
			if (qualityAppraisePerson != null) {
				taskInfo.setQualityEvaluatePerson(qualityAppraisePerson);
			}
			collection.add(taskInfo);
			entryList.add(taskInfo);

		}
		if(mustUpdateBySql){
			updateInfoWhenAuditted();
		}
		saveUpdateData(entryList);
		
		
		// if(collection.size()>0){//�����޸�
		// FDCScheduleTaskFactory.getRemoteInstance().update(collection);
		// }
		FDCScheduleInfo fdcScheduleInfo = getDataFormLeftTable();
		editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		editUI.loadData2Gantt(fdcScheduleInfo);
	}

	/**
	 * @description ���ֲ���ʱ����ұ�����ݣ������������ݻָ�
	 * @author ����ΰ
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void saveUpdateData(List list) {
		if (list.size() > 0 && list != null) {
			kDRightTable.removeRows(false);
			int count = kDLeftTable.getRowCount();
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryID = row.getCell("id").getValue().toString().trim();
				for (int j = 0; j < list.size(); j++) {
					FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) list.get(j);
					if (entryID.equals(scheduleTaskInfo.getId().toString().trim())) {
						row.getStyleAttributes().setHided(false);
						if (scheduleTaskInfo.getAdminPerson() != null) {
							row.getCell("resPerson").setValue(scheduleTaskInfo.getAdminPerson());
						}
						if (scheduleTaskInfo.getAdminDept() != null) {
							row.getCell("resDep").setValue(scheduleTaskInfo.getAdminDept());
						}
						if (scheduleTaskInfo.getPlanEvaluatePerson() != null) {
							row.getCell("planEvaluatePerson").setValue(scheduleTaskInfo.getPlanEvaluatePerson());
						}
						if (scheduleTaskInfo.getQualityEvaluatePerson() != null) {
							row.getCell("qualityEvaluatePerson").setValue(scheduleTaskInfo.getQualityEvaluatePerson());
						}

					}
				}
			}

		}
	}
	
	/**
	 * 
	 * �����������ȼƻ��Ѿ���������ͨ���ű�ֱ�Ӹ��¶�Ӧ����������Ϣ
	 * @param taskList
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2012-2-27
	 * @throws BOSException 
	 */
	private void updateInfoWhenAuditted() throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		StringBuffer sql = new StringBuffer("update t_sch_fdcscheduleTask set ");
		boolean [] isModify = new boolean[4];
		int index = 0;
		String dutyDeptId = null;
		String dutyPersonId = null;
		String appraisePerson = null;
		String schedulePerson = null;
		
		//�ж���Щ��Ҫ����
		if(prmtDutyDep.getValue() != null){
			isModify[IndexModify.DUTYDEP] = true;
			sql.append("  FAdminDeptID = ? , ");
			dutyDeptId = ((AdminOrgUnitInfo)prmtDutyDep.getValue()).getId().toString();
		}
		
		if(prmtDutyPerson.getValue() != null){
			isModify[IndexModify.DUTYPERSON] = true;
			sql.append(" FAdminPersonID  = ? , ");
			dutyPersonId = ((PersonInfo)prmtDutyPerson.getValue()).getId().toString();
		}
		
		if(prmtQualityAppraisePerson.getValue() != null){
			isModify[IndexModify.QUALITYPERSON] = true;
			sql.append(" FQualityEvaluatePersonID  = ? , ");
			appraisePerson = ((PersonInfo)prmtQualityAppraisePerson.getValue()).getId().toString();
		}
		
		if(prmtScheduleAppraisePerson.getValue() != null){
			isModify[IndexModify.SCHEDULEPERSON] = true;
			sql.append(" FPlanEvaluatePersonID  = ? , ");
			schedulePerson = ((PersonInfo)prmtScheduleAppraisePerson.getValue()).getId().toString();
		}
		int lastChar = sql.lastIndexOf(",");
		sql.delete(lastChar,lastChar+1);
		sql.append(" where fid = ? ");
		
	    FDCScheduleTaskInfo task = null;
	    List paramList = new ArrayList(taskList.size());
	    List valueList = null;
	    int rightTableCount = kDRightTable.getRowCount();
		for(int i=0;i<rightTableCount;i++){
			
			valueList = new ArrayList();
			task = (FDCScheduleTaskInfo) kDRightTable.getRow(i).getUserObject();
			if(isModify[IndexModify.DUTYDEP]){
				valueList.add(dutyDeptId == null?task.getAdminDept().getId().toString():dutyDeptId);
			}
			
			if(isModify[IndexModify.DUTYPERSON]){
				valueList.add(dutyPersonId == null?task.getAdminPerson().getId().toString():dutyPersonId);
			}
			
			if(isModify[IndexModify.QUALITYPERSON]){
				valueList.add(appraisePerson == null?task.getQualityEvaluatePerson().getId().toString():appraisePerson);
			}
			
			if(isModify[IndexModify.SCHEDULEPERSON]){
				valueList.add(schedulePerson == null?task.getPlanEvaluatePerson().getId().toString():schedulePerson);
			}
			valueList.add(task.getId().toString());
			
			paramList.add(valueList);
		}
		
	    builder.executeBatch(sql.toString(), paramList);
		
		
	
		
	}

	private Map map;

	/**
	 * �õ����е��������Map��
	 * 
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-9
	 * @throws Exception void
	 * @version EAS7.0
	 * @see
	 */
	public void getAllTaskInfo() throws Exception {
		map = new HashMap();
		if (scheduleList.size() > 0) {
			for (int i = 0; i < scheduleList.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) scheduleList.get(i);
				if (!map.containsKey(taskInfo.getId().toString())) {
					map.put(taskInfo.getId().toString(), taskInfo);
				}

			}
		}
	}

	/**
	 * ���沢�ر�
	 * 
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-9
	 * @return
	 * @throws Exception
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	public FDCScheduleInfo getSaveAndCloseTableData() throws Exception {
		getAllTaskInfo();
		// scheduleInfo.getTaskEntrys().clear();
		int count = kDRightTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDRightTable.getRow(i);
				String entryID = row.getCell("id").getValue().toString();
				if (map.get(entryID) != null) {
					FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) map.get(entryID);
					PersonInfo resPerson = (PersonInfo) prmtDutyPerson.getValue();
					AdminOrgUnitInfo resDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
					PersonInfo planEvaluatePerson = (PersonInfo) prmtScheduleAppraisePerson.getValue();
					PersonInfo qualityEvaluatePerson = (PersonInfo) prmtQualityAppraisePerson.getValue();
					if (resPerson != null) {
						taskInfo.setAdminPerson(resPerson);
					}
					if (resDep != null) {
						taskInfo.setAdminDept(resDep.castToFullOrgUnitInfo());
					}
					if (planEvaluatePerson != null) {
						taskInfo.setPlanEvaluatePerson(planEvaluatePerson);
					}
					if (qualityEvaluatePerson != null) {
						taskInfo.setQualityEvaluatePerson(qualityEvaluatePerson);
					}

					for (int j = 0; j < scheduleInfo.getTaskEntrys().size(); j++) {
						FDCScheduleTaskInfo taskEntryInfo = scheduleInfo.getTaskEntrys().get(j);
						if (taskEntryInfo.getId().toString().equals(entryID)) {
							scheduleInfo.getTaskEntrys().removeObject(j);
							scheduleInfo.getTaskEntrys().insertObject(j - 1, taskInfo);
						}
					}
				}
			}
		}
		return scheduleInfo;
	}

	public Map setFdcschedul(List scheduleList) {
		Map taskMap = new HashMap();
		if (scheduleList.size() > 0) {
			for (int i = 0; i < scheduleList.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) scheduleList
						.get(i);
				if (!taskMap.containsKey(taskInfo.getId().toString())) {
					taskMap.put(taskInfo.getId().toString(), taskInfo);
				}

			}
		}
		return taskMap;
	}

	/**
	 * ȷ��
	 * 
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-9
	 * @return
	 * @throws Exception
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	public FDCScheduleInfo getDataFormLeftTable() throws Exception {
		getAllTaskInfo();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryID = "";
				if (row.getCell("id").getValue() != null) {
					entryID = row.getCell("id").getValue().toString().trim();
				}
				if (map.get(entryID) != null) {
					FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) map.get(entryID);
					PersonInfo resPerson = (PersonInfo) row.getCell("resPerson").getValue();
					FullOrgUnitInfo resDep = (FullOrgUnitInfo) row.getCell("resDep").getValue();
					PersonInfo planEvaluatePerson = (PersonInfo) row.getCell("planEvaluatePerson").getValue();
					PersonInfo qualityEvaluatePerson = (PersonInfo) row.getCell("qualityEvaluatePerson").getValue();
					if (resPerson != null) {
						taskInfo.setAdminPerson(resPerson);
					}
					if (resDep != null) {
						taskInfo.setAdminDept(resDep);
					}
					if (planEvaluatePerson != null) {
						taskInfo.setPlanEvaluatePerson(planEvaluatePerson);
					}
					if (qualityEvaluatePerson != null) {
						taskInfo.setQualityEvaluatePerson(qualityEvaluatePerson);
					}
					FDCScheduleTaskCollection collection = scheduleInfo.getTaskEntrys();

					for (int j = 0; j < collection.size(); j++) {
						FDCScheduleTaskInfo taskEntryInfo = collection.get(j);
						if (taskEntryInfo.getId().toString().equals(entryID)) {
							scheduleInfo.getTaskEntrys().remove(taskEntryInfo);
							
							/* modified by zhaqoin for R140318-0254 on 2014/03/26 start */
							//scheduleInfo.getTaskEntrys().add(taskInfo);
							scheduleInfo.getTaskEntrys().insertObject(j - 1, taskInfo);
							/* modified by zhaqoin for R140318-0254 on 2014/03/26 end */
						}

					}

					// scheduleInfo.getTaskEntrys().add(taskInfo);
				}

			}
		}

		return scheduleInfo;
	}
	
   static class IndexModify{
	   /**������*/
	   public static final int DUTYDEP = 0;
	   /**���β���*/
	   public static final int DUTYPERSON = 1;
	   /**��������������*/
	   public static final int QUALITYPERSON = 2;
	   /**����������*/
	   public static final int SCHEDULEPERSON = 3;
   }
}