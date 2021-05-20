/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTCellEditorListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportFactory;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SelectTenancyContractUI extends AbstractSelectTenancyContractUI {
	public SelectTenancyContractUI() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		this.prmtCustomer.setEditable(false);
		this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerAllQuery");
		this.prmtCustomer.setDisplayFormat("$name$");
		this.prmtCustomer.setEditFormat("$number$");
		this.prmtCustomer.setCommitFormat("$number$");
		this.prmtCustomer.setEnabledMultiSelection(true);
		this.prmtCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,SysContext.getSysContext().getCurrentUserInfo()));
		
		this.prmtMoneyDefine.setDisplayFormat("$name$");		
        this.prmtMoneyDefine.setEditFormat("$name$");		
        this.prmtMoneyDefine.setCommitFormat("$name$");	
		prmtMoneyDefine.setEnabledMultiSelection(true);
		prmtMoneyDefine.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo arg0) {
				// TODO Auto-generated method stub
				
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo arg0, OrgType arg1) {
				// TODO Auto-generated method stub
				
			}});
		
		Calendar c = Calendar.getInstance();
		this.kdpEndDate.setValue(c.getTime());
		
		c.add(Calendar.MONTH, -1);
		this.kdpStartDate.setValue(c.getTime());
		this.actionConfirm.setEnabled(true);
		this.actionDCancel.setEnabled(true);
		
		
//		final KDCheckBox cbox = new KDCheckBox();
//		cbox.setSelected(false);
//		cbox.addChangeListener(new ChangeListener(){
//           private void changeSelectedState(boolean selected){
//        	   int size = kDTable1.getRowCount();
//        	   IRow r = null;
//        	   for(int i=0;i<size;i++){
//        		   r = kDTable1.getRow(i);
//        		   r.getCell("selected").setValue(selected);
//        	   }
//           }
//			
//			public void stateChanged(ChangeEvent e) {
//				// TODO Auto-generated method stub
//				
//				if(cbox.SELECTED == cbox.getSelectState()){
//					changeSelectedState(true);
//				}else if(cbox.UNSELECTED == cbox.getSelectState()){
//					changeSelectedState(true);
//				}
//				
//			}});
//		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(cbox);
//		this.kDTable1.checkParsed();
//		this.kDTable1.getHeadRow(0).getCell(0).setEditor(cellEditor);
		this.kDTable1.addKDTMouseListener(new KDTMouseListener(){
			
			private void changeSelectedState(boolean selected){
	        	   int size = kDTable1.getRowCount();
	        	   IRow r = null;
	        	   for(int i=0;i<size;i++){
	        		   r = kDTable1.getRow(i);
	        		   r.getCell("selected").setValue(selected);
	        	   }
	           }
				

			public void tableClicked(KDTMouseEvent arg0) {
				// TODO Auto-generated method stub
				int rowIndex = kDTable1.getHeadRow(0).getRowIndex();
				String txtInfo  = kDTable1.getHeadRow(0).getCell(0).getValue()+"";
				if(arg0.getColIndex() == 0 && arg0.getRowIndex() ==  rowIndex && (txtInfo.equals("全选")||txtInfo.equals("选择"))){
					changeSelectedState(true);
					kDTable1.getHeadRow(0).getCell(0).setValue("全清");
				}else if(arg0.getColIndex() == 0 && arg0.getRowIndex() ==  rowIndex && txtInfo.equals("全清")){
					changeSelectedState(false);
					kDTable1.getHeadRow(0).getCell(0).setValue("全选");
				}
			}
			
		});
	}

	private void loadInvoiceInfo() {

		Date startDate = null;
		Date endDate = null;
		Object[] m = null;
		if (kdpStartDate.getValue() != null) {
			startDate = (Date) kdpStartDate.getValue();
		}
		if (kdpEndDate.getValue() != null) {
			endDate = (Date) kdpEndDate.getValue();
		}
		if (prmtMoneyDefine.getData() != null) {
			m = (Object[]) prmtMoneyDefine.getValue();
		}
		if (m == null) {
			FDCMsgBox.showError("请选择款项类型!");
			SysUtil.abort();
		}
		if (startDate == null) {
			FDCMsgBox.showError("请选择开始日期");
			SysUtil.abort();
		}
		if (endDate == null) {
			FDCMsgBox.showError("请选择结束日期");
			SysUtil.abort();
		}
		
		if(endDate.compareTo(startDate)<0){
			FDCMsgBox.showError("结束日期必须大于开始日期");
			SysUtil.abort();
		}

		this.kDTable1.removeRows();

		Map paramMap = new HashMap();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("moneyDefine", m);
		paramMap.put("sellProject", getUIContext().get("sellProject"));
		if(prmtCustomer.getData()!=null){
			paramMap.put("customer", prmtCustomer.getData());
		}
		try {
			Map resultMap = InvoiceBatchImportFactory.getRemoteInstance()
					.queryInvoiceData(paramMap);
			InvoiceBatchImportEntryCollection cols = (InvoiceBatchImportEntryCollection) resultMap
					.get("result");
			int size = cols.size();
			InvoiceBatchImportEntryInfo entry = null;
			IRow r = null;
			//this.kDTable1.checkParsed();
			for (int i = 0; i < size; i++) {
				entry = cols.get(i);
				BigDecimal canInvoiceAmt = FDCHelper.ZERO;
				canInvoiceAmt =FDCHelper.subtract(entry.getAppAmountWithoutTax(),entry.getAlreadyInvoiceAmt()==null?FDCHelper.ZERO:entry.getAlreadyInvoiceAmt());
				if(canInvoiceAmt.compareTo(FDCHelper.ZERO) <=0 ){
					continue;
				}
				r = this.kDTable1.addRow();
				fillDataToRow(entry, r);

			}

		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fillDataToRow(InvoiceBatchImportEntryInfo entry, IRow r) {
		
		
		r.setUserObject(entry);
		r.getCell("custName").setValue(entry.getCustomer().getName());
		r.getCell("contractName").setValue(entry.getTenancybill().getName());
		r.getCell("roomName").setValue(entry.getRoomName());
		r.getCell("startDate").setValue(entry.getStartDate());
		r.getCell("endDate").setValue(entry.getEndDate());
		r.getCell("selected").setValue(false);
		r.getCell("apDate").setValue(entry.getAppDate());
		r.getCell("appAmount").setValue(entry.getAppAmountWithoutTax());
		r.getCell("alreayInvoiceAmt").setValue(entry.getAlreadyInvoiceAmt());

	}

	private static final Logger logger = CoreUIObject
			.getLogger(SelectTenancyContractUI.class);

	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
		super.actionSearch_actionPerformed(e);
		loadInvoiceInfo();
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
		Calendar c = Calendar.getInstance();
		this.kdpEndDate.setValue(c.getTime());
		
		c.add(Calendar.DAY_OF_MONTH, -1);
		this.kdpStartDate.setValue(c.getTime());
		
		this.prmtMoneyDefine.setValue(null);
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		super.actionConfirm_actionPerformed(e);
		int rowCount  = this.kDTable1.getRowCount();
		InvoiceBatchImportEditUI ui = (InvoiceBatchImportEditUI) getUIContext().get("ownerUI");
		InvoiceBatchImportEntryCollection cols = new InvoiceBatchImportEntryCollection();
		
		IRow r = null;
		boolean selected = false;
		for(int i=0;i<rowCount;i++){
			r = kDTable1.getRow(i);
			selected = Boolean.valueOf(r.getCell("selected").getValue()+"");
			if(selected){
				cols.add((InvoiceBatchImportEntryInfo) r.getUserObject());
			}
		}
		
		Object[] m=(Object[]) prmtMoneyDefine.getValue();
    	String moneyDefine="";
    	for(int i=0;i<m.length;i++){
    		if(i==m.length-1){
    			moneyDefine=moneyDefine+((MoneyDefineInfo)m[i]).getName();
    		}else{
    			moneyDefine=moneyDefine+((MoneyDefineInfo)m[i]).getName().toString()+",";
    		}
    	}
    	
	   ui.txtDes.setText(moneyDefine);
	   ui.pkStartDate.setValue(kdpStartDate.getValue());
	   ui.pkEndDate.setValue(kdpEndDate.getValue());
	   ui.loadInvoiceInfo(cols);
	   this.destroyWindow();
	}

	/**
	 * output actionDCancel_actionPerformed
	 */
	public void actionDCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionDCancel_actionPerformed(e);
		this.destroyWindow();
	}

}