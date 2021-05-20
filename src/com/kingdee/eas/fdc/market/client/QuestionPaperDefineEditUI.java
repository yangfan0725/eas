/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectSingleKey;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.market.DocumentFactory;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.IDocument;
import com.kingdee.eas.fdc.market.QuestionBaseInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.QuestionStyle;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.market.SelectQuestionInfo;
import com.kingdee.eas.fdc.market.SelectQuestionItemCollection;
import com.kingdee.eas.fdc.market.SelectQuestionItemInfo;

import com.kingdee.eas.fdc.sellhouse.client.QuestionDefinitionPriverProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuestionPaperDefineEditUI extends AbstractQuestionPaperDefineEditUI {
	private static final Logger logger = CoreUIObject.getLogger(QuestionPaperDefineEditUI.class);

	XDocument xDocument;
	JScrollPane xScrollPane;
	QuestionSelectPromptBox f7;
	private int questItems = 0;

	// DocumentInfo documentInfo;

	/**
	 * output class constructor
	 */
	public QuestionPaperDefineEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
		QuestionPaperDefineInfo d = (QuestionPaperDefineInfo) getDataBinder().getValueObject();
		if (d != null) {
			try {
				changeDocumentId(d.getDocumentId());
			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * ��ӡ�ʾ�
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		 * AttributeSet attributeSet = new HashAttributeSet();
		 * attributeSet.add(OrientationRequested.LANDSCAPE); // ��ȡ���д�ӡ����
		 * PrintService[] printServices =
		 * PrintServiceLookup.lookupPrintServices(docFlavor, attributeSet); //
		 * ��ȡĬ�ϴ�ӡ���� PrintService defaultPrintService =
		 * PrintServiceLookup.lookupDefaultPrintService();
		 * PrintRequestAttributeSet printRequestAttributeSet = new
		 * HashPrintRequestAttributeSet(); // printRequestAttributeSet.add(new
		 * PrinterResolution(72, 72, // PrinterResolution.DPI));
		 * printRequestAttributeSet.add(xDocument.getMediaPrintableArea());
		 * printRequestAttributeSet.toArray(); // ��ʾ��ӡ�Ի��� PrintService selection
		 * = ServiceUI.printDialog(null, 200, 200, printServices,
		 * defaultPrintService, docFlavor, printRequestAttributeSet); if
		 * (selection == null) { // ȡ����ӡ return; } PrinterResolution
		 * printerResolution = (PrinterResolution)
		 * selection.getDefaultAttributeValue(PrinterResolution.class);
		 * printerResolution.getFeedResolution(PrinterResolution.DPI);
		 * printRequestAttributeSet.add(printerResolution); DocAttributeSet das
		 * = new HashDocAttributeSet(); Doc doc = new SimpleDoc(xDocument,
		 * docFlavor, das); doc.getAttributes();
		 * 
		 * // if(true) return;
		 * 
		 * DocPrintJob job = selection.createPrintJob();
		 * 
		 * job.getAttributes(); try { job.print(doc, printRequestAttributeSet);
		 * } catch (PrintException pe) { logger.error(pe);
		 * MsgBox.showError("��ӡ���̳���."); }
		 */
		QuestionPaperDefineInfo info = (QuestionPaperDefineInfo) this.editData;
		if (info != null && info.getNumber() != null) {
			String payMentId = info.getId().toString();
			QuestionDefinitionPriverProvider data = new QuestionDefinitionPriverProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.market.app.QuestionDefinePrintQuery"));
			//QuestionPaperDefineAndDocumentPrintQuery
			// com.kingdee.eas.fdc.market.app.DocumentSubjectAndItemsPrintQuery
			// com.kingdee.eas.fdc.market.app.DocumentItemAndOpetionPrintQuery
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/market/questionManage/questionDefine", data, javax.swing.SwingUtilities.getWindowAncestor(this));

		} else {
			MsgBox.showInfo("û�����ݣ����飡");
		}
	}

	/**
	 * Ԥ���ʾ�
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		/*XPrintPreviewDialog xpd = new XPrintPreviewDialog(getFrame(this), "", true, xDocument);
		xpd.setVisible(true);
		xpd.setVisible(false);
		xpd.dispose();*/
		QuestionPaperDefineInfo info = (QuestionPaperDefineInfo) this.editData;
		if (info != null && info.getId()!=null) {
			String payMentId = info.getId().toString();
			QuestionDefinitionPriverProvider data = new QuestionDefinitionPriverProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.market.app.QuestionDefinePrintQuery"));
			//QuestionPaperDefineAndDocumentPrintQuery
			// com.kingdee.eas.fdc.market.app.DocumentSubjectAndItemsPrintQuery
			// com.kingdee.eas.fdc.market.app.DocumentItemAndOpetionPrintQuery
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/market/questionManage/questionDefine", data, javax.swing.SwingUtilities.getWindowAncestor(this));			
		} else {
			MsgBox.showInfo("û�����ݣ����飡");
		}
	}

	/**
	 * 
	 */
	protected void answerResult_actionPerformed(ActionEvent e) throws Exception {
		if (xDocument.getDoc().getId() == null) {
			MsgBox.showWarning("�����ʾ��޷�����");
			return;
		}
		DocumentSubjectInfo selectedInfo = xDocument.getSelectedSubjectInfo();
		if (selectedInfo != null) {
			// ������ѡ��Ŀ
		} else {
			// ���������ʾ�
		}

	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		QuestionTypeInfo qtTypeInfo = (QuestionTypeInfo) this.prmtpaperType.getValue();
		if (qtTypeInfo == null) {
			MsgBox.showInfo("�ʾ������Ϊ�գ�");
			return;
		}
		String number = this.txtNumber.getText().trim();
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;
		if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
			if (number == null || "".equals(number)) {
				MsgBox.showInfo("���ݱ�Ų���Ϊ�գ�");
				return;
			}
		}

		Date startDate = (Date) this.pkstartDate.getValue();
		if (startDate == null) {
			MsgBox.showInfo("�������ڲ���Ϊ�գ�");
			return;
		}
		Date endDate = (Date) this.pkendDate.getValue();
		if (endDate == null) {
			MsgBox.showInfo("��ֹ���ڲ���Ϊ�գ�");
			return;
		}
		if (startDate != null && endDate != null) {
			if (endDate.before(startDate)) {
				MsgBox.showInfo("��ֹ���ڲ���С���������ڣ�");
				return;
			}
		}

		Date bizDate = (Date) this.pkBizDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("ҵ�����ڲ���Ϊ�գ�");
			return;
		}
		String topic = this.txttopric.getText().trim();
		if (topic == null || "".equals(topic)) {
			MsgBox.showInfo("�ʾ����ⲻ��Ϊ�գ�");
			return;
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (QuestionPaperDefineFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("���ݱ�Ų����ظ���");
				SysUtil.abort();
			}
		}
		IDocument iDocument = DocumentFactory.getRemoteInstance();
		if (xDocument.getDoc().getId() != null) {
			iDocument.update(new ObjectSingleKey("id", xDocument.getDoc().getId().toString()), xDocument.getDoc());
		} else {
			IObjectPK ipk = iDocument.addnew(xDocument.getDoc());
			if (ipk != null) {
				this.txtdocumentId.setText(ipk.toString());
			} else {
				MsgBox.showInfo("�������");
				return;
			}
		}

		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		QuestionTypeInfo qtTypeInfo = (QuestionTypeInfo) this.prmtpaperType.getValue();
		if (qtTypeInfo == null) {
			MsgBox.showInfo("�ʾ������Ϊ�գ�");
			return;
		}
		String number = this.txtNumber.getText().trim();
		if (number == null || "".equals(number)) {
			MsgBox.showInfo("���ݱ�Ų���Ϊ�գ�");
			return;
		}

		Date startDate = (Date) this.pkstartDate.getValue();
		if (startDate == null) {
			MsgBox.showInfo("�������ڲ���Ϊ�գ�");
			return;
		}
		Date endDate = (Date) this.pkendDate.getValue();
		if (endDate == null) {
			MsgBox.showInfo("��ֹ���ڲ���Ϊ�գ�");
			return;
		}
		if (startDate != null && endDate != null) {
			if (endDate.before(startDate)) {
				MsgBox.showInfo("��ֹ���ڲ���С���������ڣ�");
				return;
			}
		}

		Date bizDate = (Date) this.pkBizDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("ҵ�����ڲ���Ϊ�գ�");
			return;
		}
		String topic = this.txttopric.getText().trim();
		if (topic == null || "".equals(topic)) {
			MsgBox.showInfo("�ʾ����ⲻ��Ϊ�գ�");
			return;
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (QuestionPaperDefineFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("���ݱ�Ų����ظ���");
				SysUtil.abort();
			}
		}
		IDocument iDocument = DocumentFactory.getRemoteInstance();
		if (xDocument.getDoc().getId() != null) {
			iDocument.update(new ObjectSingleKey("id", xDocument.getDoc().getId().toString()), xDocument.getDoc());
		} else {
			IObjectPK ipk = iDocument.addnew(xDocument.getDoc());
			if (ipk != null) {
				this.txtdocumentId.setText(ipk.toString());
			} else {
				MsgBox.showInfo("�������");
				return;
			}
		}

		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.market.QuestionPaperDefineEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionPaperDefineInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperDefineInfo();
		objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		xDocument.setEmptyDoc();
		return objectValue;
	}

	public void onLoad() throws Exception {
		xDocument = new XDocument();
		xScrollPane = new JScrollPane(xDocument);
		btnWorkFlowG.setVisible(false);
		btnCreateFrom.setVisible(false);
		btnSignature.setVisible(false);
		btnViewSignature.setVisible(false);
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		separatorFW6.setVisible(false);
		separatorFW9.setVisible(false);
		btnVoucher.setVisible(false);
		btnDelVoucher.setVisible(false);
		btnCopyFrom.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCopy.setVisible(false);
		btnAuditResult.setVisible(false);

		btnWFViewdoProccess.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);

		kdtEntrys.setVisible(false);

		paperPanel.setLayout(new BorderLayout());
		paperPanel.add(xScrollPane, BorderLayout.CENTER);

		super.onLoad();
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.prmtpaperType.setRequired(true);
		this.txtNumber.setRequired(true);
		this.pkstartDate.setRequired(true);
		this.pkendDate.setRequired(true);
		this.pkBizDate.setRequired(true);
		this.txttopric.setRequired(true);
		this.txttopric.setMaxLength(80);

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			questItems = 0;
		} else {
			questItems = this.editData.getEntrys().size();
		}
		answerResult.setVisible(false);
		handleCodingRule();
		this.bizPromptLastUpdateUser.setEnabled(false);
		this.dateLastUpdateTime.setEnabled(false);
		this.bizPromptLastUpdateUser.setValue(SysContext.getSysContext().getCurrentUserInfo());
		this.dateLastUpdateTime.setValue(new Timestamp(System.currentTimeMillis()));
	}

	public void action_DeleteSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectInfo dThree = xDocument.getSelectedSubjectInfo();
		if (dThree == null) {
			MsgBox.showWarning("����ѡ����Ŀ��Ȼ���ٽ���ɾ��.");
			return;
		}
		xDocument.deleteDSubject(dThree);

	}

	public com.kingdee.eas.base.uiframe.client.UINewFrame getFrame(Container c) {
		UINewFrame uf = null;
		while (!(c instanceof JFrame) && c.getParent() != null) {
			c = c.getParent();
		}
		if (c instanceof UINewFrame) {
			uf = (UINewFrame) c;
		}
		return uf;
	}

	public void action_NewSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
		dThree.setXFontName("Dialog");
		dThree.setTopic("");
		dThree.setXFontSize(12);
		dThree.setColumnCount(1);
		dThree.setSubjectNumber(0);// �����������ݣ����������������С��1
		dThree.setShowNumber(1);
		dThree.setIsShowNumber(1);
		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		DocumentItemInfo dItem = new DocumentItemInfo();
		dItem.setXFontSize(12);
		dItem.setXFontName("Dialog");
		dThree.getItems().add(dItem);
		dThree.copyNewToOld();// ����һ��,��������ֵ���бȽ�

		Map myContext = new HashMap();
		myContext.put(SubjectViewUI3.SUBJECT_INFO_NAME, dThree);
		myContext.put(SubjectViewUI3.INTEM_INFO_NAME, dThree.getItems().get(0));

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// "8bCpR/BZR76BNqY/Rc6wMhel7x8="

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(SubjectViewUI3.class.getName(), uiContext, myContext, OprtState.ADDNEW);
		uiWindow.show();//
		if (dThree.isValueChange() && ((SubjectViewUI3) uiWindow.getUIObject()).isClosingWidOk()) {
			questItems++;
			dThree.setShowNumber(questItems);
			xDocument.insertDSubject(dThree);

		}

	}

	public void action_SelectSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
		dThree.setXFontName("Dialog");
		dThree.setTopic("");
		dThree.setXFontSize(12);
		dThree.setColumnCount(1);
		dThree.setSubjectNumber(0);// �����������ݣ����������������С��1
		dThree.setShowNumber(1);
		dThree.setIsShowNumber(1);
		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();
//		dThree.getItems().add(dItem);
		dThree.copyNewToOld();// ����һ��,��������ֵ���бȽ�

		Map myContext = new HashMap();
		myContext.put(StoreSubjectListUI.can_ToQuestion_Paper, dThree);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// 
		QuestionTypeInfo questionType = (QuestionTypeInfo) this.prmtpaperType.getValue();
		uiContext.put("questionType", questionType);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(StoreSubjectListUI.class.getName(), uiContext, myContext, OprtState.VIEW);
		uiWindow.show();//
		if (dThree.isValueChange()) {
			questItems++;
			dThree.setShowNumber(questItems);
			xDocument.insertDSubject(dThree);
		}

		if (true)
			return;

		// ����ѡ��һ���
		if (f7 == null) {
			f7 = new QuestionSelectPromptBox();
		}
		f7.show();
		Object[] obj = (Object[]) f7.getData();// objΪ���صĶ��󼯺�

		DocumentSubjectInfo[] infos = parseOriginalQuestion(obj);
		for (int i = 0; infos != null && i < infos.length; i++) {
			infos[i].setSubjectNumber(xDocument.getDoc().getMaxSubjectSerialNumber() + 1);
			xDocument.insertDSubject(infos[i]);

		}

	}

	public void action_UpdateSubject_actionPerformed(ActionEvent e) throws Exception {
		DocumentSubjectInfo dThree = xDocument.getSelectedSubjectInfo();
		if (dThree == null) {
			MsgBox.showWarning("����ѡ����Ŀ��Ȼ���ٽ��б��.");
			return;
		}
		dThree.copyNewToOld();// ���ǰ���ȱ���һ��
		Map myContext = new HashMap();
		myContext.put(SubjectViewUI3.SUBJECT_INFO_NAME, dThree);
		myContext.put(SubjectViewUI3.INTEM_INFO_NAME, null);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// "8bCpR/BZR76BNqY/Rc6wMhel7x8="

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(SubjectViewUI3.class.getName(), uiContext, myContext, OprtState.ADDNEW);
		uiWindow.show();
		if (((SubjectViewUI3) uiWindow.getUIObject()).isClosingWidOk()) {
			if (dThree.isValueChange()) {
				xDocument.updateDSubject(dThree);
			}
		} else {
			dThree.resetValue();
		}
	}

	/**
	 * ���ҳ������
	 */
	protected void paperProperty_actionPerformed(ActionEvent e) throws Exception {
		//
		DocumentInfo docInfo = xDocument.getDoc();
		docInfo.copyNewToOld();
		Map dataObject = new HashMap();
		dataObject.put("docInfo", docInfo);
		// ��ʾ����
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(DocumentPaperPropertiyUI.class.getName(), new UIContext(this), dataObject, null);
		uiWindow.show();
		if (docInfo.isValueChange()) {
			// ������Ա仯�ˣ���ô��Ҫ�������� xDocument
			xDocument.setDoc(docInfo);
			// �������κβ���
		}
	}

	public DocumentSubjectInfo[] parseOriginalQuestion(Object[] objs) throws Exception {
		DocumentSubjectInfo[] dSubject = null;
		if (objs != null) {
			dSubject = new DocumentSubjectInfo[objs.length];
			for (int i = 0; i < objs.length; i++) {
				dSubject[i] = parseOriginalQuestion(objs[i]);
			}
		}

		return dSubject;
	}

	int maxSubjectNum = 1;

	public DocumentSubjectInfo parseOriginalQuestion(Object obj) throws Exception {
		DocumentSubjectInfo info = new DocumentSubjectInfo();
		QuestionBaseInfo qbi = (QuestionBaseInfo) obj;
		String style = qbi.getQuestionStyle().getValue();
		if (QuestionStyle.ASKQUESTION_VALUE == style || QuestionStyle.FILLBLANKQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		} else if (QuestionStyle.SINGLESELECTQUESTION_VALUE == style || QuestionStyle.JUDGEQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
			SelectQuestionInfo si = (SelectQuestionInfo) com.kingdee.eas.fdc.market.QuestionHelp.getQuestion(qbi.getId().toString(), qbi.getQuestionStyle().getValue());
			SelectQuestionItemCollection item = si.getItem();
			if (item != null && item.size() > 0) {
				DocumentItemInfo itemInfo = new DocumentItemInfo();
				info.getItems().add(itemInfo);
				for (int i = 0; i < item.size(); i++) {
					SelectQuestionItemInfo sItemInfo = item.get(i);
					DocumentOptionInfo optionInfo = new DocumentOptionInfo();
					optionInfo.setTopic(sItemInfo.getName());
					optionInfo.setXFontName("Dialog");
					optionInfo.setXFontSize(14);
					itemInfo.getOptions().add(optionInfo);
				}
			}

		} else if (QuestionStyle.MUTISELECTQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
			SelectQuestionInfo si = (SelectQuestionInfo) com.kingdee.eas.fdc.market.QuestionHelp.getQuestion(qbi.getId().toString(), qbi.getQuestionStyle().getValue());
			SelectQuestionItemCollection item = si.getItem();
			if (item != null && item.size() > 0) {
				DocumentItemInfo itemInfo = new DocumentItemInfo();
				info.getItems().add(itemInfo);
				for (int i = 0; i < item.size(); i++) {
					SelectQuestionItemInfo sItemInfo = item.get(i);
					DocumentOptionInfo optionInfo = new DocumentOptionInfo();
					optionInfo.setTopic(sItemInfo.getName());
					optionInfo.setXFontName("Dialog");
					optionInfo.setXFontSize(14);
					itemInfo.getOptions().add(optionInfo);
				}
			}
		}
		return info;
	}

	String documentId = null;

	/**
	 * ��documentId����󣬵��ô˺�����������ʾ���ʾ�����
	 * 
	 * @param documentId
	 *            �ʾ�PK
	 */
	public void changeDocumentId(String documentId) throws Exception {
		if (this.documentId == null || !this.documentId.equals(documentId)) {
			this.documentId = documentId;
			if (documentId != null) {
				IDocument iDocument = DocumentFactory.getRemoteInstance();
				DocumentInfo info = (DocumentInfo) iDocument.getDocumentInfo(" where id = '" + documentId + "' order by subjects.subjectNumber");// subjects

				if (info != null) {
					sortDocumentElements(info);
					xDocument.setDoc(info);
				} else {
					xDocument.setEmptyDoc();
				}
			} else {
				xDocument.setEmptyDoc();
			}
		}
	}

	/**
	 * ���ʾ��е���Ϣ������Ŀ���顢ѡ��������Ŀ֮�䲻��Ҫ������ΪY�������ݿ��ѯ����ʱ�Ѿ�ORDER BY��
	 * 
	 * @param info
	 */
	public static void sortDocumentElements(DocumentInfo info) {
		DocumentSubjectCollection subjectCollection = info.getSubjects();
		int subCount = subjectCollection.size();
		for (int subIndex = 0; subIndex < subCount; subIndex++) {
			DocumentSubjectInfo subjectInfo = subjectCollection.get(subIndex);
			DocumentItemCollection itemCollection = subjectInfo.getItems();
			int itemCount = itemCollection.size();
			List itemList = new ArrayList();
			// ѭ����ȡ���飬��������ڲ�ѡ��
			for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
				DocumentItemInfo itemInfo = itemCollection.get(itemIndex);
				itemList.add(itemInfo);
				DocumentOptionCollection optionCollection = itemInfo.getOptions();
				int optionCount = optionCollection.size();
				List optionList = new ArrayList();
				// ѭ����ȡѡ��
				for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
					DocumentOptionInfo optionInfo = optionCollection.get(optionIndex);
					optionList.add(optionInfo);
				}
				// ѡ������
				Collections.sort(optionList);
				optionCollection.clear();
				for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
					optionCollection.add((DocumentOptionInfo) optionList.get(optionIndex));
				}
			}
			// ��������
			Collections.sort(itemList);
			itemCollection.clear();
			for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
				itemCollection.add((DocumentItemInfo) itemList.get(itemIndex));
			}
		}
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    } 
    protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(objValue, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
							prepareNumber(objValue, number);
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
    
    public void setNumberTextEnabled(){
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
	 //ͨ����������ȡ���롣������á�
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }
           
        } catch (Exception err) {
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }

    protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

}