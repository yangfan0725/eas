/*
 * @(#)EASTest.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartFrame;
import com.kingdee.bos.ctrl.freechart.chart.ChartUtilities;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.axis.AxisLocation;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryLabelPositions;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.encoders.EncoderUtil;
import com.kingdee.bos.ctrl.freechart.chart.encoders.ImageFormat;
import com.kingdee.bos.ctrl.freechart.chart.labels.ItemLabelAnchor;
import com.kingdee.bos.ctrl.freechart.chart.labels.ItemLabelPosition;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryItemLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.DatasetRenderingOrder;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.LineAndShapeRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.StackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.DatasetUtilities;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.freechart.ui.TextAnchor;
import com.kingdee.bos.ctrl.freechart.util.ArrayUtilities;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.common.json.simple.JSONObject;
import com.kingdee.eas.cp.common.json.simple.JSONValue;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgCollection;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgFactory;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;

/**
 * ����:���ȱ���ͼ�ΰ�����
 * @author adward_huang  date:2012-9-13
 * @version EAS6.1
 */
public class ScheduleChartHelper {

	private static final long serialVersionUID = -7839731547156290L;
	
	private static final Logger logger = CoreUIObject.getLogger(ScheduleChartHelper.class);

	// ������ɫ-����ɫ
	public static final Color CHART_TITLE_COLOR = new Color(153, 153, 255);
	public static final Font CHART_TITLE_FONT = new Font("����", Font.BOLD, 28);

	// ��״ͼ��ɫ
	public static final Color CHART_TIMED_FINISHED_COLOR = new Color(0, 176, 80);// ��ʱ�����ɫ--��ɫ
	public static final Color CHART_15IN_TIME_FINISHED_COLOR = new Color(255, 255, 0);//  �ӳ�15���������ɫ--��ɫ
	public static final Color CHART_15OUTER_TIME_FINISHED_COLOR = new Color(217, 150, 148);// �ӳ�15���������ɫ--ǳ��ɫ
	public static final Color CHART_NO_TIME_FINISHED_COLOR = new Color(255, 0, 0);// δ�����ɫ--��ɫ
	public static final Color CHART_UNCOMFIRMED_TIME_FINISHED_COLOR = new Color(153, 0, 153);// ��ȷ����ɫ--����ɫ

	// ����ͼ��ɫ
	public static final Color CHART_TIMED_FINISHED_COLOR_RATE = new Color(0, 255, 42);// ��ʱ�����--��ɫ
	public static final Color CHART_DEFERED_FINISHED_COLOR_RATE = new Color(255, 127, 42);// ��ʱ�������ɫ--����ɫ

	// ͼ�η�������
	public static final String[] CHART_CATEGORY_ROW_LIST = { "��ʱ���", "�ӳ�15�������", "�ӳ�15�������", "δ���", "��ȷ��" };
	public static final String[] CHART_CATEGORY_RATE_LIST = { "��ʱ�����", "�ӳ������" };
	
	// ����chartͼ�εĸ߶ȣ��Ե�Ԫ��߶�Ϊ��λ��
	private static final int CHART_HEIGHT = 32;
	
	// ���������п��
	private static final int EXPORT_COLUMN_WIDTH = 19;
	
	private static final int CHART_Y_AXIS_GAP = 10;
	
	private static final String NUMBER_SEPRATOR = "!";
	

	/**
	 * ������������״ͼ,����ͼ ���ݼ�
	 * @param data
	 * @param rowKeys
	 * @param columnKeys
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-10
	 */
	public static CategoryDataset getChartDataset(double[][] data, String[] rowKeys, String[] columnKeys) {
		
		return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
	}

	/** 
	* ��ȡ��״ͼ�������ݼ� 
	*/
	public CategoryDataset getBarDataset(double[][] data, String[] columnKeys) {
		CategoryDataset dataset = getChartDataset(data, ScheduleChartHelper.CHART_CATEGORY_ROW_LIST, columnKeys);
		return dataset;
	}

	/** 
	* ��ȡ����ͼ�������ݼ� 
	*/
	public CategoryDataset getLineDataset(double[][] data, String[] columnKeys) {
		CategoryDataset dataset = getChartDataset(data, ScheduleChartHelper.CHART_CATEGORY_RATE_LIST, columnKeys);
		return dataset;
	}

	/** 
	* ��ȡ����ͼ�������ݼ� 
	*/
	public PieDataset getPieDataset(double[] data) {
		PieDataset dataset = getPieDataset(data, ScheduleChartHelper.CHART_CATEGORY_ROW_LIST);
		return dataset;
	}
	
	/** 
	* ��ȡ��״ͼ�������ݼ� 
	*/
	public PieDataset getPieDataset(double[] data, String[] rowKeys) {
		if (data != null && rowKeys != null) {
			if (data.length == rowKeys.length) {
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < data.length; i++) {
					dataset.setValue(rowKeys[i], data[i]);
				}
				return dataset;
			}
		}
		return null;
	}

	/**
	 * �������ṩ����ӿڣ�����ͼ�ν���
	 * @param data
	 * @param columnKeys
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-13
	 */
	public JFreeChart getChartPanel(String chartTitle, String[] columnKeys, double[][] data, double[][] lineData,int max) {

		CategoryDataset barDataset = getBarDataset(data, columnKeys);
		CategoryDataset lineDataset = getLineDataset(lineData, columnKeys);
		JFreeChart chart = null;
		
		// ������ȼƻ�����Ŀ������1��������״ͼ����ͼ����Ϸ�ʽ��ʾ
		if (columnKeys != null && columnKeys.length > 1) {
			chart = createBarChart(barDataset, chartTitle, lineDataset,max);
		} else {
			// ������ȼƻ�����Ŀ��Ϊ1ʱ�����Ա�״ͼ��ʾ
			if (data != null) {
				int len = data.length;
				double[] value = new double[len];
				for(int i = 0; i< len; i++){
					value[i] = data[i][0];
				}
				PieDataset pieDataset = getPieDataset(value);
				chart = createPieChart(pieDataset, chartTitle, CHART_CATEGORY_ROW_LIST);
			}
		}
		return chart;
	}

	/**
	 * ����ͼ��JfreeChart����
	 * @param dataset ���ݼ�
	 * @param title ͼ�����
	 * @return ͼ��JFreeChart
	 */
	private JFreeChart createBarChart(CategoryDataset barDataset, String title, CategoryDataset lineDataset, int max) {

		//��ChartFactory�����������Chart����
		JFreeChart chart = ChartFactory.createStackedBarChart3D(title, //ͼ�����
				"",//�����ǩ 
				"",//�����ǩ 
				barDataset, //���ݼ�
				PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ, 
				true,//�Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false) 
				true, //�Ƕ���ʾtooltip
				false //�Ƿ�����URL����
				);

		// ����chart����
		setBarChart(chart, max);

		// �������ͼ��
		addLineChartDataset(chart, lineDataset);

		return chart;
	}

	/**
	  * ����ͼ������
	  * @param chart ��Ҫ���õ�ͼ��
	  * @return JFreeChart���úõ�ͼ��
	  */
	private void setBarChart(JFreeChart chart, int max) {
		//������������ľ����Ⱦ
		//chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); 

		// ����Title��Legend������(��Ȼ������)
		chart.getTitle().setFont(CHART_TITLE_FONT);
		chart.getTitle().setPaint(CHART_TITLE_COLOR);
		chart.getLegend().setItemFont(new Font("����", Font.CENTER_BASELINE, 10));

		// ���ͼ���������
		CategoryPlot plot = chart.getCategoryPlot();

		//���ú����߿ɼ�
		plot.setRangeGridlinesVisible(true);
		// ��������ɫ��
		plot.setRangeGridlinePaint(Color.gray);
		// ������ͼ����ɫ
		plot.setBackgroundPaint(Color.white);
		// �����ݺ��������ʾλ��
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		// �����ᾫ��
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		
		// ���ÿ̶ȱ����0��ʼ 
		vn.setAutoRangeIncludesZero(true);
		
		// ���������ݱ�ǩ����ʾ��ʽ
		DecimalFormat df = new DecimalFormat("#0");
		vn.setNumberFormatOverride(df);

		plot.setRangeAxis(vn);
		// ����ͼ��ĺ��ᣨX�ᣩ
		CategoryAxis domainAxis = plot.getDomainAxis();
		this.setXDomainAxis(domainAxis);

		// ����ͼ������ᣨY�ᣩ
//		ValueAxis valueAxis = plot.getRangeAxis();
		NumberAxis valueAxis = (NumberAxis)plot.getRangeAxis();
		this.setYDomainAxis(valueAxis, max);

		// ����ͼ�����ɫ
		this.setBarRender(plot);
	}

	/**
	 * ��������ԭ��ͼ������������ͼ�Σ�����������ͼ���������
	 * @param chart		��״ͼchart����
	 * @Author��adward_huang
	 * @CreateTime��2012-9-7
	 */
	private void addLineChartDataset(JFreeChart chart, CategoryDataset lineDataset) {

		CategoryPlot localCategoryPlot = (CategoryPlot) chart.getPlot();
		localCategoryPlot.setDataset(1, lineDataset);
		localCategoryPlot.mapDatasetToRangeAxis(1, 1);
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		// ����X-AXIS������б45����ʾ
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis localNumberAxis = new NumberAxis();
		// �������ֵ��1  
		localNumberAxis.setUpperBound(1);
		// ������ʾ��ʽ�ǰٷֱ�  
		DecimalFormat df = new DecimalFormat("0.00%");
		localNumberAxis.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ  
		
		localCategoryPlot.setRangeAxis(1, localNumberAxis);
		
		LineAndShapeRenderer localLineAndShapeRenderer = new LineAndShapeRenderer();
		localLineAndShapeRenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		localCategoryPlot.setRenderer(1, localLineAndShapeRenderer);
		localCategoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		this.setLineRenderColor(localLineAndShapeRenderer, getLineColors());
	}

	/**
	 * ��X�������
	 * 
	 * @param domainAxis
	 */
	private void setXDomainAxis(CategoryAxis domainAxis) {

		Font labelFont = new Font("����", Font.CENTER_BASELINE, 10);
//		domainAxis.setLowerMargin(0.1);// ���þ���ͼƬ��˾����ʱΪ10%
//		domainAxis.setUpperMargin(0.1);// ���þ���ͼƬ�Ҷ˾����ʱΪ�ٷ�֮10

		domainAxis.setLabelFont(labelFont);// X�����
		domainAxis.setTickLabelFont(labelFont);// X����ֵ

		domainAxis.setCategoryLabelPositionOffset(10);// ͼ��������ǩ�ľ���(10����)
//		domainAxis.setCategoryMargin(0.1);// �����ǩ֮��ľ���20%

		domainAxis.setMaximumCategoryLabelLines(1);
		domainAxis.setMaximumCategoryLabelWidthRatio(0);

		domainAxis.setLabelFont(labelFont);// �����
		domainAxis.setTickLabelFont(labelFont);// ����ֵ

		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// �����ϵ� Lable �Ƿ�������ʾ
	}

	/**
	  * ��Y�������
	  *
	  * @param ValueAxis
	  */
	private void setYDomainAxis(NumberAxis valueAxis, int max) {
		Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);

		valueAxis.setUpperMargin(0.1);// ������ߵ�һ������ͼƬ���˵ľ���(�������10%)
		valueAxis.setLowerMargin(0.15);// ������͵�һ�� Item ��ͼƬ�׶˵ľ���

		valueAxis.setLabelFont(labelFont);// Y�����
		valueAxis.setTickLabelFont(labelFont);// Y����ֵ
		// Lableˮƽ��ʾ
		valueAxis.setLabelAngle(1.5);
		valueAxis.setLowerBound(0);
		
		double gap = max / CHART_Y_AXIS_GAP;
		if(max <= CHART_Y_AXIS_GAP){
			gap = 1D;
		}
		double max2 = max + 1 * gap;
		valueAxis.setUpperBound(max2);
		valueAxis.setTickUnit(new NumberTickUnit(gap));// ������������ֵ��С
	}

	/**
	 * ������״ͼ����ɫ(��Ⱦͼ��) 
	 * ÿ�����ӵ���ϸ����(��ά����)
	 * 
	 * @param categoryPlot
	 */
	private void setBarRender(CategoryPlot plot) {

		//BarRenderer3D renderer = new BarRenderer3D(); //��״��Ⱦ��  ÿһ����ʾΪһ������
		StackedBarRenderer renderer = new StackedBarRenderer();//��״��Ⱦ��  ����������һ����״ͼ����ʾ
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
		renderer.setBaseOutlineStroke(new BasicStroke(0.3F));
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

		// ���÷�����״ͼ��ɫ
		setBarRenderColor(renderer, getBarColors());

		renderer.setItemMargin(0.1);// �������Ӽ��Ϊ����10%

		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ����������(3D�Ĳ��谡��䣬2D�������Ǿ�����)OUTSIDE3,9
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelPaint(Color.BLUE);// ������ֵ��ɫ��Ĭ�Ϻ�ɫ

		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelFont((new Font("����", Font.CENTER_BASELINE, 10)));// 12�ź���

		renderer.setBaseItemLabelPaint(Color.black);// ����Ϊ��ɫ
//		renderer.setBaseItemLabelsVisible(true);// ��״ͼ���Ƿ���ʾ����

		// �������ӿ��
		renderer.setMaximumBarWidth(0.06);
		// �������Ӹ߶�
		renderer.setMinimumBarLength(0.2);
		
		plot.setRenderer(renderer);
	}

	/**
	 * ������������״ͼ������ɫ
	 * @param renderer
	 * @param barColors
	 * @Author��adward_huang
	 * @CreateTime��2012-9-10
	 */
	private void setBarRenderColor(StackedBarRenderer renderer, List<Color> barColors) {
		Paint p1 = new GradientPaint(0.0f, 0.0f, barColors.get(0), 0.0f, 0.0f, new Color(189, 183, 89));
		renderer.setSeriesPaint(0, p1);// ����������ɫ

		Paint p2 = new GradientPaint(0.0f, 0.0f, barColors.get(1), 0.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);// ����������ɫ

		Paint p3 = new GradientPaint(0.0f, 0.0f, barColors.get(2), 0.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(2, p3);// ����������ɫ

		Paint p4 = new GradientPaint(0.0f, 0.0f, barColors.get(3), 0.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);// ����������ɫ

		Paint p5 = new GradientPaint(0.0f, 0.0f, barColors.get(4), 0.0f, 0.0f, new Color(155, 45, 45));
		renderer.setSeriesPaint(4, p5);// ����������ɫ
	}

	/**
	 * ��������������ͼ��ɫ
	 * @param renderer
	 * @param barColors
	 * @Author��adward_huang
	 * @CreateTime��2012-9-10
	 */
	private void setLineRenderColor(LineAndShapeRenderer renderer, List<Color> lineColors) {

		renderer.setBaseOutlinePaint(Color.red);// �߿���ɫ
		renderer.setSeriesPaint(0, lineColors.get(0));// ����������ɫ

		renderer.setSeriesOutlinePaint(1, Color.BLACK);// �߿���ɫ
		renderer.setSeriesPaint(1, lineColors.get(1));// ����������ɫ

	}

	/**
	 * ��������ȡ��״ͼ��ɫ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-10
	 */
	private List<Color> getBarColors() {

		List<Color> colorList = new ArrayList<Color>();
		// ��״ͼ��ɫ
		colorList.add(CHART_TIMED_FINISHED_COLOR);// ��ʱ�����ɫ--��ɫ
		colorList.add(CHART_15IN_TIME_FINISHED_COLOR);//  �ӳ�15���������ɫ--��ɫ
		colorList.add(CHART_15OUTER_TIME_FINISHED_COLOR);// �ӳ�15���������ɫ--ǳ��ɫ
		colorList.add(CHART_NO_TIME_FINISHED_COLOR);// δ�����ɫ--��ɫ
		colorList.add(CHART_UNCOMFIRMED_TIME_FINISHED_COLOR);// ��ȷ����ɫ--��ɫ

		return colorList;
	}

	/**
	 * ��������ȡ����ͼ��ɫ����
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-10
	 */
	private List<Color> getLineColors() {

		List<Color> colorList = new ArrayList<Color>();
		// ����ͼ��ɫ
		colorList.add(CHART_TIMED_FINISHED_COLOR_RATE);// ��ʱ�����--��ɫ
		colorList.add(CHART_DEFERED_FINISHED_COLOR_RATE);// ��ʱ�������ɫ--����ɫ

		return colorList;
	}

	/**
	 * �ڴ�������ʾ���ɵ�ͼ��
	 * @param chart ��Ҫ��ʾ��ͼ��
	 * @param frameTitle �������
	 */
	@SuppressWarnings("unused")
	private void displayChart(JFreeChart chart, String frameTitle) {
		// �����ɵı���ŵ�Ԥ��������
		ChartFrame preview = new ChartFrame(frameTitle, chart);
		preview.pack();
		preview.setLocationRelativeTo(null);
		preview.setVisible(true);
	}

	/** 
	 * ��״ͼ 
	 *  
	 * @param dataset 
	 *            ���ݼ� 
	 * @param chartTitle 
	 *            ͼ���� 
	 * @param charName 
	 *            ����ͼ������ 
	 * @param pieKeys 
	 *            �ֱ������ּ� 
	 * @return 
	 */
	public static JFreeChart createPieChart(PieDataset dataset, String chartTitle, String[] pieKeys) {
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart  
				// title  
				dataset,// data  
				true,// include legend  
				true, false);

		chart.setTextAntiAlias(false);
		// ����ͼ�����������������title  
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(CHART_TITLE_FONT);
		title.setPaint(CHART_TITLE_COLOR);
		chart.setTitle(title);
		chart.getLegend().setItemFont(new Font("����", Font.CENTER_BASELINE, 10));

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ  

		// ָ����ͼ�����ߵ���ɫ  
		// plot.setBaseSectionOutlinePaint(Color.BLACK);  
		// plot.setBaseSectionPaint(Color.BLACK);  

		// ����������ʱ����Ϣ  
		plot.setNoDataMessage("�޶�Ӧ�����ݣ������²�ѯ!");

		// ����������ʱ����Ϣ��ʾ��ɫ  
		plot.setNoDataMessagePaint(Color.red);

		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ  
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat(
				"0.00%")));
		// ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����  
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));

		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));

		// ָ��ͼƬ��͸����(0.0-1.0)  
		plot.setForegroundAlpha(1f);
		// ָ����ʾ�ı�ͼ��Բ��(false)����Բ��(true)  
		plot.setCircular(false, true);

		// ���õ�һ�� ����section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���  
		plot.setStartAngle(90);

		LegendTitle legend = chart.getLegend(0);
		legend.setItemFont(new Font("����", Font.BOLD, 14));

		// // ���÷ֱ���ɫ  
		plot.setSectionPaint(pieKeys[0], CHART_TIMED_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[1], CHART_15IN_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[2], CHART_15OUTER_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[3], CHART_NO_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[4], CHART_UNCOMFIRMED_TIME_FINISHED_COLOR);

		return chart;
	}
	
	/**
	 * ������ͼ�κ��������������ͬ���֣������Ϊ���(1)�����(2)��
	 * @param columnNames
	 * @Author��adward_huang
	 * @CreateTime��2012-9-20
	 */
	@SuppressWarnings("unchecked")
	public static void checkChartColumnDuplicateName(String[] columnNames){
		
		if(columnNames == null){
			return;
		}
		List<String> nameList = new ArrayList<String>();
		if(ArrayUtilities.hasDuplicateItems(columnNames)){
			int len = columnNames.length;
			 for (int i = 0; i < len; i++) {
				for (int j = 0; j < i; j++) {
					final Object o1 = columnNames[i];
					final Object o2 = columnNames[j];
					if (o1 != null && o2 != null) {
						if (o1.equals(o2)) {
							nameList.add(o1.toString());
						}
					}
				}
			}
		}
		List<String> list = Arrays.asList(columnNames);
		
		if(nameList.size() > 0){
			Map<String,Integer> map = new HashMap<String,Integer>();
			
			for(int i = 0; i< list.size();i++){
				if(nameList.contains(list.get(i))){
					String name = columnNames[i];
					Integer index = 1;
					if (map.containsKey(name)) {
						index = map.get(name);
						map.put(name, index+1);
					} else {
						map.put(name, index);
					}
					columnNames[i] = name + "("+(map.get(name))+")";
				}
			}
		}
	}
	
	/**
	 * ��������Map���͵����ݷ�װΪJSON��ʽ�����ݣ���Ҫ��CustomerParams�в����ݼ����������
	 * @param data
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-27
	 */
	@SuppressWarnings("unchecked")
	public static String getMapToString(Map<String, Map<String, String>> data) {
		JSONObject json = new JSONObject();
		if(data != null && !data.isEmpty()){
			Iterator<Map.Entry<String, Map<String, String>>> iter = data.entrySet().iterator();
			Map<String,String> map = null;
			while(iter.hasNext()){
				Map.Entry<String, Map<String, String>> entry = iter.next();
				map = entry.getValue();
				JSONObject json2 = new JSONObject();
				for(Iterator<Map.Entry<String, String>> iter2 = map.entrySet().iterator();iter2.hasNext();){
					Map.Entry<String, String> resultMap = iter2.next();
					String key = resultMap.getKey();
					String value = resultMap.getValue();
					json2.put(key, value);
				}
				json.put(entry.getKey(), json2);
			}
		}
		return json.toString();
	}
	
	/**
	 * ��������JSON��ʽ������ת��ΪMAP��������
	 * @param data
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-27
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getStringToMap(String data){
		try {
			JSONObject json = (JSONObject) JSONValue.parse(data);
			Map<String, Map<String, String>> maps = (Map<String, Map<String, String>>) json;
			return maps;
		} catch (Exception e) {
			logger.error("��ѯ�������ݻ�ȡʧ�ܣ���ɾ���÷������ٴ�����"+e.getMessage());
		}
		return new HashMap<String, Map<String, String>>();
	}
	
	/**
	 * ��������ͼƬ�ͱ��ͳһ������EXCEL
	 * @param panel
	 * @param table
	 * @param title
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @Author��adward_huang
	 * @CreateTime��2012-9-29
	 */
	public static void exportToExcel(JComponent panel, KDTable table, String title) throws RowsExceededException, WriteException, IOException{
		// ��ȡchartͼ��
		BufferedImage chartImage = ScheduleChartHelper.getChartIMG(panel);
		
		// ��ȡ����ͷ����
		List<String> headerData = ScheduleChartHelper.getTableHeaderData(table);
		
		// ��ȡ�������
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(table);
		
		
		ScheduleChartHelper.exportExcelSave(chartImage,headerData,tableData,title);
	}
	
	/**
	 * ��������ͼƬ�ͱ��ͳһ������EXCEL
	 * @param panel
	 * @param table
	 * @param title
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @Author��adward_huang
	 * @CreateTime��2012-9-29
	 */
	public static void exportToExcel(BufferedImage chartImage, KDTable table, String title) throws RowsExceededException, WriteException, IOException{
		
		// ��ȡ����ͷ����
		List<String> headerData = ScheduleChartHelper.getTableHeaderData(table);
		
		// ��ȡ�������
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(table);
		
		
		ScheduleChartHelper.exportExcelSave(chartImage,headerData,tableData,title);
	}
	
	/**
	 * ��������ȡ�������
	 * @param chartTable
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-28
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getTableHeaderData(KDTable chartTable) {
		List<String> headList = new ArrayList<String>();
		
		List list = chartTable.getHead().getRow(0).getCells();
		for(int i = 1;i<list.size();i++){
			KDTCell cell = (KDTCell)list.get(i);
			headList.add(cell.getValue().toString());
			
		}
		
		return headList;
	}
	
	/**
	 * ��������ȡ�������
	 * @param chartTable
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-28
	 */
	public static List<List<String>> getTableListData(KDTable chartTable) {
		List<List<String>> tableList = new ArrayList<List<String>>();
		int columnCount = chartTable.getColumnCount();
		int rowCount = chartTable.getRowCount();

		List<String> rowList = null;
		for (int i = 0; i < rowCount; i++) {
			rowList = new ArrayList<String>();
			for (int j = 1; j < columnCount; j++) {
				Object obj = chartTable.getCell(i, j).getValue();
				String value = obj == null ? "" : obj.toString();
				rowList.add(value);
			}
			tableList.add(rowList);
		}
		return tableList;
	}
	
	/**
	 * ������������EXCEL
	 * @param bufferedImage
	 * @param header
	 * @param data
	 * @param title
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @Author��adward_huang
	 * @CreateTime��2012-9-28
	 */
	public static void exportToExcel(BufferedImage bufferedImage, List<String> header, 
			List<List<String>> data,String title, File file) 
					throws IOException, RowsExceededException, WriteException{
		
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet(title, 0);
			
			int width = 0;
			if(header != null && !header.isEmpty()){
				width = header.size();
			}
			
			WritableImage writableImage = new WritableImage(0,0,width,CHART_HEIGHT,getImageFile(bufferedImage));
			Label label = null;
			for(int i = 0;i< width;i++){
				label = new Label(i,CHART_HEIGHT ,header.get(i),getChartTableHeadFormat());
				sheet.addCell(label);
			}
			
			sheet.addImage(writableImage);
			int columnSize = 0;
			if(data != null && !data.isEmpty()){
				int rows = data.size();
				for(int i = 0;i< rows ;i++){
					List<String> rowList = data.get(i);
					if(rowList != null && rowList.size() > columnSize){
						columnSize = rowList.size();
					}
					for(int j =0; j< rowList.size();j++){
						sheet.setColumnView(j, EXPORT_COLUMN_WIDTH); // �����еĿ��
						label = new Label(j, CHART_HEIGHT + i + 1, rowList.get(j));
						sheet.addCell(label);
					}
				}
			}
			book.write();
		}finally{
			book.close();
		}
		
	}
	
	/**
	 * ���������ñ�ͷ���弰��ɫ
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-26
	 */
	public static CellFormat getChartTableHeadFormat(){
		//��Ӵ���������ɫ,��������ɫ Formatting�Ķ���    
		WritableFont font = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE); // ���ö��뷽ʽ
			format.setBackground(Colour.LIGHT_ORANGE);
			
		} catch (WriteException e) {
			logger.error(e);
		}
		return format;
	}
	
	/**
	* ��ͼ���浽ָ����·��
	* @param filePath ͼƬ�ļ�����·��
	* @param chart ��Ҫ�����ͼ��
	* @param width ͼƬ�Ŀ��
	* @param height ͼƬ�߶�
	 * @throws IOException 
	*/
	public static BufferedImage getChartIMG(JFreeChart chart, int width, int height) throws IOException {
		
		String path = getTempFilePath(".png");
		File file = new File(path);
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		} catch (Exception e) {
			logger.error(e);
		}
		return ImageIO.read(file);
	}
	
	/**
	 * ��������ͼ���ͼ��
	 */
	public static BufferedImage getChartIMG(JComponent panel) {
		int height2 = panel.getHeight();
		int width2 = panel.getWidth();
		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		panel.paintComponents(g);
		return img;
	}
	
	/**
	 * ��������ȡͼ���ļ�
	 * @param image
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 * @Author��adward_huang
	 * @CreateTime��2012-9-28
	 */
	public static File getImageFile(BufferedImage image) throws IOException {
		File file = new File(getTempFilePath(".png"));
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));

			EncoderUtil.writeBufferedImage(image, ImageFormat.JPEG, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return file;
	}
	
	/** 
     * ��ʱ�����������ֵ�excel���͡�������ѡ�񲿷ֵ�excel��
     * �ı��洦��
     * 
     * @param isBlockOut �Ƿ�ֻ����ѡ���
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
     */
    public static void exportExcelSave(BufferedImage chartImage, List<String> headerData, List<List<String>> tableData, String title) throws RowsExceededException, WriteException, IOException {

		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new FileExtensionFilter(".xls", ".xls"));
		int result = chooser.showSaveDialog(null);

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		File saveFile = null;
		String filePath = null;

		File selectedFile = chooser.getSelectedFile();

		//ѡ����������û�������ļ�����Ҳ��������׺
		String selectedFileName = selectedFile.getName();
		//���ú�׺Ϊ.xls
		String saveFileName = addExtension(selectedFileName, ".xls");
		if (saveFileName == null) {
			return;
		}
		saveFile = new File(selectedFile.getParentFile(), saveFileName);
		filePath = saveFile.getAbsolutePath();

		//��������Դ�����
		String sureCover = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Exists");
		while (saveFile.exists()) {

			int value = JOptionPane.showConfirmDialog(null, filePath + sureCover, "", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);

			// ������ǡ����滻��ԭ��ͬ�����ļ�
			if (value == 0) {
				exportToExcel(chartImage, headerData, tableData, title, saveFile);
				return;
			}
			// ������񡱣����������ļ���
			else {
				chooser = new JFileChooser();
				chooser.setSelectedFile(saveFile);
				chooser.addChoosableFileFilter(new FileExtensionFilter(".xls", ".xls"));
				result = chooser.showSaveDialog(null);

				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				saveFile = chooser.getSelectedFile();

				saveFileName = addExtension(saveFile.getName(), ".xls");
				saveFile = new File(saveFile.getParentFile(), saveFileName);
				filePath = saveFile.getAbsolutePath();
			}
		}

		exportToExcel(chartImage, headerData, tableData, title, saveFile);
		
	}
    
    /**
	 * ����������ѡ��Ľڵ㰴��֯�ṹ��δ�С������
	 * @param checkedNodeMap
	 * @Author��adward_huang
	 * @CreateTime��2012-10-9
	 */
	public static List<Map.Entry<String, Map<String, String>>> sortedMapBySelectOrgLevel(Map<String, Map<String, String>> checkedNodeMap){
		
		if(checkedNodeMap == null || checkedNodeMap.isEmpty()){
			return null;
		}
		
		// ��MAPת��ΪLIST��������
		List<Map.Entry<String, Map<String, String>>> mapList = new ArrayList<Map.Entry<String, Map<String, String>>>(checkedNodeMap.entrySet());
		
		//ͨ���Ƚ���ʵ�ֱȽ����� 
		Collections.sort(mapList, new Comparator<Map.Entry<String, Map<String, String>>>() {
			public int compare(Map.Entry<String, Map<String, String>> mapping1, Map.Entry<String, Map<String, String>> mapping2) {
				int result = 0;
				try {
					String sortNumber1 = mapping1.getValue().get(FDCScheduleConstant.CHART_SELECT_SORTED_ORG);
					String sortNumber2 = mapping2.getValue().get(FDCScheduleConstant.CHART_SELECT_SORTED_ORG);
					result = getCompareResultBySortNumber(sortNumber1, sortNumber2);
				} catch (Exception e) {
					result = 0;// �˴���ֹ�������Ϊ�ջ��߳��ַ����ֵ�ʱ���쳣����
					logger.error("method: sortedMapBySelectOrgLevel ����Ϊ�ջ���levelΪ�����ֵ��쳣��" + e.getMessage());
				}
				return  result;
			}
		});
		
		return mapList;
	}
	
	 /**
	 * ����������ѡ��Ľڵ㰴��֯�ṹ��δ�С������
	 * @param checkedNodeMap
	 * @Author��adward_huang
	 * @CreateTime��2012-10-9
	 */
	public static List<Map.Entry<String, DefaultKingdeeTreeNode>> sortedMapByLevel(Map<String, DefaultKingdeeTreeNode> checkedNodeMap){
		
		if(checkedNodeMap == null || checkedNodeMap.isEmpty()){
			return null;
		}
		
		// ��MAPת��ΪLIST��������
		List<Map.Entry<String, DefaultKingdeeTreeNode>> mapList = new ArrayList<Map.Entry<String, DefaultKingdeeTreeNode>>(checkedNodeMap.entrySet());
		
		//ͨ���Ƚ���ʵ�ֱȽ����� 
		Collections.sort(mapList, new Comparator<Map.Entry<String, DefaultKingdeeTreeNode>>() {
			public int compare(Map.Entry<String, DefaultKingdeeTreeNode> mapping1, Map.Entry<String, DefaultKingdeeTreeNode> mapping2) {
				int result = 0;
				try {
					Integer level1 = mapping1.getValue().getLevel();
					Integer level2 = mapping2.getValue().getLevel();
					if (level1 != null && level2 != null) {
						result = level1.compareTo(level2);
					}
				} catch (Exception e) {
					result = 0;// �˴���ֹ�������Ϊ�ջ��߳��ַ����ֵ�ʱ���쳣����
					logger.error("method: sortedMapByLevel ����Ϊ�ջ���levelΪ�����ֵ��쳣��" + e.getMessage());
				}
				return result;
			}
		});
		
		return mapList;
	}
	
	/**
	 * ���������ճ��������֯�ṹ�����й���
	 * @param orgCollection
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-13
	 */
	@SuppressWarnings("unchecked")
	public static void sortedListBySortNumber(List<ScheduleReportOrgInfo> childrenOrgs) {
		
		Collections.sort(childrenOrgs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScheduleReportOrgInfo source = (ScheduleReportOrgInfo) arg0;
				ScheduleReportOrgInfo target = (ScheduleReportOrgInfo) arg1;
				int result = 0;
				if (source.getLongNumber() != null && target.getLongNumber() != null) {
					// �����û�������˳��������νڵ�ļ���
					if(source.getLevel() == target.getLevel()){
						try{
						// �����String.compareTo���Ƚϻ�������
						result = new Integer(source.getSortNumber()).compareTo(new Integer(target.getSortNumber()));
						}catch(Exception e){
							result = 0;// �˴���ֹ�������Ϊ�ջ��߳��ַ����ֵ�ʱ���쳣����
							logger.error("method: sortedListBySortNumber ����Ϊ�ջ���sortNumberΪ�����ֵ��쳣��"+e.getMessage());
						}
					}
				}
				return result;
			}
		});
	}
	
	/**
	 * ��������ȡ�ڵ����������
	 * @param node
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-11-8
	 */
	public static String getSortedNodeNumber(DefaultKingdeeTreeNode node) {

		String sortedNumber = "";
		if (node == null) {
			return sortedNumber;
		}
		ScheduleReportOrgInfo orgInfo = (ScheduleReportOrgInfo) node.getUserObject();
		sortedNumber = orgInfo.getSortNumber() + "";

		if (node.getParent() != null) {
			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) node.getParent();
			ScheduleReportOrgInfo parentOrg = (ScheduleReportOrgInfo) parentNode.getUserObject();
			sortedNumber = parentOrg.getSortNumber() + NUMBER_SEPRATOR + sortedNumber;
			while (parentNode.getParent() != null) {
				parentNode = (DefaultKingdeeTreeNode) parentNode.getParent();
				parentOrg = (ScheduleReportOrgInfo) parentNode.getUserObject();
				sortedNumber = parentOrg.getSortNumber() + NUMBER_SEPRATOR + sortedNumber;
			}
		}

		return sortedNumber;
	}
	
	/**
	 * ���������������������
	 * @param value1
	 * @param value2
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-11-8
	 */
	public static int getCompareResultBySortNumber(String value1,String value2){
		
		if(value1 == null || value2 == null){
			return 0;
		}
		String[] valueA = value1.split(NUMBER_SEPRATOR);
		String[] valueB = value2.split(NUMBER_SEPRATOR);
		if(valueA.length < valueB.length){
			return -1;
		}
		if(valueA.length > valueB.length){
			return 1;
		}
		
		int len = valueA.length;
		int result = 0;
		for (int i = 0; i < len; i++) {
			if (!valueA[i].equals(valueB[i])) {
				try {
					Integer number1 = new Integer(valueA[i]);
					Integer number2 = new Integer(valueB[i]);
					if(number1 > number2){
						return 1;
					}else if(number1 < number2){
						return -1;
					}else{
						return 0;
					}
				} catch (Exception e) {
					result = 0;
					logger.error("method: getCompareResultBySortNumber �������Ϊ�����ֵ��쳣��" + e.getMessage());
				}
			}
		}
		return result;
	}
	
	/**
	 * ����������List���Ͻ�������
	 * @param checkedNodeMap
	 * @Author��adward_huang
	 * @CreateTime��2012-10-9
	 */
	@SuppressWarnings("unchecked")
	public static void sortedListByIntegerValue(List<Integer> list){
		
		if(list == null || list.isEmpty()){
			return ;
		}
		
		//ͨ���Ƚ���ʵ�ֱȽ����� 
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				int result = 0;
				try {
					Integer level1 = Integer.parseInt(o1.toString());
					Integer level2 = Integer.parseInt(o2.toString());
					if (level1 != null && level2 != null) {
						result = level1.compareTo(level2);
					}
				} catch (Exception e) {
					result = 0;// �˴���ֹ�������Ϊ�ջ��߳��ַ����ֵ�ʱ���쳣����
					logger.error("method: sortedListByIntegerValue valueΪ�����ֵ��쳣��" + e.getMessage());
				}
				return result;
			}
			
		});
	}
	
	/**
	 * ��������ȡ���ڵ���Ϣ
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	public static ScheduleReportOrgInfo getRootNodeInfo() throws EASBizException, BOSException{
		String rootSQL = "select id,name,number,longnumber,level,relateOrg.id,sortnumber,remark from where relateOrg.id='"
			+ FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID + "'";
		ScheduleReportOrgInfo rootInfo = ScheduleReportOrgFactory.getRemoteInstance().getScheduleReportOrgInfo(rootSQL);
		return rootInfo;
	}
	
	/**
	 * ���������ݸ��ڵ������ȡ�������ڵ�
	 * @param node
	 * @throws BOSException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-16
	 */
	@SuppressWarnings("unchecked")
	public static List<ScheduleReportOrgInfo> getAllTreeNodeData(ScheduleReportOrgInfo orgInfo) throws BOSException {
		String childrenSQL = "select id, name,number,longnumber,sortnumber,level,remark,parent.id,parent.name,parent.number,parent.longnumber,parent.level,relateOrg.id,relateOrg.name from "
				+ "where longnumber like '" + orgInfo.getLongNumber() + "!%' ";
		ScheduleReportOrgCollection orgColl = ScheduleReportOrgFactory.getRemoteInstance().getScheduleReportOrgCollection(childrenSQL);
		List<ScheduleReportOrgInfo> list = new ArrayList<ScheduleReportOrgInfo>();
		for(Iterator iter = orgColl.iterator();iter.hasNext();){
			list.add((ScheduleReportOrgInfo)iter.next());
		}
		return list;
	}
	
	/**
	 * ��������list����ת��ΪMap����
	 * @param list
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-25
	 */
	public static Map<String, ScheduleReportOrgInfo> getAllDataMap(List<ScheduleReportOrgInfo> list){
		Map<String, ScheduleReportOrgInfo> dataMap = new HashMap<String, ScheduleReportOrgInfo>();
		if(list == null){
			return dataMap;
		}
		for(ScheduleReportOrgInfo info: list){
			dataMap.put(info.getId().toString(), info);
		}
		return dataMap;
	}
	
	/**
	 * ��������ȡ�����ӽڵ���Ϣ��һ�μ��أ�������Զ�̷���
	 * @param node
	 * @param allTreeNodeData
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	public static List<ScheduleReportOrgInfo> getChildrenOrgs(ScheduleReportOrgInfo orgInfo, List<ScheduleReportOrgInfo> orderedTreeNodeList) {
		List<ScheduleReportOrgInfo> childrenList = new ArrayList<ScheduleReportOrgInfo>();
		for (ScheduleReportOrgInfo child: orderedTreeNodeList) {
			if (child.getParent() != null && child.getParent().getId().toString().equals(orgInfo.getId().toString())) {
				childrenList.add(child);
			}
		}
		return childrenList;
	}
    
    /**
     * ��������ȡ��ʱ�ļ���·��
     * @return
     * @Author��adward_huang
     * @CreateTime��2012-9-28
     */
    public static String getTempFilePath(String suffix) {
		String tempPath = "";
		try {
			tempPath = File.createTempFile("tmp", suffix).getAbsolutePath();
		} catch (IOException ex) {
			tempPath = Math.random() * Math.random() + "";
		}
		return tempPath;
	}
    
    
    /**
     * Ϊ�û������ѡ����ļ�����չ��
     * @param fileName �ļ���
     * @param ext	   ��չ��	
     * @return	����չ������ļ���
     */
    public static String addExtension(String fileName, String ext){
    	
		if(fileName != null ){
			int dotIndex = fileName.indexOf(".");
			if(dotIndex>= 0){
				fileName = fileName.substring(0, dotIndex)+ ext;
			}
			else{
				fileName += ext;
			}
		}		
		
		return fileName;
    }
    
    /**
     * ���������˷���ĸ�����ֵ��ַ���
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @Author��adward_huang
     * @CreateTime��2012-10-13
     */
    public static String filtNumberAndLetter(String str) throws PatternSyntaxException {
		// ֻ������ĸ������        
		 String regEx= "[^a-zA-Z0-9]";                      
		// ��������������ַ�   
//		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

    /**
     * ��������ȡ�������ݼ���
     * @param map
     * @return
     * @Author��adward_huang
     * @CreateTime��2012-10-17
     */
    public static List<String> getReportDataList(Map<String, String> map) {

		String id = map.get(FDCScheduleConstant.REPORT_CHART_ID);
		String projectName = map.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);
		String plannedComp = map.get(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP);
		String timedComp = map.get(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP);
		String fifInComp = map.get(FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP);
		String fifOutComp = map.get(FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP);
		String unComp = map.get(FDCScheduleConstant.REPORT_CHART_UNCOMP);
		String confirmedComp = map.get(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP);
		String timedCompRate = map.get(FDCScheduleConstant.REPORT_CHART_TIMED_COMP_RATE);
		String delayedCompRate = map.get(FDCScheduleConstant.REPORT_CHART_DELAYED_COMP_RATE);

		List<String> dataList = new ArrayList<String>();
		dataList.add(nullStr(id));
		dataList.add(nullStr(projectName));
		dataList.add(nullNumberStr(plannedComp));
		dataList.add(nullNumberStr(timedComp));
		dataList.add(nullNumberStr(fifInComp));
		dataList.add(nullNumberStr(fifOutComp));
		dataList.add(nullNumberStr(unComp));
		dataList.add(nullNumberStr(confirmedComp));
		dataList.add(nullNumberStr(timedCompRate));
		dataList.add(nullNumberStr(delayedCompRate));
		return dataList;
	}
	
	/**
	 * ����������ֵת��Ϊ""
	 * @param str
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	public static String nullStr(String str){
		if(str == null){
			return "";
		}
		return str;
	}
	
	/**
	 * ����������ֵת��Ϊ0
	 * @param str
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	public static String nullNumberStr(String str){
		if(str == null){
			return "0";
		}
		return str;
	}
    
    private static class FileExtensionFilter extends FileFilter {

    	protected String ext;
    	protected String description;

    	public FileExtensionFilter(String ext, String description) {
    	   this.ext = ext.toLowerCase();
    	   this.description = description;
    	}

    	public boolean accept(File file) {
    	   if (file.isDirectory()) {
    	    return true;
    	   }

    	   if (file.getName().toLowerCase().endsWith(ext)) {
    	    return true;
    	   }

    	   return false;
    	}
    	public String getDescription() {
    		   return description;
    		}
    }
}
