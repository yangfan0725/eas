/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class F7SelectRentMoneyDefineListUI extends AbstractF7SelectRentMoneyDefineListUI {
	public static final Color KEY_LOCKED_ROW = new Color(0xE8E8E3);
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() != 1)
			return;
		int activeRowIndex = e.getRowIndex();
		int activeColumnIndex = e.getColIndex();
		if (activeColumnIndex == this.tblMain.getColumnIndex("isSelected")) {
			ICell iCell = this.tblMain.getRow(activeRowIndex).getCell("isSelected");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				iCell.setValue(Boolean.FALSE);
			} else {
				iCell.setValue(Boolean.TRUE);
			}
		}
	}

	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		TenancyRoomPayListEntryCollection payCol = new TenancyRoomPayListEntryCollection();
		TenancyRoomEntryCollection roomCol = new TenancyRoomEntryCollection();
		TenBillOtherPayCollection otherCol = new TenBillOtherPayCollection();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			ICell iCell = this.tblMain.getRow(j).getCell("isSelected");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				IRow seleRow = this.tblMain.getRow(j);
				Object o  = seleRow.getCell("strartDate").getUserObject();		
				if(o instanceof TenancyRoomPayListEntryInfo){
					TenancyRoomPayListEntryInfo payInfo = (TenancyRoomPayListEntryInfo) seleRow.getCell("strartDate").getUserObject();
					TenancyRoomEntryInfo roomEntryInfo = (TenancyRoomEntryInfo) seleRow.getCell("endDate").getUserObject();
					payCol.add(payInfo);
					roomCol.add(roomEntryInfo);
				}
				
				if(o instanceof TenBillOtherPayInfo){
					TenBillOtherPayInfo oPayInfo = (TenBillOtherPayInfo) o;
					otherCol.add(oPayInfo);
				}
			}
		}
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("roomCol", roomCol);
		this.getUIContext().put("otherCol", otherCol);
		this.destroyWindow();
	}

	public void actionUnselect_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnselect_actionPerformed(e);
		TenancyRoomPayListEntryCollection payCol = new TenancyRoomPayListEntryCollection();
		TenancyRoomEntryCollection roomCol = new TenancyRoomEntryCollection();
		TenBillOtherPayCollection otherCol = new TenBillOtherPayCollection();
		payCol.clear();
		roomCol.clear();
		otherCol.clear();
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("roomCol", roomCol);
		this.getUIContext().put("otherCol", otherCol);
		this.destroyWindow();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		execQuery();
	}

	private void initControl() {
		this.tblMain.checkParsed();
		this.tblMain.getHead().getRow(0).getCell(1).setValue("����/������ͬ���");
		this.tblMain.getColumn("room").setMergeable(true);
		
		
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionPre.setVisible(false);
		
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionSubmit.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.btnSelect.setVisible(true);
		this.btnSelect.setEnabled(true);
		this.btnUnSelect.setVisible(true);
		this.btnUnSelect.setEnabled(true);
		
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("isSelected").getStyleAttributes().setLocked(false);
	}

	private void execQuery() throws BOSException {
		String tenId = (String) this.getUIContext().get("tenId");
		if(tenId == null){
			logger.error("error impossible...");
			this.abort();
		}
		
		this.tblMain.removeRows(false);
		EntityViewInfo evi = new EntityViewInfo();
		
		evi.getSelector().add("*");
		evi.getSelector().add("roomPayList.*");
		evi.getSelector().add("roomPayList.moneyDefine.name");
		evi.getSelector().add("roomPayList.moneyDefine.number");
		evi.getSelector().add("roomPayList.moneyDefine.moneyType");
		evi.getSelector().add("roomPayList.moneyDefine.sysType");
		evi.getSelector().add("roomPayList.moneyDefine.isEnabled");
		
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy", tenId));
		evi.setFilter(filterInfo);

		evi.getSorter().add(new SorterItemInfo("roomPayList.leaseSeq"));
		evi.getSorter().add(new SorterItemInfo("roomLongNum"));
		
		TenancyRoomEntryCollection csCol = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(evi);
	
		for (int i = 0; i < csCol.size(); i++) {
			TenancyRoomEntryInfo roomEntryInfo = csCol.get(i);
			for (int m = 0; m < roomEntryInfo.getRoomPayList().size(); m++) {
				TenancyRoomPayListEntryInfo payInfo = roomEntryInfo.getRoomPayList().get(m);
				if (payInfo == null) {
					return;
				}
				IRow row = this.tblMain.addRow();
				row.getCell("isSelected").setValue(Boolean.FALSE);
				row.getCell("leaseSeq").setValue("" + payInfo.getLeaseSeq());
				row.getCell("moneyDefine").setValue(payInfo.getMoneyDefine().getName());
				row.getCell("room").setValue(roomEntryInfo.getRoomLongNum());
				row.getCell("strartDate").setValue(payInfo.getStartDate());
				row.getCell("endDate").setValue(payInfo.getEndDate());
				row.getCell("appAmount").setValue(payInfo.getAppAmount());
				row.getCell("paidAmount").setValue(payInfo.getAllRemainAmount());
				row.getCell("strartDate").setUserObject(payInfo);
				row.getCell("endDate").setUserObject(roomEntryInfo);
				setLockActRevAmount(payInfo,row);
			}
			
		}
		
		
		evi = new EntityViewInfo();
		SelectorItemCollection sic = evi.getSelector();
		sic.add("*");
		sic.add("otherBill.*");
		sic.add("moneyDefine.name");
		sic.add("moneyDefine.number");
		sic.add("moneyDefine.id");
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",tenId));
		evi.setFilter(filter);
		
		SorterItemCollection  sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("leaseSeq"));
		sorter.add(new SorterItemInfo("otherBill.number"));
		evi.setSorter(sorter);
		
		TenBillOtherPayCollection otherPayCols = TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayCollection(evi);
		
		int currRowIndex = this.tblMain.getRowCount()-1;
	    int size = otherPayCols.size();
	    for(int i =0 ;i<size;i++){
	    	TenBillOtherPayInfo payInfo= otherPayCols.get(i);
	    	
	    	IRow row = this.tblMain.addRow();
			row.getCell("isSelected").setValue(Boolean.FALSE);
			row.getCell("leaseSeq").setValue("" + payInfo.getLeaseSeq());
			row.getCell("moneyDefine").setValue(payInfo.getMoneyDefine().getName());
			if(payInfo.getOtherBill()!=null)row.getCell("room").setValue(payInfo.getOtherBill().getNumber());
			row.getCell("strartDate").setValue(payInfo.getStartDate());
			row.getCell("endDate").setValue(payInfo.getEndDate());
			row.getCell("appAmount").setValue(payInfo.getAppAmount());
			row.getCell("paidAmount").setValue(payInfo.getAllRemainAmount());
			row.getCell("strartDate").setUserObject(payInfo);
			row.getCell("endDate").setUserObject(payInfo);
			setLockActRevAmount(payInfo,row);
	    	
	    }
	    
	    this.tblMain.getMergeManager().mergeBlock(0, 0, this.tblMain.getRowCount() - 1, 1, KDTMergeManager.FREE_MERGE);
		
		
	}

	
	private void setLockActRevAmount(IRevListInfo revListInfo,IRow row)
	{
		if(!revListInfo.isIsCanRevBeyond()){
			BigDecimal remainAppAmount = CRMHelper.getBigDecimal(revListInfo.getFinalAppAmount());
			BigDecimal allRemainAMount = CRMHelper.getBigDecimal(revListInfo.getAllRemainAmount());
			if(allRemainAMount.compareTo(remainAppAmount) >= 0)	{
				for(int i=0;i<tblMain.getColumnCount();i++){
					if(!tblMain.getColumnKey(i).equals("leaseSeq")&&!tblMain.getColumnKey(i).equals("room")){
						row.getCell(i).getStyleAttributes().setBackground(KEY_LOCKED_ROW);
					}
				}
//				row.getStyleAttributes().setLocked(true);
				row.getCell("isSelected").getStyleAttributes().setLocked(true);
			}
		}
		if(revListInfo instanceof TenBillOtherPayInfo){
			if(((TenBillOtherPayInfo)revListInfo).isIsUnPay()){
				for(int i=0;i<tblMain.getColumnCount();i++){
					if(!tblMain.getColumnKey(i).equals("leaseSeq")&&!tblMain.getColumnKey(i).equals("room")){
						row.getCell(i).getStyleAttributes().setBackground(Color.CYAN);
					}
				}
//				row.getStyleAttributes().setLocked(true);
				row.getCell("isSelected").getStyleAttributes().setLocked(true);
			}
		}else if(revListInfo instanceof TenancyRoomPayListEntryInfo){
			if(((TenancyRoomPayListEntryInfo)revListInfo).isIsUnPay()){
				for(int i=0;i<tblMain.getColumnCount();i++){
					if(!tblMain.getColumnKey(i).equals("leaseSeq")&&!tblMain.getColumnKey(i).equals("room")){
						row.getCell(i).getStyleAttributes().setBackground(Color.CYAN);
					}
				}
//				row.getStyleAttributes().setLocked(true);
				row.getCell("isSelected").getStyleAttributes().setLocked(true);
			}
		}
	}
	private static final Logger logger = CoreUIObject.getLogger(F7SelectRentMoneyDefineListUI.class);

	public F7SelectRentMoneyDefineListUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void checkIsOUSealUp() throws Exception {
	}
	
	protected IObjectValue createNewData() {
		return null;
	}

}