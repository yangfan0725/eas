package com.kingdee.eas.fdc.contract.programming.client;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.text.Position.Bias;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.AimCost;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;

public class CreateProTableRow {
	
	protected DataBinder dataBinder = null;
	
	public CreateProTableRow(DataBinder dataBinder){
		this.dataBinder = dataBinder;
	}
	
	/**
     * ��ָ������������У����������һ�У�
     *
     * @param table
     */
    public void addLine(KDTable table , int level) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table , level , null);
        IRow row = table.addRow();
        loadLineFields(table, row, detailData);
    }
    
    /**
     * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У��������ϼ������ݣ������ϼ��������
     *
     * @param table
     */
    public void insertLine(KDTable table , int rowIndex , int level , ProgrammingContractInfo head) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table , level , head);
        IRow row = table.addRow(rowIndex);
        loadLineFields(table, row, detailData);
        loadLineFields(table, table.getRow(rowIndex-1), head);
        //�����ж���
        table.getRow(rowIndex-1).setUserObject(head);
    }
    
    /**
     * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У���������������������������¼����һ��������head�������ͬ����ʹ��head.parent��
     *
     * @param table
     */
    public void insertSameLine(KDTable table , int rowIndex , int level , ProgrammingContractInfo head) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createSameNewDetailData(table , level , head);
        IRow row = table.addRow(rowIndex);
        loadLineFields(table, row, detailData);
    }
    

	/**
     * ��ָ�������ɾ����
     *
     * @param table
     */
    public void removeLine(KDTable table , ArrayList list) throws Exception
    {
    	if(list == null || list.size() == 0)
    		return;
    	int top = 0;
    	for(int i = list.size()-1 ; i >= 0  ; i--){
    		top = ((Integer)list.get(i)).intValue();
    		IObjectValue detailData = (IObjectValue) table.getRow(top).getUserObject();
            table.removeRow(top);
            IObjectCollection collection = (IObjectCollection) table
                    .getUserObject();
			if (collection != null){
				if (detailData != null) {
					collection.removeObject(top);
				}
			}
    	}
    }
    
    /**
     * ��ָ�������ɾ��ָ������
     * @param table
     * @param rowIndex
     * @throws Exception
     */
    public void removeLine(KDTable table , int rowIndex) throws Exception
    {
//		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
//		IObjectCollection collection = (IObjectCollection) table.getUserObject();
//		if (collection != null) {
//			if (detailData != null) {
//				collection.removeObject(rowIndex);
//			}
//		}
    }
    
    /**
     * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
     */
    protected IObjectValue createSameNewDetailData(KDTable table , int level , ProgrammingContractInfo head)
    {
        if(table == null)
        {
            return null;
        }
        ProgrammingContractInfo newDetailInfo = new ProgrammingContractInfo();
        newDetailInfo.setId(BOSUuid.create("ECE079DB"));
        newDetailInfo.setLevel(level);
        newDetailInfo.setEstimateAmount(FDCHelper.ZERO);
        if(head != null)newDetailInfo.setParent(head);
       
        newDetailInfo.setBalance(FDCHelper.ZERO);
	     newDetailInfo.setControlBalance(FDCHelper.ZERO);
	     newDetailInfo.setAmount(FDCHelper.ZERO);
	     newDetailInfo.setControlAmount(FDCHelper.ZERO);
	     newDetailInfo.setSignUpAmount(FDCHelper.ZERO);
	     newDetailInfo.setChangeAmount(FDCHelper.ZERO);
	     newDetailInfo.setSettleAmount(FDCHelper.ZERO);
        return (IObjectValue) newDetailInfo;
    }
    /**
     * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
     */
    protected IObjectValue createNewDetailData(KDTable table , int level , ProgrammingContractInfo head)
    {
        if(table == null)
        {
            return null;
        }
        ProgrammingContractInfo newDetailInfo = new ProgrammingContractInfo();
        newDetailInfo.setId(BOSUuid.create("ECE079DB"));
        newDetailInfo.setLevel(level);
        newDetailInfo.setEstimateAmount(FDCHelper.ZERO);
        if(head != null){
        	newDetailInfo.setParent(head);
        	//clone �ϼ��ĳɱ����� �������� �����ϼ��ĳɱ����ɣ������������
        	cloneHead(head,newDetailInfo);
        	head.getCostEntries().clear();
        	head.getEconomyEntries().clear();
        } 
       
        return (IObjectValue) newDetailInfo;
    }
    
    public void cloneHead(ProgrammingContractInfo head,ProgrammingContractInfo newDetailInfo) {
    	ProgrammingContracCostCollection  costColl = (ProgrammingContracCostCollection)head.getCostEntries();
    	newDetailInfo.getCostEntries().clear();
    	for(int i = 0 ; i < costColl.size(); i++){
    		ProgrammingContracCostInfo oldInfo = costColl.get(i);
    		ProgrammingContracCostInfo info = (ProgrammingContracCostInfo)oldInfo.clone();
    		info.setContract(newDetailInfo);
    		info.setId(null);
    		newDetailInfo.getCostEntries().add(info);
//    		oldInfo.setGoalCost(FDCHelper.ZERO);
//    		oldInfo.setAssigned(FDCHelper.ZERO);
//    		oldInfo.setAssigning(FDCHelper.ZERO);
    		oldInfo.setContractAssign(FDCHelper.ZERO);
    	}
    	ProgrammingContractEconomyCollection economyColl = (ProgrammingContractEconomyCollection)head.getEconomyEntries();
    	newDetailInfo.getEconomyEntries().clear();
    	for(int i = 0 ; i < economyColl.size(); i++){
    		ProgrammingContractEconomyInfo oldInfo = economyColl.get(i);
    		ProgrammingContractEconomyInfo info = (ProgrammingContractEconomyInfo)oldInfo.clone();
    		info.setContract(newDetailInfo);
    		info.setId(null);
    		newDetailInfo.getEconomyEntries().add(info);
    		oldInfo.setAmount(FDCHelper.ZERO);
    		oldInfo.setCondition(null);
    		oldInfo.setPaymentDate(null);
    	}
	    newDetailInfo.setBalance(head.getBalance());
		 head.setBalance(FDCHelper.ZERO);
	     newDetailInfo.setControlBalance(head.getControlBalance());
	     head.setControlBalance(FDCHelper.ZERO);
	     newDetailInfo.setAmount(head.getAmount());
	     head.setAmount(FDCHelper.ZERO);
	     newDetailInfo.setControlAmount(head.getControlAmount());
	     head.setControlAmount(FDCHelper.ZERO);
	     newDetailInfo.setSignUpAmount(head.getSignUpAmount());
	     head.setSignUpAmount(FDCHelper.ZERO);
	     newDetailInfo.setChangeAmount(head.getChangeAmount());
	     head.setChangeAmount(FDCHelper.ZERO);
	     newDetailInfo.setSettleAmount(head.getSettleAmount());
	     head.setSettleAmount(FDCHelper.ZERO);
	     newDetailInfo.setCostAccountNames(head.getCostAccountNames());
	     head.setCostAccountNames(null);
	     
	     newDetailInfo.setContractType(head.getContractType());
	     head.setContractType(null);
	     
	     newDetailInfo.setBuildPrice(head.getBuildPrice());
	     head.setBuildPrice(null);
	     newDetailInfo.setIsInput(head.isIsInput());
	     head.setIsInput(false);
	     newDetailInfo.setQuantities(head.getQuantities());
	     head.setQuantities(null);
	     newDetailInfo.setUnit(head.getUnit());
	     head.setUnit(null);
	     newDetailInfo.setPrice(head.getPrice());
	     head.setPrice(null);
	     
	     newDetailInfo.setPaperDate(head.getPaperDate());
	     head.setPaperDate(null);
	     newDetailInfo.setDocumentsAuditDate(head.getDocumentsAuditDate());
	     head.setDocumentsAuditDate(null);
	     newDetailInfo.setResultAuditDate(head.getResultAuditDate());
	     head.setResultAuditDate(null);
	     newDetailInfo.setContractAuditDate(head.getContractAuditDate());
	     head.setContractAuditDate(null);
	     newDetailInfo.setEnterAuditDate(head.getEnterAuditDate());
	     head.setEnterAuditDate(null);
	}
    public BigDecimal getAllContractAssign(ProgrammingContractInfo pcInfo,ProgrammingContractCollection pcCollection,CostAccountInfo caInfo, boolean flag) {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (flag) {
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {
						if(costAccountInfo.getLongNumber()!=null){
							if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
								BigDecimal contractAssign = pccInfo.getContractAssign();
								if (contractAssign == null) {
									contractAssign = FDCHelper.ZERO;
								}
								allContractAssign = allContractAssign.add(contractAssign);
							}
						}
					}
				}
			} else {
				if (!programmingContractInfo.getId().toString().equals(pcInfo.getId().toString())) {
					ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
					for (int j = 0; j < costEntries.size(); j++) {
						ProgrammingContracCostInfo pccInfo = costEntries.get(j);
						CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
						if (costAccountInfo != null) {
							if(costAccountInfo.getLongNumber()!=null){
								if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
									BigDecimal contractAssign = pccInfo.getContractAssign();
									if (contractAssign == null) {
										contractAssign = FDCHelper.ZERO;
									}
									allContractAssign = allContractAssign.add(contractAssign);
								}
							}
						}
					}
				}
			}
		}
		return allContractAssign;
	}
    public void cloneInsertHead(ProgrammingContractInfo head,ProgrammingContractCollection pcCollection,ProgrammingContractInfo newDetailInfo,AimCostInfo aimCostInfo) {
    	ProgrammingContracCostCollection  costColl = (ProgrammingContracCostCollection)head.getCostEntries();
    	newDetailInfo.getCostEntries().clear();
    	BigDecimal assin=FDCHelper.ZERO;
    	for(int i = 0 ; i < costColl.size(); i++){
    		
    		ProgrammingContracCostInfo oldInfo = costColl.get(i);
    		ProgrammingContracCostInfo info = (ProgrammingContracCostInfo)oldInfo.clone();
    		
    		BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(info.getCostAccount(),aimCostInfo);
    		
    		info.setContract(newDetailInfo);
    		info.setId(null);
    		info.setGoalCost(goalCost);
    		
    		BigDecimal allAssigned = getAllContractAssign(head,pcCollection,info.getCostAccount(), true);// �ѷ���
			// ���"������" == "Ŀ��ɱ�" - "�ѷ���"
			BigDecimal assigning = goalCost.subtract(allAssigned);// ������
			
    		info.setAssigned(allAssigned);
    		info.setContractAssign(assigning);
    		info.setAssigning(assigning);
    		assin=assin.add(assigning);
    		newDetailInfo.getCostEntries().add(info);
    		oldInfo.setAssigned(goalCost);
    		oldInfo.setAssigning(oldInfo.getContractAssign());
    	}
    	ProgrammingContractEconomyCollection economyColl = (ProgrammingContractEconomyCollection)head.getEconomyEntries();
    	newDetailInfo.getEconomyEntries().clear();
    	for(int i = 0 ; i < economyColl.size(); i++){
    		ProgrammingContractEconomyInfo oldInfo = economyColl.get(i);
    		ProgrammingContractEconomyInfo info = (ProgrammingContractEconomyInfo)oldInfo.clone();
    		info.setContract(newDetailInfo);
    		info.setId(null);
    		newDetailInfo.getEconomyEntries().add(info);
    	}
    	newDetailInfo.setBalance(assin);
	    newDetailInfo.setAmount(assin);
	    newDetailInfo.setCostAccountNames(head.getCostAccountNames());
	    newDetailInfo.setContractType(head.getContractType());
	}

	/**
     * ��ʾ������
     */
    public void loadLineFields(KDTable table, IRow row, IObjectValue obj)
    {
        dataBinder.loadLineFields(table, row, obj);
    }
}
