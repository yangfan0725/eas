/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.SelectRevListUI;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysFactory;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class InvoiceBillEditUI extends AbstractInvoiceBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvoiceBillEditUI.class);
    public InvoiceBillEditUI() throws Exception
    {
        super();
    }
    public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		try {
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				IRow row=this.kdtEntry.getRow(i);
				InvoiceBillEntryInfo entry=(InvoiceBillEntryInfo) row.getUserObject();
			
				IRevListInfo revListInfo = TENRevHelper.getRevListInfo(entry.getRevListType(),entry.getRevListId());
				
				Map date=getDate(entry.getRevListId());
				CRMClientHelper.setColValue(row, "moneyDefine", revListInfo.getMoneyDefine());
				CRMClientHelper.setColValue(row, "appDate", revListInfo.getAppDate());
				CRMClientHelper.setColValue(row, "startDate", date.get("startDate"));
				CRMClientHelper.setColValue(row, "endDate", date.get("endDate"));
				CRMClientHelper.setColValue(row, "appAmount", revListInfo.getAppAmount());
				CRMClientHelper.setColValue(row, "actRevAmount", revListInfo.getActRevAmount());
				CRMClientHelper.setColValue(row, "invoiceAmount", revListInfo.getInvoiceAmount());
				
				
				BigDecimal rate=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(revListInfo.getMoneyDefine().getId())).getRate();
				if(this.editData.getSourceBillId()!=null){
					InvoiceBatchImportEntryInfo entryImport=(InvoiceBatchImportEntryInfo) InvoiceBatchImportEntryFactory.getRemoteInstance().getCoreBillEntryBaseInfo(new ObjectUuidPK(this.editData.getSourceBillId()));
					rate=entryImport.getTaxRate();
				}
				CRMClientHelper.setColValue(row, "rate", rate);
				BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(rate, new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
				CRMClientHelper.setColValue(row, "amountNoTax", FDCHelper.divide(row.getCell("amount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
			
				CRMClientHelper.setColValue(row, "appAmountNoTax", FDCHelper.divide(row.getCell("appAmount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
				CRMClientHelper.setColValue(row, "actRevAmountNoTax", FDCHelper.divide(row.getCell("actRevAmount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
			}
			TenancyCustomerEntryCollection col=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection("select fdcCustomer.id from where tenancyBill.id='"+this.editData.getTenancyBill().getId().toString()+"'");
			Set id=new HashSet();
			for(int i=0;i<col.size();i++){
				id.add(col.get(i).getFdcCustomer().getId().toString());
			}
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
			view.setFilter(filter);
			this.prmtCustomer.setEntityViewInfo(view);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		setOprtState(this.oprtState);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
    public Map getDate(String id) throws BOSException, EASBizException {
    	Map map=new HashMap();
		if(BOSUuid.read(id).getType().toString().equals("31D11A7E")){
			TenancyRoomPayListEntryInfo info=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(id));
			map.put("startDate", info.getStartDate());
			map.put("endDate", info.getEndDate());
		}else{
			TenBillOtherPayInfo info=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayInfo(new ObjectUuidPK(id));
			map.put("startDate", info.getStartDate());
			map.put("endDate", info.getEndDate());
		}
		return map;
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
		return InvoiceBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		InvoiceBillInfo info=new InvoiceBillInfo();
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setTenancyBill(ten);
		try {
			TenancyCustomerEntryCollection col=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection("select fdcCustomer.* from where tenancyBill.id='"+ten.getId().toString()+"'");
			info.setFdcCustomer(col.get(0).getFdcCustomer());
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		info.setOrgUnit(ten.getOrgUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("orgUnit.*");
		sels.add("fiVouchered");
		
		sels.add("entry.revListType");
		sels.add("sourceBillId");
		return sels;
	}
	public void onLoad() throws Exception {
		this.prmtCustomer.setEnabledMultiSelection(false);
		
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		this.actionRemoveLine.setVisible(true);
		
		this.btnSubmitAudit.setIcon(EASResource.getIcon("imgTbtn_suitbest"));
		 
		KDWorkButton btnSelect = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		
		this.actionSelect.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnSelect = (KDWorkButton)contEntry.add(this.actionSelect);
		btnSelect.setText("������");
		btnSelect.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("amount").setEditor(numberEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("rate").setEditor(numberEditor);
		this.kdtEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("rate").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amountNoTax").setEditor(numberEditor);
		this.kdtEntry.getColumn("amountNoTax").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amountNoTax").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("appAmountNoTax").setEditor(numberEditor);
		this.kdtEntry.getColumn("appAmountNoTax").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("appAmountNoTax").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("actRevAmountNoTax").setEditor(numberEditor);
		this.kdtEntry.getColumn("actRevAmountNoTax").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("actRevAmountNoTax").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amount").setRequired(true);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kdtEntry.getColumn("description").setEditor(txtEditor);
		
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.actionSelect.setEnabled(false);
			
			this.actionSubmitAudit.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.actionSelect.setEnabled(true);
			
			this.actionSubmitAudit.setEnabled(true);
		}
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
	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		
		Map map = new HashMap();
		List paylist = new ArrayList();
		List otherPayList = new ArrayList();
		TenancyBillInfo tenancy =this.editData.getTenancyBill();
		inPutListByTenancyBillandList(tenancy, paylist, otherPayList);
		
		map.put("1tenPayList", paylist);
		map.put("2tenOtherPayList", otherPayList);
		
		uiContext.put(SelectRevListUI.KEY_APP_REV_LIST, map);
		uiContext.put(SelectRevListUI.KEY_MONEYSYSTYPE, MoneySysTypeEnum.TenancySys);
		uiContext.put(SelectRevListUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
		uiContext.put("isInvoice", "true");
		uiContext.put("isGathering", "true");
		uiContext.put("ISBYCUSTOMER", "false");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectRevListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		Map resContext = uiWindow.getUIObject().getUIContext();
		
		if (resContext.get(SelectRevListUI.KEY_RES_OPT) == null ||
				!((Boolean)resContext.get(SelectRevListUI.KEY_RES_OPT)).booleanValue()){
			return;
		}
		this.kdtEntry.removeRows(false);
		List list = (List) resContext.get(SelectRevListUI.KEY_RES_APP_REV_LIST);
		
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0; i<list.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) list.get(i);
			InvoiceBillEntryInfo revEntry = new InvoiceBillEntryInfo();
			revEntry.setRevListId(revListInfo.getId() == null ? null : revListInfo.getId().toString());
			revEntry.setRevListType(revListInfo.getRevListTypeEnum());
			IRow row = this.kdtEntry.addRow();
			row.setUserObject(revEntry);

			CRMClientHelper.setColValue(row, "moneyDefine", revListInfo.getMoneyDefine());
			CRMClientHelper.setColValue(row, "description", revListInfo.getMoneyDefine().getName());
			
			BigDecimal rate=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(revListInfo.getMoneyDefine().getId())).getRate();
			CRMClientHelper.setColValue(row, "rate", rate);
			BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(rate, new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
		
			Map date=getDate(revListInfo.getId().toString());
			
			CRMClientHelper.setColValue(row, "appDate", revListInfo.getAppDate());
			CRMClientHelper.setColValue(row, "startDate", date.get("startDate"));
			CRMClientHelper.setColValue(row, "endDate", date.get("endDate"));
			CRMClientHelper.setColValue(row, "appAmount", revListInfo.getAppAmount());
			CRMClientHelper.setColValue(row, "actRevAmount", revListInfo.getActRevAmount());
			CRMClientHelper.setColValue(row, "invoiceAmount", revListInfo.getInvoiceAmount());
			CRMClientHelper.setColValue(row, "amount", FDCHelper.subtract(revListInfo.getAppAmount(), revListInfo.getInvoiceAmount()));
			
			CRMClientHelper.setColValue(row, "amountNoTax", FDCHelper.divide(FDCHelper.subtract(revListInfo.getAppAmount(), revListInfo.getInvoiceAmount()), div, 2, BigDecimal.ROUND_HALF_UP));
			
			CRMClientHelper.setColValue(row, "appAmountNoTax", FDCHelper.divide(revListInfo.getAppAmount(), div, 2, BigDecimal.ROUND_HALF_UP));
			CRMClientHelper.setColValue(row, "actRevAmountNoTax", FDCHelper.divide(revListInfo.getActRevAmount(), div, 2, BigDecimal.ROUND_HALF_UP));
			
			total=FDCHelper.add(total, FDCHelper.subtract(revListInfo.getAppAmount(), revListInfo.getInvoiceAmount()));
		}
		this.txtAmount.setValue(total);
	}
	protected void inPutListByTenancyBillandList(TenancyBillInfo tenancy,List paylist,List otherPayList) throws EASBizException, BOSException{
		tenancy = TenancyHelper.getTenancyBillInfo(tenancy.getId().toString());
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			TenancyRoomPayListEntryCollection tenRoomPays = tenRoom
					.getRoomPayList();
			for (int j = 0; j < tenRoomPays.size(); j++) {
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPays.get(j);
				tenRoomPay.setTenRoom(tenRoom);
				// by tim_gao �����ͬ
				tenRoomPay.setTenBill(tenancy);
				paylist.add(tenRoomPay);
			}
		}
		TenBillOtherPayCollection tenOtherColl = tenancy.getOtherPayList();
		CRMHelper.sortCollection(tenOtherColl, "leaseSeq", true);
		for (int i = 0; i < tenOtherColl.size(); i++) {
			TenBillOtherPayInfo tenOtherInfo = tenOtherColl.get(i);
			// by tim_gao �����ͬ
			tenOtherInfo.setHead(tenancy);
			otherPayList.add(tenOtherInfo);
		}
	}
	public void actionSubmitAudit_actionPerformed(ActionEvent e) throws Exception {
		actionSubmit_actionPerformed(e);
		actionAudit_actionPerformed(e);
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("amount".equals(this.kdtEntry.getColumnKey(e.getColIndex()))){
			BigDecimal total=FDCHelper.ZERO;
			for(int i=0; i<this.kdtEntry.getRowCount(); i++){
				IRow row = this.kdtEntry.getRow(i);
				total=FDCHelper.add(total, row.getCell("amount").getValue());
			}
			this.txtAmount.setValue(total);
			
			BigDecimal rate=(BigDecimal) this.kdtEntry.getRow(e.getRowIndex()).getCell("rate").getValue();
			BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(rate, new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));

			this.kdtEntry.getRow(e.getRowIndex()).getCell("amountNoTax").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
		}
	}
	public void actionRemoveLine_actionPerformed(ActionEvent arg0)throws Exception {
		super.actionRemoveLine_actionPerformed(arg0);
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0; i<this.kdtEntry.getRowCount(); i++){
			IRow row = this.kdtEntry.getRow(i);
			total=FDCHelper.add(total, row.getCell("amount").getValue());
		}
		this.txtAmount.setValue(total);
	}
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCustomer);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"��¼����Ϊ�գ�");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row = this.kdtEntry.getRow(i);
			if(row.getCell("amount").getValue()==null||((BigDecimal)row.getCell("amount").getValue()).compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"Ʊ�ݽ���Ϊ�գ�");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
			if(FDCHelper.add(row.getCell("amount").getValue(), row.getCell("invoiceAmount").getValue()).compareTo((BigDecimal) row.getCell("appAmount").getValue())>0){
				FDCMsgBox.showWarning(this,"�ѿ�Ʊ�ݽ��+Ʊ�ݽ��ܴ���Ӧ�ս�");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
	}
	
	@Override
	protected void prmtCustomer_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.prmtCustomer_dataChanged(e);
		if(e.getNewValue()!=null){
			FDCCustomerInfo c = (FDCCustomerInfo) e.getNewValue();
			if(c.getInvType() != null){
				this.cbType.setSelectedItem(c.getInvType());
			}
		}
	}

}