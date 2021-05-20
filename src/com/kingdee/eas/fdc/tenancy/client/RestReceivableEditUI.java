package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.reportone.r1.print.designer.gui.ScriptViewer.Action;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.IRestReceivable;
import com.kingdee.eas.fdc.tenancy.RestReceivableCollection;
import com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection;
import com.kingdee.eas.fdc.tenancy.RestReceivableFactory;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent;
import com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

public class RestReceivableEditUI extends AbstractRestReceivableEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RestReceivableEditUI.class);
	// ��֯��Ԫ
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public RestReceivableEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
		if (editData != null && editData.getOtherPayList().size() > 0) {
			for (int i = 0; i < editData.getOtherPayList().size(); i++) {
				if (editData.getOtherPayList().get(i).getHead() == null) {
					editData.getOtherPayList().get(i).setHead(
							editData.getTenancyBill());
				}
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		// �Ƿ�����ʵ����������༭��ɾ������
		if (saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(true);
//			this.actionRemove.setEnabled(true);
//			this.actionEdit.setEnabled(true);
//			actionAudit.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			actionAudit.setEnabled(false);
		}
		initTenancyBillF7Filter();
		initRemarkColLen();
		initMoneyDefineCol();
		kdtEntrys_detailPanel.setTitle("Ӧ����ϸ");
		handleCodingRule();
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
	}
	
	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData, currentOrgId)) { // �˴���orgId�벽��1
																						// ��
																						// ��orgIdʱһ�µ�
																						// ��
																						// �ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				txtNumber.setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"
	 * �������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		txtNumber.setText(number);

	}

	protected void setNumberTextEnabled() {

		if (txtNumber != null) {
			// CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();

			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();

			if (currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RestReceivableFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}
	private static final String CANTEDIT = "cantEdit";

	private static final String CANTREMOVE = "cantRemove";
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "���ȱ��浥�ݣ�");
			return;
		}
		if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "��������ˣ����ܱ༭��");
			abort();
		}
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "���ȱ��浥�ݣ�");
			return;
		}
		boolean flag = true;
		if(editData.getOtherPayList() != null){
			for(int i=0;i<editData.getOtherPayList().size();i++){
				BigDecimal actrev = editData.getOtherPayList().get(i).getActRevAmount();
				if(actrev.compareTo(FDCHelper.ZERO)!=0 ){
					flag = false;
					break;
				} 
			}
		}
		if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "��������ˣ�����ɾ����");
			abort();
		}
		if(!flag){
			MsgBox.showInfo(this, "�����Ѿ��տ�ķ�¼���޷�ɾ��");
			abort();
		}
		checkBeforeEditOrRemove(CANTREMOVE);
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * ����������
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "���ȱ��浥�ݣ�");
			return;
		}
		// �õ��ݲ��߹�����������ԭ����˷���
		IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
		if (TenancyBillStateEnum.Saved.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "�����ύ���ݣ�");
			SysUtil.abort();
		} else if (TenancyBillStateEnum.Submitted.equals(editData
				.getBillState())) {
			restRevDao.passAudit(null, editData);
			MsgBox.showInfo(this, "�����ɹ���");
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
			this.onLoad();
		} else {
			MsgBox.showInfo(this, "��ǰ����״̬����������");
			SysUtil.abort();
		}
	}

	/**
	 * ������������
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "���ȱ��浥�ݣ�");
			return;
		}
		// �õ��ݲ��߹�����������ԭ����˷���
		IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
		
		
		
			//if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			
			boolean flag = true;
			/*if (editData.getOtherPayList().size() > 0) {
				for (int i = 0; i < editData.getOtherPayList().size(); i++) {
					if (editData.getOtherPayList().get(i).getActRevAmount() != null) {
						flag = false;
						break;
					}
				}
			}*/
			/**
			 * �տ��ʵ�ս��Ͳ�Ϊ0
			 */
			if(editData.getOtherPayList().size() > 0){
				for(int i=0;i<editData.getOtherPayList().size();i++){
					if(editData.getOtherPayList().get(i).getActRevAmount() != null && editData.getOtherPayList().get(i).getActRevAmount().compareTo(FDCHelper.ZERO)!=0){
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				/**
				 * ����ֱ�Ӵӽ�����ȡ״̬��״̬����û�и���
				 * ��������Ǵ����ݿ��в���һ��
				 */
				checkBillStats(this.editData.getId().toString());
				restRevDao.unpassAudit(null, editData);
				MsgBox.showInfo(this, "�������ɹ���");
				actionUnAudit.setEnabled(false);
				actionAudit.setEnabled(true);
				this.onLoad();
			} else {
				/*checkBillStats(this.editData.getId().toString());
				int i = MsgBox.showConfirm2(this, "�Ѵ����տ���Ƿ�ȷ��Ҫ������");
				if (i == 0) {
					restRevDao.unpassAudit(null, editData);
					MsgBox.showInfo(this, "�������ɹ���");
					actionUnAudit.setEnabled(false);
					actionAudit.setEnabled(true);
					this.onLoad();
				}*/
				MsgBox.showInfo(this, "�Ѵ����տ�����ܷ�����!");
				SysUtil.abort();
			}
	}
	protected void checkBeforeEditOrRemove(String warning) {
		TenancyBillStateEnum state = this.editData.getBillState();
		if (state != null
				&& (state == TenancyBillStateEnum.Auditing || state == TenancyBillStateEnum.Audited )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	/**
	 * ����״̬�ļ��
	 * @param id
	 * @return
	 */
	private boolean checkBillStats(String id){
		boolean result = true;
		try {
			
			IRestReceivable restRevDao = RestReceivableFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
			evi.setFilter(filterInfo);

			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("billState"));

			evi.setSelector(coll);

			RestReceivableCollection collection = restRevDao.getRestReceivableCollection(evi);
		
			if (collection != null && collection.size() > 0) {
				for (int i = 0; i < collection.size(); i++) {
					RestReceivableInfo info = collection.get(i);
					if (!info.getBillState().equals(TenancyBillStateEnum.Audited)) {
						MsgBox.showInfo(this, "��ǰ����״̬���ܷ�������");
						SysUtil.abort();
					}
				}
			} else {
				MsgBox.showWarning(this, "�˼�¼�Ѳ����ڣ�");
				SysUtil.abort();
			}

		} catch (BOSException e) {
			logger.error(e + "��ȡ����Ӧ�յ���:" + id + "״̬ʧ�ܣ�");
		}

		
		return result;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);

		this.toolBar.remove(this.btnAuditResult);

		this.actionAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_audit"));
		this.actionUnAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_unaudit"));
	}

	/**
	 * ���÷�¼��ע�ֶ����볤��Ϊ255
	 */
	private void initRemarkColLen() {
		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		txtRemark.setMinLength(0);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txtRemark);

		kdtEntrys.getColumn("remark").setEditor(editor);

		
		kdtEntrys.getColumn("appAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
	}

	/**
	 * ��ʼ��������ΪF7����
	 */
	private void initMoneyDefineCol() {
		String queryPK = "com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery";

		KDBizPromptBox prmtMoneyDefine = new KDBizPromptBox();
		prmtMoneyDefine.setQueryInfo(queryPK);
		prmtMoneyDefine.setDisplayFormat("$name$");
		prmtMoneyDefine.setCommitFormat("$number$");
		prmtMoneyDefine.setEditFormat("$number$");

		// ��ӹ��ˣ��������Ϊ�����ɽ������ѡ�ΥԼ��������
		prmtMoneyDefine.setEntityViewInfo(getMoneyDefineEVI());

		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(prmtMoneyDefine);
		kdtEntrys.getColumn("moneyDefine").setEditor(editor);
		ObjectValueRender ovr = new ObjectValueRender();
		ovr.setFormat(new BizDataFormat("$name$"));
		kdtEntrys.getColumn("moneyDefine").setRenderer(ovr);

		KDDatePicker kdtEntrys_startDate_DatePicker = new KDDatePicker();
		kdtEntrys_startDate_DatePicker.setVisible(true);
		kdtEntrys_startDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtEntrys_startDate_CellEditor = new KDTDefaultCellEditor(
				kdtEntrys_startDate_DatePicker);
		this.kdtEntrys.getColumn("appDate").setEditor(
				kdtEntrys_startDate_CellEditor);
		this.kdtEntrys.getColumn("actRevDate").setEditor(
				kdtEntrys_startDate_CellEditor);
	}

	/**
	 * ��ȡ�����Ĺ���View
	 */
	protected EntityViewInfo getMoneyDefineEVI() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("moneyType",
		// MoneyTypeEnum.TAXFEE_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6)");
		view.setFilter(filter);
		return view;
	}

	/**
	 * ���޺�ͬ��ӹ��ˣ������
	 */
	protected void initTenancyBillF7Filter() {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", cuId));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.EXECUTING_VALUE));
		evi.setFilter(filter);
		this.prmtTenancyBill.setEntityViewInfo(evi);
	}

	protected IObjectValue createNewData() {
		RestReceivableInfo objectValue = new RestReceivableInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setBillState(TenancyBillStateEnum.Saved);
		objectValue.setBizDate(new Date());
		return objectValue;
	}

	private void verifyNumber() {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", editData.getNumber()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTINCLUDE));
		}
		evi.setFilter(filter);
		try {
			boolean flag = RestReceivableFactory.getRemoteInstance().exists(
					filter);
			if (flag) {
				MsgBox.showInfo(this, "�����ظ������������룡");
				SysUtil.abort();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		//super.verifyInput(e);
		// У�鵥��ͷ
		String number = this.txtNumber.getText();
		if ((number == null || number.trim().length() <= 0)&&this.txtNumber.isEnabled()) {
			showMsg("���ݱ��");
		}
		if(this.txtNumber.isEnabled()){
			verifyNumber();
		}
		Date bizDate = editData.getBizDate();
		if (bizDate == null) {
			showMsg("ҵ������");
		}

		TenancyBillInfo tb = editData.getTenancyBill();
		if (tb == null) {
			showMsg("���޺�ͬ");
		}

		// У���¼
		RestReceivableEntryCollection colls = editData.getEntrys();
		if (kdtEntrys.getRowCount() < 1) {
			MsgBox.showInfo(this, "������¼��һ����¼��Ϣ��");
			SysUtil.abort();
		}
		String pattern = "��¼��{0}���е�{1}����Ϊ�գ�";
		int count = kdtEntrys.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getCell("moneyDefine").getValue() == null) {
				showEntryMsg((i + 1), pattern, "��������");
			}
			if (row.getCell("appDate").getValue() == null) {
				showEntryMsg((i + 1), pattern, "Ӧ������");
			}
			if (row.getCell("startDate").getValue() == null) {
				showEntryMsg((i + 1), pattern, "��ʼ����");
			}
			if (row.getCell("endDate").getValue() == null) {
				showEntryMsg((i + 1), pattern, "��������");
			}
			if (row.getCell("appAmount").getValue() == null) {
				showEntryMsg((i + 1), pattern, "Ӧ�ս��");
			}
		}

	}

	public IObjectPK runSubmit() throws Exception {
		editData.setBillState(TenancyBillStateEnum.Submitted);
		return super.runSubmit();
	}

	protected void showMsg(String msg) {
		MsgBox.showInfo(this, msg + "����Ϊ�գ�");
		SysUtil.abort();
	}

	protected void showEntryMsg(int index, String pattern, String fName) {
		String msg = MessageFormat.format(pattern, new Object[] { "" + index,
				fName });
		MsgBox.showInfo(this, msg);
		SysUtil.abort();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("otherPayList.head.*");
		sic.add("billState");
		return sic;
	}

	/**
	 * �����������Ƿ��Ѿ����տ����ж�Ӧ�ս����д�Ƿ���ȷ
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if (OprtState.EDIT.equals(getOprtState())) {
			int colIndex = e.getColIndex();
			String colKey = this.kdtEntrys.getColumnKey(colIndex);
			IRow row = KDTableUtil.getSelectedRow(kdtEntrys);
			if (row.getCell("actRevAmount").getValue() != null) {
				if ("appAmount".equals(colKey)) {
					if (row.getCell("appAmount").getValue() != null) {
						BigDecimal app = (BigDecimal)row.getCell("appAmount").getValue();
						BigDecimal act = (BigDecimal) row.getCell(
								"actRevAmount").getValue();
						if (app.compareTo(act) < 0) {
							row.getCell("appAmount").setValue(null);
							MsgBox.showInfo(this, "Ӧ�ս���С��ʵ�ս�");
						}
					}
				}
			}
			if (row.getCell("actRevAmount").getValue() != null&&((BigDecimal)row.getCell("actRevAmount").getValue()).compareTo(FDCHelper.ZERO)!=0) {
				if ("moneyDefine".equals(colKey)) {
					MsgBox.showInfo(this, "����Ŀ�Ѿ��տ�������Ʋ����޸ģ�");
					row.getCell("moneyDefine").setValue(e.getOldValue());
				}
			}
		}
	}

	/**
	 * ����������¼������ɾ��ť�����¼�
	 */
	protected void initListener() {
		super.initListener();
		// ��ӷ�¼�����¼�
		kdtEntrys_detailPanel.addAddListener(new IDetailPanelListener() {
			// ����ǰ�����¼�
			public void beforeEvent(DetailPanelEvent event) throws Exception {

			}

			// ���������¼�
			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// ɾ����¼�����¼�
		kdtEntrys_detailPanel.addRemoveListener(new IDetailPanelListener() {

			public void beforeEvent(DetailPanelEvent event) throws Exception {
				IRow row = KDTableUtil.getSelectedRow(kdtEntrys);
				
					if (row.getCell("actRevAmount").getValue() != null&&((BigDecimal)row.getCell("actRevAmount").getValue()).compareTo(FDCHelper.ZERO)!=0) {
						MsgBox.showInfo("�÷�¼�Ѿ���Ӧ�����տ���޷�ɾ����");
						SysUtil.abort();
					}
			}

			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// �����м����¼�
		kdtEntrys_detailPanel.addInsertListener(new IDetailPanelListener() {

			public void beforeEvent(DetailPanelEvent event) throws Exception {

			}

			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// ѡ���м����¼�
		kdtEntrys.addKDTSelectListener(new KDTSelectListener() {
			public void tableSelectChanged(KDTSelectEvent e) {

			}
		});
	}
	
	
	  /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        this.storeFields();
		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			//handleCodingRule();
		}
    }
    
}