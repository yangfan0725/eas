/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:���ȱ���������
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleReportBaseUI extends AbstractScheduleReportBaseUI {

	private static final long serialVersionUID = 827340447499844046L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportBaseUI.class);

	private CommonQueryDialog commonQueryDialog = null;
	
	// ��ʾ���ְ�ť
	private KDWorkButton isShowChartNumberBtn;

	// Ƕ���ڲ�ѯ�����е��Զ����ѯ����
	private ScheduleReportQueryPanelUI filterUI = null;
	
	// ����ͼ�ζ���
	private JFreeChart chart = null;
	
	// ����ͼ�δ�С
	private Dimension chartDimension = null;

	//	private static final int DIVIDE_SCALE = 4;

	private static final int MULTI_SCALE = 2;

	// ѡ�����֯�򹤳�
	private Map<String, Map<String, String>> selectAllOrgsMap;

	// ��ʼ����
	private Date startDate;

	// ��������
	private Date endDate;

	// ͼ�α���
	private String chartTitle = "";

	// ͼ������
	private String scheduleChartType;
	
	// ��ʾ����֯�򹤳���Ŀ����
	private String projectName;
	
	

	/**
	 * output class constructor
	 */
	public ScheduleReportBaseUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		initTableStyle();
		
		initPanelState();
		// �������ͼ�ν���
		buildTableAndChartPanel();

		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
	}
	
	private void initPanelState(){
		this.chartTable.setEditable(false);
		this.chartTable.setEnabled(false);
		this.exportToExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		
		isShowChartNumberBtn = new KDWorkButton("��ʾ����");
		this.isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_1"));
		isShowChartNumberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʾ���ֲ���
				showChartNumber();
			}
		});
		
		chartContainer.addButton(isShowChartNumberBtn);
	}

	/**
	 * ��������ʼ������ͼ�ν���
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	public void buildTableAndChartPanel() {

		// ��ȡ��ѡ��֯
		Map<String, Map<String, String>> allOrgsMap = getSelectedAllOrgsId();

		// ��ʼ���ںͽ�������
		Date startDate = getSelectedStartDate();
		Date endDate = getSelectedEndDate();
		String type = getScheduleType();

		// ���˺�Ľ�����Ŀ�����Ϣ
		Map<String, Map<String, String>> dataMap = getScheduleReportData(allOrgsMap, startDate, endDate, type);

		initReportPanel(dataMap);
	}

	/**
	 * ��������ʼ��������棬����ͼ�ν���ͱ�����
	 * @param dataMap ��Ŀ������Ϣ
	 * @Author��adward_huang
	 * @CreateTime��2012-9-13
	 */
	private void initReportPanel(Map<String, Map<String, String>> dataMap) {

		// Ĭ����5��ͳ����״ͼ
		int dataBarColumnSize = ScheduleChartHelper.CHART_CATEGORY_ROW_LIST.length;

		// Ĭ������ͳ������ͼ
		int dataLineColumnSize = ScheduleChartHelper.CHART_CATEGORY_RATE_LIST.length;

		// ��Ŀ����С
		int dataPrjSize = 0;
		if (dataMap != null && !dataMap.isEmpty()) {
			dataPrjSize = dataMap.size();
		}

		// ���ͼ�����ݣ�������
		// ��״ͼ����
		double[][] barData = new double[dataBarColumnSize][dataPrjSize];

		// ����ͼ����
		double[][] lineData = new double[dataLineColumnSize][dataPrjSize];

		// �����Ŀ����
		String[] columnKeys = new String[dataPrjSize];

		int index = 0;

		if (dataMap != null && !dataMap.isEmpty()) {
			this.chartTable.removeRows();
			// ��ѡ�нڵ㰴��֯��μ�������
			List<Map.Entry<String, Map<String, String>>> sortedMapValueList = ScheduleChartHelper.sortedMapBySelectOrgLevel(dataMap);

			List<Integer> plannedResult = new ArrayList<Integer>();
			for (Map.Entry<String, Map<String, String>> entry : sortedMapValueList) {
				Map<String, String> map = entry.getValue();

				// ��Map�����ݰ�����װ��List�������ȡ
				List<String> dataList = ScheduleChartHelper.getReportDataList(map);
				plannedResult.add(Integer.parseInt(dataList.get(2)));
				for (int i = 0; i < dataBarColumnSize; i++) {
					barData[i][index] = Double.parseDouble(dataList.get(i + 3));
				}
				for (int i = 0; i < dataLineColumnSize; i++) {
					lineData[i][index] = new BigDecimal(dataList.get(i + 8)).doubleValue();
				}
				columnKeys[index] = map.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);

				index++;

				// ��ʼ�����
				IRow row = this.chartTable.addRow();
				initTableData(dataList, row);
			}

			// ��X-AXIS���ƴ�����ͬ������½��й���
			ScheduleChartHelper.checkChartColumnDuplicateName(columnKeys);
			ScheduleChartHelper.sortedListByIntegerValue(plannedResult);
			
			int size = plannedResult.size() > 0 ? plannedResult.size() - 1: 0;
			// ��ʼ��ͼ�ν���
			initChartPanelData(columnKeys, barData, lineData,plannedResult.get(size));
		}
	}

	private void initTableStyle() {
		
		String[] colNames = new String[]{"plannedComp","timedComp","fifInComp","fifOutComp","unComp","confirmComp"};
		for(String col : colNames){
			setTableStyle(col);
		}
	}
	
	private void setTableStyle(String columnName){
		if(chartTable.getColumn(columnName) != null){
			StyleAttributes style= chartTable.getColumn(columnName).getStyleAttributes();
			style.setUnderline(true);
			style.setFontColor(Color.BLUE);
			style.setHorizontalAlign(HorizontalAlignment.CENTER);
		}
		
	}
	

	/**
	 * ��������ʼ�����
	 * @param dataMap	��Ŀ������Ϣ
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	private void initTableData(List<String> dataList, IRow row) {
		this.chartTable.checkParsed();
		row.getCell("id").setValue(dataList.get(0));
		row.getCell("projectName").setValue(dataList.get(1));
		row.getCell("plannedComp").setValue(dataList.get(2));
		row.getCell("timedComp").setValue(dataList.get(3));
		row.getCell("fifInComp").setValue(dataList.get(4));
		row.getCell("fifOutComp").setValue(dataList.get(5));
		row.getCell("unComp").setValue(dataList.get(6));
		row.getCell("confirmComp").setValue(dataList.get(7));
		String timedCompRate = new BigDecimal(dataList.get(8)).multiply(new BigDecimal(100)).setScale(MULTI_SCALE) + "%";
		row.getCell("timedCompRate").setValue(timedCompRate);
		String delayedCompRate = new BigDecimal(dataList.get(9)).multiply(new BigDecimal(100)).setScale(MULTI_SCALE) + "%";
		row.getCell("delayedCompRate").setValue(delayedCompRate);
	}

	/**
	 * ��������ʼ��ͼ�ν���
	 * @param dataMap	��Ŀ������Ϣ
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	private void initChartPanelData(String[] columnKeys, double[][] barData, double[][] lineData, Integer maxValue) {

		ScheduleChartHelper chartHelper = new ScheduleChartHelper();
		String title = getChartTitle();
		chart = chartHelper.getChartPanel(title, columnKeys, barData, lineData, maxValue);

		ChartPanel chartPanel = new ChartPanel(chart);
		
		// �Ƴ�ͼ�ν��������¼�
		EventListener[] listeners = chartPanel.getListeners(MouseListener.class);
		for(EventListener listener: listeners){
			
			chartPanel.removeMouseListener((MouseListener) listener);
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		chartDimension = new Dimension();
		chartDimension.setSize(width , height * 0.5);
		if (height < 864) {
			chartDimension.setSize(width, height - 440);
		}
		//		chartPanel.setSize(new Dimension(1166, 500));
		chartPanel.setSize(chartDimension);
		fillChart(chartPanel);
	}

	private void fillChart(JPanel chartPanel) {
		this.chartPanel.removeAll();
		this.chartPanel.add(chartPanel);
		updateUI();
	}

	/**
	* ��������ȡ���˺�Ľ�����Ŀ�������������������ʵ�ֻ�ȡͼ�����ݺͱ�����ݵĽӿ�
	 * @param orgIds	�ɲ�ѯ���洫�����ѡ��֯ID����
	 * @param startDate	�ɲ�ѯ���洫���������ڿ�ʼ����
	 * @param endDate   �ɲ�ѯ���洫���������ڽ�������
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-11
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Map<String, String>> getScheduleReportData(Map<String, Map<String, String>> orgIds, Date startDate, Date endDate,
			String type) {

		if (orgIds == null) {
			FDCMsgBox.showInfo("����δ������֯�����ܽ��и��ٲ�ѯ��");
			SysUtil.abort();
		}
		Map<String, Map<String, String>> dataMap = null;
		Map<String, Date> dates = new HashMap<String, Date>();
		dates.put(FDCScheduleConstant.REPORT_CHART_START_DATE, startDate);
		dates.put(FDCScheduleConstant.REPORT_CHART_END_DATE, endDate);
		try {
			dataMap = ScheduleReportFacadeFactory.getRemoteInstance().getScheduleReportData(orgIds, dates, type);
		} catch (EASBizException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}

		return dataMap;
	}

	/**
	* output actionQuery_actionPerformed
	*/
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (this.actionQuery.isVisible()) {
			getFilterUI(true);
			super.actionQuery_actionPerformed(e);
		}
	}

	/**
	 * output actionExportToExcel_actionPerformed
	 */
	public void actionExportToExcel_actionPerformed(ActionEvent e) throws Exception {

		if (getSelectedAllOrgsId() != null && !getSelectedAllOrgsId().isEmpty() && chart != null) {
			try {
				BufferedImage image = ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight());
				ScheduleChartHelper.exportToExcel(image, chartTable, getChartTitle());
			} catch (Exception ex) {
				MsgBox.showWarning(this, "����EXCELʧ�ܣ���ر���ѡ�񵼳���EXCEL�ļ������ԣ�");
			}
		} else {
			MsgBox.showWarning(this, "û�п��Ե��������ݣ���ѡ�����������������ԣ�");
			SysUtil.abort();
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportBaseUI#actionPrint_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {

		List<List<String>> tableData = ScheduleChartHelper.getTableListData(chartTable);
		if(chart == null || tableData == null || tableData.isEmpty()){
			MsgBox.showWarning("û������ͼ�����ݣ��뱣֤�������ݺ��ٴ�ӡ��");
			return;
		}
		
		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight()));
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataProvider, SwingUtilities.getWindowAncestor(this));

	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#actionPrintPreview_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(chartTable);
		if(chart == null || tableData == null || tableData.isEmpty()){
			MsgBox.showWarning("û������ͼ�����ݣ��뱣֤�������ݺ��ٴ�ӡԤ����");
			return;
		}
		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight()));
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataProvider, SwingUtilities.getWindowAncestor(this));

	}
	
	/**
	 * ��������ʾ��״ͼ������
	 * @Author��adward_huang
	 * @CreateTime��2012-10-24
	 */
	private void showChartNumber() {
		if (chart != null) {
			CategoryPlot plot = chart.getCategoryPlot();
			if (isShowChartNumberBtn.getText().equals("��ʾ����")) {
				plot.getRenderer().setBaseItemLabelsVisible(true);
				isShowChartNumberBtn.setText("��������");
				isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_2"));
			} else {
				plot.getRenderer().setBaseItemLabelsVisible(false);
				isShowChartNumberBtn.setText("��ʾ����");
				isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_1"));
			}
		}
	}

	/**
	 * ���������ò�ѯ��Χ����
	 * @param map
	 * @param startDate
	 * @param endDate
	 * @param chartTitle
	 * @param scheduleChartType
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	protected void setSelectorAllValue(Map<String, Map<String, String>> map, Date startDate, Date endDate, String projectName,
			String scheduleChartType) {

		if (map != null) {
			this.setSelectAllOrgsId(map);
		}
		if (startDate != null) {
			this.setSelectedStartDate(startDate);
		}
		if (endDate != null) {
			this.setSelectedEndDate(endDate);
		}
		if (projectName != null) {
			this.setProjectName(projectName);
		}
		if (scheduleChartType != null) {
			this.setScheduleType(scheduleChartType);
		}
		if (scheduleChartType != null) {
			this.setScheduleType(scheduleChartType);
		}
	}

	/**
	 * ��������ʼ����ѯ���壬�����Զ����ѯ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-17
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(450);
			commonQueryDialog.setTitle("������ѯ");
		}
		commonQueryDialog.addUserPanel(this.getFilterUI(false));
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI(boolean isUpdate) {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ScheduleReportQueryPanelUI();
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		this.filterUI.setScheduleReportBaseUI(this, isUpdate);
		return this.filterUI;
	}

	/**
	* @see com.kingdee.eas.framework.client.ListUI#initDefaultFilter()
	*/
	protected boolean initDefaultFilter() {
		return !FDCMsgBox.isHidedBox();
	}

	/**
	 * �������ṩ����Ľӿڣ��������ѡ��֯ID����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected Map<String, Map<String, String>> getSelectedAllOrgsId() {
		return selectAllOrgsMap;
	}

	private void setSelectAllOrgsId(Map<String, Map<String, String>> selectOrgsMap) {
		this.selectAllOrgsMap = new HashMap<String, Map<String, String>>();
		this.selectAllOrgsMap.putAll(selectOrgsMap);
	}

	/**
	 * �������ṩ����Ľӿڣ������������ڷ�Χ��ʼ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected Date getSelectedStartDate() {
		return startDate;
	}

	private void setSelectedStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * �������ṩ����Ľӿڣ������������ڷ�Χ��������
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected Date getSelectedEndDate() {
		return endDate;
	}

	private void setSelectedEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * �������ṩ����Ľӿڣ������ͳ��ָ������
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected String getScheduleType() {
		return scheduleChartType;
	}

	private void setScheduleType(String scheduleChartType) {
		this.scheduleChartType = scheduleChartType;
	}

	protected String getProjectName() {
		if(projectName != null && projectName.length() > 0){
			return projectName+ FDCScheduleConstant.ORG_PROJECT_NAME_SEPARATOR;
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	* �������ṩ����Ľӿڣ������ͼ�εı���
	* @return
	* @Author��adward_huang
	* @CreateTime��2012-9-12
	*/
	protected String getChartTitle() {
		return chartTitle;
	}

	/**
	 * ��������ͼ���ͼ��
	 * <p>
	 * ��ʱ���״�ֻ��Ҫ��ӡͼ��������ı��ʹ�����������Դ��ӡ<br>
	 * ��ʱ�״�ȡͼƬʹ�ô˷�����������getUIIMG()
	 *//*
	protected BufferedImage getChartIMG() {
		int height2 = chartContainer.getHeight();
		int width2 = chartContainer.getWidth();
		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		chartContainer.paintComponents(g);
		return img;
	}*/

	/**
	 * �����״�ģ��·��
	 * ����������
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/FDCSchedule";
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#getBizInterface()
	 */
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		/* TODO �Զ����ɷ������ */
		return null;
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#getEditUIName()
	 */
	@Override
	protected String getEditUIName() {
		/* TODO �Զ����ɷ������ */
		return null;
	}
	
	@Override
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
		uiContext.put("selectedOrgs", getSelectedAllOrgsId());
		uiContext.put("currentOrgId", this.chartTable.getCell(rowIndex, "id").getValue());
		uiContext.put("currentOrgName", this.chartTable.getCell(rowIndex, "projectName").getValue());
		uiContext.put("taskType", getScheduleType());
		uiContext.put("chartType", getScheduleType());
		uiContext.put("Owner", this);
		uiContext.put("state", chartTable.getColumnKey(columnIndex));//�ƻ���ɣ���ʱ��ɣ���ʱ��ɣ���ȷ��,δ���(����������)
		
		UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(detailUIName, uiContext).show();
	}
	
	
	

}