/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenPriceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenPriceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenPriceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyPriceEntryInfo;

/**
 * output class name
 */
public class SelectedTenPriceUI extends AbstractSelectedTenPriceUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SelectedTenPriceUI.class);

	/**
	 * output class constructor
	 */
	public SelectedTenPriceUI() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		Map uiContext = (Map) getUIContext();
		Set<String> selected = (Set<String>) uiContext.get("selected");
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("sellProject");
		sic.add("roomName");
		sic.add("area");
		sic.add("dayPrice");
		sic.add("monthPrice");
		sic.add("yearPrice");
		sic.add("maxFreeDay");
		sic.add("maxLease");
		sic.add("deposit");
		sic.add("parent.*");
		sic.add("state");
		sic.add("description");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("state","1"));
		filter.getFilterItems().add(new FilterItemInfo("parent.orgUnit.id",SysContext.getSysContext().getCurrentFIUnit().getId()+""));
		view.setFilter(filter);
		
		TenPriceEntryCollection cols = TenPriceEntryFactory.getRemoteInstance().getTenPriceEntryCollection(view);
		this.kDTable1.checkParsed();
		int size  = cols.size();
		if(size>0)
			this.kDTable1.removeRows();
		TenPriceEntryInfo entry = null;
		IRow r = null;
		String key = null;
		boolean isSelected = false;
		for(int i=0;i<size;i++){
			isSelected = false;
			r = this.kDTable1.addRow();
			entry = cols.get(i);
			key = entry.getId()+"";
			isSelected=	selected.contains(key);
			fillDataToRow(entry, r, isSelected);
			
		}
		
		
	}

	private void fillDataToRow(TenPriceEntryInfo entry, IRow r,
			boolean isSelected) {
		
		if(r.getCell("iSelected") != null)
		  r.getCell("iSelected").setValue(isSelected);
		r.getCell("sellProject").setValue(entry.getSellProject());
		r.getCell("roomName").setValue(entry.getRoomName());
		r.getCell("area").setValue(entry.getArea());
		r.getCell("dayPrice").setValue(entry.getDayPrice());
		r.getCell("monthPrice").setValue(entry.getMonthPrice());
		r.getCell("yearPrice").setValue(entry.getYearPrice());
		r.getCell("maxFreeDay").setValue(entry.getMaxFreeDay());
		r.getCell("maxLease").setValue(entry.getMaxLease());
		if(r.getCell("depoist") != null)
		  r.getCell("depoist").setValue(entry.getDeposit());//deposit
		if(r.getCell("deposit") != null)
		  r.getCell("deposit").setValue(entry.getDeposit());
		r.getCell("id").setValue(entry.getId());
		r.getCell("id").setUserObject(entry);
		if(r.getCell("description") != null)
			  r.getCell("description").setValue(entry.getDescription());
	}

	/**
	 * output actionSelected_actionPerformed
	 */
	public void actionSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionSelected_actionPerformed(e);
		Map uiContext = this.getUIContext();
		TenancyBillEditUI ui = (TenancyBillEditUI) uiContext.get("parentUI");
		TenPriceEntryCollection cols = new TenPriceEntryCollection();
		int size = this.kDTable1.getRowCount();
		IRow r = null;
		boolean isSelected = false;
		
		for(int i=0;i<size;i++){
			r = this.kDTable1.getRow(i);
			isSelected = Boolean.valueOf(r.getCell("iSelected").getValue()+"");
			if(isSelected){
				cols.add((TenPriceEntryInfo) r.getCell("id").getUserObject());
			}
		}
		
		IRow billRow = null;
		size = cols.size();
		if(size>0)
			ui.tblTenPrice.removeRows();
		
		for(int i=0;i<size;i++){
			billRow = ui.tblTenPrice.addRow();
			TenancyPriceEntryInfo entry = new TenancyPriceEntryInfo();
			TenPriceEntryInfo price = cols.get(i);
			entry.setTenPrice(price);
			entry.setParent(ui.editData);
			billRow.setUserObject(entry);
			fillDataToRow(price, billRow, false);
		}
		this.destroyWindow();
		
		
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
		this.destroyWindow();
	}
	
	@Override
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		Map uiContext = (Map) getUIContext();
		Set<String> selected = (Set<String>) uiContext.get("selected");
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("sellProject");
		sic.add("roomName");
		sic.add("area");
		sic.add("dayPrice");
		sic.add("monthPrice");
		sic.add("yearPrice");
		sic.add("maxFreeDay");
		sic.add("maxLease");
		sic.add("deposit");
		sic.add("parent.*");
		sic.add("state");
		sic.add("description");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("state","1"));
		String sellProject = this.txtProjectName.getText();
		if(!StringUtils.isEmpty(sellProject)){
			filter.getFilterItems().add(new FilterItemInfo("sellProject","%"+sellProject+"%",CompareType.LIKE));
		}
		String roomNumber = this.txtRoomNumber.getText();
		if(!StringUtils.isEmpty(roomNumber)){
			filter.getFilterItems().add(new FilterItemInfo("roomName","%"+roomNumber+"%",CompareType.LIKE));
		}
		filter.getFilterItems().add(new FilterItemInfo("parent.orgUnit.id",SysContext.getSysContext().getCurrentFIUnit().getId()+""));
		view.setFilter(filter);
		
		TenPriceEntryCollection cols = TenPriceEntryFactory.getRemoteInstance().getTenPriceEntryCollection(view);
		this.kDTable1.checkParsed();
		int size  = cols.size();
		if(size>0)
			this.kDTable1.removeRows();
		TenPriceEntryInfo entry = null;
		IRow r = null;
		String key = null;
		boolean isSelected = false;
		for(int i=0;i<size;i++){
			isSelected = false;
			r = this.kDTable1.addRow();
			entry = cols.get(i);
			key = entry.getId()+"";
			isSelected=	selected.contains(key);
			fillDataToRow(entry, r, isSelected);
			
		}
	}

}