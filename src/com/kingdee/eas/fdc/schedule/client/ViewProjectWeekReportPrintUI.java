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
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ViewProjectWeekReportPrintUI extends AbstractViewProjectWeekReportPrintUI {
	private static final long serialVersionUID = -4155418224252894875L;
	private static final Logger logger = CoreUIObject.getLogger(ViewProjectWeekReportPrintUI.class);

	private ProjectSpecialInfo ps;
	private CurProjectInfo prj;
	public ViewProjectWeekReportPrintUI() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.setUITitle("��Ŀ�����ӡ����");
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		kDthisWeek.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
		this.kdtThisEntry.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.kdtThisEntry.getStyleAttributes().setLocked(true);
		this.kdtNextEntrys.getStyleAttributes().setLocked(true);
		onladDataOfTable();
		kdtThisEntry.getColumn("description").setWidth(200);//
		kdtNextEntrys.getColumn("respPerson").setWidth(300);
		kdtNextEntrys.getColumn("respDept").setWidth(370);
		this.kDSplitPane1.setDividerLocation(250);
		this.kDSplitPane2.setDividerLocation(225);
	}

	protected void initWorkButton() {
		btnExportExcel.setEnabled(true);
		btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnPrint.setEnabled(true);
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.btnPrintPreview.setEnabled(true);
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		super.initWorkButton();
	}

	/**
	 * @description ���ݴ������Ķ���õ�����
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void onladDataOfTable() throws Exception {
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
			// /* ProjectWeekReportEditUI projectWeekReport =
			// (ProjectWeekReportEditUI) getUIContext().get("Owner");
			// String editDataId = projectWeekReport.editDataId;
			// EntityViewInfo view = new EntityViewInfo();
			// view.getSelector().add(new SelectorItemInfo("*"));
			// view.getSelector().add(new SelectorItemInfo("project.*"));
			// view.getSelector().add(new SelectorItemInfo("entrys.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.relateTask.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.respPerson.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.respDept.*"));
			// FilterInfo filter = new FilterInfo();
			// filter.getFilterItems().add(new FilterItemInfo("id",
			// editDataId));
			// view.setFilter(filter);
			// ProjectWeekReportCollection collection =
			// ProjectWeekReportFactory.
			// getRemoteInstance().getProjectWeekReportCollection(view);
			// if (collection.size() > 0) {*/
			ProjectWeekReportInfo projectWeekReportInfo = (ProjectWeekReportInfo) getUIContext().get("reportInfo");
			   if (projectWeekReportInfo.getProjectSpecial() != null) {
				ps = projectWeekReportInfo.getProjectSpecial();
				prj = projectWeekReportInfo.getProject();
			} else {
				prj = projectWeekReportInfo.getProject();
			}
				String state = projectWeekReportInfo.getState().toString();
				ProjectWeekReportEntryCollection entryCollection = projectWeekReportInfo.getEntrys();
				List thisWeek = new ArrayList();
				List nextWeek = new ArrayList();

				if (entryCollection.size() > 0) {
					for (int i = 0; i < entryCollection.size(); i++) {
						ProjectWeekReportEntryInfo entryInfo = entryCollection.get(i);
						if (entryInfo.isIsNext()) {
							nextWeek.add(entryInfo);
						} else {
							thisWeek.add(entryInfo);
						}
					}
				}
				loadThisTableFields(thisWeek, state);
				loadNextTableFields(nextWeek);
				initChart(thisWeek);
			}
		// }

	}
	
	protected void initChart(List thisWeek) throws Exception {
		kDPanelImage.add(createChartPanel(thisWeek), BorderLayout.CENTER);
	}

	/**
	 * @description ����һ��ͼ�����
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected JPanel createChartPanel(List thisWeek) throws Exception {
		JFreeChart chart = createChart(createDataset(thisWeek));
		return new ChartPanel(chart);
	}

	/**
	 * @description ����ͼ�����������Դ
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected Dataset createDataset(List thisWeek) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisWeek.size() > 0) {
			int finished = 0;// �����
			int delayed = 0;// ��ʱ��
			int unfinished = 0;// δ�����
			int excudeing = 0;// ִ����
			int affirm = 0;// ��ȷ��
			for (int i = 0; i < kdtThisEntry.getRowCount(); i++) {
				Object value = kdtThisEntry.getRow(i).getCell("state").getUserObject();
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
			double fin = Double.parseDouble(String.valueOf(finished));
			double dely = Double.parseDouble(String.valueOf(delayed));
			double aff = Double.parseDouble(String.valueOf(affirm));
			double unfin = Double.parseDouble(String.valueOf(unfinished));
			double excude = Double.parseDouble(String.valueOf(excudeing));
			double total = fin + dely + aff + unfin + excude;
			if (finished == thisWeek.size()) {
				result.setValue("��ʱ���", total);
			} else {
				result.setValue("��ʱ���", fin);
			}
			if (delayed == thisWeek.size()) {
				result.setValue("��ʱ���", total);
			} else {
				result.setValue("��ʱ���", dely);
			}
			if (affirm == thisWeek.size()) {
				result.setValue("��ȷ��", total);
			} else {
				result.setValue("��ȷ��", aff);
			}
			if (unfinished == thisWeek.size()) {
				result.setValue("�ӳ�δ���", total);
			} else {
				result.setValue("�ӳ�δ���", unfin);
			}
			if (excude == thisWeek.size()) {
				result.setValue("ִ����", total);
			} else {
				result.setValue("ִ����", excude);
			}
		}
		return result;
	}

	/**
	 * ����ͼƬ����
	 * 
	 * @return
	 */
	public String getChartTitle() throws Exception {
		String content = "";
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
//			ProjectWeekReportEditUI projectWeekReport = (ProjectWeekReportEditUI) getUIContext().get("Owner");
//			String editDataId = projectWeekReport.editDataId;
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSelector().add(new SelectorItemInfo("*"));
//			view.getSelector().add(new SelectorItemInfo("project.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id", editDataId));
//			view.setFilter(filter);
			// ProjectWeekReportCollection collection =
			// ProjectWeekReportFactory.
			// getRemoteInstance().getProjectWeekReportCollection(view);
			// if (collection.size() > 0) {
			ProjectWeekReportInfo projectWeekReportInfo = (ProjectWeekReportInfo) getUIContext().get("reportInfo");
				ProjectWeekReportEntryCollection entryCollection = projectWeekReportInfo.getEntrys();
				List thisWeek = new ArrayList();
				List nextWeek = new ArrayList();

				if (entryCollection.size() > 0) {
					for (int i = 0; i < entryCollection.size(); i++) {

						ProjectWeekReportEntryInfo entryInfo = entryCollection.get(i);
						if (entryInfo.isIsNext()) {
							nextWeek.add(entryInfo);
						} else {
							thisWeek.add(entryInfo);
						}
					}
				}
				content = projectWeekReportInfo.getYear() + "���" + projectWeekReportInfo.getWeek() + "��"
						+ projectWeekReportInfo.getProject().getName() + "��Ŀ�ܱ�";
				String title = projectWeekReportInfo.getYear() + "���" + projectWeekReportInfo.getWeek() + "��";
				if (thisWeek.size() > 0 && thisWeek != null) {
					int finished = 0;// �����
					int delayed = 0;// ��ʱ��
					int unfinished = 0;// δ�����
					int excudeing = 0;// ִ����
					int affirm = 0;// ��ȷ��
				for (int i = 0; i < kdtThisEntry.getRowCount(); i++) {
					Object value = kdtThisEntry.getRow(i).getCell("state").getUserObject();
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

					String str = title + "������" + thisWeek.size() + "��,��ʱ���" + finished + "��,��ʱ���" + delayed + "��,��ȷ��" + affirm + "��,��ʱ��δ���"
							+ unfinished + "��,ִ����" + excudeing + "��ƻ����ܹ�������" + nextWeek.size() + "��,�����±�״ͼ��";
					Font font = new Font(str, Font.PLAIN, 12);
					content = content + "\n" + font.getName();
				} else {
					String str = title + "������" + thisWeek.size() + "��,��ʱ���0��,��ʱ���0��,��ȷ��0��,��ʱ��δ���0��,ִ����0��ƻ����ܹ�������" + nextWeek.size() + "��," + "\n"
							+ "�����±�״ͼ��";
					Font font = new Font(str, Font.PLAIN, 12);
					content = content + "\n" + font.getName();
				}

			}

		return content;

	}

	/**
	 * ����һ��ͼ��
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) throws Exception {
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
			chart.addSubtitle(new TextTitle(titls[1], new Font("����", Font.BOLD, 12)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 12)); 
		return chart;
	}

	public void loadNextTableFields(List list) {
		kdtNextEntrys.removeRows(false);
		this.kdtNextEntrys.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = kdtNextEntrys.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				//row.getCell("relateTask").setValue(entryInfo.getRelateTask());
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());
				row.getCell("entryID").setValue(entryInfo.getId());

			}
		}

	}

	/*
	 * �������е����ݣ���ʾ�����ڱ����
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#loadThisTableFields()
	 */
	public void loadThisTableFields(List list, String state) throws BOSException, SQLException {
		kdtThisEntry.removeRows(false);
		this.kdtThisEntry.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			ProjectWeekReportEntryInfo entryInfo = null;
			entryInfo = (ProjectWeekReportEntryInfo) list.get(0);
			String costCenterId = null;
			
			String sql = "select fcostcenterid from t_fdc_curproject where fid =(select fprojectid from t_sch_fdcschedule where fid = (select fscheduleid from t_sch_fdcscheduletask where fid = ?))";
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql);
			builder.addParam(entryInfo.getRelateTask().getId().toString());
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				costCenterId = rs.getString(1);
			}
			
			Object obj = ps == null ? prj : ps;

			boolean isParamControl = ViewReportHelper.isStartingParamControl(costCenterId, obj);
			boolean isEvaluation = false;
			Set<String> evSet = null;
			if (isParamControl) {
				FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
				for (int i = 0; i < list.size(); i++) {
					entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
					cols.add(entryInfo.getRelateTask());
				}
				evSet = FDCScheduleTaskStateHelper.isEvaluationed(null, cols);
			}
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				isEvaluation = false;
				entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = kdtThisEntry.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				row.getCell("completePercent").setValue(new Integer(entryInfo.getCompletePrecent()));
				row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
				row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate());
				if (entryInfo.getDescription() != null && !"".equals(entryInfo.getDescription())) {
					row.getCell("description").setValue(entryInfo.getDescription());
				} else {
					row.getCell("description").setValue("");
				}
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());

				row.getCell("entryID").setValue(entryInfo.getId());
				if (evSet != null && evSet.contains(entryInfo.getRelateTask().getSrcID())) {
					isEvaluation = true;
				}
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, entryInfo);
			}
		}
	}

	/**
	 * @description ���ڱ�����ݲ������޸�
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */

	protected void kdtNextEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		if (this.kdtNextEntrys.getSelectManager().getActiveRowIndex() >= 0) {
			IRow row = this.kdtNextEntrys.getRow(this.kdtNextEntrys.getSelectManager().getActiveRowIndex());

			row.getCell("taskName").getStyleAttributes().setLocked(true);
			row.getCell("planStartDate").getStyleAttributes().setLocked(true);
			row.getCell("planEndDate").getStyleAttributes().setLocked(true);
			row.getCell("respPerson").getStyleAttributes().setLocked(true);
			row.getCell("respDept").getStyleAttributes().setLocked(true);
		} else {
			return;
		}
	}

	/**
	 * @description ���ڱ�����ݲ������޸�
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected void kdtThisEntry_tableClicked(KDTMouseEvent e) throws Exception {
		IRow row = this.kdtThisEntry.getRow(this.kdtThisEntry.getSelectManager().getActiveRowIndex());
		if (null == row) {
			SysUtil.abort();
		}
		row.getCell("state").getStyleAttributes().setLocked(true);
		row.getCell("taskName").getStyleAttributes().setLocked(true);
		row.getCell("planEndDate").getStyleAttributes().setLocked(true);
		// row.getCell("isComplete").getStyleAttributes().setLocked(true);
		row.getCell("completePercent").getStyleAttributes().setLocked(true);
		row.getCell("realEndDate").getStyleAttributes().setLocked(true);
		row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
		row.getCell("description").getStyleAttributes().setLocked(true);
		row.getCell("respPerson").getStyleAttributes().setLocked(true);
		row.getCell("respDept").getStyleAttributes().setLocked(true);

		super.kdtThisEntry_tableClicked(e);
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "��������", kdtThisEntry });
		tables.add(new Object[] { "��������", kdtNextEntrys });
		FDCTableHelper.exportExcel(this, tables);
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
	protected BufferedImage getChartIMG() {
		// int height2 = kDPanelImage.getHeight();
		// int width2 = kDPanelImage.getWidth();
		// // kDPanelImage.add(KDTitle);
		//
		// BufferedImage img = new BufferedImage(width2, height2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img.getGraphics();
		// kDPanelImage.paintComponents(g);
		// return img;

		// ��ӡǰխ��
		Dimension size = kDPanelImage.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 780, h1);
		kDPanelImage.setSize(size);
		int height2 = kDPanelImage.getHeight();
		int width2 = kDPanelImage.getWidth();
		// kDPanelImage.add(KDTitle);

		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		kDPanelImage.paintComponents(g);
		// �Ļ�ȥ
		size.setSize(w1, h1);
		kDPanelImage.setSize(size);
		return img;
	}

	protected BufferedImage getImgAndTable() {
		// BufferedImage img1 = getChartIMG();
		// int w1 = img1.getWidth();
		// int h1 = img1.getHeight();
		// // ����������
		// KDTSelectBlock curSelect = kdtThisEntry.getSelectManager().get();
		// // ����������ʿ��(2�����صı߽�+����п�+�����п��)
		// int fitWidth = 2 + kdtThisEntry.getIndexColumn().getWidth() +
		// kdtThisEntry.getColumns().getWidth();
		// // ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		// int fitHeight = 2 + kdtThisEntry.getHead().getHeight() +
		// kdtThisEntry.getBody().getHeight();
		// // ���ѡ��
		// kdtThisEntry.getSelectManager().remove();
		// // �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		// kdtThisEntry.setSize(fitWidth, fitHeight);
		//
		// // 3������񵼳�Ϊimg2
		// int w2 = kdtThisEntry.getWidth() + 10;
		// int h2 = kdtThisEntry.getHeight() + 10;
		// BufferedImage img2 = new BufferedImage(w2, h2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img2.getGraphics();
		// kdtThisEntry.paintAll(g);
		// g.dispose();
		// float scale = (float) w2 / w1;
		// w1 = w2;
		// h1 = (int) (h1 * scale);
		// BufferedImage img = new BufferedImage(w1, h1 + h2,
		// BufferedImage.TYPE_INT_RGB);
		// Image scaleImage = img1.getScaledInstance(w1, h1,
		// Image.SCALE_AREA_AVERAGING);
		// g = img.createGraphics();
		// g.drawImage(scaleImage, 0, 0, w1, h1, null);
		//
		// // 5����img2����img���·�
		// g.drawImage(img2, 0, h1, w2, h2, null);
		//
		// g.dispose();
		//
		// // 6�������߿����ԭ������ѡ��֮ǰ��ѡ�����
		// if (curSelect != null) {
		// kdtThisEntry.getSelectManager().select(curSelect);
		// }
		//
		// // 7������img
		// return img;
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// ����������
		KDTSelectBlock curSelect = kdtThisEntry.getSelectManager().get();
		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + kdtThisEntry.getIndexColumn().getWidth() + kdtThisEntry.getColumns().getWidth();
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + kdtThisEntry.getHead().getHeight() + kdtThisEntry.getBody().getHeight();
		// ���ѡ��
		kdtThisEntry.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		kdtThisEntry.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		// ��ӡǮխ��
		kdtThisEntry.getColumn("description").setWidth(148);
		kdtThisEntry.getColumn("taskName").setWidth(200);
		kdtThisEntry.getColumn("planEndDate").setWidth(100);
		kdtThisEntry.getColumn("completePercent").setWidth(50);
		kdtThisEntry.getColumn("realEndDate").setWidth(100);
		kdtThisEntry.getColumn("intendEndDate").setWidth(96);
		 int w2 = kdtThisEntry.getWidth() + 10 + 290;
		// int w2 = kdtThisEntry.getWidth() + 10 + 430;
		int h2 = kdtThisEntry.getHeight() + 10;
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		kdtThisEntry.paintAll(g);
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
			kdtThisEntry.getSelectManager().select(curSelect);
		}
		// ��ӡ��խ��
		kdtThisEntry.getColumn("description").setWidth(200);
		kdtThisEntry.getColumn("taskName").setWidth(300);
		kdtThisEntry.getColumn("planEndDate").setWidth(140);
		kdtThisEntry.getColumn("completePercent").setWidth(80);
		kdtThisEntry.getColumn("realEndDate").setWidth(140);
		kdtThisEntry.getColumn("intendEndDate").setWidth(140);
		// 7������img
		return img;
		
	}

	protected BufferedImage getUIIMG() {
		// // 1����ͼ����嵼��Ϊimg1
		// BufferedImage img1 = getImgAndTable();
		// int w1 = img1.getWidth();
		// int h1 = img1.getHeight();
		//
		// KDTSelectBlock curSelect = kdtNextEntrys.getSelectManager().get();
		// // ����������ʿ��(2�����صı߽�+����п�+�����п��)
		// int fitWidth = 2 + kdtNextEntrys.getIndexColumn().getWidth() +
		// kdtNextEntrys.getColumns().getWidth();
		// // ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		// int fitHeight = 2 + kdtNextEntrys.getHead().getHeight() +
		// kdtNextEntrys.getBody().getHeight();
		// // ���ѡ��
		// kdtNextEntrys.getSelectManager().remove();
		// // �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		// kdtNextEntrys.setSize(fitWidth, fitHeight);
		//
		// // 3������񵼳�Ϊimg2
		// int w2 = kdtNextEntrys.getWidth();
		// int h2 = kdtNextEntrys.getHeight();
		// BufferedImage img2 = new BufferedImage(w2, h2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img2.getGraphics();
		// kdtNextEntrys.paintAll(g);
		// g.dispose();
		//
		// // 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�
		// float scale = (float) w2 / w1;
		// w1 = w2;
		// h1 = (int) (h1 * scale);
		// BufferedImage img = new BufferedImage(w1, h1 + h2,
		// BufferedImage.TYPE_INT_RGB);
		// Image scaleImage = img1.getScaledInstance(w1, h1,
		// Image.SCALE_AREA_AVERAGING);
		// g = img.createGraphics();
		// g.drawImage(scaleImage, 0, 0, w1, h1, null);
		//
		// // 5����img2����img���·�
		// g.drawImage(img2, 0, h1, w2, h2, null);
		// g.dispose();
		// if (curSelect != null) {
		// kdtNextEntrys.getSelectManager().select(curSelect);
		// }
		//
		// // 7������img
		// return img;
		// 1����ͼ����嵼��Ϊimg1
		BufferedImage img1 = getImgAndTable();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();

		KDTSelectBlock curSelect = kdtNextEntrys.getSelectManager().get();
		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + kdtNextEntrys.getIndexColumn().getWidth() + kdtNextEntrys.getColumns().getWidth();
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + kdtNextEntrys.getHead().getHeight() + kdtNextEntrys.getBody().getHeight();
		// ���ѡ��
		kdtNextEntrys.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		kdtNextEntrys.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		// ����4�д�����������ͼƬ���Ⱥ� ���û�ȥ
		int printpre1 = kdtNextEntrys.getColumn(0).getWidth();
		int printpre2 = kdtNextEntrys.getColumn(1).getWidth();
		int printpre3 = kdtNextEntrys.getColumn(2).getWidth();
		int printpre4 = kdtNextEntrys.getColumn(3).getWidth();
		int printpre5 = kdtNextEntrys.getColumn(4).getWidth();
		kdtNextEntrys.getColumn(0).setWidth(180);
		kdtNextEntrys.getColumn(1).setWidth(180);
		kdtNextEntrys.getColumn(2).setWidth(180);
		kdtNextEntrys.getColumn(3).setWidth(180);
		kdtNextEntrys.getColumn(4).setWidth(200);
		int w2 = kdtNextEntrys.getWidth();
		int h2 = kdtNextEntrys.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		kdtNextEntrys.paintAll(g);
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
			kdtNextEntrys.getSelectManager().select(curSelect);
		}
		// ����4�д�����������ͼƬ���Ⱥ� ���û�ȥ
		kdtNextEntrys.getColumn(0).setWidth(printpre1);
		kdtNextEntrys.getColumn(1).setWidth(printpre2);
		kdtNextEntrys.getColumn(2).setWidth(printpre3);
		kdtNextEntrys.getColumn(3).setWidth(printpre4);
		kdtNextEntrys.getColumn(4).setWidth(printpre5);
		// 7������img
		return img;
	}

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
	 * ��ӡ
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * ��ӡԤ��
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}
}