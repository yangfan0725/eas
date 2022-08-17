/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
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
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory;
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
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
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
import com.kingdee.util.NumericExceptionSubItem;
/**
 * output class name
 */
public class PurchaseManageListUI extends AbstractPurchaseManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseManageListUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseManageListUI() throws Exception
    {
        super();
    }
    protected void initControl(){
    	super.initControl();
    	CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"lastAgio","purAmount","bulidingArea","strdBuildingPrice","roomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomPrice","dealTotalAmount","attachmentAmount","fitmentTotalAmount"});
    	FDCHelper.formatTableDate(getBillListTable(), "changeDate");
    	if (!isSaleHouseOrg){
			this.actionSignContract.setEnabled(false);
		}else{
			this.actionSignContract.setEnabled(true);
		}
    	this.actionInvalid.setVisible(false);
    	this.btnInvalid.setVisible(false);
    	this.btnToMT.setText("同步盟拓");
    	this.btnToMT.setIcon(EASResource.getIcon("imgTbtn_input"));
    }
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"purAmount","bulidingArea","roomArea","strdTotalAmount","dealTotalAmount","attachmentAmount","fitmentTotalAmount"});
	}
    protected void initWorkButton() {
		super.initWorkButton();
        this.btnRelateSign.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.menuItemRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.menuItemRelateSign.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.btnRelateSign.setEnabled(true);
        this.btnRelatePrePurchase.setEnabled(true);
        this.menuItemRelatePrePurchase.setEnabled(true);
        this.menuItemRelateSign.setEnabled(true);
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return PurchaseManageFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return PurchaseManageEditUI.class.getName();
	}
	protected String getBizStateAuditValue() {
		return TransactionStateEnum.PURAUDIT_VALUE;
	}

	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.PURAUDIT;
	}

	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.PURNULLIFY;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.PURAPPLE;
	}
	protected TransactionStateEnum getBizStateSavedEnum() {
		return TransactionStateEnum.PURSAVED;
	}
	public void actionSignContract_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
    	PurchaseManageInfo info=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id),sels);
		SHEManageHelper.toTransactionBill(info.getId(), info.getBizState(), this, SignManageEditUI.class.getName());
		this.refresh(null);
	}
	
	protected String whoAmI() {
		return IAMPURCHASE;
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
		sels.add("purCustomerEntry.*");
		sels.add("purCustomerEntry.customer.*");
		sels.add("purCustomerEntry.certificate.*");
		sels.add("purPayListEntry.*");
		sels.add("purPayListEntry.moneyDefine.*");
		PurchaseManageInfo info =PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id),sels);
		if(!(TransactionStateEnum.PURAPPLE.equals(info.getBizState())||
				TransactionStateEnum.PURAUDIT.equals(info.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getPurCustomerEntry().size()];
		for(int i=0;i<info.getPurCustomerEntry().size();i++){
			custObjs[i]=info.getPurCustomerEntry().get(i).getCustomer();
		}
		
		Set tranEntryIdSet = new HashSet();
		PurPayListEntryCollection signPayListColl = info.getPurPayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			PurPayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}			
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(), info ,custObjs,tranEntryIdSet);
	}

	public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PurchaseManageInfo info=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PrePurchaseManageInfo){
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

	public void actionRelateSign_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId",id));
		view.setFilter(filter);
		SignManageCollection  col= SignManageFactory.getRemoteInstance().getSignManageCollection(view);
		if(col.get(0)!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", col.get(0).getId().toString());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(SignManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        return;
		}
		FDCMsgBox.showWarning(this,"无关联签约单据！");
    }
	public void actionToMT_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		
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
		JSONArray carr=new JSONArray();
		 
		for(int ii = 0; ii < id.size(); ii++){
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("purCustomerEntry.isMain");
			sic.add("purCustomerEntry.customer.*");
			sic.add("purCustomerEntry.customer.certificateType.name");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("purSaleManEntry.user.number");
			
			 PurchaseManageInfo info=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id.get(ii).toString()),sic);
			 if(!info.getBizState().equals(TransactionStateEnum.PURAUDIT)&&!info.getBizState().equals(TransactionStateEnum.PURAPPLE)&&!info.getBizState().equals(TransactionStateEnum.TOSIGN)){
				 continue;
			 }
			 JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "2");
			cjo.put("projectId",info.getSellProject().getNumber());
			String customerId="";
			SHECustomerInfo quc=null;
			Timestamp qudate=null;
			
			
			for(int i=0;i<info.getPurCustomerEntry().size();i++){
				if(i==0){
					customerId=info.getPurCustomerEntry().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getPurCustomerEntry().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getPurCustomerEntry().get(i).isIsMain()){
						quc=info.getPurCustomerEntry().get(i).getCustomer();
					}
				}else{
					if(info.getPurCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getPurCustomerEntry().get(i).getCustomer().getReportDate()==null){
						MsgBox.showWarning("客户报备日期和首访日期都为空！");
						return;
					}
					if(qudate==null){
						if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						quc=info.getPurCustomerEntry().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getPurCustomerEntry().get(i).getCustomer();
						}
					}
				}
			}
			cjo.put("qdCustId",quc.getNumber());
			cjo.put("customerId", customerId);
			if(info.getRoom()!=null){
				cjo.put("roomId", info.getRoom().getNumber());
				cjo.put("rcYT", info.getRoom().getProductType().getName());
			}
			cjo.put("roomStatus", "认购");
			cjo.put("userId", info.getPurSaleManEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			cjo.put("tradeAmount", info.getDealTotalAmount());
			cjo.put("payType", info.getPayType().getName());
			cjo.put("expectDate", df.format(info.getPlanSignDate()));
			cjo.put("price", info.getDealBuildPrice());
			cjo.put("area",info.getBulidingArea());
			cjo.put("contractNo", info.getNumber());
			cjo.put("rengouId", "");
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			
			if(PurChangeStateEnum.CHANGE.equals(info.getChangeState())){
				cjo.put("isChangeRoomNew", "1");
			}else{
				cjo.put("isChangeRoomNew", "0");
			}
			
			sic=new SelectorItemCollection();
			sic.add("number");
			if(info.getSrcId()!=null){
				SincerityPurchaseInfo src=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
				cjo.put("rcId", src.getNumber());
			}
			cjo.put("salesStatisticsDate", "");
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
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("room.*");
		PurchaseManageInfo info =PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id),sels);
		if(info.getRoom()!=null){
			DelayPayBillCollection col = DelayPayBillFactory.getRemoteInstance().getDelayPayBillCollection("select newEntry.*,newEntry.moneyDefine.*,* from where room.id='"+info.getRoom().getId().toString()+"' and sourceFunction not like '%QUIT%'");
			if(col.size()>0){
				MsgBox.showWarning(this,"存在延期申请单,禁止修改！");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
}