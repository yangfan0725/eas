/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordInfo;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class InvoiceEditUI extends AbstractInvoiceEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvoiceEditUI.class);

	MarketDisplaySetting setting=new MarketDisplaySetting();
	//setting.getMarkInvoice() Ӫ�������Ƿ�����ͳһƱ�ݹ��� 16������  32����
	private List IDList = new ArrayList();
	private String sellProjectId = "";
	public InvoiceEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		if(setting.getMarkInvoice() == 32){
			this.f7Cheque.setRequired(true);
			this.txtNumber.setVisible(false);
		}else{
			this.f7Cheque.setVisible(false);
			this.txtNumber.setRequired(true);
		}
//		this.tblReceive.getStyleAttributes().setLocked(true);
//		this.txtNumber.setRequired(true);
		
		this.txtNumber.setMaxLength(80);
		this.txtAmount.setEditable(false);
		this.f7Cheque.setEditable(false);
		this.prmtUser.setRequired(true);
		this.txtAmount.setRequired(true);
		this.actionAddNew.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionCancelCancel.setVisible(false);
		this.txtAmount.setRemoveingZeroInDispaly(false);
		this.txtAmount.setRemoveingZeroInEdit(false);
		this.txtAmount.setPrecision(2);
		this.txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAmount.setSupportedEmpty(true);
		if(this.getOprtState().equals("ADDNEW"))
		{
			this.actionPrint.setVisible(false);
			this.actionPrintPreview.setVisible(false);
			this.kDTabbedPane1.remove(1);	//����ʱ��¼��û�����ݵģ��������ص�
		}
		tblReceive.checkParsed();

		tblReceive.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblReceive.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.txtDescription.setMaxLength(80);
		
		this.prmtCustomer.setEnabled(true);
		this.prmtCustomer.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				fillRevByRoomAndCust();
		}});
		this.KDchequeType.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				KDchequeType_itemStateChanged(e);
				
			}
		});
		this.f7Cheque.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				f7Cheque_changeDate(e);
			}
		});
		
		super.onLoad();
		//add by xin_wang 
		initF7Cheque(null);
	
		
		if("VIEW".equals(this.getOprtState())){
			this.btnSelectRoom.setEnabled(false);
			this.tblReceive.getStyleAttributes().setLocked(true);
			for(int i=0; i<this.tblReceive.getRowCount(); i++){
				IRow row = this.tblReceive.getRow(i);
				ICell cell = row.getCell("isSelected");
				cell.getStyleAttributes().setLocked(true);
			}
		}
		this.tblReceive.getColumn("revNumber").getStyleAttributes().setLocked(true);
		this.tblReceive.getColumn("moneyName").getStyleAttributes().setLocked(true);
		this.tblReceive.getColumn("amount").getStyleAttributes().setLocked(true);
		this.tblReceive.getColumn("billDate").getStyleAttributes().setLocked(true);
		this.tblReceive.getColumn("receipt").getStyleAttributes().setLocked(true);
		this.tblReceive.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	
		//add by jiyu_guan
		loadFeildsForRecord();
		
		this.prmtCustomer.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				prmtCustomer.getQueryAgent().resetRuntimeEntityView();
				RoomInfo roomInfo = (RoomInfo)txtRoomNumber.getUserObject();
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				viewInfo.setFilter(filter);
				/*if(roomInfo==null){
					FDCMsgBox.showWarning("����ѡ�񷿼䣡");
					SysUtil.abort();
				}else{*/
				/**
				 * ȥ����������� update by renliang
				 */
					FDCCustomerCollection fdcCustColl = getAllDistinctFdcCustomersByRoom(roomInfo);
					Set fdcCustIdSet = new HashSet();
					for (int i = 0; i < fdcCustColl.size(); i++) {
						fdcCustIdSet.add(fdcCustColl.get(i).getId().toString());
					}
					if(fdcCustIdSet.size()==0)
						filter.getFilterItems().add(new FilterItemInfo("id", "nullnull"));
					else
						filter.getFilterItems().add(new FilterItemInfo("id", fdcCustIdSet , CompareType.INCLUDE));
				//}
				prmtCustomer.setEntityViewInfo(viewInfo);
			
		}});
		this.setUITitle("Ʊ�ݱ༭");
		this.contRoomSignContractNumber.setVisible(false);
		
		if(this.getUIContext().get("sellProjectId")!=null){
			sellProjectId = this.getUIContext().get("sellProjectId").toString();
		}
		if(this.getUIContext().get("customer2")!=null){
			this.prmtCustomer.setValue(getUIContext().get("customer2"));
		}
	}
	
	
	protected void f7Cheque_changeDate(DataChangeEvent e) {
		if(this.f7Cheque.getValue()!=null){
			this.comboStatus.setSelectedItem(((ChequeInfo)this.f7Cheque.getValue()).getStatus());
		}
	}

	private void KDchequeType_itemStateChanged(ItemEvent e) {
		initF7Cheque(e);
		
	}
	private void initF7Cheque(ItemEvent e){
		ChequeTypeEnum cte =null;
			if(e!=null){
				 cte =(ChequeTypeEnum)e.getItem();
			}else{
				cte = (ChequeTypeEnum)this.KDchequeType.getSelectedItem();
			}
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("keeper.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.BOOKED_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString()));
			if(cte!=null){
				filter.getFilterItems().add(new FilterItemInfo("chequeType", cte.getValue()));
			}
//			filter.getFilterItems().add(new FilterItemInfo("state", ChequeStatusEnum.NEW));
//			filter.setMaskString("#1 or #2");
			viewInfo.setFilter(filter);
			viewInfo.getSorter().add(new SorterItemInfo("number"));
			this.f7Cheque.setEntityViewInfo(viewInfo);		
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
//			sels.add("applyRec.*");
			this.f7Cheque.setSelectorCollection(sels);
	}
	
	
	
	
	protected void loadData() throws Exception {
		super.loadData();
		
	}

	public void actionPrint_actionPerformed(ActionEvent e)
	throws Exception
	{
		String invoiceID = this.editData.getId().toString();
		Map map = new HashMap();
		//��ϵͳ����,����ID,�ͻ�ID�ŵ�list����InvoicePrintDataProvider��,�Ա��ҵ�Ʊ�ݶ�Ӧ���տ�����״��տ��¼��Ϣ
		map = getParamForReceivingBill();
		InvoicePrintDataProvider data = new InvoicePrintDataProvider(
				map,invoiceID,
				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ReceiveInvoicePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/invoice", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
		
	}
	
	/**
	 * output actionReceiveBillPrintView_actionPerformed method
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
	throws Exception
	{
		String receiveBillID = this.editData.getId().toString();
		Map map = new HashMap();
		//��ϵͳ����,����ID,�ͻ�ID�ŵ�list����InvoicePrintDataProvider��,�Ա��ҵ�Ʊ�ݶ�Ӧ���տ�����״��տ��¼��Ϣ
		map = getParamForReceivingBill();
		InvoicePrintDataProvider data = new InvoicePrintDataProvider(
				map,receiveBillID,
				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ReceiveInvoicePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/invoice", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrintPreview_actionPerformed(e);
	}
	private Map getParamForReceivingBill(){
		Map map = new HashMap();
		MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.getUIContext().get("SystemType");
		sysType =sysType==null?MoneySysTypeEnum.SalehouseSys:sysType;
		map.put("sysType",sysType);
		RoomInfo roomInfo = (RoomInfo)this.txtRoomNumber.getUserObject();
		String roomId = null;
		if(roomInfo!=null){
			roomId = roomInfo.getId().toString();
		}
		map.put("roomId",roomId);
		String sysCustId = null;
		FDCCustomerInfo fdcCustInfo = (FDCCustomerInfo)this.prmtCustomer.getValue();
		if(fdcCustInfo!=null){
			sysCustId = fdcCustInfo.getSysCustomer().getId().toString();
		}
		map.put("sysCustId",sysCustId);
		return map;
	}
	
	private void fillRevByRoomAndCust() {
		try {
			MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.getUIContext().get("SystemType");
			sysType =sysType==null?MoneySysTypeEnum.SalehouseSys:sysType;
			RoomInfo roomInfo = (RoomInfo)this.txtRoomNumber.getUserObject();
			String roomId = null;
			String purchaseId =null;
			if(roomInfo!=null){
				roomId = roomInfo.getId().toString();
				if(roomInfo.getLastPurchase()!=null){
					purchaseId = roomInfo.getLastPurchase().getId().toString();
				}
			}
			String sysCustId = null;
			FDCCustomerInfo fdcCustInfo = (FDCCustomerInfo)this.prmtCustomer.getValue();
			if(fdcCustInfo!=null) sysCustId = fdcCustInfo.getSysCustomer().getId().toString();
			
			//if(roomId==null || sysCustId==null) return;
			if(sysCustId==null) {
				return;
			}
//			FDCReceivingBillCollection revColl = FDCReceivingBillFactory.getRemoteInstance()
//								.getFDCReceivingBillCollection("select number,bizDate,revBillType,amount,invoice.number,receipt.number,receipt.amount,receiptState,receiptNumber," +
//										"entries.*,entries.moneyDefine.name,entries.moneyDefine.number,customer.name " +
//									"where room.id = '"+roomId+"' and (billStatus ='"+RevBillStatusEnum.AUDITED_VALUE+"' " +
//											" or billStatus ='"+RevBillStatusEnum.RECED_VALUE+"')"+
//											" and (revBillType ='"+RevBillTypeEnum.GATHERING_VALUE+"' or revBillType ='"+RevBillTypeEnum.TRANSFER_VALUE+"') " +
//											" and customer.id = '"+ sysCustId +"'");
			//ʹ��VOȡֵ  
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("number");
			coll.add("bizDate");
			coll.add("revBillType");
			coll.add("amount");
			coll.add("invoice.number");
			coll.add("receipt.number");
			coll.add("receipt.amount");
			coll.add("receiptState");
			coll.add("receiptNumber");
			coll.add("entries.*");
			coll.add("entries.moneyDefine.name");
			coll.add("entries.moneyDefine.number");
			coll.add("customer.name");
			FilterInfo filter = new FilterInfo();
			if(roomId!=null && !"".equals(roomId)){
				filter.getFilterItems().add(new FilterItemInfo("room.id",roomId));
			}
			filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(RevBillStatusEnum.AUDITED_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(RevBillStatusEnum.RECED_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("revBillType",RevBillTypeEnum.GATHERING_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("revBillType",RevBillTypeEnum.TRANSFER_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("customer.id",sysCustId));
			/*��Ҫ����ϵͳ����
			 * ��¥ϵͳ  �տ�ҵ������  revBizType �ͻ��տ�Ϲ��տ�����Ϲ��տ�������
			 * ����ϵͳ 	�տ�ҵ������  revBizType ����Ԥ���տ���޺�ͬ�տ�
			 * ��ҵϵͳ    �տ�ҵ������  revBizType ��ҵ�շ�
			 */
			if(MoneySysTypeEnum.SalehouseSys.equals(sysType)){//ȥ��¥�տ
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.CUSTOMER_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.PURCHASE_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.SINCERITY_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.AREACOMPENSATE_VALUE));
				
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and (#6 or #7 or #8 or #9)");	
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and (#5 or #6 or #7 or #8)");
				}
			}else if (MoneySysTypeEnum.TenancySys.equals(sysType)){
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.OBLIGATE_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.TENANCY_VALUE));
				
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and (#6 or #7)");
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and (#5 or #6)");
				}
			}else if(MoneySysTypeEnum.ManageSys.equals(sysType)){
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.MANAGE_VALUE));
			
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and #6");
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and #5");
				}
			}
			
			view.setSelector(coll);
			view.setFilter(filter);
			FDCReceivingBillCollection revColl = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(view);
			
			this.tblReceive.removeRows();
			int rowCount = 0;
			int groupCount = 0;
			for (int i = 0; i < revColl.size(); i++) {
				FDCReceivingBillInfo revInfo = revColl.get(i);
				FDCReceivingBillEntryCollection entryColl = revInfo.getEntries();
				for (int j = 0; j < entryColl.size(); j++) {
					FDCReceivingBillEntryInfo entryInfo = entryColl.get(j);
					entryInfo.setHead(revInfo);
					MoneyDefineInfo moneyName = entryInfo.getMoneyDefine();
					IRow row = this.tblReceive.addRow();
					rowCount++;
					row.setUserObject(entryInfo);

					row.getCell("revNumber").setValue(revInfo.getNumber());
					row.getCell("moneyName").setValue(moneyName == null ? null : moneyName.getName());
					row.getCell("amount").setValue(entryInfo.getRevAmount());
					row.getCell("billDate").setValue(revInfo.getBizDate());				
					
					if(setting.getMarkInvoice()==32) {
						row.getCell("receipt").setValue(revInfo.getReceipt()==null?"":revInfo.getReceipt().getNumber());
						if(revInfo.getInvoice()!=null){
							row.getCell("invoiceNum").setValue(revInfo.getInvoice()==null?"":revInfo.getInvoice().getNumber());
						}
					}else{
						row.getCell("receipt").setValue(revInfo.getReceiptNumber());
						if(revInfo.getInvoice()!=null){
							row.getCell("invoiceNum").setValue(revInfo.getInvoice().getNumber());
						}
						
					}
					//���ݸ�����Ƿ�������վ�,��Ʊ���ж��ǲ���Ҫ��row xin_wang 2010.09.25
					lockRecord(revInfo,row);
									
				}
				
				String[] mergeColName = new String[]{"isSelected", "revNumber", "billDate", "receipt","invoiceNum"};
				
				for(int k=0; k<mergeColName.length; k++){
					int colIndex = this.tblReceive.getColumnIndex(mergeColName[k]);
					this.tblReceive.getMergeManager().mergeBlock(groupCount, colIndex, rowCount-1, colIndex, KDTMergeManager.FREE_ROW_MERGE);
				}				
				groupCount = rowCount;
			}
			//����ϴ��û���ѡ���¼���û�ÿ��ѡ�񶼻����¼�¼
			IDList.clear();
			
			//��ʾ��ͬ����
			//�����ͬ�� ������ʾ
			this.txtRoomSignContractNumber.setText(null);
			if(purchaseId!=null){
				RoomSignContractCollection signColl = RoomSignContractFactory.getRemoteInstance()
				.getRoomSignContractCollection("select contractNumber where purchase.id = '"+purchaseId+"' and isBlankOut =0 ");
				if(signColl.size()>0) {
					FDCCustomerInfo customer =(FDCCustomerInfo)this.prmtCustomer.getValue();
//					if(customer!=null&&customer.isIsForSHE()){//����¥�Ŀͻ�����ʾ��ͬ��
						this.txtRoomSignContractNumber.setText(signColl.get(0).getContractNumber());
//					}
				}else{
					this.txtRoomSignContractNumber.setText(null);
				}
			}
		} catch (BOSException e) {
			handleException(e);
		}
	}

	private void lockRecord(FDCReceivingBillInfo revInfo,IRow row){
			ChequeTypeEnum cte = (ChequeTypeEnum)this.KDchequeType.getSelectedItem();
			whenChequeTypeChange(cte,row,revInfo,setting.getMarkInvoice());
		
		
	}
	
	
	private void whenChequeTypeChange(ChequeTypeEnum cte,IRow row,FDCReceivingBillInfo revInfo,int MarkInvoice){
		if(MarkInvoice==32){
				if(cte!=null){
					if(ChequeTypeEnum.invoice.equals(cte)){//��Ʊ
						InvoiceInfo invoice = revInfo.getInvoice();
						if (invoice == null) {
							row.getCell("isSelected").setValue(Boolean.FALSE);
							if(IDList.contains(revInfo.getId())){
								row.getCell("isSelected").setValue(Boolean.TRUE);
							}
						} else {
							if(this.editData.getId()==null) {  //���������
								row.getCell("isSelected").setValue(Boolean.FALSE);
								row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR); //�Ѿ��з�Ʊ�������ǵ�ǰ���ŵ�
								row.getStyleAttributes().setLocked(true);
							}else{	//�޸ĵ����
								row.getCell("isSelected").setValue(Boolean.FALSE);
								if(invoice.getId().toString().equals(this.editData.getId().toString())){  //�ǵ�ǰ����
									row.getCell("isSelected").setValue(Boolean.TRUE);
								}else{
									row.getStyleAttributes().setLocked(true);
									row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
								}
							}
						}	
					}
					if(ChequeTypeEnum.receipt.equals(cte)){//�վ�
						ChequeInfo cheque = revInfo.getReceipt();
						if (cheque == null) {
							row.getCell("isSelected").setValue(Boolean.FALSE);
							// ��������ϵͳ�ڼ���һ�ν��棬�����տû�б��������ݿ⣬��֪���û��ϴ�ѡ���ļ����տ��ͬʱ���ʱ���տ�ϵĵ��ݺŻ�û�и�����
							if(IDList.contains(revInfo.getId())){
								row.getCell("isSelected").setValue(Boolean.TRUE);
							}
							
						} else {
							if(this.editData.getId()==null) {  //���������
								row.getCell("isSelected").setValue(Boolean.FALSE);
								row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR); //�Ѿ��з�Ʊ�������ǵ�ǰ���ŵ�
								row.getStyleAttributes().setLocked(true);
							}else{	//�޸ĵ����
								row.getCell("isSelected").setValue(Boolean.FALSE);
								if(cheque.getId().toString().equals(this.editData.getCheque().getId().toString())){  //�ǵ�ǰ����
									row.getCell("isSelected").setValue(Boolean.TRUE);
								}else{
									row.getCell("isSelected").setValue(Boolean.FALSE);
									row.getStyleAttributes().setLocked(true);
									row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
								}
							}
						}
						
						//�ſ��������ˣ���Ϊ�վ��ǿ������⿪�����⻻
//						row.getCell("isSelected").setValue(Boolean.FALSE);
//						// ��������ϵͳ�ڼ���һ�ν��棬�����տû�б��������ݿ⣬��֪���û��ϴ�ѡ���ļ����տ��ͬʱ���ʱ���տ�ϵĵ��ݺŻ�û�и�����
//						if(IDList.contains(revInfo.getId())){
//							row.getCell("isSelected").setValue(Boolean.TRUE);
//						}
					}
				}
		}else{//û������Ʊ�ݹ���  xin_wang 2010.09.27
			if(cte!=null){
				if(ChequeTypeEnum.invoice.equals(cte)){//��Ʊ
					InvoiceInfo invoice = revInfo.getInvoice();
					if (invoice == null) {
						row.getCell("isSelected").setValue(Boolean.FALSE);
						if(IDList.contains(revInfo.getId())){
							row.getCell("isSelected").setValue(Boolean.TRUE);
						}
					} else {
						if(this.editData.getId()==null) {  //���������
							row.getCell("isSelected").setValue(Boolean.FALSE);
							row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR); //�Ѿ��з�Ʊ�������ǵ�ǰ���ŵ�
							row.getStyleAttributes().setLocked(true);
						}else{	//�޸ĵ����
							row.getCell("isSelected").setValue(Boolean.FALSE);
							if(invoice.getId().toString().equals(this.editData.getId().toString())){  //�ǵ�ǰ����
								row.getCell("isSelected").setValue(Boolean.TRUE);
							}else{
								row.getStyleAttributes().setLocked(true);
								row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
							}
						}
					}	
				}
				if(ChequeTypeEnum.receipt.equals(cte)){//�վ�
					String cheque = revInfo.getReceiptNumber();
					if (cheque == null) {
						row.getCell("isSelected").setValue(Boolean.FALSE);
						// ��������ϵͳ�ڼ���һ�ν��棬�����տû�б��������ݿ⣬��֪���û��ϴ�ѡ���ļ����տ��ͬʱ���ʱ���տ�ϵĵ��ݺŻ�û�и�����
						if(IDList.contains(revInfo.getId())){
							row.getCell("isSelected").setValue(Boolean.TRUE);
						}
						
					} else {
						if(this.editData.getId()==null) {  //���������
							row.getCell("isSelected").setValue(Boolean.FALSE);
							row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR); //�Ѿ��з�Ʊ�������ǵ�ǰ���ŵ�
							row.getStyleAttributes().setLocked(true);
						}else{	//�޸ĵ����
							row.getCell("isSelected").setValue(Boolean.FALSE);
							if(cheque.equals(this.editData.getNumber())){  //�ǵ�ǰ����
								row.getCell("isSelected").setValue(Boolean.TRUE);
							}else{
								row.getCell("isSelected").setValue(Boolean.FALSE);
								row.getStyleAttributes().setLocked(true);
								row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
							}
						}
					}
					//�ſ��������ˣ���Ϊ�վ��ǿ������⿪�����⻻
//					row.getCell("isSelected").setValue(Boolean.FALSE);
//					// ��������ϵͳ�ڼ���һ�ν��棬�����տû�б��������ݿ⣬��֪���û��ϴ�ѡ���ļ����տ��ͬʱ���ʱ���տ�ϵĵ��ݺŻ�û�и�����
//					if(IDList.contains(revInfo.getId())){
//						row.getCell("isSelected").setValue(Boolean.TRUE);
//					}
				}
			}
		}
	}
	
	
	
	
	/**
	 * output storeFields method
	 */
	
	public void storeFields() {
		
		if(!"".equals(this.sellProjectId)){
			SellProjectInfo sellProject = new SellProjectInfo();
			sellProject.setId(BOSUuid.read(sellProjectId));
			this.editData.setProject(sellProject);
		}
		super.storeFields();
		this.editData.setRoom((RoomInfo) this.txtRoomNumber.getUserObject());
		if(setting.getMarkInvoice() == 32){
			this.editData.setCheque((ChequeInfo) this.f7Cheque.getValue());
			this.editData.setChequeType(((ChequeInfo) this.f7Cheque.getValue()).getChequeType());
		}else{
			this.editData.setChequeType((ChequeTypeEnum)this.KDchequeType.getSelectedItem());
		}
		
		initOldData(this.editData);
	}

	/**
	 * output tblReceive_tableClicked method
	 */
	protected void tblReceive_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		
		//ת�ƽ��㣬�Դ���editstop�¼���ʹ�����ʾ����ʱͳ��һ��
		this.txtDescription.setFocusable(true);
		this.txtDescription.requestFocus();
	}

	protected void tblReceive_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (colIndex == 0) {
			IRow row = this.tblReceive.getRow(e.getRowIndex());
			if (row == null) {
				return;
			}
			
			setSumAmount();
		}
	}
	
	
	private void setSumAmount() {
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 0; i < this.tblReceive.getRowCount(); i++) {
			IRow row = this.tblReceive.getRow(i);
			Boolean isSelected = (Boolean) row.getCell("isSelected").getValue();
			if (isSelected != null && isSelected.booleanValue()) {
				FDCReceivingBillEntryInfo entryInfo = (FDCReceivingBillEntryInfo)row.getUserObject();
				BigDecimal amount = entryInfo.getHead().getAmount();
				if (amount != null) {
					sum = sum.add(amount);
				}
				//�ѿͻ�ѡ����տID��¼��
				IDList.add(entryInfo.getHead().getId());
			}
		}
		this.txtAmount.setValue(sum);
	}

	/**
	 * output btnSelectRoom_actionPerformed method
	 */
	protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		
		MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.getUIContext().get("SystemType");
		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, null, null,sysType==null?MoneySysTypeEnum.SalehouseSys:sysType);
		if (room != null) {
			this.tblReceive.removeRows();
			this.txtRoomNumber.setText(room.getDisplayName());
			this.txtRoomNumber.setUserObject(room);
			
			FDCCustomerCollection fdcCustColl = getAllDistinctFdcCustomersByRoom(room);
			//����1��ʱ�� ֱ���裬������
			if(fdcCustColl.size()==1){
				prmtCustomer.setValue(fdcCustColl.get(0));
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				viewInfo.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("id", fdcCustColl.get(0).getId().toString() , CompareType.INCLUDE));
//				prmtCustomer.setValue(null);
				prmtCustomer.setEntityViewInfo(viewInfo);
			}else{
				Set fdcCustIdSet = new HashSet();
				for (int i = 0; i < fdcCustColl.size(); i++) {
					fdcCustIdSet.add(fdcCustColl.get(i).getId().toString());
				}
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				viewInfo.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("id", fdcCustIdSet , CompareType.INCLUDE));
				prmtCustomer.setValue(null);
				prmtCustomer.setEntityViewInfo(viewInfo);
			}
		}else{
			prmtCustomer.setValue(null);
		}
		
	}
	
	
	private FDCCustomerCollection getAllDistinctFdcCustomersByRoom(RoomInfo roomInfo) {
		FDCCustomerCollection fdcCustColl = new FDCCustomerCollection();
		FDCReceivingBillCollection fdcRevColl = new FDCReceivingBillCollection();
		try { 
				if(roomInfo!=null){
					 fdcRevColl = FDCReceivingBillFactory.getRemoteInstance()
					.getFDCReceivingBillCollection("select customer.id where room.id = '"+roomInfo.getId()+"'");
				}else{
					if(!"".equals(this.sellProjectId)){
						 fdcRevColl = FDCReceivingBillFactory.getRemoteInstance()
						 .getFDCReceivingBillCollection("select customer.id where sellProject.id='"+this.sellProjectId+"'");
				
					}else{
						 fdcRevColl = FDCReceivingBillFactory.getRemoteInstance()
						 .getFDCReceivingBillCollection("select customer.id ");
				
					}
				}
				String custIdStr = "";
				for (int i = 0; i < fdcRevColl.size(); i++) {
					//���˵�û���տ�ͻ������
					if(fdcRevColl.get(i).getCustomer()==null){
						continue;
					}
					String tempStrId = fdcRevColl.get(i).getCustomer().getId().toString();
					if(custIdStr.indexOf("'"+tempStrId+"'")<0) 
						custIdStr += (custIdStr.equals("")?"":",") + "'"+tempStrId+"'";
				}
				if(!custIdStr.equals(""))
					fdcCustColl = FDCCustomerFactory.getRemoteInstance()
							.getFDCCustomerCollection("select name,number,isForSHE where sysCustomer.id in ("+custIdStr+")");
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return fdcCustColl;
	}
	
	

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("cheque.*");
		sels.add("user.*");
		sels.add("customer.id");
		sels.add("customer.number");
		sels.add("customer.name");
		sels.add("customer.sysCustomer.*");
		sels.add("customer.isForSHE");
		return sels;
	}

	protected IObjectValue createNewData() {
		InvoiceInfo invoice = new InvoiceInfo();
		invoice.setDate(new Date());
		invoice.setAmount(FDCHelper.ZERO);
		invoice.setUser(SysContext.getSysContext().getCurrentUserInfo());
		if (this.getUIContext().get("room") != null) {
			RoomInfo room = (RoomInfo) this.getUIContext().get("room");
			invoice.setRoom(room);	
			
			this.txtRoomNumber.setText(room.getDisplayName());
			this.txtRoomNumber.setUserObject(room);				
		}
		if (this.getUIContext().get("customer") != null) {
			CustomerInfo customer = (CustomerInfo) this.getUIContext().get("customer");
			try {
				FDCCustomerCollection custColl = FDCCustomerFactory.getRemoteInstance()
					.getFDCCustomerCollection("select name,number,isForSHE where sysCustomer.id='"+customer.getId()+"'");
				if(custColl.size()>0)
					invoice.setCustomer(custColl.get(0));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.getUIContext().get("chequeType")!=null){//��ʼ�� Ʊ������
			if(ChequeTypeEnum.invoice.equals((ChequeTypeEnum)(this.getUIContext().get("chequeType")))){
				this.KDchequeType.setSelectedItem(ChequeTypeEnum.invoice);
				this.KDchequeType.setEnabled(false);
			}else if(ChequeTypeEnum.receipt.equals((ChequeTypeEnum)(this.getUIContext().get("chequeType")))){
				this.KDchequeType.setSelectedItem(ChequeTypeEnum.receipt);
				this.KDchequeType.setEnabled(false);
			}
		}
		this.comboStatus.setEnabled(false);
//		this.comboStatus.setSelectedItem(ChequeStatusEnum.Booked);
		return invoice;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InvoiceFactory.getRemoteInstance();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		BigDecimal amount = this.txtAmount.getBigDecimalValue();
		
		
		String fnumber = this.txtNumber.getText();
		if (StringUtils.isEmpty(fnumber)) {
			MsgBox.showInfo("Ʊ�ݺű���¼��!");
			return;
		}		
		
		if (this.prmtUser.getValue() == null) {
			MsgBox.showInfo("��Ʊ�˱���¼��!");
			return;
		}
		/**
		 * ����R101125-184�ᵥ��ȥ��Ʊ�ݱ�����������������
		 * update by renliang at 2011-1-12
		 */
		/*if (this.txtRoomNumber.getUserObject() == null) {
			MsgBox.showInfo("û��ѡ�񷿼�!");
			return;
		}*/
		
		BOSUuid fdcBillID = null;
		FDCReceivingBillCollection addRevs = new FDCReceivingBillCollection();
		FDCReceivingBillCollection removeRevs = new FDCReceivingBillCollection();
		for (int i = 0; i < this.tblReceive.getRowCount(); i++) {  //�����кϲ��е�Ҫ���⴦��
			IRow row = this.tblReceive.getRow(i);
			if(row.getStyleAttributes().isLocked()) continue;	//�����Ĳ����޸ĵ�
			
			Boolean isSelected = (Boolean) row.getCell("isSelected").getValue();
			FDCReceivingBillEntryInfo entryInfo = (FDCReceivingBillEntryInfo)row.getUserObject();
			FDCReceivingBillInfo fdc = entryInfo.getHead();
			
			if(fdc.getId()!=fdcBillID) 
				fdcBillID = fdc.getId();
			else
				continue;
			
			if (isSelected != null && isSelected.booleanValue()) {
				addRevs.add(fdc);
			} else {
				removeRevs.add(fdc);
			}
		}
		if (addRevs.size() == 0) {
			MsgBox.showInfo("����ѡ��һ��δ��Ʊ�ݵ��տ!");
			return;
		}
		if (amount == null || amount.compareTo(FDCHelper.ZERO) <= 0) {
			MsgBox.showInfo("������¼��!");
			return;
		}
		
		//���ڻ����б����ظ���ʾ���ǡ����룥xx���Ѿ����ڣ������ظ���������Ҫ���޸�Ϊ����Ʊ�ţ�xx���Ѿ����ڣ������ظ���������ڴ˼���
		String ksqlStr = "where number = '"+fnumber+"' ";
		if(this.editData.getId()!=null) ksqlStr += " and id != '"+this.editData.getId()+"' ";
		if(InvoiceFactory.getRemoteInstance().exists(ksqlStr)) {
			MsgBox.showInfo("Ʊ�ݺ�"+fnumber+"�Ѿ����ڣ������ظ�!");
			return;
		}
				
		
		this.setOprtState("EIDT");
		InvoiceInfo invoiceInfo = (InvoiceInfo) this.editData;
	
		super.actionSubmit_actionPerformed(e);
		
		if(setting.getMarkInvoice() == 32){  //ͳһƱ��
			if(ChequeTypeEnum.invoice.equals((ChequeTypeEnum)(this.KDchequeType.getSelectedItem()))){
				dealWhenMarkInvoic(invoiceInfo,addRevs,removeRevs);
			}else if(ChequeTypeEnum.receipt.equals((ChequeTypeEnum)(this.KDchequeType.getSelectedItem()))){
				//Ʊ���������վ�ʱ���Ĵ���
				dealWhenMarkInvoicOther(invoiceInfo,addRevs,removeRevs);
			}
		}else{
			if(ChequeTypeEnum.invoice.equals((ChequeTypeEnum)(this.KDchequeType.getSelectedItem()))){
				dealWhenNotMarkInvoic(invoiceInfo,addRevs,removeRevs);
			}else if(ChequeTypeEnum.receipt.equals((ChequeTypeEnum)(this.KDchequeType.getSelectedItem()))){
				//Ʊ���������վ�ʱ���Ĵ���
				dealWhenNotMarkInvoicOther(invoiceInfo,addRevs,removeRevs);
			}
			
		}
		//�����Ժ�ͻ���
		this.btnSubmit.setEnabled(false);	
	}
	
	private void dealWhenMarkInvoicOther(InvoiceInfo invoiceInfo,FDCReceivingBillCollection addRevs,FDCReceivingBillCollection removeRevs) throws Exception {
		StringBuffer writtenOfCheque = new StringBuffer();
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			if (fdc.getReceipt() == null) {
				fdcSels.add("receipt");				
				fdc.setReceipt((ChequeInfo)this.f7Cheque.getValue());
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.HasMake);	//�ѿ�
				fdcSels.add("receiptNumber");
			}			
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ���վݺ�
		}
		
		ChequeInfo cheque = (ChequeInfo)this.f7Cheque.getValue();
		this.editData.setCheque(cheque);			//�վݶ�Ӧ������Ʊ��			

		StringBuffer sb = new StringBuffer();
		if(invoiceInfo.getRoom()!=null){
			sb.append(invoiceInfo.getRoom().getName());
			sb.append("������");
		}
		
		cheque.setResume(sb.toString());
		cheque.setStatus(ChequeStatusEnum.WrittenOff);
		BigDecimal invoiceAmount = invoiceInfo.getAmount();
		if(invoiceAmount == null)	invoiceAmount = new BigDecimal("0");
		
		Format u = NTNumberFormat.getInstance("rmb");
		cheque.setCapitalization(u.format(invoiceAmount));    		
		cheque.setAmount(invoiceAmount);
		
		String payerStrs = "";
		Date lastPayDate = null;
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);

			if(fdc.getCustomer()!=null) {
				String custName = fdc.getCustomer().getName();
				if(payerStrs.indexOf(custName)<0)
					payerStrs += "," + custName;
			}
			if(fdc.getBizDate()!=null) {
				if(lastPayDate==null || fdc.getBizDate().after(lastPayDate))
					lastPayDate = fdc.getBizDate();	
			}
		}    		
		//������ݴ��ڱ��ֶ���󳤶�300,���н�ȡ
		String des = writtenOfCheque.toString();
		if(des.length() > 300) 	des = des.substring(0, 300);
		cheque.setDescription(des);
		
		cheque.setWrittenOffer(SysContext.getSysContext().getCurrentUserInfo());
		cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
		cheque.setPayer(payerStrs.replaceFirst(",", ""));
		if(lastPayDate==null) {
			cheque.setPayTime(FDCCommonServerHelper.getServerTimeStamp());
		}else{
			cheque.setPayTime(new Timestamp(lastPayDate.getTime()));
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("resume");
		sels.add("status");
		sels.add("amount");
		sels.add("description");
		sels.add("capitalization");    		
		sels.add("writtenOffer");
		sels.add("writtenOffTime");
		sels.add("payer");
		sels.add("payTime");
		ChequeFactory.getRemoteInstance().updatePartial(cheque, sels);	//���±���Ӧ���վ���Ϣ    
	}
	
	private void dealWhenNotMarkInvoicOther(InvoiceInfo invoiceInfo,FDCReceivingBillCollection addRevs,FDCReceivingBillCollection removeRevs) throws Exception {
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			
			if(fdc.getReceiptNumber()==null) {
				fdcSels.add("receipt");				
				fdc.setReceipt((ChequeInfo)this.f7Cheque.getValue());
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.HasMake);	//�ѿ�
				fdcSels.add("receiptNumber");
			}else{
//				if(fdc.getReceiptState().equals(ReceiptStatusEnum.HasMake)) {
//					fdc.setReceiptState(ReceiptStatusEnum.HasInvoice); //�ѻ���Ʊ
//					fdcSels.add("receiptState");
//				}
			}
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ���վݺ�
		}		
	
	}
	private void dealWhenMarkInvoic(InvoiceInfo invoiceInfo,FDCReceivingBillCollection addRevs,FDCReceivingBillCollection removeRevs) throws Exception { //Ʊ��ͳһ����
		StringBuffer writtenOfCheque = new StringBuffer("�����վݺţ�");
		
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			fdcSels.add("invoice");				
			fdc.setInvoice(this.editData);
			if (fdc.getReceipt() == null) {
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.UnMake);	//δ��
			}else{
				ChequeInfo revCheque = fdc.getReceipt();	//�վ�
				if(fdc.getReceiptState().equals(ReceiptStatusEnum.HasMake)) {
					fdcSels.add("receiptState");
					fdc.setReceiptState(ReceiptStatusEnum.HasInvoice); //�ѻ���Ʊ
				}	

				revCheque.setStatus(ChequeStatusEnum.Verified);	 //�ѻ���
				SelectorItemCollection cheQueSel = new SelectorItemCollection();
				cheQueSel.add("status");
				ChequeFactory.getRemoteInstance().updatePartial(revCheque, cheQueSel); //�����վ�״̬
			}
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ�ķ�Ʊ�ż��վ�״̬
		}		

		for (int i = 0; i < removeRevs.size(); i++) {
			FDCReceivingBillInfo fdc = removeRevs.get(i);
			
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			fdcSels.add("invoice");
			fdc.setInvoice(null);
			if(fdc.getReceipt()!=null && fdc.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)) {
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.HasMake);
				
				ChequeInfo revCheque = fdc.getReceipt();	
				revCheque.setStatus(ChequeStatusEnum.WrittenOff);	
				SelectorItemCollection cheQueSel = new SelectorItemCollection();
				cheQueSel.add("status");
				ChequeFactory.getRemoteInstance().updatePartial(revCheque, cheQueSel); //�����վ�״̬
			}else if(fdc.getReceipt()==null){
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.UnMake);
			}
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ�ķ�Ʊ�ż��վ�״̬
		}
		
		
		ChequeInfo cheque = (ChequeInfo)this.f7Cheque.getValue();
		this.editData.setCheque(cheque);			//��Ʊ��Ӧ������Ʊ��			

		StringBuffer sb = new StringBuffer();
		if(invoiceInfo.getRoom()!=null){
			sb.append(invoiceInfo.getRoom().getName());
			sb.append("������");
		}
		cheque.setResume(sb.toString());
		cheque.setStatus(ChequeStatusEnum.WrittenOff);
		BigDecimal invoiceAmount = invoiceInfo.getAmount();
		if(invoiceAmount == null)	invoiceAmount = new BigDecimal("0");
		
		Format u = NTNumberFormat.getInstance("rmb");
		cheque.setCapitalization(u.format(invoiceAmount));    		
		cheque.setAmount(invoiceAmount);
		
		String payerStrs = "";
		Date lastPayDate = null;
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);
			ChequeInfo revCheque = fdc.getReceipt();	//�վ�
			if(revCheque != null){
				BigDecimal revAmount = revCheque.getAmount();
				if(revAmount != null)	revAmount = revAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
				writtenOfCheque.append(revCheque.getNumber());
				writtenOfCheque.append("(").append(revAmount).append("Ԫ),");
			}else{
				BigDecimal revAmount = fdc.getAmount();
				if(revAmount != null) revAmount = revAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
				writtenOfCheque.append(fdc.getRevBillType()==null?"":fdc.getRevBillType().getAlias());
				writtenOfCheque.append("���վ�(").append(revAmount).append("Ԫ),");				
			}
			if(fdc.getCustomer()!=null) {
				String custName = fdc.getCustomer().getName();
				if(payerStrs.indexOf(custName)<0)
					payerStrs += "," + custName;
			}
			if(fdc.getBizDate()!=null) {
				if(lastPayDate==null || fdc.getBizDate().after(lastPayDate))
					lastPayDate = fdc.getBizDate();	
			}
		}    		
		//������ݴ��ڱ��ֶ���󳤶�300,���н�ȡ
		String des = writtenOfCheque.toString();
		if(des.length() > 300) 	des = des.substring(0, 300);
		cheque.setDescription(des);
		
		cheque.setWrittenOffer(SysContext.getSysContext().getCurrentUserInfo());
		cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
		cheque.setPayer(payerStrs.replaceFirst(",", ""));
		if(lastPayDate==null) {
			cheque.setPayTime(FDCCommonServerHelper.getServerTimeStamp());
		}else{
			cheque.setPayTime(new Timestamp(lastPayDate.getTime()));
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("resume");
		sels.add("status");
		sels.add("amount");
		sels.add("description");
		sels.add("capitalization");    		
		sels.add("writtenOffer");
		sels.add("writtenOffTime");
		sels.add("payer");
		sels.add("payTime");
		ChequeFactory.getRemoteInstance().updatePartial(cheque, sels);	//���±���Ʊ��Ӧ���վ���Ϣ    	
	}
	
	
	private void dealWhenNotMarkInvoic(InvoiceInfo invoiceInfo,FDCReceivingBillCollection addRevs,FDCReceivingBillCollection removeRevs) throws Exception {
		for (int i = 0; i < addRevs.size(); i++) {
			FDCReceivingBillInfo fdc = addRevs.get(i);
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			fdcSels.add("invoice");						
			fdc.setInvoice(this.editData);
			if(fdc.getReceiptNumber()==null) {
				fdc.setReceiptState(ReceiptStatusEnum.UnMake);	//δ��
				fdcSels.add("receiptState");
			}else{
				if(fdc.getReceiptState().equals(ReceiptStatusEnum.HasMake)) {
					fdc.setReceiptState(ReceiptStatusEnum.HasInvoice); //�ѻ���Ʊ
					fdcSels.add("receiptState");
				}
			}
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ�ķ�Ʊ�ż��վ�״̬
		}		

		for (int i = 0; i < removeRevs.size(); i++) {
			FDCReceivingBillInfo fdc = removeRevs.get(i);
			
			SelectorItemCollection fdcSels = new SelectorItemCollection();
			fdcSels.add("invoice");
			fdc.setInvoice(null);
			if(fdc.getReceipt()!=null && fdc.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)) {
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.HasMake);
			}else if(fdc.getReceipt()==null){
				fdcSels.add("receiptState");
				fdc.setReceiptState(ReceiptStatusEnum.UnMake);
			}
			FDCReceivingBillFactory.getRemoteInstance().updatePartial(fdc, fdcSels);  //�����տ�ķ�Ʊ�ż��վ�״̬
		}	

	}
	
	
	protected void f7Cheque_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Cheque_dataChanged(e);
		Object obj = e.getNewValue();
		if(obj == null){
			this.txtNumber.setText(null);
		}else{
			ChequeInfo cheque = (ChequeInfo) obj;
			this.txtNumber.setText(cheque.getNumber());
		}
	}
	

	/**
	 * �ر�ǰ���²�����¼ add by jiyu_guan 
	 * �����Ϊ��Ʊ�����������վݲ�����¼�� 
	 * �����ָ�Ϊ��Ʊ����ֻ�������վݵ��տ������¼
	 */
	public boolean checkBeforeWindowClosing() {
		boolean check = super.checkBeforeWindowClosing();
		if (check && (STATUS_EDIT.equals(getOprtState()))) {
			StringBuffer content = new StringBuffer();
			List hasReceipt = new ArrayList();
			IReceiptInvoiceFacade facade = null;
			try {
				facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			} catch (BOSException e) {
				handUIException(e);
			}

			FDCReceivingBillCollection addRevs = new FDCReceivingBillCollection();
			for (int i = 0; i < this.tblReceive.getRowCount(); i++) {
				IRow row = this.tblReceive.getRow(i);
				Boolean isSelected = (Boolean) row.getCell("isSelected").getValue();
				FDCReceivingBillEntryInfo entryInfo = (FDCReceivingBillEntryInfo)row.getUserObject();
				if (isSelected != null && isSelected.booleanValue()) {
					addRevs.add(entryInfo.getHead());
				}
			}
			//-------------------------����Ʊ�Ĳ�����¼-----------------------------------------------------------
			//���ݽ����ϵ�Ʊ���������ж���ʲô����
			if(ChequeTypeEnum.invoice.equals((ChequeTypeEnum)this.KDchequeType.getSelectedItem())){
				for (int i = 0; i < addRevs.size(); i++) {
					FDCReceivingBillInfo fdc = addRevs.get(i);
					if (fdc.getReceiptNumber() != null	&& !fdc.getReceiptNumber().equals("")) {
						content.append(fdc.getReceiptNumber() + ",");
						hasReceipt.add(fdc.getId().toString());
					}
				}
				RecordTypeEnum recordType = null;
				if (content.length() > 0) {
					content.insert(0, "�����վ�");
					content.append("����Ʊ" + editData.getNumber());
					recordType = RecordTypeEnum.ReceiptToInvoice;
				} else {
					content.append("����Ʊ" + editData.getNumber());
					recordType = RecordTypeEnum.MakeOutInvoice;
				}
				if (recordType != null) {
					try {
						facade.updateRecord(1, editData.getId().toString(),	recordType, content.toString(), null);
					} catch (BOSException e) {
						handUIException(e);
					}
				}
				if (hasReceipt != null && hasReceipt.size() > 0) {
					for (int i = 0; i < hasReceipt.size(); i++) {
						String recID = (String) hasReceipt.get(i);
						try {
							facade.updateRecord(0, recID, recordType, content.toString(), null);
						} catch (BOSException e) {
							handUIException(e);
						}
					}
				}
			}
			//-----------------------�����ݲ���------------------------------------------------------------
			//��ʾ����XX�տ�����վ�
			if(ChequeTypeEnum.receipt.equals((ChequeTypeEnum)this.KDchequeType.getSelectedItem())){
				content.append("���տ:");
				for (int i = 0; i < addRevs.size(); i++) {
					FDCReceivingBillInfo fdc = addRevs.get(i);
					content.append(fdc.getNumber());
					if(i!=0){
						content.append(",");
					}
				}
				content.append(" ���վ�");
				RecordTypeEnum recordType = RecordTypeEnum.MakeOutInvoice;
				if (recordType != null) {
					try {
						facade.updateRecord(1, editData.getId().toString(),	recordType, content.toString(), null);
					} catch (BOSException e) {
						handUIException(e);
					}
				}
//				if (hasReceipt != null && hasReceipt.size() > 0) {
//					for (int i = 0; i < hasReceipt.size(); i++) {
//						String recID = (String) hasReceipt.get(i);
//						try {
//							facade.updateRecord(0, recID, recordType, content.toString(), null);
//						} catch (BOSException e) {
//							handUIException(e);
//						}
//					}
//				}
			}
		}
		return check;
	}
	
	/**
	 * װ�ز�����¼��ҳ�� add by jiyu_guan
	 */
	private void loadFeildsForRecord() {
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			try {
				IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
						.getRemoteInstance();
				FDCReceiveBillRecordCollection col = (FDCReceiveBillRecordCollection) facade
						.getRecord(editData.getId());
				if (col != null && col.size() > 0) {
					tblRecord.checkParsed();
					tblRecord.removeRows();

					FDCReceiveBillRecordInfo record;
					IRow row;
					for (int i = 0; i < col.size(); i++) {
						record = col.get(i);
						if (record != null) {
							row = tblRecord.addRow();
							row.getCell("id").setValue(record.getId());
							row.getCell("recordType").setValue(
									record.getRecordType());
							row.getCell("content")
									.setValue(record.getContent());
							row.getCell("operatingUser").setValue(
									record.getOperatingUser().getName());
							row.getCell("operatingUser").setUserObject(
									record.getOperatingUser());
							row.getCell("operatingDate").setValue(
									record.getOperatingDate());
							row.getCell("description").setValue(
									record.getDescription());
						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
		for(int i=0;i<this.tblReceive.getRowCount();i++)	{
			IRow row = this.tblReceive.getRow(i);
			FDCReceivingBillEntryInfo entryInfo = (FDCReceivingBillEntryInfo)row.getUserObject();
			FDCReceivingBillInfo revInfo = entryInfo.getHead();
			InvoiceInfo invoice = revInfo.getInvoice();
			row.getStyleAttributes().setLocked(false);
			if(invoice!=null) {
				if(this.editData.getId()==null && invoice.getId().toString().equals(this.editData.getId().toString()))  { //����
					row.getStyleAttributes().setLocked(true);
				}
			}
		}
		
		
		
		
	}
	public void loadFields() {
		ChequeTypeEnum ci =this.editData.getChequeType();
		if(ci!=null){
			this.KDchequeType.setSelectedItem(ci);
		}
		super.loadFields();
		//��ͬ�� Ҫ���
		this.txtRoomSignContractNumber.setText(null);
		RoomInfo room =this.editData.getRoom();
		String roomId =null;
		//������ʱ��Ϊ��
		
		if(room!=null){
			roomId =room.getId().toString();
			String purchaseId =null;
			if(room.getLastPurchase()!=null){
				purchaseId = room.getLastPurchase().getId().toString();
			}
			RoomSignContractCollection signColl;
			try {
				if(purchaseId!=null){
					signColl = RoomSignContractFactory.getRemoteInstance()
					.getRoomSignContractCollection("select contractNumber where purchase.id = '"+purchaseId+"' and isBlankOut =0 ");
		//			if(MoneySysTypeEnum.SalehouseSys.equals(this.getUIContext().get("SystemType"))){
						if(signColl.size()>0) {
								this.txtRoomSignContractNumber.setText(signColl.get(0).getContractNumber());
		//				}	
					}else{
							this.txtRoomSignContractNumber.setText(null);
					}
				}
			} catch (BOSException e) {
				this.handleException(e);
			}
		}
	}
	
	protected void showSubmitSuccess() {
		// TODO Auto-generated method stub
//		super.showSubmitSuccess();
		if(ChequeTypeEnum.invoice.equals((ChequeTypeEnum)this.KDchequeType.getSelectedItem())){
			setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		} else{
			setMessageText("�վ�" + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		}
		setIsShowTextOnly(false);
	    setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
	    showMessage();
	}
	
}