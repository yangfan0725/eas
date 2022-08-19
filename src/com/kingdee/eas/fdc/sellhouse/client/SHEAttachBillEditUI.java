/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.util.ControlUtilities;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.PropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillCollection;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListCollection;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellStageEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SHEAttachBillEditUI extends AbstractSHEAttachBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHEAttachBillEditUI.class);
    public SHEAttachBillEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.txtDescription.setMaxLength(255);
		this.cbProductTypeProperty.setRequired(true);
		this.txtCustomer.setRequired(true);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		this.actionAddNew.setVisible(false);
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setEnabled(false);
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < PropertyEnum.getEnumList().size(); i++){
        	combo.addItem(PropertyEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("property").setEditor(comboEditor);
//		this.kdtEntry.getColumn("property").setRequired(true);
//		this.kdtEntry.getColumn("context").setRequired(true);
		this.kdtEntry.getColumn("attach").getStyleAttributes().setFontColor(Color.BLUE);
		
		this.contEntry.setTitle("附件清单");
		
		if(this.getUIContext().get("VR")!=null){
			this.actionSave.setVisible(false);
			this.actionSubmit.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionRemove.setVisible(false);
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
		}
    }
    @Override
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("state");
    	sic.add("number");
    	sic.add("name");
    	sic.add("sourceBillId");
    	return sic;
    }
    boolean isOnload=false;
	SellStageEnum oldSellStage=null;
	private void sellStageAction() throws EASBizException, BOSException{
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.cbProductTypeProperty.getSelectedItem()==null||this.cbSellStage.getSelectedItem()==null||this.cbSellType.getSelectedItem()==null){
			return;
		}
		if(this.kdtEntry.getRowCount()>0){
			isShowWarn=true;
        }
        if(isShowWarn){
        	if(FDCMsgBox.showConfirm2(this, "是否覆盖附件清单数据？")== FDCMsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        if(isUpdate){
        	for(int i=0;i<this.kdtEntry.getRowCount();i++){
        		this.deleteAttachment(((SHEAttachBillEntryInfo)this.kdtEntry.getRow(i).getUserObject()).getId().toString());
        	}
       	 	this.kdtEntry.removeRows();
       	 	
       	 	EntityViewInfo view=new EntityViewInfo();
       	 	FilterInfo filter = new FilterInfo();
       	 	filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
	 		filter.getFilterItems().add(new FilterItemInfo("productTypeProperty", ((ProductTypePropertyEnum)this.cbProductTypeProperty.getSelectedItem()).getValue()));
	 		filter.getFilterItems().add(new FilterItemInfo("sellStage", ((SellStageEnum)this.cbSellStage.getSelectedItem()).getValue()));
	 		filter.getFilterItems().add(new FilterItemInfo("sellType", ((SellTypeEnum)this.cbSellType.getSelectedItem()).getValue()));
	 		filter.getFilterItems().add(new FilterItemInfo("room.id", null,CompareType.EQUALS));
	 		view.setFilter(filter);
	        SHEAttachListCollection col=SHEAttachListFactory.getRemoteInstance().getSHEAttachListCollection(view);
	        for(int i=0;i<col.size();i++){
	        	SHEAttachListInfo sp=col.get(i);
	        	for(int j=0;j<sp.getEntry().size();j++){
	        		 IRow row=this.kdtEntry.addRow();
		        	 SHEAttachBillEntryInfo info=new SHEAttachBillEntryInfo();
		        	 info.setId(BOSUuid.create(info.getBOSType()));
		        	 row.setUserObject(info);
		        	 row.getCell("property").setValue(sp.getEntry().get(j).getProperty());
		        	 row.getCell("context").setValue(sp.getEntry().get(j).getContext());
		        	 if(sp.getEntry().get(j).getProperty().equals(PropertyEnum.BY)){
		        		 row.getCell("attach").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		        	 }
	        	}
	         }
	        oldSellStage=(SellStageEnum) cbSellStage.getSelectedItem();
        }else{
        	isOnload=true;
        	cbSellStage.setSelectedItem(oldSellStage);
        	isOnload=false;
        }
	}
	protected void cbSellStage_itemStateChanged(ItemEvent e) throws Exception {
		if(isOnload)return;
		if (e.getStateChange() != ItemEvent.SELECTED) {
			return;
		}
		sellStageAction();
	}
	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.kdtEntry.getColumnKey(e.getColIndex()).equals("attach")) {
			String id=((SHEAttachBillEntryInfo)this.kdtEntry.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        SelectorItemCollection sic=new SelectorItemCollection();
	        sic.add("name");
	        sic.add("building.sellProject.name");
	        sic.add("building.sellProject.orgUnit.name");
	        sic.add("building.number");
	        sic.add("building.name");
	        RoomInfo roomInfo=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(((RoomInfo) this.prmtRoom.getValue()).getId()),sic);
	        String context="（"+this.kdtEntry.getRow(e.getRowIndex()).getCell("context").getValue().toString()+"）";
	        info.setBeizhu(roomInfo.getBuilding().getSellProject().getOrgUnit().getName()+"/"+roomInfo.getBuilding().getSellProject().getName()+"/"+roomInfo.getBuilding().getNumber()+"-"+roomInfo.getBuilding().getName()+"/"+roomInfo.getName()+"/"+context);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtEntry.getRow(e.getRowIndex()).getCell("attach").setValue(loadAttachment(id));
		}
	}
	public void loadFields() {
		kdtEntry.checkParsed();
		isOnload=true;
		
    	super.loadFields();
    	
    	for(int i=0;i<this.kdtEntry.getRowCount();i++){
			SHEAttachBillEntryInfo entry=(SHEAttachBillEntryInfo) this.kdtEntry.getRow(i).getUserObject();
			try {
				if(entry.getId()!=null){
					this.kdtEntry.getRow(i).getCell("attach").setValue(loadAttachment(entry.getId().toString()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			PropertyEnum property = (PropertyEnum) this.kdtEntry.getRow(i).getCell("property").getValue();
			 if(property.equals(PropertyEnum.BY)){
				 this.kdtEntry.getRow(i).getCell("attach").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        	 }
		}
    	try {
	    	if(OprtState.ADDNEW.equals(this.getOprtState())&&this.cbSellStage.getSelectedItem()!=null){
				sellStageAction();
	    	}
	    	if(this.editData.getNumber()!=null){
	    		IObjectValue objectValue=SHEManageHelper.getCurTransactionBill(this.editData.getRoom().getId());
				if(objectValue!=null){
					if(objectValue instanceof PurchaseManageInfo){
						this.cbSellStage.removeItem(SellStageEnum.QY);
						this.cbSellStage.removeItem(SellStageEnum.WQ);
						this.cbSellStage.removeItem(SellStageEnum.JF);
					}
				}
	    	}
	    	this.cbSellStage.removeItem(SellStageEnum.RGBG);
			this.cbSellStage.removeItem(SellStageEnum.QYBG);
    	} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	oldSellStage=(SellStageEnum) this.cbSellStage.getSelectedItem();
    	isOnload=false;
    	
    	setSaveActionStatus();
    }
	protected String loadAttachment(String id) throws BOSException{
		Map att=new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		sels.add("attachment.size");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+"("+col.get(i).getAttachment().getSize()+");";
		}
		return name;
	}
	public boolean isModify()
    {
		if(OprtState.VIEW.equals(getOprtState()) || "FINDVIEW".equals(getOprtState())){
			return false;
		}else if(OprtState.ADDNEW.equals(getOprtState())){
			return true;
		}else{
			return super.isModify();
		}
    }
	public boolean checkBeforeWindowClosing(){
		if (hasWorkThreadRunning()) {
			return false;
		}
		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}
		boolean b = true;
		if (!b) {
			return b;
		} else {
			if (isModify()) {
				int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Confirm_Save_Exit"));
				if (result == KDOptionPane.YES_OPTION) {
					try {
						if (editData.getState() == null|| editData.getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSave,ActionEvent.ACTION_PERFORMED, btnSave.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSubmit,ActionEvent.ACTION_PERFORMED, btnSubmit.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
						}
					} catch (Exception exc) {
						return false;
					}
				} else if (result == KDOptionPane.NO_OPTION) {
					if(editData!=null){
						for(int i=0;i<this.editData.getEntry().size();i++){
							if(this.editData.getEntry().get(i).getId()==null) continue;
							try {
								if(!SHEAttachBillEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getEntry().get(i).getId().toString()))){
									this.deleteAttachment(this.editData.getEntry().get(i).getId().toString());
								}
							} catch (EASBizException e) {
								e.printStackTrace();
							} catch (BOSException e) {
								e.printStackTrace();
							}
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					AttachmentFactory.getRemoteInstance().delete(new ObjectUuidPK(col.get(i).getAttachment().getId().toString()));
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
    
    public void storeFields()
    {
        super.storeFields();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SHEAttachBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
	protected IObjectValue createNewData() {
		SHEAttachBillInfo info=new SHEAttachBillInfo();
		try {
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("productType.property");
			RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(this.getUIContext().get("roomId").toString()),sic);
			info.setRoom(room);
			if(room.getProductType()!=null){
				info.setProductTypeProperty(room.getProductType().getProperty());
			}
			IObjectValue objectValue=SHEManageHelper.getCurTransactionBill(room.getId());
			if(objectValue!=null){
				if(objectValue instanceof PurchaseManageInfo){
					info.setCustomer(((PurchaseManageInfo)objectValue).getCustomerNames());
					info.setNumber(((PurchaseManageInfo)objectValue).getTransactionID().toString());
					info.setName(((PurchaseManageInfo)objectValue).getTransactionID().toString());
					info.setSellType(((PurchaseManageInfo)objectValue).getSellType());
					info.setSellStage(SellStageEnum.RG);
				}
				if(objectValue instanceof SignManageInfo){
					info.setCustomer(((SignManageInfo)objectValue).getCustomerNames());
					info.setNumber(((SignManageInfo)objectValue).getTransactionID().toString());
					info.setName(((SignManageInfo)objectValue).getTransactionID().toString());
					info.setSellType(((SignManageInfo)objectValue).getSellType());
				}
			}else{
				FDCMsgBox.showWarning("当前无有效交易，不能进行上传附件操作！");
				this.setOprtState(OprtState.VIEW);
				SysUtil.abort();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			FDCMsgBox.showWarning("该单据不是审批状态，不能进行反审批操作！");
			return;
		}
		if(editData.getSourceBillId()!=null){
			FDCMsgBox.showWarning("单据由变更生成，不能进行反审批操作！");
			SysUtil.abort();
		}
		String id = getSelectBOID();
		
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (id != null) {
			SHEAttachBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditButtonStatus(this.getOprtState());
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			FDCMsgBox.showWarning("该单据不是提交状态，不能进行审批操作！");
			return;
		}
		if (isModify()) {
			FDCMsgBox.showWarning("单据已被修改，请先提交。");
			this.abort();
		}
		
		String id = getSelectBOID();
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (id != null) {
			SHEAttachBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditButtonStatus(STATUS_VIEW);
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtCustomer);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbProductTypeProperty);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbSellStage);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbSellType);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"附件清单不能为空！");
			SysUtil.abort();
		}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
				
			PropertyEnum property = (PropertyEnum) row.getCell("property").getValue();
			if (property == null) {
				FDCMsgBox.showWarning(this,"性质不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("property"));
				SysUtil.abort();
			} 
			String context = (String)row.getCell("context").getValue();
			if (context==null||"".equals(context.trim())){
				FDCMsgBox.showWarning(this,"内容不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("context"));
				SysUtil.abort();
			}
			if(property.equals(PropertyEnum.BY)){
				String attach=(String)row.getCell("attach").getValue();
				if (attach==null||"".equals(attach.trim())){
					FDCMsgBox.showWarning(this,"附件不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("attach"));
					SysUtil.abort();
				}
			}
		}
		if(!((SellStageEnum)this.cbSellStage.getSelectedItem()).equals(SellStageEnum.RGBG)&&
				!((SellStageEnum)this.cbSellStage.getSelectedItem()).equals(SellStageEnum.QYBG)){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", this.editData.getNumber()));
			filter.getFilterItems().add(new FilterItemInfo("productTypeProperty", ((ProductTypePropertyEnum)this.cbProductTypeProperty.getSelectedItem()).getValue()));
			filter.getFilterItems().add(new FilterItemInfo("sellStage", ((SellStageEnum)this.cbSellStage.getSelectedItem()).getValue()));
			filter.getFilterItems().add(new FilterItemInfo("sellType", ((SellTypeEnum)this.cbSellType.getSelectedItem()).getValue()));
			filter.getFilterItems().add(new FilterItemInfo("room.id", ((RoomInfo)this.prmtRoom.getValue()).getId().toString()));
			if (this.editData.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, this.editData.getId(), CompareType.NOTEQUALS));
			}
			if(SHEAttachBillFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"该附件清单类型已存在！");
				SysUtil.abort();
			}
		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}

}