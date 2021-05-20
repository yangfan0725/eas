/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.LegendItem;
import com.kingdee.bos.ctrl.freechart.chart.LegendItemCollection;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.axis.SubCategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.Plot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.GroupedStackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.data.KeyToGroupMap;
import com.kingdee.bos.ctrl.freechart.data.Range;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.category.DefaultCategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;

/**
 * output class name
 */
public class ExaminationRateAchievedUI extends AbstractExaminationRateAchievedUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExaminationRateAchievedUI.class);
    
    public String curName;
    /**
     * output class constructor
     */
    public ExaminationRateAchievedUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		// ���ز˵��� ���ߺͷ���
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
		tblMain.getStyleAttributes().setLocked(true);
		// ������һ�εļ���
		initPreTime();
		tblMain.getHead().getRow(0).getCell(0).setValue("�·�");
	}
    
    private void initPreTime() {
		// ��ǰ�û�
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// ��ǰ����֯
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String function = "examinationRateAchievedUI";
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, function);
			if (!FDCHelper.isEmpty(treeId)) {
				// DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
				// CurProjectInfo info = new CurProjectInfo();
				// info.setId(BOSUuid.read(treeId));
				// node.setUserObject(info);
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo info = (CurProjectInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							curName = info.getName();
							initChart();
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	if (isFirstOnload()) {
			return;
		}
		curName = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			curName = cpinfo.getName();
		}
		initChart();
    }

    // �ƻ���������
	private Date startDate;
	// �ƻ��깤����
	private Date endDate;

	protected void initChart() {
		super.initChart();
		cbYear.removeAllItems();
		fillChart();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			// curName = "";
			// curName = cpinfo.getName();
			if (cpinfo.getId() != null) {
				String id = cpinfo.getId().toString();
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("name"));
				// ��������
				sic.add(new SelectorItemInfo("startDate"));
				// �깤����
				sic.add(new SelectorItemInfo("endDate"));
				view.setSelector(sic);
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("project.id", id));
				// �Ƿ������°汾
				info.getFilterItems().add(new FilterItemInfo("isCheckVersion", Boolean.TRUE));
				// ���ֶ�Ϊ�ձ�ʾ������ƻ�������ƻ�����ʣ�
				// info.getFilterItems().add(new
				// FilterItemInfo("projectSpecial.id", null,
				// CompareType.EQUALS));
				view.setFilter(info);
				FDCScheduleCollection col = null;
				try {
					col = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
					// ������Ŀ�ͼƻ���һ��һ�Ĺ�ϵ���������°汾ֻ��һ�����ݣ��������ﲻ��whileѭ��
					if (col != null) {
						startDate = null;
						endDate = null;
						for (int count = 0; count < col.size(); count++) {
							FDCScheduleInfo fdcsinfo = col.get(count);
							
							// �ƻ���������
							if (fdcsinfo.getStartDate() != null && startDate != null) {
								startDate = startDate.compareTo(fdcsinfo.getStartDate()) < 0 ? startDate : fdcsinfo.getStartDate();
							} else {
								startDate = fdcsinfo.getStartDate();
							}

							// �ƻ��깤����
							if (fdcsinfo.getEndDate() != null && endDate != null) {
								endDate = endDate.compareTo(fdcsinfo.getEndDate()) > 0 ? endDate : fdcsinfo.getEndDate();
							} else {
								endDate = fdcsinfo.getEndDate();
							}
						}
							if (startDate == null || endDate == null) {
								return;
							}
							// // �ƻ���������
							// startDate = fdcsinfo.getStartDate();
							// // �ƻ��깤����
							// endDate = fdcsinfo.getEndDate();
							Date nowdate = new Date();
							// Ӧ��ȥ��ǰ�����·ݵ����һ��
						Date endOfMonth = DateUtils.endOfMonth(nowdate);
						if (endOfMonth.before(endDate)) {
								// Ŀǰ���ڱȼƻ�������ֻ꣬��ʾ����������~
							endDate = endOfMonth;
							}
						// if (nowdate.before(endDate)) {
						// // Ŀǰ���ڱȼƻ�������ֻ꣬��ʾ����������~
						// endDate = nowdate;
						// }

							// ������������ֵ
							Calendar cal = Calendar.getInstance();
							cal.setTime(startDate);
							int m = cal.get(Calendar.YEAR);
							cal.setTime(endDate);
							int n = cal.get(Calendar.YEAR);
							// �������2008�꣬������������ҲӦ�ú���2008�꣬����ȡ�Ⱥţ�
							for (int i = m; i <= n; i++) {
								cbYear.addItem(new Integer(i));
							}
							Date curDate = new Date();
							cal.setTime(curDate);
						int cur = cal.get(Calendar.YEAR);
						for (int i = 0; i < cbYear.getItemCount(); i++) {
							int year = Integer.parseInt(cbYear.getItemAt(i).toString());
							if (year == cur) {
								cbYear.setSelectedIndex(i);
							}
							}
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	// ������һ��
		tblMain.checkParsed();
		// �Ƴ�������
		tblMain.removeRows();
		Object obj = null;
		if (cbYear.getSelectedItem() == null) {
			return;
		}
		// �õ��ͻ���������ʵ����
		obj = cbYear.getSelectedItem();
		int year = Integer.parseInt(obj.toString());
		Calendar cal = Calendar.getInstance();
		// ѡ����ݵ�һ��һ��(ѡ�����ڵĿ�ʼ)
		cal.set(year, 0, 1);
		Date yearBegin = cal.getTime();// 2010-1-1
		// ѡ���·ݵ�ʮ������ʮһ�գ�ѡ�����ڵĽ�����
		cal.set(year, 11, 31);
		Date yearEnd = cal.getTime();// 2010-12-31

		Date planBegin = startDate;// �ƻ���ʼ
		Date planEnd = endDate;// �ƻ�����
		// ������� Date ���ڴ� Date���򷵻�ֵ 0������� Date �� Date ����֮ǰ��
		// �򷵻�С�� 0 ��ֵ������� Date �� Date ����֮���򷵻ش��� 0 ��ֵ��
		Date begin = yearBegin.compareTo(planBegin) > 0 ? yearBegin : planBegin;
		Date end = yearEnd.compareTo(planEnd) < 0 ? yearEnd : planEnd;
		// ���¼ƻ������
		int[] curPlan = new int[12];
		// ����ʵ�������
		int[] curAct = new int[12];
		// ��ʱ�����
		int[] aswc = new int[12];
		int[] curPlanUN = new int[12];
		// ��ʱ�����
		int[] yswc = new int[12];

		// ���ݹ�����Ŀѡ�����ȫ������
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CurProjectInfo info = (CurProjectInfo) node.getUserObject();
		FDCScheduleTaskCollection col = null;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		// ʵ���깤����
		sic.add(new SelectorItemInfo("actualEndDate"));
		// ʵ�ʿ�ʼ����
		sic.add(new SelectorItemInfo("actualStartDate"));
		// �ƻ���ʼ����
		sic.add(new SelectorItemInfo("start"));
		// �ƻ��������
		sic.add(new SelectorItemInfo("end"));
		// ������
		sic.add(new SelectorItemInfo("complete"));
		view.setSelector(sic);
		FilterInfo finfo = new FilterInfo();
		finfo.getFilterItems().add(new FilterItemInfo("schedule.project.id", info.getId().toString()));
		// �������ڹ��˳����������
		// �ƻ���ʼ�������ڵ�����Ҫ�������ʵĿ�ʼ����
		// finfo.getFilterItems().add(new FilterItemInfo("start", begin,
		// CompareType.GREATER_EQUALS));
		// �ƻ������������ڵ�����Ҫ�������ʵĽ�������
		finfo.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS_EQUALS));
		// ����������ƻ�
		// finfo.getFilterItems().add(new
		// FilterItemInfo("schedule.projectSpecial.id", null,
		// CompareType.EQUALS));
		finfo.getFilterItems().add(new FilterItemInfo("isCheckNode", Boolean.TRUE));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		view.setFilter(finfo);
		// �õ��Ĺ�����Ŀ��ѡ�������������
		col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo info2 = col.get(i);
			Date jhwc = info2.getEnd();// ����8��

			// update(add) by libing at 2011-10-13
			Object o = cbYear.getSelectedItem();
			int y = Integer.parseInt(o.toString());
			Calendar c1 = Calendar.getInstance();
			c1.set(y, 0, 1);
			int diffday = DateUtils.compareUpToDay(info2.getEnd(), c1.getTime());
			if (diffday < 0) {
				continue;
			}
			// ����Ϊ13���޸�����
			
			cal.setTime(jhwc);
			int count = cal.get(Calendar.MONTH);
			// 8�·ݵĵ��¼ƻ��������1("�ƻ���������ڵ�ǰ�µ�����ƻ�")
			curPlan[count] = curPlan[count] + 1;
			if (info2.getComplete() == null) {
				info2.setComplete(new BigDecimal(0));
			}
			if (info2.getComplete().compareTo(new BigDecimal(100)) < 0) {
				// 8���·ݵ�δ�������1
				curPlanUN[count] = curPlanUN[count] + 1;
			} else {
				// 8�·ݵ�ʵ���������1(����״̬Ϊ����ɣ��Ҽƻ���������ڵ�ǰ�µ�����ƻ�)
				curAct[count] = curAct[count] + 1;
				if (info2.getActualEndDate() != null) {
					// if (info2.getActualEndDate().compareTo(info2.getEnd()) <=
					// 0) {
					// // ��ʱ�������ʵ���������С�ڵ��ڼƻ�������ڣ�
					// aswc[count] = aswc[count] + 1;
					// } else {
					// // ��ʱ�����(ʵ��������ڴ��ڼƻ��������)
					// yswc[count] = yswc[count] + 1;
					// }
					if (DateUtils.compareUpToDay(info2.getActualEndDate(), info2.getEnd()) <= 0) {
						// ��ʱ�������ʵ���������С�ڵ��ڼƻ�������ڣ�
						aswc[count] = aswc[count] + 1;
					} else {
						// ��ʱ�����(ʵ��������ڴ��ڼƻ��������)
						yswc[count] = yswc[count] + 1;
					}
				} else {
					continue;
				}

			}
		}
		// ���ر������
		tblMain.checkParsed();
		tblMain.removeRows();
		cal.setTime(begin);
		int fstart = cal.get(Calendar.MONTH);
		cal.setTime(end);
		int fend = cal.get(Calendar.MONTH);
		// �ۼƼƻ������
		int allPlan = 0;
		int m = 1;
		for (int i = fstart; i <= fend; i++) {
			IRow row = tblMain.addRow();
			// �·�A
			row.getCell("month").setValue((i + 1) + "��");
			// ���¼ƻ������B
			row.getCell("curPlan").setValue(new Integer(curPlan[i]));
			allPlan += curPlan[i];
			// ����ʵ�������C
			row.getCell("curDone").setValue(new Integer(curAct[i]));

			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ���������D
				row.getCell("curRate").setExpressions("=C" + m + "/" + "B" + m);
			}

			// ��ʱ�����E
			row.getCell("wellDone").setValue(new Integer(aswc[i]));

			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ��ʱ�����F
				row.getCell("wellRate").setExpressions("=E" + m + "/" + "B" + m);
			}
			// ��ʱ�����G
			row.getCell("lateDone").setValue(new Integer(yswc[i]));

			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ��ʱ�����H
				row.getCell("lateRate").setExpressions("=G" + m + "/" + "B" + m);
			}
			// �ۼƼƻ������I
			// row.getCell("allPlan").setValue(new Integer(allPlan));

			// �ۼƼƻ������:Ĭ������,"�ƻ��������"�ڵ�ǰ��֮ǰ(������ʷ��ݣ�������ǰ��)����������.
			Calendar cc = Calendar.getInstance();
			Object oo = cbYear.getSelectedItem();
			int yy = Integer.parseInt(oo.toString());
			cc.set(yy, i + 1, 1, 0, 0, 0);
			Date d = cc.getTime();
			EntityViewInfo ee = new EntityViewInfo();
			FilterInfo ff = new FilterInfo();
			ff.getFilterItems().add(new FilterItemInfo("schedule.project.id", info.getId().toString()));
			// �ƻ������������ڵ��ڵ�ǰ����
			ff.getFilterItems().add(new FilterItemInfo("end", d, CompareType.LESS));
			ff.getFilterItems().add(new FilterItemInfo("isCheckNode", Boolean.TRUE));
			ff.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
			ee.setFilter(ff);
			FDCScheduleTaskCollection fstc = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(ee);
			if (fstc != null) {
				row.getCell("allPlan").setValue(new Integer(fstc.size()));
			}
			// �ۼ�ʵ�������
			int allDone = 0;
			// �ۼư�ʱ�����
			int allWellDone = 0;
			// �ۼ���ʱ�����
			int allLateDone = 0;
			int equals = 0;
			for (int co = 0; co < fstc.size(); co++) {
				FDCScheduleTaskInfo scheduleTaskInfo = fstc.get(co);
				//��ȥʵ�����ʱ���ڵ�����ǰ������(�ۼ�ʱ����ʾ��ǰ��ɵ�)���������һ�Σ����Ǻ�����Ϊsql��util�Ĳ�һ��������û���˵�
				// ����������ڴι���
				if (DateUtils.compareUpToDay(scheduleTaskInfo.getEnd(), d) >= 0) {
					equals++;
					continue;
				}
				if (scheduleTaskInfo.getComplete() == null) {
					scheduleTaskInfo.setComplete(new BigDecimal(0));
				}
				// δ���
				if (scheduleTaskInfo.getComplete().compareTo(new BigDecimal(100)) < 0) {

				} else {
					// �����
					allDone += 1;
					// if (scheduleTaskInfo.getActualEndDate() != null &&
					// scheduleTaskInfo.getEnd() != null) {
					// if (scheduleTaskInfo.getActualEndDate().compareTo(
					// scheduleTaskInfo.getEnd()) <= 0) {
					// allWellDone += 1;
					// } else {
					// allLateDone += 1;
					// }
					// }
					if (DateUtils.compareUpToDay(scheduleTaskInfo.getActualEndDate(), scheduleTaskInfo.getEnd()) <= 0) {
						allWellDone += 1;
					} else {
						allLateDone += 1;
					}

				}
			}
			if (fstc != null) {
				/**
				 * �ٴ������ۼ��������ֵ<br>
				 * ͬ��Ҫ�޸�ר���˾�����ˣ���̱�����ʵ�����<br>
				 * �ۼƼƻ������
				 */
				row.getCell("allPlan").setValue(new Integer(new Integer(fstc.size()).intValue() - equals));

			}
			// �ۼ�ʵ�������J
			row.getCell("allDone").setValue(new Integer(allDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// �ۼ������K
				row.getCell("allRate").setExpressions("=J" + m + "/" + "I" + m);
			}
			// �ۼư�ʱ�����L
			row.getCell("allWellDone").setValue(new Integer(allWellDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// �ۼư�ʱ�����M
				row.getCell("allWellRate").setExpressions("=L" + m + "/" + "I" + m);
			}
			// �ۼ���ʱ�����N
			row.getCell("allLateDone").setValue(new Integer(allLateDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// �ۼ���ʱ�����O
				row.getCell("allLateRate").setExpressions("=N" + m + "/" + "I" + m);
			}
			m++;
		}
		fillChart();
    }

    protected void fillChart() {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
		updateUI();
	}

	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
    
    /**
	 * ��ʼ������� add by libing
	 */
	protected void initTree() throws Exception {
		super.initTree();
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		// Ĭ��չ������ĩ��
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    
	protected void initTable() {
		super.initTable();
	}
    
	/**
	 * ����һ����״ͼ���ݼ�<br>
	 * ʵ��Ӧ��ʱ���ڴ˷�����ȡ�����������ݼ�����
	 * <p>
	 * ��һ������Ϊ��ɰٷֱ�<br>
	 * �ڶ�������Ϊ���飬����������Ϊ���������飬������Ϊ���ڷ���<br>
	 * ����������Ϊ�¶�
	 */
	protected Dataset createDataset() {
		// return super.createDataset();
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int count = tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			if (tblMain.getRow(i).getCell("wellRate").getValue() == null) {
				result.addValue(0, "���´���� (��ʱ���)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("wellRate").getValue().toString()) * 100, "���´���� (��ʱ���)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("lateRate").getValue() == null) {
				result.addValue(0, "���´���� (��ʱ���)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("lateRate").getValue().toString()) * 100, "���´���� (��ʱ���)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("allWellRate").getValue() == null) {
				result.addValue(0, "�ۼƴ���� (��ʱ���)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allWellRate").getValue().toString()) * 100, "�ۼƴ���� (��ʱ���)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("allLateRate").getValue() == null) {
				result.addValue(0, "�ۼƴ���� (��ʱ���)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allLateRate").getValue().toString()) * 100, "�ۼƴ���� (��ʱ���)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
		}
		return result;
	}

	protected int getChartHeight() {
		return getHeight() - 200;
	}

	/**
	 * ����ͼ��
	 */
	protected JFreeChart createChart(Dataset dataset) {
		// ���+������Ŀ+��������
		String title = "���˼ƻ������";
		if (!FDCHelper.isEmpty(curName)) {
			title = curName + " " + title;
		}
		if (!FDCHelper.isEmpty(cbYear.getSelectedItem())) {
			title = cbYear.getSelectedItem().toString() + " " + title;
		}
		JFreeChart chart = ChartFactory.createStackedBarChart(title, // ����
				"�·�", // ����
				"�����", // ����
				(CategoryDataset) dataset, // ����Դ
				PlotOrientation.VERTICAL, // ͼ����
				true, // ˵�����
				true, // ���ͣ����ʾ
				false // urls
				);
		// ��������
		chart.getTitle().setFont(new Font("����", Font.PLAIN, 20));

		// ���÷�����������¿�ͷ�Ķ�����һ���飬�ۼƿ�ͷ��������һ����
		// ���������������ʾ���ĸ�����
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");
		map.mapKeyToGroup("���´���� (��ʱ���)", "G1");
		map.mapKeyToGroup("���´���� (��ʱ���)", "G1");
		map.mapKeyToGroup("�ۼƴ���� (��ʱ���)", "G2");
		map.mapKeyToGroup("�ۼƴ���� (��ʱ���)", "G2");
		renderer.setSeriesToGroupMap(map);
		// ���ڼ��
		renderer.setItemMargin(0.02);
		// �������
		renderer.setDrawBarOutline(false);

		// �������ɫ�����ڽ���
		// setSeriesPaint���ø���ɫ���õĽڵ㣬ÿ���¶��ڣ����������ӵ������һ�����鿪ʼ
		// �±�Ϊ0��������������Ȼ�󵽵ڶ������ӣ��Դ�����ֱ�����
		// ���ڱ����У�ÿ����ֻ��2�����ӣ�ÿ�����ӷ�2�����飬����0��2��ʾ���º��ۼƵİ�ʱ���
		// 1,3��ʾ��ʱ���
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149), 0.0f, 0.0f, new Color(89, 183, 89));
		renderer.setSeriesPaint(0, p1);

		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200), 20.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);

		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255), 0.0f, 0.0f, new Color(49, 82, 123));
		renderer.setSeriesPaint(2, p3);

		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170), 20.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);
		// ��ɫ���䷽�򣬴˴�Ϊ���Ľ���
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));

		// ���ͣ������״ͼ����ʾ��ʾ��Ϣ��ʽ
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("({0}, {1}) = {2}%", NumberFormat.getInstance()));
		// ��ͼ����ʾ���ݵĸ�ʽ���˴���ʾ�ٷֱȣ����ҿ�����ʾ
		// renderer.setItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator(
		// "{2}", NumberFormat.getInstance()));
		// renderer.setItemLabelsVisible(true);
		// renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
		// ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER,
		// TextAnchor.TOP_CENTER, 0.0));
		// renderer.setItemLabelFont(new Font("����", Font.PLAIN, 9));
		chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
		chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRenderer(renderer);

		// ���ᣬ������ÿ���¶�֮��ļ��
		SubCategoryAxis domainAxis = new SubCategoryAxis("");
		domainAxis.setCategoryMargin(0.2);
		// domainAxis.addSubCategory("����");
		// domainAxis.addSubCategory("�ۼ�");
		plot.setDomainAxis(domainAxis);
		// ���ᣬС������
		NumberAxis rangeAxis = new NumberAxis("�����(%)") {
			public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
				range = new Range(0, 110);
				super.setRange(range, turnOffAutoRange, notify);
			}
		};
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		rangeAxis.setUpperMargin(0.10);
		rangeAxis.setLowerBound(0.0);
		rangeAxis.setUpperBound(110.0);
		rangeAxis.setTickUnit(new NumberTickUnit(5.0));
		plot.setRangeAxis(rangeAxis);

		// �����˵����
		plot.setFixedLegendItems(createLegendItems());
		return chart;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("���°�ʱ���", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183, 89));
		LegendItem item2 = new LegendItem("������ʱ���", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105, 105));
		LegendItem item3 = new LegendItem("�ۼư�ʱ���", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82, 123));
		LegendItem item4 = new LegendItem("�ۼ���ʱ���", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 45, 45));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
		return result;
	}

	// ��ӡ
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	// ��ӡԤ��
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionPrintPreview_actionPerformed(e);
	}

	protected String getTDPath() {
		// TODO Auto-generated method stub
		return super.getTDPath();
	}

	/**
	 * ����һ��ͼƬ(��д����)
	 * <p>
	 * ����ͼƬ˼·��<br>
	 * 1����ͼ����嵼��Ϊimg1<br>
	 * 2����������õ����ʵĸ߿����ѡ��<br>
	 * 3������񵼳�Ϊimg2<br>
	 * 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�<br>
	 * 5����img2����img���·�<br>
	 * 6�������߿����ԭ������ѡ��֮ǰ��ѡ�����<br>
	 * 7������img
	 */
	protected BufferedImage getUIIMG() {
		// 1����ͼ����嵼��Ϊimg1
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// BufferedImage img1 = new BufferedImage(w1, h1,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img1.getGraphics();
		// pnlChart.paintAll(g);
		// g.dispose();

		// 2����������õ����ʵĸ߿����ѡ��
		// �������ڸ߿�ѡ��
		// int curDivLeft = sptLeft.getDividerLocation();
		// int curDivTop = sptTop.getDividerLocation();

		KDTSelectBlock curSelect = tblMain.getSelectManager().get();

		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth() + tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + tblMain.getHead().getHeight() + tblMain.getBody().getHeight();
		// sptTop
		// .setDividerLocation(curDivTop
		// + (tblMain.getHeight() - fitHeight));
		// ���ѡ��
		tblMain.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		tblMain.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		int w2 = tblMain.getWidth();
		int h2 = tblMain.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�
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
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7������img
		return img;
	}

	protected BufferedImage getChartIMG() {
		return super.getChartIMG();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		if (tblMain.getRowCount() <= 0) {
			FDCMsgBox.showInfo("û����Ӧ��ͼ�����赼��");
			abort();
		}
		super.actionExportIMG_actionPerformed(e);
	}

	protected boolean isMainSchedule() {
		return true;
	}

	public boolean destroyWindow() {
		try {
			IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
			// ��ǰ�û�
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// ��ǰ����֯
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CurProjectInfo curProject = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
				curProject = (CurProjectInfo) node.getUserObject();
			}
			// ������Ŀ
			Object[] objs = new Object[] { user, orgUnit, curProject };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = curProject.getId().toString();
				if (isMainSchedule()) {// mainscheudlerateachivement
					// ���������Ĺ�����Ŀ
					autoRemember.save(userID, orgUnitID, "examinationRateAchievedUI", curProjectID);
				} else {
					// �����ר�� ��ô���ȼ���ר�������Ĺ�����ĿID�� �ڶ�������ר���ID
					// autoRemember.save(userID, orgUnitID,
					// "SpecialScheudleRateAchivementProject", curProjectID);
					// autoRemember.save(userID, orgUnitID,
					// "SpecialScheudleRateAchivementProjectSpecial",
					// editData.getProjectSpecial().getId()
					// .toString());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return super.destroyWindow();
	}

	/**
	 * @description �����е�ֵ�Ƿ����
	 * @author �ź���
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */

	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
}