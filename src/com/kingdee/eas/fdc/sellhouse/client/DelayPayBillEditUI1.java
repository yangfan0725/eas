/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class DelayPayBillEditUI1 extends AbstractDelayPayBillEditUI1
{
    public DelayPayBillEditUI1() throws Exception {
		super();
	}
	private static final Logger logger = CoreUIObject.getLogger(DelayPayBillEditUI1.class);
    private static final String CANTEDIT = "cantEdit";
    protected Map listenersMap = new HashMap();
    public void onLoad() throws Exception {
		super.onLoad();
		initControl();

		setAuditButtonStatus(this.getOprtState());
		
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(new Date(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllParentSellProjectCollection((editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("project.id", null,CompareType.IS));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and (#4 or #5)");
		view.setFilter(filter);
		
		this.prmtPayType.setEntityViewInfo(view);
		this.prmtPayType.setEnabled(false);
		
		SellProjectInfo sellProject=editData.getSellProject();
		
		NewFDCRoomPromptDialog dialog=new NewFDCRoomPromptDialog(Boolean.FALSE, null, null,
				MoneySysTypeEnum.SalehouseSys, null,sellProject);
		this.prmtRoom.setSelector(dialog);
		
		
		this.tblAttachement.checkParsed();
		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		initTblPayList(this.kdtEntry);
		kdtEntry.setEnabled(false);
		initTblPayList(this.kdtNewEntry);
		
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contNewEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contNewEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.pkBizDate.setEnabled(false);
		this.pkPurDate.setEnabled(false);
		this.pkSignDate.setEnabled(false);
		
		this.actionALine.setVisible(false);
		this.actionRLine.setVisible(false);
		
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setHided(true);
		this.kdtNewEntry.getColumn("appAmount").getStyleAttributes().setHided(true);
		
		this.kdtNewEntry.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
		this.kdtNewEntry.getColumn("appAmount").getStyleAttributes().setLocked(true);
		
		handleCodingRule();
		this.txtNumber.setEnabled(false);
	}
    protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		
		
		//by tim_gao 2010-11-17有更改
			//-- 销售组织下获取成本中心为空的处理 zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();
			
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				this.txtNumber.setText(number);
			}
			setNumberTextEnabled();
		}
	}
    protected void  prepareNumber (IObjectValue iobjectvalue, String s){
    	this.txtNumber.setText(s);
    }
    private void initTblPayList(KDTable table) {
    	table.checkParsed();
		String formatString = "yyyy-MM-dd";
		table.getColumn("appDate").getStyleAttributes().setNumberFormat(formatString);
		
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		table.getColumn("appDate").setEditor(dateEditor);
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setEditFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		// 这里应该和认购单上的过滤一致
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE,
						CompareType.EQUALS));
		Set moneyTypeSet = new HashSet();
		moneyTypeSet.add(MoneyTypeEnum.EARNESTMONEY_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.FISRTAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.HOUSEAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.LOANAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//		moneyTypeSet.add(MoneyTypeEnum.REPLACEFEE_VALUE);
//		moneyTypeSet.add(MoneyTypeEnum.LATEFEE_VALUE);
//		moneyTypeSet.add(MoneyTypeEnum.COMMISSIONCHARGE_VALUE);
//		moneyTypeSet.add(MoneyTypeEnum.ELSEAMOUNT_VALUE);
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						moneyTypeSet,
						CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("name", "定金",CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.RoomAttachMoneyDefineNum,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.FittmentMoneyDefineNum,CompareType.NOTEQUALS));
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor mdeditor=  new KDTDefaultCellEditor(f7Box);
		
		table.getColumn("moneyDefine").setEditor(mdeditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(true);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		table.getColumn("appAmount").setEditor(amountEditor);
		table.getColumn("appAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		table.getColumn("moneyDefine").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getColumn("appDate").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getColumn("appAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
	}
    public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				tblAttachement.checkParsed();
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("seq").setValue(attachment.getAttachID());
					row.getCell("name").setValue(attachment.getName());
					row.getCell("date").setValue(attachment.getCreateTime());
					row.getCell("type").setValue(boaInfo.getAssoType());
				}
			}
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
    	IRow row = this.kdtNewEntry.addRow();
		DelayPayBillNewEntryInfo entry = new DelayPayBillNewEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtNewEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtNewEntry.removeRow(activeRowIndex);
	}
	protected void prmtPayType_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.prmtPayType_dataChanged(e);
	}
	protected void prmtRoom_dataChanged(DataChangeEvent e) throws Exception {
		RoomInfo room=(RoomInfo) this.prmtRoom.getValue();
		this.prmtPayType.setValue(null);
		this.txtCustomerNames.setText(null);
		if(room!=null){
			if(!room.getSellState().equals(RoomSellStateEnum.Purchase)&&!room.getSellState().equals(RoomSellStateEnum.Sign)){
				FDCMsgBox.showWarning(this,"只能选择认购或者签约状态房间");
				this.prmtRoom.setValue(null);
			}
			String sourceFunction=null;
			if(room.getSellState().equals(RoomSellStateEnum.Sign)){
				sourceFunction="SIGN";
			}else{
				sourceFunction="PUR";
			}
			DelayPayBillCollection col = DelayPayBillFactory.getRemoteInstance().getDelayPayBillCollection("select newEntry.*,newEntry.moneyDefine.*,* from where sourceFunction='"+sourceFunction+"' and room.id='"+room.getId().toString()+"' and id!='"+editData.getId()+"' order by newEntry.seq");
			if(col.size()>0){
				FDCMsgBox.showWarning(this,"延期申请不允许申请多次");
				this.prmtRoom.setValue(null);
			}
			isUpdateDate=true;
			setTable();
			isUpdateDate=false;
		}
	}
	private boolean isUpdateDate=false;
	private void setTable() throws BOSException{
		RoomInfo room=(RoomInfo) this.prmtRoom.getValue();
		if(room!=null){
			SignManageCollection signCol=SignManageFactory.getRemoteInstance().getSignManageCollection("select signPayListEntry.*,signPayListEntry.moneyDefine.*,payType.*,sellProject.*,* from where room.id='"+room.getId()+"' and (bizState='SignApple' or bizState='SignAudit') order by signPayListEntry.seq");
			if(signCol.size()>0){
				this.prmtPayType.setValue(signCol.get(0).getPayType());
				this.txtCustomerNames.setText(signCol.get(0).getCustomerNames());
				
				if(signCol.get(0).getSrcId()!=null){
					PurchaseManageCollection srcCol=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection("select * from where id='"+signCol.get(0).getSrcId()+"'");
					if(srcCol.size()>0){
						this.pkPurDate.setValue(srcCol.get(0).getBusAdscriptionDate());
					}
				}
				
				this.pkSignDate.setValue(signCol.get(0).getBusAdscriptionDate());
				if(isUpdateDate){
					this.pkPlanSignDate.setValue(signCol.get(0).getPlanSignDate());
				}
				this.editData.setSourceFunction("SIGN");
				this.editData.setSellProject(signCol.get(0).getSellProject());
				this.prmtSellProject.setValue(signCol.get(0).getSellProject());
				
				this.kdtEntry.removeRows();
				this.kdtNewEntry.removeRows();
				
				SignPayListEntryCollection entryCol=signCol.get(0).getSignPayListEntry();
				CRMHelper.sortCollection(entryCol, new String[]{"seq"}, true);
				for(int i=0;i<entryCol.size();i++){
					SignPayListEntryInfo entry=entryCol.get(i);
					IRow row=this.kdtEntry.addRow();
					DelayPayBillEntryInfo dentry=new DelayPayBillEntryInfo();
					row.setUserObject(dentry);
					
					row.getCell("moneyDefine").setValue(entry.getMoneyDefine());
					row.getCell("appDate").setValue(entry.getAppDate());
					row.getCell("appAmount").setValue(entry.getAppAmount());
					
					IRow newrow=this.kdtNewEntry.addRow();
					if(i==0)newrow.getStyleAttributes().setLocked(true);
					DelayPayBillNewEntryInfo newdentry=new DelayPayBillNewEntryInfo();
					newrow.setUserObject(newdentry);
					
					newrow.getCell("moneyDefine").setValue(entry.getMoneyDefine());
					newrow.getCell("appDate").setValue(entry.getAppDate());
					newrow.getCell("appAmount").setValue(entry.getAppAmount());
				}
			}else{
				PurchaseManageCollection purCol=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection("select room.*,purPayListEntry.*,purPayListEntry.moneyDefine.*,payType.*,sellProject.*,* from where room.id='"+room.getId()+"' and (bizState='PurApple' or bizState='PurAudit')");
				if(purCol.size()>0){
					this.editData.setSourceFunction("PUR");
					this.editData.setSellProject(purCol.get(0).getSellProject());
					this.prmtSellProject.setValue(purCol.get(0).getSellProject());
					this.prmtPayType.setValue(purCol.get(0).getPayType());
					this.txtCustomerNames.setText(purCol.get(0).getCustomerNames());
					this.pkPurDate.setValue(purCol.get(0).getBusAdscriptionDate());
					this.pkSignDate.setValue(null);
					if(isUpdateDate){
						this.pkPlanSignDate.setValue(FDCDateHelper.addDays(purCol.get(0).getBusAdscriptionDate(), 3));
					}
					this.kdtNewEntry.removeRows();
					if(isUpdateDate){
						this.kdtEntry.removeRows();
						updatePayListByPayType(this.kdtEntry,purCol.get(0));
					}
					updatePayListByPayType(this.kdtNewEntry,purCol.get(0));
				}
			}
		}else{
			this.kdtEntry.removeRows();
			this.kdtNewEntry.removeRows();
		}
	}
	protected void updatePayListByPayType(KDTable table,PurchaseManageInfo info) {
		try {
			boolean isAddFittment=true;
			boolean isAddRoomAttach=true;
			BigDecimal attachmentTotalAmount=FDCHelper.ZERO;
			
			if(attachmentTotalAmount.compareTo(FDCHelper.ZERO)>0){
				isAddRoomAttach=false;
			}
			IRow fittment=null;
			IRow roomAttach=null;
			int digit=0;
			int priceToIntegerType=0;
			HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
			if(SHEManageHelper.setPrecision(info.getDigit())==null){
				digit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()).intValue();
			}else{
				digit=SHEManageHelper.setPrecision(info.getDigit()).intValue();
			}
			if(SHEManageHelper.setRoundingMode(info.getPriceToIntegerType())==null){
				priceToIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString()).intValue();
			}else{
				priceToIntegerType=SHEManageHelper.setRoundingMode(info.getPriceToIntegerType()).intValue();
			}
			
			List toAddRowEntry = SHEManageHelper.updatePayListByPayType((SHEPayTypeInfo) this.prmtPayType.getValue(), info.isIsEarnestInHouseAmount(), table, fittment, roomAttach, null, 
					info.getContractTotalAmount(), info.getDealTotalAmount(),info.getRoom().getStandardTotalAmount()
					, info.getBulidingArea(), info.getRoomArea(),info.getRoom(), digit, priceToIntegerType,(Date)this.pkPlanSignDate.getValue(),SHEPayTypeBizTimeEnum.SIGN,isAddFittment,isAddRoomAttach);
			int rowCount=0;
			for (int i = 0; i < toAddRowEntry.size(); i++) {
				IRow row =null;
				if(i==0){
					row = table.addRow(0);
					if(i==0)row.getStyleAttributes().setLocked(true);
				}else{
					row = table.addRow(rowCount+1);
				}
				row.getCell("moneyDefine").setValue(((TranPayListEntryInfo) toAddRowEntry.get(i)).getMoneyDefine());
				row.getCell("appDate").setValue(((TranPayListEntryInfo) toAddRowEntry.get(i)).getAppDate());
				row.getCell("appAmount").setValue(((TranPayListEntryInfo) toAddRowEntry.get(i)).getAppAmount());
				
				rowCount=row.getRowIndex();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
		{
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
			
		}
}
	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}
	protected void setSaveActionStatus() {
		if (this.editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

    public void loadFields()
    {
    	isOnload=true;
    	detachListeners();
		
		super.loadFields();

		setSaveActionStatus();
		setAuditButtonStatus(this.getOprtState());
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.removeRows();
		for(int i=0;i<this.editData.getEntry().size();i++){
			IRow row=this.kdtEntry.addRow();
			row.getCell("moneyDefine").setValue(this.editData.getEntry().get(i).getMoneyDefine());
			row.getCell("appAmount").setValue(this.editData.getEntry().get(i).getAppAmount());
			row.getCell("appDate").setValue(this.editData.getEntry().get(i).getAppDate());
		}
		
		this.kdtNewEntry.checkParsed();
		this.kdtNewEntry.removeRows();
		for(int i=0;i<this.editData.getNewEntry().size();i++){
			IRow row=this.kdtNewEntry.addRow();
			if(i==0)row.getStyleAttributes().setLocked(true);
			row.getCell("moneyDefine").setValue(this.editData.getNewEntry().get(i).getMoneyDefine());
			row.getCell("appAmount").setValue(this.editData.getNewEntry().get(i).getAppAmount());
			row.getCell("appDate").setValue(this.editData.getNewEntry().get(i).getAppDate());
		}
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
		
		attachListeners();
		isOnload=false;
    }
	public void storeFields() {
		this.editData.getEntry().clear();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			DelayPayBillEntryInfo entry=new DelayPayBillEntryInfo();
			entry.setSeq(i);
			entry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyDefine").getValue());
			entry.setAppAmount((BigDecimal) row.getCell("appAmount").getValue());
			entry.setAppDate((Date) row.getCell("appDate").getValue());
			this.editData.getEntry().add(entry);
		}
		
		this.editData.getNewEntry().clear();
		for(int i=0;i<this.kdtNewEntry.getRowCount();i++){
			IRow row=this.kdtNewEntry.getRow(i);
			DelayPayBillNewEntryInfo entry=new DelayPayBillNewEntryInfo();
			entry.setSeq(i);
			entry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyDefine").getValue());
			entry.setAppAmount((BigDecimal) row.getCell("appAmount").getValue());
			entry.setAppDate((Date) row.getCell("appDate").getValue());
			
			entry.setActRevAmount(new BigDecimal(FDCDateHelper.getDiffDays((Date) this.kdtEntry.getRow(i).getCell("appDate").getValue(), (Date) row.getCell("appDate").getValue())-1));
			this.editData.getNewEntry().add(entry);
		}
		super.storeFields();
	}
	
    /**
     * 初始化显示
     * */
    private void initControl(){
    	this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPrint.setEnabled(false);
		this.actionPrintPreview.setEnabled(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
    	
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    }
	public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
		checkBeforeEditOrRemove("CANREMOVE");
		super.actionRemove_actionPerformed(arg0);
	}
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(arg0);
		setSaveActionStatus();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		handleOldData();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审批中或者已审批，不能再提交！");
			SysUtil.abort();
		}
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.auditAction.setVisible(true);
		this.auditAction.setEnabled(true);
		
		handleOldData();
	}
	protected void verifyInput(ActionEvent e) throws Exception{
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRoom);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPayType);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtCaseInfo);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkPlanSignDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbIsPass);
		RoomInfo room=(RoomInfo) this.prmtRoom.getValue();
		PurchaseManageCollection purCol=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection("select purPayListEntry.*,purPayListEntry.moneyDefine.*,payType.*,* from where room.id='"+room.getId()+"' and (bizState='PurApple' or bizState='PurAudit')");
		if(purCol.size()>0){
			if(FDCDateHelper.getDiffDays(purCol.get(0).getBusAdscriptionDate(), (Date) this.pkBizDate.getValue())>4){
				FDCMsgBox.showWarning(this,"制度要求延期申请单必须认购当天发起！");
				SysUtil.abort();
			}
		}
		if(room.getSellState().equals(RoomSellStateEnum.Sign)&&!this.cbType.getSelectedItem().equals(DelayPayTypeEnum.YQFK)){
			FDCMsgBox.showWarning(this,"签约状态不能进行延期签约");
			SysUtil.abort();
		}
		if(this.kdtNewEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"新付款明细不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtNewEntry.getRowCount();i++){
			IRow row=this.kdtNewEntry.getRow(i);
			if(row.getCell("moneyDefine").getValue()==null){
				FDCMsgBox.showWarning(this,"款项名称不能为空！");
				this.kdtNewEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtNewEntry.getColumnIndex("moneyDefine"));
				SysUtil.abort();
			}
			if(row.getCell("appDate").getValue()==null){
				FDCMsgBox.showWarning(this,"应收日期不能为空！");
				this.kdtNewEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtNewEntry.getColumnIndex("appDate"));
				SysUtil.abort();
			}
//			if(row.getCell("appAmount").getValue()==null){
//				FDCMsgBox.showWarning(this,"应收金额不能为空！");
//				this.kdtNewEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtNewEntry.getColumnIndex("appAmount"));
//				SysUtil.abort();
//			}
//			if(((BigDecimal)row.getCell("appAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
//				FDCMsgBox.showWarning(this,"应收金额不能为0！");
//				this.kdtNewEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtNewEntry.getColumnIndex("appAmount"));
//				SysUtil.abort();
//			}
		}
	}
	
    protected boolean checkCanSubmit() throws Exception {
		
		if(editData.getId()==null){
			return true;
		}
		//检查是否在工作流中
//		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		FDCBillStateEnum state = editData.getState();
		if (state != null
				&& (FDCBillStateEnum.AUDITTED.equals(state))) {
			return false;
		}else{
			return true;
		}
		
	}
    protected void checkBeforeEditOrRemove(String warning) {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		FDCBillStateEnum State = ((DelayPayBillInfo)editData).getState();
		
		if (State != null
				&& (State == FDCBillStateEnum.AUDITTING || State == FDCBillStateEnum.AUDITTED )) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
    protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	public void auditAction_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectBOID();
		if (!FDCBillStateEnum.SUBMITTED.equals(getState(id))) {
			if (FDCBillStateEnum.AUDITTED.equals(getState(id))) {
				FDCMsgBox.showWarning("该单据已经是已审批状态！");
			}else{
				FDCMsgBox.showWarning("该单据不是提交状态，不能进行审批操作！");
			}
			return;
		}
		if (isModify()) {
			FDCMsgBox.showWarning("单据已被修改，请先提交。");
			this.abort();
		}
		
    	FDCClientUtils.checkBillInWorkflow(this, id);
		DelayPayBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		this.unAuditAction.setVisible(true);
		this.unAuditAction.setEnabled(true);
		this.auditAction.setVisible(false);
		this.auditAction.setEnabled(false);
	}
	
    public FDCBillStateEnum getState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("State");
    	return ((DelayPayBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	public void unAuditAction_actionPerformed(ActionEvent e) throws Exception {
		super.unAuditAction_actionPerformed(e);
		String id = getSelectBOID();
		
		if (!FDCBillStateEnum.AUDITTED.equals(getState(id))) {
			FDCMsgBox.showWarning("该单据不是审批状态，不能进行反审批操作！");
			return;
		}
    	
    	FDCClientUtils.checkBillInWorkflow(this, id);
		
		DelayPayBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		this.unAuditAction.setVisible(false);
		this.unAuditAction.setEnabled(false);
		this.auditAction.setVisible(true);
		this.auditAction.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	    setAuditButtonStatus(oprtType);
	    if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionRLine.setEnabled(false);
			this.actionALine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionRLine.setEnabled(true);
			this.actionALine.setEnabled(true);
		}
	}
    protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		auditAction.setVisible(true);
    		unAuditAction.setVisible(true);
    		auditAction.setEnabled(true);
    		unAuditAction.setEnabled(true);
    		
    		DelayPayBillInfo bill = (DelayPayBillInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    				unAuditAction.setVisible(true);
    				unAuditAction.setEnabled(true);   
    	    		auditAction.setVisible(false);
    	    		auditAction.setEnabled(false);
    			}else{
    				unAuditAction.setVisible(false);
    				unAuditAction.setEnabled(false);   
    	    		auditAction.setVisible(true);
    	    		auditAction.setEnabled(true);
    			}
    		}
    	}else {
    		auditAction.setVisible(false);
    		unAuditAction.setVisible(false);
    		auditAction.setEnabled(false);
    		unAuditAction.setEnabled(false);
    	}
    }
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory.getRemoteInstance();
    }
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        DelayPayBillInfo info = new DelayPayBillInfo();
        info.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        try{
        	info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());//业务日期
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setId(BOSUuid.create(info.getBOSType()));
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		info.setState(FDCBillStateEnum.SAVED);
		info.setSellProject(sellproject);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
        return info;
    }
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sel = super.getSelectors();
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("newEntry.*");
    	sel.add("newEntry.moneyDefine.*");
    	sel.add("entry.*");
    	sel.add("entry.moneyDefine.*");
    	sel.add("sourceFunction");
    	return sel;
    }
	protected IObjectValue createNewDetailData(KDTable arg0) {
		return null;
	}
	protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
    }
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    protected void attachListeners() {
		addDataChangeListener(this.prmtRoom);
		addDataChangeListener(this.pkPlanSignDate);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtRoom);
		removeDataChangeListener(this.pkPlanSignDate);
	}
	private boolean isOnload=false;
	protected void cbType_itemStateChanged(ItemEvent e) throws Exception {
		if(isOnload){
			return;
		}
		if(this.cbType.getSelectedItem().equals(DelayPayTypeEnum.YQQY)){
			this.pkPlanSignDate.setEnabled(true);
			this.kdtNewEntry.setEnabled(false);
			this.txtCaseInfo.setText("1、按揭方式：客户因XX原因，申请延期签约，承诺于XX年XX月XX日支付XX%首付且签约，并于XX年XX月XX日办理按揭，预计全款到账时间为XX年XX月XX日。延期几天，请领导审批。\n"+
"2、分期方式：客户因XX原因，申请延期签约，承诺于XX年XX月XX日支付XX%首付且签约，并于XX年XX月XX日付清剩余房款。延期几天，请领导审批。\n");
		}else if(this.cbType.getSelectedItem().equals(DelayPayTypeEnum.YQFK)){
			this.pkPlanSignDate.setEnabled(false);
			this.kdtNewEntry.setEnabled(true);
			this.txtCaseInfo.setText("1、延首付款：客户因XX原因，申请首付款延期付款，承诺于XX年XX月XX日支付XX%首付，并在XX月XX日付清剩余首付款，预计全款到账时间为XX年XX月XX日。本月首付分期分期已申请XX套，占当月签约比例XX%。延期几天，请领导审批。\n"+
"2、延分期/按揭款：客户因XX原因，申请分期/按揭款延期付款，承诺于XX年XX月XX日支付/办理按揭，预计全款到账时间为XX年XX月XX日。延期几天，请领导审批。\n"+
"3、变更付款方式但优惠不变：客户因XX原因，申请从XX付款方式变更为XX付款方式，预计全款到账日期为XX年XX月XX日，报XX同意，原优惠折扣不变。请领导审批。\n");
		}else{
			this.pkPlanSignDate.setEnabled(true);
			this.kdtNewEntry.setEnabled(true);
			this.txtCaseInfo.setText("1、延签后足额首付并按揭方式：客户因XX原因，申请延期签约&延期付款，承诺于XX年XX月XX日支付XX%首付且签约，并延期至XX年XX月XX日办理按揭，预计全款到账时间为XX年XX月XX日。延期签约XX天&延期付款XX天，请领导审批。\n"+
"2、延签后足额首付并分期方式：客户因XX原因，申请延期签约&延期付款，承诺于XX年XX月XX日支付XX%首付且签约，并延期至XX年XX月XX日付清剩余房款。延期签约XX天&延期付款XX天，请领导审批。\n"+
"3、延签后非足额首付并按揭方式：客户因XX原因，申请延期签约&延期付款，承诺于XX年XX月XX日支付XX%首付且签约，于XX月XX日付清剩余首付款。并延期至XX年XX月XX日办理按揭，预计全款到账时间为XX年XX月XX日。本月首付分期已申请XX万，占当月签约比例XX%。延期签约XX天&延期付款XX天，请领导审批。\n"+
"4、延签后非足额首付并分期方式：客户因XX原因，申请延期签约&延期付款，承诺于XX年XX月XX日支付XX%首付且签约，于XX月XX日付清剩余首付款。并延期至XX年XX月XX日付清剩余房款。本月首付分期已申请XX万，占当月签约比例XX%。延期签约XX天&延期付款XX天，请领导审批。\n");
		}
		isUpdateDate=true;
		setTable();
		isUpdateDate=false;
	}
	protected void pkPlanSignDate_dataChanged(DataChangeEvent e)throws Exception {
		if(isOnload){
			return;
		}
		if(!isUpdateDate){
			setTable();
		}
	}
}