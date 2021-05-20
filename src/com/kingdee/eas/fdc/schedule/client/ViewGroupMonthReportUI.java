/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.RectangleEdge;
import com.kingdee.bos.ctrl.freechart.util.Rotation;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ �����±���
 * 
 * @author ��С��
 * @version EAS7.0
 * @createDate 2011-09-08
 * @see
 */
public class ViewGroupMonthReportUI extends AbstractViewGroupMonthReportUI {
	/** ȱʡ�汾�� */
	private static final long serialVersionUID = 1L;
	
	private Map allTask = new HashMap();
	
	private Map<String, String> paramMap = null;
	

	/** ��־ */
	private static final Logger logger = CoreUIObject.getLogger(ViewGroupMonthReportUI.class);

	/**
	 * output class constructor
	 */
	public ViewGroupMonthReportUI() throws Exception {
		super();
	}
	
	private void initParams() throws BOSException {
		if (paramMap == null) {
			paramMap = ViewReportHelper.getAllParams();
		}
	}

	private void initCbYearAndMonth() throws BOSException, SQLException {
		cbYear.removeAllItems();
		cbMonth.removeAllItems();
		ItemListener[] yearListeners = cbYear.getItemListeners();
		ItemListener[] monthListeners = cbMonth.getItemListeners();
		for (ItemListener aListener : yearListeners)
			cbYear.removeItemListener(aListener);
		for (ItemListener aListener : monthListeners)
			cbYear.removeItemListener(aListener);
		Set set = getAllTask();
		if (set.size() > 0) {
			for (int i = 1; i <= 12; i++) {
				cbMonth.addItem(i + "");
			}
			int max = Integer.parseInt(((String) set.toArray()[0]).split("-")[0]);
			int min = Integer.parseInt(((String) set.toArray()[set.size() - 1]).split("-")[0]);
			for (int i = max; i >= min && i <= max; i--) {
				cbYear.addItem(i + "");
			}
		}
		
		for (ItemListener aListener : yearListeners)
			cbYear.addItemListener(aListener);
		for (ItemListener aListener : monthListeners)
			cbYear.addItemListener(aListener);
	}

	/**
	 * ��ʼ�����<br>
	 * ������û�����Ԫ�������<br>
	 */
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
		this.thisTable.checkParsed();
		this.nextTable.checkParsed();
		thisTable.getColumn("project").setWidth(160);
		thisTable.getColumn("planEndDate").setWidth(100);
		thisTable.getColumn("checkDate").setWidth(100);
		thisTable.getColumn("realEndDate").setWidth(100);
		thisTable.getColumn("intendEndDate").setWidth(100);

		nextTable.getColumn("company").setWidth(140);
		nextTable.getColumn("project").setWidth(250);//
		nextTable.getColumn("taskName").setWidth(220);
		nextTable.getColumn("planStartDate").setWidth(220);
		nextTable.getColumn("planEndDate").setWidth(220);
		nextTable.getColumn("respPerson").setWidth(120);
		nextTable.getColumn("respDept").setWidth(120);
	}
	
	

	protected FDCScheduleTaskCollection getAllTask(String date, boolean isNext) throws BOSException, ParseException, SQLException {
		CurProjectInfo currProject = null;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectors = view.getSelector();
		addSelectors(selectors);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.MILESTONE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.costCenter.isFreeze", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.costCenter.isOUSealUp", Boolean.FALSE));

        FilterInfo filterOther = new FilterInfo();
		filterOther.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial  is null "));
		
		if (prmtProject.getValue() != null) {
			currProject = (CurProjectInfo) prmtProject.getData();
		}

		filter.mergeFilter(filterOther, "and");
		view.setFilter(filter);
		FDCScheduleTaskCollection col = null;
		if(allTask.containsKey("allTask")){
			col = (FDCScheduleTaskCollection) allTask.get("allTask");
		}else{
			col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			allTask.put("allTask", col);
		}
		 
		if (isNext) {
			return ViewReportHelper.getNextMonthTasks(col, date, currProject);
		}
		return ViewReportHelper.getCurrMonthTasks(col, date, currProject);
	}


	private void addSelectors(SelectorItemCollection selectors) {
		selectors.add("schedule.project.fullOrgUnit.name");
		selectors.add("schedule.project.fullOrgUnit.isCompanyOrgUnit");
		selectors.add("schedule.project.costCenter.parent.isCompanyOrgUnit");
		selectors.add("schedule.project.costCenter.parent.name");
		selectors.add("schedule.project.costCenter.name");
		selectors.add("schedule.project.costCenter.id");
		selectors.add("schedule.project.id");
		selectors.add("schedule.project.name");
		selectors.add("schedule.project.number");
		selectors.add("schedule.projectspecial.id");
		selectors.add("schedule.projectspecial.name");
		selectors.add("adminPerson.id");
		selectors.add("adminPerson.name");
		selectors.add("adminPerson.number");
		selectors.add("adminDept.id");
		selectors.add("adminDept.name");
		selectors.add("adminDept.number");
		selectors.add("id");
		selectors.add("name");
		selectors.add("start");
		selectors.add("end");
		selectors.add("actualEndDate");
		selectors.add("actualStartDate");
		selectors.add("planEvaluationPerson.name");
		selectors.add("planEvaluationPerson.id");
		selectors.add("qualityEvaluatePerson.name");
		selectors.add("qualityEvaluatePerson.id");
		selectors.add("actualStartDate");
		selectors.add("*");
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-11-3 void
	 * @version EAS7.0
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */

	private Set getAllTask() throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select task.fstart,task.fend from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch on sch.fid = task.fscheduleid ");
		builder
				.appendSql("left outer join t_fdc_curproject prj on sch.fprojectid = prj.fid where prj.fisenabled = 1 and sch.fversion =1 and task.ftasktype = 'milestone'");
		IRowSet rs = builder.executeQuery();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		LinkedHashSet set = new LinkedHashSet();
		while (rs.next()) {
			try {
				set.add(sdf.parse(sdf.format(rs.getDate("fstart"))));
				set.add(sdf.parse(sdf.format(rs.getDate("fend"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ViewReportHelper.sortDateByYearMonth(set);
	}

	/**
	 * @discription <��ʼ��ҳ������>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 * @throws ParseException
	 * @see <��ص���>
	 */
	public void initData() throws EASBizException, BOSException, ParseException, SQLException {
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();
		/*
		 * ȡ������-��
		 */
		if (cbYear.getSelectedItem() == null || cbMonth.getSelectedItem() == null) {
			return;
		}
		String selectedYear = cbYear.getSelectedItem().toString().trim();
		String selectedMonth = cbMonth.getSelectedItem().toString().trim();
		currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), false);
		nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), true);
		loadThisTableFields(currMonth);
		loadNextTableFields(nextMonth);
		
		if (currMonth.size() > 0) {
			initChart(currMonth);
		} else {
			pnlChart.removeAll();
		}
	}

	/**
	 * @discription <��ʼ�����¼ƻ��������>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public void loadNextTableFields(FDCScheduleTaskCollection list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				FullOrgUnitInfo respDept = entryInfo.getAdminDept();
				FDCScheduleInfo monthReportInfo = entryInfo.getSchedule();
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planStartDate").setValue(entryInfo.getStart());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("checkDate").setValue(entryInfo.getCheckDate());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(respDept);
				row.getCell("entryID").setValue(entryInfo.getId());
				FullOrgUnitInfo costCenter = entryInfo.getSchedule().getProject().getFullOrgUnit();
				if (null != costCenter) {
					if (costCenter.isIsCompanyOrgUnit()) {
						row.getCell("company").setValue(costCenter.getName());
					}
				}
				if (null != monthReportInfo.getProject()) {
					row.getCell("project").setValue(monthReportInfo.getProject().getName());
				}
				if (null != monthReportInfo.getProjectSpecial()) {
					row.getCell("project").setValue(monthReportInfo.getProjectSpecial().getName());
				}
				
				if(null != entryInfo.getPlanEvaluatePerson())
					  row.getCell("planPerson").setValue(entryInfo.getPlanEvaluatePerson().getName());
				if(null != entryInfo.getQualityEvaluatePerson())
					 row.getCell("quanlityPerson").setValue(entryInfo.getQualityEvaluatePerson().getName());
			}
		}

	}

	/**
	 * @discription <��ʼ�����������������������>
	 * @author <Xiaolong Luo>
	 * @throws SQLException
	 */
	public void loadThisTableFields(FDCScheduleTaskCollection list) throws BOSException, SQLException {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		boolean isEvaluation = false;
		boolean isParamControl = false;
		Map<String, ScheduleTaskProgressReportInfo> reportMap = ViewReportHelper.getScheudleReportData(list);
		Set<String> evaluationSet = FDCScheduleTaskStateHelper.isEvaluationed(null, list);
		ScheduleTaskProgressReportInfo reportInfo = null;
		
		String srcId = null;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) list.get(i);
				srcId = entryInfo.getSrcID();
				isEvaluation = false;
				isParamControl = false;
				if (evaluationSet.contains(srcId)) {
					isEvaluation = true;
				}
				//TODO�����������ʲô�ġ������ͻ����׿���Ϊ��
				if (paramMap != null && paramMap.containsKey(entryInfo.getSchedule().getProject().getCostCenter().getId().toString())) {
					isParamControl = true;
				}
				
				
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("realEndDate").setValue(entryInfo.getActualEndDate());
				row.getCell("checkDate").setValue(entryInfo.getCheckDate());
				row.getCell("completePercent").setValue(entryInfo.getComplete());
				if (reportMap.containsKey(srcId)) {
					reportInfo = reportMap.get(srcId);
					row.getCell("description").setValue(reportInfo.getDescription());
				}
				int complate = entryInfo.getComplete() == null ? 0 : entryInfo.getComplete().intValue();
				if (complate == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
					row.getCell("intendEndDate").setValue(entryInfo.getActualEndDate());
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
					row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate() == null ? entryInfo.getEnd() : entryInfo.getIntendEndDate());
				}
				FullOrgUnitInfo respDept = entryInfo.getAdminDept();
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(respDept);
				row.getCell("entryID").setValue(entryInfo.getId());
				FullOrgUnitInfo costCenter = entryInfo.getSchedule().getProject().getFullOrgUnit();
				if (null != costCenter) {
					if (costCenter.isIsCompanyOrgUnit()) {
						row.getCell("company").setValue(costCenter.getName());
					}
				}
				CurProjectInfo project = entryInfo.getSchedule().getProject();
				if (null != project) {
					row.getCell("project").setValue(project.getName());
				}
				
				if(null != entryInfo.getPlanEvaluatePerson())
					  row.getCell("planPerson").setValue(entryInfo.getPlanEvaluatePerson().getName());
				if(null != entryInfo.getQualityEvaluatePerson())
					 row.getCell("quanlityPerson").setValue(entryInfo.getQualityEvaluatePerson().getName());
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, entryInfo);
			}
		}
	}

	/**
	 * 
	 * @description ��ȡ���Ȼ㱨����
	 * @author �ź���
	 * @createDate 2011-12-15
	 * @return Map
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	private Map getScheudleReportData(FDCScheduleTaskInfo entryInfo) throws BOSException {
		Map report = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("intendEndDate");
		view.getSelector().add("description");
		view.getSelector().add("completePrecent");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", entryInfo.getId().toString()));
		view.setFilter(filter);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("reportDate");
		item.setSortType(SortType.DESCEND);
		sorter.add(item);
		view.setSorter(sorter);

		ScheduleTaskProgressReportCollection reportCol = ScheduleTaskProgressReportFactory.getRemoteInstance()
				.getScheduleTaskProgressReportCollection(view);
		if (reportCol != null && reportCol.size() > 0) {
			report.put("description", reportCol.get(0).getDescription());
			report.put("intendEndDate", reportCol.get(0).getIntendEndDate());
			report.put("completePrecent", reportCol.get(0).getCompletePrecent());
			reportCol.get(0).getReportDate();
		}
		return report;
	}

	/*
	 * չʾ��ʱ��ѭ������У����ݸ������е�ֵ��������ʾ��stateLogo�е�չʾ��ʽ��<br>
	 * 
	 * ע�⣺<br> 1���򹳵ĵ�Ԫ����Ҫ��������Ϊ���壬����̫ϸ����Ȧ������Ҫ��<br>
	 * 2����ɫδʹ�ñ�׼��Color.Green��Color.Orange����ʹ�����¹����Ľ�����ɫ���棬<br>
	 * ����Ϊ��ɫ̫�������״��ۣ�����ʹ��ʱ��������ȡ�
	 */
	// public void initStateCell(boolean isEvaluation, boolean isParamControl,
	// IRow row, FDCScheduleTaskInfo entryInfo) throws BOSException {
	// int state = -1;
	// // ʵ���������
	// Date realEndDate = entryInfo.getActualEndDate();
	// // �ƻ��������
	// Date planEndDate = entryInfo.getEnd();
	// String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
	// // ��
	// String achieve = EASResource.getString(rsPath, "achieve");
	// // Ȧ
	// String pending = EASResource.getString(rsPath, "pending");
	// // ��
	// Color green = new Color(10, 220, 10);
	// // ��
	// Color red = new Color(245, 0, 0);
	// // ��
	// Color orange = new Color(220, 180, 0);
	// int complete = entryInfo.getComplete() == null ? 0 :
	// entryInfo.getComplete().intValue();
	// state = ViewReportHelper.getState(isEvaluation, isParamControl,
	// entryInfo);
	// if (row != null) {
	// switch (state) {
	// case 0:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(green);
	// break;
	// case 1:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// case 2:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// case 3:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").getStyleAttributes().setFontColor(orange);
	// break;
	// }
	// }
	// }

	protected void initChart(FDCScheduleTaskCollection thisMonth) throws ParseException, SQLException {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(thisMonth), BorderLayout.CENTER);
		pnlChart.updateUI();
	}

	/**
	 * @description ����һ��ͼ�����
	 * @author
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws SQLException
	 * @throws ParseException
	 * @see
	 */
	protected JPanel createChartPanel(FDCScheduleTaskCollection thisMonth) throws ParseException, SQLException {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * ����һ��ͼ��
	 * 
	 * @param dataset
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	protected JFreeChart createChart(Dataset dataset) throws ParseException, SQLException {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat(
						"0.00%")));
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		plot.setOutlineStroke(new BasicStroke(0));// �߿��ϸ
		plot.setOutlinePaint(Color.white);// �߿���ɫ
		plot.setLabelFont(new Font("����", Font.PLAIN, 10));
		plot.setForegroundAlpha(0.75f);
		plot.setBackgroundPaint(Color.white);
		// ����ͻ����ʾ�����ݿ�
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// pie.setSectionLabelFont(new Font("����", Font.TRUETYPE_FONT, 12));
		// �趨����͸���ȣ�0-1.0֮�䣩
		plot.setBackgroundAlpha(0.6f);
		// �趨ǰ��͸���ȣ�0-1.0֮�䣩
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.08);
		plot.setNoDataMessage("δ��������");
		plot.setSectionPaint("��ʱ���", Color.GREEN);
		plot.setSectionPaint("��ʱ���", Color.RED);
		plot.setSectionPaint("��ȷ��", Color.ORANGE);
		plot.setSectionPaint("�ӳ�δ���", new Color(139, 0, 180));
		plot.setSectionPaint("ִ����", new Color(0, 82, 41));

		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("����", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("����", Font.BOLD, 10)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 10));
		return chart;
	}

	/*
	 * ��ȡͼƬ��Title
	 */
	public String getChartTitle() throws ParseException, SQLException {
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();
		String title = "";
		/*
		 * ȡ������-��
		 */
		String selectedYear = cbYear.getSelectedItem().toString().trim();
		String selectedMonth = cbMonth.getSelectedItem().toString().trim();
		if (null == selectedYear || "".equals(selectedYear) || selectedYear.length() == 0) {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			return title;
		}
		try {
			currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), false);
			nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), true);
			title = selectedYear + "��" + selectedMonth + "�¼����¶ȱ���";
			if (thisTable.getRowCount() > 0) {
				int finished = 0;// �����
				int delayed = 0;// ��ʱ��
				int unfinished = 0;// δ�����
				int excudeing = 0;// ִ����
				int affirm = 0;// ��ȷ��
				for (int i = 0; i < thisTable.getRowCount(); i++) {
					Object value = thisTable.getRow(i).getCell("state").getUserObject();
					if (value == null) {
						excudeing++;
						continue;
					}

					int state = Integer.parseInt(value.toString());
					switch (state) {
					case 0:
						finished++;
						break;
					case 1:
						delayed++;
						break;
					case 2:
						affirm++;
						break;
					case 3:
						unfinished++;
						break;
					}

					}
				String str = getTitle(currMonth, nextMonth, Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), finished, delayed,
						unfinished, affirm, excudeing);
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = selectedMonth + "��" + selectedMonth + "����̱�����" + currMonth.size() + "��,���а�ʱ���0��,��ʱ���0��,��ȷ��0��,��ʱ��δ���0��,ִ����0��ƻ�������̱�����"
						+ nextMonth.size() + "��," + "\n" + "��������̱�����״̬�ֲ����±�ͼ:";
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		return title;
	}

	private String getTitle(FDCScheduleTaskCollection currMonth, FDCScheduleTaskCollection nextMonth, int year, int month, int finished, int delayed,
			int unfinished, int affirm, int excudeing) {
		return year + "��" + month + "����̱�����" + currMonth.size() + "��,���а�ʱ���" + finished + "��,��ʱ���" + delayed + "��,��ȷ��" + affirm + "��,��ʱ��δ���"
				+ unfinished + "��,ִ����" + excudeing + "��ƻ�������̱�����" + nextMonth.size() + "�������̱�����״̬�ֲ����±�ͼ:";
	}

	/**
	 * @description ����ͼ�����������Դ
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected Dataset createDataset() {
		if (thisTable.getRowCount() > 0) {
			int finished = 0;// �����
			int delayed = 0;// ��ʱ��
			int unfinished = 0;// δ�����
			int excudeing = 0;// ִ����
			int affirm = 0;// ��ȷ��
			for (int i = 0; i < thisTable.getRowCount(); i++) {
				Object value = thisTable.getRow(i).getCell("state").getUserObject();
				if (value == null) {
					excudeing++;
					continue;
				}

				int state = Integer.parseInt(value.toString());
				switch (state) {
				case 0:
					finished++;
					break;
				case 1:
					delayed++;
					break;
				case 2:
					affirm++;
					break;
				case 3:
					unfinished++;
					break;
				}

			}
			return getDataValue(finished, delayed, unfinished, excudeing, affirm);
		}
		return new DefaultPieDataset();
	}

	private DefaultPieDataset getDataValue(int finished, int delayed, int unfinished, int excudeing, int affirm) {
		DefaultPieDataset result = new DefaultPieDataset();
		int size = thisTable.getRowCount();
		double fin = Double.parseDouble(String.valueOf(finished));
		double dely = Double.parseDouble(String.valueOf(delayed));
		double aff = Double.parseDouble(String.valueOf(affirm));
		double unfin = Double.parseDouble(String.valueOf(unfinished));
		double excude = Double.parseDouble(String.valueOf(excudeing));
		double total = fin + dely + aff + unfin + excude;
		if (finished == size) {
			result.setValue("��ʱ���", total);
		} else {
			result.setValue("��ʱ���", fin);
		}
		if (delayed == size) {
			result.setValue("��ʱ���", total);
		} else {
			result.setValue("��ʱ���", dely);
		}
		if (affirm == size) {
			result.setValue("��ȷ��", total);
		} else {
			result.setValue("��ȷ��", aff);
		}
		if (unfinished == size) {
			result.setValue("�ӳ�δ���", total);
		} else {
			result.setValue("�ӳ�δ���", unfin);
		}
		if (excude == size) {
			result.setValue("ִ����", total);
		} else {
			result.setValue("ִ����", excude);
		}
		return result;
	}

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		itemStateChanged();
	}

	protected void cbMonth_itemStateChanged(ItemEvent e) throws Exception {
		itemStateChanged();
	}

	protected void itemStateChanged() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
			initData();
		}
		pnlChart.updateUI();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initParams();
		initCbYearAndMonth();
		this.btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportExcel.setEnabled(true);
		this.menueItemExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.menueItemExportExcel.setEnabled(true);
		this.btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		this.btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		setDefalutSelected();
		this.nextTable.getStyleAttributes().setLocked(true);
		this.thisTable.getStyleAttributes().setLocked(true);
        this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		 loadCurProject();
		thisTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
					if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
						thisTable.showCellDetailInfo();
					}
				}
			}
		});
	}
	
	private void loadCurProject() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		// filter.getFilterItems().add(new FilterItemInfo("isEnabled",
		// Boolean.TRUE));
		// String sql =
		// "select fid from t_fdc_curproject where FCostCenterId in (select fid from t_org_baseunit where flongnumber like (select flongnumber||'%' from t_org_baseunit where fid ='"
		// + SysContext.getSysContext().getCurrentCostUnit().getId().toString()
		// +
		// "') and fisfreeze = 0 and fisOuSealUp = 0  and fid in (select forgid from T_PM_OrgRange where fuserid = '"
		// + SysContext.getSysContext().getCurrentUserInfo().getId().toString()
		// + "'))";
		// filter.getFilterItems().add(new FilterItemInfo("curProject.id", sql,
		// CompareType.INNER));
		// logger.error(sql);
		view.setFilter(filter);

		prmtProject.setEntityViewInfo(view);
		// prmtCurproject.setDataNoNotify(editData.getProject());
		// prmtCurproject.setData(editData.getProject());
		prmtProject.setEditable(false);
		logger.error(view);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintpreview_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "��������", thisTable });
		tables.add(new Object[] { "��������", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * �����״����
	 * <p>
	 * ����������
	 * 
	 * @return
	 */
	// protected String getTDName() {
	// return "����ͼ��";
	// }
	/**
	 * �����״�ģ��·��
	 * <p>
	 * ����������
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}

	/**
	 * ����һ��ͼƬ
	 * <p>
	 * ͨ�����ɽ������ɣ�����ͼ��ͱ������������<br>
	 * 
	 * ����������д�˷��������򵼳���ͼƬ����ҳ����ȫ��ͬ<br>
	 * ���ܵ��±���ĳЩ�л����п��������Ҽ�����к���
	 * 
	 * @return
	 */
	protected BufferedImage getUIIMG() {
		BufferedImage img1 = getImgAndTable();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();

		KDTSelectBlock curSelect = nextTable.getSelectManager().get();
		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + nextTable.getIndexColumn().getWidth() + nextTable.getColumns().getWidth();
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + nextTable.getHead().getHeight() + nextTable.getBody().getHeight();
		// ���ѡ��
		nextTable.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		nextTable.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		// ����4�д�����������ͼƬ���Ⱥ� ���û�ȥ
		int printpre1 = nextTable.getColumn(0).getWidth();
		int printpre2 = nextTable.getColumn(1).getWidth();
		int printpre3 = nextTable.getColumn(2).getWidth();
		int printpre4 = nextTable.getColumn(3).getWidth();
		int printpre5 = nextTable.getColumn(4).getWidth();
		int printpre6 = nextTable.getColumn(5).getWidth();
		int printpre7 = nextTable.getColumn(6).getWidth();
		int printpre8 = nextTable.getColumn(7).getWidth();
		nextTable.getColumn(0).setWidth(120);
		nextTable.getColumn(1).setWidth(140);
		nextTable.getColumn(2).setWidth(100);
		nextTable.getColumn(3).setWidth(100);
		nextTable.getColumn(4).setWidth(100);
		nextTable.getColumn(5).setWidth(100);
		nextTable.getColumn(6).setWidth(100);
		nextTable.getColumn(7).setWidth(100);
		int w2 = nextTable.getWidth();
		int h2 = nextTable.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		nextTable.paintAll(g);
		g.dispose();

		// 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1 - 550, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5����img2����img���·�
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();
		if (curSelect != null) {
			nextTable.getSelectManager().select(curSelect);
		}
		// ����4�д�����������ͼƬ���Ⱥ� ���û�ȥ
		nextTable.getColumn(0).setWidth(printpre1);
		nextTable.getColumn(1).setWidth(printpre2);
		nextTable.getColumn(2).setWidth(printpre3);
		nextTable.getColumn(3).setWidth(printpre4);
		nextTable.getColumn(4).setWidth(printpre5);
		nextTable.getColumn(5).setWidth(printpre6);
		nextTable.getColumn(6).setWidth(printpre7);
		nextTable.getColumn(7).setWidth(printpre8);
		// 7������img
		return img;
	}

	protected BufferedImage getImgAndTable() {
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// ����������
		KDTSelectBlock curSelect = thisTable.getSelectManager().get();
		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + thisTable.getIndexColumn().getWidth() + thisTable.getColumns().getWidth();
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + thisTable.getHead().getHeight() + thisTable.getBody().getHeight();
		// ���ѡ��
		thisTable.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		thisTable.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		// ��ӡǮխ��
		int printpre0 = thisTable.getColumn(0).getWidth();
		int printpre1 = thisTable.getColumn(1).getWidth();
		int printpre2 = thisTable.getColumn(2).getWidth();
		int printpre3 = thisTable.getColumn(3).getWidth();
		int printpre4 = thisTable.getColumn(4).getWidth();
		int printpre5 = thisTable.getColumn(5).getWidth();
		int printpre6 = thisTable.getColumn(6).getWidth();
		int printpre7 = thisTable.getColumn(7).getWidth();
		int printpre8 = thisTable.getColumn(8).getWidth();
		int printpre9 = thisTable.getColumn(9).getWidth();
		int printpre10 = thisTable.getColumn(10).getWidth();
		int printpre11 = thisTable.getColumn(11).getWidth();
		thisTable.getColumn(0).setWidth(50);
		thisTable.getColumn(1).setWidth(110);
		thisTable.getColumn(2).setWidth(110);
		thisTable.getColumn(3).setWidth(110);
		thisTable.getColumn(4).setWidth(80);
		thisTable.getColumn(5).setWidth(85);
		thisTable.getColumn(6).setWidth(80);
		thisTable.getColumn(7).setWidth(80);
		thisTable.getColumn(8).setWidth(80);
		thisTable.getColumn(9).setWidth(80);
		thisTable.getColumn(10).setWidth(80);
		thisTable.getColumn(11).setWidth(80);
		int w2 = thisTable.getWidth() + 10 + 290;
		// int w2 = kdtThisEntry.getWidth() + 10 + 430;
		int h2 = thisTable.getHeight() + 10;
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		thisTable.paintAll(g);
		g.dispose();
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5����img2����img���·�
		g.drawImage(img2, 0, h1, w2, h2, null);

		g.dispose();

		// 6�������߿����ԭ������ѡ��֮ǰ��ѡ�����
		if (curSelect != null) {
			thisTable.getSelectManager().select(curSelect);
		}
		// ��ӡ��խ��
		thisTable.getColumn(0).setWidth(printpre0);
		thisTable.getColumn(1).setWidth(printpre1);
		thisTable.getColumn(2).setWidth(printpre2);
		thisTable.getColumn(3).setWidth(printpre3);
		thisTable.getColumn(4).setWidth(printpre4);
		thisTable.getColumn(5).setWidth(printpre5);
		thisTable.getColumn(6).setWidth(printpre6);
		thisTable.getColumn(7).setWidth(printpre7);
		thisTable.getColumn(8).setWidth(printpre8);
		thisTable.getColumn(9).setWidth(printpre9);
		thisTable.getColumn(10).setWidth(printpre10);
		thisTable.getColumn(11).setWidth(printpre11);
		// 7������img
		return img;
	}

	/**
	 * ��������ͼ���ͼ��
	 * <p>
	 * ��ʱ���״�ֻ��Ҫ��ӡͼ��������ı��ʹ�����������Դ��ӡ<br>
	 * ��ʱ�״�ȡͼƬʹ�ô˷�����������getUIIMG()
	 */
	protected BufferedImage getChartIMG() {
		// int height2 = pnlChart.getHeight();
		// int width2 = pnlChart.getWidth();
		// BufferedImage img = new BufferedImage(width2, height2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img.getGraphics();
		// pnlChart.paintComponents(g);
		// return img;
		// ��ӡǰխ��
		Dimension size = pnlChart.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 850, h1);
		pnlChart.setSize(size);
		int height2 = pnlChart.getHeight();
		int width2 = pnlChart.getWidth();
		// kDPanelImage.add(KDTitle);

		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		pnlChart.paintComponents(g);
		// �Ļ�ȥ
		size.setSize(w1, h1);
		pnlChart.setSize(size);
		return img;
	}

	public void onShow() throws Exception {
		super.onShow();
		initTable();
		sptTop.setDividerLocation(220);
		sptDown.setDividerLocation(220);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	private void setDefalutSelected() {
		if (setDefalutYearSelected()) {
			setDefalutMonthSelected();
		}
	}

	protected boolean setDefalutYearSelected() {
		// ��ʼ���ص�ǰ�µ����� modify by duhongming
		for (int index = 0; index < cbYear.getItemCount(); index++) {
			String item = (String) cbYear.getItemAt(index);
			if (item.equals(getCurrYear() + "")) {
				cbYear.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	protected boolean setDefalutMonthSelected() {
		for (int index = 0; index < cbMonth.getItemCount(); index++) {
			String item = (String) cbMonth.getItemAt(index);
			if (item.equals(getCurrMonthOfYear() + "")) {
				cbMonth.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	private int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}

	private int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}
	
	@Override
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		itemStateChanged();
	}
	
	@Override
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {

		allTask.clear();
		itemStateChanged();
	}
}