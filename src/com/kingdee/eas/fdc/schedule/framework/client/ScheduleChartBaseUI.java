/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.freechart.chart.encoders.SunJPEGEncoderAdapter;
import com.kingdee.bos.ctrl.freechart.util.PngEncoder;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.util.client.EASResource;

/**
 * ����ͼ�������
 * <p>
 * ������һ������������ͼ�����±��Ŀ��<br>
 * ʵ���״򡢱���ͼƬ�ȹ���
 * 
 * @author emanon
 * 
 */
public class ScheduleChartBaseUI extends AbstractScheduleChartBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleChartBaseUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleChartBaseUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUIState();
	}

	public void onShow() throws Exception {
		super.onShow();
		sptTop.setDividerLocation(getChartHeight());
	}

	/**
	 * ��ӡ
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(),
				getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * ��ӡԤ��
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(),
				getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * ����ͼƬ���ļ�
	 * <p>
	 * ֧�ֵ���Ϊjpg����png<br>
	 * ��������ļ���δ������׺ʱ��ȡѡ��ĺ�׺<br>
	 * 
	 * һ�㲻����д�˷�������дgetUIIMG()����ͼƬ����
	 */
	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		// ��ѡ
		fc.setMultiSelectionEnabled(false);
		// 2�ָ�ʽ
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("jpg",
				new String[] { "jpg" });
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png",
				new String[] { "png" });
		fc.setFileFilter(jpgFilter);
		fc.setFileFilter(pngFilter);
		int retVal = fc.showSaveDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}
		File chooseFile = fc.getSelectedFile();
		// �õ�ͼƬ
		BufferedImage img = getUIIMG();
		if (img == null) {
			return;
		}
		String imgName = chooseFile.getName().trim().toLowerCase();
		String newFile = chooseFile.getParent() + "\\" + imgName;
		// �ж�ѡ����jpg����png
		if (fc.getFileFilter().equals(jpgFilter)) {
			if (!imgName.endsWith("jpg")) {
				newFile += ".jpg";
			}
			File saveIMG = new File(newFile);
			// д��jpg
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			new SunJPEGEncoderAdapter().encode(img, fos);
			bos.close();
		} else {
			if (!imgName.endsWith("png")) {
				newFile += ".png";
			}
			File saveIMG = new File(newFile);
			// д��png
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] pngEncode = new PngEncoder(img).pngEncode();
			bos.write(pngEncode);
			bos.close();
		}
	}

	/**
	 * �����״����
	 * <p>
	 * ����������
	 * 
	 * @return
	 */
	protected String getTDName() {
		return "����ͼ��";
	}

	/**
	 * �����״�ģ��·��
	 * <p>
	 * ����������
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/rate";
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
		int height2 = sptTop.getHeight();
		int width2 = sptTop.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		sptTop.paintComponents(g);
		return img;
	}

	/**
	 * ��������ͼ���ͼ��
	 * <p>
	 * ��ʱ���״�ֻ��Ҫ��ӡͼ��������ı��ʹ�����������Դ��ӡ<br>
	 * ��ʱ�״�ȡͼƬʹ�ô˷�����������getUIIMG()
	 */
	protected BufferedImage getChartIMG() {
		int height2 = pnlChart.getHeight();
		int width2 = pnlChart.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		pnlChart.paintComponents(g);
		return img;
	}

	/**
	 * ��ѡ��ı�
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

	}

	/**
	 * ���ѡ��ı�
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
	}

	/**
	 * ���ý���״̬
	 */
	protected void initUIState() {
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		menuItemPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		menuItemPrintPre.setIcon(EASResource.getIcon("imgTbtn_preview"));
		btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_chart"));
		menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_chart"));

		actionPrint.setEnabled(true);
		actionPrintPreview.setEnabled(true);
		actionExportIMG.setEnabled(true);
	}

	/**
	 * ͼ��ĸ߶�<br>
	 * һ���Ǿ�����һ�㣬ʹ�õ�ǰҳ��߶ȼ�ȥ�±��߶�<br>
	 * ��onshow�е��ã���Ϊonloadʱ��ҳ��߶Ȼ�û��ȷ����ֻ��Ĭ�ϸ߶�
	 * 
	 * @return
	 */
	protected int getChartHeight() {
		return getHeight() - 150;
	}

}