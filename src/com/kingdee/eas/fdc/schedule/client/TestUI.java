/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.InputMethodEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.CaretEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.dm.CategoryFactory;
import com.kingdee.eas.cp.dm.CategoryInfo;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.eas.fdc.basedata.util.FDCCPDMUtil;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class TestUI extends AbstractTestUI {
	private static final Logger logger = CoreUIObject.getLogger(TestUI.class);

	FDCImageAttUtil imgUtil = null;

	/**
	 * output class constructor
	 */
	public TestUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.checkParsed();
		actionUpload.setEnabled(true);
		actionViewAtt.setEnabled(true);
		ctTable.addButton(btnUpload);
		ctTable.addButton(btnViewAtt);
		// ҵ�񵥾�ID��һ��ΪeditData.getID().toString()���˴�����ʹ��һ���Ѵ��ڵĺ�ͬID
		String editDataID = "AMDLw/uVQEGwurP+Ycnp54Qj/24=";
		// onLoad��ʹ��ҵ�񵥾�ID���������࣬������������ʹ��
		imgUtil = new FDCImageAttUtil(this, editDataID, null, true);

		// ȡ������ͼƬ�����б�����ʾ��tblMain��
		initTable();
		// ctTable.setTitle("��������");
		// ctTable.setTitleStyle(KDContainer.STATICSTYLE);
		// ctTable.setTitleLength(250);

		// ((TitleLabel) ((JPanel) ctTable.getComponents()[0]).getComponent(1))
		// .add(new JLabel("test"));
		// ((BigArrowButton) ((JPanel)
		// ctTable.getComponents()[0]).getComponent(0))
		// .add(new JLabel("test"));
		// ((JPanel) ctTable.getComponents()[0]).add(new JLabel("test"));
		tblMain.fireTableClick(0, 1, 1);
		tblMain.getSelectManager().select(0, 0, 0, 0);
	}

	public void actionUpload_actionPerformed(ActionEvent e) throws Exception {
		// imgUtil.uploadIMGAtt();
		// initTable();
		// uploadCP();
		String destBillEditUIClassName = Test2UI.class.getName();
		Map map = new UIContext(this);
		// map.put("srcBillID", srcBillInfo.getId().toString());
		map.put(UIContext.OWNER, this);
		// map.put("srcBillBOSTypeString", destBillInfo.getBOSType());
		IUIWindow uiWindow = null;
		// UIFactoryName.MODEL Ϊ����ģʽ
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				destBillEditUIClassName, map, null, OprtState.VIEW);

		// ��ʼչ��UI
		uiWindow.show();

	}

	protected void uploadCP() throws IOException {
		try {
			CategoryInfo category = CategoryFactory.getRemoteInstance()
					.getCategoryInfo(
							new ObjectUuidPK("xrVQ2BQ2T+ShIpGje1B/3MNdkBQ="));
			ContractBillInfo bizInfo = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(
							new ObjectUuidPK("LHgHIp1HQGmY5xf3CAAYGQ1t0fQ="));
			ArchiveDocumentParamsInfo params = FDCCPDMUtil.createParams(
					bizInfo, bizInfo.getName(), "a", category, null);
			FDCCPDMUtil.upLoadFileToCP(params, new File(
					"C:\\Documents and Settings\\emanon\\����\\��֧.txt"));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	protected void download() {
		try {
			FDCCPDMUtil.downLoadFileFromCP("avIDVWwQQiCQ+IPhTScIyPO3pDE=",
					"��֧.txt");
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	protected void add() {
		File file = new File(
				"C:/Documents and Settings/emanon/����/ArchiveDocumentParamsInfo.java");
		try {
			FDCCPDMUtil.addFileToCP("avIDVWwQQiCQ+IPhTScIyPO3pDE=", file);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	protected void view() {
		// try {
		// Desktop.getDesktop().open(
		// new File("C:/Documents and Settings/emanon/����/33.xls"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	private void initTable() {
		tblMain.removeRows();
		List atts = imgUtil.getImgAtts();
		for (int i = 0; i < atts.size(); i++) {
			AttachmentInfo att = (AttachmentInfo) atts.get(i);
			IRow row = tblMain.addRow();
			row.getCell("attID").setValue(att.getId().toString());
			row.getCell("attName").setValue(att.getName());
		}
	}

	/**
	 * ��ʾ
	 */
	public void actionViewAtt_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			String attID = (String) row.getCell("attID").getValue();
			// imgUtil.setIMGByAttID(attID, pnlIMG);
			// ImageIcon img =
			// imgUtil.getImageFromFile(imgUtil.downIMGAtt(attID));
			// row.getCell("attName").setValue(img);
		}
		int height2 = tblMain.getHeight();
		int width2 = tblMain.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		tblMain.paint(g);
		// Image img = tblMain.createVolatileImage(tblMain.getWidth(), tblMain
		// .getHeight());
		JLabel lb = new JLabel();
		Dimension maximumSize = new Dimension(width2, height2);
		lb.setSize(maximumSize);
		lb.setMaximumSize(maximumSize);
		lb.setPreferredSize(maximumSize);
		lb.setOpaque(true);
		lb.setIcon(new ImageIcon(img));
		pnlIMG.removeAll();
		pnlIMG.add(lb);
		pnlIMG.updateUI();
		// initStatePanel();
		// download();
		// add();
		view();
	}

	public void initStatePanel() {
		pnlIMG.removeAll();
		KDTaskStatePanel pnlState = new KDTaskStatePanel(new String[] { "����һ",
				"���Զ�" }, new String[] { "��", "��" }, new Color[] { Color.RED,
				Color.BLUE }, new boolean[] { true, false });
		// KDTaskStatePanel pnlState = new KDTaskStatePanel();
		pnlState.setBounds(0, 0, pnlState.getWidth(), pnlState.getHeight());
		pnlIMG.add(pnlState);
		pnlIMG.updateUI();
	}

	/**
	 * �ڱ�������һ��������taskState����ֵΪ0-3��int������ֵ��<br>
	 * 0��ʾ����ʱ��ɡ���1��ʾ����ʱ��ɡ���2��ʾ��δ�ﵽ������ڡ���3��ʾ����ʱδ��ɡ���<br>
	 * չʾ��ʱ��ѭ������У����ݸ������е�ֵ��������ʾ��stateLogo�е�չʾ��ʽ��<br>
	 * 
	 * ע�⣺<br>
	 * 1���򹳵ĵ�Ԫ����Ҫ��������Ϊ���壬����̫ϸ����Ȧ������Ҫ��<br>
	 * 2����ɫδʹ�ñ�׼��Color.Green��Color.Orange����ʹ�����¹����Ľ�����ɫ���棬<br>
	 * ����Ϊ��ɫ̫�������״��ۣ�����ʹ��ʱ��������ȡ�
	 */
	public void initStateCell() {
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

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row != null) {
				int state = ((Integer) row.getCell("taskState").getValue())
						.intValue();
				switch (state) {
				case 0:
					row.getCell("stateLogo").setValue(achieve);
					row.getCell("stateLogo").getStyleAttributes().setBold(true);
					row.getCell("stateLogo").getStyleAttributes().setFontColor(
							green);
					break;
				case 1:
					row.getCell("stateLogo").setValue(achieve);
					row.getCell("stateLogo").getStyleAttributes().setBold(true);
					row.getCell("stateLogo").getStyleAttributes().setFontColor(
							red);
					break;
				case 2:
					row.getCell("stateLogo").setValue(pending);
					row.getCell("stateLogo").getStyleAttributes().setFontColor(
							orange);
					break;
				case 3:
					row.getCell("stateLogo").setValue(pending);
					row.getCell("stateLogo").getStyleAttributes().setFontColor(
							red);
					break;
				}
			}
		}
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}
	
	protected void kDTextField1_caretPositionChanged(InputMethodEvent e)
			throws Exception {
		
	}

	protected void kDTextField1_propertyChange(PropertyChangeEvent e)
			throws Exception {
	}

	protected void kDTextField1_vetoableChange(PropertyChangeEvent e)
			throws Exception {
	}

	protected void kDTextField1_actionPerformed(ActionEvent e) throws Exception {
	}

	protected void kDTextField1_focusLost(FocusEvent e) throws Exception {
		System.out.println(1);
	}

	protected void kDTextField1_hierarchyChanged(HierarchyEvent e)
			throws Exception {
		System.out.println(2);
	}

	protected void kDTextField1_caretUpdate(CaretEvent e) throws Exception {
		System.out.println(3);
	}

	protected void kDTextField1_componentHidden(ComponentEvent e)
			throws Exception {
		System.out.println(4);
	}

	protected void kDTextField1_componentRemoved(ContainerEvent e)
			throws Exception {
		System.out.println(5);
	}

	protected void kDTextField1_inputMethodTextChanged(InputMethodEvent e)
			throws Exception {
		System.out.println(6);
	}
}