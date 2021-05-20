/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class VirtualRoomFullInfoUI extends AbstractVirtualRoomFullInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(VirtualRoomFullInfoUI.class);
    
//    private PrePurchaseManageInfo prePurchaseInfo = null;
    
//    private PurchaseManageInfo purchaseInfo = null;
    
//    private SignManageInfo signInfo = null;
    
//    private PurchaseInfo purchase = null;

//	private RoomSignContractInfo sign = null;

	//=== �ۺ��������====
	private static String PROCESSES_NAME = "processName";
	private static String PROCESSES_LIMITDATE = "limitDate";
	private static String PROCESSES_ISFINISH = "isFinish";
	private static String PROCESSES_ACTDATE = "actDate";
	private static String PROCESSES_PERSON = "person";
	private static String DATA_NAEM = "dataName";
	private static String DATA_ISOK = "isOk";
	private static String DATA_BIZDATE = "bizDate";
	private static String DES = "des";
	private static String ID = "id";
	
	
	CoreUIObject detailUI = null;
	
	CoreUIObject keepDownUI = null;
	private SellProjectInfo sellProject = null;

	public void loadFields() {
		super.loadFields();
		RoomInfo room = (RoomInfo) this.editData;
//		String roomModeTypeName = null;
		try {
//			if(room.getRoomModel()!= null && room.getRoomModel().getId().toString()!= null){
//				roomModeTypeName = SHEHelper.getRoomModelTypeName(room.getRoomModel().getId().toString());;
//			}
			room = SHEHelper.queryRoomInfo(room.getId().toString());
			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		} 
		this.txtNumber.setText(room.getName());
		if(room.getRoomModel()!=null)
			this.f7RoomModel.setValue(room.getRoomModel().getName());
		if(room.getRoomModel() != null && room.getRoomModel().getRoomModelType() !=null){
			this.txtRoomModelType.setText(room.getRoomModel().getRoomModelType().getName());
		}
		this.boxRoomState.setSelectedItem(room.getSellState());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtPricingType.setText(room.getCalcType()!=null?room.getCalcType().getAlias():"");
//		this.txtSellType.setText(room.getSellType()!=null?room.getSellType().getAlias():"");
//		SellTypeEnum sellType = (SellTypeEnum)room.getSellType();
//		if(SellTypeEnum.LocaleSell.equals(sellType)){
//			this.txtBuildingArea.setValue(room.getActualBuildingArea());
//			this.txtRoomArea.setValue(room.getActualRoomArea());
//		}
//		else if(SellTypeEnum.PreSell.equals(sellType)){
			this.txtBuildingArea.setValue(room.getBuildingArea());
			this.txtRoomArea.setValue(room.getRoomArea());
//		}else{
//			this.txtBuildingArea.setValue(room.getPlanBuildingArea());
//			this.txtRoomArea.setValue(room.getPlanRoomArea());
//		}
		this.txtActBuildArea.setValue(room.getActualBuildingArea());
		this.txtActRoomArea.setValue(room.getActualRoomArea());
		BigDecimal buildArea=room.getActualBuildingArea()==null?room.getBuildingArea():room.getActualBuildingArea();
		BigDecimal roomArea=room.getActualRoomArea()==null?room.getRoomArea():room.getActualRoomArea();
		
		this.txtStandardBuildPrice.setValue(FDCHelper.divide(room.getStandardTotalAmount(), buildArea, 2, BigDecimal.ROUND_HALF_UP));
		this.txtStandardRoomPrice.setValue(FDCHelper.divide(room.getStandardTotalAmount(), roomArea, 2, BigDecimal.ROUND_HALF_UP));
		this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
		
		RoomSellStateEnum state = room.getSellState();
//		PurchaseManageCollection col = null;
//		if (RoomSellStateEnum.PrePurchase.equals(state)
//				||RoomSellStateEnum.Purchase.equals(state)
//				||RoomSellStateEnum.Sign.equals(state)){
//			//�õ�Ԥ����
//			try {
//				if(RoomSellStateEnum.PrePurchase.equals(state)){
//					PrePurchaseManageCollection prePurchaseInfo = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection("select saleManNames,dealTotalAmount,dealBuildPrice,dealRoomPrice,id,bizDate,payType.*,bizState from where room.id='"+room.getId().toString()+ "' and bizState in('PreApple','PreAudit')");
//					if(prePurchaseInfo.size()>0){
////						this.txtDealTotalAmount.setVisible(false);
////						this.txtDealBuildPrice.setVisible(false);
////						this.txtDealRoomPrice.setVisible(false);
//						this.txtSaleMan.setText(prePurchaseInfo.get(0).getSaleManNames());
//					}
//				}
//				if(RoomSellStateEnum.Purchase.equals(state)){
//					PurchaseManageCollection purchaseInfo = PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection("select saleManNames,dealTotalAmount,dealBuildPrice,dealRoomPrice,id,bizDate,payType.*,bizState from where room.id='"+room.getId().toString()+ "' and bizState in('PurApple','PurAudit')");
//					if(purchaseInfo.size()>0){
////						this.txtDealTotalAmount.setVisible(false);
////						this.txtDealBuildPrice.setVisible(false);
////						this.txtDealRoomPrice.setVisible(false);
//						this.txtSaleMan.setText(purchaseInfo.get(0).getSaleManNames());
//					}
//				}
//				if(RoomSellStateEnum.Sign.equals(state)){
//					SignManageCollection signInfp = SignManageFactory.getRemoteInstance().getSignManageCollection("select saleManNames,dealTotalAmount,dealBuildPrice,dealRoomPrice,id,bizDate,payType.*,bizState from where room.id='"+room.getId().toString()+ "' and bizState in('SignApple','SignAudit')");
//					if(signInfp.size()>0){
//						this.txtDealTotalAmount.setVisible(true);
//						this.txtDealBuildPrice.setVisible(true);
//						this.txtDealRoomPrice.setVisible(true);
//						this.txtDealTotalAmount.setValue(signInfp.get(0).getDealTotalAmount());
//						this.txtDealBuildPrice.setValue(signInfp.get(0).getDealBuildPrice());
//						this.txtDealRoomPrice.setValue(signInfp.get(0).getDealRoomPrice());
//						this.txtSaleMan.setText(signInfp.get(0).getSaleManNames());
//					}
//				}
//			} catch (Exception e1) {
//				logger.warn(e1);
//				logger.debug("", e1);
//			}
//			join = SHEHelper.getRoomJoinIn(room);
//			areaCompensate = SHEHelper.getRoomAreaCompensation(room);
//			loan = SHEHelper.getRoomLoan(room);
//			propertyBook = SHEHelper.getRoomPropertyBook(room);
//		} else{
//			prePurchaseInfo = null;
//			purchaseInfo = null;
//			signInfo = null;
//			join = null;
//			areaCompensate = null;
//			loan = null;
//			propertyBook = null;
//		}

		if (state.equals(RoomSellStateEnum.Init)) {
			tabBizInfo.removeAll();
//			showKeepDownInfo();
//			tabBizInfo.add("Ԥ��",keepDown);
		} else if (state.equals(RoomSellStateEnum.OnShow)) {
			tabBizInfo.removeAll();
			try {
				initBuyRoomPlan();
			} catch (Exception e1) {
				logger.warn(e1);
				logger.debug("", e1);
			}
			tabBizInfo.add("��ҵ�ƻ�", buyRoomPlanPane);
			//����û�б�ҪҪԤ��ҳǩ�ˣ�Ҫ������ȷ��
//			showKeepDownInfo();
//			tabBizInfo.add("Ԥ��",keepDown);
			tabBizInfo.setSelectedIndex(0);
		} else if (state.equals(RoomSellStateEnum.KeepDown)) {
			tabBizInfo.removeAll();
			showKeepDownInfo();
			tabBizInfo.add("Ԥ��",keepDown);
		} else if (state.equals(RoomSellStateEnum.SincerPurchase)) {
			tabBizInfo.removeAll();
			tabBizInfo.add("ԤԼ�ź�",sincerityPurchase);
			showSincerityPurchaseInfo();
		} else if (state.equals(RoomSellStateEnum.PrePurchase)) {
			tabBizInfo.removeAll();
			tabBizInfo.add("ҵ������",bizTotalFlow);
			showBizTotalFlowInfo();
			//������ۺ���������������ʾ(���ҷ��񡢲�Ȩ����������)
			addServicePlan(room);
		} else if (state.equals(RoomSellStateEnum.Purchase)) {
			tabBizInfo.removeAll();
			tabBizInfo.add("ҵ������",bizTotalFlow);
			showBizTotalFlowInfo();
			//������ۺ���������������ʾ(���ҷ��񡢲�Ȩ����������)
			addServicePlan(room);
			
		}else if (state.equals(RoomSellStateEnum.Sign)) {
			tabBizInfo.removeAll();
			tabBizInfo.add("ҵ������",bizTotalFlow);
			showBizTotalFlowInfo();
			//������ۺ���������������ʾ(���ҷ��񡢲�Ȩ����������)
			addServicePlan(room);
		}
		this.tabBizInfo.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(tabBizInfo.getSelectedComponent()==null) return;
				String comName = tabBizInfo.getSelectedComponent().getName();
				int comIndex = tabBizInfo.getSelectedIndex();
				if(comIndex==0) return;
				if(comName==null) return;
				if(comName.equals("buyRoomPlanPane")) {
					initBuyRoomPlan();
				}else if(comName.equals("bizTotalFlow")){
					showBizTotalFlowInfo();
				}else if(comName.equals("roomLoanPanel")){
					showRoomLoanInfo();
				}else if(comName.equals("roomPropertyBook")){
					showroomPropertyBookInfo();
				}else if(comName.equals("roomJoin")){
					showroomJoinInfo();
					
				}else if(comName.equals("sincerityPurchase")){
					showSincerityPurchaseInfo();
				}else if(comName.equals("keepDown")){
					showKeepDownInfo();
				}
			}

			
		});
		this.getUIContext().put("sincerityPurchaseTable", sincerityPurchaseTable);
		
	}
	
	
	private void showroomJoinInfo() {
		this.tblJoinProcesses.checkParsed();
		this.tblJoinProcesses.removeRows();
		this.tblJoinData.checkParsed();
		this.tblJoinData.removeRows();
		RoomInfo room = (RoomInfo)this.editData;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		SelectorItemCollection sel = new SelectorItemCollection();
		SorterItemCollection sort = new SorterItemCollection();
		sel.add("approachEntry.transactor.*");
		sel.add("approachEntry.*");
		sel.add("dataEntry.*");
		view.setFilter(filter);
		view.setSelector(sel);
		view.setSorter(sort);
		RoomJoinCollection  roomcoll = null;
		try {
			 roomcoll = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
		}
		RoomJoinInfo info = roomcoll.get(0);
		if(info==null)return;
		RoomJoinApproachEntryCollection roomApp = info.getApproachEntry();
		for(int i = 0 ; i <roomApp.size();i++){
			RoomJoinApproachEntryInfo roomJoininfo = roomApp.get(i);
			IRow row = tblJoinProcesses.addRow();
			row.getCell(PROCESSES_NAME).setValue(roomJoininfo.getName());
			row.getCell(PROCESSES_LIMITDATE).setValue(roomJoininfo.getPromiseFinishDate());
			row.getCell(PROCESSES_ISFINISH).setValue(Boolean.valueOf(roomJoininfo.isIsFinish()));
			row.getCell(PROCESSES_ACTDATE).setValue(roomJoininfo.getActualFinishDate());
			row.getCell(DES).setValue(roomJoininfo.getRemark());
			row.getCell(ID).setValue(info.getId().toString());
		}
		RoomJoinDataEntryCollection  roomData = info.getDataEntry();
		for(int i = 0 ; i <roomData.size();i++){
			RoomJoinDataEntryInfo roomDataInfo = roomData.get(i);
			IRow row = tblJoinData.addRow();
			row.getCell(DATA_NAEM).setValue(roomDataInfo.getName());
			row.getCell(DATA_ISOK).setValue(Boolean.valueOf(roomDataInfo.isIsFinish()));
			row.getCell(DATA_BIZDATE).setValue(roomDataInfo.getFinishDate());
			row.getCell(ID).setValue(info.getId().toString());
		}
		
	}

	private void showroomPropertyBookInfo() {
		this.tblPBProcesser.checkParsed();
		this.tblPBProcesser.removeRows();
		this.tblPBData.checkParsed();
		this.tblPBData.removeRows();
		RoomInfo room = (RoomInfo)this.editData;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		SelectorItemCollection sel = new SelectorItemCollection();
		SorterItemCollection sort = new SorterItemCollection();
		sel.add("entry.transactor.*");
		sel.add("entry.*");
		sel.add("entryTwo.*");
		view.setFilter(filter);
		view.setSelector(sel);
		view.setSorter(sort);
		RoomPropertyBookCollection  roomcoll = null;
		try {
			 roomcoll = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
		}
		RoomPropertyBookInfo info = roomcoll.get(0);
		if(info==null)return;
		RoomPropertyBookEntryCollection roomApp = info.getEntry();
		for(int i = 0 ; i <roomApp.size();i++){
			RoomPropertyBookEntryInfo roomJoininfo = roomApp.get(i);
			IRow row = tblPBProcesser.addRow();
			row.getCell(PROCESSES_NAME).setValue(roomJoininfo.getName());
			row.getCell(PROCESSES_LIMITDATE).setValue(roomJoininfo.getPromiseFinishDate());
			row.getCell(PROCESSES_ISFINISH).setValue(Boolean.valueOf(roomJoininfo.isIsFinish()));
			row.getCell(PROCESSES_ACTDATE).setValue(roomJoininfo.getActualFinishDate());
			row.getCell(DES).setValue(roomJoininfo.getDescription());
			row.getCell(ID).setValue(info.getId().toString());
		}
		RoomPropertyBookEntryTwoCollection  roomData = info.getEntryTwo();
		for(int i = 0 ; i <roomData.size();i++){
			RoomPropertyBookEntryTwoInfo roomDataInfo = roomData.get(i);
			IRow row = tblPBData.addRow();
			row.getCell(DATA_NAEM).setValue(roomDataInfo.getName());
			row.getCell(DATA_ISOK).setValue(Boolean.valueOf(roomDataInfo.isIsFinish()));
			row.getCell(DATA_BIZDATE).setValue(roomDataInfo.getProcessDate());
			row.getCell(ID).setValue(info.getId().toString());
		}
		
	
	}

	private RoomLoanCollection getRoomloadColl(RoomInfo room){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.BANKFUND_VALUE)));
		filter.setMaskString("#0 and ( #1 or #2 or #3 or #4)");
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("mmType.*");
		sel.add("*");
		sel.add("oRSOMortgaged.name");
		sel.add("room.id");
		sel.add("room.building.sellProject.id");
		SorterItemCollection sort = new SorterItemCollection();
		view.setFilter(filter);
		view.setSelector(sel);
		view.setSorter(sort);
		RoomLoanCollection  roomcoll = null;
		try {
			 roomcoll = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
		}
		return roomcoll;
	}
	
	private void showRoomLoanInfo() {
		this.tblLoan.checkParsed();
		tblLoan.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblLoan.getStyleAttributes().setLocked(true);
		this.tblLoan.removeRows();
		RoomLoanCollection  roomcoll = getRoomloadColl((RoomInfo)this.editData);
		for(int i = 0 ; i < roomcoll.size() ; i++){
			RoomLoanInfo  info = roomcoll.get(i);
			IRow row = this.tblLoan.addRow();
			row.setUserObject(info);
			row.getCell("loanNumber").setValue(info.getNumber());
			row.getCell("loanState").setValue(info.getAFMortgagedState());
			row.getCell("appDate").setValue(info.getApplicationDate());
			row.getCell("curProcess").setValue(info.getApproach());
			row.getCell("promiseDate").setValue(info.getPromiseDate());
			row.getCell("actFinishDate").setValue(info.getActualFinishDate());
			row.getCell("afmType").setValue(info.getMmType()!=null?info.getMmType().getMoneyType():null);
			row.getCell("afmAmount").setValue(info.getActualLoanAmt());
			row.getCell("afm").setValue(info.getORSOMortgaged()!=null?info.getORSOMortgaged().getName():null);
			row.getCell(ID).setValue(info.getId().toString());
		}
	}
	
	private void addServicePlan(RoomInfo room) {
		
//		//����
//		if(getRoomloadColl(room).size()>0){
//			tabBizInfo.add("���ҷ���",this.roomLoanPanel);
//		}
//		//���
//		if(RoomJoinStateEnum.JOINING.equals(room.getRoomJoinState())||RoomJoinStateEnum.JOINED.equals(room.getRoomJoinState())){
//			tabBizInfo.add("������",this.roomJoin);
//		}
//		//��Ȩ
//		if(RoomBookStateEnum.BOOKING.equals(room.getRoomLoanState())||RoomBookStateEnum.BOOKED.equals(room.getRoomLoanState())){
//			tabBizInfo.add("��Ȩ����",this.roomPropertyBook);
//		}
		
	}
	/**
	 * ȡ�õ�ǰ���ݵĸ����
	 * @param tbovCol
	 * @return
	 */
	private SHEPayTypeInfo getPayType(){
		RoomInfo room = (RoomInfo) this.editData;
		TransactionInfo transactionInfo;
		try {
			transactionInfo = SHEManageHelper.getTransactionInfo(null, room);
			BOSUuid billId=transactionInfo.getBillId();
			Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(billId).getObjectType(),new ObjectUuidPK(billId));
				if(billInfo instanceof BaseTransactionInfo){
				BaseTransactionInfo btInfo=(BaseTransactionInfo)billInfo;
				if(null!=btInfo.getPayType()){
					SHEPayTypeInfo ptInfo=SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(btInfo.getPayType().getId()));
					return ptInfo;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			this.handleException(e);
			this.abort(e);
		}
		return null;
	}
	/**
	 * ҵ����ݳ���ҵ��˳������
	 * @param busAl Ҫ�����ҵ�����
	 * @return
	 */
	private ArrayList sortBusiness(ArrayList busAl){
		ArrayList lastAl =new ArrayList();
		ArrayList BizFlowAl =new ArrayList();
		BizFlowAl.add(SHEManageHelper.PRELIMINARY);
		BizFlowAl.add(SHEManageHelper.PURCHASE);
		BizFlowAl.add(SHEManageHelper.SIGN);
		BizFlowAl.add(SHEManageHelper.MORTGAGE);
		BizFlowAl.add(SHEManageHelper.ACCFUND);
		BizFlowAl.add(SHEManageHelper.INTEREST);
		BizFlowAl.add(SHEManageHelper.JOIN);
		BizFlowAl.add(SHEManageHelper.AREACOMPENSATE);
		for(int j=0;j<BizFlowAl.size();j++){//����˳�����
			for(int k=0;k<busAl.size();k++){
				if((String)BizFlowAl.get(j)==((TranBusinessOverViewInfo)busAl.get(k)).getBusinessName()){//------------
					lastAl.add(busAl.get(k));//-------------
				}
			}
		}
		return lastAl;
	}
	
	/**
	 * ���������
	 */
	private ArrayList sortMoneyDefine(ArrayList payAl){
		ArrayList lastAl = new ArrayList();
		for(int j=0;j<payAl.size();j++){
			TranBusinessOverViewInfo tbovInfo=(TranBusinessOverViewInfo)payAl.get(j);
			try {
				TransactionInfo  tInfo= TransactionFactory.getRemoteInstance().getTransactionInfo(new ObjectUuidPK(tbovInfo.getHead().getId()));
				BOSUuid billId=tInfo.getBillId();
				Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(billId).getObjectType(),new ObjectUuidPK(billId));
				if(billInfo instanceof PurchaseManageInfo){ //����
					PurchaseManageInfo pmInfo =(PurchaseManageInfo)billInfo;
					PurPayListEntryCollection  ppleCol=pmInfo.getPurPayListEntry();
					for(int k=0;k<ppleCol.size();k++){
						MoneyDefineInfo mdInfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(ppleCol.get(k).getMoneyDefine().getId()));
						for(int m=0;m<payAl.size();m++){
							if( ((TranBusinessOverViewInfo)payAl.get(m)).getBusinessName()==mdInfo.getName()){
							lastAl.add(payAl.get(m));//����
							payAl.remove(m);
							m--;
							}
						}
					}
				}
				if(billInfo instanceof SignManageInfo){
					SignManageInfo smInfo =(SignManageInfo)billInfo;
					SignPayListEntryCollection  ppleCol=smInfo.getSignPayListEntry();
					for(int k=0;k<ppleCol.size();k++){
						for(int m=0;m<payAl.size();m++){
							MoneyDefineInfo mdInfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(ppleCol.get(k).getMoneyDefine().getId()));
							if( ((TranBusinessOverViewInfo)payAl.get(m)).getBusinessName()==mdInfo.getName()){
							lastAl.add(payAl.get(m));//����
							payAl.remove(m);
							m--;
							}
						}
					}
				}
				if(billInfo instanceof PrePurchaseManageInfo){
					PrePurchaseManageInfo pmInfo =(PrePurchaseManageInfo)billInfo;
					PrePurchasePayListEntryCollection  ppleCol=pmInfo.getPrePurchasePayListEntry();
					for(int k=0;k<ppleCol.size();k++){
						MoneyDefineInfo mdInfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(ppleCol.get(k).getMoneyDefine().getId()));
						for(int m=0;m<payAl.size();m++){
							if( ((TranBusinessOverViewInfo)payAl.get(m)).getBusinessName()==mdInfo.getName()){
							lastAl.add(payAl.get(m));//����
							payAl.remove(m);
							m--;
							}
						}
					}
				}
//				if(billInfo instanceof RoomLoanInfo){
//					RoomLoanInfo pmInfo =(RoomLoanInfo)billInfo;
//				}
//				if(billInfo instanceof RoomAreaCompensateInfo){
////					System.out.println("running at here!    ----------->6");
//				}
//				if(billInfo instanceof RoomJoinInfo){
////					System.out.println("running at here!    ----------->7");
//				}
//				if(billInfo instanceof RoomPropertyBookInfo){
////					System.out.println("running at here!    ----------->8");
//				}
			} catch (Exception e) {
				this.handleException(e);
				this.abort(e);
			}
		}
		if(payAl.size()>0){
			lastAl.addAll(payAl);
		}
		return lastAl;
	}
	
	/**
	 * ��������д������           �������д�����죬�ڸ���������Χ�ڸо��ǳ����ӣ����Ű��Ϊ����
	 * @param tbovCol Ҫ�����ҵ����������
	 * @author xiaominWang
	 * @return �Ѿ��ź���Ķ���
	 */
	private ArrayList sort(TranBusinessOverViewCollection tbovCol){
		if(tbovCol != null && tbovCol.size()> 0){
			ArrayList lastAl=new ArrayList();//���Ҫ���صĶ���
			//�ѿ����ڵ�ҵ�������ҳ�������ȷ����һ�������׼ȷ��
			ArrayList nullAl=new ArrayList();
			for(int i=0;i<tbovCol.size();i++){
				if(null==tbovCol.get(i).getFinishDate()){
					nullAl.add(tbovCol.get(i));
					tbovCol.remove(tbovCol.get(i));
					i--;
				}                             
			}
			//׼���������ѿ����ڵ�ҵ���ҳ���, ���� �����ڶ�����ɾ��
			ArrayList nullBuAl=new ArrayList();
			for(int i=0;i<nullAl.size();i++){
				TranBusinessOverViewInfo info=(TranBusinessOverViewInfo)nullAl.get(i);
				if(null!=info.getType()&&info.getType().equals(BusTypeEnum.BUSINESS)){
					nullBuAl.add(info);//�����ڵĶ�������ҵ���
					nullAl.remove(info);//�ѿ����ڶ��е�ҵ��ɾ��
					i--;
				}
			}
			
			//�ж�����
			boolean bizDefine=false;//�Ƿ�����ҵ����ϸ,��������������ж�����
			SHEPayTypeInfo payType=getPayType();//�õ���ǰ�����
			if(null!=payType){
				if(null!=payType.getBizLists()){//���������Ҳ��ϸ
					bizDefine=true;
				}else{
					bizDefine=false;
				}
			}
			
			//��һ������
			CRMHelper.sortCollection(tbovCol, "finishDate", true);//��������󣬾�����ͬ���ڵ�ҵ����������һ�𣬿����ڵ�ҵ�����������ɢ
			
			//��Ը����� ��������������
//			//�ڶ�������  �Ѿ������ʱ���ҵ�������ۼ���һ��������
			ArrayList newAl=new ArrayList();
			for(int i=0;i<tbovCol.size();){
				TranBusinessOverViewInfo info =(TranBusinessOverViewInfo)tbovCol.get(i);
				ArrayList sonAl=new ArrayList();
				sonAl.add(info);//������������
				for(int j=i;j<tbovCol.size();j++){
					if(j+1==tbovCol.size()){//��������һ���ˣ��Ͳ�ȥ�Ƚ���,��Ϊ����� al.get(j+1) ������±�Խ��
						i++;
						break;
					}
					TranBusinessOverViewInfo nextInfo = (TranBusinessOverViewInfo)tbovCol.get(j+1);
					try {
						DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
						Date date=df.parse(df.format(info.getFinishDate()));
						Date nextDate=df.parse(df.format(nextInfo.getFinishDate()));
						if(date.compareTo(nextDate)==0){
							sonAl.add(nextInfo);//����һ���������ȵļ������
							if(j+2==tbovCol.size()){//��� ���һ��Ҳ�����ˣ��Ͳ���Ҫ��ȥѭ��������
								i=j+2;//ʹ�������ѭ��
								break;//�������ѭ��
							}
						}else{
							i=j+1;//�������������ȵ��Ǹ�λ��
							break;
						}
					} catch (ParseException e) {
						this.handleException(e);
						this.abort(e);
					}
				}
				newAl.add(sonAl);//����ͬʱ��Ķ��м����ܶ���
			}
			//newAl:�Ѿ��ź�������װ����ͬ����(û�п�����)��ҵ���������еĶ���
			//���������򣬰���ͬʱ���ҵ�񡢸�����ϸ��������
			       //��һ�������������ҵ����ϸ
			if(bizDefine){//���������ҵ����ϸ
				for(int i=0;i<newAl.size();i++){//�Ѳ��ǿ����ڵ�����
					ArrayList aList=(ArrayList)newAl.get(i);
					if(aList.size()==1){//����ö�����ֻ��һ��,�ӽ�ȥ
						lastAl.add(aList.get(0));
					} else if(aList.size()>1){//����ö������ж���������򣬱߼���
						ArrayList busAl=new ArrayList();
						ArrayList payAl=new ArrayList();
						for(int j=0;j<aList.size();j++){
							TranBusinessOverViewInfo info=(TranBusinessOverViewInfo)aList.get(j);
							if(info.getType().equals(BusTypeEnum.BUSINESS)){
								busAl.add(info);
							}else if(info.getType().equals(BusTypeEnum.PAY)){
								payAl.add(info);
							}
						}
						if(busAl.size()>1){//���ҵ�����������һ                            ����ҵ����ϸ��¼����
							BizListEntryCollection bleCol=	payType.getBizLists();//ҵ����ϸ��¼
							busAl.addAll(nullBuAl);//�ѿ����ڵ�ҵ��ӽ�ȥ������ڶ���  ע�⣺���� �˿ն����е�ҵ��
							for(int k=0;k<bleCol.size();k++){
								for(int m=0;m<busAl.size();m++){
									if(bleCol.get(k).getName()==((TranBusinessOverViewInfo)busAl.get(m)).getBusinessName()){
										lastAl.add(busAl.get(m));//�������ʾ�Ķ����м��� TODO
										busAl.remove(m);
										m--;
									}
								}
							}
							if(busAl.size()>0){//��û�ӽ�ȥ�ļӽ�ȥ
								busAl=sortBusiness(busAl);
								lastAl.addAll(busAl);
							}
						}else if(busAl.size()==1){
							lastAl.add(busAl.get(0));
						}
						if(payAl.size()>1){//����������������һ
							ArrayList mdAl=sortMoneyDefine(payAl);
							lastAl.addAll(mdAl);
							lastAl.addAll(payAl);//��ʣ��ļӽ�ȥ
						}else if(payAl.size()==1){
							lastAl.add(payAl.get(0));
						}
					}	
				}
					
				//�ѿ����ڵ�����
				ArrayList busAl=new ArrayList();
				ArrayList payAl=new ArrayList();
				for(int j=0;j<nullAl.size();j++){
					TranBusinessOverViewInfo info=(TranBusinessOverViewInfo)nullAl.get(j);
					if(null!=info.getType()){
						if(info.getType().equals(BusTypeEnum.BUSINESS)){
							busAl.add(info);
						}else if(info.getType().equals(BusTypeEnum.PAY)){
							payAl.add(info);
						}
					}else{
						payAl.add(info);
					}
				}
				if(busAl.size()>1){//���ҵ�����������һ
					
					ArrayList bAl=sortBusiness(busAl);
					lastAl.addAll(bAl);
				}else{
					lastAl.addAll(busAl);
				}
				if(payAl.size()>1){//����������������һ
					ArrayList mdAl=sortMoneyDefine(payAl);
					lastAl.addAll(mdAl);
					lastAl.addAll(payAl);//��ʣ��ļӽ�ȥ
				}else{
					lastAl.addAll(payAl);//���ս����lastAl
				}
			//�ڶ�����������û�и��������û��ҵ����ϸ	
			}else if(null!=payType||!bizDefine){//���û�и��������û��ҵ����ϸ
				//�Ȱ�ҵ���˳���ź�
				ArrayList payAl=new ArrayList();
				newAl.add(nullBuAl);//�ѿ����ڵ�ҵ��ӽ�ȥ
				for(int i=0;i<newAl.size();i++){
					ArrayList aList=(ArrayList)newAl.get(i);
					if(aList.size()==1&&((TranBusinessOverViewInfo)aList.get(0)).getType().equals(BusTypeEnum.BUSINESS)){//����ö�����ֻ��һ����Ϊҵ��ʱ,�ӽ�ȥ
						lastAl.add(aList.get(0));
					} else if(aList.size()>1){//����ö������ж���������򣬱߼���
						ArrayList busAl=new ArrayList();
						for(int j=0;j<aList.size();j++){
							TranBusinessOverViewInfo info=(TranBusinessOverViewInfo)aList.get(j);
							if(info.getType().equals(BusTypeEnum.BUSINESS)){
								busAl.add(info);
							}else if(info.getType().equals(BusTypeEnum.PAY)){
								payAl.add(info);
							}
						}
						if(busAl.size()>1){//���ҵ�����������һ
							lastAl.addAll(sortBusiness(busAl));
						}else{
							lastAl.addAll(busAl);
						}
					}else if(aList.size()==1&&((TranBusinessOverViewInfo)aList.get(0)).getType().equals(BusTypeEnum.PAY)){//����ǿ�������������
						payAl.add(aList.get(0));
					}
				}
				HashMap hMap=new HashMap();
				if(lastAl.size()>0){
					//��Ҫ����Ŀ����λ��
					for(int i=0;i<lastAl.size();i++){//��������Ĺ��̣������Ѿ�����ҵ��
						if(((TranBusinessOverViewInfo)lastAl.get(i)).getBusinessName()=="Ԥ���Ǽ�"){
							hMap.put("Ԥ���Ǽ�",new Integer(i));
						}
						if(((TranBusinessOverViewInfo)lastAl.get(i)).getBusinessName()=="ǩ�Ϲ���"){
							hMap.put("ǩ�Ϲ���",new Integer(i));
						}
						if(((TranBusinessOverViewInfo)lastAl.get(i)).getBusinessName()=="ǩԼ"){
							hMap.put("ǩԼ",new Integer(i));
						}
					}
				}
				
//				((TranBusinessOverViewInfo)payAl.get(0)).getBusinessName();
				RoomInfo room = (RoomInfo) this.editData;
				TransactionInfo transactionInfo;
				try {
					transactionInfo = SHEManageHelper.getTransactionInfo(null, room);
					BOSUuid billId=transactionInfo.getBillId();
					Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(billId).getObjectType(),new ObjectUuidPK(billId));
					if(billInfo instanceof PrePurchaseManageInfo){ //Ԥ��
							 if(null!=hMap.get("Ԥ���Ǽ�")){//�����Ԥ���Ǽǣ��ͼӵ�Ԥ���ǼǺ���
								for(int i=0;i<payAl.size();i++){
									lastAl.add(((Integer)hMap.get("Ԥ���Ǽ�")).intValue()+i,payAl.get(i));
								}
							}
					}
					else if(billInfo instanceof PurchaseManageInfo){ //�Ϲ�
						payAl.add(nullAl);//��ȥ����ҵ��Ŀ����ڶ��мӽ�ȥ
						 if(null!=hMap.get("ǩ�Ϲ���")){
							for(int i=0;i<payAl.size();i++){
								lastAl.add(((Integer)hMap.get("ǩ�Ϲ���")).intValue()+i,payAl.get(i));
							}
						}
					}
					else if(billInfo instanceof SignManageInfo){ //ǩԼ
						payAl.add(nullAl);//��ȥ����ҵ��Ŀ����ڶ��мӽ�ȥ
						 if(null!=hMap.get("ǩԼ")){
							for(int i=0;i<payAl.size();i++){
								lastAl.add(((Integer)hMap.get("ǩԼ")).intValue()+i,payAl.get(i));
							}
						}
					}else{//�����û�еĻ�
						for(int i=0;i<newAl.size();i++){
							ArrayList aList=(ArrayList)newAl.get(i);
						
							if(aList.size()==1&&((TranBusinessOverViewInfo)aList.get(0)).getType().equals(BusTypeEnum.PAY)){//����ö�����ֻ��һ��,�ӽ�ȥ
								lastAl.add(aList.get(0));
							} else if(aList.size()>1){//����ж��ҵ��������ʱ����ͬ
								ArrayList newPayAl=new ArrayList();
								for(int j=0;j<aList.size();j++){
									TranBusinessOverViewInfo info=(TranBusinessOverViewInfo)aList.get(j);
									if(info.getType().equals(BusTypeEnum.PAY)){
										newPayAl.add(info);
									}
								}
								if(newPayAl.size()>1){//����������������һ
									ArrayList mdAl=sortMoneyDefine(newPayAl);//����
									lastAl.addAll(mdAl);
								}else if(newPayAl.size()==1){
									lastAl.addAll(newPayAl);
								}
							}	
						}
						lastAl.addAll(nullAl);//�ѿն��мӽ�ȥ
					}
				} catch (BOSException e) {
					this.handleException(e);
					this.abort(e);
				}
			}
			return lastAl;
		}else{
			return null;
		}
	}
	
	/**
	 * �жϵ�ǰ���׵����µ�������һ�ֵ���
	 * @param transactionInfo
	 * @return 
	 */
	private String getBillClass(TransactionInfo transactionInfo){
		BOSUuid billId=transactionInfo.getBillId();
		Object billInfo=null;
		try {
			billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(billId).getObjectType(),new ObjectUuidPK(billId));
		} catch (BOSException e) {
			this.handUIException(e);
			this.abort();
		}
		if(billInfo instanceof PrePurchaseManageInfo){ //Ԥ��
			return PrePurchaseManageInfo.class.getName();
		}
		else if(billInfo instanceof PurchaseManageInfo){ //�Ϲ�
			return "PurchaseManageInfo";
		}
		else if(billInfo instanceof SignManageInfo){ //ǩԼ
			return "SignManageInfo";
		}
		return "others";
	}
	
/**
 * ԤԼ�źŵļ���ҵ������
 * @param room
 */
	private void addPrePurchase(RoomInfo room) {
		TransactionInfo transactionInfo = null;
		try {
			transactionInfo = SHEManageHelper.getTransactionInfo(null, room);//��������
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(transactionInfo==null){
			FDCMsgBox.showInfo(this,"�ķ���Ϊ����������");
			SysUtil.abort();
		}
		//������õ�ԤԼ�ź�
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("transactionID", transactionInfo.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));
		view.setFilter(filter);
		SincerityPurchaseCollection spCol=null;
		try{
			spCol=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection(view);
		}catch(Exception e){
			this.handleException(e);
		    this.abort();
		}
		TranBusinessOverViewCollection col=null;
		if(transactionInfo != null){
			col= transactionInfo.getTranBusinessOverView();//ҵ����������
		}else{
			return ;
		}
		//ԤԼ�źŵĲ���	
		if(null!=spCol&&spCol.size()>0){
			for(int i=0;i<spCol.size();i++){
				SincerityPurchaseInfo spInfo=(SincerityPurchaseInfo)spCol.get(i);
				IRow row = totalFlowTable.addRow();
				row.getCell("id").setValue(spInfo.getId());
				row.getCell("biz").setValue("ԤԼ�ź�");
				row.getCell("value").setValue(Boolean.TRUE);
				if(col != null && col.size()> 0){
					boolean change=false;
					if(!change){
						for(int j=0;j<col.size();j++){
							if(col.get(j).getBusinessName().equals("Ԥ���Ǽ�")){
								EntityViewInfo evInfo=new EntityViewInfo();
								FilterInfo fInfo=new FilterInfo();
								fInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
								evInfo.setFilter(fInfo);
								try {
									PrePurchaseManageCollection ppmCol=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(evInfo);
									if(!ppmCol.isEmpty()){
										row.getCell("actDate").setValue(ppmCol.get(0).getBizDate());
										
										change=true;
									}
								} catch (BOSException e) {
									this.handUIException(e);
									this.abort();
								}
							}
						}
					}
				if(!change){
					for(int j=0;j<col.size();j++){
						if(col.get(j).getBusinessName().equals("ǩ�Ϲ���")){
							EntityViewInfo evInfo=new EntityViewInfo();
							FilterInfo fInfo=new FilterInfo();
							fInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
							evInfo.setFilter(fInfo);
							try {
								PurchaseManageCollection ppmCol=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(evInfo);
								row.getCell("actDate").setValue(ppmCol.get(0).getBizDate());
								
								change=true;
							} catch (BOSException e) {
								this.handUIException(e);
								this.abort();
							}
						}
					}
				}
				if(!change){
					for(int j=0;j<col.size();j++){
						if(col.get(j).getBusinessName().equals("ǩԼ")){
							EntityViewInfo evInfo=new EntityViewInfo();
							FilterInfo fInfo=new FilterInfo();
							fInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
							evInfo.setFilter(fInfo);
							try {
								SignManageCollection ppmCol=SignManageFactory.getRemoteInstance().getSignManageCollection(evInfo);
								row.getCell("actDate").setValue(ppmCol.get(0).getBizDate());
								
								change=true;
							} catch (BOSException e) {
								this.handUIException(e);
								this.abort();
							}
						}
					}
				}
				if(!change){
					row.getCell("actDate").setValue(spInfo.getAuditTime());//�����û�еĻ���û�취�ˣ�ֻ����Ϊ����������    �Ǻǣ������߼�����������������ƺ��ǲ����ܵģ���ȷ��
				}
				}
			}
		}
	}
	
	private void showBizTotalFlowInfo() {
		RoomInfo room = (RoomInfo) this.editData;
		this.totalFlowTable.checkParsed();
		this.totalFlowTable.removeRows();
		this.totalFlowTable.getTreeColumn().setDepth(2);
		TransactionInfo transactionInfo = null;
		try {
			transactionInfo = SHEManageHelper.getTransactionInfo(null, room);//��������
			
			SelectorItemCollection sel=new SelectorItemCollection();
			ObjectUuidPK pk=new ObjectUuidPK(transactionInfo.getBillId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
			
			ArrayList customer=new ArrayList();
			if(objectValue instanceof SincerityPurchaseInfo){
				sel.add("salesman.name");
				sel.add("customer.customer.*");
				sel.add("customer.*");
				SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(transactionInfo.getBillId()),sel);
				for(int i=0;i<info.getCustomer().size();i++){
					customer.add(info.getCustomer().get(i));
				}
				this.txtSaleMan.setText(info.getSalesman().getName());
			}else if(objectValue instanceof PrePurchaseManageInfo){
				sel.add("saleManNames");
				sel.add("prePurchaseCustomerEntry.customer.*");
				sel.add("prePurchaseCustomerEntry.*");
				PrePurchaseManageInfo info=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId()),sel);
				for(int i=0;i<info.getPrePurchaseCustomerEntry().size();i++){
					customer.add(info.getPrePurchaseCustomerEntry().get(i));
				}
				this.txtSaleMan.setText(info.getSaleManNames());
			}else if(objectValue instanceof PurchaseManageInfo){
				sel.add("saleManNames");
				sel.add("purCustomerEntry.customer.*");
				sel.add("purCustomerEntry.*");
				PurchaseManageInfo info=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId()),sel);
				for(int i=0;i<info.getPurCustomerEntry().size();i++){
					customer.add(info.getPurCustomerEntry().get(i));
				}
				this.txtSaleMan.setText(info.getSaleManNames());
			}else if(objectValue instanceof SignManageInfo){
				sel.add("saleManNames");
				sel.add("dealTotalAmount");
				sel.add("dealBuildPrice");
				sel.add("dealRoomPrice");
				
				sel.add("signCustomerEntry.customer.*");
				sel.add("signCustomerEntry.*");
				SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(transactionInfo.getBillId()),sel);
				for(int i=0;i<info.getSignCustomerEntry().size();i++){
					customer.add(info.getSignCustomerEntry().get(i));
				}
				this.txtDealTotalAmount.setValue(info.getDealTotalAmount());
				this.txtDealBuildPrice.setValue(info.getDealBuildPrice());
				this.txtDealRoomPrice.setValue(info.getDealRoomPrice());
				this.txtSaleMan.setText(info.getSaleManNames());
			}
			SHEManageHelper.loadCustomer(labelCustomer1, labelCustomer2, labelCustomer3,labelCustomer4,labelCustomer5, customer, null, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//��ԤԼ�źżӽ�ȥ
		addPrePurchase(room);
		
		TranBusinessOverViewCollection col=null;
		if(transactionInfo != null){
			col= transactionInfo.getTranBusinessOverView();//ҵ����������
		}else{
			return ;
		}
		//�ų�������                   
		for(int i=0;i<col.size();i++){
			if(null==col.get(i).getBusinessName()){//���û������
				col.remove(col.get(i));
				i--;
			}
//			System.out.println("name::::"+col.get(i).getBusinessName()+col.get(i).getId());
		}
		//ҵ��������ֵ
		if(col != null && col.size()> 0){
				ArrayList sortAl=sort(col); //TODO xiaominWang   ��������ķ���
				for(int i=0;i<sortAl.size();i++){
					if(sortAl.get(i) instanceof TranBusinessOverViewInfo){
					TranBusinessOverViewInfo info = (TranBusinessOverViewInfo)sortAl.get(i);
					IRow row = totalFlowTable.addRow();
					row.getCell("id").setValue(info.getId());
					row.getCell("biz").setValue(info.getBusinessName());
					row.getCell("apDate").setValue(info.getFinishDate());
					BusTypeEnum type = info.getType();
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actRevAmount = FDCHelper.ZERO;
					if(info.getAppAmount() != null){
						appAmount = info.getAppAmount();
					}
					if(info.getActRevAmount() != null){
						actRevAmount = info.getActRevAmount();
					}
					if(BusTypeEnum.BUSINESS.equals(type)){
						if(info.isIsFinish()){
							row.getCell("value").setValue(Boolean.TRUE);
						}else{
							row.getCell("value").setValue(Boolean.FALSE);
						}
					}else if(BusTypeEnum.PAY.equals(type)){
						if(actRevAmount.subtract(appAmount).compareTo(FDCHelper.ZERO) == 0){
							row.getCell("value").setValue(Boolean.TRUE);
						}else{
							row.getCell("value").setValue(Boolean.FALSE);
						}
					}
					row.getCell("actDate").setValue(info.getActualFinishDate());
					row.getCell("description").setValue(info.getDesc());
					//���������򣬰ѱ��ҵ�����ҵ������к󣬲���Ϊ������
					if(info.getBusinessName()=="Ԥ���Ǽ�"){
						EntityViewInfo evInfo=new EntityViewInfo();
						FilterInfo filterInfo=new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
						filterInfo.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAUDIT_VALUE));
						evInfo.setFilter(filterInfo);
						try {
							ChangeManageCollection cmCol=ChangeManageFactory.getRemoteInstance().getChangeManageCollection(evInfo);
							if(null!=cmCol&&!cmCol.isEmpty()){
								CRMHelper.sortCollection(cmCol, "changeDate", true);
								for(int k=0;k<cmCol.size();k++){
									IRow sonRow = totalFlowTable.addRow();
									sonRow.setTreeLevel(1);
									sonRow.getCell("id").setValue(cmCol.get(k).getId());
									sonRow.getCell("value").setValue(new Boolean(true));
									sonRow.getCell("biz").setValue(cmCol.get(k).getBizType().getAlias());
									sonRow.getCell("actDate").setValue(cmCol.get(k).getAuditTime());
								}
							}
						} catch (Exception e) {
							this.handleException(e);
							this.abort(e);
						}
					}
					if(info.getBusinessName()=="ǩ�Ϲ���"){
						EntityViewInfo evInfo=new EntityViewInfo();
						FilterInfo filterInfo=new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
						filterInfo.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAUDIT_VALUE));
						evInfo.setFilter(filterInfo);
						try {
							ChangeManageCollection cmCol=ChangeManageFactory.getRemoteInstance().getChangeManageCollection(evInfo);
							if(null!=cmCol&&!cmCol.isEmpty()){
								CRMHelper.sortCollection(cmCol, "changeDate", true);
								for(int k=0;k<cmCol.size();k++){
									IRow sonRow = totalFlowTable.addRow();
									sonRow.setTreeLevel(1);
									sonRow.getCell("id").setValue(cmCol.get(k).getId());
									sonRow.getCell("value").setValue(new Boolean(true));				
									sonRow.getCell("biz").setValue(cmCol.get(k).getBizType().getAlias());
									sonRow.getCell("actDate").setValue(cmCol.get(k).getAuditTime());
								}
							}
						} catch (Exception e) {
							this.handleException(e);
							this.abort(e);
						}
					}
					if(info.getBusinessName()=="ǩԼ"){
						EntityViewInfo evInfo=new EntityViewInfo();
						FilterInfo filterInfo=new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("transactionID",transactionInfo.getId()));
						filterInfo.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE));
						evInfo.setFilter(filterInfo);
						try {
							ChangeManageCollection cmCol=ChangeManageFactory.getRemoteInstance().getChangeManageCollection(evInfo);
							if(null!=cmCol&&!cmCol.isEmpty()){
								CRMHelper.sortCollection(cmCol, "changeDate", true);
								for(int k=0;k<cmCol.size();k++){
									IRow sonRow = totalFlowTable.addRow();
									sonRow.setTreeLevel(1);
									sonRow.getCell("id").setValue(cmCol.get(k).getId());
									sonRow.getCell("value").setValue(new Boolean(true));			
									sonRow.getCell("biz").setValue(cmCol.get(k).getBizType().getAlias());
									sonRow.getCell("actDate").setValue(cmCol.get(k).getAuditTime());
								}
							}
						} catch (Exception e) {
							this.handleException(e);
							this.abort(e);
						}
					}
				}
			}
		}
	}
	
	private void showSincerityPurchaseInfo() {
		sincerityPurchaseTable.checkParsed();
		sincerityPurchaseTable.removeRows();
		sincerityPurchaseTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		RoomInfo room = (RoomInfo) this.editData;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("createTime"));
		view.getSelector().add("id");
		view.getSelector().add("sincerityState");
		view.getSelector().add("customerNames");
		view.getSelector().add("appointmentPeople");
		view.getSelector().add("appointmentPhone");
		view.getSelector().add("projectNum");
		view.getSelector().add("bizDate");
		view.getSelector().add("invalidationDate");
		view.getSelector().add("salesman.name");
		view.getSelector().add("description");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("isValid",Boolean.FALSE));
		view.setFilter(filter);
		SincerityPurchaseCollection col = null;
		try {
			col = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection(view);
			
		} catch (BOSException e) {
			logger.warn(e);
			logger.debug("", e);
		}
		if(col != null && col.size()> 0){
			for(int i=0;i<col.size();i++){
				SincerityPurchaseInfo info = col.get(i);
				IRow row = sincerityPurchaseTable.addRow();
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("sincerityState").setValue(info.getSincerityState());
				//�ͻ������Ѿ�ƴ��
				row.getCell("customer").setValue(info.getCustomerNames());
				row.getCell("appointmentPeople").setValue(info.getAppointmentPeople());
				row.getCell("appointmentPhone").setValue(info.getAppointmentPhone());
				row.getCell("projectNum").setValue(new Integer(info.getProjectNum()));
				row.getCell("bizDate").setValue(info.getBizDate());
				row.getCell("invalidationDate").setValue(info.getInvalidationDate());
				row.getCell("salesman").setValue(info.getSaleManStr());
				row.getCell("description").setValue(info.getDescription());
			}
		}
	}
	/**
	 * ��ʾԤ����¼
	 * @author xiaominWang
	 */
	private void showKeepDownInfo(){			
		this.keepDownTable.removeRows();
		this.keepDownTable.checkParsed();
		keepDownTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//����Ϊѡ����
		RoomInfo room = (RoomInfo) this.editData;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("creator.*");
		view.getSelector().add("bizDate");
		view.getSelector().add("keepDate");
		view.getSelector().add("description");
		view.getSelector().add("customerEntry");
		view.getSelector().add("customerEntry.customer");
		view.getSelector().add("reason");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("cancelDate", null));
//		filter.getFilterItems().add(new FilterItemInfo("cacelStaff", null));
		try {
			RoomKeepDownBillCollection col = (RoomKeepDownBillCollection)RoomKeepDownBillFactory.getRemoteInstance().getRoomKeepDownBillCollection(view);
			CRMHelper.sortCollection(col, "bizDate", false);
			if(col != null && col.size()>0){
				for(int i=0;i<col.size();i++){
					RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)col.get(i);
					if(info != null && info.getId() != null){
//						info.getCustomerEntry();
						this.keepDownTable.addRow();
						if(null!=info.getBizDate()){
							this.keepDownTable.getRow(i).getCell("bizDate").setValue(info.getBizDate().toString());
						}
						if(null!=info.getHandler()){
							this.keepDownTable.getRow(i).getCell("handler").setValue(info.getHandler().getName());
						}
						this.keepDownTable.getRow(i).getCell("id").setValue(info.getId().toString());
						if(null!=info.getDescription()){
							this.keepDownTable.getRow(i).getCell("description").setValue(info.getDescription());
						}
						this.keepDownTable.getRow(i).getCell("keepDate").setValue(new Integer(info.getKeepDate()).toString());
						Date date=info.getBizDate();
						Date today=null;
						Calendar time=Calendar.getInstance();
						time.add(Calendar.DAY_OF_MONTH,-info.getKeepDate());
						today=time.getTime();
						if(today.compareTo(date)>0){
							this.keepDownTable.getRow(i).getCell("IsExtended").setValue(new Boolean(true));
						}else{
							this.keepDownTable.getRow(i).getCell("IsExtended").setValue(new Boolean(false));
						}
					
						
						RoomKeepCustomerEntryCollection customerCol = null;
						EntityViewInfo customerView = new EntityViewInfo();
						customerView.getSelector().add("id");
						customerView.getSelector().add("customer");
						customerView.getSelector().add("customer.*");
						FilterInfo customerFilter = new FilterInfo();
						customerView.setFilter(customerFilter);
						customerFilter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString()));
						customerCol = RoomKeepCustomerEntryFactory.getRemoteInstance().getRoomKeepCustomerEntryCollection(customerView);
//						SHECustomerCollection sheCusCol = new SHECustomerCollection();
						StringBuffer cusDes = new StringBuffer();
						if(null!=customerCol && customerCol.size()>0){
							for(int j = 0 ; j<customerCol.size();j++){
								if(j != 0){
									cusDes.append(";");
								}
//								sheCusCol.add(customerCol.get(j).getCustomer());
								cusDes.append(customerCol.get(j).getCustomer().getName());
							}
							this.keepDownTable.getRow(i).getCell("customer").setValue(cusDes.toString());
						}
						if(info.getReason()!=null){
							this.keepDownTable.getRow(i).getCell("reason").setValue(info.getReason().getAlias());
						}
					}
				}
			}
		} catch (Exception e) {
			this.handleException(e);
			this.abort();
		}
		
	}
	
	
	private void showView(Object bizObj) throws UIException {
		if(bizObj == null){
			logger.warn("��ô��Ϊnull��,��ϸ���.");
			return;
		}
		String id = null;
		Class viewClazz = null;
		if (bizObj instanceof RoomKeepDownBillInfo) {
			RoomKeepDownBillInfo keepDownBillInfo = (RoomKeepDownBillInfo) bizObj;
			id = keepDownBillInfo.getId().toString();
			viewClazz = RoomKeepDownBillEditUI.class;
		}else if(bizObj instanceof PurchaseInfo) {
			PurchaseInfo purchaseInfo = (PurchaseInfo) bizObj;
			id = purchaseInfo.getId().toString();
			viewClazz = PurchaseEditUI.class;
		}else if(bizObj instanceof PurchaseChangeInfo) {
			PurchaseChangeInfo changeInfo = (PurchaseChangeInfo) bizObj;
			id = changeInfo.getId().toString();
			viewClazz = PurchaseChangeBillEditUI.class;
		}else if(bizObj instanceof PurchaseChangeCustomerInfo) {
			PurchaseChangeCustomerInfo cusChange = (PurchaseChangeCustomerInfo) bizObj;
			id = cusChange.getId().toString();
			viewClazz = PurchaseChangeCustomerEditUI.class;
		}else if(bizObj instanceof RoomSignContractInfo) {
			RoomSignContractInfo signContractInfo = (RoomSignContractInfo) bizObj;
			id = signContractInfo.getId().toString();
			viewClazz = RoomSignContractEditUI.class;
		}else if(bizObj instanceof QuitRoomInfo){
			QuitRoomInfo quitRoomInfo = (QuitRoomInfo) bizObj;
			id = quitRoomInfo.getId().toString();
			viewClazz = QuitRoomEditUI.class;
		}else if(bizObj instanceof ChangeRoomInfo){
			ChangeRoomInfo changeRoom = (ChangeRoomInfo) bizObj;
			id = changeRoom.getId().toString();
			viewClazz = ChangeRoomEditUI.class;
		}else{
			logger.warn("��֧�ֵ�bizType:"+bizObj.getClass().getName());
			return;
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", id);
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(viewClazz.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}
	
	
	
//����ԤԼ�źű༭ҳ��ΪSincerityPurchaseChangeNameUI
	protected void sincerityPurchaseTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception{
		try {
			
			if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				IRow row = sincerityPurchaseTable.getRow(e.getRowIndex());
				String id  = row.getCell("id").getValue().toString();
				if("".equals(id) || id != null){
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", id);
					uiContext.put("sellProject", sellProject);
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
									SincerityPurchaseChangeNameUI.class.getName(), uiContext,
							null, "VIEW");
					uiWindow.show();
				}
			}
		} catch (Exception exc) {
			handUIException(exc);
		}
	}

	protected void totalFlowTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception{
		try {
			if (e.getButton() == 1 && e.getClickCount() == 2) {
				if(e.getType()!=1) return;
				
				RoomInfo room = (RoomInfo) editData;
				
				if(!SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo())){
					String per=NewCommerceHelper.getPermitSaleManIdSql(room.getBuilding().getSellProject(),SysContext.getSysContext().getCurrentUserInfo());
		    		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		    		
		    		sqlBuilder.appendSql("select * from ( ");
		    		sqlBuilder.appendSql("select distinct bill.fid from t_she_prePurchaseManage bill ");
		    		sqlBuilder.appendSql("left join T_SHE_PrePurchaseSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' and bill.fbizState not in('SignNullify','PreNullify','PurNullify','PCNullify','CRNullify','QRNullify','CNNullify','ToPur','ToSign') ");
		    		sqlBuilder.appendSql("union ");
		    		sqlBuilder.appendSql("select distinct bill.fid from t_she_purchaseManage bill ");
		    		sqlBuilder.appendSql("left join T_SHE_PurSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' and bill.fbizState not in('SignNullify','PreNullify','PurNullify','PCNullify','CRNullify','QRNullify','CNNullify','ToPur','ToSign') ");
		    		sqlBuilder.appendSql("union ");
		    		sqlBuilder.appendSql("select distinct bill.fid from t_she_signManage bill ");
		    		sqlBuilder.appendSql("left join T_SHE_SignSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' and bill.fbizState not in('SignNullify','PreNullify','PurNullify','PCNullify','CRNullify','QRNullify','CNNullify','ToPur','ToSign'))");
				
					IRowSet rs = sqlBuilder.executeQuery();
					if(rs.size()==0){
						FDCMsgBox.showError(this,"�޲鿴Ȩ�ޣ�");
						SysUtil.abort();
					}
				}
				TransactionCollection tran=TransactionFactory.getRemoteInstance().getTransactionCollection("select billid from where room.id='"+room.getId()+"' and isValid=0 ");
				if(tran.size()>0){
					tran.get(0).getBillId();
				}			
				IRow row = totalFlowTable.getRow(e.getRowIndex());
				String tranBusinessOverViewId = row.getCell("id").getValue().toString();
				
				String bizFlow = (String) row.getCell("biz").getValue();
				Boolean isFinish = (Boolean) row.getCell("value").getValue();
				
				PrePurchaseManageInfo prePurchaseManage = SHEManageHelper.getPrePurchaseManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				PurchaseManageInfo purchaseManage = SHEManageHelper.getPurchaseManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				SignManageInfo signManage = SHEManageHelper.getSignManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				if(Boolean.TRUE.equals(isFinish)){
					if (bizFlow.equals(SHEManageHelper.PRELIMINARY)) {
						if (prePurchaseManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", prePurchaseManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PrePurchaseManageEditUI.class.getName(),
									uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					}else if (bizFlow.equals(SHEManageHelper.PURCHASE)) {
						if (purchaseManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", purchaseManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PurchaseManageEditUI.class.getName(), uiContext,
									null, OprtState.VIEW);
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.SIGN)) {
						if (signManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", signManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											SignManageEditUI.class.getName(),
									uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.MORTGAGE)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomLoan(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,false);
						}
					} else if (bizFlow.equals(SHEManageHelper.ACCFUND)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomAccFund(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,false);
						}
					} else if (bizFlow.equals(SHEManageHelper.AREACOMPENSATE)) {
						RoomAreaCompensateInfo areaCompensation = SHEHelper.getRoomAreaCompensation(room);
						if (areaCompensation != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", areaCompensation.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
									RoomAreaCompensateEditUI.class.getName(),
									uiContext, null, "FINDVIEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.JOIN)) {
						RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
						if (joinIn != null) {
							SHEHelper.toJoinBill(room, joinIn, false);
						}
					} else if (bizFlow.equals(SHEManageHelper.INTEREST)) {
						RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
						if (propertyBook != null) {
							SHEHelper.toPropertyBill(room, propertyBook, false);
						}
					}else if(bizFlow.equals("ԤԼ�ź�")){
						return ;
					}else if(bizFlow.equals(ChangeBizTypeEnum.CHANGENAME.getAlias())||bizFlow.equals(ChangeBizTypeEnum.CHANGEROOM.getAlias())||
							bizFlow.equals(ChangeBizTypeEnum.PRICECHANGE.getAlias())||bizFlow.equals(ChangeBizTypeEnum.QUITROOM.getAlias())){//Ϊ����ɵĸ�����ϸʱֻ�ܲ鿴�տ
						UIContext uiContext = new UIContext(this); 
						uiContext.put("ID", tranBusinessOverViewId);
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeManageEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					}else{
						SHEHelper.openRevBill(this, tranBusinessOverViewId,true);
					}
				}
				else {
					if (bizFlow.equals(SHEManageHelper.PRELIMINARY)) {
						if(purchaseManage != null || signManage != null){
							//������Ϲ���ǩԼ
						}
					}else if (bizFlow.equals(SHEManageHelper.PURCHASE)) {
						if (signManage != null) {
							//�����ǩԼ
						}else if(prePurchaseManage != null){
							//Ԥ��ת�Ϲ�
							SHEManageHelper.toTransactionBill(BOSUuid.read(prePurchaseManage.getId().toString()), prePurchaseManage.getBizState(),this,PurchaseManageEditUI.class.getName());
						}else{
							//�����Ϲ���
							UIContext uiContext = new UIContext(this);
							uiContext.put("sellProject", room.getBuilding().getSellProject());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PurchaseManageEditUI.class.getName(), uiContext,
									null, "ADDNEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.SIGN)) {
						if (purchaseManage != null) {
							//�Ϲ�תǩԼ
							SHEManageHelper.toTransactionBill(BOSUuid.read(purchaseManage.getId().toString()), purchaseManage.getBizState(),this,SignManageEditUI.class.getName());
						}else if(prePurchaseManage != null){
							//Ԥ��תǩԼ
							SHEManageHelper.toTransactionBill(BOSUuid.read(prePurchaseManage.getId().toString()), prePurchaseManage.getBizState(),this,SignManageEditUI.class.getName());
						}else{
							//����ǩԼ��
							UIContext uiContext = new UIContext(this);
							uiContext.put("sellProject", room.getBuilding().getSellProject());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											SignManageEditUI.class.getName(),
									uiContext, null, "ADDNEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.MORTGAGE)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomLoan(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,true);
						}
					} else if (bizFlow.equals(SHEManageHelper.ACCFUND)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomAccFund(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,true);
						}
					} else if (bizFlow.equals(SHEManageHelper.AREACOMPENSATE)) {
						RoomAreaCompensateInfo areaCompensation = SHEHelper.getRoomAreaCompensation(room);
						if (areaCompensation != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", areaCompensation.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
									RoomAreaCompensateEditUI.class.getName(),
									uiContext, null, "EDIT");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.JOIN)) {
						RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
						if (joinIn != null) {
							SHEHelper.toJoinBill(room, joinIn, true);
						}else{
							UIContext uiContext = new UIContext();
							uiContext.put("sellProject", sellProject);
							uiContext.put("room", room);
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomJoinListUI.class.getName(), uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.INTEREST)) {
						RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
						if (propertyBook != null) {
							SHEHelper.toPropertyBill(room, propertyBook, true);
						}else{
							UIContext uiContext = new UIContext();
							uiContext.put("sellProject", sellProject);
							uiContext.put("room", room);
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPropertyBookListUI.class.getName(), uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					}else if(bizFlow.equals("�۸���") || bizFlow.equals("����") || bizFlow.equals("����") ||  bizFlow.equals("�˷�")){
						String changeIdStr = row.getCell("id").getValue().toString();
						if(changeIdStr==null) return;
						UIContext uiContext = new UIContext();
						uiContext.put("ID", changeIdStr);  
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeManageEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();	
					}else{//Ϊ������ϸʱ�����տ
						SHEHelper.openRevBill(this, tranBusinessOverViewId,false);
					}
				}
			}
		} catch (Exception exc) {
			handUIException(exc);
		} finally {
		}
	}

/**
 * @author xiaominWang
 * TODO 
 * Ԥ��������¼���˫���򿪱༭����
 */
	protected void keepDownTable_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getClickCount()==2){   //�ж��Ƿ�˫��
			String id = null;
			int curRow = keepDownTable.getSelectManager().getActiveRowIndex();//��õ�ǰ����к�
	        if (keepDownTable.getRow(curRow)!=null && keepDownTable.getRow(curRow).getCell("id") != null&& keepDownTable.getRow(curRow).getCell("id").getValue() != null)//�жϵ��ڻ��ID
	        {
	        	id = keepDownTable.getRow(curRow).getCell("id").getValue().toString();
	        }
		UIContext context = new UIContext(this);
	    context.put("ID",id);
		UIFactory.createUIFactory(UIFactoryName.NEWWIN).create("com.kingdee.eas.fdc.sellhouse.client.RoomKeepDownBillEditUI",context,null,OprtState.VIEW).show();
		}
	}


	/**
	 * output class constructor
	 */
	public VirtualRoomFullInfoUI() throws Exception {
		super();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("id");
		sels.add("buildingArea");
		sels.add("roomArea");
		sels.add("actualBuildingArea");
		sels.add("actualRoomArea");
		
		sels.add("apportionArea");
		sels.add("balconyArea");
		sels.add("floorHeight");
		sels.add("direction.name");
		
		sels.add("sight.name");
		sels.add("roomModel.name");
		sels.add("roomModel.roomModelType.*");
		sels.add("buildingProperty.name");
		sels.add("houseProperty");
		
		sels.add("roomPrice");
		sels.add("buildPrice");
		sels.add("standardTotalAmount");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("isCalByRoomArea");
		sels.add("sellState");
		
		sels.add("lastPurchase.id");
		sels.add("lastPurchase.payType.id");
		
		sels.add("lastSignContract.id");
		sels.add("lastSignContract.signDate");
		
		sels.add("toPurchaseDate");
		sels.add("roomCompensateState");
		sels.add("roomLoanState");
		sels.add("roomACCFundState");
		sels.add("roomBookState");
		
		//��ʾԤ�ⷿ�ź�ʵ��ź�
		sels.add("number");
		sels.add("roomNo");
		//���ң���Ȩ�����״̬
		sels.add("roomLoanState");
		sels.add("roomJoinState");
		sels.add("roomBookState");
		sels.add("building.sellProject.*");
		return sels;
	}

	private void setTextFormat(KDFormattedTextField text){
		text.setRemoveingZeroInDispaly(false);
		text.setRemoveingZeroInEdit(false);
		text.setPrecision(2);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setSupportedEmpty(true);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		
		setTextFormat(txtBuildingArea);
		setTextFormat(txtRoomArea);
		setTextFormat(txtActBuildArea);
		setTextFormat(txtActRoomArea);
		setTextFormat(txtStandardBuildPrice);
		setTextFormat(txtStandardRoomPrice);
		setTextFormat(txtStandardTotalAmount);
		setTextFormat(txtDealBuildPrice);
		setTextFormat(txtDealRoomPrice);
		setTextFormat(txtDealTotalAmount);
//		UIContext uiContext = new UIContext(ui);
//		uiContext.put("dataMap", new HashMap());
//		CoreUIObject plUI = null;
//		plUI = (CoreUIObject) UIFactoryHelper.initUIObject(BuyingRoomPlanUI.class.getName(), uiContext,
//				null, "VIEW");
		Object isVirtual = this.getUIContext().get("isVirtual");
		Boolean virtual = (Boolean)isVirtual;
		//virtual=trueΪ���۷�Դ��ѯ��falseΪ���� 
		if(isVirtual !=null && virtual.booleanValue()){
			this.contRoomState.setVisible(false);
			this.contPricingType.setVisible(false);
			tabBizInfo.removeAll();
			try {
				initBuyRoomPlan();
				tabBizInfo.add("��ҵ�ƻ�", buyRoomPlanPane);
			} catch (Exception e1) {
				logger.warn(e1);
				logger.debug("", e1);
			}
		}
		sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
		this.totalFlowTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.labelCustomer1.setForeground(Color.BLUE);
		this.labelCustomer2.setForeground(Color.BLUE);
		this.labelCustomer3.setForeground(Color.BLUE);
		this.labelCustomer4.setForeground(Color.BLUE);
		this.labelCustomer5.setForeground(Color.BLUE);
		kDLabel1.setForeground(Color.RED);
	}
	protected void labelCustomer1_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labelCustomer1.getUserObject());
		}
	}
	protected void labelCustomer2_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer2.getUserObject());
		}
	}
	protected void labelCustomer3_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer3.getUserObject());
		}
	}
	
	protected void labelCustomer4_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer4.getUserObject());
		}
	}
	protected void labelCustomer5_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer5.getUserObject());
		}
	}
	private void initBuyRoomPlan() {
		Object isVirtual = this.getUIContext().get("isVirtual");
		Boolean virtual = (Boolean)isVirtual;
		RoomInfo room = (RoomInfo) this.editData;
		BigDecimal buildingArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		buildingArea = this.txtBuildingArea.getBigDecimalValue();
		roomArea = this.txtRoomArea.getBigDecimalValue();
		String pricingType = this.txtPricingType.getText();
//		if (detailUI == null) {
			UIContext uiContext = new UIContext(ui);
			uiContext.put(UIContext.ID, this.getUIContext().get(UIContext.ID));
			uiContext.put("totalAmount", room.getStandardTotalAmount());
			uiContext.put("isVirtual", isVirtual);
			uiContext.put("sellState", room.getSellState());
			uiContext.put("buildingArea", buildingArea);
			uiContext.put("roomArea", roomArea);
			uiContext.put("pricingType", pricingType);
			try {
				detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(BuyingRoomPlanUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			} catch (UIException e) {
				logger.warn(e);
				logger.debug("", e);
			}
			buyRoomPlanPane.setViewportView(detailUI);
			buyRoomPlanPane.setKeyBoardControl(true);
//		} else {
////			detailUI.getUIContext().put("isRecover", "Y");
////			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
//			detailUI.onLoad();
//		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}
	
	protected void tblLoan_tableClicked(KDTMouseEvent e) throws Exception {
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			 IRow row = tblLoan.getRow(e.getRowIndex());
			 RoomLoanInfo info = (RoomLoanInfo)row.getUserObject();
			 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id",tblLoan));
             uiContext.put("roomId", info.getRoom().getId().toString());
             uiContext.put("sellProjectId", info.getRoom().getBuilding().getSellProject().getId().toString());
             uiContext.put("mmTypeId" , info.getMmType().getId().toString());
             IUIWindow uiWindow =null;
         	 uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
              create(RoomLoanEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
             uiWindow.show();
    	 }
	}
	
	protected void tblJoinProcesses_tableClicked(KDTMouseEvent e) throws Exception {
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
    		 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id",tblJoinProcesses));
             IUIWindow uiWindow =null;
         	 uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
              create(RoomJoinEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
             uiWindow.show();
    	 }
	}
	protected void tblJoinData_tableClicked(KDTMouseEvent e) throws Exception {
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
    		 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id",tblJoinData));
             IUIWindow uiWindow =null;
         	 uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
              create(RoomJoinEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
             uiWindow.show();
    	 }
	}
	protected void tblPBData_tableClicked(KDTMouseEvent e) throws Exception {
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
    		 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id",tblPBData));
             IUIWindow uiWindow =null;
         	 uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
              create(RoomPropertyBookEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
             uiWindow.show();
    	 }
	}
	
	protected void tblPBProcesser_tableClicked(KDTMouseEvent e) throws Exception {
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
    		 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id",tblPBProcesser));
             IUIWindow uiWindow =null;
         	 uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
              create(RoomPropertyBookEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
             uiWindow.show();
    	 }
	}
	
	private int selectIndex = -1;
    protected String getSelectedKeyValue(String keyFiledName,KDTable table)
    {
        int[] selectRows = KDTableUtil.getSelectedRows(table);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
        return ListUiHelper.getSelectedKeyValue(selectRows,table,keyFiledName);
    }
}