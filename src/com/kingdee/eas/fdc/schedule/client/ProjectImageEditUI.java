/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.util.PngEncoder;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDImageIcon;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.ProjectStatusEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.image.ScaleFilter;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectImageEditUI extends AbstractProjectImageEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectImageEditUI.class);

	private Map scollPanes = new HashMap();
	private Map imagePanles = new HashMap();
	private Map imageLabels = new HashMap();
	private int zoo = 0;
	private int zooNum = 30;
	private int nowAngle = 0;

	// tabIndex �ϼ�
	private int tabIndex = 0;

	// ��¼���ǰ��
	protected int curIndex = -1;

	FDCImageAttUtil imageUtil = null;

	/**
	 * output class constructor
	 */
	public ProjectImageEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		/*
		 * �ж��Ƿ��ǹؼ�֤��
		 */
		if (KeyCert.isSelected()) {
			editData.setIsKeyCert(true);
		} else {
			editData.setIsKeyCert(false);
		}

		if (getOprtState().equals(OprtState.ADDNEW)) {
			String num = "01";
			CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
			if (prj == null && editData != null && editData.getProject() != null) {
				prj = editData.getProject();
			}

			try {
				num = getNumber(prj);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.number.setText(num);
			this.editData.setNumber(num);
			if (this.getUIContext().get("currentProject") instanceof CurProjectInfo) {
				CurProjectInfo curProjectInfo = (CurProjectInfo) this.getUIContext().get("currentProject");
				this.editData.setProject(curProjectInfo);
			}
			this.editData.setRelateTask((FDCScheduleTaskInfo) this.relationTask.getData());
		}

	}

	private void setImageBtnEnabled(boolean isEnabled) {
		btnAddImage.setEnabled(isEnabled);
		btnDeleteImage.setEnabled(isEnabled);
	}

	/**
	 * ����ͼƬ�ؼ�״̬
	 */
	private void setImageBtnEnabledByOprtState() {
		if (STATUS_VIEW.equals(this.oprtState)) {
			setImageBtnEnabled(false);
		} else {
			setImageBtnEnabled(true);
			if (editData != null && editData.getState() != null) {
				if (editData.getState().getValue().equals(ScheduleStateEnum.AUDITTED_VALUE)) {
					setImageBtnEnabled(false);
				}
			}
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setImageBtnEnabledByOprtState();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.btnCreateFrom.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);

		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);

		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		this.btnCopy.setVisible(false);

		menuTable1.setVisible(false);
		menuItemCancel.setVisible(false);
		menuItemCancelCancel.setVisible(false);

		menuItemCreateFrom.setVisible(false);
		menuItemCreateTo.setVisible(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCopy.setVisible(false);

		btnWorkFlowG.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);

		btnSubmit.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnAttachment.setVisible(false);
		btnFirst.setVisible(false);
		btnPre.setVisible(false);
		btnNext.setVisible(false);
		btnLast.setVisible(false);

	}

	public void onLoad() throws Exception {
		super.onLoad();
		initSeting();

		CurProjectInfo CurProjetInfo = (CurProjectInfo) getUIContext().get("CurProject");
		if (CurProjetInfo == null && editData != null && editData.getProject() != null) {
			CurProjetInfo = editData.getProject();
		}
		if (null == CurProjetInfo) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("project.id", CurProjetInfo.getId());
		filter.appendFilterItem("bizType.bizType.name", "�������");
		view.setFilter(filter);
		relationTask.setEntityViewInfo(view);
	}
	/**
	 * ��¼ Ĭ��ѡ����
	 */
	private void selectTab(){
		int i=kdtEntries.getRowCount();
		if(i>0){
			showImage(0);
		}
	}
	protected void initSeting() {
		/*
		 * ����ͼƬ�ϴ��ؼ�
		 */
		kDContainer1.addButton(this.btnAddImage);
		kDContainer1.addButton(this.btnDeleteImage);

		setImageBtnEnabledByOprtState();
		// �ֶμ���
		getDetailTable().getColumn("name").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("fileName").getStyleAttributes().setLocked(true);

		IColumn curCol_type = getDetailTable().getColumn("type");
		KDComboBox comboBox = new KDComboBox();

		KDTDefaultCellEditor bizPromptEditor = new KDTDefaultCellEditor(comboBox);
		bizPromptEditor.setClickCountToStart(1);

		comboBox.addItems(ProjectStatusEnum.getEnumList().toArray());
		curCol_type.setEditor(bizPromptEditor);

		// ����Ҽ��˵�
		enableExportExcel(kdtEntries);
		TableHelper.addPopMenu(this, kdtEntries, new ItemAction[] { actionSavePic });

		name.setMaxLength(80);
		txtDescription.setMaxLength(2000);
		logger.info("editData.getState() = " + editData.getState());
		if (editData.getState() != null) {
			if (!editData.getState().getValue().equals(ScheduleStateEnum.SAVED_VALUE)) {
				btnSave.setEnabled(false);
			}
			if (editData.getState().getValue().equals(ScheduleStateEnum.AUDITTED_VALUE)) {
				btnSave.setEnabled(false);
				btnSubmit.setEnabled(false);
				btnEdit.setEnabled(false);
				btnRemove.setEnabled(false);
			}

		}
		btnRemove.setVisible(false);
		btnAuditResult.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnAttachment.setVisible(false);
		if (getOprtState().equals(OprtState.VIEW)) {
			btnAddNew.setEnabled(false);
			btnEdit.setEnabled(false);
		}
		//��¼ Ĭ��ѡ����
		selectTab();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectImageFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		ProjectImageInfo newData = new ProjectImageInfo();

		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		if (prj == null && editData != null && editData.getProject() != null) {
			prj = editData.getProject();
		}
		newData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		newData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		newData.setProject(prj);
		/***
		 * ��֯Ӧ��ȡ������Ŀ������֯
		 */
		newData.setOrgUnit(prj.getFullOrgUnit());
		// newData.setIsKeyCert(true);
		return newData;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		ProjectImageEntryInfo newData = new ProjectImageEntryInfo();
		newData.setType(ProjectStatusEnum.SG);

		try {
			newData.setId(BOSUuid.create(getBizType()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newData;
	}

	/**
	 * �������
	 * 
	 * @param prj
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private String getNumber(CurProjectInfo prj) throws BOSException, SQLException {
		String number = "01";
		String num = prj.getId().toString();
		// SQL��ѯ������ר�����
		FDCSQLBuilder sql = new FDCSQLBuilder();
		if (KeyCert.isSelected()) {
			sql.appendSql(" select FNumber as num from T_SCH_ProjectImage where FProjectID ='" + num
					+ "' and FIsKeyCert = '1' order by FNumber desc ");
		} else {
			sql.appendSql(" select FNumber as num from T_SCH_ProjectImage where FProjectID ='" + num
					+ "' and FIsKeyCert = '0' order by FNumber desc ");
		}
		IRowSet rs = null;
		rs = sql.executeQuery();

		while (rs.next()) {
			String curNum = rs.getString("num");
			try {
				int curNumber = Integer.parseInt(curNum);
				if (curNumber < 9) {
					number = "0" + (curNumber + 1);
				} else {
					number = "" + (curNumber + 1);
				}
				break;
			} catch (Exception e) {
				continue;
			}
		}
		return number;
	}

	/**
	 * @return
	 */
	protected KDTable getDetailTable() {
		return kdtEntries;
	}
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		try {
			// KDLabel.setBounds(x, y, WIDTH, height);
			File[] files = FileUtil.getSelectFiles(this, false, "jpg");
			File file = null;
			for (int i = 0; i < files.length; i++) {
				file = files[i];
				String strFullPath = file.getPath();
				if (strFullPath != null) {

					// �ж��ļ��Ƿ����
					if (!file.exists()) {
						MsgBox.showWarning(this, "�ļ�������");
						return;
					}
					
					// this.txtFileName.setText(file.getName());
					// this.txtFile.setText(strFullPath);
					ProjectImageEntryInfo entry = (ProjectImageEntryInfo) this.createNewDetailData(kdtEntries);
					String fileName = file.getName();
					entry.setFileName(fileName);
					String name = fileName.substring(0, fileName.indexOf("."));
					if (verifyPicture(name) == false) {
						int d = FDCMsgBox.showConfirm2("��ǰ��������Ѿ����ڸ�ͼƬ���Ƿ��ϴ���");
						if (d == FDCMsgBox.CANCEL) {
							return;
						}
					}
					entry.setName(name);

					IRow row = kdtEntries.addRow();
					this.loadLineFields(kdtEntries, row, entry);
					// kdtEntries.getColumn("type")

					// ����ͼƬ
					KDLabel imageLabel = dealPic(row, file, name);

					// ��ͼƬ���ݱ��浽����
					storeFileToRow(row, imageLabel);
					loadFileFromRow(row, imageLabel);
					// if (row != null && tabImage.getTabCount() >=
					// row.getRowIndex())
					// this.tabImage.setSelectedIndex(row.getRowIndex());
					
				}
			}
		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean verifyPicture(String picName) {
		if (kdtEntries.getRowCount() > 0) {
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				IRow row = kdtEntries.getRow(i);
				String name = row.getCell("name").getValue().toString();
				if (picName.equals(name)) {
					return false;
				}
			}
		}
		return true;
	}

	protected void storeFileToRow(IRow row, KDLabel imageLabel) {
		File file = (File) imageLabel.getUserObject();

		try {

			byte b[] = null;
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			long size = fc.size();
			b = new byte[(new Long(size)).intValue()];
			MappedByteBuffer mbb = fc.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, size);
			mbb.get(b);
			mbb.clear();
			fc.close();
			fis.close();

			ByteArrayInputStream in = new ByteArrayInputStream(b);
			BufferedImage img = ImageIO.read(in);
			ScaleFilter scf = new ScaleFilter();
			BufferedImage smallIMG = scf.filter(img, null);
			byte[] smallbyte = new PngEncoder(smallIMG).pngEncode();

			ProjectImageEntryInfo newData = (ProjectImageEntryInfo) row.getUserObject();
			newData.setFile(b);
			newData.setSmallFile(smallbyte);
			/*
			 * ����¼��ӵ�editData
			 */
			editData.getEntries().add(newData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	// �������ͼƬ
	protected KDLabel dealPic(IRow row, File file, String name) {

		// key ֵΪÿһ����¼�����id
		ProjectImageEntryInfo entry = (ProjectImageEntryInfo) row.getUserObject();
		String key = entry.getId().toString();
		if (file == null) {
			file = loadFile(entry);
		}
		if (name == null) {
			name = entry.getName();
		}

		KDScrollPane spImage = getScrollPane(key, name);
		KDLabel imageLabel = getImageLabel(key, spImage, file);

		return imageLabel;
	}

	protected File loadFile(ProjectImageEntryInfo entry) {
		File file = null;
		byte[] b = entry.getFile();
		if (b != null) {
			try {
				file = File.createTempFile("KDTF-" + entry.getName(), ".jpg");
				FileOutputStream fos = new FileOutputStream(file);

				fos.write(b);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return file;
	}

	public void loadFields() {
		// ע��������

		super.loadFields();

		scollPanes.clear();
		imagePanles.clear();
		imageLabels.clear();
		// tabImage.removeAll();
		tabIndex = 0;
		// ���ÿһ�У����
		int rowCount = getDetailTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			//
			IRow row = getDetailTable().getRow(i);

			dealPic(row, null, null);
		}

		// �����ϼ�����
		attachListeners();

		curIndex = -1;
		// ��������м�¼��λ����һ��
		if (kdtEntries.getRow(0) != null) {
			kdtEntries.getSelectManager().select(0, 0);
		}

		if (!STATUS_ADDNEW.equals(getOprtState())) {
			try {
				imageUtil = new FDCImageAttUtil(this, editData.getId().toString(), null);
				List imgAtts = imageUtil.getImgAtts();
				if (imgAtts != null && imgAtts.size() > 0) {
					for (int i = 0; i < imgAtts.size(); i++) {
						AttachmentInfo attInfo = (AttachmentInfo) imgAtts.get(i);
						IRow row = kdtEntries.addRow();
						row.getCell("name").setValue(attInfo.getName());
						row.getCell("fileName").setValue(attInfo.getId().toString());
					}
				}

			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if (editData.isIsKeyCert()) {
			windowTitle = "��Ŀ�ؼ�֤�ձ༭";
			KeyCert.setSelected(true);
			noKeyCert.setSelected(false);
		} else {
			windowTitle = "��Ŀ������ȱ༭";
			KeyCert.setSelected(false);
			noKeyCert.setSelected(true);
		}
	}

	protected void detachListeners() {

	}

	protected void attachListeners() {

	}

	public KDScrollPane getScrollPane(String key, String name) {
		// ���KDScrollPane�����ڣ��½�һ��KDScrollPane
		KDScrollPane spImage = (KDScrollPane) scollPanes.get(key);
		if (spImage == null) {
			spImage = new KDScrollPane();
			scollPanes.put(key, spImage);
			// this.tabImage.add(name, spImage);
		}

		return spImage;
	}

	public KDLabel getImageLabel(String key, KDScrollPane spImage, File file) {

		KDLabel imageLabel = (KDLabel) imageLabels.get(key);
		if (imageLabel == null) {
			imageLabel = new KDLabel();
			if (file != null) {
				BufferedImage materialImage;
				try {
					materialImage = ImageIO.read(file);
					zoo = sumAdaptZoo(materialImage);
					KDImageIcon icon;
					icon = new KDImageIcon(comPicture(nowAngle, file));
					imageLabel.setIcon(icon);
					imageLabel.setUserObject(file);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			imageLabels.put(key, imageLabel);

			spImage.setViewportView(imageLabel);

		}

		return imageLabel;
	}

	private Image comPicture(int angle, File file) throws IOException {
		if (file == null)
			return null;
		BufferedImage materialImage = ImageIO.read(file);
		int w = materialImage.getWidth();
		int h = materialImage.getHeight();
		if (angle != 0) {
			BufferedImage test = ImageIO.read(file);
			BufferedImage dstImage = null;
			AffineTransform affineTransform = new AffineTransform();

			if (angle == 180) {
				affineTransform.translate(w, h);
				dstImage = new BufferedImage(w, h, test.getType());
			} else if (angle == 90) {
				affineTransform.translate(h, 0);
				dstImage = new BufferedImage(h, w, test.getType());
			} else if (angle == 270) {
				affineTransform.translate(0, w);
				dstImage = new BufferedImage(h, w, test.getType());
			}

			affineTransform.rotate(java.lang.Math.toRadians(angle));
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

			materialImage = affineTransformOp.filter(test, dstImage);
		}

		double imageD = sumScale(materialImage);

		if (materialImage.getHeight() < materialImage.getWidth()) {
			w = (int) (w + imageD * (zoo * zooNum));
			h = h + (zoo * zooNum);
		} else {
			h = (int) (h + imageD * (zoo * zooNum));
			w = w + (zoo * zooNum);
		}

		if (h <= 0 || w <= 0)
			return null;

		Image image = materialImage.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
		return image;
	}

	private int sumAdaptZoo(BufferedImage materialImage) {
		int w = materialImage.getWidth();
		int h = materialImage.getHeight();
		int tmpW, tmpH;

		double imageD = sumScale(materialImage);
		int i = 0;
		if (w > 790 || h > 590) {
			for (; 1 == 1; i--) {
				if (h < w) {
					tmpW = (int) (w + imageD * (i * zooNum));
					tmpH = h + (i * zooNum);
				} else {
					tmpH = (int) (h + imageD * (i * zooNum));
					tmpW = w + (i * zooNum);
				}
				if (tmpW <= 790 && tmpH <= 590)
					break;
			}
		}
		return i;
	}

	// չʾͼ��
	protected void showImage(int curIndex) {
		IRow row = kdtEntries.getRow(curIndex);
		ProjectImageEntryInfo newData = (ProjectImageEntryInfo) row.getUserObject();
		String key = newData.getId().toString();
		if (imageLabels == null)
			return;
		else {
			if (key != null) {
				// �����϶�ȡ����
				KDLabel imageLabel = (KDLabel) imageLabels.get(key);
				loadFileFromRow(row, imageLabel);
				if (imageLabel == null) {
					return;
				}
			}
		}
	}

	// �����϶�ȡ����
	protected void loadFileFromRow(IRow row, KDLabel imageLabel) {
		ProjectImageEntryInfo entry = (ProjectImageEntryInfo) row.getUserObject();
		if (imageLabel == null) {
			return;
		}
		if (imageLabel.getIcon() == null) {
			File file = loadFile(entry);
			KDImageIcon icon;
			try {
				Image image = comPicture(nowAngle, file);
				if (image == null)
					return;
				icon = new KDImageIcon(image);
				imageLabel.setIcon(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		Icon icon = imageLabel.getIcon();
		Dimension pSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		imageLabel.setSize(pSize);
		imageLabel.setPreferredSize(pSize);
		pnlIMG.setLayout(null);
		pnlIMG.setPreferredSize(pSize);
		pnlIMG.removeAll();
		if (pSize.width < kDScrollPane1.getWidth() || pSize.height < kDScrollPane1.getHeight()) {
			imageLabel.setBounds((kDScrollPane1.getWidth() - pSize.width) / 2, (kDScrollPane1.getHeight() - pSize.height) / 2, pSize.width,
					pSize.height);
		}
		// imageLabel.setIcon(null);
		// imageLabel.setOpaque(true);
		// imageLabel.setBackground(Color.RED);
		pnlIMG.add(imageLabel);
		pnlIMG.updateUI();
	}

	private double sumScale(BufferedImage materialImage) {
		double imageD = 0.0;
		if (materialImage.getWidth() > materialImage.getHeight())
			imageD = (double) materialImage.getWidth() / (double) materialImage.getHeight();
		else
			imageD = (double) materialImage.getHeight() / (double) materialImage.getWidth();
		return imageD;
	}

	/**
	 * ɾ����ǰѡ�з�¼
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		int[] slts = KDTableUtil.getSelectedRows(kdtEntries);
		for (int i = slts.length - 1; i >= 0; i--) {
			kdtEntries.removeRow(slts[i]);
		}
		if(kdtEntries.getRowCount()>0){
			selectTab();
		}else{
			pnlIMG.removeAll();
		}
		
	}

	/**
	 * output actionSavePic_actionPerformed method
	 */
	public void actionSavePic_actionPerformed(ActionEvent e) throws Exception {

		String strFullPath = FileUtil.getSelectFile(this, true, "jpg");

		int top = kdtEntries.getSelectManager().get().getTop();
		IRow row = kdtEntries.getRow(top);
		ProjectImageEntryInfo newData = (ProjectImageEntryInfo) row.getUserObject();

		File file = null;
		byte[] b = newData.getFile();
		if (b != null) {
			try {
				// String path =
				// strFullPath.substring(0,strFullPath.indexOf('.'));
				// file = File.(path, ".jpg");
				file = new File(strFullPath);

				FileOutputStream fos = new FileOutputStream(file);

				fos.write(b);
				fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				showImage(curIndex);
			}
		}
	}

	/**
	 * ��¼���༭�¼�
	 * 
	 * @throws Exception
	 */
	protected void afterEditStopped(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex) throws Exception {
		String colKey = table.getColumnKey(colIndex);
		IRow row = table.getRow(rowIndex);
		//
		if ("name".equals(colKey)) {
			if (newValue == null) {
				table.getEditManager().editCellAt(colIndex, rowIndex);
				return;
			}
			// key ֵΪÿһ����¼�����id
			ProjectImageEntryInfo entry = (ProjectImageEntryInfo) row.getUserObject();
			String key = entry.getId().toString();
			KDScrollPane spImage = getScrollPane(key, null);
			KDLabel imageLabel = getImageLabel(key, spImage, null);
			// tabImage.setTitleAt(imagePanel.getTabIndex(), (String) newValue);
		}
	}

	protected void tblEntry_editValueChanged(KDTEditEvent e) throws Exception {
		int top = kdtEntries.getSelectManager().get().getTop();
		if (top == curIndex) {
			return;
		}
		curIndex = top;
		showImage(curIndex);

	}

	/**
	 * ��¼���ѡ��仯�¼�
	 */
	protected void afterSelectLine(KDTable table, IObjectValue lineData) {
		int top = kdtEntries.getSelectManager().get().getTop();
		if (top == curIndex) {
			return;
		}
		curIndex = top;
		showImage(curIndex);

		// if (tabImage.getTabCount() >= curIndex)
		// this.tabImage.setSelectedIndex(curIndex);
	}

	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		if (name.getText() == null || name.getText().length() < 1) {
			MsgBox.showWarning("����������Ʋ���Ϊ��");
			SysUtil.abort();
		}
		if (getOprtState().equals(OprtState.ADDNEW)) {
			CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
			if (prj == null && editData != null && editData.getProject() != null) {
				prj = editData.getProject();
			}
			String prjID = prj.getId().toString();
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select FName_l2 as name from T_SCH_ProjectImage where FProjectID ='" + prjID + "'");

			IRowSet rs = null;
			rs = sql.executeQuery();
			String curName = "";
			String name = this.name.getText();
			while (rs.next()) {
				curName = rs.getString("name");
				if (name.equals(curName)) {
					MsgBox.showWarning(name + "������������Ѿ�����");
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * output entries_editStopped method
	 */
	protected void entries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		curIndex = e.getRowIndex();
		showImage(curIndex);
	}

	protected void btnSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception {

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
		super.actionSave_actionPerformed(e);
		// btnSave.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
		super.actionSubmit_actionPerformed(e);

		btnSave.setEnabled(false);
		if (!getOprtState().equals(OprtState.ADDNEW)) {
			actionSave.setEnabled(false);
		} else {
			actionSave.setEnabled(true);
		}
	}

	/**
	 * @throws Exception
	 */
	public void verifyData() throws Exception {
		super.verifyData();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("fileName"));
		sic.add(new SelectorItemInfo("isKeyCert"));
		sic.add(new SelectorItemInfo("project"));
		sic.add(new SelectorItemInfo("imgDescription"));
		return sic;
	}

	/**
	 * �������������Ƿ��߼���ȵ��жϣ���������������ҵ�����֮���໥�Ƚ�
	 */
	static public boolean isEqual(Object objA, Object objB) {
		if (objA == objB) {
			return true;
		}

		if (objA instanceof String && objB == null) {
			objB = "";
		} else if (objB instanceof String && objA == null) {
			objA = "";
		} else if ((objA == null && objB != null) || (objA != null && objB == null)) {
			return false;
		}

		if (objA instanceof CoreBaseInfo && objB instanceof CoreBaseInfo) { // ҵ�����ֻ�ж�id
			CoreBaseInfo obj1 = (CoreBaseInfo) objA;
			CoreBaseInfo obj2 = (CoreBaseInfo) objB;
			return obj1.getId().equals(obj2.getId());
		} else if (objA instanceof BigDecimal && objB instanceof BigDecimal) {
			BigDecimal big1 = (BigDecimal) objA;
			BigDecimal big2 = (BigDecimal) objB;
			return big1.compareTo(big2) == 0;
		} else {
			return objA.equals(objB);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output tImage_stateChanged method
	 */

	/*
	 * protected void tImage_stateChanged(javax.swing.event.ChangeEvent e)
	 * throws Exception { int index = tabImage.getSelectedIndex(); //
	 * kdtEntries.getSelectManager().select(index, 0); }
	 */

}