/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class SignManageListUI extends AbstractSignManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignManageListUI.class);
//    
    public SignManageListUI() throws Exception
    {
        super();
    }
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"bulidingArea","actBulidingArea","roomArea","actRoomArea","strdTotalAmount","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount"});
	}
    protected void initControl() {
		super.initControl();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"lastAgio","bulidingArea","actBulidingArea","strdBuildingPrice","roomArea","actRoomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomArea","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount"});
		FDCHelper.formatTableDate(getBillListTable(), "pur.bizDate");
		FDCHelper.formatTableDate(getBillListTable(), "pur.busAdscriptionDate");
//		FDCHelper.formatTableDate(getBillListTable(), "onRecordDate");
		FDCHelper.formatTableDate(getBillListTable(), "changeDate");
		FDCHelper.formatTableDateTime(getBillListTable(), "createTime");
		FDCHelper.formatTableDateTime(getBillListTable(), "lastUpdateTime");
		if (!isSaleHouseOrg){
			this.actionWebMark.setEnabled(false);
			this.actionUnOnRecord.setEnabled(false);
			this.actionOnRecord.setEnabled(false);
		}
		this.actionInvalid.setVisible(false);
		this.btnInvalid.setVisible(false);
		
		tblMain.getHeadRow(0).getCell("salesman.name").setValue("置业顾问");
		
		this.btnToMT.setIcon(EASResource.getIcon("imgTbtn_input"));
    }
	public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				if(((PurchaseManageInfo)objectValue).getSrcId()!=null){
					
					ObjectUuidPK srcpk=new ObjectUuidPK(((PurchaseManageInfo)objectValue).getSrcId());
					IObjectValue srcobjectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
					
					if(srcobjectValue instanceof PrePurchaseManageInfo){
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", ((PurchaseManageInfo)objectValue).getSrcId());
				        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
				        uiWindow.show();
				        return;
					}
				}
			}else if(objectValue instanceof PrePurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", info.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联预定单据！");
	}

	public void actionRelatePurchase_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", info.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联认购单据！");
    }


	public void actionOnRecord_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("bizState");
	    	sels.add("isOnRecord");
	    	SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(i).toString()));
	    	
	    	if(sign.isIsOnRecord()){
	    		FDCMsgBox.showWarning(this, "该单据已经网签！");
				return;
	    	}
			if (!getBizStateAuditEnum().equals(sign.getBizState())) {
				FDCMsgBox.showWarning("该单据不是审批状态，不能进行网签操作！");
				return;
			}
			SignManageFactory.getRemoteInstance().onRecord(BOSUuid.read(id.get(i).toString()),null,null); 
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}

	public void actionUnOnRecord_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("bizState");
	    	sels.add("isOnRecord");
	    	SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(i).toString()));
	    	
	    	if(!sign.isIsOnRecord()){
	    		FDCMsgBox.showWarning(this, "只有已备案的单据才可以进行取消备案操作！");
				return;
	    	}
			if (!getBizStateAuditEnum().equals(sign.getBizState())) {
				FDCMsgBox.showWarning("该单据不是审批状态，不能进行取消备案操作！");
				return;
			}
			SignManageFactory.getRemoteInstance().unOnRecord(BOSUuid.read(id.get(i).toString())); 
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    public void actionWebMark_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//		UIContext uiContext = new UIContext(this);
//		uiContext.put(UIContext.ID, null);
//		uiContext.put(UIContext.OWNER, this);
//		uiContext.put("sellProjID", sellProject.getId().toString());
//		
//		ICell cell = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("room.number");
//		String roomNumber = cell.getValue().toString();
//		uiContext.put("roomNumber", roomNumber);
		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WebMarkPutInEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
	}

    protected String getEditUIName() {
		return SignManageEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return SignManageFactory.getRemoteInstance();
	}

	 protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnRelatePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnWebMark.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.btnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.btnUnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.menuItemWebMark.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.menuItemOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.menuItemUnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
	       
        this.actionWebMark.setVisible(false);
        this.menuItemOnRecord.setText("网签");
        this.menuItemUnOnRecord.setText("取消网签");
        this.btnOnRecord.setText("网签");
        this.btnUnOnRecord.setText("取消网签");
	}

	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.SIGNAUDIT;
	}

	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.SIGNNULLIFY;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.SIGNAPPLE;
	}
	protected TransactionStateEnum getBizStateSavedEnum() {
		return TransactionStateEnum.SIGNSAVED;
	}
	protected void editCheck(String id) throws EASBizException, BOSException {
//		if(checkIsHasBankPayment(id)){
//			FDCMsgBox.showWarning(this,"签约单已经产生银行放款单业务，不能进行修改操作！");
//			SysUtil.abort();
//		}
	}
	private boolean checkIsHasBankPayment(String id) throws EASBizException, BOSException{
		if(id==null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", id));
		return BankPaymentEntryFactory.getRemoteInstance().exists(filter);
	}
	public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();	
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("signCustomerEntry.*");
		sels.add("signCustomerEntry.customer.*");
		sels.add("signCustomerEntry.certificate.*");
		sels.add("signPayListEntry.*");
		sels.add("signPayListEntry.moneyDefine.*");
		SignManageInfo info =SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id),sels);
									
		if(!(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())||
				TransactionStateEnum.SIGNAUDIT.equals(info.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getSignCustomerEntry().size()];
		for(int i=0;i<info.getSignCustomerEntry().size();i++){
			custObjs[i]=info.getSignCustomerEntry().get(i).getCustomer();
		}
		Set tranEntryIdSet = new HashSet();
		SignPayListEntryCollection signPayListColl = info.getSignPayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			SignPayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}		
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(),info ,custObjs,tranEntryIdSet);
	}
	
	protected String whoAmI() {
		return IAMSIGN;
	}
	public void actionToMT_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		
		JSONArray carr=new JSONArray();
		 
		String	paramValue="true";
		try {
			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_QD");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int ii = 0; ii < id.size(); ii++){
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("signCustomerEntry.isMain");
			sic.add("signCustomerEntry.customer.*");
			sic.add("signCustomerEntry.customer.certificateType.name");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("signSaleManEntry.user.number");
			
			 SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(ii).toString()),sic);
			 if(!info.getBizState().equals(TransactionStateEnum.SIGNAUDIT)&&!info.getBizState().equals(TransactionStateEnum.SIGNAPPLE)){
				 continue;
			 }
			 JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber());
			cjo.put("type", "3");
			cjo.put("projectId",info.getSellProject().getNumber());
			String customerId="";
			SHECustomerInfo quc=null;
			Timestamp qudate=null;
			for(int i=0;i<info.getSignCustomerEntry().size();i++){
				if(i==0){
					customerId=info.getSignCustomerEntry().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getSignCustomerEntry().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getSignCustomerEntry().get(i).isIsMain()){
						quc=info.getSignCustomerEntry().get(i).getCustomer();
					}
				}else{
					if(info.getSignCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i).getCustomer().getReportDate()==null){
						MsgBox.showWarning("客户报备日期和首访日期都为空！");
						return;
					}
					if(qudate==null){
						if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						quc=info.getSignCustomerEntry().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getSignCustomerEntry().get(i).getCustomer();
						}
					}
				}
			}
			cjo.put("qdCustId",quc.getNumber());
			cjo.put("cstName",quc.getName());
			cjo.put("cstPhone",quc.getPhone());
			cjo.put("cstCardId",quc.getCertificateNumber());
			if(quc.getCertificateType()!=null)
				cjo.put("cstCardIdType",quc.getCertificateType().getName());
			
			cjo.put("customerId", customerId);
			if(info.getRoom()!=null){
				cjo.put("roomId", info.getRoom().getNumber());
				cjo.put("rcYT", info.getRoom().getProductType().getName());
			}
			cjo.put("roomStatus", "签约");
			cjo.put("userId", info.getSignSaleManEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			cjo.put("tradeAmount", info.getDealTotalAmount());
			cjo.put("payType", info.getPayType().getName());
			cjo.put("expectDate", "");
			cjo.put("price", info.getDealBuildPrice());
			cjo.put("area",info.getBulidingArea());
			cjo.put("contractNo", info.getNumber());
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			
			if(SignChangeStateEnum.CHANGE.equals(info.getChangeState())){
				cjo.put("isChangeRoomNew", "1");
			}else{
				cjo.put("isChangeRoomNew", "0");
			}
			
			sic=new SelectorItemCollection();
			sic.add("number");
			if(info.getSrcId()!=null){
				if(info.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
					PurchaseManageInfo src=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(info.getSrcId()),sic);
					cjo.put("rengouId", src.getNumber());
				}else{
					SincerityPurchaseInfo src=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
					cjo.put("rcId",src.getNumber());
				}
			}
			
			cjo.put("salesStatisticsDate", df.format(info.getBizDate()));
			cjo.put("fyyt", info.getRoom().getProductType().getName());
			
			carr.add(cjo);
  		}
		try {
			String rs=SHEManageHelper.execPost(null, "sap_transaction_mcrm_channel", carr.toJSONString());
			JSONObject rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
				return;
			}
			rs=SHEManageHelper.execPost(null, "sap_transaction_organ_personal_channel", carr.toJSONString());
			rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
			}else{
				MsgBox.showInfo("同步成功！");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
//	@Override
//	public void actionToYB_actionPerformed(ActionEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super.actionToYB_actionPerformed(e);
//		checkSelected();
//		ArrayList id = getSelectedIdValues();
////		toebei
//		String	param1="false";
//		try {
//			param1 = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_YB");
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		if("true".equals(param1)){
//			for(int ii = 0; ii < id.size(); ii++){
//				SelectorItemCollection sic=new SelectorItemCollection();
//				sic.add("*");
//				sic.add("sellProject.number");
//				sic.add("room.number");
//				sic.add("signCustomerEntry.isMain");
//				sic.add("signCustomerEntry.customer.*");
//				sic.add("signCustomerEntry.customer.certificateType.name");
//				sic.add("room.productType.name");
//				sic.add("payType.name");
//				sic.add("signSaleManEntry.user.number");
//				
//				 SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(ii).toString()),sic);
//				 if(!info.getBizState().equals(TransactionStateEnum.SIGNAUDIT)&&!info.getBizState().equals(TransactionStateEnum.SIGNAPPLE)){
//					 continue;
//				 }
//				 FDCSQLBuilder builder=new FDCSQLBuilder();
//					String timestamp = null;
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					 JSONObject initjson=new JSONObject(); 
//					 JSONObject esbjson=new JSONObject();
//					 String instId=null;
//					 String requestTime=null;
//					 JSONObject datajson=new JSONObject();
//					 JSONArray ybarr=new JSONArray();
////							房间
//							builder.clear();
//							builder.appendSql(" select instId,requestTime from esbInfo where source='room'");
//							IRowSet rs3=builder.executeQuery();
//					 try {
//						while(rs3.next()){
//							  instId=rs3.getString("instId");
//							  requestTime=rs3.getString("requestTime");
//						 }
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					 if(instId!=null){
//						 esbjson.put("instId",instId);
//					 }
//					 if(requestTime!=null){
//						 esbjson.put("requestTime",requestTime);
//					 }
//					 
////						上次返回在时间戳
//					 builder.clear();
//					 builder.appendSql(" select ybtime from ybTimeRecord where source='room'");
//						IRowSet rs1=builder.executeQuery();
//						try {
//							if(rs1.first()&&rs1!=null){
//							 timestamp=rs1.getString("ybtime");
//							}else{
//								timestamp="";
//							}
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						
////					客户
//					JSONObject initcjson=new JSONObject();
//					JSONObject esbcjson=new JSONObject();
//					JSONObject datacjson=new JSONObject();
//					JSONArray ybcarr=new JSONArray();
//					String instcId=null;
//					String requestcTime=null;
//					String timestampc = null;
//					builder.clear();
//					builder.appendSql(" select instId,requestTime from esbInfo where source='owner'");
//						IRowSet rs31=builder.executeQuery();
//						try {
//							while(rs31.next()){
//								instcId=rs31.getString("instId");
//						  requestcTime=rs31.getString("requestTime");
//							}
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						if(instId!=null){
//							esbcjson.put("instId",instId);
//						}
//						if(requestTime!=null){
//							esbcjson.put("requestTime",requestTime);
//						}
//						
////						上次返回在时间戳
//						builder.clear();
//						builder.appendSql(" select ybtime from ybTimeRecord where source='owner'");
//						IRowSet rs11=builder.executeQuery();
//						try {
//							if(rs11.first()&&rs11!=null){
//								timestampc=rs11.getString("ybtime");
//							}else{
//							timestampc="";
//						}
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//			
////					toYB
//					JSONObject ybjson=new JSONObject();
//					if(info.getBizState()!=null&&info.getBizState().equals("SignAudit")){
//						if(info.getRoom().getId().toString()!=null){
//							String roomId=info.getRoom().getId().toString();
//							RoomInfo room1=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId),sic);
//
//							SelectorItemCollection spsic=new SelectorItemCollection();
//							sic.add("parent.*");
//							String roomState=room1.getSellState().getName();
//							ybjson.put("roomState", "");
//							if(roomState!=null){
//								if(roomState.equals("Sign")){
//									ybjson.put("roomState", "签约");
//								}else {
//									ybjson.put("roomState", "未售");
//								}
//							}
//							if(room1.getBuilding()!=null){
//								if(room1.getBuilding().getSellProject().getParent()!=null){
//									ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
//									if(room1.getBuilding().getSellProject().getParent().getName()!=null){
//										ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
//									}else{
//										ybjson.put("projectName","");
//									}
//									if(room1.getBuilding().getSellProject().getId().toString()!=null){
//										ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
//									}else{
//										ybjson.put("stageId","");
//									}
//									if(room1.getBuilding().getSellProject().getName()!=null){
//										ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
//									}else{
//										ybjson.put("stageName","");
//									}
//								}else{					
//									ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
//									if(room1.getBuilding().getSellProject().getParent().getName()!=null){
//										ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
//									}else{
//										ybjson.put("projectName","");
//									}
//									if(room1.getBuilding().getSellProject().getId().toString()!=null){
//										ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
//									}else{
//										ybjson.put("stageId","");
//									}
//									if(room1.getBuilding().getSellProject().getName()!=null){
//										ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
//									}else{
//										ybjson.put("stageName","");
//									}
//								}
//							if(room1.getId()!=null){
//								String roomId1 = room1.getId().toString();
//								ybjson.put("roomId", roomId1);
//								builder.clear();
//								builder.appendSql("select FBusAdscriptionDate ,FID,FSaleManNames from T_SHE_SignManage where froomid='"+roomId1+"'");
//								IRowSet rsdate=builder.executeQuery();
//								try {
//									while(rsdate.next()){
//										String signDate=rsdate.getString("FBusAdscriptionDate");
//										if(signDate!=null){
//											ybjson.put("signDate", signDate);
//										}else{
//											ybjson.put("signDate", "");
//										}
//////									判断是否全部回款
//										ybjson.put("payupState", "0");
//										BigDecimal act=BigDecimal.ZERO;
//										if(rsdate.getString("FID")!=null){
//											
//												ybjson.put("cst1guid", info.getSignCustomerEntry().get(0).getCustomer().getId().toString());
//												if(info.getSignCustomerEntry().get(1)!=null){
//														ybjson.put("cst2guid", info.getSignCustomerEntry().get(1).getCustomer().getId().toString());
//												}else{
//													ybjson.put("cst2guid", "");
//												}
//												if(info.getSignCustomerEntry().get(2)!=null){
//													ybjson.put("cst3guid", info.getSignCustomerEntry().get(2).getCustomer().getId().toString());
//												}else{
//													ybjson.put("cst3guid", "");
//												}
//												if(info.getSignCustomerEntry().get(3)!=null){
//													ybjson.put("cst4guid", info.getSignCustomerEntry().get(3).getCustomer().getId().toString());
//												}else{
//													ybjson.put("cst4guid", "");
//												}
//												if(info.getSignCustomerEntry().get(4)!=null){
//													ybjson.put("cst5guid", info.getSignCustomerEntry().get(4).getCustomer().getId().toString());
//												}else{
//													ybjson.put("cst5guid", "");
//												}
//												for(int i1=0;i1<info.getSignCustomerEntry().size();i1++){
//													SHECustomerInfo quc=info.getSignCustomerEntry().get(i1).getCustomer();
//													JSONObject ybcjson=new JSONObject();
//														ybcjson.put("cstguid", quc.getId().toString());							
//														ybcjson.put("cstname",quc.getName().replaceAll("\\s", ""));	
//														if(info.getSignCustomerEntry().get(i1).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i1).getCustomer().getReportDate()==null){
//															throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
//														}
//														if(quc.getSex()!=null&&!"".equals(String.valueOf(quc.getSex()))){
//															ybcjson.put("Gender",quc.getSex().getAlias().replaceAll("\\s", ""));
//														}
//														
//														ybcjson.put("CstType",quc.getCustomerType().getAlias().replaceAll("\\s", ""));
//														if(quc.getCertificateType()!=null&&quc.getCertificateType().getName()!=null){
//															ybcjson.put("CardType",quc.getCertificateType().getName().toString().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("CardType","");
//														}
//														if(quc.getCertificateNumber()!=null &&quc.getCertificateNumber()!="/"){
//															ybcjson.put("CardID",quc.getCertificateNumber().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("CardID","");
//														}
//														if(quc.getEmail()!=null &&quc.getEmail()!="/"){
//															ybcjson.put("Email",quc.getEmail().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("Email","");
//														}
//														if(quc.getMailAddress()!=null &&quc.getMailAddress()!="/"){
//															ybcjson.put("Address",quc.getMailAddress().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("Address","");
//														}
//														if(quc.getPhone()!=null &&quc.getPhone()!="/"){
//															ybcjson.put("Tel",quc.getPhone().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("Tel","");
//														}
//														if(quc.getOfficeTel()!=null &&quc.getOfficeTel()!="/"){
//															ybcjson.put("OfficeTel",quc.getOfficeTel().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("OfficeTel","");
//														}
//														if(quc.getTel()!=null &&quc.getTel()!="/"){
//															ybcjson.put("HomeTel",quc.getTel().replaceAll("\\s", ""));
//														}else{
//															ybcjson.put("HomeTel","");
//														}
//														String createTime=sdf.format(quc.getCreateTime());
//														String updateTime=sdf.format(quc.getLastUpdateTime());
//														ybcjson.put("CreatedTime",createTime);
//														ybcjson.put("ModifiedTime",updateTime);
//														String projectId=info.getSellProject().getId().toString();
//														if(projectId!=null){
//															builder.clear();
//															builder.appendSql("select fparentid as pid from t_she_sellproject where fid='"+projectId+"' ");
//															IRowSet rspro=builder.executeQuery();
//															try {
//																while(rspro.next()){
//																	projectId=rspro.getString("pid");
//																}
//															} catch (SQLException e1) {
//																// TODO Auto-generated catch block
//																e1.printStackTrace();
//															}
//															ybcjson.put("projectId",projectId);
//														}
//														ybcarr.add(ybcjson);
//												}			
//										}
//										ybjson.put("zygw","");
//										if(rsdate.getString("FSaleManNames")!=null){
//											ybjson.put("zygw", rsdate.getString("FSaleManNames"));
//										}	
//										
//									}
//								} catch (Exception e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//							}else{
//								ybjson.put("roomId", "");
//							}
//							ybjson.put("totalPrice", "");
//							if(info.getDealTotalAmount()!=null){
//								ybjson.put("totalPrice", info.getDealTotalAmount().toString());
//							}
//							ybjson.put("unitPrice", "");
//							if(info.getDealBuildPrice()!=null){
//								ybjson.put("unitPrice", info.getDealBuildPrice().toString());
//							}
//							if(room1.getNumber()!=null){
//								ybjson.put("roomCode", room1.getNumber().toString().replaceAll("\\s", ""));
//							}else{
//								ybjson.put("roomCode", "");
//							}
//							ybjson.put("buildingId","");
//							ybjson.put("buildingName","");
//							if(room1.getBuilding()!=null){
//								ybjson.put("buildingId", room1.getBuilding().getId().toString());
//								if(room1.getBuilding().getName()!=null){
//									ybjson.put("buildingName", room1.getBuilding().getName().replaceAll("\\s", ""));
//								}
//							}
//							if(!" ".equals(String.valueOf(room1.getUnit()))){
//								ybjson.put("unitName", room1.getUnit());
//							}else{
//								ybjson.put("unitName","");
//							}
//							
//							
//							if(!"".equals(String.valueOf(room1.getFloor()))){
//								ybjson.put("floorName", room1.getFloor());
//							}else{
//								ybjson.put("floorName", "");
//							}
//							
//							if(room1.getDisplayName()!=null){
//								ybjson.put("room", room1.getDisplayName().replaceAll("\\s", ""));
//							}else{
//								ybjson.put("room", "");
//							}
//							
//							if(room1.getName()!=null){
//								ybjson.put("roomInfo", room1.getName().replaceAll("\\s", ""));
//							}else{
//								ybjson.put("roomInfo","");
//							}							
//								ybjson.put("bldArea", "");
//								ybjson.put("tnArea", "");			
//							if(room1.getCreateTime()!=null){
//								String createTime = sdf.format(room1.getCreateTime());
//								ybjson.put("createTime", createTime);
//							}else{
//								ybjson.put("createTime", "");
//							}
//							if(room1.getLastUpdateTime()!=null){
//								String updateTime = sdf.format(room1.getLastUpdateTime());
//								ybjson.put("modifiedTime", updateTime);
//							}else{
//								ybjson.put("modifiedTime", "");
//							}	
//							ybarr.add(ybjson);
//							
//							if(room1.getHandoverRoomDate()!=null){
//								ybjson.put("deliveryDate", room1.getHandoverRoomDate().toString());
//							}else{
//								ybjson.put("deliveryDate", "");
//							}
//							datajson.put("datas",ybarr);
//							datajson.put("timestamp",timestamp);
//							initjson.put("esbInfo", esbjson);
//							initjson.put("requestInfo",datajson);
//							
////							同步房间信息到yiBei
//							Context ctx=null;
//							if(ybarr.size()>0){
//								try {
////									System.out.println(initjson.toJSONString());
//									String rs111=SHEManageHelper.execPostYB(ctx, initjson.toJSONString(),timestamp);
//									JSONObject rso = JSONObject.parseObject(rs111);
//									if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
//					    				throw new EASBizException(new NumericExceptionSubItem("100",rso.getJSONObject("esbInfo").getString("returnMsg")));
//					    			}else{
//										JSONObject rst=rso.getJSONObject("esbInfo");
//										 instId=rst.getString("instId");
//										 requestTime=rst.getString("requestTime");
//										 builder.clear();
//										 builder.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='room'");
//										 builder.executeUpdate();
//										 JSONObject rst1=rso.getJSONObject("resultInfo");
//										 String ts=rst1.getString("timestamp");
//										 builder.clear();
//										 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='room' ");
//										 builder.executeUpdate();	
//									}
//								} catch (SQLException e1) {
//									e1.printStackTrace();
//								} catch (IOException e1) {
//									e1.printStackTrace();
//								}
//							}
////							kehu
////							yb业主客户
//							datacjson.put("datas",ybcarr);
//							if(timestamp!=null){
//								datacjson.put("timestamp",timestampc);
//							}else{
//								datacjson.put("timestamp","");
//							}
//							initcjson.put("esbInfo", esbcjson);
//							initcjson.put("requestInfo",datacjson);
//							
//							if(ybcarr.size()>0){
//								try {
////									System.out.println(initcjson.toJSONString());
//									String rs111=SHEManageHelper.execPostYBowner(ctx, initcjson.toJSONString(),timestamp);
//									JSONObject rso = JSONObject.parseObject(rs111);
//									if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
//					    				throw new EASBizException(new NumericExceptionSubItem("100",rso.getJSONObject("esbInfo").getString("returnMsg")));
//					    			}else{
//										JSONObject rst=rso.getJSONObject("esbInfo");
//										 instcId=rst.getString("instId");
//										 requestcTime=rst.getString("requestTime");
//										 builder.clear();
//										 builder.appendSql(" update esbInfo set instId='"+instcId+"',requestTime='"+requestcTime+"' where source='owner'");
//										 builder.executeUpdate();
//										 JSONObject rst1=rso.getJSONObject("resultInfo");
//										 String ts=rst1.getString("timestamp");
//										 builder.clear();
//										 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='owner' ");
//										 builder.executeUpdate();
////										MsgBox.showInfo("客户同步成功");
//									}
//								} catch (SQLException e1) {
//									e1.printStackTrace();
//								} catch (IOException e1) {
//									e1.printStackTrace();
//								}
//							}
//							
//						}
//						}
//			}
//		}
//	}
//}
}