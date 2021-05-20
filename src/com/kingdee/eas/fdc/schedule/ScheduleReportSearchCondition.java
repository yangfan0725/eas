/*
 * @(#)ScheduleReportSearchCondition.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;

/**
 * ����:���ȱ���������������
 * @author adward_huang  date:2012-9-25
 * @version EAS6.1
 */
public class ScheduleReportSearchCondition {
	
	// ����ID
	private String projectId;
	
	// ��������
	private String userId;
	
	// ��ʼ����
	private String startDate;
	
	// ��������
	private String endDate;
	
	// ��������
	private Map<String, Map<String, String>> dataMap;
	
	// ѡ������ڵ�
	private DefaultKingdeeTreeNode selectNode;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public DefaultKingdeeTreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(DefaultKingdeeTreeNode selectNode) {
		this.selectNode = selectNode;
	}

	public Map<String, Map<String, String>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Map<String, String>> dataMap) {
		this.dataMap = dataMap;
	}

}
