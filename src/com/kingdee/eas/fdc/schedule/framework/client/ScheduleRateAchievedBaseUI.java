/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * ����ʷ���������ࣨ��״ͼ��
 * <p>
 * ������Ҫʵ�ֳ�ʼ����(initTree)����ʼ������Դ(createDataset)����ʼ��ͼ��(initChart��createChart)
 * �Լ���ʼ�����(initTable)���ܡ�<br>
 * 
 * @author emanon
 * 
 */
public class ScheduleRateAchievedBaseUI extends
		AbstractScheduleRateAchievedBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleRateAchievedBaseUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleRateAchievedBaseUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.checkParsed();
		initUI();
	}

	/**
	 * ��ѡ��ı䣬�������ͼ���Լ����
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		initChart();
		fillChart();
		initTable();
	}

	/**
	 * ���ѡ��ı䣬�������ͼ���Լ����
	 */
	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		fillChart();
		initTable();
	}

	/**
	 * ��ʼ������<br>
	 * ��Ϊ������<br>
	 * 1�������������⣬��ʼ����<br>
	 * 2����ʼ��������ͷ<br>
	 * 3������ͼ�������⣬��ʼ�����<br>
	 * 4�����ͼ��<br>
	 * 5����ʼ���������
	 * 
	 * @throws Exception
	 */
	protected void initUI() throws Exception {
		initTree();
		initHead();
		initChart();
		fillChart();
		initTable();
	}

	/**
	 * ��ʼ����<br>
	 * ���Թ���һ���ɱ����Ļ��߹�����Ŀ��
	 */
	protected void initTree() throws Exception {

	}

	/**
	 * ��ʼ��������ͷ<br>
	 */
	protected void initHead() throws Exception {

	}

	/**
	 * ��ʼ��ͼ�����<br>
	 * �����������������ֵ�����÷ָ�����������߶ȵ�<br>
	 * ע�����ͼ�����ݲ��ڴ����
	 */
	protected void initChart() {

	}

	/**
	 * ���ͼ��<br>
	 * һ�㲻�����ش˷�����ֻ������createChartPanel()��createDataset()����
	 */
	protected void fillChart() {
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
	}

	/**
	 * ��ʼ�����<br>
	 * ����������ݳ�ʼ���У���ӵ������������ۼ�������2�е�<br>
	 */
	protected void initTable() {

	}

	/**
	 * ����һ��ͼ�����<br>
	 * 
	 * @return
	 */
	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * ����ͼ�����������Դ
	 * 
	 * @return
	 */
	protected Dataset createDataset() {
		return null;
	}

	/**
	 * ����һ��ͼ��
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		return null;
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
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth()
				+ tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + tblMain.getHead().getHeight()
				+ tblMain.getBody().getHeight();
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
		BufferedImage img2 = new BufferedImage(w2, h2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2,
				BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1,
				Image.SCALE_AREA_AVERAGING);
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

}