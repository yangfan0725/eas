/**
 * ���޿����Ҳ࣬������Ϣ�ͷ���ҵ������
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.query.QuerySort.SortItem;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RoomDetailInfoUI extends AbstractRoomDetailInfoUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomDetailInfoUI.class);

	public void loadFields()
	{
		super.loadFields();
		RoomInfo room = (RoomInfo) this.editData;
		//��ӷ�����Ϣ eric_wang 2010.06.12
		
		this.txtRoomInfo.setText(room.getName());
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtTenancyArea.setValue(room.getTenancyArea());
		this.txtStandardRent.setValue(room.getStandardRent());
		this.comboStandardRentType.setSelectedItem(room.getRentType());
		
		this.comboTenancyState.setSelectedItem(room.getTenancyState());
		
		this.tabBizInfo.removeAll();


		// ҵ��   i.	����ҵ��������ʾ���޺�ͬ
		addBizTable(room);
//		// Ӧ��   b)	Ӧ��-��������,����,����ķ�����ʾ   i.	��ʾ�÷��䵱ǰ��ͬ�ĸ�����ϸ		
//		addPlanPayTable(room);
//		// ʵ��   i.	��ʾ����������տ.����ҵ����������.
//		addActPayTable(room);
	}





	private void addPlanPayTable(RoomInfo room)
	{  //�������� Ӧ������   �������� ��� �ұ�
		String[] keyNames = {"startDate,��ʼ����","endDate,��������","appPayDate,Ӧ������","moneyDefine,��������","appAmountR2,Ӧ�ս��","actAmountR2,ʵ�ս��"};//"currency,�ұ�" 
		final KDTable planPayTable = CommerceHelper.getTheTableByKeyNames(keyNames);
		
		if(room.getTenancyState()!=null && !room.getTenancyState().equals(TenancyStateEnum.newTenancy) 
				&& !room.getTenancyState().equals(TenancyStateEnum.continueTenancy) 
				&& !room.getTenancyState().equals(TenancyStateEnum.enlargeTenancy))  
			return;

		TenancyRoomPayListEntryCollection tenPayList = getPayListCollByRoom(room);
		for(int i=0;i<tenPayList.size();i++) {
			TenancyRoomPayListEntryInfo payInfo = tenPayList.get(i);
			Map keyObjects = new HashMap();
			keyObjects.put("startDate",payInfo.getStartDate());
			keyObjects.put("endDate",payInfo.getEndDate());
			keyObjects.put("appPayDate",payInfo.getAppDate());
			keyObjects.put("moneyDefine",payInfo.getMoneyDefine());
			keyObjects.put("appAmountR2",payInfo.getAppAmount());
			keyObjects.put("actAmountR2",payInfo.getActRevAmount());
//			keyObjects.put("currency",payInfo.getCurrency());
			CommerceHelper.fillTheTableByKeyObjects(planPayTable,keyObjects);
		}
		
		this.tabBizInfo.add(planPayTable, "Ӧ��");
	}

	private void addActPayTable(RoomInfo room)
	{
		KDTable actPayTable = new KDTable();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("revListType", RevListTypeEnum.TENROOMREV_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("revListType", RevListTypeEnum.TENOTHERREV_VALUE));
		filter.setMaskString("#0 and (#1 or #2)");
		
		view.setFilter(filter);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("revAmount");
		sels.add("moneyDefine.name");
		sels.add("head.bizDate");
		sels.add("head.customer.name");
		sels.add("head.currency.name");
		sels.add("head.creator.name");
		sels.add("head.billStatus");
		sels.add("head.tenancyObj.number");
		view.setSelector(sels);
		
		SorterItemCollection coll = new SorterItemCollection();
		SorterItemInfo sii =new SorterItemInfo("head.bizDate");
		sii.setSortType(SortType.DESCEND);
		SorterItemInfo sii1 =new SorterItemInfo("head.revBizType");
		sii.setSortType(SortType.DESCEND);
		coll.add(sii1);
		coll.add(sii);
		
		view.setSorter(coll);
		
		FDCReceivingBillEntryCollection revEs = null;
		try {
			revEs = FDCReceivingBillEntryFactory.getRemoteInstance().getFDCReceivingBillEntryCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		TenancyHelper.fillActPayTable(actPayTable, revEs, TENReceivingBillEditUI.class);
		this.tabBizInfo.add(actPayTable, "ʵ��");
	}
	
//	private void addActPayTable(RoomInfo room)
//	{
//		KDTable actPayTable = new KDTable();
//		
//		EntityViewInfo view = new EntityViewInfo();
//		view.getSorter().add(new SorterItemInfo("createTime"));
//		view.getSelector().add("amount");
//		view.getSelector().add("bizDate");
//		view.getSelector().add("currency.name");
//		view.getSelector().add("payerName");
//		view.getSelector().add("creator.name");
//		view.getSelector().add("fdcReceiveBillEntry.amount");
//		view.getSelector().add("fdcReceiveBillEntry.moneyDefine.name");
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.moneySysType", MoneySysTypeEnum.TenancySys));
//		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room.id", room.getId().toString()));
//		
//		ReceivingBillCollection tenActPayColl = null;
//		try {
//			tenActPayColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
//		} catch (BOSException e) {
//			super.handUIExceptionAndAbort(e);
//		}
//		//TODO ���տ��������Ҫ���޸�
//		SHEHelper.fillActPayTable(actPayTable, tenActPayColl, TENReceiveBillEditUI.class);
//		this.tabBizInfo.add(actPayTable, "ʵ��");
//	}	
	private KDTable bizTable;
	public KDTable getBizTable() {
		return bizTable;
	}

	private void addBizTable(RoomInfo room)
	{
		String[] keyNames = {"conStatus,��ͬ״̬","conType,��ͬ����","customer,�ͻ�","tenBeginDate,���ڿ�ʼ",
				"tenEndDate,���ڽ���","tenPeriod,�ڼ䳤��","bizDate,ҵ������","createTimeD,�Ƶ�����","standRentR2,��׼���","dealRentR2,�ɽ����","id,ID"};

		 bizTable = CommerceHelper.getTheTableByKeyNames(keyNames);
		this.tabBizInfo.add(bizTable, "ҵ��");
		
		
		if(room.getTenancyState()==null || room.getTenancyState().equals(TenancyStateEnum.unTenancy))
			return;
		
		
		bizTable.addKDTMouseListener(new KDTMouseListener()		{
			public void tableClicked(KDTMouseEvent e)	{
				try	{
					if (e.getButton() == 1 && e.getClickCount() == 2)	{
						int rowIndex = e.getRowIndex();
						if (rowIndex == -1) 	{
							return;
						}
						IRow row = bizTable.getRow(rowIndex);
						Object bizObj = row.getCell("id").getValue();
						if(bizObj!=null && bizObj instanceof String) {
							CommerceHelper.openTheUIWindow(this,TenancyBillEditUI.class.getName(),bizObj.toString());
						}						
					}
				} catch (Exception exc)		{
					handUIException(exc);
				} finally{
				}
			}
		});

		//KeepRoomDownCollection keepRoomDownColl = getKeepRoomDownColl(roomId);
		//�������޺�ͬ��
		TenancyBillCollection tenancyBillColl = this.getTenancyBillCollByRoomId(room.getId().toString());		
		for(int i=0;i<tenancyBillColl.size();i++) {
			TenancyBillInfo tenBill = (TenancyBillInfo)tenancyBillColl.get(i);
			Map keyObjects = new HashMap();			
			keyObjects.put("conStatus",tenBill.getTenancyState());
			keyObjects.put("conType",tenBill.getTenancyType());
			keyObjects.put("customer",tenBill.getTenCustomerDes());  //����ͻ�,...
			keyObjects.put("tenBeginDate",tenBill.getStartDate());
			keyObjects.put("tenEndDate",tenBill.getEndDate());
			keyObjects.put("tenPeriod",tenBill.getLeaseCount());
			keyObjects.put("bizDate",tenBill.getTenancyDate());
			keyObjects.put("createTimeD",tenBill.getCreateTime());
			keyObjects.put("standRentR2",tenBill.getStandardTotalRent());
			keyObjects.put("dealRentR2",tenBill.getDealTotalRent());
			keyObjects.put("id",tenBill.getId().toString());
			CommerceHelper.fillTheTableByKeyObjects(bizTable,keyObjects);
		}		
	
		
	}



	
	/**
	 * ���ݷ����ID ��ø÷�������к�ͬ
	 * @param roomId
	 * @return
	 */
	private TenancyBillCollection getTenancyBillCollByRoomId(String roomId)
	{
		TenancyBillCollection tenancyBillColl = new TenancyBillCollection();;
		TenancyRoomEntryCollection tenancyRoomEntryColl = null;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tenancy.*");
		
		FilterInfo filter = new FilterInfo();		
		filter.getFilterItems().add(new FilterItemInfo("room",roomId));
		view.setFilter(filter);
		SorterItemCollection sortColl = new SorterItemCollection ();
		SorterItemInfo sortItem = new SorterItemInfo("tenancy.tenancyDate");
		sortItem.setSortType(SortType.ASCEND);
		sortColl.add(sortItem);
		view.setSorter(sortColl);
		
		try	{
			tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		} catch (BOSException e){
			super.handUIException(e);
		}
		
		for(int i=0;i<tenancyRoomEntryColl.size();i++) 
			tenancyBillColl.add(tenancyRoomEntryColl.get(i).getTenancy());

		return tenancyBillColl;
	}
	
	
	/**
	 * ���ݷ����ID ��ø÷�������и���ƻ�
	 * ��room��lastTenancy�ֶλ�÷���ĵ�ǰ��ͬ,��ͨ��tenancyId�ͷ���IDһ����˸�����ϸ
	 * @param roomId
	 * @return
	 * @throws BOSException 
	 */
	private TenancyRoomPayListEntryCollection getPayListCollByRoom(RoomInfo room)
	{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("startDate");
		view.getSelector().add("endDate");
		view.getSelector().add("AppPayDate");
		view.getSelector().add("AppDate");
		view.getSelector().add("moneyDefine");
		view.getSelector().add("moneyDefine.number");
		view.getSelector().add("moneyDefine.name");
		view.getSelector().add("appAmount");
		view.getSelector().add("actAmount");
		view.getSelector().add("actRevAmount");
		
		view.getSorter().add(new SorterItemInfo("seq"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room.id",room.getId().toString()));
		if(room.getLastTenancy() != null  &&  room.getLastTenancy().getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy.id", room.getLastTenancy().getId().toString()));	
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		
		view.setFilter(filter);
		TenancyRoomPayListEntryCollection tenRoomPays = null;
		try {
			tenRoomPays = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
		} catch (BOSException e) {
			super.handUIException(e);
		}
		return tenRoomPays;
	}
	
	/**
	 * output class constructor
	 */
	public RoomDetailInfoUI() throws Exception
	{
		super();
	}

	private void setTextFormat(KDFormattedTextField text)
	{
		text.setRemoveingZeroInDispaly(false);
		text.setRemoveingZeroInEdit(false);
		text.setPrecision(2);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setSupportedEmpty(true);
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		txtRoomInfo.setHorizontalAlignment(JTextField.LEFT);
		setTextFormat(txtBuildingArea);
		setTextFormat(txtRoomArea);
		setTextFormat(txtApportionArea);
		setTextFormat(txtBalconyArea);
		setTextFormat(txtActualBuildingArea);
		setTextFormat(txtTenancyArea);
		setTextFormat(txtActualBuildingArea);
		setTextFormat(txtActualRoomArea);
		setTextFormat(txtStandardRent);
	}

	protected IObjectValue createNewData()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}
}