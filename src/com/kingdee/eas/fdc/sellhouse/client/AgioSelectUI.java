/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioBillCollection;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public class AgioSelectUI extends AbstractAgioSelectUI {
	private static final Logger logger = CoreUIObject
			.getLogger(AgioSelectUI.class);

	public boolean isCanceled = false;
	public AgioSelectUI() throws Exception {
		super();
	}

	protected void chkIsBasePriceSell_actionPerformed(ActionEvent e)
			throws Exception {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("baseBuildingPrice");
		selector.add("isBasePriceAudited");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomID), selector);
		if(roomInfo != null){
			if(roomInfo.getBaseBuildingPrice() == null || "".equals(roomInfo.getBaseBuildingPrice())){
				this.chkIsBasePriceSell.setSelected(false);
				MsgBox.showWarning(this," �÷���׼۲����ڣ�");
				return;
			}else{
				if(!roomInfo.isIsBasePriceAudited()){
					this.chkIsBasePriceSell.setSelected(false);
					MsgBox.showWarning(this,"�÷���׼�û�б����ˣ�");
					return;
				}
			}
		}
		super.chkIsBasePriceSell_actionPerformed(e);
	}
	
	public void actionYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		boolean isVirtual=((Boolean)this.getUIContext().get("isVirtual")).booleanValue();
		boolean isAttach=((Boolean)this.getUIContext().get("isAttach")).booleanValue();
		boolean existDJ=false;
		boolean existZJ=false;
		IObjectValue objectValue = (IObjectValue)this.getUIContext().get("objectValue");
		
		AgioEntryCollection agios = new AgioEntryCollection();
		int agioCount = 0;
		for (int i = 0; i < this.tblSelect.getRowCount(); i++) {
			IRow row = this.tblSelect.getRow(i);
			if (row.getCell("select").getValue() != null) {
				boolean isSelected = ((Boolean) row.getCell("select").getValue()).booleanValue();
				if (isSelected) {
					AgioBillInfo agio = (AgioBillInfo) row.getCell("agioName").getUserObject();
					BigDecimal pro = (BigDecimal) row.getCell("pro").getValue();
					BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
					OperateEnum operate=(OperateEnum)row.getCell("operate").getValue();
					if (agio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
						if (pro.compareTo(agio.getPro()) < 0) {
							FDCMsgBox.showWarning(this,"��" + (i + 1)	+ "�� ����Ĵ��۰ٷֱȳ����ۿ۷����Ĵ��۰ٷֱȷ�Χ"+agio.getPro().setScale(2)+"!");
							return;
						}
					}
					if (agio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						if (pro.compareTo(agio.getPro()) > 0) {
							FDCMsgBox.showWarning(this,"��" + (i + 1) + "�� ����ļ��㳬���ۿ۷����ļ��㷶Χ0~"+agio.getPro().setScale(2)+"!");
							return;
						}
					}
					if (agio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						if (amount.compareTo(agio.getAmount()) > 0) {
							FDCMsgBox.showWarning(this,"��" + (i + 1)+ "�� ������ܼ��Żݳ����ۿ۷������ܼ��Żݷ�Χ0~"+agio.getAmount().setScale(2)+"!");
							return;
						}
					}
					if (agio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
						existDJ=true;
						if(isVirtual && PriceAccountTypeEnum.StandSetPrice.equals(this.comboPriceAccount.getSelectedItem()))
						{
							FDCMsgBox.showWarning(this,"�����������ǰ���׼�ܼ�ʱ������ѡ�񵥼��Ż��ۿ�����!");
							return;
						}
						if(isAttach && PriceAccountTypeEnum.StandSetPrice.equals(this.comboPriceAccount.getSelectedItem())){
							
							FDCMsgBox.showWarning(this,"�����䶨�������ǰ���׼�ܼ�ʱ������ѡ�񵥼��Ż��ۿ�����!");
							return;
						}
						if(PriceAccountTypeEnum.StandSetPrice.equals(this.comboPriceAccount.getSelectedItem()))
						{
							FDCMsgBox.showWarning(this,"���Ƽ۷�ʽ�ǰ���׼�ܼ�ʱ������ѡ�񵥼��Ż��ۿ�����!");
							return;
						}
					
						if (amount.compareTo(agio.getAmount()) > 0) {
							FDCMsgBox.showWarning(this,"��" + (i + 1)	+ "�� ����ĵ����Żݳ����ۿ۷����ĵ����Żݷ�Χ0~"+agio.getAmount().setScale(2)+"!");
							return;
						}
					}
					if(agio.getCalType().equals(AgioCalTypeEnum.ZongJia)){
						existZJ=true;
					}
					if (agio.getStartDate() != null
							&& agio.getStartDate().after(
									DateTimeUtils.truncateDate(new Date()))) {
						FDCMsgBox.showWarning(this,"��" + (i + 1) + "�� �ۿ�û�е���Ч����!");
						return;
					}
					if (agio.getCancelDate() != null
							&& !agio.getCancelDate().after(
									DateTimeUtils.truncateDate(new Date()))) {
						FDCMsgBox.showWarning(this,"��" + (i + 1) + "�� �ۿ��Ѿ���ʧЧ����!");
						return;
					}
					
					
					AgioEntryInfo entry = null;
					if(objectValue instanceof PrePurchaseManageInfo){
						entry= new PrePurchaseAgioEntryInfo();
					}
					if(objectValue instanceof PurchaseManageInfo){
						entry= new PurAgioEntryInfo();
					}
					if(objectValue instanceof SignManageInfo){
						entry= new SignAgioEntryInfo();
					}
					if(objectValue instanceof ChangeManageInfo){
						entry = new ChangeAgioEntryInfo(); 
					}
					if(objectValue instanceof SpecialDiscountInfo){
						entry = new SpecialDiscountAgioEntryInfo(); 
					}
					if(entry == null){
						entry = new SignAgioEntryInfo(); 
					}
					entry.setAgio(agio);
					entry.setPro(pro);
					entry.setAmount(amount);
					entry.setSeq(agioCount);
					entry.setOperate(operate);
					entry = (AgioEntryInfo)entry;
					agios.add(entry);
					
					agioCount++;
				}
			}
		}		
		if(existDJ&&existZJ){
			FDCMsgBox.showWarning(this,"�ܼ��Ż��뵥���Ż��ۿ����Ͳ���ͬʱѡ��!");
			return;
		}
		AgioParam agioParam = new AgioParam();
		agioParam.setAgios(agios);
		agioParam.setPriceAccountType((PriceAccountTypeEnum)this.comboPriceAccount.getSelectedItem());
		agioParam.setToInteger(this.chkIsAutoToInteger.isSelected());
		agioParam.setBasePriceSell(this.chkIsBasePriceSell.isSelected());
		agioParam.setToIntegerType((ToIntegerTypeEnum) this.comboToIntegerType.getSelectedItem());
		agioParam.setDigit((DigitEnum) this.comboDigit.getSelectedItem());
		agioParam.setSpecialAgio((SpecialDiscountInfo) this.prmtSpecialAgio.getValue());
		this.getUIContext().put("agioParam", agioParam);
		isCanceled=false;
		this.destroyWindow();
	}
	public Object[] getReturnValue()
	{
		IObjectValue objectValue = (IObjectValue)this.getUIContext().get("objectValue");
		
		AgioEntryCollection agios = new AgioEntryCollection();
		int agioCount = 0;
		for (int i = 0; i < this.tblSelect.getRowCount(); i++) {
			IRow row = this.tblSelect.getRow(i);
			if (row.getCell("select").getValue() != null) {
				boolean isSelected = ((Boolean) row.getCell("select").getValue()).booleanValue();
				if (isSelected) {
					AgioBillInfo agio = (AgioBillInfo) row.getCell("agioName").getUserObject();
					BigDecimal pro = (BigDecimal) row.getCell("pro").getValue();
					BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
					
					AgioEntryInfo entry = null;
					if(objectValue instanceof PrePurchaseManageInfo){
						entry= new PrePurchaseAgioEntryInfo();
					}
					if(objectValue instanceof PurchaseManageInfo){
						entry= new PurAgioEntryInfo();
					}
					if(objectValue instanceof SignManageInfo){
						entry= new SignAgioEntryInfo();
					}
					if(objectValue instanceof ChangeManageInfo){
						entry = new ChangeAgioEntryInfo(); 
					}
					if(entry == null){
						entry = new SignAgioEntryInfo(); 
					}
					entry.setAgio(agio);
					entry.setPro(pro);
					entry.setAmount(amount);
					entry.setSeq(agioCount);
					entry = (AgioEntryInfo)entry;
					agios.add(entry);
					
					agioCount++;
				}
			}
		}		
		AgioParam agioParam = new AgioParam();
		agioParam.setAgios(agios);
		agioParam.setPriceAccountType((PriceAccountTypeEnum)this.comboPriceAccount.getSelectedItem());
		agioParam.setToInteger(this.chkIsAutoToInteger.isSelected());
		agioParam.setBasePriceSell(this.chkIsBasePriceSell.isSelected());
		agioParam.setToIntegerType((ToIntegerTypeEnum) this.comboToIntegerType.getSelectedItem());
		agioParam.setDigit((DigitEnum) this.comboDigit.getSelectedItem());
		agioParam.setSpecialAgio((SpecialDiscountInfo) this.prmtSpecialAgio.getValue());
		this.getUIContext().put("agioParam", agioParam);
		
		return agios.toArray();
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	public void actionNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.getUIContext().put("agioParam", null);
		isCanceled=true;
		this.destroyWindow();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		tblSelect.checkParsed();
		tblSelect.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		tblSelect.getColumn("isEspecial").getStyleAttributes().setHided(true);
		tblSelect.getColumn("pro").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblSelect.getColumn("pro").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblSelect.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblSelect.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		String roomId = (String) this.getUIContext().get("roomId");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("building.sellProject.*");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)), sels);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("project.id", roomInfo.getBuilding().getSellProject().getId()));
		filter.getFilterItems().add(new FilterItemInfo("startDate",new Date(), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("startDate", null, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate",  null,CompareType.EQUALS));

		
		IObjectValue objectValue = (IObjectValue)this.getUIContext().get("objectValue");
		
		SHEPayTypeInfo pt=null;
		if(objectValue instanceof SignManageInfo){
			pt=((SignManageInfo)objectValue).getPayType();
		}else if(objectValue instanceof PurchaseManageInfo){
			pt=((PurchaseManageInfo)objectValue).getPayType();
		}
		if(pt!=null){
			filter.getFilterItems().add(new FilterItemInfo("payType", pt.getId().toString(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("payType", null,CompareType.EQUALS));
			filter.setMaskString("#0 and #1 and #2 and (#3 or #4) and (#5 or #6) and (#7 or #8)");
		}else{
			filter.setMaskString("#0 and #1 and #2 and (#3 or #4) and (#5 or #6) ");
		}
		
		SelectorItemCollection agiosel=new SelectorItemCollection();
		agiosel.add("*");
		agiosel.add("roomEntry.*");
		view.setSelector(agiosel);
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo sortItem=new SorterItemInfo("calType"); 
		sortItem.setSortType(SortType.DESCEND);
		sort.add(sortItem);
		view.setSorter(sort);
		AgioBillCollection agios = AgioBillFactory.getRemoteInstance().getAgioBillCollection(view);
		
		boolean isAgioType=false;
		if(agios.size()>0&&agios.get(0).getAgioType()!=null&&agios.get(0).getAgioType().equals(AgioTypeEnum.XCHJ)){
			isAgioType=true;
			this.btnUp.setVisible(false);
			this.btnDown.setVisible(false);
		}
		CRMHelper.sortCollection(agios, "calType", isAgioType);
		
//		AgioBillCollection seqAgios = new AgioBillCollection();		
//		Map hasMap = new HashMap();		
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.getUIContext().get("agios");
//		for (int j = 0; j < agioEntrys.size(); j++) {
//			AgioEntryInfo entry = agioEntrys.get(j);
//			if(entry.getAgio()==null) continue;
//			seqAgios.add(entry.getAgio());
//			hasMap.put(entry.getAgio().getId().toString(), entry.getAgio());
//		}
//		
//		for (int i = 0; i < agios.size(); i++) {
//			if (hasMap.containsKey(agios.get(i).getId().toString())) {
//				continue;
//			}
//			seqAgios.add(agios.get(i));
//		}
		
		CRMHelper.sortCollection(agios, "seq", true);
		for (int i = 0; i < agios.size(); i++) {
			AgioBillInfo agio = agios.get(i);
			AgioRoomEntryCollection roomEntrys = agio.getRoomEntry();
			if (roomEntrys.size() != 0) {
				boolean isIncluded = false;
				for (int j = 0; j < roomEntrys.size(); j++) {
					AgioRoomEntryInfo entry = roomEntrys.get(j);
					if (entry.getRoom() != null
							&& entry.getRoom().getId().toString()
									.equals(roomId)) {
						isIncluded = true;
					}
				}
				if (!isIncluded) {
					continue;
				}
			}
			IRow row = this.tblSelect.addRow();
			row.getCell("agioName").setValue(agio.getName());
			row.getCell("agioName").setUserObject(agio);
			row.getCell("select").setValue(Boolean.FALSE);
			row.getCell("calType").setValue(agio.getCalType());
			row.getCell("pro").setValue(agio.getPro());
			row.getCell("amount").setValue(agio.getAmount());
			row.getCell("startDate").setValue(agio.getStartDate());
			row.getCell("cancelDate").setValue(agio.getCancelDate());
			row.getCell("des").setValue(agio.getDescription());
			row.getCell("operate").setValue(OperateEnum.MULTIPLY);
			for (int j = 0; j < agioEntrys.size(); j++) {
				AgioEntryInfo agioEntry = agioEntrys.get(j);
				if(agioEntry.getAgio()==null) continue;
				String agioId = agioEntry.getAgio().getId().toString();
				if (agio.getId().toString().equals(agioId)) {
					row.getCell("select").setValue(Boolean.TRUE);
					row.getCell("pro").setValue(agioEntry.getPro());
					row.getCell("amount").setValue(agioEntry.getAmount());
					row.getCell("operate").setValue(agioEntry.getOperate());
				}
			}
		}

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		tblSelect.getColumn("pro").setEditor(numberEditor);
		tblSelect.getColumn("amount").setEditor(numberEditor);
		tblSelect.getColumn("pro").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblSelect.getColumn("pro").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblSelect.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblSelect.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		for (int i = 0; i < this.tblSelect.getRowCount(); i++) {
			IRow row = this.tblSelect.getRow(i);
			AgioBillInfo agio = (AgioBillInfo) row.getCell("agioName").getUserObject();
			Boolean isSelected = (Boolean) row.getCell("select").getValue();
			if (isSelected.booleanValue()&&agio.getCalType()!=null) {
				if (agio.getCalType().equals(AgioCalTypeEnum.Dazhe)
						|| agio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
					row.getCell("pro").getStyleAttributes().setLocked(false);
					row.getCell("amount").getStyleAttributes().setLocked(true);
				} else {
					row.getCell("pro").getStyleAttributes().setLocked(true);
					row.getCell("amount").getStyleAttributes().setLocked(false);
				}
			}
		}
		
		AgioParam curAgioParam = (AgioParam)this.getUIContext().get("agioParam");
		if(curAgioParam==null) curAgioParam = new AgioParam();
		
		this.comboPriceAccount.setSelectedItem(curAgioParam.getPriceAccountType());
		this.chkIsAutoToInteger.setSelected(curAgioParam.isToInteger());
		this.chkIsBasePriceSell.setSelected(curAgioParam.isBasePriceSell());
		this.comboToIntegerType.setSelectedItem(curAgioParam.getToIntegerType());
		this.comboDigit.setSelectedItem(curAgioParam.getDigit());
		this.prmtSpecialAgio.setValue(curAgioParam.getSpecialAgio());
		
		if(objectValue==null||!(objectValue instanceof SignManageInfo)){
			this.prmtSpecialAgio.setVisible(false);
			this.contSpecialAgio.setVisible(false);
			this.btnRelate.setVisible(false);
		}else{
			view = new EntityViewInfo();
			filter = new FilterInfo();
			
			Set customer=new HashSet();
			for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
				customer.add(((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer().getId().toString());
			}
			filter.getFilterItems().add(new FilterItemInfo("customer.id", customer,CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("entrys.room.id", roomInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("bizState", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", roomInfo.getBuilding().getSellProject().getId()));
			filter.getFilterItems().add(new FilterItemInfo("endDate", new Date(), CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("endDate",  null,CompareType.EQUALS));
			filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5) ");
			view.setFilter(filter);
			this.prmtSpecialAgio.setEntityViewInfo(view);
		}
		
		tblSelect.getStyleAttributes().setLocked(true);
	}
	protected void btnUp_actionPerformed(ActionEvent e) throws Exception {
		super.btnUp_actionPerformed(e);
		int i = this.tblSelect.getSelectManager().getActiveRowIndex();
		int j = this.tblSelect.getSelectManager().getActiveColumnIndex();
		if (i == -1) {
			return;
		}
		if (i == 0) {
			return;
		}
		IRow row = this.tblSelect.removeRow(i);
		this.tblSelect.addRow(i - 1, row);
		this.tblSelect.getSelectManager().select(i - 1, j);
	}

	protected void btnDown_actionPerformed(ActionEvent e) throws Exception {
		super.btnDown_actionPerformed(e);
		int i = this.tblSelect.getSelectManager().getActiveRowIndex();
		int j = this.tblSelect.getSelectManager().getActiveColumnIndex();
		if (i == -1) {
			return;
		}
		if (i == this.tblSelect.getRowCount() - 1) {
			return;
		}
		IRow row = this.tblSelect.removeRow(i);
		this.tblSelect.addRow(i + 1, row);
		this.tblSelect.getSelectManager().select(i + 1, j);
	}
	
	public static IObjectCollection showUI(CoreUI ui, String roomId,
			AgioEntryCollection agios,IObjectValue objectValue,boolean isAgioListCanEdit) throws UIException {
		AgioParam agioParam = showUI(ui, roomId, agios, null,objectValue,isAgioListCanEdit,false);
		if(agioParam == null) return null;
		return agioParam.getAgios();
	}
	
	//���ڵͼ����۸�ѡ��Ҫ���������������� showUI����
	private static String roomID = "";
	/**
	 * @param objectValue	���ݴ����Ķ����ж�
	 */
	public static AgioParam showUI(CoreUI ui, String roomId,
			AgioEntryCollection agios, AgioParam agioParam, IObjectValue objectValue,boolean isAgioListCanEdit,boolean isVirtual) throws UIException {
		UIContext uiContext = new UIContext(ui);
		roomID = roomId;
		uiContext.put("roomId", roomId);
		uiContext.put("agios", agios);
		uiContext.put("agioParam", agioParam);
		uiContext.put("objectValue", objectValue);
		uiContext.put("isAgioListCanEdit", new Boolean(isAgioListCanEdit));
		uiContext.put("isVirtual", new Boolean(isVirtual));
		uiContext.put("isAttach", new Boolean(false));
		
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(AgioSelectUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
		AgioParam retAgioParam = (AgioParam) uiWindow.getUIObject().getUIContext().get("agioParam");
		return retAgioParam;
	}
	

	protected void tblSelect_tableClicked(KDTMouseEvent e) throws Exception {
		boolean isAgioListCanEdit=((Boolean)this.getUIContext().get("isAgioListCanEdit")).booleanValue();
//		if(chkIsBasePriceSell.isSelected()){
//			return;
//		}
		if(prmtSpecialAgio.getValue()!=null){
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			if(rowIndex==-1){
				return;
			}
			if(colIndex==-1){
				return;
			}
			if (this.tblSelect.getColumnKey(colIndex).equals("select")) {
				IRow row = tblSelect.getRow(rowIndex);
				if (row == null) {
					return;
				}
				boolean isSelect = ((Boolean) row.getCell("select").getValue()).booleanValue();
				row.getCell("select").setValue(new Boolean(!isSelect));
				AgioBillInfo agio = (AgioBillInfo) row.getCell("agioName").getUserObject();
				if (!isSelect) {
					if(!isAgioListCanEdit){
						this.tblSelect.getColumn("pro").getStyleAttributes().setLocked(true);
						this.tblSelect.getColumn("amount").getStyleAttributes().setLocked(true);
					}else{
						if (agio.getCalType().equals(AgioCalTypeEnum.Dazhe)
								|| agio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
							row.getCell("pro").getStyleAttributes().setLocked(false);
							row.getCell("amount").getStyleAttributes().setLocked(true);
						} else {
							row.getCell("pro").getStyleAttributes().setLocked(true);
							row.getCell("amount").getStyleAttributes().setLocked(false);

						}
					}
				} else {
					row.getCell("pro").getStyleAttributes().setLocked(true);
					row.getCell("amount").getStyleAttributes().setLocked(true);
					row.getCell("pro").setValue(agio.getPro());
					row.getCell("amount").setValue(agio.getAmount());
				}
			}
		}
	}

	protected void prmtSpecialAgio_dataChanged(DataChangeEvent e) throws Exception {
		if(prmtSpecialAgio.getValue()==null){
			this.tblSelect.getColumn("select").getStyleAttributes().setLocked(false);
		}else{
			this.tblSelect.getColumn("select").getStyleAttributes().setLocked(true);
			for(int i=0;i<this.tblSelect.getRowCount();i++){
				IRow row = tblSelect.getRow(i);
				row.getCell("select").setValue(Boolean.FALSE);
			}
		}
	}

	protected void btnRelate_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtSpecialAgio.getValue()!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", ((SpecialDiscountInfo)this.prmtSpecialAgio.getValue()).getId());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SpecialDiscountEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"����ѡ�������ۿ۵���");
		}
	}
	
}