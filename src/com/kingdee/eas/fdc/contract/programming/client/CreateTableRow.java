package com.kingdee.eas.fdc.contract.programming.client;

import java.util.ArrayList;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;

public class CreateTableRow {
	
	protected DataBinder dataBinder = null;
	
	public CreateTableRow(DataBinder dataBinder){
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
     * ��ָ������в����У�һ�����ڵ�ǰѡ�е���һ�У���������
     *
     * @param table
     */
    public void insertLine(KDTable table , int rowIndex , int level , ProgrammingTemplateEntireInfo head) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table , level , head);
        IRow row = table.addRow(rowIndex);
        loadLineFields(table, row, detailData);
        loadLineFields(table, table.getRow(rowIndex-1), head);
        table.getRow(rowIndex-1).getCell("costAccount").getStyleAttributes().setLocked(true);
    }
    
    /**
     * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У�����������
     *
     * @param table
     */
    public void insertSameLine(KDTable table , int rowIndex , int level , ProgrammingTemplateEntireInfo head) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createSameNewDetailData(table , level , head);
        IRow row = table.addRow(rowIndex);
        loadLineFields(table, row, detailData);
    }
    
    private IObjectValue createSameNewDetailData(KDTable table, int level,ProgrammingTemplateEntireInfo head) {
    	 if(table == null)
         {
             return null;
         }
         ProgrammingTemplateEntireInfo newDetailInfo = new ProgrammingTemplateEntireInfo();
         newDetailInfo.setId(BOSUuid.create("B9EEC6B4"));
         newDetailInfo.setLevel(level);
         if(head != null)
         	newDetailInfo.setHead(head);
         return (IObjectValue) newDetailInfo;
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
     * clone�ϼ�����
     */
    protected IObjectValue createNewDetailData(KDTable table , int level , ProgrammingTemplateEntireInfo head)
    {
    	 if(table == null)
         {
             return null;
         }
    	 ProgrammingTemplateEntireInfo newDetailInfo = new ProgrammingTemplateEntireInfo();
         newDetailInfo.setId(BOSUuid.create(newDetailInfo.getBOSType()));
         newDetailInfo.setLevel(level);
         if(head != null){
         	newDetailInfo.setHead(head);
         	//clone �ϼ��ĳɱ����� �������� �����ϼ��ĳɱ����ɣ������������
         	cloneHead(head,newDetailInfo);
         	head.getPteCost().clear();
         	head.getPteEnonomy().clear();
         	
         	 newDetailInfo.setContractType(head.getContractType());
    	     head.setContractType(null);
         } 
        return (IObjectValue) newDetailInfo;
    }
    
    private void cloneHead(ProgrammingTemplateEntireInfo head,ProgrammingTemplateEntireInfo newDetailInfo) {
    	PTECostCollection pteCostColl =  newDetailInfo.getPteCost();
    	pteCostColl.clear();
    	PTECostCollection oldPteCostColl = head.getPteCost();
    	for(int i = 0 ; i < oldPteCostColl.size();i++){
    		PTECostInfo info = (PTECostInfo)oldPteCostColl.get(i).clone();
    		pteCostColl.add(info);
    	}
    	PTEEnonomyCollection pteEnonomyColl =  newDetailInfo.getPteEnonomy();
    	pteEnonomyColl.clear();
    	PTEEnonomyCollection oldPteEnonomyColl = head.getPteEnonomy();
    	for(int i = 0 ; i < oldPteEnonomyColl.size();i++){
    		PTEEnonomyInfo info = (PTEEnonomyInfo)oldPteEnonomyColl.get(i).clone();
    		pteEnonomyColl.add(info);
    	}
    	
	}

	/**
     * ��ʾ������
     */
    protected void loadLineFields(KDTable table, IRow row, IObjectValue obj)
    {
        dataBinder.loadLineFields(table, row, obj);
    }
}
