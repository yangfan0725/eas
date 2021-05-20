/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.DocumentFactory;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.IDocument;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.client.XDocument;
import com.kingdee.eas.fdc.market.client.XPrintPreviewDialog;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomer;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuestionPaperAnswerEditUI extends AbstractQuestionPaperAnswerEditUI {
	private static final Logger logger = CoreUIObject.getLogger(QuestionPaperAnswerEditUI.class);

	private XDocument xDoc;
	JScrollPane xScrollPane;

	/**
	 * output class constructor
	 */
	public QuestionPaperAnswerEditUI() throws Exception {
		super();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		initOldData(getDataObject());
		super.loadFields();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);

	}

	protected void f7SellProject_dataChanged(DataChangeEvent e) throws Exception {
		SellProjectInfo info = (SellProjectInfo) e.getNewValue();
		if (info != null) {
			String id = info.getId().toString();

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);

			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", id));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAPPLY_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
			filter.setMaskString(" #0 and (#1 or #2)");
		}

		if (this.f7Customer.getValue() != null && info != null) {
			String sellProjectID = info.getId().toString();
			String customerID = ((FDCCustomerInfo) this.f7Customer.getValue()).getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectID));
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", customerID));
			filter.setMaskString("#0 and #1");
			view.setFilter(filter);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		QuestionPaperAnswerInfo info = (QuestionPaperAnswerInfo) this.editData;
		if (info != null) {
			QuestionPaperDefineInfo paperDefineInfo = (QuestionPaperDefineInfo) this.txtquestionPaper.getValue();
			if (paperDefineInfo != null) {
				info.setQuestionPaper(paperDefineInfo);
			}
			PersonInfo personInfo = (PersonInfo) this.prmtpersion.getValue();
			if (personInfo != null) {
				info.setPersion(personInfo);
			}
			Date bizDate = (Date) this.pkBizDate.getValue();
			if (bizDate != null) {
				info.setBizDate(bizDate);
			}
			Date inputDate = (Date) this.pkinputDate.getValue();
			if (inputDate != null) {
				info.setInputDate(inputDate);
			}
			String number = this.txtNumber.getText();
			if (number != null && number != "") {
				info.setNumber(number);
			}
			String description = this.txtDescription.getText();
			if (description != null && description != "") {
				info.setDescription(description);
			}
			SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject.getValue();
			if (sellProject != null) {
				info.setSellProject(sellProject);
			}
			FDCCustomerInfo fdcCustomerInfo = (FDCCustomerInfo) this.f7Customer.getValue();
			if (fdcCustomerInfo != null) {
				info.setCustomer(fdcCustomerInfo);
			}

		}

		if (this.editData.getOrgUnit() == null)
			this.editData.setOrgUnit((FullOrgUnitInfo) SysContext.getSysContext().getCurrentOrgUnit());
	}

	protected void prmtCommerceChance_dataChanged(DataChangeEvent e) throws Exception {

	}

	protected void f7Purchase_dataChanged(DataChangeEvent e) throws Exception {
		PurchaseInfo info = (PurchaseInfo) e.getNewValue();

		if (info != null) {
			RoomInfo room = info.getRoom();
			if (room != null) {
				if (room.getId() != null) {
					BOSUuid id = room.getId();
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("number");
					selColl.add("buildingArea");
					selColl.add("buildPrice");
					selColl.add("standardTotalAmount");
					selColl.add("roomModel.name");

					room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(id), selColl);
				}

			}

			if (info.getPayType() != null) {
				BOSUuid id = info.getPayType().getId();
				SelectorItemCollection selColl = new SelectorItemCollection();
				selColl.add("id");
				selColl.add("name");

				SHEPayTypeInfo type = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(id), selColl);
			}

		}

	}

	protected void f7Customer_dataChanged(DataChangeEvent e) throws Exception {
		FDCCustomerInfo info = (FDCCustomerInfo) e.getNewValue();

		if (info != null && this.f7SellProject.getValue() != null) {
			String sellProjectID = ((SellProjectInfo) this.f7SellProject.getValue()).getId().toString();
			String customerID = info.getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectID));
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", customerID));
			filter.setMaskString("#0 and #1");
			view.setFilter(filter);
		}
	}

	public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.f7SellProject.getValue();
		if (sellProjectInfo == null) {
			FDCMsgBox.showWarning("������ѡ��Ӫ����Ŀ��");
			return;
		}

		getUIContext().put("sellProject", sellProjectInfo);
		Map linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
		UIContext uiContext = new UIContext(this);
		if (linkedCus != null) {
			uiContext.putAll(linkedCus);
			uiContext.put("sellProject", sellProjectInfo);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}

		/*
		 * UIContext uiContext = new UIContext(this);
		 * uiContext.put(CustomerEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE); //
		 * ����UI������ʾ IUIWindow uiWindow =
		 * UIFactory.createUIFactory(UIFactoryName
		 * .MODEL).create(CustomerEditUI.class.getName(), uiContext, null,
		 * "ADDNEW"); uiWindow.show();
		 * 
		 * CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		 * FDCCustomerInfo cus = (FDCCustomerInfo)
		 * cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA); if
		 * (cus != null) { this.f7Customer.setValue(cus); }
		 */
	}

	public void onLoad() throws Exception {
		this.setPreferredSize(new Dimension(1000, 600));
		super.onLoad();
		// �����ʾ�Ľ���
		myInit();
		rePaintXDocument();
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionCreateFrom.setVisible(false);

		this.txtquestionPaper.setRequired(true);
		this.prmtpersion.setRequired(true);
		this.pkBizDate.setRequired(true);
		this.pkinputDate.setRequired(true);
		this.txtNumber.setRequired(true);

		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);

		// ���õ�ǰӪ����Ա�ܿ����Ŀͻ�
		UserInfo currUsr = SysContext.getSysContext().getCurrentUserInfo();
		this.f7Customer.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(currUsr));

		this.prmtSheCustomer.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				setPromtCustomerFilterView(QuestionPaperAnswerEditUI.this.f7SellProject);
			}
		});

		// ��Ŀ
		this.f7SellProject.setEntityViewInfo(NewCommerceHelper.getPermitProjectView());
		setQuestionPaperView();

		if (this.getUIContext().get("commerceChance") != null) {
			CommerceChanceInfo info = (CommerceChanceInfo) this.getUIContext().get("commerceChance");
			if (info != null) {
				this.f7SellProject.setValue(info.getSellProject());
				this.f7Customer.setValue(info.getFdcCustomer());
				this.pkBizDate.setValue(new Date());
				this.pkinputDate.setValue(new Date());
			}
		} else if (com.kingdee.eas.fdc.market.client.CustomerManagementUI.FROMTRACK.equals(this.getUIContext().get("from"))) {
			TrackRecordInfo info = (TrackRecordInfo) this.getUIContext().get("trackRecord");
			if (info != null) {
				this.f7SellProject.setValue(info.getSellProject());
				this.f7Customer.setValue(info.getHead());
				this.pkBizDate.setValue(new Date());
				this.pkinputDate.setValue(new Date());
				// �����Ϲ���������Ϊ��

			}
		} else if (this.getUIContext().get("fdcCustomer") != null) {
			FDCCustomerInfo info = (FDCCustomerInfo) this.getUIContext().get("fdcCustomer");
			if (info != null) {
				this.f7SellProject.setValue(null);
				this.f7Customer.setValue(info);
				this.pkBizDate.setValue(new Date());
				this.pkinputDate.setValue(new Date());

				EntityViewInfo purchaseView = new EntityViewInfo();
				FilterInfo purchaseFilter = new FilterInfo();
				purchaseView.setFilter(purchaseFilter);
				purchaseFilter.getFilterItems().add(new FilterItemInfo("customerInfo.customer.id", info.getId().toString()));
				purchaseFilter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAPPLY_VALUE));
				purchaseFilter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
				purchaseFilter.setMaskString(" #0 and (#1 or #2)");
				EntityViewInfo commerceChanceView = new EntityViewInfo();
				FilterInfo commerceChanceFilter = new FilterInfo();
				commerceChanceFilter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", info.getId().toString()));
				commerceChanceView.setFilter(commerceChanceFilter);
			}
		}
		// ����ʱ��û���������ܣ��������ں�̨���ύ���ܹ���
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		this.btnSave.setText("����");
		this.menuItemSave.setText("����");
		this.menuSubmitOption.setVisible(false);
		handleCodingRule();
		initOldData(this.editData);
		
		//wyh �����̻�
		if (this.getUIContext().get("commerceChance") != null) {
			CommerceChanceInfo ccni = (CommerceChanceInfo)this.getUIContext().get("commerceChance");
			editData.setCommerceChance(ccni);
		}
	}

	protected void initOldData(IObjectValue dataObject) {
		// super.initOldData(dataObject);
	}

	private void setQuestionPaperView() {
		String ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if (ctrlUnitId != null && !"".equals(ctrlUnitId)) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("CU.id", ctrlUnitId));
			view.setFilter(filter);
			if (this.txtquestionPaper.getEntityViewInfo() != null) {
				this.txtquestionPaper.getEntityViewInfo().setFilter(filter);
			} else {
				this.txtquestionPaper.setEntityViewInfo(view);
			}

		}

	}

	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
	}

	/**
	 * 
	 * ��������ʼ���ʾ����--�Ҳ�հ�����
	 */
	private void myInit() {
		xDoc = new XDocument();
		xDoc.setEnableRightMouseEvent(false);// �����Ҽ�����
		xScrollPane = new JScrollPane(xDoc);
		this.docRootPanel.add(xScrollPane, java.awt.BorderLayout.CENTER);
		// �����ʾ�ı༭���¼�
		this.txtquestionPaper.addChangeListener(new QuestionPaperDefineBoxChanged());

	}

	private void rePaintXDocument() {
		try {
			QuestionPaperDefineInfo paperDefineInfo = (QuestionPaperDefineInfo) txtquestionPaper.getValue();
			if (paperDefineInfo == null || paperDefineInfo.getDocumentId() == null) {
				changeDocumentId(null);
			} else {
				changeDocumentId(paperDefineInfo.getDocumentId());
			}
		} catch (Exception e) {
			logger.error(e);
			xDoc.setEmptyDoc();
			MsgBox.showError("���ݿ�����ʱ�������Ժ��ٲ�����");
		}
	}

	String documentId = null;

	public void changeDocumentId(String documentId) throws Exception {
		if (this.documentId == null || !this.documentId.equals(documentId)) {
			this.documentId = documentId;
			if (this.documentId != null) {
				IDocument iDocument = DocumentFactory.getRemoteInstance();
				DocumentInfo info = (DocumentInfo) iDocument.getDocumentInfo("where id = '" + documentId
						+ "' order by subjects.subjectNumber, subjects.items.itemNumber,subjects.items.options.optionNumber");
				QuestionPaperDefineEditUI.sortDocumentElements(info);// ����˳��
				if (info != null) {
					storeFields();
					xDoc.setDoc(info);
					xDoc.setAnswerCollection(((QuestionPaperAnswerInfo) this.editData).getEntrys());
				} else {
					xDoc.setEmptyDoc();
				}
			} else {
				xDoc.setEmptyDoc();
			}
			// xDoc.updateUI();
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		dateValid();
		if(this.getUIContext().get("HashMap") != null){
			HashMap hp = (HashMap)this.getUIContext().get("HashMap");
			String key = "question_"+hp.get("question_num");
			hp.put(key, fillEditData());
			disposeUIWindow();
			return;
		}
		actionSave_actionPerformed(e);
//		editData.setCustomer(item)
		super.actionSubmit_actionPerformed(e);
		QuestionPaperAnswerInfo qpa = (QuestionPaperAnswerInfo) this.editData;
		qpa.getEntrys().clear();
		qpa.getEntrys().addCollection(xDoc.getAnswerCollection());

		loadFields();

//		�ʾ������Ϣ
//		if (this.getUIContext().get("fnumber") != null) {
//			String sql = "update T_MAR_QuestionPaperAnswer set fshecustomerid = '" + this.getUIContext().get("fnumber") + "' where fid = '" + editData.getId().toString() + "'";
//			FDCSQLBuilder builder = new FDCSQLBuilder();
//			builder.appendSql(sql);
//			builder.execute();
//			this.getUIContext().put("fquestionid", editData.getId().toString());
//		}
	}

	//������� wyh
	public QuestionPaperAnswerInfo fillEditData(){
		this.editData.setSellProject((SellProjectInfo)this.f7SellProject.getValue());
		this.editData.setSheCustomer((SHECustomerInfo)this.prmtSheCustomer.getValue());
		this.editData.setNumber(this.txtNumber.getText());
		this.editData.setDescription(this.txtDescription.getText());
		this.editData.setCommerceChance((CommerceChanceInfo)this.getUIContext().get("commerceChance"));
		QuestionPaperAnswerInfo qpa = (QuestionPaperAnswerInfo)editData;
		qpa.getEntrys().clear();
		qpa.getEntrys().addCollection(xDoc.getAnswerCollection());
		return qpa;
	}
	
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		dateValid();
		super.actionSave_actionPerformed(e);
		QuestionPaperAnswerInfo qpa = (QuestionPaperAnswerInfo) this.editData;
		qpa.getEntrys().clear();
		qpa.getEntrys().addCollection(xDoc.getAnswerCollection());
		loadFields();
	}

	private void dateValid() throws Exception {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;
		if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
			if (this.txtNumber.getText() == null || "".equals(this.txtNumber.getText().trim()) || " ".equals(this.txtNumber.getText().trim())) {
				MsgBox.showInfo("���ݱ�Ų���Ϊ�գ�");
				SysUtil.abort();
			}
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", this.txtNumber.getText().trim()));
			if (QuestionPaperAnswerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("���ݱ�Ų����ظ���");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", this.txtNumber.getText().trim()));
			filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
			if (QuestionPaperAnswerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("���ݱ�Ų����ظ���");
				SysUtil.abort();
			}
		}

		QuestionPaperDefineInfo paperDefineInfo = (QuestionPaperDefineInfo) this.txtquestionPaper.getValue();
		if (paperDefineInfo == null) {
			MsgBox.showInfo("�ʾ��岻��Ϊ�գ�");
			SysUtil.abort();
		}
		/*
		 * PersonInfo person = (PersonInfo) this.prmtpersion.getValue(); if
		 * (person == null) { MsgBox.showInfo("ҵ��Ա����Ϊ�գ�"); SysUtil.abort(); }
		 */
		Date bizDate = (Date) this.pkBizDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("ҵ�����ڲ���Ϊ�գ�");
			SysUtil.abort();
		}
		Date inputDate = (Date) this.pkinputDate.getValue();
		if (inputDate == null) {
			MsgBox.showInfo("�ʾ���д���ڲ���Ϊ�գ�");
			SysUtil.abort();
		}

		// src start �ʾ������У��
		DocumentInfo documentInfo = xDoc.getDoc();
		if (documentInfo != null) {
			// �����ʾ����
			DocumentSubjectCollection dsCollection = documentInfo.getSubjects();
			for (int subIndex = 0; subIndex < dsCollection.size(); subIndex++) {
				DocumentSubjectInfo subj = dsCollection.get(subIndex);
				if (subj.getIsRequired() == 1) {// ������
					// �����ʾ����
					DocumentItemCollection itemCollection = subj.getItems();
					for (int itemIndex = 0; itemIndex < itemCollection.size(); itemIndex++) {
						DocumentItemInfo item = itemCollection.get(itemIndex);
						// �����ʾ�ѡ��
						int answer = 0;
						DocumentOptionCollection optionCollection = item.getOptions();
						items: for (int optionIndex = 0; optionIndex < optionCollection.size(); optionIndex++) {
							DocumentOptionInfo option = optionCollection.get(optionIndex);
							// ��ȡ��
							QuestionPaperAnswerEntryCollection answerEntryCollection = xDoc.getAnswerCollection();
							for (int j = 0; j < answerEntryCollection.size(); j++) {
								QuestionPaperAnswerEntryInfo info = (QuestionPaperAnswerEntryInfo) answerEntryCollection.get(j);
								// �жϱ������Ƿ���
								if (answerEntryCollection.size() > 0 && option.getId().toString().equals(info.getOption())) {
									answer++;
									// ������ж�
									if (subj.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL)) {

									} else {// ����������ѭ��
										break items;// ����ѭ��
									}
								}
							}
						}
						if (subj.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL)) {
							if (answer != optionCollection.size()) {
								MsgBox.showInfo("�ʾ������û����д��������*����ĿΪ���");
								SysUtil.abort();
							}
						} else {
							if (answer == 0) {
								MsgBox.showInfo("�ʾ�û����д��������*����ĿΪ���");
								SysUtil.abort();
							}
						}

					}
				}
			}
		}
		// src end
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory.getRemoteInstance();
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

		return new com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo();
		objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setPersion(SysContext.getSysContext().getCurrentUserInfo().getPerson());

		objectValue.setOrgUnit((FullOrgUnitInfo) SysContext.getSysContext().getCurrentOrgUnit());
		/**
		 * �����Ŀ by renliang at 2011-7-11
		 */
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo sellproject = (SellProjectInfo) this.getUIContext().get("sellProject");
			objectValue.setSellProject(sellproject);
		}
		if (this.getUIContext().get("isFromCommerceChance") != null) {
			CommerceChanceInfo commer = (CommerceChanceInfo) this.getUIContext().get("isFromCommerceChance");
			objectValue.setCommerceChance(commer);
		}
		if (this.getUIContext().get("cluesCustomer") != null) {
			CluesManageInfo cluesInfo = (CluesManageInfo) this.getUIContext().get("cluesCustomer");
			this.prmtSheCustomer.setEnabled(false);
			objectValue.setCluesManage(cluesInfo.getId().toString());
		}//
		if (this.getUIContext().get("sheCustomer") != null) {
			SHECustomerInfo custInfo = (SHECustomerInfo) this.getUIContext().get("sheCustomer");
			objectValue.setSheCustomer(custInfo);
		}

		try {
			Date currDate = new Date(FDCCommonServerHelper.getServerTimeStamp().getTime());
			objectValue.setBizDate(currDate);
			objectValue.setInputDate(currDate);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return objectValue;
	}

	class QuestionPaperDefineBoxChanged implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			// �ػ����ʾ�
			QuestionPaperAnswerEditUI.this.rePaintXDocument();
		}

	}

	protected OrgType getMainBizOrgType() {
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

	public void setNumberTextEnabled() {
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

	// ͨ����������ȡ���롣������á�
	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (iCodingRuleManager.isExist(caller, orgId)) {
				String number = "";
				if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(caller, orgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(caller, orgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							// ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
							number = iCodingRuleManager.getNumber(caller, orgId);
						}
					} else {
						// ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.readNumber(caller, orgId);
					}
				} else {
					// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
					number = iCodingRuleManager.getNumber(caller, orgId);
				}

				// ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
				prepareNumber(caller, number);
				if (iCodingRuleManager.isModifiable(caller, orgId)) {
					// ����������û����޸�
					setNumberTextEnabled();
				}
				return;
			}

		} catch (Exception err) {
			// ��ȡ�����������ֿ����ֹ�������룡
			handleCodingRuleError(err);

			// �ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
			setNumberTextEnabled();
		}

		// �ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
		setNumberTextEnabled();
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		//src start
		//this.txtNumber.setText("");
		//src end
		this.txtquestionPaper.setData(null);
		this.f7Governor.setData(null);
		this.pkBizDate.setValue(null);
		this.pkinputDate.setValue(null);
		this.txtDescription.setText("");
		super.actionAddNew_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		XPrintPreviewDialog xpd = new XPrintPreviewDialog(getFrame(this), "", true, xDoc);
		xpd.setVisible(true);
		xpd.setVisible(false);
		xpd.dispose();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		AttributeSet attributeSet = new HashAttributeSet();
		attributeSet.add(OrientationRequested.LANDSCAPE);
		// ��ȡ���д�ӡ����
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(docFlavor, attributeSet);
		if (printServices == null || printServices.length == 0) {
			FDCMsgBox.showWarning("�Ҳ�����ӡ�������飡");
			return;
		}

		// ��ȡĬ�ϴ�ӡ����
		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		// printRequestAttributeSet.add(new PrinterResolution(72, 72,
		// PrinterResolution.DPI));
		printRequestAttributeSet.add(xDoc.getMediaPrintableArea());
		printRequestAttributeSet.toArray();
		// ��ʾ��ӡ�Ի���
		PrintService selection = ServiceUI.printDialog(null, 200, 200, printServices, defaultPrintService, docFlavor, printRequestAttributeSet);
		if (selection == null) {
			// ȡ����ӡ
			return;
		}
		PrinterResolution printerResolution = (PrinterResolution) selection.getDefaultAttributeValue(PrinterResolution.class);
		printerResolution.getFeedResolution(PrinterResolution.DPI);
		printRequestAttributeSet.add(printerResolution);
		DocAttributeSet das = new HashDocAttributeSet();
		Doc doc = new SimpleDoc(xDoc, docFlavor, das);
		doc.getAttributes();

		// if(true) return;

		DocPrintJob job = selection.createPrintJob();

		job.getAttributes();
		try {
			job.print(doc, printRequestAttributeSet);
		} catch (PrintException pe) {
			logger.error(pe);
			MsgBox.showError("��ӡ���̳���.");
		}
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

	/** ������ѡ��ҵ�񵥾�f7��ֵ�����ƿͻ�f7�Ĺ������� */
	private void setPromtCustomerFilterView(KDBizPromptBox f7SellProject) {
		SellProjectInfo spInfo = (SellProjectInfo) f7SellProject.getValue();
		try {
			if (spInfo != null) {
				while (spInfo.getLevel() != 1)
					spInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo("select * where id = '" + spInfo.getParent().getId() + "' ");
			}
			UserInfo currInfo = SysContext.getSysContext().getCurrentUserInfo();
			prmtSheCustomer.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(spInfo, currInfo));
			prmtSheCustomer.getQueryAgent().resetRuntimeEntityView();
			prmtSheCustomer.setRefresh(true);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sic =super.getSelectors();
		sic.add("CU.*");
		return sic;
	}
}