/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevListCollection;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillEntryCollection;
import com.kingdee.eas.fdc.tenancy.DepositDealBillEntryFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class DepositDealBillEditUI extends AbstractDepositDealBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DepositDealBillEditUI.class);
    public DepositDealBillEditUI() throws Exception
    {
        super();
    }
    public void loadFields() {
    	
    	this.kdtEntry.checkParsed();
		this.kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
//		this.kdtEntry.getColumn("appAmount").setEditor(numberEditor);
//		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("appAmount").setEditor(numberEditor);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("actAmount").setEditor(numberEditor);
		this.kdtEntry.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amount").setRequired(true);
		this.kdtEntry.getColumn("amount").setEditor(numberEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("startDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("endDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("appDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("actDate").getStyleAttributes().setLocked(true);
		
		this.kdtEntry.getColumn("actAmount").getStyleAttributes().setBackground(new Color(225,225,225));
		this.kdtEntry.getColumn("actDate").getStyleAttributes().setBackground(new Color(225,225,225));
		
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", editData.getTenancyBill().getId().toString());
		try {
			TenancyBillEditUI ui = (TenancyBillEditUI) UIFactoryHelper.initUIObject(TenancyBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
			this.panel.setViewportView(ui);
			this.panel.setKeyBoardControl(true);
			this.panel.setEnabled(false);
		} catch (UIException e) {
			e.printStackTrace();
		}
		try {
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("moneyDefine.*");
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				DepositDealBillEntryInfo entry=(DepositDealBillEntryInfo) this.kdtEntry.getRow(i).getUserObject();
				if(BOSUuid.read(entry.getSrcId()).getType().equals(new TenancyRoomPayListEntryInfo().getBOSType())){
					TenancyRoomPayListEntryInfo srcInfo= TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(entry.getSrcId()),sic);
					this.kdtEntry.getRow(i).getCell("moneyDefine").setValue(srcInfo.getMoneyDefine().getName());
					this.kdtEntry.getRow(i).getCell("startDate").setValue(srcInfo.getStartDate());
					this.kdtEntry.getRow(i).getCell("endDate").setValue(srcInfo.getEndDate());
					this.kdtEntry.getRow(i).getCell("appDate").setValue(srcInfo.getAppDate());
					this.kdtEntry.getRow(i).getCell("appAmount").setValue(srcInfo.getAppAmount());
					this.kdtEntry.getRow(i).getCell("actAmount").setValue(srcInfo.getActRevAmount());
					this.kdtEntry.getRow(i).getCell("actDate").setValue(srcInfo.getActRevDate());
				}else{
					TenBillOtherPayInfo srcInfo=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayInfo(new ObjectUuidPK(entry.getSrcId()),sic);
					this.kdtEntry.getRow(i).getCell("moneyDefine").setValue(srcInfo.getMoneyDefine().getName());
					this.kdtEntry.getRow(i).getCell("startDate").setValue(srcInfo.getStartDate());
					this.kdtEntry.getRow(i).getCell("endDate").setValue(srcInfo.getEndDate());
					this.kdtEntry.getRow(i).getCell("appDate").setValue(srcInfo.getAppDate());
					this.kdtEntry.getRow(i).getCell("appAmount").setValue(srcInfo.getAppAmount());
					this.kdtEntry.getRow(i).getCell("actAmount").setValue(srcInfo.getActRevAmount());
					this.kdtEntry.getRow(i).getCell("actDate").setValue(srcInfo.getActRevDate());
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"appAmount","actAmount","amount"});
		
		
		setOprtState(this.oprtState);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void storeFields()
    {
        super.storeFields();
        
        Date now=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(this.cbType.getSelectedItem()!=null){
        	editData.setName(this.txtCustomer.getText()+" "+((DepositDealTypeEnum)this.cbType.getSelectedItem()).getAlias()+" "+sdf.format(now));
      		this.txtName.setName(editData.getName());
        }else{
        	editData.setName(this.txtCustomer.getText()+" "+sdf.format(now));
      		this.txtName.setName(editData.getName());
        }
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return DepositDealBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		DepositDealBillInfo info=new DepositDealBillInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setTenancyBill(ten);
		info.setOrgUnit(ten.getOrgUnit());
		
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("orgUnit.*");
		sels.add("entry.*");
		return sels;
	}
	public void onLoad() throws Exception {
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.actionAttachment.setVisible(true);
		
		this.actionAuditResult.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.txtName.setEnabled(false);
		
//		this.cbType.removeItem(DepositDealTypeEnum.NOTQUIT);
//		this.cbType.removeItem(DepositDealTypeEnum.OFFSET);
//		this.kDContainer1.setTitle("保证金信息");
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		DepositDealBillDataProvider data = new DepositDealBillDataProvider(
				editData.getString("id"),this.editData.getTenancyBill().getId().toString(),getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/tenancy/DepositDealBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
		"com.kingdee.eas.fdc.tenancy.app.DepositDealBillPrintQuery");
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e)
	throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;

		}
		DepositDealBillDataProvider data = new DepositDealBillDataProvider(
				editData.getString("id"),this.editData.getTenancyBill().getId().toString(), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		setAuditButtonStatus(this.getOprtState());
	}
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"保证金信息不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			BigDecimal actAmount=FDCHelper.toBigDecimal(this.kdtEntry.getRow(i).getCell("actAmount").getValue());
			BigDecimal amount=FDCHelper.toBigDecimal(this.kdtEntry.getRow(i).getCell("amount").getValue());
			if(amount.compareTo(actAmount)>0){
				FDCMsgBox.showWarning(this,"申请退押金金额不能大于实收金额！");
				SysUtil.abort();
			}
			BigDecimal subAmount=FDCHelper.ZERO;
			DepositDealBillEntryInfo entry=(DepositDealBillEntryInfo) this.kdtEntry.getRow(i).getUserObject();
			if(BOSUuid.read(entry.getSrcId()).getType().equals(new TenancyRoomPayListEntryInfo().getBOSType())){
				TenancyRoomPayListEntryInfo srcInfo=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(entry.getSrcId()));
				subAmount=FDCHelper.subtract(srcInfo.getActRevAmount(), srcInfo.getHasRefundmentAmount());
			}else{
				TenBillOtherPayInfo srcInfo=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayInfo(new ObjectUuidPK(entry.getSrcId()));
				subAmount=FDCHelper.subtract(srcInfo.getActRevAmount(), srcInfo.getHasRefundmentAmount());
			}
			if(amount.compareTo(subAmount)>0){
				FDCMsgBox.showWarning(this,"申请退押金金额不能大于可退金额！");
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , this.editData.getId().toString()));
		if(!BoAttchAssoFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"请先上传附件！");
			SysUtil.abort();
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception{
		 AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		 String boID = getSelectBOID();
		 if(boID == null)
			 return;
		 boolean isEdit = false;
		 if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState())){
			 isEdit = true;
        }
		 acm.showAttachmentListUIByBoID(boID, this, isEdit);
    }
	 public boolean destroyWindow() {
		 boolean b = super.destroyWindow();
		 if(b){
			 try {
				 if(!DepositDealBillFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId().toString()))){
					 deleteAttachment(this.editData.getId().toString());
				 }
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
       }
       return b;
	}
	 protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			EntityViewInfo attview=new EntityViewInfo();
			FilterInfo attfilter = new FilterInfo();
			
			attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
			attview.setFilter(attfilter);
			BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
			if(attcol.size()==1){
				BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
			}else if(attcol.size()>1){
				BoAttchAssoFactory.getRemoteInstance().delete(filter);
			}
		}
	}
	protected void cbType_itemStateChanged(ItemEvent e) throws Exception {
		super.cbType_itemStateChanged(e);
		this.kdtEntry.removeRows();
		this.kDContainer1.setTitle("");
		if(this.cbType.getSelectedItem()==null){
			return;
		}
		Set mdType=new HashSet();
		if(DepositDealTypeEnum.QUITYJ.equals(this.cbType.getSelectedItem())){
			this.kDContainer1.setTitle("押金信息");
			MoneyDefineCollection col=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * from where moneyType='"+MoneyTypeEnum.DEPOSITAMOUNT_VALUE+"' and sysType='"+MoneySysTypeEnum.TENANCYSYS_VALUE+"'");
			for(int i=0;i<col.size();i++){
				mdType.add(col.get(i).getId().toString());
			}
		}else if(DepositDealTypeEnum.QUITZJ.equals(this.cbType.getSelectedItem())){
			this.kDContainer1.setTitle("租金信息");
			MoneyDefineCollection col=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * from where moneyType='"+MoneyTypeEnum.RENTAMOUNT_VALUE+"' and sysType='"+MoneySysTypeEnum.TENANCYSYS_VALUE+"'");
			for(int i=0;i<col.size();i++){
				mdType.add(col.get(i).getId().toString());
			}
		}else{
			this.kDContainer1.setTitle("其他款项信息");
			MoneyDefineCollection col=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * from where moneyType!='"+MoneyTypeEnum.DEPOSITAMOUNT_VALUE+"' and moneyType!='"+MoneyTypeEnum.RENTAMOUNT_VALUE+"' and sysType='"+MoneySysTypeEnum.TENANCYSYS_VALUE+"'");
			for(int i=0;i<col.size();i++){
				mdType.add(col.get(i).getId().toString());
			}
		}
		
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("otherPayList.*");
		sic.add("otherPayList.moneyDefine.*");
		sic.add("tenancyRoomList.*");
		sic.add("tenancyRoomList.roomPayList.*");
		sic.add("tenancyRoomList.roomPayList.moneyDefine.*");
		DepositDealBillEntryCollection entryCol=new DepositDealBillEntryCollection();
		try {
			RevListCollection revCol=new RevListCollection();
			
			TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(this.editData.getTenancyBill().getId()), sic);
			for (int i = 0; i < tenBill.getOtherPayList().size(); i++) {
				TenBillOtherPayInfo entry=tenBill.getOtherPayList().get(i);
				if(mdType.contains(entry.getMoneyDefine().getId().toString())){
					DepositDealBillEntryCollection deCol=DepositDealBillEntryFactory.getRemoteInstance().getDepositDealBillEntryCollection("select * from where srcId='"+entry.getId().toString()+"' and amount>0");
					if(deCol.size()>0){
						continue;
					}
					BigDecimal subAmount=FDCHelper.subtract(entry.getActRevAmount(), entry.getHasRefundmentAmount());
					if(subAmount.compareTo(FDCHelper.ZERO)<=0){
						continue;
					}
					revCol.add(entry);
				}
			}
			if(tenBill.getTenancyRoomList().size()>0){
				for (int i = 0; i < tenBill.getTenancyRoomList().get(0).getPayList().size(); i++) {
					TenancyRoomPayListEntryInfo entry = tenBill.getTenancyRoomList().get(0).getRoomPayList().get(i);
					if(mdType.contains(entry.getMoneyDefine().getId().toString())){
						DepositDealBillEntryCollection deCol=DepositDealBillEntryFactory.getRemoteInstance().getDepositDealBillEntryCollection("select * from where srcId='"+entry.getId().toString()+"' and amount>0");
						if(deCol.size()>0){
							continue;
						}
						BigDecimal subAmount=FDCHelper.subtract(entry.getActRevAmount(), entry.getHasRefundmentAmount());
						if(subAmount.compareTo(FDCHelper.ZERO)<=0){
							continue;
						}
						revCol.add(entry);
					}
				}
			}
			CRMHelper.sortCollection(revCol, new String[]{"moneyDefine.number","appDate"}, true);
			for(int i=0;i<revCol.size();i++){
				IRow row=this.kdtEntry.addRow();
				DepositDealBillEntryInfo entry=new DepositDealBillEntryInfo();
				entry.setSrcId(revCol.get(i).getId().toString());
				row.setUserObject(entry);
				if(BOSUuid.read(revCol.get(i).getId().toString()).getType().equals(new TenancyRoomPayListEntryInfo().getBOSType())){
					TenancyRoomPayListEntryInfo srcInfo=(TenancyRoomPayListEntryInfo)revCol.get(i);
					this.kdtEntry.getRow(i).getCell("moneyDefine").setValue(srcInfo.getMoneyDefine().getName());
					this.kdtEntry.getRow(i).getCell("startDate").setValue(srcInfo.getStartDate());
					this.kdtEntry.getRow(i).getCell("endDate").setValue(srcInfo.getEndDate());
					this.kdtEntry.getRow(i).getCell("appDate").setValue(srcInfo.getAppDate());
					this.kdtEntry.getRow(i).getCell("appAmount").setValue(srcInfo.getAppAmount());
					this.kdtEntry.getRow(i).getCell("actAmount").setValue(srcInfo.getActRevAmount());
					this.kdtEntry.getRow(i).getCell("actDate").setValue(srcInfo.getActRevDate());
				}else{
					TenBillOtherPayInfo srcInfo=(TenBillOtherPayInfo)revCol.get(i);
					this.kdtEntry.getRow(i).getCell("moneyDefine").setValue(srcInfo.getMoneyDefine().getName());
					this.kdtEntry.getRow(i).getCell("startDate").setValue(srcInfo.getStartDate());
					this.kdtEntry.getRow(i).getCell("endDate").setValue(srcInfo.getEndDate());
					this.kdtEntry.getRow(i).getCell("appDate").setValue(srcInfo.getAppDate());
					this.kdtEntry.getRow(i).getCell("appAmount").setValue(srcInfo.getAppAmount());
					this.kdtEntry.getRow(i).getCell("actAmount").setValue(srcInfo.getActRevAmount());
					this.kdtEntry.getRow(i).getCell("actDate").setValue(srcInfo.getActRevDate());
				}
			}
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
}