/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class BatchModifyTaskType extends AbstractBatchModifyTaskType {
	private static final Logger logger = CoreUIObject.getLogger(BatchModifyTaskType.class);
	private List taskList = new ArrayList();
	private List scheduleList = new ArrayList();
	private FDCScheduleInfo scheduleInfo = null;
	private boolean isNeedReload =false;
 
	public BatchModifyTaskType() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.cmbTaskType.addItem("");
		this.cmbTaskType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
		initTableData();
		prmtBizType.setEnabledMultiSelection(true);
	
		
		this.contBizType.setSize(this.contBizType.getHeight(),this.contBizType.getWidth()*2);
	}

	private void initTableData() {
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI) {
			FDCScheduleBaseEditUI baseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			scheduleInfo = baseEditUI.scheduleInfo;
			
			if(scheduleInfo.getProjectSpecial() != null){
				this.prmtBizType.setEnabled(false);
				RESchTaskTypeEnum typeEnum = null;
				for(int i=cmbTaskType.getItemCount();i>0;i--){
					if(cmbTaskType.getItemAt(i-1) instanceof RESchTaskTypeEnum)
					   typeEnum = (RESchTaskTypeEnum) cmbTaskType.getItemAt(i-1);
					if(typeEnum != null && typeEnum.equals(RESchTaskTypeEnum.MILESTONE)){
						cmbTaskType.removeItemAt(i-1);
					}
					
				}
			}

			if (baseEditUI.getScheduleGanttProject() == null) {
				FDCMsgBox.showWarning("�ù�����Ŀ��û������");
				SysUtil.abort();
			}
			List tasks = baseEditUI.getScheduleGanttProject().getSortKDTask();
			FDCScheduleTaskInfo taskInfo = null;
			for (int i = 0; i < tasks.size(); i++) {
				taskInfo = (FDCScheduleTaskInfo) ((KDTask) tasks.get(i)).getScheduleTaskInfo();
				scheduleInfo.getTaskEntrys().add(taskInfo);
				taskList.add(taskInfo);
				scheduleList.add(taskInfo);
			}
		}
		
		fillTableData(taskList);
	}
	
	private String parserBizType(ScheduleTaskBizTypeCollection  cols){
		StringBuffer bizTypeStr = new StringBuffer();
		ScheduleTaskBizTypeInfo type = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			type = (ScheduleTaskBizTypeInfo) it.next();
			bizTypeStr.append(type.getBizType().getName());
		}
		return bizTypeStr.toString();
	}

	public void fillTableData(List list) {
		kDLeftTable.checkParsed();
		kDLeftTable.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) list.get(i);
				IRow row = kDLeftTable.addRow();
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
				row.getCell("bizType").setValue(parserBizType(taskInfo.getBizType()));
				row.getCell("taskType").setValue(taskInfo.getTaskType());
			}
			initTree();
		}
	}

	public void initTree() {
		int count = this.kDLeftTable.getRowCount();
		kDLeftTable.getStyleAttributes().setLocked(true);
		for (int i = 0; i < count; i++) {
			// ȡ��ԭ����
			IRow row = this.kDLeftTable.getRow(i);
			String value = "";
			if (row.getCell("taskName").getValue() instanceof String) {
				value = (String) row.getCell("taskName").getValue();
			}
			// ȡ�������κ��Ƿ���ϸ�ڵ�
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(value);// ��ʾ��ֵ
			treeNode.setTreeLevel(level + 1);// ���Σ�
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			row.getCell("taskName").setValue(treeNode);// ����ǰ�湹���ĵ�Ԫ��������
			row.getCell("taskName").getStyleAttributes().setLocked(false);
		}
	}

	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

	public void actionLeftMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int rows[] = KDTableUtil.getSelectedRows(kDRightTable);

		if (rows.length <= 0) {
			FDCMsgBox.showWarning("��û��ѡ��Ҫ�ƶ����л�����ѡ�����б�û������");
		} else {
			for (int i = rows.length; i > 0; i--) {
				String entryInfoId = kDRightTable.getRow(rows[i - 1]).getCell(
						"id").getValue().toString().trim();
				String taskName = kDRightTable.getRow(rows[i - 1]).getCell(
						"taskName").getValue().toString().trim();
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
					String entryID = row.getCell("id").getValue().toString()
							.trim();
					for (Iterator it = list.iterator(); it.hasNext();) {
						FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) it
								.next();
						if (scheduleTaskInfo.getId().toString().trim().equals(// ���ID��ͬ����ʾ����
								entryID)) {
							row.getStyleAttributes().setHided(false);
						}
					}
				}

			}
		}
	}

	public void actionRightMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		;
		int rows[] = KDTableUtil.getSelectedRows(kDLeftTable);
		if (rows.length <= 0) {
			FDCMsgBox.showWarning("��ѡ��Ҫ�ƶ�����");
		} else {

			for (int i = 0; i < rows.length; i++) {
				String entryInfoId = kDLeftTable.getRow(rows[i]).getCell("id")
						.getValue().toString();
				String taskName = kDLeftTable.getRow(rows[i]).getCell(
						"taskName").getValue().toString();
				String longNumber = "";
				if (kDLeftTable.getRow(rows[i]).getCell("longNumber")
						.getValue() != null) {
					longNumber = kDLeftTable.getRow(rows[i]).getCell(
							"longNumber").getValue().toString();
				}
				kDLeftTable.getRow(rows[i]).getStyleAttributes().setHided(true);// ����
				FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
				taskInfo.setId(BOSUuid.read(entryInfoId));
				taskInfo.setLongNumber(longNumber);
				taskInfo.setName(taskName);
				if (cboPanelPoint.isSelected()) {// ѡ�а�����Ҷ�ڵ�
					if (!taskInfo.isIsLeaf()) {// ����Ҷ�ڵ�
						List entryList = getChildenNode(taskInfo);
						if (entryList.size() > 0) {
							for (Iterator entry = entryList.iterator(); entry
									.hasNext();) {
								FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) entry
										.next();
								for (int j = 0; j < kDLeftTable.getRowCount(); j++) {
									IRow row = kDLeftTable.getRow(j);
									if (scheduleTaskInfo.getId().toString()
											.trim().equals(
													row.getCell("id")
															.getValue()
															.toString().trim())) {// ID��ͬ����
										row.getStyleAttributes().setHided(true);
										list.add(scheduleTaskInfo);
									}
								}
							}
						}
					}
				} else {
					list.add(taskInfo);
				}
			}

			// Ϊ�ұߵı��������
			if (list.size() > 0) {
				int rightCount = kDRightTable.getRowCount();
				if (rightCount > 0) {
					Map idMap = new HashMap();
					for (int j = 0; j < rightCount; j++) {
						IRow rowNew = kDRightTable.getRow(j);
						String entryId = rowNew.getCell("id").getValue()
								.toString().trim();
						idMap.put(entryId, entryId);
					}
					if (idMap.size() > 0 && !idMap.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list
									.get(i);
							if (idMap.get(taskinfo.getId().toString()) == null) {
								IRow row = kDRightTable.addRow();
								row.getCell("id").setValue(taskinfo.getId());
								row.getCell("taskName").setValue(
										taskinfo.getName().trim());
							}
						}
					}
				} else {
					for (int i = 0; i < list.size(); i++) {
						FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list
								.get(i);
						IRow row = kDRightTable.addRow();
						row.getCell("id").setValue(taskinfo.getId());
						row.getCell("taskName").setValue(
								taskinfo.getName().trim());
					}
				}

			}
		}
	}
	
	public List getChildenNode(FDCScheduleTaskInfo taskInfo) throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryInfoId = row.getCell("id").getValue().toString();
				String taskName = row.getCell("taskName").getValue().toString();
				String longNumber = row.getCell("longNumber").getValue()
						.toString().trim();
				FDCScheduleTaskInfo entryInfo = new FDCScheduleTaskInfo();
				entryInfo.setId(BOSUuid.read(entryInfoId));
				entryInfo.setLongNumber(longNumber);
				entryInfo.setName(taskName);
				if (longNumber.startsWith(taskInfo.getLongNumber().trim())) {
					list.add(entryInfo);
				}
			}
		}

		return list;
	}


	public void actionAllLeftMove_actionPerformed(ActionEvent e) throws Exception {
		kDLeftTable.removeRows(false);
		kDRightTable.removeRows(false);
		fillTableData(scheduleList);
	}

	public void actionAllRightMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		IRow row  = null;
		FDCScheduleTaskInfo taskInfo = null;
		for (int i = 0; i < count; i++) {
			row = kDLeftTable.getRow(i);
			String entryInfoId = row.getCell("id").getValue().toString();
			String taskName = row.getCell("taskName").getValue().toString();
			row.getStyleAttributes().setHided(true);
			taskInfo = new FDCScheduleTaskInfo();
			taskInfo.setId(BOSUuid.read(entryInfoId));
			taskInfo.setName(taskName);
			list.add(taskInfo);
			taskInfo = null;
		}
		
		
		fillRightTable(list);
	
	}
	
	public void fillRightTable(List list) {
		if (list.size() > 0 && list != null) {
			kDRightTable.removeRows(false);
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list.get(i);
				IRow row = kDRightTable.addRow();
				row.getCell("id").setValue(taskinfo.getId());
				row.getCell("taskName").setValue(taskinfo.getName().trim());
			}
		}
	}
	
	
	
	private Map createParamMap(){
		boolean isBizTypeNull  = false;
		Object [] selectedBizTypes = null;
		
		int rowCount = kDRightTable.getRowCount();
		if(rowCount<1){
			FDCMsgBox.showError("��ѡ��Ҫ�޸ĵ�����");
			abort();
		}
		
		
		
		if(prmtBizType.getData() != null)
		{
			selectedBizTypes = (Object[]) prmtBizType.getData();
			if(selectedBizTypes.length > 0 && selectedBizTypes[0] == null){
				isBizTypeNull = true;
			}
			
		}else{
			isBizTypeNull = true;
		}
		RESchTaskTypeEnum taskType =  null;
		if( cmbTaskType.getSelectedItem() instanceof RESchTaskTypeEnum )
		   taskType=(RESchTaskTypeEnum) cmbTaskType.getSelectedItem();
		
		if(isBizTypeNull  && taskType == null){
			FDCMsgBox.showError("��ѡ��Ҫ�޸ĵ��������ͻ���ҵ�����ͣ�");
			abort();
		}
		
		Map paramMap = new HashMap();
		Set idSet = new HashSet();
		
		String currId = null;
		for(int i = 0;i<rowCount;i++){
			if((currId = kDRightTable.getCell(i, "id").getValue().toString()) != null){
				idSet.add(currId);
			}
		}
		paramMap.put("idSet",idSet);
		if(taskType!=null){
			paramMap.put("taskType",taskType);
		}
		if(!isBizTypeNull){
			paramMap.put("bizTypes",selectedBizTypes);
		}
		paramMap.put("scheduleId",scheduleInfo.getId());
		return paramMap;
	}

	public void actionMakeSure_actionPerformed(ActionEvent e) throws Exception {
		scheduleInfo = saveChange();
	}

	public void actionSaveAndClose_actionPerformed(ActionEvent e) throws Exception {
		scheduleInfo = saveChange();
		destroyWindow();
	}
	
	private void refreshParentUI(FDCScheduleInfo info) throws Exception{
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI) {
			FDCScheduleBaseEditUI baseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			baseEditUI.refresh(info);
		}
	}
	
	
	private FDCScheduleInfo saveChange() throws EASBizException, BOSException{
		Map resultMap = createParamMap();
		FDCScheduleInfo info = null;
		resultMap = FDCScheduleFactory.getRemoteInstance().batchChangeTaskProperties(resultMap);
		if(resultMap.get("isNeedReload") != null && Boolean.valueOf(resultMap.get("isNeedReload").toString()).booleanValue()){
			isNeedReload = Boolean.valueOf(resultMap.get("isNeedReload").toString()).booleanValue();
			info = (FDCScheduleInfo) resultMap.get("scheduleInfo");
		}
		return info;
	}
	
	public boolean destroyWindow() {

		if(isNeedReload){
			try {
				refreshParentUI(scheduleInfo);
			} catch (Exception e) {
				handUIException(e);
			}
		}
		return super.destroyWindow();
	}
	
	

}