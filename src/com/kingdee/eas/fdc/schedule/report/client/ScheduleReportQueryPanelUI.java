/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:���ȱ����ѯ���˽���
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */ 
@SuppressWarnings("deprecation")
public class ScheduleReportQueryPanelUI extends AbstractScheduleReportQueryPanelUI {

	private static final long serialVersionUID = 2742191723711223558L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportQueryPanelUI.class);

	// ���ȱ���ѡ�񹤳���Ŀ����
	private F7ScheduleReportDialog orgDialog = null;

	// ���н��ȱ���������
	private ScheduleReportBaseUI baseUI = null;

	// �ж��Ƿ�Ϊ�޸Ĳ���
	private boolean isUpdate = false;

	/**
	 * output class constructor
	 */
	public ScheduleReportQueryPanelUI() throws Exception {
		super();
	}

	/**
	 * ����������������󴫵ݵ��Ӵ�����
	 * @param baseUI
	 * @Author��adward_huang
	 * @CreateTime��2012-9-18
	 */
	public void setScheduleReportBaseUI(ScheduleReportBaseUI baseUI, boolean isUpdate) {
		this.baseUI = baseUI;
		this.isUpdate = isUpdate;
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportQueryPanelUI#actionSelectOrg_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSelectOrg_actionPerformed(ActionEvent e) throws Exception {
		/* TODO �Զ����ɷ������ */
		super.actionSelectOrg_actionPerformed(e);

		// ����ѡ����֯��Ŀ����
		Map<String, Map<String, String>> selectOrg = selectOrgDialogData();

		// ���ѡ��Χ�ı��ؼ�����ʾ��֯������Ŀ��
		//		fillQueryRangeTxtByOrgName(selectOrg);
		String selectValue = getSelectValue();
		String showProjectName = orgDialog.getProjectName();
		this.orgIDList.setText(selectValue);
		this.checkedIdsArea.setText(ScheduleChartHelper.getMapToString(selectOrg));
		this.projectNameTxtField.setText(showProjectName);
	}

	/**
	 * ����������ѡ�����֯��������Ŀ������ѯ��Χ�齨Text
	 * @param map
	 * @Author��adward_huang
	 * @CreateTime��2012-9-18
	 */
	@SuppressWarnings("unused")
	private void fillQueryRangeTxtByOrgName(Map<String, String> map) {

		if (map == null || map.isEmpty()) {
			return;
		}
		Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
		int index = 0;
		int size = map.size();
		StringBuffer fillOrgName = new StringBuffer(100);
		// ƴ����֯����
		while (iter.hasNext()) {
			Map.Entry<String, String> entryMap = iter.next();
			index++;
			String name = entryMap.getValue();
			if (index == size) {
				fillOrgName.append(name);
			} else {
				fillOrgName.append(name).append(";");
			}
		}

		this.orgIDList.setText(fillOrgName.toString());
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#verify()
	 */
	@Override
	public boolean verify() {

		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if (para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate")) {
			if (para.getDate("endQueryDate").before(para.getDate("beginQueryDate"))) {
				MsgBox.showWarning(this, "��ʼ���ڲ��ܴ��ڽ������ڣ�������ѡ��");
				return false;
			}
		}

		if (para.isNull("orgIDs")) {
			MsgBox.showWarning(this, "��ѯ��Χ����Ϊ�գ���ѡ���ѯ��Χ��");
			return false;
		}

		try {
			rebuildBaseUI();
		} catch (Exception e) {
			/* TODO �Զ����� catch �� */
			this.handUIException(e);
		}

		return true;
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#getCustomerParams()
	 */
	public CustomerParams getCustomerParams() {

		FDCCustomerParams param = new FDCCustomerParams();

		Date startDate = (Date) this.startDate.getValue();
		Date endDate = (Date) this.endDate.getValue();
		String orgIDs = this.orgIDList.getText();
		String data = this.checkedIdsArea.getText();
		String projectName = this.projectNameTxtField.getText();
		if (!FDCHelper.isEmpty(startDate)) {
			param.add("beginQueryDate", startDate);
		}
		if (!FDCHelper.isEmpty(endDate)) {
			param.add("endQueryDate", endDate);
		}

		if (!FDCHelper.isEmpty(orgIDs)) {
			param.add("orgIDs", orgIDs);
		}

		if (!FDCHelper.isEmpty(orgIDs)) {
			param.add("data", data);
		}
		
		if (!FDCHelper.isEmpty(projectName)) {
			param.add("projectName", projectName);
		}

		return param.getCustomerParams();
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#setCustomerParams(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		this.orgIDList.setText(para.get("orgIDs"));
		this.startDate.setValue(para.getDate("beginQueryDate"));
		this.endDate.setValue(para.getDate("endQueryDate"));
		this.checkedIdsArea.setText(para.getString("data"));
		this.projectNameTxtField.setText(para.getString("projectName"));
		super.setCustomerParams(para.getCustomerParams());
	}

	/**
	 * �������ػ汨�����
	 * @throws Exception 
	 * @Author��adward_huang
	 * @CreateTime��2012-9-18
	 */
	private void rebuildBaseUI() throws Exception {
		Date startDate = (Date) this.startDate.getValue();
		Date endDate = (Date) this.endDate.getValue();
		//		Map<String,Map<String, String>> selectOrg = orgDialog.getData();
		String data = checkedIdsArea.getText().toString();
		Map<String, Map<String, String>> selectOrg = ScheduleChartHelper.getStringToMap(data);
		String projectName = projectNameTxtField.getText().toString();
		if (this.baseUI == null) {
			return;
		} else {
			this.baseUI.setSelectorAllValue(selectOrg, startDate, endDate, projectName, null);

			// �ػ游����
			if (isUpdate) {
				this.baseUI.buildTableAndChartPanel();
			}
		}
	}

	/**
	 * ��������ʾ�������������弰��ȡ������ѡ�����֯�͹���
	 * @throws Exception
	 * @Author��adward_huang
	 * @CreateTime��2012-9-18
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> selectOrgDialogData() throws Exception {

		ScheduleReportOrgQueryUI orgUI = new ScheduleReportOrgQueryUI();
		orgDialog = new F7ScheduleReportDialog(orgUI);
		// ���øô�������ѡ�񱨱���֯�ṹ����
		orgDialog.show();

		Map<String, Map<String, String>> returnDataMap = (Map<String, Map<String, String>>) orgDialog.getData();
		//		if(ScheduleReportOrgQueryUI.DATA_FROM_REPORT.equals(orgDialog.getDataType().toString())){
		//			returnDataMap = (Map<String,Map<String, String>>)orgDialog.getData();
		//		}
		return returnDataMap;
	}

	/**
	 * ������ƴ�ӹ������ƺ���Ŀ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-20
	 */
	private String getSelectValue() {
		List<String> list = orgDialog.getSelectProAndOrgList();
		if (list == null || list.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i)).append(";");
			}
		}
		return sb.toString();
	}
	
}