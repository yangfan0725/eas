/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.ScheduleReportSearchCondition;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:���ȱ���ר��ƻ�����ʽ���
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleExpertPlanTreeRateUI extends AbstractScheduleExpertPlanTreeRateUI {

	private static final long serialVersionUID = 2971050138473397559L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleExpertPlanTreeRateUI.class);

	// ͼ�α���
	private static final String CHART_TITLE = "ר��ƻ������";

	// ѡ��Ĺ�������
	private String selectProjectName;

	private boolean hasDateSearch = false;

	private Map<String, Map<String, String>> dataMap;

	/**
	 * output class constructor
	 */
	public ScheduleExpertPlanTreeRateUI() throws Exception {
		super();
		kDSplitPane2.setDividerLocation(650);
		// ��ʼ��ҳ��ؼ�
		clearDates();

	}

	/**
	 * ��������ʼ������ͼ�ν���
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected void buildTableAndChartPanel(boolean hasCacheData) {

		// ��ʼ���ںͽ�������
		Date startDate = getSelectedStartDate();
		Date endDate = getSelectedEndDate();
		String projectID = getProjectID();

		// ���˺�Ľ�����Ŀ�����Ϣ
		dataMap = getScheduleExpertReportData(startDate, endDate, projectID);

		if (!hasCacheData) {
			// ���滺��
			saveSearchCondition(projectID, startDate, endDate, dataMap);
		}

		this.setDataMap(dataMap);
		initReportPanel(dataMap);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeBaseUI#orgTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		//		super.orgTree_valueChanged(e);

		String[] idAndName = getSelectTreeNodeId();
		clearDates();
		if (idAndName[0] != null) {
			hasDateSearch = false;
			// ���ò���ֵ
			setSelectorValue(idAndName[0]);
			this.setSelectProjectName(idAndName[1]);
			this.setChartTitle(this.getSelectProjectName() + CHART_TITLE);
			// ��ȡ��������
			ScheduleReportSearchCondition condition = getCacheSearchData(idAndName[0]);
			if (condition != null && condition.getStartDate() != null && condition.getEndDate() != null) {
				Date startDate = getStringToDate(condition.getStartDate());
				Date endDate = getStringToDate(condition.getEndDate());

				//				// ��ȡ��������
				//				Map<String, Map<String, String>> dataMap = condition.getDataMap();

				// ���ݻ�����������
				resetDates(startDate, endDate);
				// ���ò���ֵ
				//				setSelectorValue(idAndName[0]);
				this.setSelectorAllValue(idAndName[0], startDate, endDate, null, null);
				// ���ݻ��������ػ�ͼ�κͱ��
				//				rebuildTableAndChartPanelByCache(dataMap);

			}// Ĭ�ϲ����滺�����ݣ�������ڵ����ֻ��ʾ��������
			buildTableAndChartPanel(true);

		}
	}

	/**
	* ��������ȡ�����Ļ�������
	* @throws BOSException 
	* @Author��adward_huang
	* @CreateTime��2012-9-25
	*/
	private ScheduleReportSearchCondition getCacheSearchData(String projectId) throws BOSException {

		String searchValue = getSearchCondition(projectId);

		ScheduleReportSearchCondition condition = getSearchConditionObject(searchValue);

		return condition;
	}

	private void setSelectorValue(String projectID) {
		Date startDate = null;
		Date endDate = null;
		if (hasDateSearch && startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
			startDate = (Date) startDatePicker.getValue();
			endDate = (Date) endDatePicker.getValue();
		}
		if (projectID == null) {
			projectID = getProjectID();
		}
		if (projectID == null) {
			FDCMsgBox.showInfo("��ѡ�񹤳���Ŀ�ڵ㣡");
			SysUtil.abort();
		}
		setSelectorAllValue(projectID, startDate, endDate, null, null);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleExpertPlanTreeRateUI#actionSearch_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {

		hasDateSearch = true;
		String id = getSelectTreeNodeId()[0];
		// ���ò���ֵ
		setSelectorValue(id);

		if (getSelectedEndDate().before(getSelectedStartDate())) {
			MsgBox.showWarning(this, "��ʼ���ڲ��ܴ��ڽ������ڣ�������ѡ��");
			SysUtil.abort();
		}
		// Ĭ��ÿ��������������������
		buildTableAndChartPanel(false);
	}

	/**
	 * ��������ʼ��ͼ�ν���
	 * @param dataMap	��Ŀ������Ϣ
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected void initChartPanelData(String[] columnKeys, double[][] barData, double[][] lineData, int maxValue) {

		ScheduleChartHelper chartHelper = new ScheduleChartHelper();
		String title = getChartTitle();

		chart = chartHelper.getChartPanel(title, columnKeys, barData, lineData,maxValue);

		ChartPanel chartPanel = new ChartPanel(chart);
		
		removeAllMouseListeners(chartPanel);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Dimension chartDimension = new Dimension();
		chartDimension.setSize(width * 0.78, height * 0.63 - 100);
		if (height < 864) {
			chartDimension.setSize(width* 0.78 - 100, height * 0.63 - 100);
		}
//		chartPanel.setSize(new Dimension(980, 550));  //1280,1024
		chartPanel.setSize(chartDimension);
		this.chartPanel.removeAll();
		//		chartContainer.setSize(new Dimension(1010, 450));
		
		this.chartPanel.add(chartPanel);
		updateUI();
	}

	protected String getChartTitle() {
		return super.getChartTitle();
	}

	protected void setChartTitle(String chartTitle) {
		super.setChartTitle(chartTitle);
	}

	/**
	 * output actionExportToExcel_actionPerformed
	 */
	public void actionExportToExcel_actionPerformed(ActionEvent e) throws Exception {
		if (getProjectID() != null && dataMap != null && !dataMap.isEmpty()) {
			super.actionExportToExcel_actionPerformed(e);
		} else {
			MsgBox.showWarning(this, "û�п��Ե��������ݣ���ѡ�����ڵ�����������ԣ�");
			SysUtil.abort();
		}
	}


	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrePrint_actionPerformed
	 */
	public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrePrint_actionPerformed(e);
	}

	/**
	 * @see com.kingdee.eas.framework.client.CoreUI#getTableForCommon()
	 */
	@Override
	protected KDTable getTableForCommon() {
		return this.chartTable;
	}

	/**
	 * �����������������
	 * @Author��adward_huang
	 * @CreateTime��2012-9-25
	 */
	private void clearDates() {
		startDatePicker.setValue(null);
		endDatePicker.setValue(null);

	}

	/**
	 * ������������������
	 * @param startDate
	 * @param endDate
	 * @Author��adward_huang
	 * @CreateTime��2012-9-25
	 */
	public void resetDates(Date startDate, Date endDate) {
		startDatePicker.setValue(startDate);
		endDatePicker.setValue(endDate);
	}

	public boolean isHasDateSearch() {
		return hasDateSearch;
	}

	public void setHasDateSearch(boolean hasDateSearch) {
		this.hasDateSearch = hasDateSearch;
	}

	public String getSelectProjectName() {
		return selectProjectName;
	}

	public void setSelectProjectName(String selectProjectName) {
		this.selectProjectName = selectProjectName;
	}
	
	protected void chartTable_tableClicked(KDTMouseEvent e) throws Exception {
		int columnIndex = chartTable.getSelectManager().getActiveColumnIndex();
		int rowIndex = chartTable.getSelectManager().getActiveRowIndex();
		Object value = chartTable.getCell(rowIndex, columnIndex).getValue();
		int intValue = 0;
		try{
			intValue = Integer.parseInt(value+"");
		}catch (NumberFormatException ex) {
             //nothing to do
		}
		
//		if(value == null ||intValue==0){
//			FDCMsgBox.showInfo("�����ڶ�Ӧ�����޷��鿴�����б���Ϣ!");
//		    return;
//		}
		
		String detailUIName = RptTaskListUI.class.getName();
		UIContext uiContext = new UIContext();
		uiContext.put("startDate", getSelectedStartDate());
		uiContext.put("endDate", getSelectedEndDate());
		uiContext.put("selectedOrgs", getSelectTreeNodeId()[0]);//������ĿID
		uiContext.put("currentOrgId", this.chartTable.getCell(rowIndex, "id").getValue());
		uiContext.put("currentOrgName", this.chartTable.getCell(rowIndex, "projectName").getValue());
		uiContext.put("taskType", "proPrj");
		uiContext.put("chartType", "proPrj");
		uiContext.put("Owner", this);
		uiContext.put("state", chartTable.getColumnKey(columnIndex));//�ƻ���ɣ���ʱ��ɣ���ʱ��ɣ���ȷ��,δ���(����������)
		
		UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(detailUIName, uiContext).show();
	}

}