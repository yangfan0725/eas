/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ �ƻ����������б�༭����
 * 
 * @author ��С��
 * @version EAS7.0
 * @createDate 2011-08-04
 * @see
 */
public class ViewProjectMonthReportUI extends AbstractViewProjectMonthReportUI {

	/** ȱʡ�汾�� */
	private static final long serialVersionUID = 1L;

	/** ��־�ļ� */
	private static final Logger logger = CoreUIObject.getLogger(ViewProjectMonthReportUI.class);
	
	private Map paramMap = new HashMap();
	
	
	public Map getParamMap(){
		return this.paramMap;
	}

	/**
	 * output class constructor
	 */
	public ViewProjectMonthReportUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		// super.initTree();
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());

		TreeNode node = (TreeNode) this.treeMain.getModel().getRoot();
		addSpecial(node);
	}

	/**
	 * @discription <�򹤳���Ŀ���м���Ŀר��>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
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
	private void addSpecial(TreeNode node) throws Exception {

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (childAt.getChildCount() == 0 && childAt.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo proj = (CurProjectInfo) childAt.getUserObject();
				if (proj != null) {
					String id = proj.getId().toString();
					ProjectSpecialCollection col = getSpecial(id);
					for (Iterator iter = col.iterator(); iter.hasNext();) {
						ProjectSpecialInfo element = (ProjectSpecialInfo) iter.next();
						childAt.add(new DefaultKingdeeTreeNode(element));

					}
				}
			} else {
				addSpecial(childAt);

			}

		}
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportIMG_actionPerformed(e);
	}

	/**
	 * @discription <�����Ŀר���Map>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
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
	private ProjectSpecialCollection getSpecial(String projId) throws Exception {
		ProjectSpecialCollection coll = new ProjectSpecialCollection();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject.id", projId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		coll = ProjectSpecialFactory.getRemoteInstance().getProjectSpecialCollection(view);
		return coll;
	}

	public void onLoad() throws Exception {
		initDiplay();
		super.onLoad();
		nextTable.checkParsed();
		thisTable.checkParsed();
		thisTable.getColumn("taskName").setWidth(170);
		thisTable.getColumn("completePercent").setWidth(100);
		thisTable.getColumn("planEndDate").setWidth(120);
		thisTable.getColumn("realEndDate").setWidth(120);
		thisTable.getColumn("intendEndDate").setWidth(120);
		thisTable.getColumn("respPerson").setWidth(120);
		thisTable.getColumn("respDept").setWidth(120);

		nextTable.getColumn("taskName").setWidth(280);
		nextTable.getColumn("planStartDate").setWidth(250);
		nextTable.getColumn("planEndDate").setWidth(250);
		nextTable.getColumn("respPerson").setWidth(170);
		nextTable.getColumn("respDept").setWidth(170);
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
		
		this.kdtPanel.setTitleAt(0, "��������������");
		this.kdtPanel.setTitleAt(1, "��������ƻ�");
		
	}

	public void onShow() throws Exception {
		super.onShow();
		this.thisTable.getColumn("isComplete").getStyleAttributes().setHided(true);
		thisTable.getColumn("intendEndDate").setWidth(1);
		// ���õ���excelͼ��
		btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnExportExcel.setEnabled(true);
		btnExportIMG.setVisible(false);
		// �������ڵ�ȫչ��
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.sptTop.setDividerLocation(246);
		this.sptDown.setDividerLocation(1);
		this.sptDown.setEnabled(false);
		
	}

	/**
	 * @discription <��ʼ��ҳ����ʾ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
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
	public void initDiplay() {
		// �Ƴ�ͼ��
		pnlChart.removeAll();
		// �Ƴ��������
		thisTable.removeRows(false);
		nextTable.removeRows(false);
		this.sptTop.setDividerLocation(320);
	}

	protected String getTDPath() {

		return "/bim/fdc/process/processProjectbill";
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

		/*
		 * ��ǰ��ר��������
		 */
		getProjectMonthReportData(node);
		sortCBYear();
	}

	private void getProjectMonthReportData(DefaultKingdeeTreeNode node) {
		if (node == null || node.getUserObject() instanceof OrgStructureInfo)
			return;
		
		String projectSpecialId = null;
		String projectId = null;
		
		if(node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo){
			projectSpecialId = ((ProjectSpecialInfo)node.getUserObject()).getId().toString();
		}
		if(node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo){
			projectId = ((CurProjectInfo)node.getUserObject()).getId().toString();
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		// ��������
		sic.add(new SelectorItemInfo("startDate"));
		// �깤����
		sic.add(new SelectorItemInfo("endDate"));
		FilterInfo info = new FilterInfo();
		if (!StringUtils.isEmpty(projectSpecialId)) {
			info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSpecialId));
		} else {
			info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}
		
		if (!StringUtils.isEmpty(projectId)) {
			info.getFilterItems().add(new FilterItemInfo("project.id", projectId, CompareType.EQUALS));
		}
		
		SorterItemCollection soters = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("year");
		si.setSortType(SortType.DESCEND);
		soters.add(si);
		si = new SorterItemInfo("month");
		si.setSortType(SortType.DESCEND);
		soters.add(si);

		view.setFilter(info);
		view.setSorter(soters);
		ProjectMonthReportCollection col = null;
		Set itemSet = new HashSet();
		try {
			col = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
			if (col != null && col.size() > 0) {
				for (int i = 0; i < col.size(); i++) {
					ProjectMonthReportInfo monthReportInfo = col.get(i);
					int year = monthReportInfo.getYear();
					int month = monthReportInfo.getMonth();
					String str = year + "-" + month;
				    itemSet.add(str);
				}
			}
			
			cbYear.addItems(itemSet.toArray());
			
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	@Deprecated
	private void testExtract(DefaultKingdeeTreeNode node) {
		
		if (node != null && node.isLeaf() && node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
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
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", id));
				
				view.setFilter(info);
				ProjectMonthReportCollection col = null;
				try {
					col = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							ProjectMonthReportInfo monthReportInfo = col.get(i);
							int year = monthReportInfo.getYear();
							int month = monthReportInfo.getMonth();
							String str = year + "-" + month;
							if (year != 0 && month != 0) {
								boolean isAdd = true;
								for (int m = 0; m < cbYear.getItemCount(); m++) {
									String item = cbYear.getItemAt(m).toString();
									if (item.equals(str)) {
										isAdd = false;
									}
								}
								if (isAdd) {
									cbYear.addItem(str);
								}
							}
						}
					}
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
			}
		}else if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
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
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("project.id", id));
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
				
				view.setFilter(info);
				// cbYear.addItem("");
				ProjectMonthReportCollection col = null;
				try {
					col = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							ProjectMonthReportInfo monthReportInfo = col.get(i);
							int year = monthReportInfo.getYear();
							int month = monthReportInfo.getMonth();
							String str = year + "-" + month;
							if (year != 0 && month != 0) {
								boolean isAdd = true;
								for (int m = 0; m < cbYear.getItemCount(); m++) {
									String item = cbYear.getItemAt(m).toString();
									if (item.equals(str)) {
										isAdd = false;
									}
								}
								if (isAdd) {
									cbYear.addItem(str);
								}
							}
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
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

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		if (cbYear.getSelectedItem() != null && !"".equals(cbYear.getSelectedItem())) {
			initData();
		} else {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			this.cboReportState.removeAllItems();
			this.prmtReportPerson.setValue(null);
			this.dpReportTime.setValue(null);
		}
		pnlChart.updateUI();
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "��������", thisTable });
		tables.add(new Object[] { "��������", nextTable });
		// FDCTableHelper.exportExcel(this, tables);

		// Excel ������ҳǩ��ͬ,�����ͬ���޸�ҳǩ����-i
		int index = 2;
		for (int i = 0; i < tables.size(); i++) {
			Object objs[] = (Object[]) tables.get(i);
			String title = (String) objs[0];

			for (int j = i + 1; j < tables.size(); j++) {
				Object tempObjs[] = (Object[]) tables.get(j);
				String title1 = (String) tempObjs[0];
				if (title != null && title1 != null && title1.equals(title)) {
					title = title + "_" + index;
					index++;
					objs[0] = title;
					continue;
				}
			}

		}
		ExportManager exportM = new ExportManager();
		String path = null;
		File tempFile = File.createTempFile("eastemp", ".xls");
		path = tempFile.getCanonicalPath();
		KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[tables.size()];
		for (int i = 1; i < tables.size() + 1; i++) {
			Object objs[] = (Object[]) tables.get(i - 1);
			String title = (String) objs[0];
			KDTable table = (KDTable) objs[1];
			tablesVO[i - 1] = new KDTables2KDSBookVO(table);
			title = title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i - 1].setTableName(title);
		}
		KDSBook book = null;
		book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO, true, true);

		// book.addSheet(after, name);
		exportM.exportToExcel(book, path);

		// ������excel��
		try {
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		} catch (IOException e2) {
			// excel��ʧ�ܣ����浽ָ��λ��
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION) {
				File dest = fileChooser.getSelectedFile();
				try {
					// ��������ʱ�ļ���ָ��Ŀ��
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
				} catch (Exception e3) {
					this.handUIException(e3);
				}
			}
		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.cboReportState.removeAllItems();
		this.prmtReportPerson.setValue(null);
		this.dpReportTime.setValue(null);
		/* Ϊ����ѡ���ʼ������ */
		initCbYear();
		setDefalutYearSelected();
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
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		this.cboReportState.removeAllItems();
		this.prmtReportPerson.setValue(null);
		this.dpReportTime.setValue(null);

		/*
		 * ȡ������-��
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
		int year = 1990;
		int month = 1;
		if (!("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			month = Integer.parseInt(split[1]);
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("head.creator.*"));
		view.getSelector().add(new SelectorItemInfo("head.state"));
		view.getSelector().add(new SelectorItemInfo("head.createTime"));
		view.getSelector().add(new SelectorItemInfo("relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("relateTask.schedule.project.costcenter"));
		view.getSelector().add(new SelectorItemInfo("respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("head.project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("head.projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("head.year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("head.month", new Integer(month)));
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("head.project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("head.projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("head.year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("head.month", new Integer(month)));
		}
		
		
		
		if(paramMap.get("respPerson") != null){
			PersonInfo respPerson = (PersonInfo) paramMap.get("respPerson");
			filter.getFilterItems().add(new FilterItemInfo("respPerson",respPerson.getId().toString()));
		}
		if(paramMap.get("qualityPerson") != null){
			PersonInfo respPerson = (PersonInfo) paramMap.get("qualityPerson");
			filter.getFilterItems().add(new FilterItemInfo("quanlityPerson",respPerson.getName()));
		}
		if(paramMap.get("planPerson") != null){
			PersonInfo respPerson = (PersonInfo) paramMap.get("planPerson");
			filter.getFilterItems().add(new FilterItemInfo("planPerson",respPerson.getName()));
		}
		
		if(paramMap.get("startDate") != null){
			Date startDate = (Date) paramMap.get("startDate");
			filter.getFilterItems().add(new FilterItemInfo("relateTask.actualStartDate",FDCDateHelper.getSQLBegin(startDate),CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("relateTask.actualStartDate",FDCDateHelper.getSQLEnd(startDate),CompareType.LESS_EQUALS));
		}
		if(paramMap.get("endDate") != null){
			Date endDate = (Date) paramMap.get("endDate");
			filter.getFilterItems().add(new FilterItemInfo("realEndDate",FDCDateHelper.getSQLBegin(endDate),CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("realEndDate",FDCDateHelper.getSQLEnd(endDate),CompareType.LESS_EQUALS));
		}

		view.setFilter(filter);
		ProjectMonthReportEntryCollection collection = ProjectMonthReportEntryFactory.getRemoteInstance().getProjectMonthReportEntryCollection(view);
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();
		if (collection.size() > 0) {
			ProjectMonthReportInfo projectWeekReportInfo = collection.get(0).getHead();

			for (int i = 0; i < collection.size(); i++) {
				ProjectMonthReportEntryInfo entryInfo = collection.get(i);
				if (entryInfo.isIsNext()) {
					nextMonth.add(entryInfo);
				} else {
					currMonth.add(entryInfo);
				}
			}
			this.cboReportState.addItem(projectWeekReportInfo.getState());
			this.prmtReportPerson.setValue(projectWeekReportInfo.getCreator());
			this.dpReportTime.setValue(projectWeekReportInfo.getCreateTime());
		}

		loadThisTableFields(currMonth);
		loadNextTableFields(nextMonth);
		initChart(currMonth);
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
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());
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
	 * @throws BOSException
	 * @throws SQLException
	 * @see <��ص���>
	 */
	public void loadThisTableFields(List list) throws BOSException, SQLException {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		if (list.size() > 0) {// ���ϴ���0
			ProjectMonthReportEntryInfo entryInfo = null;
			entryInfo = (ProjectMonthReportEntryInfo) list.get(0);
			String costCenterId = entryInfo.getRelateTask().getSchedule().getProject().getCostCenter().getId().toString();
			DefaultKingdeeTreeNode node = getLastSelectedNode();
			boolean isParamControl = ViewReportHelper.isStartingParamControl(costCenterId, node.getUserObject());
			boolean isEvaluation = false;
			Set<String> evSet = null;
			FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
			for (int i = 0; i < list.size(); i++) {
				entryInfo = (ProjectMonthReportEntryInfo) list.get(i);
				cols.add(entryInfo.getRelateTask());
			}
			if (isParamControl) {
				evSet = FDCScheduleTaskStateHelper.isEvaluationed(null, cols);
			}
			
			Map<String, FDCScheduleTaskInfo> realTaskEntry = FDCScheduleTaskStateHelper.getNewstTasks(null, cols);
			for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
				isEvaluation = false;
				entryInfo = (ProjectMonthReportEntryInfo) list.get(i);
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				if (entryInfo.getCompletePrecent() == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
				}
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
				if (evSet != null && evSet.contains(entryInfo.getRelateTask().getSrcID())) {
					isEvaluation = true;
				}
				row.getCell("entryID").setValue(entryInfo.getId());
				
				row.getCell("quanlityPerson").setValue(entryInfo.getQuanlityPerson());
				row.getCell("qEvaluationDate").setValue(entryInfo.getQEvaluationDate());
				row.getCell("qEvaluationResult").setValue(entryInfo.getQEvaluationResult());
				
				row.getCell("planPerson").setValue(entryInfo.getPlanPerson());
				row.getCell("pEvaluationDate").setValue(entryInfo.getPEvaluationDate());
				row.getCell("pEvaluationResult").setValue(entryInfo.getPEvaluationResult());
				
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, realTaskEntry.get(entryInfo.getRelateTask().getSrcID()));
			}
		}
	}

	/**
	 * ����ͼƬ����
	 * 
	 * @return
	 */
	public String getChartTitle() {
		String title = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (null == node) {
			return "";
		}
		/*
		 * ȡ������-��
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
		int year = 1990;
		int month = 1;
		if (null != selectedItem && !("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			month = Integer.parseInt(split[1]);
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
			if (null == curProjectInfo.getName() || "".equals(curProjectInfo.getName())) {
				try {
					CurProjectInfo curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(curProjectInfo.getId()));
					title = year + "��" + month + "��" + curProject.getName() + "��Ŀ�±�";
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else {
				title = year + "��" + month + "��" + curProjectInfo.getName() + "��Ŀ�±�";
			}
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
			title = year + "��" + month + "��" + cpinfo.getName() + "��Ŀ�±�";
		}

		view.setFilter(filter);
		try {
			ProjectMonthReportCollection collection = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
			List currMonth = new ArrayList();
			List nextMonth = new ArrayList();
			if (collection.size() > 0) {
				ProjectMonthReportInfo projectWeekReportInfo = collection.get(0);
				ProjectMonthReportEntryCollection entrysColl = projectWeekReportInfo.getEntrys();

				for (int i = 0; i < entrysColl.size(); i++) {
					ProjectMonthReportEntryInfo entryInfo = entrysColl.get(i);
					if (entryInfo.isIsNext()) {
						nextMonth.add(entryInfo);
					} else {
						currMonth.add(entryInfo);
					}
				}
			}

			if (currMonth.size() > 0) {
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

				String str = year + "��" + month + "��������" + currMonth.size() + "��,���а�ʱ���" + finished + "���ʱ���" + delayed + "���ȷ��" + affirm
						+ "���ʱ��δ���" + unfinished + "�ִ����" + excudeing + "�" + "�ƻ���������" + nextMonth.size() + "���������״̬�ֲ������±�״ͼ��";
				Font font = new Font(str, Font.BOLD + Font.ITALIC, 13);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = year + "��" + month + "��������" + currMonth.size() + "��,���а�ʱ���0���ʱ���0���ȷ��0���ʱ��δ���0��" + "����ʱ�����0%," + "���ƻ���������" + nextMonth.size() + "���������״̬�ֲ������±�״ͼ��";
				Font font = new Font(str, Font.BOLD + Font.ITALIC, 13);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return "";
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

	/**
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		return super.createChart(dataset);
	}

	protected void initChart(List thisMonth) {
		// pnlChart.removeAll();
		pnlChart.add(createChartPanel(thisMonth), BorderLayout.CENTER);
	}

	/**
	 * @description ����һ��ͼ�����
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected JPanel createChartPanel(List thisMonth) {
		JFreeChart chart = createChart(createDataset(thisMonth));
		return new ChartPanel(chart);
	}

	/**
	 * @description ����ͼ�����������Դ
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected Dataset createDataset(List thisMonth) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisMonth.size() > 0) {
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

			double fin = Double.parseDouble(String.valueOf(finished));
			double dely = Double.parseDouble(String.valueOf(delayed));
			double aff = Double.parseDouble(String.valueOf(affirm));
			double unfin = Double.parseDouble(String.valueOf(unfinished));
			double excude = Double.parseDouble(String.valueOf(excudeing));
			double total = fin + dely + aff + unfin + excude;

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
			if (affirm == thisMonth.size()) {
				result.setValue("��ȷ��", total);
			} else {
				result.setValue("��ȷ��", aff);
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
		}
		return result;
	}
	protected String getUIName() {
		return ViewProjectMonthReportUI.class.getName();
	}
	@Override
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
		
		UIContext uiContext = new UIContext();
		uiContext.put("parentWindow", this);
		if(!paramMap.isEmpty()){
			uiContext.put("paramMap", paramMap);
		}
		IUIWindow ui =UIFactory.createUIFactory(UIFactoryName.MODEL).create(ReportConditionFilterUI.class.getName(), uiContext);
		ui.show();
	}
}