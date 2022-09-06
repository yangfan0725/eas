/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.DoPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillFactory;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class NoTradingSellBillEditUI extends AbstractNoTradingSellBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoTradingSellBillEditUI.class);
    public NoTradingSellBillEditUI() throws Exception
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
    	this.actionAttachment.setVisible(true);
    	this.txtDescription.setMaxLength(2000);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		this.actionAddNew.setVisible(false);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
    	KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.contEntry.setTitle("项目明细");
		
		this.kdtEntry.checkParsed();
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("sellAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("sellAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("sellAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtEntry.getColumn("backAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("backAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("backAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$number$;$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		FilterInfo	filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("doProperty", DoPropertyEnum.FCP_VALUE));
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo ss=new SorterItemInfo("number");
		sort.add(ss);
		view.setSorter(sort);
		f7Box.setEntityViewInfo(view);
		
		this.kdtEntry.getColumn("sellProject").setEditor(f7Editor);
		this.kdtEntry.getColumn("sellProject").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof SellProjectInfo){
					SellProjectInfo info = (SellProjectInfo)obj;
					return info.getNumber()+";"+info.getName();
				}
				return super.getText(obj);
			}
		});
		
		this.txtName.setEnabled(false);
		
		this.kdtEntry.getColumn("customer").getStyleAttributes().setHided(true);
    }
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("state");
    	return sic;
    }
    public void storeFields()
    {
        super.storeFields();
        
        Date now=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

        editData.setName(sdf.format(now)+"-非操盘项目销售回款确认单");
  		this.txtName.setName(editData.getName());
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return NoTradingSellBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		NoTradingSellBillInfo info=new NoTradingSellBillInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		try {
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4")));
			info.setOrgUnit(FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4")));
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
			NoTradingSellBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
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
			NoTradingSellBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditButtonStatus(STATUS_VIEW);
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"项目明细不能为空！");
			SysUtil.abort();
		}
		Set roomSet=new HashSet();
		Set spSet=new HashSet();
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
			if (row.getCell("sellProject").getValue() == null) {
				FDCMsgBox.showWarning(this,"项目不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("sellProject"));
				SysUtil.abort();
			} 
			SellProjectInfo sp=(SellProjectInfo) row.getCell("sellProject").getValue();
			if(spSet.contains(sp.getId())){
				FDCMsgBox.showWarning(this,"项目重复！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("sellProject"));
				SysUtil.abort();
			}else{
				spSet.add(sp.getId());
			}
//			if (row.getCell("customer").getValue() == null) {
//				FDCMsgBox.showWarning(this,"客户不能为空！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("customer"));
//				SysUtil.abort();
//			} 
			if (row.getCell("room").getValue() == null) {
				FDCMsgBox.showWarning(this,"房间不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
				SysUtil.abort();
			} 
			RoomInfo room=(RoomInfo) row.getCell("room").getValue();
			if(roomSet.contains(room.getId())){
				FDCMsgBox.showWarning(this,"房间重复！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
				SysUtil.abort();
			}else{
				roomSet.add(room.getId());
			}
			NoTradingSellBillEntryCollection entryCol=NoTradingSellBillEntryFactory.getRemoteInstance().getNoTradingSellBillEntryCollection("select head.number from where room.id='"+room.getId()+"' and head.id!='"+this.editData.getId()+"'");
			if(entryCol.size()>0){
				FDCMsgBox.showWarning(this,"单据："+entryCol.get(0).getHead().getNumber()+"房间已存在！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
				SysUtil.abort();
			}
			if (row.getCell("sellAmount").getValue() == null) {
				FDCMsgBox.showWarning(this,"销售金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("sellAmount"));
				SysUtil.abort();
			} 
			if (row.getCell("backAmount").getValue() == null) {
				FDCMsgBox.showWarning(this,"回款金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("backAmount"));
				SysUtil.abort();
			} 
		}
	}
	protected void verifyInputForSave()throws Exception{
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.actionILine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(kdtEntry)) {
				row = kdtEntry.addRow();
				NoTradingSellBillEntryInfo entry = new NoTradingSellBillEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			} else {
				row = kdtEntry.addRow(top);
				NoTradingSellBillEntryInfo entry = new NoTradingSellBillEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
		} else {
			row = kdtEntry.addRow();
			NoTradingSellBillEntryInfo entry = new NoTradingSellBillEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			row.setUserObject(entry);
		}
		row.getCell("customer").getStyleAttributes().setLocked(true);
		row.getCell("room").getStyleAttributes().setLocked(true);
	}

	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		NoTradingSellBillEntryInfo entry = new NoTradingSellBillEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		row.getCell("customer").getStyleAttributes().setLocked(true);
		row.getCell("room").getStyleAttributes().setLocked(true);
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtEntry.removeRow(activeRowIndex);
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex=e.getRowIndex();
		int colIndex=e.getColIndex();
		if("sellProject".equals(this.kdtEntry.getColumnKey(colIndex))){
			SellProjectInfo sp=(SellProjectInfo) this.kdtEntry.getRow(rowIndex).getCell("sellProject").getValue();
			if(sp!=null){
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//				f7Box.setDisplayFormat("$name$");
//				f7Box.setEditFormat("$number$");
//				f7Box.setCommitFormat("$number$");
//				f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SHECustomerQuery");
//				f7Editor = new KDTDefaultCellEditor(f7Box);
//				
//				FilterInfo	filter = new FilterInfo();
//				FilterItemCollection filterItems = filter.getFilterItems();
//				filterItems.add(new FilterItemInfo("sellProject.id", SHEManageHelper.getParentSellProject(null, sp).getId()));
//				
//				EntityViewInfo view=new EntityViewInfo();
//				view.setFilter(filter);
//				f7Box.setEntityViewInfo(view);
//				
//				this.kdtEntry.getRow(rowIndex).getCell("customer").setEditor(f7Editor);
				
				f7Box = new KDBizPromptBox(); 
				f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Editor = new KDTDefaultCellEditor(f7Box);
				
				RoomCollection roomCol=RoomFactory.getRemoteInstance().getRoomCollection("select * from where building.doProperty='"+DoPropertyEnum.FCP_VALUE+"' and building.sellProject.id='"+sp.getId()+"'");
				if(roomCol.size()==0){
					RoomInfo xx=new RoomInfo();
					xx.setId(BOSUuid.create(xx.getBOSType()));
					roomCol.add(xx);
				}
				BuildingInfo bu=null;
				BuildingCollection buCol=BuildingFactory.getRemoteInstance().getBuildingCollection("select * from where doProperty='"+DoPropertyEnum.FCP_VALUE+"' and sellProject.id='"+sp.getId()+"'");
				if(buCol.size()>0){
					bu=buCol.get(0);
				}
				NewFDCRoomPromptDialog dialog=new NewFDCRoomPromptDialog(Boolean.FALSE, bu, null,
						MoneySysTypeEnum.SalehouseSys, roomCol,sp);
				f7Box.setSelector(dialog);
				this.kdtEntry.getRow(rowIndex).getCell("room").setEditor(f7Editor);
				
//				this.kdtEntry.getRow(rowIndex).getCell("customer").getStyleAttributes().setLocked(false);
				this.kdtEntry.getRow(rowIndex).getCell("room").getStyleAttributes().setLocked(false);
				if(e.getOldValue()!=null&&!((SellProjectInfo)e.getOldValue()).getId().equals(sp.getId())){
//					this.kdtEntry.getRow(rowIndex).getCell("customer").setValue(null);
					this.kdtEntry.getRow(rowIndex).getCell("room").setValue(null);
				}
				this.kdtEntry.getRow(rowIndex).getCell("org").setValue(FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(sp.getOrgUnit().getId())).getName());
			}else{
//				this.kdtEntry.getRow(rowIndex).getCell("customer").setValue(null);
				this.kdtEntry.getRow(rowIndex).getCell("room").setValue(null);
//				this.kdtEntry.getRow(rowIndex).getCell("customer").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(rowIndex).getCell("room").getStyleAttributes().setLocked(true);
				
				this.kdtEntry.getRow(rowIndex).getCell("org").setValue(null);
			}
		}else if("room".equals(this.kdtEntry.getColumnKey(colIndex))){
			RoomInfo room=(RoomInfo) this.kdtEntry.getRow(rowIndex).getCell("room").getValue();
			if(room!=null&&!room.getSellState().equals(RoomSellStateEnum.Init)&&!room.getSellState().equals(RoomSellStateEnum.OnShow)){
				FDCMsgBox.showWarning(this,"房间状态只能为未推盘或者待售！");
				this.kdtEntry.getRow(rowIndex).getCell("room").setValue(null);
				SysUtil.abort();
			}
		}
	}
	public void loadFields() {
		super.loadFields();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			SellProjectInfo sp=(SellProjectInfo) this.kdtEntry.getRow(i).getCell("sellProject").getValue();
			if(sp!=null){
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//				f7Box.setDisplayFormat("$name$");
//				f7Box.setEditFormat("$number$");
//				f7Box.setCommitFormat("$number$");
//				f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SHECustomerQuery");
//				f7Editor = new KDTDefaultCellEditor(f7Box);
//				
//				FilterInfo	filter = new FilterInfo();
//				FilterItemCollection filterItems = filter.getFilterItems();
//				try {
//					filterItems.add(new FilterItemInfo("sellProject.id", SHEManageHelper.getParentSellProject(null, sp).getId()));
//				} catch (EASBizException e) {
//					e.printStackTrace();
//				} catch (BOSException e) {
//					e.printStackTrace();
//				}
//				
//				EntityViewInfo view=new EntityViewInfo();
//				view.setFilter(filter);
//				f7Box.setEntityViewInfo(view);
//				
//				this.kdtEntry.getRow(i).getCell("customer").setEditor(f7Editor);
				
				f7Box = new KDBizPromptBox(); 
				f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Editor = new KDTDefaultCellEditor(f7Box);
				
				RoomCollection roomCol=null;
				BuildingInfo bu=null;
				try {
					roomCol = RoomFactory.getRemoteInstance().getRoomCollection("select * from where building.doProperty='"+DoPropertyEnum.FCP_VALUE+"' and building.sellProject.id='"+sp.getId()+"'");
					if(roomCol.size()==0){
						RoomInfo xx=new RoomInfo();
						xx.setId(BOSUuid.create(xx.getBOSType()));
						roomCol.add(xx);
					}
					BuildingCollection buCol=BuildingFactory.getRemoteInstance().getBuildingCollection("select * from where doProperty='"+DoPropertyEnum.FCP_VALUE+"' and sellProject.id='"+sp.getId()+"'");
					if(buCol.size()>0){
						bu=buCol.get(0);
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}
				NewFDCRoomPromptDialog dialog=new NewFDCRoomPromptDialog(Boolean.FALSE, bu, null,
						MoneySysTypeEnum.SalehouseSys, roomCol,sp);
				f7Box.setSelector(dialog);
				this.kdtEntry.getRow(i).getCell("room").setEditor(f7Editor);
				
//				this.kdtEntry.getRow(i).getCell("customer").getStyleAttributes().setLocked(false);
				this.kdtEntry.getRow(i).getCell("room").getStyleAttributes().setLocked(false);
			}else{
//				this.kdtEntry.getRow(i).getCell("customer").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(i).getCell("room").getStyleAttributes().setLocked(true);
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionEdit_actionPerformed(e);
		this.txtName.setEnabled(false);
	}
}