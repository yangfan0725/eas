/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;

/**
 * output class name
 */
public class DeptMonthlyTaskReportUI extends AbstractDeptMonthlyTaskReportUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4582166030031599300L;
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyTaskReportUI.class);
    
    /**
     * output class constructor
     */
    public DeptMonthlyTaskReportUI() throws Exception
    {
        super();
    }
   
	/**
	 * @throws Exception
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.windowTitle = "�����¶ȱ���";
		initData();
	}
	/**
	 * @return
	 */
	public String getChartTitle() {
		String title = "";
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		String selectedItem = getUIContext().get("scheduleMonth").toString();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();

		/*
		 * ȡ������-��
		 */
		// String selectedItem = (String) cbYear.getSelectedItem();
		if (null == selectedItem || "".equals(selectedItem) || selectedItem.length() == 0) {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			return title;
		}

		int year = 1990;
		int month = 1;

		try {
			/*
			 * ����ñ�������
			 */
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			month = Integer.parseInt(split[1]);
			currMonth = getDeptMonthScheduleTasks(year, month, adminDept, false);

			/*
			 * ����������ݡ�
			 */
			nextMonth = getDeptMonthScheduleTasks(year, month, adminDept, true);
			title = year + "��" + month + "��" + adminDept.getName() + "�±�";
			if (currMonth.size() > 0) {
				int finished = 0;// �����
				int delayed = 0;// ��ʱ��
				int unfinished = 0;// δ�����
				int excudeing = 0;// ִ����
				for (int i = 0; i < currMonth.size(); i++) {
					DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) currMonth.get(i);
					Date planEndDate = entryInfo.getPlanFinishDate();
					BigDecimal complete = new BigDecimal(0);
					complete = entryInfo.getComplete();
					Date realEndDate = null;
					DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
					if (report != null) {
						complete = report.getCompletePrecent();
						realEndDate = report.getRealEndDate();
					}
					int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete
							.intValue());
					switch (state) {
					case 0:
						finished++;
						break;
					case 1:
						delayed++;
						break;
					case 2:
						unfinished++;
						break;
					case 4:
						excudeing++;
						break;
					}
				}
				String str = getTitle(currMonth, nextMonth, year, month, finished, delayed, unfinished, excudeing);
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = year + "��" + month + "�¹�����" + currMonth.size() + "��,���а�ʱ���0��,��ʱ���0��,��ʱ��δ���0��,ִ����0��ƻ���������" + nextMonth.size()
						+ "��," + "\n" + "��������״̬�ֲ����±�ͼ��";
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return "";
	}

	public void initData() throws Exception {
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		String selectedItem = getUIContext().get("scheduleMonth").toString();
		this.cbYear.addItem(selectedItem);
		cbYear.setSelectedIndex(0);
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		/*
		 * ȡ������-��
		 */
		if (null == selectedItem || "".equals(selectedItem) || selectedItem.length() == 0) {
			return;
		}

		int year = 1990;
		int month = 1;

		/*
		 * ����ñ�������
		 */
		String[] split = selectedItem.split("-");
		year = Integer.parseInt(split[0]);
		month = Integer.parseInt(split[1]);
		currMonth = getDeptMonthScheduleTasks(year, month, adminDept, false);

		/*
		 * ����������ݡ�
		 */
		nextMonth = getDeptMonthScheduleTasks(year, month, adminDept, true);

		initChart(currMonth);
		if (currMonth.size() > 0) {
			loadThisTableFields(currMonth);
		}
		if (nextMonth.size() > 0) {
			loadNextTableFields(nextMonth);
		}
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }
    
	public boolean destroyWindow() {
		return true;
	}
	
	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {

	}
	
	protected String getUIName() {
		return DeptMonthlyTaskReportUI.class.getName();
	}
	/**
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		// super.initTree();
	}
}