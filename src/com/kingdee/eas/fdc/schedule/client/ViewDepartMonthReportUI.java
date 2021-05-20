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
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

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
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanelNotToDo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ �ƻ����������б�༭����
 * 
 * @author ��С�����ܺ���
 * @version EAS7.0
 * @createDate 2011-09-08
 * @see
 */
public class ViewDepartMonthReportUI extends AbstractViewDepartMonthReportUI {

	/** ȱʡ�汾�� */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = CoreUIObject.getLogger(ViewDepartMonthReportUI.class);

	protected ITreeBuilder treeBuilder;

	/**
	 * output class constructor
	 */
	public ViewDepartMonthReportUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		// չʾ�ɱ�������֯
		buildCostCenterTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}

	/**
	 * 
	 * @description �����ɱ�������
	 * @author �ź���
	 * @createDate 2011-11-2
	 * @throws Exception void
	 * @version EAS7.0
	 * @see
	 */
	private void buildCostCenterTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		String longNumber = currentCostUnit.getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE, CompareType.EQUALS));
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, filter, new SelectorItemCollection());
		treeBuilder.buildTree(treeMain);
	}

	/**
	 * 
	 * @description �ɱ�������֯�ӿ�
	 * @author �ź���
	 * @createDate 2011-11-2
	 * @return
	 * @throws Exception
	 *             ILNTreeNodeCtrl
	 * @version EAS7.0
	 * @see
	 */
	private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(CostCenterOrgUnitFactory.getRemoteInstance());
	}

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		if (cbYear.getSelectedItem() != null && !"".equals(cbYear.getSelectedItem())) {
			initData();
		} else {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
		}
		pnlChart.updateUI();
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
	 * @see <��ص���>
	 */
	public void initData() throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo ccoui = (CostCenterOrgUnitInfo) node.getUserObject();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		/*
		 * ȡ������-��
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
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
		currMonth = getDeptMonthScheduleTasks(year, month, ccoui, false);

		/*
		 * ����������ݡ�
		 */
		nextMonth = getDeptMonthScheduleTasks(year, month, ccoui, true);

		initChart(currMonth);
		if (currMonth.size() > 0) {
			loadThisTableFields(currMonth);
		}
		if (nextMonth.size() > 0) {
			loadNextTableFields(nextMonth);
		}
	}

	protected void initChart(List thisMonth) throws BOSException {
		pnlChart.add(createChartPanel(thisMonth), BorderLayout.CENTER);
	}

	/**
	 * @description ����һ��ͼ�����
	 * @author
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected JPanel createChartPanel(List thisMonth) throws BOSException {
		JFreeChart chart = createChart(createDataset(thisMonth));
		return new ChartPanel(chart);
	}

	/**
	 * ����һ��ͼ��
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
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
		plot.setSectionPaint("�ӳ�δ���", new Color(139, 0, 180));
		plot.setSectionPaint("ִ����", new Color(0, 82, 41));

		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("����", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("����", Font.BOLD, 12)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 12));
		return chart;
	}

	public String getChartTitle() {
		String title = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo ccoui = (CostCenterOrgUnitInfo) node.getUserObject();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();

		/*
		 * ȡ������-��
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
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
			currMonth = getDeptMonthScheduleTasks(year, month, ccoui, false);
			title = year + "��" + month + "��" + ccoui.getName() + "�±�";
			/*
			 * ����������ݡ�
			 */
			nextMonth = getDeptMonthScheduleTasks(year, month, ccoui, true);
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

	private DeptTaskProgressReportInfo getReportByScheduleTask(DeptMonthlyScheduleTaskInfo entryInfo) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = view.getSelector();
		selector.add("completePrecent");
		selector.add("planEndDate");
		selector.add("realStartDate");
		selector.add("realEndDate");
		selector.add("intendEndDate");
		selector.add("reportor.id");
		selector.add("reportor.name");
		selector.add("reportDate");
		selector.add("state");
		selector.add("description");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("relateTask.id", entryInfo.getId().toString());
		filter.appendFilterItem("progressEdition", "1");
		view.setFilter(filter);
		DeptTaskProgressReportCollection reportCol = DeptTaskProgressReportFactory.getRemoteInstance()
				.getDeptTaskProgressReportCollection(view);
		if (reportCol != null && reportCol.size() > 0) {
			return reportCol.get(0);
		}
		return null;
	}

	public String getTitle(List currMonth, List nextMonth, int year, int month, int finished, int delayed, int unfinished,
			 int excudeing) {
		return year + "��" + month + "�¹�����" + currMonth.size() + "��,���а�ʱ���" + finished + "��,��ʱ���" + delayed + "��,��ʱ��δ���"
				+ unfinished + "��,ִ����" + excudeing + "��ƻ���������" + nextMonth.size() + "��,��������״̬�ֲ����±�ͼ��";
	}

	/**
	 * @description ����ͼ�����������Դ
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected Dataset createDataset(List thisMonth) throws BOSException {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisMonth.size() > 0) {
			int finished = 0;// �����
			int delayed = 0;// ��ʱ��
			int unfinished = 0;// δ�����
			int excudeing = 0;// ִ����
			for (int i = 0; i < thisMonth.size(); i++) {
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) thisMonth.get(i);
				Date planEndDate = entryInfo.getPlanFinishDate();
				BigDecimal complete = new BigDecimal(0);
				complete = entryInfo.getComplete();
				Date realEndDate = null;
				DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
				if (report != null) {
					complete = report.getCompletePrecent();
					realEndDate = report.getRealEndDate();
				}
				int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete.intValue());
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
			return getDataValue(thisMonth, finished, delayed, unfinished, excudeing);
		}
		return result;
	}

	private DefaultPieDataset getDataValue(List thisMonth, int finished, int delayed, int unfinished, int excudeing) {
		DefaultPieDataset result = new DefaultPieDataset();
		double fin = Double.parseDouble(String.valueOf(finished));
		double dely = Double.parseDouble(String.valueOf(delayed));
		double unfin = Double.parseDouble(String.valueOf(unfinished));
		double excude = Double.parseDouble(String.valueOf(excudeing));
		double total = fin + dely + unfin + excude;
		if (finished == thisMonth.size()) {
			result.setValue("��ʱ���", total);
		} else {
			result.setValue("��ʱ���", fin);
		}
		if (delayed == thisMonth.size()) {
			result.setValue("��ʱ���", total);
		} else {
			result.setValue("��ʱ���", dely);
		}
		if (unfinished == thisMonth.size()) {
			result.setValue("�ӳ�δ���", total);
		} else {
			result.setValue("�ӳ�δ���", unfin);
		}
		if (excude == thisMonth.size()) {
			result.setValue("ִ����", total);
		} else {
			result.setValue("ִ����", excude);
		}
		return result;
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
	public void loadNextTableFields(List list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
				row.getCell("planEndDate").setValue(entryInfo.getPlanFinishDate());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(entryInfo.getSchedule().getAdminDept());
				row.getCell("entryID").setValue(entryInfo.getId());
			}
		}

	}
	

	/**
	 * @discription <��ʼ�����������������������>
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
	public void loadThisTableFields(List list) {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) list.get(i);
				DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
				if (report == null) {
					report = new DeptTaskProgressReportInfo();
				}
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getPlanFinishDate());
				BigDecimal complate = entryInfo.getComplete();
				if (null != complate && complate.intValue() == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
				}
				row.getCell("completePercent").setValue(report.getCompletePrecent());
				row.getCell("realEndDate").setValue(report.getRealEndDate());
				row.getCell("intendEndDate").setValue(report.getIntendEndDate());
				row.getCell("description").setValue(report.getDescription());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("description").setValue(report.getDescription());
				row.getCell("respDept").setValue(entryInfo.getSchedule().getAdminDept());
				row.getCell("entryID").setValue(entryInfo.getId());
				initStateCell(row, entryInfo); 
			}
		}
	}

	/*
	 * չʾ��ʱ��ѭ������У����ݸ������е�ֵ��������ʾ��stateLogo�е�չʾ��ʽ��<br>
	 * 
	 * ע�⣺<br> 1���򹳵ĵ�Ԫ����Ҫ��������Ϊ���壬����̫ϸ����Ȧ������Ҫ��<br>
	 * 2����ɫδʹ�ñ�׼��Color.Green��Color.Orange����ʹ�����¹����Ľ�����ɫ���棬<br>
	 * ����Ϊ��ɫ̫�������״��ۣ�����ʹ��ʱ��������ȡ�
	 */
	public void initStateCell(IRow row, DeptMonthlyScheduleTaskInfo entryInfo) {
		Date planEndDate = entryInfo.getPlanFinishDate();
		BigDecimal complete = new BigDecimal(0);
		complete = entryInfo.getComplete();
		Date realEndDate = null;
		DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
		if (report != null) {
			complete = report.getCompletePrecent();
			realEndDate = report.getRealEndDate();
		} 
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// ��
		String achieve = EASResource.getString(rsPath, "achieve");
		// Ȧ
		String pending = EASResource.getString(rsPath, "pending");
		// ��
		Color red = new Color(245, 0, 0);
		// ��
		Color green = new Color(10, 220, 10);
		// ��
		Color orange = new Color(220, 180, 0);
		int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete.intValue());
		if (row != null) {
			switch (state) {
			case 0:
				row.getCell("state").setValue(achieve);
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(green);
				break;
			case 1:
				row.getCell("state").setValue(achieve);
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case 2:
				row.getCell("state").setValue(pending);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case 3:
				row.getCell("state").setValue(pending);
				row.getCell("state").getStyleAttributes().setFontColor(orange);
				break;
			}
		}
	}
	
	public static int getState(Date realEndDate, Date planEndDate, int complete) {
		// �������Ϊ100 ���ݼƻ�ʱ���ʵ��ʱ�����ж��ǰ�ʱ��ɻ���ʱ���
		if (realEndDate != null && complete == 100) {
			if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
				return 0;
			}
			return 1;
		}
		if (complete < 100) {
			if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
				return 4; // �������С��100 �ƻ��������>=��ǰʱ�� ״̬Ϊ�գ�ִ���У�
			}
			return 2;
		}
		return -1;
	}
	

	/**
	 * @discription <ȡ��ָ���·�����>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/08>
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
	 * @see <��ص���>
	 */
	public List getDeptMonthScheduleTasks(int year, int month, Object obj, boolean isNext) throws BOSException {
		if (isNext) {
			if (month == 12) {
				year = year + 1;
				month = 1;
			} else {
				month = month + 1;
			} 
		}
		String[] arrays = getDeptMonthSchedule(year, month, obj);
		List monthList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("schedule.*");
		view.getSelector().add("schedule.adminDept.name");
		view.getSelector().add(new SelectorItemInfo("id"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.id", new HashSet(Arrays.asList(arrays)), CompareType.INCLUDE));
		view.setFilter(filter);
		DeptMonthlyScheduleTaskCollection collection = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(view);
		for (int j = 0; j < collection.size(); j++) {
			DeptMonthlyScheduleTaskInfo deptMonthlyScheduleTaskInfo = collection.get(j);
			deptMonthlyScheduleTaskInfo.put("report", getReportByScheduleTask(deptMonthlyScheduleTaskInfo));
			monthList.add(deptMonthlyScheduleTaskInfo);
		}
		return monthList;
	}

	/**
	 * 
	 * @description ������֯���ꡢ�»�ȡ�����¶ȼƻ�
	 * @author �ź���
	 * @createDate 2011-12-15
	 * @param year
	 * @param month
	 * @param obj
	 * @return
	 * @throws BOSException
	 *             String[]
	 * @version EAS7.0
	 * @see
	 */
	private String[] getDeptMonthSchedule(int year, int month, Object obj) throws BOSException {
		CostCenterOrgUnitInfo costUnit = null;
		AdminOrgUnitInfo adminUnit = null;
		String adminDeptID = "";
		if (obj instanceof CostCenterOrgUnitInfo) {
			costUnit = (CostCenterOrgUnitInfo) obj;
			adminDeptID = costUnit.getId().toString();
		} else if (obj instanceof AdminOrgUnitInfo) {
			adminUnit = (AdminOrgUnitInfo) obj;
			adminDeptID = adminUnit.getId().toString();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from T_SCH_DeptMonthlySchedule where FAdminDept='" + adminDeptID + "' and fyear=" + year + " and fmonth="
				+ month);
		IRowSet rs = builder.executeQuery();
		String[] arrays = new String[rs.size()];
		int i = 0;
		try {
			while (rs.next()) {
				arrays[i] = rs.getString("fid");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrays;
	}


	private Set getProjectsByOrgUnit(String orgUnitID) {
		Set ids = new HashSet();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid from T_FDC_CurProject where FFullOrgUnit='" + orgUnitID + "'");
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString("fid"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public void onLoad() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		super.onLoad();
		nextTable.checkParsed();
		thisTable.checkParsed();
		thisTable.getColumn("taskName").setWidth(250);
		thisTable.getColumn("completePercent").setWidth(120);
		thisTable.getColumn("planEndDate").setWidth(120);
		thisTable.getColumn("realEndDate").setWidth(120);
		thisTable.getColumn("respPerson").setWidth(130);
		thisTable.getColumn("respDept").setWidth(130);

		nextTable.getColumn("taskName").setWidth(280);
		nextTable.getColumn("planStartDate").setWidth(250);
		nextTable.getColumn("planEndDate").setWidth(250);
		nextTable.getColumn("respPerson").setWidth(170);
		nextTable.getColumn("respDept").setWidth(170);

		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.btnExportIMG.setText("����Excel");
		this.btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportIMG.setToolTipText("����Excel");
		this.menuItemExpIMG.setText("����Excel");
		this.menuItemExpIMG.setToolTipText("����Excel");
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));

		this.nextTable.getStyleAttributes().setLocked(true);
		this.thisTable.getStyleAttributes().setLocked(true);
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

	public void onShow() throws Exception {
		super.onShow();
		this.sptTop.setDividerLocation(246);
		this.sptDown.setDividerLocation(252);
		thisTable.getColumn("intendEndDate").getStyleAttributes().setHided(true);
		thisTable.getColumn("isComplete").getStyleAttributes().setHided(true);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		/*
		 * ��ʼ�����������������
		 */
		initCbYear();
		setDefalutYearSelected();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		// super.actionExportIMG_actionPerformed(e);
		List tables = new ArrayList();
		tables.add(new Object[] { "��������", thisTable });
		tables.add(new Object[] { "��������", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * @discription <��ʼ������ѡ��(�ꡢ��)��ֵ>
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
	public void initCbYear() {
		cbYear.removeAllItems();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo orgUnit = (CostCenterOrgUnitInfo) node.getUserObject();
			if (orgUnit.getId() != null) {
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("year");
				sic.add("month");
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("adminDept.id", orgUnit.getId().toString()));
				view.setFilter(info);
				view.setSelector(sic);
				DeptMonthlyScheduleCollection col = null;
				try {
					col = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							DeptMonthlyScheduleInfo departReportInfo = col.get(i);
							int year = departReportInfo.getYear();
							int month = departReportInfo.getMonth();
							String str = year + "-" + month;
							cbYear.addItem(str);
						}
					}
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
			}
		}
		sortCBYear();
	}

	/**
	 * 
	 * @description ��������
	 * @author �ź���
	 * @createDate 2011-12-21 void
	 * @version EAS7.0
	 * @see
	 */
	private void sortCBYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		LinkedHashSet items = new LinkedHashSet();
		for (int i = 0; i < cbYear.getItemCount(); i++) {
			String item = (String) cbYear.getItemAt(i);
			try {
				items.add(sdf.parse(item));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Set sortDateByYearMonth = ViewReportHelper.sortDateByYearMonth(items);
		cbYear.removeAllItems();
		cbYear.addItems(sortDateByYearMonth.toArray());
	}
	protected BufferedImage getChartIMG() {
		// ��ӡǰխ��
		Dimension size = pnlChart.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 180, h1);
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
		thisTable.getColumn("description").setWidth(148);
		int w2 = thisTable.getWidth() + 10 + 130;
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
		thisTable.getColumn("description").setWidth(200);
		// 7������img
		return img;
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	protected BufferedImage getUIIMG() {
		// 1����ͼ����嵼��Ϊimg1
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
		nextTable.getColumn(0).setWidth(180);
		nextTable.getColumn(1).setWidth(180);
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
		BufferedImage img = new BufferedImage(w1 - 170, h1 + h2, BufferedImage.TYPE_INT_RGB);
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
		// 7������img
		return img;
	}
	
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanelNotToDo();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
	}

	protected String getUIName() {
		return ViewDepartMonthReportUI.class.getName();
	}
}