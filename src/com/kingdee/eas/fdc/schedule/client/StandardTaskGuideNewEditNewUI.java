/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.dm.CategoryInfo;
import com.kingdee.eas.cp.dm.DocumentInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.basedata.util.FDCCPDMUtil;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.fdc.schedule.GuideTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ��������׼����ָ����������ӡ����롢ɾ��;ͼƬ�ϴ�����ӡ�����
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-8-9
 * @see
 */
public class StandardTaskGuideNewEditNewUI extends AbstractStandardTaskGuideNewEditNewUI {
	/** ɾ����ť���� **/
	private static final String BUTTON_DEL_NAME = "ɾ��";

	/** ���밴ť���� **/
	private static final String BUTTON_IMPORT_NAME = "����";

	/** ������ť���� **/
	private static final String BUTTON_ADD_NAME = "����";

	private static final long serialVersionUID = 1L;
	
	DateFormat FORMAT_TIME = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final Logger logger = CoreUIObject.getLogger(StandardTaskGuideNewEditNewUI.class);
	final private static  int size = 1024*5;
	
	/** ͼƬ�ϴ����γɵ�ͼƬ��ʶ **/
	private String attID = null;
	/** ��ǰʵ�庬�е�ͼƬ��ʾ **/
	private String sourceAttID = "";
	private FDCImageAttUtil imageAttUtil = null;
	
	/** ͼƬִ����ӻ�ɾ���ļ��� **/
	private int imageCheckTimes = 0;
	
	KDWorkButton btnAddForEntryA = null;
	KDWorkButton btnImoprtForEntryA = null;
	KDWorkButton btnDelForEntryA = null;

	KDWorkButton btnAddForEntryB = null;
	KDWorkButton btnImoprtForEntryB = null;
	KDWorkButton btnDelForEntryB = null;

	KDWorkButton btnAddForImage = null;
	KDWorkButton btnDelForImage = null;

	/**
	 * output class constructor
	 */
	public StandardTaskGuideNewEditNewUI() throws Exception {
		super();
	}

	protected FDCTreeBaseDataInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		StandardTaskGuideInfo info = new StandardTaskGuideInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return StandardTaskGuideFactory.getRemoteInstance();
	}

	/**
	 * @description ��ʵ���л�ȡ����,��ֵ��ҳ��
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void loadFields() {
		cleanCompentAndSetTableStyle();
		super.loadFields();
		loadFieldOfEntry();
		try {
			loadFieldOfImage();
		} catch (EASBizException e1) {
			handUIException(e1);
		}
		if (StringUtils.isEmpty(editData.getNumber())) {

			if (getUIContext().get("upId") != null) {
				String parentId = getUIContext().get("upId").toString();
				Set numberSet = new HashSet();
				String sql = "select fnumber from T_SCH_StandardTaskGuideNew ";
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				if (!parentId.equals("upid")) {
					try {
						StandardTaskGuideInfo guide = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(
								new ObjectUuidPK(parentId));
						editData.setParent(guide);
						editData.setLevel(guide.getLevel() + 1);
					} catch (EASBizException e) {
						handUIException(e);
					} catch (BOSException e) {
						handUIException(e);
					}
					builder.appendSql(" where fparentid = ?");
					builder.addParam(editData.getParent().getId().toString());

				} else {
					builder.appendSql(" where fparentid is null");
				}

				try {
					IRowSet rs = builder.executeQuery();
					String strNumber = "";
					while (rs.next()) {
						numberSet.add(Integer.parseInt(rs.getString(1)));
					}
					for (int i = 1; i < 100; i++) {
						if (!numberSet.contains(i)) {
							strNumber = (i < 10 ? "0" + i : i + "");
							break;
						}
					}
					this.txtNumber.setText(strNumber);
					if (editData.getParent() != null && editData.getParent().getNumber() != null) {
						this.txtParentNumber.setText(editData.getParent().getNumber());
					}
					editData.setNumber(strNumber);

				} catch (BOSException e) {
					handUIException(e);
				} catch (SQLException e) {
					handUIException(e);
				}
			}
		} else {
			this.txtNumber.setText(editData.getNumber());
		}
	}

	/**
	 * @description ����ͼƬ����¼�С����ñ����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void cleanCompentAndSetTableStyle() {
		entryA.removeRows();
		entryB.removeRows();
		entryA.getStyleAttributes().setLocked(true);
		entryB.getStyleAttributes().setLocked(true);

	}
	
	protected void afterSubmitAddNew() {
		 super.afterSubmitAddNew();
	}
	

	/**
	 * @description ��ʵ��ӻ�ȡ���ݸ������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadFieldOfEntry() {
		for (int i = 0; i < editData.getEntrys().size(); i++) {
			StandardTaskGuideEntryInfo entryInfo = editData.getEntrys().get(i);
			IRow row = null;
			if (GuideTypeEnum.systemFlow.equals(entryInfo.getGuideType())) {// ����ָʾ���������
				row = entryA.addRow();
			} else {
				row = entryB.addRow();
			}
			copyProperies(entryInfo, row);
		}
	}

	/**
	 * @description ��ʵ���ȡ������������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void copyProperies(StandardTaskGuideEntryInfo entryInfo, IRow row) {
		row.getCell("docName").setValue(entryInfo.getDocName());
		row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
		row.getCell("docName").getStyleAttributes().setUnderline(true);
		if (entryInfo.isIsCPFile()) {
			row.getCell("docPath").setValue("Эͬ�ĵ�ƽ̨");
		} else {
			row.getCell("docPath").setValue("ϵͳ����ƽ̨�ĵ�");
		}
		row.getCell("ID").setValue(entryInfo.getId().toString());
		row.getCell("docID").setValue(entryInfo.getDocID());
		row.getCell("guideType").setValue(entryInfo.getGuideType().getValue());
		row.getCell("isCPFile").setValue((new Boolean(entryInfo.isIsCPFile())));
	}

	/**
	 * @description ����ҳ��ͼƬ
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @throws EASBizException
	 * @see
	 */
	private void loadFieldOfImage() throws EASBizException {
		try {
			if (imageAttUtil == null && editData.getId() != null) {
				imageAttUtil = new FDCImageAttUtil(this, editData.getId().toString(), null);
				List imageList = imageAttUtil.getImgAtts();
				if (!imageList.isEmpty()) {
					attID = ((AttachmentInfo) imageList.get(0)).getId().toString();
					sourceAttID = attID;
					imageAttUtil.setIMGByAttID(attID, kDPanel1);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description ׷�ӷ�¼��ѯ�ֶ�
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return SelectorItemCollection
	 * @version EAS7.0
	 * @see
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("longNumber");
		sic.add("entrys.id");
		sic.add("entrys.docID");
		sic.add("entrys.docPath");
		sic.add("entrys.docName");
		sic.add("entrys.guideType");
		sic.add("entrys.isCPFile");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		sic.add("parent.longNubmer");
		return sic;
	}

	/**
	 * @description ��ҳ���ȡ���ݷ���ʵ����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void storeFields() {
		super.storeFields();
		this.editData.getEntrys().clear();
		entryStoreFields(entryA, GuideTypeEnum.systemFlow);
		entryStoreFields(entryB, GuideTypeEnum.referenceCase);
	}

	/**
	 * ������ɾ�� ����ʾ��ͼ��ֻ���û����ˡ����桱����ʱ����<br>
	 * ���ϴ�����ͼƬ������Ҫ������ͼƬ����Ҫɾ���ϵ�ͼƬ
	 * @author �ź���
	 */
	private void storeOrDeleteImage() {
		// 1�������к���ͼƬ���ͻ���ѡ��ر�,����˵Ҫ����,���һ��ʼ�о�ɾ��Ȼ���ϴ�ͼƬ
		if (kDPanel1.getComponentCount() > 0 && !attID.equals(sourceAttID)) {
			if (!StringUtils.isEmpty(sourceAttID)) {
				DeleteImageTread deleteImageTread = new DeleteImageTread(editData.getId().toString(), sourceAttID);
				deleteImageTread.run();
			}
			UploadImageTread uploadImageTread = new UploadImageTread(attID);
			uploadImageTread.run();
		}
		// 2��������û��ͼƬ,�ͻ���ѡ���˹ر�,����˵Ҫ���棬���Ǳ�����ͼƬ����ô��ɾ��ͼƬ
		if (kDPanel1.getComponentCount() == 0 && !StringUtils.isEmpty(sourceAttID)) {
			DeleteImageTread deleteImageTread = new DeleteImageTread(editData.getId().toString(), sourceAttID);
			deleteImageTread.run();
		}
	}

	/**
	 * @description ѭ��Table������,�����¼ʵ����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void entryStoreFields(KDTable table, GuideTypeEnum guideType) {
		for (int i = 0; i < table.getRowCount(); i++) {
			StandardTaskGuideEntryInfo info = new StandardTaskGuideEntryInfo();
			IRow row = table.getRow(i);
			info.setDocName(getCaseCellData(row, "docName"));
			info.setDocPath(getCaseCellData(row, "docPath"));
			info.setId(BOSUuid.read(getCaseCellData(row, "ID")));
			info.setDocID(getCaseCellData(row, "docID"));
			info.setGuideType(guideType);
			info.setIsCPFile(new Boolean(getCaseCellData(row, "isCPFile")).booleanValue());
			this.editData.getEntrys().add(info);
		}
	}

	/**
	 * @description ��row�л�ȡCellֵ��ת������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return String
	 * @version EAS7.0
	 * @see
	 */
	private String getCaseCellData(IRow row, String cellName) {
		return row.getCell(cellName).getValue().toString();
	}

	/**
	 * @description ��ҳ�����ñ�ͷ
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void onLoad() throws Exception {
		rMenuItemSubmitAndAddNew.setEnabled(false);
		rMenuItemSubmitAndAddNew.removeAll();
		this.menuBar.remove(rMenuItemSubmitAndAddNew);
		entryA.checkParsed();
		entryB.checkParsed();
		super.onLoad();
		this.windowTitle = "��׼����ָ���༭";
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnRemove.setVisible(false);
		
		txtStandardDuration.setPrecision(0);
		txtStandardDuration.setDataType(Integer.class);
		txtStandardDuration.setNegatived(false);
	}
	
	private void notifyDelete(){
		int result =FDCMsgBox.showConfirm2("��ȷ��ɾ����ǰ��ѡ������");
		if(result == MsgBox.NO || result == MsgBox.CANCEL){
			abort();
		}
	}

	/**
	 * 
	 * @description ��ʼ����ť,�԰�ť��Ӽ���
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	protected void initWorkButton() {
		if (btnAddForEntryA == null) { //˵��δ��ʼ���ؼ�
			initMyWorkButton();
		}
		super.initWorkButton();
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionCopy.setVisible(false);
		actionFirst.setVisible(false);
		actionNext.setVisible(false);
		actionPre.setVisible(false);
		actionLast.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);

		btnSave.setText("����");
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
	}

	private void initMyWorkButton() {
		btnAddForEntryA = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnImoprtForEntryA = createButton("btnImport", BUTTON_IMPORT_NAME, "imgTbtn_input");
		btnDelForEntryA = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		btnAddForEntryB = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnImoprtForEntryB = createButton("btnImport", BUTTON_IMPORT_NAME, "imgTbtn_input");
		btnDelForEntryB = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		btnAddForImage = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnDelForImage = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		entryAContainer.addButton(btnAddForEntryA);
		entryAContainer.addButton(btnImoprtForEntryA);
		entryAContainer.addButton(btnDelForEntryA);

		entryBContainer.addButton(btnAddForEntryB);
		entryBContainer.addButton(btnImoprtForEntryB);
		entryBContainer.addButton(btnDelForEntryB);

		imageContainer.addButton(btnAddForImage);
		imageContainer.addButton(btnDelForImage);

		addBtnListener(btnAddForEntryA, entryA);
		importBtnListener(btnImoprtForEntryA, entryA);
		delBtnListener(btnDelForEntryA, entryA);

		addBtnListener(btnAddForEntryB, entryB);
		importBtnListener(btnImoprtForEntryB, entryB);
		delBtnListener(btnDelForEntryB, entryB);

		addBtnListenerForImage(btnAddForImage);

		delBtnListenerForImage(btnDelForImage);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			setCtrlEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			setCtrlEnabled(true);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			setCtrlEnabled(false);
		}
	}
	
	/**
	 * 
	 * @description ҳ�水ť״̬����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return String
	 * @version EAS7.0
	 * @see
	 */
	public String getOprtState() {
		if (this.oprtState.equals(STATUS_VIEW)) {
			try {
				//TODO ������������⣬���벻Ӧ�÷�������������ع��÷��� Added By Owen_wen 2013.11.6 
				restOperate();
			} catch (Exception e) {
				// e.printStackTrace(); shit ��������쳣������  Added By Owen_wen 2013.11.6 
			}
		}
		if (this.oprtState.equals(STATUS_ADDNEW)) {
		} else {
			try {
				restOperate();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		
		return oprtState;
	}

	/**
	 * ���û�����ĳЩ��ť�ؼ�
	 */
	private void setCtrlEnabled(boolean flag) {
		if (btnAddForEntryA == null) { //˵��δ��ʼ���ؼ�
			initMyWorkButton();
		}
		btnAddForEntryA.setEnabled(flag);
		btnAddForEntryB.setEnabled(flag);
		btnDelForEntryA.setEnabled(flag);
		btnDelForEntryB.setEnabled(flag);
		btnImoprtForEntryA.setEnabled(flag);
		btnImoprtForEntryB.setEnabled(flag);
		btnAddForImage.setEnabled(flag);
		btnDelForImage.setEnabled(flag);
		//		actionAddNew.setEnabled(flag);
		//		actionEdit.setEnabled(flag);
		//		actionRemove.setEnabled(flag);
	}

	/**
	 * 
	 * @description ��Эͬƽ̨���븽������׼����ָ��
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void importBtnListener(KDWorkButton importBtn, final KDTable table) {
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				try {
					// ����Эͬ�ĵ�����
					CategoryInfo category = buildCategory();
					// ������Ŀ
					DocumentInfo dInfo = buildDocument(category);// F7��������
					for (int i = 0; i < table.getRowCount(); i++) {
						IRow row = table.getRow(i);
						if (row.getCell("docID").getValue().equals(dInfo.getId().toString())) {
							return;
						}
					}
					importCPDFile(table, dInfo);
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		});
	}
	
	/**
	 * 
	 * @description �����ĵ�����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return CategoryInfo
	 * @version EAS7.0
	 * @see
	 */
	private CategoryInfo buildCategory() {
		KDBizPromptBox F7Category = new KDBizPromptBox();
		F7Category.setQueryInfo("com.kingdee.eas.fdc.schedule.app.CategoryQuery");
		F7Category.setName("����");
		F7Category.setDataBySelector();// F7��������
		CategoryInfo category = (CategoryInfo) F7Category.getValue();
		category.setName("����");
		return category;
	}

	/**
	 * 
	 * @description ������Ŀ
	 * @author �ź���
	 * @param category
	 *            ��ѯʵ��
	 * @createDate 2011-8-9
	 * @return DocumentInfo
	 * @version EAS7.0
	 * @see
	 */
	private DocumentInfo buildDocument(CategoryInfo category) {
		KDBizPromptBox F7Document = new KDBizPromptBox();
		F7Document.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7DocumentQuery");
		F7Document.setName("����");
		SelectorItemCollection sic = new SelectorItemCollection();
		EntityViewInfo view = getCategoryQuery(sic, category);
		F7Document.setEntityViewInfo(view);
		F7Document.setSelectorCollection(sic);
		F7Document.setDataBySelector();
		DocumentInfo dInfo = (DocumentInfo) F7Document.getValue();
		return dInfo;
	}

	/**
	 * 
	 * @description ��ȡ�ĵ�
	 * @author �ź���
	 * @param sic
	 *            ��ѯ��
	 * @param category
	 *            ��ѯʵ��
	 * @return EntityViewInfo
	 * @see
	 */
	private EntityViewInfo getCategoryQuery(SelectorItemCollection sic, CategoryInfo category) {
		EntityViewInfo view = new EntityViewInfo();
		sic.add("title");
		sic.add("contentType");
		sic.add("category.id");
		sic.add("category.name");
		sic.add("category.docArea.name");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("category.id", category.getId(), CompareType.EQUALS));
		view.setFilter(filter);
		return view;
	}
	
	private void importCPDFile(final KDTable table, DocumentInfo dInfo) throws BOSException, SQLException {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FBusinessID as docID, FDisplayName as docName from T_FME_BusiDoc ");
		sql.appendSql(" where FBusinessID = ?");
		sql.addParam(dInfo.getId().toString());
		IRowSet rs = sql.executeQuery();
		while (rs.next()) {
			String docID = rs.getString("docID");
			String docName = rs.getString("docName");
			String path = dInfo.getCategory().getDocArea().getName() + "//" + dInfo.getCategory().getName();
			IRow row = table.addRow();
			StandardTaskGuideEntryInfo entryInfo = new StandardTaskGuideEntryInfo();
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
			entryInfo.setGuideType(GuideTypeEnum.systemFlow);
			entryInfo.setIsCPFile(true);
			row.getCell("ID").setValue(entryInfo.getId().toString());
			row.getCell("docName").setValue(docName);
			row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
			row.getCell("docName").getStyleAttributes().setUnderline(true);
			row.getCell("docPath").setValue(path);
			row.getCell("docID").setValue(docID);
			row.getCell("isCPFile").setValue(Boolean.TRUE);
		}
	}

	/**
	 * 
	 * @description ��Ӹ���
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void addBtnListener(KDWorkButton addBtn, final KDTable table) {
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				File file = getFileChooser();
				StandardTaskGuideEntryInfo entryinfo = createNewDetail(file, 1);
				try {
					String attachmentID = FDCAttachmentUtil.uploadAtt(entryinfo.getId().toString(), file);
					addRowForEntry(file, table, entryinfo);
					table.getRow(table.getRowCount() - 1).getCell("docID").setValue(attachmentID);
				} catch (Exception ex) {
					if (ex.getMessage() != null) {
						FDCMsgBox.showWarning(ex.getMessage());
					}
					SysUtil.abort();
				}
			}
		});
	}

	/**
	 * 
	 * @description ͼƬ���
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void addBtnListenerForImage(KDWorkButton addBtn) {
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				try {
					addTempImage(kDPanel1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}
	private File imageFile;
	private void addTempImage(JComponent compt) throws IOException {
		int componentCount = kDPanel1.getComponentCount();
		if (componentCount > 0) {
			if (FDCMsgBox.showConfirm2(this, "��������ͼƬ��ɾ����ǰ��ͼƬ") == FDCMsgBox.OK) {
				kDPanel1.removeAll();
			} else {
				return;
			}
		}
		imageFile = getImageFileChooser();
		if (imageFile != null) {
			attID = imageFile.getName() + FORMAT_TIME.format(new Date());
			
			ImageIcon icon = imageAttUtil.getImageFromFile(imageFile);
			Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
			if (compt instanceof JLabel) {
				compt.setSize(size);
				compt.setMaximumSize(size);
				compt.setPreferredSize(size);
				((JLabel) compt).setText(null);
				((JLabel) compt).setIcon(icon);
			} else if (compt instanceof JPanel) {
				compt.removeAll();
				JLabel lable = new JLabel();
				lable.setSize(compt.getSize());
				lable.setHorizontalAlignment(SwingConstants.CENTER);
				double hwRate = (icon.getIconHeight()*1.0)/ icon.getIconWidth() ;
				int width = icon.getIconWidth();
				int height = icon.getIconHeight();
				if(width>lable.getWidth()){
					width = lable.getWidth();
					height = new Integer((int) (lable.getHeight()*hwRate)).intValue();
				}
				
				icon.setImage(icon.getImage().getScaledInstance(width,height, Image.SCALE_DEFAULT));
				
				lable.setIcon(icon);
				((KDPanel) compt).add(lable);
				compt.updateUI();
				imageCheckTimes++;
			}
		}
	}
		
	private void deleteTempImage(JComponent compt) {
		if (compt instanceof JPanel) {
			compt.removeAll();
			compt.updateUI();
			imageCheckTimes++;
		}
	}
	
	private void uploadImage(String attID, JComponent compt) {
		try {
			imageAttUtil.setBosID(this.editData.getId().toString());
			sourceAttID = imageAttUtil.uploadAtt(imageFile);
			imageAttUtil.setIMGByAttID(attID, compt);
			imageAttUtil.refreshAtt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.kDPanel1.removeAll();
		this.sourceAttID = null;
		attID = null;
	}
	
	/**
	 * 
	 * @description ͼƬɾ��
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @param delBtn
	 *            ɾ��ͼƬ��ť
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void delBtnListenerForImage(KDWorkButton delBtn) {
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					notifyDelete();
					deleteTempImage(kDPanel1);
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		});
	}
	
	private void deleteImage(String bizID, String imageAttID) {
		try {
			FDCAttachmentUtil.deleteAtt(bizID, imageAttID);
			imageAttUtil.refreshAtt();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description �ĵ�ɾ��
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @param delBtn
	 *            �ĵ�ɾ����ť
	 * @param table
	 *            �����ķ�¼��Ӧ��
	 * @version EAS7.0
	 * @see
	 */
	private void delBtnListener(KDWorkButton delBtn, final KDTable table) {
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows[] = KDTableUtil.getSelectedRows(table);
				IRow row = null;
				if (rows.length >0) {
					try {
						notifyDelete();
						for(int i=rows.length;i>0;i--){
							row = table.getRow(rows[i-1]);
							FDCAttachmentUtil.deleteAtt(editData.getId().toString(), getCaseCellData(row, "docID"));
							table.removeRow(row.getRowIndex());
						}
						
						
					} catch (EASBizException e1) {
						handUIException(e1);
					} catch (BOSException e1) {
						handUIException(e1);
					}
				}
			}
		});
	}

	
	/**
	 * 
	 * @description �쳣����
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void handlerException(Exception ex) {
		if (ex.getMessage() != null) {
			FDCMsgBox.showWarning(this, ex.getMessage());
		}
		SysUtil.abort();
	}


	/**
	 * 
	 * @description ����һ��Button
	 * @author �ź���
	 * @param name
	 * @param text
	 * @param image
	 * @return KDWorkButton
	 */
	private KDWorkButton createButton(String name, String text, String image) {
		KDWorkButton btn = new KDWorkButton();
		btn.setName(name);
		btn.setText(text);
		btn.setSize(21, 9);
		btn.setIcon(EASResource.getIcon(image));
		btn.setVisible(true);
		btn.setEnabled(true);
		return btn;
	}

	/**
	 * 
	 * @description ������¼ʵ��
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @param file
	 * @param tableIndex
	 * @return StandardTaskGuideEntryInfo
	 * @version EAS7.0
	 * @see
	 */
	private StandardTaskGuideEntryInfo createNewDetail(File file, int tableIndex) {
		StandardTaskGuideEntryInfo entryInfo = new StandardTaskGuideEntryInfo();
		entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		entryInfo.setIsCPFile(false);
		if (tableIndex == 1) {
			entryInfo.setGuideType(GuideTypeEnum.systemFlow);
		} else {
			entryInfo.setGuideType(GuideTypeEnum.referenceCase);
		}
		return entryInfo;
	}

	/**
	 * 
	 * @description ���һ�У���ֵ����
	 * @author �ź���
	 * @param fileName
	 *            ������
	 * @param filePath
	 *            ����·��
	 * @param table
	 *            ����ķ�¼��
	 * @param entryinfo
	 *            ��¼ʵ��
	 */
	private void addRowForEntry(File file, KDTable table, StandardTaskGuideEntryInfo entryinfo) {
		IRow row = table.addRow();
		row.getCell("isCPFile").setValue(Boolean.FALSE);
		row.getCell("docName").setValue(file.getName());
		row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
		row.getCell("docName").getStyleAttributes().setUnderline(true);
		row.getCell("docPath").setValue("ϵͳ����ƽ̨�ĵ�");
		// row.getCell("docPath").setValue(file.getPath());
		row.getCell("ID").setValue(entryinfo.getId().toString());
	}

	/**
	 * 
	 * @description ��ȡѡ��ĸ���
	 * @author �ź���
	 * @return File
	 */
	private File getFileChooser() {
		KDFileChooser fc = initFileChooser();
		int retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return null;
		}
		File upFile = fc.getSelectedFile();
		validFileSize(upFile);
		return upFile;
	}
	
	private KDFileChooser initFileChooser(){
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		return fc;
	}
	private void validFileSize(File upFile){
		if (upFile.length() > size * 1024) {
			FDCMsgBox.showWarning(this, "�벻Ҫ�ϴ��ļ�����" + size/1024 + "MB�ĸ��������ⵥ�ݴ򿪻�����");
			SysUtil.abort();
		}
	}

	private File getImageFileChooser() {
		KDFileChooser fc =  initFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg,bmp,gif,png", imageAttUtil.getTypesArray());
		fc.setFileFilter(filter);

		int retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return null;
		}
		File upFile = fc.getSelectedFile();
		validFileSize(upFile);
		return upFile;
	}

	/**
	 * 
	 * @description EntryA˫�����ļ�
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @version EAS7.0
	 * @see
	 */
	protected void entryA_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e, entryA);
	}

	/**
	 * 
	 * @description ˫��table�ĵڶ�����Ԫ��,�ж��ļ�"�Ƿ�Эͬ",�����ĵ�
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @param ����¼�
	 * @param table
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws IOException
	 * @throws SQLException void
	 * @version EAS7.0
	 * @see
	 */
	private void tableClicked(KDTMouseEvent e, KDTable table) throws EASBizException, BOSException, IOException, SQLException {
		if (e.getClickCount() == 1 && e.getColIndex() == 1 && KDTableUtil.getSelectedRow(table) != null) {
			IRow row = KDTableUtil.getSelectedRow(table);// ��Ҫ�ж��Ƿ��Ǹ���ID
			Boolean isCPFile = (Boolean) row.getCell("isCPFile").getValue();
			if (isCPFile.booleanValue()) {
				
				try {
					File[] files = FDCCPDMUtil.downLoadFileFromCP(getCaseCellData(row, "docID"), getCaseCellData(row, "docName"));
					FDCAttachmentUtil.openFileInWindows(files[0].getPath());
				} catch (Exception e1) {
					FDCMsgBox.showWarning(this, "��ʧ��!");
					SysUtil.abort();
				}
			} else {
				FDCAttachmentUtil.openAtt(getCaseCellData(row, "docID"));
			}
		}
	}

	/**
	 * 
	 * @description EntryB˫�����ļ�
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @version EAS7.0
	 * @see
	 */
	protected void entryB_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e, entryB);
	}

	/**
	 * 
	 * @description �ж�ҳ���Ƿ�������
	 * @author �ź���
	 * @createDate 2011-8-12 void
	 * @version EAS7.0
	 * @see
	 */
	private void whetherOrNotEditData() {
		if (editData.getId() == null) {
			FDCMsgBox.showWarning(this, "�ϴ����������ȱ��浥��");
			SysUtil.abort();
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setCtrlEnabled(true);
		btnAddNew.setEnabled(true);
	}

	/**
	 * @description ������ݣ����ݼ���Ϊ1��������ݣ�
	 * @author ����ΰ
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 */
	public void addNew() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("level", "1"));
		view.setFilter(filter);
		StandardTaskGuideCollection collections = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		int number = 0;
		String numStr = "";
		if (collections.size() > 0) {
			for (int i = 0; i < collections.size(); i++) {
				StandardTaskGuideInfo standardInfo = (StandardTaskGuideInfo) collections.get(i);
				String num = standardInfo.getNumber();
				if (Integer.parseInt(num) > number) {
					number = Integer.parseInt(num);
				}
			}
		}
		// ���ñ������룬��ͬ�����������1
		if (number + 1 < 10) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("000").append((number + 1) + "");
			numStr = numberStr.toString();
		} else if ((number + 1) >= 10 && (number + 1) < 100) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("00").append((number + 1) + "");
			numStr = numberStr.toString();
		} else if ((number + 1) >= 100 && (number + 1) < 1000) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("0").append((number + 1) + "");
			numStr = numberStr.toString();
		} else {
			numStr = (number + 1) + "";
		}
		this.txtNumber.setText(numStr);
	}

	/**
	 * @description �༭�����Լ���������(���ݳ�������ʾ��һ������)
	 * @author ����ΰ * @createDate 2011-8-9
	 * @version EAS7.0
	 */
	public void restOperate() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		view.setFilter(filter);
		StandardTaskGuideCollection collections = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		String longNumber = "";
		if (collections.size() > 0) {
			for (int i = 0; i < collections.size(); i++) {
				StandardTaskGuideInfo info = collections.get(i);
				longNumber = info.getLongNumber();
			}
		}
		if (longNumber.indexOf(".") > 0 || longNumber.indexOf("!") > 0) {
			longNumber = longNumber.replace('!', '.');
			this.txtParentNumber.setText(longNumber);
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			FDCMsgBox.showInfo("�������Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,2})");
		if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
			FDCMsgBox.showInfo("���벻�ܳ���2�����������ַ���");
			SysUtil.abort();
		}
//		Pattern verifyLongNumber = Pattern.compile("([0-9]{1,})");
		Integer duration =  (Integer) (txtStandardDuration.getValue(Integer.class) == null?null:txtStandardDuration.getValue(Integer.class));
		
		if (duration!=null && (duration.intValue()<1 || duration.intValue()>10000)) {
				FDCMsgBox.showInfo("��׼����ֻ����¼��1-10000֮���������");
				abort();
		}
	}

	/**
	 * @description ��֤�����Ƿ����
	 * @author ����ΰ
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void verifyInputNumber() throws Exception {
		String number = this.txtNumber.getText().trim();
		if (this.txtNumber.getText() == null || "".equals(this.txtNumber.getText().toString().trim())) {
			FDCMsgBox.showInfo("�������벻��Ϊ�գ�");
			SysUtil.abort();
		}
		this.txtNumber.setText(number);
	}

	/**
	 * @description ��ѯ���е�number
	 * @author ����ΰ
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void selectNumber(String number, EntityViewInfo view) throws Exception {
		StandardTaskGuideCollection collection = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		if (collection.size() > 0) {
			// ����string���͵����飬�����е�number����
			String[] numberArray = new String[collection.size()];
			for (int temp = 0; temp < collection.size(); temp++) {
				StandardTaskGuideInfo schTemplateTaskInfo = (StandardTaskGuideInfo) collection.get(temp);
				numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
			}
			// ѭ���ж�
			for (int m = 0; m < numberArray.length; m++) {
				if (number.equals(numberArray[m])) {
					FDCMsgBox.showWarning(this, "�������� " + number + " �Ѿ����ڣ������ظ�");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * @description �༭��ʱ������Ƿ����
	 * @author ����ΰ
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void provingEditNumber(String number) throws Exception {
		String dataId = this.editData.getId().toString();
		StandardTaskGuideInfo info = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(dataId));
		if (info.getLevel() == 1) {// ����Ϊ1�����
			// ��ѯ���м���Ϊ1������number���ų��Լ�number
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("level", new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("id", dataId, CompareType.NOTEQUALS));
			view.setFilter(filter);
			selectNumber(number, view);
			this.editData.setNumber(number);
		} else {
			// ���β�Ϊ1������£��ų��Լ���number
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", info.getParent().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id", dataId, CompareType.NOTEQUALS));
			view.setFilter(filter);
			selectNumber(number, view);
			this.editData.setNumber(number);
			this.editData.setParent(info.getParent());
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInputNumber();
		super.actionSave_actionPerformed(e);
		storeOrDeleteImage();
		/** ���ִ���˱���,���ü��� **/
		imageCheckTimes = 0;
	}
	
	// ��Ҫ�ǽ���رյ�ʱ���ж������Ƿ����ı� modify by duhongming
	public boolean destroyWindow() {
		// ��ʼ��ͼƬ,����˱���,����ͼƬ,��ô��ɾ����ǰ��,Ȼ���ϴ����ڵ�,���һ��û��ֱ���ϴ����ڵ�ͼ
		if (editData == null) {
			return super.destroyWindow();
		}
		String selectedItemData = (String) txtName.getSelectedItemData();
		Object selectItem = taskTypeSelect.getSelectedItem();
		String selectItemStr = "";
		if (selectItem != null) {
			selectItemStr = ((RESchTaskTypeEnum) selectItem).getValue();
		}
		String numbertxt = txtNumber.getText();
		String desctxt = txtDescription.getText();
		String durationtxt = txtStandardDuration.getText();
		if (numbertxt.equals("")) {
			numbertxt = new String();
		}
		if (desctxt.equals("")) {
			desctxt = new String();
		}
		if (durationtxt.equals("")) {
			durationtxt = new Integer(0).toString();
		}

		String[] newObj = new String[] { selectedItemData, numbertxt, selectItemStr, desctxt, durationtxt,
				new Integer(entryA.getRowCount() + entryB.getRowCount()).toString() };
		if (editData == null) {
			for (int i = 0; i < newObj.length; i++) {
				if (newObj[i] != null)
					return super.destroyWindow();
			}
		}
		StandardTaskGuideEntryCollection entrys = editData.getEntrys();
		int entrySize = 0;
		if (entrys != null) {
			entrySize = entrys.size();
		}
		RESchTaskTypeEnum taskType = editData.getTaskType();
		String taskTypeStr = new String();
		if (taskType != null) {
			taskTypeStr = taskType.getValue();
		}
		BigDecimal standardDuration = editData.getStandardDuration();
		int duration = 0;
		if (standardDuration != null) {
			duration = standardDuration.intValue();
		}
		String description = editData.getDescription();
		if (description == null) {
			description = new String();
		}
		String name = editData.getName();
		if (name == null) {
			name = new String();
		}
		String[] oldObj = new String[] { name, new String(editData.getNumber() == null ? "" : editData.getNumber()), taskTypeStr,
				description,
				new Integer(duration).toString(),
				new Integer(entrySize).toString() };
		/** ��ͼƬ�����⴦��,���ܻ���Ҫ��һ���Ƿ���ʵ����ͼƬ�ı仯 **/
		if (imageCheckTimes > 0) {
			return super.destroyWindow();
		}
		for (int i = 0; i < newObj.length; i++) {
			if (!oldObj[i].equals(newObj[i])) {
				return super.destroyWindow();
			}
		}
		/** �ر�ʱList����ִ������ˢ�� **/
		StandardTaskGuideNewListUI listUI = (StandardTaskGuideNewListUI) getUIContext().get("Owner");
		listUI.execQuery();
		return super.destroyWindow();
	}

	class DeleteImageTread implements Runnable {
		private String id, attid;

		public DeleteImageTread(String bizID, String imageAttID) {
			this.id = bizID;
			this.attid = imageAttID;
		}
		public void run() {
			deleteImage(id, attid);
		}
	}

	/**
	 * ������ִ��ɾ��ͼƬ������ִ��ͼƬ���ϴ�������ϴ�ʧ�ܵ����,������������Ķ��߳�<br>
	 * 
	 * ��Ȩ�������������������޹�˾��Ȩ����
	 * 
	 * @author �ź���
	 * @version EAS7.0
	 * @createDate 2011-10-19
	 * @see
	 */
	class UploadImageTread implements Runnable {
		private String id;

		public UploadImageTread(String imageAttID) {
			this.id = imageAttID;
		}

		public void run() {
			uploadImage(id, kDPanel1);
		}
	}

	protected boolean isModifySave() {
		return true;
	}
}