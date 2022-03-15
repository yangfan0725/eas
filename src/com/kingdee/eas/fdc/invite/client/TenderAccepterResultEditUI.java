/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.invite.BaseInviteCollection;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.IPInviteListTypeEntryCollection;
import com.kingdee.eas.fdc.invite.IPInviteListTypeEntryFactory;
import com.kingdee.eas.fdc.invite.IPInviteListTypeEntryInfo;
import com.kingdee.eas.fdc.invite.InviteBidEvaluationCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormInfo;
import com.kingdee.eas.fdc.invite.InviteClarifyCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsInfo;
import com.kingdee.eas.fdc.invite.InviteFixCollection;
import com.kingdee.eas.fdc.invite.InviteFixFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadCollection;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadInfo;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.MaterialSampleCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterRListEntryInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierStockEditUI;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


public class TenderAccepterResultEditUI extends AbstractTenderAccepterResultEditUI
{
	private Map tableMaps=new HashMap();
	public TenderAccepterResultEditUI() throws Exception {
		super();
	}
	public void storeFields()
	{
		storeEntry();
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		loadEntry();
		try {
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection("select programmingContract.amount from where parent.id='"+this.editData.getInviteProject().getId().toString()+"'");
			if(col.size()>0&&col.get(0).getProgrammingContract()!=null){
				this.txtAmount.setValue(col.get(0).getProgrammingContract().getAmount());
			}
			loadRecordTable();
		} catch (BOSException e) {
			this.handleException(e);
		}
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	private void loadEntry(){
		tableMaps=new HashMap();
		this.entryPanel.removeAll();
		for(int i=0;i<this.editData.getEntrys().size();i++){
			TenderAccepterResultEntryInfo entry=this.editData.getEntrys().get(i);
			KDTable table=null;
			if(tableMaps.get(entry.getRecordSeq())==null){
				table=new KDTable();
				if(entry.getRecordSeq()==0){
					this.createTable(table, "�б�������");
				}else{
					this.createTable(table, "��"+entry.getRecordSeq()+"���-�б�������");
				}
				tableMaps.put(entry.getRecordSeq(), table);
			}else{
				table=(KDTable) tableMaps.get(entry.getRecordSeq());
			}
			
			IRow row=table.addRow();
			row.setUserObject(entry);
			row.getCell("hit").setValue(entry.isHit());
			row.getCell("supplier").setValue(entry.getSupplier());
			row.getCell("inviteType").setValue(entry.getSupplier().getInviteType().getName());
			row.getCell("taxerQua").setValue(entry.getSupplier().getTaxerQua());
			row.getCell("bidAmount").setValue(entry.getBidAmount());
			row.getCell("totalSeq").setValue(entry.getTotalSeq());
			row.getCell("lastAmount").setValue(entry.getLastAmount());
			row.getCell("fix").setValue(entry.getInviteFix());
			row.getCell("afterFixAmount").setValue(entry.getAfterFixAmount());
			row.getCell("isLowest").setValue(entry.isIsLowest());
			row.getCell("cost").setValue(entry.getCostOfContruction());
		}
	}
	private void storeEntry(){
		this.editData.getEntrys().clear();
		if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table=(KDTable) tableMaps.get(key[k]);
				
				BigDecimal minAmount=null;
				for(int i=0;i<table.getRowCount();i++){
					Boolean hit=(Boolean) table.getRow(i).getCell("hit").getValue();
					BigDecimal lastAmount = (BigDecimal) table.getRow(i).getCell("lastAmount").getValue();
					
					if(!hit.booleanValue()&&(minAmount==null||lastAmount.compareTo(minAmount)<0)){
						minAmount=lastAmount;
					}
				}
				for(int i=0;i<table.getRowCount();i++){
					IRow row=table.getRow(i);
					TenderAccepterResultEntryInfo entry=(TenderAccepterResultEntryInfo) row.getUserObject();
					Boolean hit=(Boolean) table.getRow(i).getCell("hit").getValue();
					BigDecimal lastAmount = (BigDecimal) row.getCell("lastAmount").getValue();
					
					entry.setHit(hit);
					entry.setTotalSeq((Integer) row.getCell("totalSeq").getValue());
					
					entry.setLastAmount(lastAmount);
					if(hit&&(minAmount==null||lastAmount.compareTo(minAmount)<=0)){
						entry.setIsLowest(true);
					}else{
						entry.setIsLowest(false);
					}
					entry.setCostOfContruction((BigDecimal)row.getCell("cost").getValue());
					
					this.editData.getEntrys().add(entry);
				}
			}
		}
	}
	private void createTable(final KDTable table,String title){
		table.checkParsed();
		FDCTableHelper.disableAutoAddLine(table);
		IRow headRow=table.addHeadRow();
		
		IColumn coloum=table.addColumn();
		coloum.setKey("hit");
		headRow.getCell("hit").setValue("�б�");
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		coloum.setEditor(editor);
 		coloum.setRequired(true);
 		
 		
 		
 		coloum=table.addColumn();
		coloum.setKey("supplier");
		coloum.setWidth(250);
		headRow.getCell("supplier").setValue("��Ӧ��");
 		KDBizPromptBox f7Box = new KDBizPromptBox(); 
 		
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
		
		f7Box.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				if(e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())){
					
					SupplierStockInfo info = (SupplierStockInfo) e.getNewValue();
					int rowIndex = table.getSelectManager().getActiveRowIndex();
					if(info.getInviteType()!=null){
						
						try {
							InviteTypeInfo i = InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(info.getInviteType().getId()));
							table.getCell(rowIndex, "inviteType").setValue(i.getName());
						} catch (EASBizException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BOSException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
				
			}});
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		coloum.setEditor(f7Editor);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("inviteType");
		coloum.getStyleAttributes().setLocked(true);
		coloum.setWidth(250);
		headRow.getCell("inviteType").setValue("�ɹ����");
		
		coloum=table.addColumn();
		coloum.setKey("taxerQua");
		coloum.setWidth(100);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		headRow.getCell("taxerQua").setValue("��˰������");
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("bidAmount");
		coloum.setWidth(150);
		headRow.getCell("bidAmount").setValue("���һ�ֻر�۸�(Ԫ)");
		KDFormattedTextField price = new KDFormattedTextField();
		price.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		price.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		price.setPrecision(2);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("totalSeq");
		headRow.getCell("totalSeq").setValue("�б�˳��");
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.INTEGER_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setValue(null);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		coloum.setEditor(amountEditor);
		coloum.setRequired(true);
		
		coloum=table.addColumn();
		coloum.setKey("lastAmount");
		headRow.getCell("lastAmount").setValue("�б�۸�(Ԫ)");
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.setRequired(true);
		
		coloum=table.addColumn();
		coloum.setKey("fix");
		headRow.getCell("fix").setValue("����ϵ��");
		coloum.setRenderer(new ObjectValueRender(){
			public String getText(Object obj)
			{
				if(obj instanceof InviteFixInfo){
					if(obj==null||((InviteFixInfo)obj).getFix()==null) return null;
					return ((InviteFixInfo)obj).getFix().toString();
				}
				return null;
			}
		});
		coloum.getStyleAttributes().setLocked(true);
		coloum.getStyleAttributes().setBackground(new Color(245,245,245));
		
		coloum=table.addColumn();
		coloum.setKey("afterFixAmount");
		headRow.getCell("afterFixAmount").setValue("�����󱨼�(Ԫ)");
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("isLowest");
		headRow.getCell("isLowest").setValue("�Ƿ���ͼ��б�");
		coloum.setEditor(editor);
		coloum.getStyleAttributes().setLocked(true); 
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		
		coloum=table.addColumn();
		coloum.setKey("cost");
		headRow.getCell("cost").setValue("����ɲɹ��ĵ������(Ԫ)");
		coloum.setWidth(200);
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.setRequired(true);
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		this.entryPanel.add(contEntry, title);
		
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table=(KDTable) e.getSource();
		if ("lastAmount".equals(table.getColumn(e.getColIndex()).getKey())){
			InviteFixInfo fix = (InviteFixInfo) table.getRow(e.getRowIndex()).getCell("fix").getValue();
			BigDecimal lastAmount = (BigDecimal) table.getRow(e.getRowIndex()).getCell("lastAmount").getValue();
			if (fix != null && fix.getFix() != null && lastAmount != null){
				table.getRow(e.getRowIndex()).getCell("afterFixAmount").setValue(lastAmount.divide(fix.getFix(), 4,RoundingMode.HALF_UP));
			}else{
				table.getRow(e.getRowIndex()).getCell("afterFixAmount").setValue(null);
			}
		}
	}
	private void loadRecordTable() throws BOSException {
		this.kdtRecord.checkParsed();
		this.kdtRecord.getStyleAttributes().setLocked(true);
		FDCTableHelper.disableAutoAddLine(kdtRecord);
		this.kdtRecord.removeColumns();
		IRow headRow=this.kdtRecord.addHeadRow();
		
		IColumn column=this.kdtRecord.addColumn();
		column.setKey("times");
		headRow.getCell("times").setValue("�ر����");
		
		column=this.kdtRecord.addColumn();
		column.setKey("seq");
		headRow.getCell("seq").setValue("���");
		
		if(tableMaps!=null&&tableMaps.size()>0) {
			KDTable table=(KDTable) tableMaps.get(0);
			if(table==null){
				table=(KDTable) tableMaps.get(1);
			}
			if(table!=null){
				for(int i=0;i<table.getRowCount();i++){
					SupplierStockInfo supplier=(SupplierStockInfo)table.getCell(i, "supplier").getValue();
					if(supplier!=null&&this.editData.getInviteProject()!=null){
						
						column=this.kdtRecord.addColumn();
						column.setKey(supplier.getId().toString());
						column.getStyleAttributes().setNumberFormat("#0.00");
						column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						column.setWidth(200);
						headRow.getCell(supplier.getId().toString()).setValue(supplier.getName());
						
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						view.getSelector().add(new SelectorItemInfo("isMultiple"));
						view.getSelector().add(new SelectorItemInfo("times"));
						view.getSelector().add(new SelectorItemInfo("price"));
						view.getSelector().add(new SelectorItemInfo("entry.*"));
						
						SorterItemCollection sort=new SorterItemCollection();
						sort.add(new SorterItemInfo("times"));
						sort.add(new SorterItemInfo("supplier.number"));
						sort.add(new SorterItemInfo("entry.seq"));
						view.setSorter(sort);
						
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",this.editData.getInviteProject().getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
						for(int j=0;j<col.size();j++){
							if(col.get(j).isIsMultiple()){
								for(int k=0;k<col.get(j).getEntry().size();k++){
									IRow addRow=this.kdtRecord.getRow(col.get(j).getEntry().get(k).getSeq()-1);
									if(addRow==null){
										addRow=this.kdtRecord.addRow();
										addRow.getCell("times").setValue(Integer.valueOf(col.get(j).getTimes()));
									}
									addRow.getCell(supplier.getId().toString()).setUserObject(col.get(j).getId().toString());
									addRow.getCell(supplier.getId().toString()).setValue(col.get(j).getEntry().get(k).getPrice());
									addRow.getCell("seq").setValue("��"+col.get(j).getEntry().get(k).getSeq()+"���");
								}
							}else{
								IRow addRow=this.kdtRecord.getRow(col.get(j).getTimes()-1);
								if(addRow==null){
									addRow=this.kdtRecord.addRow();
									addRow.getCell("times").setValue(Integer.valueOf(col.get(j).getTimes()));
								}
								addRow.getCell(supplier.getId().toString()).setUserObject(col.get(j).getId().toString());
								addRow.getCell(supplier.getId().toString()).setValue(col.get(j).getPrice());
							}
						}
					}
				}
			}
		}
		if(this.kdtRecord.getRowCount()>0&&this.kdtRecord.getRow(0).getCell("seq").getValue()!=null){
			for(int i=0;i<this.kdtRecord.getColumnCount();i++){
				if(!this.kdtRecord.getColumnKey(i).equals("times")&&!this.kdtRecord.getColumnKey(i).equals("seq")){
					ClientHelper.getFootRow(this.kdtRecord, new String[]{this.kdtRecord.getColumnKey(i)});
				}
			}
		}
		mergerTable(this.kdtRecord,new String[]{"times"},new String[]{"times"});
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		parentPanel_stateChanged(null);
		this.txtSpecial.setToolTipText("������Ŀ��ɱ������ݿ⡢�г��۵Ĳ����������Ŀ��ɱ�ԭ�����б��������б������Ĳ��죨�������ʽ�ȣ���δ���б�߻��еĶ�����򶨱��ԭ��˵��������˵����");
		fillAttachmentList();
	}
	protected void initTable() {
		this.kdtInviteListEntry.checkParsed();
		
		KDWorkButton btnInviteAddRowinfo = new KDWorkButton();
		KDWorkButton btnInviteInsertRowinfo = new KDWorkButton();
		KDWorkButton btnInviteDeleteRowinfo = new KDWorkButton();

		this.actionInviteListEntryALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnInviteAddRowinfo = (KDWorkButton) this.contInviteListEntry.add(this.actionInviteListEntryALine);
		btnInviteAddRowinfo.setText("������");
		btnInviteAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInviteListEntryILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInviteInsertRowinfo = (KDWorkButton) contInviteListEntry.add(this.actionInviteListEntryILine);
		btnInviteInsertRowinfo.setText("������");
		btnInviteInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionInviteListEntryRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnInviteDeleteRowinfo = (KDWorkButton) contInviteListEntry.add(this.actionInviteListEntryRLine);
		btnInviteDeleteRowinfo.setText("ɾ����");
		btnInviteDeleteRowinfo.setSize(new Dimension(140, 19));
		
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		this.kdtInviteListEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtInviteListEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtInviteListEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtInviteListEntry.getColumn("amount").setRequired(true);
		
		this.kdtInviteListEntry.getColumn("price").setEditor(amountEditor);
		this.kdtInviteListEntry.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtInviteListEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtInviteListEntry.getColumn("num").setEditor(amountEditor);
		this.kdtInviteListEntry.getColumn("num").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtInviteListEntry.getColumn("num").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.InviteListTypeQuery");
		f7Box.setEnabledMultiSelection(false);
		f7Box.setEditFormat("$name$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$name$");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtInviteListEntry.getColumn("inviteListType").setEditor(f7Editor);
		this.kdtInviteListEntry.getColumn("inviteListType").setRequired(true);
	}
	public void actionInviteListEntryALine_actionPerformed(ActionEvent e)throws Exception {
		IRow row = this.kdtInviteListEntry.addRow();
		TenderAccepterRListEntryInfo info = new TenderAccepterRListEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		row.setUserObject(info);
	}
	public void actionInviteListEntryILine_actionPerformed(ActionEvent e)throws Exception {
		IRow row = null;
		if (this.kdtInviteListEntry.getSelectManager().size() > 0) {
			int top = this.kdtInviteListEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtInviteListEntry)){
				actionInviteListEntryALine_actionPerformed(e);
			}else{
				TenderAccepterRListEntryInfo info = new TenderAccepterRListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				
				row = this.kdtInviteListEntry.addRow(top);
				row.setUserObject(info);
			}
		} else {
			actionInviteListEntryALine_actionPerformed(e);
		}
	}
	public void actionInviteListEntryRLine_actionPerformed(ActionEvent e)throws Exception {
		if (this.kdtInviteListEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtInviteListEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtInviteListEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtInviteListEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtInviteListEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtInviteListEntry.removeRow(top);
			}
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return TenderAccepterResultFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		sic.add(new SelectorItemInfo("inviteProject.inviteListEntry.*"));
		sic.add(new SelectorItemInfo("inviteProject.inviteListEntry.inviteListType.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		sic.add(new SelectorItemInfo("drawingDepth"));
		sic.add(new SelectorItemInfo("deployType"));
		sic.add(new SelectorItemInfo("entrys.supplier.grade.*"));
		sic.add(new SelectorItemInfo("entrys.supplier.*"));
		sic.add(new SelectorItemInfo("entrys.inviteFix.*"));
		sic.add(new SelectorItemInfo("entrys.supplier.inviteType.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		return sic;
	}
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/TenderAccepterResult";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.TenderAcceptResultPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		TenderAccepterResultInfo info=new TenderAccepterResultInfo();
		createBaseInvite(info);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("supplier.*"));
		view.getSelector().add(new SelectorItemInfo("supplier.grade.*"));
		view.getSelector().add(new SelectorItemInfo("supplier.inviteType.*"));
		view.getSelector().add(new SelectorItemInfo("entry.*"));
		
		SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo times=new SorterItemInfo("times");
		times.setSortType(SortType.DESCEND);
		sort.add(times);
		sort.add(new SorterItemInfo("supplier.number"));
		sort.add(new SorterItemInfo("entry.seq"));
		view.setSorter(sort);
		
		if(info.getInviteProject()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",info.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
		}
		try {
			SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
			if(col!=null && col.size()>0) {
				HashMap map =TenderAccepterResultFactory.getRemoteInstance().getFixMap(info.getInviteProject().getId());
				HashMap supplier=new HashMap();
				
				int time=col.get(0).getTimes();
				for(int i=0;i<col.size();i++){
					if(col.get(i).getSupplier()!=null){
						if(time!=col.get(i).getTimes()){
							continue;
						}
						if(supplier.get(col.get(i).getSupplier().getId().toString())==null){
							Object[] key = supplier.keySet().toArray(); 
							for (int k = 0; k < key.length; k++) { 
								SupplierInviteRecordInfo recordEntry=(SupplierInviteRecordInfo) supplier.get(key[k]);
								if(!Boolean.valueOf(recordEntry.isIsMultiple()).equals(Boolean.valueOf(col.get(i).isIsMultiple()))){
									FDCMsgBox.showWarning(this,"��Ӧ��"+col.get(i).getSupplier().getName()+"Ͷ���¼�Ƿ�ֱ�β�һ�£�");
									SysUtil.abort();
								}
								if(recordEntry.isIsMultiple()&&col.get(i).isIsMultiple()&&recordEntry.getEntry().size()!=col.get(i).getEntry().size()){
									FDCMsgBox.showWarning(this,"��Ӧ��"+col.get(i).getSupplier().getName()+"Ͷ���¼�ֱ��������һ�£�");
									SysUtil.abort();
								}
							}
							supplier.put(col.get(i).getSupplier().getId().toString(),col.get(i));
							
							if(col.get(i).isIsMultiple()){
								for(int j=0;j<col.get(i).getEntry().size();j++){
									TenderAccepterResultEntryInfo entry=new TenderAccepterResultEntryInfo();
									entry.setSupplier(col.get(i).getSupplier());
									if(col.get(i).getSupplier().getGrade()!=null){
										String sid = col.get(i).getSupplier().getId().toString();
										if(map.get(sid)!=null) {
											entry.setInviteFix((InviteFixInfo) map.get(sid));
										}
									}
									entry.setRecordSeq(col.get(i).getEntry().get(j).getSeq());
									entry.setBidAmount(col.get(i).getEntry().get(j).getPrice());
									entry.setLastAmount(col.get(i).getEntry().get(j).getPrice());
									info.getEntrys().add(entry);
								}
							}else{
								TenderAccepterResultEntryInfo entry=new TenderAccepterResultEntryInfo();
								entry.setSupplier(col.get(i).getSupplier());
								if(col.get(i).getSupplier().getGrade()!=null){
									String sid = col.get(i).getSupplier().getId().toString();
									if(map.get(sid)!=null) {
										entry.setInviteFix((InviteFixInfo) map.get(sid));
									}
								}
								entry.setRecordSeq(0);
								entry.setBidAmount(col.get(i).getPrice());
								entry.setLastAmount(col.get(i).getPrice());
								info.getEntrys().add(entry);
							}
						}
					}
				}
			}
			if(info.getInviteProject()!=null){
				IPInviteListTypeEntryCollection listcol=IPInviteListTypeEntryFactory.getRemoteInstance().getIPInviteListTypeEntryCollection("select *,inviteListType.* from where head.id='"+info.getInviteProject().getId().toString()+"'");
				for(int i=0;i<listcol.size();i++){
					TenderAccepterRListEntryInfo entry=new TenderAccepterRListEntryInfo();
					entry.setId(BOSUuid.create(entry.getBOSType()));
					entry.setInviteListType(listcol.get(i).getInviteListType());
					entry.setNum(listcol.get(i).getNum());
					entry.setPrice(listcol.get(i).getPrice());
					entry.setAmount(listcol.get(i).getAmount());
					entry.setModel(listcol.get(i).getModel());
					entry.setRemark(listcol.get(i).getRemark());
					
					info.getListEntry().add(entry);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new TenderAccepterResultEntryInfo();
	}
	protected void closeDeleteAttachment(){
	}
	protected void kdtRecord_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.kdtRecord.getRow(e.getRowIndex());
			if(row.getCell(e.getColIndex()).getUserObject()==null) return;
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell(e.getColIndex()).getUserObject().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SupplierInviteRecordEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.combRange);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSpecial);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
		if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				boolean isHit=false;
				KDTable table=(KDTable) tableMaps.get(key[k]);
				for(int i=0;i<table.getRowCount();i++){
					IRow row=table.getRow(i);
					if( ((Boolean) table.getCell(i, "hit").getValue()).booleanValue() ) {
						isHit=true;
					}
					if(table.getCell(i, "totalSeq").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"�б�˳����Ϊ�գ�");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("totalSeq"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"��"+key[k]+"����б�˳����Ϊ�գ�");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("totalSeq"));
							SysUtil.abort();
						}
					}
					if(table.getCell(i, "lastAmount").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"�б�۲���Ϊ�գ�");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("lastAmount"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"��"+key[k]+"����б�۲���Ϊ�գ�");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("lastAmount"));
							SysUtil.abort();
						}
					}
					if(table.getCell(i, "cost").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"����ɲɹ��ĵ�����۲���Ϊ�գ�");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("cost"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"��"+key[k]+"�������ɲɹ��ĵ�����۲���Ϊ�գ�");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("cost"));
							SysUtil.abort();
						}
					}
				}
				if(!isHit){
					if(key[k].equals(0)){
						MsgBox.showWarning(this,"�б�������������һ����Ӧ���б꣡");
						SysUtil.abort();
					}else{
						MsgBox.showWarning(this,"��"+key[k]+"���������һ����Ӧ���б꣡");
						this.entryPanel.setSelectedIndex(k);
						SysUtil.abort();
					}
				}
			}
		}
		if(this.kdtInviteListEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"�ɹ���ϸ��Ϣ����Ϊ�գ�");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtInviteListEntry.getRowCount();i++){
			if(this.kdtInviteListEntry.getCell(i, "inviteListType").getValue()==null) {
				FDCMsgBox.showWarning(this,"�ɹ���ϸ����Ϊ�գ�");
				this.kdtInviteListEntry.getEditManager().editCellAt(i, this.kdtInviteListEntry.getColumnIndex("inviteListType"));
				SysUtil.abort();
			}
			if(this.kdtInviteListEntry.getCell(i, "amount").getValue()==null) {
				FDCMsgBox.showWarning(this,"�б����Ϊ�գ�");
				this.kdtInviteListEntry.getEditManager().editCellAt(i, this.kdtInviteListEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
	}
	HashMap<Integer, KDTable> tableMap = new HashMap<Integer, KDTable>();
	String[] className = new String[] {
			SupplierQualifyEditUI.class.getName(),     InviteTenderPlanningEditUI.class.getName(),
			InviteDocumentsEditUI.class.getName(),     SupplierInviteRecordEditUI.class.getName(),
			InviteClarifyEditUI.class.getName(),       MaterialSampleEditUI.class.getName(),
			InviteBidEvaluationEditUI.class.getName(), InviteChangeFormEditUI.class.getName()//�����ı�ɹ���ʽ��ʾ
	};
	KDScrollPane[] panes = new KDScrollPane[] {
			this.supplierPanel, this.planningPanel, this.documentPanel, this.recordPanel,
			this.clarifyPanel, this.samplePanel, this.reportPanel,this.changInviteTypeApplication
	};
	
	protected void parentPanel_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
		if(this.editData == null ) {
			return;
		}
		int index = this.parentPanel.getSelectedIndex();
		if( tableMap.get(index)==null ) {
			String tblBaseInviteStrXML= null;
			String prjID = this.editData.getInviteProject().getId().toString();
			if( index==3) {
				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"> <Styles> <c:Style id=\"sCol0\"> <c:Protection hidden=\"true\"/> </c:Style> </Styles> <Table id=\"KDTable\"> <t:Sheet name=\"sheet1\"> <t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"> <t:ColumnGroup> <t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\"/> <t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\"/> <t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\"/> <t:Column t:key=\"openDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\"/> <t:Column t:key=\"place\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\"/> <t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\"/> <t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\"/> <t:Column t:key=\"returnDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\"/> <t:Column t:key=\"times\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\"/> <t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\"/> <t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\"/> <t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\"/> <t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\"/> <t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\"/> <t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\"/> </t:ColumnGroup> <t:Head> <t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"> <t:Cell>$Resource{id}</t:Cell> <t:Cell>״̬</t:Cell> <t:Cell>���ݱ���</t:Cell> <t:Cell>��������</t:Cell> <t:Cell>����ص�</t:Cell> <t:Cell>��Ӧ��</t:Cell> <t:Cell>��Ӧ�̱���</t:Cell> <t:Cell>�ر�����</t:Cell> <t:Cell>�ر����</t:Cell> <t:Cell>��ע</t:Cell> <t:Cell>���Ʋ���</t:Cell> <t:Cell>������</t:Cell> <t:Cell>��������</t:Cell> <t:Cell>������</t:Cell> <t:Cell>��������</t:Cell> </t:Row> </t:Head> </t:Table> <t:SheetOptions> <t:MergeBlocks> <t:Head/> </t:MergeBlocks> </t:SheetOptions> </t:Sheet> </Table> </DocRoot>  ";
//				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"openDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"place\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"returnDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"times\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{openDate}</t:Cell><t:Cell>$Resource{place}</t:Cell><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{returnDate}</t:Cell><t:Cell>$Resource{times}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{respDept}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

			}else {
				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"> <Styles> <c:Style id=\"sCol0\"> <c:Protection hidden=\"true\"/> </c:Style> <c:Style id=\"sCol5\"><c:Protection locked=\"true\"/></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\"/></c:Style></Styles> <Table id=\"KDTable\"> <t:Sheet name=\"sheet1\"> <t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"> <t:ColumnGroup> <t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\"/> <t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\"/> <t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\"/> <t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\"/> <t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\"/> <t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\"/> <t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\"/> <t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\"/> </t:ColumnGroup> <t:Head> <t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"> <t:Cell>$Resource{id}</t:Cell> <t:Cell>״̬</t:Cell> <t:Cell>���ݱ���</t:Cell> <t:Cell>���Ʋ���</t:Cell> <t:Cell>������</t:Cell> <t:Cell>��������</t:Cell> <t:Cell>������</t:Cell> <t:Cell>��������</t:Cell> </t:Row> </t:Head> </t:Table> <t:SheetOptions> <t:MergeBlocks> <t:Head/> </t:MergeBlocks> </t:SheetOptions> </t:Sheet> </Table> </DocRoot>";
			}
			
			KDTable table = new KDTable();
			table.setFormatXml(tblBaseInviteStrXML);
			table.checkParsed();
			table.getStyleAttributes().setLocked(true);
			if(table.getColumn("respDept") != null) {
				table.getColumn("respDept").getStyleAttributes().setHided(true);
			}
			
			String fmt = "yyyy-MM-dd";
			table.getColumn(COL_CREATEDATE).getStyleAttributes().setLocked(true);
			table.getColumn(COL_AUDITDATE).getStyleAttributes().setLocked(true);
			table.getColumn(COL_CREATEDATE).getStyleAttributes().setNumberFormat(fmt);
			table.getColumn(COL_AUDITDATE).getStyleAttributes().setNumberFormat(fmt);
			
			if( index==3) {
				table.getColumn("openDate").getStyleAttributes().setLocked(true);
				table.getColumn("returnDate").getStyleAttributes().setLocked(true);
				table.getColumn("openDate").getStyleAttributes().setNumberFormat(fmt);
				table.getColumn("returnDate").getStyleAttributes().setNumberFormat(fmt);
			}
//			table.parseFormatXml(tblBaseInviteStrXML);
			
			Iterator<BaseInviteInfo> iter=null;
			if(index==0) {//��Χ��Ӧ��ҳǩ
				SupplierQualifyCollection recordCols = SupplierQualifyListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==1) {
				InviteTenderPlanningCollection recordCols = InviteTenderPlanningListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==2) {
				InviteDocumentsCollection recordCols = InviteDocumentsListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==3) {
				SupplierInviteRecordCollection recordCols = SupplierInviteRecordListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==4) {
				InviteClarifyCollection recordCols = InviteClarifyListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==5) {
				MaterialSampleCollection recordCols = MaterialSampleListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==6) {
				InviteBidEvaluationCollection recordCols = InviteBidEvaluationListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}//���Ӹı�ɹ���ʽ��ʾ
			else if(index==7)
			{
				com.kingdee.eas.fdc.invite.InviteChangeFormCollection recordCols =com.kingdee.eas.fdc.invite.client.InviteChangeFormListUICTEx.getBillList(prjID);
				
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}
			
			if(iter != null) {
				while (iter.hasNext()) {
					Object obj = iter.next();
					if(obj instanceof BaseInviteInfo)
					{
						fillTable(table,(BaseInviteInfo ) obj);
					}else if(obj instanceof InviteChangeFormInfo)
					{
						fillTable(table, (InviteChangeFormInfo)obj );
					}
						
				}
			}
//			
			
			tableMap.put(index, table);
			panes[index].setViewportView(table);
			
			table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
		            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		                try {
		                	 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
		         	        	setCursorOfWair();
		         	        	int index = parentPanel.getSelectedIndex();
		         				IRow row=KDTableUtil.getSelectedRow( tableMap.get(index) );
		         				String id = row.getCell("id").getValue().toString();
		         				UIContext uiContext = new UIContext(this);
	         					uiContext.put(UIContext.ID, id);
		         				
	         					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
         								className[index], uiContext, null,"VIEW");
		         				uiWindow.show();
		         				setCursorOfDefault();
		         	      }
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                }
		            }
		        });
		}
		
	}
	
	/**
	 * ���ӷ������������
	 * @param table
	 * @param info
	 * @throws BOSException
	 */
	protected void fillTable(KDTable table, com.kingdee.eas.fdc.invite.InviteChangeFormInfo info) throws BOSException {
		IRow row = table.addRow();

		if (info != null) {
			row.getCell(COL_ID).setValue(info.getId());
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_STATE).setValue(info.getState());
//
//			if (info.get != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
//			}
			if (info.getCreator() != null) {
				row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			}
			row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
			if (info.getAuditor() != null) {
				row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
			}
			row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
			
			
			row.setUserObject(info);
		}
	}
	
	
	
	protected void fillTable(KDTable table, BaseInviteInfo info) throws BOSException {
		IRow row = table.addRow();

		if (info != null) {
			row.getCell(COL_ID).setValue(info.getId());
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_STATE).setValue(info.getState());

			if (info.getRespDept() != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
			}
			if (info.getCreator() != null) {
				row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			}
			row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
			if (info.getAuditor() != null) {
				row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
			}
			row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
			
			if(info instanceof SupplierInviteRecordInfo ) {
				SupplierInviteRecordInfo sInfo = (SupplierInviteRecordInfo) info;
				row.getCell("openDate").setValue(sInfo.getOpenDate());
				row.getCell("place").setValue(sInfo.getPlace());
				if(sInfo.getSupplier()!=null){
					row.getCell("supplier").setValue(sInfo.getSupplier().getName());
				}
				row.getCell("price").setValue(sInfo.getPrice());
				row.getCell("returnDate").setValue(sInfo.getReturnDate());
				row.getCell("times").setValue(Integer.valueOf(sInfo.getTimes()));
				row.getCell("remark").setValue(sInfo.getRemark());
			}
			row.setUserObject(info);
		}
	}
	
	protected final String COL_ID = "id";
    protected final String COL_STATE = "state";
    protected final String COL_RESPDEPT = "respDept";
    protected final String COL_CREATOR = "creator";
    protected final String COL_CREATEDATE = "createDate";
    protected final String COL_AUDITOR = "auditor";
    protected final String COL_AUDITDATE = "auditDate";
    protected final String PROJECTDATE_COL = "prjDate";
    protected final String AUDITTIME_COL = "auditTime";
    protected final String COL_NUMBER = "number";
    
    public final static String INVITEPROJECTID = "inviteProjectId";
    public final static String SUPPLIERIDSET = "supplierIDSet";
    
    private HashMap getProviderDataMap() {
    	HashMap map = new HashMap();
    	HashSet set = new HashSet();
    	String inviteProjectId = this.editData.getInviteProject().getId().toString();
    	
    	if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table=(KDTable) tableMaps.get(key[k]);
				
				for(int i=0;i<table.getRowCount();i++){
					SupplierStockInfo supplier=(SupplierStockInfo)table.getCell(i, "supplier").getValue();
					if(supplier !=null ) {
		    			set.add(supplier.getId().toString());
		    		}
				}
			}
		}
    	map.put(TenderAccepterResultEditUI.INVITEPROJECTID, inviteProjectId);
    	map.put(TenderAccepterResultEditUI.SUPPLIERIDSET, set);
    	return map;
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
		TenderAccepterResultPrintDataProvider dataPvd = new TenderAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
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
		TenderAccepterResultPrintDataProvider dataPvd = new TenderAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	private boolean isHasAttachment = false;
	public void fillAttachmentList(){
		this.cmbAttachment.removeAllItems();
		String boId = null;
		if(this.editData.getId() == null){
			return;
		}else{
			boId = this.editData.getId().toString();
		}
		
		if(boId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boId));
			
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			 try {
				cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
//				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
			if(cols != null && cols.size()>0){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.cmbAttachment.addItem(attachment);
				}
				this.isHasAttachment = true;
			}else{
				this.isHasAttachment =false;
			}
		}
		this.btnViewAttachment.setEnabled(this.isHasAttachment);
	}

	public void actionViewAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionViewAttachment_actionPerformed(e);
    	String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionAttachment_actionPerformed(e);
    	fillAttachmentList();
    }
	protected void lockContainer(Container container) {
   	 if(lblAttachmentContainer.getName().equals(container.getName())){
         	return;
         }
         else{
         	super.lockContainer(container);
         }
   }
}