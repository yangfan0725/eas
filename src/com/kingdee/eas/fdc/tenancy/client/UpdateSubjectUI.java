/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
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
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * ��Ŀά��
 */
public class UpdateSubjectUI extends AbstractUpdateSubjectUI
{
    private static final Logger logger = CoreUIObject.getLogger(UpdateSubjectUI.class);

    public UpdateSubjectUI() throws Exception
    {
        super();
    }

    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        FDCSQLBuilder builder=new FDCSQLBuilder();
        for(int i=0;i<kDTable1.getRowCount();i++){
        	builder.appendSql("update T_BDC_FDCReceivingBillEntry set FRevAccountID=?,FOppAccountID=? where fid=?");
        	IRow row=kDTable1.getRow(i);
        	String fid=row.getCell("entryID").getValue().toString();
        	if(row.getCell("account").getValue()==null){
        		MsgBox.showError("��"+(i+1)+"���п�Ŀû��ѡ��");
        		abort();
        	}
        	AccountViewInfo rev=(AccountViewInfo)row.getCell("account").getValue();
        	if(row.getCell("oppAccount").getValue()==null){
        		MsgBox.showError("��"+(i+1)+"���п�Ŀû��ѡ��");
        		abort();
        	}
        	AccountViewInfo opp=(AccountViewInfo)row.getCell("oppAccount").getValue();
        	String revBillType=this.getUIContext().get("revBillType").toString();
        	if(revBillType.equals("�տ�") || revBillType.equals("�˿�") || revBillType.equals("����")){
        		builder.addParam(rev.getId().toString());
        		builder.addParam(opp.getId().toString());
        	}
        	else{
	        	builder.addParam(opp.getId().toString());
	        	builder.addParam(rev.getId().toString());
        	}
        	builder.addParam(fid);
        	builder.execute();
        	builder.clear();
        }
        MsgBox.showInfo("����ά����Ŀ�ɹ���");
    }

	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------ת6.0���޸�,��Ŀ����CU����,���ݲ�����֯���и���
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		evi.setFilter(filter);
		return evi;
	}
    
    public void onLoad() throws Exception {
    	kDTable1.checkParsed();
    	super.onLoad();
    	FDCTableHelper.disableDelete(kDTable1);
    	//Ϊ�˱��ֿ�Ŀѡ���һ����   ���տ ����copy������Ŀѡ��
    	KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		kDTable1.getColumn("account").setEditor(f7Editor);
		
		
		KDBizPromptBox oppSubject = new KDBizPromptBox();
		oppSubject.setEntityViewInfo(view);
		oppSubject.setSelector(opseelect);
		oppSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		oppSubject.setEditFormat("$number$");
		oppSubject.setCommitFormat("$number$");
		ICellEditor oppEditor = new KDTDefaultCellEditor(oppSubject);
		kDTable1.getColumn("oppAccount").setEditor(oppEditor);
		initTable();
		SimpleKDTSortManager.setTableSortable(kDTable1);
		gatheringSubject.addDataChangeListener(new DataChangeListener()
		{
			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getOldValue() instanceof AccountViewInfo && eventObj.getNewValue() instanceof AccountViewInfo) {
					if (!((AccountViewInfo) eventObj.getOldValue()).getId().toString().equals(((AccountViewInfo) eventObj.getNewValue()).getId().toString())) {
						if (MsgBox.showConfirm2("�Ƿ��Զ����������µ�ͬһ�е����п�Ŀ?") == MsgBox.YES){
							setAllAccount((AccountViewInfo)eventObj.getNewValue(),"account");
						}					
					} 
				}					
			}			
		});
		
		oppSubject.addDataChangeListener(new DataChangeListener()
		{
			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getOldValue() instanceof AccountViewInfo && eventObj.getNewValue() instanceof AccountViewInfo) {
					if (MsgBox.showConfirm2("�Ƿ��Զ����������µ�ͬһ�е����п�Ŀ?") == MsgBox.YES){
						setAllAccount((AccountViewInfo)eventObj.getNewValue(),"oppAccount");
					}				
				} 
			}			
		});
		
		
    }
    
    public void setAllAccount(AccountViewInfo account,String columnName)
    {
    	int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
    	for(int i=rowIndex;i<kDTable1.getRowCount();i++)
    	{
    		IRow row = kDTable1.getRow(i);
    		row.getCell(columnName).setValue(account);
    	}
    }
    /**
     *  ��ȡ�� �տѡ�еĵ��ݵ���Ϣ
     */
    /**
     * @throws Exception
     */
    private void initTable() throws Exception{
    	MoneySysTypeEnum sysType = (MoneySysTypeEnum)getUIContext().get("sysType");
    	if(MoneySysTypeEnum.TenancySys.equals(sysType))
    	{
    		this.kDTable1.getColumn("purchaseNumber").getStyleAttributes().setHided(true);
    		this.kDTable1.getColumn("sincerityNumber").getStyleAttributes().setHided(true);
    	}else if(MoneySysTypeEnum.SalehouseSys.equals(sysType))
    	{
    		this.kDTable1.getColumn("tenancyContract.number").getStyleAttributes().setHided(true);
    		this.kDTable1.getColumn("obligateNumber").getStyleAttributes().setHided(true);
    	}
    	HashSet set=(HashSet)this.getUIContext().get("list");
    	EntityViewInfo view=new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("id");
    	sic.add("head.id");
    	sic.add("head.number");
    	sic.add("head.description");
    	sic.add("head.revBillType");
    	sic.add("head.sellProject.id");
    	sic.add("head.sellProject.name");
    	sic.add("head.tenancyObj.id");
    	sic.add("head.tenancyObj.name");
    	sic.add("head.tenancyObj.number");
    	sic.add("head.obligateObj.id");
    	sic.add("head.obligateObj.name");
    	sic.add("head.obligateObj.number");
    	
    	sic.add("head.purchaseObj.id");
    	sic.add("head.purchaseObj.name");
    	sic.add("head.purchaseObj.number");
    	sic.add("head.sincerityObj.id");
    	sic.add("head.sincerityObj.name");
    	sic.add("head.sincerityObj.number");
    	
    	sic.add("head.customer.id");
    	sic.add("head.customer.name");
    	sic.add("head.room.id");
    	sic.add("head.room.name");
    	sic.add("head.room.number");
    	sic.add("room.id");
    	sic.add("room.name");
    	sic.add("room.number");
    	sic.add("moneyDefine.id");
    	sic.add("moneyDefine.moneyType");
    	sic.add("moneyDefine.name");
    	sic.add("revAccountBank.id");
    	sic.add("revAccountBank.bankAccountNumber");
    	sic.add("settlementType.id");
    	sic.add("settlementType.name");
    	sic.add("revAccount.id");
    	sic.add("revAccount.name");
    	sic.add("revAccount.number");
    	sic.add("oppAccount.id");
    	sic.add("oppAccount.name");
    	sic.add("oppAccount.number");
    	sic.add("sourceEntries.id");
    	sic.add("sourceEntries.fromRevListId");
    	sic.add("sourceEntries.fromRevListType");
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("head.id",set,CompareType.INCLUDE));
    	SorterItemCollection sc=new SorterItemCollection();
		SorterItemInfo sort=new SorterItemInfo("head.number");
		sort.setSortType(SortType.ASCEND);
		sc.add(sort);
		view.setSelector(sic);
		view.setFilter(filter);
		view.setSorter(sc);
    	FDCReceivingBillEntryCollection coll=FDCReceivingBillEntryFactory.getRemoteInstance().getFDCReceivingBillEntryCollection(view);
    	String revBillType=this.getUIContext().get("revBillType").toString();
    	SelectorItemCollection sels=new SelectorItemCollection(); //���� ֻ��Ϊ��ת������Ĳ�ѯ
    	sels.add("id");
    	sels.add("moneyDefine.id");
    	sels.add("moneyDefine.name");
    	sels.add("moneyDefine.moneyType");
    	int a=0;
    	int b=0;
    	int c=0;
    	int d=0;
    	String desc = "";
    	for(int i=0;i<coll.size();i++){
    		FDCReceivingBillEntryInfo info=coll.get(i);
    		IRow row=kDTable1.addRow();
    		row.getCell("ID").setValue(info.getHead().getId().toString());
    		row.getCell("entryID").setValue(info.getId().toString());
    		row.getCell("revBillType").setValue(info.getHead().getRevBillType());
    		row.getCell("number").setValue(info.getHead().getNumber());
    		
    		
    		if(MoneySysTypeEnum.TenancySys.equals(sysType))
    		{
    			if(info.getRoom()!=null){
        			row.getCell("room").setValue(info.getRoom().getName()==null?null:info.getRoom().getName());
        		}
    			if(info.getHead().getTenancyObj()!=null){
        			row.getCell("tenancyContract.number").setValue(info.getHead().getTenancyObj().getNumber());
        		}
    			if(info.getHead().getObligateObj()!=null)
    			{
    				row.getCell("obligateNumber").setValue(info.getHead().getObligateObj().getNumber());
    			}
    		}else if(MoneySysTypeEnum.SalehouseSys.equals(sysType))
    		{
    			if(info.getHead().getRoom()!=null){
        			row.getCell("room").setValue(info.getHead().getRoom().getName()==null?null:info.getHead().getRoom().getName());
        		}
    			if(info.getHead().getPurchaseObj()!=null){
        			row.getCell("purchaseNumber").setValue(info.getHead().getPurchaseObj().getNumber());
        		}
    			if(info.getHead().getSincerityObj()!=null)
    			{
    				row.getCell("sincerityNumber").setValue(info.getHead().getSincerityObj().getNumber());
    			}
    		}
    		if(info.getHead().getTenancyObj()!=null){
    			row.getCell("tenancyContract.number").setValue(info.getHead().getTenancyObj().getNumber());
    		}
    		row.getCell("sellProject.name").setValue(info.getHead().getSellProject().getName());
    		row.getCell("tenCustomer").setValue(info.getHead().getCustomer().getName());
    		row.getCell("moneyDefine.name").setValue(info.getMoneyDefine().getName());
    		if(info.getSettlementType()!=null && info.getSettlementType().getName()!=null){
    			row.getCell("settlementType").setValue(info.getSettlementType().getName());
    		}
    		//�տ� �˿�ʱ  
    		if(revBillType.equals("�տ�") || revBillType.equals("�˿�") || revBillType.equals("����")){
    			d++;
    			//���˿��Ŀ
    			row.getCell("account").setValue(info.getRevAccount()==null?null:info.getRevAccount());
    			//�Է���Ŀ
    			row.getCell("oppAccount").setValue(info.getOppAccount()==null?null:info.getOppAccount());
    			//�տ�����˿�  ���������͵�����ʾ����һ�� ������Ҫ���� �տ��˺�
    			if(info.getRevAccountBank()!=null){
    				row.getCell("receiveAccount").setValue(info.getRevAccountBank().getBankAccountNumber()==null?null:info.getRevAccountBank().getBankAccountNumber());
    			}
    			if(revBillType.equals("����"))
    			{
    				desc = info.getHead().getDescription(); 
    				this.getUIContext().put("des", desc);
    				if(desc!=null){
    					if(desc.startsWith("���ת��"))
    						a++;
        				else if(desc.startsWith("����˿�"))
        					b++;
        				else
        					c++;
    				}
    			}
    		}
    		if(revBillType.equals("ת��")){//ת��   ������Ҫ���� ת��������  ���� ��ת������ͻ��Ƕ��һ�ġ�����
    			//���������տ��Ŀ ����ת����Ŀ  ת���Ŀʱ�ǶԷ���Ŀ
    			row.getCell("oppAccount").setValue(info.getRevAccount()==null?null:info.getRevAccount());//ת��
    			row.getCell("account").setValue(info.getOppAccount()==null?null:info.getOppAccount());//ת��
    			TransferSourceEntryCollection tsec=info.getSourceEntries();
//    			MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.getUIContext().get("sysType");
    			for(int j=0;j<tsec.size();j++){
    				a++;
    				if(MoneySysTypeEnum.TenancySys.equals(sysType))
    				{
    					IRevListInfo rev=TENRevHelper.getRevListInfo(null, tsec.get(j).getFromRevListType(),tsec.get(j).getFromRevListId(),sels);
    					if(tsec.size()==1){
        					row.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        				}
        				else{//����ж��� ת������  ����Ҫ������ʾ
        					if(j==0){
        						row.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        					}
        					else{
    	    					IRow r=kDTable1.addRow();
    	    					r=(IRow)row.clone();
    	    					r.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        					}
        				}
    				}else if(MoneySysTypeEnum.SalehouseSys.equals(sysType))
    				{
    					ICoreBase iface = SHEComHelper.getRevListBizInterface(null, tsec.get(j).getFromRevListType());
    					IRevListInfo rev = (IRevListInfo) iface.getValue(new ObjectUuidPK(tsec.get(j).getFromRevListId()),sels);
    					if(tsec.size()==1){
        					row.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        				}
        				else{//����ж��� ת������  ����Ҫ������ʾ
        					if(j==0){
        						row.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        					}
        					else{
    	    					IRow r=kDTable1.addRow();
    	    					r=(IRow)row.clone();
    	    					r.getCell("receiveAccount").setValue(rev.getMoneyDefine().getName());
        					}
        				}
//    					IRevListInfo rev=TENRevHelper.getRevListInfo(null, tsec.get(j).getFromRevListType(),tsec.get(j).getFromRevListId(),sels);
    				}
    			}
    		}
    	}
    	if(a==coll.size() || b==coll.size() || c==coll.size() || d==coll.size()){
    		if(revBillType.equals("����")){//����
        		if(desc!=null){
    				if(desc.startsWith("���ת��"))
    				{
    					kDTable1.getHeadRow(0).getCell("account").setValue("ת���Ŀ");
    	    		    kDTable1.getHeadRow(0).getCell("oppAccount").setValue("ת����Ŀ");
    	    		    kDTable1.getHeadRow(0).getCell("moneyDefine.name").setValue("ת�����");
    	    		    kDTable1.getHeadRow(0).getCell("receiveAccount").setValue("ת������");
    				}else if(desc.startsWith("����˿�"))
    				{
    					kDTable1.getHeadRow(0).getCell("account").setValue("�˿��Ŀ");
    	    		    kDTable1.getHeadRow(0).getCell("oppAccount").setValue("�Է���Ŀ");
    				}else
    				{
    					kDTable1.getHeadRow(0).getCell("account").setValue("�տ��Ŀ");
    	    		    kDTable1.getHeadRow(0).getCell("oppAccount").setValue("�Է���Ŀ");
    				}					
    			}
        	}
    	}else
    	{
    		MsgBox.showInfo("ѡ��ĵ���������ת���Ŀ��һ��");
    		this.abort();
    	}
    	//����Ԫ��ϲ�
    	int tempTop = 0;
		String tempReference = "";
		for (int i = 0; i < this.kDTable1.getRowCount(); i++)
		{
			IRow row = this.kDTable1.getRow(i);
			
			String id = (String)row.getCell("ID").getValue();
						
			if (!tempReference.equalsIgnoreCase(id))
			{
				tempTop = i;
				tempReference = id;
			} else
			{
				// �ںϵ�Ԫ��
//				this.kDTable1.getGroupManager().setGroup(true);
//				this.kDTable1.getColumn("revBillType").setGroup(true);
//				this.kDTable1.getColumn("number").setGroup(true);
//				this.kDTable1.getColumn("sellProject.name").setGroup(true);
//				this.kDTable1.getGroupManager().group();
				
				//this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("revBillType"), i, this.kDTable1.getColumnIndex("revBillType"));
//				this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("number"), i, this.kDTable1.getColumnIndex("number"));
//				this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("sellProject.name"), i, this.kDTable1.getColumnIndex("sellProject.name"));
//				this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("room"), i, this.kDTable1.getColumnIndex("room"));
				if(MoneySysTypeEnum.TenancySys.equals(sysType))
				{
//					this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("tenancyContract.number"), i, this.kDTable1.getColumnIndex("tenancyContract.number"));
//					this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("obligateNumber"), i, this.kDTable1.getColumnIndex("obligateNumber"));
				}else if(MoneySysTypeEnum.SalehouseSys.equals(sysType))
				{
//					this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("purchaseNumber"), i, this.kDTable1.getColumnIndex("purchaseNumber"));
//					this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("sincerityNumber"), i, this.kDTable1.getColumnIndex("sincerityNumber"));
				}
				
//				this.kDTable1.getMergeManager().mergeBlock(tempTop, this.kDTable1.getColumnIndex("tenCustomer"), i, this.kDTable1.getColumnIndex("tenCustomer"));
			}
		}
    }
    
    public void loadFields() {
    	kDTable1.checkParsed();
    	super.loadFields();
    	kDTable1.getColumn("revBillType").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("number").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("sellProject.name").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("room").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("tenancyContract.number").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("obligateNumber").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("purchaseNumber").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("sincerityNumber").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("moneyDefine.name").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("receiveAccount").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("tenCustomer").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("account").setRequired(true);
    	kDTable1.getColumn("oppAccount").setRequired(true);
    	String revBillType=this.getUIContext().get("revBillType").toString();
    	if(revBillType.equals("�տ�")){//�տ�
    		kDTable1.getHeadRow(0).getCell("account").setValue("�տ��Ŀ");
    		kDTable1.getHeadRow(0).getCell("oppAccount").setValue("�Է���Ŀ");
    	}
    	if(revBillType.equals("�˿�")){//�˿�
    		kDTable1.getHeadRow(0).getCell("account").setValue("�˿��Ŀ");
    		kDTable1.getHeadRow(0).getCell("oppAccount").setValue("�Է���Ŀ");
    	}
    	if(revBillType.equals("ת��")){//ת��
    		kDTable1.getHeadRow(0).getCell("account").setValue("ת���Ŀ");
    		kDTable1.getHeadRow(0).getCell("oppAccount").setValue("ת����Ŀ");
    		kDTable1.getHeadRow(0).getCell("moneyDefine.name").setValue("ת�����");
    		kDTable1.getHeadRow(0).getCell("receiveAccount").setValue("ת������");
    	}
    	
    }

	protected IObjectValue createNewData() {
		FDCReceivingBillInfo info=new FDCReceivingBillInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCReceivingBillFactory.getRemoteInstance();
	}

}