package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI;
import com.kingdee.eas.fdc.sellhouse.client.SelectPaymentListUI;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class TENReceiveBillEditUI extends ReceiveBillEidtUI {
	
	private static final Logger logger = CoreUIObject.getLogger(TENReceiveBillEditUI.class);
	
	public TENReceiveBillEditUI() throws Exception {
		super();
	}
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		//TODO
		MsgBox.showInfo(this, "�����տ��ʹ���²˵�����ʹ�����տ�˵���");
		
		//���տ��������ֻ�ò鿴
		this.actionEdit.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionRec.setVisible(false);
	}
	
	/**
	 * ����ϵͳװ������
	 */
	protected void loadFieldsForSys(ReceivingBillInfo receivingBillInfo,FDCReceiveBillInfo fdcReceiveBillInfo)
	{
		//ǰ���Ѿ��жϣ�������ʵ������Ϊ��
		if(fdcReceiveBillInfo.getMoneySysType() == null)		{
			this.gatheringObject = GatheringObjectEnum.room;
		}else	{
			this.gatheringObject = fdcReceiveBillInfo.getGatheringObject();
		}
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
		if (room != null){
			this.f7Room.setUserObject(room);
			this.f7Room.setValue(room);
			this.f7Room.setText(room.getBuilding().getName() + "-" + room.getNumber());
			//TODO Ϊ���޺�ͬ���ù�������
			
			this.f7SellOrder.setValue(null);//����û���̴Σ����ܻ����ӷ����̴�
		}
		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
		if (tenancyBillInfo != null)
		{
			this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
		}
		
		if (this.getOprtState().equals(OprtState.EDIT)) {
			this.f7Room.setEnabled(false);
			this.f7TenancyContract.setEnabled(false);

			if (fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LateFee) || fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.CommissionCharge)
					|| fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.ElseAmount)) {
				this.txtReceiveAmount.setEnabled(true);
			} else {
			}
		} else if (this.getOprtState().equals(OprtState.VIEW)) {
			this.f7Room.setEnabled(false);
			this.f7TenancyContract.setEnabled(false);
		}

		try {
			this.setF7SysCustomerFilterForTEN();
		} catch (BOSException e) {
			this.handleException(e);
		}
	}
	
	
	/**
	 * ����ϵͳ ���ÿͻ��Ĺ�������
	 * ��Ҫ��ͨ�����޺�ͬ�����˵ģ����������ͨ�����������ˣ���Ȼ���Թ��˵�һ���ֿͻ�������û��ʵ�����壬��Χ̫��
	 */
	private void setF7SysCustomerFilterForTEN() throws BOSException
	{
		EntityViewInfo queryView = new EntityViewInfo();
		queryView.getSelector().add("fdcCustomer.sysCustomer.*");
		FilterInfo queryFilter = new FilterInfo();
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
		if (tenancyBillInfo != null)
		{
			queryFilter.getFilterItems().add(new FilterItemInfo("tenancyBill", tenancyBillInfo.getId().toString()));
		}
		queryView.setFilter(queryFilter);
		TenancyCustomerEntryCollection tenancyCustomerEntryColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(queryView);

		Set set = new HashSet();
		for (int i = 0; i < tenancyCustomerEntryColl.size(); i++)
		{
			TenancyCustomerEntryInfo tenancyCustomerEntryInfo = tenancyCustomerEntryColl.get(i);
			FDCCustomerInfo fdcCustomerInfo = tenancyCustomerEntryInfo.getFdcCustomer();
			CustomerInfo sysCustomerInfo = fdcCustomerInfo.getSysCustomer();
			if (sysCustomerInfo == null)
			{
				logger.warn("�Ϲ��ķ��ز��ͻ�δͬ��,����ϸ���. fdcCustomerId:"+ fdcCustomerInfo.getId().toString());
				continue;
			}
			set.add(sysCustomerInfo.getId().toString());
		}

		//��ֹ��֮ǰ�Ĺ������������
		f7Customer.getQueryAgent().setDefaultFilterInfo(null);
		f7Customer.getQueryAgent().setHasCUDefaultFilter(false);
		f7Customer.getQueryAgent().resetRuntimeEntityView();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(set.isEmpty()){
			filter.getFilterItems().add(new FilterItemInfo("id", "idnull"));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		}
		this.f7Customer.setEntityViewInfo(view);
	}
	
	
	/**
	 * ���޺�ͬF7�¼�
	 */
	protected void f7TenancyContract_dataChanged(DataChangeEvent e) throws Exception
	{
		if(!this.lcTenancyContract.isVisible())
		{
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
		//�Է���Щ���Դ�������������ȡ����
		if(tenancyBillInfo != null)
		{
			tenancyBillInfo = TenancyHelper.getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(),tenancyBillInfo.getId().toString());
		}
		
		//����Ǵ����޺�ͬ�Ǳߣ�ֱ�Ӵ���ͬ���������տ����жϷ����Ƿ�Ϊ��
		if (this.getUIContext().get(KEY_TENANCYBILLID) != null)
		{
			if (tenancyBillInfo != null)
			{
				this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
				TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();;
				if (tenancyCustomerEntryColl.size() > 0)
				{
					CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
					this.f7Customer.setValue(sysCustomer);
				}
			} 
			else
			{
				this.txtReceiveAmount.setValue(null);
			}
		}
		else
		{
			if (room == null)
			{
				MsgBox.showInfo("����ѡ�񷿼䣡");
				return;
			}
			if (tenancyBillInfo != null)
			{
				this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
				
				TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();;
				if (tenancyCustomerEntryColl.size() > 0)
				{
					CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
					this.f7Customer.setValue(sysCustomer);
				}
			} 
		}
		this.setF7SysCustomerFilterForTEN();
		
		//��f7ѡ�񷿼��¼����з�Χ����
		if(tenancyBillInfo != null)
		{
			TenancyRoomEntryCollection treColl = tenancyBillInfo.getTenancyRoomList();
			RoomCollection roomColl = new RoomCollection();
			if(treColl != null)
			{
				for(int i = 0; i < treColl.size(); i ++)
				{
					roomColl.add(treColl.get(i).getRoom());
				}
			}
			if(roomColl.size() > 0)
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, roomColl));
			}
			else
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, null));
			}
		}
		else
		{
			f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, null));
		}
		this.kDTable1.removeRows();
	}
	
	
	
	/**
	 * ������޵ĸ�����ϸ
	 * @param payColl
	 * @param settlementMap
	 */
	private void addRowByTenancyRoomPayEntryColl(IObjectCollection payColl,Map settlementMap)
	{
		if(payColl==null) return;
		for(int i = 0;i < payColl.size(); i ++)
		{
			IRow row = this.kDTable1.addRow();
			ITenancyPayListInfo info = (ITenancyPayListInfo) payColl.getObject(i);
			
			BigDecimal appAmount = info.getAppAmount();
			if(appAmount != null)
				appAmount = appAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			else
				appAmount = FDCHelper.ZERO;
			
			BigDecimal actAmount = info.getActAmount();
			if(actAmount != null)
				actAmount = actAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			else
				actAmount = FDCHelper.ZERO;
			
			row.getCell("moneyType").setValue(info.getMoneyDefine());
			//���ݿ����Զ�������Ŀ
			setAccountByMoneyDefine(info.getMoneyDefine(), row);
			
			row.getCell("appAmount").setValue(appAmount);
			row.getCell("actAmount").setValue(actAmount);
			BigDecimal canRefundmentAmount = info.getCanRefundmentAmount();
			if(canRefundmentAmount != null)
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				canRefundmentAmount = FDCHelper.ZERO;
			row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
			
			String id = info.getStrId();
			
			row.getCell("id").setValue(id);
			
			if(settlementMap.get(id) != null)
			{
				if (settlementMap.get(id) instanceof Object[])
				{
					Object[] o = (Object[]) settlementMap.get(id);

					if (o != null)
					{
						for (int j = 0; j < o.length; j++)
						{
							if (j == 0)
							{
								row.getCell("settlementType").setValue(o[j]);
								
								//���ֻ��һ�е�Ԓ���͎����տ���~�����˿���~
								if (o.length == 1)
								{
									BigDecimal gatheringAmount = appAmount.subtract(actAmount);

									row.getCell("refundmentAmount").setValue(canRefundmentAmount);
									row.getCell("gatheringAmount").setValue(gatheringAmount);
									updateAmountTotal();
								}
							} else
							{
								IRow tempRow = this.kDTable1.addRow();
								tempRow.getCell("id").setValue(id);
								tempRow.getCell("moneyType").setValue(
										info.getMoneyDefine());
								tempRow.getCell("settlementType")
										.setValue(o[j]);
								
								//���ݿ����Զ�������Ŀ
								setAccountByMoneyDefine(info.getMoneyDefine(), tempRow);
							}
						}
						// �ںϵ�Ԫ��
						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(), 0,
								row.getRowIndex() + o.length - 1, 0);

						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(),
								row.getCell("appAmount").getColumnIndex(),
								row.getRowIndex() + o.length - 1,
								row.getCell("appAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(),
								row.getCell("actAmount").getColumnIndex(),
								row.getRowIndex() + o.length - 1,
								row.getCell("actAmount").getColumnIndex());
					}
				}
				else if(settlementMap.get(id) instanceof Object)
				{
					Object o =  settlementMap.get(id);
					if (o != null)
					{
						row.getCell("settlementType").setValue(o);
					}
				}
			}	
		}
	}

	
	
	protected void btnChoose_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyChoose();
		this.kDTable1.removeRows();
		
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
		
		UIContext uiContext = new UIContext(ui);
		
		uiContext.put(KEY_MONEYTSYSTYPE, MoneySysTypeEnum.TenancySys);
		uiContext.put(KEY_BILLTYPE,this.recBillType);
		uiContext.put(KEY_GATHERING, selectedItem);			
		
		TenancyBillInfo tenancyBill = (TenancyBillInfo) this.f7TenancyContract.getValue();
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) this.f7Accessorial.getValue();
		uiContext.put(KEY_TenancyBill, tenancyBill);
		uiContext.put(Key_Room, room);
		uiContext.put(KEY_TEN_ATTACH, tenAttach);		
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectPaymentListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		if (uiWindow.getUIObject().getUIContext().get(KEY_OPT) == null ||
				!((Boolean) uiWindow.getUIObject().getUIContext().get(KEY_OPT)).booleanValue()){
			return;
		}
		
		Map settlementMap = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_SettlementType);
		Boolean isSettlement = (Boolean) uiWindow.getUIObject().getUIContext().get(Key_IsSettlement);
		if(isSettlement == null)
			isSettlement = Boolean.FALSE;
		//����ȫ�ֲ���
		this.isSettlement = isSettlement.booleanValue();
		
		if (isSettlement.booleanValue()) {
			this.cbIsSettlement.setSelected(true);
			this.hongChongFdcRecEntryColl = (FDCReceiveBillEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_HongChongRecEntryColl);
		} else {
			this.cbIsSettlement.setSelected(false);
		}
		
		// ��ӱ����
		if (this.isSettlement) {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(true);
		} else {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(false);
		}
		
		//���·�ϵͳ��Ӹ�����ϸ���б���
		IObjectCollection tenPayEntryColl = (IObjectCollection) uiWindow.getUIObject().getUIContext().get(KEY_TenEntryPayColl);
		this.addRowByTenancyRoomPayEntryColl(tenPayEntryColl,settlementMap);

	}	
	
	
	
	/**
	 * ����ѡ���¼�
	 */
	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception
	{
		if(isLoadField){
			return;
		}
		
		if("VIEW".equalsIgnoreCase(this.getOprtState()) || "FINDVIEW".equalsIgnoreCase(this.getOprtState())){
			return;
		}
		
		this.doSomeThingAsTenancyRoomchosen(e);

		this.kDTable1.removeRows();
	}
	
	
	/**
	 * ��ѡ�����޷����ʱ�������Ĳ���
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	private void doSomeThingAsTenancyRoomchosen(DataChangeEvent e) throws BOSException, EASBizException, UuidException
	{
		RoomInfo room = null;
		if(e.getNewValue() instanceof RoomInfo)
		{
			room = (RoomInfo) e.getNewValue();
		}
		if (room == null)
		{
			return;
		}
		Set tempSet = getQueryBillStates();
		TenancyBillCollection tenancyBillColl = this.getValidTenancyContractByRoomAndState(room, tempSet);		
		if(tenancyBillColl != null && tenancyBillColl.size() < 1)
		{
			logger.warn("δȡ�÷����Ϊ "+ room.getNumber() +" �ģ���Ч��ͬ��");
			this.f7TenancyContract.setValue(null);
			this.f7TenancyContract.setEnabled(false);
			if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
			{
				MsgBox.showInfo("�÷���û�н����տ�����ĺ�ͬ��");
			}
			this.abort();
		}
		
		this.f7Room.setUserObject(room);
		this.f7Room.setValue(room);
		this.f7SellOrder.setValue(room.getSellOrder());
		this.f7TenancyContract.setEnabled(true);
		this.setF7TenancyContractFilterByRoom(room, tempSet);
		
		if (tenancyBillColl != null && tenancyBillColl.size() == 1)
		{
			this.f7TenancyContract.setValue(tenancyBillColl.get(0));
			TenancyBillInfo tenancyBillInfo = tenancyBillColl.get(0);
			// ��������߽����޹���
			this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());

			TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();
			
			if (tenancyCustomerEntryColl != null && tenancyCustomerEntryColl.size() > 0)
			{
				CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
				this.f7Customer.setValue(sysCustomer);
			}
		}//����������޺�ͬ��ֵ�������ж����޺�ͬ�����ǲ����Ѿ���ֵ��
		else
		{
			TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
			MoneyDefineCollection moneyDefineColl = this.getValidMoneyNameByTenancyContract(tenancyBillInfo,room);
			if (moneyDefineColl !=null && moneyDefineColl.size() > 0)
			{
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				Set idSet = new HashSet();
				for (int i = 0; i < moneyDefineColl.size(); i++)
				{
					idSet.add(moneyDefineColl.get(i).getId().toString());
				}
				filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			} 
			else
			{
			}
		}
		String sellProjectId = room.getBuilding().getSellProject().getId().toString();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
		this.f7SellProject.setValue(sellProject);
	}
	
	
	/**
	 * ��ȡ�÷����¿��Խ����տ�����ĺ�ͬ
	 * @param room
	 * @param billStateSet ��ͬ��״̬
	 * @return
	 * @throws BOSException
	 */
	private TenancyBillCollection getValidTenancyContractByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tenancy.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));

		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",billStateSet,CompareType.INCLUDE));
		}
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		
		Set tenIds = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tenIds.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo tenView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		tenView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", tenIds, CompareType.INCLUDE));
		tenView.getSelector().add("*");
		tenView.getSelector().add("tenCustomerList.sysCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.*");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.sysCustomer.name");
		tenView.getSelector().add("tenancyRoomList");
		
		return TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(tenView);
	}
	
	
	/**
	 * ͨ�����������ú�ͬ�Ĺ�������
	 */
	private void setF7TenancyContractFilterByRoom(RoomInfo room, Set billStateSet) throws BOSException
	{
		TenancyRoomEntryCollection tenancyRoomEntryColl = new TenancyRoomEntryCollection();
		EntityViewInfo tenancyRoomEntryView = new EntityViewInfo();
		tenancyRoomEntryView.getSelector().add("tenancy.id");
		FilterInfo tenancyRoomEntryFilter = new FilterInfo();
		tenancyRoomEntryFilter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		tenancyRoomEntryView.setFilter(tenancyRoomEntryFilter);
		tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(tenancyRoomEntryView);
		
		Set tempSet = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tempSet.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo view = new EntityViewInfo();
	
		FilterInfo filter = new FilterInfo();
		if(tempSet == null || tempSet.size() < 1)
		{
			filter.getFilterItems().add(new FilterItemInfo("id","never equal"));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("id",tempSet,CompareType.INCLUDE));
		}
		//��ͬ״̬����
		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancyState",billStateSet,CompareType.INCLUDE));
		}
		
		view.setFilter(filter);
		this.f7TenancyContract.setEntityViewInfo(view);
	}
	
	
	/**
	 * ���ĳ����ͬ��ĳ������ĸ�����
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private  MoneyDefineCollection getValidMoneyNameByTenancyContract(TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		MoneyDefineCollection moneyDefineColl = new MoneyDefineCollection();
		if(tenancyBillInfo == null || room == null)
		{
			return moneyDefineColl;
		}
		
		//������˿��ֱ�ӷ����˿�
		if (ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("revBillType.*");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.REFUNDMENT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
			moneyDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			return moneyDefineColl;
		}
		
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("moneyDefine.*");
        view.getSelector().add("*");
		view.setFilter(filter);
		
		SorterItemCollection selColl = new SorterItemCollection();
		selColl.add(new SorterItemInfo("seq"));
		view.setSorter(selColl);
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
	
		for(int i = 0; i < tenancyRoomPayListEntryColl.size(); i ++)
		{
			TenancyRoomPayListEntryInfo temp = tenancyRoomPayListEntryColl.get(i);
			BigDecimal actAmount = temp.getActAmount();
			if(actAmount == null)
			{
				actAmount = FDCHelper.ZERO;
			}
			BigDecimal appAmount = temp.getAppAmount();
			if(appAmount == null)
			{
				appAmount = FDCHelper.ZERO;
			}
			//δ�ﵽӦ�ɽ��
			if(actAmount.compareTo(appAmount) < 0)
			{
				moneyDefineColl.add(temp.getMoneyDefine());
//				�鿴���ǣ�����ȡ���ճ���ֵ
				if(!OprtState.VIEW.equals(this.getOprtState()))
				{
					this.seq = temp.getSeq();
				}
				break;
			}
			else
			{
				continue;
			}
		}
		return moneyDefineColl;
	}
	
	
	
	/**
	 * ����ϵͳ �� �½�����
	 */
	protected IObjectValue createNewDataForSys(ReceivingBillInfo receivingBillInfo, FDCReceiveBillInfo fdcReceiveBillInfo) throws EASBizException, BOSException, UuidException
	{
		String  roomId = (String)this.getUIContext().get(KEY_ROOMID);
		RoomInfo room = null;
		if(roomId != null)
		{
			SelectorItemCollection selColl = new SelectorItemCollection();
			selColl.add("*");
			selColl.add("building.*");
			selColl.add("building.sellProject.*");
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)),selColl);
		}
		if (room != null)
		{
			fdcReceiveBillInfo.setRoom(room);
			String sellProjectId = room.getBuilding().getSellProject().getId().toString();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("project.*");
			//TODO �����ѯ��ʲô�ã�
			SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
								new ObjectUuidPK(BOSUuid.read(sellProjectId)),sels);
			fdcReceiveBillInfo.setSellProject(sellProject);
		}
		//�����޺�ͬ�տ��Ǳߴ������Ĳ���
		String tenancyBillId = (String) this.getUIContext().get(KEY_TENANCYBILLID);
		if (tenancyBillId == null)
		{
			if(room != null)
			{
				Set tempSet = getQueryBillStates();
				TenancyBillCollection tenancyBillColl = this.getValidTenancyContractByRoomAndState(room, tempSet);
				if(tenancyBillColl.size() == 1)
				{
					TenancyBillInfo tenancyBillInfo = tenancyBillColl.get(0);
					fdcReceiveBillInfo.setTenancyContract(tenancyBillInfo);
				}
				else if(tenancyBillColl.size() < 1)
				{
					MsgBox.showInfo("�÷���û�з����տ�����ĺ�ͬ��");
					this.abort();
				}
			}
		} else
		{
			fdcReceiveBillInfo.setTenancyContract(TenancyHelper.getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(),tenancyBillId));
		}
		
		if (fdcReceiveBillInfo.getTenancyContract() != null)
		{
			TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
			if (room != null)
			{
				//TODO �����������������
				MoneyDefineCollection moneyDefineColl = this.getValidMoneyNameByTenancyContract(tenancyBillInfo,room );
				if (moneyDefineColl.size() == 1)
				{
					fdcReceiveBillInfo.setMoneyDefine(moneyDefineColl.get(0));
				}
			}
			TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();
			if (tenancyCustomerEntryColl != null && tenancyCustomerEntryColl.size() > 0)
			{
				CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
				if (sysCustomer != null)
				{
					receivingBillInfo.setPayerID(sysCustomer.getId().toString());
					receivingBillInfo.setPayerNumber(sysCustomer.getNumber());
					receivingBillInfo.setPayerName(sysCustomer.getName());
				}
			}
		}
		return receivingBillInfo;
	}
	
	
	/**
	 * У��ѡ��ť
	 */
	protected void verifyChoose()
	{
			if(this.f7TenancyContract.getValue() == null)
			{
				MsgBox.showWarning("���޺�ͬΪ�գ��޷����е�ǰ������"	);
				this.abort();
			}
			
			if(GatheringObjectEnum.room.equals(ComboGatheringOjbect.getSelectedItem())){
				if(this.f7Room.getValue() == null)
				{
					MsgBox.showWarning("��ѡ�񷿼䣡");
					this.abort();
				}
			}else if(GatheringObjectEnum.accessorialResource.equals(ComboGatheringOjbect.getSelectedItem())){
				if(this.f7Accessorial.getValue() == null)
				{
					MsgBox.showWarning("��ѡ��������Դ��");
					this.abort();
				}
			}else{
				MsgBox.showWarning("����ϵͳ��û�ж��豸���տ");
				this.abort();
			}

	}
	
	
	/**
	 * ͨ����ѡ����ʽ�ϵͳ�����ƽ�����һЩ��ʾ
	 * @param moneySysTypeEnum
	 * @param itemEvent
	 * @author laiquan_luo
	 */
	protected void  changeUIByMoneySys(MoneySysTypeEnum moneySysTypeEnum,ItemEvent itemEvent)
	{
			//������޺�ͬ��ֵ�Ļ��������ͻ����ã��Ͳ�����������
			if(this.f7TenancyContract.getValue() == null)
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,moneySysTypeEnum, null));
			}
			this.lcTenancyContract.setVisible(true);
			this.getParent();
			if(this.f7Room.getValue() == null)
			{
				this.f7TenancyContract.setEnabled(false);
			}
			else
			{
				this.f7TenancyContract.setEnabled(true);
			}
			if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))
			{
				this.f7TenancyContract.setEnabled(false);
			}
			this.contPurchase.setVisible(false);
			//ֱ�Ӵ���ͬ�����ģ�����Ҫѡ�񷿼�
			if(this.getUIContext().get(KEY_MONEYTSYSTYPE) != null &&
					this.getUIContext().get(KEY_MONEYTSYSTYPE).equals(MoneySysTypeEnum.TenancySys))
			{
			   //this.btnChooseRoom.setVisible(false);
			   //this.contRoom.setVisible(false);
			   this.lcTenancyContract.setVisible(true);
			}
			this.contSalesman.setBoundLabelText("���޹���");
			
			if(this.kDTable1.getColumn("derateAmount") != null)
			this.kDTable1.getColumn("derateAmount").getStyleAttributes().setHided(true);
	}
	
	
	protected void setControlByGathering(GatheringEnum gathering) {
		if(GatheringEnum.ObligatedRoom.equals(gathering)){
			setControlVisible(true, false, false, false, false, true);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
			setControlVisible(false, true, false, false, false, true);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			setControlVisible(true, false, false, true, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			setControlVisible(false, true, false, true, false, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.CustomerRev.equals(gathering))	{
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(false);
		}
	}
	
	
	
	
	
	
}
