/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.BelongSystemEnum;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.fdc.market.SchemeTypeEnum;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillListUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;
/**
 * output class name
 */
public class TrackRecordEditUI extends AbstractTrackRecordEditUI {
	private static final Logger logger = CoreUIObject.getLogger(TrackRecordEditUI.class);
	public final static String FROMTRACK = "fromtrack";

	// private Set billRoomSet = null; //��ѡ����������ķ���id����

	/**
	 * output class constructor
	 */
	public TrackRecordEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();

		setComboTrackResult((MoneySysTypeEnum) this.comboSysType.getSelectedItem());
		attachListeners();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

		// �������ز˵��͹��߰�ť
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAudit.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionCopy.setVisible(false);

		this.actionAttachment.setVisible(true);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			this.pkEventDate.setValue(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());

			this.prmtRetionBill.setEnabled(false); //
			this.comboTrackResult.setEnabled(false);
		}

		// ���õ�ǰӪ����Ա�ܿ����Ŀͻ�
		this.prmtCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(this.editData.getSellProject(),SysContext.getSysContext().getCurrentUserInfo()));
		// ���õ�ǰӪ����Ա�ܿ�������Ŀ
		this.prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		// ���õ�ǰӪ����Ա�ܿ������̻�
		this.prmtCommerceChance.setEntityViewInfo(CommerceHelper.getPermitCommerceChanceView());
		this.prmtSaleMan.setEntityViewInfo(CommerceHelper.getPermitSalemanView(this.editData.getSellProject()));

		setCommerceChanceView();

		TrackRecordEnum trackRecord = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackRecord = (TrackRecordEnum) trackValue;
		setTheTrackResultQuery(trackRecord);

		if (this.prmtCommerceChance.getValue() != null) {
			this.comboTrackResult.setEnabled(true);
		}

		this.txtTrackTxt.setMaxLength(80);

		this.prmtRetionBill.setDisplayFormat("name"); //
		this.prmtRetionBill.setEditFormat("$number$");
		this.prmtRetionBill.setCommitFormat("$number$");

		// ������� �������������ģ���ȥ����ť��
		String actionView = (String) this.getUIContext().get("ActionView");
		if (actionView != null && actionView.equals("OnlyView")) {
		//	this.toolBar.setVisible(false);
			
			//��Ҫ����"�ʾ���д"��"�˳�"��������ť by lww 12172010
			this.btnAddNew.setVisible(false);
//			this.btnEdit.setVisible(false);
//			this.btnSave.setVisible(false);
//			this.btnRemove.setVisible(false);
			this.btnFirst.setVisible(false);
			this.btnPre.setVisible(false);
			this.btnNext.setVisible(false);
			this.btnLast.setVisible(false);
}

		setGroupFilter();
		setMarketManageView();
		
		EntityViewInfo evi= new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		evi.setFilter(filter);
		
		HashMap ctx=new HashMap();
		ctx.put("EntityViewInfo", evi);
		ctx.put("EnableMultiSelection", new Boolean(false));
		ctx.put("HasCUDefaultFilter", new Boolean(true));
		this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeQuery");	
		this.prmtClassify.setDisplayFormat("$name$");		
        this.prmtClassify.setEditFormat("$number$");		
	    this.prmtClassify.setCommitFormat("$number$");
	    this.prmtClassify.setEntityViewInfo(evi);
	    
	    this.actionQuestionPrint.setVisible(false);
	}

	/**
	 * ����"�ʾ���д"ͼ�� 02122010
	 */
	protected void initWorkButton() {
		super.initWorkButton();
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
	}
	/**
	 * ҳ��״̬Ϊ"�鿴"��"�༭"�£��ʾ���д��ť����
	 */
	protected void initDataStatus() {
		super.initDataStatus();
//		this.actionQuestionPrint.setEnabled(!OprtState.ADDNEW.equals(this.getOprtState()));
		this.actionQuestionPrint.setEnabled(OprtState.VIEW.equals(this.getOprtState()) || 
					OprtState.EDIT.equals(this.getOprtState()));
	}
		
	private void prmtCustomerSetFilter() {

	}

	/**
	 * �����ʾ���дaction 01122010
	 */
	public void actionQuestionPrint_actionPerformed(ActionEvent e)
			throws Exception {
		if(isModify()){
			FDCMsgBox.showInfo("���ȱ��浥����Ϣ!");
			abort();
		}
		super.actionQuestionPrint_actionPerformed(e);
		
		if(this.editData.getId()!=null){//�����жϵ�ǰ���� �Ƿ���������ݿ�
			UIContext uiContext = new UIContext(this);
			uiContext.put("trackRecord", editData.clone());
			uiContext.put("from", FROMTRACK);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
					.getName(), uiContext, null, OprtState.ADDNEW);
			curDialog.show();
		}
	}

	private void setGroupFilter() {
		SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
		if (sp == null) {
			return;
		}
		String spId = sp.getId().toString();
		// �����¼����͵ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtEventType, spId, "�¼�����", "commerArch");
		// ���ýӴ���ʽ�ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtReceptionType, spId, "�Ӵ���ʽ", "CustomerArch");

	}

	/**
	 * ���ø����̻���view
	 */
	private void setCommerceChanceView() {
		// if(this.oprtState.equals(OprtState.ADDNEW) ||
		// this.oprtState.equals(OprtState.EDIT))
		// this.prmtCommerceChance.setValue(null);

		FDCCustomerInfo custInfo = (FDCCustomerInfo) this.prmtCustomer.getValue();
		SellProjectInfo projectInfo = (SellProjectInfo) this.prmtSellProject.getValue();
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();

		String custId = "null";
		String proId = "null";

		if (custInfo != null)
			custId = custInfo.getId().toString();
		if (projectInfo != null)
			proId = projectInfo.getId().toString();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", sysType == null ? "null" : sysType.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", custId));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", proId));
		filter.getFilterItems().add(new FilterItemInfo("commerceStatus", CommerceStatusEnum.Finish.getValue(), CompareType.NOTEQUALS));
		view.setFilter(filter);
		this.prmtCommerceChance.setEntityViewInfo(view);
	}

	/**
	 * ����Ӫ�����view
	 */
	private void setMarketManageView() {
		SellProjectInfo projectInfo = (SellProjectInfo) this.prmtSellProject.getValue();
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();

		String proId = "null";
		if (projectInfo != null)
			proId = projectInfo.getId().toString();
		String sysTypeValue = "null";
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys))
			sysTypeValue = MoneySysTypeEnum.SALEHOUSESYS_VALUE;
		else if (sysType.equals(MoneySysTypeEnum.TenancySys))
			sysTypeValue = MoneySysTypeEnum.TENANCYSYS_VALUE;
		else if (sysType.equals(MoneySysTypeEnum.ManageSys))
			sysTypeValue = MoneySysTypeEnum.MANAGESYS_VALUE;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("belongSystem", sysTypeValue));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", proId));
		view.setFilter(filter);
		this.prmtMarketManage.setEntityViewInfo(view);
	}

	protected void prmtCustomer_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtCustomer_dataChanged(e);
		if (CommerceHelper.isF7DataChanged(e)) {
			this.prmtCommerceChance.setValue(null);
			setCommerceChanceView();

			TrackRecordEnum trackRecord = null;
			Object trackValue = this.comboTrackResult.getSelectedItem();
			if (trackValue instanceof TrackRecordEnum)
				trackRecord = (TrackRecordEnum) trackValue;
			setTheTrackResultQuery(trackRecord);
		}
	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		if (CommerceHelper.isF7DataChanged(e)) {
			this.prmtCommerceChance.setValue(null);
			setCommerceChanceView();
			setMarketManageView();

			TrackRecordEnum trackRecord = null;
			Object trackValue = this.comboTrackResult.getSelectedItem();
			if (trackValue instanceof TrackRecordEnum)
				trackRecord = (TrackRecordEnum) trackValue;
			setTheTrackResultQuery(trackRecord);
		
		}
		setGroupFilter();
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		vertifyInput();
	}

	public void vertifyInput() throws Exception {
		CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
		FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtCustomer.getValue();

		StringBuffer buff = new StringBuffer();
		if (this.txtNumber.isVisible() && this.txtNumber.isEnabled() && this.txtNumber.isEditable()) {
			if (StringUtils.isEmpty(this.txtNumber.getText()))
				buff.append("���벻��Ϊ��!\n");
		}
		// if(this.prmtSellProject.getValue()==null)
		// buff.append("������Ŀ���ܿ�!\n");
		if (this.comboSysType.getSelectedItem() == null)
			buff.append("����ϵͳ���ܿ�!\n");
		if (customer == null)
			buff.append("�ͻ����ܿ�!\n");
		if (this.pkEventDate.getValue() == null)
			buff.append("�������ڲ��ܿ�!\n");
		if (this.prmtReceptionType.getValue() == null) {
			buff.append("�Ӵ���ʽ���ܿ�!\n");
		}

		if (comChance != null) {
			/*
			 * ���̻�����Ҳ�����޸����ɹ� 20090223 TrackRecordEnum trackResult = null; Object
			 * trackValue = this.comboTrackResult.getSelectedItem();
			 * if(trackValue instanceof TrackRecordEnum) trackResult =
			 * (TrackRecordEnum)trackValue;
			 * 
			 * String relationBillId = this.txtRelationContract.getText();
			 * if(trackResult==null || relationBillId==null ||
			 * relationBillId.trim().equals("")){
			 * buff.append("�����̻���Ϊ�յ������,�����ɹ��͹������ݶ�����Ϊ��!\n"); }
			 */

			if (customer.getId() != null && comChance.getFdcCustomer() != null && comChance.getFdcCustomer().getId() != null) {
				if (!customer.getId().equals(comChance.getFdcCustomer().getId()))
					buff.append("�������ͻ��͸����̻���Ŀͻ���һ��!\n");
			}
		} else {
			TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.comboTrackType.getSelectedItem();
			if (trackType != null && trackType.equals(TrackRecordTypeEnum.Intency)) {
				buff.append("'����'ʱ�̻����ܿ�!\n");
			}
		}

		TrackRecordEnum trackResult = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackResult = (TrackRecordEnum) trackValue;
		if (trackResult != null) {
			if (this.prmtRetionBill.getValue() == null) {
				buff.append("�и����ɹ�������£�����ѡ���Ӧ��'��������'��");
			}
		}

		// ���и����ɹ�ʱУ�� ͬһ���ݲ��ܱ�ͬһ�ͻ���θ�������
		if (this.prmtRetionBill.getValue() != null && customer != null) {
			String relationContractID = this.txtRelationContract.getText();
			if (!FDCHelper.isEmpty(relationContractID)) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", customer.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("relationContract", relationContractID.trim()));
				// ������޸ģ����ж�Ҫ�ѵ�ǰ������¼�ų�
				if (this.editData.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
				}
				// Ԥ�����Ϲ����ܻ����ͬһ���Ϲ���
				if (trackResult != null) {
					if (trackResult.equals(TrackRecordEnum.DestineApp)) {
						filter.getFilterItems().add(new FilterItemInfo("trackResult", TrackRecordEnum.DESTINEAPP_VALUE));
					} else if (trackResult.equals(TrackRecordEnum.PurchaseApp)) {
						filter.getFilterItems().add(new FilterItemInfo("trackResult", TrackRecordEnum.PURCHASEAPP_VALUE));
					}
				}
				if (TrackRecordFactory.getRemoteInstance().exists(filter)) {
					buff.append("�õ����ѱ��˿ͻ��������ù��������ظ�����!");
				}
			}
		}

		if (!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
			// return;
		}

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
		FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtCustomer.getValue();

		if (customer == null || customer.getId() == null)
			return;

		/***
		 * �������еķ�д�߼���ȫ��Ӧ��Ǩ�Ƶ��������ˣ�����RPC���� TODO
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", customer.getId().toString()));
		boolean isExist = TrackRecordFactory.getRemoteInstance().exists(filter);
		SelectorItemCollection selectColl = new SelectorItemCollection();
		selectColl.add(new SelectorItemInfo("lastTrackDate"));
		customer.setLastTrackDate(editData.getEventDate());
		if (!isExist) {
			// ��һ����������ʱ��д�ͻ������еĸ����ĽӴ���ʽ
			selectColl.add(new SelectorItemInfo("receptionType"));
			customer.setReceptionType((ReceptionTypeInfo) this.prmtReceptionType.getValue());
		}

		FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);

		super.actionSave_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());

		//��"����"��ť����Ҫ��"�ʾ���д"��ť����
//		this.actionQuestionPrint.setEnabled(true);
		
		// ��д�̻��������Ϲ���
		setSincerCommer();

		// CommerceStatusEnum ��������ʱ��д�ͻ������۸����׶κ����޸����׶�.ͬʱ��д�����̻��Ľ׶�
		// �ø����ɹ��ĵ���߼�ȥ��д
		TrackRecordEnum trackResult = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackResult = (TrackRecordEnum) trackValue;

		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.comboTrackType.getSelectedItem();

		// ���¿ͻ��ĸ����׶�
		updateCustomerTrackPhase(customer, sysType, trackType, trackResult);
		// �����̻��ĸ����׶�
		if (comChance == null || comChance.getId() == null)
			return;

		updateCommChanceTrackPhase(comChance, sysType, trackType, trackResult);
	}

	// ����Ӹ�����ʱ����������ɹ��ǳ����Ϲ������ó����Ϲ�����û��ѡ����̻��Ļ���д����̻��������Ϲ���
	protected void setSincerCommer() throws EASBizException, BOSException {
		if (TrackRecordEnum.SincerityPur.equals(this.comboTrackResult.getSelectedItem())) {
			if (this.prmtRetionBill.getValue() != null) {
				SincerityPurchaseInfo sincer = (SincerityPurchaseInfo) this.prmtRetionBill.getValue();
				if (sincer.getCommerceChance() == null) {
					CommerceChanceInfo commerInfo = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
					SelectorItemCollection selectColl = new SelectorItemCollection();
					selectColl.add(new SelectorItemInfo("commerceChance"));
					sincer.setCommerceChance(commerInfo);
					SincerityPurchaseFactory.getRemoteInstance().updatePartial(sincer, selectColl);
				}
			}
		}
	}

	/**
	 * ���¿ͻ��ĸ����׶� --�����µĸ�����¼�ĸ����ɹ�ȥ��д
	 * 
	 * @param customer
	 *            �ͻ�
	 * @param sysType
	 *            ����ϵͳ
	 * @param trackType
	 *            �������� (������Ǳ�ڡ�����)
	 * @param trackResult
	 *            �����ɹ� (�����Ϲ�,Ԥ������,�Ϲ�����,ǩԼ����,�˷�����,��������)
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateCustomerTrackPhase(FDCCustomerInfo customer, MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) throws BOSException, EASBizException {
		if (customer == null)
			return;
		if (sysType == null)
			return;
		// ȷ����¥ϵͳ��Ӧ������¥�ĸ��ٽ��������ϵͳ��Ӧ�������޵ĸ������
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys) && trackResult != null && !trackResult.getValue().startsWith("S"))
			return;
		if (sysType.equals(MoneySysTypeEnum.TenancySys) && trackResult != null && !trackResult.getValue().startsWith("T"))
			return;
		// �Ѹ������ת��Ϊ��Ӧ���̻�״̬
		CommerceStatusEnum thisCommStatus = convertToCommStatus(sysType, trackType, trackResult);
		if (thisCommStatus == null)
			return;
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			CommerceStatusEnum custSaleTrack = customer.getSaleTrackPhase();
			if (compareCommerceStatus(sysType, thisCommStatus, custSaleTrack) > 0) {
				SelectorItemCollection selectColl = new SelectorItemCollection();
				selectColl.add(new SelectorItemInfo("saleTrackPhase"));
				customer.setSaleTrackPhase(thisCommStatus);
				FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);
			}
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			CommerceStatusEnum custTenancyTrack = customer.getTenancyTrackPhase();
			if (compareCommerceStatus(sysType, thisCommStatus, custTenancyTrack) > 0) {
				SelectorItemCollection selectColl = new SelectorItemCollection();
				selectColl.add(new SelectorItemInfo("tenancyTrackPhase"));
				customer.setTenancyTrackPhase(thisCommStatus);
				FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);
			}
		}
	}

	// �����̻�״̬֮��ıȽ�
	// ����Clew Ǳ��Latency ����Intent ����Sincerity Ԥ��SaleDestine �Ϲ�SalePurchase
	// ǩԼSaleSign ����TenancySign ��ֹFinish
	// ��¥ "Clew,Latency,Intent,Sincerity,SaleDestine,SalePurchase,SaleSign,"
	// ����"Clew,Latency,Intent,TenancySign,"
	// �������״̬������ͬһϵͳ�еģ���Ĭ�����
	public static int compareCommerceStatus(MoneySysTypeEnum sysType, CommerceStatusEnum sorStatus, CommerceStatusEnum orgStatus) {
		if (sysType == null)
			return 0;
		if (sorStatus == null && orgStatus == null)
			return 0;
		if (sorStatus == null)
			return -1;
		else if (orgStatus == null)
			return 1;
		if (sorStatus.equals(orgStatus))
			return 0;

		String statusString = "";
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			statusString = CommerceStatusEnum.CLEW_VALUE + "," + CommerceStatusEnum.LATENCY_VALUE + "," + CommerceStatusEnum.INTENT_VALUE + "," + CommerceStatusEnum.SINCERITY_VALUE + ","
					+ CommerceStatusEnum.SALEDESTINE_VALUE + "," + CommerceStatusEnum.SALEPURCHASE_VALUE + "," + CommerceStatusEnum.SALESIGN_VALUE + ",";
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			statusString = CommerceStatusEnum.CLEW_VALUE + "," + CommerceStatusEnum.LATENCY_VALUE + "," + CommerceStatusEnum.INTENT_VALUE + "," + CommerceStatusEnum.TENANCYSIGN_VALUE + ",";
		}
		if (statusString.indexOf(sorStatus.getValue() + ",") < 0 || statusString.indexOf(orgStatus.getValue() + ",") < 0)
			return 0; // ������ͬһϵͳ��Ĭ�����

		return statusString.indexOf(sorStatus.getValue() + ",") - statusString.indexOf(orgStatus.getValue() + ",");
	}

	/**
	 * �����̻��ĸ����׶�
	 * 
	 * @param comChance
	 *            �̻�
	 * @param sysType
	 * @param trackType
	 * @param trackResult
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateCommChanceTrackPhase(CommerceChanceInfo comChance, MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) throws BOSException,
			EASBizException {
		if (comChance == null)
			return;
		if (sysType == null)
			return;
		// ȷ����¥ϵͳ��Ӧ������¥�ĸ��ٽ��������ϵͳ��Ӧ�������޵ĸ������
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys) && trackResult != null && !trackResult.getValue().startsWith("S"))
			return;
		if (sysType.equals(MoneySysTypeEnum.TenancySys) && trackResult != null && !trackResult.getValue().startsWith("T"))
			return;
		// ȷ���̻�������ϵͳ�͵�ǰ������ϵͳһ��
		if (comChance.getSysType() == null || !comChance.getSysType().equals(sysType))
			return;
		// �Ѹ������ת��Ϊ��Ӧ���̻�״̬
		CommerceStatusEnum thisCommStatus = convertToCommStatus(sysType, trackType, trackResult);
		if (thisCommStatus == null)
			return;
		if (compareCommerceStatus(sysType, thisCommStatus, comChance.getCommerceStatus()) > 0) {
			SelectorItemCollection selectColl = new SelectorItemCollection();
			selectColl.add(new SelectorItemInfo("commerceStatus"));
			comChance.setCommerceStatus(thisCommStatus);
			CommerceChanceFactory.getRemoteInstance().updatePartial(comChance, selectColl);
		}
	}

	/**
	 * �Ѹ������ת��Ϊ��Ӧ���̻�״̬
	 * 
	 * @return
	 */
	public static CommerceStatusEnum convertToCommStatus(MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) {
		CommerceStatusEnum thisCommStatus = null;
		if (sysType == null || trackType == null)
			return null;

		thisCommStatus = CommerceStatusEnum.Clew;
		if (trackType.equals(TrackRecordTypeEnum.Latency))
			thisCommStatus = CommerceStatusEnum.Latency;
		else if (trackType.equals(TrackRecordTypeEnum.Intency)) {
			if (trackResult == null)
				thisCommStatus = CommerceStatusEnum.Intent;
			else {
				if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
					if (trackResult.equals(TrackRecordEnum.SincerityPur))
						thisCommStatus = CommerceStatusEnum.Sincerity;
					else if (trackResult.equals(TrackRecordEnum.DestineApp))
						thisCommStatus = CommerceStatusEnum.SaleDestine;
					else if (trackResult.equals(TrackRecordEnum.PurchaseApp))
						thisCommStatus = CommerceStatusEnum.SalePurchase;
					else if (trackResult.equals(TrackRecordEnum.SignApp))
						thisCommStatus = CommerceStatusEnum.SaleSign;
					// �˷������Ҫ���⴦���� ,������δ������
					// else if(trackResult.equals(TrackRecordEnum.CancelApp))
					// thisCommStatus = CommerceStatusEnum.SaleFinish;
				} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
					if (trackResult.equals(TrackRecordEnum.TenancyApp))
						thisCommStatus = CommerceStatusEnum.TenancySign;
				}
			}
		}

		return thisCommStatus;
	}

	protected void prmtCommerceChance_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtCommerceChance_dataChanged(e);

		if (CommerceHelper.isF7DataChanged(e)) {
			if (e.getOldValue() != null) {
				this.comboTrackResult.setSelectedItem(null);
				this.prmtRetionBill.setValue(null);
				this.txtRelationContract.setText("");
			}

			if (e.getNewValue() != null) {
				// CommerceChanceInfo newCom =
				// (CommerceChanceInfo)e.getNewValue();
				this.comboTrackResult.setEnabled(true);
			} else {
				this.comboTrackResult.setEnabled(false);
				this.txtRelationContract.setEnabled(false);
			}
		}
	}

	// ��������ϵͳ���˸����ɹ�
	private void setComboTrackResult(MoneySysTypeEnum sysType) {
		TrackRecordEnum selectItem = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			selectItem = (TrackRecordEnum) trackValue;

		this.comboTrackResult.removeAllItems();
		this.comboTrackResult.addItem("");
		if (sysType == null)
			return;
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			this.comboTrackResult.addItems(TrackRecordEnum.getEnumList().toArray());
			this.comboTrackResult.removeItem(TrackRecordEnum.TenancyApp);
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			this.comboTrackResult.addItem(TrackRecordEnum.TenancyApp);
		}
		if (selectItem != null)
			this.comboTrackResult.setSelectedItem(selectItem);
	}

	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);

		MoneySysTypeEnum sysType = (MoneySysTypeEnum) e.getItem();

		this.prmtCommerceChance.setValue(null);
		setCommerceChanceView();

		setComboTrackResult(sysType);

		setMarketManageView();
	}

	protected void comboTrackResult_itemStateChanged(ItemEvent e) throws Exception {
		super.comboTrackResult_itemStateChanged(e);
		TrackRecordEnum trackRec = null;
		Object itemValue = e.getItem(); // ����Ϊ""
		if (!itemValue.equals(this.comboTrackResult.getSelectedItem()))
			return;

		this.txtRelationContract.setText("");
		this.prmtRetionBill.setValue(null);

		if (itemValue instanceof TrackRecordEnum)
			trackRec = (TrackRecordEnum) itemValue;
		// prmtRetionBill
		if (trackRec != null) {
			setTheTrackResultQuery(trackRec);
			this.prmtRetionBill.setRequired(true);
		} else {
			this.prmtRetionBill.setEnabled(false);
		}

	}

	protected void prmtRetionBill_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtRetionBill_dataChanged(e);

		// ����Ǵ��ݽ����Ĺ�������,���ô���id�ĸ�ֵ����
		String billNumberStr = (String) this.getUIContext().get("BillNumberString");
		if (billNumberStr != null)
			return;

		CoreBaseInfo coreBase = (CoreBaseInfo) e.getNewValue();
		if (coreBase != null) {
			this.txtRelationContract.setText(coreBase.getId().toString());

			/*
			 * ���ü�鷿���Ƿ����̻������򷿼���,���Կ���ȥ�� TrackRecordEnum trackRec = null; Object
			 * trackValue = this.comboTrackResult.getSelectedItem();
			 * if(trackValue instanceof TrackRecordEnum) trackRec =
			 * (TrackRecordEnum)trackValue;
			 * 
			 * if(trackRec!=null) { billRoomSet = new HashSet();
			 * if(trackRec.equals(TrackRecordEnum.SincerityPur)) {
			 * //�����Ϲ�ʱ,ѡ����̻��Ŀͻ������Ϲ��� SincerityPurchaseInfo sinPur =
			 * (SincerityPurchaseInfo)this.prmtRetionBill.getValue();
			 * if(sinPur!=null && sinPur.getRoom()!=null)
			 * billRoomSet.add(sinPur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.DestineApp)) {
			 * //Ԥ������,ѡ����̻��Ŀͻ�Ԥ�������Ԥ������״̬���Ϲ��� PurchaseInfo pur =
			 * (PurchaseInfo)this.prmtRetionBill.getValue(); if(pur!=null &&
			 * pur.getRoom()!=null)
			 * billRoomSet.add(pur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.PurchaseApp))
			 * {//�Ϲ�����,ѡ����̻��ͻ��Ϲ�������Ϲ��������Ϲ��� PurchaseInfo pur =
			 * (PurchaseInfo)this.prmtRetionBill.getValue(); if(pur!=null &&
			 * pur.getRoom()!=null)
			 * billRoomSet.add(pur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.SignApp))
			 * {//ǩԼ����,ѡ���ύ״̬,�����л���������ǩԼ��. RoomSignContractInfo sign =
			 * (RoomSignContractInfo)this.prmtRetionBill.getValue();
			 * if(sign!=null && sign.getRoom()!=null)
			 * billRoomSet.add(sign.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.CancelApp))
			 * {//�˷�����,ѡ����̻��ͻ����˷��� QuitRoomInfo quit =
			 * (QuitRoomInfo)this.prmtRetionBill.getValue(); if(quit!=null &&
			 * quit.getRoom()!=null)
			 * billRoomSet.add(quit.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.TenancyApp))
			 * {//��������,ѡ����̻��ͻ��������ͬ TenancyBillInfo tenBill =
			 * (TenancyBillInfo)this.prmtRetionBill.getValue();
			 * if(tenBill!=null) { EntityViewInfo view = new EntityViewInfo();
			 * FilterInfo filter = new FilterInfo();
			 * filter.getFilterItems().add(new
			 * FilterItemInfo("tenancy.id",tenBill.getId().toString()));
			 * view.setFilter(filter); TenancyRoomEntryCollection tenRooms =
			 * TenancyRoomEntryFactory
			 * .getRemoteInstance().getTenancyRoomEntryCollection(view); for(int
			 * i=0;i<tenRooms.size();i++){ TenancyRoomEntryInfo tenRoom =
			 * tenRooms.get(i);
			 * billRoomSet.add(tenRoom.getRoom().getId().toString()); } } } }
			 */
		} else {
			this.txtRelationContract.setText("");
			// billRoomSet = new HashSet();
		}
	}

	/**
	 * ���ù������ݵ�f7�ؼ���query
	 */
	private void setTheTrackResultQuery(TrackRecordEnum trackRec) {
		this.contRelationContract.setVisible(false);
		if (trackRec != null) {
			this.contRelationContract.setVisible(true);
			String customerId = "null";
			FDCCustomerInfo fdcCust = (FDCCustomerInfo) this.prmtCustomer.getValue();
			if (fdcCust != null)
				customerId = fdcCust.getId().toString();
			else {// ��ѡ����̻�����Ӧ�Ŀͻ�
				CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
				if (comChance != null)
					customerId = comChance.getFdcCustomer().getId().toString();
			}

			String billId = this.txtRelationContract.getText();
			if (billId != null && billId.trim().equals("")) // billID��Ϊ�յ�ʱ��
				// Ҫ�����Ӧ�ĵ���
				// �����Ѹ�ֵ��f7�ؼ���
				billId = null;

			if (billId != null)
				this.contRelationContract.setEnabled(false);
			this.prmtRetionBill.setDisplayFormat("$number$"); // ��Щ���ݻ�����û�����ƣ�
			// ֻ���ñ�Ŵ���
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
			String sellProIdStr = sp==null?"null":sp.getId().toString();
			if (trackRec.equals(TrackRecordEnum.SincerityPur)) { // �����Ϲ�ʱ,
				// ѡ����̻��Ŀͻ������Ϲ���
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");
				filter.getFilterItems().add(new FilterItemInfo("customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("sincerityState", SincerityPurchaseStateEnum.QUITNUM_VALUE, CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				if (billId != null) {
					try {
						SincerityPurchaseInfo billInfo = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("�����ѱ�ɾ��");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.DestineApp) || trackRec.equals(TrackRecordEnum.PurchaseApp)) { // Ԥ������
				// ,
				// �Ϲ�����
				// ,
				// ѡ����̻��Ŀͻ�Ԥ�������Ԥ������״̬���Ϲ���
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("customerInfo.customer.id", customerId));
				if (trackRec.equals(TrackRecordEnum.DestineApp)) {	
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASEAPPLY_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASECHECK_VALUE));
				} else {
					filter.setMaskString("#0 and (#1 or #2)");
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAPPLY_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
				}

				if (billId != null) {
					try {
						PurchaseInfo billInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("�����ѱ�ɾ��");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.SignApp)) {//ǩԼ����,ѡ���ύ״̬,
				// �����л���������ǩԼ��
				// .
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSignContractQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("purchase.customerInfo.customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

				if (billId != null) {
					try {
						RoomSignContractInfo billInfo = RoomSignContractFactory.getRemoteInstance().getRoomSignContractInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("�����ѱ�ɾ��");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.CancelApp)) {// �˷�����,
				// ѡ����̻��ͻ����˷���
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuitRoomQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("purchase.customerInfo.customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

				if (billId != null) {
					try {
						QuitRoomInfo billInfo = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("�����ѱ�ɾ��");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.TenancyApp)) {// ��������,
				// ѡ����̻��ͻ��������ͬ
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("tenCustomerList.fdcCustomer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITED_VALUE));

				if (billId != null) {
					try {
						TenancyBillInfo billInfo = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("�����ѱ�ɾ��");
					}
				}
			}
			this.prmtRetionBill.setEntityViewInfo(view);
			this.prmtRetionBill.setEnabled(true);

		}
	}

	protected void btnAddNewBill_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddNewBill_actionPerformed(e);

		TrackRecordEnum trackRec = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackRec = (TrackRecordEnum) trackValue;

		if (trackRec != null) {
			UIContext uiContext = new UIContext(this);
			String classUIName = null;
			if (trackRec.equals(TrackRecordEnum.SincerityPur)) {
				classUIName = SincerityPurchaseEditUI.class.getName(); // �����Ϲ�ʱ,
				// �����Ϲ���
				uiContext.put("sellProject", this.prmtSellProject.getValue());
				uiContext.put("commerceChance", this.prmtCommerceChance.getValue());
				uiContext.put("customer", this.prmtCustomer.getValue());
			} else if (trackRec.equals(TrackRecordEnum.DestineApp)) {
				classUIName = PurchaseEditUI.class.getName(); // Ԥ������,�Ϲ���
				uiContext.put("sellProject", this.prmtSellProject.getValue());
				uiContext.put("isPrePurchase", new Boolean(true));
			} else if (trackRec.equals(TrackRecordEnum.PurchaseApp)) {
				classUIName = PurchaseEditUI.class.getName(); // �Ϲ�����,�Ϲ���
				uiContext.put("sellProject", this.prmtSellProject.getValue());
			} else if (trackRec.equals(TrackRecordEnum.SignApp)) {
				classUIName = RoomSignContractListUI.class.getName(); //ǩԼ����,ǩԼ��
				// .
			} else if (trackRec.equals(TrackRecordEnum.CancelApp)) {
				classUIName = QuitRoomListUI.class.getName(); // �˷�����,�˷���
			} else if (trackRec.equals(TrackRecordEnum.TenancyApp)) {
				classUIName = TenancyBillListUI.class.getName(); // ��������,�����ͬ
			}
			if (classUIName != null) {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(classUIName, uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			}
		}

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		this.detachListeners();
		TrackRecordInfo trackInfo = new TrackRecordInfo();

		// ���Դ��̻���������е��øø��ټ�¼ �����̻����ͻ���������Ŀ�������ɹ�������ϵͳ ��������
		CommerceChanceInfo commInfo = (CommerceChanceInfo) this.getUIContext().get("CommerceChance");
		FDCCustomerInfo custInfo = (FDCCustomerInfo) this.getUIContext().get("FdcCustomer");
		SellProjectInfo sellProInfo = (SellProjectInfo) this.getUIContext().get("SellProject");
		TrackRecordEnum trackEnum = (TrackRecordEnum) this.getUIContext().get("TrackRecordEnum");

		// ΪӪ����ṩ
		MarketManageInfo marketManInfo = (MarketManageInfo) this.getUIContext().get("MarketManage");
		if (marketManInfo != null) {
			this.prmtMarketManage.setValue(marketManInfo);
			trackInfo.setMarketManage(marketManInfo);
		}

		String billNumberStr = (String) this.getUIContext().get("BillNumberString");
		String billIdStr = (String) this.getUIContext().get("BillIdString");
		if (commInfo != null) { // ��������̻������������һ��Ϊ'����',���ҿ��Ը����̻�������ϵͳ���ø�����¼������ϵͳ
			trackInfo.setCommerceChance(commInfo);
			trackInfo.setTrackType(TrackRecordTypeEnum.Intency);
			this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Intency);

			if (commInfo.getSysType() != null) {
				trackInfo.setSysType(commInfo.getSysType());
				this.comboSysType.setSelectedItem(commInfo.getSysType());
			}
		} else {
			TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.getUIContext().get("TrackRecordTypeEnum");
			if (trackType != null) {
				trackInfo.setTrackType(trackType);
				this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Intency);
			} else {
				trackInfo.setTrackType(TrackRecordTypeEnum.Clew);
				this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Clew);
			}
			this.prmtCommerceChance.setEnabled(false);

			MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.getUIContext().get("MoneySysTypeEnum");
			if (sysType != null) {
				trackInfo.setSysType(sysType);
				this.comboSysType.setSelectedItem(sysType);
			} else {
				trackInfo.setSysType(MoneySysTypeEnum.SalehouseSys);
				this.comboSysType.setSelectedItem(MoneySysTypeEnum.SalehouseSys);
			}
		}
		if (trackInfo.getTrackType() != null && trackInfo.equals(TrackRecordTypeEnum.Intency)) {
			this.prmtCommerceChance.setEnabled(false);
		}

		if (trackEnum != null) {
			trackInfo.setTrackResult(trackEnum);
			this.comboTrackResult.setSelectedItem(trackEnum);
		} else {
			trackInfo.setTrackResult(null);
			this.comboTrackResult.setSelectedItem(null);
		}

		if (custInfo != null)
			trackInfo.setHead(custInfo);
		if (sellProInfo != null)
			trackInfo.setSellProject(sellProInfo);

		if (billIdStr != null)
			trackInfo.setRelationContract(billIdStr);
		if (billNumberStr != null) {
			this.prmtRetionBill.setValue(billNumberStr);
			this.prmtRetionBill.setEnabled(false);
		}

		UserInfo currUser = SysContext.getSysContext().getCurrentUserInfo();
		trackInfo.setSaleMan(currUser);
		this.prmtSaleMan.setValue(currUser);
		trackInfo.setCreator(currUser);
		this.prmtCreator.setValue(currUser);
		trackInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		this.pkCreateTime.setValue(new Timestamp(System.currentTimeMillis()));

		this.attachListeners();
		return trackInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TrackRecordFactory.getRemoteInstance();
	}

	protected void comboTrackType_itemStateChanged(ItemEvent e) throws Exception {
		// super.comboTrackType_itemStateChanged(e);

		TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) e.getItem();
		if (trackType != null && trackType.equals(TrackRecordTypeEnum.Intency)) {
			this.prmtCommerceChance.setEnabled(true);
			this.prmtCommerceChance.setRequired(true);
		} else {
			this.prmtCommerceChance.setEnabled(false);
		}
		this.prmtCommerceChance.setValue(null);

	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
		this.addDataChangeListener(this.prmtCommerceChance);
		this.addDataChangeListener(this.comboSysType);
		this.addDataChangeListener(this.comboTrackType);
		this.addDataChangeListener(this.comboTrackResult);
		this.addDataChangeListener(this.prmtCustomer);
		this.addDataChangeListener(this.prmtSellProject);
	}

	protected void detachListeners() {
		this.removeDataChangeListener(this.prmtCommerceChance);
		this.removeDataChangeListener(this.comboSysType);
		this.removeDataChangeListener(this.comboTrackType);
		this.removeDataChangeListener(this.comboTrackResult);
		this.removeDataChangeListener(this.prmtCustomer);
		this.removeDataChangeListener(this.prmtSellProject);
	}

	// �ͻ��̻��ܿ�����Ҫ���õ�
	public KDBizPromptBox getCustomerPrmt() {
		return this.prmtCustomer;
	}

	public KDBizPromptBox getSellProjectPrmt() {
		return this.prmtSellProject;
	}

	public KDBizPromptBox getSaleManPrmt() {
		return this.prmtSaleMan;
	}

	public KDBizPromptBox getReceptionTypePrmt() {
		return this.prmtReceptionType;
	}

	public KDComboBox getTrackTypeCombo() {
		return this.comboTrackType;
	}

	public KDComboBox getSysTypeCombo() {
		return this.comboSysType;
	}

	public KDComboBox getTrackResultCombo() {
		return this.comboTrackResult;
	}

	public KDBizPromptBox getCommercePrmt() {
		return this.prmtCommerceChance;
	}

	public TrackRecordInfo getTrackData() {
		return this.editData;
	}

	public TrackRecordInfo getTrackOldData() {
		return this.editData;
	}

	public void initOldDataNull() {
		super.initOldData(null);
	}
}