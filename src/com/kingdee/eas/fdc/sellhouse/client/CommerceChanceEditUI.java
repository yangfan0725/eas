/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
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
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceEditUI extends AbstractCommerceChanceEditUI {

	private static final Logger logger = CoreUIObject.getLogger(CommerceChanceEditUI.class);

	/**
	 * output class constructor
	 */
	public CommerceChanceEditUI() throws Exception {
		super();
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		CommerceChanceInfo comChanceInfo = new CommerceChanceInfo();

		// ����list��ѡ���Ӫ�����ʺ�������Ŀ
		// UserInfo userSelected =
		// (UserInfo)this.getUIContext().get("userSelected");
		UserInfo currUsr = SysContext.getSysContext().getCurrentUserInfo();
		if (currUsr != null) {
			// this.prmtSaleMan.setValue(currUsr);
			comChanceInfo.setSaleMan(currUsr);
		}

		SellProjectInfo projectSelected = (SellProjectInfo) this.getUIContext().get("projectSelected");
		if (projectSelected != null) {
			// this.prmtSellProject.setValue(projectSelected);
			comChanceInfo.setSellProject(projectSelected);
		}

		// �̻�����,Ĭ�ϵ�ǰ����,��¼,�����޸�.
		comChanceInfo.setCommerceDate(new Date(System.currentTimeMillis()));

		// ָ������ϵͳ Ĭ������¥ϵͳ
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.getUIContext().get("SysType");
		comChanceInfo.setCommerceStatus(CommerceStatusEnum.Intent);
		if (sysType != null) {
			comChanceInfo.setSysType(sysType);
			if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
				this.tblHopedRooms.getColumn("saleStatus").getStyleAttributes().setHided(true);
				this.tblHopedRooms.getColumn("tenancyStatus").getStyleAttributes().setHided(false);
			}
		} else {
			comChanceInfo.setSysType(MoneySysTypeEnum.SalehouseSys);
			this.tblHopedRooms.getColumn("saleStatus").getStyleAttributes().setHided(false);
			this.tblHopedRooms.getColumn("tenancyStatus").getStyleAttributes().setHided(true);
		}

		FDCCustomerInfo fdcCust = (FDCCustomerInfo) this.getUIContext().get("FdcCustomer");
		if (fdcCust != null) {
			comChanceInfo.setFdcCustomer(fdcCust);
			comChanceInfo.setName(fdcCust.getName());
		}

		comChanceInfo.setCommerceIntention(CommerceIntentionEnum.UnKnow);
		comChanceInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		comChanceInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		comChanceInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		comChanceInfo.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		comChanceInfo.setBookedDate(new Date());
		return comChanceInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.tblHopedRooms;
	}

	public void onLoad() throws Exception {
		super.onLoad();
        this.actionCustomerInfo.setVisible(true);
        this.actionCustomerInfo.setEnabled(true);
		this.actionAddTrackRecord.setEnabled(true);
		this.actionModifyTrackRecord.setEnabled(true);
		this.actionDelTrackRecord.setEnabled(true);
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//
//			this.actionAddTrackRecord.setEnabled(false);
//			this.actionModifyTrackRecord.setEnabled(false);
//			this.actionDelTrackRecord.setEnabled(false);
//			this.actionCustomerInfo.setEnabled(false);
//			
//		}

		// �������ز˵��͹��߰�ť
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.actionCopy.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAudit.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);

		this.actionAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);

		this.tblHopedRooms.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		this.txtName.setRequired(true);

		// ���õ�ǰӪ����Ա�ܿ����Ŀͻ�
		UserInfo currUsr = SysContext.getSysContext().getCurrentUserInfo();
		this.prmtFdcCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,currUsr));
		// ��Ŀ
		setSellProjectViewFilter();
		// �����������Ĺ���
		this.prmtSaleMan.setEntityViewInfo(CommerceHelper.getPermitSalemanView(null));

		// ���ñ������
		SHEHelper.setNumberEnabled(this.editData, this.getOprtState(), this.txtNumber);

		// �����û���ʾ����
		setCustomerInfo(this.editData.getFdcCustomer());

		// ��������¼���е�Ԫ��Ϊ�գ�����ʾ����(Ĭ������)
		this.tblHopedRooms.getColumn("unit").getStyleAttributes().setHided(true);
		if (this.tblHopedRooms.getRowCount() > 0) {
			for (int i = 0; i < this.tblHopedRooms.getRowCount(); i++) {
				CommerceRoomEntryInfo roomEntry = (CommerceRoomEntryInfo) this.tblHopedRooms.getRow(i).getUserObject();
				if (roomEntry != null && roomEntry.getRoom() != null && roomEntry.getRoom().getBuildUnit() != null) {
					this.tblHopedRooms.getColumn("unit").getStyleAttributes().setHided(false);
				}
			}
		}

		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.btnAddRoom.setEnabled(false);
			this.btnInsertRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		this.prmtHopedProductType.setEntityViewInfo(view);

		this.tblContract.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		// this.tblContract.getColumn("dealPrice").getStyleAttributes().
		// setHorizontalAlign(HorizontalAlignment.RIGHT);
		// this.tblContract.getColumn("dealPrice").getStyleAttributes().
		// setNumberFormat("%r-[ ]0.2n");
		this.tblContract.getStyleAttributes().setLocked(true);

		this.tblTrackRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		// this.tblTrackRecord.getColumn("billNumber").getStyleAttributes().
		// setHided(true);
		// this.tblTrackRecord.getColumn("createTime").getStyleAttributes().
		// setNumberFormat("yyyy-MM-dd");
		this.tblTrackRecord.getStyleAttributes().setLocked(true);
		this.tblHopedRooms.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblHopedRooms.getStyleAttributes().setLocked(true);
		this.prmtProductDetail.setEntityViewInfo(CommerceHelper.getCUFilterView());

		// ������� �������������ģ���ȥ����ť��
		String actionView = (String) this.getUIContext().get("ActionView");
		if (actionView != null && actionView.equals("OnlyView")) {
			this.toolBar.setVisible(false);
		}
		setGroupFilter();

		addGroupDataListener(prmtFirstPayProportion);
		addGroupDataListener(prmtBuyHouseReason);
		addGroupDataListener(prmtHopedPitch);
		addGroupDataListener(prmtHopedAreaRequirement);
		addGroupDataListener(prmtHopedFloor);
		addGroupDataListener(prmtHopedUnitPrice);

		addGroupDataListener(prmtHopedTotalPrices);
		addGroupDataListener(prmtHopedBuildingProperty);
		addGroupDataListener(prmtHopedRoomModelType);
		addGroupDataListener(prmtHopedSightRequirement);
		addGroupDataListener(prmtHopedDirection);
		addGroupDataListener(prmtHopedProductType);
		
		this.tblQuestion.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblQuestion.setEnabled(false);
		this.actionQuestionPrint.setEnabled(true);
		//this.txtPhoneNumber.setEnabled(false);
	}
	
	public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception {
		
		if(this.editData.getId()!=null){//�����жϵ�ǰ�̻� �Ƿ���������ݿ�
			UIContext uiContext = new UIContext(this);
			uiContext.put("commerceChance", editData);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
					.getName(), uiContext, null, OprtState.ADDNEW);
			curDialog.show();
		}
	}
	
	protected void tblQuestion_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getButton()==1 && e.getClickCount()==2){//˫��
			QuestionPaperAnswerInfo info=(QuestionPaperAnswerInfo)tblQuestion.getRow(e.getRowIndex()).getUserObject();
			if(info == null){
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, info.getId().toString());
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
					.getName(), uiContext, null, OprtState.VIEW);
			curDialog.show();
		}
	}

	private void addGroupDataListener(final KDBizPromptBox f7) {
		f7.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (f7.getValue() == null) {
					return;
				}
				if (CommerceChanceEditUI.this.prmtSellProject.getValue() == null) {
					MsgBox.showInfo("����ѡ��������Ŀ!");
					f7.setValue(null);
				}
			}
		});
	}

	private void setGroupFilter() {
		SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
		if (sp == null) {
			return;
		}
		String spId = sp.getId().toString();
		// �����׸������ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtFirstPayProportion, spId, "�׸�����", "commerArch");
		// �����̻�ԭ��ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtBuyHouseReason, spId, "�̻�ԭ��", "commerArch");
		// ��������ǿ�ȵļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedPitch, spId, "����ǿ��", "commerArch");
		// �����������ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedAreaRequirement, spId, "�������", "commerArch");
		// ��������¥��ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedFloor, spId, "����¥��", "commerArch");
		// �������򵥼۵ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedUnitPrice, spId, "���򵥼�", "commerArch");
		// ���������ܼ۵ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedTotalPrices, spId, "�����ܼ�", "commerArch");
		// �������������ʵļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedBuildingProperty, spId, "��������", "RoomArch");
		// �������������ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedRoomModelType, spId, "�������", "RoomArch");
		// �������򷿼侰�۵ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedSightRequirement, spId, "���侰��", "RoomArch");
		// �������򷿼䳯��ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedDirection, spId, "���䳯��", "RoomArch");
		// ���������Ʒ���͵ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.prmtHopedProductType, spId, "��Ʒ����", "RoomArch");
	}

	private void showTrackRecordAndContract() {
		this.tblTrackRecord.checkParsed();
		this.tblContract.checkParsed();
		this.tblContract.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblTrackRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		this.tblContract.getColumn("dealPrice").getStyleAttributes().setNumberFormat("###,##0.00");

		CommerceChanceInfo commerce = (CommerceChanceInfo) this.editData;
		if (commerce == null || commerce.getFdcCustomer() == null)
			return;

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add("*");
		itemColl.add("head.name");
		itemColl.add("eventType.name");
		itemColl.add("receptionType.name");
		itemColl.add("sellProject.name");
		itemColl.add("creator.name");
		view.setSelector(itemColl);
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sortInfo = new SorterItemInfo("eventDate");
		sortInfo.setSortType(SortType.DESCEND);
		sorter.add(sortInfo);
		view.setSorter(sorter);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", commerce.getFdcCustomer().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("commerceChance.id", commerce.getId().toString()));
		view.setFilter(filter);
		TrackRecordCollection trackRecords = null;
		try {
			trackRecords = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		if (trackRecords == null)
			return;
		/*
		 * �����Ϲ�, Ԥ������ �Ϲ����� ǩԼ���� �˷����� �������� �����Ϲ��� �Ϲ��� ǩԼ�� �˷��� �����ͬ
		 */
		// ��ӵ����ټ�¼���� ����ͳ�Ƴ�ͬ���ͬ���͵ı�Ŵ�
		this.tblTrackRecord.removeRows();
		Set sincerityCIds = new HashSet();
		Set purchaseCIds = new HashSet();
		Set singnCIds = new HashSet();
		Set cancelCIds = new HashSet();
		Set tenancyCIds = new HashSet();
		for (int i = 0; i < trackRecords.size(); i++) {
			TrackRecordInfo trackInfo = (TrackRecordInfo) trackRecords.get(i);

			IRow row = this.tblTrackRecord.addRow();
			row.getCell("number").setValue(trackInfo.getNumber()); //
			row.getCell("eventDate").setValue(trackInfo.getEventDate()); //
			row.getCell("trackName").setValue(trackInfo.getName()); //
			row.getCell("trackEvent").setValue(trackInfo.getEventType());
			row.getCell("trackResult").setValue(trackInfo.getTrackResult());
			row.getCell("trackDes").setValue(trackInfo.getTrackDes());
			row.getCell("id").setValue(trackInfo.getId().toString());
			row.getCell("customer").setValue(trackInfo.getHead().getName());
			row.getCell("receptionType").setValue(trackInfo.getReceptionType());
			row.getCell("sellProject").setValue(trackInfo.getSellProject());
			row.getCell("billNumber").setValue(trackInfo.getRelationContract());
			row.getCell("description").setValue(trackInfo.getDescription());
			row.getCell("creator").setValue(trackInfo.getCreator().getName());
			row.getCell("createTime").setValue(trackInfo.getCreateTime());

			if (trackInfo.getTrackResult() != null && trackInfo.getRelationContract() != null && !trackInfo.getRelationContract().trim().equals("")) {
				if (trackInfo.getTrackResult().equals(TrackRecordEnum.SincerityPur))
					sincerityCIds.add(trackInfo.getRelationContract());
				else if (trackInfo.getTrackResult().equals(TrackRecordEnum.DestineApp) || trackInfo.getTrackResult().equals(TrackRecordEnum.PurchaseApp))
					purchaseCIds.add(trackInfo.getRelationContract());
				else if (trackInfo.getTrackResult().equals(TrackRecordEnum.SignApp))
					singnCIds.add(trackInfo.getRelationContract());
				else if (trackInfo.getTrackResult().equals(TrackRecordEnum.CancelApp))
					cancelCIds.add(trackInfo.getRelationContract());
				else if (trackInfo.getTrackResult().equals(TrackRecordEnum.TenancyApp))
					tenancyCIds.add(trackInfo.getRelationContract());
			}
		}

		// ���Ҷ�Ӧ�ĺ�ͬ�б�
		this.tblContract.removeRows();
		if (sincerityCIds.size() > 0) { // �����Ϲ���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", sincerityCIds, CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("room.number"));
			selector.add(new SelectorItemInfo("customer.name"));
			view.setSelector(selector);
			SincerityPurchaseCollection sincPurColl = null;
			try {
				sincPurColl = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			if (sincPurColl != null) {
				for (int i = 0; i < sincPurColl.size(); i++) {
					SincerityPurchaseInfo sincertyPur = sincPurColl.get(i);
					
					// by tim_gao ԤԼ�źŽṹ�ı� 2010-06-15  ������Ϊ ��¼ �ˣ��պ���ϸ����
					
					String customer =null;
					for(int j= 0 ; j <sincertyPur.getCustomer().size() ; j++){
						customer = sincertyPur.getCustomer().get(j).getCustomer().getName()+";";
					}
					addTblContractRow("�����Ϲ���", sincertyPur.getNumber(), sincertyPur.getName(), sincertyPur.getSincerityState().getAlias(), sincertyPur.getId().toString(),
							SincerityPurchaseEditUI.class.getName(), sincertyPur.getRoom() == null ? "" : sincertyPur.getRoom().getNumber(), sincertyPur.getCustomer() == null ? "" : customer, sincertyPur.getSincerityAmount().toString());
				}
			}
		}

		if (purchaseCIds.size() > 0) { // �Ϲ���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", purchaseCIds, CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("room.number"));
			view.setSelector(selector);
			PurchaseCollection purColl = null;
			try {
				purColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			if (purColl != null) {
				for (int i = 0; i < purColl.size(); i++) {
					PurchaseInfo purInfo = purColl.get(i);
					addTblContractRow("�Ϲ���", purInfo.getNumber(), purInfo.getName(), purInfo.getPurchaseState().getAlias(), purInfo.getId().toString(), PurchaseEditUI.class.getName(), purInfo
							.getRoom() == null ? "" : purInfo.getRoom().getNumber(), purInfo.getCustomerNames(), purInfo.getDealAmount().toString());
				}
			}
		}

		if (singnCIds.size() > 0) { // ǩԼ��
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", singnCIds, CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("room.number"));
			selector.add(new SelectorItemInfo("purchase.customerNames"));
			selector.add(new SelectorItemInfo("purchase.dealAmount"));
			view.setSelector(selector);
			RoomSignContractCollection signConColl = null;
			try {
				signConColl = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			if (signConColl != null) {
				for (int i = 0; i < signConColl.size(); i++) {
					RoomSignContractInfo signInfo = signConColl.get(i);
					// ��Ӧ�Ŀͻ��ͽ�� �����Ӧ���Ϲ�����
					PurchaseInfo purInfo = signInfo.getPurchase();
					String custNames = (purInfo == null ? "" : purInfo.getCustomerNames());
					String dealAmount = (purInfo == null ? "" : purInfo.getDealAmount().toString());
					addTblContractRow("ǩԼ��", signInfo.getNumber(), signInfo.getName(), signInfo.getState().toString(), signInfo.getId().toString(), RoomSignContractEditUI.class.getName(), signInfo
							.getRoom() == null ? "" : signInfo.getRoom().getNumber(), custNames, dealAmount);
				}
			}
		}

		if (cancelCIds.size() > 0) { // �˷���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", cancelCIds, CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("room.number"));
			selector.add(new SelectorItemInfo("purchase.customerNames"));
			selector.add(new SelectorItemInfo("purchase.dealAmount"));
			view.setSelector(selector);
			QuitRoomCollection quitRoomColl = null;
			try {
				quitRoomColl = QuitRoomFactory.getRemoteInstance().getQuitRoomCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			if (quitRoomColl != null) {
				for (int i = 0; i < quitRoomColl.size(); i++) {
					QuitRoomInfo quitRoomInfo = quitRoomColl.get(i);
					// ��Ӧ�Ŀͻ��ͽ�� �����Ӧ���Ϲ�����
					PurchaseInfo purInfo = quitRoomInfo.getPurchase();
					String custNames = (purInfo == null ? "" : purInfo.getCustomerNames());
					String dealAmount = (purInfo == null ? "" : purInfo.getDealAmount().toString());
					addTblContractRow("�˷���", quitRoomInfo.getNumber(), quitRoomInfo.getName(), quitRoomInfo.getState().toString(), quitRoomInfo.getId().toString(), QuitRoomEditUI.class.getName(),
							quitRoomInfo.getRoom().getNumber(), custNames, dealAmount);
				}
			}
		}

		if (tenancyCIds.size() > 0) { // �����ͬ
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", tenancyCIds, CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("purchase.customerNames"));
			selector.add(new SelectorItemInfo("purchase.dealAmount"));
			view.setSelector(selector);
			TenancyBillCollection tenancyBillColl = null;
			try {
				tenancyBillColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			if (tenancyBillColl != null) {
				for (int i = 0; i < tenancyBillColl.size(); i++) {
					TenancyBillInfo tenancyBillInfo = tenancyBillColl.get(i);
					// �����¼�еķ�����봮
					String roomNumbers = "";
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenancyBillInfo.getId().toString()));
					view.setFilter(filter);
					try {
						TenancyRoomEntryCollection roomColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
						for (int j = 0; j < roomColl.size(); j++)
							roomNumbers += (j == 0 ? "" : ",") + roomColl.get(j).getRoom().getNumber();
					} catch (BOSException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
					// �ͻ���¼�еĿͻ����ƴ�
					String cusNames = "";
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenancyBillInfo.getId().toString()));
					view.setFilter(filter);
					try {
						TenancyCustomerEntryCollection cusColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(view);
						for (int j = 0; j < cusColl.size(); j++)
							cusNames += (j == 0 ? "" : ",") + cusColl.get(j).getFdcCustomer().getName();
					} catch (BOSException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
					addTblContractRow("�����ͬ", tenancyBillInfo.getNumber(), tenancyBillInfo.getName(), tenancyBillInfo.getTenancyState().getAlias(), tenancyBillInfo.getId().toString(),
							TenancyBillEditUI.class.getName(), roomNumbers, cusNames, tenancyBillInfo.getDealTotalRent().toString());
				}
			}
		}

	}

	private void addTblContractRow(String type, String number, String name, String status, String id, String uiClassName, String room, String customer, String dealPrice) {
		IRow row = this.tblContract.addRow();
		row.getCell("type").setValue(type);
		row.getCell("number").setValue(number);
		row.getCell("name").setValue(name);
		row.getCell("status").setValue(status);
		row.getCell("id").setValue(id);
		row.getCell("UINAME").setValue(uiClassName);
		row.getCell("room").setValue(room);
		row.getCell("customer").setValue(customer);
		row.getCell("dealPrice").setValue(dealPrice);
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		vertifyInput();
	}

	public void vertifyInput() throws Exception {
		StringBuffer buff = new StringBuffer();
		if (this.comboSysType.getSelectedItem() == null)
			buff.append("����ϵͳ����Ϊ��!\n");
		if (this.prmtFdcCustomer.getValue() == null)
			buff.append("�ͻ�����Ϊ��!\n");
		if (this.prmtSellProject.getValue() == null)
			buff.append("������Ŀ���ܿ�!\n");
		if (this.txtNumber.isVisible() && this.txtNumber.isEnabled() && this.txtNumber.isEditable()) {
			if (StringUtils.isEmpty(this.txtNumber.getText()))
				buff.append("���벻��Ϊ��!\n");
		}
		if (StringUtils.isEmpty(this.txtName.getText()))
			buff.append("�̻����Ʋ��ܿ�!\n");
		if (StringUtils.isEmpty(this.txtPhoneNumber.getText()))
			buff.append("�̻���ϵ�绰���ܿ�!\n");
		if (this.prmtCommerceLevel.getValue() == null)
			buff.append("�̻������ܿ�!\n");
		if (this.comboCommerceStatus.getSelectedItem() == null)
			buff.append("�̻�״̬���ܿ�!\n");
		if (this.pkCommerceDate.getValue() == null)
			buff.append("�̻����ڲ��ܿ�!\n");
		if (this.prmtSaleMan.getValue() == null)
			buff.append("Ӫ�����ʲ��ܿ�!\n");
		// ���򷿼����Ϊ�տ��Ա��� 20090223
		// if(this.tblHopedRooms.getRowCount()==0)
		// buff.append("���򷿼䲻�ܿ�!\n");
		String sellProjectStr = this.prmtSellProject.getText();
		if (this.tblHopedRooms.getRowCount() > 0) {
			for (int i = 0; i < this.tblHopedRooms.getRowCount(); i++) {
				CommerceRoomEntryInfo roomEntry = (CommerceRoomEntryInfo) this.tblHopedRooms.getRow(i).getUserObject();
				if (roomEntry != null && roomEntry.getRoom() != null ){
					RoomInfo room = (RoomInfo)roomEntry.getRoom();
					if(room.getBuilding()!= null && room.getBuilding().getSellProject()!= null){
						String sellProjectName= room.getBuilding().getSellProject().getName();
						if(sellProjectName !=null && !"".equals(sellProjectName) && !sellProjectName.equals(sellProjectStr)){
							buff.append("����Դ��"+(i+1)+"�з��䲻���ڸ�������Ŀ!\n");
						}
					}
				}
			}
		}
		if (!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// ������Щ����������Ϊ�յ�����
		if (this.editData.getCU() == null)
			this.editData.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if (this.editData.getOrgUnit() == null)
			this.editData.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		super.actionSubmit_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	
	public void loadFields() {
		super.loadFields();

		// ���������¼�ͺ�ͬ ,�����׸�����ʾ״̬
		if (!this.getOprtState().equals(OprtState.ADDNEW)) {
			showTrackRecordAndContract();
			refashIntentLevelText();
			try {
				refreshTblQuestion(editData.getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (this.editData.getSysType() != null) {
				if (this.editData.getSysType().equals(MoneySysTypeEnum.TenancySys)) {
					this.txtFirstPayAmount.setEnabled(false);
					this.prmtFirstPayProportion.setEnabled(false);
				}
			}
		}

		setEnableWhereHasTrackRecord();
	}
	
	private void refreshTblQuestion(String id) throws Exception{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("inputDate");
		sic.add("questionPaper.id");
		sic.add("questionPaper.topric");
		sic.add("number");
		sic.add("bizDate");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("commerceChance.id",id));
		view.setSelector(sic);
		view.setFilter(filter);
		QuestionPaperAnswerCollection coll=QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
		tblQuestion.removeRows();
		//���½���һ��Ԫ���� ��Ȼ�ᱨ�쳣 jiadong
		tblQuestion.checkParsed();
		for(Iterator it=coll.iterator();it.hasNext();){
			QuestionPaperAnswerInfo info=(QuestionPaperAnswerInfo)it.next();
			IRow row=tblQuestion.addRow();
			if(info!=null){
				row.getCell("number").setValue(info.getNumber()!=null?info.getNumber():null);
				row.getCell("printDate").setValue(info.getInputDate()!=null?info.getInputDate():null);
				row.getCell("bizDate").setValue(info.getBizDate()!=null?info.getBizDate():null);
				row.getCell("title").setValue(info.getQuestionPaper().getTopric()!=null?info.getQuestionPaper().getTopric():null);
				row.getCell("id").setValue(info.getId()!=null?info.getId():null);
				row.setUserObject(info);
			}
		}
	}

	// ����Ѿ��и�����¼�ˣ��������޸Ŀͻ�����Ŀ�����۹�����
	private void setEnableWhereHasTrackRecord() {
		if (this.tblTrackRecord.getRowCount() > 0) {
			this.prmtFdcCustomer.setEnabled(false);
			this.prmtSellProject.setEnabled(false);
			this.prmtSaleMan.setEnabled(false);
		}
	}

	private void setCustomerInfo(FDCCustomerInfo customer) {
		this.txtCustomerPhone.setText(customer != null ? customer.getPhone() : "");
		this.txtCertificateNumber.setText(customer != null ? customer.getCertificateNumber() : "");
		this.txtAddress.setText(customer != null ? customer.getMailAddress() : "");

		String phoneNumber = this.txtPhoneNumber.getText();
//		if (phoneNumber == null || phoneNumber.trim().equals(""))  //--�̻���ϵ�绰�Զ�����  jiadong
			this.txtPhoneNumber.setText(customer != null ? customer.getPhone() : "");

	}

	protected IObjectValue createNewDetailData(KDTable table) {
		CommerceRoomEntryInfo roomEntry = new CommerceRoomEntryInfo();

		return roomEntry;
	}
	
	
	public void actionCustomerInfo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCustomerInfo_actionPerformed(e);
		if (this.getOprtState().equals(OprtState.ADDNEW)) {
			MsgBox.showInfo("����״̬�²��ܲ鿴�ͻ���Ϣ!");
			return;
		}
		CommerceChanceInfo comChanceInfo = (CommerceChanceInfo) this.editData;
		if (comChanceInfo != null) {
			FDCCustomerInfo fdcCustomerInfo = comChanceInfo.getFdcCustomer();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, fdcCustomerInfo.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} else {
			MsgBox.showInfo("û�пͻ���Ϣ�����飡");
			SysUtil.abort();
		}
	}

	public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddTrackRecord_actionPerformed(e);

		if (this.getOprtState().equals(OprtState.ADDNEW)) {
			MsgBox.showInfo("����״̬�²������Ӹ��ټ�¼!");
			return;
		}

		CommerceChanceInfo comInfo = this.editData;
		if (comInfo == null)
			return;
		if (comInfo.getCommerceStatus().equals(CommerceStatusEnum.Finish)) {
			MsgBox.showInfo("��ֹ״̬���̻��޷��ٸ���!");
			return;
		}

		// ���������ټ�¼����
		UIContext uiContext = new UIContext(this);
		uiContext.put("CommerceChance", comInfo);
		uiContext.put("FdcCustomer", comInfo.getFdcCustomer());
		uiContext.put("SellProject", comInfo.getSellProject());
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}

		// ˢ�¸��ټ�¼��
		if (!this.getOprtState().equals(OprtState.ADDNEW))
			showTrackRecordAndContract();
	}

	public void actionModifyTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyTrackRecord_actionPerformed(e);

		if (this.getOprtState().equals(OprtState.ADDNEW))
			return;
		CommerceChanceInfo comInfo = this.editData;
		if (comInfo == null)
			return;

		int[] trackRows = KDTableUtil.getSelectedRows(this.tblTrackRecord);
		if (trackRows.length == 0) {
			MsgBox.showInfo("����ѡ��Ҫ�޸ĵĸ�����¼��");
			return;
		}
		String idTrack = (String) this.tblTrackRecord.getRow(trackRows[0]).getCell("id").getValue();
		if (idTrack == null)
			return;

		// ���������ټ�¼����
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, idTrack);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}

		showTrackRecordAndContract();

	}

	public void actionDelTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelTrackRecord_actionPerformed(e);

		if (this.getOprtState().equals(OprtState.ADDNEW))
			return;
		CommerceChanceInfo comInfo = this.editData;
		if (comInfo == null)
			return;

		int[] trackRows = KDTableUtil.getSelectedRows(this.tblTrackRecord);
		if (trackRows.length == 0) {
			MsgBox.showInfo("����ѡ��Ҫɾ���ĸ�����¼��");
			return;
		}
		String idTrack = (String) this.tblTrackRecord.getRow(trackRows[0]).getCell("id").getValue();
		if (idTrack == null)
			return;

		if (MsgBox.OK == MsgBox.showConfirm2("ȷ��Ҫɾ����")) {
			TrackRecordFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(idTrack)));
			showTrackRecordAndContract();
		}
	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		if (this.getOprtState().equals(OprtState.VIEW))
			return;
		super.btnAddRoom_actionPerformed(e);

		RoomInfo room = null;
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		if (sysType != null) {
			if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) { // ��¥ϵͳ
				room = RoomSelectUI.showOneRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys, null, (SellProjectInfo) this.prmtSellProject.getValue());
				if (room != null && !RoomSellStateEnum.OnShow.equals(room.getSellState())
						&& !RoomSellStateEnum.Init.equals(room.getSellState()) && !RoomSellStateEnum.SincerPurchase.equals(room.getSellState())) {
					MsgBox.showInfo("���䲻��δ���̣����ۻ�����Ϲ�״̬��");
					return;
				}
			} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {// ����ϵͳ
				room = RoomSelectUI.showOneRoomSelectUI(this, null, null, MoneySysTypeEnum.TenancySys, null, (SellProjectInfo) this.prmtSellProject.getValue());
				if (room != null && !room.getTenancyState().equals(TenancyStateEnum.waitTenancy)) {
					MsgBox.showInfo("���䲻��'" + TenancyStateEnum.waitTenancy.getAlias() + "'״̬��");
					return;
				}
			}
		}

		if (room == null)
			return;

		if (isExistTheRoom(room))
			return;

		IRow row = this.tblHopedRooms.addRow();
		fillTheRoomRow(row, room);
		this.refashIntentLevelText();
	}

	protected void btnInsertRoom_actionPerformed(ActionEvent e) throws Exception {
		if (this.getOprtState().equals(OprtState.VIEW))
			return;
		super.btnInsertRoom_actionPerformed(e);

		RoomInfo room = null;
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		if (sysType != null) {
			if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) { // ��¥ϵͳ
				room = RoomSelectUI.showOneRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys, null, (SellProjectInfo) this.prmtSellProject.getValue());
				if (room != null && !RoomSellStateEnum.OnShow.equals(room.getSellState())
						&& !RoomSellStateEnum.Init.equals(room.getSellState()) && !RoomSellStateEnum.SincerPurchase.equals(room.getSellState())) {
					MsgBox.showInfo("���䲻��δ���̣����ۻ�����Ϲ�״̬��");
					return;
				}
			} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) { // ����ϵͳ
				room = RoomSelectUI.showOneRoomSelectUI(this, null, null, MoneySysTypeEnum.TenancySys, null, (SellProjectInfo) this.prmtSellProject.getValue());
				if (room != null && !room.getTenancyState().equals(TenancyStateEnum.waitTenancy)) {
					MsgBox.showInfo("���䲻��'" + TenancyStateEnum.waitTenancy.getAlias() + "'״̬��");
					return;
				}
			}
		}
		if (room == null)
			return;
		if (isExistTheRoom(room))
			return;

		int rowIndex = this.tblHopedRooms.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0)
			rowIndex = this.tblHopedRooms.getRowCount();

		IRow row = this.tblHopedRooms.addRow(rowIndex);
		fillTheRoomRow(row, room);
		this.refashIntentLevelText();
	}

	protected void btnDeleteRoom_actionPerformed(ActionEvent e) throws Exception {
		if (this.getOprtState().equals(OprtState.VIEW))
			return;

		super.btnDeleteRoom_actionPerformed(e);

		int rowIndex = this.tblHopedRooms.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			MsgBox.showInfo("��ѡ��Ҫɾ���ķ���!");
			return;
		}

		this.tblHopedRooms.removeRow(rowIndex);
		this.refashIntentLevelText();
	}

	private void fillTheRoomRow(IRow row, RoomInfo room) {
		CommerceRoomEntryInfo roomEntry = new CommerceRoomEntryInfo();
		roomEntry.setRoom(room);
		row.setUserObject(roomEntry);
		row.getCell("building").setValue(room.getBuilding());
		row.getCell("unit").setValue(String.valueOf(room.getBuildUnit()));
		row.getCell("number").setValue(room.getNumber());
		row.getCell("roomModel").setValue(room.getRoomModel());
		row.getCell("saleStatus").setValue(room.getSellState());
		row.getCell("tenancyStatus").setValue(room.getTenancyState());
		row.getCell("planToChange").setValue(new Boolean(true));
	}

	private boolean isExistTheRoom(RoomInfo room) {
		for (int i = 0; i < this.tblHopedRooms.getRowCount(); i++) {
			IRow row = this.tblHopedRooms.getRow(i);
			CommerceRoomEntryInfo roomEntry = (CommerceRoomEntryInfo) row.getUserObject();
			if (roomEntry.getRoom().equals(room))
				return true;
		}
		return false;
	}

	protected void tblHopedRooms_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1) {
			if (e.getType() != 1)
				return;

			int colIndex = this.tblHopedRooms.getColumnIndex("planToChange");
			// planToChange ��������� ��'�ƻ���������'�����ѡ��״̬
			if (e.getColIndex() == colIndex) {
				if (e.getClickCount() == 1) {
					if (!this.getOprtState().equals(OprtState.VIEW)) {
						IRow clickRow = this.tblHopedRooms.getRow(e.getRowIndex());
						if (clickRow != null && clickRow.getCell("planToChange").getValue() != null) {
							Boolean cellValue = (Boolean) clickRow.getCell("planToChange").getValue();
							clickRow.getCell("planToChange").setValue(new Boolean(!cellValue.booleanValue()));
						}
					}
				}
			} else {
				if (e.getClickCount() == 2) {
					CommerceRoomEntryInfo roomEntry = (CommerceRoomEntryInfo) this.tblHopedRooms.getRow(e.getRowIndex()).getUserObject();
					if (roomEntry == null) {
						return;
					}

					// �򿪷����ui����
					CommerceHelper.openTheUIWindow(this, RoomEditUI.class.getName(), roomEntry.getRoom().getId().toString());
				}
			}

		}
	}

	protected void tblContract_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;

			IRow row = this.tblContract.getRow(e.getRowIndex());
			String uiClassName = (String) row.getCell("UINAME").getValue();
			String id = (String) row.getCell("id").getValue();
			CommerceHelper.openTheUIWindow(this, uiClassName, id);
		}

	}

	protected void tblTrackRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTrackRecord_tableClicked(e);

		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;

			String trackInfoId = (String) this.tblTrackRecord.getRow(e.getRowIndex()).getCell("id").getValue();
			if (trackInfoId == null) {
				return;
			}

			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, trackInfoId);
			uiContext.put("ActionView", "OnlyView");
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	public void prmtFdcCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws EASBizException, BOSException {
		FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtFdcCustomer.getValue();
		setCustomerInfo(customer);
		if (customer != null && !this.getOprtState().equals(OprtState.VIEW)){       // && FDCHelper.isEmpty(this.txtName.getText())
			this.txtName.setText(customer.getName());
		}
	}

	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);

		// �̻�״̬�����޸ģ���ʼ��ʱ�򱻶���Ϊ����
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		this.comboCommerceStatus.setSelectedItem(null);
		if (sysType != null) {
			this.comboCommerceStatus.setSelectedItem(CommerceStatusEnum.Intent);
			if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
				this.txtFirstPayAmount.setEnabled(true);
				this.prmtFirstPayProportion.setEnabled(true);
				this.tblHopedRooms.getColumn("saleStatus").getStyleAttributes().setHided(false);
				this.tblHopedRooms.getColumn("tenancyStatus").getStyleAttributes().setHided(true);
			} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
				this.txtFirstPayAmount.setValue(null);
				this.prmtFirstPayProportion.setValue(null);
				this.txtFirstPayAmount.setEnabled(false);
				this.prmtFirstPayProportion.setEnabled(false);
				this.tblHopedRooms.getColumn("saleStatus").getStyleAttributes().setHided(true);
				this.tblHopedRooms.getColumn("tenancyStatus").getStyleAttributes().setHided(false);
			}
		} else {
			this.comboCommerceStatus.removeAllItems();
			this.comboCommerceStatus.addItems(CommerceStatusEnum.getEnumList().toArray());
		}

	}

	// Ӫ�����ʱ仯�ı����ܷ��ʵ���������Ŀ
	protected void prmtSaleMan_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSaleMan_dataChanged(e);

		setSellProjectViewFilter();
	}

	private void setSellProjectViewFilter() throws Exception {
		UserInfo saleMan = (UserInfo) this.prmtSaleMan.getValue();
		String permitSPIdSql = CommerceHelper.getPermitProjectIdSql(saleMan);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", permitSPIdSql, CompareType.INNER));
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		if (sysType != null) {
			if (sysType.equals(MoneySysTypeEnum.SalehouseSys))
				filter.getFilterItems().add(new FilterItemInfo("isForSHE", Boolean.TRUE));
			else if (sysType.equals(MoneySysTypeEnum.TenancySys))
				filter.getFilterItems().add(new FilterItemInfo("isForTen", Boolean.TRUE));
			else if (sysType.equals(MoneySysTypeEnum.ManageSys))
				filter.getFilterItems().add(new FilterItemInfo("isForPPM", Boolean.TRUE));
		}
		view.setFilter(filter);
		this.prmtSellProject.setEntityViewInfo(view);
	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		setGroupFilter();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);

		this.tblContract.removeRows();
		this.tblTrackRecord.removeRows();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getCommerceStatus().equals(CommerceStatusEnum.Finish)) {
			MsgBox.showInfo("����ֹ״̬���̻��������޸�!");
			return;
		}
		super.actionEdit_actionPerformed(e);

		this.btnAddRoom.setEnabled(true);
		this.btnInsertRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);

		this.tblHopedRooms.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblHopedRooms.getStyleAttributes().setLocked(true);

		if (this.editData.getSysType() != null) {
			if (this.editData.getSysType().equals(MoneySysTypeEnum.TenancySys)) {
				this.txtFirstPayAmount.setEnabled(false);
				this.prmtFirstPayProportion.setEnabled(false);
			}
		}

		setEnableWhereHasTrackRecord();
		this.txtPhoneNumber.setEnabled(false);
	}

	// ˢ�����򼶱����ʾ
	private void refashIntentLevelText() {
		for (int i = 0; i < this.tblHopedRooms.getRowCount(); i++) {
			IRow row = this.tblHopedRooms.getRow(i);
			// Object cellValue = row.getCell("intentLevel").getValue();
			// if(cellValue!=null && cellValue instanceof Integer)
			row.getCell("intentLevelStr").setValue("��" + (i + 1) + "����");
		}
	}

	// ������״̬���̻�����ɾ��
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * FilterInfo filter = new FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("head.id",this.editData.getId().toString()));
		 * 
		 * if(TrackRecordFactory.getRemoteInstance().exists(filter)){
		 * MsgBox.showInfo("�Ѿ��и��ټ�¼�����˸����̻�,����ɾ��!"); return; }
		 */
		CommerceStatusEnum comStatus = this.editData.getCommerceStatus();
		if (!comStatus.equals(CommerceStatusEnum.Intent)) {
			MsgBox.showInfo("������״̬���̻�����ɾ��!");
			return;
		}

		if (this.tblTrackRecord.getRowCount() > 0) {
			MsgBox.showInfo("���и�����¼���̻�����ɾ��!");
			return;
		}

		super.actionRemove_actionPerformed(e);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	// �ͻ��̻��ܿ�����Ҫ���õ�
	public KDBizPromptBox getCustomerPrmt() {
		return this.prmtFdcCustomer;
	}

	public KDBizPromptBox getSellProjectPrmt() {
		return this.prmtSellProject;
	}

	public KDBizPromptBox getSaleManPrmt() {
		return this.prmtSaleMan;
	}

	public KDTextField getCommerceName() {
		return this.txtName;
	}

	public CommerceChanceInfo getCommerceData() {
		return this.editData;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("orgUnit.*");
		return sels;
	}
}